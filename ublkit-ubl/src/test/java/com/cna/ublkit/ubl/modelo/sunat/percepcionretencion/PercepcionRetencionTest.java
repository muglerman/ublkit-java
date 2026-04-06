package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BasePercepcionRetencion Class Tests")
class BasePercepcionRetencionTest {

    private ComprobantePercepcion percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new ComprobantePercepcion();
    }

    @Test
    @DisplayName("Should set and get numero")
    void testSetYGetNumero() {
        percepcion.setNumero(1);
        assertEquals(1, percepcion.getNumero());
    }

    @Test
    @DisplayName("Should set and get serie")
    void testSetYGetSerie() {
        percepcion.setSerie("P001");
        assertEquals("P001", percepcion.getSerie());
    }

    @Test
    @DisplayName("Should set and get tipoRegimenPorcentaje")
    void testSetYGetTipoRegimenPorcentaje() {
        BigDecimal tasa = new BigDecimal("0.03");
        percepcion.setTipoRegimenPorcentaje(tasa);
        assertEquals(0, tasa.compareTo(percepcion.getTipoRegimenPorcentaje()));
    }

    @Test
    @DisplayName("Should set and get operacion")
    void testSetYGetOperacion() {
        OperacionPR operacion = new OperacionPR();
        percepcion.setOperacion(operacion);
        assertNotNull(percepcion.getOperacion());
    }

    @Test
    @DisplayName("Should handle null operacion")
    void testManejaOperacionNula() {
        percepcion.setOperacion(null);
        assertNull(percepcion.getOperacion());
    }

    @Test
    @DisplayName("Should set and get moneda")
    void testSetYGetMoneda() {
        percepcion.setMoneda("PEN");
        assertEquals("PEN", percepcion.getMoneda());
    }

    @Test
    @DisplayName("Should handle various monedas")
    void testManejaVariasMonedas() {
        percepcion.setMoneda("USD");
        assertEquals("USD", percepcion.getMoneda());

        percepcion.setMoneda("EUR");
        assertEquals("EUR", percepcion.getMoneda());
    }
}

@DisplayName("ComprobantePercepcion Class Tests")
class ComprobantePercepcionTest {

    private ComprobantePercepcion percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new ComprobantePercepcion();
    }

    @Test
    @DisplayName("Should set and get tipoRegimen")
    void testSetYGetTipoRegimen() {
        percepcion.setTipoRegimen("02");
        assertEquals("02", percepcion.getTipoRegimen());
    }

    @Test
    @DisplayName("Should set and get importeTotalPercibido")
    void testSetYGetImporteTotalPercibido() {
        BigDecimal importe = new BigDecimal("500.00");
        percepcion.setImporteTotalPercibido(importe);
        assertEquals(0, importe.compareTo(percepcion.getImporteTotalPercibido()));
    }

    @Test
    @DisplayName("Should set and get importeTotalCobrado")
    void testSetYGetImporteTotalCobrado() {
        BigDecimal importe = new BigDecimal("1000.00");
        percepcion.setImporteTotalCobrado(importe);
        assertEquals(0, importe.compareTo(percepcion.getImporteTotalCobrado()));
    }

    @Test
    @DisplayName("Should handle null amounts")
    void testManejaImportesNulos() {
        percepcion.setImporteTotalPercibido(null);
        percepcion.setImporteTotalCobrado(null);
        assertNull(percepcion.getImporteTotalPercibido());
        assertNull(percepcion.getImporteTotalCobrado());
    }

    @Test
    @DisplayName("Should initialize with default values")
    void testInicializaConValoresPorDefecto() {
        ComprobantePercepcion nueva = new ComprobantePercepcion();
        assertNotNull(nueva);
    }
}

@DisplayName("ComprobanteRetencion Class Tests")
class ComprobanteRetencionTest {

    private ComprobanteRetencion retencion;

    @BeforeEach
    void setUp() {
        retencion = new ComprobanteRetencion();
    }

