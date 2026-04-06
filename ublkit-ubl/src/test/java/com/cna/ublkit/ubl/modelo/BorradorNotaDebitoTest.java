package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BorradorNotaDebito Class Tests")
class BorradorNotaDebitoTest {

    private BorradorNotaDebito notaDebito;

    @BeforeEach
    void setUp() {
        notaDebito = new BorradorNotaDebito();
    }

    @Test
    @DisplayName("Should create empty BorradorNotaDebito")
    void testCrearNotaDebitoVacia() {
        assertNotNull(notaDebito);
        assertNull(notaDebito.getTipoNota());
    }

    @Test
    @DisplayName("Should set and get tipoNota")
    void testTipoNota() {
        notaDebito.setTipoNota("01");
        assertEquals("01", notaDebito.getTipoNota());
    }

    @Test
    @DisplayName("Should support nota de débito por intereses")
    void testTipoNotaPorIntereses() {
        notaDebito.setTipoNota("01");
        assertEquals("01", notaDebito.getTipoNota());
    }

    @Test
    @DisplayName("Should set and get comprobanteAfectadoSerieNumero")
    void testComprobanteAfectado() {
        String serieNumero = "F001-123";
        notaDebito.setComprobanteAfectadoSerieNumero(serieNumero);
        assertEquals(serieNumero, notaDebito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should set and get comprobanteAfectadoTipo")
    void testComprobanteAfectadoTipo() {
        notaDebito.setComprobanteAfectadoTipo("01");
        assertEquals("01", notaDebito.getComprobanteAfectadoTipo());
    }

    @Test
    @DisplayName("Should support factura (01) as affected document")
    void testComprobanteAfectadoFactura() {
        notaDebito.setComprobanteAfectadoTipo("01");
        notaDebito.setComprobanteAfectadoSerieNumero("F001-150");

        assertEquals("01", notaDebito.getComprobanteAfectadoTipo());
        assertEquals("F001-150", notaDebito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should support boleta (03) as affected document")
    void testComprobanteAfectadoBoleta() {
        notaDebito.setComprobanteAfectadoTipo("03");
        notaDebito.setComprobanteAfectadoSerieNumero("B001-100");

        assertEquals("03", notaDebito.getComprobanteAfectadoTipo());
        assertEquals("B001-100", notaDebito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should set and get sustentoDescripcion")
    void testSustentoDescripcion() {
        String sustento = "Cobro de intereses por mora en pago";
        notaDebito.setSustentoDescripcion(sustento);
        assertEquals(sustento, notaDebito.getSustentoDescripcion());
    }

    @Test
    @DisplayName("Should set and get totalImporte")
    void testTotalImporte() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("200.00"),
            new BigDecimal("150.00"),
            new BigDecimal("200.00"),
            null, null
        );
        notaDebito.setTotalImporte(total);
        assertEquals(total, notaDebito.getTotalImporte());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setSerie")
    void testHeredaSerie() {
        notaDebito.setSerie("ND01");
        assertEquals("ND01", notaDebito.getSerie());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setNumero")
    void testHeredaNumero() {
        notaDebito.setNumero(25);
        assertEquals(25, notaDebito.getNumero());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setFechaEmision")
    void testHeredaFechaEmision() {
        java.time.LocalDate fecha = java.time.LocalDate.of(2024, 12, 31);
        notaDebito.setFechaEmision(fecha);
        assertEquals(fecha, notaDebito.getFechaEmision());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setMoneda")
    void testHeredaMoneda() {
        notaDebito.setMoneda("PEN");
        assertEquals("PEN", notaDebito.getMoneda());
    }

    @Test
    @DisplayName("Should support nota de débito por intereses")
    void testNotaIntereses() {
        notaDebito.setTipoNota("01");
        notaDebito.setComprobanteAfectadoTipo("01");
        notaDebito.setComprobanteAfectadoSerieNumero("F001-150");
        notaDebito.setSustentoDescripcion("Intereses por mora en pago");

        assertEquals("01", notaDebito.getTipoNota());
        assertEquals("F001-150", notaDebito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should support nota de débito por ajuste de precio")
    void testNotaAjustePrecio() {
        notaDebito.setTipoNota("01");
        notaDebito.setSustentoDescripcion("Ajuste de precio por variación en tipo de cambio");

        assertEquals("01", notaDebito.getTipoNota());
    }

    @Test
    @DisplayName("Should handle multiple updates to fields")
    void testMultiplesActualizaciones() {
        notaDebito.setTipoNota("01");
        notaDebito.setTipoNota("01");
        assertEquals("01", notaDebito.getTipoNota());

        notaDebito.setNumero(1);
        notaDebito.setNumero(25);
        assertEquals(25, notaDebito.getNumero());
    }

    @Test
    @DisplayName("Should handle null sustento")
    void testSustentoNulo() {
        notaDebito.setSustentoDescripcion(null);
        assertNull(notaDebito.getSustentoDescripcion());
    }

    @Test
    @DisplayName("Should handle complete nota de débito")
    void testNotaDebitoCompleta() {
        notaDebito.setTipoNota("01");
        notaDebito.setSerie("ND01");
        notaDebito.setNumero(1);
        notaDebito.setMoneda("PEN");
        notaDebito.setFechaEmision(java.time.LocalDate.now());
        notaDebito.setComprobanteAfectadoTipo("01");
        notaDebito.setComprobanteAfectadoSerieNumero("F001-150");
        notaDebito.setSustentoDescripcion("Intereses por mora en pago");

        assertEquals("01", notaDebito.getTipoNota());
        assertEquals("ND01", notaDebito.getSerie());
        assertEquals("F001-150", notaDebito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should support different series patterns")
    void testDiferentesSeriesND() {
        BorradorNotaDebito nd1 = new BorradorNotaDebito();
        nd1.setSerie("ND01");
        nd1.setNumero(1);

        BorradorNotaDebito nd2 = new BorradorNotaDebito();
        nd2.setSerie("ND02");
        nd2.setNumero(1);

        BorradorNotaDebito nd3 = new BorradorNotaDebito();
        nd3.setSerie("D001");
        nd3.setNumero(1);

        assertEquals("ND01", nd1.getSerie());
        assertEquals("ND02", nd2.getSerie());
        assertEquals("D001", nd3.getSerie());
    }

    @Test
    @DisplayName("Should handle complex sustento descriptions")
    void testSustentosComplejos() {
        String sustento = "Intereses por mora en pago - Período: 01/12/2024 al 31/12/2024 - Tasa diaria: 0.05%";
        notaDebito.setSustentoDescripcion(sustento);
        assertTrue(notaDebito.getSustentoDescripcion().contains("Periodo"));
    }
}
