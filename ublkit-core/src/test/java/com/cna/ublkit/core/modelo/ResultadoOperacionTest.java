package com.cna.ublkit.core.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ResultadoOperacion - Record Tests")
class ResultadoOperacionTest {

    @Test
    @DisplayName("crear_conExitoTrue_exitoso")
    void crear_conExitoTrue_exitoso() {
        // Arrange
        String dato = "Operación exitosa";

        // Act
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(true, dato, null, null);

        // Assert
        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo(dato);
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    @Test
    @DisplayName("crear_conExitoFalse_exitoso")
    void crear_conExitoFalse_exitoso() {
        // Act
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(false, null, "ERR001", "Error de validación");

        // Assert
        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.dato()).isNull();
        assertThat(resultado.codigoError()).isEqualTo("ERR001");
        assertThat(resultado.mensajeError()).isEqualTo("Error de validación");
    }

    @Test
    @DisplayName("ok_conDato_crearResultadoExitoso")
    void ok_conDato_crearResultadoExitoso() {
        // Arrange
        String dato = "Datos procesados";

        // Act
        ResultadoOperacion<String> resultado = ResultadoOperacion.ok(dato);

        // Assert
        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo(dato);
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    @Test
    @DisplayName("ok_conDatoNull_crearResultadoExitoso")
    void ok_conDatoNull_crearResultadoExitoso() {
        // Act
        ResultadoOperacion<String> resultado = ResultadoOperacion.ok(null);

        // Assert
        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    @Test
    @DisplayName("error_conCodigoYMensaje_crearResultadoError")
    void error_conCodigoYMensaje_crearResultadoError() {
        // Arrange
        String codigo = "ERR001";
        String mensaje = "Error en la validación";

        // Act
        ResultadoOperacion<String> resultado = ResultadoOperacion.error(codigo, mensaje);

        // Assert
        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.dato()).isNull();
        assertThat(resultado.codigoError()).isEqualTo(codigo);
        assertThat(resultado.mensajeError()).isEqualTo(mensaje);
    }

    @Test
    @DisplayName("error_conCodigoYMensajeNull_crearResultadoError")
    void error_conCodigoYMensajeNull_crearResultadoError() {
        // Act
        ResultadoOperacion<String> resultado = ResultadoOperacion.error(null, null);

        // Assert
        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.dato()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
        "true, Dato1, , ",
        "false, , ERR001, Error 1",
        "true, , , ",
        "false, , ERR002, Error 2"
    })
    @DisplayName("crear_conDiferentesValores_exitoso")
    void crear_conDiferentesValores_exitoso(String exito, String dato, String codigo, String mensaje) {
        // Arrange
        boolean exitoBool = Boolean.parseBoolean(exito);
        String datoProcesado = (dato == null || dato.isEmpty()) ? null : dato;
        String codigoProcesado = (codigo == null || codigo.isEmpty()) ? null : codigo;
        String mensajeProcesado = (mensaje == null || mensaje.isEmpty()) ? null : mensaje;

        // Act
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(exitoBool, datoProcesado, codigoProcesado, mensajeProcesado);

        // Assert
        assertThat(resultado.exito()).isEqualTo(exitoBool);
        assertThat(resultado.dato()).isEqualTo(datoProcesado);
        assertThat(resultado.codigoError()).isEqualTo(codigoProcesado);
        assertThat(resultado.mensajeError()).isEqualTo(mensajeProcesado);
    }

    @Test
    @DisplayName("exito_accesor_retornaValorCorrect")
    void exito_accesor_retornaValorCorrect() {
        // Arrange
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(true, "Dato", null, null);

        // Act
        boolean exito = resultado.exito();

        // Assert
        assertThat(exito).isTrue();
    }

    @Test
    @DisplayName("dato_accesor_retornaValorCorrect")
    void dato_accesor_retornaValorCorrect() {
        // Arrange
        String datoEsperado = "Datos procesados";
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(true, datoEsperado, null, null);

        // Act
        String datoActual = resultado.dato();

        // Assert
        assertThat(datoActual).isEqualTo(datoEsperado);
    }

