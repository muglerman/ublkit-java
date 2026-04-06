package com.cna.ublkit.ubl.modelo.sunat.baja;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ItemBaja Class Tests")
class ItemBajaTest {

    @Test
    @DisplayName("Should create empty ItemBaja")
    void testCrearItemBajaVacio() {
        ItemBaja item = new ItemBaja();
        assertNotNull(item);
    }

    @Test
    @DisplayName("Should set and get serieNumero")
    void testSerieNumero() {
        ItemBaja item = new ItemBaja();
        item.setSerieNumero("F001-100");
        assertEquals("F001-100", item.getSerieNumero());
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testTipoDocumento() {
        ItemBaja item = new ItemBaja();
        item.setTipoDocumento("01");
        assertEquals("01", item.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get motivo")
    void testMotivo() {
        ItemBaja item = new ItemBaja();
        item.setMotivo("01");
        assertEquals("01", item.getMotivo());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testDescripcion() {
        ItemBaja item = new ItemBaja();
        item.setDescripcion("Anulación por error");
        assertEquals("Anulación por error", item.getDescripcion());
    }

    @Test
    @DisplayName("Should handle complete item baja")
    void testItemBajaCompleto() {
        ItemBaja item = new ItemBaja();
        item.setSerieNumero("F001-100");
        item.setTipoDocumento("01");
        item.setMotivo("01");
        item.setDescripcion("Anulación por error");

        assertEquals("F001-100", item.getSerieNumero());
        assertEquals("01", item.getTipoDocumento());
        assertEquals("01", item.getMotivo());
    }

    @Test
    @DisplayName("Should support different motivos")
    void testDiferentesMotivos() {
        String[] motivos = {"01", "02", "03"};
        for (String motivo : motivos) {
            ItemBaja item = new ItemBaja();
            item.setMotivo(motivo);
            assertEquals(motivo, item.getMotivo());
        }
    }

    @Test
    @DisplayName("Should handle null values")
    void testValoresNulos() {
        ItemBaja item = new ItemBaja();
        assertNull(item.getSerieNumero());
        assertNull(item.getTipoDocumento());
        assertNull(item.getMotivo());
    }

    @Test
    @DisplayName("Should handle multiple updates")
    void testMultiplesActualizaciones() {
        ItemBaja item = new ItemBaja();
        item.setSerieNumero("F001-100");
        item.setSerieNumero("F001-101");
        assertEquals("F001-101", item.getSerieNumero());
    }
}
