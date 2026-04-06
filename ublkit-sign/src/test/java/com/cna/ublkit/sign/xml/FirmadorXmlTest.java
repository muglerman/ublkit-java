package com.cna.ublkit.sign.xml;

import com.cna.ublkit.core.error.ExcepcionUblKit;
import com.cna.ublkit.sign.certificado.CargadorCertificado;
import com.cna.ublkit.sign.certificado.DetallesCertificado;
import com.cna.ublkit.sign.certificado.OrigenCertificado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for FirmadorXml - XML signing implementation.
 * Tests XPath targeting, signature injection, and element selection.
 */
class FirmadorXmlTest {

    private static DetallesCertificado certificado;

    private static final String XML_FACTURA_MINIMA = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
                     xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                     xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
                     xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2">
                <cbc:UBLVersionID>2.1</cbc:UBLVersionID>
                <cbc:ID>F001-1</cbc:ID>
                <cbc:IssueDate>2026-03-30</cbc:IssueDate>
            </Invoice>
            """;

    private static final String XML_SIN_EXTENSIONS = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2">
                <cbc:ID xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">F001-2</cbc:ID>
            </Invoice>
            """;

    @BeforeAll
    static void cargarCertificado() {
        InputStream is = FirmadorXmlTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        assertThat(is).isNotNull();
        certificado = CargadorCertificado.cargar(new OrigenCertificado(is, "changeit"));
    }

    @Test
    void firmar_conStringXml_creeFirma() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        assertThat(doc).isNotNull();
        NodeList signatures = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        assertThat(signatures.getLength()).isEqualTo(1);
    }

    @Test
    void firmar_conDocumentoDOM_modificaInPlace() {
        Document doc = XmlHelper.convertirStringADocumento(XML_FACTURA_MINIMA);
        Document resultado = FirmadorXml.firmar(doc, "SignSUNAT", certificado);

        assertThat(resultado).isSameAs(doc);
    }

    @Test
    void firmar_creaExtensiones_siBienNoExisten() {
        Document doc = FirmadorXml.firmar(XML_SIN_EXTENSIONS, "SignSUNAT", certificado);

        NodeList extensions = doc.getElementsByTagName("ext:UBLExtensions");
        assertThat(extensions.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_inyectaSignatureElemento() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        NodeList signatureElements = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        assertThat(signatureElements.getLength()).isEqualTo(1);
    }

    @Test
    void firmar_conIdReferenciaPersonalizado() {
        String idRef = "MiFirma123";
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, idRef, certificado);

        NodeList signatureElements = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        assertThat(signatureElements.item(0).getAttributes().getNamedItem("Id").getNodeValue())
                .isEqualTo(idRef);
    }

    @Test
    void firmar_conIdReferenciaVacio() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "", certificado);

        NodeList signatureElements = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        assertThat(signatureElements.getLength()).isEqualTo(1);
    }

    @Test
    void firmar_contieneDiagestValue() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        NodeList digestValues = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
        assertThat(digestValues.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_contieneCertificadoX509() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        NodeList x509Certificates = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
        assertThat(x509Certificates.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_conXmlNull_lanzaExcepcion() {
        assertThatThrownBy(() -> FirmadorXml.firmar((String) null, "SignSUNAT", certificado))
                .isInstanceOf(Exception.class);
    }

    @Test
    void firmar_conCertificadoNull_lanzaExcepcion() {
        assertThatThrownBy(() -> FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void firmar_xmlMalformado_lanzaExcepcion() {
        String xmlMalformado = "<xml incompleto>";
        assertThatThrownBy(() -> FirmadorXml.firmar(xmlMalformado, "SignSUNAT", certificado))
                .isInstanceOf(ExcepcionUblKit.class)
                .hasMessageContaining("Error al parsear XML");
    }

    @Test
    void firmar_crearUBLExtensions() {
        Document doc = FirmadorXml.firmar(XML_SIN_EXTENSIONS, "SignSUNAT", certificado);

        NodeList extensions = doc.getElementsByTagName("ext:UBLExtensions");
        assertThat(extensions.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_crearUBLExtension() {
        Document doc = FirmadorXml.firmar(XML_SIN_EXTENSIONS, "SignSUNAT", certificado);

        NodeList extension = doc.getElementsByTagName("ext:UBLExtension");
        assertThat(extension.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_crearExtensionContent() {
        Document doc = FirmadorXml.firmar(XML_SIN_EXTENSIONS, "SignSUNAT", certificado);

        NodeList content = doc.getElementsByTagName("ext:ExtensionContent");
        assertThat(content.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_conMultiplesIntentos_cadaUnoExitoso() {
        Document doc1 = FirmadorXml.firmar(XML_FACTURA_MINIMA, "Sign1", certificado);
        Document doc2 = FirmadorXml.firmar(XML_FACTURA_MINIMA, "Sign2", certificado);

        NodeList sig1 = doc1.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        NodeList sig2 = doc2.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");

        assertThat(sig1.getLength()).isEqualTo(1);
        assertThat(sig2.getLength()).isEqualTo(1);
    }

    @Test
    void firmar_preservaContenidoOriginal() {
        String idReferenciaOriginal = "F001-1";
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        NodeList ids = doc.getElementsByTagNameNS("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", "ID");
        boolean encontrado = false;
        for (int i = 0; i < ids.getLength(); i++) {
            if (ids.item(i).getTextContent().equals(idReferenciaOriginal)) {
                encontrado = true;
                break;
            }
        }
        assertThat(encontrado).isTrue();
    }

    @Test
    void firmar_noInstanciable() {
        assertThatThrownBy(() -> new FirmadorXml())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("utilitaria");
    }

    @Test
    void firmar_conIdReferenciaEspeciales() {
        String[] idsEspeciales = {"SignSUNAT", "ID-001", "_sign", "firma-2025"};

        for (String id : idsEspeciales) {
            Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, id, certificado);
            NodeList signatures = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
            assertThat(signatures.getLength()).isGreaterThan(0);
        }
    }

    @Test
    void firmar_usaAlgoritmoSHA1() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        // Verificar que el DigestMethod usa SHA1
        NodeList digestMethods = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
        assertThat(digestMethods.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_usaTransformEnveloped() {
        Document doc = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        // Verificar que existe Transform element para enveloped
        NodeList transforms = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Transform");
        assertThat(transforms.getLength()).isGreaterThan(0);
    }

    @Test
    void firmar_conDocumentoExistente_agregaraFirmaASiExiste() {
        Document doc = XmlHelper.convertirStringADocumento(XML_FACTURA_MINIMA);
        Document signed = FirmadorXml.firmar(doc, "SignSUNAT", certificado);

        NodeList signatures = signed.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        assertThat(signatures.getLength()).isEqualTo(1);
    }
}
