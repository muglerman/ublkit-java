package com.cna.ublkit.render.pdf.helper;

import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

public class PlaywrightBrowserManager {

    /** Alto útil de una página A5 apaisada a 96 dpi: 148 mm = 559 px (márgenes en 0). */
    private static final double ALTO_A5_LANDSCAPE_PX = 559.0;
    /** Escala mínima razonable: por debajo el documento sería ilegible (se deja paginar). */
    private static final double ESCALA_MINIMA = 0.45;

    private static Playwright playwright;
    private static Browser browser;

    public static synchronized Browser getBrowser() {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (browser != null) {
                    try { browser.close(); } catch (Exception ignored) {}
                }
                if (playwright != null) {
                    try { playwright.close(); } catch (Exception ignored) {}
                }
            }));
        }
        return browser;
    }

    /**
     * Carga el HTML en la página y genera el PDF aplicando la política de tamaño por formato.
     *
     * <p>Para A5 (apaisado, "una A4 partida a la mitad y girada", 210×148 mm) ajusta el documento a
     * <b>una sola página</b>: mide el alto real del contenido y, si excede los 148 mm de la hoja,
     * reduce la escala de impresión (fit-to-page) para que entre completo sin partirse. Los
     * documentos cortos (que ya caben) se imprimen a escala 1. Para A4 se respeta la paginación
     * natural (un comprobante largo puede ocupar varias páginas).</p>
     */
    public static byte[] renderizarPdf(Page page, String html, FormatoImpresion formato) {
        page.setContent(html, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
        Page.PdfOptions options = getPdfOptions(formato);
        if (formato == FormatoImpresion.A5) {
            options.setScale(escalaAjusteA5(page));
        }
        return page.pdf(options);
    }

    /** Escala de impresión para que el contenido A5 entre en una sola página apaisada. */
    private static double escalaAjusteA5(Page page) {
        Object alto = page.evaluate("() => Math.ceil(document.body.scrollHeight)");
        double altoContenido = ((Number) alto).doubleValue();
        if (altoContenido <= ALTO_A5_LANDSCAPE_PX) {
            return 1.0;
        }
        // 0.98 de margen de seguridad para evitar un desbordamiento por redondeo a una 2ª página.
        double escala = (ALTO_A5_LANDSCAPE_PX / altoContenido) * 0.98;
        return Math.max(ESCALA_MINIMA, Math.min(1.0, escala));
    }

    public static Page.PdfOptions getPdfOptions(FormatoImpresion formato) {
        Page.PdfOptions options = new Page.PdfOptions()
            .setPrintBackground(true)
            .setMargin(new com.microsoft.playwright.options.Margin()
                .setTop("0")
                .setRight("0")
                .setBottom("0")
                .setLeft("0"));

        switch (formato) {
            case A5:
                // A5 apaisado: una A4 partida a la mitad y girada (210 x 148 mm).
                options.setFormat("A5");
                options.setLandscape(true);
                break;
            case TICKET_80MM:
                options.setWidth("80mm");
                options.setPreferCSSPageSize(true);
                break;
            case TICKET_58MM:
                options.setWidth("58mm");
                options.setPreferCSSPageSize(true);
                break;
            case A4:
            default:
                options.setFormat("A4");
                break;
        }

        return options;
    }
}
