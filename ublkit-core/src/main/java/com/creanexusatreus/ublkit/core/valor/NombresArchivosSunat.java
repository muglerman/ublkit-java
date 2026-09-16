package com.creanexusatreus.ublkit.core.valor;

/** Utilidades comunes para nombres técnicos SUNAT. */
public final class NombresArchivosSunat {
    private NombresArchivosSunat() { }

    public static String buildSunatDocumentName(String ruc, String tipo, String serie, String correlativo) {
        if (ruc == null || !ruc.matches("\\d{11}")) throw new IllegalArgumentException("RUC inválido");
        if (tipo == null || !tipo.matches("\\d{2}")) throw new IllegalArgumentException("Tipo inválido");
        if (serie == null || !serie.matches("[A-Za-z][A-Za-z0-9]{3}")) throw new IllegalArgumentException("Serie inválida");
        return ruc + "-" + tipo + "-" + serie.toUpperCase() + "-" + padded(correlativo);
    }

    public static String padded(String number) {
        long value = Long.parseLong(number);
        if (value <= 0 || value > 99_999_999) throw new IllegalArgumentException("Correlativo inválido");
        return String.format("%08d", value);
    }

    public static String xml(String base) { return normalize(base) + ".xml"; }
    /** ZIP de envío a SUNAT; no lleva prefijo de respuesta. */
    public static String submissionZip(String base) { return stripCdrPrefix(normalize(base)) + ".zip"; }
    /** ZIP de CDR/respuesta de SUNAT. */
    public static String cdrZip(String base) { return prefix(normalize(base), "R-") + ".zip"; }
    /** Alias histórico: representa el ZIP de envío, no el CDR. */
    public static String zip(String base) { return submissionZip(base); }
    public static String cdrXml(String base) { return prefix(normalize(base), "R-") + ".xml"; }
    public static String special(String ruc, String kind, String identifier) {
        if (ruc == null || !ruc.matches("\\d{11}") || kind == null || kind.isBlank() || identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException("Identificador SUNAT especial inválido");
        }
        return ruc + "-" + kind + "-" + identifier;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Base de nombre requerida");
        return value.endsWith(".xml") || value.endsWith(".zip") ? value.substring(0, value.length() - 4) : value;
    }

    private static String prefix(String value, String prefix) { return value.startsWith(prefix) ? value : prefix + value; }
    private static String stripCdrPrefix(String value) { return value.startsWith("R-") ? value.substring(2) : value; }
}
