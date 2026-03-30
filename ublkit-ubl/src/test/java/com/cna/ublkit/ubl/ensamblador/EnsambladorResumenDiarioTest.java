package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnsambladorResumenDiarioTest {

    @Test
    void ensamblar_aplicaFechaEmisionPorDefecto() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setFechaEmision(null);

        EnsambladorResumenDiario.ensamblar(resumen);

        assertNotNull(resumen.getFechaEmision());
        assertEquals(LocalDate.now(), resumen.getFechaEmision());
    }

    @Test
    void ensamblar_aplicaFechaEmisionComprobantesPorDefecto() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setFechaEmisionComprobantes(null);

        EnsambladorResumenDiario.ensamblar(resumen);

        assertEquals(resumen.getFechaEmision(), resumen.getFechaEmisionComprobantes());
    }

    @Test
    void ensamblar_aplicaNumeroPorDefecto() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setNumero(null);

        EnsambladorResumenDiario.ensamblar(resumen);

        assertEquals(1, resumen.getNumero());
    }

    @Test
    void ensamblar_aplicaMonedaPorDefecto() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setMoneda(null);

        EnsambladorResumenDiario.ensamblar(resumen);

        assertEquals("PEN", resumen.getMoneda());
    }

    @Test
    void ensamblar_propagaMonedaAComprobantes() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setMoneda("USD");
        resumen.getComprobantes().getFirst().comprobante().setMoneda(null);

        EnsambladorResumenDiario.ensamblar(resumen);

        assertEquals("USD", resumen.getComprobantes().getFirst().comprobante().getMoneda());
    }

    @Test
    void ensamblar_respetaMonedaExistenteEnComprobante() {
        ResumenDiario resumen = crearResumenSinDefectos();
        resumen.setMoneda("PEN");
        resumen.getComprobantes().getFirst().comprobante().setMoneda("USD");

        EnsambladorResumenDiario.ensamblar(resumen);

        assertEquals("USD", resumen.getComprobantes().getFirst().comprobante().getMoneda());
    }

    @Test
    void ensamblar_retornaMismaInstancia() {
        ResumenDiario resumen = crearResumenSinDefectos();
        ResumenDiario resultado = EnsambladorResumenDiario.ensamblar(resumen);
        assertSame(resumen, resultado);
    }

    private ResumenDiario crearResumenSinDefectos() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(new EmisorDocumento("20123456789", null, "EMPRESA S.A.C.", null, null));

        ComprobanteResumen comp = new ComprobanteResumen();
        comp.setTipoComprobante("03");
        comp.setSerieNumero("B001-1");
        comp.setMoneda("PEN");
        comp.setCliente(new ReceptorDocumento("1", "12345678", "JUAN", null, null));
        comp.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("118.00"), null,
                new BigDecimal("100.00"), null, null, null
        ));
        comp.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("18.00"), new BigDecimal("0.18"),
                null, null, null, null
        ));

        resumen.setComprobantes(List.of(new ItemResumenDiario("1", comp)));
        return resumen;
    }
}
