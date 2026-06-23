package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import com.cna.ublkit.core.modelo.TipoCambio;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Operación individual de cobro (percepción) o pago (retención).
 *
 * @param numeroOperacion Número secuencial de la operación.
 * @param fechaOperacion  Fecha del cobro o pago.
 * @param importeOperacion Importe del cobro o pago.
 * @param comprobante     Comprobante afectado por la operación.
 * @param tipoCambio      Tipo de cambio (si aplica moneda extranjera).
 *
 * @since 0.1.0
 */
public record OperacionPR(
        Integer numeroOperacion,
        LocalDate fechaOperacion,
        BigDecimal importeOperacion,
        ComprobanteAfectadoPR comprobante,
        TipoCambio tipoCambio
) {
}
