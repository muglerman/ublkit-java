package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteAfectadoPR;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;
import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa un {@link ComprobanteRetencion} a XML SUNAT Retention-1.
 *
 * @since 0.1.0
 */
public final class SerializadorXmlRetencion implements SerializadorXml<ComprobanteRetencion> {

    @Override
    public String serializar(ComprobanteRetencion retencion) {
        Document doc = crearDocumentoSunat(NS_RETENTION, "Retention", true);
        Element raiz = doc.getDocumentElement();
        raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ns11", NS_RETENTION);

        FragmentosXml.agregarExtensiones(doc, raiz);
        raiz.appendChild(cbc(doc, "UBLVersionID", UBL_VERSION_SUNAT));
        raiz.appendChild(cbc(doc, "CustomizationID", CUSTOMIZATION_ID_VOIDED));
        raiz.appendChild(cbc(doc, "ID", retencion.getSerie() + "-" + retencion.getNumero()));
        raiz.appendChild(cbc(doc, "IssueDate", retencion.getFechaEmision()));
        raiz.appendChild(cbc(doc, "DocumentCurrencyCode", moneda(retencion.getMoneda())));

        agregarFirma(doc, raiz, retencion.getFirmante(), retencion.getEmisor());
        agregarEmisor(doc, raiz, retencion.getEmisor());
        agregarCliente(doc, raiz, retencion.getCliente());
        agregarOperacion(doc, raiz, retencion.getOperacion(), retencion.getTipoRegimen(),
                retencion.getTipoRegimenPorcentaje(), retencion.getImporteTotalRetenido(),
                retencion.getImporteTotalPagado(), moneda(retencion.getMoneda()));

        if (retencion.getObservacion() != null && !retencion.getObservacion().isBlank()) {
            raiz.appendChild(cbcCdata(doc, "Note", retencion.getObservacion()));
        }
        return documentoAString(doc);
    }

    private static String moneda(String moneda) {
        return (moneda == null || moneda.isBlank()) ? "PEN" : moneda;
    }

    private void agregarFirma(Document doc, Element raiz, FirmanteDocumento firmante, EmisorDocumento emisor) {
        if (firmante == null && emisor != null) {
            firmante = new FirmanteDocumento(emisor.ruc(), emisor.razonSocial());
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

    private void agregarEmisor(Document doc, Element raiz, EmisorDocumento emisor) {
        if (emisor == null) return;
        Element supplier = cac(doc, "AgentParty");
        Element partyId = cac(doc, "PartyIdentification");
        partyId.appendChild(cbcConAtributos(doc, "ID", emisor.ruc(), "schemeID", "6"));
        supplier.appendChild(partyId);
        Element legal = cac(doc, "PartyLegalEntity");
        legal.appendChild(cbcCdata(doc, "RegistrationName", emisor.razonSocial()));
        supplier.appendChild(legal);
        raiz.appendChild(supplier);
    }

    private void agregarCliente(Document doc, Element raiz, ReceptorDocumento cliente) {
        if (cliente == null) return;
        Element receiver = cac(doc, "ReceiverParty");
        Element partyId = cac(doc, "PartyIdentification");
        partyId.appendChild(cbcConAtributos(doc, "ID", cliente.numDocIdentidad(),
                "schemeID", cliente.tipoDocIdentidad() != null ? cliente.tipoDocIdentidad() : "6"));
        receiver.appendChild(partyId);
        Element legal = cac(doc, "PartyLegalEntity");
        legal.appendChild(cbcCdata(doc, "RegistrationName", cliente.nombre()));
        receiver.appendChild(legal);
        raiz.appendChild(receiver);
    }

    private void agregarOperacion(Document doc, Element raiz, OperacionPR operacion, String tipoRegimen,
                                  BigDecimal porcentaje, BigDecimal totalRetenido, BigDecimal totalPagado,
                                  String moneda) {
        if (operacion == null) return;
        Element docRef = cac(doc, "SUNATRetentionDocumentReference");
        docRef.appendChild(cbc(doc, "ID", String.valueOf(operacion.numeroOperacion() != null ? operacion.numeroOperacion() : 1)));
        docRef.appendChild(cbc(doc, "IssueDate", operacion.fechaOperacion()));

        ComprobanteAfectadoPR comprobante = operacion.comprobante();
        if (comprobante != null) {
            docRef.appendChild(cbc(doc, "DocumentTypeCode",
                    comprobante.tipoComprobante() != null ? comprobante.tipoComprobante() : "01"));
            docRef.appendChild(cbc(doc, "DocumentSerialID", serie(comprobante.serieNumero())));
            docRef.appendChild(cbc(doc, "DocumentNumberID", numero(comprobante.serieNumero())));
            docRef.appendChild(sacMonto(doc, "TotalInvoiceAmount",
                    comprobante.importeTotal() != null ? comprobante.importeTotal() : BigDecimal.ZERO,
                    moneda(comprobante.moneda())));
        }

        docRef.appendChild(sacMonto(doc, "PaidAmount", totalPagado != null ? totalPagado : BigDecimal.ZERO, moneda));
        docRef.appendChild(cbcConAtributos(doc, "SUNATRetentionSystemCode",
                tipoRegimen != null ? tipoRegimen : "01",
                "listName", "SUNAT:Codigo de regimen de retencion",
                "listAgencyName", "PE:SUNAT"));
        docRef.appendChild(cbcConAtributos(doc, "SUNATRetentionPercent",
                escalar(porcentaje != null ? porcentaje.multiply(new BigDecimal("100")) : BigDecimal.ZERO),
                "unitCode", "PCT"));
        docRef.appendChild(sacMonto(doc, "SUNATRetentionAmount",
                totalRetenido != null ? totalRetenido : BigDecimal.ZERO, moneda));
        docRef.appendChild(sacMonto(doc, "SUNATNetTotalPaid",
                resta(totalPagado, totalRetenido), moneda));
        raiz.appendChild(docRef);
    }

    private static BigDecimal resta(BigDecimal a, BigDecimal b) {
        return (a != null ? a : BigDecimal.ZERO).subtract(b != null ? b : BigDecimal.ZERO);
    }

    private static String serie(String serieNumero) {
        if (serieNumero == null || !serieNumero.contains("-")) return "0000";
        return serieNumero.substring(0, serieNumero.indexOf('-'));
    }

    private static String numero(String serieNumero) {
        if (serieNumero == null || !serieNumero.contains("-")) return "0";
        return serieNumero.substring(serieNumero.indexOf('-') + 1);
    }
}

