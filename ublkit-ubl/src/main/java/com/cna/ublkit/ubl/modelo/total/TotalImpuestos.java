package com.cna.ublkit.ubl.modelo.total;

import java.math.BigDecimal;

/**
 * Desglose completo de impuestos del documento.
 * Equivale a {@code cac:TaxTotal} en UBL 2.1.
 *
 * @param total                    Suma total de todos los impuestos.
 * @param gravadoImporte           Monto IGV gravado.
 * @param gravadoBaseImponible     Base imponible gravada.
 * @param exoneradoImporte         Monto exonerado.
 * @param exoneradoBaseImponible   Base imponible exonerada.
 * @param inafectoImporte          Monto inafecto.
 * @param inafectoBaseImponible    Base imponible inafecta.
 * @param gratuitoImporte          Monto gratuito.
 * @param gratuitoBaseImponible    Base imponible gratuita.
 * @param exportacionImporte       Monto de exportación.
 * @param exportacionBaseImponible Base imponible de exportación.
 * @param ivapImporte              Monto IVAP.
 * @param ivapBaseImponible        Base imponible IVAP.
 * @param icbImporte               Monto ICBPER (bolsas plásticas).
 * @param iscImporte               Monto ISC.
 * @param iscBaseImponible         Base imponible ISC.
 *
 * @since 0.1.0
 */
public record TotalImpuestos(
        BigDecimal total,
        BigDecimal gravadoImporte,
        BigDecimal gravadoBaseImponible,
        BigDecimal exoneradoImporte,
        BigDecimal exoneradoBaseImponible,
        BigDecimal inafectoImporte,
        BigDecimal inafectoBaseImponible,
        BigDecimal gratuitoImporte,
        BigDecimal gratuitoBaseImponible,
        BigDecimal exportacionImporte,
        BigDecimal exportacionBaseImponible,
        BigDecimal ivapImporte,
        BigDecimal ivapBaseImponible,
        BigDecimal icbImporte,
        BigDecimal iscImporte,
        BigDecimal iscBaseImponible
) {
}
