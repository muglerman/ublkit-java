package com.cna.ublkit.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa la tasa de tipo de cambio aplicable a una operación o documento.
 *
 * @param fecha fecha de aplicación del tipo de cambio
 * @param valor valor monetario del tipo de cambio
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public record TipoCambio(
        LocalDate fecha,
        BigDecimal valor
) {
}
