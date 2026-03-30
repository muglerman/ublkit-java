package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;

import java.util.List;

/**
 * Renderizador HTML para Comunicación de Baja.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlComunicacionBaja implements RenderizadorDocumento<ComunicacionBaja> {

    @Override
    public ResultadoRender renderizar(ContextoRender<ComunicacionBaja> contexto) {
        ComunicacionBaja baja = contexto.documento();
        List<ItemBaja> items = baja.getComprobantes() != null ? baja.getComprobantes() : List.of();
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset=\"UTF-8\"><style>")
                .append("body{font-family:Arial,sans-serif;font-size:12px;color:#222;padding:24px}")
                .append("h1{font-size:18px;margin:0 0 12px 0}table{width:100%;border-collapse:collapse}")
                .append("th,td{border:1px solid #ddd;padding:8px;text-align:left}th{background:#f5f5f5}")
                .append("</style></head><body>")
                .append("<h1>Comunicación de Baja</h1>")
                .append("<p><b>Número:</b> RA-")
                .append(baja.getFechaEmision() != null ? baja.getFechaEmision().toString().replace("-", "") : "00000000")
                .append("-").append(baja.getNumero() != null ? baja.getNumero() : 1).append("</p>")
                .append("<table><thead><tr><th>#</th><th>Tipo</th><th>Serie</th><th>Número</th><th>Motivo</th></tr></thead><tbody>");
        int i = 1;
        for (ItemBaja item : items) {
            html.append("<tr><td>").append(i++).append("</td><td>")
                    .append(item.tipoComprobante() != null ? item.tipoComprobante() : "")
                    .append("</td><td>").append(item.serie() != null ? item.serie() : "")
                    .append("</td><td>").append(item.numero() != null ? item.numero() : "")
                    .append("</td><td>").append(item.descripcionSustento() != null ? item.descripcionSustento() : "")
                    .append("</td></tr>");
        }
        html.append("</tbody></table></body></html>");
        return ResultadoRender.html(html.toString());
    }
}

