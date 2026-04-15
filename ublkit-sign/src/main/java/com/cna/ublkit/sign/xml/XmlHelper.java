package com.cna.ublkit.sign.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Utilidades para manipulación segura de documentos XML DOM.
 * <p>
 * Configura parsers con protección contra ataques XXE (External Entity)
 * y desbordamiento (Secure Processing).
 * </p>
 *
 * @since 0.1.0
 */
public final class XmlHelper {

    private static final String ENCODING = "ISO-8859-1";

    private XmlHelper() {
    }

    /**
     * Crea un {@link DocumentBuilder} seguro con namespace-awareness.
     */
    public static DocumentBuilder crearDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return dbf.newDocumentBuilder();
    }

    /**
     * Convierte un String XML a un {@link Document} DOM.
     */
    public static Document convertirStringADocumento(String xml)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = crearDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    /**
     * Serializa un {@link Document} DOM a bytes con codificación ISO-8859-1.
     */
    public static byte[] documentoABytes(Document documento) throws Exception {
        return documentoABytesMinificado(documento);
    }

    /**
     * Serializa un {@link Document} DOM a bytes compactos/minificados (sin indentación).
     */
    public static byte[] documentoABytesMinificado(Document documento) throws Exception {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        transformer.transform(new DOMSource(documento), new StreamResult(salida));
        return salida.toByteArray();
    }

    /**
     * @deprecated usar {@link #documentoABytesMinificado(Document)} para flujo de firma/transporte.
     */
    @Deprecated
    public static byte[] documentoABytesLegacy(Document documento) throws Exception {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        serializarNodo(documento, salida);
        return salida.toByteArray();
    }

    /**
     * Serializa un nodo DOM a un {@link ByteArrayOutputStream}.
     */
    private static void serializarNodo(Node nodo, ByteArrayOutputStream salida) throws Exception {
        DOMImplementationRegistry registro = DOMImplementationRegistry.newInstance();
        DOMImplementationLS implementacion = (DOMImplementationLS) registro.getDOMImplementation("LS");
        LSOutput lsOutput = implementacion.createLSOutput();
        lsOutput.setEncoding(ENCODING);
        lsOutput.setByteStream(salida);
        LSSerializer serializer = implementacion.createLSSerializer();
        serializer.getDomConfig().setParameter("xml-declaration", true);
        serializer.write(nodo, lsOutput);
    }

    /**
     * Elimina nodos de texto que solo contienen espacios en blanco.
     * Se usa para evitar variaciones por pretty print antes de firmar.
     */
    public static void compactarEstructura(Node nodo) {
        NodeList hijos = nodo.getChildNodes();
        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.TEXT_NODE && hijo.getTextContent() != null
                    && hijo.getTextContent().trim().isEmpty()) {
                nodo.removeChild(hijo);
            } else {
                compactarEstructura(hijo);
            }
        }
    }
}
