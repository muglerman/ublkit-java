package com.cna.ublkit.ubl.modelo.sunat.baja;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Reversion Class Tests")
class ReversionTest {

    private Reversion reversion;

    @BeforeEach
    void setUp() {
        reversion = new Reversion();
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        reversion.setNumeroDocumento("F001-100");
        assertEquals("F001-100", reversion.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get fecha")
    void testSetYGetFecha() {
        LocalDate fecha = LocalDate.of(2026, 4, 5);
        reversion.setFecha(fecha);
        assertEquals(fecha, reversion.getFecha());
    }

    @Test
    @DisplayName("Should set and get tipo")
    void testSetYGetTipo() {
        reversion.setTipo("01");
        assertEquals("01", reversion.getTipo());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        reversion.setDescripcion("Error en factura");
        assertEquals("Error en factura", reversion.getDescripcion());
    }

    @Test
    @DisplayName("Should handle multiple reversiones")
    void testManejaMultiplesReversiones() {
        reversion.setNumeroDocumento("F001-100");
        reversion.setFecha(LocalDate.of(2026, 4, 5));
        assertNotNull(reversion.getNumeroDocumento());
        assertNotNull(reversion.getFecha());
    }
}

@DisplayName("ItemBaja Full Extended Tests")
class ItemBajaExtendedTest {

    private ItemBaja item;

    @BeforeEach
    void setUp() {
        item = new ItemBaja();
    }

    @Test
    @DisplayName("Should set and get all properties")
    void testFijaYObtieneTodosCampos() {
        item.setTipoDocumento("01");
        item.setSerieDocumento("F001");
        item.setNumeroDocumento(100);
        item.setMotivoBaja("01");

        assertEquals("01", item.getTipoDocumento());
        assertEquals("F001", item.getSerieDocumento());
        assertEquals(100, item.getNumeroDocumento());
        assertEquals("01", item.getMotivoBaja());
    }

    @Test
    @DisplayName("Should handle multiple items")
    void testManejaMultiplesItems() {
        ItemBaja item1 = new ItemBaja();
        item1.setTipoDocumento("01");
        item1.setNumeroDocumento(1);

        ItemBaja item2 = new ItemBaja();
        item2.setTipoDocumento("03");
        item2.setNumeroDocumento(2);

        List<ItemBaja> items = Arrays.asList(item1, item2);
        assertEquals(2, items.size());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        item.setTipoDocumento(null);
        item.setMotivoBaja(null);
        assertNull(item.getTipoDocumento());
        assertNull(item.getMotivoBaja());
    }
}

@DisplayName("ComunicacionBaja Extended Tests")
class ComunicacionBajaExtendedTest {

    private ComunicacionBaja comunicacion;

    @BeforeEach
    void setUp() {
        comunicacion = new ComunicacionBaja();
    }

    @Test
    @DisplayName("Should set and get numero")
    void testSetYGetNumero() {
        comunicacion.setNumero(1);
        assertEquals(1, comunicacion.getNumero());
    }

    @Test
    @DisplayName("Should set and get fecha emision comprobantes")
    void testSetYGetFechaEmisionComprobantes() {
        LocalDate fecha = LocalDate.of(2026, 4, 5);
        comunicacion.setFechaEmisionComprobantes(fecha);
        assertEquals(fecha, comunicacion.getFechaEmisionComprobantes());
    }

    @Test
    @DisplayName("Should set and get items")
    void testSetYGetItems() {
        ItemBaja item = new ItemBaja();
        item.setTipoDocumento("01");
        List<ItemBaja> items = Arrays.asList(item);
        comunicacion.setItems(items);

        assertNotNull(comunicacion.getItems());
        assertEquals(1, comunicacion.getItems().size());
    }

    @Test
    @DisplayName("Should handle multiple items in comunicacion")
    void testManejaMultiplesItemsEnComunicacion() {
        ItemBaja item1 = new ItemBaja();
        ItemBaja item2 = new ItemBaja();
        ItemBaja item3 = new ItemBaja();

        comunicacion.setItems(Arrays.asList(item1, item2, item3));
        assertEquals(3, comunicacion.getItems().size());
    }

    @Test
    @DisplayName("Should inherit from DocumentoSunat")
    void testHeredaDeDocumentoSunat() {
        comunicacion.setMoneda("PEN");
        comunicacion.setFechaEmision(LocalDate.of(2026, 4, 6));

        assertEquals("PEN", comunicacion.getMoneda());
        assertEquals(LocalDate.of(2026, 4, 6), comunicacion.getFechaEmision());
    }
}
