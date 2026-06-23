package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
 * Gate del Manifiesto de Carga: verifica que las 5 plantillas {@code manifiesto.a4-landscape.html.twig}
 * compilan y cablean los datos reales (cabecera con ubicación del emisor, tabla de guías con distrito
 * de destino, condición de pago → pill). Renderiza HTML (sin Playwright) para validar la sintaxis
 * Pebble y los filtros en cada estilo.
 *
 * @since 0.4.0
 */
@DisplayName("📦 Manifiesto de Carga - Validación de datos en los 5 estilos")
class RenderizadorManifiestoTest {

    private static BorradorManifiesto sample() {
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
                "operaciones@mantaro.pe", "(01) 555-7788", "LIMA - LIMA - ATE · 150103",
                "20600456789", "LMA-123-4567",
                "MF0002-18052025", null, LocalDate.of(2025, 5, 23),
                3, 15, new BigDecimal("240.00"), new BigDecimal("240.00"), lineas);
    }

    @ParameterizedTest(name = "manifiesto · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    @DisplayName("✓ Manifiesto cabla datos reales en cada estilo")
    void manifiestoCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorManifiesto manifiesto = sample();
        ResultadoRender resultado = new RenderizadorHtmlManifiesto()
                .renderizar(ContextoRender.of(manifiesto, null, null, Map.of(), estilo));
        String html = resultado.contenidoHtml();

        assertFalse(html == null || html.isBlank(), "HTML vacío para estilo " + estilo);
        assertTrue(html.contains("MF0002-18052025"), "Falta número de manifiesto en " + estilo);
        assertTrue(html.contains("TRANSPORTES MANTARO E.I.R.L."), "Falta emisor en " + estilo);
        assertTrue(html.contains("20600456789"), "Falta RUC en " + estilo);
        assertTrue(html.contains("LMA-123-4567"), "Falta Reg. MTC en " + estilo);
        // Ubicación del emisor en el header (dep - prov - dist · ubigeo)
        assertTrue(html.contains("LIMA - LIMA - ATE · 150103"), "Falta ubicación del emisor en " + estilo);
        // Tabla de guías
        assertTrue(html.contains("T001-02139"), "Falta serie-número de guía en " + estilo);
        assertTrue(html.contains("0002-026708"), "Falta taquito en " + estilo);
        assertTrue(html.contains("Zoilo Espinoza Castillo"), "Falta remitente en " + estilo);
        assertTrue(html.contains("PUCALLPA"), "Falta distrito de destino en " + estilo);
        assertTrue(html.contains("AHP-842"), "Falta placa en " + estilo);
        // Pills de condición de pago (clase derivada vía matches)
        assertTrue(html.contains("cancelado"), "Falta clase pill 'cancelado' en " + estilo);
        assertTrue(html.contains("cobrar"), "Falta clase pill 'cobrar' en " + estilo);
        // Total consolidado
        assertTrue(html.contains("240.00"), "Falta total de flete/peso en " + estilo);
        assertTrue(html.contains("Uso interno"), "Falta nota de uso interno en " + estilo);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("✓ Genera un PDF A4 válido (Playwright)")
    void generaPdfValido() {
        ResultadoRender resultado = new RenderizadorPdfManifiesto()
                .renderizar(ContextoRender.of(sample(), null, null, Map.of(), EstiloPlantilla.DEFAULT));
        byte[] pdf = resultado.contenidoPdf();
        assertTrue(pdf != null && pdf.length > 1000, "PDF vacío o demasiado pequeño");
        assertTrue(pdf[0] == '%' && pdf[1] == 'P' && pdf[2] == 'D' && pdf[3] == 'F',
                "El contenido no inicia con la cabecera %PDF");
    }
}
