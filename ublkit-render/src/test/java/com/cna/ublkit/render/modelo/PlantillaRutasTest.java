package com.cna.ublkit.render.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("templates/classic-mono/invoice.a4.html",
                PlantillaRutas.ruta("invoice", FormatoImpresion.A4, EstiloPlantilla.CLASSIC_MONO));
    }

    @Test
    @DisplayName("Should resolve note ticket 80mm in classic mono")
    void shouldResolveNoteTicket80mmInClassicMono() {
        assertEquals("templates/classic-mono/note.ticket80mm.html",
                PlantillaRutas.ruta("note", FormatoImpresion.TICKET_80MM, EstiloPlantilla.CLASSIC_MONO));
    }

    @Test
    @DisplayName("Should resolve summary with default style")
    void shouldResolveSummaryWithDefaultStyle() {
        assertEquals("templates/classic-mono/summary.a4.html",
                PlantillaRutas.ruta("summary", FormatoImpresion.A4, null));
    }

    @Test
    @DisplayName("Should resolve style from carpeta")
    void shouldResolveStyleFromCarpeta() {
        assertEquals(EstiloPlantilla.FOREST_MODERN, EstiloPlantilla.fromCarpeta("03-forest-modern"));
    }
}
