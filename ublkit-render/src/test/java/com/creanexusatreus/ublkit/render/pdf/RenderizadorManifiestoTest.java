package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlManifiesto;
import com.creanexusatreus.ublkit.render.modelo.BorradorManifiesto;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.LineaManifiesto;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;

/**
 * Gate del Manifiesto de Carga: verifica que las 5 plantillas {@code manifiesto.a4.html.twig}
 * compilan y cablean los datos reales (cabecera con ubicación del emisor, tabla de guías con distrito
 * de destino, condición de pago → pill). Valida HTML y PDF multipágina para cubrir sintaxis Pebble,
 * contrato de cabecera y paginación en cada estilo.
 *
 * @since 0.4.0
 */
@DisplayName("📦 Manifiesto de Carga - Validación de datos en los 5 estilos")
class RenderizadorManifiestoTest {

    private static final String LOGO_DATA_URI =
            "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI0MCIgaGVpZ2h0PSI0MCI+PHJlY3Qgd2lkdGg9IjQwIiBoZWlnaHQ9IjQwIiBmaWxsPSIjMUU0Njk0Ii8+PC9zdmc+";
    private static final Pattern PAGE_OBJECT_PATTERN = Pattern.compile("/Type\\s*/Page\\b");
    private static final Pattern PDF_OBJECT_PATTERN = Pattern.compile("\\d+\\s+\\d+\\s+obj\\b(.*?)endobj", Pattern.DOTALL);
    private static final Pattern PAGE_TYPE_INSIDE_OBJECT_PATTERN = Pattern.compile("/Type\\s*/Page\\b");
    private static final Pattern MEDIABOX_PATTERN =
            Pattern.compile("/MediaBox\\s*\\[\\s*([\\d.+-]+)\\s+([\\d.+-]+)\\s+([\\d.+-]+)\\s+([\\d.+-]+)\\s*]");
    private static final Pattern ROTATE_PATTERN = Pattern.compile("/Rotate\\s+(-?\\d+)");

    private static BorradorManifiesto sample() {
        return sample("operaciones@mantaro.pe", "(01) 555-7788");
    }

    private static BorradorManifiesto sample(String email, String telefono) {
        List<LineaManifiesto> lineas = List.of(
                new LineaManifiesto(LocalDate.of(2025, 5, 22), "T001-02139", "0002-026708",
                        "Zoilo Espinoza Castillo", "Comercial San Juan E.I.R.L.", "PUCALLPA", "AHP-842",
                        4, new BigDecimal("80.00"), new BigDecimal("80.00"), "Cancelado", "cancelado"),
                new LineaManifiesto(LocalDate.of(2025, 5, 20), "E07-0933", null,
                        "Importaciones Young People S.A.C.", "Tiendas Selva S.A.C.", "TARAPOTO", "AHP-842",
                        10, new BigDecimal("150.00"), new BigDecimal("150.00"), "Por cobrar", "cobrar"),
                new LineaManifiesto(LocalDate.of(2025, 5, 21), "T002-0334", "0002-026715",
                        "Macedo Ríos del Águila Elva", "Multiservicios Jen E.I.R.L.", "YARINACOCHA", "AEK-946",
                        1, new BigDecimal("10.00"), new BigDecimal("10.00"), null, ""));

        return new BorradorManifiesto(
                "TRANSPORTES MANTARO E.I.R.L.", "Mantaro", "Av. Los Transportistas 1180 — Ate, Lima",
                email, telefono, "LIMA - LIMA - ATE · 150103",
                "20600456789", "LMA-123-4567",
                "MF0002-18052025", null, LocalDate.of(2025, 5, 23),
                3, 15, new BigDecimal("240.00"), new BigDecimal("240.00"), lineas);
    }

