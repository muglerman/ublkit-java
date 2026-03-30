package com.cna.ublkit.ubl.modelo.guia;

import java.util.List;

/**
 * Vehículo de transporte de la guía de remisión.
 * Soporta vehículo principal y vehículos secundarios (carreta, semirremolque, etc.).
 *
 * @param placa              Número de placa del vehículo.
 * @param numeroCirculacion  Número de tarjeta única de circulación (TUC).
 * @param numeroAutorizacion Número de autorización o certificado de habilitación vehicular.
 * @param codigoEmisor       Código de la entidad emisora de la autorización.
 * @param secundarios        Lista de vehículos secundarios adjuntos.
 *
 * @since 0.1.0
 */
public record Vehiculo(
        String placa,
        String numeroCirculacion,
        String numeroAutorizacion,
        String codigoEmisor,
        List<Vehiculo> secundarios
) {
}
