package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Percepción asociada a una factura electrónica.
 * Obligatoria cuando {@code tipoOperacion} = "2001" (Catálogo 51).
 *
 * @param tipo        Tipo de percepción (Catálogo 53).
 * @param montoBase   Importe sin impuestos del documento.
 * @param porcentaje  Tasa de percepción (establecida por el tipo).
 * @param monto       Monto de percepción calculado (montoBase × porcentaje).
 * @param montoTotal  Total con percepción incluida (montoBase + monto).
 *
 * @since 0.1.0
 */
public record Percepcion(
        String tipo,
        BigDecimal montoBase,
        BigDecimal porcentaje,
        BigDecimal monto,
        BigDecimal montoTotal
) {
}
