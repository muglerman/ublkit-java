package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlComunicacionBaja;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.cna.ublkit.render.pdf.helper.FontResolver;

import java.io.ByteArrayOutputStream;

/**
 * Renderizador PDF para Comunicación de Baja.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfComunicacionBaja implements RenderizadorDocumento<ComunicacionBaja> {

    private final RenderizadorHtmlComunicacionBaja renderizadorHtml = new RenderizadorHtmlComunicacionBaja();

    @Override
    public ResultadoRender renderizar(ContextoRender<ComunicacionBaja> contexto) {
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
            throw new RuntimeException("Error renderizando PDF de comunicación de baja: " + e.getMessage(), e);
        }
    }
}

