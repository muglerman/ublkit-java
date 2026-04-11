package com.cna.ublkit.ubl.modelo.actor;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;

/**
 * Emisor del documento electrónico (proveedor del bien o servicio).
 * Equivale a {@code cac:AccountingSupplierParty} en UBL 2.1.
 *
 * @param ruc              RUC del emisor (11 dígitos).
 * @param nombreComercial  Nombre comercial (opcional).
 * @param razonSocial      Razón social (obligatoria).
 * @param direccion        Dirección fiscal del emisor (opcional).
 * @param contacto         Datos de contacto (opcional).
 *
 * @since 0.1.0
 */
public record EmisorDocumento(
        String ruc,
        String nombreComercial,
        String razonSocial,
        Direccion direccion,
        Contacto contacto,
        String cuentaBancoNacionDetraccion
) {
    public EmisorDocumento(String ruc, String nombreComercial, String razonSocial, Direccion direccion, Contacto contacto) {
        this(ruc, nombreComercial, razonSocial, direccion, contacto, null);
    }
}
