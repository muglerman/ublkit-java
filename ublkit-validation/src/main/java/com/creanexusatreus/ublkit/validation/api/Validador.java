package com.cna.ublkit.validation.api;

import com.cna.ublkit.validation.modelo.ResultadoValidacion;

/**
 * Contrato base para validadores del sistema.
 *
 * @param <T> tipo de objeto a validar
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public interface Validador<T> {

    /**
     * Ejecuta la validación sobre el objeto recibido.
     *
     * @param objetivo objeto a validar
     * @return resultado de validación con errores y advertencias acumuladas
     */
    ResultadoValidacion validar(T objetivo);
}
