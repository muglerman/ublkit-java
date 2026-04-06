package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FormaDePago Record Tests")
class FormaDePagoTest {

    @Test
    @DisplayName("Should create FormaDePago for Contado")
    void testFormaContado() {
        BigDecimal total = new BigDecimal("1000.00");
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            total,
            null
        );

        assertEquals("Contado", formaDePago.tipo());
        assertEquals(0, total.compareTo(formaDePago.total()));
        assertNull(formaDePago.cuotas());
    }

    @Test
    @DisplayName("Should create FormaDePago for Credito with cuotas")
    void testFormaCredito() {
        BigDecimal total = new BigDecimal("1000.00");
        CuotaDePago cuota1 = new CuotaDePago(
            new BigDecimal("500.00"),
            java.time.LocalDate.of(2024, 12, 31)
        );
        CuotaDePago cuota2 = new CuotaDePago(
            new BigDecimal("500.00"),
            java.time.LocalDate.of(2025, 1, 31)
        );

        FormaDePago formaDePago = new FormaDePago(
            "Credito",
            total,
            java.util.Arrays.asList(cuota1, cuota2)
        );

        assertEquals("Credito", formaDePago.tipo());
        assertEquals(0, total.compareTo(formaDePago.total()));
        assertEquals(2, formaDePago.cuotas().size());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal total = new BigDecimal("1000.00");
        FormaDePago forma1 = new FormaDePago("Contado", total, null);
        FormaDePago forma2 = new FormaDePago("Contado", total, null);

        assertEquals(forma1, forma2);
        assertEquals(forma1.hashCode(), forma2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct payment methods")
    void testDesigualdadPorDiferentesValores() {
        FormaDePago forma1 = new FormaDePago(
            "Contado",
            new BigDecimal("1000.00"),
            null
        );

        FormaDePago forma2 = new FormaDePago(
            "Credito",
            new BigDecimal("2000.00"),
            null
        );

        assertNotEquals(forma1, forma2);
    }

    @Test
    @DisplayName("Should preserve total precision")
    void testPreservaTotalPrecision() {
        BigDecimal total = new BigDecimal("1234.567890");
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            total,
            null
        );

        assertEquals(0, total.compareTo(formaDePago.total()));
    }

    @Test
    @DisplayName("Should handle zero total")
    void testTotalZero() {
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            BigDecimal.ZERO,
            null
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(formaDePago.total()));
    }

    @Test
    @DisplayName("Should handle large total values")
    void testTotalGrande() {
        BigDecimal total = new BigDecimal("999999999.99");
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            total,
            null
        );

        assertEquals(0, total.compareTo(formaDePago.total()));
    }

    @Test
    @DisplayName("Should support multiple cuotas")
    void testMultiplesCuotas() {
        java.util.List<CuotaDePago> cuotas = java.util.Arrays.asList(
            new CuotaDePago(new BigDecimal("250.00"), java.time.LocalDate.of(2024, 12, 31)),
            new CuotaDePago(new BigDecimal("250.00"), java.time.LocalDate.of(2025, 1, 31)),
            new CuotaDePago(new BigDecimal("250.00"), java.time.LocalDate.of(2025, 2, 28)),
            new CuotaDePago(new BigDecimal("250.00"), java.time.LocalDate.of(2025, 3, 31))
        );

        FormaDePago formaDePago = new FormaDePago(
            "Credito",
            new BigDecimal("1000.00"),
            cuotas
        );

        assertEquals(4, formaDePago.cuotas().size());
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            new BigDecimal("1000.00"),
            null
        );

        String toString = formaDePago.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should support empty cuotas list")
    void testCuotasVacia() {
        FormaDePago formaDePago = new FormaDePago(
            "Credito",
            new BigDecimal("1000.00"),
            java.util.Collections.emptyList()
        );

        assertTrue(formaDePago.cuotas().isEmpty());
    }

    @Test
    @DisplayName("Should handle null total")
    void testTotalNulo() {
        FormaDePago formaDePago = new FormaDePago(
            "Contado",
            null,
            null
        );

        assertNull(formaDePago.total());
    }
}
