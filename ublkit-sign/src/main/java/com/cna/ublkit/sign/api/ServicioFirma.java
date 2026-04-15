package com.cna.ublkit.sign.api;

import com.cna.ublkit.sign.certificado.DetallesCertificado;
import com.cna.ublkit.sign.xml.FirmadorXml;
import com.cna.ublkit.sign.xml.XmlHelper;

import org.w3c.dom.Document;
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
 * ResultadoFirma resultado = ServicioFirma.firmarXml(xmlString, "SignSUNAT", cert);
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

    private static final String ID_REFERENCIA_DEFAULT = "SignSUNAT";

    private ServicioFirma() {
    }

    /**
     * Firma un XML UBL con el ID de referencia por defecto ("SignSUNAT").
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
            Document documentoFirmado = FirmadorXml.firmar(xml, idReferencia, certificado);

            byte[] bytes = XmlHelper.documentoABytes(documentoFirmado);
            String xmlStr = new String(bytes, StandardCharsets.ISO_8859_1);
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
}
