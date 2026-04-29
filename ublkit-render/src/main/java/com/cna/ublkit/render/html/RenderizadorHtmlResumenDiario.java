package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.PlantillaRutas;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pebble.PebbleEngines;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ComprobanteResumen;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renderizador HTML para Resumen Diario.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlResumenDiario implements RenderizadorDocumento<ResumenDiario> {

    private final io.pebbletemplates.pebble.PebbleEngine engine = PebbleEngines.crear();

    private String obtenerRutaPlantilla(ContextoRender<ResumenDiario> contexto) {
        return PlantillaRutas.ruta("summary", FormatoImpresion.A4,
                PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT));
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<ResumenDiario> contexto) {
        ResumenDiario resumen = contexto.documento();
        LocalDateTime fechaEmision = fechaEmision(resumen.getFechaEmision());

        Map<String, Object> rc = new HashMap<>();
        rc.put("id", "RC-" + (resumen.getFechaEmision() != null ? resumen.getFechaEmision().toString().replace("-", "") : "00000000") + "-" + (resumen.getNumero() != null ? resumen.getNumero() : 1));
        rc.put("issueDate", fechaEmision);
        rc.put("referenceDate", resumen.getFechaEmisionComprobantes());
        rc.put("fechaEmision", fechaEmision);
        rc.put("currency", txt(resumen.getMoneda()));
        rc.put("hash", txt(contexto.hashDocumento()));
        rc.put("qr", txt(contexto.qrBase64()));

        if (resumen.getEmisor() != null) {
            Map<String, Object> issuer = new HashMap<>();
            issuer.put("ruc", txt(resumen.getEmisor().ruc()));
            issuer.put("name", txt(resumen.getEmisor().razonSocial()));
            issuer.put("tradeName", txt(resumen.getEmisor().nombreComercial()));
            issuer.put("razonSocial", txt(resumen.getEmisor().razonSocial()));
            Map<String, Object> address = new HashMap<>();
            address.put("direccion", "");
            issuer.put("address", address);
            rc.put("issuer", issuer);
            rc.put("company", issuer);
        }
        if (resumen.getFirmante() != null) {
            Map<String, Object> signer = new HashMap<>();
            signer.put("ruc", txt(resumen.getFirmante().ruc()));
            signer.put("name", txt(resumen.getFirmante().razonSocial()));
            rc.put("signer", signer);
        }

        List<Map<String, Object>> lines = new ArrayList<>();
        if (resumen.getComprobantes() != null) {
            int index = 1;
            for (ItemResumenDiario item : resumen.getComprobantes()) {
                Map<String, Object> line = new HashMap<>();
                line.put("index", index++);
                line.put("operationType", txt(item.tipoOperacion()));

                ComprobanteResumen c = item.comprobante();
                if (c != null) {
                    line.put("documentType", txt(c.getTipoComprobante()));
                    line.put("document", txt(c.getSerieNumero()));
                    line.put("tipoDoc", txt(c.getTipoComprobante()));
                    line.put("serieNro", txt(c.getSerieNumero()));
                    line.put("estado", txt(item.tipoOperacion()));
                    line.put("currency", txt(c.getMoneda()));

                    if (c.getCliente() != null) {
                        line.put("customerDocType", txt(c.getCliente().tipoDocIdentidad()));
                        line.put("customerDoc", txt(c.getCliente().numDocIdentidad()));
                        line.put("customerName", txt(c.getCliente().nombre()));
                    }

                    if (c.getValorVenta() != null) {
                        line.put("total", txt(c.getValorVenta().importeTotal()));
                        line.put("mtoOperGravadas", txt(c.getValorVenta().gravado()));
                        line.put("gravado", txt(c.getValorVenta().gravado()));
                        line.put("exonerado", txt(c.getValorVenta().exonerado()));
                        line.put("inafecto", txt(c.getValorVenta().inafecto()));
                        line.put("gratuito", txt(c.getValorVenta().gratuito()));
                    }

                    if (c.getImpuestos() != null) {
                        line.put("igv", txt(c.getImpuestos().igv()));
                        line.put("mtoIGV", txt(c.getImpuestos().igv()));
                        line.put("isc", txt(c.getImpuestos().isc()));
                        line.put("icb", txt(c.getImpuestos().icb()));
                        line.put("ivap", txt(c.getImpuestos().ivap()));
                        line.put("otros", txt(c.getImpuestos().otros()));
                    }

                    if (c.getPercepcion() != null) {
                        line.put("perceptionType", txt(c.getPercepcion().codReg()));
                        line.put("perceptionAmount", txt(c.getPercepcion().mto()));
                        line.put("perceptionTotal", txt(c.getPercepcion().mtoTotal()));
                    }

                    if (c.getComprobanteAfectado() != null) {
                        line.put("affectedType", txt(c.getComprobanteAfectado().tipoComprobante()));
                        line.put("affectedDocument", txt(c.getComprobanteAfectado().serieNumero()));
                    }
                }

                lines.add(line);
            }
        }
        rc.put("lines", lines);
        rc.put("details", lines);
        applyTemplateAttributes(rc, contexto.atributosPlantilla());

        Map<String, Object> scope = new HashMap<>();
        scope.put("summary", rc);
        scope.put("doc", rc);
        scope.put("cl", firstCustomer(lines, resumen));
        scope.put("moneda", txt(resumen.getMoneda()));

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando resumen diario HTML: " + e.getMessage(), e);
        }
    }

    private String txt(Object value) {
        if (value == null) return "";
        if (value instanceof BigDecimal b) return b.stripTrailingZeros().toPlainString();
        return value.toString();
    }

    private LocalDateTime fechaEmision(java.time.LocalDate fecha) {
        return LocalDateTime.of(fecha != null ? fecha : java.time.LocalDate.now(), java.time.LocalTime.MIDNIGHT);
    }

    private void applyTemplateAttributes(Map<String, Object> summary, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        summary.put("header", txt(attrs.get("header")));
        summary.put("footer", txt(attrs.get("footer")));
    }

    private Map<String, Object> firstCustomer(List<Map<String, Object>> lines, ResumenDiario resumen) {
        if (lines != null) {
            for (Map<String, Object> line : lines) {
                Object name = line.get("customerName");
                Object doc = line.get("customerDoc");
                if (name != null || doc != null) {
                    Map<String, Object> customer = new HashMap<>();
                    customer.put("rznSocial", txt(name));
                    customer.put("numDoc", txt(doc));
                    customer.put("name", txt(name));
                    customer.put("identity", txt(doc));
                    Map<String, Object> address = new HashMap<>();
                    address.put("direccion", "");
                    customer.put("address", address);
                    return customer;
                }
            }
        }
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("rznSocial", resumen.getEmisor() != null ? txt(resumen.getEmisor().razonSocial()) : "");
        fallback.put("numDoc", resumen.getEmisor() != null ? txt(resumen.getEmisor().ruc()) : "");
        fallback.put("name", resumen.getEmisor() != null ? txt(resumen.getEmisor().razonSocial()) : "");
        fallback.put("identity", resumen.getEmisor() != null ? txt(resumen.getEmisor().ruc()) : "");
        Map<String, Object> address = new HashMap<>();
        address.put("direccion", "");
        fallback.put("address", address);
        return fallback;
    }
}
