package com.cna.ublkit.validation.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SeveridadValidacion Tests")
class SeveridadValidacionTest {

    @Test
    @DisplayName("Should have ERROR severity level")
    void severidad_ERROR_exists() {
        assertNotNull(SeveridadValidacion.ERROR);
        assertEquals("ERROR", SeveridadValidacion.ERROR.name());
    }

    @Test
    @DisplayName("Should have ADVERTENCIA severity level")
    void severidad_ADVERTENCIA_exists() {
        assertNotNull(SeveridadValidacion.ADVERTENCIA);
        assertEquals("ADVERTENCIA", SeveridadValidacion.ADVERTENCIA.name());
    }

    @Test
    @DisplayName("Should have exactly 2 severity levels")
    void severidad_tamanio() {
        SeveridadValidacion[] valores = SeveridadValidacion.values();
        assertEquals(2, valores.length);
    }

    @Test
    @DisplayName("Should compare ERROR and ADVERTENCIA as different")
    void severidad_diferentesValore() {
        assertNotEquals(SeveridadValidacion.ERROR, SeveridadValidacion.ADVERTENCIA);
    }

    @Test
    @DisplayName("Should support valueOf() for ERROR")
    void severidad_valueOf_ERROR() {
        assertEquals(SeveridadValidacion.ERROR, SeveridadValidacion.valueOf("ERROR"));
    }

    @Test
    @DisplayName("Should support valueOf() for ADVERTENCIA")
    void severidad_valueOf_ADVERTENCIA() {
        assertEquals(SeveridadValidacion.ADVERTENCIA, SeveridadValidacion.valueOf("ADVERTENCIA"));
    }

    @Test
    @DisplayName("Should throw exception for invalid severity")
    void severidad_valueOf_invalido() {
        assertThrows(IllegalArgumentException.class, () -> SeveridadValidacion.valueOf("CRITICO"));
    }

    @Test
    @DisplayName("Severity levels should be consistent")
    void severidad_consistencia() {
        SeveridadValidacion error = SeveridadValidacion.ERROR;
        SeveridadValidacion advertencia = SeveridadValidacion.ADVERTENCIA;
        assertEquals(error, error);
        assertEquals(advertencia, advertencia);
    }
}
