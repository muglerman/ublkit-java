package com.cna.ublkit.ubl.modelo.actor;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;

/**
 * Receptor del documento electrónico (cliente / adquiriente).
 * Equivale a {@code cac:AccountingCustomerParty} en UBL 2.1.
 *
 * @param tipoDocIdentidad Código del tipo de documento de identidad (Catálogo 06).
 * @param numDocIdentidad  Número del documento de identidad.
 * @param nombre           Nombre o razón social del receptor.
 * @param direccion        Dirección del receptor (opcional).
 * @param contacto         Datos de contacto (opcional).
 *
 * @since 0.1.0
 */
public record ReceptorDocumento(
        String tipoDocIdentidad,
        String numDocIdentidad,
        String nombre,
        Direccion direccion,
        Contacto contacto
) {
}
