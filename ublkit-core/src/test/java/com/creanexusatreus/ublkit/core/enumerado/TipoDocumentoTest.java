package com.cna.ublkit.core.enumerado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TipoDocumento - Enum Tests")
class TipoDocumentoTest {

    @Test
    @DisplayName("enum_tieneValorFACTURA")
    void enum_tieneValorFACTURA() {
        // Act
        TipoDocumento tipo = TipoDocumento.FACTURA;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("FACTURA");
    }

    @Test
    @DisplayName("enum_tieneValorBOLETA")
    void enum_tieneValorBOLETA() {
        // Act
        TipoDocumento tipo = TipoDocumento.BOLETA;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("BOLETA");
    }

    @Test
    @DisplayName("enum_tieneValorNOTA_CREDITO")
    void enum_tieneValorNOTA_CREDITO() {
        // Act
        TipoDocumento tipo = TipoDocumento.NOTA_CREDITO;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("NOTA_CREDITO");
    }

    @Test
    @DisplayName("enum_tieneValorNOTA_DEBITO")
    void enum_tieneValorNOTA_DEBITO() {
        // Act
        TipoDocumento tipo = TipoDocumento.NOTA_DEBITO;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("NOTA_DEBITO");
    }

    @Test
    @DisplayName("enum_tieneValorGUIA_REMISION_REMITENTE")
    void enum_tieneValorGUIA_REMISION_REMITENTE() {
        // Act
        TipoDocumento tipo = TipoDocumento.GUIA_REMISION_REMITENTE;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("GUIA_REMISION_REMITENTE");
    }

    @Test
    @DisplayName("enum_tieneValorGUIA_REMISION_TRANSPORTISTA")
    void enum_tieneValorGUIA_REMISION_TRANSPORTISTA() {
        // Act
        TipoDocumento tipo = TipoDocumento.GUIA_REMISION_TRANSPORTISTA;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("GUIA_REMISION_TRANSPORTISTA");
    }

    @Test
    @DisplayName("enum_tieneValorRESUMEN_DIARIO")
    void enum_tieneValorRESUMEN_DIARIO() {
        // Act
        TipoDocumento tipo = TipoDocumento.RESUMEN_DIARIO;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("RESUMEN_DIARIO");
    }

    @Test
    @DisplayName("enum_tieneValorCOMUNICACION_BAJA")
    void enum_tieneValorCOMUNICACION_BAJA() {
        // Act
        TipoDocumento tipo = TipoDocumento.COMUNICACION_BAJA;

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo("COMUNICACION_BAJA");
    }

    @Test
    @DisplayName("values_retornaTodosLosOchoValores")
    void values_retornaTodosLosOchoValores() {
        // Act
        TipoDocumento[] valores = TipoDocumento.values();

        // Assert
        assertThat(valores)
            .isNotEmpty()
            .contains(
                TipoDocumento.FACTURA,
                TipoDocumento.BOLETA,
                TipoDocumento.NOTA_CREDITO,
                TipoDocumento.NOTA_DEBITO,
                TipoDocumento.GUIA_REMISION_REMITENTE,
                TipoDocumento.GUIA_REMISION_TRANSPORTISTA,
                TipoDocumento.RESUMEN_DIARIO,
                TipoDocumento.COMUNICACION_BAJA
            );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "FACTURA", "BOLETA", "NOTA_CREDITO", "NOTA_DEBITO",
        "GUIA_REMISION_REMITENTE", "GUIA_REMISION_TRANSPORTISTA",
        "RESUMEN_DIARIO", "COMUNICACION_BAJA"
    })
    @DisplayName("valueOf_conTodosLosNombres_exitoso")
    void valueOf_conTodosLosNombres_exitoso(String nombre) {
        // Act
        TipoDocumento tipo = TipoDocumento.valueOf(nombre);

        // Assert
        assertThat(tipo).isNotNull();
        assertThat(tipo.name()).isEqualTo(nombre);
    }

    @Test
    @DisplayName("valueOf_conNombreInvalido_lanzaIllegalArgumentException")
    void valueOf_conNombreInvalido_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> TipoDocumento.valueOf("DOCUMENTO_INVALIDO"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("equals_mismosValores_retornaTrue")
    void equals_mismosValores_retornaTrue() {
        // Act & Assert
        assertThat(TipoDocumento.FACTURA).isEqualTo(TipoDocumento.FACTURA);
        assertThat(TipoDocumento.BOLETA).isEqualTo(TipoDocumento.BOLETA);
    }

    @Test
    @DisplayName("equals_diferentesValores_retornaFalse")
    void equals_diferentesValores_retornaFalse() {
        // Act & Assert
        assertThat(TipoDocumento.FACTURA).isNotEqualTo(TipoDocumento.BOLETA);
        assertThat(TipoDocumento.NOTA_CREDITO).isNotEqualTo(TipoDocumento.NOTA_DEBITO);
    }

    @Test
    @DisplayName("toString_retornaValorCorrecto")
    void toString_retornaValorCorrecto() {
        // Act
        String factura = TipoDocumento.FACTURA.toString();
        String boleta = TipoDocumento.BOLETA.toString();
        String notaCredito = TipoDocumento.NOTA_CREDITO.toString();

        // Assert
        assertThat(factura).isEqualTo("FACTURA");
        assertThat(boleta).isEqualTo("BOLETA");
        assertThat(notaCredito).isEqualTo("NOTA_CREDITO");
    }

    @Test
    @DisplayName("hashCode_mismosValores_mismoHash")
    void hashCode_mismosValores_mismoHash() {
        // Act & Assert
        assertThat(TipoDocumento.FACTURA.hashCode())
            .isEqualTo(TipoDocumento.FACTURA.hashCode());
        assertThat(TipoDocumento.BOLETA.hashCode())
            .isEqualTo(TipoDocumento.BOLETA.hashCode());
    }

    @Test
    @DisplayName("ordinal_retornaIndiceCorrect")
    void ordinal_retornaIndiceCorrect() {
        // Act & Assert
        assertThat(TipoDocumento.FACTURA.ordinal()).isZero();
        assertThat(TipoDocumento.BOLETA.ordinal()).isOne();
        assertThat(TipoDocumento.NOTA_CREDITO.ordinal()).isEqualTo(2);
        assertThat(TipoDocumento.COMUNICACION_BAJA.ordinal()).isEqualTo(7);
    }

    @Test
    @DisplayName("compareTo_retornaOrdenCorrect")
    void compareTo_retornaOrdenCorrect() {
        // Act
        int comparacion = TipoDocumento.FACTURA.compareTo(TipoDocumento.BOLETA);

        // Assert
        assertThat(comparacion).isNegative();
    }

    @Test
    @DisplayName("compareTo_mismosValores_retornaCero")
    void compareTo_mismosValores_retornaCero() {
        // Act
        int comparacion = TipoDocumento.FACTURA.compareTo(TipoDocumento.FACTURA);

        // Assert
        assertThat(comparacion).isZero();
    }
}
