package com.cna.ublkit.ubl.modelo.guia;

/**
 * Puerto o aeropuerto de embarque/desembarque.
 *
 * @param codigo      Código del puerto (Catálogo 63) o aeropuerto (Catálogo 64).
 * @param descripcion Descripción legible del puerto o aeropuerto.
 *
 * @since 0.1.0
 */
public record Puerto(
        String codigo,
        String descripcion
) {
}
