package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.BorradorManifiesto;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.PlantillaRutas;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pebble.FuentesEmbebidas;
import com.cna.ublkit.render.pebble.PebbleEngines;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Renderizador del Manifiesto de Carga en HTML usando Pebble. Documento interno: solo A4,
 * sin QR ni hash. El estilo visual se resuelve por {@link EstiloPlantilla}.
 *
 * @since 0.4.0
 */
public class RenderizadorHtmlManifiesto implements RenderizadorDocumento<BorradorManifiesto> {

    private static final FormatoImpresion FORMATO = FormatoImpresion.A4;
    private final PebbleEngine engine;

    public RenderizadorHtmlManifiesto() {
        this.engine = PebbleEngines.crear();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorManifiesto> contexto) {
        EstiloPlantilla estilo = PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT);

        Map<String, Object> scope = new HashMap<>();
        scope.put("doc", contexto.documento());
        scope.put("fontStyle", FuentesEmbebidas.cssParaEstilo(estilo));
        if (contexto.atributosPlantilla() != null) {
            scope.put("params", contexto.atributosPlantilla());
            scope.putAll(contexto.atributosPlantilla());
        }

        String ruta = PlantillaRutas.ruta("manifiesto", FORMATO, estilo, contexto.extensionPlantilla());
        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(ruta);
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando Manifiesto HTML: " + e.getMessage(), e);
        }
    }
}
