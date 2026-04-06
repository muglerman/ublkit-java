package com.cna.ublkit.core.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DisplayName("TipoCambio - Record Tests")
class TipoCambioTest {

    @Test
    @DisplayName("crear_conFechaYValorValidos_exitoso")
    void crear_conFechaYValorValidos_exitoso() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        BigDecimal valor = BigDecimal.valueOf(3.85);

        // Act
        TipoCambio tipoCambio = new TipoCambio(fecha, valor);

        // Assert
        assertThat(tipoCambio.fecha()).isEqualTo(fecha);
        assertThat(tipoCambio.valor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("crear_conFechaNull_exitoso")
    void crear_conFechaNull_exitoso() {
        // Arrange
        BigDecimal valor = BigDecimal.valueOf(3.85);

        // Act
        TipoCambio tipoCambio = new TipoCambio(null, valor);

        // Assert
        assertThat(tipoCambio.fecha()).isNull();
        assertThat(tipoCambio.valor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("crear_conValorNull_exitoso")
    void crear_conValorNull_exitoso() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);

        // Act
        TipoCambio tipoCambio = new TipoCambio(fecha, null);

        // Assert
        assertThat(tipoCambio.fecha()).isEqualTo(fecha);
        assertThat(tipoCambio.valor()).isNull();
    }

    @Test
    @DisplayName("crear_conAmboNull_exitoso")
    void crear_conAmboNull_exitoso() {
        // Act
        TipoCambio tipoCambio = new TipoCambio(null, null);

        // Assert
        assertThat(tipoCambio.fecha()).isNull();
        assertThat(tipoCambio.valor()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-15, 3.85",
        "2024-02-29, 3.90",
        "2024-12-31, 3.75",
        "2023-01-01, 3.80"
    })
    @DisplayName("crear_conDiferentesFechasYValores_exitoso")
    void crear_conDiferentesFechasYValores_exitoso(String fechaStr, String valorStr) {
        // Arrange
        LocalDate fecha = LocalDate.parse(fechaStr);
        BigDecimal valor = new BigDecimal(valorStr);

        // Act
        TipoCambio tipoCambio = new TipoCambio(fecha, valor);

        // Assert
        assertThat(tipoCambio.fecha()).isEqualTo(fecha);
        assertThat(tipoCambio.valor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("fecha_accesor_retornaValorCorrect")
    void fecha_accesor_retornaValorCorrect() {
        // Arrange
        LocalDate fechaEsperada = LocalDate.of(2024, 6, 15);
        TipoCambio tipoCambio = new TipoCambio(fechaEsperada, BigDecimal.valueOf(3.85));

        // Act
        LocalDate fechaActual = tipoCambio.fecha();

        // Assert
        assertThat(fechaActual).isEqualTo(fechaEsperada);
    }

    @Test
    @DisplayName("valor_accesor_retornaValorCorrect")
    void valor_accesor_retornaValorCorrect() {
        // Arrange
        BigDecimal valorEsperado = BigDecimal.valueOf(4.25);
        TipoCambio tipoCambio = new TipoCambio(LocalDate.of(2024, 1, 15), valorEsperado);

        // Act
        BigDecimal valorActual = tipoCambio.valor();

        // Assert
        assertThat(valorActual).isEqualTo(valorEsperado);
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        BigDecimal valor = BigDecimal.valueOf(3.85);
        TipoCambio tipoCambio1 = new TipoCambio(fecha, valor);
        TipoCambio tipoCambio2 = new TipoCambio(fecha, valor);

        // Act & Assert
        assertThat(tipoCambio1).isEqualTo(tipoCambio2);
    }

    @Test
    @DisplayName("equals_diferenteFecha_retornaFalse")
    void equals_diferenteFecha_retornaFalse() {
        // Arrange
        BigDecimal valor = BigDecimal.valueOf(3.85);
        TipoCambio tipoCambio1 = new TipoCambio(LocalDate.of(2024, 1, 15), valor);
        TipoCambio tipoCambio2 = new TipoCambio(LocalDate.of(2024, 1, 16), valor);

        // Act & Assert
        assertThat(tipoCambio1).isNotEqualTo(tipoCambio2);
    }

    @Test
    @DisplayName("equals_diferenteValor_retornaFalse")
    void equals_diferenteValor_retornaFalse() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        TipoCambio tipoCambio1 = new TipoCambio(fecha, BigDecimal.valueOf(3.85));
        TipoCambio tipoCambio2 = new TipoCambio(fecha, BigDecimal.valueOf(3.90));

        // Act & Assert
        assertThat(tipoCambio1).isNotEqualTo(tipoCambio2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        TipoCambio tipoCambio = new TipoCambio(LocalDate.of(2024, 1, 15), BigDecimal.valueOf(3.85));

        // Act & Assert
        assertThat(tipoCambio).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        TipoCambio tipoCambio = new TipoCambio(LocalDate.of(2024, 1, 15), BigDecimal.valueOf(3.85));
        Object otro = LocalDate.of(2024, 1, 15);

        // Act & Assert
        assertThat(tipoCambio).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        BigDecimal valor = BigDecimal.valueOf(3.85);
        TipoCambio tipoCambio1 = new TipoCambio(fecha, valor);
        TipoCambio tipoCambio2 = new TipoCambio(fecha, valor);

        // Act & Assert
        assertThat(tipoCambio1.hashCode()).isEqualTo(tipoCambio2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteValor_diferenteHash")
    void hashCode_diferenteValor_diferenteHash() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        TipoCambio tipoCambio1 = new TipoCambio(fecha, BigDecimal.valueOf(3.85));
        TipoCambio tipoCambio2 = new TipoCambio(fecha, BigDecimal.valueOf(3.90));

        // Act & Assert
        assertThat(tipoCambio1.hashCode()).isNotEqualTo(tipoCambio2.hashCode());
    }

    @Test
    @DisplayName("toString_representacionValida")
    void toString_representacionValida() {
        // Arrange
        TipoCambio tipoCambio = new TipoCambio(LocalDate.of(2024, 1, 15), BigDecimal.valueOf(3.85));

        // Act
        String resultado = tipoCambio.toString();

        // Assert
        assertThat(resultado)
            .contains("TipoCambio")
            .contains("2024-01-15")
            .contains("3.85");
    }

    @Test
    @DisplayName("crear_conValorCero_exitoso")
    void crear_conValorCero_exitoso() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        BigDecimal valor = BigDecimal.ZERO;

        // Act
        TipoCambio tipoCambio = new TipoCambio(fecha, valor);

        // Assert
        assertThat(tipoCambio.valor()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("crear_conValorNegativo_exitoso")
    void crear_conValorNegativo_exitoso() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        BigDecimal valor = BigDecimal.valueOf(-1.50);

        // Act
        TipoCambio tipoCambio = new TipoCambio(fecha, valor);

        // Assert
        assertThat(tipoCambio.valor()).isEqualTo(BigDecimal.valueOf(-1.50));
    }
}
