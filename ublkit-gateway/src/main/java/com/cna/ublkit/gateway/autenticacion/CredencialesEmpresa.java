package com.cna.ublkit.gateway.autenticacion;

/**
 * Credenciales de la empresa emisora para autenticación ante servicios de SUNAT/OSE.
 * <p>
 * Contiene tanto los datos básicos (RUC, usuario y clave SOL) necesarios para
 * SOAP, como los datos OAuth2 (client_id, client_secret) necesarios para las
 * APIs REST (ej. Guías de Remisión).
 * </p>
 *
 * @param ruc          Número de RUC de la empresa emisora (11 dígitos).
 * @param usuarioSol   Usuario SOL (sin el RUC, ej. "MODDATOS").
 * @param claveSol     Contraseña del usuario SOL.
 * @param clientId     Client ID de la aplicación registrada en SUNAT para APIs REST.
 * @param clientSecret Client Secret de la aplicación registrada en SUNAT para APIs REST.
 *
 * @since 0.1.0
 */
public record CredencialesEmpresa(
        String ruc,
        String usuarioSol,
        String claveSol,
        String clientId,
        String clientSecret
) {

    /**
     * Construye un nombre de usuario concatenando RUC y Usuario SOL (ej. "10000000000MODDATOS").
     * Formato esperado por las APIs de autenticación de SUNAT.
     */
    public String getUsernameConcatenado() {
        return (ruc != null ? ruc : "") + (usuarioSol != null ? usuarioSol : "");
    }
}
