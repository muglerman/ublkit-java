package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Comprobante afectado por una operación de percepción o retención.
 *
 * @param moneda          Moneda del comprobante afectado.
 * @param tipoComprobante Tipo de comprobante (Catálogo 01).
 * @param serieNumero     Serie y número del comprobante.
 * @param fechaEmision    Fecha de emisión del comprobante afectado.
 * @param importeTotal    Importe total del comprobante afectado.
 *
 * @since 0.1.0
 */
public record ComprobanteAfectadoPR(
        String moneda,
        String tipoComprobante,
        String serieNumero,
        LocalDate fechaEmision,
        BigDecimal importeTotal
) {
}
