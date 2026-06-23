package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionEnsamblaje - Exception Tests")
class ExcepcionEnsamblajeTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error en ensamblaje del documento";

        // Act
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje(mensaje);

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
        String mensaje = "Error de ensamblaje";
        Throwable causa = new NullPointerException("Campo obligatorio ausente");

        // Act
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje(mensaje, causa);

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
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje(null);

        // Assert
        assertThat(excepcion).hasMessage(null);
    }

    @Test
    @DisplayName("heredaDeExcepcionUblKit")
    void heredaDeExcepcionUblKit() {
        // Arrange
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "No se pudo enriquecer el documento con datos SUNAT";
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new IllegalStateException("Estado inconsistente");
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Error", causaEsperada);

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
            throw new ExcepcionEnsamblaje("Assembly failed");
        })
            .isInstanceOf(ExcepcionEnsamblaje.class)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage("Assembly failed");
    }

    @Test
    @DisplayName("lanzarConCausa_puedeSerCatchedConCause")
    void lanzarConCausa_puedeSerCatchedConCause() {
        // Arrange
        Throwable causa = new RuntimeException("Missing field");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionEnsamblaje("Assembly error", causa);
        })
            .isInstanceOf(ExcepcionEnsamblaje.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("constructorSinCausa_noTieneCausa")
    void constructorSinCausa_noTieneCausa() {
        // Act
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Mensaje");

        // Assert
        assertThat(excepcion.getCause()).isNull();
    }

    @Test
    @DisplayName("toString_contieneMensajeYTipo")
    void toString_contieneMensajeYTipo() {
        // Arrange
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Test error");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionEnsamblaje")
            .contains("Test error");
    }

    @Test
    @DisplayName("stackTrace_disponible")
    void stackTrace_disponible() {
        // Arrange
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("encadenamiento_conMultiplesCausas")
    void encadenamiento_conMultiplesCausas() {
        // Arrange
        Throwable causaRaiz = new IOException("I/O error");
        Throwable causaIntermedia = new RuntimeException("Processing error", causaRaiz);
        ExcepcionEnsamblaje excepcion = new ExcepcionEnsamblaje("Assembly error", causaIntermedia);

        // Act & Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }
}
