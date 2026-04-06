package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.DeclaracionAduanera;

public class DeclaracionAduaneraBuilder {
    private String tipoDocumento = "61";
    private String numero = "2000123456";
    private String serieAduana = "04";

    public DeclaracionAduaneraBuilder withTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public DeclaracionAduaneraBuilder withNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public DeclaracionAduaneraBuilder withSerieAduana(String serieAduana) {
        this.serieAduana = serieAduana;
        return this;
    }

    public DeclaracionAduanera build() {
        return new DeclaracionAduanera(tipoDocumento, numero, serieAduana);
    }

    public static DeclaracionAduaneraBuilder aDeclaracionAduanera() {
        return new DeclaracionAduaneraBuilder();
    }
}
