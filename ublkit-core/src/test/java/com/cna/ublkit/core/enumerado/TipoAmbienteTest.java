package com.cna.ublkit.core.enumerado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TipoAmbiente - Enum Tests")
class TipoAmbienteTest {

    @Test
    @DisplayName("enum_tieneValorBETA")
    void enum_tieneValorBETA() {
        // Act
        TipoAmbiente ambiente = TipoAmbiente.BETA;

        // Assert
        assertThat(ambiente).isNotNull();
        assertThat(ambiente.name()).isEqualTo("BETA");
    }

    @Test
    @DisplayName("enum_tieneValorPRODUCCION")
    void enum_tieneValorPRODUCCION() {
        // Act
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        // Assert
        assertThat(ambiente).isNotNull();
        assertThat(ambiente.name()).isEqualTo("PRODUCCION");
    }

    @Test
    @DisplayName("valueOf_conNombreBETA_retornaBETA")
    void valueOf_conNombreBETA_retornaBETA() {
        // Act
        TipoAmbiente ambiente = TipoAmbiente.valueOf("BETA");

        // Assert
        assertThat(ambiente).isEqualTo(TipoAmbiente.BETA);
    }

    @Test
    @DisplayName("valueOf_conNombrePRODUCCION_retornaPRODUCCION")
    void valueOf_conNombrePRODUCCION_retornaPRODUCCION() {
        // Act
        TipoAmbiente ambiente = TipoAmbiente.valueOf("PRODUCCION");

        // Assert
        assertThat(ambiente).isEqualTo(TipoAmbiente.PRODUCCION);
    }

    @Test
    @DisplayName("values_retornaTodosLosValores")
    void values_retornaTodosLosValores() {
        // Act
        TipoAmbiente[] valores = TipoAmbiente.values();

        // Assert
        assertThat(valores)
            .isNotEmpty()
            .contains(TipoAmbiente.BETA, TipoAmbiente.PRODUCCION);
    }

    @ParameterizedTest
    @ValueSource(strings = {"BETA", "PRODUCCION"})
    @DisplayName("valueOf_conTodosLosNombres_exitoso")
    void valueOf_conTodosLosNombres_exitoso(String nombre) {
        // Act
        TipoAmbiente ambiente = TipoAmbiente.valueOf(nombre);

        // Assert
        assertThat(ambiente).isNotNull();
        assertThat(ambiente.name()).isEqualTo(nombre);
    }

    @Test
    @DisplayName("valueOf_conNombreInvalido_lanzaIllegalArgumentException")
    void valueOf_conNombreInvalido_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> TipoAmbiente.valueOf("DEVELOPMENT"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("equals_mismosValores_retornaTrue")
    void equals_mismosValores_retornaTrue() {
        // Act & Assert
        assertThat(TipoAmbiente.BETA).isEqualTo(TipoAmbiente.BETA);
        assertThat(TipoAmbiente.PRODUCCION).isEqualTo(TipoAmbiente.PRODUCCION);
    }

    @Test
    @DisplayName("equals_diferentesValores_retornaFalse")
    void equals_diferentesValores_retornaFalse() {
        // Act & Assert
        assertThat(TipoAmbiente.BETA).isNotEqualTo(TipoAmbiente.PRODUCCION);
    }

    @Test
    @DisplayName("toString_retornaValorCorrecto")
    void toString_retornaValorCorrecto() {
        // Act
        String toStringBeta = TipoAmbiente.BETA.toString();
        String toStringProduccion = TipoAmbiente.PRODUCCION.toString();

        // Assert
        assertThat(toStringBeta).isEqualTo("BETA");
        assertThat(toStringProduccion).isEqualTo("PRODUCCION");
    }

    @Test
    @DisplayName("hashCode_mismosValores_mismoHash")
    void hashCode_mismosValores_mismoHash() {
        // Act & Assert
        assertThat(TipoAmbiente.BETA.hashCode())
            .isEqualTo(TipoAmbiente.BETA.hashCode());
        assertThat(TipoAmbiente.PRODUCCION.hashCode())
            .isEqualTo(TipoAmbiente.PRODUCCION.hashCode());
    }

    @Test
    @DisplayName("ordinal_retornaIndiceCorrect")
    void ordinal_retornaIndiceCorrect() {
        // Act & Assert
        assertThat(TipoAmbiente.BETA.ordinal()).isZero();
        assertThat(TipoAmbiente.PRODUCCION.ordinal()).isOne();
    }

    @Test
    @DisplayName("compareTo_retornaOrdenCorrect")
    void compareTo_retornaOrdenCorrect() {
        // Act
        int comparacion = TipoAmbiente.BETA.compareTo(TipoAmbiente.PRODUCCION);

        // Assert
        assertThat(comparacion).isNegative();
    }
}
