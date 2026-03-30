package com.cna.ublkit.ubl.modelo.sunat.resumen;

import java.math.BigDecimal;

/**
 * Valores de venta de un comprobante en el Resumen Diario.
 *
 * @param importeTotal Importe total del comprobante.
 * @param otrosCargos  Otros cargos.
 * @param gravado      Monto gravado.
 * @param exonerado    Monto exonerado.
 * @param inafecto     Monto inafecto.
 * @param gratuito     Monto gratuito.
 *
 * @since 0.1.0
 */
public record ComprobanteValorVenta(
        BigDecimal importeTotal,
        BigDecimal otrosCargos,
        BigDecimal gravado,
        BigDecimal exonerado,
        BigDecimal inafecto,
        BigDecimal gratuito
) {
}
