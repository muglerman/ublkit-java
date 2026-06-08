package com.cna.ublkit.render.modelo;

/**
 * Helper para resolver rutas de plantillas por estilo y formato.
 *
 * @since 0.3.0
 */
public final class PlantillaRutas {

    /**
     * Carpeta de las plantillas genéricas (sin estilo). Los tickets térmicos son monoespaciados y
     * de rollo angosto: el estilo visual no aplica, así que viven en una única plantilla por
     * tipo/ancho, compartida por todos los estilos.
     */
    public static final String CARPETA_GENERICA = "generico";

    private PlantillaRutas() {
    }

    public static String ruta(String plantillaBase, FormatoImpresion formato, EstiloPlantilla estilo, ExtensionPlantilla extension) {
        EstiloPlantilla estiloSeguro = estilo != null ? estilo : EstiloPlantilla.DEFAULT;
        FormatoImpresion formatoSeguro = formato != null ? formato : FormatoImpresion.A4;
        ExtensionPlantilla extensionSegura = extension != null ? extension : ExtensionPlantilla.DEFAULT;
        String carpeta = esTicket(formatoSeguro) ? CARPETA_GENERICA : estiloSeguro.carpeta();
        return "templates/" + carpeta + "/" + plantillaBase + sufijo(formatoSeguro, extensionSegura);
    }

    /** {@code true} si el formato es un ticket térmico (58 mm u 80 mm). */
    public static boolean esTicket(FormatoImpresion formato) {
        return formato == FormatoImpresion.TICKET_58MM || formato == FormatoImpresion.TICKET_80MM;
    }
    
    // Retrocompatibilidad
    public static String ruta(String plantillaBase, FormatoImpresion formato, EstiloPlantilla estilo) {
        return ruta(plantillaBase, formato, estilo, ExtensionPlantilla.DEFAULT);
    }

    public static EstiloPlantilla resolver(EstiloPlantilla estilo, EstiloPlantilla defecto) {
        return estilo != null ? estilo : (defecto != null ? defecto : EstiloPlantilla.DEFAULT);
    }

    public static String sufijo(FormatoImpresion formato, ExtensionPlantilla extension) {
        String ext = extension != null ? extension.sufijo() : ExtensionPlantilla.DEFAULT.sufijo();
        return switch (formato) {
            case A5 -> ".a5" + ext;
            case TICKET_80MM -> ".ticket80mm" + ext;
            case TICKET_58MM -> ".ticket58mm" + ext;
            default -> ".a4" + ext;
        };
    }
    
    // Retrocompatibilidad
    public static String sufijo(FormatoImpresion formato) {
        return sufijo(formato, ExtensionPlantilla.DEFAULT);
    }
}
