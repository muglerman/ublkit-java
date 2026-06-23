package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Cuota individual dentro de una forma de pago al crédito.
 *
 * @param importe   Monto de la cuota.
 * @param fechaPago Fecha de vencimiento de la cuota.
 *
 * @since 0.1.0
 */
public record CuotaDePago(
        BigDecimal importe,
        LocalDate fechaPago
) {
}
