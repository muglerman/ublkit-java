package com.creanexusatreus.ublkit.sign.api;

import com.creanexusatreus.ublkit.sign.certificado.DetallesCertificado;
import com.creanexusatreus.ublkit.sign.xml.FirmadorXml;
import com.creanexusatreus.ublkit.sign.xml.XmlHelper;
import com.creanexusatreus.ublkit.core.valor.IdentificadoresFirma;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.charset.StandardCharsets;

/**
 * Servicio de alto nivel para firmar documentos XML UBL.
 * <p>
 * Orquesta la carga del XML, la firma digital y la serialización del resultado.
 * Esta es la API pública principal del módulo {@code ublkit-sign}.
 * </p>
 *
 * <pre>{@code
 * // Ejemplo de uso:
 * DetallesCertificado cert = CargadorCertificado.cargar(
 *     new OrigenCertificado(inputStream, "password")
 * );
 *
 * ResultadoFirma resultado = ServicioFirma.firmarXml(xmlString, cert);
 *
 * if (resultado.exitoso()) {
 *     byte[] xmlFirmado = resultado.xmlFirmado();
 *     String hash = resultado.digestValue();
 * }
 * }</pre>
 *
 * <p><strong>Concurrencia:</strong> esta clase es stateless y thread-safe.
 * Para alto throughput, se recomienda cargar {@code DetallesCertificado} una sola vez
 * al inicio de la aplicación y reutilizarlo entre hilos.</p>
 *
 * @since 0.1.0
 */
public final class ServicioFirma {

    public static final String ID_REFERENCIA_DEFAULT = IdentificadoresFirma.SIGNATURE_ID;

    private ServicioFirma() {
    }

    /**
     * Firma un XML UBL con el ID de referencia por defecto ("UBLKIT-SIGN").
     */
    public static ResultadoFirma firmarXml(String xml, DetallesCertificado certificado) {
        return firmarXml(xml, ID_REFERENCIA_DEFAULT, certificado);
    }

    /**
     * Firma un XML UBL con un ID de referencia personalizado.
     *
     * @param xml          Contenido XML a firmar.
     * @param idReferencia ID de la referencia de firma.
     * @param certificado  Certificado con clave privada.
     * @return Resultado con el XML firmado, el hash y el estado.
     */
    public static ResultadoFirma firmarXml(String xml, String idReferencia, DetallesCertificado certificado) {
        if (certificado == null) {
            throw new NullPointerException("Certificado no puede ser null");
        }
        try {
            byte[] bytes = FirmadorXml.firmarComoBytes(xml, idReferencia, certificado);
            String xmlStr = new String(bytes, StandardCharsets.ISO_8859_1);
            Document documentoFirmado = XmlHelper.convertirStringADocumento(xmlStr);
            validarEstructuraFirma(documentoFirmado, idReferencia);
            String digestValue = extraerDigestValue(documentoFirmado);

            return ResultadoFirma.exitoso(bytes, xmlStr, digestValue);

        } catch (Exception e) {
            return ResultadoFirma.fallido(e.getMessage());
        }
    }

    /**
     * Extrae el DigestValue de la firma insertada en el documento.
     */
    private static String extraerDigestValue(Document documento) {
        NodeList digestNodes = documento.getElementsByTagNameNS(
                "http://www.w3.org/2000/09/xmldsig#", "DigestValue"
        );
        if (digestNodes.getLength() > 0) {
            return digestNodes.item(0).getTextContent();
        }
        return null;
    }

    private static void validarEstructuraFirma(Document documento, String idReferencia) {
        NodeList firmas = documento.getElementsByTagNameNS(
                "http://www.w3.org/2000/09/xmldsig#", "Signature");
        if (firmas.getLength() != 1) throw new IllegalStateException("XML firmado debe contener una única ds:Signature");
        Element firma = (Element) firmas.item(0);
        if (!idReferencia.equals(firma.getAttribute("Id"))) {
            throw new IllegalStateException("El Id de ds:Signature no coincide con el identificador solicitado");
        }
        requireNode(documento, "SignedInfo");
        Element reference = (Element) requireNode(documento, "Reference");
        if (!reference.hasAttribute("URI") || !reference.getAttribute("URI").isEmpty()) {
            throw new IllegalStateException("La referencia XMLDSIG debe ser enveloped (URI vacío)");
        }
        Element digestMethod = (Element) requireNode(documento, "DigestMethod");
        Element digestValue = (Element) requireNode(documento, "DigestValue");
        Element signatureMethod = (Element) requireNode(documento, "SignatureMethod");
        Element signatureValue = (Element) requireNode(documento, "SignatureValue");
        if (digestMethod.getAttribute("Algorithm").isBlank()
                || digestValue.getTextContent().isBlank()
                || signatureMethod.getAttribute("Algorithm").isBlank()
                || signatureValue.getTextContent().isBlank()) {
            throw new IllegalStateException("La firma XML contiene valores criptográficos incompletos");
        }
        if (documento.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509Data").getLength() == 0) {
            throw new IllegalStateException("La firma XML debe incluir certificado X509");
        }
        NodeList referenciasUbl = documento.getElementsByTagNameNS(
                "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", "URI");
        for (int i = 0; i < referenciasUbl.getLength(); i++) {
            Node uri = referenciasUbl.item(i);
            Node external = uri.getParentNode();
            Node attachment = external == null ? null : external.getParentNode();
            Node signature = attachment == null ? null : attachment.getParentNode();
            if (external != null && "ExternalReference".equals(external.getLocalName())
                    && attachment != null && "DigitalSignatureAttachment".equals(attachment.getLocalName())
                    && signature != null && "Signature".equals(signature.getLocalName())
                    && !IdentificadoresFirma.uri(idReferencia).equals(uri.getTextContent())) {
                throw new IllegalStateException("cbc:URI no apunta al Id de ds:Signature");
            }
        }
    }

    private static Node requireNode(Document documento, String localName) {
        NodeList nodes = documento.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", localName);
        if (nodes.getLength() == 0) throw new IllegalStateException("Falta ds:" + localName);
        return nodes.item(0);
    }
}
