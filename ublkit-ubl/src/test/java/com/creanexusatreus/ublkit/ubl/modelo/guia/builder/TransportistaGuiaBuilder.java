package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.TransportistaGuia;

public class TransportistaGuiaBuilder {
    private String tipoDocumentoIdentidad = "06";
    private String numeroDocumentoIdentidad = "20123456789";
    private String nombre = "Empresa Transporte SA";
    private String numeroRegistroMTC = "MTC123456";

    public TransportistaGuiaBuilder withTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
        return this;
    }

    public TransportistaGuiaBuilder withNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
        this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
        return this;
    }

    public TransportistaGuiaBuilder withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public TransportistaGuiaBuilder withNumeroRegistroMTC(String numeroRegistroMTC) {
        this.numeroRegistroMTC = numeroRegistroMTC;
        return this;
    }

    public TransportistaGuia build() {
        return new TransportistaGuia(tipoDocumentoIdentidad, numeroDocumentoIdentidad, nombre, numeroRegistroMTC);
    }

    public static TransportistaGuiaBuilder aTransportistaGuia() {
        return new TransportistaGuiaBuilder();
    }
}