    @Test
    @DisplayName("codigoError_accesor_retornaValorCorrect")
    void codigoError_accesor_retornaValorCorrect() {
        // Arrange
        String codigoEsperado = "ERR001";
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(false, null, codigoEsperado, "Error");

        // Act
        String codigoActual = resultado.codigoError();

        // Assert
        assertThat(codigoActual).isEqualTo(codigoEsperado);
    }

    @Test
    @DisplayName("mensajeError_accesor_retornaValorCorrect")
    void mensajeError_accesor_retornaValorCorrect() {
        // Arrange
        String mensajeEsperado = "Mensaje de error importante";
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(false, null, "ERR001", mensajeEsperado);

        // Act
        String mensajeActual = resultado.mensajeError();

        // Assert
        assertThat(mensajeActual).isEqualTo(mensajeEsperado);
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        ResultadoOperacion<String> resultado1 = new ResultadoOperacion<>(true, "Dato", null, null);
        ResultadoOperacion<String> resultado2 = new ResultadoOperacion<>(true, "Dato", null, null);

        // Act & Assert
        assertThat(resultado1).isEqualTo(resultado2);
    }

    @Test
    @DisplayName("equals_diferenteExito_retornaFalse")
    void equals_diferenteExito_retornaFalse() {
        // Arrange
        ResultadoOperacion<String> resultado1 = new ResultadoOperacion<>(true, "Dato", null, null);
        ResultadoOperacion<String> resultado2 = new ResultadoOperacion<>(false, "Dato", null, null);

        // Act & Assert
        assertThat(resultado1).isNotEqualTo(resultado2);
    }

    @Test
    @DisplayName("equals_diferenteDato_retornaFalse")
    void equals_diferenteDato_retornaFalse() {
        // Arrange
        ResultadoOperacion<String> resultado1 = new ResultadoOperacion<>(true, "Dato1", null, null);
        ResultadoOperacion<String> resultado2 = new ResultadoOperacion<>(true, "Dato2", null, null);

        // Act & Assert
        assertThat(resultado1).isNotEqualTo(resultado2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(true, "Dato", null, null);

        // Act & Assert
        assertThat(resultado).isNotEqualTo(null);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        ResultadoOperacion<String> resultado1 = new ResultadoOperacion<>(true, "Dato", null, null);
        ResultadoOperacion<String> resultado2 = new ResultadoOperacion<>(true, "Dato", null, null);

        // Act & Assert
        assertThat(resultado1.hashCode()).isEqualTo(resultado2.hashCode());
    }

    @Test
    @DisplayName("ok_conTipoGenerico_exitoso")
    void ok_conTipoGenerico_exitoso() {
        // Arrange
        Integer dato = 42;

        // Act
        ResultadoOperacion<Integer> resultado = ResultadoOperacion.ok(dato);

        // Assert
        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo(42);
        assertThat(resultado.codigoError()).isNull();
    }

    @Test
    @DisplayName("error_conMultiplesCodigos_exitoso")
    void error_conMultiplesCodigos_exitoso() {
        // Act
        ResultadoOperacion<String> resultado1 = ResultadoOperacion.error("VALIDATION_ERROR", "Validación fallida");
        ResultadoOperacion<String> resultado2 = ResultadoOperacion.error("SYSTEM_ERROR", "Error del sistema");

        // Assert
        assertThat(resultado1.codigoError()).isEqualTo("VALIDATION_ERROR");
        assertThat(resultado2.codigoError()).isEqualTo("SYSTEM_ERROR");
        assertThat(resultado1).isNotEqualTo(resultado2);
    }

    @Test
    @DisplayName("toString_representacionValida")
    void toString_representacionValida() {
        // Arrange
        ResultadoOperacion<String> resultado = new ResultadoOperacion<>(true, "Dato", null, null);

        // Act
        String resultadoStr = resultado.toString();

        // Assert
        assertThat(resultadoStr)
            .contains("ResultadoOperacion")
            .contains("true")
            .contains("Dato");
    }
}
