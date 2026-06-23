package com.cna.ublkit.ubl.modelo.guia.builder;

import com.cna.ublkit.ubl.modelo.guia.LineaGuia;
import com.cna.ublkit.ubl.modelo.guia.AtributoItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LineaGuiaBuilder {
    private String unidadMedida = "KGM";
    private BigDecimal cantidad = new BigDecimal("100.00");
    private String descripcion = "Producto de prueba";
    private String codigo = "PROD001";
    private String codigoSunat = "30000000";
    private List<AtributoItem> atributos = new ArrayList<>();

    public LineaGuiaBuilder withUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
        return this;
    }

    public LineaGuiaBuilder withCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public LineaGuiaBuilder withDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public LineaGuiaBuilder withCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public LineaGuiaBuilder withCodigoSunat(String codigoSunat) {
        this.codigoSunat = codigoSunat;
        return this;
    }

    public LineaGuiaBuilder withAtributos(List<AtributoItem> atributos) {
        this.atributos = atributos;
        return this;
    }

    public LineaGuiaBuilder addAtributo(AtributoItem atributo) {
        this.atributos.add(atributo);
        return this;
    }

    public LineaGuia build() {
        return new LineaGuia(unidadMedida, cantidad, descripcion, codigo, codigoSunat, atributos);
    }

    public static LineaGuiaBuilder aLineaGuia() {
        return new LineaGuiaBuilder();
    }
}
