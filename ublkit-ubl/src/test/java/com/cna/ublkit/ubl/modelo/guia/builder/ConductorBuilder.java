package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.Conductor;

public class ConductorBuilder {
    private String tipo = "Principal";
    private String tipoDocumentoIdentidad = "01";
    private String numeroDocumentoIdentidad = "12345678";
    private String nombres = "Juan";
    private String apellidos = "Perez Garcia";
    private String licencia = "LIC123456";

    public ConductorBuilder withTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public ConductorBuilder withTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
        return this;
    }

    public ConductorBuilder withNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
        this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
        return this;
    }

    public ConductorBuilder withNombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public ConductorBuilder withApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public ConductorBuilder withLicencia(String licencia) {
        this.licencia = licencia;
        return this;
    }

    public Conductor build() {
        return new Conductor(tipo, tipoDocumentoIdentidad, numeroDocumentoIdentidad,
                           nombres, apellidos, licencia);
    }

    public static ConductorBuilder aConductor() {
        return new ConductorBuilder();
    }
}
