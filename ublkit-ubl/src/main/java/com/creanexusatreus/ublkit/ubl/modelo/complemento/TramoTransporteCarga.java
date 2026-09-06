package com.creanexusatreus.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Tramo opcional de un servicio de transporte de carga SPOT.
 * Se emite dentro de {@code cac:Shipment/cac:Consignment}.
 *
 * @since 0.1.0
 */
public record TramoTransporteCarga(
        String identificador,
        PuntoTransporte origen,
        PuntoTransporte destino,
        String descripcion,
        BigDecimal valorPreliminarReferencialCargaEfectiva
) {
    public TramoTransporteCarga {
        if (identificador == null || !identificador.matches("\\d{1,2}")) {
            throw new IllegalArgumentException("El identificador del tramo debe tener hasta 2 dígitos");
        }
        if (descripcion != null && (descripcion.isBlank()
                || descripcion.length() < 3 || descripcion.length() > 100)) {
            throw new IllegalArgumentException(
                    "La descripción del tramo debe contener entre 3 y 100 caracteres");
        }
        if (valorPreliminarReferencialCargaEfectiva != null) {
            validarMonto(valorPreliminarReferencialCargaEfectiva);
        }
    }

    private static void validarMonto(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor referencial del tramo debe ser positivo");
        }
        BigDecimal normalizado = valor.stripTrailingZeros();
        int decimales = Math.max(normalizado.scale(), 0);
        int enteros = normalizado.precision() - decimales;
        if (enteros > 12 || decimales > 2) {
            throw new IllegalArgumentException(
                    "El valor referencial del tramo debe tener hasta 12 enteros y 2 decimales");
        }
    }
}
