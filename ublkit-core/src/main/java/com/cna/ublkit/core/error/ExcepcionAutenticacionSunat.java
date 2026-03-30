package com.cna.ublkit.core.error;

/**
 * Excepción para errores de autenticación SUNAT (SOL/OAuth2).
 *
 * @since 0.1.0
 */
public class ExcepcionAutenticacionSunat extends ExcepcionUblKit {

    public ExcepcionAutenticacionSunat(String mensaje) {
        super(mensaje);
    }

    public ExcepcionAutenticacionSunat(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

