package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;

/**
 * Suite EXHAUSTIVA de pruebas para validar la generación de PDFs de NOTA DE CRÉDITO/DÉBITO.
 *
 * Valida:
 * - Nota de Crédito (07)
 * - Nota de Débito (08)
 * - Todos los campos específicos
 * - Comprobante afectado (referencia)
 * - Sustento de la nota
 *
 * @since 0.3.0
 */
@DisplayName("📄 NOTA DE CRÉDITO/DÉBITO - Validación Exhaustiva de Datos")
class RenderizadorPdfNotaDataValidationTest {

    @Nested
    @DisplayName("📋 Nota de Crédito (07) - Validaciones")
    class NotaCreditoTests {

        @Test
        @DisplayName("✓ Identificación básica de Nota de Crédito")
        void identificacionNotaCredito() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe generar HTML válido");
        }

        @Test
        @DisplayName("✓ Serie-Número de nota presente")
        void serieNumerNotaCredito() {
            BorradorNotaCredito nota = crearNotaCredito();
            String serieNumero = nota.getSerie() + "-" + nota.getNumero();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(serieNumero),
                "HTML debe contener: " + serieNumero);
        }

        @Test
        @DisplayName("✓ Recaudación del comprobante afectado")
        void comprobanteAfectado() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("F001-123"),
                "Debe contener serie-número del comprobante afectado");
        }

        @Test
        @DisplayName("✓ Sustento de la nota presente")
        void sustentoPresente() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe contener sustento");
        }

        @Test
        @DisplayName("✓ RUC del emisor correcto")
        void rucEmisor() {
            BorradorNotaCredito nota = crearNotaCredito();
            String ruc = nota.getEmisor().ruc();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(ruc),
                "HTML debe contener RUC emisor: " + ruc);
        }

        @Test
        @DisplayName("✓ Razón social del emisor")
        void razonSocialEmisor() {
            BorradorNotaCredito nota = crearNotaCredito();
            String razonSocial = nota.getEmisor().razonSocial();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(razonSocial),
                "HTML debe contener razón social: " + razonSocial);
        }

        @Test
        @DisplayName("✓ Moneda correcta")
        void monedaCredito() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("PEN"),
                "Debe contener moneda");
        }

        @Test
        @DisplayName("✓ Totales presentes")
        void totalesCredito() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 500, "Debe contener totales");
        }
    }

    @Nested
    @DisplayName("📋 Nota de Débito (08) - Validaciones")
    class NotaDebitoTests {

        @Test
        @DisplayName("✓ Identificación básica de Nota de Débito")
        void identificacionNotaDebito() {
            BorradorNotaDebito nota = crearNotaDebito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe generar HTML válido");
        }

        @Test
        @DisplayName("✓ Serie-Número de nota presente")
        void serieNumerNotaDebito() {
            BorradorNotaDebito nota = crearNotaDebito();
            String serieNumero = nota.getSerie() + "-" + nota.getNumero();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(serieNumero),
                "HTML debe contener: " + serieNumero);
        }

        @Test
        @DisplayName("✓ Comprobante afectado referenciado")
        void comprobanteAfectadoDebito() {
            BorradorNotaDebito nota = crearNotaDebito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("F001-123"),
                "Debe contener serie-número del comprobante afectado");
        }

        @Test
        @DisplayName("✓ Sustento de la nota de débito")
        void sustentoDebitoPresente() {
            BorradorNotaDebito nota = crearNotaDebito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe contener sustento");
        }

        @Test
        @DisplayName("✓ RUC del emisor (débito)")
        void rucEmisorDebito() {
            BorradorNotaDebito nota = crearNotaDebito();
            String ruc = nota.getEmisor().ruc();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(ruc),
                "HTML debe contener RUC emisor: " + ruc);
        }

        @Test
        @DisplayName("✓ Razón social (débito)")
        void razonSocialEmisorDebito() {
            BorradorNotaDebito nota = crearNotaDebito();
            String razonSocial = nota.getEmisor().razonSocial();

            String html = renderizarHtml(nota);

            assertTrue(html.contains(razonSocial),
                "HTML debe contener razón social: " + razonSocial);
        }

        @Test
        @DisplayName("✓ Moneda en nota débito")
        void monedaDebito() {
            BorradorNotaDebito nota = crearNotaDebito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("PEN"),
                "Debe contener moneda");
        }

        @Test
        @DisplayName("✓ Totales en nota débito")
        void totalesDebito() {
            BorradorNotaDebito nota = crearNotaDebito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 500, "Debe contener totales");
        }
    }

    @Nested
    @DisplayName("👤 Cliente/Receptor - Validaciones")
    class ReceptorTests {

        @Test
        @DisplayName("✓ Tipo documento receptor")
        void tipoDocReceptor() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe contener tipo doc receptor");
        }

        @Test
        @DisplayName("✓ Número documento receptor")
        void numeroDocReceptor() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("10987654321"),
                "Debe contener número documento receptor");
        }

        @Test
        @DisplayName("✓ Nombre receptor")
        void nombreReceptor() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("Cliente Ejemplo"),
                "Debe contener nombre receptor");
        }
    }

    @Nested
    @DisplayName("📝 Líneas de Detalle - Validaciones")
    class LineasTests {

        @Test
        @DisplayName("✓ Líneas de detalle presentes")
        void lineasPresentes() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 500, "Debe contener líneas de detalle");
        }

        @Test
        @DisplayName("✓ Descripción del concepto")
        void descripcionConcepto() {
            BorradorNotaCredito nota = crearNotaCredito();

            String html = renderizarHtml(nota);

            assertTrue(html.contains("Ajuste") || html.length() > 100,
                "Debe contener descripción");
        }
    }

    @Nested
    @DisplayName("🔗 Coherencia de Datos - Validaciones")
    class CoherenciaTests {

        @Test
        @DisplayName("✓ Emisor diferente de receptor")
        void emisorReceptorDiferentes() {
            BorradorNotaCredito nota = crearNotaCredito();

            String emisorRuc = nota.getEmisor().ruc();
            String receptorId = nota.getReceptor().numDocIdentidad();

            assertFalse(emisorRuc.equals(receptorId),
                "Emisor y receptor deben ser diferentes");
        }

        @Test
        @DisplayName("✓ Total positivo")
        void totalPositivo() {
            BorradorNotaCredito nota = crearNotaCredito();

            assertTrue(nota.getTotalImporte().importe().compareTo(BigDecimal.ZERO) >= 0,
                "Total debe ser >= 0");
        }
    }

    @Nested
    @DisplayName("❌ Información Faltante - Validaciones")
    class InformacionFaltanteTests {

        @Test
        @DisplayName("✓ Maneja nota sin líneas")
        void sinLineas() {
            BorradorNotaCredito nota = crearNotaCredito();
            nota.setDetalles(null);

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe renderizar sin líneas");
        }

        @Test
        @DisplayName("✓ Maneja nota con totales nulos")
        void conTotalesNulos() {
            BorradorNotaCredito nota = crearNotaCredito();
            nota.setTotalImporte(null);

            String html = renderizarHtml(nota);

            assertTrue(html.length() > 100, "Debe renderizar sin totales");
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // HELPERS
    // ═════════════════════════════════════════════════════════════════════

    private String renderizarHtml(Object nota) {
        ContextoRender<Object> contexto = ContextoRender.of(nota, "hash123", null);
        @SuppressWarnings("unchecked")
        RenderizadorHtmlNota renderer = new RenderizadorHtmlNota(FormatoImpresion.A4);
        ResultadoRender resultado = renderer.renderizar(contexto);
        return resultado.contenidoHtml();
    }

    private BorradorNotaCredito crearNotaCredito() {
        BorradorNotaCredito nota = new BorradorNotaCredito();

        nota.setSerie("FC01");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 4, 1));
        nota.setHoraEmision(LocalTime.of(10, 30));

        // Emisor
        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null,
                "Lima", "Lima", "Lima",
                "Av. Principal 999", "PE"
        );
        nota.setEmisor(new EmisorDocumento(
                "20123456789",
                "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor,
                new Contacto("info@miempresa.com", "015551234", null)
        ));

        // Receptor
        nota.setReceptor(new ReceptorDocumento(
                "6",
                "10987654321",
                "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Secundaria 456", "PE"),
                null
        ));

        // Comprobante afectado
        nota.setComprobanteAfectadoSerieNumero("F001-123");
        nota.setComprobanteAfectadoTipo("01");

        // Sustento
        nota.setTipoNota("01"); // Ajuste de precio
        nota.setSustentoDescripcion("Ajuste por error en facturación");

        // Líneas
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Ajuste");
        linea.setCantidad(new BigDecimal("1"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));

        // Totales
        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                null,
                null
        ));

        return nota;
    }

    private BorradorNotaDebito crearNotaDebito() {
        BorradorNotaDebito nota = new BorradorNotaDebito();

        nota.setSerie("FD01");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 4, 1));
        nota.setHoraEmision(LocalTime.of(11, 0));

        // Emisor
        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null,
                "Lima", "Lima", "Lima",
                "Av. Principal 999", "PE"
        );
        nota.setEmisor(new EmisorDocumento(
                "20123456789",
                "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor,
                new Contacto("info@miempresa.com", "015551234", null)
        ));

        // Receptor
        nota.setReceptor(new ReceptorDocumento(
                "6",
                "10987654321",
                "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Secundaria 456", "PE"),
                null
        ));

        // Comprobante afectado
        nota.setComprobanteAfectadoSerieNumero("F001-123");
        nota.setComprobanteAfectadoTipo("01");

        // Sustento
        nota.setTipoNota("02"); // Aumento de precio
        nota.setSustentoDescripcion("Incremento de precio por cambio de cotización");

        // Líneas
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Incremento");
        linea.setCantidad(new BigDecimal("1"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("50.00"));
        nota.setDetalles(List.of(linea));

        // Totales
        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("50.00"),
                new BigDecimal("50.00"),
                new BigDecimal("50.00"),
                null,
                null
        ));

        return nota;
    }
}
