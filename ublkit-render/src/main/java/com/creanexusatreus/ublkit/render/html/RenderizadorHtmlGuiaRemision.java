package com.creanexusatreus.ublkit.render.html;

import com.creanexusatreus.ublkit.render.api.RenderizadorDocumento;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.modelo.PlantillaRutas;
import com.creanexusatreus.ublkit.render.modelo.ResultadoRender;
import com.creanexusatreus.ublkit.render.pebble.FuentesEmbebidas;
import com.creanexusatreus.ublkit.render.pebble.PebbleEngines;
import com.creanexusatreus.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.creanexusatreus.ublkit.ubl.modelo.guia.TerceroGuia;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Renderizador de guías de remisión en formato HTML usando Pebble.
 * Permite usar tanto plantillas .html como .html.twig limpiamente.
 *
 * @since 0.3.0
 */
public class RenderizadorHtmlGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {

    private static final Map<String, String> TRANSPORT_INDICATOR_LABELS = transportIndicatorLabels();
    private static final String FREIGHT_PAYER_PREFIX = "Pagador del flete: ";

    private final FormatoImpresion formato;
    private final PebbleEngine engine;

    public RenderizadorHtmlGuiaRemision() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlGuiaRemision(FormatoImpresion formato) {
        this.formato = formato;
        this.engine = PebbleEngines.crear();
    }

    private String obtenerRutaPlantilla(ContextoRender<BorradorGuiaRemision> contexto) {
        // La GRE transportista (tipo 31) tiene plantilla propia solo en A4; A5 y tickets
        // siguen resolviendo "despatch" (sus plantillas adaptan el contenido vía datos).
        boolean esTransportista = formato == FormatoImpresion.A4
                && contexto.documento().isGRETransportista();
        return PlantillaRutas.ruta(
            esTransportista ? "despatch-carrier" : "despatch",
            formato,
            PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT),
            contexto.extensionPlantilla()
        );
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        Map<String, Object> scope = new HashMap<>();
        scope.put("doc", contexto.documento());
        scope.put("qrBase64", contexto.qrBase64());
        scope.put("hashDocumento", contexto.hashDocumento());
        scope.put("fontStyle", PlantillaRutas.esTicket(formato)
                ? FuentesEmbebidas.cssTicketGenerico()
                : FuentesEmbebidas.cssParaEstilo(
                        PlantillaRutas.resolver(contexto.estiloPlantilla(), EstiloPlantilla.DEFAULT)));

        if (contexto.atributosPlantilla() != null) {
            scope.put("params", contexto.atributosPlantilla());
            scope.putAll(contexto.atributosPlantilla());
        }
        scope.put("indicadoresTraslado",
                transportIndicatorLabels(contexto.documento(), contexto.atributosPlantilla()));

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla(contexto));
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando guía HTML: " + e.getMessage(), e);
        }
    }

    private List<String> transportIndicatorLabels(BorradorGuiaRemision document, Map<String, Object> attributes) {
        List<String> labels = new ArrayList<>();
        String payerType = stringAttribute(attributes, "tipoPagadorFlete");
        TerceroGuia thirdPartyPayer = document.getPagadorFleteTercero();
        if (document.getEnvio() != null && document.getEnvio().getIndicadores() != null) {
            document.getEnvio().getIndicadores().stream()
                    .filter(indicator -> indicator != null && !indicator.isBlank())
                    .filter(indicator -> payerType == null || !isFreightPayerIndicator(indicator))
                    .map(indicator -> resolveIndicatorLabel(indicator, thirdPartyPayer))
                    .forEach(labels::add);
        }
        if (payerType != null) {
            labels.add(resolveFreightPayerLabel(payerType, attributes, thirdPartyPayer));
        }
        return labels.stream().distinct().toList();
    }

    private String resolveIndicatorLabel(String indicator, TerceroGuia thirdPartyPayer) {
        if ("SUNAT_Envio_IndicadorPagadorFlete_Tercero".equals(indicator)) {
            return resolveFreightPayerLabel("Tercero", null, thirdPartyPayer);
        }
        return TRANSPORT_INDICATOR_LABELS.getOrDefault(indicator, indicator);
    }

    private String resolveFreightPayerLabel(String payerType, Map<String, Object> attributes, TerceroGuia thirdPartyPayer) {
        String normalizedType = payerType == null ? null : payerType.trim();
        if (normalizedType == null || normalizedType.isEmpty()) {
            return "";
        }

        String baseLabel = switch (normalizedType.toLowerCase(Locale.ROOT)) {
            case "remitente" -> FREIGHT_PAYER_PREFIX + "remitente";
            case "subcontratador" -> FREIGHT_PAYER_PREFIX + "contratante SUNAT";
            case "tercero" -> FREIGHT_PAYER_PREFIX + "tercero";
            default -> FREIGHT_PAYER_PREFIX + normalizedType;
        };

        if (!"tercero".equalsIgnoreCase(normalizedType)) {
            return baseLabel;
        }

        String detailName = stringAttribute(attributes, "pagadorFleteTerceroNombre");
        String detailDocument = stringAttribute(attributes, "pagadorFleteTerceroDocumento");
        if (detailName == null && thirdPartyPayer != null) {
            detailName = thirdPartyPayer.nombre();
        }
        if (detailDocument == null && thirdPartyPayer != null) {
            detailDocument = thirdPartyPayer.numeroDocumentoIdentidad();
        }
        if (detailName == null) {
            return baseLabel;
        }
        return baseLabel + " · " + detailName
                + (detailDocument != null && !detailDocument.isBlank() ? " (" + detailDocument + ")" : "");
    }

    private boolean isFreightPayerIndicator(String indicator) {
        return indicator != null && indicator.startsWith("SUNAT_Envio_IndicadorPagadorFlete_");
    }

    private String stringAttribute(Map<String, Object> attributes, String key) {
        if (attributes == null || attributes.get(key) == null) {
            return null;
        }
        String value = attributes.get(key).toString().trim();
        return value.isEmpty() ? null : value;
    }

    private static Map<String, String> transportIndicatorLabels() {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put("SUNAT_Envio_IndicadorRetornoVehiculoVacio", "Retorno de vehículo vacío");
        labels.put("SUNAT_Envio_IndicadorRetornoVehiculoEnvaseVacio",
                "Retorno de envases o embalajes vacíos");
        labels.put("SUNAT_Envio_IndicadorTransbordoProgramado", "Transbordo programado");
        labels.put("SUNAT_Envio_IndicadorTransbordo", "Transbordo");
        labels.put("SUNAT_Envio_IndicadorTrasladoTotal", "Traslado total");
        labels.put("SUNAT_Envio_IndicadorTrasladoTotalDAMoDS", "Traslado total DAM/DS");
        labels.put("SUNAT_Envio_IndicadorTrasladoVehiculoM1L", "Vehículo categoría M1/L");
        labels.put("SUNAT_Envio_IndicadorVehiculoConductoresTransp",
                "Vehículo y conductores de transportista");
        labels.put("SUNAT_Envio_IndicadorTrasporteSubcontratado", "Transporte subcontratado");
        labels.put("SUNAT_Envio_IndicadorPagadorFlete_Remitente", FREIGHT_PAYER_PREFIX + "remitente");
        labels.put("SUNAT_Envio_IndicadorPagadorFlete_Subcontratador",
                FREIGHT_PAYER_PREFIX + "contratante SUNAT");
        labels.put("SUNAT_Envio_IndicadorPagadorFlete_Tercero", FREIGHT_PAYER_PREFIX + "tercero");
        return Map.copyOf(labels);
    }
}
