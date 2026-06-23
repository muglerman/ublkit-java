package com.creanexusatreus.ublkit.render.html;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.PlantillaRutas;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pebble.FuentesEmbebidas;
import com.creanexusatreus.ublkit.render.pebble.PebbleEngines;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
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
        // La boleta (tipo 03) tiene plantilla propia solo en A4; A5 y tickets siguen
        // resolviendo "invoice" (sus plantillas adaptan el rótulo vía doc.tipoComprobante).
        boolean esBoleta = formato == FormatoImpresion.A4
                && "03".equals(contexto.documento().getTipoComprobante());
        return PlantillaRutas.ruta(
            esBoleta ? "boleta" : "invoice",
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

        // Fuentes embebidas (base64): el ticket usa la monoespaciada genérica (sin estilo);
        // A4/A5 usan las del estilo. Render offline sin CDN.
        scope.put("fontStyle", PlantillaRutas.esTicket(formato)
                ? FuentesEmbebidas.cssTicketGenerico()
                : FuentesEmbebidas.cssParaEstilo(
                        PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT)));

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
