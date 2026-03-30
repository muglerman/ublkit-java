package com.cna.ublkit.catalogs.modelo;

import java.util.Map;
import java.util.Optional;

/**
 * Representa una entrada dinámica dentro de un catálogo normativo (ej. SUNAT cargado desde CSV).
 * Define el contrato básico para obtener códigos, descripciones y metadatos adicionales.
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public interface EntradaCatalogo {

    /**
     * Devuelve el código oficial de esta entrada según el catálogo correspondiente.
     *
     * @return código alfanumérico o numérico.
     */
    String getCodigo();

    /**
     * Devuelve la descripción oficial de la entrada.
     * 
     * @return texto descriptivo del código.
     */
    String getDescripcion();

    /**
     * Devuelve un atributo adicional (ej. "codigo_internacional" para el catálogo 05),
     * en caso de que el catálogo tenga más columnas más allá de código y descripción.
     *
     * @param clave el nombre exacto de la columna en el origen (ej. CSV header).
     * @return el valor del atributo si existe, Optional.empty() de lo contrario.
     */
    Optional<String> getAtributoAdicional(String clave);

    /**
     * Devuelve un mapa inmodificable con todos los atributos de la fila (incluyendo
     * código y descripción). Útil para mapeos globales o debug.
     *
     * @return el mapa completo de clave-valor.
     */
    Map<String, String> getTodosAtributos();
}
