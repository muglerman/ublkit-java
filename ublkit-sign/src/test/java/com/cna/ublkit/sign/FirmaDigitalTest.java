package com.cna.ublkit.sign;

import com.cna.ublkit.core.error.ExcepcionUblKit;
import com.cna.ublkit.sign.api.ResultadoFirma;
import com.cna.ublkit.sign.api.ServicioFirma;
import com.cna.ublkit.sign.certificado.CargadorCertificado;
import com.cna.ublkit.sign.certificado.DetallesCertificado;
import com.cna.ublkit.sign.certificado.OrigenCertificado;
import com.cna.ublkit.sign.xml.FirmadorXml;
import com.cna.ublkit.sign.xml.XmlHelper;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.crypto.dsig.XMLSignature;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FirmaDigitalTest {

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

    @BeforeAll
    static void cargarCertificado() {
        InputStream is = FirmaDigitalTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        assertThat(is).isNotNull();
        certificado = CargadorCertificado.cargar(new OrigenCertificado(is, "changeit"));
    }

    @Test
    void testCargarCertificado_exitoso() {
        assertThat(certificado).isNotNull();
        assertThat(certificado.clavePrivada()).isNotNull();
        assertThat(certificado.certificado()).isNotNull();
        assertThat(certificado.certificado().getSubjectX500Principal().getName()).contains("CN=Test");
    }

    @Test
    void testCargarCertificado_passwordIncorrecto() {
        InputStream is = FirmaDigitalTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        assertThatThrownBy(() -> CargadorCertificado.cargar(new OrigenCertificado(is, "incorrecto")))
                .isInstanceOf(ExcepcionUblKit.class)
                .hasMessageContaining("Error al cargar el certificado");
    }

    @Test
    void testOrigenCertificado_defaultPKCS12() {
        InputStream is = FirmaDigitalTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        var origen = new OrigenCertificado(is, "changeit");
        assertThat(origen.tipo()).isEqualTo("PKCS12");
    }

    @Test
    void testXmlHelper_convertirYSerializar() throws Exception {
        Document doc = XmlHelper.convertirStringADocumento(XML_FACTURA_MINIMA);
        assertThat(doc).isNotNull();
        assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("Invoice");

        byte[] bytes = XmlHelper.documentoABytes(doc);
        assertThat(bytes).isNotEmpty();
        String resultado = new String(bytes, "ISO-8859-1");
        assertThat(resultado).contains("F001-1");
    }

    @Test
    void testFirmadorXml_firmaExitosa() {
        Document docFirmado = FirmadorXml.firmar(XML_FACTURA_MINIMA, "SignSUNAT", certificado);

        assertThat(docFirmado).isNotNull();
        // Verificar que la firma fue insertada
        assertThat(docFirmado.getElementsByTagNameNS(
                "http://www.w3.org/2000/09/xmldsig#", "Signature").getLength()
        ).isEqualTo(1);
        // Verificar que el DigestValue existe
        assertThat(docFirmado.getElementsByTagNameNS(
                "http://www.w3.org/2000/09/xmldsig#", "DigestValue").getLength()
        ).isGreaterThan(0);
    }

    @Test
    void testServicioFirma_flujoCompleto() {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, certificado);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.mensajeError()).isNull();
        assertThat(resultado.xmlFirmado()).isNotEmpty();
        assertThat(resultado.xmlFirmadoStr()).contains("F001-1");
        assertThat(resultado.xmlFirmadoStr()).contains("ds:Signature");
        assertThat(resultado.digestValue()).isNotBlank();
    }

    @Test
    void testServicioFirma_xmlInvalido() {
        ResultadoFirma resultado = ServicioFirma.firmarXml("<invalido>", "SignSUNAT", certificado);

        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isNotBlank();
    }

    @Test
    void testResultadoFirma_factoryMethods() {
        var exitoso = ResultadoFirma.exitoso(new byte[]{1}, "xml", "hash");
        assertThat(exitoso.exitoso()).isTrue();
        assertThat(exitoso.digestValue()).isEqualTo("hash");

        var fallido = ResultadoFirma.fallido("error msg");
        assertThat(fallido.exitoso()).isFalse();
        assertThat(fallido.mensajeError()).isEqualTo("error msg");
        assertThat(fallido.xmlFirmado()).isNull();
    }

    @Test
    @DisplayName("Debe preservar la inmutabilidad del documento original al firmar in-place")
    void debePreservarInmutabilidadDocumentoOriginal() throws Exception {
        // Arrange
        Document documentoOriginal = XmlHelper.convertirStringADocumento(XML_FACTURA_MINIMA);

        // Act
        Document documentoFirmado = FirmadorXml.firmar(documentoOriginal, "SignSUNAT", certificado);

        // Assert
        assertNotSame(documentoOriginal, documentoFirmado, "El documento firmado debe ser una nueva instancia (clon)");

        // El documento original NO debe tener firma
        NodeList firmaOriginal = documentoOriginal.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        assertEquals(0, firmaOriginal.getLength(), "El documento original no debe ser modificado");

        // El documento firmado SÍ debe tener firma
        NodeList firmaNueva = documentoFirmado.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        assertEquals(1, firmaNueva.getLength(), "El documento firmado debe contener la firma");
    }
}
