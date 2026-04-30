package com.cna.ublkit.render.modelo;

/**
 * Extensiones soportadas para las plantillas.
 *
 * @since 0.3.0
 */
public enum ExtensionPlantilla {
    HTML(".html"),
    TWIG(".html.twig");

    public static final ExtensionPlantilla DEFAULT = HTML;

    private final String sufijo;

    ExtensionPlantilla(String sufijo) {
        this.sufijo = sufijo;
    }

    public String sufijo() {
        return sufijo;
    }
}
