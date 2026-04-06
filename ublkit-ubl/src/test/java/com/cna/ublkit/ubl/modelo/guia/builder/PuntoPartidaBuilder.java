package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.PuntoPartida;

/**
 * Builder para crear instancias de PuntoPartida en tests.
 * Facilita la construcción flexible de records inmutables.
 */
public class PuntoPartidaBuilder {
    private String ubigeo = "150131";
    private String direccion = "Jr. Test 123";
    private String codigoLocal = "0000";
    private String ruc = "20123456789";

    public PuntoPartidaBuilder withUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
        return this;
    }

    public PuntoPartidaBuilder withDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public PuntoPartidaBuilder withCodigoLocal(String codigoLocal) {
        this.codigoLocal = codigoLocal;
        return this;
    }

    public PuntoPartidaBuilder withRuc(String ruc) {
        this.ruc = ruc;
        return this;
    }

    public PuntoPartida build() {
        return new PuntoPartida(ubigeo, direccion, codigoLocal, ruc);
    }

    public static PuntoPartidaBuilder aPuntoPartida() {
        return new PuntoPartidaBuilder();
    }
}
