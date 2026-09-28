package com.creanexusatreus.ublkit.render.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Línea de un Manifiesto de Carga: una guía de remisión consolidada en el viaje.
 * Es un modelo de PRESENTACIÓN (documento interno), no forma parte del UBL.
 *
 * @param numeroOrden      Posición visual correlativa de la guía dentro del manifiesto.
 * @param fechaEmision     Fecha de emisión de la guía.
 * @param guiaSerieNumero  Serie-número de la guía asignada (ej. {@code T001-02139}).
 * @param taquito          Número de taquito/tracking físico de la guía (segunda línea).
 * @param destinatario     Razón social / nombre del destinatario.
 * @param mercancias       Cantidades y descripciones de los ítems de la guía, una entrada por línea visual.
 * @param flete            Flete en soles.
 * @param condicionPago    Condición de pago para mostrar (ej. {@code Cancelado}, {@code Por cobrar}, {@code P.C.E.}).
 * @param pagoClase        Clase CSS del pill de pago derivada de {@code condicionPago}
 *                         ({@code cancelado} / {@code cobrar} / {@code pce}).
 *
 * @since 0.4.0
 */
public record LineaManifiesto(
        int numeroOrden,
        LocalDate fechaEmision,
        String guiaSerieNumero,
        String taquito,
        String destinatario,
        List<String> mercancias,
        BigDecimal flete,
        String condicionPago,
        String pagoClase
) {
}
