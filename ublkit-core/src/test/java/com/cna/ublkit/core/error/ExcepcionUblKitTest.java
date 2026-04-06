package com.cna.ublkit.core.error;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExcepcionUblKit - Exception Tests")
class ExcepcionUblKitTest {

    @Test
    @DisplayName("crear_conMensaje_exitoso")
    void crear_conMensaje_exitoso() {
        // Arrange
        String mensaje = "Error en la operación";

        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit(mensaje);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(RuntimeException.class)
            .hasMessage(mensaje)
            .hasNoCause();
    }

    @Test
    @DisplayName("crear_conMensajeYCausa_exitoso")
    void crear_conMensajeYCausa_exitoso() {
        // Arrange
        String mensaje = "Error en la operación";
        Throwable causa = new IllegalArgumentException("Argumento inválido");

        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit(mensaje, causa);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(RuntimeException.class)
            .hasMessage(mensaje)
            .hasCause(causa)
            .getCause()
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("crear_conMensajeNull_exitoso")
    void crear_conMensajeNull_exitoso() {
        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit(null);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(RuntimeException.class)
            .hasMessage(null)
            .hasNoCause();
    }

    @Test
    @DisplayName("crear_conMensajeYCausaNull_exitoso")
    void crear_conMensajeYCausaNull_exitoso() {
        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Mensaje", null);

        // Assert
        assertThat(excepcion)
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Mensaje")
            .hasNoCause();
    }

    @Test
    @DisplayName("getMessage_retornaMensajeCorrect")
    void getMessage_retornaMensajeCorrect() {
        // Arrange
        String mensajeEsperado = "Error de validación";
        ExcepcionUblKit excepcion = new ExcepcionUblKit(mensajeEsperado);

        // Act
        String mensajeActual = excepcion.getMessage();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("getCause_retornaCausaCorrect")
    void getCause_retornaCausaCorrect() {
        // Arrange
        Throwable causaEsperada = new RuntimeException("Causa original");
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Mensaje", causaEsperada);

        // Act
        Throwable causaActual = excepcion.getCause();

        // Assert
        assertThat(causaActual).isEqualTo(causaEsperada);
    }

    @Test
    @DisplayName("lanzar_conMensaje_puedeSerCatch")
    void lanzar_conMensaje_puedeSerCatch() {
        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionUblKit("Test error");
        })
            .isInstanceOf(ExcepcionUblKit.class)
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Test error");
    }

    @Test
    @DisplayName("lanzar_conCausa_puedeSerCatchConCause")
    void lanzar_conCausa_puedeSerCatchConCause() {
        // Arrange
        Throwable causa = new IllegalStateException("Estado inválido");

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ExcepcionUblKit("Error con causa", causa);
        })
            .isInstanceOf(ExcepcionUblKit.class)
            .hasCause(causa);
    }

    @Test
    @DisplayName("toString_contieneMensajeYClase")
    void toString_contieneMensajeYClase() {
        // Arrange
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Error message");

        // Act
        String resultado = excepcion.toString();

        // Assert
        assertThat(resultado)
            .contains("ExcepcionUblKit")
            .contains("Error message");
    }

    @Test
    @DisplayName("instanceof_verificaRelacionHerencia")
    void instanceof_verificaRelacionHerencia() {
        // Arrange
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Test");

        // Act & Assert
        assertThat(excepcion).isInstanceOf(RuntimeException.class);
        assertThat(excepcion).isInstanceOf(Exception.class);
        assertThat(excepcion).isInstanceOf(Throwable.class);
    }

    @Test
    @DisplayName("stackTrace_disponibleEnExcepcion")
    void stackTrace_disponibleEnExcepcion() {
        // Arrange
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Test");

        // Act
        StackTraceElement[] stackTrace = excepcion.getStackTrace();

        // Assert
        assertThat(stackTrace).isNotEmpty();
    }

    @Test
    @DisplayName("constructorConCausa_encadenamientoCorrect")
    void constructorConCausa_encadenamientoCorrect() {
        // Arrange
        Throwable causaRaiz = new IOException("Error raíz");
        Throwable causaIntermedia = new RuntimeException("Causa intermedia", causaRaiz);

        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit("Mensaje final", causaIntermedia);

        // Assert
        assertThat(excepcion.getCause()).isEqualTo(causaIntermedia);
        assertThat(excepcion.getCause().getCause()).isEqualTo(causaRaiz);
    }

    @Test
    @DisplayName("mensajeVacio_esValido")
    void mensajeVacio_esValido() {
        // Act
        ExcepcionUblKit excepcion = new ExcepcionUblKit("");

        // Assert
        assertThat(excepcion.getMessage()).isEmpty();
    }
}
