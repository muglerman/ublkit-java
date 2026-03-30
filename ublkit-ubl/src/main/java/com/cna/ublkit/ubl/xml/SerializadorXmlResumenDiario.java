package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;
import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa un {@link ResumenDiario} a XML SummaryDocuments 1.1 según la especificación SUNAT.
 * <p>
 * Formato: RC-{yyyyMMdd}-{numero}
 * </p>
 *
 * @since 0.1.0
 */
public final class SerializadorXmlResumenDiario implements SerializadorXml<ResumenDiario> {

    private static final DateTimeFormatter FORMATO_ID_FECHA = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String serializar(ResumenDiario resumen) {
        Document doc = crearDocumentoSunat(NS_SUMMARY_DOCUMENTS, "SummaryDocuments", true);
        Element raiz = doc.getDocumentElement();

        // 1. UBLExtensions
        FragmentosXml.agregarExtensiones(doc, raiz);

        // 2. UBLVersionID y CustomizationID
        raiz.appendChild(cbc(doc, "UBLVersionID", UBL_VERSION_SUNAT));
        raiz.appendChild(cbc(doc, "CustomizationID", CUSTOMIZATION_ID_SUMMARY));

        // 3. ID = RC-yyyyMMdd-numero
        String id = "RC-" + resumen.getFechaEmision().format(FORMATO_ID_FECHA) + "-" + resumen.getNumero();
        raiz.appendChild(cbc(doc, "ID", id));

        // 4. ReferenceDate
        raiz.appendChild(cbc(doc, "ReferenceDate", resumen.getFechaEmisionComprobantes()));

        // 5. IssueDate
        raiz.appendChild(cbc(doc, "IssueDate", resumen.getFechaEmision()));

        // 6. Signature
        agregarFirmaSunat(doc, raiz, resumen);

        // 7. AccountingSupplierParty
        agregarEmisorSunat(doc, raiz, resumen.getEmisor());

        // 8. SummaryDocumentsLine(s)
        agregarLineas(doc, raiz, resumen.getComprobantes());

        return documentoAString(doc);
    }

    // ── Firma ────────────────────────────────────────────────────

    private void agregarFirmaSunat(Document doc, Element raiz, ResumenDiario resumen) {
        FirmanteDocumento firmante = resumen.getFirmante();
        if (firmante == null && resumen.getEmisor() != null) {
            firmante = new FirmanteDocumento(resumen.getEmisor().ruc(), resumen.getEmisor().razonSocial());
        }
        if (firmante == null) return;

        Element signature = cac(doc, "Signature");
        signature.appendChild(cbc(doc, "ID", firmante.ruc()));

        Element signatoryParty = cac(doc, "SignatoryParty");
        Element partyId = cac(doc, "PartyIdentification");
        partyId.appendChild(cbc(doc, "ID", firmante.ruc()));
        signatoryParty.appendChild(partyId);

        Element partyName = cac(doc, "PartyName");
        partyName.appendChild(cbcCdata(doc, "Name", firmante.razonSocial()));
        signatoryParty.appendChild(partyName);
        signature.appendChild(signatoryParty);

        Element attachment = cac(doc, "DigitalSignatureAttachment");
        Element extRef = cac(doc, "ExternalReference");
        extRef.appendChild(cbc(doc, "URI", "#UBLKIT-SIGN"));
        attachment.appendChild(extRef);
        signature.appendChild(attachment);

        raiz.appendChild(signature);
    }

    // ── Emisor SUNAT ─────────────────────────────────────────────

    private void agregarEmisorSunat(Document doc, Element raiz, EmisorDocumento emisor) {
        if (emisor == null) return;

        Element supplier = cac(doc, "AccountingSupplierParty");
        supplier.appendChild(cbc(doc, "CustomerAssignedAccountID", emisor.ruc()));
        supplier.appendChild(cbc(doc, "AdditionalAccountID", "6"));

        Element party = cac(doc, "Party");
        Element legalEntity = cac(doc, "PartyLegalEntity");
        legalEntity.appendChild(cbcCdata(doc, "RegistrationName", emisor.razonSocial()));
        party.appendChild(legalEntity);
        supplier.appendChild(party);

        raiz.appendChild(supplier);
    }

    // ── Líneas del Resumen ───────────────────────────────────────

