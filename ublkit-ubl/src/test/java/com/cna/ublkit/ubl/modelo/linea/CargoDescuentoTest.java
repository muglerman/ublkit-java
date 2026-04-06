package com.cna.ublkit.ubl.modelo.linea;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CargoDescuento Record Tests")
class CargoDescuentoTest {

    @Test
    @DisplayName("Should create CargoDescuento with all fields")
    void testCrearCargoDescuento() {
        BigDecimal monto = new BigDecimal("50.00");
        BigDecimal porcentaje = new BigDecimal("0.05");

        CargoDescuento cargo = new CargoDescuento(
            "10",
            monto,
            porcentaje,
            "F001-123"
        );

        assertEquals("10", cargo.tipo());
        assertEquals(0, monto.compareTo(cargo.monto()));
        assertEquals(0, porcentaje.compareTo(cargo.porcentaje()));
        assertEquals("F001-123", cargo.serieNumero());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        CargoDescuento cargo1 = new CargoDescuento(
            "10", new BigDecimal("50.00"), new BigDecimal("0.05"), "F001-123"
        );

        CargoDescuento cargo2 = new CargoDescuento(
            "10", new BigDecimal("50.00"), new BigDecimal("0.05"), "F001-123"
        );

        assertEquals(cargo1, cargo2);
        assertEquals(cargo1.hashCode(), cargo2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct cargos")
    void testDesigualdadPorDiferentesValores() {
        CargoDescuento cargo1 = new CargoDescuento(
            "10", new BigDecimal("50.00"), new BigDecimal("0.05"), "F001-123"
        );

        CargoDescuento cargo2 = new CargoDescuento(
            "20", new BigDecimal("100.00"), new BigDecimal("0.10"), "F001-124"
        );

        assertNotEquals(cargo1, cargo2);
    }

    @Test
    @DisplayName("Should support descuento type")
    void testTipoDescuento() {
        CargoDescuento descuento = new CargoDescuento(
            "01",
            new BigDecimal("50.00"),
            new BigDecimal("0.05"),
            null
        );

        assertEquals("01", descuento.tipo());
    }

    @Test
    @DisplayName("Should support cargo type")
    void testTipoCargo() {
        CargoDescuento cargo = new CargoDescuento(
            "04",
            new BigDecimal("50.00"),
            new BigDecimal("0.05"),
            null
        );

        assertEquals("04", cargo.tipo());
    }

    @Test
    @DisplayName("Should preserve monto precision")
    void testPreservaMontoPrecision() {
        BigDecimal monto = new BigDecimal("123.456789");
        CargoDescuento cargo = new CargoDescuento(
            "10", monto, new BigDecimal("0.05"), "F001-123"
        );

        assertEquals(0, monto.compareTo(cargo.monto()));
    }

    @Test
    @DisplayName("Should preserve porcentaje precision")
    void testPreservaPorcentajePrecision() {
        BigDecimal porcentaje = new BigDecimal("0.125");
        CargoDescuento cargo = new CargoDescuento(
            "10", new BigDecimal("50.00"), porcentaje, "F001-123"
        );

        assertEquals(0, porcentaje.compareTo(cargo.porcentaje()));
    }

    @Test
    @DisplayName("Should handle zero monto")
    void testMontoZero() {
        CargoDescuento cargo = new CargoDescuento(
            "10", BigDecimal.ZERO, new BigDecimal("0.05"), "F001-123"
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(cargo.monto()));
    }

    @Test
    @DisplayName("Should handle large values")
    void testValoresGrandes() {
        BigDecimal monto = new BigDecimal("999999999.99");
        CargoDescuento cargo = new CargoDescuento(
            "10", monto, new BigDecimal("0.99"), "F001-123"
        );

        assertEquals(0, monto.compareTo(cargo.monto()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        CargoDescuento cargo = new CargoDescuento(
            "10", new BigDecimal("50.00"), new BigDecimal("0.05"), "F001-123"
        );

        String toString = cargo.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values for optional fields")
    void testValoresNulos() {
        CargoDescuento cargo = new CargoDescuento(
            "10", null, null, null
        );

        assertEquals("10", cargo.tipo());
        assertNull(cargo.monto());
        assertNull(cargo.porcentaje());
        assertNull(cargo.serieNumero());
    }

    @Test
    @DisplayName("Should support various charge/discount types")
    void testVariosTipos() {
        String[] tipos = {"01", "02", "04", "05", "06", "10", "11", "20"};
        for (String tipo : tipos) {
            CargoDescuento cargo = new CargoDescuento(
                tipo, new BigDecimal("50.00"), new BigDecimal("0.05"), "F001-123"
            );
            assertEquals(tipo, cargo.tipo());
        }
    }
}
