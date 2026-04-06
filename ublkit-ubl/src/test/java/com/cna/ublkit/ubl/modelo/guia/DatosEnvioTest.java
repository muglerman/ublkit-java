package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DatosEnvio Class Tests")
class DatosEnvioTest {

    @Test
    @DisplayName("Should create empty DatosEnvio")
    void testCrearDatosEnvioVacio() {
        DatosEnvio datos = new DatosEnvio();
        assertNotNull(datos);
        assertNull(datos.getTipoTraslado());
    }

    @Test
    @DisplayName("Should set and get tipoTraslado")
    void testTipoTraslado() {
        DatosEnvio datos = new DatosEnvio();
        datos.setTipoTraslado("01");
        assertEquals("01", datos.getTipoTraslado());
    }

    @Test
    @DisplayName("Should set and get motivoTraslado")
    void testMotivoTraslado() {
        DatosEnvio datos = new DatosEnvio();
        datos.setMotivoTraslado("Venta");
        assertEquals("Venta", datos.getMotivoTraslado());
    }

    @Test
    @DisplayName("Should set and get pesoTotal")
    void testPesoTotal() {
        DatosEnvio datos = new DatosEnvio();
        java.math.BigDecimal peso = new java.math.BigDecimal("1000.00");
        datos.setPesoTotal(peso);
        assertEquals(0, peso.compareTo(datos.getPesoTotal()));
    }

    @Test
    @DisplayName("Should set and get pesoTotalUnidadMedida")
    void testPesoTotalUnidadMedida() {
        DatosEnvio datos = new DatosEnvio();
        datos.setPesoTotalUnidadMedida("KGM");
        assertEquals("KGM", datos.getPesoTotalUnidadMedida());
    }

    @Test
    @DisplayName("Should set and get numeroDeBultos")
    void testNumeroDeBultos() {
        DatosEnvio datos = new DatosEnvio();
        datos.setNumeroDeBultos(50);
        assertEquals(50, datos.getNumeroDeBultos());
    }

    @Test
    @DisplayName("Should set and get fechaTraslado")
    void testFechaTraslado() {
        DatosEnvio datos = new DatosEnvio();
        java.time.LocalDate fecha = java.time.LocalDate.of(2024, 12, 31);
        datos.setFechaTraslado(fecha);
        assertEquals(fecha, datos.getFechaTraslado());
    }

    @Test
    @DisplayName("Should set and get contenedores")
    void testContenedores() {
        DatosEnvio datos = new DatosEnvio();
        java.util.List<Contenedor> contenedores = java.util.Arrays.asList(
            new Contenedor("CONT001", "PREC001")
        );
        datos.setContenedores(contenedores);
        assertEquals(1, datos.getContenedores().size());
    }

    @Test
    @DisplayName("Should set and get choferes")
    void testChoferes() {
        DatosEnvio datos = new DatosEnvio();
        java.util.List<Conductor> choferes = java.util.Arrays.asList(
            new Conductor("Principal", "1", "12345678", "Juan", "Pérez", "LIC001")
        );
        datos.setChoferes(choferes);
        assertEquals(1, datos.getChoferes().size());
    }

    @Test
    @DisplayName("Should set and get vehiculo")
    void testVehiculo() {
        DatosEnvio datos = new DatosEnvio();
        Vehiculo vehiculo = new Vehiculo("ABC-1234", "TUC001", "AUTH001", "03", null);
        datos.setVehiculo(vehiculo);
        assertEquals(vehiculo, datos.getVehiculo());
    }

    @Test
    @DisplayName("Should handle multiple updates")
    void testMultiplesActualizaciones() {
        DatosEnvio datos = new DatosEnvio();
        datos.setTipoTraslado("01");
        datos.setTipoTraslado("02");
        assertEquals("02", datos.getTipoTraslado());

        datos.setNumeroDeBultos(10);
        datos.setNumeroDeBultos(50);
        assertEquals(50, datos.getNumeroDeBultos());
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    void testValoresNulos() {
        DatosEnvio datos = new DatosEnvio();
        assertNull(datos.getTipoTraslado());
        assertNull(datos.getContenedores());
        assertNull(datos.getChoferes());
    }
}
