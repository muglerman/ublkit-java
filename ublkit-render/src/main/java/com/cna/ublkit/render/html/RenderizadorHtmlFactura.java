package com.cna.ublkit.render.html;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.PlantillaRutas;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.pebble.PebbleEngines;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.complemento.Anticipo;
import com.cna.ublkit.ubl.modelo.complemento.CuotaDePago;
import com.cna.ublkit.ubl.modelo.complemento.Descuento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convierte un BorradorFactura (o boleta) en HTML usando plantillas Pebble.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlFactura implements RenderizadorDocumento<BorradorFactura> {
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_IDENTITY = "identity";
    private static final String KEY_DOCUMENT_TYPE = "documentType";
    private static final String KEY_DOCUMENT = "document";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_VALUE = "value";
    private static final String KEY_EXTRAS = "extras";
    private static final String LEGEND_TOTAL_TEXT = "1000";

    private final io.pebbletemplates.pebble.PebbleEngine engine;
    private final FormatoImpresion formato;

    public RenderizadorHtmlFactura() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlFactura(FormatoImpresion formato) {
        this.engine = PebbleEngines.crear();
        this.formato = formato;
    }

    private String obtenerRutaPlantilla(ContextoRender<BorradorFactura> contexto) {
        return PlantillaRutas.ruta("invoice", formato, PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT));
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        BorradorFactura doc = contexto.documento();
        LocalDateTime fechaEmision = fechaEmision(doc.getFechaEmision(), doc.getHoraEmision());

        Map<String, Object> invoice = new HashMap<>();
        invoice.put(KEY_TYPE, parseInt(doc.getTipoComprobante(), 1));
        invoice.put("typedIdentity", txt(doc.getTipoComprobante()));
        invoice.put(KEY_IDENTITY, txt(doc.getSerie()) + "-" + (doc.getNumero() != null ? doc.getNumero() : ""));
        invoice.put(KEY_NAME, mapearNombreDocumento(doc.getTipoComprobante()));
        invoice.put("issueDate", fechaEmision);
        invoice.put("fechaEmision", fechaEmision);
        invoice.put("issueTime", doc.getHoraEmision() != null ? doc.getHoraEmision() : LocalTime.MIDNIGHT);
        invoice.put("dueDate", doc.getFechaVencimiento());
        invoice.put("currency", txt(doc.getMoneda()));
        invoice.put("operationTypeCode", txt(doc.getTipoOperacion()));
        invoice.put("operationTypeName", mapearTipoOperacion(doc.getTipoOperacion()));
        invoice.put("note", txt(doc.getObservaciones()));
        invoice.put("orderReference", txt(doc.getOrdenDeCompra()));
        invoice.put("hash", txt(contexto.hashDocumento()));
        invoice.put("qr", txt(contexto.qrBase64()));
        invoice.put("logo", "logo.jpg");
        invoice.put("deliveryAddress", txt(doc.getDireccionEntrega() != null ? doc.getDireccionEntrega().direccion() : ""));
        invoice.put("serie", txt(doc.getSerie()) + "-" + txt(doc.getNumero()));
        invoice.put("correlativo", txt(doc.getNumero()));
        invoice.put("taxRates", Map.of(
                "igv", txt(doc.getTasaIgv()),
                "ivap", txt(doc.getTasaIvap()),
                "icb", txt(doc.getTasaIcb())
        ));

        if (doc.getFirmante() != null) {
            Map<String, Object> signer = new HashMap<>();
            signer.put(KEY_IDENTITY, txt(doc.getFirmante().ruc()));
            signer.put(KEY_NAME, txt(doc.getFirmante().razonSocial()));
            invoice.put("signer", signer);
        }

        if (doc.getTipoCambio() != null) {
            Map<String, Object> exchange = new HashMap<>();
            exchange.put("date", doc.getTipoCambio().fecha());
            exchange.put(KEY_VALUE, txt(doc.getTipoCambio().valor()));
            invoice.put("exchangeRate", exchange);
        }

        if (doc.getEmisor() != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put(KEY_IDENTITY, txt(doc.getEmisor().ruc()));
            taxpayer.put(KEY_NAME, txt(doc.getEmisor().razonSocial()));
            taxpayer.put("ruc", txt(doc.getEmisor().ruc()));
            taxpayer.put("razonSocial", txt(doc.getEmisor().razonSocial()));
            taxpayer.put("tradeName", txt(doc.getEmisor().nombreComercial()));
            Map<String, Object> address = new HashMap<>();
            address.put("direccion", txt(doc.getEmisor().direccion() != null ? doc.getEmisor().direccion().direccion() : ""));
            taxpayer.put("address", address);
            taxpayer.put("addressText", txt(doc.getEmisor().direccion() != null ? doc.getEmisor().direccion().direccion() : ""));
            taxpayer.put("location", location(doc.getEmisor().direccion()));
            taxpayer.put("contact", contactMap(doc.getEmisor().contacto()));
            invoice.put("taxpayer", taxpayer);
            invoice.put("company", taxpayer);
        }

        if (doc.getReceptor() != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put(KEY_DOCUMENT_TYPE, txt(doc.getReceptor().tipoDocIdentidad()));
            customer.put(KEY_IDENTITY, txt(doc.getReceptor().numDocIdentidad()));
            customer.put(KEY_NAME, txt(doc.getReceptor().nombre()));
            customer.put("rznSocial", txt(doc.getReceptor().nombre()));
            customer.put("numDoc", txt(doc.getReceptor().numDocIdentidad()));
            Map<String, Object> address = new HashMap<>();
            address.put("direccion", txt(doc.getReceptor().direccion() != null ? doc.getReceptor().direccion().direccion() : ""));
            customer.put("address", address);
            customer.put("location", location(doc.getReceptor().direccion()));
            customer.put("contact", contactMap(doc.getReceptor().contacto()));
            invoice.put("customer", customer);
            invoice.put("cl", customer);
        }

        if (doc.getFormaDePago() != null) {
            Map<String, Object> payment = new HashMap<>();
            payment.put(KEY_TYPE, txt(doc.getFormaDePago().tipo()));
            payment.put(KEY_TOTAL, txt(doc.getFormaDePago().total()));
            List<Map<String, Object>> installments = new ArrayList<>();
            if (doc.getFormaDePago().cuotas() != null) {
                for (CuotaDePago cuota : doc.getFormaDePago().cuotas()) {
                    Map<String, Object> c = new HashMap<>();
                    c.put(KEY_AMOUNT, txt(cuota.importe()));
                    c.put("dueDate", txt(cuota.fechaPago()));
                    installments.add(c);
                }
            }
            payment.put("installments", installments);
            invoice.put("payment", payment);

            // Compatibilidad con plantillas ticket existentes.
            invoice.put("shares", installments);
        }

        if (doc.getDetraccion() != null) {
            Map<String, Object> detr = new HashMap<>();
            detr.put("method", txt(doc.getDetraccion().medioDePago()));
            detr.put("financialAccount", txt(doc.getDetraccion().cuentaBancaria()));
            detr.put("serviceCode", txt(doc.getDetraccion().tipoBienDetraido()));
            detr.put("percent", txt(doc.getDetraccion().porcentaje()));
            detr.put("amount", txt(doc.getDetraccion().monto()));
            invoice.put("detraction", detr);
        }

        if (doc.getPercepcion() != null) {
            Map<String, Object> perception = new HashMap<>();
            perception.put(KEY_TYPE, txt(doc.getPercepcion().tipo()));
            perception.put("base", txt(doc.getPercepcion().montoBase()));
            perception.put("percent", txt(doc.getPercepcion().porcentaje()));
            perception.put(KEY_AMOUNT, txt(doc.getPercepcion().monto()));
            perception.put(KEY_TOTAL, txt(doc.getPercepcion().montoTotal()));
            invoice.put("perception", perception);
        }
        if (doc.getGuiaEmbebida() != null) {
            Map<String, Object> embeddedGuide = new HashMap<>();
            embeddedGuide.put("arrival", txt(doc.getGuiaEmbebida().llegada() != null ? doc.getGuiaEmbebida().llegada().direccion() : ""));
            embeddedGuide.put("departure", txt(doc.getGuiaEmbebida().partida() != null ? doc.getGuiaEmbebida().partida().direccion() : ""));
            embeddedGuide.put("license", txt(doc.getGuiaEmbebida().nroLicencia()));
            embeddedGuide.put("plate", txt(doc.getGuiaEmbebida().transpPlaca()));
            embeddedGuide.put("authorization", txt(doc.getGuiaEmbebida().transpCodeAuth()));
            embeddedGuide.put("brand", txt(doc.getGuiaEmbebida().transpMarca()));
            embeddedGuide.put("mode", txt(doc.getGuiaEmbebida().modTraslado()));
            embeddedGuide.put("weight", txt(doc.getGuiaEmbebida().pesoBruto()));
            embeddedGuide.put("weightUnit", txt(doc.getGuiaEmbebida().undPesoBruto()));
            if (doc.getGuiaEmbebida().transportista() != null) {
                Map<String, Object> transport = new HashMap<>();
                transport.put(KEY_NAME, txt(doc.getGuiaEmbebida().transportista().nombre()));
                transport.put(KEY_DOCUMENT_TYPE, txt(doc.getGuiaEmbebida().transportista().tipoDocIdentidad()));
                transport.put(KEY_IDENTITY, txt(doc.getGuiaEmbebida().transportista().numDocIdentidad()));
                embeddedGuide.put("carrier", transport);
            }
            invoice.put("embeddedGuide", embeddedGuide);
        }

        if (doc.getAnticipos() != null && !doc.getAnticipos().isEmpty()) {
            List<Map<String, Object>> advances = new ArrayList<>();
            for (Anticipo a : doc.getAnticipos()) {
                Map<String, Object> map = new HashMap<>();
                map.put(KEY_TYPE, txt(a.tipo()));
                map.put(KEY_DOCUMENT_TYPE, txt(a.comprobanteTipo()));
                map.put(KEY_DOCUMENT, txt(a.comprobanteSerieNumero()));
                map.put(KEY_AMOUNT, txt(a.monto()));
                advances.add(map);
            }
            invoice.put("advances", advances);
        }

        if (doc.getDescuentos() != null && !doc.getDescuentos().isEmpty()) {
            List<Map<String, Object>> discounts = doc.getDescuentos().stream().map(this::mapDiscount).toList();
            invoice.put("discounts", discounts);
        }
        if (doc.getCargos() != null && !doc.getCargos().isEmpty()) {
            List<Map<String, Object>> charges = doc.getCargos().stream().map(this::mapCharge).toList();
            invoice.put("charges", charges);
        }

        Map<String, Object> reference = new HashMap<>();
        reference.put("order", txt(doc.getOrdenDeCompra()));
        reference.put("note", txt(doc.getObservaciones()));
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
            for (DocumentoRelacionado r : doc.getDocumentosRelacionados()) {
                Map<String, Object> d = new HashMap<>();
                d.put(KEY_TYPE, txt(r.tipoDocumento()));
                d.put(KEY_DOCUMENT, txt(r.serieNumero()));
                docs.add(d);
            }
            reference.put("documents", docs);
        }
        invoice.put("reference", reference);

        if (doc.getDetalles() != null) {
            List<Map<String, Object>> items = new ArrayList<>();
            int idx = 1;
            for (LineaDetalle linea : doc.getDetalles()) {
                Map<String, Object> item = new HashMap<>();
                item.put("index", idx++);
                item.put("quantity", txt(linea.getCantidad()));
                item.put("unitCode", txt(linea.getUnidadMedida()));
                item.put("description", txt(linea.getDescripcion()));
                item.put("code", txt(linea.getCodigoProducto()));
                item.put("codProducto", txt(linea.getCodigoProducto()));
                item.put("sunatCode", txt(linea.getCodigoProductoSunat()));
                item.put("codProductoSUNAT", txt(linea.getCodigoProductoSunat()));
                item.put("gs1Code", txt(linea.getCodigoProductoGS1()));
                item.put(KEY_VALUE, txt(linea.getPrecio()));
                item.put("price", txt(linea.getPrecio()));
                item.put("mtoValorUnitario", txt(linea.getPrecio()));
                item.put("mtoValorVenta", txt(linea.getCantidad() != null && linea.getPrecio() != null
                        ? linea.getCantidad().multiply(linea.getPrecio()) : linea.getPrecio()));
                item.put("referencePrice", txt(linea.getPrecioReferencia()));
                item.put("referencePriceType", txt(linea.getPrecioReferenciaTipo()));
                item.put("igvType", txt(linea.getIgvTipo()));
                item.put("igv", txt(linea.getIgv()));
                item.put("isc", txt(linea.getIsc()));
                item.put("icb", txt(linea.getIcb()));
                item.put("taxTotal", txt(linea.getTotalImpuestos()));
                items.add(item);
            }
            invoice.put("items", items);
            invoice.put("details", items);
        }
        invoice.putIfAbsent("details", List.of());

        Map<String, Object> summary = summaryMap(doc.getTotalImporte(), doc.getTotalImpuestos(), doc.getLeyendas());
        invoice.put("summary", summary);
        invoice.put("mtoImpVenta", summary.getOrDefault("total", ""));
        invoice.put("mtoIGV", summary.getOrDefault("igv", ""));
        invoice.put("mtoOperGravadas", summary.getOrDefault("taxable", ""));
        invoice.put("legends", legends(doc.getLeyendas()));
        applyTemplateAttributes(invoice, contexto.atributosPlantilla());

        Map<String, Object> scope = new HashMap<>();
        scope.put("invoice", invoice);
        scope.put("doc", invoice);
        scope.put("cl", invoice.get("cl"));
        scope.put("moneda", txt(doc.getMoneda()));

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando factura HTML: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> summaryMap(TotalImporte totalImp, TotalImpuestos totalTax, Map<String, String> leyendas) {
        Map<String, Object> summary = new HashMap<>();
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
            summary.put("free", txt(totalTax.gratuitoBaseImponible()));
            summary.put("exportation", txt(totalTax.exportacionBaseImponible()));
            summary.put("ivap", txt(totalTax.ivapImporte()));
            summary.put("icb", txt(totalTax.icbImporte()));
            summary.put("isc", txt(totalTax.iscImporte()));

            // Alias retrocompatibles para tickets.
            summary.put("taxableFre", txt(totalTax.inafectoBaseImponible()));
            summary.put("taxableVat", txt(totalTax.exoneradoBaseImponible()));
        }
        if (leyendas != null && leyendas.containsKey(LEGEND_TOTAL_TEXT)) {
            summary.put("totalText", leyendas.get(LEGEND_TOTAL_TEXT));
        } else {
            summary.put("totalText", "");
        }
        return summary;
    }

    private Map<String, Object> mapDiscount(Descuento d) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, txt(d.tipo()));
        map.put(KEY_AMOUNT, txt(d.monto()));
        map.put("factor", txt(d.factor()));
        map.put("base", txt(d.montoBase()));
        return map;
    }

    private Map<String, Object> mapCharge(CargoDescuento c) {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, txt(c.tipo()));
        map.put(KEY_AMOUNT, txt(c.monto()));
        map.put("factor", txt(c.porcentaje()));
        map.put(KEY_DOCUMENT, txt(c.serieNumero()));
        return map;
    }

    private String mapearNombreDocumento(String tipo) {
        if ("03".equals(tipo)) return "BOLETA DE VENTA ELECTRONICA";
        return "FACTURA ELECTRONICA";
    }

    private String mapearTipoOperacion(String tipo) {
        return switch (txt(tipo)) {
            case "0101" -> "Venta interna";
            case "1001" -> "Detracción";
            case "2001" -> "Percepción";
            case "1002" -> "Anticipo";
            default -> "";
        };
    }

    private List<Map<String, Object>> legends(Map<String, String> leyendas) {
        List<Map<String, Object>> values = new ArrayList<>();
        if (leyendas == null || leyendas.isEmpty()) return values;
        leyendas.forEach((code, value) -> {
            if (LEGEND_TOTAL_TEXT.equals(code)) return;
            Map<String, Object> item = new HashMap<>();
            item.put("code", txt(code));
            item.put("value", txt(value));
            values.add(item);
        });
        return values;
    }

    private void applyTemplateAttributes(Map<String, Object> invoice, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        invoice.put("header", txt(attrs.get("header")));
        invoice.put("footer", txt(attrs.get("footer")));
        Object extras = attrs.get(KEY_EXTRAS);
        if (extras instanceof List<?> list) {
            invoice.put(KEY_EXTRAS, list);
        } else {
            invoice.put(KEY_EXTRAS, List.of());
        }
    }

    private Map<String, Object> contactMap(Contacto contacto) {
        Map<String, Object> c = new HashMap<>();
        c.put(KEY_NAME, txt(contacto != null ? contacto.nombre() : ""));
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

    private LocalDateTime fechaEmision(java.time.LocalDate fecha, java.time.LocalTime hora) {
        return LocalDateTime.of(fecha != null ? fecha : java.time.LocalDate.now(),
                hora != null ? hora : LocalTime.MIDNIGHT);
    }
}
