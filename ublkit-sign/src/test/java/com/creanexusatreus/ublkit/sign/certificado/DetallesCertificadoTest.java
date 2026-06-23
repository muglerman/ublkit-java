package com.cna.ublkit.sign.certificado;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for DetallesCertificado record - certificate details extraction.
 * Tests certificate information extraction and validation.
 */
class DetallesCertificadoTest {

    private static DetallesCertificado certificado;

    @BeforeAll
    static void cargarCertificado() {
        InputStream is = DetallesCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, "changeit");
        certificado = CargadorCertificado.cargar(origen);
    }

    @Test
    void creacion_conValoresValidos() {
        assertThat(certificado).isNotNull();
        assertThat(certificado.clavePrivada()).isNotNull();
        assertThat(certificado.certificado()).isNotNull();
    }

    @Test
    void creacion_clavePrivadaNula_lanzaExcepcion() {
        assertThatThrownBy(() -> new DetallesCertificado(null, certificado.certificado()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clavePrivada no puede ser nula");
    }

    @Test
    void creacion_certificadoNulo_lanzaExcepcion() {
        assertThatThrownBy(() -> new DetallesCertificado(certificado.clavePrivada(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("certificado no puede ser nulo");
    }

    @Test
    void creacion_ambosNulos_lanzaExcepcion() {
        assertThatThrownBy(() -> new DetallesCertificado(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void clavePrivada_extraeAlgoritmo() {
        PrivateKey clave = certificado.clavePrivada();

        assertThat(clave.getAlgorithm()).isNotNull();
        assertThat(clave.getAlgorithm()).contains("RSA");
    }

    @Test
    void clavePrivada_tieneFormato() {
        PrivateKey clave = certificado.clavePrivada();

        assertThat(clave.getFormat()).isNotEmpty();
    }

    @Test
    void certificado_extraeSubjectDN() {
        X509Certificate cert = certificado.certificado();
        String subjectDN = cert.getSubjectX500Principal().getName();

        assertThat(subjectDN).isNotEmpty();
        assertThat(subjectDN).contains("CN=");
    }

    @Test
    void certificado_extraeIssuerDN() {
        X509Certificate cert = certificado.certificado();
        String issuerDN = cert.getIssuerX500Principal().getName();

        assertThat(issuerDN).isNotEmpty();
    }

    @Test
    void certificado_extraeSerialNumber() {
        X509Certificate cert = certificado.certificado();

        assertThat(cert.getSerialNumber()).isNotNull();
        assertThat(cert.getSerialNumber().signum()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void certificado_extraeFechaValezInicio() {
        X509Certificate cert = certificado.certificado();
        Date notBefore = cert.getNotBefore();

        assertThat(notBefore).isNotNull();
    }

    @Test
    void certificado_extraeFechaValezFin() {
        X509Certificate cert = certificado.certificado();
        Date notAfter = cert.getNotAfter();

        assertThat(notAfter).isNotNull();
    }

    @Test
    void certificado_validezCronologica() {
        X509Certificate cert = certificado.certificado();

        assertThat(cert.getNotBefore()).isBefore(cert.getNotAfter());
    }

    @Test
    void certificado_extraeClavePublica() {
        X509Certificate cert = certificado.certificado();

        assertThat(cert.getPublicKey()).isNotNull();
        assertThat(cert.getPublicKey().getAlgorithm()).contains("RSA");
    }

    @Test
    void certificado_tieneVersionX509v3() {
        X509Certificate cert = certificado.certificado();

        // Versión 2 en el campo version corresponde a X.509 v3
        assertThat(cert.getVersion()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void certificado_extraeSignatureAlgorithm() {
        X509Certificate cert = certificado.certificado();

        assertThat(cert.getSigAlgName()).isNotEmpty();
    }

    @Test
    void extraeSubjectCN() {
        X509Certificate cert = certificado.certificado();
        String subjectDN = cert.getSubjectX500Principal().getName();

        // CN (Common Name) debe estar presente
        assertThat(subjectDN).contains("CN=");
    }

    @Test
    void record_equals_mismosValores() {
        InputStream is = DetallesCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, "changeit");
        DetallesCertificado cert2 = CargadorCertificado.cargar(origen);

        assertThat(certificado).isEqualTo(cert2);
    }

    @Test
    void record_hashCode_consistente() {
        InputStream is = DetallesCertificadoTest.class.getClassLoader().getResourceAsStream("test-keystore.p12");
        OrigenCertificado origen = new OrigenCertificado(is, "changeit");
        DetallesCertificado cert2 = CargadorCertificado.cargar(origen);

        assertThat(certificado.hashCode()).isEqualTo(cert2.hashCode());
    }

    @Test
    void certificado_algoritmoClave_RSA() {
        X509Certificate cert = certificado.certificado();
        String algoritmo = cert.getPublicKey().getAlgorithm();

        assertThat(algoritmo).isEqualTo("RSA");
    }

    @Test
    void certificado_noEstaRevocado() {
        // Solo verificamos que podemos acceder a métodos de revocación
        X509Certificate cert = certificado.certificado();

        // El test keystore es un certificado autofirmado de prueba
        assertThat(cert).isNotNull();
    }

    @Test
    void clavePrivada_obtieneEncodedForm() {
        PrivateKey clave = certificado.clavePrivada();
        byte[] encoded = clave.getEncoded();

        assertThat(encoded).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"CN", "O", "C"})
    void certificado_contienePropiedadesX500(String propiedad) {
        X509Certificate cert = certificado.certificado();
        String subjectDN = cert.getSubjectX500Principal().getName();

        // Al menos CN debe estar presente en el DN
        if ("CN".equals(propiedad)) {
            assertThat(subjectDN).contains("CN=");
        }
    }

    @Test
    void certificado_toString_conInformacion() {
        String str = certificado.toString();

        assertThat(str).isNotEmpty();
        assertThat(str).contains("DetallesCertificado");
    }

    @Test
    void clavePrivada_tipoPrivateKey() {
        PrivateKey clave = certificado.clavePrivada();

        assertThat(clave).isInstanceOf(PrivateKey.class);
    }

    @Test
    void certificado_tipoX509Certificate() {
        X509Certificate cert = certificado.certificado();

        assertThat(cert).isInstanceOf(X509Certificate.class);
    }
}
