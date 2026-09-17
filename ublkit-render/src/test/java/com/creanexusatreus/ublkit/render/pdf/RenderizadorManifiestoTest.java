package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlManifiesto;
import com.creanexusatreus.ublkit.render.modelo.BorradorManifiesto;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.LineaManifiesto;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.support.PdfVisualSnapshotSupport;

/**
 * Gate del Manifiesto de Carga: verifica que las 5 plantillas {@code manifiesto.a4.html.twig}
 * compilan y cablean los datos reales (cabecera con vehículo, tabla de guías con mercancía y
 * condición de pago). Valida HTML y PDF multipágina para cubrir sintaxis Pebble,
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
        List<LineaManifiesto> lineas = List.of(
                new LineaManifiesto(1, LocalDate.of(2025, 5, 22), "T001-02139", "0002-026708",
                        "Comercial San Juan E.I.R.L.", "10 CAJAS",
                        new BigDecimal("80.00"), "Cancelado", "cancelado"),
                new LineaManifiesto(2, LocalDate.of(2025, 5, 20), "E07-0933", "",
                        "MAYTA VIVANCO CARMEN DOLORES Y ASOCIADOS DE TRANSPORTE INTERNACIONAL",
                        "3 ATAUD · 6 CAJAS", new BigDecimal("150.00"), "Por cobrar", "cobrar"),
                new LineaManifiesto(3, LocalDate.of(2025, 5, 21), "T002-0334", "0002-026715",
                        "Multiservicios Jen E.I.R.L.", "1.5 ROLLOS · 2 PAQUETES · 4 SACOS",
                        new BigDecimal("10.00"), null, ""));

        return new BorradorManifiesto(
                "TRANSPORTES MANTARO E.I.R.L.", "Mantaro", "Av. Los Transportistas 1180 — Ate, Lima",
                "LIMA - LIMA - ATE · 150103", "MF0002-18052025", null, LocalDate.of(2025, 5, 23),
                "C7E778", "HINO", 3, 15, new BigDecimal("240.00"), lineas);
    }

    private static BorradorManifiesto multipageSample() {
        List<LineaManifiesto> lineas = IntStream.rangeClosed(1, 72)
                .mapToObj(i -> new LineaManifiesto(
                        i,
                        LocalDate.of(2025, 5, (i % 28) + 1),
                        "T001-" + String.format("%05d", i),
                        "0002-" + String.format("%06d", i),
                        "Destinatario demo " + i,
                        i + " CAJAS · 1.5 ROLLOS",
                        new BigDecimal("12.50"),
                        i % 2 == 0 ? "Cancelado" : "Por cobrar",
                        i % 2 == 0 ? "cancelado" : "cobrar"))
                .toList();

        BigDecimal totalFlete = lineas.stream()
                .map(linea -> linea.flete() != null ? linea.flete() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BorradorManifiesto(
                "TRANSPORTES MANTARO E.I.R.L.", "Mantaro", "Av. Los Transportistas 1180 — Ate, Lima",
                "LIMA - LIMA - ATE · 150103", "MF0002-18052025", null, LocalDate.of(2025, 5, 23),
                "C7E778", "HINO", lineas.size(), 180, totalFlete, lineas);
    }

    private static BorradorManifiesto compactSample() {
        List<String> destinatarios = List.of(
                "TRANSPORTES Y NEGOCIOS KOTOSH PERU S.A.C.",
                "MAYTA VIVANCO CARMEN DOLORES",
                "MENESES TORRES RAYDA",
                "COMPUESTOS SINTETICOS S A",
                "BARBOZA PINEDO EDWIN LUIS",
                "DISTRIBUIDORA COMERCIAL DEL CENTRO E.I.R.L.");
        List<String> mercancias = List.of(
                "1 ATAUD",
                "31 CAJAS",
                "3 ATAUD · 6 CAJAS",
                "10 ROLLOS",
                "1.5 PAQUETES · 4 SACOS",
                "82 BULTOS");

        List<LineaManifiesto> lineas = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> new LineaManifiesto(
                        i,
                        LocalDate.of(2026, 9, 16),
                        "V001-" + String.format("%08d", i + 4),
                        i % 4 == 0 ? String.valueOf(8985 + i) : "",
                        destinatarios.get((i - 1) % destinatarios.size()),
                        mercancias.get((i - 1) % mercancias.size()),
                        new BigDecimal(i % 3 == 0 ? "400.00" : i % 2 == 0 ? "30.00" : "100.00"),
                        i % 3 == 1 ? "P.C.E" : "PAGADO",
                        i % 3 == 1 ? "pce" : "cancelado"))
                .toList();

        BigDecimal totalFlete = lineas.stream()
                .map(LineaManifiesto::flete)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BorradorManifiesto(
                "TRANSPORTES MANTARO E.I.R.L.", "Mantaro", "Av. Los Transportistas 1180 — Ate, Lima",
                "LIMA - LIMA - ATE · 150103", "MNF1-00000002", null, LocalDate.of(2026, 9, 16),
                "C7E778", "HINO", lineas.size(), 62, totalFlete, lineas);
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

    @Test
    @DisplayName("↳ Exporta captura Classic Mono de 12 guías cuando se solicita")
    void exportaCapturaCompactacionCuandoSeSolicita() throws IOException {
        String outputPath = System.getProperty("ublkit.manifest.capture", "");
        if (outputPath.isBlank()) {
            return;
        }

        ResultadoRender resultado = new RenderizadorPdfManifiesto()
                .renderizar(ContextoRender.of(
                        compactSample(), null, null, Map.of("logo", LOGO_DATA_URI), EstiloPlantilla.CLASSIC_MONO));

        PdfVisualSnapshotSupport.writePdfPreview(resultado.contenidoPdf(), Path.of(outputPath));
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
        assertTrue(html.contains("LIMA - LIMA - ATE · 150103"), "Falta ubicación del emisor en " + estilo);
        assertTrue(html.contains("C7E778"), "Falta placa del manifiesto en " + estilo);
        assertTrue(html.contains("HINO"), "Falta marca del manifiesto en " + estilo);
        assertFalse(html.contains("manifest-header-registration"), "No debe existir RUC/Reg. MTC en " + estilo);
        assertFalse(html.contains("manifest-header-contact"), "No debe existir contacto en " + estilo);
        assertFalse(html.contains("Guías · bultos"), "No debe existir resumen superior de guías/bultos en " + estilo);
        assertFalse(html.contains("Peso · flete"), "No debe existir resumen superior de peso/flete en " + estilo);
        assertTrue(html.contains("T001-02139"), "Falta serie-número de guía en " + estilo);
        assertTrue(html.contains("0002-026708"), "Falta tracking en " + estilo);
        assertTrue(html.contains("Comercial San Juan E.I.R.L."), "Falta destinatario en " + estilo);
        assertTrue(html.contains("MAYTA VIVANCO CARMEN DOLORES Y ASOCIADOS DE TRANSPORTE INTERNACIONAL"),
                "Falta destinatario largo en " + estilo);
        assertTrue(html.contains("10 CAJAS"), "Falta mercancía de un ítem en " + estilo);
        assertTrue(html.contains(">3 ATAUD ·</span>"), "Falta primer ítem de mercancía doble en " + estilo);
        assertTrue(html.contains(">6 CAJAS</span>"), "Falta segundo ítem de mercancía doble en " + estilo);
        assertTrue(html.contains(">1.5 ROLLOS ·</span>")
                        && html.contains(">2 PAQUETES ·</span>")
                        && html.contains(">4 SACOS</span>"),
                "Falta mercancía de tres ítems en " + estilo);
        assertTrue(html.contains(">#</th>"), "Falta columna correlativa en " + estilo);
        assertTrue(html.contains(">Destinatario<"), "Falta columna Destinatario en " + estilo);
        assertTrue(html.contains(">Mercancía<"), "Falta columna Mercancía en " + estilo);
        assertFalse(html.contains(">Peso kg<"), "No debe existir columna Peso kg en " + estilo);
        assertFalse(html.contains("<th class=\"c-bul\">"), "No debe existir columna Bultos en detalle en " + estilo);
        assertFalse(html.contains(">null<"), "No debe imprimirse null para tracking vacío en " + estilo);
        assertTrue(html.contains("cancelado"), "Falta clase pill 'cancelado' en " + estilo);
        assertTrue(html.contains("cobrar"), "Falta clase pill 'cobrar' en " + estilo);
        assertTrue(html.contains("Total · 3 guías consolidadas"), "Falta total de guías en " + estilo);
        assertTrue(html.contains(">Bultos</span>"), "Falta total de bultos en " + estilo);
        assertTrue(html.contains(">15</span>"), "Total de bultos incorrecto en " + estilo);
        assertTrue(html.contains(">Flete</span>"), "Falta total de flete en " + estilo);
        assertTrue(html.contains("240.00"), "Total de flete incorrecto en " + estilo);
        assertTrue(html.contains("Uso interno"), "Falta nota de uso interno en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto compacto · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Compacta columnas y controla el flujo de texto en cada estilo")
    void compactaColumnasYControlaTextoEnCadaEstilo(EstiloPlantilla estilo) {
        String html = renderHtml(sample(), estilo);

        assertTrue(html.contains(".items .c-index { width: 22px;"), "Ancho incorrecto para # en " + estilo);
        assertTrue(html.contains(".items .c-fecha { width: 65px;"), "Ancho incorrecto para fecha en " + estilo);
        assertTrue(html.contains(".items .c-serie { width: 93px;"), "Ancho incorrecto para serie en " + estilo);
        assertTrue(html.contains(".items .c-taquito { width: 65px;"), "Ancho incorrecto para tracking en " + estilo);
        assertTrue(html.contains(".items .c-destinatario { width: 215px;"),
                "Ancho incorrecto para destinatario en " + estilo);
        assertTrue(html.contains(".items .c-mercancia { width: 129px;"),
                "Ancho incorrecto para mercancía en " + estilo);
        assertTrue(html.contains(".items .c-flete { width: 65px;"), "Ancho incorrecto para flete en " + estilo);
        assertTrue(html.contains(".items .c-pago { width: 65px;"), "Ancho incorrecto para pago en " + estilo);
        assertTrue(html.contains("font-size: 7.5px; line-height: 1.2;"),
                "La cabecera de tabla no está compactada en " + estilo);
        assertTrue(html.contains("font-size: 8.5px; line-height: 1.15;"),
                "Las filas no están compactadas en " + estilo);
        assertTrue(html.contains("font-size: 8px;") && html.contains("white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"),
                "Destinatario no usa fuente compacta, nowrap y ellipsis en " + estilo);
        assertTrue(html.contains("<span class=\"recipient-text\">"),
                "Destinatario no usa contenedor de una sola línea en " + estilo);
        assertTrue(html.contains(".items .merch-item { display: inline-block; white-space: nowrap; }"),
                "Los ítems de mercancía no son bloques inseparables en " + estilo);
        assertTrue(html.contains("<td class=\"pago c-pago\">"), "Pago no usa alineación de columna en " + estilo);
        assertTrue(html.contains(">Placa<") && html.contains(">Marca<") && html.contains("Fecha de salida"),
                "La cabecera derecha perdió datos en " + estilo);
    }

    @ParameterizedTest(name = "12 guías compactas · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Mantiene 12 guías compactas en una página A4")
    void mantieneDoceGuiasEnUnaPagina(EstiloPlantilla estilo) {
        ResultadoRender resultado = new RenderizadorPdfManifiesto()
                .renderizar(ContextoRender.of(
                        compactSample(), null, null, Map.of("logo", LOGO_DATA_URI), estilo));

        assertEquals(1, pageCount(resultado.contenidoPdf()),
                "El manifiesto compacto de 12 guías debe caber en una página en " + estilo);
    }

    @ParameterizedTest(name = "manifiesto sin vehículo · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Mantiene placa y marca vacías cuando no hay vehículo")
    void mantieneVehiculoVacioCuandoNoExiste(EstiloPlantilla estilo) {
        BorradorManifiesto source = sample();
        BorradorManifiesto withoutVehicle = new BorradorManifiesto(
                source.emisorRazonSocial(), source.emisorNombreComercial(), source.emisorDireccion(),
                source.emisorUbicacion(), source.numero(), source.serie(), source.fechaSalida(), "", "",
                source.totalGuias(), source.totalBultos(), source.totalFlete(), source.lineas());

        String html = renderHtml(withoutVehicle, estilo);
        assertTrue(html.contains(">Placa<"), "Falta etiqueta Placa en " + estilo);
        assertTrue(html.contains(">Marca<"), "Falta etiqueta Marca en " + estilo);
        assertFalse(html.contains(">null<"), "No debe imprimirse null sin vehículo en " + estilo);
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
