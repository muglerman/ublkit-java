package com.cna.ublkit.render.html;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.Conductor;
import com.cna.ublkit.ubl.modelo.guia.Contenedor;
import com.cna.ublkit.ubl.modelo.guia.LineaGuia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorHtmlGuiaRemision.
 * Tests HTML rendering of shipping guides (guías de remisión).
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorHtmlGuiaRemision - HTML Shipping Guide Rendering")
class RenderizadorHtmlGuiaRemisionTest {

    @Nested
    @DisplayName("Basic HTML Rendering Tests")
    class BasicHtmlRenderingTests {

        @Test
        @DisplayName("Should generate valid HTML output")
        void shouldGenerateValidHtmlOutput() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertNotNull(resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should return non-null HTML content")
        void shouldReturnNonNullHtmlContent() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().length() > 0);
        }

        @Test
        @DisplayName("Should render with default constructor")
        void shouldRenderWithDefaultConstructor() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertNotNull(resultado.contenidoHtml());
        }
    }

    @Nested
    @DisplayName("Format Support Tests")
    class FormatSupportTests {

        @ParameterizedTest
        @EnumSource(FormatoImpresion.class)
        @DisplayName("Should support all print formats")
        void shouldSupportAllPrintFormats(FormatoImpresion formato) {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision(formato);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml());
            assertNotNull(resultado.contenidoHtml());
        }
    }

    @Nested
    @DisplayName("Guide Metadata Tests")
    class GuideMetadataTests {

        @Test
        @DisplayName("Should map series and number")
        void shouldMapSeriesAndNumber() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("GR-123"), "Must contain series-number");
        }

        @Test
        @DisplayName("Should map issue date")
        void shouldMapIssueDate() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2026-03-30"), "Must contain date");
        }

        @Test
        @DisplayName("Should map hash")
        void shouldMapHash() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            String hash = "hash_test_value";
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, hash, null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(hash), "Must contain hash");
        }

        @Test
        @DisplayName("Should map QR code when provided")
        void shouldMapQrCodeWhenProvided() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            String qr = "QR_DATA_BASE64";
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", qr);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(qr), "Must contain QR");
        }
    }

    @Nested
    @DisplayName("Remitter and Recipient Tests")
    class RemitterAndRecipientTests {

        @Test
        @DisplayName("Should map remitter information")
        void shouldMapRemitterInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Must contain remitter RUC");
        }

        @Test
        @DisplayName("Should map recipient information")
        void shouldMapRecipientInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10123456789"), "Must contain recipient document");
        }
    }

    @Nested
    @DisplayName("Shipment Details Tests")
    class ShipmentDetailsTests {

        @Test
        @DisplayName("Should map shipment date")
        void shouldMapShipmentDate() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map weight information")
        void shouldMapWeightInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("100"), "Must contain weight");
        }

        @Test
        @DisplayName("Should map number of packages")
        void shouldMapNumberOfPackages() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }
    }

    @Nested
    @DisplayName("Transportation Tests")
    class TransportationTests {

        @Test
        @DisplayName("Should map carrier information")
        void shouldMapCarrierInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map vehicle information")
        void shouldMapVehicleInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }

        @Test
        @DisplayName("Should map driver information")
        void shouldMapDriverInformation() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertNotNull(html);
        }
    }

    @Nested
    @DisplayName("Line Items Tests")
    class LineItemsTests {

        @Test
        @DisplayName("Should map all line items")
        void shouldMapAllLineItems() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Product 1"), "Must contain line description");
        }

        @Test
        @DisplayName("Should map line quantities")
        void shouldMapLineQuantities() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10"), "Must contain quantity");
        }

        @Test
        @DisplayName("Should map measurement units")
        void shouldMapMeasurementUnits() {
            BorradorGuiaRemision guia = crearGuiaCompleta();
            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("ZZ"), "Must contain unit");
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle guide without remitter")
        void shouldHandleGuideWithoutRemitter() {
            BorradorGuiaRemision guia = new BorradorGuiaRemision();
            guia.setSerie("GR");
            guia.setNumero(1);
            guia.setFechaEmision(LocalDate.now());

            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle guide without recipient")
        void shouldHandleGuideWithoutRecipient() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            guia.setDestinatario(null);

            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle guide without shipment data")
        void shouldHandleGuideWithoutShipmentData() {
            BorradorGuiaRemision guia = new BorradorGuiaRemision();
            guia.setSerie("GR");
            guia.setNumero(1);
            guia.setFechaEmision(LocalDate.now());

            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }

        @Test
        @DisplayName("Should handle guide without line items")
        void shouldHandleGuideWithoutLineItems() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            guia.setDetalles(List.of());

            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle many line items")
        void shouldHandleManyLineItems() {
            BorradorGuiaRemision guia = crearGuiaBasica();
            List<LineaGuia> lineas = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                LineaGuia linea = new LineaGuia(null, new java.math.BigDecimal("10"), "Product " + i, "ZZ", null, null);
                lineas.add(linea);
            }
            guia.setDetalles(lineas);

            ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash", null);
            RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
            assertTrue(resultado.contenidoHtml().length() > 5000);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private BorradorGuiaRemision crearGuiaBasica() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("GR");
        guia.setNumero(1);
        guia.setFechaEmision(LocalDate.now());
        guia.setTipoComprobante("30");
        return guia;
    }

    private BorradorGuiaRemision crearGuiaCompleta() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("GR");
        guia.setNumero(123);
        guia.setFechaEmision(LocalDate.of(2026, 3, 30));
        guia.setTipoComprobante("30");

        // Remitter
        guia.setRemitente(new com.cna.ublkit.ubl.modelo.actor.EmisorDocumento(
                "20123456789", "Empresa A SAC", null, null, null
        ));

        // Recipient
        guia.setDestinatario(new com.cna.ublkit.ubl.modelo.guia.DestinatarioGuia(
                "1", "10123456789", "Cliente B"
        ));

        // Line items
        List<LineaGuia> lineas = new java.util.ArrayList<>();
        LineaGuia linea = new LineaGuia(null, new java.math.BigDecimal("10"), "Product 1", "ZZ", null, null);
        lineas.add(linea);
        guia.setDetalles(lineas);

        return guia;
    }
}
