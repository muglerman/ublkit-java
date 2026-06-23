package com.cna.ublkit.ubl.modelo.sunat.resumen;

/**
 * Ítem del Resumen Diario (operación + comprobante).
 *
 * @param tipoOperacion Código de operación (Catálogo 19).
 * @param comprobante   Datos del comprobante declarado.
 *
 * @since 0.1.0
 */
public record ItemResumenDiario(
        String tipoOperacion,
        ComprobanteResumen comprobante
) {
}
