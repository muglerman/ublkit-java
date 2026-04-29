package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.qr.GeneradorQrSunat;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.cna.ublkit.render.pdf.helper.FontResolver;

import java.io.ByteArrayOutputStream;

/**
 * Convierte un {@link BorradorFactura} en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfFactura implements RenderizadorDocumento<BorradorFactura> {

    private final RenderizadorHtmlFactura renderizadorHtml;

    public RenderizadorPdfFactura() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfFactura(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlFactura(formato);
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        if (contexto.qrBase64() == null || contexto.qrBase64().isEmpty()) {
            GeneradorQrSunat generadorQr = new GeneradorQrSunat();
            String qrBase64 = generadorQr.generarQrBase64(contexto.documento(), contexto.hashDocumento());
            contexto = ContextoRender.of(
                    contexto.documento(),
                    contexto.hashDocumento(),
                    qrBase64,
                    contexto.atributosPlantilla(),
                    contexto.estiloPlantilla()
            );
        }

        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = HtmlXhtmlSanitizer.sanear(resultadoHtml.contenidoHtml());

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            FontResolver.configurePdfA(builder);
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo HTML a PDF: " + e.getMessage(), e);
        }
    }

}
