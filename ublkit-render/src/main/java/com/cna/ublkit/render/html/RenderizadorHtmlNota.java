package com.cna.ublkit.render.html;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.DocumentoBase;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convierte Nota de Crédito/Débito en HTML usando plantillas Pebble.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlNota implements RenderizadorDocumento<Object> {
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_IDENTITY = "identity";
    private static final String KEY_DOCUMENT_TYPE = "documentType";
    private static final String KEY_DOCUMENT = "document";
    private static final String KEY_CODE = "code";
    private static final String KEY_EXTRAS = "extras";
    private static final String LEGEND_TOTAL_TEXT = "1000";

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
        Object doc = contexto.documento();
        if (!(doc instanceof BorradorNotaCredito) && !(doc instanceof BorradorNotaDebito)) {
            throw new IllegalArgumentException("El documento debe ser BorradorNotaCredito o BorradorNotaDebito");
        }

        Map<String, Object> invoice = new HashMap<>();
        if (doc instanceof BorradorNotaCredito nc) {
            cargarDatosComunes(invoice, nc, "07", "NOTA DE CREDITO ELECTRONICA");
            invoice.put("noteTypeCode", txt(nc.getTipoNota()));
            invoice.put("noteTypeName", mapearTipoNotaCredito(nc.getTipoNota()));
            invoice.put("sustento", txt(nc.getSustentoDescripcion()));
            invoice.put("relatedDocument", relatedDocument(nc.getComprobanteAfectadoTipo(), nc.getComprobanteAfectadoSerieNumero(), nc.getTipoNota(), nc.getSustentoDescripcion()));
        }
        if (doc instanceof BorradorNotaDebito nd) {
            cargarDatosComunes(invoice, nd, "08", "NOTA DE DEBITO ELECTRONICA");
            invoice.put("noteTypeCode", txt(nd.getTipoNota()));
            invoice.put("noteTypeName", mapearTipoNotaDebito(nd.getTipoNota()));
            invoice.put("sustento", txt(nd.getSustentoDescripcion()));
            invoice.put("relatedDocument", relatedDocument(nd.getComprobanteAfectadoTipo(), nd.getComprobanteAfectadoSerieNumero(), nd.getTipoNota(), nd.getSustentoDescripcion()));
        }

        invoice.put("hash", txt(contexto.hashDocumento()));
        invoice.put("qr", txt(contexto.qrBase64()));
        invoice.put("logo", "logo.jpg");
        invoice.put("legends", legends(((DocumentoBase) doc).getLeyendas()));
        applyTemplateAttributes(invoice, contexto.atributosPlantilla());

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

    private void cargarDatosComunes(Map<String, Object> invoice, DocumentoBase doc, String typeCode, String name) {
        invoice.put("type", parseInt(typeCode, 7));
        invoice.put("typedIdentity", typeCode);
        invoice.put("identity", txt(doc.getSerie()) + "-" + (doc.getNumero() != null ? doc.getNumero() : ""));
        invoice.put("name", name);
        invoice.put("issueDate", txt(doc.getFechaEmision()));
        invoice.put("issueTime", txt(doc.getHoraEmision()));
        invoice.put("currency", txt(doc.getMoneda()));
        invoice.put("orderReference", txt(doc.getOrdenDeCompra()));
        invoice.put("taxRates", Map.of(
                "igv", txt(doc.getTasaIgv()),
                "ivap", txt(doc.getTasaIvap()),
                "icb", txt(doc.getTasaIcb())
        ));
        if (doc.getTipoCambio() != null) {
            Map<String, Object> exchange = new HashMap<>();
            exchange.put("date", txt(doc.getTipoCambio().fecha()));
            exchange.put("value", txt(doc.getTipoCambio().valor()));
            invoice.put("exchangeRate", exchange);
        }

        if (doc.getEmisor() != null) {
            invoice.put("taxpayer", taxpayer(doc.getEmisor()));
        }
        if (doc.getReceptor() != null) {
            invoice.put("customer", customer(doc.getReceptor()));
        }
        if (doc.getFirmante() != null) {
            Map<String, Object> signer = new HashMap<>();
            signer.put(KEY_IDENTITY, txt(doc.getFirmante().ruc()));
            signer.put(KEY_NAME, txt(doc.getFirmante().razonSocial()));
            invoice.put("signer", signer);
        }

        if (doc.getDetalles() != null) {
            List<Map<String, Object>> items = new ArrayList<>();
            int i = 1;
            for (LineaDetalle linea : doc.getDetalles()) {
                Map<String, Object> item = new HashMap<>();
                item.put("index", i++);
                item.put("quantity", txt(linea.getCantidad()));
                item.put("unitCode", txt(linea.getUnidadMedida()));
                item.put("description", txt(linea.getDescripcion()));
                item.put("code", txt(linea.getCodigoProducto()));
                item.put("sunatCode", txt(linea.getCodigoProductoSunat()));
                item.put("gs1Code", txt(linea.getCodigoProductoGS1()));
                item.put("price", txt(linea.getPrecio()));
                item.put("igvType", txt(linea.getIgvTipo()));
                item.put("igv", txt(linea.getIgv()));
                item.put("isc", txt(linea.getIsc()));
                item.put("icb", txt(linea.getIcb()));
                item.put("taxTotal", txt(linea.getTotalImpuestos()));
                items.add(item);
            }
            invoice.put("items", items);
        }
        if (doc.getCargos() != null && !doc.getCargos().isEmpty()) {
            List<Map<String, Object>> charges = doc.getCargos().stream().map(this::mapCharge).toList();
            invoice.put("charges", charges);
        }

        Map<String, Object> reference = new HashMap<>();
        reference.put("order", txt(doc.getOrdenDeCompra()));
        if (doc.getGuias() != null && !doc.getGuias().isEmpty()) {
            List<Map<String, Object>> guides = new ArrayList<>();
            for (GuiaRelacionada guia : doc.getGuias()) {
                Map<String, Object> g = new HashMap<>();
                g.put(KEY_TYPE, txt(guia.tipoDocumento()));
                g.put(KEY_DOCUMENT, txt(guia.serieNumero()));
                guides.add(g);
            }
            reference.put("guides", guides);
        }
        if (doc.getDocumentosRelacionados() != null && !doc.getDocumentosRelacionados().isEmpty()) {
            List<Map<String, Object>> docs = new ArrayList<>();
            for (DocumentoRelacionado rel : doc.getDocumentosRelacionados()) {
                Map<String, Object> d = new HashMap<>();
                d.put(KEY_TYPE, txt(rel.tipoDocumento()));
                d.put(KEY_DOCUMENT, txt(rel.serieNumero()));
                docs.add(d);
            }
            reference.put("documents", docs);
        }
        invoice.put("reference", reference);

        invoice.put("summary", summaryMap(doc));
    }

    private Map<String, Object> summaryMap(DocumentoBase doc) {
        Map<String, Object> summary = new HashMap<>();
        TotalImporte totalImp = doc instanceof BorradorNotaCredito nc ? nc.getTotalImporte()
                : doc instanceof BorradorNotaDebito nd ? nd.getTotalImporte() : null;
        TotalImpuestos totalTax = doc.getTotalImpuestos();

        if (totalImp != null) {
            summary.put("total", txt(totalImp.importe()));
            summary.put("subtotal", txt(totalImp.importeSinImpuestos()));
            summary.put("withTaxes", txt(totalImp.importeConImpuestos()));
            summary.put("advanceTotal", txt(totalImp.anticipos()));
            summary.put("discount", txt(totalImp.descuentos()));
        }
        if (totalTax != null) {
            summary.put("taxTotal", txt(totalTax.total()));
            summary.put("igv", txt(totalTax.gravadoImporte()));
            summary.put("taxable", txt(totalTax.gravadoBaseImponible()));
            summary.put("exonerated", txt(totalTax.exoneradoBaseImponible()));
            summary.put("unaffected", txt(totalTax.inafectoBaseImponible()));
            summary.put("icb", txt(totalTax.icbImporte()));
            summary.put("isc", txt(totalTax.iscImporte()));

            // Alias para templates ticket existentes.
            summary.put("taxableFre", txt(totalTax.inafectoBaseImponible()));
            summary.put("taxableVat", txt(totalTax.exoneradoBaseImponible()));
        }
        if (doc.getLeyendas() != null && doc.getLeyendas().containsKey(LEGEND_TOTAL_TEXT)) {
            summary.put("totalText", doc.getLeyendas().get(LEGEND_TOTAL_TEXT));
        } else {
            summary.put("totalText", "");
        }
        return summary;
    }

    private Map<String, Object> taxpayer(EmisorDocumento emisor) {
        Map<String, Object> m = new HashMap<>();
        m.put(KEY_IDENTITY, txt(emisor.ruc()));
        m.put(KEY_NAME, txt(emisor.razonSocial()));
        m.put("tradeName", txt(emisor.nombreComercial()));
        m.put("address", txt(emisor.direccion() != null ? emisor.direccion().direccion() : ""));
        m.put("location", location(emisor.direccion()));
        m.put("contact", contactMap(emisor.contacto()));
        return m;
    }

    private Map<String, Object> customer(ReceptorDocumento receptor) {
        Map<String, Object> m = new HashMap<>();
        m.put(KEY_DOCUMENT_TYPE, txt(receptor.tipoDocIdentidad()));
        m.put(KEY_IDENTITY, txt(receptor.numDocIdentidad()));
        m.put(KEY_NAME, txt(receptor.nombre()));
        m.put("address", txt(receptor.direccion() != null ? receptor.direccion().direccion() : ""));
        m.put("location", location(receptor.direccion()));
        m.put("contact", contactMap(receptor.contacto()));
        return m;
    }

    private Map<String, Object> relatedDocument(String type, String document, String reasonCode, String reasonDescription) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, txt(type));
        map.put(KEY_DOCUMENT, txt(document));
        map.put(KEY_CODE, txt(reasonCode));
        map.put("description", txt(reasonDescription));
        return map;
    }

    private Map<String, Object> mapCharge(CargoDescuento c) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, txt(c.tipo()));
        map.put("amount", txt(c.monto()));
        map.put("factor", txt(c.porcentaje()));
        map.put(KEY_DOCUMENT, txt(c.serieNumero()));
        return map;
    }

    private List<Map<String, Object>> legends(Map<String, String> leyendas) {
        List<Map<String, Object>> values = new ArrayList<>();
        if (leyendas == null || leyendas.isEmpty()) return values;
        leyendas.forEach((code, value) -> {
            if (LEGEND_TOTAL_TEXT.equals(code)) return;
            Map<String, Object> item = new HashMap<>();
            item.put(KEY_CODE, txt(code));
            item.put("value", txt(value));
            values.add(item);
        });
        return values;
    }

    private void applyTemplateAttributes(Map<String, Object> invoice, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        invoice.put("header", txt(attrs.get("header")));
        invoice.put("footer", txt(attrs.get("footer")));

        if (attrs.containsKey("colorPrimario")) {
            invoice.put("colorPrimario", txt(attrs.get("colorPrimario")));
        }
        if (attrs.containsKey("color")) {
            invoice.put("color", txt(attrs.get("color")));
            if (!attrs.containsKey("colorPrimario")) {
                invoice.put("colorPrimario", txt(attrs.get("color")));
            }
        }

        if (attrs.containsKey("logo")) {
            Object logoObj = attrs.get("logo");
            if (logoObj instanceof String s) {
                if (s.startsWith("data:image")) {
                    invoice.put("logo", s);
                } else {
                    // Fallback for paths? Keep it as is or try to read? We'll just pass it
                    invoice.put("logo", s);
                }
            } else if (logoObj instanceof java.io.InputStream is) {
                try (is) {
                    byte[] bytes = is.readAllBytes();
                    String b64 = java.util.Base64.getEncoder().encodeToString(bytes);
                    invoice.put("logo", "data:image/png;base64," + b64);
                } catch (java.io.IOException e) {
                    // Fallback
                }
            } else if (logoObj instanceof byte[] bytes) {
                String b64 = java.util.Base64.getEncoder().encodeToString(bytes);
                invoice.put("logo", "data:image/png;base64," + b64);
            }
        }

        Object extras = attrs.get(KEY_EXTRAS);
        if (extras instanceof List<?> list) {
            invoice.put(KEY_EXTRAS, list);
        } else {
            invoice.put(KEY_EXTRAS, List.of());
        }
    }

    private String mapearTipoNotaCredito(String code) {
        return switch (txt(code)) {
            case "01" -> "Anulación de la operación";
            case "02" -> "Anulación por error en RUC";
            case "03" -> "Corrección por error en descripción";
            case "07" -> "Devolución por ítem";
            default -> "";
        };
    }

    private String mapearTipoNotaDebito(String code) {
        return switch (txt(code)) {
            case "01" -> "Intereses por mora";
            case "02" -> "Aumento en el valor";
            case "03" -> "Penalidades";
            default -> "";
        };
    }

    private Map<String, Object> contactMap(Contacto contacto) {
        Map<String, Object> c = new HashMap<>();
        c.put("name", txt(contacto != null ? contacto.nombre() : ""));
        c.put("telephone", txt(contacto != null ? contacto.telefono() : ""));
        c.put("email", txt(contacto != null ? contacto.email() : ""));
        c.put("web", "");
        return c;
    }

    private String location(Direccion d) {
        if (d == null) return "";
        List<String> parts = new ArrayList<>();
        if (!txt(d.departamento()).isBlank()) parts.add(txt(d.departamento()));
        if (!txt(d.provincia()).isBlank()) parts.add(txt(d.provincia()));
        if (!txt(d.distrito()).isBlank()) parts.add(txt(d.distrito()));
        if (!txt(d.ubigeo()).isBlank()) parts.add("Ubigeo " + d.ubigeo());
        return String.join(" - ", parts);
    }

    private int parseInt(String value, int dft) {
        try {
            return value != null ? Integer.parseInt(value) : dft;
        } catch (NumberFormatException ex) {
            return dft;
        }
    }

    private String txt(Object value) {
        if (value == null) return "";
        if (value instanceof BigDecimal b) {
            return b.stripTrailingZeros().toPlainString();
        }
        return value.toString();
    }
}
