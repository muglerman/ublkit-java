package com.cna.ublkit.validation.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResultadoValidacion Tests")
class ResultadoValidacionTest {

    @Test
    @DisplayName("Should create empty resultado")
    void crear_resultadoVacio() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        assertTrue(resultado.esValido());
        assertEquals(0, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Should be valid when no errors added")
    void esValido_sinErrores() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ADV-001", "Warning", SeveridadValidacion.ADVERTENCIA));

        assertTrue(resultado.esValido(), "Should be valid with only warnings");
    }

    @Test
    @DisplayName("Should be invalid when ERROR added")
    void esValido_conError() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ERR-001", "Error message", SeveridadValidacion.ERROR));

        assertFalse(resultado.esValido());
    }

    @Test
    @DisplayName("Should be invalid when multiple errors added")
    void esValido_conMultiplesErrores() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ERR-001", "Error 1", SeveridadValidacion.ERROR));
        resultado.agregar(new IncidenciaValidacion("ERR-002", "Error 2", SeveridadValidacion.ERROR));
        resultado.agregar(new IncidenciaValidacion("ADV-001", "Warning", SeveridadValidacion.ADVERTENCIA));

        assertFalse(resultado.esValido());
        assertEquals(3, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Should accumulate incidents without errors")
    void agregar_soloAdvertencias() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ADV-001", "Warning 1", SeveridadValidacion.ADVERTENCIA));
        resultado.agregar(new IncidenciaValidacion("ADV-002", "Warning 2", SeveridadValidacion.ADVERTENCIA));
        resultado.agregar(new IncidenciaValidacion("ADV-003", "Warning 3", SeveridadValidacion.ADVERTENCIA));

        assertTrue(resultado.esValido());
        assertEquals(3, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Should return unmodifiable list of incidents")
    void getIncidencias_inmodificable() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ADV-001", "Warning", SeveridadValidacion.ADVERTENCIA));

        List<IncidenciaValidacion> incidencias = resultado.getIncidencias();
        assertThrows(UnsupportedOperationException.class, () -> incidencias.add(
                new IncidenciaValidacion("ADV-002", "Warning 2", SeveridadValidacion.ADVERTENCIA)
        ));
    }

    @Test
    @DisplayName("Should preserve all incident details")
    void agregar_preservaDetalles() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        IncidenciaValidacion incidencia = new IncidenciaValidacion("ERR-001", "Detailed error message", SeveridadValidacion.ERROR);
        resultado.agregar(incidencia);

        List<IncidenciaValidacion> incidencias = resultado.getIncidencias();
        assertEquals(1, incidencias.size());
        assertEquals("ERR-001", incidencias.get(0).codigo());
        assertEquals("Detailed error message", incidencias.get(0).mensaje());
        assertEquals(SeveridadValidacion.ERROR, incidencias.get(0).severidad());
    }

    @Test
    @DisplayName("Should handle mixed error and warning scenarios")
    void agregar_mezclaErroresYAdvertencias() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ADV-001", "Warning 1", SeveridadValidacion.ADVERTENCIA));
        resultado.agregar(new IncidenciaValidacion("ERR-001", "Error 1", SeveridadValidacion.ERROR));
        resultado.agregar(new IncidenciaValidacion("ADV-002", "Warning 2", SeveridadValidacion.ADVERTENCIA));
        resultado.agregar(new IncidenciaValidacion("ERR-002", "Error 2", SeveridadValidacion.ERROR));

        assertFalse(resultado.esValido());
        assertEquals(4, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Should return multiple incidents in order")
    void getIncidencias_orden() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        IncidenciaValidacion inc1 = new IncidenciaValidacion("001", "First", SeveridadValidacion.ADVERTENCIA);
        IncidenciaValidacion inc2 = new IncidenciaValidacion("002", "Second", SeveridadValidacion.ADVERTENCIA);
        IncidenciaValidacion inc3 = new IncidenciaValidacion("003", "Third", SeveridadValidacion.ADVERTENCIA);

        resultado.agregar(inc1);
        resultado.agregar(inc2);
        resultado.agregar(inc3);

        List<IncidenciaValidacion> incidencias = resultado.getIncidencias();
        assertEquals(3, incidencias.size());
        assertEquals("001", incidencias.get(0).codigo());
        assertEquals("002", incidencias.get(1).codigo());
        assertEquals("003", incidencias.get(2).codigo());
    }

    @Test
    @DisplayName("Multiple calls to getIncidencias should return same content")
    void getIncidencias_consistencia() {
        ResultadoValidacion resultado = new ResultadoValidacion();
        resultado.agregar(new IncidenciaValidacion("ERR-001", "Error", SeveridadValidacion.ERROR));

        List<IncidenciaValidacion> list1 = resultado.getIncidencias();
        List<IncidenciaValidacion> list2 = resultado.getIncidencias();

        assertEquals(list1.size(), list2.size());
        assertEquals(list1.get(0).codigo(), list2.get(0).codigo());
    }
}
