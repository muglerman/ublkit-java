package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Percepcion Record Tests")
class PercepcionTest {

    @Test
    @DisplayName("Should create Percepcion with all fields")
    void testCrearPercepcion() {
        BigDecimal montoBase = new BigDecimal("2000.00");
        BigDecimal porcentaje = new BigDecimal("0.03");
        BigDecimal monto = new BigDecimal("60.00");
        BigDecimal montoTotal = new BigDecimal("2060.00");

        Percepcion percepcion = new Percepcion(
            "02",
            montoBase,
            porcentaje,
            monto,
            montoTotal
        );

        assertEquals("02", percepcion.tipo());
        assertEquals(0, montoBase.compareTo(percepcion.montoBase()));
        assertEquals(0, porcentaje.compareTo(percepcion.porcentaje()));
        assertEquals(0, monto.compareTo(percepcion.monto()));
        assertEquals(0, montoTotal.compareTo(percepcion.montoTotal()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal montoBase = new BigDecimal("2000.00");
        BigDecimal porcentaje = new BigDecimal("0.03");
        BigDecimal monto = new BigDecimal("60.00");
        BigDecimal montoTotal = new BigDecimal("2060.00");

        Percepcion percepcion1 = new Percepcion(
            "02", montoBase, porcentaje, monto, montoTotal
        );

        Percepcion percepcion2 = new Percepcion(
            "02", montoBase, porcentaje, monto, montoTotal
        );

        assertEquals(percepcion1, percepcion2);
        assertEquals(percepcion1.hashCode(), percepcion2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct percepciones")
    void testDesigualdadPorDiferentesValores() {
        Percepcion percepcion1 = new Percepcion(
            "02",
            new BigDecimal("2000.00"),
            new BigDecimal("0.03"),
            new BigDecimal("60.00"),
            new BigDecimal("2060.00")
        );

        Percepcion percepcion2 = new Percepcion(
            "03",
            new BigDecimal("3000.00"),
            new BigDecimal("0.02"),
            new BigDecimal("60.00"),
            new BigDecimal("3060.00")
        );

        assertNotEquals(percepcion1, percepcion2);
    }

    @Test
    @DisplayName("Should support multiple perception types")
    void testDiferentesTiposPercepcion() {
        String[] tipos = {"01", "02", "03"};
        for (String tipo : tipos) {
            Percepcion percepcion = new Percepcion(
                tipo,
                new BigDecimal("2000.00"),
                new BigDecimal("0.03"),
                new BigDecimal("60.00"),
                new BigDecimal("2060.00")
            );
            assertEquals(tipo, percepcion.tipo());
        }
    }

    @Test
    @DisplayName("Should preserve montoBase precision")
    void testPreservaMontoBasePrecision() {
        BigDecimal montoBase = new BigDecimal("1234.5678");
        Percepcion percepcion = new Percepcion(
            "02", montoBase, new BigDecimal("0.03"),
            new BigDecimal("37.04"), new BigDecimal("1271.61")
        );

        assertEquals(0, montoBase.compareTo(percepcion.montoBase()));
    }

    @Test
    @DisplayName("Should preserve porcentaje precision")
    void testPreservaPorcentajePrecision() {
        BigDecimal porcentaje = new BigDecimal("0.035");
        Percepcion percepcion = new Percepcion(
            "02",
            new BigDecimal("2000.00"),
            porcentaje,
            new BigDecimal("70.00"),
            new BigDecimal("2070.00")
        );

        assertEquals(0, porcentaje.compareTo(percepcion.porcentaje()));
    }

    @Test
    @DisplayName("Should preserve monto precision")
    void testPreservaMontoPrecision() {
        BigDecimal monto = new BigDecimal("123.456789");
        Percepcion percepcion = new Percepcion(
            "02",
            new BigDecimal("2000.00"),
            new BigDecimal("0.03"),
            monto,
            new BigDecimal("2123.46")
        );

        assertEquals(0, monto.compareTo(percepcion.monto()));
    }

    @Test
    @DisplayName("Should handle zero montos")
    void testMontosZero() {
        Percepcion percepcion = new Percepcion(
            "02",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(percepcion.montoBase()));
        assertEquals(0, BigDecimal.ZERO.compareTo(percepcion.monto()));
    }

    @Test
    @DisplayName("Should handle large monto values")
    void testMontosGrandes() {
        BigDecimal montoBase = new BigDecimal("999999999.99");
        BigDecimal monto = new BigDecimal("29999999.99");
        BigDecimal montoTotal = new BigDecimal("1029999999.98");

        Percepcion percepcion = new Percepcion(
            "02", montoBase, new BigDecimal("0.03"), monto, montoTotal
        );

        assertEquals(0, montoBase.compareTo(percepcion.montoBase()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Percepcion percepcion = new Percepcion(
            "02",
            new BigDecimal("2000.00"),
            new BigDecimal("0.03"),
            new BigDecimal("60.00"),
            new BigDecimal("2060.00")
        );

        String toString = percepcion.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values for optional fields")
    void testCamposNulos() {
        Percepcion percepcion = new Percepcion(
            "02", null, null, null, null
        );

        assertEquals("02", percepcion.tipo());
        assertNull(percepcion.montoBase());
        assertNull(percepcion.porcentaje());
        assertNull(percepcion.monto());
        assertNull(percepcion.montoTotal());
    }

    @Test
    @DisplayName("Should support partial null values")
    void testCamposParcialesNulos() {
        Percepcion percepcion = new Percepcion(
            "02",
            new BigDecimal("2000.00"),
            null,
            null,
            null
        );

        assertEquals("02", percepcion.tipo());
        assertEquals(0, new BigDecimal("2000.00").compareTo(percepcion.montoBase()));
        assertNull(percepcion.porcentaje());
    }
}
