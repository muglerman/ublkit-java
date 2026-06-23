package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.DocumentoBajaGuia;

public class DocumentoBajaGuiaBuilder {
    private String tipoDocumento = "01";
    private String serieNumero = "F001-000001";

    public DocumentoBajaGuiaBuilder withTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public DocumentoBajaGuiaBuilder withSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
        return this;
    }

    public DocumentoBajaGuia build() {
        return new DocumentoBajaGuia(tipoDocumento, serieNumero);
    }

    public static DocumentoBajaGuiaBuilder aDocumentoBajaGuia() {
        return new DocumentoBajaGuiaBuilder();
    }
}
