package com.cna.ublkit.ubl.modelo.complemento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GuiaRelacionada Record Tests")
class GuiaRelacionadaTest {

    @Test
    @DisplayName("Should create GuiaRelacionada with serie and tipo")
    void testCrearGuiaRelacionada() {
        GuiaRelacionada guia = new GuiaRelacionada(
            "T001-123",
            "09"
        );

        assertEquals("T001-123", guia.serieNumero());
        assertEquals("09", guia.tipoDocumento());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        GuiaRelacionada guia1 = new GuiaRelacionada("T001-123", "09");
        GuiaRelacionada guia2 = new GuiaRelacionada("T001-123", "09");

        assertEquals(guia1, guia2);
        assertEquals(guia1.hashCode(), guia2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct guias")
    void testDesigualdadPorDiferentesValores() {
        GuiaRelacionada guia1 = new GuiaRelacionada("T001-123", "09");
        GuiaRelacionada guia2 = new GuiaRelacionada("T001-124", "31");

        assertNotEquals(guia1, guia2);
    }

    @Test
    @DisplayName("Should support Guía de Remisión (09)")
    void testTipoGuiaRemision() {
        GuiaRelacionada guia = new GuiaRelacionada("T001-100", "09");
        assertEquals("09", guia.tipoDocumento());
    }

    @Test
    @DisplayName("Should support Guía de Remisión en Tránsito (31)")
    void testTipoGuiaTransito() {
        GuiaRelacionada guia = new GuiaRelacionada("T001-200", "31");
        assertEquals("31", guia.tipoDocumento());
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        GuiaRelacionada guia = new GuiaRelacionada("T001-123", "09");
        String toString = guia.toString();

        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values")
    void testValoresNulos() {
        GuiaRelacionada guia = new GuiaRelacionada(null, null);
        assertNull(guia.serieNumero());
        assertNull(guia.tipoDocumento());
    }
}
