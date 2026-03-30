package com.cna.ublkit.ubl.modelo.guia;

/**
 * Punto de destino del traslado.
 *
 * @param ubigeo      Código UBIGEO INEI (6 dígitos).
 * @param direccion   Dirección completa del punto de destino.
 * @param codigoLocal Código de establecimiento/local anexo.
 * @param ruc         RUC asociado al punto de destino.
 *
 * @since 0.1.0
 */
public record PuntoDestino(
        String ubigeo,
        String direccion,
        String codigoLocal,
        String ruc
) {
}
