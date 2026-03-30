package com.cna.ublkit.ubl.modelo.guia;

/**
 * Documento de baja asociado a una guía de remisión.
 *
 * @param tipoDocumento Tipo del documento dado de baja (Catálogo 01).
 * @param serieNumero   Serie y número del documento dado de baja.
 *
 * @since 0.1.0
 */
public record DocumentoBajaGuia(
        String tipoDocumento,
        String serieNumero
) {
}
