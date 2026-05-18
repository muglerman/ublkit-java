package com.cna.ublkit.render.pdf.helper;

import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightBrowserManager {

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
                options.setFormat("A5");
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
