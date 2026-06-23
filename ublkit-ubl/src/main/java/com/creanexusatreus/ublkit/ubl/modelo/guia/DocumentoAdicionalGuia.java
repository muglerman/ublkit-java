package com.cna.ublkit.ubl.modelo.guia;

/**
 * Documento adicional relacionado al transporte (Catálogo 61).
 *
 * @param tipoDocumento Código del tipo de documento (Catálogo 61).
 * @param serieNumero   Serie y número del documento.
 * @param emisor        Identificador del emisor del documento (RUC).
 *
 * @since 0.1.0
 */
public record DocumentoAdicionalGuia(
        String tipoDocumento,
        String serieNumero,
        String emisor
) {
}
