package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Anticipo Record Tests")
class AnticipoTest {

    @Test
    @DisplayName("Should create Anticipo with all fields")
    void testCrearAnticipo() {
        BigDecimal monto = new BigDecimal("500.00");
        Anticipo anticipo = new Anticipo(
            "04",
            "F001-123",
            "12",
            monto
        );

        assertEquals("04", anticipo.tipo());
        assertEquals("F001-123", anticipo.comprobanteSerieNumero());
        assertEquals("12", anticipo.comprobanteTipo());
        assertEquals(0, monto.compareTo(anticipo.monto()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal monto = new BigDecimal("500.00");
        Anticipo anticipo1 = new Anticipo("04", "F001-123", "12", monto);
        Anticipo anticipo2 = new Anticipo("04", "F001-123", "12", monto);

        assertEquals(anticipo1, anticipo2);
        assertEquals(anticipo1.hashCode(), anticipo2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct anticipos")
    void testDesigualdadPorDiferentesValores() {
        Anticipo anticipo1 = new Anticipo(
            "04",
            "F001-123",
            "12",
            new BigDecimal("500.00")
        );

        Anticipo anticipo2 = new Anticipo(
            "05",
            "F001-124",
            "12",
            new BigDecimal("600.00")
        );

        assertNotEquals(anticipo1, anticipo2);
    }

    @Test
    @DisplayName("Should support all standard anticipation types")
    void testTiposAnticipo() {
        String[] tipos = {"04", "05", "06"};
        for (String tipo : tipos) {
            Anticipo anticipo = new Anticipo(
                tipo,
                "F001-123",
                "12",
                new BigDecimal("500.00")
            );
            assertEquals(tipo, anticipo.tipo());
        }
    }

    @Test
    @DisplayName("Should preserve monto precision")
    void testPreservaMontoPrecision() {
        BigDecimal monto = new BigDecimal("123.456789");
        Anticipo anticipo = new Anticipo(
            "04",
            "F001-123",
            "12",
            monto
        );

        assertEquals(0, monto.compareTo(anticipo.monto()));
    }

    @Test
    @DisplayName("Should handle zero monto")
    void testMontoZero() {
        Anticipo anticipo = new Anticipo(
            "04",
            "F001-123",
            "12",
            BigDecimal.ZERO
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(anticipo.monto()));
    }

    @Test
    @DisplayName("Should handle large monto values")
    void testMontoGrande() {
        BigDecimal monto = new BigDecimal("999999999.99");
        Anticipo anticipo = new Anticipo(
            "04",
            "F001-123",
            "12",
            monto
        );

        assertEquals(0, monto.compareTo(anticipo.monto()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Anticipo anticipo = new Anticipo(
            "04",
            "F001-123",
            "12",
            new BigDecimal("500.00")
        );

        String toString = anticipo.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should preserve comprobanteSerieNumero format")
    void testPreservaComprobanteSerieNumero() {
        String serieNumero = "F001-12345";
        Anticipo anticipo = new Anticipo(
            "04",
            serieNumero,
            "12",
            new BigDecimal("500.00")
        );

        assertEquals(serieNumero, anticipo.comprobanteSerieNumero());
    }

    @Test
    @DisplayName("Should support null values for optional fields when needed")
    void testCamposNulos() {
        Anticipo anticipo = new Anticipo(
            "04",
            null,
            null,
            new BigDecimal("500.00")
        );

        assertNull(anticipo.comprobanteSerieNumero());
        assertNull(anticipo.comprobanteTipo());
        assertEquals("04", anticipo.tipo());
    }
}
