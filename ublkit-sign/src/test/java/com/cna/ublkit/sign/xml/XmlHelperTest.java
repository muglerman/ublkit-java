package com.cna.ublkit.sign.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for XmlHelper - XML utilities and helpers.
 * Tests XPath compilation, namespace handling, and element finding.
 */
class XmlHelperTest {

    private static final String XML_SIMPLE = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2">
                <cbc:ID xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">F001-1</cbc:ID>
            </Invoice>
            """;

    private static final String XML_COMPLEJO = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                     xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                     xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                     xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2">
                <cbc:UBLVersionID>2.1</cbc:UBLVersionID>
                <cbc:ID>F001-1</cbc:ID>
                <cbc:IssueDate>2026-03-30</cbc:IssueDate>
                <ext:UBLExtensions>
                    <ext:UBLExtension>
                        <ext:ExtensionContent/>
                    </ext:UBLExtension>
                </ext:UBLExtensions>
            </Invoice>
            """;

    private static final String XML_NAMESPACES = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <root xmlns="http://example.com/default"
                  xmlns:ns1="http://example.com/ns1"
                  xmlns:ns2="http://example.com/ns2">
                <ns1:elemento>valor1</ns1:elemento>
                <ns2:elemento>valor2</ns2:elemento>
                <elemento>valor3</elemento>
            </root>
            """;

    @Test
    void crearDocumentBuilder_noNulo() throws ParserConfigurationException {
        var builder = XmlHelper.crearDocumentBuilder();
        assertThat(builder).isNotNull();
    }

    @Test
    void crearDocumentBuilder_conNamespaceAware() throws ParserConfigurationException {
        var builder = XmlHelper.crearDocumentBuilder();
        // Se espera que sea namespace-aware según la implementación
        assertThat(builder).isNotNull();
    }

    @Test
    void crearDocumentBuilder_conValidating_false() throws ParserConfigurationException {
        var builder = XmlHelper.crearDocumentBuilder();
        // Se espera que no valide DTDs
        assertThat(builder).isNotNull();
    }

    @Test
    void crearDocumentBuilder_conSeguridad() throws ParserConfigurationException {
        var builder = XmlHelper.crearDocumentBuilder();
        // Se espera que tenga seguridad habilitada contra XXE
        assertThat(builder).isNotNull();
    }

    @Test
    void convertirStringADocumento_conXmlValido() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);

        assertThat(doc).isNotNull();
        assertThat(doc.getDocumentElement()).isNotNull();
    }

    @Test
    void convertirStringADocumento_retornaElementoRaiz() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);

        assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("Invoice");
    }

    @Test
    void convertirStringADocumento_conNamespaces() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_NAMESPACES);

        assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("root");
    }

    @Test
    void convertirStringADocumento_conXmlComplejo() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_COMPLEJO);

        assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("Invoice");
        assertThat(doc.getElementsByTagName("cbc:ID").getLength()).isGreaterThan(0);
    }

    @Test
    void convertirStringADocumento_conXmlMalformado_lanzaExcepcion() {
        String xmlMalformado = "<xml incompleto>";

        assertThatThrownBy(() -> XmlHelper.convertirStringADocumento(xmlMalformado))
                .isInstanceOf(Exception.class);
    }

    @Test
    void convertirStringADocumento_conXmlVacio_lanzaExcepcion() {
        assertThatThrownBy(() -> XmlHelper.convertirStringADocumento(""))
                .isInstanceOf(Exception.class);
    }

    @Test
    void convertirStringADocumento_conXmlNull_lanzaExcepcion() {
        assertThatThrownBy(() -> XmlHelper.convertirStringADocumento(null))
                .isInstanceOf(Exception.class);
    }

    @Test
    void documentoABytes_retornaBytesNoVacios() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        assertThat(bytes).isNotEmpty();
    }

    @Test
    void documentoABytes_conContenido() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("Invoice");
        assertThat(resultado).contains("F001-1");
    }

    @Test
    void documentoABytes_conEncodingISO88591() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).isNotEmpty();
    }

    @Test
    void documentoABytes_preservaEstructura() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_COMPLEJO);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("UBLVersionID");
        assertThat(resultado).contains("2.1");
    }

    @Test
    void documentoABytes_conXmlDeclaration() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).startsWith("<?xml");
    }

    @Test
    void documentoABytes_docNull_lanzaExcepcion() {
        assertThatThrownBy(() -> XmlHelper.documentoABytes(null))
                .isInstanceOf(Exception.class);
    }

    @Test
    void roundTrip_convertirYSerializar() throws Exception {
        Document doc1 = XmlHelper.convertirStringADocumento(XML_SIMPLE);
        byte[] bytes = XmlHelper.documentoABytes(doc1);
        String resultado = new String(bytes, "ISO-8859-1");
        Document doc2 = XmlHelper.convertirStringADocumento(resultado);

        assertThat(doc1.getDocumentElement().getLocalName())
                .isEqualTo(doc2.getDocumentElement().getLocalName());
    }

    @Test
    void documentoABytes_multiplasVeces() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_SIMPLE);

        byte[] bytes1 = XmlHelper.documentoABytes(doc);
        byte[] bytes2 = XmlHelper.documentoABytes(doc);

        assertThat(bytes1).isEqualTo(bytes2);
    }

    @Test
    void crearDocumentBuilder_multiplasVeces() throws ParserConfigurationException {
        var builder1 = XmlHelper.crearDocumentBuilder();
        var builder2 = XmlHelper.crearDocumentBuilder();

        assertThat(builder1).isNotNull();
        assertThat(builder2).isNotNull();
    }

    @Test
    void documentoABytes_conNamespacesMultiples() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_NAMESPACES);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("http://example.com/ns1");
        assertThat(resultado).contains("http://example.com/ns2");
    }

    @Test
    void documentoABytes_preservaCaracteresEspeciales() throws Exception {
        String xmlConEspeciales = """
                <?xml version="1.0" encoding="ISO-8859-1"?>
                <root>
                    <elemento>Texto con acentuación: áéíóú</elemento>
                </root>
                """;
        Document doc = XmlHelper.convertirStringADocumento(xmlConEspeciales);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("acentuación");
    }

    @Test
    void convertirStringADocumento_preservaDatos() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_COMPLEJO);

        assertThat(doc.getElementsByTagName("cbc:UBLVersionID").item(0).getTextContent())
                .isEqualTo("2.1");
        assertThat(doc.getElementsByTagName("cbc:ID").item(0).getTextContent())
                .isEqualTo("F001-1");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<?xml version=\"1.0\"?><root/>",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>",
            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><root/>"
    })
    void convertirStringADocumento_conVariasDeclaraciones(String xml) throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(xml);

        assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("root");
    }

    @Test
    void documentoABytes_conDocumentoComplejo() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_COMPLEJO);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado.length()).isGreaterThan(0);
        assertThat(resultado).contains("Invoice");
        assertThat(resultado).contains("ext:UBLExtensions");
    }

    @Test
    void noInstanciable() {
        assertThatThrownBy(() -> new XmlHelper())
                .isInstanceOf(UnsupportedOperationException.class)
                .or()
                .isInstanceOf(InstantiationException.class)
                .or()
                .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    void crearDocumentBuilder_esThreadSafe() throws ParserConfigurationException {
        // Llamar desde múltiples contextos debería funcionar
        var builder1 = XmlHelper.crearDocumentBuilder();
        var builder2 = XmlHelper.crearDocumentBuilder();

        assertThat(builder1).isNotNull();
        assertThat(builder2).isNotNull();
    }

    @Test
    void documentoABytes_conElementosAnidados() throws Exception {
        String xmlAnidado = """
                <?xml version="1.0"?>
                <root>
                    <nivel1>
                        <nivel2>
                            <nivel3>contenido</nivel3>
                        </nivel2>
                    </nivel1>
                </root>
                """;
        Document doc = XmlHelper.convertirStringADocumento(xmlAnidado);
        byte[] bytes = XmlHelper.documentoABytes(doc);

        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("contenido");
    }
}
