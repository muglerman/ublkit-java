package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;

import java.io.ByteArrayOutputStream;

/**
 * Convierte una {@link BorradorGuiaRemision} en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {

    private final RenderizadorHtmlGuiaRemision renderizadorHtml;

    public RenderizadorPdfGuiaRemision() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfGuiaRemision(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlGuiaRemision(formato);
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.useSVGDrawer(new BatikSVGDrawer());
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Guía de Remisión a PDF: " + e.getMessage(), e);
        }
    }
}
