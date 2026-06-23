package com.cna.ublkit.ubl.modelo.actor;

/**
 * Persona que firma electrónicamente el documento.
 * Si no se especifica, se usan los datos del emisor.
 *
 * @param ruc         RUC del firmante (11 dígitos).
 * @param razonSocial Razón social del firmante.
 *
 * @since 0.1.0
 */
public record FirmanteDocumento(
        String ruc,
        String razonSocial
) {
}
