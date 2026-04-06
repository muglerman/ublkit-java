package com.cna.ublkit.render.html;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorHtmlComunicacionBaja.
 * Tests HTML rendering of voided document communications.
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorHtmlComunicacionBaja - HTML Voided Communication Rendering")
class RenderizadorHtmlComunicacionBajaTest {

    @Nested
    @DisplayName("Basic HTML Rendering Tests")
    class BasicHtmlRenderingTests {

        @Test
        @DisplayName("Should generate valid HTML output")
        void shouldGenerateValidHtmlOutput() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertNotNull(resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should return non-null HTML content")
        void shouldReturnNonNullHtmlContent() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().length() > 0);
        }
    }

    @Nested
    @DisplayName("Communication Metadata Tests")
    class CommunicationMetadataTests {

        @Test
        @DisplayName("Should map communication ID with date")
        void shouldMapCommunicationIdWithDate() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("RA-"), "Must contain RA prefix");
            assertTrue(html.contains("2026"), "Must contain year");
        }

        @Test
        @DisplayName("Should map issue date")
        void shouldMapIssueDate() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2026-03-30"), "Must contain issue date");
        }

        @Test
        @DisplayName("Should map currency")
        void shouldMapCurrency() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("PEN"), "Must contain currency");
        }

        @Test
        @DisplayName("Should map hash")
        void shouldMapHash() {
            ComunicacionBaja baja = crearComunicacionBasica();
            String hash = "hash_value_test";
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, hash, null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(hash), "Must contain hash");
        }

        @Test
        @DisplayName("Should map QR code when provided")
        void shouldMapQrCodeWhenProvided() {
            ComunicacionBaja baja = crearComunicacionBasica();
            String qr = "QR_CODE_BASE64";
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", qr);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(qr), "Must contain QR");
        }
    }

    @Nested
    @DisplayName("Issuer Information Tests")
    class IssuerInformationTests {

        @Test
        @DisplayName("Should map issuer RUC")
        void shouldMapIssuerRuc() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Must contain issuer RUC");
        }

        @Test
        @DisplayName("Should map issuer name")
        void shouldMapIssuerName() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Empresa Test"), "Must contain issuer name");
        }

        @Test
        @DisplayName("Should map issuer trade name")
        void shouldMapIssuerTradeName() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Empresa Comercial"), "Must contain trade name");
        }
    }

    @Nested
    @DisplayName("Voided Items Tests")
    class VoidedItemsTests {

        @Test
        @DisplayName("Should map all voided items")
        void shouldMapAllVoidedItems() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("01"), "Must contain document type");
            assertTrue(html.contains("F001"), "Must contain series");
            assertTrue(html.contains("100"), "Must contain number");
        }

        @Test
        @DisplayName("Should map item document type")
        void shouldMapItemDocumentType() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("01"), "Must contain document type");
        }

        @Test
        @DisplayName("Should map item series")
        void shouldMapItemSeries() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("F001"), "Must contain series");
        }

        @Test
        @DisplayName("Should map item number")
        void shouldMapItemNumber() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("100"), "Must contain number");
        }

        @Test
        @DisplayName("Should map voiding reason")
        void shouldMapVoidingReason() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Cancelación"), "Must contain reason");
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle communication without issuer")
        void shouldHandleCommunicationWithoutIssuer() {
            ComunicacionBaja baja = new ComunicacionBaja();
            baja.setFechaEmision(LocalDate.now());
            baja.setNumero(1);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle communication without signer")
        void shouldHandleCommunicationWithoutSigner() {
            ComunicacionBaja baja = crearComunicacionBasica();
            baja.setFirmante(null);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle communication with empty items")
        void shouldHandleCommunicationWithEmptyItems() {
            ComunicacionBaja baja = crearComunicacionBasica();
            baja.setComprobantes(List.of());

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle communication with many voided items")
        void shouldHandleCommunicationWithManyVoidedItems() {
            ComunicacionBaja baja = crearComunicacionBasica();
            List<ItemBaja> items = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                ItemBaja item = new ItemBaja("F0" + (i / 10), i, "01", "Reason " + i);
                items.add(item);
            }
            baja.setComprobantes(items);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

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
            ComunicacionBaja baja = crearComunicacionBasica();
            java.util.Map<String, Object> attrs = java.util.Map.of(
                    "header", "Custom Header",
                    "footer", "Custom Footer"
            );
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null, attrs);
            RenderizadorHtmlComunicacionBaja renderer = new RenderizadorHtmlComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Custom Header"));
            assertTrue(html.contains("Custom Footer"));
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private ComunicacionBaja crearComunicacionBasica() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.now());
        baja.setMoneda("PEN");
        baja.setNumero(1);
        return baja;
    }

    private ComunicacionBaja crearComunicacionCompleta() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 3, 30));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 3, 29));
        baja.setMoneda("PEN");
        baja.setNumero(1);

        EmisorDocumento emisor = new EmisorDocumento(
                "20123456789", "Empresa Test", "Empresa Comercial", null, null
        );
        baja.setEmisor(emisor);

        com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento firmante = new com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento(
                "20123456789", "Empresa Test"
        );
        baja.setFirmante(firmante);

        ItemBaja item = new ItemBaja("F001", 100, "01", "Cancelación");
        baja.setComprobantes(List.of(item));

        return baja;
    }
}
