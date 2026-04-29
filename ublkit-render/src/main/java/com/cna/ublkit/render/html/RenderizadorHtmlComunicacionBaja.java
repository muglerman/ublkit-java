package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.PlantillaRutas;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pebble.PebbleEngines;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
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

    private final io.pebbletemplates.pebble.PebbleEngine engine = PebbleEngines.crear();

    private String obtenerRutaPlantilla(ContextoRender<ComunicacionBaja> contexto) {
        return PlantillaRutas.ruta("voided", FormatoImpresion.A4,
                PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT));
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<ComunicacionBaja> contexto) {
        ComunicacionBaja baja = contexto.documento();
        LocalDateTime fechaEmision = fechaEmision(baja.getFechaEmision());

        Map<String, Object> ra = new HashMap<>();
        ra.put("id", "RA-" + (baja.getFechaEmision() != null ? baja.getFechaEmision().toString().replace("-", "") : "00000000") + "-" + (baja.getNumero() != null ? baja.getNumero() : 1));
        ra.put("issueDate", fechaEmision);
        ra.put("referenceDate", baja.getFechaEmisionComprobantes());
        ra.put("fechaEmision", fechaEmision);
        ra.put("currency", txt(baja.getMoneda()));
        ra.put("hash", txt(contexto.hashDocumento()));
        ra.put("qr", txt(contexto.qrBase64()));

        if (baja.getEmisor() != null) {
            Map<String, Object> issuer = new HashMap<>();
            issuer.put("ruc", txt(baja.getEmisor().ruc()));
            issuer.put("name", txt(baja.getEmisor().razonSocial()));
            issuer.put("tradeName", txt(baja.getEmisor().nombreComercial()));
            issuer.put("razonSocial", txt(baja.getEmisor().razonSocial()));
            Map<String, Object> address = new HashMap<>();
            address.put("direccion", "");
            issuer.put("address", address);
            ra.put("issuer", issuer);
            ra.put("company", issuer);
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
                line.put("tipoDoc", txt(item.tipoComprobante()));
                line.put("serie", txt(item.serie()));
                line.put("number", item.numero() != null ? item.numero().toString() : "");
                line.put("correlativo", item.numero() != null ? item.numero().toString() : "");
                line.put("reason", txt(item.descripcionSustento()));
                line.put("desMotivoBaja", txt(item.descripcionSustento()));
                lines.add(line);
            }
        }
        ra.put("lines", lines);
        ra.put("details", lines);
        applyTemplateAttributes(ra, contexto.atributosPlantilla());

        Map<String, Object> scope = new HashMap<>();
        scope.put("voided", ra);
        scope.put("doc", ra);
        scope.put("cl", fallbackCustomer(baja));
        scope.put("moneda", txt(baja.getMoneda()));

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
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

    private LocalDateTime fechaEmision(java.time.LocalDate fecha) {
        return LocalDateTime.of(fecha != null ? fecha : java.time.LocalDate.now(), java.time.LocalTime.MIDNIGHT);
    }

    private void applyTemplateAttributes(Map<String, Object> voided, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        voided.put("header", txt(attrs.get("header")));
        voided.put("footer", txt(attrs.get("footer")));
    }

    private Map<String, Object> fallbackCustomer(ComunicacionBaja baja) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("rznSocial", baja.getEmisor() != null ? txt(baja.getEmisor().razonSocial()) : "");
        customer.put("numDoc", baja.getEmisor() != null ? txt(baja.getEmisor().ruc()) : "");
        customer.put("name", baja.getEmisor() != null ? txt(baja.getEmisor().razonSocial()) : "");
        customer.put("identity", baja.getEmisor() != null ? txt(baja.getEmisor().ruc()) : "");
        Map<String, Object> address = new HashMap<>();
        address.put("direccion", "");
        customer.put("address", address);
        return customer;
    }
}
