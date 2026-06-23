package com.cna.ublkit.ubl.modelo.sunat.resumen;

/**
 * Comprobante afectado en el Resumen Diario.
 *
 * @param tipoComprobante Tipo de comprobante afectado (Catálogo 01).
 * @param serieNumero     Serie y número del comprobante.
 *
 * @since 0.1.0
 */
public record ComprobanteAfectadoResumen(
        String tipoComprobante,
        String serieNumero
) {
}
