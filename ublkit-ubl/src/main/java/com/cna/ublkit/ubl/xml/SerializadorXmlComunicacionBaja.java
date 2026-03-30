package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;
import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa una {@link ComunicacionBaja} (o {@link com.cna.ublkit.ubl.modelo.sunat.baja.Reversion})
 * a XML VoidedDocuments 1.0 según la especificación SUNAT.
 * <p>
 * Formato: RA-{yyyyMMdd}-{numero} para Comunicación de Baja
 *          RR-{yyyyMMdd}-{numero} para Reversión
 * </p>
 *
 * @since 0.1.0
 */
public final class SerializadorXmlComunicacionBaja implements SerializadorXml<ComunicacionBaja> {

    private static final DateTimeFormatter FORMATO_ID_FECHA = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public String serializar(ComunicacionBaja comunicacion) {
        Document doc = crearDocumentoSunat(NS_VOIDED_DOCUMENTS, "VoidedDocuments", false);
        Element raiz = doc.getDocumentElement();

        // 1. UBLExtensions
        FragmentosXml.agregarExtensiones(doc, raiz);

        // 2. UBLVersionID y CustomizationID
        raiz.appendChild(cbc(doc, "UBLVersionID", UBL_VERSION_SUNAT));
        raiz.appendChild(cbc(doc, "CustomizationID", CUSTOMIZATION_ID_VOIDED));

        // 3. ID = RA-yyyyMMdd-numero
        String prefijo = esPrefijoBaja(comunicacion);
        String id = prefijo + "-" + comunicacion.getFechaEmision().format(FORMATO_ID_FECHA) + "-" + comunicacion.getNumero();
        raiz.appendChild(cbc(doc, "ID", id));

        // 4. ReferenceDate (fecha de emisión de los comprobantes dados de baja)
        raiz.appendChild(cbc(doc, "ReferenceDate", comunicacion.getFechaEmisionComprobantes()));

        // 5. IssueDate (fecha de la comunicación)
        raiz.appendChild(cbc(doc, "IssueDate", comunicacion.getFechaEmision()));

        // 6. Signature
        agregarFirmaSunat(doc, raiz, comunicacion);

        // 7. AccountingSupplierParty (formato SUNAT simplificado)
        agregarEmisorSunat(doc, raiz, comunicacion.getEmisor());

        // 8. VoidedDocumentsLine(s)
        agregarLineas(doc, raiz, comunicacion.getComprobantes());

        return documentoAString(doc);
    }

    // ── Helpers ──────────────────────────────────────────────────

    private String esPrefijoBaja(ComunicacionBaja comunicacion) {
        return comunicacion instanceof com.cna.ublkit.ubl.modelo.sunat.baja.Reversion ? "RR" : "RA";
    }

    private void agregarFirmaSunat(Document doc, Element raiz, ComunicacionBaja comunicacion) {
        FirmanteDocumento firmante = comunicacion.getFirmante();
        if (firmante == null && comunicacion.getEmisor() != null) {
            firmante = new FirmanteDocumento(comunicacion.getEmisor().ruc(), comunicacion.getEmisor().razonSocial());
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

    private void agregarLineas(Document doc, Element raiz, List<ItemBaja> comprobantes) {
        if (comprobantes == null) return;

        int lineId = 1;
        for (ItemBaja item : comprobantes) {
            Element line = sac(doc, "VoidedDocumentsLine");
            line.appendChild(cbc(doc, "LineID", lineId));
            line.appendChild(cbc(doc, "DocumentTypeCode", item.tipoComprobante()));
            line.appendChild(sac(doc, "DocumentSerialID", item.serie()));
            line.appendChild(sac(doc, "DocumentNumberID", String.valueOf(item.numero())));
            line.appendChild(sac(doc, "VoidReasonDescription", item.descripcionSustento()));
            raiz.appendChild(line);
            lineId++;
        }
    }
}
