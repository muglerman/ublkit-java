package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renderizador HTML para Comunicación de Baja.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlComunicacionBaja implements RenderizadorDocumento<ComunicacionBaja> {

    private final PebbleEngine engine = new PebbleEngine.Builder().loader(new io.pebbletemplates.pebble.loader.ClasspathLoader()).build();

    @Override
    public ResultadoRender renderizar(ContextoRender<ComunicacionBaja> contexto) {
        ComunicacionBaja baja = contexto.documento();

        Map<String, Object> ra = new HashMap<>();
        ra.put("id", "RA-" + (baja.getFechaEmision() != null ? baja.getFechaEmision().toString().replace("-", "") : "00000000") + "-" + (baja.getNumero() != null ? baja.getNumero() : 1));
        ra.put("issueDate", txt(baja.getFechaEmision()));
        ra.put("referenceDate", txt(baja.getFechaEmisionComprobantes()));
        ra.put("currency", txt(baja.getMoneda()));
        ra.put("hash", txt(contexto.hashDocumento()));
        ra.put("qr", txt(contexto.qrBase64()));

        if (baja.getEmisor() != null) {
            Map<String, Object> issuer = new HashMap<>();
            issuer.put("ruc", txt(baja.getEmisor().ruc()));
            issuer.put("name", txt(baja.getEmisor().razonSocial()));
            issuer.put("tradeName", txt(baja.getEmisor().nombreComercial()));
            ra.put("issuer", issuer);
        }
        if (baja.getFirmante() != null) {
            Map<String, Object> signer = new HashMap<>();
            signer.put("ruc", txt(baja.getFirmante().ruc()));
            signer.put("name", txt(baja.getFirmante().razonSocial()));
            ra.put("signer", signer);
        }

        List<Map<String, Object>> lines = new ArrayList<>();
        if (baja.getComprobantes() != null) {
            int i = 1;
            for (ItemBaja item : baja.getComprobantes()) {
                Map<String, Object> line = new HashMap<>();
                line.put("index", i++);
                line.put("documentType", txt(item.tipoComprobante()));
                line.put("serie", txt(item.serie()));
                line.put("number", item.numero() != null ? item.numero().toString() : "");
                line.put("reason", txt(item.descripcionSustento()));
                lines.add(line);
            }
        }
        ra.put("lines", lines);
        applyTemplateAttributes(ra, contexto.atributosPlantilla());

        Map<String, Object> scope = new HashMap<>();
        scope.put("voided", ra);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate("templates/voided.a4.html");
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando comunicación de baja HTML: " + e.getMessage(), e);
        }
    }

    private String txt(Object value) {
        return value == null ? "" : value.toString();
    }

    private void applyTemplateAttributes(Map<String, Object> voided, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        voided.put("header", txt(attrs.get("header")));
        voided.put("footer", txt(attrs.get("footer")));

        if (attrs.containsKey("color")) {
            voided.put("color", txt(attrs.get("color")));
        }

        if (attrs.containsKey("logo")) {
            Object logoObj = attrs.get("logo");
            if (logoObj instanceof String s) {
                voided.put("logo", s);
            } else if (logoObj instanceof java.io.InputStream is) {
                try (is) {
                    byte[] bytes = is.readAllBytes();
                    String b64 = java.util.Base64.getEncoder().encodeToString(bytes);
                    voided.put("logo", "data:image/png;base64," + b64);
                } catch (java.io.IOException e) {
                    // Fallback to default
                }
            } else if (logoObj instanceof byte[] bytes) {
                String b64 = java.util.Base64.getEncoder().encodeToString(bytes);
                voided.put("logo", "data:image/png;base64," + b64);
            }
        }
    }
}
