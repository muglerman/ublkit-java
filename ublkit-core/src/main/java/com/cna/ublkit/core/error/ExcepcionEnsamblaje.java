package com.cna.ublkit.core.error;

/**
 * Excepción lanzada cuando el ensamblaje (enrichment) de un documento falla.
 *
 * @since 0.1.0
 */
public class ExcepcionEnsamblaje extends ExcepcionUblKit {

    public ExcepcionEnsamblaje(String mensaje) {
        super(mensaje);
    }

    public ExcepcionEnsamblaje(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
