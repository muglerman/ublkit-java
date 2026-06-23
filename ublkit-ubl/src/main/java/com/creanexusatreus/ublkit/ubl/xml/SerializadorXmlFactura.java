package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.core.error.ExcepcionSerializacionXml;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;
import static com.cna.ublkit.ubl.xml.FragmentosXml.*;
import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa un {@link BorradorFactura} (Factura o Boleta) a XML UBL 2.1 Invoice.
 * <p>
 * Usa javax.xml DOM para construir el documento XML. El orden de los elementos
 * sigue la especificación UBL 2.1 conforme a las guías SUNAT.
 * </p>
 *
 * @since 0.1.0
 */
public final class SerializadorXmlFactura implements SerializadorXml<BorradorFactura> {

    private static final String ATTR_LIST_AGENCY_NAME = "listAgencyName";
    private static final String ATTR_LIST_NAME = "listName";
    private static final String VALUE_PE_SUNAT = "PE:SUNAT";
    private static final String TAG_PAYMENT_TERMS = "PaymentTerms";
    private static final String TAG_PAYMENT_MEANS_ID = "PaymentMeansID";
    private static final String TAG_INVOICE_CIERRE = "</Invoice>";

    @Override
    public String serializar(BorradorFactura factura) {
        Document doc = crearDocumento(NS_INVOICE, "Invoice");
        Element raiz = doc.getDocumentElement();

        // 1. UBLExtensions
        agregarExtensiones(doc, raiz);

        // 2. Datos generales
        agregarDatosGenerales(doc, raiz, factura);

        // 3. DueDate
        if (factura.getFechaVencimiento() != null) {
            raiz.appendChild(cbc(doc, "DueDate", factura.getFechaVencimiento()));
        }

        // 4. InvoiceTypeCode
        Element typeCode = cbcConAtributos(doc, "InvoiceTypeCode",
                factura.getTipoComprobante() != null ? factura.getTipoComprobante() : "01",
                "listID", factura.getTipoOperacion() != null ? factura.getTipoOperacion() : "0101",
                ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                ATTR_LIST_NAME, "Tipo de Documento",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01");
        raiz.appendChild(typeCode);

        // 5. Notes (leyendas)
        agregarLeyendas(doc, raiz, factura.getLeyendas());
        if (factura.getObservaciones() != null) {
            raiz.appendChild(cbcCdata(doc, "Note", factura.getObservaciones()));
        }

        // 6. DocumentCurrencyCode
        raiz.appendChild(cbcConAtributos(doc, "DocumentCurrencyCode",
                factura.getMoneda() != null ? factura.getMoneda() : "PEN",
                "listID", "ISO 4217 Alpha",
                ATTR_LIST_AGENCY_NAME, "United Nations Economic Commission for Europe",
                ATTR_LIST_NAME, "Currency"));

        // 7. OrderReference
        if (factura.getOrdenDeCompra() != null) {
            Element orderRef = cac(doc, "OrderReference");
            orderRef.appendChild(cbc(doc, "ID", factura.getOrdenDeCompra()));
            raiz.appendChild(orderRef);
        }

        // 8. DespatchDocumentReference (guías)
        agregarGuias(doc, raiz, factura.getGuias());

        // 9. AdditionalDocumentReference (docs relacionados)
        agregarDocumentosRelacionados(doc, raiz, factura.getDocumentosRelacionados());

        // 10. AdditionalDocumentReference (anticipos)
        agregarAnticiposRef(doc, raiz, factura);

        // 11. Signature
        agregarFirma(doc, raiz, factura);

        // 12. AccountingSupplierParty
        agregarEmisor(doc, raiz, factura.getEmisor());

        // 13. AccountingCustomerParty
        agregarReceptor(doc, raiz, factura.getReceptor());

        // 14. Delivery (dirección de entrega)
        agregarEntrega(doc, raiz, factura);

        // 15. PaymentMeans (detracción)
        agregarDetraccion(doc, raiz, factura);

        // 16. PaymentTerms (percepción)
        agregarPercepcionTerms(doc, raiz, factura);

        // 17. PaymentTerms (forma de pago)
        agregarFormaDePago(doc, raiz, factura);

        // 18. PrepaidPayment (anticipos monto)
        agregarAnticiposPrepaid(doc, raiz, factura);

        // 19. AllowanceCharge (anticipos como descuento)
        agregarAnticiposDescuento(doc, raiz, factura);

        // 20. AllowanceCharge (cargos globales)
        agregarCargosDescuentos(doc, raiz, factura.getCargos(), true, moneda(factura));

        // 21. AllowanceCharge (descuentos globales)
        agregarDescuentosGlobales(doc, raiz, factura);

        // 22. AllowanceCharge (percepción)
        agregarPercepcionCargo(doc, raiz, factura);

        // 23. TaxTotal
        agregarTotalImpuestos(doc, raiz, factura.getTotalImpuestos(), moneda(factura));

        // 24. LegalMonetaryTotal
        agregarLegalMonetaryTotal(doc, raiz, factura);

        // 25. InvoiceLines
        agregarLineas(doc, raiz, factura);

        return documentoAString(doc);
    }

    // ── Leyendas ─────────────────────────────────────────────────

    private void agregarLeyendas(Document doc, Element raiz, Map<String, String> leyendas) {
        if (leyendas == null) return;
        for (Map.Entry<String, String> entry : leyendas.entrySet()) {
            Element note = doc.createElementNS(NS_CBC, "cbc:Note");
            note.setAttribute("languageLocaleID", entry.getKey());
            note.appendChild(doc.createCDATASection(entry.getValue()));
            raiz.appendChild(note);
        }
    }

    // ── Anticipos DocRef ─────────────────────────────────────────

    private void agregarAnticiposRef(Document doc, Element raiz, BorradorFactura factura) {
        if (factura.getAnticipos() == null) return;
        int idx = 1;
        for (Anticipo anticipo : factura.getAnticipos()) {
            Element ref = cac(doc, "AdditionalDocumentReference");
            ref.appendChild(cbc(doc, "ID", anticipo.comprobanteSerieNumero()));
            ref.appendChild(cbcConAtributos(doc, "DocumentTypeCode", anticipo.comprobanteTipo(),
                    ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_LIST_NAME, "Documento Relacionado",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"));
            ref.appendChild(cbcConAtributos(doc, "DocumentStatusCode", String.valueOf(idx),
                    ATTR_LIST_NAME, "Anticipo",
                    ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT));

            if (factura.getEmisor() != null) {
                Element issuer = cac(doc, "IssuerParty");
                Element partyId = cac(doc, "PartyIdentification");
                partyId.appendChild(cbcConAtributos(doc, "ID", factura.getEmisor().ruc(),
                        "schemeID", "6",
                        "schemeName", "Documento de Identidad",
                        "schemeAgencyName", VALUE_PE_SUNAT,
                        "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"));
                issuer.appendChild(partyId);
                ref.appendChild(issuer);
            }

            raiz.appendChild(ref);
            idx++;
        }
    }

    // ── Entrega ──────────────────────────────────────────────────

    private void agregarEntrega(Document doc, Element raiz, BorradorFactura factura) {
        if (factura.getDireccionEntrega() != null) {
            Element delivery = cac(doc, "Delivery");
            Element location = cac(doc, "DeliveryLocation");
            Element address = cac(doc, "Address");
            agregarDireccion(doc, address, factura.getDireccionEntrega());
            location.appendChild(address);
            delivery.appendChild(location);
            raiz.appendChild(delivery);
        }
    }

    // ── Detracción ───────────────────────────────────────────────

    private void agregarDetraccion(Document doc, Element raiz, BorradorFactura factura) {
        Detraccion det = factura.getDetraccion();
        if (det == null) return;

        // PaymentMeans
        Element pm = cac(doc, "PaymentMeans");
        pm.appendChild(cbc(doc, "ID", "Detraccion"));
        pm.appendChild(cbc(doc, "PaymentMeansCode", det.medioDePago()));
        Element account = cac(doc, "PayeeFinancialAccount");
        account.appendChild(cbc(doc, "ID", det.cuentaBancaria()));
        pm.appendChild(account);
        raiz.appendChild(pm);

        // PaymentTerms
        Element pt = cac(doc, TAG_PAYMENT_TERMS);
        pt.appendChild(cbc(doc, "ID", "Detraccion"));
        pt.appendChild(cbc(doc, TAG_PAYMENT_MEANS_ID, det.tipoBienDetraido()));
        pt.appendChild(cbc(doc, "PaymentPercent",
                escalar(det.porcentaje().multiply(new BigDecimal("100")))));
        pt.appendChild(cbcMonto(doc, "Amount", det.monto(), moneda(factura)));
        raiz.appendChild(pt);
    }

    // ── Percepción PaymentTerms ──────────────────────────────────

    private void agregarPercepcionTerms(Document doc, Element raiz, BorradorFactura factura) {
        Percepcion perc = factura.getPercepcion();
        if (perc == null) return;
        Element pt = cac(doc, TAG_PAYMENT_TERMS);
        pt.appendChild(cbc(doc, "ID", "Percepcion"));
        pt.appendChild(cbcMonto(doc, "Amount", perc.montoTotal(), "PEN"));
        raiz.appendChild(pt);
    }

    // ── Forma de pago ────────────────────────────────────────────

    private void agregarFormaDePago(Document doc, Element raiz, BorradorFactura factura) {
        FormaDePago fdp = factura.getFormaDePago();
        if (fdp == null) return;

        Element pt = cac(doc, TAG_PAYMENT_TERMS);
        pt.appendChild(cbc(doc, "ID", "FormaPago"));
        pt.appendChild(cbc(doc, TAG_PAYMENT_MEANS_ID, fdp.tipo()));
        if ("Credito".equals(fdp.tipo()) && fdp.total() != null) {
            pt.appendChild(cbcMonto(doc, "Amount", fdp.total(), moneda(factura)));
        }
        raiz.appendChild(pt);

        // Cuotas
        if (fdp.cuotas() != null) {
            int idx = 1;
            for (CuotaDePago cuota : fdp.cuotas()) {
                Element cuotaEl = cac(doc, TAG_PAYMENT_TERMS);
                cuotaEl.appendChild(cbc(doc, "ID", "FormaPago"));
                cuotaEl.appendChild(cbc(doc, TAG_PAYMENT_MEANS_ID, String.format("Cuota%03d", idx)));
                cuotaEl.appendChild(cbcMonto(doc, "Amount", cuota.importe(), moneda(factura)));
                cuotaEl.appendChild(cbc(doc, "PaymentDueDate", cuota.fechaPago()));
                raiz.appendChild(cuotaEl);
                idx++;
            }
        }
    }

    // ── Anticipos PrepaidPayment ─────────────────────────────────

    private void agregarAnticiposPrepaid(Document doc, Element raiz, BorradorFactura factura) {
        if (factura.getAnticipos() == null) return;
        for (Anticipo anticipo : factura.getAnticipos()) {
            Element prepaid = cac(doc, "PrepaidPayment");
            prepaid.appendChild(cbc(doc, "ID", anticipo.comprobanteSerieNumero()));
            prepaid.appendChild(cbcMonto(doc, "PaidAmount", anticipo.monto(), moneda(factura)));
            raiz.appendChild(prepaid);
        }
    }

    // ── Anticipos como AllowanceCharge ───────────────────────────

    private void agregarAnticiposDescuento(Document doc, Element raiz, BorradorFactura factura) {
        if (factura.getAnticipos() == null) return;
        for (Anticipo anticipo : factura.getAnticipos()) {
            Element ac = cac(doc, "AllowanceCharge");
            ac.appendChild(cbc(doc, "ChargeIndicator", "false"));
            ac.appendChild(cbc(doc, "AllowanceChargeReasonCode", anticipo.tipo()));
            ac.appendChild(cbc(doc, "MultiplierFactorNumeric", "1"));
            ac.appendChild(cbcMonto(doc, "Amount", anticipo.monto(), moneda(factura)));
            ac.appendChild(cbcMonto(doc, "BaseAmount", anticipo.monto(), moneda(factura)));
            raiz.appendChild(ac);
        }
    }

    // ── Descuentos globales ──────────────────────────────────────

    private void agregarDescuentosGlobales(Document doc, Element raiz, BorradorFactura factura) {
        if (factura.getDescuentos() == null) return;
        for (Descuento desc : factura.getDescuentos()) {
            Element ac = cac(doc, "AllowanceCharge");
            ac.appendChild(cbc(doc, "ChargeIndicator", "false"));
            ac.appendChild(cbc(doc, "AllowanceChargeReasonCode", desc.tipo()));
            if (desc.factor() != null) {
                ac.appendChild(cbc(doc, "MultiplierFactorNumeric", escalar(desc.factor())));
            }
            ac.appendChild(cbcMonto(doc, "Amount", desc.monto(), moneda(factura)));
            ac.appendChild(cbcMonto(doc, "BaseAmount", desc.montoBase(), moneda(factura)));
            raiz.appendChild(ac);
        }
    }

    // ── Percepción AllowanceCharge ───────────────────────────────

    private void agregarPercepcionCargo(Document doc, Element raiz, BorradorFactura factura) {
        Percepcion perc = factura.getPercepcion();
        if (perc == null) return;
        Element ac = cac(doc, "AllowanceCharge");
        ac.appendChild(cbc(doc, "ChargeIndicator", "true"));
        ac.appendChild(cbc(doc, "AllowanceChargeReasonCode", perc.tipo()));
        ac.appendChild(cbc(doc, "MultiplierFactorNumeric", escalar(perc.porcentaje())));
        ac.appendChild(cbcMonto(doc, "Amount", perc.monto(), "PEN"));
        ac.appendChild(cbcMonto(doc, "BaseAmount", perc.montoBase(), "PEN"));
        raiz.appendChild(ac);
    }

    // ── LegalMonetaryTotal ───────────────────────────────────────

    private void agregarLegalMonetaryTotal(Document doc, Element raiz, BorradorFactura factura) {
        var total = factura.getTotalImporte();
        if (total == null) return;

        String m = moneda(factura);
        Element lmt = cac(doc, "LegalMonetaryTotal");
        lmt.appendChild(cbcMonto(doc, "LineExtensionAmount", total.importeSinImpuestos(), m));
        lmt.appendChild(cbcMonto(doc, "TaxInclusiveAmount", total.importeConImpuestos(), m));
        if (total.descuentos() != null) {
            lmt.appendChild(cbcMonto(doc, "AllowanceTotalAmount", total.descuentos(), m));
        }
        if (total.anticipos() != null) {
            lmt.appendChild(cbcMonto(doc, "PrepaidAmount", total.anticipos(), m));
        }
        lmt.appendChild(cbcMonto(doc, "PayableAmount", total.importe(), m));
        raiz.appendChild(lmt);
    }

    // ── InvoiceLines ─────────────────────────────────────────────

    private void agregarLineas(Document doc, Element raiz, BorradorFactura factura) {
        List<LineaDetalle> detalles = factura.getDetalles();
        if (detalles == null) return;

        String m = moneda(factura);
        int idx = 1;
        for (LineaDetalle linea : detalles) {
            Element invoiceLine = cac(doc, "InvoiceLine");
            invoiceLine.appendChild(cbc(doc, "ID", idx));

            // InvoicedQuantity
            invoiceLine.appendChild(cbcCantidad(doc, "InvoicedQuantity",
                    linea.getCantidad() != null ? linea.getCantidad() : BigDecimal.ONE,
                    linea.getUnidadMedida() != null ? linea.getUnidadMedida() : "NIU"));

            // LineExtensionAmount
            BigDecimal extension = linea.getIscBaseImponible() != null
                    ? linea.getIscBaseImponible()
                    : (linea.getIgvBaseImponible() != null ? linea.getIgvBaseImponible() : BigDecimal.ZERO);
            invoiceLine.appendChild(cbcMonto(doc, "LineExtensionAmount", extension, m));

            // PricingReference
            agregarPricingReference(doc, invoiceLine, linea, m);

            // TaxTotal
            agregarImpuestosLinea(doc, invoiceLine, linea, m);

            // Item + Price
            agregarItemYPrecio(doc, invoiceLine, linea, m);

            raiz.appendChild(invoiceLine);
            idx++;
        }
    }

    // ── Helpers ──────────────────────────────────────────────────

    private String moneda(BorradorFactura factura) {
        return factura.getMoneda() != null ? factura.getMoneda() : "PEN";
    }

    /**
     * Serializa una factura usando escritura streaming (StAX) para las líneas de detalle.
     * <p>
     * Esta variante reduce el uso de memoria en documentos con miles de líneas,
     * evitando construir en DOM todas las {@code cac:InvoiceLine} al mismo tiempo.
     * </p>
     */
    public void serializarStreaming(BorradorFactura factura, OutputStream outputStream) {
        if (factura == null) {
            throw new ExcepcionSerializacionXml("Factura no puede ser null");
        }
        if (outputStream == null) {
            throw new ExcepcionSerializacionXml("OutputStream no puede ser null");
        }

        try {
            String cabeceraSinLineas = extraerCabeceraSinLineas(factura);
            outputStream.write(cabeceraSinLineas.getBytes(StandardCharsets.ISO_8859_1));

            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
            XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream, StandardCharsets.ISO_8859_1.name());

            escribirLineasStreaming(writer, factura);
            writer.flush();

            outputStream.write(TAG_INVOICE_CIERRE.getBytes(StandardCharsets.ISO_8859_1));
            outputStream.flush();
        } catch (Exception e) {
            throw new ExcepcionSerializacionXml("Error serializando factura en modo streaming", e);
        }
    }

    /**
     * Serializa una factura en modo streaming y retorna el XML como String.
     */
    public String serializarStreaming(BorradorFactura factura) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializarStreaming(factura, baos);
        return baos.toString(StandardCharsets.ISO_8859_1);
    }

    private String extraerCabeceraSinLineas(BorradorFactura factura) {
        BorradorFactura clon = clonarSinLineas(factura);
        String xmlSinLineas = serializar(clon);
        int idxCierre = xmlSinLineas.lastIndexOf(TAG_INVOICE_CIERRE);
        if (idxCierre < 0) {
            throw new ExcepcionSerializacionXml("No se pudo ubicar cierre de Invoice al preparar modo streaming");
        }
        return xmlSinLineas.substring(0, idxCierre);
    }

    private BorradorFactura clonarSinLineas(BorradorFactura factura) {
        BorradorFactura clon = new BorradorFactura();

        clon.setSerie(factura.getSerie());
        clon.setNumero(factura.getNumero());
        clon.setMoneda(factura.getMoneda());
        clon.setFechaEmision(factura.getFechaEmision());
        clon.setHoraEmision(factura.getHoraEmision());
        clon.setEmisor(factura.getEmisor());
        clon.setReceptor(factura.getReceptor());
        clon.setFirmante(factura.getFirmante());
        clon.setTasaIgv(factura.getTasaIgv());
        clon.setTasaIvap(factura.getTasaIvap());
        clon.setTasaIcb(factura.getTasaIcb());
        clon.setLeyendas(factura.getLeyendas());
        clon.setTipoCambio(factura.getTipoCambio());
        clon.setOrdenDeCompra(factura.getOrdenDeCompra());
        clon.setGuias(factura.getGuias());
        clon.setDocumentosRelacionados(factura.getDocumentosRelacionados());
        clon.setCargos(factura.getCargos());
        clon.setTotalImpuestos(factura.getTotalImpuestos());

        clon.setTipoComprobante(factura.getTipoComprobante());
        clon.setTipoOperacion(factura.getTipoOperacion());
        clon.setFechaVencimiento(factura.getFechaVencimiento());
        clon.setObservaciones(factura.getObservaciones());
        clon.setFormaDePago(factura.getFormaDePago());
        clon.setTotalImporte(factura.getTotalImporte());
        clon.setDireccionEntrega(factura.getDireccionEntrega());
        clon.setDetraccion(factura.getDetraccion());
        clon.setPercepcion(factura.getPercepcion());
        clon.setAnticipos(factura.getAnticipos());
        clon.setDescuentos(factura.getDescuentos());
        clon.setGuiaEmbebida(factura.getGuiaEmbebida());

        clon.setDetalles(null);
        return clon;
    }

    private void escribirLineasStreaming(XMLStreamWriter writer, BorradorFactura factura) throws XMLStreamException {
        List<LineaDetalle> detalles = factura.getDetalles();
        if (detalles == null) {
            return;
        }

        String moneda = moneda(factura);
        int idx = 1;
        for (LineaDetalle linea : detalles) {
            writer.writeStartElement("cac", "InvoiceLine", NS_CAC);

            escribirCbc(writer, "ID", String.valueOf(idx));
            escribirCbcCantidad(writer, "InvoicedQuantity",
                    linea.getCantidad() != null ? linea.getCantidad() : BigDecimal.ONE,
                    linea.getUnidadMedida() != null ? linea.getUnidadMedida() : "NIU");

            BigDecimal extension = linea.getIscBaseImponible() != null
                    ? linea.getIscBaseImponible()
                    : (linea.getIgvBaseImponible() != null ? linea.getIgvBaseImponible() : BigDecimal.ZERO);
            escribirCbcMonto(writer, "LineExtensionAmount", extension, moneda);

            escribirPricingReferenceStreaming(writer, linea, moneda);
            escribirImpuestosLineaStreaming(writer, linea, moneda);
            escribirItemYPrecioStreaming(writer, linea, moneda);

            writer.writeEndElement();
            idx++;
        }
    }

    private void escribirPricingReferenceStreaming(XMLStreamWriter writer, LineaDetalle linea, String moneda) throws XMLStreamException {
        if (linea.getPrecioReferencia() == null) {
            return;
        }

        writer.writeStartElement("cac", "PricingReference", NS_CAC);
        writer.writeStartElement("cac", "AlternativeConditionPrice", NS_CAC);
        escribirCbcMonto(writer, "PriceAmount", linea.getPrecioReferencia(), moneda);
        escribirCbcConAtributos(writer, "PriceTypeCode",
                linea.getPrecioReferenciaTipo() != null ? linea.getPrecioReferenciaTipo() : "01",
                "listAgencyName", "PE:SUNAT",
                "listName", "Tipo de Precio",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16");
        writer.writeEndElement();
        writer.writeEndElement();
    }

    private void escribirImpuestosLineaStreaming(XMLStreamWriter writer, LineaDetalle linea, String moneda) throws XMLStreamException {
        writer.writeStartElement("cac", "TaxTotal", NS_CAC);
        escribirCbcMonto(writer, "TaxAmount", orZero(linea.getTotalImpuestos()), moneda);

        if (linea.getIsc() != null) {
            writer.writeStartElement("cac", "TaxSubtotal", NS_CAC);
            escribirCbcMonto(writer, "TaxableAmount", orZero(linea.getIscBaseImponible()), moneda);
            escribirCbcMonto(writer, "TaxAmount", linea.getIsc(), moneda);

            writer.writeStartElement("cac", "TaxCategory", NS_CAC);
            escribirCbcConAtributos(writer, "Percent",
                    escalar(linea.getTasaIsc() != null ? linea.getTasaIsc().multiply(new BigDecimal("100")) : BigDecimal.ZERO));
            if (linea.getIscTipo() != null) {
                escribirCbc(writer, "TierRange", linea.getIscTipo());
            }
            writer.writeStartElement("cac", "TaxScheme", NS_CAC);
            escribirCbc(writer, "ID", "2000");
            escribirCbc(writer, "Name", "ISC");
            escribirCbc(writer, "TaxTypeCode", "EXC");
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
        }

        writer.writeStartElement("cac", "TaxSubtotal", NS_CAC);
        escribirCbcMonto(writer, "TaxableAmount", orZero(linea.getIgvBaseImponible()), moneda);
        escribirCbcMonto(writer, "TaxAmount", orZero(linea.getIgv()), moneda);

        writer.writeStartElement("cac", "TaxCategory", NS_CAC);
        CategoriaIgv cat = CategoriaIgv.obtener(linea.getIgvTipo());
        escribirCbcConAtributos(writer, "ID", cat.categoriaId(),
                "schemeAgencyName", "United Nations Economic Commission for Europe",
                "schemeID", "UN/ECE 5305",
                "schemeName", "Tax Category Identifier");
        escribirCbcConAtributos(writer, "Percent",
                escalar(linea.getTasaIgv() != null ? linea.getTasaIgv().multiply(new BigDecimal("100")) : BigDecimal.ZERO));
        escribirCbcConAtributos(writer, "TaxExemptionReasonCode",
                linea.getIgvTipo() != null ? linea.getIgvTipo() : "10",
                "listAgencyName", "PE:SUNAT",
                "listName", "Afectacion del IGV",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07");

        writer.writeStartElement("cac", "TaxScheme", NS_CAC);
        escribirCbcConAtributos(writer, "ID", cat.tribCode(),
                "schemeAgencyName", "PE:SUNAT",
                "schemeID", "UN/ECE 5153",
                "schemeName", "Codigo de tributos");
        escribirCbc(writer, "Name", cat.tribName());
        escribirCbc(writer, "TaxTypeCode", cat.tribTypeCode());
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndElement();

        if (linea.getIcb() != null) {
            writer.writeStartElement("cac", "TaxSubtotal", NS_CAC);
            escribirCbcMonto(writer, "TaxAmount", linea.getIcb(), moneda);
            escribirCbcCantidad(writer, "BaseUnitMeasure",
                    linea.getCantidad() != null ? linea.getCantidad() : BigDecimal.ONE,
                    linea.getUnidadMedida() != null ? linea.getUnidadMedida() : "NIU");

            writer.writeStartElement("cac", "TaxCategory", NS_CAC);
            escribirCbcMonto(writer, "PerUnitAmount", orZero(linea.getTasaIcb()), moneda);

            writer.writeStartElement("cac", "TaxScheme", NS_CAC);
            escribirCbcConAtributos(writer, "ID", "7152",
                    "schemeAgencyName", "PE:SUNAT",
                    "schemeID", "UN/ECE 5153",
                    "schemeName", "Codigo de tributos");
            escribirCbc(writer, "Name", "ICBPER");
            escribirCbc(writer, "TaxTypeCode", "OTH");
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
        }

        writer.writeEndElement();
    }

    private void escribirItemYPrecioStreaming(XMLStreamWriter writer, LineaDetalle linea, String moneda) throws XMLStreamException {
        writer.writeStartElement("cac", "Item", NS_CAC);
        writer.writeStartElement("cbc", "Description", NS_CBC);
        writer.writeCData(linea.getDescripcion() != null ? linea.getDescripcion() : "");
        writer.writeEndElement();

        if (linea.getCodigoProducto() != null) {
            writer.writeStartElement("cac", "SellersItemIdentification", NS_CAC);
            escribirCbc(writer, "ID", linea.getCodigoProducto());
            writer.writeEndElement();
        }
        if (linea.getCodigoProductoGS1() != null) {
            writer.writeStartElement("cac", "StandardItemIdentification", NS_CAC);
            escribirCbcConAtributos(writer, "ID", linea.getCodigoProductoGS1(), "schemeID", "GTIN");
            writer.writeEndElement();
        }
        if (linea.getCodigoProductoSunat() != null) {
            writer.writeStartElement("cac", "CommodityClassification", NS_CAC);
            escribirCbcConAtributos(writer, "ItemClassificationCode", linea.getCodigoProductoSunat(),
                    "listID", "UNSPSC",
                    "listAgencyName", "GS1 US",
                    "listName", "Item Classification");
            writer.writeEndElement();
        }
        writer.writeEndElement();

        writer.writeStartElement("cac", "Price", NS_CAC);
        escribirCbcMonto(writer, "PriceAmount", orZero(linea.getPrecio()), moneda);
        writer.writeEndElement();
    }

    private void escribirCbc(XMLStreamWriter writer, String nombre, String valor) throws XMLStreamException {
        writer.writeStartElement("cbc", nombre, NS_CBC);
        writer.writeCharacters(valor != null ? valor : "");
        writer.writeEndElement();
    }

    private void escribirCbcConAtributos(XMLStreamWriter writer, String nombre, String valor, String... atributos)
            throws XMLStreamException {
        writer.writeStartElement("cbc", nombre, NS_CBC);
        for (int i = 0; i < atributos.length - 1; i += 2) {
            writer.writeAttribute(atributos[i], atributos[i + 1]);
        }
        writer.writeCharacters(valor != null ? valor : "");
        writer.writeEndElement();
    }

    private void escribirCbcMonto(XMLStreamWriter writer, String nombre, BigDecimal monto, String moneda)
            throws XMLStreamException {
        writer.writeStartElement("cbc", nombre, NS_CBC);
        writer.writeAttribute("currencyID", moneda);
        writer.writeCharacters(escalar(monto));
        writer.writeEndElement();
    }

    private void escribirCbcCantidad(XMLStreamWriter writer, String nombre, BigDecimal cantidad, String unidad)
            throws XMLStreamException {
        writer.writeStartElement("cbc", nombre, NS_CBC);
        writer.writeAttribute("unitCode", unidad);
        writer.writeAttribute("unitCodeListAgencyName", "United Nations Economic Commission for Europe");
        writer.writeAttribute("unitCodeListID", "UN/ECE rec 20");
        writer.writeCharacters(cantidad.toPlainString());
        writer.writeEndElement();
    }

    private BigDecimal orZero(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }
}
