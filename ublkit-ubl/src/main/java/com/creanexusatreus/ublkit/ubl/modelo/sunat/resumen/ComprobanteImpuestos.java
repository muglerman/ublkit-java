package com.cna.ublkit.ubl.modelo.sunat.resumen;

import java.math.BigDecimal;

/**
 * Impuestos de un comprobante en el Resumen Diario.
 *
 * @param igv    IGV del comprobante.
 * @param tasaIgv Tasa de IGV (ej. 0.18).
 * @param icb    ICBPER del comprobante.
 * @param isc    ISC del comprobante.
 * @param ivap   IVAP del comprobante.
 * @param otros  Otros tributos.
 *
 * @since 0.1.0
 */
public record ComprobanteImpuestos(
        BigDecimal igv,
        BigDecimal tasaIgv,
        BigDecimal icb,
        BigDecimal isc,
        BigDecimal ivap,
        BigDecimal otros
) {
}
