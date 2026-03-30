package com.cna.ublkit.ubl.modelo.sunat.resumen;

import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;

/**
 * Comprobante declarado dentro de un Resumen Diario.
 *
 * @since 0.1.0
 */
public class ComprobanteResumen {

    private String moneda;
    private String tipoComprobante;
    private String serieNumero;
    private ReceptorDocumento cliente;
    private ComprobanteValorVenta valorVenta;
    private ComprobanteImpuestos impuestos;
    private PercepcionResumen percepcion;
    private ComprobanteAfectadoResumen comprobanteAfectado;

    public ComprobanteResumen() {}

    public String getMoneda() { return moneda; }
    public String getTipoComprobante() { return tipoComprobante; }
    public String getSerieNumero() { return serieNumero; }
    public ReceptorDocumento getCliente() { return cliente; }
    public ComprobanteValorVenta getValorVenta() { return valorVenta; }
    public ComprobanteImpuestos getImpuestos() { return impuestos; }
    public PercepcionResumen getPercepcion() { return percepcion; }
    public ComprobanteAfectadoResumen getComprobanteAfectado() { return comprobanteAfectado; }

    public void setMoneda(String moneda) { this.moneda = moneda; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public void setSerieNumero(String serieNumero) { this.serieNumero = serieNumero; }
    public void setCliente(ReceptorDocumento cliente) { this.cliente = cliente; }
    public void setValorVenta(ComprobanteValorVenta valorVenta) { this.valorVenta = valorVenta; }
    public void setImpuestos(ComprobanteImpuestos impuestos) { this.impuestos = impuestos; }
    public void setPercepcion(PercepcionResumen percepcion) { this.percepcion = percepcion; }
    public void setComprobanteAfectado(ComprobanteAfectadoResumen comprobanteAfectado) { this.comprobanteAfectado = comprobanteAfectado; }
}
