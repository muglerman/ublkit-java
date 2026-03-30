package com.cna.ublkit.ubl.modelo.complemento;

/**
 * Referencia a un documento tributario relacionado (ej. factura afectada por nota).
 *
 * @param tipoDocumento Tipo de documento relacionado (Catálogo 12).
 * @param serieNumero   Serie y número del documento.
 *
 * @since 0.1.0
 */
public record DocumentoRelacionado(
        String tipoDocumento,
        String serieNumero
) {
}
