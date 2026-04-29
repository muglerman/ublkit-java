package com.cna.ublkit.render.modelo;

/**
 * Helper para resolver rutas de plantillas por estilo y formato.
 *
 * @since 0.3.0
 */
public final class PlantillaRutas {

    private PlantillaRutas() {
    }

    public static String ruta(String plantillaBase, FormatoImpresion formato, EstiloPlantilla estilo) {
        EstiloPlantilla estiloSeguro = estilo != null ? estilo : EstiloPlantilla.DEFAULT;
        FormatoImpresion formatoSeguro = formato != null ? formato : FormatoImpresion.A4;
        return "templates/" + estiloSeguro.carpeta() + "/" + plantillaBase + sufijo(formatoSeguro);
    }

    public static EstiloPlantilla resolver(EstiloPlantilla estilo, EstiloPlantilla defecto) {
        return estilo != null ? estilo : (defecto != null ? defecto : EstiloPlantilla.DEFAULT);
    }

    public static String sufijo(FormatoImpresion formato) {
        return switch (formato) {
            case A5 -> ".a5.html";
            case TICKET_80MM -> ".ticket80mm.html";
            case TICKET_58MM -> ".ticket58mm.html";
            default -> ".a4.html";
        };
    }
}
