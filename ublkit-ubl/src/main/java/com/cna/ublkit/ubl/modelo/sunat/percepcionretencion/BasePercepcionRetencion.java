package com.cna.ublkit.ubl.modelo.sunat.percepcionretencion;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase base para comprobantes de Percepción (40) y Retención (20).
 *
 * @since 0.1.0
 */
public abstract class BasePercepcionRetencion {

    private String serie;
    private Integer numero;
    private String moneda;
    private LocalDate fechaEmision;
    private EmisorDocumento emisor;
    private FirmanteDocumento firmante;
    private ReceptorDocumento cliente;
    private BigDecimal tipoRegimenPorcentaje;
    private String observacion;
    private OperacionPR operacion;

    protected BasePercepcionRetencion() {}

    public String getSerie() { return serie; }
    public Integer getNumero() { return numero; }
    public String getMoneda() { return moneda; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public EmisorDocumento getEmisor() { return emisor; }
    public FirmanteDocumento getFirmante() { return firmante; }
    public ReceptorDocumento getCliente() { return cliente; }
    public BigDecimal getTipoRegimenPorcentaje() { return tipoRegimenPorcentaje; }
    public String getObservacion() { return observacion; }
    public OperacionPR getOperacion() { return operacion; }

    public void setSerie(String serie) { this.serie = serie; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setEmisor(EmisorDocumento emisor) { this.emisor = emisor; }
    public void setFirmante(FirmanteDocumento firmante) { this.firmante = firmante; }
    public void setCliente(ReceptorDocumento cliente) { this.cliente = cliente; }
    public void setTipoRegimenPorcentaje(BigDecimal tipoRegimenPorcentaje) { this.tipoRegimenPorcentaje = tipoRegimenPorcentaje; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public void setOperacion(OperacionPR operacion) { this.operacion = operacion; }
}
