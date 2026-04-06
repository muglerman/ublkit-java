package com.cna.ublkit.ubl;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.cna.ublkit.ubl.ensamblador.EnsambladorNota;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comprehensive Integration and Edge Case Tests")
class UblIntegracionComprehensiveTest {

    private BorradorFactura factura;
    private EmisorDocumento emisor;
    private ReceptorDocumento receptor;

    @BeforeEach
    void setUp() {
        factura = new BorradorFactura();
        emisor = new EmisorDocumento("20123456789", null, "Empresa Vendedora SA", null, null);
        receptor = new ReceptorDocumento("06", "20987654321", "Cliente Comprador SA", null, null);

        factura.setEmisor(emisor);
        factura.setReceptor(receptor);
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 4, 6));
        factura.setHoraEmision(LocalTime.of(14, 30, 0));
        factura.setTasaIgv(new BigDecimal("0.18"));
    }

    @Test
    @DisplayName("Should create complete factura with all components")
    void testCreaFacturaCompletaConTodosComponentes() {
        // Create line items
        LineaDetalle linea1 = new LineaDetalle();
        linea1.setItem(1);
        linea1.setCodigo("SKU001");
        linea1.setDescripcion("Producto A");
        linea1.setCantidad(new BigDecimal("10.00"));
        linea1.setPrecio(new BigDecimal("100.00"));
        linea1.setUnidadMedida("KG");
        linea1.setImporte(new BigDecimal("1000.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setItem(2);
        linea2.setCodigo("SKU002");
        linea2.setDescripcion("Producto B");
        linea2.setCantidad(new BigDecimal("5.00"));
        linea2.setPrecio(new BigDecimal("200.00"));
        linea2.setUnidadMedida("UN");
        linea2.setImporte(new BigDecimal("1000.00"));

        factura.setDetalles(Arrays.asList(linea1, linea2));

        // Create charges and discounts
        CargoDescuento descuento = new CargoDescuento("01", new BigDecimal("100.00"), new BigDecimal("0.05"), null);

        factura.setCargos(Arrays.asList(descuento));

        // Set totals
        TotalImpuestos totales = new TotalImpuestos(
            new BigDecimal("360.00"),  // total
            new BigDecimal("360.00"),  // gravadoImporte
            new BigDecimal("2000.00"), // gravadoBaseImponible
            BigDecimal.ZERO,           // exoneradoImporte
            BigDecimal.ZERO,           // exoneradoBaseImponible
            BigDecimal.ZERO,           // inafectoImporte
            BigDecimal.ZERO,           // inafectoBaseImponible
            BigDecimal.ZERO,           // gratuitoImporte
            BigDecimal.ZERO,           // gratuitoBaseImponible
            BigDecimal.ZERO,           // exportacionImporte
            BigDecimal.ZERO,           // exportacionBaseImponible
            BigDecimal.ZERO,           // ivapImporte
            BigDecimal.ZERO,           // ivapBaseImponible
            BigDecimal.ZERO,           // icbImporte
            BigDecimal.ZERO,           // iscImporte
            BigDecimal.ZERO            // iscBaseImponible
        );
        factura.setTotalImpuestos(totales);

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertEquals(2, resultado.getDetalles().size());
        assertNotNull(resultado.getCargos());
        assertNotNull(resultado.getTotalImpuestos());
    }

    @Test
    @DisplayName("Should handle nota credito with referencing")
    void testManejaNotaCreditoConReferencia() {
        BorradorNotaCredito notaCredito = new BorradorNotaCredito();
        notaCredito.setSerie("NC01");
        notaCredito.setNumero(1);
        notaCredito.setMoneda("PEN");
        notaCredito.setFechaEmision(LocalDate.of(2026, 4, 7));
        notaCredito.setHoraEmision(LocalTime.of(10, 0, 0));
        notaCredito.setEmisor(emisor);
        notaCredito.setReceptor(receptor);

        LineaDetalle linea = new LineaDetalle();
        linea.setItem(1);
        linea.setDescripcion("Devolución de producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));

        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertNotNull(resultado);
        assertEquals("NC01", resultado.getSerie());
    }

    @Test
    @DisplayName("Should handle nota debito with multiple lines")
    void testManejaNotaDebitoConMultiplesLineas() {
        BorradorNotaDebito notaDebito = new BorradorNotaDebito();
        notaDebito.setSerie("ND01");
        notaDebito.setNumero(1);
        notaDebito.setMoneda("PEN");
        notaDebito.setFechaEmision(LocalDate.of(2026, 4, 8));
        notaDebito.setHoraEmision(LocalTime.of(11, 30, 0));
        notaDebito.setEmisor(emisor);
        notaDebito.setReceptor(receptor);
        notaDebito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea1 = new LineaDetalle();
        linea1.setItem(1);
        linea1.setDescripcion("Intereses por mora");
        linea1.setCantidad(new BigDecimal("1.00"));
        linea1.setPrecio(new BigDecimal("50.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setItem(2);
        linea2.setDescripcion("Gastos de cobranza");
        linea2.setCantidad(new BigDecimal("1.00"));
        linea2.setPrecio(new BigDecimal("25.00"));

        notaDebito.setDetalles(Arrays.asList(linea1, linea2));

        BorradorNotaDebito resultado = EnsambladorNota.ensamblar(notaDebito);

        assertNotNull(resultado);
        assertEquals(2, resultado.getDetalles().size());
    }

    @Test
    @DisplayName("Should handle factura with different currencies")
    void testManejaFacturaConDiferentesMonedas() {
        factura.setMoneda("USD");
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals("USD", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should handle missing optional fields")
    void testManejaCamposOpcionalesFaltantes() {
        factura.setNombreComercial(null);
        factura.setObservaciones(null);
        factura.setOrdenDeCompra(null);

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertNull(resultado.getNombreComercial());
    }

    @Test
    @DisplayName("Should handle very large numbers")
    void testManejaNumerosMuyGrandes() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto de alto valor");
        linea.setCantidad(new BigDecimal("999999.99"));
        linea.setPrecio(new BigDecimal("999999.99"));
        linea.setImporte(new BigDecimal("999999999999.9801"));

        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertNotNull(resultado.getDetalles().get(0).getImporte());
    }

    @Test
    @DisplayName("Should handle decimal precision")
    void testManejaPrecisionDecimal() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("3.33"));
        linea.setPrecio(new BigDecimal("33.33"));
        linea.setImporte(new BigDecimal("110.8889"));

        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertTrue(resultado.getDetalles().get(0).getImporte().scale() >= 2);
    }

    @Test
    @DisplayName("Should handle zero-amount items")
    void testManejaItemsConMontoCero() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Obsequio");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(BigDecimal.ZERO);
        linea.setImporte(BigDecimal.ZERO);

        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getDetalles().get(0).getPrecio()));
    }

    @Test
    @DisplayName("Should preserve order of line items")
    void testPreservaOrdenDeLineas() {
        LineaDetalle linea1 = new LineaDetalle();
        linea1.setItem(1);
        linea1.setDescripcion("Primero");

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setItem(2);
        linea2.setDescripcion("Segundo");

        LineaDetalle linea3 = new LineaDetalle();
        linea3.setItem(3);
        linea3.setDescripcion("Tercero");

        factura.setDetalles(Arrays.asList(linea1, linea2, linea3));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertEquals("Primero", resultado.getDetalles().get(0).getDescripcion());
        assertEquals("Segundo", resultado.getDetalles().get(1).getDescripcion());
        assertEquals("Tercero", resultado.getDetalles().get(2).getDescripcion());
    }

    @Test
    @DisplayName("Should handle special characters in descriptions")
    void testManejaCaracteresEspecialesEnDescripciones() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto & Servicio (Incluye) \"Especial\" - Ñoño");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));

        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
        assertTrue(resultado.getDetalles().get(0).getDescripcion().contains("&"));
    }

    @Test
    @DisplayName("Should handle maximum decimal places")
    void testManejaLugaresDecimalesMaximos() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.123456789"));
        linea.setPrecio(new BigDecimal("99.987654321"));

        factura.setDetalles(Arrays.asList(linea));

        BorradorFactura resultado = EnsambladorFactura.ensamblar(factura);

        assertNotNull(resultado);
    }
}
