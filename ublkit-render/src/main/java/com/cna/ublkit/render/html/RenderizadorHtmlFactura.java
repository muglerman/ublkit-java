package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
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
 * Convierte un BorradorFactura (o boleta) en HTML utilizando plantillas base Pebble (Twig).
 * Realiza la traducción del modelo de dominio de UBLKit hacia las llaves esperadas por la plantilla.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlFactura implements RenderizadorDocumento<BorradorFactura> {

    private final PebbleEngine engine;
    private final FormatoImpresion formato;

    public RenderizadorHtmlFactura() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlFactura(FormatoImpresion formato) {
        this.engine = new PebbleEngine.Builder().build();
        this.formato = formato;
    }

    private String obtenerRutaPlantilla() {
        return switch (formato) {
            case A5 -> "templates/invoice.a5.html";
            case TICKET_80MM -> "templates/invoice.ticket80mm.html";
            case TICKET_58MM -> "templates/invoice.ticket58mm.html";
            default -> "templates/invoice.a4.html";
        };
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        BorradorFactura doc = contexto.documento();

        Map<String, Object> invoice = new HashMap<>();

        // Identificación y metadatos
        invoice.put("type", doc.getTipoComprobante() != null ? Integer.parseInt(doc.getTipoComprobante()) : 1);
        invoice.put("identity", doc.getSerie() + "-" + doc.getNumero());
        invoice.put("name", mapearNombreDocumento(doc.getTipoComprobante()));
        invoice.put("issueDate", doc.getFechaEmision() != null ? doc.getFechaEmision().toString() : "");
        invoice.put("dueDate", doc.getFechaVencimiento() != null ? doc.getFechaVencimiento().toString() : "");
        invoice.put("currency", doc.getMoneda());
        
        // Atributos inyectados del contexto
        invoice.put("hash", contexto.hashDocumento() != null ? contexto.hashDocumento() : "");
        invoice.put("qr", contexto.qrBase64() != null ? contexto.qrBase64() : "");
        invoice.put("logo", "logo.jpg"); // logo por defecto, podría inyectarse desde un contexto visual futuro

        // Emisor -> taxpayer
        if (doc.getEmisor() != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put("identity", doc.getEmisor().ruc());
            taxpayer.put("name", doc.getEmisor().razonSocial());
            taxpayer.put("address", doc.getEmisor().direccion() != null ? doc.getEmisor().direccion().direccion() : "");
            
            // Map Contact (simplificado, asumiendo estructura futura de contacto)
            Map<String, Object> contact = new HashMap<>();
            contact.put("email", ""); // Rellenar con getCorreo() si se agrega al dominio
            contact.put("telephone", "");
            taxpayer.put("contact", contact);

            invoice.put("taxpayer", taxpayer);
        }

        // Receptor -> customer
        if (doc.getReceptor() != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("identity", doc.getReceptor().numDocIdentidad());
            customer.put("name", doc.getReceptor().nombre());
            customer.put("address", doc.getReceptor().direccion() != null ? doc.getReceptor().direccion().direccion() : "");
            invoice.put("customer", customer);
        }

        // Totales y Resumen -> summary
        if (doc.getTotalImporte() != null) {
            Map<String, Object> summary = new HashMap<>();
            summary.put("total", doc.getTotalImporte().importe());
            // Leyenda "SON: ..."
            if (doc.getLeyendas() != null && doc.getLeyendas().containsKey("1000")) {
                summary.put("totalText", doc.getLeyendas().get("1000"));
            }
            if (doc.getTotalImpuestos() != null) {
                summary.put("igv", doc.getTotalImpuestos().total());
                // Operaciones gravadas
                if (doc.getTotalImpuestos().gravadoBaseImponible() != null) {
                     summary.put("taxable", doc.getTotalImpuestos().gravadoBaseImponible());
                }
            }
            invoice.put("summary", summary);
        }

        // Detalles -> items
        if (doc.getDetalles() != null) {
            List<Map<String, Object>> items = doc.getDetalles().stream().map(linea -> {
                Map<String, Object> map = new HashMap<>();
                map.put("quantity", linea.getCantidad());
                map.put("unitCode", linea.getUnidadMedida());
                map.put("description", linea.getDescripcion());
                map.put("value", linea.getPrecio()); // UBLKit LineaDetalle solo expone getPrecio()
                map.put("price", linea.getPrecio());
                return map;
            }).collect(Collectors.toList());
            invoice.put("items", items);
        }

        Map<String, Object> scope = new HashMap<>();
        scope.put("invoice", invoice);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla());
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando factura HTML: " + e.getMessage(), e);
        }
    }

    private String mapearNombreDocumento(String tipo) {
        if ("03".equals(tipo)) return "BOLETA DE VENTA ELECTRÓNICA";
        if ("07".equals(tipo)) return "NOTA DE CRÉDITO ELECTRÓNICA";
        if ("08".equals(tipo)) return "NOTA DE DÉBITO ELECTRÓNICA";
        return "FACTURA ELECTRÓNICA";
    }
}
