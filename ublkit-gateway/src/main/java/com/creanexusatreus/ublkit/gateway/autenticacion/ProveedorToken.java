package com.cna.ublkit.gateway.autenticacion;

import com.cna.ublkit.core.enumerado.TipoAmbiente;

/**
 * Proveedor responsable de gestionar y despachar tokens OAuth2
 * para las APIs REST de SUNAT.
 * <p>
 * Las implementaciones de esta interfaz pueden cachear el token para
 * evitar solicitudes innecesarias al servicio de autenticación si el token
 * aún no ha expirado.
 * </p>
 *
 * @since 0.1.0
 */
@FunctionalInterface
public interface ProveedorToken {

    /**
     * Obtiene un token Bearer válido para hacer peticiones REST.
     *
     * @param credenciales Las credenciales de la empresa.
     * @param ambiente     El ambiente al cual pertenece el token.
     * @return El token JWT o Bearer devuelto por SUNAT.
     */
    String obtenerToken(CredencialesEmpresa credenciales, TipoAmbiente ambiente);
}
