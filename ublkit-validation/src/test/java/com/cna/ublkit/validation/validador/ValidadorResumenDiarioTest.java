package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorResumenDiarioTest {

    private final ValidadorResumenDiario validador = new ValidadorResumenDiario();

    @Test
    void validar_resumenValido_sinErrores() {
        ResumenDiario resumen = crearResumenValido();
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.esValido(), "Resumen válido no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-RC-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinFechaEmision_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setFechaEmision(null);
        assertFalse(validador.validar(resumen).esValido());
    }

    @Test
    void validar_sinFechaComprobantes_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setFechaEmisionComprobantes(null);
        assertFalse(validador.validar(resumen).esValido());
    }

    @Test
    void validar_referenceDateMayorQueIssueDate_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 14));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 15));
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RC-004".equals(i.codigo())));
    }

    @Test
    void validar_sinNumero_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setNumero(null);
        assertFalse(validador.validar(resumen).esValido());
    }

    @Test
    void validar_sinEmisor_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setEmisor(null);
        assertFalse(validador.validar(resumen).esValido());
    }

    @Test
    void validar_emisorRucInvalido_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setEmisor(new EmisorDocumento("12345", "Empresa", "Empresa", null, null));
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RC-008".equals(i.codigo())));
    }

    @Test
    void validar_sinComprobantes_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        resumen.setComprobantes(null);
        assertFalse(validador.validar(resumen).esValido());
    }

    @Test
    void validar_tipoOperacionInvalido_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        ComprobanteResumen comp = resumen.getComprobantes().getFirst().comprobante();
        resumen.setComprobantes(List.of(new ItemResumenDiario("9", comp)));
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RC-011".equals(i.codigo())));
    }

    @Test
    void validar_tipoComprobanteinvalido_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        ComprobanteResumen comp = resumen.getComprobantes().getFirst().comprobante();
        comp.setTipoComprobante("01");
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RC-014".equals(i.codigo())));
    }

    @Test
    void validar_notaSinComprobanteAfectado_retornaError() {
        ResumenDiario resumen = crearResumenValido();
        ComprobanteResumen comp = resumen.getComprobantes().getFirst().comprobante();
        comp.setTipoComprobante("07");
        comp.setComprobanteAfectado(null);
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RC-018".equals(i.codigo())));
    }

    @Test
    void validar_notaConComprobanteAfectado_sinError() {
        ResumenDiario resumen = crearResumenValido();
        ComprobanteResumen comp = resumen.getComprobantes().getFirst().comprobante();
        comp.setTipoComprobante("07");
        comp.setComprobanteAfectado(new ComprobanteAfectadoResumen("03", "B001-1"));
        ResultadoValidacion resultado = validador.validar(resumen);
        assertTrue(resultado.esValido());
    }

    static ResumenDiario crearResumenValido() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));

        ComprobanteResumen comprobante = new ComprobanteResumen();
        comprobante.setTipoComprobante("03");
        comprobante.setSerieNumero("B001-1");
        comprobante.setMoneda("PEN");
        comprobante.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        comprobante.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("118.00"), null,
                new BigDecimal("100.00"), null, null, null));
        comprobante.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("18.00"), new BigDecimal("0.18"),
                null, null, null, null));

        resumen.setComprobantes(List.of(new ItemResumenDiario("1", comprobante)));
        return resumen;
    }
}
