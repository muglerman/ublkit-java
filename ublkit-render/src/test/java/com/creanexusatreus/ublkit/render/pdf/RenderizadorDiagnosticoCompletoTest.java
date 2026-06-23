package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlComunicacionBaja;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.Conductor;
import com.cna.ublkit.ubl.modelo.guia.DatosEnvio;
import com.cna.ublkit.ubl.modelo.guia.DestinatarioGuia;
import com.cna.ublkit.ubl.modelo.guia.LineaGuia;
import com.cna.ublkit.ubl.modelo.guia.TerceroGuia;
import com.cna.ublkit.ubl.modelo.guia.TransportistaGuia;
import com.cna.ublkit.ubl.modelo.guia.Vehiculo;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ComprobanteResumen;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

/**
 * Test de DIAGNÓSTICO INTEGRAL: renderiza a HTML y PDF <b>todas</b> las variantes de plantilla
 * vivas (cada estilo × tipo de documento × formato) y las guarda en {@code target/diagnostico/}
 * para inspección visual.
 *
 * <p>Cubre A4/A5 de invoice/note/debit/despatch + summary/voided (A4) + boleta/despatch-carrier
 * (solo A4) para los 5 estilos → 60 plantillas con estilo; y los tickets térmicos genéricos
 * (invoice/note/debit/despatch en ticket80mm/ticket58mm, sin estilo) → 8 plantillas en
 * {@code templates/generico/}. Total 68 plantillas {@code .html.twig}. Sirve además como red de
 * seguridad de que toda plantilla compila/renderiza con las fuentes embebidas (sin CDN).</p>
 */
@DisplayName("🧪 DIAGNÓSTICO INTEGRAL - HTML + PDF de todas las plantillas")
class RenderizadorDiagnosticoCompletoTest {

    private static final Path BASE = Paths.get("target", "diagnostico");
    /** Formatos con variante por estilo (papel). */
    private static final FormatoImpresion[] FORMATOS_DISENO = {
            FormatoImpresion.A4, FormatoImpresion.A5
    };
    /** Formatos de ticket térmico: plantilla genérica única (sin estilo). */
    private static final FormatoImpresion[] FORMATOS_TICKET = {
            FormatoImpresion.TICKET_80MM, FormatoImpresion.TICKET_58MM
    };

    private int htmlGenerados = 0;
    private int pdfGenerados = 0;

