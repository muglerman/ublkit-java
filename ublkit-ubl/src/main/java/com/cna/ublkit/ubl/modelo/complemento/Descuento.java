package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Descuento global aplicado al documento.
 *
 * @param tipo      Código de tipo de descuento (Catálogo 53).
 * @param monto     Monto del descuento.
 * @param factor    Factor o porcentaje del descuento (ej. 0.05 para 5%).
 * @param montoBase Monto base sobre el que se calcula el descuento.
 *
 * @since 0.1.0
 */
public record Descuento(
        String tipo,
        BigDecimal monto,
        BigDecimal factor,
        BigDecimal montoBase
) {
}
