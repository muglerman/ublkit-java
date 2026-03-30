package com.cna.ublkit.ubl.modelo.guia;

/**
 * Punto de partida del traslado.
 *
 * @param ubigeo      Código UBIGEO INEI (6 dígitos).
 * @param direccion   Dirección completa del punto de partida.
 * @param codigoLocal Código de establecimiento/local anexo.
 * @param ruc         RUC asociado al punto de partida.
 *
 * @since 0.1.0
 */
public record PuntoPartida(
        String ubigeo,
        String direccion,
        String codigoLocal,
        String ruc
) {
}
