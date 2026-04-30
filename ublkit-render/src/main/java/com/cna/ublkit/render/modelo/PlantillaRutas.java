package com.cna.ublkit.render.modelo;

/**
 * Helper para resolver rutas de plantillas por estilo y formato.
 *
 * @since 0.3.0
 */
public final class PlantillaRutas {

    private PlantillaRutas() {
    }

    public static String ruta(String plantillaBase, FormatoImpresion formato, EstiloPlantilla estilo, ExtensionPlantilla extension) {
        EstiloPlantilla estiloSeguro = estilo != null ? estilo : EstiloPlantilla.DEFAULT;
        FormatoImpresion formatoSeguro = formato != null ? formato : FormatoImpresion.A4;
        ExtensionPlantilla extensionSegura = extension != null ? extension : ExtensionPlantilla.DEFAULT;
        return "templates/" + estiloSeguro.carpeta() + "/" + plantillaBase + sufijo(formatoSeguro, extensionSegura);
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
