package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.ubl.modelo.total.TotalImporte;

/**
 * Borrador de Nota de Débito Electrónica (08).
 * <p>
 * Incrementa el importe de una factura o boleta previamente emitida.
 * El {@code tipoNota} corresponde al Catálogo 10 de SUNAT.
 * </p>
 *
 * @since 0.1.0
 */
public final class BorradorNotaDebito extends DocumentoBase {

    /** Tipo de nota de débito (Catálogo 10). */
    private String tipoNota;

    /** Serie y número del comprobante afectado (ej. "F001-123"). */
    private String comprobanteAfectadoSerieNumero;

    /** Tipo del comprobante afectado (Catálogo 01). */
    private String comprobanteAfectadoTipo;

    /** Texto sustentatorio para la emisión de la nota. */
    private String sustentoDescripcion;

    /** Total del importe de la nota. */
    private TotalImporte totalImporte;

    public BorradorNotaDebito() {
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getTipoNota() { return tipoNota; }
    public String getComprobanteAfectadoSerieNumero() { return comprobanteAfectadoSerieNumero; }
    public String getComprobanteAfectadoTipo() { return comprobanteAfectadoTipo; }
    public String getSustentoDescripcion() { return sustentoDescripcion; }
    public TotalImporte getTotalImporte() { return totalImporte; }

    // ── Setters ──────────────────────────────────────────────────

    public void setTipoNota(String tipoNota) { this.tipoNota = tipoNota; }
    public void setComprobanteAfectadoSerieNumero(String comprobanteAfectadoSerieNumero) { this.comprobanteAfectadoSerieNumero = comprobanteAfectadoSerieNumero; }
    public void setComprobanteAfectadoTipo(String comprobanteAfectadoTipo) { this.comprobanteAfectadoTipo = comprobanteAfectadoTipo; }
    public void setSustentoDescripcion(String sustentoDescripcion) { this.sustentoDescripcion = sustentoDescripcion; }
    public void setTotalImporte(TotalImporte totalImporte) { this.totalImporte = totalImporte; }
}
