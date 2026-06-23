package com.creanexusatreus.ublkit.ubl.modelo.guia.builder;

import com.creanexusatreus.ublkit.ubl.modelo.guia.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class VehiculoBuilder {
    private String placa = "AXZ-999";
    private String numeroCirculacion = "TUC12345";
    private String numeroAutorizacion = "AUTH67890";
    private String codigoEmisor = "01";
    private String marca = "VOLVO";
    private String modelo = "FH16";
    private List<Vehiculo> secundarios = new ArrayList<>();

    public VehiculoBuilder withPlaca(String placa) {
        this.placa = placa;
        return this;
    }

    public VehiculoBuilder withNumeroCirculacion(String numeroCirculacion) {
        this.numeroCirculacion = numeroCirculacion;
        return this;
    }

    public VehiculoBuilder withNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
        return this;
    }

    public VehiculoBuilder withCodigoEmisor(String codigoEmisor) {
        this.codigoEmisor = codigoEmisor;
        return this;
    }

    public VehiculoBuilder withSecundarios(List<Vehiculo> secundarios) {
        this.secundarios = secundarios;
        return this;
    }

    public VehiculoBuilder withMarca(String marca) {
        this.marca = marca;
        return this;
    }

    public VehiculoBuilder withModelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public Vehiculo build() {
        return new Vehiculo(placa, numeroCirculacion, numeroAutorizacion, codigoEmisor, marca, modelo, secundarios);
    }

    public static VehiculoBuilder aVehiculo() {
        return new VehiculoBuilder();
    }
}
