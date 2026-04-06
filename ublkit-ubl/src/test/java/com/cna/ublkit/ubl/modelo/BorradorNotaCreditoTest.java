package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BorradorNotaCredito Class Tests")
class BorradorNotaCreditoTest {

    private BorradorNotaCredito notaCredito;

    @BeforeEach
    void setUp() {
        notaCredito = new BorradorNotaCredito();
    }

    @Test
    @DisplayName("Should create empty BorradorNotaCredito")
    void testCrearNotaCreditoVacia() {
        assertNotNull(notaCredito);
        assertNull(notaCredito.getTipoNota());
    }

    @Test
    @DisplayName("Should set and get tipoNota")
    void testTipoNota() {
        notaCredito.setTipoNota("01");
        assertEquals("01", notaCredito.getTipoNota());
    }

    @Test
    @DisplayName("Should support all tipos de nota according to Catálogo 09")
    void testTodosLosTiposNota() {
        String[] tipos = {"01", "02", "03", "04", "05"};
        for (String tipo : tipos) {
            BorradorNotaCredito nota = new BorradorNotaCredito();
            nota.setTipoNota(tipo);
            assertEquals(tipo, nota.getTipoNota());
        }
    }

    @Test
    @DisplayName("Should set and get comprobanteAfectadoSerieNumero")
    void testComprobanteAfectado() {
        String serieNumero = "F001-123";
        notaCredito.setComprobanteAfectadoSerieNumero(serieNumero);
        assertEquals(serieNumero, notaCredito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should set and get comprobanteAfectadoTipo")
    void testComprobanteAfectadoTipo() {
        notaCredito.setComprobanteAfectadoTipo("01");
        assertEquals("01", notaCredito.getComprobanteAfectadoTipo());
    }

    @Test
    @DisplayName("Should support factura (01) as affected document")
    void testComprobanteAfectadoFactura() {
        notaCredito.setComprobanteAfectadoTipo("01");
        notaCredito.setComprobanteAfectadoSerieNumero("F001-100");

        assertEquals("01", notaCredito.getComprobanteAfectadoTipo());
        assertEquals("F001-100", notaCredito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should support boleta (03) as affected document")
    void testComprobanteAfectadoBoleta() {
        notaCredito.setComprobanteAfectadoTipo("03");
        notaCredito.setComprobanteAfectadoSerieNumero("B001-50");

        assertEquals("03", notaCredito.getComprobanteAfectadoTipo());
    }

    @Test
    @DisplayName("Should set and get sustentoDescripcion")
    void testSustentoDescripcion() {
        String sustento = "Anulación por error en emisión";
        notaCredito.setSustentoDescripcion(sustento);
        assertEquals(sustento, notaCredito.getSustentoDescripcion());
    }

    @Test
    @DisplayName("Should set and get totalImporte")
    void testTotalImporte() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("500.00"),
            new BigDecimal("400.00"),
            new BigDecimal("500.00"),
            null, null
        );
        notaCredito.setTotalImporte(total);
        assertEquals(total, notaCredito.getTotalImporte());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setSerie")
    void testHeredaSerie() {
        notaCredito.setSerie("NC01");
        assertEquals("NC01", notaCredito.getSerie());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setNumero")
    void testHeredaNumero() {
        notaCredito.setNumero(50);
        assertEquals(50, notaCredito.getNumero());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setFechaEmision")
    void testHeredaFechaEmision() {
        java.time.LocalDate fecha = java.time.LocalDate.of(2024, 12, 31);
        notaCredito.setFechaEmision(fecha);
        assertEquals(fecha, notaCredito.getFechaEmision());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase - setMoneda")
    void testHeredaMoneda() {
        notaCredito.setMoneda("PEN");
        assertEquals("PEN", notaCredito.getMoneda());
    }

    @Test
    @DisplayName("Should support nota de crédito anulación")
    void testNotaAnulacion() {
        notaCredito.setTipoNota("01");
        notaCredito.setComprobanteAfectadoTipo("01");
        notaCredito.setComprobanteAfectadoSerieNumero("F001-100");
        notaCredito.setSustentoDescripcion("Anulación por error de datos");

        assertEquals("01", notaCredito.getTipoNota());
        assertEquals("F001-100", notaCredito.getComprobanteAfectadoSerieNumero());
    }

    @Test
    @DisplayName("Should support nota de crédito por devolución")
    void testNotaDevolucion() {
        notaCredito.setTipoNota("02");
        notaCredito.setComprobanteAfectadoTipo("01");
        notaCredito.setComprobanteAfectadoSerieNumero("F001-101");
        notaCredito.setSustentoDescripcion("Devolución de productos por defecto");

        assertEquals("02", notaCredito.getTipoNota());
    }

    @Test
    @DisplayName("Should support nota de crédito por rebaja")
    void testNotaRebaja() {
        notaCredito.setTipoNota("03");
        notaCredito.setSustentoDescripcion("Rebaja por bonificación comercial");

        assertEquals("03", notaCredito.getTipoNota());
    }

    @Test
    @DisplayName("Should handle multiple updates to fields")
    void testMultiplesActualizaciones() {
        notaCredito.setTipoNota("01");
        notaCredito.setTipoNota("02");
        assertEquals("02", notaCredito.getTipoNota());

        notaCredito.setNumero(1);
        notaCredito.setNumero(50);
        assertEquals(50, notaCredito.getNumero());
    }

    @Test
    @DisplayName("Should handle null sustento")
    void testSustentoNulo() {
        notaCredito.setSustentoDescripcion(null);
        assertNull(notaCredito.getSustentoDescripcion());
    }

    @Test
    @DisplayName("Should handle complete nota de crédito")
    void testNotaCreditoCompleta() {
        notaCredito.setTipoNota("01");
        notaCredito.setSerie("NC01");
        notaCredito.setNumero(1);
        notaCredito.setMoneda("PEN");
        notaCredito.setFechaEmision(java.time.LocalDate.now());
        notaCredito.setComprobanteAfectadoTipo("01");
        notaCredito.setComprobanteAfectadoSerieNumero("F001-100");
        notaCredito.setSustentoDescripcion("Anulación por error");

        assertEquals("01", notaCredito.getTipoNota());
        assertEquals("NC01", notaCredito.getSerie());
        assertEquals("F001-100", notaCredito.getComprobanteAfectadoSerieNumero());
    }
}