    private void agregarLineas(Document doc, Element raiz, List<ItemResumenDiario> comprobantes) {
        if (comprobantes == null) return;

        int lineId = 1;
        for (ItemResumenDiario item : comprobantes) {
            ComprobanteResumen comp = item.comprobante();
            String moneda = comp.getMoneda() != null ? comp.getMoneda() : "PEN";

            Element line = sac(doc, "SummaryDocumentsLine");

            // LineID
            line.appendChild(cbc(doc, "LineID", lineId));

            // DocumentTypeCode
            line.appendChild(cbc(doc, "DocumentTypeCode", comp.getTipoComprobante()));

            // ID (serie-número del comprobante)
            line.appendChild(cbc(doc, "ID", comp.getSerieNumero()));

            // AccountingCustomerParty (cliente)
            agregarClienteResumen(doc, line, comp.getCliente());

            // BillingReference (comprobante afectado, para notas)
            agregarComprobanteAfectado(doc, line, comp.getComprobanteAfectado());

            // SUNATPerceptionSummaryDocumentReference (percepción)
            agregarPercepcionResumen(doc, line, comp.getPercepcion());

            // Status (tipo de operación)
            Element status = cac(doc, "Status");
            status.appendChild(cbc(doc, "ConditionCode", item.tipoOperacion()));
            line.appendChild(status);

            // TotalAmount
            line.appendChild(sacMonto(doc, "TotalAmount", comp.getValorVenta().importeTotal(), moneda));

            // BillingPayments (gravado, exonerado, inafecto, gratuito)
            agregarBillingPayments(doc, line, comp.getValorVenta(), moneda);

            // AllowanceCharge (otrosCargos)
            agregarOtrosCargos(doc, line, comp.getValorVenta(), moneda);

            // TaxTotals (IGV, ICBPER, ISC, IVAP, OTROS)
            agregarImpuestosResumen(doc, line, comp.getImpuestos(), moneda);

            raiz.appendChild(line);
            lineId++;
        }
    }

    // ── Cliente del comprobante ──────────────────────────────────

    private void agregarClienteResumen(Document doc, Element line, ReceptorDocumento cliente) {
        if (cliente == null) return;

        Element customer = cac(doc, "AccountingCustomerParty");
        customer.appendChild(cbc(doc, "CustomerAssignedAccountID", cliente.numDocIdentidad()));
        customer.appendChild(cbc(doc, "AdditionalAccountID", cliente.tipoDocIdentidad()));
        line.appendChild(customer);
    }

    // ── Comprobante afectado (BillingReference) ──────────────────

    private void agregarComprobanteAfectado(Document doc, Element line, ComprobanteAfectadoResumen afectado) {
        if (afectado == null) return;

        Element billingRef = cac(doc, "BillingReference");
        Element invoiceRef = cac(doc, "InvoiceDocumentReference");
        invoiceRef.appendChild(cbc(doc, "ID", afectado.serieNumero()));
        invoiceRef.appendChild(cbc(doc, "DocumentTypeCode", afectado.tipoComprobante()));
        billingRef.appendChild(invoiceRef);
        line.appendChild(billingRef);
    }

    // ── Percepción ───────────────────────────────────────────────

    private void agregarPercepcionResumen(Document doc, Element line, PercepcionResumen percepcion) {
        if (percepcion == null) return;

        Element perc = sac(doc, "SUNATPerceptionSummaryDocumentReference");
        perc.appendChild(sac(doc, "SUNATPerceptionSystemCode", percepcion.codReg()));
        perc.appendChild(sac(doc, "SUNATPerceptionPercent", escalar(percepcion.tasa())));
        perc.appendChild(cbcMonto(doc, "TotalInvoiceAmount", percepcion.mto(), "PEN"));
        perc.appendChild(sacMonto(doc, "SUNATTotalCashed", percepcion.mtoTotal(), "PEN"));
        perc.appendChild(cbcMonto(doc, "TaxableAmount", percepcion.mtoBase(), "PEN"));
        line.appendChild(perc);
    }

    // ── BillingPayments ──────────────────────────────────────────

    private void agregarBillingPayments(Document doc, Element line, ComprobanteValorVenta vv, String moneda) {
        if (vv == null) return;

        if (vv.gravado() != null) {
            agregarBillingPayment(doc, line, vv.gravado(), "01", moneda);
        }
        if (vv.exonerado() != null) {
            agregarBillingPayment(doc, line, vv.exonerado(), "02", moneda);
        }
        if (vv.inafecto() != null) {
            agregarBillingPayment(doc, line, vv.inafecto(), "03", moneda);
        }
        if (vv.gratuito() != null) {
            agregarBillingPayment(doc, line, vv.gratuito(), "05", moneda);
        }
    }

