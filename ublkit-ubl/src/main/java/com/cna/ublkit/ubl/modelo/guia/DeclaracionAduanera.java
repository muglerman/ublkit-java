package com.cna.ublkit.ubl.modelo.guia;

/**
 * Referencia a una Declaración Aduanera de Mercancías (DAM) o Declaración Simplificada (DS).
 * Requerida para motivos de comercio exterior (08, 09, 10, 19).
 *
 * @param tipoDocumento  Tipo de documento (Catálogo 61).
 * @param numero         Número de la declaración.
 * @param serieAduana     Serie o código de la aduana.
 *
 * @since 0.1.0
 */
public record DeclaracionAduanera(
        String tipoDocumento,
        String numero,
        String serieAduana
) {
}
