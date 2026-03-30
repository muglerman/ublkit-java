package com.cna.ublkit.ubl.modelo.guia;

/**
 * Conductor/chofer de la guía de remisión.
 *
 * @param tipo                     Tipo de conductor: "Principal" o "Secundario".
 * @param tipoDocumentoIdentidad   Tipo de documento de identidad (Catálogo 06).
 * @param numeroDocumentoIdentidad Número del documento de identidad.
 * @param nombres                  Nombres del conductor.
 * @param apellidos                Apellidos del conductor.
 * @param licencia                 Número de licencia de conducir.
 *
 * @since 0.1.0
 */
public record Conductor(
        String tipo,
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombres,
        String apellidos,
        String licencia
) {
}
