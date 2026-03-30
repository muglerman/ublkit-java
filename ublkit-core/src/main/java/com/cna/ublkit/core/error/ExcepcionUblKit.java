package com.cna.ublkit.core.error;

/**
 * Excepción base del proyecto UBLKit.
 *
 * <p>Todas las excepciones funcionales o técnicas específicas del proyecto
 * deberían heredar de esta clase para facilitar manejo uniforme.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public class ExcepcionUblKit extends RuntimeException {

    /**
     * Crea una excepción con mensaje.
     *
     * @param mensaje detalle del error
     */
    public ExcepcionUblKit(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una excepción con mensaje y causa.
     *
     * @param mensaje detalle del error
     * @param causa excepción original
     */
    public ExcepcionUblKit(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
