package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorNotaCreditoTest {

    private final ValidadorNotaCredito validador = new ValidadorNotaCredito();

    @Test
    void validar_notaCreditoValida_sinErrores() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.esValido(), "Nota crédito válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-NC-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinSerie_retornaError() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        nota.setSerie(null);
        assertFalse(validador.validar(nota).esValido());
    }

    @Test
    void validar_serieInvalida_retornaError() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        nota.setSerie("X001");
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-NC-003".equals(i.codigo())));
    }

    @Test
    void validar_sinComprobanteAfectado_retornaError() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        nota.setComprobanteAfectadoSerieNumero(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-NC-009".equals(i.codigo())));
    }

    @Test
    void validar_sinSustento_retornaError() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        nota.setSustentoDescripcion(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-NC-010".equals(i.codigo())));
    }

    @Test
    void validar_sinTipoNota_retornaError() {
        BorradorNotaCredito nota = crearNotaCreditoValida();
        nota.setTipoNota(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-NC-011".equals(i.codigo())));
    }

    static BorradorNotaCredito crearNotaCreditoValida() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.now());
        nota.setMoneda("PEN");
        nota.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa", null, null));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Descuento concedido");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));

        return nota;
    }
}
