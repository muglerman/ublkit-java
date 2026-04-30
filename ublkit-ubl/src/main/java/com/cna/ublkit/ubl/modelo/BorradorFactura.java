package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;

import java.time.LocalDate;
import java.util.List;

/**
 * Borrador de Factura Electrónica (01) o Boleta de Venta Electrónica (03).
 * <p>
 * Ambos documentos comparten la misma estructura UBL 2.1 ({@code Invoice}).
 * La diferencia normativa está en la serie (Fxxx vs Bxxx), el tipo de comprobante
 * (Catálogo 01: "01" vs "03") y las reglas del receptor.
 * </p>
 * <p>
 * El campo {@code tipoComprobante} se puede deducir automáticamente de la serie
 * si no se especifica explícitamente.
 * </p>
 *
 * @since 0.1.0
 */
public final class BorradorFactura extends DocumentoBase {


    /** Tipo de operación según Catálogo 51 (ej. "0101" venta interna). */
    private String tipoOperacion;

    /** Fecha de vencimiento del comprobante. */
    private LocalDate fechaVencimiento;

    /** Observaciones o notas adicionales. */
    private String observaciones;

    /** Forma de pago (Contado o Crédito con cuotas). */
    private FormaDePago formaDePago;

    /** Total del importe del comprobante. */
    private TotalImporte totalImporte;

    /** Dirección de entrega (opcional). */
    private Direccion direccionEntrega;

    /** Detracción asociada (solo aplica a facturas con tipoOperacion 1001). */
    private Detraccion detraccion;

    /** Percepción asociada (solo aplica a facturas con tipoOperacion 2001). */
    private Percepcion percepcion;

    /** Anticipos asociados al comprobante. */
    private List<Anticipo> anticipos;

    /** Descuentos globales. */
    private List<Descuento> descuentos;

    /** Guía de remisión embebida (Factura-Guía). */
    private GuiaEmbebida guiaEmbebida;

    public BorradorFactura() {
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getTipoOperacion() { return tipoOperacion; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public String getObservaciones() { return observaciones; }
    public FormaDePago getFormaDePago() { return formaDePago; }
    public TotalImporte getTotalImporte() { return totalImporte; }
    public Direccion getDireccionEntrega() { return direccionEntrega; }
    public Detraccion getDetraccion() { return detraccion; }
    public Percepcion getPercepcion() { return percepcion; }
    public List<Anticipo> getAnticipos() { return anticipos; }
    public List<Descuento> getDescuentos() { return descuentos; }
    public GuiaEmbebida getGuiaEmbebida() { return guiaEmbebida; }

    // ── Setters ──────────────────────────────────────────────────

    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setFormaDePago(FormaDePago formaDePago) { this.formaDePago = formaDePago; }
    public void setTotalImporte(TotalImporte totalImporte) { this.totalImporte = totalImporte; }
    public void setDireccionEntrega(Direccion direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public void setDetraccion(Detraccion detraccion) { this.detraccion = detraccion; }
    public void setPercepcion(Percepcion percepcion) { this.percepcion = percepcion; }
    public void setAnticipos(List<Anticipo> anticipos) { this.anticipos = anticipos; }
    public void setDescuentos(List<Descuento> descuentos) { this.descuentos = descuentos; }
    public void setGuiaEmbebida(GuiaEmbebida guiaEmbebida) { this.guiaEmbebida = guiaEmbebida; }
}
