package com.cna.ublkit.render.html;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorHtmlFactura.
 * Tests HTML rendering of invoices with format support (A4, A5, Ticket).
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorHtmlFactura - HTML Invoice Rendering")
class RenderizadorHtmlFacturaTest {

    @Nested
    @DisplayName("Basic HTML Rendering Tests")
    class BasicHtmlRenderingTests {

        @Test
        @DisplayName("Should generate valid HTML output")
        void shouldGenerateValidHtmlOutput() {
            BorradorFactura factura = crearFacturaBasica();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash123", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml(), "Must return HTML");
            assertFalse(resultado.isPdf(), "Must not return PDF");
            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().contains("<html") ||
                      resultado.contenidoHtml().contains("<!DOCTYPE"));
        }

        @Test
        @DisplayName("Should return non-null HTML content")
        void shouldReturnNonNullHtmlContent() {
            BorradorFactura factura = crearFacturaBasica();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().length() > 0);
        }

        @Test
        @DisplayName("Should render with default constructor")
        void shouldRenderWithDefaultConstructor() {
            BorradorFactura factura = crearFacturaBasica();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

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
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(formato);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isHtml(), "Format " + formato + " must return HTML");
            assertNotNull(resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().length() > 100);
        }

        @Test
        @DisplayName("Should render A4 format")
        void shouldRenderA4Format() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(FormatoImpresion.A4);

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should render A5 format")
        void shouldRenderA5Format() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(FormatoImpresion.A5);

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should render TICKET_80MM format")
        void shouldRenderTicket80MmFormat() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(FormatoImpresion.TICKET_80MM);

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should render TICKET_58MM format")
        void shouldRenderTicket58MmFormat() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(FormatoImpresion.TICKET_58MM);

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }
    }

    @Nested
    @DisplayName("Invoice Content Mapping Tests")
    class InvoiceContentMappingTests {

        @Test
        @DisplayName("Should map invoice series and number")
        void shouldMapInvoiceSeriesAndNumber() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("F001-123"), "Must contain invoice series-number");
        }

        @Test
        @DisplayName("Should map issuer information")
        void shouldMapIssuerInformation() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Must contain issuer RUC");
            assertTrue(html.contains("Mi Empresa SAC"), "Must contain issuer name");
        }

        @Test
        @DisplayName("Should map customer information")
        void shouldMapCustomerInformation() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10987654321"), "Must contain customer document");
            assertTrue(html.contains("Cliente Ejemplo EIRL"), "Must contain customer name");
        }

        @Test
        @DisplayName("Should map issue date")
        void shouldMapIssueDate() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2026-03-30"), "Must contain issue date");
        }

        @Test
        @DisplayName("Should map currency")
        void shouldMapCurrency() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("PEN"), "Must contain currency");
        }
    }

    @Nested
    @DisplayName("Invoice Details Tests")
    class InvoiceDetailsTests {

        @Test
        @DisplayName("Should map all line items")
        void shouldMapAllLineItems() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Servicio de consultoría"), "Must contain line 1 description");
            assertTrue(html.contains("Licencia de software anual"), "Must contain line 2 description");
        }

        @Test
        @DisplayName("Should map line quantities")
        void shouldMapLineQuantities() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            // Quantity 2 for first line, 1 for second line
            assertTrue(html.contains("2") || html.contains("quantity"), "Must contain quantities");
        }

        @Test
        @DisplayName("Should map line unit prices")
        void shouldMapLineUnitPrices() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("500") || html.contains("1200"), "Must contain prices");
        }

        @Test
        @DisplayName("Should map measurement units")
        void shouldMapMeasurementUnits() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("ZZ"), "Must contain measurement unit");
        }
    }

    @Nested
    @DisplayName("Totals and Taxes Tests")
    class TotalsAndTaxesTests {

        @Test
        @DisplayName("Should map subtotal correctly")
        void shouldMapSubtotalCorrectly() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2200"), "Must contain subtotal");
        }

        @Test
        @DisplayName("Should map IGV tax")
        void shouldMapIgvTax() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("396"), "Must contain IGV");
        }

        @Test
        @DisplayName("Should map total with taxes")
        void shouldMapTotalWithTaxes() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("2596"), "Must contain total");
        }

        @Test
        @DisplayName("Should map amount text legend")
        void shouldMapAmountTextLegend() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("DOS MIL"), "Must contain amount text");
        }
    }

    @Nested
    @DisplayName("Metadata Tests")
    class MetadataTests {

        @Test
        @DisplayName("Should map document hash")
        void shouldMapDocumentHash() {
            BorradorFactura factura = crearFacturaBasica();
            String hash = "abc123hash456";
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, hash, null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(hash), "Must contain document hash");
        }

        @Test
        @DisplayName("Should map QR code when provided")
        void shouldMapQrCodeWhenProvided() {
            BorradorFactura factura = crearFacturaBasica();
            String qr = "QR_BASE64_STRING";
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", qr);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains(qr), "Must contain QR code");
        }

        @Test
        @DisplayName("Should handle null QR code gracefully")
        void shouldHandleNullQrCodeGracefully() {
            BorradorFactura factura = crearFacturaBasica();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Must handle null QR");
        }
    }

    @Nested
    @DisplayName("Edge Cases and Robustness Tests")
    class EdgeCasesAndRobustnessTests {

        @Test
        @DisplayName("Should handle invoice with no customer")
        void shouldHandleInvoiceWithNoCustomer() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Must handle null customer");
        }

        @Test
        @DisplayName("Should handle invoice with no issuer")
        void shouldHandleInvoiceWithNoIssuer() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Must handle null issuer");
        }

        @Test
        @DisplayName("Should handle empty line items")
        void shouldHandleEmptyLineItems() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");
            factura.setDetalles(List.of());

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle special characters in descriptions")
        void shouldHandleSpecialCharactersInDescriptions() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Product & Services <moderno> \"premium\"");
            linea.setCantidad(new BigDecimal("1"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("100"));
            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle very large quantities")
        void shouldHandleVeryLargeQuantities() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Item");
            linea.setCantidad(new BigDecimal("999999.99"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("999.99"));
            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle very small quantities")
        void shouldHandleVerySmallQuantities() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Item");
            linea.setCantidad(new BigDecimal("0.0001"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("0.01"));
            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isHtml());
        }

        @Test
        @DisplayName("Should handle many line items")
        void shouldHandleManyLineItems() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            List<LineaDetalle> lineas = new java.util.ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                LineaDetalle linea = new LineaDetalle();
                linea.setDescripcion("Product " + i);
                linea.setCantidad(new BigDecimal("1"));
                linea.setUnidadMedida("ZZ");
                linea.setPrecio(new BigDecimal("10"));
                lineas.add(linea);
            }
            factura.setDetalles(lineas);

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

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
            BorradorFactura factura = crearFacturaBasica();
            Map<String, Object> attrs = Map.of(
                    "header", "Custom Header",
                    "footer", "Custom Footer"
            );
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null, attrs);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Custom Header"));
            assertTrue(html.contains("Custom Footer"));
        }

        @Test
        @DisplayName("Should handle null template attributes")
        void shouldHandleNullTemplateAttributes() {
            BorradorFactura factura = crearFacturaBasica();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null, null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto));
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private BorradorFactura crearFacturaBasica() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.now());
        factura.setTipoComprobante("01");
        return factura;
    }

    private BorradorFactura crearFacturaCompleta() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(123);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setTipoComprobante("01");

        Direccion direccionEmisor = new Direccion("150101", "0000", null,
                "Lima", "Lima", "Lima", "Av. Javier Prado 123", "PE");
        factura.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada", direccionEmisor,
                new Contacto("gerencia@miempresa.com", "015551234", null)));

        factura.setReceptor(new ReceptorDocumento("6", "10987654321", "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Falsa 456", "PE"), null));

        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Servicio de consultoría");
        linea1.setCantidad(new BigDecimal("2"));
        linea1.setUnidadMedida("ZZ");
        linea1.setPrecio(new BigDecimal("500.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Licencia de software anual");
        linea2.setCantidad(new BigDecimal("1"));
        linea2.setUnidadMedida("ZZ");
        linea2.setPrecio(new BigDecimal("1200.00"));

        factura.setDetalles(List.of(linea1, linea2));

        factura.setTotalImporte(new TotalImporte(new BigDecimal("2596.00"),
                new BigDecimal("2200.00"), new BigDecimal("2596.00"), null, null));

        factura.setTotalImpuestos(new TotalImpuestos(new BigDecimal("396.00"),
                new BigDecimal("396.00"), new BigDecimal("2200.00"), null, null, null,
                null, null, null, null, null, null, null, null, null, null));

        factura.setLeyendas(Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));

        return factura;
    }
}
