package com.cna.ublkit.render.html;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.complemento.Anticipo;
import com.cna.ublkit.ubl.modelo.complemento.CuotaDePago;
import com.cna.ublkit.ubl.modelo.complemento.Descuento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
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
import java.util.stream.Collectors;

/**
 * Convierte un BorradorFactura (o boleta) en HTML usando plantillas Pebble.
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
        invoice.put("type", parseInt(doc.getTipoComprobante(), 1));
        invoice.put("typedIdentity", txt(doc.getTipoComprobante()));
        invoice.put("identity", txt(doc.getSerie()) + "-" + (doc.getNumero() != null ? doc.getNumero() : ""));
        invoice.put("name", mapearNombreDocumento(doc.getTipoComprobante()));
        invoice.put("issueDate", txt(doc.getFechaEmision()));
        invoice.put("issueTime", txt(doc.getHoraEmision()));
        invoice.put("dueDate", txt(doc.getFechaVencimiento()));
        invoice.put("currency", txt(doc.getMoneda()));
        invoice.put("operationTypeCode", txt(doc.getTipoOperacion()));
        invoice.put("operationTypeName", mapearTipoOperacion(doc.getTipoOperacion()));
        invoice.put("note", txt(doc.getObservaciones()));
        invoice.put("orderReference", txt(doc.getOrdenDeCompra()));
        invoice.put("hash", txt(contexto.hashDocumento()));
        invoice.put("qr", txt(contexto.qrBase64()));
        invoice.put("logo", "logo.jpg");

        if (doc.getTipoCambio() != null) {
            Map<String, Object> exchange = new HashMap<>();
            exchange.put("date", txt(doc.getTipoCambio().fecha()));
            exchange.put("value", txt(doc.getTipoCambio().valor()));
            invoice.put("exchangeRate", exchange);
        }

        if (doc.getEmisor() != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put("identity", txt(doc.getEmisor().ruc()));
            taxpayer.put("name", txt(doc.getEmisor().razonSocial()));
            taxpayer.put("tradeName", txt(doc.getEmisor().nombreComercial()));
            taxpayer.put("address", txt(doc.getEmisor().direccion() != null ? doc.getEmisor().direccion().direccion() : ""));
            taxpayer.put("location", location(doc.getEmisor().direccion()));
            taxpayer.put("contact", contactMap(doc.getEmisor().contacto()));
            invoice.put("taxpayer", taxpayer);
        }

        if (doc.getReceptor() != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("documentType", txt(doc.getReceptor().tipoDocIdentidad()));
            customer.put("identity", txt(doc.getReceptor().numDocIdentidad()));
            customer.put("name", txt(doc.getReceptor().nombre()));
            customer.put("address", txt(doc.getReceptor().direccion() != null ? doc.getReceptor().direccion().direccion() : ""));
            customer.put("location", location(doc.getReceptor().direccion()));
            customer.put("contact", contactMap(doc.getReceptor().contacto()));
            invoice.put("customer", customer);
        }

        if (doc.getFormaDePago() != null) {
            Map<String, Object> payment = new HashMap<>();
            payment.put("type", txt(doc.getFormaDePago().tipo()));
            payment.put("total", txt(doc.getFormaDePago().total()));
            List<Map<String, Object>> installments = new ArrayList<>();
            if (doc.getFormaDePago().cuotas() != null) {
                for (CuotaDePago cuota : doc.getFormaDePago().cuotas()) {
                    Map<String, Object> c = new HashMap<>();
                    c.put("amount", txt(cuota.importe()));
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
            perception.put("type", txt(doc.getPercepcion().tipo()));
            perception.put("base", txt(doc.getPercepcion().montoBase()));
            perception.put("percent", txt(doc.getPercepcion().porcentaje()));
            perception.put("amount", txt(doc.getPercepcion().monto()));
            perception.put("total", txt(doc.getPercepcion().montoTotal()));
            invoice.put("perception", perception);
        }

        if (doc.getAnticipos() != null && !doc.getAnticipos().isEmpty()) {
            List<Map<String, Object>> advances = new ArrayList<>();
            for (Anticipo a : doc.getAnticipos()) {
                Map<String, Object> map = new HashMap<>();
                map.put("type", txt(a.tipo()));
                map.put("documentType", txt(a.comprobanteTipo()));
                map.put("document", txt(a.comprobanteSerieNumero()));
                map.put("amount", txt(a.monto()));
                advances.add(map);
            }
            invoice.put("advances", advances);
        }

        if (doc.getDescuentos() != null && !doc.getDescuentos().isEmpty()) {
            List<Map<String, Object>> discounts = doc.getDescuentos().stream().map(this::mapDiscount).collect(Collectors.toList());
            invoice.put("discounts", discounts);
        }

        Map<String, Object> reference = new HashMap<>();
        reference.put("order", txt(doc.getOrdenDeCompra()));
        reference.put("note", txt(doc.getObservaciones()));
        if (doc.getGuias() != null && !doc.getGuias().isEmpty()) {
            List<Map<String, Object>> guides = new ArrayList<>();
            for (GuiaRelacionada guia : doc.getGuias()) {
                Map<String, Object> g = new HashMap<>();
                g.put("type", txt(guia.tipoDocumento()));
                g.put("document", txt(guia.serieNumero()));
                guides.add(g);
            }
            reference.put("guides", guides);
        }
        if (doc.getDocumentosRelacionados() != null && !doc.getDocumentosRelacionados().isEmpty()) {
            List<Map<String, Object>> docs = new ArrayList<>();
            for (DocumentoRelacionado r : doc.getDocumentosRelacionados()) {
                Map<String, Object> d = new HashMap<>();
                d.put("type", txt(r.tipoDocumento()));
                d.put("document", txt(r.serieNumero()));
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
                item.put("sunatCode", txt(linea.getCodigoProductoSunat()));
                item.put("gs1Code", txt(linea.getCodigoProductoGS1()));
                item.put("value", txt(linea.getPrecio()));
                item.put("price", txt(linea.getPrecio()));
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
        }

        Map<String, Object> summary = summaryMap(doc.getTotalImporte(), doc.getTotalImpuestos(), doc.getLeyendas());
        invoice.put("summary", summary);

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
        if (leyendas != null && leyendas.containsKey("1000")) {
            summary.put("totalText", leyendas.get("1000"));
        } else {
            summary.put("totalText", "");
        }
        return summary;
    }

    private Map<String, Object> mapDiscount(Descuento d) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", txt(d.tipo()));
        map.put("amount", txt(d.monto()));
        map.put("factor", txt(d.factor()));
        map.put("base", txt(d.montoBase()));
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
