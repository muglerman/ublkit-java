package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnsambladorNota Class Tests")
class EnsambladorNotaTest {

    private BorradorNotaCredito notaCredito;
    private BorradorNotaDebito notaDebito;

    @BeforeEach
    void setUp() {
        notaCredito = new BorradorNotaCredito();
        notaDebito = new BorradorNotaDebito();
    }

    @Test
    @DisplayName("Should ensamblar nota de crédito minima")
    void testEnsambladorNotaCreditoMinima() {
        notaCredito.setMoneda(null);
        notaCredito.setTasaIgv(null);

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("10.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertEquals("PEN", resultado.getMoneda());
        assertNotNull(resultado.getTasaIgv());
        assertEquals(0, new BigDecimal("0.18").compareTo(resultado.getTasaIgv()));
    }

    @Test
    @DisplayName("Should ensamblar nota de débito minima")
    void testEnsambladorNotaDebitoMinima() {
        notaDebito.setMoneda(null);
        notaDebito.setTasaIgv(null);

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Intereses");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("50.00"));
        notaDebito.setDetalles(Arrays.asList(linea));

        BorradorNotaDebito resultado = EnsambladorNota.ensamblar(notaDebito);

        assertEquals("PEN", resultado.getMoneda());
        assertNotNull(resultado.getTasaIgv());
    }

    @Test
    @DisplayName("Should preserve existing moneda")
    void testPreservaMonedaExistente() {
        notaCredito.setMoneda("USD");
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertEquals("USD", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should calculate line IGV")
    void testCalculaIgvLinea() {
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setIgvTipo("10");
        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertNotNull(resultado.getDetalles().get(0).getIgv());
        assertNotNull(resultado.getDetalles().get(0).getIgvBaseImponible());
    }

    @Test
    @DisplayName("Should calculate total impuestos")
    void testCalculaTotalImpuestos() {
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("10.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setIgvTipo("10");
        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertNotNull(resultado.getTotalImpuestos());
    }

    @Test
    @DisplayName("Should calculate total importe")
    void testCalculaTotalImporte() {
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto");
        linea.setCantidad(new BigDecimal("1.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setIgvTipo("10");
        notaCredito.setDetalles(Arrays.asList(linea));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertNotNull(resultado.getTotalImporte());
        assertNotNull(resultado.getTotalImporte().importe());
    }

    @Test
    @DisplayName("Should handle null detalles")
    void testManejaDetallesNulos() {
        notaCredito.setDetalles(null);
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertEquals("PEN", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should handle multiple line items")
    void testMultiplesLineas() {
        notaCredito.setTasaIgv(new BigDecimal("0.18"));

        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Producto A");
        linea1.setCantidad(new BigDecimal("5.00"));
        linea1.setPrecio(new BigDecimal("100.00"));
        linea1.setIgvTipo("10");

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Producto B");
        linea2.setCantidad(new BigDecimal("3.00"));
        linea2.setPrecio(new BigDecimal("200.00"));
        linea2.setIgvTipo("10");

        notaCredito.setDetalles(Arrays.asList(linea1, linea2));

        BorradorNotaCredito resultado = EnsambladorNota.ensamblar(notaCredito);

        assertEquals(2, resultado.getDetalles().size());
    }
}
