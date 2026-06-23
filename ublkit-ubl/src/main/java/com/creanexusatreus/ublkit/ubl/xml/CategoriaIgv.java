package com.cna.ublkit.ubl.xml;

/**
 * Mapea el código de afectación IGV (Catálogo 07 SUNAT) a la información requerida
 * en el XML UBL 2.1 (TaxCategory, TaxScheme).
 *
 * @param categoriaId   ID de categoría UBL (S, E, O, Z, etc.)
 * @param tribCode      Código de tributo SUNAT (1000, 9997, 9998, 9996, 9995, 1016)
 * @param tribName      Nombre del tributo (IGV, EXO, INA, GRA, EXP, IVAP)
 * @param tribTypeCode  Tipo del tributo (VAT, FRE, OTH)
 *
 * @since 0.1.0
 */
record CategoriaIgv(
        String categoriaId,
        String tribCode,
        String tribName,
        String tribTypeCode
) {

    private static final CategoriaIgv GRAVADO = new CategoriaIgv("S", "1000", "IGV", "VAT");
    private static final CategoriaIgv EXONERADO = new CategoriaIgv("E", "9997", "EXO", "VAT");
    private static final CategoriaIgv INAFECTO = new CategoriaIgv("O", "9998", "INA", "FRE");
    private static final CategoriaIgv GRATUITO = new CategoriaIgv("Z", "9996", "GRA", "FRE");
    private static final CategoriaIgv EXPORTACION = new CategoriaIgv("G", "9995", "EXP", "FRE");
    private static final CategoriaIgv IVAP = new CategoriaIgv("S", "1016", "IVAP", "VAT");

    /**
     * Obtiene la categoría de impuesto según el código de afectación IGV (Catálogo 07 SUNAT).
     *
     * Códigos soportados:
     * - 10-16: Gravado (con variantes)
     * - 17: IVAP (Impuesto Valor Agregado Promocional)
     * - 20-22: Exonerado (operaciones onerosas, transferencias y retiros gratuitos)
     * - 30-37: Inafecto (operaciones no gravadas, retiros y transferencias)
     * - 40: Exportación
     */
    static CategoriaIgv obtener(String igvTipo) {
        if (igvTipo == null) return GRAVADO;
        return switch (igvTipo) {
            // Códigos Gravados (10-16)
            case "10", "11", "12", "13", "14", "15", "16" -> GRAVADO;

            // Código 17: Gravado - IVAP
            case "17" -> IVAP;

            // Códigos Exonerados (20-22)
            // 20: Exonerado - Operación Onerosa
            // 21: Exonerado - Transferencia gratuita
            // 22: Exonerado - Retiro por premio (SUNAT Catalog 7, added Q1 2024)
            case "20", "21", "22" -> EXONERADO;

            // Códigos Inafectos (30-37)
            // 30: Inafecto - Operación Onerosa
            // 31-36: Inafecto - Retiros y variantes
            // 37: Inafecto - Transferencia gratuita (SUNAT Catalog 7, added Q1 2024)
            case "30", "31", "32", "33", "34", "35", "36", "37" -> INAFECTO;

            // Código 40: Exportación
            case "40" -> EXPORTACION;

            // Fallback para códigos no explícitos (gratuitos que empiezan con 1 o 3)
            default -> {
                if (igvTipo.startsWith("1") || igvTipo.startsWith("3")) {
                    yield GRATUITO;
                }
                yield GRAVADO;
            }
        };
    }
}
