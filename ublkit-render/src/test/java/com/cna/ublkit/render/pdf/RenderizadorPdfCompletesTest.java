package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

/**
 * Suite de pruebas EXHAUSTIVA para la generación de PDFs. Verifica: - Estructura HTML correcta - Datos mapeados
 * correctamente - Cálculos correctos - Campos nulos/vacíos - Formatos múltiples - Validación de contenido PDF
 *
 * @since 0.2.0
 */
@DisplayName("Renderers PDF - Suite Completa")
class RenderizadorPdfCompletesTest {

    @Nested
    @DisplayName("📄 Factura - Tests Generales")
    class FacturaGeneralTests {

        @Test
        @DisplayName("✓ Debe generar PDF válido con estructura básica")
        void debeGenerarPdfValidoConEstructuraBasica() throws IOException {
            BorradorFactura factura = crearFacturaMinima();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash123", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf(), "Debe ser PDF");
            assertFalse(resultado.isHtml(), "NO debe ser HTML");
            assertNotNull(resultado.contenidoPdf(), "PDF no debe ser nulo");
            assertTrue(resultado.contenidoPdf().length > 500, "PDF debe tener tamaño significativo");
            assertEquals("%PDF-", new String(resultado.contenidoPdf(), 0, 5), "Debe tener cabecera PDF válida");
        }

        @ParameterizedTest
        @EnumSource(FormatoImpresion.class)
        @DisplayName("✓ Debe soportar todos los formatos de impresión")
        void debeSoportarTodosLosFormatos(FormatoImpresion formato) throws IOException {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash456", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura(formato);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf(), "Formato " + formato + " debe generar PDF");
            assertTrue(resultado.contenidoPdf().length > 100, "Formato " + formato + " debe tener contenido");
        }

