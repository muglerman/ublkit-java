package com.creanexusatreus.ublkit.ubl.modelo.complemento;

/**
 * Referencia a "otros documentos relacionados" ({@code cac:AdditionalDocumentReference}).
 *
 * @param tipoDocumento Identificador del documento relacionado según el Catálogo 12
 *                      SUNAT (ej. 01 corrección RUC, 06 factura remitente, 99 otros).
 * @param serieNumero   Serie y número del documento.
 *
 * @since 0.1.0
 */
public record DocumentoRelacionado(
        String tipoDocumento,
        String serieNumero
) {
}
