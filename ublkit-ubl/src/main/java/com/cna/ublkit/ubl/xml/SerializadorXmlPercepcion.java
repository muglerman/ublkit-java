package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteAfectadoPR;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;
import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa un {@link ComprobantePercepcion} a XML SUNAT Perception-1.
 *
 * @since 0.1.0
 */
public final class SerializadorXmlPercepcion implements SerializadorXml<ComprobantePercepcion> {

    @Override
    public String serializar(ComprobantePercepcion percepcion) {
        Document doc = crearDocumentoSunat(NS_PERCEPTION, "Perception", true);
        Element raiz = doc.getDocumentElement();

        FragmentosXml.agregarExtensiones(doc, raiz);
        raiz.appendChild(cbc(doc, "UBLVersionID", UBL_VERSION_SUNAT));
        raiz.appendChild(cbc(doc, "CustomizationID", CUSTOMIZATION_ID_VOIDED));
        raiz.appendChild(cbc(doc, "ID", percepcion.getSerie() + "-" + percepcion.getNumero()));
        raiz.appendChild(cbc(doc, "IssueDate", percepcion.getFechaEmision()));
        raiz.appendChild(cbc(doc, "DocumentCurrencyCode", moneda(percepcion.getMoneda())));

        agregarFirma(doc, raiz, percepcion.getFirmante(), percepcion.getEmisor());
        agregarEmisor(doc, raiz, percepcion.getEmisor());
        agregarCliente(doc, raiz, percepcion.getCliente());
        agregarOperacion(doc, raiz, percepcion.getOperacion(), percepcion.getTipoRegimen(),
                percepcion.getTipoRegimenPorcentaje(), percepcion.getImporteTotalPercibido(),
                percepcion.getImporteTotalCobrado(), moneda(percepcion.getMoneda()));

        if (percepcion.getObservacion() != null && !percepcion.getObservacion().isBlank()) {
            raiz.appendChild(cbcCdata(doc, "Note", percepcion.getObservacion()));
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
                                  BigDecimal porcentaje, BigDecimal totalPercibido, BigDecimal totalCobrado,
                                  String moneda) {
        if (operacion == null) return;
        Element docRef = cac(doc, "SUNATPerceptionDocumentReference");
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

        docRef.appendChild(sacMonto(doc, "TotalCashPaymentAmount",
                totalCobrado != null ? totalCobrado : BigDecimal.ZERO, moneda));
        docRef.appendChild(cbcConAtributos(doc, "SUNATPerceptionSystemCode",
                tipoRegimen != null ? tipoRegimen : "01",
                "listName", "SUNAT:Codigo de regimen de percepcion",
                "listAgencyName", "PE:SUNAT"));
        docRef.appendChild(cbcConAtributos(doc, "SUNATPerceptionPercent",
                escalar(porcentaje != null ? porcentaje.multiply(new BigDecimal("100")) : BigDecimal.ZERO),
                "unitCode", "PCT"));
        docRef.appendChild(sacMonto(doc, "SUNATTotalCashed",
                totalCobrado != null ? totalCobrado : BigDecimal.ZERO, moneda));
        docRef.appendChild(sacMonto(doc, "SUNATNetTotalCashed",
                suma(totalCobrado, totalPercibido), moneda));
        raiz.appendChild(docRef);
    }

    private static BigDecimal suma(BigDecimal a, BigDecimal b) {
        return (a != null ? a : BigDecimal.ZERO).add(b != null ? b : BigDecimal.ZERO);
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

