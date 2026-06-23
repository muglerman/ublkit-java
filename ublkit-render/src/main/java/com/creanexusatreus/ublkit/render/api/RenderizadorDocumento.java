package com.cna.ublkit.render.api;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;

/**
 * Contrato base para renderizadores de documentos tribuarios.
 *
 * @param <T> Tipo de documento a renderizar (Ej. BorradorFactura, BorradorGuiaRemision)
 *
 * @since 0.1.0
 */
public interface RenderizadorDocumento<T> {

    /**
     * Convierte el documento tributario y sus metadatos en una representación visual.
     *
     * @param contexto contexto que contiene el documento junto al hash y QR.
     * @return objeto que envuelve el resultado (ej: cadena HTML o arreglo de bytes de PDF)
     */
    ResultadoRender renderizar(ContextoRender<T> contexto);
}
