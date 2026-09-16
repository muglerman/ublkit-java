package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlFactura;
import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlFactura;

/** Genera archivos inspeccionables de cada caso tributario y del caso mixto completo. */
class RenderizadorImpuestosMixtosDiagnosticTest {

    private static final Path OUTPUT = Paths.get("target", "diagnostico", "impuestos-mixtos");

    @Test
    void generaArchivosTemporalesDeCasosIndividualesYCombinado() throws IOException {
        List<NamedCase> casos = List.of(
                new NamedCase("01-gravado", List.of(linea("GRAVADO", "300", "10", "300", "54"))),
                new NamedCase("02-exonerado", List.of(linea("EXONERADO", "290", "20", "290", "0"))),
                new NamedCase("03-inafecto", List.of(linea("INAFECTO", "100", "30", "100", "0"))),
                new NamedCase("04-gratuito", List.of(linea("GRATUITO", "50", "11", "50", "0"))),
                new NamedCase("05-mixto-gravado-exonerado-inafecto-gratuito", List.of(
                        linea("GRAVADO", "300", "10", "300", "54"),
                        linea("EXONERADO", "290", "20", "290", "0"),
                        linea("INAFECTO", "100", "30", "100", "0"),
                        linea("GRATUITO", "50", "11", "50", "0"))));

        for (NamedCase caso : casos) {
            BorradorFactura factura = EnsambladorFactura.ensamblar(factura(caso.lineas()));
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "diagnostico-mixto", null,
                    EstiloPlantilla.DEFAULT);
            Path dir = OUTPUT.resolve(caso.nombre());
            Files.createDirectories(dir);

            String xml = new SerializadorXmlFactura().serializar(factura);
            Files.writeString(dir.resolve("documento.xml"), xml);
            assertTrue(xml.contains("TaxSubtotal"));

            writeHtmlAndPdf(dir, "a4", new RenderizadorHtmlFactura(FormatoImpresion.A4),
                    new RenderizadorPdfFactura(FormatoImpresion.A4), contexto);
            writeHtmlAndPdf(dir, "ticket80", new RenderizadorHtmlFactura(FormatoImpresion.TICKET_80MM),
                    new RenderizadorTicketFactura(FormatoImpresion.TICKET_80MM), contexto);
        }
    }

    private static void writeHtmlAndPdf(Path dir, String suffix, RenderizadorDocumento<BorradorFactura> htmlRenderer,
            RenderizadorDocumento<BorradorFactura> pdfRenderer, ContextoRender<BorradorFactura> contexto) throws IOException {
        ResultadoRender html = htmlRenderer.renderizar(contexto);
        ResultadoRender pdf = pdfRenderer.renderizar(contexto);
        Files.writeString(dir.resolve("documento-" + suffix + ".html"), html.contenidoHtml());
        Files.write(dir.resolve("documento-" + suffix + ".pdf"), pdf.contenidoPdf());
        assertTrue(html.contenidoHtml().length() > 100);
        assertEquals("%PDF-", new String(pdf.contenidoPdf(), 0, 5));
    }

    private static LineaDetalle linea(String descripcion, String base, String afectacion, String baseIgv, String igv) {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion(descripcion);
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal(base));
        linea.setIgvTipo(afectacion);
        linea.setIgvBaseImponible(new BigDecimal(baseIgv));
        linea.setIgv(new BigDecimal(igv));
        linea.setTotalImpuestos(new BigDecimal(igv));
        if ("10".equals(afectacion)) {
            linea.setTasaIgv(new BigDecimal("0.18"));
        }
        return linea;
    }

    private static BorradorFactura factura(List<LineaDetalle> lineas) {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("B001");
        factura.setNumero(2);
        factura.setFechaEmision(LocalDate.of(2026, 1, 1));
        factura.setMoneda("PEN");
        factura.setTipoComprobante("03");
        factura.setTipoOperacion("0101");
        factura.setEmisor(new EmisorDocumento("20123456789", "ACME", "ACME SAC", null, null));
        factura.setReceptor(new ReceptorDocumento("6", "20100000002", "CLIENTE", null, null));
        factura.setDetalles(lineas);
        return factura;
    }

    private record NamedCase(String nombre, List<LineaDetalle> lineas) { }
}
