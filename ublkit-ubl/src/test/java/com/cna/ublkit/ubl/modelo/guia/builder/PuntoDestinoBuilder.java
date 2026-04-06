package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.PuntoDestino;

/**
 * Builder para crear instancias de PuntoDestino en tests.
 */
public class PuntoDestinoBuilder {
    private String ubigeo = "150131";
    private String direccion = "Jr. Destino 456";
    private String codigoLocal = "0000";
    private String ruc = "20987654321";

    public PuntoDestinoBuilder withUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
        return this;
    }

    public PuntoDestinoBuilder withDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public PuntoDestinoBuilder withCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
        return this;
    }

    public PuntoDestinoBuilder withRuc(String ruc) {
        this.ruc = ruc;
        return this;
    }

    public PuntoDestino build() {
        return new PuntoDestino(ubigeo, direccion, codigoLocal, ruc);
    }

    public static PuntoDestinoBuilder aPuntoDestino() {
        return new PuntoDestinoBuilder();
    }
}
