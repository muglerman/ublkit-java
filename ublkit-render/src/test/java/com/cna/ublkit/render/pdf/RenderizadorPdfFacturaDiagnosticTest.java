package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
 * Test de DIAGNÓSTICO: Genera HTML de factura y lo guarda para inspección manual. Útil para ver qué datos se están
 * renderizando y en qué formato.
 */
@DisplayName("📄 FACTURA - Diagnóstico HTML")
class RenderizadorPdfFacturaDiagnosticTest {

        @Test
        @DisplayName("📝 Generar y guardar HTML de factura para inspección")
        void generarHtmlYGuardarParaInspeccion() throws Exception {
                BorradorFactura factura = crearFacturaCompleta();

                ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash123", null);
                RenderizadorHtmlFactura renderer = new RenderizadorHtmlFactura(FormatoImpresion.A4);
                ResultadoRender resultado = renderer.renderizar(contexto);
                String html = resultado.contenidoHtml();

                // Guardar HTML a archivo temporal para inspección
                Path outputFile = Files.createTempFile("factura-", ".html");
                Files.write(outputFile, html.getBytes());

                System.out.println("\n");
                System.out.println("=".repeat(80));
                System.out.println("📄 HTML DE FACTURA GENERADO");
                System.out.println("=".repeat(80));
                System.out.println("\nGuardado en: " + outputFile.toAbsolutePath());
                System.out.println("\n--- PRIMEROS 3000 CARACTERES ---\n");
                System.out.println(html.substring(0, Math.min(3000, html.length())));
                System.out.println("\n--- VERIFICACIONES DE CONTENIDO ---\n");

                // Verificaciones básicas
                verifyContent("RUC Emisor (20123456789)", html, "20123456789");
                verifyContent("Nombre Emisor (Mi Empresa SAC)", html, "Mi Empresa SAC");
                verifyContent("RUC Cliente (10987654321)", html, "10987654321");
                verifyContent("Nombre Cliente (Cliente Ejemplo EIRL)", html, "Cliente Ejemplo EIRL");
                verifyContent("Serie-Número (F001-123)", html, "F001-123");
                verifyContent("Fecha Emisión (30/03/2026)", html, "30/03/2026");
                verifyContent("Moneda (PEN)", html, "PEN");
                verifyContent("Subtotal (2200)", html, "2200");
                verifyContent("IGV (396)", html, "396");
                verifyContent("Total (2596)", html, "2596");
                verifyContent("Línea 1 (Servicio de consultoría)", html, "Servicio de consultoría");
                verifyContent("Línea 2 (Licencia de software anual)", html, "Licencia de software anual");
                verifyContent("Cantidad línea 1 (2)", html, "2");
                verifyContent("Precio línea 1 (500)", html, "500");
                verifyContent("Leyenda (SON:)", html, "SON:");

                System.out.println("\n" + "=".repeat(80));
                System.out.println("✅ Inspección completada");
                System.out.println("=".repeat(80) + "\n");

                assertTrue(html.length() > 100, "HTML debe contener contenido");
        }

        private void verifyContent(String label, String html, String content) {
                boolean found = html.contains(content);
                String status = found ? "✅" : "❌";
                String record = String.format("%-50s : %s [%s]", label, status, content);
                System.out.println(record);
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

                factura.setTotalImporte(new TotalImporte(new BigDecimal("2596.00"), new BigDecimal("2200.00"),
                                new BigDecimal("2596.00"), null, null));

                factura.setTotalImpuestos(new TotalImpuestos(new BigDecimal("396.00"), new BigDecimal("396.00"),
                                new BigDecimal("2200.00"), null, null, null, null, null, null, null, null, null, null,
                                null, null, null));

                factura.setLeyendas(Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));
                factura.setOrdenDeCompra("OC-2026-001");
                factura.setObservaciones("Entrega en Lima Metropolitana");

                return factura;
        }
}
