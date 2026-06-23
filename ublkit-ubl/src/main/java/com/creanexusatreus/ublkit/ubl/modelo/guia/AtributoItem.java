package com.cna.ublkit.ubl.modelo.guia;

/**
 * Atributo adicional de un ítem de la guía de remisión (par código-valor).
 *
 * @param codigo Código del atributo.
 * @param valor  Valor del atributo.
 *
 * @since 0.1.0
 */
public record AtributoItem(
        String codigo,
        String valor
) {
}
