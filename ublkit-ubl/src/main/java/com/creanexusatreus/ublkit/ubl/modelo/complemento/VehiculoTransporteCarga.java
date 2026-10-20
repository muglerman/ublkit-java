package com.creanexusatreus.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/** Datos físicos y referenciales opcionales de un vehículo para la operación SPOT 1004. */
public record VehiculoTransporteCarga(
        String identificador,
        String configuracionVehicular,
        BigDecimal cargaUtilTne,
        BigDecimal cargaEfectivaTne,
        BigDecimal valorReferencialPorTne,
        BigDecimal valorPreliminarCargaUtilNominal,
        Boolean aplicaRetornoVacio
) {
    public VehiculoTransporteCarga {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("El identificador del vehículo es obligatorio");
        }
        if (configuracionVehicular != null
                && (configuracionVehicular.isBlank() || configuracionVehicular.length() > 15)) {
            throw new IllegalArgumentException("La configuración vehicular debe contener hasta 15 caracteres");
        }
        validarPositivo(cargaUtilTne, "carga útil");
        validarPositivo(cargaEfectivaTne, "carga efectiva");
        validarPositivo(valorReferencialPorTne, "valor referencial por TNE");
        validarPositivo(valorPreliminarCargaUtilNominal, "valor preliminar de carga útil nominal");
    }

    private static void validarPositivo(BigDecimal valor, String campo) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La " + campo + " debe ser positiva");
        }
    }
}
