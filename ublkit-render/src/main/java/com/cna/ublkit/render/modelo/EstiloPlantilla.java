package com.cna.ublkit.render.modelo;

/**
 * Estilos visuales disponibles para las plantillas de render.
 *
 * @since 0.3.0
 */
public enum EstiloPlantilla {
    CLASSIC_MONO("classic-mono"),
    CORPORATE_BLUE("corporate-blue"),
    FOREST_MODERN("forest-modern"),
    MINIMAL_SERIF("minimal-serif"),
    BOLD_ACCENT("bold-accent");

    public static final EstiloPlantilla DEFAULT = CLASSIC_MONO;

    private final String carpeta;

    EstiloPlantilla(String carpeta) {
        this.carpeta = carpeta;
    }

    public String carpeta() {
        return carpeta;
    }

    public static EstiloPlantilla fromCarpeta(String carpeta) {
        if (carpeta == null || carpeta.isBlank()) {
            return DEFAULT;
        }
        for (EstiloPlantilla estilo : values()) {
            if (estilo.carpeta.equalsIgnoreCase(carpeta)) {
                return estilo;
            }
        }
        return DEFAULT;
    }
}
