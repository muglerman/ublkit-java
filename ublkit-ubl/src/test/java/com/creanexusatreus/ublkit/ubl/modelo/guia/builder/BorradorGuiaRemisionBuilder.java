package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder fluido para crear instancias de BorradorGuiaRemision en tests.
 * Facilita la construcción step-by-step con valores por defecto sensatos.
 */
public class BorradorGuiaRemisionBuilder {
    private String version = "2.0";
    private String serie = "T001";
    private Integer numero = 1;
    private LocalDate fechaEmision = LocalDate.now();
    private LocalTime horaEmision = LocalTime.now();
    private String tipoComprobante = "09"; // 09=Remitente, 31=Transportista
    private String observaciones = "";
    private DocumentoBajaGuia documentoBaja;
    private DocumentoRelacionadoGuia documentoRelacionado;
    private List<DocumentoRelacionadoGuia> documentosRelacionados = new ArrayList<>();
    private List<DocumentoAdicionalGuia> documentosAdicionales = new ArrayList<>();
    private FirmanteDocumento firmante;
    private EmisorDocumento remitente;
    private DestinatarioGuia destinatario;
    private TerceroGuia tercero;
    private CompradorGuia comprador;
    private DatosEnvio envio;
    private List<LineaGuia> detalles = new ArrayList<>();

    public BorradorGuiaRemisionBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public BorradorGuiaRemisionBuilder withSerie(String serie) {
        this.serie = serie;
        return this;
    }

    public BorradorGuiaRemisionBuilder withNumero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public BorradorGuiaRemisionBuilder withFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public BorradorGuiaRemisionBuilder withHoraEmision(LocalTime horaEmision) {
        this.horaEmision = horaEmision;
        return this;
    }

    public BorradorGuiaRemisionBuilder withTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        return this;
    }

    public BorradorGuiaRemisionBuilder withObservaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public BorradorGuiaRemisionBuilder withDocumentoBaja(DocumentoBajaGuia documentoBaja) {
        this.documentoBaja = documentoBaja;
        return this;
    }

    public BorradorGuiaRemisionBuilder withDocumentoRelacionado(DocumentoRelacionadoGuia documentoRelacionado) {
        this.documentoRelacionado = documentoRelacionado;
        return this;
    }

    public BorradorGuiaRemisionBuilder withDocumentosRelacionados(List<DocumentoRelacionadoGuia> documentosRelacionados) {
        this.documentosRelacionados = documentosRelacionados;
        return this;
    }

    public BorradorGuiaRemisionBuilder addDocumentoRelacionado(DocumentoRelacionadoGuia documento) {
        this.documentosRelacionados.add(documento);
        return this;
    }

    public BorradorGuiaRemisionBuilder withDocumentosAdicionales(List<DocumentoAdicionalGuia> documentosAdicionales) {
        this.documentosAdicionales = documentosAdicionales;
        return this;
    }

    public BorradorGuiaRemisionBuilder addDocumentoAdicional(DocumentoAdicionalGuia documento) {
        this.documentosAdicionales.add(documento);
        return this;
    }

    public BorradorGuiaRemisionBuilder withFirmante(FirmanteDocumento firmante) {
        this.firmante = firmante;
        return this;
    }

    public BorradorGuiaRemisionBuilder withRemitente(EmisorDocumento remitente) {
        this.remitente = remitente;
        return this;
    }

    public BorradorGuiaRemisionBuilder withDestinatario(DestinatarioGuia destinatario) {
        this.destinatario = destinatario;
        return this;
    }

    public BorradorGuiaRemisionBuilder withTercero(TerceroGuia tercero) {
        this.tercero = tercero;
        return this;
    }

    public BorradorGuiaRemisionBuilder withComprador(CompradorGuia comprador) {
        this.comprador = comprador;
        return this;
    }

    public BorradorGuiaRemisionBuilder withEnvio(DatosEnvio envio) {
        this.envio = envio;
        return this;
    }

    public BorradorGuiaRemisionBuilder withDetalles(List<LineaGuia> detalles) {
        this.detalles = detalles;
        return this;
    }

    public BorradorGuiaRemisionBuilder addDetalle(LineaGuia detalle) {
        this.detalles.add(detalle);
        return this;
    }

    public BorradorGuiaRemision build() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setVersion(version);
        guia.setSerie(serie);
        guia.setNumero(numero);
        guia.setFechaEmision(fechaEmision);
        guia.setHoraEmision(horaEmision);
        guia.setTipoComprobante(tipoComprobante);
        guia.setObservaciones(observaciones);
        guia.setDocumentoBaja(documentoBaja);
        guia.setDocumentoRelacionado(documentoRelacionado);
        guia.setDocumentosRelacionados(documentosRelacionados);
        guia.setDocumentosAdicionales(documentosAdicionales);
        guia.setFirmante(firmante);
        guia.setRemitente(remitente);
        guia.setDestinatario(destinatario);
        guia.setTercero(tercero);
        guia.setComprador(comprador);
        guia.setEnvio(envio);
        guia.setDetalles(detalles);
        return guia;
    }

    public static BorradorGuiaRemisionBuilder aBorradorGuiaRemision() {
        return new BorradorGuiaRemisionBuilder();
    }
}
