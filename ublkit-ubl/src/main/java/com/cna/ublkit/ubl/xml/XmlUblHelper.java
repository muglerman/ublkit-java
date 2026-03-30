package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.core.error.ExcepcionSerializacionXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.cna.ublkit.ubl.xml.ConstantesUbl.*;

/**
 * Utilidades para construir documentos XML UBL 2.1 usando javax.xml DOM.
 * <p>
 * Provee métodos estáticos para crear documentos, elementos y serializar a String.
 * </p>
 *
 * @since 0.1.0
 */
public final class XmlUblHelper {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private XmlUblHelper() {
    }

    /**
     * Crea un nuevo documento DOM con el elemento raíz del namespace dado.
     */
    public static Document crearDocumento(String namespace, String nombreRaiz) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);

            Element raiz = doc.createElementNS(namespace, nombreRaiz);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", NS_CAC);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", NS_CBC);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ccts", NS_CCTS);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", NS_DS);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", NS_EXT);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:qdt", NS_QDT);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", NS_SAC);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:udt", NS_UDT);

            doc.appendChild(raiz);
            return doc;
        } catch (Exception e) {
            throw new ExcepcionSerializacionXml("Error creando documento XML", e);
        }
    }

    // ── Creación de elementos ────────────────────────────────────

    public static Element cbc(Document doc, String nombre, String valor) {
        Element el = doc.createElementNS(NS_CBC, "cbc:" + nombre);
        el.setTextContent(valor);
        return el;
    }

    public static Element cbc(Document doc, String nombre, BigDecimal valor) {
        return cbc(doc, nombre, escalar(valor));
    }

    public static Element cbc(Document doc, String nombre, LocalDate fecha) {
        return cbc(doc, nombre, fecha.format(FORMATO_FECHA));
    }

    public static Element cbc(Document doc, String nombre, LocalTime hora) {
        return cbc(doc, nombre, hora.format(FORMATO_HORA));
    }

    public static Element cbc(Document doc, String nombre, int valor) {
        return cbc(doc, nombre, String.valueOf(valor));
    }

    public static Element cbcConAtributos(Document doc, String nombre, String valor, String... atributos) {
        Element el = doc.createElementNS(NS_CBC, "cbc:" + nombre);
        el.setTextContent(valor);
        for (int i = 0; i < atributos.length - 1; i += 2) {
            el.setAttribute(atributos[i], atributos[i + 1]);
        }
        return el;
    }

    public static Element cbcMonto(Document doc, String nombre, BigDecimal monto, String moneda) {
        Element el = doc.createElementNS(NS_CBC, "cbc:" + nombre);
        el.setTextContent(escalar(monto));
        el.setAttribute("currencyID", moneda);
        return el;
    }

    public static Element cbcCantidad(Document doc, String nombre, BigDecimal cantidad, String unidad) {
        Element el = doc.createElementNS(NS_CBC, "cbc:" + nombre);
        el.setTextContent(cantidad.toPlainString());
        el.setAttribute("unitCode", unidad);
        el.setAttribute("unitCodeListAgencyName", "United Nations Economic Commission for Europe");
        el.setAttribute("unitCodeListID", "UN/ECE rec 20");
        return el;
    }

    public static Element cac(Document doc, String nombre) {
        return doc.createElementNS(NS_CAC, "cac:" + nombre);
    }

    public static Element ext(Document doc, String nombre) {
        return doc.createElementNS(NS_EXT, "ext:" + nombre);
    }

    public static Element cbcCdata(Document doc, String nombre, String valor) {
        Element el = doc.createElementNS(NS_CBC, "cbc:" + nombre);
        el.appendChild(doc.createCDATASection(valor));
        return el;
    }

    public static Element sac(Document doc, String nombre) {
        return doc.createElementNS(NS_SAC, "sac:" + nombre);
    }

    public static Element sacMonto(Document doc, String nombre, BigDecimal monto, String moneda) {
        Element el = doc.createElementNS(NS_SAC, "sac:" + nombre);
        el.setTextContent(escalar(monto));
        el.setAttribute("currencyID", moneda);
        return el;
    }

    public static Element sac(Document doc, String nombre, String valor) {
        Element el = doc.createElementNS(NS_SAC, "sac:" + nombre);
        el.setTextContent(valor);
        return el;
    }

    /**
     * Crea un documento DOM para documentos SUNAT propios (VoidedDocuments, SummaryDocuments).
     * Usa un subset diferente de namespaces.
     */
    public static Document crearDocumentoSunat(String namespace, String nombreRaiz, boolean incluirPerception) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);

            Element raiz = doc.createElementNS(namespace, nombreRaiz);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", NS_CAC);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", NS_CBC);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", NS_DS);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", NS_EXT);
            raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", NS_SAC);
            if (incluirPerception) {
                raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ccts", NS_CCTS);
                raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:qdt", NS_QDT);
                raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:udt", NS_UDT);
                raiz.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ns11", NS_PERCEPTION);
            }

            doc.appendChild(raiz);
            return doc;
        } catch (Exception e) {
            throw new ExcepcionSerializacionXml("Error creando documento XML SUNAT", e);
        }
    }

    // ── Serialización ────────────────────────────────────────────

    /**
     * Serializa un documento DOM a String XML con encoding ISO-8859-1.
     */
    public static String documentoAString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new ExcepcionSerializacionXml("Error serializando documento XML a String", e);
        }
    }

    /**
     * Serializa un documento DOM a bytes XML.
     */
    public static byte[] documentoABytes(Document doc) {
        return documentoAString(doc).getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
    }

    // ── Helpers internos ─────────────────────────────────────────

    static String escalar(BigDecimal valor) {
        if (valor == null) return "0.00";
        return valor.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    static String escalarPrecio(BigDecimal valor) {
        if (valor == null) return "0.00";
        return valor.setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }
}
