package com.cna.ublkit.core.enumerado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProtocoloTransporte - Enum Tests")
class ProtocoloTransporteTest {

    @Test
    @DisplayName("enum_tieneValorSOAP")
    void enum_tieneValorSOAP() {
        // Act
        ProtocoloTransporte protocolo = ProtocoloTransporte.SOAP;

        // Assert
        assertThat(protocolo).isNotNull();
        assertThat(protocolo.name()).isEqualTo("SOAP");
    }

    @Test
    @DisplayName("enum_tieneValorREST")
    void enum_tieneValorREST() {
        // Act
        ProtocoloTransporte protocolo = ProtocoloTransporte.REST;

        // Assert
        assertThat(protocolo).isNotNull();
        assertThat(protocolo.name()).isEqualTo("REST");
    }

    @Test
    @DisplayName("valueOf_conNombreSOAP_retornaSOAP")
    void valueOf_conNombreSOAP_retornaSOAP() {
        // Act
        ProtocoloTransporte protocolo = ProtocoloTransporte.valueOf("SOAP");

        // Assert
        assertThat(protocolo).isEqualTo(ProtocoloTransporte.SOAP);
    }

    @Test
    @DisplayName("valueOf_conNombreREST_retornaREST")
    void valueOf_conNombreREST_retornaREST() {
        // Act
        ProtocoloTransporte protocolo = ProtocoloTransporte.valueOf("REST");

        // Assert
        assertThat(protocolo).isEqualTo(ProtocoloTransporte.REST);
    }

    @Test
    @DisplayName("values_retornaTodosLosValores")
    void values_retornaTodosLosValores() {
        // Act
        ProtocoloTransporte[] valores = ProtocoloTransporte.values();

        // Assert
        assertThat(valores)
            .isNotEmpty()
            .contains(ProtocoloTransporte.SOAP, ProtocoloTransporte.REST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOAP", "REST"})
    @DisplayName("valueOf_conTodosLosNombres_exitoso")
    void valueOf_conTodosLosNombres_exitoso(String nombre) {
        // Act
        ProtocoloTransporte protocolo = ProtocoloTransporte.valueOf(nombre);

        // Assert
        assertThat(protocolo).isNotNull();
        assertThat(protocolo.name()).isEqualTo(nombre);
    }

    @Test
    @DisplayName("valueOf_conNombreInvalido_lanzaIllegalArgumentException")
    void valueOf_conNombreInvalido_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> ProtocoloTransporte.valueOf("INVALID"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("equals_mismosValores_retornaTrue")
    void equals_mismosValores_retornaTrue() {
        // Act & Assert
        assertThat(ProtocoloTransporte.SOAP).isEqualTo(ProtocoloTransporte.SOAP);
        assertThat(ProtocoloTransporte.REST).isEqualTo(ProtocoloTransporte.REST);
    }

    @Test
    @DisplayName("equals_diferentesValores_retornaFalse")
    void equals_diferentesValores_retornaFalse() {
        // Act & Assert
        assertThat(ProtocoloTransporte.SOAP).isNotEqualTo(ProtocoloTransporte.REST);
    }

    @Test
    @DisplayName("toString_retornaValorCorrecto")
    void toString_retornaValorCorrecto() {
        // Act
        String toString1 = ProtocoloTransporte.SOAP.toString();
        String toString2 = ProtocoloTransporte.REST.toString();

        // Assert
        assertThat(toString1).isEqualTo("SOAP");
        assertThat(toString2).isEqualTo("REST");
    }

    @Test
    @DisplayName("hashCode_mismosValores_mismoHash")
    void hashCode_mismosValores_mismoHash() {
        // Act & Assert
        assertThat(ProtocoloTransporte.SOAP.hashCode())
            .isEqualTo(ProtocoloTransporte.SOAP.hashCode());
    }

    @Test
    @DisplayName("ordinal_retornaIndiceCorrect")
    void ordinal_retornaIndiceCorrect() {
        // Act & Assert
        assertThat(ProtocoloTransporte.SOAP.ordinal()).isZero();
        assertThat(ProtocoloTransporte.REST.ordinal()).isOne();
    }
}
