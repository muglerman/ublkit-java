package com.cna.ublkit.ubl.modelo.linea;

import java.math.BigDecimal;

/**
 * Cargo o descuento aplicado a una línea de detalle o al documento global.
 * Equivale a {@code cac:AllowanceCharge} en UBL 2.1.
 *
 * @param tipo       Código de tipo de cargo/descuento (Catálogo 53).
 * @param monto      Monto del cargo o descuento.
 * @param porcentaje Factor o porcentaje aplicado.
 * @param serieNumero Serie y número del documento referido (si aplica).
 *
 * @since 0.1.0
 */
public record CargoDescuento(
        String tipo,
        BigDecimal monto,
        BigDecimal porcentaje,
        String serieNumero
) {
}