    private static BorradorManifiesto multipageSample() {
        List<LineaManifiesto> lineas = IntStream.rangeClosed(1, 72)
                .mapToObj(i -> new LineaManifiesto(
                        LocalDate.of(2025, 5, (i % 28) + 1),
                        "T001-" + String.format("%05d", i),
                        "0002-" + String.format("%06d", i),
                        "Remitente demo " + i,
                        "Destinatario demo " + i,
                        i % 2 == 0 ? "PUCALLPA" : "TARAPOTO",
                        i % 3 == 0 ? "AHP-842" : "AEK-946",
                        (i % 4) + 1,
                        new BigDecimal("10.00"),
                        new BigDecimal("12.50"),
                        i % 2 == 0 ? "Cancelado" : "Por cobrar",
                        i % 2 == 0 ? "cancelado" : "cobrar"))
                .toList();

        int totalBultos = lineas.stream()
                .mapToInt(linea -> linea.bultos() != null ? linea.bultos() : 0)
                .sum();
        BigDecimal totalPeso = lineas.stream()
                .map(linea -> linea.peso() != null ? linea.peso() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFlete = lineas.stream()
                .map(linea -> linea.flete() != null ? linea.flete() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BorradorManifiesto(
                "TRANSPORTES MANTARO E.I.R.L.", "Mantaro", "Av. Los Transportistas 1180 — Ate, Lima",
                "operaciones@mantaro.pe", "(01) 555-7788", "LIMA - LIMA - ATE · 150103",
                "20600456789", "LMA-123-4567",
                "MF0002-18052025", null, LocalDate.of(2025, 5, 23),
                lineas.size(), totalBultos, totalPeso, totalFlete, lineas);
    }

    private static String renderHtml(BorradorManifiesto manifiesto, EstiloPlantilla estilo) {
        return new RenderizadorHtmlManifiesto()
                .renderizar(ContextoRender.of(manifiesto, null, null, Map.of("logo", LOGO_DATA_URI), estilo))
                .contenidoHtml();
    }

    private static int pageCount(byte[] pdf) {
        Matcher matcher = PAGE_OBJECT_PATTERN.matcher(new String(pdf, StandardCharsets.ISO_8859_1));
        int pages = 0;
        while (matcher.find()) {
            pages++;
        }
        return pages;
    }

    private static void assertPaginasA4Vertical(byte[] pdf, EstiloPlantilla estilo) {
        String contenidoPdf = new String(pdf, StandardCharsets.ISO_8859_1);
        Matcher objetos = PDF_OBJECT_PATTERN.matcher(contenidoPdf);
        Matcher mediaBoxGlobal = MEDIABOX_PATTERN.matcher(contenidoPdf);
        float[] mediaBoxHeredado = mediaBoxGlobal.find()
                ? parseMediaBox(mediaBoxGlobal)
                : null;

        int paginasValidadas = 0;
        while (objetos.find()) {
            String objeto = objetos.group(1);
            if (!PAGE_TYPE_INSIDE_OBJECT_PATTERN.matcher(objeto).find()) {
                continue;
            }
            paginasValidadas++;

            Matcher mediaBoxLocal = MEDIABOX_PATTERN.matcher(objeto);
            float[] mediaBox = mediaBoxLocal.find()
                    ? parseMediaBox(mediaBoxLocal)
                    : mediaBoxHeredado;

            assertTrue(mediaBox != null,
                    "No se pudo resolver MediaBox de la página " + paginasValidadas + " en " + estilo);

            float width = mediaBox[2] - mediaBox[0];
            float height = mediaBox[3] - mediaBox[1];
            assertTrue(height > width,
                    "La página " + paginasValidadas + " no está en vertical (alto <= ancho) en " + estilo);
            assertTrue(width >= 590f && width <= 600f,
                    "Ancho fuera de rango A4 en página " + paginasValidadas + " (" + width + ") para " + estilo);
            assertTrue(height >= 835f && height <= 850f,
                    "Alto fuera de rango A4 en página " + paginasValidadas + " (" + height + ") para " + estilo);

            Matcher rotate = ROTATE_PATTERN.matcher(objeto);
            if (rotate.find()) {
                int rotacion = Math.floorMod(Integer.parseInt(rotate.group(1)), 360);
                assertTrue(rotacion != 90 && rotacion != 270,
                        "La página " + paginasValidadas + " tiene rotación apaisada (" + rotacion + "°) en " + estilo);
            }
        }

        assertTrue(paginasValidadas > 1, "No se validaron páginas suficientes para verificar multipágina en " + estilo);
        assertTrue(paginasValidadas == pageCount(pdf),
                "Cantidad de páginas validadas no coincide con el conteo detectado en " + estilo);
    }

    private static float[] parseMediaBox(Matcher mediaBoxMatcher) {
        return new float[]{
                Float.parseFloat(mediaBoxMatcher.group(1)),
                Float.parseFloat(mediaBoxMatcher.group(2)),
                Float.parseFloat(mediaBoxMatcher.group(3)),
                Float.parseFloat(mediaBoxMatcher.group(4))
        };
    }

    @ParameterizedTest(name = "manifiesto · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Manifiesto cablea cabecera y datos reales en cada estilo")
    void manifiestoCableaCabeceraYDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        String html = renderHtml(sample(), estilo);

        assertFalse(html == null || html.isBlank(), "HTML vacío para estilo " + estilo);
        assertTrue(html.contains("MF0002-18052025"), "Falta número de manifiesto en " + estilo);
        assertTrue(html.contains(LOGO_DATA_URI), "Falta logo inline en " + estilo);
        assertTrue(html.contains("TRANSPORTES MANTARO E.I.R.L."), "Falta emisor en " + estilo);
        assertTrue(html.contains("Av. Los Transportistas 1180 — Ate, Lima"), "Falta dirección del emisor en " + estilo);
        assertTrue(html.contains("manifest-header-contact"), "Falta bloque de contacto en " + estilo);
        assertTrue(html.contains("operaciones@mantaro.pe"), "Falta email del emisor en " + estilo);
        assertTrue(html.contains("(01) 555-7788"), "Falta teléfono del emisor en " + estilo);
        assertTrue(html.contains("20600456789"), "Falta RUC en " + estilo);
        assertTrue(html.contains("LMA-123-4567"), "Falta Reg. MTC en " + estilo);
        // Ubicación del emisor en el header (dep - prov - dist · ubigeo)
        assertTrue(html.contains("LIMA - LIMA - ATE · 150103"), "Falta ubicación del emisor en " + estilo);
        assertFalse(html.contains("Establecimiento"), "No debe aparecer etiqueta de establecimiento en " + estilo);
        // Tabla de guías
        assertTrue(html.contains("T001-02139"), "Falta serie-número de guía en " + estilo);
        assertTrue(html.contains("0002-026708"), "Falta taquito en " + estilo);
        assertTrue(html.contains("Comercial San Juan E.I.R.L."), "Falta destinatario en " + estilo);
        assertFalse(html.contains("Zoilo Espinoza Castillo"), "No debe mostrarse remitente en " + estilo);
        assertFalse(html.contains("PUCALLPA"), "No debe mostrarse destino en " + estilo);
        assertFalse(html.contains("AHP-842"), "No debe mostrarse placa en " + estilo);
        assertFalse(html.contains(">Remitente<"), "No debe existir columna Remitente en " + estilo);
        assertTrue(html.contains(">Destinatario<"), "Falta columna Destinatario en " + estilo);
        // Pills de condición de pago (clase derivada vía matches)
        assertTrue(html.contains("cancelado"), "Falta clase pill 'cancelado' en " + estilo);
        assertTrue(html.contains("cobrar"), "Falta clase pill 'cobrar' en " + estilo);
        // Total consolidado
        assertTrue(html.contains("240.00"), "Falta total de flete/peso en " + estilo);
        assertTrue(html.contains("Uso interno"), "Falta nota de uso interno en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto sin contacto · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Omite el bloque de contacto cuando no hay email ni teléfono")
    void omiteBloqueContactoCuandoNoHayEmailNiTelefono(EstiloPlantilla estilo) {
        String html = renderHtml(sample(null, null), estilo);

        assertFalse(html.contains("manifest-header-contact"), "No debe existir bloque de contacto en " + estilo);
        assertFalse(html.contains("manifest-contact-email"), "No debe existir email en " + estilo);
        assertFalse(html.contains("manifest-contact-phone"), "No debe existir teléfono en " + estilo);
        assertTrue(html.contains("Av. Los Transportistas 1180 — Ate, Lima"), "Debe conservar la dirección en " + estilo);
        assertTrue(html.contains("LIMA - LIMA - ATE · 150103"), "Debe conservar la ubicación en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto con solo email · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Renderiza solo el email cuando es el único dato de contacto")
    void renderizaSoloEmailCuandoEsElUnicoDatoDeContacto(EstiloPlantilla estilo) {
        String html = renderHtml(sample("operaciones@mantaro.pe", null), estilo);

        assertTrue(html.contains("manifest-header-contact"), "Debe existir bloque de contacto en " + estilo);
        assertTrue(html.contains("manifest-contact-email"), "Debe existir email en " + estilo);
        assertFalse(html.contains("manifest-contact-phone"), "No debe existir teléfono en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto con solo teléfono · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Renderiza solo el teléfono cuando es el único dato de contacto")
    void renderizaSoloTelefonoCuandoEsElUnicoDatoDeContacto(EstiloPlantilla estilo) {
        String html = renderHtml(sample(null, "(01) 555-7788"), estilo);

        assertTrue(html.contains("manifest-header-contact"), "Debe existir bloque de contacto en " + estilo);
        assertFalse(html.contains("manifest-contact-email"), "No debe existir email en " + estilo);
        assertTrue(html.contains("manifest-contact-phone"), "Debe existir teléfono en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto paged-media · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Declara reglas locales de paginación y márgenes para manifiesto")
    void declaraReglasLocalesDePaginacionYMargenes(EstiloPlantilla estilo) {
        String html = renderHtml(sample(), estilo);

        assertTrue(html.contains("@page { size: A4 portrait; margin: 12mm 10mm; }"),
                "Faltan reglas @page locales en " + estilo);
        assertTrue(html.contains("display: table-header-group;"), "Falta repetición de cabecera de tabla en " + estilo);
        assertTrue(html.contains("display: table-row-group;"), "Falta footer no repetido en " + estilo);
        assertTrue(html.contains("break-inside: avoid;"), "Falta protección de cortes internos en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto multipágina · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Genera un PDF multipágina válido en cada estilo")
    void generaPdfMultipaginaValidoEnCadaEstilo(EstiloPlantilla estilo) {
        ResultadoRender resultado = new RenderizadorPdfManifiesto()
                .renderizar(ContextoRender.of(multipageSample(), null, null, Map.of("logo", LOGO_DATA_URI), estilo));
        byte[] pdf = resultado.contenidoPdf();

        assertTrue(pdf != null && pdf.length > 1_000, "PDF vacío o demasiado pequeño para estilo " + estilo);
        assertTrue(pdf[0] == '%' && pdf[1] == 'P' && pdf[2] == 'D' && pdf[3] == 'F',
                "El contenido no inicia con la cabecera %PDF en " + estilo);
        assertTrue(pageCount(pdf) > 1, "El manifiesto debería abarcar más de una página en " + estilo);
        assertPaginasA4Vertical(pdf, estilo);
    }
}
