package com.cna.ublkit.validation.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IncidenciaValidacion Tests")
class IncidenciaValidacionTest {

    @Test
    @DisplayName("Should create incident with ERROR severity")
    void crear_incidencia_conError() {
        IncidenciaValidacion incidencia = new IncidenciaValidacion("ERR-001", "Error message", SeveridadValidacion.ERROR);

        assertEquals("ERR-001", incidencia.codigo());
        assertEquals("Error message", incidencia.mensaje());
        assertEquals(SeveridadValidacion.ERROR, incidencia.severidad());
    }

    @Test
    @DisplayName("Should create incident with ADVERTENCIA severity")
    void crear_incidencia_conAdvertencia() {
        IncidenciaValidacion incidencia = new IncidenciaValidacion("ADV-001", "Warning message", SeveridadValidacion.ADVERTENCIA);

        assertEquals("ADV-001", incidencia.codigo());
        assertEquals("Warning message", incidencia.mensaje());
        assertEquals(SeveridadValidacion.ADVERTENCIA, incidencia.severidad());
    }

    @Test
    @DisplayName("Should allow null codigo")
    void crear_incidencia_codigoNulo() {
        IncidenciaValidacion incidencia = new IncidenciaValidacion(null, "Message", SeveridadValidacion.ERROR);
        assertNull(incidencia.codigo());
    }

    @Test
    @DisplayName("Should allow null mensaje")
    void crear_incidencia_mensajeNulo() {
        IncidenciaValidacion incidencia = new IncidenciaValidacion("ERR-001", null, SeveridadValidacion.ERROR);
        assertNull(incidencia.mensaje());
    }

    @Test
    @DisplayName("Should allow empty string codigo")
    void crear_incidencia_codigoVacio() {
        IncidenciaValidacion incidencia = new IncidenciaValidacion("", "Message", SeveridadValidacion.ERROR);
        assertEquals("", incidencia.codigo());
    }

    @Test
    @DisplayName("Two incidents with same values should be equal")
    void incidencia_iguales() {
        IncidenciaValidacion inc1 = new IncidenciaValidacion("ERR-001", "Message", SeveridadValidacion.ERROR);
        IncidenciaValidacion inc2 = new IncidenciaValidacion("ERR-001", "Message", SeveridadValidacion.ERROR);

        assertEquals(inc1, inc2);
    }

    @Test
    @DisplayName("Two incidents with different codigo should not be equal")
    void incidencia_diferentesCodigo() {
        IncidenciaValidacion inc1 = new IncidenciaValidacion("ERR-001", "Message", SeveridadValidacion.ERROR);
        IncidenciaValidacion inc2 = new IncidenciaValidacion("ERR-002", "Message", SeveridadValidacion.ERROR);

        assertNotEquals(inc1, inc2);
    }

    @Test
    @DisplayName("Two incidents with different severidad should not be equal")
    void incidencia_diferentesSeveridad() {
        IncidenciaValidacion inc1 = new IncidenciaValidacion("ERR-001", "Message", SeveridadValidacion.ERROR);
        IncidenciaValidacion inc2 = new IncidenciaValidacion("ERR-001", "Message", SeveridadValidacion.ADVERTENCIA);

        assertNotEquals(inc1, inc2);
    }

    @Test
    @DisplayName("Should support long codigo values")
    void incidencia_codigoLargo() {
        String codigoLargo = "SUNAT-FACTURA-2024-001-EXTENDED";
        IncidenciaValidacion incidencia = new IncidenciaValidacion(codigoLargo, "Message", SeveridadValidacion.ERROR);
        assertEquals(codigoLargo, incidencia.codigo());
    }

    @Test
    @DisplayName("Should support long mensaje values")
    void incidencia_mensajeLargo() {
        String mensajeLargo = "This is a very long validation message that contains detailed information about the validation failure. " +
                "It may contain multiple sentences and should be properly stored and retrieved.";
        IncidenciaValidacion incidencia = new IncidenciaValidacion("ERR-001", mensajeLargo, SeveridadValidacion.ERROR);
        assertEquals(mensajeLargo, incidencia.mensaje());
    }
}
