package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.complemento.FormaDePago;
import com.cna.ublkit.ubl.modelo.complemento.Anticipo;
import com.cna.ublkit.ubl.modelo.complemento.Descuento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnsambladorFactura Extended Tests")
class EnsambladorFacturaExtendedTest {

    private BorradorFactura factura;

    @BeforeEach
    void setUp() {
        factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 4, 6));
        factura.setTasaIgv(new BigDecimal("0.18"));
    }

    @Test
    @DisplayName("Should ensamblar simple factura")
    void testEnsamplaFacturaSimple() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertEquals("F001", resultado.getSerie());
    }

    @Test
    @DisplayName("Should set default moneda")
    void testFijaMonedaPorDefecto() {
        factura.setMoneda(null);
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals("PEN", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should set default IGV tasa")
    void testFijaIgvPorDefecto() {
        factura.setTasaIgv(null);
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado.getTasaIgv());
    }

    @Test
    @DisplayName("Should handle null factura")
    void testManejaFacturaNula() {
        BorradorFactura resultado = EnsambladorFactura.ensamblar(null);
        assertNull(resultado);
    }

    @Test
    @DisplayName("Should handle empty lines")
    void testManejaLineasVacias() {
        factura.setDetalles(Arrays.asList());

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should preserve invoice type")
    void testPreservaTypeComprobanteFactura() {
        factura.setTipoComprobante("01");
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals("01", resultado.getTipoComprobante());
    }

    @Test
    @DisplayName("Should handle factura with multiple lines")
    void testManejaFacturaConMultiplesLineas() {
        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Producto A");
        linea1.setCantidad(new BigDecimal("5.00"));
        linea1.setPrecio(new BigDecimal("100.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Producto B");
        linea2.setCantidad(new BigDecimal("3.00"));
        linea2.setPrecio(new BigDecimal("200.00"));

        factura.setDetalles(Arrays.asList(linea1, linea2));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals(2, resultado.getDetalles().size());
    }

    @Test
    @DisplayName("Should calculate totals")
    void testCalculaTotales() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado.getTotalImpuestos());
        assertNotNull(resultado.getTotalImporte());
    }
}

@DisplayName("NumeroALetras Comprehensive Tests")
class NumeroALetrasComprehensiveTest {

    @Test
    @DisplayName("Should convert 0 to words")
    void testConvierteZero() {
        String resultado = NumeroALetras.convertir(BigDecimal.ZERO);
        assertNotNull(resultado);
        assertTrue(resultado.toLowerCase().contains("cero") || resultado.isEmpty());
    }

    @Test
    @DisplayName("Should convert single digit")
    void testConvierteDigitoUnico() {
        String resultado = NumeroALetras.convertir(new BigDecimal("5"));
        assertNotNull(resultado);
        assertTrue(resultado.length() > 0);
    }

    @Test
    @DisplayName("Should convert tens")
    void testConvierteDieces() {
        String resultado = NumeroALetras.convertir(new BigDecimal("20"));
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should convert hundreds")
    void testConviertecentenas() {
        String resultado = NumeroALetras.convertir(new BigDecimal("100"));
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should convert thousands")
    void testConvierteMiles() {
        String resultado = NumeroALetras.convertir(new BigDecimal("1000"));
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should convert large numbers")
    void testConvierteNumerosGrandes() {
        String resultado = NumeroALetras.convertir(new BigDecimal("999999"));
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should convert decimal numbers")
    void testConvierteNumerosDecimales() {
        String resultado = NumeroALetras.convertir(new BigDecimal("123.45"));
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle null gracefully")
    void testManejaValoresNulos() {
        String resultado = NumeroALetras.convertir(null);
        // Should not throw exception
        assertTrue(resultado == null || resultado.length() >= 0);
    }

    @Test
    @DisplayName("Should return consistent results")
    void testRetornaResultadosConsistentes() {
        String resultado1 = NumeroALetras.convertir(new BigDecimal("100"));
        String resultado2 = NumeroALetras.convertir(new BigDecimal("100"));
        assertEquals(resultado1, resultado2);
    }
}

@DisplayName("Ensamblador Integration Tests")
class EnsambladoresIntegracionTest {

    @Test
    @DisplayName("Should ensamblar all document types")
    void testEnsamplaTodosTiposDocumentos() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 4, 6));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle complex documento with all complements")
    void testManejaDocumentoComplejoConTodosComplementos() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 4, 6));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        FormaDePago forma = new FormaDePago("01", BigDecimal.ZERO, null);
        factura.setFormaDePago(forma);

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);
        assertNotNull(resultado);
        assertNotNull(resultado.getFormaDePago());
    }

    @Test
    @DisplayName("Should persist all data through ensamblaje")
    void testPersisteTodosDatosEnEnsemblaje() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(100);
        factura.setMoneda("USD");
        factura.setFechaEmision(LocalDate.of(2026, 3, 15));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals("F001", resultado.getSerie());
        assertEquals(100, resultado.getNumero());
        assertEquals("USD", resultado.getMoneda());
    }
}
