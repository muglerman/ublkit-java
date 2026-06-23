package com.cna.ublkit.core.error;

/**
 * Excepción para errores durante la firma digital XML.
 *
 * @since 0.1.0
 */
public class ExcepcionFirmaDigital extends ExcepcionUblKit {

    public ExcepcionFirmaDigital(String mensaje) {
        super(mensaje);
    }

    public ExcepcionFirmaDigital(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

