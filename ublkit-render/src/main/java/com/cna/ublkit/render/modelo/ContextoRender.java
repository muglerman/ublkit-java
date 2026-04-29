package com.cna.ublkit.render.modelo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contexto utilizado para el proceso de renderización.
 * Contiene el documento principal y metadatos generados (como firma, hash y código QR)
 * necesarios para la representación visual.
 *
 * @param documento  Documento de dominio (ej: BorradorFactura, BorradorGuiaRemision)
 * @param hashDocumento Hash generado tras la firma digital (para colocar en el PDF/Ticket)
 * @param qrBase64   Código QR generado (en formato Base64) a partir de los datos del documento
 * @param <T>        Tipo del documento
 *
 * @since 0.1.0
 */
public record ContextoRender<T>(
        T documento,
        String hashDocumento,
        String qrBase64,
        Map<String, Object> atributosPlantilla,
        EstiloPlantilla estiloPlantilla
) {
    public ContextoRender {
        atributosPlantilla = atributosPlantilla != null
                ? Collections.unmodifiableMap(new HashMap<>(atributosPlantilla))
                : Map.of();
        estiloPlantilla = estiloPlantilla != null ? estiloPlantilla : EstiloPlantilla.DEFAULT;
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64, Map.of(), EstiloPlantilla.DEFAULT);
    }

    public static <T> ContextoRender<T> of(T documento) {
        return new ContextoRender<>(documento, null, null, Map.of(), EstiloPlantilla.DEFAULT);
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           Map<String, Object> atributosPlantilla) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64,
                atributosPlantilla != null ? new HashMap<>(atributosPlantilla) : Map.of(), EstiloPlantilla.DEFAULT);
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           EstiloPlantilla estiloPlantilla) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64, Map.of(),
                estiloPlantilla != null ? estiloPlantilla : EstiloPlantilla.DEFAULT);
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           Map<String, Object> atributosPlantilla, EstiloPlantilla estiloPlantilla) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64,
                atributosPlantilla != null ? new HashMap<>(atributosPlantilla) : Map.of(),
                estiloPlantilla != null ? estiloPlantilla : EstiloPlantilla.DEFAULT);
    }
}