    @Test
    @DisplayName("Should set and get tipoRegimen")
    void testSetYGetTipoRegimen() {
        retencion.setTipoRegimen("01");
        assertEquals("01", retencion.getTipoRegimen());
    }

    @Test
    @DisplayName("Should set and get importeTotalRetenido")
    void testSetYGetImporteTotalRetenido() {
        BigDecimal importe = new BigDecimal("300.00");
        retencion.setImporteTotalRetenido(importe);
        assertEquals(0, importe.compareTo(retencion.getImporteTotalRetenido()));
    }

    @Test
    @DisplayName("Should set and get importeTotalPagado")
    void testSetYGetImporteTotalPagado() {
        BigDecimal importe = new BigDecimal("5000.00");
        retencion.setImporteTotalPagado(importe);
        assertEquals(0, importe.compareTo(retencion.getImporteTotalPagado()));
    }

    @Test
    @DisplayName("Should handle null amounts")
    void testManejaImportesNulos() {
        retencion.setImporteTotalRetenido(null);
        retencion.setImporteTotalPagado(null);
        assertNull(retencion.getImporteTotalRetenido());
        assertNull(retencion.getImporteTotalPagado());
    }

    @Test
    @DisplayName("Should set retencion-specific properties")
    void testFijaPropsEspecificasRetencion() {
        retencion.setTipoRegimen("03");
        retencion.setImporteTotalRetenido(new BigDecimal("100.00"));
        retencion.setImporteTotalPagado(new BigDecimal("2000.00"));

        assertEquals("03", retencion.getTipoRegimen());
        assertNotNull(retencion.getImporteTotalRetenido());
        assertNotNull(retencion.getImporteTotalPagado());
    }
}

@DisplayName("ComprobanteAfectadoPR Class Tests")
class ComprobanteAfectadoPRTest {

    private ComprobanteAfectadoPR comprobante;

    @BeforeEach
    void setUp() {
        comprobante = new ComprobanteAfectadoPR();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        comprobante.setTipoDocumento("01");
        assertEquals("01", comprobante.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get serieDocumento")
    void testSetYGetSerieDocumento() {
        comprobante.setSerieDocumento("F001");
        assertEquals("F001", comprobante.getSerieDocumento());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        comprobante.setNumeroDocumento(123);
        assertEquals(123, comprobante.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        comprobante.setTipoDocumento(null);
        comprobante.setSerieDocumento(null);
        assertNull(comprobante.getTipoDocumento());
        assertNull(comprobante.getSerieDocumento());
    }
}

@DisplayName("OperacionPR Class Tests")
class OperacionPRTest {

    private OperacionPR operacion;

    @BeforeEach
    void setUp() {
        operacion = new OperacionPR();
    }

    @Test
    @DisplayName("Should set and get importeOperacion")
    void testSetYGetImporteOperacion() {
        BigDecimal importe = new BigDecimal("10000.00");
        operacion.setImporteOperacion(importe);
        assertEquals(0, importe.compareTo(operacion.importeOperacion()));
    }

    @Test
    @DisplayName("Should set and get fechaOperacion")
    void testSetYGetFechaOperacion() {
        java.time.LocalDate fecha = java.time.LocalDate.of(2026, 4, 6);
        operacion.setFechaOperacion(fecha);
        assertEquals(fecha, operacion.fechaOperacion());
    }

    @Test
    @DisplayName("Should get importeOperacion via record method")
    void testObtienImporteOperacionViaRecord() {
        BigDecimal importe = new BigDecimal("5000.00");
        operacion.setImporteOperacion(importe);
        assertNotNull(operacion.importeOperacion());
        assertEquals(0, importe.compareTo(operacion.importeOperacion()));
    }

    @Test
    @DisplayName("Should handle null importeOperacion")
    void testManejaImporteOperacionNulo() {
        operacion.setImporteOperacion(null);
        assertNull(operacion.importeOperacion());
    }
}
