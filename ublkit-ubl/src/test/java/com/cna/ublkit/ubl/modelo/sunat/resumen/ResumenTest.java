package com.cna.ublkit.ubl.modelo.sunat.resumen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ComprobanteValorVenta Class Tests")
class ComprobanteValorVentaTest {

    private ComprobanteValorVenta comprobante;

    @BeforeEach
    void setUp() {
        comprobante = new ComprobanteValorVenta();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        comprobante.setTipoDocumento("01");
        assertEquals("01", comprobante.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get serieNumero")
    void testSetYGetSerieNumero() {
        comprobante.setSerieNumero("F001");
        assertEquals("F001", comprobante.getSerieNumero());
    }

    @Test
    @DisplayName("Should set and get valorVentaBruta")
    void testSetYGetValorVentaBruta() {
        BigDecimal valor = new BigDecimal("1000.00");
        comprobante.setValorVentaBruta(valor);
        assertEquals(0, valor.compareTo(comprobante.getValorVentaBruta()));
    }

    @Test
    @DisplayName("Should set and get descuentoGlobal")
    void testSetYGetDescuentoGlobal() {
        BigDecimal descuento = new BigDecimal("100.00");
        comprobante.setDescuentoGlobal(descuento);
        assertEquals(0, descuento.compareTo(comprobante.getDescuentoGlobal()));
    }

    @Test
    @DisplayName("Should set and get valorVentaNeta")
    void testSetYGetValorVentaNeta() {
        BigDecimal valor = new BigDecimal("900.00");
        comprobante.setValorVentaNeta(valor);
        assertEquals(0, valor.compareTo(comprobante.getValorVentaNeta()));
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        comprobante.setValorVentaBruta(null);
        comprobante.setDescuentoGlobal(null);
        assertNull(comprobante.getValorVentaBruta());
        assertNull(comprobante.getDescuentoGlobal());
    }
}

@DisplayName("ComprobanteImpuestos Class Tests")
class ComprobanteImpuestosTest {

    private ComprobanteImpuestos impuestos;

    @BeforeEach
    void setUp() {
        impuestos = new ComprobanteImpuestos();
    }

    @Test
    @DisplayName("Should set and get codigoTributo")
    void testSetYGetCodigoTributo() {
        impuestos.setCodigoTributo("1000");
        assertEquals("1000", impuestos.getCodigoTributo());
    }

    @Test
    @DisplayName("Should set and get baseImponible")
    void testSetYGetBaseImponible() {
        BigDecimal base = new BigDecimal("900.00");
        impuestos.setBaseImponible(base);
        assertEquals(0, base.compareTo(impuestos.getBaseImponible()));
    }

    @Test
    @DisplayName("Should set and get monto")
    void testSetYGetMonto() {
        BigDecimal monto = new BigDecimal("162.00");
        impuestos.setMonto(monto);
        assertEquals(0, monto.compareTo(impuestos.getMonto()));
    }

    @Test
    @DisplayName("Should handle multiple tributos")
    void testManejaMultiplesTributos() {
        impuestos.setCodigoTributo("1000");
        impuestos.setBaseImponible(new BigDecimal("900.00"));
        impuestos.setMonto(new BigDecimal("162.00"));

        assertNotNull(impuestos.getCodigoTributo());
        assertNotNull(impuestos.getBaseImponible());
        assertNotNull(impuestos.getMonto());
    }
}

@DisplayName("ComprobanteAfectadoResumen Class Tests")
class ComprobanteAfectadoResumenTest {

    private ComprobanteAfectadoResumen comprobante;

    @BeforeEach
    void setUp() {
        comprobante = new ComprobanteAfectadoResumen();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        comprobante.setTipoDocumento("01");
        assertEquals("01", comprobante.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get serieNumero")
    void testSetYGetSerieNumero() {
        comprobante.setSerieNumero("F001");
        assertEquals("F001", comprobante.getSerieNumero());
    }

    @Test
    @DisplayName("Should set and get estado")
    void testSetYGetEstado() {
        comprobante.setEstado("1");
        assertEquals("1", comprobante.getEstado());
    }
}

@DisplayName("PercepcionResumen Class Tests")
class PercepcionResumenTest {

    private PercepcionResumen percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new PercepcionResumen();
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
    @DisplayName("Should handle null importe")
    void testManejaImporteNulo() {
        percepcion.setImporteTotalPercibido(null);
        assertNull(percepcion.getImporteTotalPercibido());
    }
}

@DisplayName("ComprobanteResumen Class Tests")
class ComprobanteResumenTest {

    private ComprobanteResumen resumen;

    @BeforeEach
    void setUp() {
        resumen = new ComprobanteResumen();
    }

    @Test
    @DisplayName("Should initialize resumen diario")
    void testInicializaResumenDiario() {
        assertNotNull(resumen);
    }
}

@DisplayName("ItemResumenDiario Class Tests")
class ItemResumenDiarioTest {

    private ItemResumenDiario item;

    @BeforeEach
    void setUp() {
        item = new ItemResumenDiario();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        item.setTipoDocumento("01");
        assertEquals("01", item.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get cantidad")
    void testSetYGetCantidad() {
        item.setCantidad(10);
        assertEquals(10, item.getCantidad());
    }

    @Test
    @DisplayName("Should set and get totalValorVenta")
    void testSetYGetTotalValorVenta() {
        BigDecimal total = new BigDecimal("1000.00");
        item.setTotalValorVenta(total);
        assertEquals(0, total.compareTo(item.getTotalValorVenta()));
    }
}

@DisplayName("ResumenDiario Class Tests")
class ResumenDiarioTest {

    private ResumenDiario resumen;

    @BeforeEach
    void setUp() {
        resumen = new ResumenDiario();
    }

    @Test
    @DisplayName("Should initialize with default values")
    void testInicializaConValoresPorDefecto() {
        assertNotNull(resumen);
    }

    @Test
    @DisplayName("Should be able to set properties")
    void testPuedeEstablecerPropiedades() {
        resumen.setNumero(1);
        assertEquals(1, resumen.getNumero());
    }

    @Test
    @DisplayName("Should handle fecha emision")
    void testManejaFechaEmision() {
        LocalDate fecha = LocalDate.of(2026, 4, 6);
        resumen.setFechaEmision(fecha);
        assertEquals(fecha, resumen.getFechaEmision());
    }

    @Test
    @DisplayName("Should handle moneda")
    void testManejaMoneda() {
        resumen.setMoneda("PEN");
        assertEquals("PEN", resumen.getMoneda());
    }
}
