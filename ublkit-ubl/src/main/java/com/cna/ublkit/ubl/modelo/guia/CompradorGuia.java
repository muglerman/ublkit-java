package com.cna.ublkit.ubl.modelo.guia;

/**
 * Comprador/adquiriente de los bienes en la Guía de Remisión.
 *
 * @param tipoDocumentoIdentidad   Tipo de documento (Catálogo 06).
 * @param numeroDocumentoIdentidad Número de documento de identidad.
 * @param nombre                   Razón social o nombre del comprador.
 *
 * @since 0.1.0
 */
public record CompradorGuia(
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombre
) {
}
