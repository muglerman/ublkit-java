package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlNota;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.qr.GeneradorQrSunat;
import com.creanexusatreus.ublkit.ubl.modelo.DocumentoBase;

import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;

/**
 * Convierte Notas Electrónicas en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfNota implements RenderizadorDocumento<Object> {

    private final RenderizadorHtmlNota renderizadorHtml;
    private final FormatoImpresion formato;

    public RenderizadorPdfNota() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfNota(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlNota(formato);
        this.formato = formato;
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<Object> contexto) {
        if (contexto.documento() instanceof DocumentoBase doc) {
            if (contexto.qrBase64() == null || contexto.qrBase64().isEmpty()) {
                GeneradorQrSunat generadorQr = new GeneradorQrSunat();
                String qrBase64 = generadorQr.generarQrBase64(doc, contexto.hashDocumento());
                contexto = ContextoRender.of(
                        contexto.documento(),
                        contexto.hashDocumento(),
                        qrBase64,
                        contexto.atributosPlantilla(),
                        contexto.estiloPlantilla()
                );
            }
        }

        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try {
            byte[] pdfBytes = PlaywrightBrowserManager.render(html, this.formato);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Nota Electrónica a PDF con Playwright: " + e.getMessage(), e);
        }
    }

}
