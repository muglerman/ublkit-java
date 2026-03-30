package com.cna.ublkit.catalogs.api;

import com.cna.ublkit.catalogs.modelo.EntradaCatalogo;

import java.util.List;
import java.util.Optional;

/**
 * Define el contrato principal para acceder a los catálogos normativos.
 * Un proveedor es el encargado de parsear y mantener en memoria los diccionarios,
 * abstraíendonos del origen (CSV, Base de Datos, API Externa).
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public interface ProveedorCatalogos {

    /**
     * Obtiene todas las entradas asociadas a un catálogo específico.
     * 
     * @param idCatalogo Identificador del catálogo (ej. "01", "05", "51").
     * @return Lista inmodificable de entradas. Puede ser vacía si el catálogo no existe.
     */
    List<EntradaCatalogo> obtenerCatalogo(String idCatalogo);

    /**
     * Busca una entrada específica dentro de un catálogo determinado.
     *
     * @param idCatalogo Identificador del catálogo a buscar (ej. "01").
     * @param codigo Código interno de la entrada (ej. "03" para Boleta en catálogo 01).
     * @return Un Optional conteniendo la entrada si se encontró, isEmpty de lo contrario.
     */
    Optional<EntradaCatalogo> buscar(String idCatalogo, String codigo);
}
