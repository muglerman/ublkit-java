package com.cna.ublkit.core.error;

/**
 * Excepción lanzada cuando la serialización de un documento a XML UBL falla.
 *
 * @since 0.1.0
 */
public class ExcepcionSerializacionXml extends ExcepcionUblKit {

    public ExcepcionSerializacionXml(String mensaje) {
        super(mensaje);
    }

    public ExcepcionSerializacionXml(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
