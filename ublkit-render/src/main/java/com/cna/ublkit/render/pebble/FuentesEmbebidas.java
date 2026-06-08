package com.cna.ublkit.render.pebble;

import com.cna.ublkit.render.modelo.EstiloPlantilla;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Genera el CSS {@code @font-face} con las fuentes embebidas en base64 (woff2 de classpath)
 * para cada estilo de plantilla.
 *
 * <p>Reemplaza la dependencia del CDN de Google Fonts ({@code @import url('https://fonts.googleapis.com/...')}),
 * de modo que el render de PDF sea determinista y funcione sin red. El resultado por estilo se
 * cachea: el base64 de las fuentes solo se calcula una vez por JVM.</p>
 *
 * @since 0.4.0
 */
public final class FuentesEmbebidas {

    /** Una fuente concreta a embeber: familia CSS, peso y ruta de classpath del woff2. */
    private record Fuente(String familia, int peso, String recurso) {
    }

    private static final Map<EstiloPlantilla, List<Fuente>> FUENTES_POR_ESTILO = new EnumMap<>(EstiloPlantilla.class);
    private static final Map<EstiloPlantilla, String> CACHE = new ConcurrentHashMap<>();

    static {
        FUENTES_POR_ESTILO.put(EstiloPlantilla.CLASSIC_MONO, concat(inter(), ibmPlexMono()));
        FUENTES_POR_ESTILO.put(EstiloPlantilla.CORPORATE_BLUE, concat(inter(), ibmPlexMono()));
        FUENTES_POR_ESTILO.put(EstiloPlantilla.FOREST_MODERN, concat(spaceGrotesk(), jetBrainsMono()));
        FUENTES_POR_ESTILO.put(EstiloPlantilla.MINIMAL_SERIF, concat(newsreader(), inter(), jetBrainsMono()));
        FUENTES_POR_ESTILO.put(EstiloPlantilla.BOLD_ACCENT, concat(archivo(), jetBrainsMono()));
    }

    private FuentesEmbebidas() {
    }

    /**
     * CSS con los {@code @font-face} (base64) de las fuentes que usa el estilo indicado.
     * Pensado para inyectarse en la plantilla como {@code {{ fontStyle | raw }}}.
     */
    public static String cssParaEstilo(EstiloPlantilla estilo) {
        EstiloPlantilla seguro = estilo != null ? estilo : EstiloPlantilla.DEFAULT;
        return CACHE.computeIfAbsent(seguro, FuentesEmbebidas::construir);
    }

    private static String construir(EstiloPlantilla estilo) {
        StringBuilder css = new StringBuilder();
        for (Fuente fuente : FUENTES_POR_ESTILO.getOrDefault(estilo, List.of())) {
            css.append("@font-face{font-family:'").append(fuente.familia())
                    .append("';font-style:normal;font-weight:").append(fuente.peso())
                    .append(";font-display:swap;src:url(data:font/woff2;base64,")
                    .append(leerBase64(fuente.recurso()))
                    .append(") format('woff2');}");
        }
        return css.toString();
    }

    private static String leerBase64(String recurso) {
        try (InputStream in = FuentesEmbebidas.class.getClassLoader().getResourceAsStream(recurso)) {
            if (in == null) {
                throw new IllegalStateException("Fuente no encontrada en classpath: " + recurso);
            }
            return Base64.getEncoder().encodeToString(in.readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo la fuente: " + recurso, e);
        }
    }

    private static List<Fuente> familia(String nombreCss, String carpeta, int... pesos) {
        List<Fuente> fuentes = new ArrayList<>(pesos.length);
        for (int peso : pesos) {
            fuentes.add(new Fuente(nombreCss, peso, "fonts/" + carpeta + "/" + peso + ".woff2"));
        }
        return fuentes;
    }

    private static List<Fuente> archivo() {
        return familia("Archivo", "archivo", 400, 500, 600, 700, 800, 900);
    }

    private static List<Fuente> inter() {
        return familia("Inter", "inter", 400, 500, 600, 700, 800);
    }

    private static List<Fuente> ibmPlexMono() {
        return familia("IBM Plex Mono", "ibm-plex-mono", 400, 500, 600, 700);
    }

    private static List<Fuente> jetBrainsMono() {
        return familia("JetBrains Mono", "jetbrains-mono", 400, 500, 600);
    }

    private static List<Fuente> spaceGrotesk() {
        return familia("Space Grotesk", "space-grotesk", 400, 500, 600, 700);
    }

    private static List<Fuente> newsreader() {
        return familia("Newsreader", "newsreader", 400, 500, 600);
    }

    @SafeVarargs
    private static List<Fuente> concat(List<Fuente>... grupos) {
        List<Fuente> todas = new ArrayList<>();
        for (List<Fuente> grupo : grupos) {
            todas.addAll(grupo);
        }
        return todas;
    }
}
