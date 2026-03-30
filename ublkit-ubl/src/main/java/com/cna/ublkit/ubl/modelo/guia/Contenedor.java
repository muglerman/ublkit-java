package com.cna.ublkit.ubl.modelo.guia;

/**
 * Contenedor de transporte con número y precinto.
 *
 * @param numero   Número de identificación del contenedor.
 * @param precinto Número de precinto del contenedor (opcional).
 *
 * @since 0.1.0
 */
public record Contenedor(
        String numero,
        String precinto
) {
}
