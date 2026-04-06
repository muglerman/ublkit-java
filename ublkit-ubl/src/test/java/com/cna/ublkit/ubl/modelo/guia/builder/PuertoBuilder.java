package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.Puerto;

public class PuertoBuilder {
    private String codigo = "CALLAO";
    private String descripcion = "Puerto del Callao";

    public PuertoBuilder withCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public PuertoBuilder withDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Puerto build() {
        return new Puerto(codigo, descripcion);
    }

    public static PuertoBuilder aPuerto() {
        return new PuertoBuilder();
    }
}
