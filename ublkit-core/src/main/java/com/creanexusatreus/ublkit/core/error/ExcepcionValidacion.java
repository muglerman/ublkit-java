package com.cna.ublkit.core.error;

/**
 * Excepción para errores de validación funcional.
 *
 * @since 0.1.0
 */
public class ExcepcionValidacion extends ExcepcionUblKit {

    public ExcepcionValidacion(String mensaje) {
        super(mensaje);
    }

    public ExcepcionValidacion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

