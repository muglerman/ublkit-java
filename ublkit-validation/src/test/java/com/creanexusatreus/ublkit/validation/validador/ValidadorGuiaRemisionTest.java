package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorGuiaRemisionTest {

    private final ValidadorGuiaRemision validador = new ValidadorGuiaRemision();

    @Test
    void validar_guiaValida_sinErrores() {
        BorradorGuiaRemision guia = crearGuiaValida();
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.esValido(), "Guía válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-GRE-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinSerie_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setSerie(null);
        assertFalse(validador.validar(guia).esValido());
    }

    @Test
    void validar_sinFecha_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setFechaEmision(null);
        assertFalse(validador.validar(guia).esValido());
    }

    @Test
    void validar_sinRemitente_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setRemitente(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-007".equals(i.codigo())));
    }

    @Test
    void validar_sinDatosEnvio_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setEnvio(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-008".equals(i.codigo())));
    }

    @Test
    void validar_sinDetalles_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setDetalles(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-009".equals(i.codigo())));
    }

    static BorradorGuiaRemision crearGuiaValida() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.now());
        guia.setRemitente(new EmisorDocumento("20000000001", "Empresa Remitente S.A.C.", "Empresa Remitente", null, null));

        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("10.00"));
        envio.setPesoTotalUnidadMedida("KGM");
        guia.setEnvio(envio);

        guia.setDetalles(List.of(new LineaGuia("NIU", BigDecimal.ONE, "Producto transportado", null, null, null)));

        return guia;
    }
}
