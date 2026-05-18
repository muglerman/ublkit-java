package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.qr.GeneradorQrSunat;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import com.cna.ublkit.render.pdf.helper.PlaywrightBrowserManager;

/**
 * Convierte un {@link BorradorFactura} en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfFactura implements RenderizadorDocumento<BorradorFactura> {

    private final RenderizadorHtmlFactura renderizadorHtml;
    private final FormatoImpresion formato;

    public RenderizadorPdfFactura() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfFactura(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlFactura(formato);
        this.formato = formato;
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
        String html = resultadoHtml.contenidoHtml();

        try (Page page = PlaywrightBrowserManager.getBrowser().newPage()) {
            page.setContent(html, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
            byte[] pdfBytes = page.pdf(PlaywrightBrowserManager.getPdfOptions(this.formato));
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo HTML a PDF con Playwright: " + e.getMessage(), e);
        }
    }

}
