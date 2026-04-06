package com.cna.ublkit.core.valor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dinero - Value Object Tests")
class DineroTest {

    @Test
    @DisplayName("crear_conMontoYMonedaValidos_exitoso")
    void crear_conMontoYMonedaValidos_exitoso() {
        // Arrange
        BigDecimal monto = BigDecimal.valueOf(100.50);
        Moneda moneda = new Moneda("USD");

        // Act
        Dinero dinero = new Dinero(monto, moneda);

        // Assert
        assertThat(dinero)
            .isNotNull();
        assertThat(dinero.monto())
            .isEqualByComparingTo(BigDecimal.valueOf(100.50));
        assertThat(dinero.moneda())
            .isEqualTo(moneda);
    }

    @Test
    @DisplayName("crear_conMontoNull_lanzaIllegalArgumentException")
    void crear_conMontoNull_lanzaIllegalArgumentException() {
        // Arrange
        Moneda moneda = new Moneda("USD");

        // Act & Assert
        assertThatThrownBy(() -> new Dinero(null, moneda))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El monto es obligatorio");
    }

    @Test
    @DisplayName("crear_conMonedaNull_lanzaIllegalArgumentException")
    void crear_conMonedaNull_lanzaIllegalArgumentException() {
        // Arrange
        BigDecimal monto = BigDecimal.valueOf(100.00);

        // Act & Assert
        assertThatThrownBy(() -> new Dinero(monto, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La moneda es obligatoria");
    }

    @Test
    @DisplayName("crear_redondeaAlDosDecimales_exitoso")
    void crear_redondeaAlDosDecimales_exitoso() {
        // Arrange
        BigDecimal montoConTresDecimales = BigDecimal.valueOf(100.555);
        Moneda moneda = new Moneda("PEN");

        // Act
        Dinero dinero = new Dinero(montoConTresDecimales, moneda);

        // Assert
        assertThat(dinero.monto())
            .isEqualTo(BigDecimal.valueOf(100.56));
    }

    @ParameterizedTest
    @CsvSource({
        "100.00, PEN",
        "0.01, USD",
        "1000000.99, EUR",
        "50.50, CHF"
    })
    @DisplayName("crear_conDiferentesMontos_exitoso")
    void crear_conDiferentesMontos_exitoso(String montoStr, String monedaCod) {
        // Arrange
        BigDecimal monto = new BigDecimal(montoStr);
        Moneda moneda = new Moneda(monedaCod);

        // Act
        Dinero dinero = new Dinero(monto, moneda);

        // Assert
        assertThat(dinero.monto()).isEqualTo(new BigDecimal(montoStr));
        assertThat(dinero.moneda()).isEqualTo(moneda);
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        Dinero dinero1 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));
        Dinero dinero2 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));

        // Act & Assert
        assertThat(dinero1).isEqualTo(dinero2);
    }

    @Test
    @DisplayName("equals_diferenteMonedaOMontoRetornaFalse")
    void equals_diferenteMonedaOMontoRetornaFalse() {
        // Arrange
        Dinero dinero1 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));
        Dinero dinero2 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("EUR"));
        Dinero dinero3 = new Dinero(BigDecimal.valueOf(200.00), new Moneda("USD"));

        // Act & Assert
        assertThat(dinero1).isNotEqualTo(dinero2);
        assertThat(dinero1).isNotEqualTo(dinero3);
    }

    @Test
    @DisplayName("equals_conObjetoNull_retornaFalse")
    void equals_conObjetoNull_retornaFalse() {
        // Arrange
        Dinero dinero = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));

        // Act & Assert
        assertThat(dinero).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        Dinero dinero = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));
        Object otro = "no es dinero";

        // Act & Assert
        assertThat(dinero).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        Dinero dinero1 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));
        Dinero dinero2 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));

        // Act & Assert
        assertThat(dinero1.hashCode()).isEqualTo(dinero2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteValor_diferenteHash")
    void hashCode_diferenteValor_diferenteHash() {
        // Arrange
        Dinero dinero1 = new Dinero(BigDecimal.valueOf(100.00), new Moneda("USD"));
        Dinero dinero2 = new Dinero(BigDecimal.valueOf(200.00), new Moneda("USD"));

        // Act & Assert
        assertThat(dinero1.hashCode()).isNotEqualTo(dinero2.hashCode());
    }

    @Test
    @DisplayName("monto_accesor_retornaValorCorrect")
    void monto_accesor_retornaValorCorrect() {
        // Arrange
        BigDecimal montoEsperado = BigDecimal.valueOf(125.75);
        Dinero dinero = new Dinero(montoEsperado, new Moneda("PEN"));

        // Act
        BigDecimal montoActual = dinero.monto();

        // Assert
        assertThat(montoActual).isEqualTo(montoEsperado);
    }

    @Test
    @DisplayName("moneda_accesor_retornaValorCorrect")
    void moneda_accesor_retornaValorCorrect() {
        // Arrange
        Moneda monedaEsperada = new Moneda("EUR");
        Dinero dinero = new Dinero(BigDecimal.valueOf(100.00), monedaEsperada);

        // Act
        Moneda monedaActual = dinero.moneda();

        // Assert
        assertThat(monedaActual).isEqualTo(monedaEsperada);
    }

    @Test
    @DisplayName("crear_conMontoCero_exitoso")
    void crear_conMontoCero_exitoso() {
        // Arrange
        BigDecimal montoCero = BigDecimal.ZERO;
        Moneda moneda = new Moneda("USD");

        // Act
        Dinero dinero = new Dinero(montoCero, moneda);

        // Assert
        assertThat(dinero.monto()).isEqualByComparingTo(BigDecimal.valueOf(0.00));
    }

    @Test
    @DisplayName("crear_conMontoNegativo_exitoso")
    void crear_conMontoNegativo_exitoso() {
        // Arrange
        BigDecimal montoNegativo = BigDecimal.valueOf(-50.00);
        Moneda moneda = new Moneda("USD");

        // Act
        Dinero dinero = new Dinero(montoNegativo, moneda);

        // Assert
        assertThat(dinero.monto()).isEqualByComparingTo(BigDecimal.valueOf(-50.00));
    }
}
