package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionFirmaDigital - Exception Tests")
class ExcepcionFirmaDigitalTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error en firma digital";

        // Act
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital(mensaje);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(ExcepcionUblKit.class)
            .isInstanceOf(RuntimeException.class)
            .hasMessage(mensaje)
            .hasNoCause();
    }

    @Test
    @DisplayName("crear_conMensajeYCausa_exitoso")
    void crear_conMensajeYCausa_exitoso() {
        // Arrange
        String mensaje = "Error al firmar el documento";
        Throwable causa = new SecurityException("Certificado no disponible");

        // Act
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital(mensaje, causa);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage(mensaje)
            .hasCause(causa);
    }

    @Test
    @DisplayName("crear_conMensajeNull_exitoso")
    void crear_conMensajeNull_exitoso() {
        // Act
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital(null);

        // Assert
        assertThat(excepcion).hasMessage(null);
    }

    @Test
    @DisplayName("heredaDeExcepcionUblKit")
    void heredaDeExcepcionUblKit() {
        // Arrange
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "No se pudo firmar el XML";
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new IllegalArgumentException("Formato de certificado inválido");
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Error", causaEsperada);

        // Act
        Throwable causaActual = excepcion.getCause();

        // Assert
        assertThat(causaActual).isEqualTo(causaEsperada);
    }

    @Test
    @DisplayName("lanzar_puedeSerCatched")
    void lanzar_puedeSerCatched() {
        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionFirmaDigital("Signing failed");
        })
            .isInstanceOf(ExcepcionFirmaDigital.class)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage("Signing failed");
    }

    @Test
    @DisplayName("lanzarConCausa_puedeSerCatchedConCause")
    void lanzarConCausa_puedeSerCatchedConCause() {
        // Arrange
        Throwable causa = new RuntimeException("Invalid key");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionFirmaDigital("Signing error", causa);
        })
            .isInstanceOf(ExcepcionFirmaDigital.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("constructorSinCausa_noTieneCausa")
    void constructorSinCausa_noTieneCausa() {
        // Act
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Mensaje");

        // Assert
        assertThat(excepcion.getCause()).isNull();
    }

    @Test
    @DisplayName("toString_contieneMensajeYTipo")
    void toString_contieneMensajeYTipo() {
        // Arrange
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Test error");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionFirmaDigital")
            .contains("Test error");
    }

    @Test
    @DisplayName("stackTrace_disponible")
    void stackTrace_disponible() {
        // Arrange
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("encadenamiento_conMultiplesCausas")
    void encadenamiento_conMultiplesCausas() {
        // Arrange
        Throwable causaRaiz = new IOException("Cannot read keystore");
        Throwable causaIntermedia = new RuntimeException("Keystore error", causaRaiz);
        ExcepcionFirmaDigital excepcion = new ExcepcionFirmaDigital("Signing error", causaIntermedia);

        // Act & Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }
}
