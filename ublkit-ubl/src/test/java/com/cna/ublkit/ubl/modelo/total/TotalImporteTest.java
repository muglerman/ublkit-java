package com.cna.ublkit.ubl.modelo.total;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TotalImporte Record Tests")
class TotalImporteTest {

    @Test
    @DisplayName("Should create TotalImporte with all fields")
    void testCrearTotalImporte() {
        BigDecimal importe = new BigDecimal("236.00");
        BigDecimal importeSinImpuestos = new BigDecimal("200.00");
        BigDecimal importeConImpuestos = new BigDecimal("236.00");

        TotalImporte total = new TotalImporte(
            importe,
            importeSinImpuestos,
            importeConImpuestos,
            null,
            null
        );

        assertEquals(0, importe.compareTo(total.importe()));
        assertEquals(0, importeSinImpuestos.compareTo(total.importeSinImpuestos()));
        assertEquals(0, importeConImpuestos.compareTo(total.importeConImpuestos()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal importe = new BigDecimal("236.00");
        BigDecimal importeSinImpuestos = new BigDecimal("200.00");
        BigDecimal importeConImpuestos = new BigDecimal("236.00");

        TotalImporte total1 = new TotalImporte(
            importe, importeSinImpuestos, importeConImpuestos, null, null
        );

        TotalImporte total2 = new TotalImporte(
            importe, importeSinImpuestos, importeConImpuestos, null, null
        );

        assertEquals(total1, total2);
        assertEquals(total1.hashCode(), total2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct totales")
    void testDesigualdadPorDiferentesValores() {
        TotalImporte total1 = new TotalImporte(
            new BigDecimal("236.00"),
            new BigDecimal("200.00"),
            new BigDecimal("236.00"),
            null,
            null
        );

        TotalImporte total2 = new TotalImporte(
            new BigDecimal("400.00"),
            new BigDecimal("300.00"),
            new BigDecimal("400.00"),
            null,
            null
        );

        assertNotEquals(total1, total2);
    }

    @Test
    @DisplayName("Should preserve importe precision")
    void testPreservaImportePrecision() {
        BigDecimal importe = new BigDecimal("123.456789");
        TotalImporte total = new TotalImporte(
            importe, new BigDecimal("100.00"),
            new BigDecimal("123.45"), null, null
        );

        assertEquals(0, importe.compareTo(total.importe()));
    }

    @Test
    @DisplayName("Should support anticipos (descuentos por prepago)")
    void testConAnticipos() {
        BigDecimal anticipos = new BigDecimal("100.00");
        TotalImporte total = new TotalImporte(
            new BigDecimal("236.00"),
            new BigDecimal("200.00"),
            new BigDecimal("236.00"),
            anticipos,
            null
        );

        assertEquals(0, anticipos.compareTo(total.anticipos()));
    }

    @Test
    @DisplayName("Should support descuentos globales")
    void testConDescuentos() {
        BigDecimal descuentos = new BigDecimal("50.00");
        TotalImporte total = new TotalImporte(
            new BigDecimal("186.00"),
            new BigDecimal("200.00"),
            new BigDecimal("236.00"),
            null,
            descuentos
        );

        assertEquals(0, descuentos.compareTo(total.descuentos()));
    }

    @Test
    @DisplayName("Should handle zero values")
    void testValoresZero() {
        TotalImporte total = new TotalImporte(
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            null,
            null
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(total.importe()));
    }

    @Test
    @DisplayName("Should handle large values")
    void testValoresGrandes() {
        BigDecimal importe = new BigDecimal("999999999.99");
        TotalImporte total = new TotalImporte(
            importe,
            new BigDecimal("800000000.00"),
            importe,
            null,
            null
        );

        assertEquals(0, importe.compareTo(total.importe()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("236.00"),
            new BigDecimal("200.00"),
            new BigDecimal("236.00"),
            null,
            null
        );

        String toString = total.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void testCamposNulos() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("236.00"),
            new BigDecimal("200.00"),
            new BigDecimal("236.00"),
            null,
            null
        );

        assertNull(total.anticipos());
        assertNull(total.descuentos());
    }

    @Test
    @DisplayName("Should support all monetary amounts")
    void testTodosLosMontos() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("300.00"),
            new BigDecimal("250.00"),
            new BigDecimal("295.00"),
            new BigDecimal("50.00"),
            new BigDecimal("5.00")
        );

        assertNotNull(total.anticipos());
        assertNotNull(total.descuentos());
    }
}
