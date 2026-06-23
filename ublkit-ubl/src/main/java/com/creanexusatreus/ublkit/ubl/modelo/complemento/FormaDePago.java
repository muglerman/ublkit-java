package com.cna.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import java.util.List;

/**
 * Forma de pago del documento (Contado o Crédito).
 *
 * @param tipo   "Contado" o "Credito".
 * @param total  Monto total de la forma de pago.
 * @param cuotas Lista de cuotas (solo aplica si tipo = "Credito").
 *
 * @since 0.1.0
 */
public record FormaDePago(
        String tipo,
        BigDecimal total,
        List<CuotaDePago> cuotas
) {
}
