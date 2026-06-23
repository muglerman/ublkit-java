package com.creanexusatreus.ublkit.render.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for template path resolution by style and format.
 *
 * @since 0.3.0
 */
@DisplayName("PlantillaRutas - Template Path Resolution")
class PlantillaRutasTest {

    @Test
    @DisplayName("Should resolve invoice A4 in classic mono")
    void shouldResolveInvoiceA4InClassicMono() {
        assertEquals("templates/classic-mono/invoice.a4.html.twig",
                PlantillaRutas.ruta("invoice", FormatoImpresion.A4, EstiloPlantilla.CLASSIC_MONO));
    }

    @Test
    @DisplayName("Should resolve A5 in the requested style folder")
    void shouldResolveA5InRequestedStyle() {
        assertEquals("templates/corporate-blue/invoice.a5.html.twig",
                PlantillaRutas.ruta("invoice", FormatoImpresion.A5, EstiloPlantilla.CORPORATE_BLUE));
    }

    @Test
    @DisplayName("Tickets resolve to the generic folder, ignoring the style")
    void shouldResolveTicketToGenericFolderIgnoringStyle() {
        // El ticket térmico es genérico (sin estilo): cualquier estilo cae en templates/generico/.
        assertEquals("templates/generico/note.ticket80mm.html.twig",
                PlantillaRutas.ruta("note", FormatoImpresion.TICKET_80MM, EstiloPlantilla.CLASSIC_MONO));
        assertEquals("templates/generico/invoice.ticket58mm.html.twig",
                PlantillaRutas.ruta("invoice", FormatoImpresion.TICKET_58MM, EstiloPlantilla.MINIMAL_SERIF));
        // Mismo resultado sin importar el estilo solicitado.
        assertEquals(
                PlantillaRutas.ruta("despatch", FormatoImpresion.TICKET_80MM, EstiloPlantilla.BOLD_ACCENT),
                PlantillaRutas.ruta("despatch", FormatoImpresion.TICKET_80MM, EstiloPlantilla.FOREST_MODERN));
    }

    @Test
    @DisplayName("esTicket distinguishes thermal formats from A4/A5")
    void shouldDetectTicketFormats() {
        assertTrue(PlantillaRutas.esTicket(FormatoImpresion.TICKET_58MM));
        assertTrue(PlantillaRutas.esTicket(FormatoImpresion.TICKET_80MM));
        assertFalse(PlantillaRutas.esTicket(FormatoImpresion.A4));
        assertFalse(PlantillaRutas.esTicket(FormatoImpresion.A5));
    }

    @Test
    @DisplayName("Should resolve summary with default style")
    void shouldResolveSummaryWithDefaultStyle() {
        assertEquals("templates/bold-accent/summary.a4.html.twig",
                PlantillaRutas.ruta("summary", FormatoImpresion.A4, null));
    }

    @Test
    @DisplayName("Should resolve style from carpeta")
    void shouldResolveStyleFromCarpeta() {
        assertEquals(EstiloPlantilla.FOREST_MODERN, EstiloPlantilla.fromCarpeta("forest-modern"));
    }
}
