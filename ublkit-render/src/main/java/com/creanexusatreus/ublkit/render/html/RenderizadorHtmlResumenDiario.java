package com.creanexusatreus.ublkit.render.html;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.PlantillaRutas;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pebble.FuentesEmbebidas;
import com.creanexusatreus.ublkit.render.pebble.PebbleEngines;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Renderizador de resúmenes diarios en formato HTML usando Pebble.
 * Permite usar tanto plantillas .html como .html.twig limpiamente.
 *
 * @since 0.3.0
 */
public class RenderizadorHtmlResumenDiario implements RenderizadorDocumento<ResumenDiario> {

    private final FormatoImpresion formato;
    private final PebbleEngine engine;

    public RenderizadorHtmlResumenDiario() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlResumenDiario(FormatoImpresion formato) {
        this.formato = formato;
        this.engine = PebbleEngines.crear();
    }

    private String obtenerRutaPlantilla(ContextoRender<ResumenDiario> contexto) {
        return PlantillaRutas.ruta(
            "summary", 
            formato, 
            PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT),
            contexto.extensionPlantilla()
        );
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<ResumenDiario> contexto) {
        Map<String, Object> scope = new HashMap<>();
        scope.put("doc", contexto.documento());
        scope.put("qrBase64", contexto.qrBase64());
        scope.put("hashDocumento", contexto.hashDocumento());
        scope.put("fontStyle", FuentesEmbebidas.cssParaEstilo(
                PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT)));

        if (contexto.atributosPlantilla() != null) {
            scope.put("params", contexto.atributosPlantilla());
            scope.putAll(contexto.atributosPlantilla());
        }

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando resumen diario HTML: " + e.getMessage(), e);
        }
    }
}
