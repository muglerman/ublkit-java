package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorNotaDebitoTest {

    private final ValidadorNotaDebito validador = new ValidadorNotaDebito();

    @Test
    void validar_notaDebitoValida_sinErrores() {
        BorradorNotaDebito nota = crearNotaDebitoValida();
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.esValido(), "Nota débito válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-ND-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinSerie_retornaError() {
        BorradorNotaDebito nota = crearNotaDebitoValida();
        nota.setSerie(null);
        assertFalse(validador.validar(nota).esValido());
    }

    @Test
    void validar_sinComprobanteAfectado_retornaError() {
        BorradorNotaDebito nota = crearNotaDebitoValida();
        nota.setComprobanteAfectadoSerieNumero(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-ND-009".equals(i.codigo())));
    }

    @Test
    void validar_sinTipoNota_retornaError() {
        BorradorNotaDebito nota = crearNotaDebitoValida();
        nota.setTipoNota(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-ND-011".equals(i.codigo())));
    }

    @Test
    void validar_sinSustento_retornaError() {
        BorradorNotaDebito nota = crearNotaDebitoValida();
        nota.setSustentoDescripcion(null);
        ResultadoValidacion resultado = validador.validar(nota);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-ND-010".equals(i.codigo())));
    }

    static BorradorNotaDebito crearNotaDebitoValida() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.now());
        nota.setMoneda("PEN");
        nota.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa", null, null));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Intereses por mora");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Intereses");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("50.00"));
        nota.setDetalles(List.of(linea));

        return nota;
    }
}
