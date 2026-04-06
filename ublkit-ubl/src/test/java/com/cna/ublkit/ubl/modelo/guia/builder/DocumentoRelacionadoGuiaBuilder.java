package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.DocumentoRelacionadoGuia;

/**
 * Builder para crear instancias de DocumentoRelacionadoGuia en tests.
 */
public class DocumentoRelacionadoGuiaBuilder {
    private String tipoDocumento = "09"; // Catálogo 21
    private String serieNumero = "F001-000001";

    public DocumentoRelacionadoGuiaBuilder withTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public DocumentoRelacionadoGuiaBuilder withSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
        return this;
    }

    public DocumentoRelacionadoGuia build() {
        return new DocumentoRelacionadoGuia(tipoDocumento, serieNumero);
    }

    public static DocumentoRelacionadoGuiaBuilder aDocumentoRelacionadoGuia() {
        return new DocumentoRelacionadoGuiaBuilder();
    }
}
