package com.cna.ublkit.core.valor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Moneda - Value Object Tests")
class MonedaTest {

    @Test
    @DisplayName("crear_conCodigoIsoDeTres_exitoso")
    void crear_conCodigoIsoDeTres_exitoso() {
        // Arrange
        String codigo = "USD";

        // Act
        Moneda moneda = new Moneda(codigo);

        // Assert
        assertThat(moneda.codigo()).isEqualTo("USD");
    }

    @Test
    @DisplayName("crear_conCodigoMinuscula_normalizaAMayuscula")
    void crear_conCodigoMinuscula_normalizaAMayuscula() {
        // Arrange
        String codigo = "usd";

        // Act
        Moneda moneda = new Moneda(codigo);

        // Assert
        assertThat(moneda.codigo()).isEqualTo("USD");
    }

    @Test
    @DisplayName("crear_conCodigoMixto_normalizaAMayuscula")
    void crear_conCodigoMixto_normalizaAMayuscula() {
        // Arrange
        String codigo = "UsD";

        // Act
        Moneda moneda = new Moneda(codigo);

        // Assert
        assertThat(moneda.codigo()).isEqualTo("USD");
    }

    @Test
    @DisplayName("crear_conCodigoNull_lanzaIllegalArgumentException")
    void crear_conCodigoNull_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @Test
    @DisplayName("crear_conCodigoVacio_lanzaIllegalArgumentException")
    void crear_conCodigoVacio_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @Test
    @DisplayName("crear_conCodigoMenorATres_lanzaIllegalArgumentException")
    void crear_conCodigoMenorATres_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda("US"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @Test
    @DisplayName("crear_conCodigoMayorATres_lanzaIllegalArgumentException")
    void crear_conCodigoMayorATres_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda("USDA"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @Test
    @DisplayName("crear_conCodigoConNumeros_lanzaIllegalArgumentException")
    void crear_conCodigoConNumeros_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda("US1"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @Test
    @DisplayName("crear_conCodigoConCaracteresEspeciales_lanzaIllegalArgumentException")
    void crear_conCodigoConCaracteresEspeciales_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new Moneda("US$"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda debe tener 3 letras ISO-4217");
    }

    @ParameterizedTest
    @ValueSource(strings = {"USD", "EUR", "GBP", "JPY", "CHF", "CAD", "AUD", "NZD", "CNY", "INR"})
    @DisplayName("crear_conDiferentesCodigos_exitoso")
    void crear_conDiferentesCodigos_exitoso(String codigo) {
        // Act
        Moneda moneda = new Moneda(codigo);

        // Assert
        assertThat(moneda.codigo()).isEqualTo(codigo);
    }

    @Test
    @DisplayName("codigo_accesor_retornaValorCorrect")
    void codigo_accesor_retornaValorCorrect() {
        // Arrange
        String codigoEsperado = "PEN";
        Moneda moneda = new Moneda(codigoEsperado);

        // Act
        String codigoActual = moneda.codigo();

        // Assert
        assertThat(codigoActual).isEqualTo(codigoEsperado);
    }

    @Test
    @DisplayName("equals_mismoCodigoEn_retornaTrue")
    void equals_mismoCodigoEn_retornaTrue() {
        // Arrange
        Moneda moneda1 = new Moneda("USD");
        Moneda moneda2 = new Moneda("USD");

        // Act & Assert
        assertThat(moneda1).isEqualTo(moneda2);
    }

    @Test
    @DisplayName("equals_mismoCodigoPeroDistintaCapitalizacion_retornaTrue")
    void equals_mismoCodigoPeroDistintaCapitalizacion_retornaTrue() {
        // Arrange
        Moneda moneda1 = new Moneda("USD");
        Moneda moneda2 = new Moneda("usd");

        // Act & Assert
        assertThat(moneda1).isEqualTo(moneda2);
    }

    @Test
    @DisplayName("equals_diferenteCodigo_retornaFalse")
    void equals_diferenteCodigo_retornaFalse() {
        // Arrange
        Moneda moneda1 = new Moneda("USD");
        Moneda moneda2 = new Moneda("EUR");

        // Act & Assert
        assertThat(moneda1).isNotEqualTo(moneda2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        Moneda moneda = new Moneda("USD");

        // Act & Assert
        assertThat(moneda).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        Moneda moneda = new Moneda("USD");
        Object otro = "USD";

        // Act & Assert
        assertThat(moneda).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoCodigoNormalizado_mismoHash")
    void hashCode_mismoCodigoNormalizado_mismoHash() {
        // Arrange
        Moneda moneda1 = new Moneda("USD");
        Moneda moneda2 = new Moneda("usd");

        // Act & Assert
        assertThat(moneda1.hashCode()).isEqualTo(moneda2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteCodigo_diferenteHash")
    void hashCode_diferenteCodigo_diferenteHash() {
        // Arrange
        Moneda moneda1 = new Moneda("USD");
        Moneda moneda2 = new Moneda("EUR");

        // Act & Assert
        assertThat(moneda1.hashCode()).isNotEqualTo(moneda2.hashCode());
    }

    @Test
    @DisplayName("toString_retornaCodigoMoneda")
    void toString_retornaCodigoMoneda() {
        // Arrange
        Moneda moneda = new Moneda("USD");

        // Act
        String resultado = moneda.toString();

        // Assert
        assertThat(resultado).isEqualTo("USD");
    }

    @Test
    @DisplayName("toString_conCodigoMinuscula_retornaMayuscula")
    void toString_conCodigoMinuscula_retornaMayuscula() {
        // Arrange
        Moneda moneda = new Moneda("eur");

        // Act
        String resultado = moneda.toString();

        // Assert
        assertThat(resultado).isEqualTo("EUR");
    }
}
