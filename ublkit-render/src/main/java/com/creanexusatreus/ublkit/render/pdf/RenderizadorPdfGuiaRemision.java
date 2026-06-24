package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;

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

        try {
            byte[] pdfBytes = PlaywrightBrowserManager.render(html, this.formato);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Guía de Remisión a PDF con Playwright: " + e.getMessage(), e);
        }
    }

}
