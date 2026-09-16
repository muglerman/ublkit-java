package com.creanexusatreus.ublkit.render.html;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.support.GreTCarrierTestFixtures;

@DisplayName("RenderizadorHtmlGuiaRemision - semántica GRE-T A4")
class RenderizadorHtmlGuiaRemisionCarrierSemanticsTest {

    @ParameterizedTest(name = "subcontrataciones separadas · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void separaSubcontratacionSunatEInternaConMtcDistintos(EstiloPlantilla estilo) {
        var scenario = GreTCarrierTestFixtures.bothSubcontractingScenario();

        String html = renderizar(estilo, scenario.guia(), scenario.atributos());

        assertTrue(html.contains("Subcontratación SUNAT · Contratante del transporte"));
        assertTrue(html.contains("Subcontratación interna · Proveedor subcontratado"));
        assertTrue(html.contains("MTC-SUNAT-221"));
        assertTrue(html.contains("MTC-INTERNO-778"));
        assertTrue(html.contains("class=\"subcontract-fields\""));
        assertEquals(2, contar(html, "class=\"subcontract-fields\""),
                "El estilo " + estilo.carpeta() + " debe renderizar ambos bloques sin cruzarlos");
    }

    @ParameterizedTest(name = "pagador tercero único · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void conservaPagadorTerceroEnUnSoloBadgeConDetalle(EstiloPlantilla estilo) {
        var scenario = GreTCarrierTestFixtures.thirdPartyPayerScenario();

        String html = renderizar(estilo, scenario.guia(), scenario.atributos());

        assertTrue(html.contains("Pagador del flete: tercero · Tercero Pagador de Flete S.A.C. (20100099988)"));
        assertEquals(1, contar(html, "Pagador del flete: tercero"),
                "El estilo " + estilo.carpeta() + " no debe duplicar el pagador tercero");
        assertFalse(html.contains("Pagador del servicio de transporte"),
                "El estilo " + estilo.carpeta() + " debe usar la terminología nueva e inequívoca");
    }

    @ParameterizedTest(name = "sellos canónicos · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void renderizaSellosCanonicosDeEstadoGreT(EstiloPlantilla estilo) {
        var baseGuide = GreTCarrierTestFixtures.baseCarrierGuide();
        Map<String, Object> attrs = GreTCarrierTestFixtures.baseCarrierAttributes();

        String pendingHtml = renderizar(estilo, baseGuide, attrs);
        String validHtml = renderizar(estilo, baseGuide, replace(attrs, "estadoGreT", "GRE-T VÁLIDA"));
        String rejectedHtml = renderizar(estilo, baseGuide, replace(attrs, "estadoGreT", "GRE-T RECHAZADA"));

        assertTrue(pendingHtml.contains("GRE-T PENDIENTE"));
        assertTrue(validHtml.contains("GRE-T VÁLIDA"));
        assertTrue(rejectedHtml.contains("GRE-T RECHAZADA"));
        assertFalse(pendingHtml.contains("EMITIDA · PENDIENTE DE VALIDACIÓN SUNAT"));
    }

    private String renderizar(EstiloPlantilla estilo,
            com.creanexusatreus.ublkit.ubl.modelo.guia.BorradorGuiaRemision guia,
            Map<String, Object> atributos) {
        return new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4)
                .renderizar(ContextoRender.of(
                        guia,
                        GreTCarrierTestFixtures.HASH,
                        GreTCarrierTestFixtures.QR_BASE64,
                        atributos,
                        estilo))
                .contenidoHtml();
    }

    private Map<String, Object> replace(Map<String, Object> source, String key, String value) {
        var mutable = new java.util.LinkedHashMap<>(source);
        mutable.put(key, value);
        return Map.copyOf(mutable);
    }

    private int contar(String texto, String fragmento) {
        return (texto.length() - texto.replace(fragmento, "").length()) / fragmento.length();
    }
}
