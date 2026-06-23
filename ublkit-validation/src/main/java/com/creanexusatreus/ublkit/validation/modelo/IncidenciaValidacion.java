package com.cna.ublkit.validation.modelo;

/**
 * Representa una incidencia detectada durante el proceso de validación.
 *
 * @param codigo código interno de la incidencia
 * @param mensaje descripción legible del problema detectado
 * @param severidad severidad asociada a la incidencia
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public record IncidenciaValidacion(
        String codigo,
        String mensaje,
        SeveridadValidacion severidad
) {
}