    private void agregarBillingPayment(Document doc, Element line, BigDecimal monto, String instruccion, String moneda) {
        Element bp = sac(doc, "BillingPayment");
        bp.appendChild(cbcMonto(doc, "PaidAmount", monto, moneda));
        bp.appendChild(cbc(doc, "InstructionID", instruccion));
        line.appendChild(bp);
    }

    // ── OtrosCargos (AllowanceCharge) ────────────────────────────

    private void agregarOtrosCargos(Document doc, Element line, ComprobanteValorVenta vv, String moneda) {
        if (vv == null || vv.otrosCargos() == null) return;

        Element ac = cac(doc, "AllowanceCharge");
        ac.appendChild(cbc(doc, "ChargeIndicator", "true"));
        ac.appendChild(cbcMonto(doc, "Amount", vv.otrosCargos(), moneda));
        line.appendChild(ac);
    }

    // ── Impuestos del Resumen ────────────────────────────────────

    private void agregarImpuestosResumen(Document doc, Element line, ComprobanteImpuestos imp, String moneda) {
        if (imp == null) return;

        // IGV (siempre)
        agregarTaxTotalResumen(doc, line, imp.igv(), moneda,
                imp.tasaIgv(), "1000", "IGV", "VAT");

        // ICBPER
        if (imp.icb() != null) {
            agregarTaxTotalSimple(doc, line, imp.icb(), moneda, "7152", "ICBPER", "OTH");
        }

        // ISC
        if (imp.isc() != null) {
            agregarTaxTotalSimple(doc, line, imp.isc(), moneda, "2000", "ISC", "EXC");
        }

        // IVAP
        if (imp.ivap() != null) {
            agregarTaxTotalSimple(doc, line, imp.ivap(), moneda, "1016", "IVAP", "VAT");
        }

        // OTROS
        if (imp.otros() != null) {
            agregarTaxTotalSimple(doc, line, imp.otros(), moneda, "9999", "OTROS", "OTH");
        }
    }

    private void agregarTaxTotalResumen(Document doc, Element line, BigDecimal monto, String moneda,
                                         BigDecimal tasa, String tribCode, String tribName, String tribTypeCode) {
        Element taxTotal = cac(doc, "TaxTotal");
        taxTotal.appendChild(cbcMonto(doc, "TaxAmount", monto != null ? monto : BigDecimal.ZERO, moneda));

        Element subtotal = cac(doc, "TaxSubtotal");
        subtotal.appendChild(cbcMonto(doc, "TaxAmount", monto != null ? monto : BigDecimal.ZERO, moneda));

        Element category = cac(doc, "TaxCategory");
        if (tasa != null) {
            category.appendChild(cbc(doc, "Percent", escalar(tasa.multiply(new BigDecimal("100")))));
        }

        Element scheme = cac(doc, "TaxScheme");
        scheme.appendChild(cbc(doc, "ID", tribCode));
        scheme.appendChild(cbc(doc, "Name", tribName));
        scheme.appendChild(cbc(doc, "TaxTypeCode", tribTypeCode));
        category.appendChild(scheme);

        subtotal.appendChild(category);
        taxTotal.appendChild(subtotal);
        line.appendChild(taxTotal);
    }

    private void agregarTaxTotalSimple(Document doc, Element line, BigDecimal monto, String moneda,
                                        String tribCode, String tribName, String tribTypeCode) {
        Element taxTotal = cac(doc, "TaxTotal");
        taxTotal.appendChild(cbcMonto(doc, "TaxAmount", monto, moneda));

        Element subtotal = cac(doc, "TaxSubtotal");
        subtotal.appendChild(cbcMonto(doc, "TaxAmount", monto, moneda));

        Element category = cac(doc, "TaxCategory");
        Element scheme = cac(doc, "TaxScheme");
        scheme.appendChild(cbc(doc, "ID", tribCode));
        scheme.appendChild(cbc(doc, "Name", tribName));
        scheme.appendChild(cbc(doc, "TaxTypeCode", tribTypeCode));
        category.appendChild(scheme);

        subtotal.appendChild(category);
        taxTotal.appendChild(subtotal);
        line.appendChild(taxTotal);
    }
}
