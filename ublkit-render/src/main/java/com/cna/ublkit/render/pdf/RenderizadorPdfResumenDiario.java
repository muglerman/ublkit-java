package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.cna.ublkit.render.pdf.helper.FontResolver;

import java.io.ByteArrayOutputStream;

/**
 * Renderizador PDF para Resumen Diario.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfResumenDiario implements RenderizadorDocumento<ResumenDiario> {

    private final RenderizadorHtmlResumenDiario renderizadorHtml = new RenderizadorHtmlResumenDiario();

    @Override
    public ResultadoRender renderizar(ContextoRender<ResumenDiario> contexto) {
        String html = renderizadorHtml.renderizar(contexto).contenidoHtml();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            FontResolver.configurePdfA(builder);
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando PDF de resumen diario: " + e.getMessage(), e);
        }
    }
}

