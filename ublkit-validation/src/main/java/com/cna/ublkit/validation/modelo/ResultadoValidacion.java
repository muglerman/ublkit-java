package com.cna.ublkit.validation.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resultado de una validación funcional o estructural.
 *
 * <p>Permite acumular errores y advertencias sin interrumpir el flujo con
 * excepciones, lo que mejora la experiencia del desarrollador.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public final class ResultadoValidacion {

    private final List<IncidenciaValidacion> incidencias = new ArrayList<>();

    /**
     * Agrega una incidencia al resultado.
     *
     * @param incidencia incidencia a registrar
     */
    public void agregar(IncidenciaValidacion incidencia) {
        this.incidencias.add(incidencia);
    }

    /**
     * Indica si la validación no contiene errores.
     *
     * @return {@code true} si no existen incidencias de severidad ERROR
     */
    public boolean esValido() {
        return incidencias.stream()
                .noneMatch(i -> i.severidad() == SeveridadValidacion.ERROR);
    }

    /**
     * Retorna las incidencias registradas.
     *
     * @return lista inmodificable de incidencias
     */
    public List<IncidenciaValidacion> getIncidencias() {
        return Collections.unmodifiableList(incidencias);
    }
}
