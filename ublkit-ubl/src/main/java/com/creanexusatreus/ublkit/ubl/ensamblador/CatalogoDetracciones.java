package com.creanexusatreus.ublkit.ubl.ensamblador;

import java.math.BigDecimal;
/**
 * Compatibilidad para la antigua consulta de porcentajes de detracción.
 * <p>
 * El Catálogo 54 identifica el bien o servicio, pero la tasa efectiva debe ser
 * proporcionada explícitamente por el emisor. No es seguro inferirla: puede
 * cambiar por norma o depender de la operación.
 * </p>
 */
public class CatalogoDetracciones {

    /**
     * No infiere porcentajes a partir del código de Catálogo 54.
     *
     * @param codigo Código de detracción (Catálogo 54).
     * @return siempre {@code null}; el porcentaje debe llegar explícitamente
     *         en {@code Detraccion}.
     * @deprecated Use {@link com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion}
     *             con un porcentaje humano explícito.
     */
    @Deprecated(since = "0.1.0", forRemoval = false)
    public static BigDecimal obtenerPorcentaje(String codigo) {
        return null;
    }
}
