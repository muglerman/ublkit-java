package com.creanexusatreus.ublkit.render.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Manifiesto de Carga — documento INTERNO de control que consolida N guías de remisión
 * transportadas en un mismo viaje. No es un comprobante SUNAT (sin QR, hash ni firma); es un
 * modelo de presentación para el render del PDF.
 *
 * @param emisorRazonSocial     Razón social de la empresa transportista emisora.
 * @param emisorNombreComercial Nombre comercial (opcional).
 * @param emisorDireccion       Dirección del emisor.
 * @param emisorUbicacion       Ubicación del emisor en formato {@code DEPARTAMENTO - PROVINCIA - DISTRITO · ubigeo}.
 * @param numero                Número del manifiesto (ej. {@code MF0002-18052025}).
 * @param serie                 Serie del manifiesto (opcional).
 * @param fechaSalida           Fecha de salida del viaje.
 * @param placa                 Placa del vehículo principal asociado al manifiesto.
 * @param marca                 Marca del mismo vehículo principal.
 * @param totalGuias            Cantidad de guías consolidadas.
 * @param totalBultos           Total de bultos.
 * @param totalFlete            Flete total en soles.
 * @param lineas                Guías consolidadas.
 *
 * @since 0.4.0
 */
public record BorradorManifiesto(
        String emisorRazonSocial,
        String emisorNombreComercial,
        String emisorDireccion,
        String emisorUbicacion,
        String numero,
        String serie,
        LocalDate fechaSalida,
        String placa,
        String marca,
        int totalGuias,
        int totalBultos,
        BigDecimal totalFlete,
        List<LineaManifiesto> lineas
) {
}
