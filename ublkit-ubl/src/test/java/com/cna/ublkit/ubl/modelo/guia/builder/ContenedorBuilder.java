package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.Contenedor;

public class ContenedorBuilder {
    private String numero = "CONT001";
    private String precinto = "PREC001";

    public ContenedorBuilder withNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public ContenedorBuilder withPrecinto(String precinto) {
        this.precinto = precinto;
        return this;
    }

    public Contenedor build() {
        return new Contenedor(numero, precinto);
    }

    public static ContenedorBuilder aContenedor() {
        return new ContenedorBuilder();
    }
}
