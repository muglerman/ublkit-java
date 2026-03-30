package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Convierte un BorradorGuiaRemision en HTML utilizando la plantilla Pebble de despacho (despatch.html).
 * Parsea el modelo de dominio en los diccionarios compatibles con las llaves que espera la vista.
 * Soporta formatos A4 y A5.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {

    private final PebbleEngine engine;
    private final FormatoImpresion formato;

    public RenderizadorHtmlGuiaRemision() {
         this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlGuiaRemision(FormatoImpresion formato) {
         this.engine = new PebbleEngine.Builder().build();
         this.formato = formato;
    }

    private String obtenerRutaPlantilla() {
        return switch(formato) {
            case A5 -> "templates/despatch.a5.html";
            default -> "templates/despatch.a4.html";
        };
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        BorradorGuiaRemision doc = contexto.documento();

        Map<String, Object> receipt = new HashMap<>();

        // Identificadores y fechas
        receipt.put("type", doc.getTipoComprobante() != null ? Integer.parseInt(doc.getTipoComprobante()) : 9);
        receipt.put("identity", doc.getSerie() + "-" + doc.getNumero());
        receipt.put("name", "31".equals(doc.getTipoComprobante()) ? "GUÍA DE REMISIÓN TRANSPORTISTA" : "GUÍA DE REMISIÓN REMITENTE");
        receipt.put("issueDate", doc.getFechaEmision() != null ? doc.getFechaEmision().toString() : "");
        receipt.put("note", doc.getObservaciones() != null ? doc.getObservaciones() : "");
        
        // Metadatos y firmas
        receipt.put("hash", contexto.hashDocumento() != null ? contexto.hashDocumento() : "");
        receipt.put("qr", contexto.qrBase64() != null ? contexto.qrBase64() : "");
        receipt.put("logo", "logo.jpg");

        // Taxpayer / Remitente
        if (doc.getRemitente() != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put("identity", doc.getRemitente().ruc());
            taxpayer.put("name", doc.getRemitente().razonSocial());
            
            Map<String, Object> contact = new HashMap<>();
            contact.put("email", "");
            contact.put("telephone", "");
            taxpayer.put("contact", contact);

            receipt.put("taxpayer", taxpayer);
        }

        // Customer / Destinatario
        if (doc.getDestinatario() != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("identity", doc.getDestinatario().numeroDocumentoIdentidad());
            customer.put("name", doc.getDestinatario().nombre());
            receipt.put("customer", customer);
        }

        // Datos del traslado (envío)
        if (doc.getEnvio() != null) {
            receipt.put("startDate", doc.getEnvio().getFechaTraslado());
            receipt.put("weight", doc.getEnvio().getPesoTotal());
            receipt.put("unitCode", doc.getEnvio().getPesoTotalUnidadMedida());
            // receipt.put("handling", doc.getEnvio().getMotivoTraslado()); // Requiere resolver catálogo 20

            Map<String, Object> address = new HashMap<>();
            if (doc.getEnvio().getPartida() != null) {
                Map<String, String> origin = new HashMap<>();
                origin.put("ubigeo", doc.getEnvio().getPartida().ubigeo());
                origin.put("line", doc.getEnvio().getPartida().direccion());
                address.put("origin", origin);
            }
            if (doc.getEnvio().getDestino() != null) {
                Map<String, String> delivery = new HashMap<>();
                delivery.put("ubigeo", doc.getEnvio().getDestino().ubigeo());
                delivery.put("line", doc.getEnvio().getDestino().direccion());
                address.put("delivery", delivery);
            }
            receipt.put("address", address);
        }

        // Ítems
        if (doc.getDetalles() != null) {
            List<Map<String, Object>> items = doc.getDetalles().stream().map(linea -> {
                Map<String, Object> item = new HashMap<>();
                item.put("quantity", linea.cantidad());
                item.put("unitCode", linea.unidadMedida());
                item.put("description", linea.descripcion());
                return item;
            }).collect(Collectors.toList());
            receipt.put("items", items);
        }

        Map<String, Object> scope = new HashMap<>();
        scope.put("receipt", receipt);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla());
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando guía HTML: " + e.getMessage(), e);
        }
    }
}
