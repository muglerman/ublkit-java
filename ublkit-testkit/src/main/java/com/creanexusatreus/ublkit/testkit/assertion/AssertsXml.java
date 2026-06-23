package com.cna.ublkit.testkit.assertion;

import org.junit.jupiter.api.Assertions;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utilidades de aserción para verificar estructuras XML de documentos UBL.
 *
 * @since 0.1.0
 */
public final class AssertsXml {

    private AssertsXml() {}

    /**
     * Verifica que el XML contenga un valor específico en la ruta XPath indicada.
     *
     * @param xml contenido del XML
     * @param xpathExpression expresión XPath
     * @param expectedValue valor esperado
     */
    public static void assertXPath(String xml, String xpathExpression, String expectedValue) {
        try {
            DocumentBuilder builder = crearBuilderSeguro();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            XPath xpath = XPathFactory.newInstance().newXPath();
            String actualValue = (String) xpath.evaluate(xpathExpression, doc, XPathConstants.STRING);

            Assertions.assertEquals(expectedValue, actualValue,
                    "Fallo al validar XPath: " + xpathExpression);
        } catch (Exception e) {
            Assertions.fail("Error procesando XML para XPath '" + xpathExpression + "': " + e.getMessage());
        }
    }

    /**
     * Verifica si una etiqueta existe en el XML (sin importar el valor).
     *
     * @param xml contenido del XML
     * @param xpathExpression expresión XPath
     */
    public static void assertElementExists(String xml, String xpathExpression) {
        try {
            DocumentBuilder builder = crearBuilderSeguro();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            XPath xpath = XPathFactory.newInstance().newXPath();
            Boolean exists = (Boolean) xpath.evaluate(xpathExpression, doc, XPathConstants.BOOLEAN);

            Assertions.assertTrue(exists, "El elemento en XPath '" + xpathExpression + "' debería existir.");
        } catch (Exception e) {
            Assertions.fail("Error validando existencia de XPath '" + xpathExpression + "': " + e.getMessage());
        }
    }

    private static DocumentBuilder crearBuilderSeguro() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false); // Ignorar namespaces para simplificar acceso en tests
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return factory.newDocumentBuilder();
    }
}
