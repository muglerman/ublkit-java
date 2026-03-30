package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Convierte un modelo de Nota Electrónica en HTML utilizando las plantillas base de Factura de Pebble (Twig).
 * Realiza la traducción del modelo de dominio de UBLKit hacia las llaves esperadas por la plantilla,
 * incluyendo los motivos y documentos relacionados característicos de las notas.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlNota implements RenderizadorDocumento<Object> {

    private final PebbleEngine engine;
    private final FormatoImpresion formato;

    public RenderizadorHtmlNota() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlNota(FormatoImpresion formato) {
        this.engine = new PebbleEngine.Builder().build();
        this.formato = formato;
    }

    private String obtenerRutaPlantilla() {
        return switch (formato) {
            case A5 -> "templates/note.a5.html";
            case TICKET_80MM -> "templates/note.ticket80mm.html";
            case TICKET_58MM -> "templates/note.ticket58mm.html";
            default -> "templates/note.a4.html";
        };
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<Object> contexto) {
        Object docBase = contexto.documento();

        if (!(docBase instanceof BorradorNotaCredito) && !(docBase instanceof BorradorNotaDebito)) {
             throw new IllegalArgumentException("El documento debe ser una BorradorNotaCredito o BorradorNotaDebito");
        }

        Map<String, Object> invoice = new HashMap<>();
        
        // Extracción de datos comunes a ambos usando reflexion de alto nivel (en Java puro real a menudo usaríamos interfaces, pero DocumentoBase es sealed)
        if (docBase instanceof BorradorNotaCredito doc) {
            extraerBasicos(invoice, doc.getSerie(), doc.getNumero(), "07", "NOTA DE CRÉDITO ELECTRÓNICA", doc.getFechaEmision() != null ? doc.getFechaEmision().toString() : "", doc.getMoneda());
            extraerRelacionado(invoice, doc.getComprobanteAfectadoTipo(), doc.getComprobanteAfectadoSerieNumero(), doc.getTipoNota(), doc.getSustentoDescripcion());
            extraerActores(invoice, doc.getEmisor(), doc.getReceptor());
            extraerTotales(invoice, doc.getTotalImporte(), doc.getTotalImpuestos(), doc.getLeyendas());
            extraerDetalles(invoice, doc.getDetalles());
        } else if (docBase instanceof BorradorNotaDebito doc) {
            extraerBasicos(invoice, doc.getSerie(), doc.getNumero(), "08", "NOTA DE DÉBITO ELECTRÓNICA", doc.getFechaEmision() != null ? doc.getFechaEmision().toString() : "", doc.getMoneda());
            extraerRelacionado(invoice, doc.getComprobanteAfectadoTipo(), doc.getComprobanteAfectadoSerieNumero(), doc.getTipoNota(), doc.getSustentoDescripcion());
            extraerActores(invoice, doc.getEmisor(), doc.getReceptor());
            extraerTotales(invoice, doc.getTotalImporte(), doc.getTotalImpuestos(), doc.getLeyendas());
            extraerDetalles(invoice, doc.getDetalles());
        }
        
        // Atributos inyectados del contexto
        invoice.put("hash", contexto.hashDocumento() != null ? contexto.hashDocumento() : "");
        invoice.put("qr", contexto.qrBase64() != null ? contexto.qrBase64() : "");
        invoice.put("logo", "logo.jpg");

        Map<String, Object> scope = new HashMap<>();
        scope.put("invoice", invoice);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla());
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando nota HTML: " + e.getMessage(), e);
        }
    }

    private void extraerBasicos(Map<String, Object> invoice, String serie, Integer numero, String type, String name, String issue, String curr) {
        invoice.put("type", Integer.parseInt(type));
        invoice.put("identity", serie + "-" + numero);
        invoice.put("name", name);
        invoice.put("issueDate", issue);
        invoice.put("currency", curr);
    }

    private void extraerRelacionado(Map<String, Object> invoice, String tipoRef, String serieRef, String codMotivo, String descMotivo) {
        Map<String, Object> docRef = new HashMap<>();
        docRef.put("type", tipoRef);
        docRef.put("document", serieRef);
        docRef.put("code", codMotivo);
        docRef.put("description", descMotivo);
        invoice.put("relatedDocument", docRef);
    }

    private void extraerActores(Map<String, Object> invoice, EmisorDocumento emisor, ReceptorDocumento receptor) {
        if (emisor != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put("identity", emisor.ruc());
            taxpayer.put("name", emisor.razonSocial());
            taxpayer.put("address", emisor.direccion() != null ? emisor.direccion().direccion() : "");
            Map<String, Object> contact = new HashMap<>();
            contact.put("email", ""); contact.put("telephone", "");
            taxpayer.put("contact", contact);
            invoice.put("taxpayer", taxpayer);
        }

        if (receptor != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("identity", receptor.numDocIdentidad());
            customer.put("name", receptor.nombre());
            customer.put("address", receptor.direccion() != null ? receptor.direccion().direccion() : "");
            invoice.put("customer", customer);
        }
    }

    private void extraerTotales(Map<String, Object> invoice, TotalImporte totalImp, TotalImpuestos totalTax, Map<String, String> keys) {
        if (totalImp != null) {
            Map<String, Object> summary = new HashMap<>();
            summary.put("total", totalImp.importe());
            if (keys != null && keys.containsKey("1000")) {
                summary.put("totalText", keys.get("1000"));
            }
            if (totalTax != null) {
                summary.put("igv", totalTax.total());
                if (totalTax.gravadoBaseImponible() != null) summary.put("taxable", totalTax.gravadoBaseImponible());
            }
            invoice.put("summary", summary);
        }
    }

    private void extraerDetalles(Map<String, Object> invoice, List<LineaDetalle> detalles) {
        if (detalles != null) {
            List<Map<String, Object>> items = detalles.stream().map(linea -> {
                Map<String, Object> item = new HashMap<>();
                item.put("quantity", linea.getCantidad());
                item.put("unitCode", linea.getUnidadMedida());
                item.put("description", linea.getDescripcion());
                item.put("value", linea.getPrecio());
                item.put("price", linea.getPrecio());
                return item;
            }).collect(Collectors.toList());
            invoice.put("items", items);
        }
    }
}
