package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.PlantillaRutas;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pebble.PebbleEngines;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Renderizador de facturas en formato HTML usando Pebble.
 * Permite usar tanto plantillas .html como .html.twig limpiamente.
 *
 * @since 0.3.0
 */
public class RenderizadorHtmlFactura implements RenderizadorDocumento<BorradorFactura> {

    private final FormatoImpresion formato;
    private final PebbleEngine engine;

    public RenderizadorHtmlFactura() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlFactura(FormatoImpresion formato) {
        this.formato = formato;
        this.engine = PebbleEngines.crear();
    }

    private String obtenerRutaPlantilla(ContextoRender<BorradorFactura> contexto) {
        return PlantillaRutas.ruta(
            "invoice", 
            formato, 
            PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT),
            contexto.extensionPlantilla()
        );
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        Map<String, Object> scope = new HashMap<>();
        
        // El documento UBL de dominio directamente a la plantilla
        scope.put("doc", contexto.documento());
        
        // Metadatos de renderizado y firma
        scope.put("qrBase64", contexto.qrBase64());
        scope.put("hashDocumento", contexto.hashDocumento());
        
        // Atributos dinámicos de estilo y configuración
        if (contexto.atributosPlantilla() != null) {
            scope.put("params", contexto.atributosPlantilla());
            
            // Para mantener cierta retrocompatibilidad en algunos casos si se inyectaba directamente
            scope.putAll(contexto.atributosPlantilla());
        }

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando factura HTML: " + e.getMessage(), e);
        }
    }
}
