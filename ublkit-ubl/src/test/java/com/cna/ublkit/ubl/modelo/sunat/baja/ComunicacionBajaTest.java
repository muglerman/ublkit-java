package com.cna.ublkit.ubl.modelo.sunat.baja;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ComunicacionBaja Class Tests")
class ComunicacionBajaTest {

    private ComunicacionBaja comunicacion;

    @BeforeEach
    void setUp() {
        comunicacion = new ComunicacionBaja();
    }

    @Test
    @DisplayName("Should create empty ComunicacionBaja")
    void testCrearComunicacionVacia() {
        assertNotNull(comunicacion);
        assertNull(comunicacion.getComprobantes());
    }

    @Test
    @DisplayName("Should set and get comprobantes")
    void testComprobantes() {
        ItemBaja item1 = new ItemBaja();
        ItemBaja item2 = new ItemBaja();

        comunicacion.setComprobantes(Arrays.asList(item1, item2));

        assertEquals(2, comunicacion.getComprobantes().size());
    }

    @Test
    @DisplayName("Should handle single item")
    void testUnItemBaja() {
        ItemBaja item = new ItemBaja();
        comunicacion.setComprobantes(Arrays.asList(item));

        assertEquals(1, comunicacion.getComprobantes().size());
    }

    @Test
    @DisplayName("Should handle empty list")
    void testListaVacia() {
        comunicacion.setComprobantes(java.util.Collections.emptyList());

        assertTrue(comunicacion.getComprobantes().isEmpty());
    }

    @Test
    @DisplayName("Should handle null comprobantes")
    void testComprobantesNulo() {
        comunicacion.setComprobantes(null);
        assertNull(comunicacion.getComprobantes());
    }

    @Test
    @DisplayName("Should update comprobantes multiple times")
    void testActualizacionesMultiples() {
        ItemBaja item1 = new ItemBaja();
        comunicacion.setComprobantes(Arrays.asList(item1));
        assertEquals(1, comunicacion.getComprobantes().size());

        ItemBaja item2 = new ItemBaja();
        comunicacion.setComprobantes(Arrays.asList(item1, item2));
        assertEquals(2, comunicacion.getComprobantes().size());
    }

    @Test
    @DisplayName("Should inherit from DocumentoSunat")
    void testHeredaDeDocumentoSunat() {
        comunicacion.setSerie("RA");
        comunicacion.setNumero(1);

        assertEquals("RA", comunicacion.getSerie());
        assertEquals(1, comunicacion.getNumero());
    }

    @Test
    @DisplayName("Should handle many items")
    void testMuchosItems() {
        java.util.List<ItemBaja> items = new java.util.ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(new ItemBaja());
        }
        comunicacion.setComprobantes(items);

        assertEquals(100, comunicacion.getComprobantes().size());
    }
}
