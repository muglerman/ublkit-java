package com.cna.ublkit.ubl.modelo.total;

import java.math.BigDecimal;

/**
 * Importes totales del documento electrónico.
 * Equivale a {@code cac:LegalMonetaryTotal} en UBL 2.1.
 *
 * @param importe             Monto total pagable ({@code cbc:PayableAmount}).
 * @param importeSinImpuestos Monto total sin impuestos ({@code cbc:LineExtensionAmount}).
 * @param importeConImpuestos Monto total con impuestos ({@code cbc:TaxInclusiveAmount}).
 * @param anticipos           Descuento global por anticipos ({@code cbc:PrepaidAmount}).
 * @param descuentos          Descuentos globales ({@code cbc:AllowanceTotalAmount}).
 *
 * @since 0.1.0
 */
public record TotalImporte(
        BigDecimal importe,
        BigDecimal importeSinImpuestos,
        BigDecimal importeConImpuestos,
        BigDecimal anticipos,
        BigDecimal descuentos
) {
}
