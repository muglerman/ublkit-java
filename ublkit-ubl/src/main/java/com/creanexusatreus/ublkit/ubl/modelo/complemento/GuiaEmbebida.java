package com.cna.ublkit.ubl.modelo.complemento;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;

import java.math.BigDecimal;

/**
 * Guía de remisión embebida dentro de una factura (Factura-Guía).
 * Permite incluir datos de envío directamente en el comprobante de pago.
 *
 * @param llegada       Dirección del punto de llegada.
 * @param partida       Dirección del punto de partida.
 * @param transportista Datos del transportista.
 * @param nroLicencia   Número de licencia de conducir del chofer.
 * @param transpPlaca   Placa del vehículo.
 * @param transpCodeAuth Código de autorización del transporte.
 * @param transpMarca   Marca del vehículo.
 * @param modTraslado   Modalidad de traslado (Catálogo 18).
 * @param pesoBruto     Peso bruto total del envío.
 * @param undPesoBruto  Unidad de medida del peso bruto (ej. "KGM").
 *
 * @since 0.1.0
 */
public record GuiaEmbebida(
        Direccion llegada,
        Direccion partida,
        ReceptorDocumento transportista,
        String nroLicencia,
        String transpPlaca,
        String transpCodeAuth,
        String transpMarca,
        String modTraslado,
        BigDecimal pesoBruto,
        String undPesoBruto
) {
}
