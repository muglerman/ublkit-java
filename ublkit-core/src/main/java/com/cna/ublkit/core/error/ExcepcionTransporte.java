package com.cna.ublkit.core.error;

/**
 * Excepción para errores de transporte HTTP/SOAP/REST.
 *
 * @since 0.1.0
 */
public class ExcepcionTransporte extends ExcepcionUblKit {

    public ExcepcionTransporte(String mensaje) {
        super(mensaje);
    }

    public ExcepcionTransporte(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

