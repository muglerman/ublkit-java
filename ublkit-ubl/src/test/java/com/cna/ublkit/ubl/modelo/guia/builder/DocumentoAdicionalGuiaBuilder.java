package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.DocumentoAdicionalGuia;

public class DocumentoAdicionalGuiaBuilder {
    private String tipoDocumento = "01";
    private String serieNumero = "001";
    private String emisor = "20123456789";

    public DocumentoAdicionalGuiaBuilder withTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public DocumentoAdicionalGuiaBuilder withSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
        return this;
    }

    public DocumentoAdicionalGuiaBuilder withEmisor(String emisor) {
        this.emisor = emisor;
        return this;
    }

    public DocumentoAdicionalGuia build() {
        return new DocumentoAdicionalGuia(tipoDocumento, serieNumero, emisor);
    }

    public static DocumentoAdicionalGuiaBuilder aDocumentoAdicionalGuia() {
        return new DocumentoAdicionalGuiaBuilder();
    }
}