        @Test
        @DisplayName("✓ HTML debe mapear emisor correctamente")
        void htmlDebeMapearEmisorCorrectamente() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("20123456789"), "Debe contener RUC emisor");
            assertTrue(html.contains("Mi Empresa SAC"), "Debe contener nombre emisor");
            assertTrue(html.contains("Av. Javier Prado 123"), "Debe contener dirección emisor");
        }

        @Test
        @DisplayName("✓ HTML debe mapear cliente correctamente")
        void htmlDebeMapearClienteCorrectamente() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("10987654321"), "Debe contener DNI cliente");
            assertTrue(html.contains("Cliente Ejemplo EIRL"), "Debe contener nombre cliente");
        }

        @Test
        @DisplayName("✓ HTML debe mapear serie y número de factura")
        void htmlDebeMapearSerieYNumeroFactura() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("F001-00000123"), "Debe contener serie y número: F001-00000123");
        }

        @Test
        @DisplayName("✓ HTML debe mapear fechas correctamente")
        void htmlDebeMapearFechasCorrectamente() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("30/03/2026"), "Debe contener fecha de emisión");
        }
    }

    @Nested
    @DisplayName("💰 Totales e Impuestos - Tests Críticos")
    class TotalesImpuestosTests {

        @Test
        @DisplayName("✓ HTML debe mapear subtotal correctamente")
        void htmlDebeMapearSubtotal() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            // 2 * 500 + 1 * 1200 = 2200
            assertTrue(html.contains("2200"), "Debe contener subtotal 2200");
        }

        @Test
        @DisplayName("✓ HTML debe mapear IGV correctamente")
        void htmlDebeMapearIgv() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            // IGV = 396
            assertTrue(html.contains("396"), "Debe contener IGV 396");
        }

        @Test
        @DisplayName("✓ HTML debe mapear total con impuestos correctamente")
        void htmlDebeMapearTotalConImpuestos() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            // Total = 2596
            assertTrue(html.contains("2596"), "Debe contener total 2596");
        }

        @Test
        @DisplayName("✓ HTML debe mapear leyenda de monto en letras")
        void htmlDebeMapearLeyenda() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("DOS MIL"), "Debe contener leyenda de monto");
        }

        @Test
        @DisplayName("✓ HTML debe mapear moneda")
        void htmlDebeMapearMoneda() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("PEN"), "Debe contener moneda PEN");
        }
    }

    @Nested
    @DisplayName("📋 Líneas de Detalle - Tests de Contenido")
    class LineasDetalleTests {

        @Test
        @DisplayName("✓ HTML debe mapear todas las líneas de detalle")
        void htmlDebeMapearTodasLasLineas() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("Servicio de consultoría"), "Debe contener línea 1");
            assertTrue(html.contains("Licencia de software anual"), "Debe contener línea 2");
        }

        @Test
        @DisplayName("✓ HTML debe mapear cantidades de líneas")
        void htmlDebeMapearCantidadesLineas() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("\"quantity\":\"2\"") || html.contains(">2<"), "Debe contener cantidad 2");
            assertTrue(html.contains("\"quantity\":\"1\"") || html.contains(">1<"), "Debe contener cantidad 1");
        }

        @Test
        @DisplayName("✓ HTML debe mapear precios unitarios")
        void htmlDebeMapearPreciosUnitarios() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("500") || html.contains("500.00"), "Debe contener precio 500");
            assertTrue(html.contains("1200") || html.contains("1200.00"), "Debe contener precio 1200");
        }

        @Test
        @DisplayName("✓ HTML debe mapear unidades de medida")
        void htmlDebeMapearUnidadesMedida() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("ZZ"), "Debe contener unidad ZZ");
        }
    }

    @Nested
    @DisplayName("❌ Campos Nulos y Vacíos - Tests de Robustez")
    class CamposNulosVaciosTests {

        @Test
        @DisplayName("✓ Debe manejar factura sin datos del cliente")
        void debeManejazarSinCliente() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar factura sin cliente");
        }

        @Test
        @DisplayName("✓ Debe manejar factura sin datos del emisor")
        void debeManejazarSinEmisor() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar factura sin emisor");
        }

        @Test
        @DisplayName("✓ Debe manejar factura sin totales")
        void debeManejazarSinTotales() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");
            factura.setDetalles(List.of());

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar factura sin totales");
        }

        @Test
        @DisplayName("✓ Debe manejar factura sin líneas de detalle")
        void debeManejazarSinLineas() {
            BorradorFactura factura = crearFacturaMinima();
            factura.setDetalles(null);

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar factura sin líneas");
        }
    }

    @Nested
    @DisplayName("🎨 Formatos de Número - Tests de Precisión")
    class FormatosNumeroTests {

        @Test
        @DisplayName("✓ HTML debe formatear decimales sin ceros innecesarios")
        void debeFormatearDecimalesSinCerosInnecesarios() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Test");
            linea.setCantidad(new BigDecimal("1"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("100.10")); // 100.10, no 100.1000

            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("100.1"), "Debe contener precio sin ceros innecesarios");
            assertFalse(html.contains("100.10000"), "NO debe contener ceros excesivos");
        }

        @Test
        @DisplayName("✓ BigDecimal con ceros finales debe formatearse correctamente")
        void bigDecimalConCerosFinalesDebeFormatearseCorrectamente() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            factura.setTotalImporte(new TotalImporte(new BigDecimal("1000.00"), new BigDecimal("850.00"),
                    new BigDecimal("1000.00"), null, null));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            String html = resultado.contenidoHtml();

            assertTrue(html.contains("1000"), "Debe contener total");
            assertTrue(html.contains("850"), "Debe contener subtotal");
        }
    }

    @Nested
    @DisplayName("📝 Formatos de Impresión - Tests Estructurales")
    class FormatosImpresionTests {

        @Test
        @DisplayName("✓ Formato A4 debe funcionar")
        void formatoA4DebeFuncionar() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura(FormatoImpresion.A4);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500);
        }

        @Test
        @DisplayName("✓ Formato A5 debe funcionar")
        void formatoA5DebeFuncionar() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura(FormatoImpresion.A5);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500);
        }

        @Test
        @DisplayName("✓ Formato TICKET_80MM debe funcionar")
        void formatoTicket80mmDebeFuncionar() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura(FormatoImpresion.TICKET_80MM);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500);
        }

        @Test
        @DisplayName("✓ Formato TICKET_58MM debe funcionar")
        void formatoTicket58mmDebeFuncionar() {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura(FormatoImpresion.TICKET_58MM);

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500);
        }
    }

    @Nested
    @DisplayName("🔧 Datos Especiales - Tests de Edge Cases")
    class DatosEspecialesTests {

        @Test
        @DisplayName("✓ Debe manejar caracteres especiales en descripciones")
        void debeManejazarCaracteresEspeciales() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Servicio de consultoría & asesoramiento \"tech\" <moderno>");
            linea.setCantidad(new BigDecimal("1"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("500"));

            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar caracteres especiales");
        }

        @Test
        @DisplayName("✓ Debe manejar montos muy grandes")
        void debeManejazarMontosGrandes() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Activo");
            linea.setCantidad(new BigDecimal("1000"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("999999.99"));

            factura.setDetalles(List.of(linea));
            factura.setTotalImporte(new TotalImporte(new BigDecimal("999999999.99"), new BigDecimal("849576271.52"),
                    new BigDecimal("999999999.99"), null, null));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar montos muy grandes");
        }

        @Test
        @DisplayName("✓ Debe manejar montos pequeños")
        void debeManejazarMontosAls() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("Item");
            linea.setCantidad(new BigDecimal("0.01"));
            linea.setUnidadMedida("ZZ");
            linea.setPrecio(new BigDecimal("0.01"));

            factura.setDetalles(List.of(linea));

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            assertDoesNotThrow(() -> renderer.renderizar(contexto), "Debe manejar montos pequeños");
        }

        @Test
        @DisplayName("✓ Debe manejar muchas líneas de detalle")
        void debeManejazarMuchasLineas() {
            BorradorFactura factura = new BorradorFactura();
            factura.setSerie("F001");
            factura.setNumero(1);
            factura.setFechaEmision(LocalDate.now());
            factura.setTipoComprobante("01");

            List<LineaDetalle> lineas = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                LineaDetalle linea = new LineaDetalle();
                linea.setDescripcion("Producto " + i);
                linea.setCantidad(new BigDecimal("1"));
                linea.setUnidadMedida("ZZ");
                linea.setPrecio(new BigDecimal("10.00"));
                lineas.add(linea);
            }

            factura.setDetalles(lineas);

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isPdf(), "Debe manejar 50 líneas");
            assertTrue(resultado.contenidoPdf().length > 1000, "PDF debe ser más grande con 50 líneas");
        }
    }

    @Nested
    @DisplayName("💾 Persistencia - Tests de Guardado")
    class PersistenciaTests {

        @Test
        @DisplayName("✓ Debe guardar PDF a archivo")
        void debeGuardarPdfAArchivo() throws IOException {
            BorradorFactura factura = crearFacturaCompleta();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash", null);
            RenderizadorPdfFactura renderer = new RenderizadorPdfFactura();

            ResultadoRender resultado = renderer.renderizar(contexto);

            Path tempFile = Files.createTempFile("test-", ".pdf");
            Files.write(tempFile, resultado.contenidoPdf());

            assertTrue(Files.exists(tempFile), "Archivo debe existir");
            assertTrue(Files.size(tempFile) > 500, "Archivo debe tener contenido");

            // Cleanup
            Files.delete(tempFile);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // HELPERS - Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private BorradorFactura crearFacturaMinima() {
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
        factura.setHoraEmision(LocalTime.of(14, 22));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");

        Direccion direccionEmisor = new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
                "Av. Javier Prado 123", "PE");
        factura.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC", "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor, new Contacto("gerencia@miempresa.com", "015551234", null)));

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

        factura.setTotalImporte(new TotalImporte(new BigDecimal("2596.00"), new BigDecimal("2200.00"),
                new BigDecimal("2596.00"), null, null));

        factura.setTotalImpuestos(
                new TotalImpuestos(new BigDecimal("396.00"), new BigDecimal("396.00"), new BigDecimal("2200.00"), null,
                        null, null, null, null, null, null, null, null, null, null, null, null));

        factura.setLeyendas(Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));

        // Agregar orden de compra
        factura.setOrdenDeCompra("OC-2026-001");

        // Agregar observaciones
        factura.setObservaciones("Entrega en Lima Metropolitana");

        return factura;
    }
}
