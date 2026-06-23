package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionSerializacionXml - Exception Tests")
class ExcepcionSerializacionXmlTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error al serializar XML";

        // Act
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml(mensaje);

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
        String mensaje = "Error de serialización";
        Throwable causa = new IllegalArgumentException("Estructura XML inválida");

        // Act
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml(mensaje, causa);

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
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml(null);

        // Assert
        assertThat(excepcion).hasMessage(null);
    }

    @Test
    @DisplayName("heredaDeExcepcionUblKit")
    void heredaDeExcepcionUblKit() {
        // Arrange
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(ExcepcionUblKit.class);
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "No se pudo convertir el documento a XML UBL";
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new RuntimeException("XML parsing error");
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Error", causaEsperada);

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
            throw new ExcepcionSerializacionXml("Serialization failed");
        })
            .isInstanceOf(ExcepcionSerializacionXml.class)
            .isInstanceOf(ExcepcionUblKit.class)
            .hasMessage("Serialization failed");
    }

    @Test
    @DisplayName("lanzarConCausa_puedeSerCatchedConCause")
    void lanzarConCausa_puedeSerCatchedConCause() {
        // Arrange
        Throwable causa = new RuntimeException("Invalid namespace");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionSerializacionXml("Serialization error", causa);
        })
            .isInstanceOf(ExcepcionSerializacionXml.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("constructorSinCausa_noTieneCausa")
    void constructorSinCausa_noTieneCausa() {
        // Act
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Mensaje");

        // Assert
        assertThat(excepcion.getCause()).isNull();
    }

    @Test
    @DisplayName("toString_contieneMensajeYTipo")
    void toString_contieneMensajeYTipo() {
        // Arrange
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Test error");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionSerializacionXml")
            .contains("Test error");
    }

    @Test
    @DisplayName("stackTrace_disponible")
    void stackTrace_disponible() {
        // Arrange
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("encadenamiento_conMultiplesCausas")
    void encadenamiento_conMultiplesCausas() {
        // Arrange
        Throwable causaRaiz = new IOException("File not found");
        Throwable causaIntermedia = new RuntimeException("IO error", causaRaiz);
        ExcepcionSerializacionXml excepcion = new ExcepcionSerializacionXml("Serialization error", causaIntermedia);

        // Act & Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }
}
