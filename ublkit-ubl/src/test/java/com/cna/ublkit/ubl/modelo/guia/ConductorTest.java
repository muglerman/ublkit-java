package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conductor Record Tests")
class ConductorTest {

    @Test
    @DisplayName("Should create Conductor for principal driver")
    void testConductorPrincipal() {
        Conductor conductor = new Conductor(
            "Principal",
            "1",
            "12345678",
            "Juan",
            "Pérez García",
            "LIC001"
        );

        assertEquals("Principal", conductor.tipo());
        assertEquals("1", conductor.tipoDocumentoIdentidad());
        assertEquals("12345678", conductor.numeroDocumentoIdentidad());
        assertEquals("Juan", conductor.nombres());
        assertEquals("Pérez García", conductor.apellidos());
        assertEquals("LIC001", conductor.licencia());
    }

    @Test
    @DisplayName("Should create Conductor for secondary driver")
    void testConductorSecundario() {
        Conductor conductor = new Conductor(
            "Secundario",
            "1",
            "87654321",
            "María",
            "López Rodríguez",
            "LIC002"
        );

        assertEquals("Secundario", conductor.tipo());
        assertEquals("87654321", conductor.numeroDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        Conductor conductor1 = new Conductor(
            "Principal", "1", "12345678", "Juan", "Pérez García", "LIC001"
        );

        Conductor conductor2 = new Conductor(
            "Principal", "1", "12345678", "Juan", "Pérez García", "LIC001"
        );

        assertEquals(conductor1, conductor2);
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Conductor conductor = new Conductor(
            "Principal", "1", "12345678", "Juan", "Pérez García", "LIC001"
        );

        String toString = conductor.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should support DNI type document (01)")
    void testTipoDniDocumento() {
        Conductor conductor = new Conductor(
            "Principal", "1", "12345678", "Juan", "Pérez", "LIC001"
        );

        assertEquals("1", conductor.tipoDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void testCamposNulos() {
        Conductor conductor = new Conductor(
            "Principal", "1", "12345678", null, null, null
        );

        assertEquals("Principal", conductor.tipo());
        assertNull(conductor.nombres());
        assertNull(conductor.apellidos());
    }
}
