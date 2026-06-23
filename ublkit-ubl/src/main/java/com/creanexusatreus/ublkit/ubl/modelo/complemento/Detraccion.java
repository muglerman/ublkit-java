package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Detracción asociada a una factura electrónica.
 * Obligatoria cuando {@code tipoOperacion} = "1001" (Catálogo 51).
 *
 * @param medioDePago       Medio de pago (Catálogo 59).
 * @param cuentaBancaria    Número de cuenta bancaria del Banco de la Nación.
 * @param tipoBienDetraido  Código del bien o servicio sujeto a detracción (Catálogo 54).
 * @param porcentaje        Porcentaje de detracción (ej. 0.12 para 12%).
 * @param monto             Monto de la detracción.
 *
 * @since 0.1.0
 */
public record Detraccion(
        String medioDePago,
        String cuentaBancaria,
        String tipoBienDetraido,
        BigDecimal porcentaje,
        BigDecimal monto
) {
}
