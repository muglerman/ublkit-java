package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlRetencionPercepcion;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.percepcionretencion.BasePercepcionRetencion;

public class RenderizadorPdfRetencionPercepcion<T extends BasePercepcionRetencion>
        implements RenderizadorDocumento<T> {
    private final RenderizadorHtmlRetencionPercepcion<T> html = new RenderizadorHtmlRetencionPercepcion<>();

    @Override
    public ResultadoRender renderizar(ContextoRender<T> contexto) {
        try {
            return ResultadoRender.pdf(PlaywrightBrowserManager.render(
                    html.renderizar(contexto).contenidoHtml(), FormatoImpresion.A4));
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando retención/percepción PDF: " + e.getMessage(), e);
        }
    }
}
