package com.cna.ublkit.core.modelo;

/**
 * Resultado genérico de operación (éxito/error) sin excepciones.
 *
 * @param <T> tipo de dato devuelto en éxito
 * @since 0.1.0
 */
public record ResultadoOperacion<T>(
        boolean exito,
        T dato,
        String codigoError,
        String mensajeError
) {

    public static <T> ResultadoOperacion<T> ok(T dato) {
        return new ResultadoOperacion<>(true, dato, null, null);
    }

    public static <T> ResultadoOperacion<T> error(String codigo, String mensaje) {
        return new ResultadoOperacion<>(false, null, codigo, mensaje);
    }
}

