package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
                "listAgencyName", "PE:SUNAT",
                "listName", "Tipo de Documento",
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
                "listAgencyName", "United Nations Economic Commission for Europe",
                "listName", "Currency"));

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
                    "listAgencyName", "PE:SUNAT",
                    "listName", "Documento Relacionado",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"));
            ref.appendChild(cbcConAtributos(doc, "DocumentStatusCode", String.valueOf(idx),
                    "listName", "Anticipo",
                    "listAgencyName", "PE:SUNAT"));

            if (factura.getEmisor() != null) {
                Element issuer = cac(doc, "IssuerParty");
                Element partyId = cac(doc, "PartyIdentification");
                partyId.appendChild(cbcConAtributos(doc, "ID", factura.getEmisor().ruc(),
                        "schemeID", "6",
                        "schemeName", "Documento de Identidad",
                        "schemeAgencyName", "PE:SUNAT",
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
        Element pt = cac(doc, "PaymentTerms");
        pt.appendChild(cbc(doc, "ID", "Detraccion"));
        pt.appendChild(cbc(doc, "PaymentMeansID", det.tipoBienDetraido()));
        pt.appendChild(cbc(doc, "PaymentPercent",
                escalar(det.porcentaje().multiply(new BigDecimal("100")))));
        pt.appendChild(cbcMonto(doc, "Amount", det.monto(), moneda(factura)));
        raiz.appendChild(pt);
    }

    // ── Percepción PaymentTerms ──────────────────────────────────

    private void agregarPercepcionTerms(Document doc, Element raiz, BorradorFactura factura) {
        Percepcion perc = factura.getPercepcion();
        if (perc == null) return;
        Element pt = cac(doc, "PaymentTerms");
        pt.appendChild(cbc(doc, "ID", "Percepcion"));
        pt.appendChild(cbcMonto(doc, "Amount", perc.montoTotal(), "PEN"));
        raiz.appendChild(pt);
    }

    // ── Forma de pago ────────────────────────────────────────────

    private void agregarFormaDePago(Document doc, Element raiz, BorradorFactura factura) {
        FormaDePago fdp = factura.getFormaDePago();
        if (fdp == null) return;

        Element pt = cac(doc, "PaymentTerms");
        pt.appendChild(cbc(doc, "ID", "FormaPago"));
        pt.appendChild(cbc(doc, "PaymentMeansID", fdp.tipo()));
        if ("Credito".equals(fdp.tipo()) && fdp.total() != null) {
            pt.appendChild(cbcMonto(doc, "Amount", fdp.total(), moneda(factura)));
        }
        raiz.appendChild(pt);

        // Cuotas
        if (fdp.cuotas() != null) {
            int idx = 1;
            for (CuotaDePago cuota : fdp.cuotas()) {
                Element cuotaEl = cac(doc, "PaymentTerms");
                cuotaEl.appendChild(cbc(doc, "ID", "FormaPago"));
                cuotaEl.appendChild(cbc(doc, "PaymentMeansID", String.format("Cuota%03d", idx)));
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
        int idx = 1;
        for (Anticipo anticipo : factura.getAnticipos()) {
            Element prepaid = cac(doc, "PrepaidPayment");
            prepaid.appendChild(cbc(doc, "ID", String.valueOf(idx)));
            prepaid.appendChild(cbcMonto(doc, "PaidAmount", anticipo.monto(), moneda(factura)));
            raiz.appendChild(prepaid);
            idx++;
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
}
