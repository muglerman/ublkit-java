package com.cna.ublkit.ubl.modelo.complemento;

/**
 * Referencia a una guía de remisión relacionada con el documento de venta.
 *
 * @param serieNumero   Serie y número de la guía (ej. "T001-123").
 * @param tipoDocumento Tipo de documento de la guía (Catálogo 01: "09" o "31").
 *
 * @since 0.1.0
 */
public record GuiaRelacionada(
        String serieNumero,
        String tipoDocumento
) {
}
