package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CuotaDePago Record Tests")
class CuotaDePagoTest {

    @Test
    @DisplayName("Should create CuotaDePago with importe and fechaPago")
    void testCrearCuota() {
        BigDecimal importe = new BigDecimal("100.00");
        LocalDate fecha = LocalDate.of(2024, 12, 31);

        CuotaDePago cuota = new CuotaDePago(importe, fecha);

        assertEquals(0, importe.compareTo(cuota.importe()));
        assertEquals(fecha, cuota.fechaPago());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal importe = new BigDecimal("100.00");
        LocalDate fecha = LocalDate.of(2024, 12, 31);

        CuotaDePago cuota1 = new CuotaDePago(importe, fecha);
        CuotaDePago cuota2 = new CuotaDePago(importe, fecha);

        assertEquals(cuota1, cuota2);
        assertEquals(cuota1.hashCode(), cuota2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct cuotas")
    void testDesigualdadPorDiferentesValores() {
        CuotaDePago cuota1 = new CuotaDePago(
            new BigDecimal("100.00"),
            LocalDate.of(2024, 12, 31)
        );

        CuotaDePago cuota2 = new CuotaDePago(
            new BigDecimal("200.00"),
            LocalDate.of(2025, 1, 31)
        );

        assertNotEquals(cuota1, cuota2);
    }

    @Test
    @DisplayName("Should preserve importe precision")
    void testPreservaImportePrecision() {
        BigDecimal importe = new BigDecimal("123.456789");
        CuotaDePago cuota = new CuotaDePago(
            importe,
            LocalDate.of(2024, 12, 31)
        );

        assertEquals(0, importe.compareTo(cuota.importe()));
    }

    @Test
    @DisplayName("Should handle zero importe")
    void testImporteZero() {
        CuotaDePago cuota = new CuotaDePago(
            BigDecimal.ZERO,
            LocalDate.of(2024, 12, 31)
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(cuota.importe()));
    }

    @Test
    @DisplayName("Should handle large importe values")
    void testImporteGrande() {
        BigDecimal importe = new BigDecimal("999999999.99");
        CuotaDePago cuota = new CuotaDePago(
            importe,
            LocalDate.of(2024, 12, 31)
        );

        assertEquals(0, importe.compareTo(cuota.importe()));
    }

    @Test
    @DisplayName("Should preserve correct date")
    void testPreservaFechaPago() {
        LocalDate fecha = LocalDate.of(2024, 12, 31);
        CuotaDePago cuota = new CuotaDePago(
            new BigDecimal("100.00"),
            fecha
        );

        assertEquals(fecha, cuota.fechaPago());
    }

    @Test
    @DisplayName("Should support future dates")
    void testFechasFuturas() {
        LocalDate futureDate = LocalDate.of(2099, 12, 31);
        CuotaDePago cuota = new CuotaDePago(
            new BigDecimal("100.00"),
            futureDate
        );

        assertEquals(futureDate, cuota.fechaPago());
    }

    @Test
    @DisplayName("Should support past dates")
    void testFechasPasadas() {
        LocalDate pastDate = LocalDate.of(2000, 1, 1);
        CuotaDePago cuota = new CuotaDePago(
            new BigDecimal("100.00"),
            pastDate
        );

        assertEquals(pastDate, cuota.fechaPago());
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        CuotaDePago cuota = new CuotaDePago(
            new BigDecimal("100.00"),
            LocalDate.of(2024, 12, 31)
        );

        String toString = cuota.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null date")
    void testFechaNula() {
        CuotaDePago cuota = new CuotaDePago(
            new BigDecimal("100.00"),
            null
        );

        assertNull(cuota.fechaPago());
    }

    @Test
    @DisplayName("Should handle null importe")
    void testImporteNulo() {
        CuotaDePago cuota = new CuotaDePago(
            null,
            LocalDate.of(2024, 12, 31)
        );

        assertNull(cuota.importe());
    }
}
