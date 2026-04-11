package com.cna.ublkit.ubl.ensamblador;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Catálogo 54 - Códigos de Bienes y Servicios Sujetos a Detracción y sus porcentajes.
 */
public class CatalogoDetracciones {

    private static final Map<String, BigDecimal> porcentajes = new HashMap<>();

    static {
        // Inicializar algunos porcentajes comunes según Anexo 1, 2, 3 de SUNAT
        // Ejemplos: "027" -> Servicios de transporte de carga (10% o 12% a veces, default 12%)
        // "037" -> Demás servicios gravados con el IGV (12%)
        // "020" -> Mantenimiento y reparación (12%)
        porcentajes.put("027", new BigDecimal("0.12"));
        porcentajes.put("037", new BigDecimal("0.12"));
        porcentajes.put("020", new BigDecimal("0.12"));
        porcentajes.put("021", new BigDecimal("0.10"));
        porcentajes.put("022", new BigDecimal("0.12"));
        porcentajes.put("025", new BigDecimal("0.10"));
        // Aquí agregamos un default del 10% para el resto por simplicidad si no está mapeado,
        // o null si queremos forzar que lo pongan.
        // Para asegurar que funcione "auto-calcular" sin errores:
    }

    /**
     * Obtiene el porcentaje de detracción según el tipo de bien/servicio.
     * @param codigo Código de detracción (Catálogo 54).
     * @return El porcentaje o 0.10 por defecto si no se conoce.
     */
    public static BigDecimal obtenerPorcentaje(String codigo) {
        if (codigo == null) return null;
        return porcentajes.getOrDefault(codigo, new BigDecimal("0.10"));
    }
}