    @Test
    @DisplayName("📦 Generar HTML + PDF de cada plantilla viva (60 con estilo + 8 tickets genéricos)")
    void generarTodasLasPlantillas() throws IOException {
        // Plantillas con estilo: A4/A5 por estilo + documentos solo-A4 (summary, voided)
        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            for (FormatoImpresion formato : FORMATOS_DISENO) {
                render("invoice", estilo, estilo.carpeta(), formato,
                        new RenderizadorHtmlFactura(formato), new RenderizadorPdfFactura(formato), crearFactura());
                render("note", estilo, estilo.carpeta(), formato,
                        new RenderizadorHtmlNota(formato), new RenderizadorPdfNota(formato), (Object) crearNotaCredito());
                render("debit", estilo, estilo.carpeta(), formato,
                        new RenderizadorHtmlNota(formato), new RenderizadorPdfNota(formato), (Object) crearNotaDebito());
                render("despatch", estilo, estilo.carpeta(), formato,
                        new RenderizadorHtmlGuiaRemision(formato), new RenderizadorPdfGuiaRemision(formato), crearGuia());
            }
            // Boleta (tipo 03) y GRE transportista (tipo 31) tienen plantilla propia solo en A4;
            // el routing del renderizador resuelve boleta.a4 / despatch-carrier.a4 por tipo.
            render("boleta", estilo, estilo.carpeta(), FormatoImpresion.A4,
                    new RenderizadorHtmlFactura(FormatoImpresion.A4), new RenderizadorPdfFactura(FormatoImpresion.A4), crearBoleta());
            render("despatch-carrier", estilo, estilo.carpeta(), FormatoImpresion.A4,
                    new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4), new RenderizadorPdfGuiaRemision(FormatoImpresion.A4), crearGuia31());
            render("summary", estilo, estilo.carpeta(), FormatoImpresion.A4,
                    new RenderizadorHtmlResumenDiario(), new RenderizadorPdfResumenDiario(), crearResumen());
            render("voided", estilo, estilo.carpeta(), FormatoImpresion.A4,
                    new RenderizadorHtmlComunicacionBaja(), new RenderizadorPdfComunicacionBaja(), crearComunicacion());
        }

        // Tickets genéricos: una sola plantilla por tipo/ancho (sin estilo). El estilo es indiferente
        // (la ruta cae en templates/generico/), pero pasamos DEFAULT por consistencia del contexto.
        for (FormatoImpresion formato : FORMATOS_TICKET) {
            render("invoice", EstiloPlantilla.DEFAULT, "generico", formato,
                    new RenderizadorHtmlFactura(formato), new RenderizadorPdfFactura(formato), crearFactura());
            render("note", EstiloPlantilla.DEFAULT, "generico", formato,
                    new RenderizadorHtmlNota(formato), new RenderizadorPdfNota(formato), (Object) crearNotaCredito());
            render("debit", EstiloPlantilla.DEFAULT, "generico", formato,
                    new RenderizadorHtmlNota(formato), new RenderizadorPdfNota(formato), (Object) crearNotaDebito());
            render("despatch", EstiloPlantilla.DEFAULT, "generico", formato,
                    new RenderizadorHtmlGuiaRemision(formato), new RenderizadorPdfGuiaRemision(formato), crearGuia());
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ DIAGNÓSTICO INTEGRAL COMPLETO");
        System.out.println("   HTML generados: " + htmlGenerados + " | PDF generados: " + pdfGenerados);
        System.out.println("   Salida: " + BASE.toAbsolutePath());
        System.out.println("=".repeat(80) + "\n");

        // 5 estilos × (2 formatos × 4 tipos + 4 tipos A4) = 60  +  8 tickets genéricos (2 × 4 tipos) = 68
        assertEquals(68, htmlGenerados, "Deben generarse 68 HTML (todas las plantillas vivas)");
        assertEquals(68, pdfGenerados, "Deben generarse 68 PDF (todas las plantillas vivas)");
    }

    private <T> void render(String tipo, EstiloPlantilla estilo, String carpetaSalida, FormatoImpresion formato,
                            RenderizadorDocumento<T> html, RenderizadorDocumento<T> pdf, T documento) throws IOException {
        ContextoRender<T> contexto = ContextoRender.of(documento, "hash123", null, estilo);

        String contenidoHtml = html.renderizar(contexto).contenidoHtml();
        byte[] contenidoPdf = pdf.renderizar(contexto).contenidoPdf();

        Path dir = BASE.resolve(carpetaSalida);
        Files.createDirectories(dir);
        String base = tipo + "." + sufijo(formato);
        Files.writeString(dir.resolve(base + ".html"), contenidoHtml);
        Files.write(dir.resolve(base + ".pdf"), contenidoPdf);

        assertTrue(contenidoHtml.length() > 100,
                "HTML vacío: " + carpetaSalida + "/" + base);
        assertTrue(contenidoPdf.length > 500,
                "PDF demasiado pequeño: " + carpetaSalida + "/" + base);
        assertEquals("%PDF-", new String(contenidoPdf, 0, 5),
                "Cabecera PDF inválida: " + carpetaSalida + "/" + base);

        htmlGenerados++;
        pdfGenerados++;
    }

    private static String sufijo(FormatoImpresion formato) {
        return switch (formato) {
            case A5 -> "a5";
            case TICKET_80MM -> "ticket80mm";
            case TICKET_58MM -> "ticket58mm";
            default -> "a4";
        };
    }

    // ═════════════════════════════════════════════════════════════════════
    // Factories de datos de ejemplo (reutilizan los de los tests por tipo)
    // ═════════════════════════════════════════════════════════════════════

    private BorradorFactura crearFactura() {
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
        factura.setLeyendas(java.util.Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));
        return factura;
    }

    private BorradorNotaCredito crearNotaCredito() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("FC01");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 4, 1));
        nota.setHoraEmision(LocalTime.of(10, 30));

        Direccion direccionEmisor = new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
                "Av. Principal 999", "PE");
        nota.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC", "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor, new Contacto("info@miempresa.com", "015551234", null)));
        nota.setReceptor(new ReceptorDocumento("6", "10987654321", "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Secundaria 456", "PE"), null));

        nota.setComprobanteAfectadoSerieNumero("F001-123");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Ajuste por error en facturación");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Ajuste");
        linea.setCantidad(new BigDecimal("1"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));
        nota.setTotalImporte(new TotalImporte(new BigDecimal("100.00"), new BigDecimal("100.00"),
                new BigDecimal("100.00"), null, null));
        return nota;
    }

    private BorradorNotaDebito crearNotaDebito() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("ND01");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 4, 1));
        nota.setHoraEmision(LocalTime.of(10, 30));
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

    private BorradorFactura crearBoleta() {
        BorradorFactura boleta = crearFactura();
        boleta.setSerie("B001");
        boleta.setNumero(4912);
        boleta.setTipoComprobante("03");
        boleta.setReceptor(new ReceptorDocumento("1", "45128734", "María Elena Castillo Rodríguez",
                new Direccion(null, null, null, null, null, null, "Calle Las Begonias 178", "PE"), null));
        return boleta;
    }

    private BorradorGuiaRemision crearGuia() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(4912);
        guia.setFechaEmision(LocalDate.of(2026, 3, 31));
        guia.setHoraEmision(LocalTime.of(7, 22));
        guia.setTipoComprobante("09");

        Direccion direccionRemitente = new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
                "Calle Principal 100", "PE");
        guia.setRemitente(new EmisorDocumento("20606860618", "Repartidor Express", "Repartidor Express S.A.C.",
                direccionRemitente, new Contacto("contacto@repartidor.com", "01 555-1000", null)));
        guia.setDestinatario(new DestinatarioGuia("6", "10123456789", "Juan Pérez"));

        DatosEnvio datosEnvio = new DatosEnvio();
        datosEnvio.setTipoTraslado("01");
        datosEnvio.setMotivoTraslado("Venta");
        datosEnvio.setPesoTotal(new BigDecimal("250"));
        datosEnvio.setPesoTotalUnidadMedida("KG");
        datosEnvio.setNumeroDeBultos(5);
        datosEnvio.setFechaTraslado(LocalDate.of(2026, 3, 31));
        datosEnvio.setTransportista(new TransportistaGuia("6", "20111111111", "Transportes XYZ", "MTC-123456"));
        datosEnvio.setVehiculo(new Vehiculo("ABC-123", "TUC-001", "HAB-2024-001", "MTC", "VOLVO", "FH16", null));
        datosEnvio.setChoferes(List.of(new Conductor("Principal", "1", "12345678", "Carlos", "López", "DL123456")));
        guia.setEnvio(datosEnvio);

        guia.setDetalles(List.of(new LineaGuia("KGM", new BigDecimal("2"), "Producto A", "PROD-001", "85101000", null)));
        guia.setObservaciones("Entrega asegurada");
        return guia;
    }

    private BorradorGuiaRemision crearGuia31() {
        BorradorGuiaRemision guia = crearGuia();
        guia.setSerie("V001");
        guia.setNumero(128);
        guia.setTipoComprobante("31");
        // En GRE-31 el emisor es la empresa de transporte; el remitente real viaja como tercero.
        guia.setRemitente(new EmisorDocumento("20600456789", "Transportes Mantaro",
                "Transportes Mantaro E.I.R.L.", null, null));
        guia.setTercero(new TerceroGuia("6", "20512345678", "Manufacturas Andina Textil S.A.C."));
        guia.setSubcontratado(new TerceroGuia("6", "20222222222", "Subcontratista Andino S.A.C."));
        return guia;
    }

    private ResumenDiario crearResumen() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 3, 30));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 3, 30));
        resumen.setMoneda("PEN");
        resumen.setNumero(1);
        resumen.setEmisor(new EmisorDocumento("20123456789", "Empresa Test", "Empresa Comercial", null, null));
        resumen.setFirmante(new FirmanteDocumento("20123456789", "Empresa Test"));

        ComprobanteResumen comprobante = new ComprobanteResumen();
        comprobante.setTipoComprobante("01");
        comprobante.setSerieNumero("F001-100");
        comprobante.setMoneda("PEN");
        comprobante.setCliente(new ReceptorDocumento("1", "10123456789", "Cliente Test", null, null));
        resumen.setComprobantes(List.of(new ItemResumenDiario("01", comprobante)));
        return resumen;
    }

    private ComunicacionBaja crearComunicacion() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 3, 30));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 3, 29));
        baja.setMoneda("PEN");
        baja.setNumero(1);
        baja.setEmisor(new EmisorDocumento("20123456789", "Empresa Test", "Empresa Comercial", null, null));
        baja.setFirmante(new FirmanteDocumento("20123456789", "Empresa Test"));
        baja.setComprobantes(List.of(new ItemBaja("F001", 100, "01", "Cancelación")));
        return baja;
    }
}
