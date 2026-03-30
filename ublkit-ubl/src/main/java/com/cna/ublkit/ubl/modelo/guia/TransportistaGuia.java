package com.cna.ublkit.ubl.modelo.guia;

/**
 * Datos del transportista en la Guía de Remisión Electrónica.
 *
 * @param tipoDocumentoIdentidad   Tipo de documento (Catálogo 06).
 * @param numeroDocumentoIdentidad Número de documento (RUC para empresas).
 * @param nombre                   Razón social o nombre del transportista.
 * @param numeroRegistroMTC        Número de registro del Ministerio de Transportes.
 *
 * @since 0.1.0
 */
public record TransportistaGuia(
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombre,
        String numeroRegistroMTC
) {
}
