package com.cna.ublkit.render.modelo;

import java.util.List;
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
        String estado,
        List<CuentaBancaria> cuentasBancarias
) {
    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64, Map.of(), null, List.of());
    }

    public static <T> ContextoRender<T> of(T documento) {
        return new ContextoRender<>(documento, null, null, Map.of(), null, List.of());
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           Map<String, Object> atributosPlantilla) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64,
                atributosPlantilla != null ? Map.copyOf(atributosPlantilla) : Map.of(), null, List.of());
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           Map<String, Object> atributosPlantilla, String estado) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64,
                atributosPlantilla != null ? Map.copyOf(atributosPlantilla) : Map.of(), estado, List.of());
    }

    public static <T> ContextoRender<T> of(T documento, String hashDocumento, String qrBase64,
                                           Map<String, Object> atributosPlantilla, String estado,
                                           List<CuentaBancaria> cuentasBancarias) {
        return new ContextoRender<>(documento, hashDocumento, qrBase64,
                atributosPlantilla != null ? Map.copyOf(atributosPlantilla) : Map.of(), estado,
                cuentasBancarias != null ? List.copyOf(cuentasBancarias) : List.of());
    }
}
