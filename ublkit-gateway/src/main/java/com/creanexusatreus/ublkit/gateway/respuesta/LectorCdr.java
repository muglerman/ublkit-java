package com.cna.ublkit.gateway.respuesta;

import com.cna.ublkit.core.error.ExcepcionUblKit;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utilitario para procesar y descomprimir el archivo ZIP que envía SUNAT,
 * buscar el archivo XML del CDR y extraer su Código de Respuesta y Observaciones.
 *
 * @since 0.1.0
 */
public final class LectorCdr {

    private LectorCdr() { }

    /**
     * Extrae el contenido del CDR de un array de bytes ZIP (que típicamente 
     * viene codificado en Base64 dentro de la respuesta SOAP/REST, y debe ser 
     * provisto ya decodificado).
     */
    public static ArchivoCdr extraer(byte[] zipBytes) {
        if (zipBytes == null || zipBytes.length == 0) {
            throw new ExcepcionUblKit("El archivo ZIP del CDR está vacío o es nulo");
        }

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().toLowerCase().endsWith(".xml")) {
                    byte[] xmlBytes = zis.readAllBytes();
                    return parsearXmlCdr(zipBytes, xmlBytes);
                }
            }
            throw new ExcepcionUblKit("El archivo ZIP no contiene ningún documento XML válido de CDR");

        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error al descomprimir/leer el archivo ZIP del CDR: " + e.getMessage(), e);
        }
    }

    private static ArchivoCdr parsearXmlCdr(byte[] zipBase, byte[] xmlBytes) {
        Document doc = parsearXmlSeguro(xmlBytes);

        // cbc:ResponseCode
        String responseCode = extraerValorNodo(doc, "cbc:ResponseCode");

        // cbc:Description
        String descripcion = extraerValorNodo(doc, "cbc:Description");

        // cbc:Note (pueden ser varias, usualmente las observaciones)
        List<String> notas = new ArrayList<>();
        NodeList noteNodes = doc.getElementsByTagName("cbc:Note");
        for (int i = 0; i < noteNodes.getLength(); i++) {
            notas.add(noteNodes.item(i).getTextContent());
        }

        return new ArchivoCdr(zipBase, responseCode, descripcion, notas);
    }

    private static Document parsearXmlSeguro(byte[] xmlBytes) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(new ByteArrayInputStream(xmlBytes));
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error al parsear XML del CDR: " + e.getMessage(), e);
        }
    }

    private static String extraerValorNodo(Document doc, String tagName) {
        NodeList nodos = doc.getElementsByTagName(tagName);
        if (nodos.getLength() > 0) {
            return nodos.item(0).getTextContent();
        }
        return null;
    }

    /**
     * Determina el estado del envío basándose en el código extraído del CDR.
     * Las reglas oficiales dictan que '0' es aceptado, y códigos por encima de 0 a 1999 son rechazos o advertencias.
     * Las notas indican observaciones.
     */
    public static EstadoEnvio determinarEstado(ArchivoCdr cdr) {
        String codigo = cdr.codigoRegreso();
        if ("0".equals(codigo)) {
            if (cdr.notas() != null && !cdr.notas().isEmpty()) {
                return EstadoEnvio.ACEPTADO_CON_OBSERVACIONES;
            }
            return EstadoEnvio.ACEPTADO;
        } else if (codigo != null && !codigo.isEmpty()) {
            return EstadoEnvio.RECHAZADO;
        }
        return EstadoEnvio.EXCEPCION;
    }
}
