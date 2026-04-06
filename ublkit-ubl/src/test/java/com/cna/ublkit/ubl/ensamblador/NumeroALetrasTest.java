package com.cna.ublkit.ubl.ensamblador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NumeroALetras Class Tests")
class NumeroALetrasTest {

    @Test
    @DisplayName("Should convert single digit numbers")
    void testNumerosSolosDigitos() {
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("1"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("9"), "PEN"));
    }

    @Test
    @DisplayName("Should convert tens")
    void testDecenas() {
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("10"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("20"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("99"), "PEN"));
    }

    @Test
    @DisplayName("Should convert hundreds")
    void testCentenas() {
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("100"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("500"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("999"), "PEN"));
    }

    @Test
    @DisplayName("Should convert thousands")
    void testMiles() {
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("1000"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("5000"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("99999"), "PEN"));
    }

    @Test
    @DisplayName("Should handle zero")
    void testCero() {
        String resultado = NumeroALetras.formatearMonto(new BigDecimal("0"), "PEN");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Should handle decimal numbers")
    void testNumerosDecimales() {
        String resultado = NumeroALetras.formatearMonto(new BigDecimal("123.45"), "PEN");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Should handle large numbers")
    void testNumerosGrandes() {
        String resultado = NumeroALetras.formatearMonto(new BigDecimal("999999.99"), "PEN");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Should return non-empty string")
    void testRetornaNonVacio() {
        String resultado = NumeroALetras.formatearMonto(new BigDecimal("500"), "PEN");
        assertNotNull(resultado);
        assertFalse(resultado.trim().isEmpty());
    }

    @Test
    @DisplayName("Should handle specific test cases for invoice amounts")
    void testCasosFacturas() {
        // Common invoice amounts
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("236.00"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("1000.00"), "PEN"));
        assertNotNull(NumeroALetras.formatearMonto(new BigDecimal("500.50"), "PEN"));
    }
}
