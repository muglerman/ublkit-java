package com.cna.ublkit.ubl.modelo.sunat.resumen;

import java.math.BigDecimal;

/**
 * Percepción asociada a un comprobante en el Resumen Diario.
 *
 * @param codReg   Código de régimen de percepción (Catálogo 22).
 * @param tasa     Tasa de percepción.
 * @param mtoBase  Monto base de percepción.
 * @param mto      Monto de percepción.
 * @param mtoTotal Monto total incluida percepción.
 *
 * @since 0.1.0
 */
public record PercepcionResumen(
        String codReg,
        BigDecimal tasa,
        BigDecimal mtoBase,
        BigDecimal mto,
        BigDecimal mtoTotal
) {
}
