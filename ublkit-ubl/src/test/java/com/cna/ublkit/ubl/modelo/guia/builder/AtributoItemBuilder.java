package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.AtributoItem;

/**
 * Builder para crear instancias de AtributoItem en tests.
 */
public class AtributoItemBuilder {
    private String codigo = "ATTR001";
    private String valor = "Valor de prueba";

    public AtributoItemBuilder withCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public AtributoItemBuilder withValor(String valor) {
        this.valor = valor;
        return this;
    }

    public AtributoItem build() {
        return new AtributoItem(codigo, valor);
    }

    public static AtributoItemBuilder anAtributoItem() {
        return new AtributoItemBuilder();
    }
}
