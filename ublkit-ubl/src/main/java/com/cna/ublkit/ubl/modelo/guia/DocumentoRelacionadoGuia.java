package com.cna.ublkit.ubl.modelo.guia;

/**
 * Documento relacionado a la guía de remisión (Catálogo 21).
 *
 * @param tipoDocumento Tipo del documento relacionado (Catálogo 21).
 * @param serieNumero   Serie y número del documento.
 *
 * @since 0.1.0
 */
public record DocumentoRelacionadoGuia(
        String tipoDocumento,
        String serieNumero
) {
}
