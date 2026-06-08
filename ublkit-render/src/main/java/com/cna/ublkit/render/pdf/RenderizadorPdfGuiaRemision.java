package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.microsoft.playwright.Page;
import com.cna.ublkit.render.pdf.helper.PlaywrightBrowserManager;

/**
 * Convierte una {@link BorradorGuiaRemision} en PDF usando HTML + OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
public class RenderizadorPdfGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {

    private final RenderizadorHtmlGuiaRemision renderizadorHtml;
    private final FormatoImpresion formato;

    public RenderizadorPdfGuiaRemision() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfGuiaRemision(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlGuiaRemision(formato);
        this.formato = formato;
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try (Page page = PlaywrightBrowserManager.getBrowser().newPage()) {
            byte[] pdfBytes = PlaywrightBrowserManager.renderizarPdf(page, html, this.formato);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Guía de Remisión a PDF con Playwright: " + e.getMessage(), e);
        }
    }

}
