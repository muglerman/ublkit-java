package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.core.modelo.TipoCambio;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Clase base sellada para todos los documentos tributarios electrónicos de venta.
 * <p>
 * Consolida los campos comunes de {@code Document} y {@code SalesDocument} de xbuilder
 * en una sola jerarquía limpia. Solo los tres tipos de documento de venta permitidos
 * por SUNAT pueden extender esta clase.
 * </p>
 *
 * @since 0.1.0
 */
public abstract sealed class DocumentoBase
        permits BorradorFactura, BorradorNotaCredito, BorradorNotaDebito {

    // ── Identificación ───────────────────────────────────────────

    private String serie;
    private Integer numero;
    private String moneda;
    private LocalDate fechaEmision;
    private LocalTime horaEmision;

    // ── Actores ──────────────────────────────────────────────────

    private EmisorDocumento emisor;
    private ReceptorDocumento receptor;
    private FirmanteDocumento firmante;

    // ── Tasas globales ───────────────────────────────────────────

    private BigDecimal tasaIgv;
    private BigDecimal tasaIvap;
    private BigDecimal tasaIcb;

    // ── Detalles ─────────────────────────────────────────────────

    private List<LineaDetalle> detalles;

    // ── Leyendas ─────────────────────────────────────────────────

    private Map<String, String> leyendas;

    // ── Referencias ──────────────────────────────────────────────

    private TipoCambio tipoCambio;
    private String ordenDeCompra;
    private List<GuiaRelacionada> guias;
    private List<DocumentoRelacionado> documentosRelacionados;
    private List<CargoDescuento> cargos;
    private TotalImpuestos totalImpuestos;

    // ── Constructor protegido ────────────────────────────────────

    protected DocumentoBase() {
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getSerie() { return serie; }
    public Integer getNumero() { return numero; }
    public String getMoneda() { return moneda; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public LocalTime getHoraEmision() { return horaEmision; }
    public EmisorDocumento getEmisor() { return emisor; }
    public ReceptorDocumento getReceptor() { return receptor; }
    public FirmanteDocumento getFirmante() { return firmante; }
    public BigDecimal getTasaIgv() { return tasaIgv; }
    public BigDecimal getTasaIvap() { return tasaIvap; }
    public BigDecimal getTasaIcb() { return tasaIcb; }
    public List<LineaDetalle> getDetalles() { return detalles; }
    public Map<String, String> getLeyendas() { return leyendas; }
    public TipoCambio getTipoCambio() { return tipoCambio; }
    public String getOrdenDeCompra() { return ordenDeCompra; }
    public List<GuiaRelacionada> getGuias() { return guias; }
    public List<DocumentoRelacionado> getDocumentosRelacionados() { return documentosRelacionados; }
    public List<CargoDescuento> getCargos() { return cargos; }
    public TotalImpuestos getTotalImpuestos() { return totalImpuestos; }

    // ── Setters ──────────────────────────────────────────────────

    public void setSerie(String serie) { this.serie = serie; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setHoraEmision(LocalTime horaEmision) { this.horaEmision = horaEmision; }
    public void setEmisor(EmisorDocumento emisor) { this.emisor = emisor; }
    public void setReceptor(ReceptorDocumento receptor) { this.receptor = receptor; }
    public void setFirmante(FirmanteDocumento firmante) { this.firmante = firmante; }
    public void setTasaIgv(BigDecimal tasaIgv) { this.tasaIgv = tasaIgv; }
    public void setTasaIvap(BigDecimal tasaIvap) { this.tasaIvap = tasaIvap; }
    public void setTasaIcb(BigDecimal tasaIcb) { this.tasaIcb = tasaIcb; }
    public void setDetalles(List<LineaDetalle> detalles) { this.detalles = detalles; }
    public void setLeyendas(Map<String, String> leyendas) { this.leyendas = leyendas; }
    public void setTipoCambio(TipoCambio tipoCambio) { this.tipoCambio = tipoCambio; }
    public void setOrdenDeCompra(String ordenDeCompra) { this.ordenDeCompra = ordenDeCompra; }
    public void setGuias(List<GuiaRelacionada> guias) { this.guias = guias; }
    public void setDocumentosRelacionados(List<DocumentoRelacionado> documentosRelacionados) { this.documentosRelacionados = documentosRelacionados; }
    public void setCargos(List<CargoDescuento> cargos) { this.cargos = cargos; }
    public void setTotalImpuestos(TotalImpuestos totalImpuestos) { this.totalImpuestos = totalImpuestos; }
}
