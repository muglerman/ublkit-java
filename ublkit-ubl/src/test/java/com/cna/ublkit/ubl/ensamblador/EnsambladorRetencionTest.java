package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnsambladorRetencion Class Tests")
class EnsambladorRetencionTest {

    private ComprobanteRetencion retencion;

    @BeforeEach
    void setUp() {
        retencion = new ComprobanteRetencion();
    }

    @Test
    @DisplayName("Should ensamblar retencion with default moneda")
    void testEnsambladorRetencionDefaultMoneda() {
        retencion.setMoneda(null);
        retencion.setTipoRegimen("01");

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertEquals("PEN", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should preserve existing moneda")
    void testPreservaMonedaExistente() {
        retencion.setMoneda("USD");
        retencion.setTipoRegimen("01");

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertEquals("USD", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should handle blank moneda as null")
    void testManejaMonedalienEncBlanco() {
        retencion.setMoneda("   ");
        retencion.setTipoRegimen("01");

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertEquals("PEN", resultado.getMoneda());
    }

    @Test
    @DisplayName("Should calculate importeTotalPagado from operacion")
    void testCalculaImporteTotalPagado() {
        retencion.setMoneda("PEN");
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("10000.00"));
        retencion.setOperacion(operacion);
        retencion.setTipoRegimen("01");

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertNotNull(resultado.getImporteTotalPagado());
        assertEquals(0, new BigDecimal("10000.00").compareTo(resultado.getImporteTotalPagado()));
    }

    @Test
    @DisplayName("Should calculate importeTotalRetenido from tasa")
    void testCalculaImporteTotalRetenido() {
        retencion.setMoneda("PEN");
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("10000.00"));
        retencion.setOperacion(operacion);
        retencion.setTipoRegimenPorcentaje(new BigDecimal("0.06"));

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertNotNull(resultado.getImporteTotalRetenido());
        assertEquals(0, new BigDecimal("600.00").compareTo(resultado.getImporteTotalRetenido()));
    }

    @Test
    @DisplayName("Should handle null operacion")
    void testManejaOperacionNula() {
        retencion.setMoneda("PEN");
        retencion.setOperacion(null);
        retencion.setTipoRegimen("01");

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertNotNull(resultado.getImporteTotalPagado());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getImporteTotalPagado()));
    }

    @Test
    @DisplayName("Should handle null tasa")
    void testManejaTasaNula() {
        retencion.setMoneda("PEN");
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("10000.00"));
        retencion.setOperacion(operacion);
        retencion.setTipoRegimenPorcentaje(null);

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertNotNull(resultado.getImporteTotalRetenido());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getImporteTotalRetenido()));
    }

    @Test
    @DisplayName("Should return null for null retencion")
    void testRetornaNuloParaRetencionNula() {
        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(null);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Should preserve existing importeTotalPagado")
    void testPreservaImporteTotalPagadoExistente() {
        retencion.setMoneda("PEN");
        retencion.setImporteTotalPagado(new BigDecimal("5000.00"));
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("10000.00"));
        retencion.setOperacion(operacion);

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertEquals(0, new BigDecimal("5000.00").compareTo(resultado.getImporteTotalPagado()));
    }

    @Test
    @DisplayName("Should preserve existing importeTotalRetenido")
    void testPreservaImporteTotalRetenidoExistente() {
        retencion.setMoneda("PEN");
        retencion.setImporteTotalRetenido(new BigDecimal("300.00"));
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("10000.00"));
        retencion.setOperacion(operacion);

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertEquals(0, new BigDecimal("300.00").compareTo(resultado.getImporteTotalRetenido()));
    }

    @Test
    @DisplayName("Should apply rounding mode HALF_UP")
    void testAplicaRedondeoHalfUp() {
        retencion.setMoneda("PEN");
        OperacionPR operacion = new OperacionPR();
        operacion.setImporteOperacion(new BigDecimal("333.333"));
        retencion.setOperacion(operacion);
        retencion.setTipoRegimenPorcentaje(new BigDecimal("0.06"));

        ComprobanteRetencion resultado = EnsambladorRetencion.ensamblar(retencion);

        assertNotNull(resultado);
        assertNotNull(resultado.getImporteTotalRetenido());
        assertEquals(2, resultado.getImporteTotalRetenido().scale());
    }
}
