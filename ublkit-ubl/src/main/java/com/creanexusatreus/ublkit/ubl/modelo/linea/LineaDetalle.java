package com.cna.ublkit.ubl.modelo.linea;

import java.math.BigDecimal;
import java.util.List;

/**
 * Línea de detalle de un documento de venta (factura, boleta, nota de crédito/débito).
 * <p>
 * Cada instancia representa una línea {@code cac:InvoiceLine} / {@code cac:CreditNoteLine}
 * / {@code cac:DebitNoteLine} en el XML UBL 2.1.
 * </p>
 * <p>
 * Campos como {@code igv}, {@code igvBaseImponible}, {@code totalImpuestos} y
 * {@code precioReferencia} son calculados automáticamente por el ensamblador/enricher
 * en etapas posteriores.
 * </p>
 *
 * @since 0.1.0
 */
public class LineaDetalle {

    // ── Descripción e identificación ──────────────────────────────

    private String descripcion;
    private String codigoProducto;
    private String codigoProductoSunat;
    private String codigoProductoGS1;

    // ── Cantidad y medida ─────────────────────────────────────────

    private String unidadMedida;
    private BigDecimal cantidad;

    // ── Precios ───────────────────────────────────────────────────

    private BigDecimal precio;
    private boolean precioConImpuestos;
    private BigDecimal precioReferencia;
    private String precioReferenciaTipo;

    // ── IGV ───────────────────────────────────────────────────────

    private BigDecimal tasaIgv;
    private BigDecimal igv;
    private BigDecimal igvBaseImponible;
    private String igvTipo;

    // ── ICBPER ────────────────────────────────────────────────────

    private BigDecimal tasaIcb;
    private BigDecimal icb;
    private boolean icbAplica;

    // ── ISC ───────────────────────────────────────────────────────

    private BigDecimal tasaIsc;
    private BigDecimal isc;
    private BigDecimal iscBaseImponible;
    private String iscTipo;

    // ── Descuentos y Cargos por línea ────────────────────────────

    private List<CargoDescuento> descuentos;
    private List<CargoDescuento> cargos;

    // ── Totales ──────────────────────────────────────────────────

    private BigDecimal totalImpuestos;

    // ── Constructor vacío ────────────────────────────────────────

    public LineaDetalle() {
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getDescripcion() { return descripcion; }
    public String getCodigoProducto() { return codigoProducto; }
    public String getCodigoProductoSunat() { return codigoProductoSunat; }
    public String getCodigoProductoGS1() { return codigoProductoGS1; }
    public String getUnidadMedida() { return unidadMedida; }
    public BigDecimal getCantidad() { return cantidad; }
    public BigDecimal getPrecio() { return precio; }
    public boolean isPrecioConImpuestos() { return precioConImpuestos; }
    public BigDecimal getPrecioReferencia() { return precioReferencia; }
    public String getPrecioReferenciaTipo() { return precioReferenciaTipo; }
    public BigDecimal getTasaIgv() { return tasaIgv; }
    public BigDecimal getIgv() { return igv; }
    public BigDecimal getIgvBaseImponible() { return igvBaseImponible; }
    public String getIgvTipo() { return igvTipo; }
    public BigDecimal getTasaIcb() { return tasaIcb; }
    public BigDecimal getIcb() { return icb; }
    public boolean isIcbAplica() { return icbAplica; }
    public BigDecimal getTasaIsc() { return tasaIsc; }
    public BigDecimal getIsc() { return isc; }
    public BigDecimal getIscBaseImponible() { return iscBaseImponible; }
    public String getIscTipo() { return iscTipo; }
    public List<CargoDescuento> getDescuentos() { return descuentos; }
    public List<CargoDescuento> getCargos() { return cargos; }
    public BigDecimal getTotalImpuestos() { return totalImpuestos; }

    // ── Setters ──────────────────────────────────────────────────

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public void setCodigoProductoSunat(String codigoProductoSunat) { this.codigoProductoSunat = codigoProductoSunat; }
    public void setCodigoProductoGS1(String codigoProductoGS1) { this.codigoProductoGS1 = codigoProductoGS1; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public void setPrecioConImpuestos(boolean precioConImpuestos) { this.precioConImpuestos = precioConImpuestos; }
    public void setPrecioReferencia(BigDecimal precioReferencia) { this.precioReferencia = precioReferencia; }
    public void setPrecioReferenciaTipo(String precioReferenciaTipo) { this.precioReferenciaTipo = precioReferenciaTipo; }
    public void setTasaIgv(BigDecimal tasaIgv) { this.tasaIgv = tasaIgv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    public void setIgvBaseImponible(BigDecimal igvBaseImponible) { this.igvBaseImponible = igvBaseImponible; }
    public void setIgvTipo(String igvTipo) { this.igvTipo = igvTipo; }
    public void setTasaIcb(BigDecimal tasaIcb) { this.tasaIcb = tasaIcb; }
    public void setIcb(BigDecimal icb) { this.icb = icb; }
    public void setIcbAplica(boolean icbAplica) { this.icbAplica = icbAplica; }
    public void setTasaIsc(BigDecimal tasaIsc) { this.tasaIsc = tasaIsc; }
    public void setIsc(BigDecimal isc) { this.isc = isc; }
    public void setIscBaseImponible(BigDecimal iscBaseImponible) { this.iscBaseImponible = iscBaseImponible; }
    public void setIscTipo(String iscTipo) { this.iscTipo = iscTipo; }
    public void setDescuentos(List<CargoDescuento> descuentos) { this.descuentos = descuentos; }
    public void setCargos(List<CargoDescuento> cargos) { this.cargos = cargos; }
    public void setTotalImpuestos(BigDecimal totalImpuestos) { this.totalImpuestos = totalImpuestos; }
}
