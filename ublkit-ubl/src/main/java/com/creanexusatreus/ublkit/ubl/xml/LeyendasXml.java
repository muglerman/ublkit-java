package com.creanexusatreus.ublkit.ubl.xml;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.creanexusatreus.ublkit.ubl.xml.ConstantesUbl.NS_CBC;

/** Utilidad única para serializar leyendas SUNAT (Catálogo 52). */
final class LeyendasXml {
    private LeyendasXml() { }

    static void agregar(Document doc, Element raiz, Map<String, String> leyendas) {
        if (leyendas == null) return;
        for (Map.Entry<String, String> entry : leyendas.entrySet()) {
            String code = entry.getKey();
            String value = entry.getValue();
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("La leyenda SUNAT requiere languageLocaleID");
            }
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("La leyenda SUNAT requiere una descripción no vacía: " + code);
            }
            Element note = doc.createElementNS(NS_CBC, "cbc:Note");
            note.setAttribute("languageLocaleID", code);
            note.appendChild(doc.createCDATASection(value));
            raiz.appendChild(note);
        }
    }
}
