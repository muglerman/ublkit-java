package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.qr.GeneradorQrSunat;
import com.cna.ublkit.ubl.modelo.DocumentoBase;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.cna.ublkit.render.pdf.helper.FontResolver;

import java.io.ByteArrayOutputStream;

/**
 * Convierte Notas Electrónicas en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfNota implements RenderizadorDocumento<Object> {

    private final RenderizadorHtmlNota renderizadorHtml;

    public RenderizadorPdfNota() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfNota(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlNota(formato);
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<Object> contexto) {
        if (contexto.documento() instanceof DocumentoBase doc) {
            if (contexto.qrBase64() == null || contexto.qrBase64().isEmpty()) {
                GeneradorQrSunat generadorQr = new GeneradorQrSunat();
                String qrBase64 = generadorQr.generarQrBase64(doc, contexto.hashDocumento());
                contexto = ContextoRender.of(contexto.documento(), contexto.hashDocumento(), qrBase64, contexto.atributosPlantilla());
            }
        }

        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            FontResolver.configurePdfA(builder);
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Nota Electrónica a PDF: " + e.getMessage(), e);
        }
    }
}
