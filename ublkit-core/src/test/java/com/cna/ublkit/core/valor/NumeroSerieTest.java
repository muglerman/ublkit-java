package com.cna.ublkit.core.valor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("NumeroSerie - Value Object Tests")
class NumeroSerieTest {

    @Test
    @DisplayName("crear_conValorValido_exitoso")
    void crear_conValorValido_exitoso() {
        // Arrange
        String valor = "F001";

        // Act
        NumeroSerie numeroSerie = new NumeroSerie(valor);

        // Assert
        assertThat(numeroSerie.getValor()).isEqualTo("F001");
    }

    @Test
    @DisplayName("crear_conValorNull_lanzaIllegalArgumentException")
    void crear_conValorNull_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new NumeroSerie(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La serie no puede ser nula ni vacía");
    }

    @Test
    @DisplayName("crear_conValorVacio_lanzaIllegalArgumentException")
    void crear_conValorVacio_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new NumeroSerie(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La serie no puede ser nula ni vacía");
    }

    @Test
    @DisplayName("crear_conValorConSoloEspacios_lanzaIllegalArgumentException")
    void crear_conValorConSoloEspacios_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new NumeroSerie("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La serie no puede ser nula ni vacía");
    }

    @Test
    @DisplayName("crear_conValorConSoloTabs_lanzaIllegalArgumentException")
    void crear_conValorConSoloTabs_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new NumeroSerie("\t\t"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La serie no puede ser nula ni vacía");
    }

    @ParameterizedTest
    @ValueSource(strings = {"F001", "B001", "FF001", "001", "A", "SERIE123"})
    @DisplayName("crear_conDiferentesValores_exitoso")
    void crear_conDiferentesValores_exitoso(String valor) {
        // Act
        NumeroSerie numeroSerie = new NumeroSerie(valor);

        // Assert
        assertThat(numeroSerie.getValor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("getValor_accesor_retornaValorCorrect")
    void getValor_accesor_retornaValorCorrect() {
        // Arrange
        String valorEsperado = "SERIE001";
        NumeroSerie numeroSerie = new NumeroSerie(valorEsperado);

        // Act
        String valorActual = numeroSerie.getValor();

        // Assert
        assertThat(valorActual).isEqualTo(valorEsperado);
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        NumeroSerie numeroSerie1 = new NumeroSerie("F001");
        NumeroSerie numeroSerie2 = new NumeroSerie("F001");

        // Act & Assert
        assertThat(numeroSerie1).isEqualTo(numeroSerie2);
    }

    @Test
    @DisplayName("equals_diferenteValor_retornaFalse")
    void equals_diferenteValor_retornaFalse() {
        // Arrange
        NumeroSerie numeroSerie1 = new NumeroSerie("F001");
        NumeroSerie numeroSerie2 = new NumeroSerie("F002");

        // Act & Assert
        assertThat(numeroSerie1).isNotEqualTo(numeroSerie2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        NumeroSerie numeroSerie = new NumeroSerie("F001");

        // Act & Assert
        assertThat(numeroSerie).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        NumeroSerie numeroSerie = new NumeroSerie("F001");
        Object otro = "F001";

        // Act & Assert
        assertThat(numeroSerie).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        NumeroSerie numeroSerie1 = new NumeroSerie("F001");
        NumeroSerie numeroSerie2 = new NumeroSerie("F001");

        // Act & Assert
        assertThat(numeroSerie1.hashCode()).isEqualTo(numeroSerie2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteValor_diferenteHash")
    void hashCode_diferenteValor_diferenteHash() {
        // Arrange
        NumeroSerie numeroSerie1 = new NumeroSerie("F001");
        NumeroSerie numeroSerie2 = new NumeroSerie("F002");

        // Act & Assert
        assertThat(numeroSerie1.hashCode()).isNotEqualTo(numeroSerie2.hashCode());
    }

    @Test
    @DisplayName("toString_retornaValor")
    void toString_retornaValor() {
        // Arrange
        String valor = "SERIE001";
        NumeroSerie numeroSerie = new NumeroSerie(valor);

        // Act
        String resultado = numeroSerie.toString();

        // Assert
        assertThat(resultado).isEqualTo(valor);
    }

    @Test
    @DisplayName("crear_conValorConCaracteresEspeciales_exitoso")
    void crear_conValorConCaracteresEspeciales_exitoso() {
        // Arrange
        String valor = "F-001-2024";

        // Act
        NumeroSerie numeroSerie = new NumeroSerie(valor);

        // Assert
        assertThat(numeroSerie.getValor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("crear_conValorConEspacios_exitoso")
    void crear_conValorConEspacios_exitoso() {
        // Arrange
        String valor = "F 001";

        // Act
        NumeroSerie numeroSerie = new NumeroSerie(valor);

        // Assert
        assertThat(numeroSerie.getValor()).isEqualTo(valor);
    }
}
