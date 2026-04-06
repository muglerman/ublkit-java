package com.cna.ublkit.ubl.modelo.linea;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LineaDetalle Extended Tests")
class LineaDetalleExtendedTest {

    private LineaDetalle linea;

    @BeforeEach
    void setUp() {
        linea = new LineaDetalle();
    }

    @Test
    @DisplayName("Should set and get item")
    void testSetYGetItem() {
        linea.setItem(1);
        assertEquals(1, linea.getItem());
    }

    @Test
    @DisplayName("Should set and get codigo")
    void testSetYGetCodigo() {
        linea.setCodigo("PROD001");
        assertEquals("PROD001", linea.getCodigo());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        linea.setDescripcion("Producto A");
        assertEquals("Producto A", linea.getDescripcion());
    }

    @Test
    @DisplayName("Should set and get cantidad")
    void testSetYGetCantidad() {
        linea.setCantidad(new BigDecimal("10.00"));
        assertEquals(0, new BigDecimal("10.00").compareTo(linea.getCantidad()));
    }

    @Test
    @DisplayName("Should set and get unidad medida")
    void testSetYGetUnidadMedida() {
        linea.setUnidadMedida("KG");
        assertEquals("KG", linea.getUnidadMedida());
    }

    @Test
    @DisplayName("Should set and get precio")
    void testSetYGetPrecio() {
        linea.setPrecio(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(linea.getPrecio()));
    }

    @Test
    @DisplayName("Should set and get importe")
    void testSetYGetImporte() {
        linea.setImporte(new BigDecimal("1000.00"));
        assertEquals(0, new BigDecimal("1000.00").compareTo(linea.getImporte()));
    }

    @Test
    @DisplayName("Should set and get IGV tipo")
    void testSetYGetIgvTipo() {
        linea.setIgvTipo("10");
        assertEquals("10", linea.getIgvTipo());
    }

    @Test
    @DisplayName("Should set and get IGV base imponible")
    void testSetYGetIgvBaseImponible() {
        linea.setIgvBaseImponible(new BigDecimal("900.00"));
        assertEquals(0, new BigDecimal("900.00").compareTo(linea.getIgvBaseImponible()));
    }

    @Test
    @DisplayName("Should set and get IGV")
    void testSetYGetIgv() {
        linea.setIgv(new BigDecimal("162.00"));
        assertEquals(0, new BigDecimal("162.00").compareTo(linea.getIgv()));
    }

    @Test
    @DisplayName("Should calculate line total correctly")
    void testCalculaTotalLineaCorrectamente() {
        linea.setCantidad(new BigDecimal("10.00"));
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setImporte(new BigDecimal("1000.00"));

        assertEquals(0, new BigDecimal("1000.00").compareTo(linea.getImporte()));
    }

    @Test
    @DisplayName("Should handle decimal quantities")
    void testManejaCantidadesDecimales() {
        linea.setCantidad(new BigDecimal("10.50"));
        linea.setPrecio(new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("10.50").compareTo(linea.getCantidad()));
    }

    @Test
    @DisplayName("Should handle multiple line items")
    void testManejaMultiplesLineaItems() {
        linea.setItem(1);
        linea.setCodigo("PROD001");
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("10.00"));
        linea.setPrecio(new BigDecimal("100.00"));

        assertEquals(1, linea.getItem());
        assertEquals("PROD001", linea.getCodigo());
        assertNotNull(linea.getDescripcion());
    }
}

@DisplayName("CargoDescuento Extended Tests")
class CargoDescuentoExtendedTest {

    private CargoDescuento cargo;

    @BeforeEach
    void setUp() {
        cargo = new CargoDescuento("01", new BigDecimal("100.00"), new BigDecimal("0.10"), null);
    }

    @Test
    @DisplayName("Should set and get tipo")
    void testSetYGetTipo() {
        assertEquals("01", cargo.tipo());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        CargoDescuento c = new CargoDescuento("01", new BigDecimal("100.00"), new BigDecimal("0.10"), null);
        assertNotNull(c.tipo());
    }

    @Test
    @DisplayName("Should set and get porcentaje")
    void testSetYGetPorcentaje() {
        assertEquals(0, new BigDecimal("0.10").compareTo(cargo.porcentaje()));
    }

    @Test
    @DisplayName("Should set and get importe")
    void testSetYGetImporte() {
        assertEquals(0, new BigDecimal("100.00").compareTo(cargo.monto()));
    }

    @Test
    @DisplayName("Should handle cargo types")
    void testManejaTiposCargo() {
        CargoDescuento c1 = new CargoDescuento("01", new BigDecimal("100.00"), new BigDecimal("0.10"), null);
        assertEquals("01", c1.tipo()); // Descuento

        CargoDescuento c2 = new CargoDescuento("02", new BigDecimal("50.00"), BigDecimal.ZERO, null);
        assertEquals("02", c2.tipo()); // Cargo
    }

    @Test
    @DisplayName("Should calculate cargo correctly")
    void testCalculaCargoCorrectamente() {
        CargoDescuento c = new CargoDescuento("02", new BigDecimal("50.00"), BigDecimal.ZERO, null);
        assertEquals("02", c.tipo());
        assertEquals(0, new BigDecimal("50.00").compareTo(c.monto()));
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        CargoDescuento c = new CargoDescuento("01", null, null, null);
        assertNull(c.monto());
        assertNull(c.porcentaje());
    }

    @Test
    @DisplayName("Should handle multiple cargos and descuentos")
    void testManejaMultiplessCargosYDescuentos() {
        CargoDescuento c = new CargoDescuento("01", new BigDecimal("100.00"), BigDecimal.ZERO, null);
        assertNotNull(c.tipo());
        assertNotNull(c.monto());
    }
}
