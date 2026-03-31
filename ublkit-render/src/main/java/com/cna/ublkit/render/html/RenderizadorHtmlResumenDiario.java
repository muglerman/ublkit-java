package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ComprobanteResumen;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
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
 * Renderizador HTML para Resumen Diario.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlResumenDiario implements RenderizadorDocumento<ResumenDiario> {

    private final PebbleEngine engine = new PebbleEngine.Builder().build();

    @Override
    public ResultadoRender renderizar(ContextoRender<ResumenDiario> contexto) {
        ResumenDiario resumen = contexto.documento();

        Map<String, Object> rc = new HashMap<>();
        rc.put("id", "RC-" + (resumen.getFechaEmision() != null ? resumen.getFechaEmision().toString().replace("-", "") : "00000000") + "-" + (resumen.getNumero() != null ? resumen.getNumero() : 1));
        rc.put("issueDate", txt(resumen.getFechaEmision()));
        rc.put("referenceDate", txt(resumen.getFechaEmisionComprobantes()));
        rc.put("currency", txt(resumen.getMoneda()));
        rc.put("hash", txt(contexto.hashDocumento()));
        rc.put("qr", txt(contexto.qrBase64()));

        if (resumen.getEmisor() != null) {
            Map<String, Object> issuer = new HashMap<>();
            issuer.put("ruc", txt(resumen.getEmisor().ruc()));
            issuer.put("name", txt(resumen.getEmisor().razonSocial()));
            issuer.put("tradeName", txt(resumen.getEmisor().nombreComercial()));
            rc.put("issuer", issuer);
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
                    line.put("currency", txt(c.getMoneda()));

                    if (c.getCliente() != null) {
                        line.put("customerDocType", txt(c.getCliente().tipoDocIdentidad()));
                        line.put("customerDoc", txt(c.getCliente().numDocIdentidad()));
                        line.put("customerName", txt(c.getCliente().nombre()));
                    }

                    if (c.getValorVenta() != null) {
                        line.put("total", txt(c.getValorVenta().importeTotal()));
                        line.put("gravado", txt(c.getValorVenta().gravado()));
                        line.put("exonerado", txt(c.getValorVenta().exonerado()));
                        line.put("inafecto", txt(c.getValorVenta().inafecto()));
                        line.put("gratuito", txt(c.getValorVenta().gratuito()));
                    }

                    if (c.getImpuestos() != null) {
                        line.put("igv", txt(c.getImpuestos().igv()));
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

        Map<String, Object> scope = new HashMap<>();
        scope.put("summary", rc);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate("templates/summary.a4.html");
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
}
