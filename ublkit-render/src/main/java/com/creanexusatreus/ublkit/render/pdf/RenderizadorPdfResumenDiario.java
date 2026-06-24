package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;

import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;

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
        try {
            byte[] pdfBytes = PlaywrightBrowserManager.render(html, FormatoImpresion.A4);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando PDF de resumen diario con Playwright: " + e.getMessage(), e);
        }
    }

}
