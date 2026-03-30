package com.cna.ublkit.ubl.modelo.guia;

/**
 * Destinatario de los bienes en la Guía de Remisión.
 *
 * @param tipoDocumentoIdentidad   Tipo de documento (Catálogo 06).
 * @param numeroDocumentoIdentidad Número de documento de identidad.
 * @param nombre                   Razón social o nombre del destinatario.
 *
 * @since 0.1.0
 */
public record DestinatarioGuia(
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombre
) {
}
