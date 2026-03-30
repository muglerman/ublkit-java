package com.cna.ublkit.ubl.modelo.guia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Datos de envío/shipment de la Guía de Remisión Electrónica.
 * Equivale a {@code cac:Shipment} en UBL 2.1.
 *
 * @since 0.1.0
 */
public class DatosEnvio {

    /** Motivo de traslado (Catálogo 20). */
    private String tipoTraslado;

    /** Descripción del motivo de traslado. */
    private String motivoTraslado;

    /** Peso bruto total del envío. */
    private BigDecimal pesoTotal;

    /** Unidad de medida del peso (ej. "KGM"). */
    private String pesoTotalUnidadMedida;

    /** Peso de los ítems seleccionados (traslado parcial). */
    private BigDecimal pesoItems;

    /** Sustento de la diferencia del peso bruto total. */
    private String sustentoPeso;

    /** Número de bultos. */
    private Integer numeroDeBultos;

    /** Modalidad de traslado (Catálogo 18). */
    private String tipoModalidadTraslado;

    /** Fecha de inicio del traslado. */
    private LocalDate fechaTraslado;

    /** Contenedores con número y precinto. */
    private List<Contenedor> contenedores;

    /** Puerto de embarque/desembarque (Catálogo 63). */
    private Puerto puerto;

    /** Aeropuerto de embarque/desembarque (Catálogo 64). */
    private Puerto aeropuerto;

    /** Transportista contratado (transporte público). */
    private TransportistaGuia transportista;

    /** Lista de conductores (principal y secundarios). */
    private List<Conductor> choferes;

    /** Vehículo de transporte. */
    private Vehiculo vehiculo;

    /** Indicadores especiales de transporte (SpecialInstructions). */
    private List<String> indicadores;

    /** Punto de partida del traslado. */
    private PuntoPartida partida;

    /** Punto de destino del traslado. */
    private PuntoDestino destino;

    /** Número de manifiesto de carga (comercio exterior). */
    private String numeroManifiesto;

    /** Declaraciones aduaneras (DAM/DS) para comercio exterior. */
    private List<DeclaracionAduanera> declaracionesAduaneras;

    public DatosEnvio() {
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getTipoTraslado() { return tipoTraslado; }
    public String getMotivoTraslado() { return motivoTraslado; }
    public BigDecimal getPesoTotal() { return pesoTotal; }
    public String getPesoTotalUnidadMedida() { return pesoTotalUnidadMedida; }
    public BigDecimal getPesoItems() { return pesoItems; }
    public String getSustentoPeso() { return sustentoPeso; }
    public Integer getNumeroDeBultos() { return numeroDeBultos; }
    public String getTipoModalidadTraslado() { return tipoModalidadTraslado; }
    public LocalDate getFechaTraslado() { return fechaTraslado; }
    public List<Contenedor> getContenedores() { return contenedores; }
    public Puerto getPuerto() { return puerto; }
    public Puerto getAeropuerto() { return aeropuerto; }
    public TransportistaGuia getTransportista() { return transportista; }
    public List<Conductor> getChoferes() { return choferes; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public List<String> getIndicadores() { return indicadores; }
    public PuntoPartida getPartida() { return partida; }
    public PuntoDestino getDestino() { return destino; }
    public String getNumeroManifiesto() { return numeroManifiesto; }
    public List<DeclaracionAduanera> getDeclaracionesAduaneras() { return declaracionesAduaneras; }

    // ── Setters ──────────────────────────────────────────────────

    public void setTipoTraslado(String tipoTraslado) { this.tipoTraslado = tipoTraslado; }
    public void setMotivoTraslado(String motivoTraslado) { this.motivoTraslado = motivoTraslado; }
    public void setPesoTotal(BigDecimal pesoTotal) { this.pesoTotal = pesoTotal; }
    public void setPesoTotalUnidadMedida(String pesoTotalUnidadMedida) { this.pesoTotalUnidadMedida = pesoTotalUnidadMedida; }
    public void setPesoItems(BigDecimal pesoItems) { this.pesoItems = pesoItems; }
    public void setSustentoPeso(String sustentoPeso) { this.sustentoPeso = sustentoPeso; }
    public void setNumeroDeBultos(Integer numeroDeBultos) { this.numeroDeBultos = numeroDeBultos; }
    public void setTipoModalidadTraslado(String tipoModalidadTraslado) { this.tipoModalidadTraslado = tipoModalidadTraslado; }
    public void setFechaTraslado(LocalDate fechaTraslado) { this.fechaTraslado = fechaTraslado; }
    public void setContenedores(List<Contenedor> contenedores) { this.contenedores = contenedores; }
    public void setPuerto(Puerto puerto) { this.puerto = puerto; }
    public void setAeropuerto(Puerto aeropuerto) { this.aeropuerto = aeropuerto; }
    public void setTransportista(TransportistaGuia transportista) { this.transportista = transportista; }
    public void setChoferes(List<Conductor> choferes) { this.choferes = choferes; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public void setIndicadores(List<String> indicadores) { this.indicadores = indicadores; }
    public void setPartida(PuntoPartida partida) { this.partida = partida; }
    public void setDestino(PuntoDestino destino) { this.destino = destino; }
    public void setNumeroManifiesto(String numeroManifiesto) { this.numeroManifiesto = numeroManifiesto; }
    public void setDeclaracionesAduaneras(List<DeclaracionAduanera> declaracionesAduaneras) { this.declaracionesAduaneras = declaracionesAduaneras; }
}
