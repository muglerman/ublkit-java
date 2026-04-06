package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Descuento Record Tests")
class DescuentoTest {

    @Test
    @DisplayName("Should create Descuento with all fields")
    void testCrearDescuento() {
        BigDecimal monto = new BigDecimal("100.00");
        BigDecimal factor = new BigDecimal("0.10");
        BigDecimal montoBase = new BigDecimal("1000.00");

        Descuento descuento = new Descuento(
            "10",
            monto,
            factor,
            montoBase
        );

        assertEquals("10", descuento.tipo());
        assertEquals(0, monto.compareTo(descuento.monto()));
        assertEquals(0, factor.compareTo(descuento.factor()));
        assertEquals(0, montoBase.compareTo(descuento.montoBase()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal monto = new BigDecimal("100.00");
        BigDecimal factor = new BigDecimal("0.10");
        BigDecimal montoBase = new BigDecimal("1000.00");

        Descuento desc1 = new Descuento("10", monto, factor, montoBase);
        Descuento desc2 = new Descuento("10", monto, factor, montoBase);

        assertEquals(desc1, desc2);
        assertEquals(desc1.hashCode(), desc2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct descuentos")
    void testDesigualdadPorDiferentesValores() {
        Descuento desc1 = new Descuento(
            "10", new BigDecimal("100.00"),
            new BigDecimal("0.10"), new BigDecimal("1000.00")
        );

        Descuento desc2 = new Descuento(
            "20", new BigDecimal("200.00"),
            new BigDecimal("0.20"), new BigDecimal("2000.00")
        );

        assertNotEquals(desc1, desc2);
    }

    @Test
    @DisplayName("Should preserve monto precision")
    void testPreservaMontoPrecision() {
        BigDecimal monto = new BigDecimal("123.456789");
        Descuento descuento = new Descuento(
            "10", monto, new BigDecimal("0.10"), new BigDecimal("1000.00")
        );

        assertEquals(0, monto.compareTo(descuento.monto()));
    }

    @Test
    @DisplayName("Should preserve factor precision")
    void testPreservaFactorPrecision() {
        BigDecimal factor = new BigDecimal("0.125");
        Descuento descuento = new Descuento(
            "10", new BigDecimal("100.00"), factor, new BigDecimal("1000.00")
        );

        assertEquals(0, factor.compareTo(descuento.factor()));
    }

    @Test
    @DisplayName("Should preserve montoBase precision")
    void testPreservaMontoBasePrecision() {
        BigDecimal montoBase = new BigDecimal("1234.567890");
        Descuento descuento = new Descuento(
            "10", new BigDecimal("100.00"), new BigDecimal("0.10"), montoBase
        );

        assertEquals(0, montoBase.compareTo(descuento.montoBase()));
    }

    @Test
    @DisplayName("Should handle zero values")
    void testValoresZero() {
        Descuento descuento = new Descuento(
            "10", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(descuento.monto()));
        assertEquals(0, BigDecimal.ZERO.compareTo(descuento.factor()));
    }

    @Test
    @DisplayName("Should handle large values")
    void testValoresGrandes() {
        BigDecimal monto = new BigDecimal("999999999.99");
        BigDecimal montoBase = new BigDecimal("9999999999.99");

        Descuento descuento = new Descuento(
            "10", monto, new BigDecimal("0.10"), montoBase
        );

        assertEquals(0, monto.compareTo(descuento.monto()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Descuento descuento = new Descuento(
            "10", new BigDecimal("100.00"),
            new BigDecimal("0.10"), new BigDecimal("1000.00")
        );

        String toString = descuento.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values")
    void testValoresNulos() {
        Descuento descuento = new Descuento("10", null, null, null);

        assertEquals("10", descuento.tipo());
        assertNull(descuento.monto());
        assertNull(descuento.factor());
        assertNull(descuento.montoBase());
    }

    @Test
    @DisplayName("Should support various discount types")
    void testVariosTiposDescuento() {
        String[] tipos = {"01", "02", "10", "11", "20"};
        for (String tipo : tipos) {
            Descuento descuento = new Descuento(
                tipo, new BigDecimal("100.00"),
                new BigDecimal("0.10"), new BigDecimal("1000.00")
            );
            assertEquals(tipo, descuento.tipo());
        }
    }
}
