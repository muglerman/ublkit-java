package com.creanexusatreus.ublkit.render.html;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.PlantillaRutas;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pebble.FuentesEmbebidas;
import com.creanexusatreus.ublkit.render.pebble.PebbleEngines;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Renderizador de comunicaciones de baja en formato HTML usando Pebble.
 * Permite usar tanto plantillas .html como .html.twig limpiamente.
 *
 * @since 0.3.0
 */
public class RenderizadorHtmlComunicacionBaja implements RenderizadorDocumento<ComunicacionBaja> {

    private final FormatoImpresion formato;
    private final PebbleEngine engine;

    public RenderizadorHtmlComunicacionBaja() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlComunicacionBaja(FormatoImpresion formato) {
        this.formato = formato;
        this.engine = PebbleEngines.crear();
    }

    private String obtenerRutaPlantilla(ContextoRender<ComunicacionBaja> contexto) {
        return PlantillaRutas.ruta(
            "voided", 
            formato, 
            PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT),
            contexto.extensionPlantilla()
        );
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<ComunicacionBaja> contexto) {
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
            throw new RuntimeException("Error renderizando comunicación de baja HTML: " + e.getMessage(), e);
        }
    }
}
