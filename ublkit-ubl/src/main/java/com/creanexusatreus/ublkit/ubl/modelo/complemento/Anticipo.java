package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Anticipo realizado por el cliente previo a la prestación del servicio o entrega del bien.
 *
 * @param tipo                   Tipo de anticipo (Catálogo 53: "04", "05", "06").
 * @param comprobanteSerieNumero Serie y número del comprobante de anticipo (ej. "F123-4").
 * @param comprobanteTipo        Tipo de documento del comprobante de anticipo (Catálogo 12).
 * @param monto                  Monto prepagado o anticipado.
 *
 * @since 0.1.0
 */
public record Anticipo(
        String tipo,
        String comprobanteSerieNumero,
        String comprobanteTipo,
        BigDecimal monto
) {
}
