package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import com.cna.ublkit.render.pdf.helper.PlaywrightBrowserManager;

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
        try (Page page = PlaywrightBrowserManager.getBrowser().newPage()) {
            page.setContent(html, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
            byte[] pdfBytes = page.pdf(PlaywrightBrowserManager.getPdfOptions(FormatoImpresion.A4));
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando PDF de resumen diario con Playwright: " + e.getMessage(), e);
        }
    }

}
