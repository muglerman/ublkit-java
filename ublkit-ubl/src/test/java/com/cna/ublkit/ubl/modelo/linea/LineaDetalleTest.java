package com.cna.ublkit.ubl.modelo.linea;

import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LineaDetalle Class Tests")
class LineaDetalleTest {

    private LineaDetalle linea;

    @BeforeEach
    void setUp() {
        linea = new LineaDetalle();
    }

    @Test
    @DisplayName("Should create empty LineaDetalle")
    void testCrearLineaVacia() {
        assertNotNull(linea);
        assertNull(linea.getDescripcion());
        assertNull(linea.getCantidad());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testDescripcion() {
        String descripcion = "Producto A";
        linea.setDescripcion(descripcion);
        assertEquals(descripcion, linea.getDescripcion());
    }

    @Test
    @DisplayName("Should set and get cantidad")
    void testCantidad() {
        BigDecimal cantidad = new BigDecimal("10.00");
        linea.setCantidad(cantidad);
        assertEquals(0, cantidad.compareTo(linea.getCantidad()));
    }

    @Test
    @DisplayName("Should set and get precio")
    void testPrecio() {
        BigDecimal precio = new BigDecimal("100.00");
        linea.setPrecio(precio);
        assertEquals(0, precio.compareTo(linea.getPrecio()));
    }

    @Test
    @DisplayName("Should set and get unidadMedida")
    void testUnidadMedida() {
        linea.setUnidadMedida("KGM");
        assertEquals("KGM", linea.getUnidadMedida());
    }

    @Test
    @DisplayName("Should set and get IGV fields")
    void testIgvFields() {
        BigDecimal igv = new BigDecimal("36.00");
        BigDecimal base = new BigDecimal("200.00");
        String tipo = "10";

        linea.setIgv(igv);
        linea.setIgvBaseImponible(base);
        linea.setIgvTipo(tipo);

        assertEquals(0, igv.compareTo(linea.getIgv()));
        assertEquals(0, base.compareTo(linea.getIgvBaseImponible()));
        assertEquals(tipo, linea.getIgvTipo());
    }

    @Test
    @DisplayName("Should set and get ISC fields")
    void testIscFields() {
        BigDecimal isc = new BigDecimal("10.00");
        BigDecimal base = new BigDecimal("100.00");
        String tipo = "01";

        linea.setIsc(isc);
        linea.setIscBaseImponible(base);
        linea.setIscTipo(tipo);

        assertEquals(0, isc.compareTo(linea.getIsc()));
        assertEquals(0, base.compareTo(linea.getIscBaseImponible()));
    }

    @Test
    @DisplayName("Should set and get ICB fields")
    void testIcbFields() {
        BigDecimal icb = new BigDecimal("1.50");
        linea.setIcb(icb);
        linea.setIcbAplica(true);

        assertEquals(0, icb.compareTo(linea.getIcb()));
        assertTrue(linea.isIcbAplica());
    }

    @Test
    @DisplayName("Should handle product codes")
    void testProductoCodes() {
        linea.setCodigoProducto("P001");
        linea.setCodigoProductoSunat("8101");
        linea.setCodigoProductoGS1("1234567890123");

        assertEquals("P001", linea.getCodigoProducto());
        assertEquals("8101", linea.getCodigoProductoSunat());
        assertEquals("1234567890123", linea.getCodigoProductoGS1());
    }

    @Test
    @DisplayName("Should handle price with and without taxes")
    void testPrecioConSinImpuestos() {
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setPrecioConImpuestos(false);

        assertEquals(0, new BigDecimal("100.00").compareTo(linea.getPrecio()));
        assertFalse(linea.isPrecioConImpuestos());
    }

    @Test
    @DisplayName("Should handle reference price")
    void testPrecioReferencia() {
        linea.setPrecioReferencia(new BigDecimal("120.00"));
        linea.setPrecioReferenciaTipo("01");

        assertEquals(0, new BigDecimal("120.00").compareTo(linea.getPrecioReferencia()));
        assertEquals("01", linea.getPrecioReferenciaTipo());
    }

    @Test
    @DisplayName("Should handle discounts list")
    void testDescuentos() {
        java.util.List<CargoDescuento> descuentos = Arrays.asList(
            new CargoDescuento("01", new BigDecimal("10.00"), new BigDecimal("0.05"), null),
            new CargoDescuento("01", new BigDecimal("5.00"), new BigDecimal("0.025"), null)
        );
        linea.setDescuentos(descuentos);

        assertEquals(2, linea.getDescuentos().size());
    }

    @Test
    @DisplayName("Should handle charges list")
    void testCargos() {
        java.util.List<CargoDescuento> cargos = Arrays.asList(
            new CargoDescuento("04", new BigDecimal("5.00"), null, null)
        );
        linea.setCargos(cargos);

        assertEquals(1, linea.getCargos().size());
    }

    @Test
    @DisplayName("Should set and get totalImpuestos")
    void testTotalImpuestos() {
        BigDecimal total = new BigDecimal("46.00");
        linea.setTotalImpuestos(total);
        assertEquals(0, total.compareTo(linea.getTotalImpuestos()));
    }

    @Test
    @DisplayName("Should set and get tax rates")
    void testTasas() {
        linea.setTasaIgv(new BigDecimal("0.18"));
        linea.setTasaIsc(new BigDecimal("0.10"));
        linea.setTasaIcb(new BigDecimal("0.015"));

        assertEquals(0, new BigDecimal("0.18").compareTo(linea.getTasaIgv()));
    }

    @Test
    @DisplayName("Should preserve precision in all BigDecimal fields")
    void testPrecision() {
        BigDecimal valor = new BigDecimal("123.456789");
        linea.setCantidad(valor);
        linea.setPrecio(valor);

        assertEquals(0, valor.compareTo(linea.getCantidad()));
        assertEquals(0, valor.compareTo(linea.getPrecio()));
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    void testValoresNulos() {
        assertNull(linea.getDescripcion());
        assertNull(linea.getCantidad());
        assertNull(linea.getPrecio());
        assertNull(linea.getIgv());
        assertFalse(linea.isIcbAplica());
    }

    @Test
    @DisplayName("Should support line item with all fields populated")
    void testLineaCompleta() {
        linea.setDescripcion("Producto A");
        linea.setCodigoProducto("P001");
        linea.setCantidad(new BigDecimal("10.00"));
        linea.setUnidadMedida("KGM");
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setIgv(new BigDecimal("36.00"));
        linea.setIgvBaseImponible(new BigDecimal("200.00"));
        linea.setIgvTipo("10");

        assertEquals("Producto A", linea.getDescripcion());
        assertEquals("P001", linea.getCodigoProducto());
        assertEquals(0, new BigDecimal("10.00").compareTo(linea.getCantidad()));
    }

    @Test
    @DisplayName("Should handle multiple updates to fields")
    void testMultiplesActualizaciones() {
        linea.setDescripcion("Producto A");
        linea.setDescripcion("Producto B");
        assertEquals("Producto B", linea.getDescripcion());

        linea.setCantidad(new BigDecimal("10.00"));
        linea.setCantidad(new BigDecimal("20.00"));
        assertEquals(0, new BigDecimal("20.00").compareTo(linea.getCantidad()));
    }
}
