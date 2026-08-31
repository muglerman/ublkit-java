package com.creanexusatreus.ublkit.render.html;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pebble.FuentesEmbebidas;
import com.creanexusatreus.ublkit.render.pebble.PebbleEngines;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.percepcionretencion.BasePercepcionRetencion;

public class RenderizadorHtmlRetencionPercepcion<T extends BasePercepcionRetencion>
        implements RenderizadorDocumento<T> {
    @Override
    public ResultadoRender renderizar(ContextoRender<T> contexto) {
        Map<String, Object> scope = new HashMap<>();
        scope.put("doc", contexto.documento());
        scope.put("fontStyle", FuentesEmbebidas.cssParaEstilo(contexto.estiloPlantilla()));
        scope.put("kind", contexto.documento().getClass().getSimpleName().contains("Retencion")
                ? "COMPROBANTE DE RETENCIÓN ELECTRÓNICA" : "COMPROBANTE DE PERCEPCIÓN ELECTRÓNICA");
        if (contexto.atributosPlantilla() != null) scope.putAll(contexto.atributosPlantilla());
        try {
            var template = PebbleEngines.crear().getTemplate("templates/generico/retention-perception.a4.html.twig");
            var writer = new StringWriter();
            template.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando retención/percepción HTML: " + e.getMessage(), e);
        }
    }
}
