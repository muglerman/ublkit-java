package com.cna.ublkit.render.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Línea de un Manifiesto de Carga: una guía de remisión consolidada en el viaje.
 * Es un modelo de PRESENTACIÓN (documento interno), no forma parte del UBL.
 *
 * @param fechaEmision     Fecha de emisión de la guía.
 * @param guiaSerieNumero  Serie-número de la guía asignada (ej. {@code T001-02139}).
 * @param taquito          Número de taquito/tracking físico de la guía (segunda línea).
 * @param remitente        Razón social / nombre del remitente.
 * @param destinatario     Razón social / nombre del destinatario.
 * @param placa            Placa del vehículo de la guía.
 * @param bultos           Número de bultos.
 * @param peso             Peso en kilogramos.
 * @param flete            Flete en soles.
 * @param condicionPago    Condición de pago para mostrar (ej. {@code Cancelado}, {@code Por cobrar}, {@code P.C.E.}).
 * @param pagoClase        Clase CSS del pill de pago derivada de {@code condicionPago}
 *                         ({@code cancelado} / {@code cobrar} / {@code pce}).
 *
 * @since 0.4.0
 */
public record LineaManifiesto(
        LocalDate fechaEmision,
        String guiaSerieNumero,
        String taquito,
        String remitente,
        String destinatario,
        String placa,
        Integer bultos,
        BigDecimal peso,
        BigDecimal flete,
        String condicionPago,
        String pagoClase
) {
}
