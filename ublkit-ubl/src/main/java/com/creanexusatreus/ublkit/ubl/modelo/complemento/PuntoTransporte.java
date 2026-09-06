package com.creanexusatreus.ublkit.ubl.modelo.complemento;

/**
 * Punto de origen o destino de un servicio de transporte de carga SPOT.
 *
 * @since 0.1.0
 */
public record PuntoTransporte(
        String ubigeo,
        String direccionDetallada
) {
    public PuntoTransporte {
        if (ubigeo == null || !ubigeo.matches("\\d{6}")) {
            throw new IllegalArgumentException("El ubigeo debe tener 6 dígitos");
        }
        if (direccionDetallada == null || direccionDetallada.isBlank()
                || direccionDetallada.length() < 3 || direccionDetallada.length() > 200) {
            throw new IllegalArgumentException(
                    "La dirección detallada debe contener entre 3 y 200 caracteres");
        }
    }
}
