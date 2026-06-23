package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionValidacion - Exception Tests")
class ExcepcionValidacionTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error de validación";

        // Act
        ExcepcionValidacion excepcion = new ExcepcionValidacion(mensaje);

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
        String mensaje = "Validación fallida";
        Throwable causa = new IllegalArgumentException("Campo requerido no presente");

        // Act
        ExcepcionValidacion excepcion = new ExcepcionValidacion(mensaje, causa);

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
        ExcepcionValidacion excepcion = new ExcepcionValidacion(null);

        // Assert
        assertThat(excepcion).hasMessage(null);
    }

    @Test
    @DisplayName("heredaDeExcepcionUblKit")
    void heredaDeExcepcionUblKit() {
        // Arrange
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "El documento no cumple con los requisitos de validación";
        ExcepcionValidacion excepcion = new ExcepcionValidacion(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new IllegalStateException("Estado incorrecto");
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Error", causaEsperada);

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
            throw new ExcepcionValidacion("Validation failed");
        })
            .isInstanceOf(ExcepcionValidacion.class)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage("Validation failed");
    }

    @Test
    @DisplayName("lanzarConCausa_puedeSerCatchedConCause")
    void lanzarConCausa_puedeSerCatchedConCause() {
        // Arrange
        Throwable causa = new RuntimeException("Invalid format");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionValidacion("Validation error", causa);
        })
            .isInstanceOf(ExcepcionValidacion.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("constructorSinCausa_noTieneCausa")
    void constructorSinCausa_noTieneCausa() {
        // Act
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Mensaje");

        // Assert
        assertThat(excepcion.getCause()).isNull();
    }

    @Test
    @DisplayName("toString_contieneMensajeYTipo")
    void toString_contieneMensajeYTipo() {
        // Arrange
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Test error");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionValidacion")
            .contains("Test error");
    }

    @Test
    @DisplayName("stackTrace_disponible")
    void stackTrace_disponible() {
        // Arrange
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("encadenamiento_conMultiplesCausas")
    void encadenamiento_conMultiplesCausas() {
        // Arrange
        Throwable causaRaiz = new IOException("Cannot read file");
        Throwable causaIntermedia = new RuntimeException("File processing error", causaRaiz);
        ExcepcionValidacion excepcion = new ExcepcionValidacion("Validation error", causaIntermedia);

        // Act & Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }

    @Test
    @DisplayName("validarCamposObligatorios_lanzaExcepcion")
    void validarCamposObligatorios_lanzaExcepcion() {
        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionValidacion("RUC es obligatorio");
        })
            .isInstanceOf(ExcepcionValidacion.class)
            .hasMessage("RUC es obligatorio");
    }

    @Test
    @DisplayName("validarFormatos_lanzaExcepcion")
    void validarFormatos_lanzaExcepcion() {
        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionValidacion("Formato de email inválido");
        })
            .isInstanceOf(ExcepcionValidacion.class)
            .hasMessage("Formato de email inválido");
    }
}
