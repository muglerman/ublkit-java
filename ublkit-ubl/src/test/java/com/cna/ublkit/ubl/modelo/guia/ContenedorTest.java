package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Contenedor Record Tests")
class ContenedorTest {

    @Test
    @DisplayName("Should create Contenedor with numero and precinto")
    void testCrearContenedor() {
        Contenedor contenedor = new Contenedor("CONT001", "PREC001");

        assertEquals("CONT001", contenedor.numero());
        assertEquals("PREC001", contenedor.precinto());
    }

    @Test
    @DisplayName("Should create Contenedor with null precinto")
    void testContenedorSinPrecinto() {
        Contenedor contenedor = new Contenedor("CONT001", null);

        assertEquals("CONT001", contenedor.numero());
        assertNull(contenedor.precinto());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        Contenedor cont1 = new Contenedor("CONT001", "PREC001");
        Contenedor cont2 = new Contenedor("CONT001", "PREC001");

        assertEquals(cont1, cont2);
        assertEquals(cont1.hashCode(), cont2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct containers")
    void testDesigualdadPorDiferentesValores() {
        Contenedor cont1 = new Contenedor("CONT001", "PREC001");
        Contenedor cont2 = new Contenedor("CONT002", "PREC002");

        assertNotEquals(cont1, cont2);
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        Contenedor contenedor = new Contenedor("CONT001", "PREC001");

        String toString = contenedor.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should preserve numero format")
    void testPreservaNumeroFormato() {
        String numero = "CONTAINER-12345-XYZ";
        Contenedor contenedor = new Contenedor(numero, "PREC001");

        assertEquals(numero, contenedor.numero());
    }

    @Test
    @DisplayName("Should preserve precinto format")
    void testPreservaPrecinto() {
        String precinto = "SEAL-IATA-98765";
        Contenedor contenedor = new Contenedor("CONT001", precinto);

        assertEquals(precinto, contenedor.precinto());
    }

    @Test
    @DisplayName("Should handle null values")
    void testValoresNulos() {
        Contenedor contenedor = new Contenedor(null, null);

        assertNull(contenedor.numero());
        assertNull(contenedor.precinto());
    }
}
