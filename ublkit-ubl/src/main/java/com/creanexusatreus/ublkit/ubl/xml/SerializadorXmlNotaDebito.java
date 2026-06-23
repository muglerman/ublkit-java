package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
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
 * Serializa un {@link BorradorNotaDebito} a XML UBL 2.1 DebitNote.
 *
 * @since 0.1.0
 */
public final class SerializadorXmlNotaDebito implements SerializadorXml<BorradorNotaDebito> {

    @Override
    public String serializar(BorradorNotaDebito nota) {
        Document doc = crearDocumento(NS_DEBIT_NOTE, "DebitNote");
        Element raiz = doc.getDocumentElement();

        // 1. UBLExtensions
        agregarExtensiones(doc, raiz);

        // 2. Datos generales
        agregarDatosGenerales(doc, raiz, nota);

        // 3. Leyendas
        agregarLeyendas(doc, raiz, nota.getLeyendas());

        // 4. DocumentCurrencyCode
        raiz.appendChild(cbcConAtributos(doc, "DocumentCurrencyCode",
                nota.getMoneda() != null ? nota.getMoneda() : "PEN",
                "listID", "ISO 4217 Alpha",
                "listAgencyName", "United Nations Economic Commission for Europe",
                "listName", "Currency"));

        // 5. DiscrepancyResponse
        agregarDiscrepancy(doc, raiz, nota);

        // 6. OrderReference
        if (nota.getOrdenDeCompra() != null) {
            Element orderRef = cac(doc, "OrderReference");
            orderRef.appendChild(cbc(doc, "ID", nota.getOrdenDeCompra()));
            raiz.appendChild(orderRef);
        }

        // 7. BillingReference
        agregarBillingReference(doc, raiz, nota);

        // 8. Guías
        agregarGuias(doc, raiz, nota.getGuias());

        // 9. Documentos relacionados
        agregarDocumentosRelacionados(doc, raiz, nota.getDocumentosRelacionados());

        // 10. Signature
        agregarFirma(doc, raiz, nota);

        // 11. Emisor
        agregarEmisor(doc, raiz, nota.getEmisor());

        // 12. Receptor
        agregarReceptor(doc, raiz, nota.getReceptor());

        // 13. TaxTotal
        agregarTotalImpuestos(doc, raiz, nota.getTotalImpuestos(), moneda(nota));

        // 14. RequestedMonetaryTotal
        agregarMonetaryTotal(doc, raiz, nota);

        // 15. DebitNoteLines
        agregarDebitNoteLines(doc, raiz, nota);

        return documentoAString(doc);
    }

    private void agregarLeyendas(Document doc, Element raiz, Map<String, String> leyendas) {
        if (leyendas == null) return;
        for (Map.Entry<String, String> entry : leyendas.entrySet()) {
            Element note = doc.createElementNS(NS_CBC, "cbc:Note");
            note.setAttribute("languageLocaleID", entry.getKey());
            note.appendChild(doc.createCDATASection(entry.getValue()));
            raiz.appendChild(note);
        }
    }

    private void agregarDiscrepancy(Document doc, Element raiz, BorradorNotaDebito nota) {
        Element discrepancy = cac(doc, "DiscrepancyResponse");
        discrepancy.appendChild(cbc(doc, "ReferenceID",
                nota.getComprobanteAfectadoSerieNumero() != null ? nota.getComprobanteAfectadoSerieNumero() : ""));
        discrepancy.appendChild(cbc(doc, "ResponseCode", nota.getTipoNota() != null ? nota.getTipoNota() : "01"));
        discrepancy.appendChild(cbcCdata(doc, "Description",
                nota.getSustentoDescripcion() != null ? nota.getSustentoDescripcion() : ""));
        raiz.appendChild(discrepancy);
    }

    private void agregarBillingReference(Document doc, Element raiz, BorradorNotaDebito nota) {
        Element billing = cac(doc, "BillingReference");
        Element invoiceRef = cac(doc, "InvoiceDocumentReference");
        invoiceRef.appendChild(cbc(doc, "ID",
                nota.getComprobanteAfectadoSerieNumero() != null ? nota.getComprobanteAfectadoSerieNumero() : ""));
        invoiceRef.appendChild(cbcConAtributos(doc, "DocumentTypeCode",
                nota.getComprobanteAfectadoTipo() != null ? nota.getComprobanteAfectadoTipo() : "01",
                "listAgencyName", "PE:SUNAT",
                "listName", "Tipo de Documento",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"));
        billing.appendChild(invoiceRef);
        raiz.appendChild(billing);
    }

    private void agregarMonetaryTotal(Document doc, Element raiz, BorradorNotaDebito nota) {
        var total = nota.getTotalImporte();
        if (total == null) return;

        String m = moneda(nota);
        Element lmt = cac(doc, "RequestedMonetaryTotal");
        lmt.appendChild(cbcMonto(doc, "LineExtensionAmount", total.importeSinImpuestos(), m));
        lmt.appendChild(cbcMonto(doc, "TaxInclusiveAmount", total.importe(), m));
        lmt.appendChild(cbcMonto(doc, "PayableAmount", total.importe(), m));
        raiz.appendChild(lmt);
    }

    private void agregarDebitNoteLines(Document doc, Element raiz, BorradorNotaDebito nota) {
        List<LineaDetalle> detalles = nota.getDetalles();
        if (detalles == null) return;

        String m = moneda(nota);
        int idx = 1;
        for (LineaDetalle linea : detalles) {
            Element debitLine = cac(doc, "DebitNoteLine");
            debitLine.appendChild(cbc(doc, "ID", idx));
            debitLine.appendChild(cbcCantidad(doc, "DebitedQuantity",
                    linea.getCantidad() != null ? linea.getCantidad() : BigDecimal.ONE,
                    linea.getUnidadMedida() != null ? linea.getUnidadMedida() : "NIU"));

            BigDecimal extension = linea.getIscBaseImponible() != null
                    ? linea.getIscBaseImponible()
                    : (linea.getIgvBaseImponible() != null ? linea.getIgvBaseImponible() : BigDecimal.ZERO);
            debitLine.appendChild(cbcMonto(doc, "LineExtensionAmount", extension, m));

            agregarPricingReference(doc, debitLine, linea, m);
            agregarImpuestosLinea(doc, debitLine, linea, m);
            agregarItemYPrecio(doc, debitLine, linea, m);

            raiz.appendChild(debitLine);
            idx++;
        }
    }

    private String moneda(BorradorNotaDebito nota) {
        return nota.getMoneda() != null ? nota.getMoneda() : "PEN";
    }
}
