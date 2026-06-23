package com.cna.ublkit.core.modelo;

/**
 * Representa una dirección física general, utilizable por emisores, receptores,
 * y lugares de partida o llegada.
 *
 * @param ubigeo       Código de 6 dígitos que corresponden al Departamento, Provincia, y Distrito.
 *                     Ejemplo: 050101
 * @param codigoLocal  Código de 4 dígitos asignado por SUNAT, que identifica al establecimiento anexo.
 *                     Tratándose del domicilio fiscal o si no se puede determinar, usar "0000".
 * @param urbanizacion Nombre de la urbanización.
 * @param departamento Nombre del Departamento o Región. Ejemplo: Ayacucho
 * @param provincia    Nombre de la Provincia. Ejemplo: Huamanga
 * @param distrito     Nombre del Distrito. Ejemplo: Ayacucho
 * @param direccion    Dirección detallada. Ejemplo: Jirón las piedras 123
 * @param codigoPais   Código de 2 dígitos (ISO 3166-1 alfa-2) que corresponde al país. Ejemplo: PE
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public record Direccion(
        String ubigeo,
        String codigoLocal,
        String urbanizacion,
        String departamento,
        String provincia,
        String distrito,
        String direccion,
        String codigoPais
) {
}
