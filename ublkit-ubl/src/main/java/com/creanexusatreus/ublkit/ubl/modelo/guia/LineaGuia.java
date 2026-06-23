package com.cna.ublkit.ubl.modelo.guia;

import java.math.BigDecimal;
import java.util.List;

/**
 * Línea de detalle de una Guía de Remisión Electrónica.
 * Cada ítem representa un bien trasladado.
 *
 * @param unidadMedida Unidad de medida (código UN/ECE Rec. 20, ej. "NIU", "KGM").
 * @param cantidad     Cantidad del bien trasladado.
 * @param descripcion  Descripción del bien.
 * @param codigo       Código interno del bien asignado por el emisor.
 * @param codigoSunat  Código SUNAT del bien.
 * @param atributos    Atributos adicionales del ítem (pares código-valor).
 *
 * @since 0.1.0
 */
public record LineaGuia(
        String unidadMedida,
        BigDecimal cantidad,
        String descripcion,
        String codigo,
        String codigoSunat,
        List<AtributoItem> atributos
) {
}
