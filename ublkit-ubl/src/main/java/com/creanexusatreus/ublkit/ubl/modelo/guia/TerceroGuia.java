package com.creanexusatreus.ublkit.ubl.modelo.guia;

/**
 * Tercero (vendedor/remitente original) en la Guía de Remisión Transportista.
 *
 * @param tipoDocumentoIdentidad   Tipo de documento (Catálogo 06).
 * @param numeroDocumentoIdentidad Número de documento de identidad.
 * @param nombre                   Razón social o nombre.
 * @param numeroRegistroMTC        Registro MTC (opcional).
 *
 * @since 0.1.0
 */
public record TerceroGuia(
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombre,
        String numeroRegistroMTC
) {
    public TerceroGuia(String tipoDocumentoIdentidad, String numeroDocumentoIdentidad, String nombre) {
        this(tipoDocumentoIdentidad, numeroDocumentoIdentidad, nombre, null);
    }
}
