package com.cna.ublkit.render.modelo;

import java.util.Arrays;
import java.util.Objects;

/**
 * Resultado de una operación de renderizado visual de un documento.
 * Encapsula la representación como una cadena HTML o arreglo de bytes de un PDF.
 *
 * @since 0.1.0
 */
public record ResultadoRender(
        String contenidoHtml,
        byte[] contenidoPdf
) {
    public ResultadoRender {
        contenidoPdf = contenidoPdf == null ? null : contenidoPdf.clone();
    }

    /**
     * Helper para inicializar un resultado estrictamente HTML.
     */
    public static ResultadoRender html(String html) {
        return new ResultadoRender(html, null);
    }

    /**
     * Helper para inicializar un resultado estrictamente PDF.
     */
    public static ResultadoRender pdf(byte[] pdf) {
        return new ResultadoRender(null, pdf);
    }

    @Override
    public byte[] contenidoPdf() {
        return contenidoPdf == null ? null : contenidoPdf.clone();
    }

    /**
     * Indica si este resultado contiene HTML.
     */
    public boolean isHtml() {
        return contenidoHtml != null;
    }

    /**
     * Indica si este resultado contiene PDF.
     */
    public boolean isPdf() {
        return contenidoPdf != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResultadoRender other)) return false;
        return Objects.equals(this.contenidoHtml, other.contenidoHtml)
                && Arrays.equals(this.contenidoPdf, other.contenidoPdf);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(contenidoHtml) + Arrays.hashCode(contenidoPdf);
    }

    @Override
    public String toString() {
        return "ResultadoRender[" +
                "contenidoHtml=" + (contenidoHtml != null) +
                ", contenidoPdf(length)=" + (contenidoPdf == null ? 0 : contenidoPdf.length) +
                ']';
    }
}
