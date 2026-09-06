package com.creanexusatreus.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import java.util.List;

/**
 * Información por línea para la operación 1004 (servicio de transporte de
 * carga). Los tres valores referenciales se emiten en {@code DeliveryTerms}
 * con identificadores 01, 02 y 03, siempre en PEN.
 *
 * @since 0.1.0
 */
public record DatosTransporteCarga(
        PuntoTransporte origen,
        PuntoTransporte destino,
        String detalleViaje,
        BigDecimal valorReferencialServicio,
        BigDecimal valorReferencialCargaEfectiva,
        BigDecimal valorReferencialCargaUtilNominal,
        List<TramoTransporteCarga> tramos
) {
    public DatosTransporteCarga {
        if (origen == null) {
            throw new IllegalArgumentException("El punto de origen es obligatorio");
        }
        if (destino == null) {
            throw new IllegalArgumentException("El punto de destino es obligatorio");
        }
        if (detalleViaje == null || detalleViaje.isBlank()
                || detalleViaje.length() < 3 || detalleViaje.length() > 500) {
            throw new IllegalArgumentException(
                    "El detalle del viaje debe contener entre 3 y 500 caracteres");
        }
        validarMonto(valorReferencialServicio, "valor referencial del servicio de transporte");
        validarMonto(valorReferencialCargaEfectiva, "valor referencial sobre la carga efectiva");
        validarMonto(valorReferencialCargaUtilNominal, "valor referencial sobre la carga útil nominal");
        tramos = tramos == null ? List.of() : List.copyOf(tramos);
    }

    private static void validarMonto(BigDecimal valor, String campo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El " + campo + " debe ser positivo");
        }
        BigDecimal normalizado = valor.stripTrailingZeros();
        int decimales = Math.max(normalizado.scale(), 0);
        int enteros = normalizado.precision() - decimales;
        if (enteros > 12 || decimales > 2) {
            throw new IllegalArgumentException(
                    "El " + campo + " debe tener hasta 12 enteros y 2 decimales");
        }
    }
}
