package com.cna.ublkit.testkit.fixture;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provee instancias pre-configuradas de {@link ResumenDiario}
 * para pruebas unitarias y de integración.
 *
 * @since 0.1.0
 */
public final class ResumenesDiarioEjemplo {

    private ResumenesDiarioEjemplo() {}

    /**
     * Resumen diario mínimo con una boleta gravada.
     */
    public static ResumenDiario resumenMinimoGravado() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(emisorEstandar());

        ComprobanteResumen comprobante = new ComprobanteResumen();
        comprobante.setTipoComprobante("03");
        comprobante.setSerieNumero("B001-1");
        comprobante.setMoneda("PEN");
        comprobante.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        comprobante.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("118.00"),
                null,
                new BigDecimal("100.00"),
                null,
                null,
                null
        ));
        comprobante.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("18.00"),
                new BigDecimal("0.18"),
                null, null, null, null
        ));

        resumen.setComprobantes(List.of(
                new ItemResumenDiario("1", comprobante)
        ));

        return resumen;
    }

    /**
     * Resumen diario con múltiples boletas (gravadas y exoneradas).
     */
    public static ResumenDiario resumenMultiple() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(3);
        resumen.setMoneda("PEN");
        resumen.setEmisor(emisorEstandar());

        // Boleta gravada
        ComprobanteResumen boletaGravada = new ComprobanteResumen();
        boletaGravada.setTipoComprobante("03");
        boletaGravada.setSerieNumero("B001-1");
        boletaGravada.setMoneda("PEN");
        boletaGravada.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        boletaGravada.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("236.00"), null,
                new BigDecimal("200.00"), null, null, null
        ));
        boletaGravada.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("36.00"), new BigDecimal("0.18"),
                null, null, null, null
        ));

        // Boleta exonerada
        ComprobanteResumen boletaExonerada = new ComprobanteResumen();
        boletaExonerada.setTipoComprobante("03");
        boletaExonerada.setSerieNumero("B001-2");
        boletaExonerada.setMoneda("PEN");
        boletaExonerada.setCliente(new ReceptorDocumento("1", "87654321", "MARIA LOPEZ", null, null));
        boletaExonerada.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("50.00"), null,
                null, new BigDecimal("50.00"), null, null
        ));
        boletaExonerada.setImpuestos(new ComprobanteImpuestos(
                BigDecimal.ZERO, new BigDecimal("0.18"),
                null, null, null, null
        ));

        // Nota de crédito de boleta (anulación)
        ComprobanteResumen notaCredito = new ComprobanteResumen();
        notaCredito.setTipoComprobante("07");
        notaCredito.setSerieNumero("B001-1");
        notaCredito.setMoneda("PEN");
        notaCredito.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        notaCredito.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("118.00"), null,
                new BigDecimal("100.00"), null, null, null
        ));
        notaCredito.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("18.00"), new BigDecimal("0.18"),
                null, null, null, null
        ));
        notaCredito.setComprobanteAfectado(new ComprobanteAfectadoResumen("03", "B001-1"));

        resumen.setComprobantes(List.of(
                new ItemResumenDiario("1", boletaGravada),
                new ItemResumenDiario("1", boletaExonerada),
                new ItemResumenDiario("3", notaCredito)
        ));

        return resumen;
    }

    /**
     * Resumen diario con percepción.
     */
    public static ResumenDiario resumenConPercepcion() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(5);
        resumen.setMoneda("PEN");
        resumen.setEmisor(emisorEstandar());

        ComprobanteResumen comprobante = new ComprobanteResumen();
        comprobante.setTipoComprobante("03");
        comprobante.setSerieNumero("B001-10");
        comprobante.setMoneda("PEN");
        comprobante.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        comprobante.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("1180.00"), null,
                new BigDecimal("1000.00"), null, null, null
        ));
        comprobante.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("180.00"), new BigDecimal("0.18"),
                null, null, null, null
        ));
        comprobante.setPercepcion(new PercepcionResumen(
                "01", new BigDecimal("2.00"),
                new BigDecimal("1180.00"), new BigDecimal("23.60"),
                new BigDecimal("1203.60")
        ));

        resumen.setComprobantes(List.of(
                new ItemResumenDiario("1", comprobante)
        ));

        return resumen;
    }

    private static EmisorDocumento emisorEstandar() {
        return new EmisorDocumento(
                "20123456789",
                "EMPRESA PRUEBA S.A.C.",
                "EMPRESA PRUEBA S.A.C.",
                null,
                null
        );
    }
}
