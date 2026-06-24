package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlManifiesto;
import com.creanexusatreus.ublkit.render.modelo.BorradorManifiesto;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;

/**
 * Convierte un {@link BorradorManifiesto} en PDF (A4 apaisado) usando HTML + Playwright. El
 * manifiesto consolida muchas columnas, por lo que se imprime en horizontal para que no se solapen.
 *
 * @since 0.4.0
 */
public class RenderizadorPdfManifiesto implements RenderizadorDocumento<BorradorManifiesto> {

    private static final FormatoImpresion FORMATO = FormatoImpresion.A4_LANDSCAPE;
    private final RenderizadorHtmlManifiesto renderizadorHtml;

    public RenderizadorPdfManifiesto() {
        this.renderizadorHtml = new RenderizadorHtmlManifiesto();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorManifiesto> contexto) {
        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try {
            byte[] pdfBytes = PlaywrightBrowserManager.render(html, FORMATO);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Manifiesto a PDF con Playwright: " + e.getMessage(), e);
        }
    }
}
