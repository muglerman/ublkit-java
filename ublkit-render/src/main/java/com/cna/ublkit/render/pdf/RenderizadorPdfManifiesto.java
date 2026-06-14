package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlManifiesto;
import com.cna.ublkit.render.modelo.BorradorManifiesto;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pdf.helper.PlaywrightBrowserManager;
import com.microsoft.playwright.Page;

/**
 * Convierte un {@link BorradorManifiesto} en PDF (A4) usando HTML + Playwright.
 *
 * @since 0.4.0
 */
public class RenderizadorPdfManifiesto implements RenderizadorDocumento<BorradorManifiesto> {

    private static final FormatoImpresion FORMATO = FormatoImpresion.A4;
    private final RenderizadorHtmlManifiesto renderizadorHtml;

    public RenderizadorPdfManifiesto() {
        this.renderizadorHtml = new RenderizadorHtmlManifiesto();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorManifiesto> contexto) {
        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try (Page page = PlaywrightBrowserManager.getBrowser().newPage()) {
            byte[] pdfBytes = PlaywrightBrowserManager.renderizarPdf(page, html, FORMATO);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Manifiesto a PDF con Playwright: " + e.getMessage(), e);
        }
    }
}
