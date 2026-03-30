package com.cna.ublkit.render.modelo;

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
}
