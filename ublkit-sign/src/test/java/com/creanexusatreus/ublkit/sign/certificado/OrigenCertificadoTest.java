package com.cna.ublkit.sign.certificado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for OrigenCertificado record - certificate source specification.
 * Tests creation and handling of certificate origins from different sources.
 */
class OrigenCertificadoTest {

    @Test
    void creacion_conConstructorCompleto() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        String password = "test123";
        String tipo = "PKCS12";

        OrigenCertificado origen = new OrigenCertificado(is, password, tipo);

        assertThat(origen).isNotNull();
        assertThat(origen.inputStream()).isEqualTo(is);
        assertThat(origen.password()).isEqualTo(password);
        assertThat(origen.tipo()).isEqualTo(tipo);
    }

    @Test
    void creacion_conConstructorConveniente_PKCS12() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        String password = "password";

        OrigenCertificado origen = new OrigenCertificado(is, password);

        assertThat(origen.inputStream()).isEqualTo(is);
        assertThat(origen.password()).isEqualTo(password);
        assertThat(origen.tipo()).isEqualTo("PKCS12");
    }

    @Test
    void creacion_conTipoJKS() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password", "JKS");

        assertThat(origen.tipo()).isEqualTo("JKS");
    }

    @Test
    void creacion_conTipoPKCS12() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password", "PKCS12");

        assertThat(origen.tipo()).isEqualTo("PKCS12");
    }

    @Test
    void creacion_conPasswordVacio() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "");

        assertThat(origen.password()).isEmpty();
    }

    @Test
    void creacion_conPasswordLargo() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        String passwordLargo = "a".repeat(1000);
        OrigenCertificado origen = new OrigenCertificado(is, passwordLargo);

        assertThat(origen.password()).isEqualTo(passwordLargo);
    }

    @Test
    void creacion_conInputStreamNulo() {
        OrigenCertificado origen = new OrigenCertificado(null, "password");

        assertThat(origen.inputStream()).isNull();
    }

    @Test
    void creacion_conPasswordNulo() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, null);

        assertThat(origen.password()).isNull();
    }

    @Test
    void creacion_conTipoNulo() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password", null);

        assertThat(origen.tipo()).isNull();
    }

    @Test
    void creacion_conTipoVacio() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password", "");

        assertThat(origen.tipo()).isEmpty();
    }

    @Test
    void record_equals_mismosValores() {
        InputStream is1 = new ByteArrayInputStream(new byte[]{1, 2, 3});
        InputStream is2 = is1; // Mismo objeto

        OrigenCertificado origen1 = new OrigenCertificado(is1, "pass", "PKCS12");
        OrigenCertificado origen2 = new OrigenCertificado(is2, "pass", "PKCS12");

        assertThat(origen1).isEqualTo(origen2);
    }

    @Test
    void record_equals_diferentesValores() {
        InputStream is1 = new ByteArrayInputStream(new byte[]{1, 2, 3});
        InputStream is2 = new ByteArrayInputStream(new byte[]{4, 5, 6});

        OrigenCertificado origen1 = new OrigenCertificado(is1, "pass", "PKCS12");
        OrigenCertificado origen2 = new OrigenCertificado(is2, "pass", "PKCS12");

        assertThat(origen1).isNotEqualTo(origen2);
    }

    @Test
    void record_equals_diferentesPasswords() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});

        OrigenCertificado origen1 = new OrigenCertificado(is, "pass1", "PKCS12");
        OrigenCertificado origen2 = new OrigenCertificado(is, "pass2", "PKCS12");

        assertThat(origen1).isNotEqualTo(origen2);
    }

    @Test
    void record_equals_diferentesValoresTipo() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});

        OrigenCertificado origen1 = new OrigenCertificado(is, "pass", "PKCS12");
        OrigenCertificado origen2 = new OrigenCertificado(is, "pass", "JKS");

        assertThat(origen1).isNotEqualTo(origen2);
    }

    @Test
    void record_hashCode_consistente() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen1 = new OrigenCertificado(is, "pass", "PKCS12");
        OrigenCertificado origen2 = new OrigenCertificado(is, "pass", "PKCS12");

        assertThat(origen1.hashCode()).isEqualTo(origen2.hashCode());
    }

    @Test
    void record_toString_conInformacion() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "pass", "PKCS12");

        String str = origen.toString();
        assertThat(str).contains("OrigenCertificado");
    }

    @Test
    void obtenerPassword_correctamente() {
        String passwordExpected = "miPassword123";
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, passwordExpected);

        assertThat(origen.password()).isEqualTo(passwordExpected);
    }

    @Test
    void obtenerTipo_defaultPKCS12() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password");

        assertThat(origen.tipo()).isEqualTo("PKCS12");
    }

    @Test
    void obtenerInputStream_correctamente() {
        InputStream is = new ByteArrayInputStream(new byte[]{10, 20, 30});
        OrigenCertificado origen = new OrigenCertificado(is, "password");

        assertThat(origen.inputStream()).isSameAs(is);
    }

    @ParameterizedTest
    @ValueSource(strings = {"PKCS12", "JKS", "JCEKS", "BKS"})
    void creacion_conVariosFormatosTipo(String tipo) {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "password", tipo);

        assertThat(origen.tipo()).isEqualTo(tipo);
    }

    @Test
    void creacion_conPasswordEspeciales() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        String passwordEspecial = "pàss@wörd!#$%";
        OrigenCertificado origen = new OrigenCertificado(is, passwordEspecial);

        assertThat(origen.password()).isEqualTo(passwordEspecial);
    }

    @Test
    void record_reflexivoEquals() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "pass", "PKCS12");

        assertThat(origen).isEqualTo(origen);
    }

    @Test
    void record_equalsConNull() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "pass", "PKCS12");

        assertThat(origen).isNotEqualTo(null);
    }

    @Test
    void record_equalsConOtroTipo() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        OrigenCertificado origen = new OrigenCertificado(is, "pass", "PKCS12");

        assertThat(origen).isNotEqualTo("string");
    }

    @Test
    void obtenerValoresMultiples() {
        InputStream is = new ByteArrayInputStream(new byte[]{1, 2, 3});
        String password = "testpass";
        String tipo = "PKCS12";

        OrigenCertificado origen = new OrigenCertificado(is, password, tipo);

        // Llamar múltiples veces debe retornar los mismos valores
        assertThat(origen.inputStream()).isSameAs(origen.inputStream());
        assertThat(origen.password()).isEqualTo(origen.password());
        assertThat(origen.tipo()).isEqualTo(origen.tipo());
    }
}
