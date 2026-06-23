package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import java.math.BigDecimal;

/**
 * Comprobante de Retención Electrónica (20).
 * Serie Rxxx. Régimen según Catálogo 23.
 *
 * @since 0.1.0
 */
public class ComprobanteRetencion extends BasePercepcionRetencion {

    /** Tipo de régimen de retención (Catálogo 23). */
    private String tipoRegimen;

    /** Importe total retenido. */
    private BigDecimal importeTotalRetenido;

    /** Importe total pagado. */
    private BigDecimal importeTotalPagado;

    public ComprobanteRetencion() {}

    public String getTipoRegimen() { return tipoRegimen; }
    public BigDecimal getImporteTotalRetenido() { return importeTotalRetenido; }
    public BigDecimal getImporteTotalPagado() { return importeTotalPagado; }

    public void setTipoRegimen(String tipoRegimen) { this.tipoRegimen = tipoRegimen; }
    public void setImporteTotalRetenido(BigDecimal importeTotalRetenido) { this.importeTotalRetenido = importeTotalRetenido; }
    public void setImporteTotalPagado(BigDecimal importeTotalPagado) { this.importeTotalPagado = importeTotalPagado; }
}
