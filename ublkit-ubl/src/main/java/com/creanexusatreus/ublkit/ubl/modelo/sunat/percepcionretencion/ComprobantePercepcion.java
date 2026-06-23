package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import java.math.BigDecimal;

/**
 * Comprobante de Percepción Electrónica (40).
 * Serie Pxxx. Régimen según Catálogo 22.
 *
 * @since 0.1.0
 */
public class ComprobantePercepcion extends BasePercepcionRetencion {

    /** Tipo de régimen de percepción (Catálogo 22). */
    private String tipoRegimen;

    /** Importe total percibido. */
    private BigDecimal importeTotalPercibido;

    /** Importe total cobrado. */
    private BigDecimal importeTotalCobrado;

    public ComprobantePercepcion() {}

    public String getTipoRegimen() { return tipoRegimen; }
    public BigDecimal getImporteTotalPercibido() { return importeTotalPercibido; }
    public BigDecimal getImporteTotalCobrado() { return importeTotalCobrado; }

    public void setTipoRegimen(String tipoRegimen) { this.tipoRegimen = tipoRegimen; }
    public void setImporteTotalPercibido(BigDecimal importeTotalPercibido) { this.importeTotalPercibido = importeTotalPercibido; }
    public void setImporteTotalCobrado(BigDecimal importeTotalCobrado) { this.importeTotalCobrado = importeTotalCobrado; }
}
