package com.cna.ublkit.ubl.modelo.sunat;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;

import java.time.LocalDate;

/**
 * Clase base para documentos SUNAT que no son UBL estándar
 * (Comunicación de Baja, Resumen Diario, etc.).
 *
 * @since 0.1.0
 */
public abstract class DocumentoSunat {

    private String moneda;
    private LocalDate fechaEmision;
    private EmisorDocumento emisor;
    private FirmanteDocumento firmante;
    private Integer numero;
    private LocalDate fechaEmisionComprobantes;

    protected DocumentoSunat() {}

    public String getMoneda() { return moneda; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public EmisorDocumento getEmisor() { return emisor; }
    public FirmanteDocumento getFirmante() { return firmante; }
    public Integer getNumero() { return numero; }
    public LocalDate getFechaEmisionComprobantes() { return fechaEmisionComprobantes; }

    public void setMoneda(String moneda) { this.moneda = moneda; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setEmisor(EmisorDocumento emisor) { this.emisor = emisor; }
    public void setFirmante(FirmanteDocumento firmante) { this.firmante = firmante; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public void setFechaEmisionComprobantes(LocalDate fechaEmisionComprobantes) { this.fechaEmisionComprobantes = fechaEmisionComprobantes; }
}
