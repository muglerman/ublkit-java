package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;

import java.util.List;

/**
 * Renderizador HTML para Resumen Diario.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlResumenDiario implements RenderizadorDocumento<ResumenDiario> {

    @Override
    public ResultadoRender renderizar(ContextoRender<ResumenDiario> contexto) {
        ResumenDiario resumen = contexto.documento();
        List<ItemResumenDiario> items = resumen.getComprobantes() != null ? resumen.getComprobantes() : List.of();
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset=\"UTF-8\"><style>")
                .append("body{font-family:Arial,sans-serif;font-size:12px;color:#222;padding:24px}")
                .append("h1{font-size:18px;margin:0 0 12px 0}table{width:100%;border-collapse:collapse}")
                .append("th,td{border:1px solid #ddd;padding:8px;text-align:left}th{background:#f5f5f5}")
                .append("</style></head><body>")
                .append("<h1>Resumen Diario de Boletas</h1>")
                .append("<p><b>Número:</b> RC-")
                .append(resumen.getFechaEmision() != null ? resumen.getFechaEmision().toString().replace("-", "") : "00000000")
                .append("-").append(resumen.getNumero() != null ? resumen.getNumero() : 1).append("</p>")
                .append("<table><thead><tr><th>#</th><th>Tipo Operación</th><th>Comprobante</th></tr></thead><tbody>");
        int i = 1;
        for (ItemResumenDiario item : items) {
            String comprobante = item.comprobante() != null
                    ? (item.comprobante().getTipoComprobante() != null ? item.comprobante().getTipoComprobante() : "")
                    + " "
                    + (item.comprobante().getSerieNumero() != null ? item.comprobante().getSerieNumero() : "")
                    : "-";
            html.append("<tr><td>").append(i++).append("</td><td>")
                    .append(item.tipoOperacion() != null ? item.tipoOperacion() : "")
                    .append("</td><td>").append(comprobante).append("</td></tr>");
        }
        html.append("</tbody></table></body></html>");
        return ResultadoRender.html(html.toString());
    }
}
