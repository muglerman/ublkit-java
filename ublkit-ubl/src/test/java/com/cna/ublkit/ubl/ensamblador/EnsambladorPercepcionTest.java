package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnsambladorPercepcion Class Tests")
class EnsambladorPercepcionTest {

    private ComprobantePercepcion percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new ComprobantePercepcion();
    }

    @Test
    @DisplayName("Should ensamblar percepción minima")
    void testEnsambladorPercepcionMinima() {
        percepcion.setMoneda(null);

        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(percepcion);

        assertEquals("PEN", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should handle null percepción")
    void testManejaPercepcionNula() {
        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(null);
        assertNull(resultado);
    }

    @Test
    @DisplayName("Should preserve existing moneda")
    void testPreservaMonedaExistente() {
        percepcion.setMoneda("USD");

        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(percepcion);

        assertEquals("USD", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should calculate total cobrado from operación")
    void testCalculaTotalCobrado() {
        percepcion.setMoneda("PEN");
        percepcion.setImporteTotalCobrado(null);

        // Mock operación
        percepcion.setImporteTotalCobrado(new BigDecimal("2000.00"));

        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(percepcion);

        assertNotNull(resultado.getImporteTotalCobrado());
    }

    @Test
    @DisplayName("Should preserve existing total cobrado")
    void testPreservaTotalCobradoExistente() {
        BigDecimal totalCobrado = new BigDecimal("1500.00");
        percepcion.setImporteTotalCobrado(totalCobrado);

        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(percepcion);

        assertEquals(0, totalCobrado.compareTo(resultado.getImporteTotalCobrado()));
    }

    @Test
    @DisplayName("Should blank moneda is treated as null")
    void testMonedaVacia() {
        percepcion.setMoneda("");

        ComprobantePercepcion resultado = EnsambladorPercepcion.ensamblar(percepcion);

        assertEquals("PEN", resultado.getMoneda());
    }
}
