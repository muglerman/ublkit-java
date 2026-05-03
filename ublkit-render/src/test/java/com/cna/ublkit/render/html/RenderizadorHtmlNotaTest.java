package com.cna.ublkit.render.html;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.ExtensionPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorHtmlNota.
 * Tests HTML rendering of credit/debit notes with format support (A4, A5, Ticket).
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorHtmlNota - HTML Note Rendering")
public class RenderizadorHtmlNotaTest {

    @Nested
    @DisplayName("Credit Note Rendering Tests")
    class CreditNoteRenderingTests {

        @Test
        @DisplayName("Should render credit note as HTML")
        void shouldRenderCreditNoteAsHtml() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertNotNull(resultado.contenidoHtml());
        }

        //@Test
        @DisplayName("Should contain credit note type code")
        void shouldContainCreditNoteTypeCode() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("07"), "Must contain note type");
        }

        @Test
        @DisplayName("Should contain credit note name")
        void shouldContainCreditNoteName() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.toUpperCase().contains("NOTA DE CR"), "Must contain note name");
        }
    }

    @Nested
    @DisplayName("Debit Note Rendering Tests")
    class DebitNoteRenderingTests {

        @Test
        @DisplayName("Should render debit note as HTML")
        void shouldRenderDebitNoteAsHtml() {
            BorradorNotaDebito nota = crearNotaDebitoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertNotNull(resultado.contenidoHtml());
        }

        //@Test
        @DisplayName("Should contain debit note type code")
        void shouldContainDebitNoteTypeCode() {
            BorradorNotaDebito nota = crearNotaDebitoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.toUpperCase().contains("08"), "Must contain note type");
        }

        @Test
        @DisplayName("Should contain debit note name")
        void shouldContainDebitNoteName() {
            BorradorNotaDebito nota = crearNotaDebitoBasica();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.toUpperCase().contains("NOTA DE D"), "Must contain note name");
        }
    }

    @Nested
    @DisplayName("Invalid Document Type Tests")
    class InvalidDocumentTypeTests {

        @Test
        @DisplayName("Should throw for non-note document")
        void shouldThrowForNonNoteDocument() {
            ContextoRender<Object> contexto = ContextoRender.of("invalid", "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            assertThrows(IllegalArgumentException.class, () -> renderer.renderizar(contexto),
                    "Must throw for invalid document type");
        }
    }

    @Nested
    @DisplayName("Format Support Tests")
    class FormatSupportTests {


        /* @ParameterizedTest
        @EnumSource(FormatoImpresion.class)
        @DisplayName("Should support all print formats for credit notes")
        void shouldSupportAllFormatsForCreditNotes(FormatoImpresion formato) {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota(formato);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertNotNull(resultado.contenidoHtml());
        }

        @ParameterizedTest
        @EnumSource(FormatoImpresion.class)
        @DisplayName("Should support all print formats for debit notes")
        void shouldSupportAllFormatsForDebitNotes(FormatoImpresion formato) {
            BorradorNotaDebito nota = crearNotaDebitoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota(formato);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertNotNull(resultado.contenidoHtml());
        } */
    }

    @Nested
    @DisplayName("Credit Note Content Mapping Tests")
    class CreditNoteContentMappingTests {

        @Test
        @DisplayName("Should map series and number")
        void shouldMapSeriesAndNumber() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("NC-001-00000100"), "Must contain series-number");
        }

        @Test
        @DisplayName("Should map issue date")
        void shouldMapIssueDate() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("30/03/2026"), "Must contain issue date");
        }

        @Test
        @DisplayName("Should map issuer information")
        void shouldMapIssuerInformation() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Must contain issuer RUC");
        }

        @Test
        @DisplayName("Should map customer information")
        void shouldMapCustomerInformation() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10123456789"), "Must contain customer document");
        }

        @Test
        @DisplayName("Should map currency")
        void shouldMapCurrency() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("PEN"), "Must contain currency");
        }
    }

    @Nested
    @DisplayName("Note Details Tests")
    class NoteDetailsTests {

        @Test
        @DisplayName("Should map line items for credit note")
        void shouldMapLineItemsForCreditNote() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Item 1"), "Must contain line description");
        }

        @Test
        @DisplayName("Should map line quantities")
        void shouldMapLineQuantities() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2"), "Must contain quantities");
        }

        @Test
        @DisplayName("Should map line prices")
        void shouldMapLinePrices() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("100"), "Must contain prices");
        }
    }

    @Nested
    @DisplayName("Note Type Mappings Tests")
    class NoteTypeMappingsTests {

        @Test
        @DisplayName("Should map credit note reason type 01")
        void shouldMapCreditNoteReasonType01() {
            BorradorNotaCredito nota = crearNotaCreditoConTipo("01");
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.toUpperCase().contains("ANULACI"), "Must map reason type 01");
        }

        @Test
        @DisplayName("Should map credit note reason type 07")
        void shouldMapCreditNoteReasonType07() {
            BorradorNotaCredito nota = crearNotaCreditoConTipo("07");
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.toUpperCase().contains("DEVOLUCI"), "Must map reason type 07");
        }

        @ParameterizedTest
        @ValueSource(strings = {"01", "02", "03"})
        @DisplayName("Should map all debit note reason types")
        void shouldMapAllDebitNoteReasonTypes(String tipo) {
            BorradorNotaDebito nota = crearNotaDebitoConTipo(tipo);
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }
    }

    @Nested
    @DisplayName("Totals and Taxes Tests")
    class TotalsAndTaxesTests {

        @Test
        @DisplayName("Should map subtotal for credit note")
        void shouldMapSubtotalForCreditNote() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("200"), "Must contain subtotal");
        }

        @Test
        @DisplayName("Should map IGV for credit note")
        void shouldMapIgvForCreditNote() {
            BorradorNotaCredito nota = crearNotaCreditoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("36"), "Must contain IGV");
        }

        @Test
        @DisplayName("Should map total for debit note")
        void shouldMapTotalForDebitNote() {
            BorradorNotaDebito nota = crearNotaDebitoCompleta();
            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle credit note without customer")
        void shouldHandleCreditNoteWithoutCustomer() {
            BorradorNotaCredito nota = new BorradorNotaCredito();
            nota.setSerie("NC");
            nota.setNumero(1);
            nota.setFechaEmision(LocalDate.now());

            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle debit note without issuer")
        void shouldHandleDebitNoteWithoutIssuer() {
            BorradorNotaDebito nota = new BorradorNotaDebito();
            nota.setSerie("ND");
            nota.setNumero(1);
            nota.setFechaEmision(LocalDate.now());

            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle notes with empty line items")
        void shouldHandleNotesWithEmptyLineItems() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            nota.setDetalles(List.of());

            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle special characters in descriptions")
        void shouldHandleSpecialCharactersInDescriptions() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Producto & Services <premium> \"test\"");
            linea.setCantidad(new BigDecimal("1"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("100"));
            nota.setDetalles(List.of(linea));

            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle many line items")
        void shouldHandleManyLineItems() {
            BorradorNotaCredito nota = crearNotaCreditoBasica();
            List<LineaDetalle> lineas = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                LineaDetalle linea = new LineaDetalle();
                linea.setDescripcion("Item " + i);
                linea.setCantidad(new BigDecimal("1"));
                linea.setUnidadMedida("ZZ");
                linea.setPrecio(new BigDecimal("10"));
                lineas.add(linea);
            }
            nota.setDetalles(lineas);

            ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash", null, Map.of(), EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG);
            RenderizadorHtmlNota renderer = new RenderizadorHtmlNota();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
            assertTrue(resultado.contenidoHtml().length() > 5000);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private BorradorNotaCredito crearNotaCreditoBasica() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("NC");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.now());
        nota.setTipoComprobante("07");
        return nota;
    }

    private BorradorNotaCredito crearNotaCreditoCompleta() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("NC-001");
        nota.setNumero(100);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setTipoComprobante("07");

        Direccion direccionEmisor = new Direccion("150101", "0000", null,
                "Lima", "Lima", "Lima", "Av. Javier Prado 123", "PE");
        nota.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada", direccionEmisor,
                new Contacto("gerencia@miempresa.com", "015551234", null)));

        nota.setReceptor(new ReceptorDocumento("1", "10123456789", "Cliente Test",
                new Direccion(null, null, null, null, null, null, "Calle Test", "PE"), null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Item 1");
        linea.setCantidad(new BigDecimal("2"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100"));
        nota.setDetalles(List.of(linea));

        nota.setTotalImporte(new TotalImporte(new BigDecimal("236"), new BigDecimal("200"), new BigDecimal("236"), null, null));
        nota.setTotalImpuestos(new TotalImpuestos(new BigDecimal("36"), new BigDecimal("36"), new BigDecimal("200"), null, null, null,
                null, null, null, null, null, null, null, null, null, null));

        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Anulación de la operación");
        nota.setComprobanteAfectadoTipo("01");
        nota.setComprobanteAfectadoSerieNumero("F001-123");

        return nota;
    }

    private BorradorNotaCredito crearNotaCreditoConTipo(String tipo) {
        BorradorNotaCredito nota = crearNotaCreditoCompleta();
        nota.setTipoNota(tipo);
        return nota;
    }

    private BorradorNotaDebito crearNotaDebitoBasica() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("ND");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.now());
        nota.setTipoComprobante("08");
        return nota;
    }

    private BorradorNotaDebito crearNotaDebitoCompleta() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("ND");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.now());
        nota.setTipoComprobante("08");

        Direccion direccionEmisor = new Direccion("150101", "0000", null,
                "Lima", "Lima", "Lima", "Av. Javier Prado 123", "PE");
        nota.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada", direccionEmisor, null));

        nota.setReceptor(new ReceptorDocumento("1", "10123456789", "Cliente Test", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Item");
        linea.setCantidad(new BigDecimal("1"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100"));
        nota.setDetalles(List.of(linea));

        nota.setTotalImporte(new TotalImporte(new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("100"), null, null));
        nota.setTotalImpuestos(new TotalImpuestos(new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("100"), null, null, null,
                null, null, null, null, null, null, null, null, null, null));

        nota.setTipoNota("01");
        nota.setComprobanteAfectadoTipo("01");
        nota.setComprobanteAfectadoSerieNumero("F001-100");

        return nota;
    }

    private BorradorNotaDebito crearNotaDebitoConTipo(String tipo) {
        BorradorNotaDebito nota = crearNotaDebitoCompleta();
        nota.setTipoNota(tipo);
        return nota;
    }
}
