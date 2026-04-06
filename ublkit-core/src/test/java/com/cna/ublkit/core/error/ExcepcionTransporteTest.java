package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionTransporte - Exception Tests")
class ExcepcionTransporteTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error de transporte HTTP";

        // Act
        ExcepcionTransporte excepcion = new ExcepcionTransporte(mensaje);

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
        String mensaje = "Error en conexión";
        Throwable causa = new java.net.ConnectException("Connection refused");

        // Act
        ExcepcionTransporte excepcion = new ExcepcionTransporte(mensaje, causa);

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
        ExcepcionTransporte excepcion = new ExcepcionTransporte(null);

        // Assert
        assertThat(excepcion).hasMessage(null);
    }

    @Test
    @DisplayName("heredaDeExcepcionUblKit")
    void heredaDeExcepcionUblKit() {
        // Arrange
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "No se pudo enviar el documento a SUNAT";
        ExcepcionTransporte excepcion = new ExcepcionTransporte(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new RuntimeException("Network timeout");
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Error", causaEsperada);

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
            throw new ExcepcionTransporte("Transport failed");
        })
            .isInstanceOf(ExcepcionTransporte.class)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage("Transport failed");
    }

    @Test
    @DisplayName("lanzarConCausa_puedeSerCatchedConCause")
    void lanzarConCausa_puedeSerCatchedConCause() {
        // Arrange
        Throwable causa = new RuntimeException("Socket timeout");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionTransporte("Transport error", causa);
        })
            .isInstanceOf(ExcepcionTransporte.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("constructorSinCausa_noTieneCausa")
    void constructorSinCausa_noTieneCausa() {
        // Act
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Mensaje");

        // Assert
        assertThat(excepcion.getCause()).isNull();
    }

    @Test
    @DisplayName("toString_contieneMensajeYTipo")
    void toString_contieneMensajeYTipo() {
        // Arrange
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Test error");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionTransporte")
            .contains("Test error");
    }

    @Test
    @DisplayName("stackTrace_disponible")
    void stackTrace_disponible() {
        // Arrange
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("encadenamiento_conMultiplesCausas")
    void encadenamiento_conMultiplesCausas() {
        // Arrange
        Throwable causaRaiz = new IOException("Connection reset");
        Throwable causaIntermedia = new RuntimeException("Network error", causaRaiz);
        ExcepcionTransporte excepcion = new ExcepcionTransporte("Transport error", causaIntermedia);

        // Act & Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }
}
