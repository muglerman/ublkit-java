package com.creanexusatreus.ublkit.render.pdf;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlFactura;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.qr.GeneradorQrSunat;

import com.microsoft.playwright.Page;
import com.creanexusatreus.ublkit.render.pdf.helper.PlaywrightBrowserManager;

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
            byte[] pdfBytes = PlaywrightBrowserManager.renderizarPdf(page, html, this.formato);
            return ResultadoRender.pdf(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo HTML a PDF con Playwright: " + e.getMessage(), e);
        }
    }

}
