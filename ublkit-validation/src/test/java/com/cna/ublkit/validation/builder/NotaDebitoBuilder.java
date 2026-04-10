package com.cna.ublkit.validation.builder;

import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder para construir instancias de BorradorNotaDebito de forma fluida.
 */
public class NotaDebitoBuilder {

    private String serie = "F001";
    private Integer numero = 1;
    private LocalDate fechaEmision = LocalDate.now();
    private String moneda = "PEN";
    private EmisorDocumento emisor;
    private ReceptorDocumento receptor;
    private List<LineaDetalle> detalles = new ArrayList<>();
    private String tipoNota = "01";
    private String sustentoDescripcion = "Ajuste por cobranza adicional";
    private String comprobanteAfectadoSerieNumero = "F001-001";
    private String comprobanteAfectadoTipo = "01";

    public NotaDebitoBuilder() {
        this.emisor = new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa", null, null);
        this.receptor = new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null);
    }

    public NotaDebitoBuilder conSerie(String serie) {
        this.serie = serie;
        return this;
    }

    public NotaDebitoBuilder conNumero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public NotaDebitoBuilder conFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public NotaDebitoBuilder conEmisor(String ruc, String razonSocial) {
        this.emisor = new EmisorDocumento(ruc, razonSocial, razonSocial, null, null);
        return this;
    }

    public NotaDebitoBuilder conReceptor(String ruc, String razonSocial) {
        this.receptor = new ReceptorDocumento("6", ruc, razonSocial, null, null);
        return this;
    }

    public NotaDebitoBuilder agregarLinea(LineaDetalle linea) {
        this.detalles.add(linea);
        return this;
    }

    public NotaDebitoBuilder conSustentoDescripcion(String sustentoDescripcion) {
        this.sustentoDescripcion = sustentoDescripcion;
        return this;
    }

    public NotaDebitoBuilder conComprobanteAfectado(String tipo, String serieNumero) {
        this.comprobanteAfectadoTipo = tipo;
        this.comprobanteAfectadoSerieNumero = serieNumero;
        return this;
    }

    public BorradorNotaDebito build() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie(serie);
        nota.setNumero(numero);
        nota.setFechaEmision(fechaEmision);
        nota.setMoneda(moneda);
        nota.setEmisor(emisor);
        nota.setReceptor(receptor);
        nota.setTipoNota(tipoNota);
        nota.setSustentoDescripcion(sustentoDescripcion);
        nota.setComprobanteAfectadoTipo(comprobanteAfectadoTipo);
        nota.setComprobanteAfectadoSerieNumero(comprobanteAfectadoSerieNumero);

        if (detalles.isEmpty()) {
            nota.setDetalles(List.of(new LineaDetalleBuilder().build()));
        } else {
            nota.setDetalles(new ArrayList<>(detalles));
        }

        return nota;
    }
}

