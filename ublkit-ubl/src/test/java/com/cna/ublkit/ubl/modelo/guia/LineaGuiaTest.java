package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LineaGuia Record Tests")
class LineaGuiaTest {

    @Test
    @DisplayName("Should create LineaGuia with basic fields")
    void testCrearLineaGuia() {
        LineaGuia linea = new LineaGuia(
            "1",
            "Producto A",
            "001",
            new java.math.BigDecimal("10.00"),
            "KGM"
        );

        assertEquals("1", linea.numeroLinea());
        assertEquals("Producto A", linea.descripcion());
        assertEquals("001", linea.codigoProducto());
        assertEquals(0, new java.math.BigDecimal("10.00").compareTo(linea.cantidad()));
        assertEquals("KGM", linea.unidadMedida());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        LineaGuia linea1 = new LineaGuia(
            "1", "Producto A", "001",
            new java.math.BigDecimal("10.00"), "KGM"
        );

        LineaGuia linea2 = new LineaGuia(
            "1", "Producto A", "001",
            new java.math.BigDecimal("10.00"), "KGM"
        );

        assertEquals(linea1, linea2);
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void testCamposNulos() {
        LineaGuia linea = new LineaGuia(
            "1", null, null, null, null
        );

        assertEquals("1", linea.numeroLinea());
        assertNull(linea.descripcion());
    }
}
