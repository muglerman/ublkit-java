package com.cna.ublkit.sign.api;

import com.cna.ublkit.sign.certificado.CargadorCertificado;
import com.cna.ublkit.sign.certificado.DetallesCertificado;
import com.cna.ublkit.sign.certificado.OrigenCertificado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for ServicioFirma class - high-level XML signing service.
 * Tests the main public API for signing UBL XML documents.
 */
class ServicioFirmaTest {

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

    private static final String XML_FACTURA_PERSONALIZADO = """
            <?xml version="1.0" encoding="ISO-8859-1"?>
            <DebitNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2"
                       xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2">
                <cbc:ID xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">ND001-1</cbc:ID>
            </DebitNote>
            """;

    @BeforeAll
    static void cargarCertificado() {
        InputStream is = ServicioFirmaTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        assertThat(is).isNotNull();
        certificado = CargadorCertificado.cargar(new OrigenCertificado(is, "changeit"));
    }

    @Test
    void firmarXml_conIdReferenciaDefault_exitoso() {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, certificado);

        assertThat(resultado).isNotNull();
        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.mensajeError()).isNull();
        assertThat(resultado.xmlFirmado()).isNotEmpty();
        assertThat(resultado.xmlFirmadoStr()).isNotEmpty();
        assertThat(resultado.digestValue()).isNotBlank();
    }

    @Test
    void firmarXml_conIdReferenciaPersonalizado_exitoso() {
        String idReferencia = "MiSignature";
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, idReferencia, certificado);

        assertThat(resultado).isNotNull();
        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmadoStr()).contains("Id=\"" + idReferencia + "\"");
        assertThat(resultado.digestValue()).isNotBlank();
    }

    @Test
    void firmarXml_verificaExtensionContent() {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, certificado);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmadoStr()).contains("ext:ExtensionContent");
        assertThat(resultado.xmlFirmadoStr()).contains("ds:Signature");
    }

    @Test
    void firmarXml_xmlInvalido_fallido() {
        String xmlInvalido = "<xml incompleto>";
        ResultadoFirma resultado = ServicioFirma.firmarXml(xmlInvalido, certificado);

        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isNotBlank();
        assertThat(resultado.xmlFirmado()).isNull();
        assertThat(resultado.digestValue()).isNull();
    }

    @Test
    void firmarXml_xmlMalformado_fallido() {
        String xmlMalformado = "no es xml";
        ResultadoFirma resultado = ServicioFirma.firmarXml(xmlMalformado, certificado);

        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isNotBlank();
    }

    @Test
    void firmarXml_conIdReferenciaVacio_exitoso() {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, "", certificado);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.digestValue()).isNotBlank();
    }

    @Test
    void firmarXml_conIdReferenciaEspeciales_exitoso() {
        String[] idsEspeciales = {"SignSUNAT", "Signature001", "ID-2025-12-31"};

        for (String idRef : idsEspeciales) {
            ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, idRef, certificado);
            assertThat(resultado.exitoso()).isTrue();
            assertThat(resultado.xmlFirmadoStr()).contains("Id=\"" + idRef + "\"");
        }
    }

    @Test
    void firmarXml_certificadoNulo_lanzaExcepcion() {
        assertThatThrownBy(() -> ServicioFirma.firmarXml(XML_FACTURA_MINIMA, (DetallesCertificado) null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void firmarXml_verificaContenidoXmlFirmado() {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, certificado);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmadoStr()).contains("F001-1");
        assertThat(resultado.xmlFirmadoStr()).contains("2026-03-30");
        assertThat(resultado.xmlFirmadoStr()).contains("Invoice");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "SignSUNAT",
            "CustomID",
            "ID_123",
            "Ref-2025"
    })
    void firmarXml_conVariosIdsReferencia_exitoso(String idRef) {
        ResultadoFirma resultado = ServicioFirma.firmarXml(XML_FACTURA_MINIMA, idRef, certificado);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.digestValue()).isNotBlank();
    }
}
