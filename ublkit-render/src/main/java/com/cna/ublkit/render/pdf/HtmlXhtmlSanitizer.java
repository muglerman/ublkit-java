package com.cna.ublkit.render.pdf;

/**
 * Ajusta HTML generado para que sea tolerante con el parser XHTML de OpenHTMLtoPDF.
 *
 * @since 0.3.0
 */
final class HtmlXhtmlSanitizer {

    private HtmlXhtmlSanitizer() {
    }

    static String sanear(String html) {
        if (html == null || html.isBlank()) {
            return "";
        }
        return html
                .replace("<meta charset=\"UTF-8\">", "<meta charset=\"UTF-8\" />")
                .replace("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">", "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
                .replace("&family=", "&amp;family=")
                .replace("&display=", "&amp;display=")
                .replaceAll("(?i)<hr([^>]*)>", "<hr$1 />");
    }
}
