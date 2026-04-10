package com.cna.ublkit.validation.builder;

import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;

import java.math.BigDecimal;

/**
 * Builder para construir instancias de LineaDetalle de forma fluida.
 *
 * Ejemplo:
 * <pre>
 * LineaDetalle linea = new LineaDetalleBuilder()
 *     .conDescripcion("Producto A")
 *     .conCantidad("2")
 *     .conPrecio("100.00")
 *     .conUnidadMedida("NIU")
 *     .build();
 * </pre>
 */
public class LineaDetalleBuilder {

    private String descripcion = "Producto por defecto";
    private BigDecimal cantidad = new BigDecimal("1");
    private BigDecimal precio = new BigDecimal("100.00");
    private String unidadMedida = "NIU";
    private String codigoProducto = "PROD001";

    public LineaDetalleBuilder conDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public LineaDetalleBuilder conCantidad(String cantidad) {
        this.cantidad = new BigDecimal(cantidad);
        return this;
    }

    public LineaDetalleBuilder conCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public LineaDetalleBuilder conPrecio(String precio) {
        this.precio = new BigDecimal(precio);
        return this;
    }

    public LineaDetalleBuilder conPrecio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public LineaDetalleBuilder conUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
        return this;
    }

    public LineaDetalleBuilder conCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
        return this;
    }

    public LineaDetalle build() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion(descripcion);
        linea.setCantidad(cantidad);
        linea.setPrecio(precio);
        linea.setUnidadMedida(unidadMedida);
        linea.setCodigoProducto(codigoProducto);
        return linea;
    }
}
