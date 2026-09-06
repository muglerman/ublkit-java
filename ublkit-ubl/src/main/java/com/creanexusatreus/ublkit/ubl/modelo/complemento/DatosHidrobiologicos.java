package com.creanexusatreus.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Información por línea para la operación 1002 (recursos hidrobiológicos).
 * Se emite como propiedades adicionales del ítem con los códigos 3001 a 3006
 * del Catálogo 55 de SUNAT.
 *
 * @since 0.1.0
 */
public record DatosHidrobiologicos(
        String matriculaEmbarcacion,
        String nombreEmbarcacion,
        String descripcionEspecie,
        String lugarDescarga,
        BigDecimal cantidadEspecieTne,
        LocalDate fechaDescarga
) {
    public DatosHidrobiologicos {
        validarTexto(matriculaEmbarcacion, 15, "matrícula de la embarcación");
        validarTexto(nombreEmbarcacion, 50, "nombre de la embarcación");
        validarTexto(descripcionEspecie, 150, "descripción de la especie");
        validarTexto(lugarDescarga, 100, "lugar de descarga");
        validarCantidad(cantidadEspecieTne);
        if (fechaDescarga == null) {
            throw new IllegalArgumentException("La fecha de descarga es obligatoria");
        }
    }

    private static void validarTexto(String valor, int maximo, String campo) {
        if (valor == null || valor.isBlank() || valor.length() > maximo) {
            throw new IllegalArgumentException(
                    "La " + campo + " debe contener entre 1 y " + maximo + " caracteres");
        }
    }

    private static void validarCantidad(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad de especie vendida debe ser positiva");
        }
        BigDecimal normalizado = valor.stripTrailingZeros();
        int decimales = Math.max(normalizado.scale(), 0);
        int enteros = normalizado.precision() - decimales;
        if (enteros > 12 || decimales > 2) {
            throw new IllegalArgumentException(
                    "La cantidad de especie vendida debe tener hasta 12 enteros y 2 decimales");
        }
    }
}
