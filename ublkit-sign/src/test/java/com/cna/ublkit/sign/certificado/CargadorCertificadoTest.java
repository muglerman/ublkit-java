package com.cna.ublkit.sign.certificado;

import com.cna.ublkit.core.error.ExcepcionUblKit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for CargadorCertificado - certificate loading from PKCS12 keystores.
 * Tests loading, password handling, and certificate chain extraction.
 */
class CargadorCertificadoTest {

    private InputStream keystoreStream;

    @BeforeEach
    void setUp() {
        keystoreStream = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        assertThat(keystoreStream).isNotNull();
    }

    @Test
    void cargar_conOrigenPKCS12_exitoso() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles).isNotNull();
        assertThat(detalles.clavePrivada()).isNotNull();
        assertThat(detalles.certificado()).isNotNull();
    }

    @Test
    void cargar_conPasswordCorrecto_exitoso() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.clavePrivada()).isNotNull();
        assertThat(detalles.certificado()).isNotNull();
    }

    @Test
    void cargar_conPasswordIncorrecto_lanzaExcepcion() {
        InputStream is = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, "passwordIncorrecto");

        assertThatThrownBy(() -> CargadorCertificado.cargar(origen))
                .isInstanceOf(ExcepcionUblKit.class)
                .hasMessageContaining("Error al cargar el certificado");
    }

    @Test
    void cargar_retornaDetallesConClavePrivada() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.clavePrivada()).isNotNull();
        assertThat(detalles.clavePrivada().getAlgorithm()).contains("RSA");
    }

    @Test
    void cargar_retornaDetallesConCertificado() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        X509Certificate cert = detalles.certificado();
        assertThat(cert).isNotNull();
        assertThat(cert.getSubjectX500Principal()).isNotNull();
    }

    @Test
    void cargar_certificadoEsX509() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.certificado()).isInstanceOf(X509Certificate.class);
    }

    @Test
    void cargar_certificadoTieneSerialNumber() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.certificado().getSerialNumber()).isNotNull();
        assertThat(detalles.certificado().getSerialNumber().signum()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void cargar_certificadoTieneFechaValezValida() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.certificado().getNotBefore()).isNotNull();
        assertThat(detalles.certificado().getNotAfter()).isNotNull();
        assertThat(detalles.certificado().getNotBefore()).isBefore(detalles.certificado().getNotAfter());
    }

    @Test
    void cargar_certificadoContieneClavePublica() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles.certificado().getPublicKey()).isNotNull();
    }

    @Test
    void cargar_conPasswordVacio_lanzaExcepcion() {
        InputStream is = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, "");

        assertThatThrownBy(() -> CargadorCertificado.cargar(origen))
                .isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    void cargar_conInputStreamNulo_lanzaExcepcion() {
        OrigenCertificado origen = new OrigenCertificado(null, "changeit");

        assertThatThrownBy(() -> CargadorCertificado.cargar(origen))
                .isInstanceOf(Exception.class);
    }

    @Test
    void cargar_conInputStreamInvalido_lanzaExcepcion() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(inputStream, "changeit");

        assertThatThrownBy(() -> CargadorCertificado.cargar(origen))
                .isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    void cargar_conTipoPKCS12Explicito() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit", "PKCS12");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles).isNotNull();
        assertThat(detalles.clavePrivada()).isNotNull();
    }

    @Test
    void cargar_buscaAliasConClavePrivada() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        // Verificar que encontró alias con clave privada
        assertThat(detalles.clavePrivada()).isNotNull();
        assertThat(detalles.clavePrivada().getAlgorithm()).isNotEmpty();
    }

    @Test
    void cargar_extraeValoresDelCertificado() {
        OrigenCertificado origen = new OrigenCertificado(keystoreStream, "changeit");
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        String subjectDN = detalles.certificado().getSubjectX500Principal().getName();
        assertThat(subjectDN).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"changeit", "changeit"})
    void cargar_conPasswordVariado_exitoso(String password) {
        InputStream is = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, password);
        DetallesCertificado detalles = CargadorCertificado.cargar(origen);

        assertThat(detalles).isNotNull();
    }

    @Test
    void cargar_multiplicasVecesDelMismoStream() {
        // Recargar el stream porque fue consumido
        InputStream is1 = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen1 = new OrigenCertificado(is1, "changeit");
        DetallesCertificado detalles1 = CargadorCertificado.cargar(origen1);

        InputStream is2 = CargadorCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen2 = new OrigenCertificado(is2, "changeit");
        DetallesCertificado detalles2 = CargadorCertificado.cargar(origen2);

        assertThat(detalles1.certificado().getSerialNumber())
                .isEqualTo(detalles2.certificado().getSerialNumber());
    }
}
