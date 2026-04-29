package com.cna.ublkit.render.modelo;

/**
 * Estilos visuales disponibles para las plantillas de render.
 *
 * @since 0.3.0
 */
public enum EstiloPlantilla {
    CLASSIC_MONO("01-classic-mono"),
    CORPORATE_BLUE("02-corporate-blue"),
    FOREST_MODERN("03-forest-modern"),
    MINIMAL_SERIF("04-minimal-serif"),
    BOLD_ACCENT("05-bold-accent");

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
