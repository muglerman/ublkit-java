package com.cna.ublkit.ubl.modelo.sunat.baja;

/**
 * Ítem de una Comunicación de Baja.
 *
 * @param serie               Serie del comprobante a dar de baja.
 * @param numero              Número del comprobante.
 * @param tipoComprobante     Tipo de comprobante (Catálogo 01).
 * @param descripcionSustento Razón de la baja.
 *
 * @since 0.1.0
 */
public record ItemBaja(
        String serie,
        Integer numero,
        String tipoComprobante,
        String descripcionSustento
) {
}
