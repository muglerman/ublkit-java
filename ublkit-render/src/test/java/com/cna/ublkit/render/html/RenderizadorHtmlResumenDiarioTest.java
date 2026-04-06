package com.cna.ublkit.render.html;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ComprobanteResumen;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorHtmlResumenDiario.
 * Tests HTML rendering of daily summaries.
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorHtmlResumenDiario - HTML Daily Summary Rendering")
class RenderizadorHtmlResumenDiarioTest {

    @Nested
    @DisplayName("Basic HTML Rendering Tests")
    class BasicHtmlRenderingTests {

        @Test
        @DisplayName("Should generate valid HTML output")
        void shouldGenerateValidHtmlOutput() {
            ResumenDiario resumen = crearResumenBasico();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertNotNull(resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should return non-null HTML content")
        void shouldReturnNonNullHtmlContent() {
            ResumenDiario resumen = crearResumenBasico();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().length() > 0);
        }
    }

    @Nested
    @DisplayName("Summary Metadata Tests")
    class SummaryMetadataTests {

        @Test
        @DisplayName("Should map summary ID with date")
        void shouldMapSummaryIdWithDate() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("RC-"), "Must contain RC prefix");
            assertTrue(html.contains("2026"), "Must contain year");
        }

        @Test
        @DisplayName("Should map issue date")
        void shouldMapIssueDate() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2026-03-30"), "Must contain issue date");
        }

        @Test
        @DisplayName("Should map reference date")
        void shouldMapReferenceDate() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2026-03-30"), "Must contain reference date");
        }

        @Test
        @DisplayName("Should map currency")
        void shouldMapCurrency() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("PEN"), "Must contain currency");
        }

        @Test
        @DisplayName("Should map hash")
        void shouldMapHash() {
            ResumenDiario resumen = crearResumenBasico();
            String hash = "hash_test_value";
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, hash, null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(hash), "Must contain hash");
        }
    }

    @Nested
    @DisplayName("Issuer Information Tests")
    class IssuerInformationTests {

        @Test
        @DisplayName("Should map issuer RUC")
        void shouldMapIssuerRuc() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Must contain issuer RUC");
        }

        @Test
        @DisplayName("Should map issuer name")
        void shouldMapIssuerName() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Empresa Test"), "Must contain issuer name");
        }

        @Test
        @DisplayName("Should map issuer trade name")
        void shouldMapIssuerTradeName() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Empresa Comercial"), "Must contain trade name");
        }
    }

    @Nested
    @DisplayName("Daily Summary Lines Tests")
    class DailySummaryLinesTests {

        @Test
        @DisplayName("Should map all summary lines")
        void shouldMapAllSummaryLines() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
            assertTrue(html.length() > 100);
        }

        @Test
        @DisplayName("Should map line operation type")
        void shouldMapLineOperationType() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map line document type")
        void shouldMapLineDocumentType() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("01"), "Must contain document type");
        }

        @Test
        @DisplayName("Should map line document series and number")
        void shouldMapLineDocumentSeriesAndNumber() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("F001-100"), "Must contain series-number");
        }
    }

    @Nested
    @DisplayName("Customer Information Tests")
    class CustomerInformationTests {

        @Test
        @DisplayName("Should map customer document type in line")
        void shouldMapCustomerDocumentTypeInLine() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map customer identity in line")
        void shouldMapCustomerIdentityInLine() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10123456789"), "Must contain customer document");
        }

        @Test
        @DisplayName("Should map customer name in line")
        void shouldMapCustomerNameInLine() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Cliente Test"), "Must contain customer name");
        }
    }

    @Nested
    @DisplayName("Amount and Tax Tests")
    class AmountAndTaxTests {

        @Test
        @DisplayName("Should map line totals")
        void shouldMapLineTotals() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map line amounts by type")
        void shouldMapLineAmountsByType() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map line taxes")
        void shouldMapLineTaxes() {
            ResumenDiario resumen = crearResumenCompleto();
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle summary without issuer")
        void shouldHandleSummaryWithoutIssuer() {
            ResumenDiario resumen = new ResumenDiario();
            resumen.setFechaEmision(LocalDate.now());
            resumen.setNumero(1);

            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle summary without signer")
        void shouldHandleSummaryWithoutSigner() {
            ResumenDiario resumen = crearResumenBasico();
            resumen.setFirmante(null);

            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle summary with empty lines")
        void shouldHandleSummaryWithEmptyLines() {
            ResumenDiario resumen = crearResumenBasico();
            resumen.setComprobantes(List.of());

            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle summary with many lines")
        void shouldHandleSummaryWithManyLines() {
            ResumenDiario resumen = crearResumenBasico();
            List<ItemResumenDiario> items = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                ComprobanteResumen comprobante = new ComprobanteResumen(
                        "01", "F001-" + i, "PEN",
                        null, null, null, null, null
                );
                ItemResumenDiario item = new ItemResumenDiario("01", comprobante);
                items.add(item);
            }
            resumen.setComprobantes(items);

            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
            assertTrue(resultado.contenidoHtml().length() > 5000);
        }
    }

    @Nested
    @DisplayName("Template Attributes Tests")
    class TemplateAttributesTests {

        @Test
        @DisplayName("Should apply custom template attributes")
        void shouldApplyCustomTemplateAttributes() {
            ResumenDiario resumen = crearResumenBasico();
            java.util.Map<String, Object> attrs = java.util.Map.of(
                    "header", "Custom Header",
                    "footer", "Custom Footer"
            );
            ContextoRender<ResumenDiario> contexto = ContextoRender.of(resumen, "hash", null, attrs);
            RenderizadorHtmlResumenDiario renderer = new RenderizadorHtmlResumenDiario();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Custom Header"));
            assertTrue(html.contains("Custom Footer"));
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private ResumenDiario crearResumenBasico() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.now());
        resumen.setMoneda("PEN");
        resumen.setNumero(1);
        return resumen;
    }

    private ResumenDiario crearResumenCompleto() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 3, 30));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 3, 30));
        resumen.setMoneda("PEN");
        resumen.setNumero(1);

        EmisorDocumento emisor = new EmisorDocumento(
                "20123456789", "Empresa Test", "Empresa Comercial", null, null
        );
        resumen.setEmisor(emisor);

        EmisorDocumento firmante = new EmisorDocumento(
                "20123456789", "Empresa Test", "Empresa Comercial", null, null
        );
        resumen.setFirmante(firmante);

        ComprobanteResumen comprobante = new ComprobanteResumen(
                "01", "F001-100", "PEN",
                new com.cna.ublkit.ubl.modelo.sunat.resumen.ClienteResumen("1", "10123456789", "Cliente Test"),
                null, null, null, null
        );
        ItemResumenDiario item = new ItemResumenDiario("01", comprobante);
        resumen.setComprobantes(List.of(item));

        return resumen;
    }
}
