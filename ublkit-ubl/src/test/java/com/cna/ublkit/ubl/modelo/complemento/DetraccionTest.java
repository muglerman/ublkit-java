package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Detraccion Record Tests")
class DetraccionTest {

    @Test
    @DisplayName("Should create Detraccion with all fields")
    void testCrearDetraccion() {
        BigDecimal porcentaje = new BigDecimal("0.12");
        BigDecimal monto = new BigDecimal("240.00");

        Detraccion detraccion = new Detraccion(
            "01",
            "123-456-789012",
            "015",
            porcentaje,
            monto
        );

        assertEquals("01", detraccion.medioDePago());
        assertEquals("123-456-789012", detraccion.cuentaBancaria());
        assertEquals("015", detraccion.tipoBienDetraido());
        assertEquals(0, porcentaje.compareTo(detraccion.porcentaje()));
        assertEquals(0, monto.compareTo(detraccion.monto()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        BigDecimal porcentaje = new BigDecimal("0.12");
        BigDecimal monto = new BigDecimal("240.00");

        Detraccion detraccion1 = new Detraccion(
            "01", "123-456-789012", "015", porcentaje, monto
        );

        Detraccion detraccion2 = new Detraccion(
            "01", "123-456-789012", "015", porcentaje, monto
        );

        assertEquals(detraccion1, detraccion2);
        assertEquals(detraccion1.hashCode(), detraccion2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct detracciones")
    void testDesigualdadPorDiferentesValores() {
        Detraccion detraccion1 = new Detraccion(
            "01", "123-456-789012", "015",
            new BigDecimal("0.12"), new BigDecimal("240.00")
        );

        Detraccion detraccion2 = new Detraccion(
            "02", "999-999-999999", "016",
            new BigDecimal("0.10"), new BigDecimal("200.00")
        );

        assertNotEquals(detraccion1, detraccion2);
    }

    @Test
    @DisplayName("Should support multiple payment media types")
    void testDiferentesMediosPago() {
        String[] medios = {"01", "02", "03", "04", "05"};
        for (String medio : medios) {
            Detraccion detraccion = new Detraccion(
                medio, "123-456-789012", "015",
                new BigDecimal("0.12"), new BigDecimal("240.00")
            );
            assertEquals(medio, detraccion.medioDePago());
        }
    }

    @Test
    @DisplayName("Should preserve bank account number format")
    void testPreservaCuentaBancaria() {
        String cuenta = "123-456-789012";
        Detraccion detraccion = new Detraccion(
            "01", cuenta, "015",
            new BigDecimal("0.12"), new BigDecimal("240.00")
        );

        assertEquals(cuenta, detraccion.cuentaBancaria());
    }

    @Test
    @DisplayName("Should preserve porcentaje precision")
    void testPreservaPorcentajePrecision() {
        BigDecimal porcentaje = new BigDecimal("0.15");
        Detraccion detraccion = new Detraccion(
            "01", "123-456-789012", "015",
            porcentaje, new BigDecimal("300.00")
        );

        assertEquals(0, porcentaje.compareTo(detraccion.porcentaje()));
    }

    @Test
    @DisplayName("Should preserve monto precision")
    void testPreservaMontoPrecision() {
        BigDecimal monto = new BigDecimal("999.99");
        Detraccion detraccion = new Detraccion(
            "01", "123-456-789012", "015",
            new BigDecimal("0.12"), monto
        );

        assertEquals(0, monto.compareTo(detraccion.monto()));
    }

    @Test
    @DisplayName("Should handle zero monto")
    void testMontoZero() {
        Detraccion detraccion = new Detraccion(
            "01", "123-456-789012", "015",
            new BigDecimal("0.12"), BigDecimal.ZERO
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(detraccion.monto()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Detraccion detraccion = new Detraccion(
            "01", "123-456-789012", "015",
            new BigDecimal("0.12"), new BigDecimal("240.00")
        );

        String toString = detraccion.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values for optional fields")
    void testCamposNulos() {
        Detraccion detraccion = new Detraccion(
            "01", null, null, null, null
        );

        assertEquals("01", detraccion.medioDePago());
        assertNull(detraccion.cuentaBancaria());
        assertNull(detraccion.tipoBienDetraido());
        assertNull(detraccion.porcentaje());
        assertNull(detraccion.monto());
    }

    @Test
    @DisplayName("Should support various good type codes")
    void testVariosCodigosBien() {
        String[] codigos = {"015", "016", "017", "018"};
        for (String codigo : codigos) {
            Detraccion detraccion = new Detraccion(
                "01", "123-456-789012", codigo,
                new BigDecimal("0.12"), new BigDecimal("240.00")
            );
            assertEquals(codigo, detraccion.tipoBienDetraido());
        }
    }
}
