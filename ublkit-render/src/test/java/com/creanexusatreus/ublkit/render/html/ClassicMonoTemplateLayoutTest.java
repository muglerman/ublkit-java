package com.creanexusatreus.ublkit.render.html;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

class ClassicMonoTemplateLayoutTest {

    private static final List<String> TEMPLATES = List.of(
            "boleta.a4", "debit.a4", "debit.a5", "despatch-carrier.a4", "despatch.a4",
            "despatch.a5", "invoice.a4", "invoice.a5", "manifiesto.a4", "note.a4",
            "note.a5", "summary.a4", "voided.a4");

    @Test
    void classicMonoMantieneReglasDeLayoutEstablesEnTodasLasVariantes() throws IOException {
        for (String template : TEMPLATES) {
            String html = resource(template);
            assertTrue(html.contains("Classic Mono refinement"), template + " debe declarar el refinamiento visual");
            assertTrue(html.contains("break-inside: avoid"), template + " debe evitar cortes de filas o bloques");
            assertTrue(html.contains("letter-spacing: .07em") || html.contains("letter-spacing: .055em"),
                    template + " debe usar tracking moderado");
        }
    }

    @Test
    void facturaClassicMonoConservaCuadriculaDatosYQrCuadrado() throws IOException {
        String invoice = resource("invoice.a4");
        assertTrue(invoice.contains("grid-template-columns: 96px minmax(0, 1fr)"));
        assertTrue(invoice.contains("table-layout: fixed"));
        assertTrue(invoice.contains(".foot .qr { width: 102px; height: 102px"));
        assertTrue(invoice.contains("object-fit: contain"));
        assertTrue(invoice.contains("class=\"block detraction\""));

        // Bindings fiscales y de verificación siguen siendo los originales de la plantilla.
        assertTrue(invoice.contains("{{ doc.totalImporte.importeConImpuestos|n_format }}"));
        assertTrue(invoice.contains("{{ doc.detraccion.monto|n_format }}"));
        assertTrue(invoice.contains("{{ qrBase64 }}"));
        assertTrue(invoice.contains("{{ hashDocumento }}"));
    }

    private String resource(String name) throws IOException {
        String path = "templates/classic-mono/" + name + ".html.twig";
        try (var input = getClass().getClassLoader().getResourceAsStream(path)) {
            assertTrue(input != null, "No se encontró " + path);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
