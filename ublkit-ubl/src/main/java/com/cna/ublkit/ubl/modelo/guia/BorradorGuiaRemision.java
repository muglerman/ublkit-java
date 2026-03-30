package com.cna.ublkit.ubl.modelo.guia;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Borrador de Guía de Remisión Electrónica (GRE).
 * <p>
 * Soporta ambos tipos:
 * <ul>
 * <li><b>GRE-Remitente (09)</b>: Serie Txxx. Emitida por el remitente de los bienes.</li>
 * <li><b>GRE-Transportista (31)</b>: Serie Vxxx. Emitida por el transportista.</li>
 * </ul>
 * <p>
 * Fuente normativa: RS 000123-2022/SUNAT, RS 000240-2024/SUNAT.
 *
 * @since 0.1.0
 */
public class BorradorGuiaRemision {

    /** Versión del formato (ej. "2.0"). */
    private String version;

    /** Serie del comprobante (Txxx para Remitente, Vxxx para Transportista). */
    private String serie;

    /** Número del comprobante. */
    private Integer numero;

    /** Fecha de emisión. */
    private LocalDate fechaEmision;

    /** Hora de emisión. */
    private LocalTime horaEmision;

    /** Tipo de comprobante (Catálogo 01: "09" o "31"). */
    private String tipoComprobante;

    /** Observaciones. */
    private String observaciones;

    /** Documento dado de baja asociado. */
    private DocumentoBajaGuia documentoBaja;

    /** Documento relacionado principal. */
    private DocumentoRelacionadoGuia documentoRelacionado;

    /** Documentos relacionados adicionales (Catálogo 21). */
    private List<DocumentoRelacionadoGuia> documentosRelacionados;

    /** Documentos adicionales (Catálogo 61). */
    private List<DocumentoAdicionalGuia> documentosAdicionales;

    /** Firmante del documento. */
    private FirmanteDocumento firmante;

    /** Datos del remitente (en GRE-09: quien envía; en GRE-31: transportista emisor). */
    private EmisorDocumento remitente;

    /** Datos del destinatario (quien recibe los bienes). */
    private DestinatarioGuia destinatario;

    /** Datos del tercero (vendedor/remitente original, aplica en GRE-31). */
    private TerceroGuia tercero;

    /** Datos del comprador/adquiriente. */
    private CompradorGuia comprador;

    /** Datos de envío/shipment. */
    private DatosEnvio envio;

    /** Líneas de detalle (bienes trasladados). */
    private List<LineaGuia> detalles;

    public BorradorGuiaRemision() {
    }

    // ── Métodos utilitarios ──────────────────────────────────────

    /** Determina si esta GRE es de tipo Remitente (código 09). */
    public boolean isGRERemitente() {
        return "09".equals(tipoComprobante);
    }

    /** Determina si esta GRE es de tipo Transportista (código 31). */
    public boolean isGRETransportista() {
        return "31".equals(tipoComprobante);
    }

    // ── Getters ──────────────────────────────────────────────────

    public String getVersion() { return version; }
    public String getSerie() { return serie; }
    public Integer getNumero() { return numero; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public LocalTime getHoraEmision() { return horaEmision; }
    public String getTipoComprobante() { return tipoComprobante; }
    public String getObservaciones() { return observaciones; }
    public DocumentoBajaGuia getDocumentoBaja() { return documentoBaja; }
    public DocumentoRelacionadoGuia getDocumentoRelacionado() { return documentoRelacionado; }
    public List<DocumentoRelacionadoGuia> getDocumentosRelacionados() { return documentosRelacionados; }
    public List<DocumentoAdicionalGuia> getDocumentosAdicionales() { return documentosAdicionales; }
    public FirmanteDocumento getFirmante() { return firmante; }
    public EmisorDocumento getRemitente() { return remitente; }
    public DestinatarioGuia getDestinatario() { return destinatario; }
    public TerceroGuia getTercero() { return tercero; }
    public CompradorGuia getComprador() { return comprador; }
    public DatosEnvio getEnvio() { return envio; }
    public List<LineaGuia> getDetalles() { return detalles; }

    // ── Setters ──────────────────────────────────────────────────

    public void setVersion(String version) { this.version = version; }
    public void setSerie(String serie) { this.serie = serie; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setHoraEmision(LocalTime horaEmision) { this.horaEmision = horaEmision; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setDocumentoBaja(DocumentoBajaGuia documentoBaja) { this.documentoBaja = documentoBaja; }
    public void setDocumentoRelacionado(DocumentoRelacionadoGuia documentoRelacionado) { this.documentoRelacionado = documentoRelacionado; }
    public void setDocumentosRelacionados(List<DocumentoRelacionadoGuia> documentosRelacionados) { this.documentosRelacionados = documentosRelacionados; }
    public void setDocumentosAdicionales(List<DocumentoAdicionalGuia> documentosAdicionales) { this.documentosAdicionales = documentosAdicionales; }
    public void setFirmante(FirmanteDocumento firmante) { this.firmante = firmante; }
    public void setRemitente(EmisorDocumento remitente) { this.remitente = remitente; }
    public void setDestinatario(DestinatarioGuia destinatario) { this.destinatario = destinatario; }
    public void setTercero(TerceroGuia tercero) { this.tercero = tercero; }
    public void setComprador(CompradorGuia comprador) { this.comprador = comprador; }
    public void setEnvio(DatosEnvio envio) { this.envio = envio; }
    public void setDetalles(List<LineaGuia> detalles) { this.detalles = detalles; }
}
