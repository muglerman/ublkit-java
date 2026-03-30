package com.cna.ublkit.core.modelo;

/**
 * Representa datos de contacto comerciales para emisores o receptores.
 *
 * @param nombre   Nombre completo de la persona o área de contacto.
 * @param telefono Número telefónico de contacto.
 * @param email    Correo electrónico de contacto.
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public record Contacto(
        String nombre,
        String telefono,
        String email
) {
}
