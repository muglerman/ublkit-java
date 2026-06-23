package com.cna.ublkit.sign.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test suite for ResultadoFirma record - signing operation result model.
 * Tests factory methods, success/failure states, and data handling.
 */
class ResultadoFirmaTest {

    private static final byte[] BYTES_XML = {1, 2, 3, 4, 5};
    private static final String XML_STR = "<?xml version=\"1.0\"?><test/>";
    private static final String DIGEST = "ABC123DEF456";

    @Test
    void exitoso_creaCorrecto() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(resultado).isNotNull();
        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmado()).isEqualTo(BYTES_XML);
        assertThat(resultado.xmlFirmadoStr()).isEqualTo(XML_STR);
        assertThat(resultado.digestValue()).isEqualTo(DIGEST);
        assertThat(resultado.mensajeError()).isNull();
    }

    @Test
    void fallido_creaCorrecto() {
        String mensajeError = "Error en la firma";
        ResultadoFirma resultado = ResultadoFirma.fallido(mensajeError);

        assertThat(resultado).isNotNull();
        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isEqualTo(mensajeError);
        assertThat(resultado.xmlFirmado()).isNull();
        assertThat(resultado.xmlFirmadoStr()).isNull();
        assertThat(resultado.digestValue()).isNull();
    }

    @Test
    void exitoso_conBytesNulos() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(null, XML_STR, DIGEST);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmado()).isNull();
        assertThat(resultado.xmlFirmadoStr()).isEqualTo(XML_STR);
    }

    @Test
    void exitoso_conStringNulo() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(BYTES_XML, null, DIGEST);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.xmlFirmado()).isEqualTo(BYTES_XML);
        assertThat(resultado.xmlFirmadoStr()).isNull();
    }

    @Test
    void exitoso_conDigestNulo() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(BYTES_XML, XML_STR, null);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.digestValue()).isNull();
        assertThat(resultado.xmlFirmado()).isEqualTo(BYTES_XML);
    }

    @Test
    void xmlFirmado_retornaClone() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        byte[] bytes1 = resultado.xmlFirmado();
        byte[] bytes2 = resultado.xmlFirmado();

        assertThat(bytes1).isEqualTo(bytes2);
        assertThat(bytes1).isNotSameAs(bytes2);
    }

    @Test
    void xmlFirmado_nullPermaneceNull() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(null, XML_STR, DIGEST);

        assertThat(resultado.xmlFirmado()).isNull();
    }

    @Test
    void fallido_conMensajeError() {
        ResultadoFirma resultado = ResultadoFirma.fallido("No se puede firmar");

        assertThat(resultado.mensajeError()).isEqualTo("No se puede firmar");
        assertThat(resultado.exitoso()).isFalse();
    }

    @Test
    void fallido_conMensajeVacio() {
        ResultadoFirma resultado = ResultadoFirma.fallido("");

        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isEmpty();
    }

    @Test
    void equals_mismosValores() {
        ResultadoFirma r1 = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        ResultadoFirma r2 = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(r1).isEqualTo(r2);
    }

    @Test
    void equals_diferentesValores() {
        ResultadoFirma r1 = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        ResultadoFirma r2 = ResultadoFirma.exitoso(new byte[]{6, 7, 8}, XML_STR, DIGEST);

        assertThat(r1).isNotEqualTo(r2);
    }

    @Test
    void equals_diferentesEstados() {
        ResultadoFirma exitoso = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        ResultadoFirma fallido = ResultadoFirma.fallido("Error");

        assertThat(exitoso).isNotEqualTo(fallido);
    }

    @Test
    void equals_reflexivo() {
        ResultadoFirma r = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(r).isEqualTo(r);
    }

    @Test
    void equals_conNull() {
        ResultadoFirma r = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(r).isNotEqualTo(null);
    }

    @Test
    void equals_conOtroTipo() {
        ResultadoFirma r = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(r).isNotEqualTo("string");
    }

    @Test
    void hashCode_consistente() {
        ResultadoFirma r1 = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        ResultadoFirma r2 = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);

        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void toString_conExitoso() {
        ResultadoFirma resultado = ResultadoFirma.exitoso(BYTES_XML, XML_STR, DIGEST);
        String str = resultado.toString();

        assertThat(str).contains("ResultadoFirma");
        assertThat(str).contains("exitoso=true");
        assertThat(str).contains("digestValue=" + DIGEST);
    }

    @Test
    void toString_conFallido() {
        ResultadoFirma resultado = ResultadoFirma.fallido("Error crítico");
        String str = resultado.toString();

        assertThat(str).contains("ResultadoFirma");
        assertThat(str).contains("exitoso=false");
        assertThat(str).contains("Error crítico");
    }

    @Test
    void creacion_conConstructorCompleto() {
        ResultadoFirma resultado = new ResultadoFirma(BYTES_XML, XML_STR, DIGEST, true, null);

        assertThat(resultado.exitoso()).isTrue();
        assertThat(resultado.mensajeError()).isNull();
    }

    @Test
    void registro_defensivaCopiaBytes() {
        byte[] bytesOriginales = {1, 2, 3};
        ResultadoFirma resultado = ResultadoFirma.exitoso(bytesOriginales, XML_STR, DIGEST);

        bytesOriginales[0] = 99;

        assertThat(resultado.xmlFirmado()[0]).isEqualTo((byte) 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Error simple",
            "Error complejo con múltiples líneas",
            "NullPointerException en firma",
            ""
    })
    void fallido_conVariosMensajes(String mensaje) {
        ResultadoFirma resultado = ResultadoFirma.fallido(mensaje);

        assertThat(resultado.exitoso()).isFalse();
        assertThat(resultado.mensajeError()).isEqualTo(mensaje);
    }
}
