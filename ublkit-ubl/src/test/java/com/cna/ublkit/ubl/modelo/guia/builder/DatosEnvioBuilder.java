package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatosEnvioBuilder {
    private String tipoTraslado = "01";
    private String motivoTraslado = "Venta";
    private BigDecimal pesoTotal = new BigDecimal("5000.00");
    private String pesoTotalUnidadMedida = "KGM";
    private BigDecimal pesoItems = new BigDecimal("5000.00");
    private String sustentoPeso;
    private Integer numeroDeBultos = 50;
    private String tipoModalidadTraslado = "01";
    private LocalDate fechaTraslado = LocalDate.now();
    private List<Contenedor> contenedores = new ArrayList<>();
    private Puerto puerto;
    private Puerto aeropuerto;
    private TransportistaGuia transportista;
    private List<Conductor> choferes = new ArrayList<>();
    private Vehiculo vehiculo;
    private List<String> indicadores = new ArrayList<>();
    private PuntoPartida partida;
    private PuntoDestino destino;
    private String numeroManifiesto;
    private List<DeclaracionAduanera> declaracionesAduaneras = new ArrayList<>();

    public DatosEnvioBuilder withTipoTraslado(String tipoTraslado) {
        this.tipoTraslado = tipoTraslado;
        return this;
    }

    public DatosEnvioBuilder withMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
        return this;
    }

    public DatosEnvioBuilder withPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = pesoTotal;
        return this;
    }

    public DatosEnvioBuilder withPesoTotalUnidadMedida(String pesoTotalUnidadMedida) {
        this.pesoTotalUnidadMedida = pesoTotalUnidadMedida;
        return this;
    }

    public DatosEnvioBuilder withNumeroDeBultos(Integer numeroDeBultos) {
        this.numeroDeBultos = numeroDeBultos;
        return this;
    }

    public DatosEnvioBuilder withTipoModalidadTraslado(String tipoModalidadTraslado) {
        this.tipoModalidadTraslado = tipoModalidadTraslado;
        return this;
    }

    public DatosEnvioBuilder withFechaTraslado(LocalDate fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
        return this;
    }

    public DatosEnvioBuilder withContenedores(List<Contenedor> contenedores) {
        this.contenedores = contenedores;
        return this;
    }

    public DatosEnvioBuilder addContenedor(Contenedor contenedor) {
        this.contenedores.add(contenedor);
        return this;
    }

    public DatosEnvioBuilder withPuerto(Puerto puerto) {
        this.puerto = puerto;
        return this;
    }

    public DatosEnvioBuilder withAeropuerto(Puerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        return this;
    }

    public DatosEnvioBuilder withTransportista(TransportistaGuia transportista) {
        this.transportista = transportista;
        return this;
    }

    public DatosEnvioBuilder withChoferes(List<Conductor> choferes) {
        this.choferes = choferes;
        return this;
    }

    public DatosEnvioBuilder addChofer(Conductor conductor) {
        this.choferes.add(conductor);
        return this;
    }

    public DatosEnvioBuilder withVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        return this;
    }

    public DatosEnvioBuilder withPartida(PuntoPartida partida) {
        this.partida = partida;
        return this;
    }

    public DatosEnvioBuilder withDestino(PuntoDestino destino) {
        this.destino = destino;
        return this;
    }

    public DatosEnvio build() {
        DatosEnvio datos = new DatosEnvio();
        datos.setTipoTraslado(tipoTraslado);
        datos.setMotivoTraslado(motivoTraslado);
        datos.setPesoTotal(pesoTotal);
        datos.setPesoTotalUnidadMedida(pesoTotalUnidadMedida);
        datos.setPesoItems(pesoItems);
        datos.setSustentoPeso(sustentoPeso);
        datos.setNumeroDeBultos(numeroDeBultos);
        datos.setTipoModalidadTraslado(tipoModalidadTraslado);
        datos.setFechaTraslado(fechaTraslado);
        datos.setContenedores(contenedores);
        datos.setPuerto(puerto);
        datos.setAeropuerto(aeropuerto);
        datos.setTransportista(transportista);
        datos.setChoferes(choferes);
        datos.setVehiculo(vehiculo);
        datos.setIndicadores(indicadores);
        datos.setPartida(partida);
        datos.setDestino(destino);
        datos.setNumeroManifiesto(numeroManifiesto);
        datos.setDeclaracionesAduaneras(declaracionesAduaneras);
        return datos;
    }

    public static DatosEnvioBuilder aDatosEnvio() {
        return new DatosEnvioBuilder();
    }
}
