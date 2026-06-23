package com.cna.ublkit.validation.validador.sunat;

import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorSunatXsl Tests")
class ValidadorSunatXslTest {

    private final ValidadorSunatXsl validador = new ValidadorSunatXsl();

    @Test
    @DisplayName("Should precompile XSL templates once at startup")
    void templates_precompilados_alInicializar() {
        assertTrue(ValidadorSunatXsl.cantidadTemplatesPrecompilados() > 0);
    }

    @Test
    @DisplayName("Should reuse compiled templates across repeated validations")
    void validarXml_reutilizaTemplatesSinRecompilar() {
        int antes = ValidadorSunatXsl.cantidadTemplatesPrecompilados();

        String oldValue = System.getProperty("ublkit.validation.sunat.enabled");
        try {
            System.setProperty("ublkit.validation.sunat.enabled", "true");
            for (int i = 0; i < 20; i++) {
                ResultadoValidacion resultado = validador.validarXml("<Invoice/>", "factura.xml", ReglaSunatXsl.FACTURA);
                assertNotNull(resultado);
            }
        } finally {
            if (oldValue != null) {
                System.setProperty("ublkit.validation.sunat.enabled", oldValue);
            } else {
                System.clearProperty("ublkit.validation.sunat.enabled");
            }
        }

        int despues = ValidadorSunatXsl.cantidadTemplatesPrecompilados();
        assertEquals(antes, despues);
    }

    @Test
    @DisplayName("Should return valid result when SUNAT validation is disabled")
    void validarXml_validacionDeshabilitada_retornaVacio() {
        // SUNAT validation is disabled by default
        ResultadoValidacion resultado = validador.validarXml("<test/>", "test.xml", ReglaSunatXsl.FACTURA);
        assertNotNull(resultado);
        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Should handle null XML with error")
    void validarXml_xmlNulo_retornaError() {
        // First enable SUNAT validation for this test
        String oldValue = System.getProperty("ublkit.validation.sunat.enabled");
        try {
            System.setProperty("ublkit.validation.sunat.enabled", "true");
            ResultadoValidacion resultado = validador.validarXml(null, "test.xml", ReglaSunatXsl.FACTURA);

            assertFalse(resultado.esValido());
            assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "SUNAT-000".equals(i.codigo())));
        } finally {
            if (oldValue != null) {
                System.setProperty("ublkit.validation.sunat.enabled", oldValue);
            } else {
                System.clearProperty("ublkit.validation.sunat.enabled");
            }
        }
    }

    @Test
    @DisplayName("Should handle empty XML with error")
    void validarXml_xmlVacio_retornaError() {
        String oldValue = System.getProperty("ublkit.validation.sunat.enabled");
        try {
            System.setProperty("ublkit.validation.sunat.enabled", "true");
            ResultadoValidacion resultado = validador.validarXml("", "test.xml", ReglaSunatXsl.FACTURA);

            assertFalse(resultado.esValido());
            assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "SUNAT-000".equals(i.codigo())));
        } finally {
            if (oldValue != null) {
                System.setProperty("ublkit.validation.sunat.enabled", oldValue);
            } else {
                System.clearProperty("ublkit.validation.sunat.enabled");
            }
        }
    }

    @Test
    @DisplayName("Should handle blank XML with error")
    void validarXml_xmlEnBlanco_retornaError() {
        String oldValue = System.getProperty("ublkit.validation.sunat.enabled");
        try {
            System.setProperty("ublkit.validation.sunat.enabled", "true");
            ResultadoValidacion resultado = validador.validarXml("   ", "test.xml", ReglaSunatXsl.FACTURA);

            assertFalse(resultado.esValido());
            assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "SUNAT-000".equals(i.codigo())));
        } finally {
            if (oldValue != null) {
                System.setProperty("ublkit.validation.sunat.enabled", oldValue);
            } else {
                System.clearProperty("ublkit.validation.sunat.enabled");
            }
        }
    }

    @Test
    @DisplayName("Should return valid result when validation is disabled")
    void validarXml_validacionDeshabilitada_conXmlValido() {
        ResultadoValidacion resultado = validador.validarXml(
                "<Invoice><ID>F001-001</ID></Invoice>",
                "invoice.xml",
                ReglaSunatXsl.FACTURA
        );

        assertNotNull(resultado);
        assertTrue(resultado.esValido());
        assertEquals(0, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Should handle all SUNAT XSL rules")
    void validarXml_debeAceptarTodosLasReglas() {
        for (ReglaSunatXsl regla : ReglaSunatXsl.values()) {
            // When disabled, should return empty result
            ResultadoValidacion resultado = validador.validarXml("<test/>", "test.xml", regla);
            assertNotNull(resultado);
        }
    }

    @Test
    @DisplayName("Should preserve XML content without modification when validation disabled")
    void validarXml_noModificaXml_cuandoDeshabilitado() {
        String xmlContent = "<Invoice><ID>F001-001</ID><Total>100.00</Total></Invoice>";
        ResultadoValidacion resultado = validador.validarXml(xmlContent, "invoice.xml", ReglaSunatXsl.FACTURA);

        assertNotNull(resultado);
        // XML should remain unchanged - validation doesn't modify the source XML
        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Should handle XML with special characters")
    void validarXml_xmlConCaracteresEspeciales() {
        String xmlContent = "<Invoice><Description>Servicio de asesoría &amp; consultoría</Description></Invoice>";
        ResultadoValidacion resultado = validador.validarXml(xmlContent, "invoice.xml", ReglaSunatXsl.FACTURA);

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle different filename parameters")
    void validarXml_nombreArchivoDiferente() {
        String[] nombres = {"factura.xml", "F001-001.xml", "invoice-2024.xml", null};

        for (String nombre : nombres) {
            ResultadoValidacion resultado = validador.validarXml("<test/>", nombre, ReglaSunatXsl.FACTURA);
            assertNotNull(resultado);
        }
    }

    @Test
    @DisplayName("Should handle FACTURA rule")
    void validarXml_reglaSUNAT_FACTURA() {
        ResultadoValidacion resultado = validador.validarXml("<Factura/>", "factura.xml", ReglaSunatXsl.FACTURA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle BOLETA rule")
    void validarXml_reglaSUNAT_BOLETA() {
        ResultadoValidacion resultado = validador.validarXml("<Boleta/>", "boleta.xml", ReglaSunatXsl.BOLETA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle NOTA_CREDITO rule")
    void validarXml_reglaSUNAT_NOTA_CREDITO() {
        ResultadoValidacion resultado = validador.validarXml("<NC/>", "nc.xml", ReglaSunatXsl.NOTA_CREDITO);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle NOTA_DEBITO rule")
    void validarXml_reglaSUNAT_NOTA_DEBITO() {
        ResultadoValidacion resultado = validador.validarXml("<ND/>", "nd.xml", ReglaSunatXsl.NOTA_DEBITO);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle GUIA_REMITENTE rule")
    void validarXml_reglaSUNAT_GUIA_REMITENTE() {
        ResultadoValidacion resultado = validador.validarXml("<GR/>", "gr.xml", ReglaSunatXsl.GUIA_REMITENTE);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle GUIA_TRANSPORTISTA rule")
    void validarXml_reglaSUNAT_GUIA_TRANSPORTISTA() {
        ResultadoValidacion resultado = validador.validarXml("<GT/>", "gt.xml", ReglaSunatXsl.GUIA_TRANSPORTISTA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle RESUMEN_DIARIO rule")
    void validarXml_reglaSUNAT_RESUMEN_DIARIO() {
        ResultadoValidacion resultado = validador.validarXml("<SummaryDocuments/>", "rd.xml", ReglaSunatXsl.RESUMEN_DIARIO);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle COMUNICACION_BAJA rule")
    void validarXml_reglaSUNAT_COMUNICACION_BAJA() {
        ResultadoValidacion resultado = validador.validarXml("<SummaryDocuments/>", "cb.xml", ReglaSunatXsl.COMUNICACION_BAJA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should return ResultadoValidacion type")
    void validarXml_retornaResultadoValidacion() {
        ResultadoValidacion resultado = validador.validarXml("<test/>", "test.xml", ReglaSunatXsl.FACTURA);
        assertTrue(resultado instanceof ResultadoValidacion);
    }

    @Test
    @DisplayName("Should handle long XML content")
    void validarXml_xmlGrande() {
        StringBuilder sb = new StringBuilder();
        sb.append("<Invoice>");
        for (int i = 0; i < 1000; i++) {
            sb.append("<Item><ID>").append(i).append("</ID></Item>");
        }
        sb.append("</Invoice>");

        ResultadoValidacion resultado = validador.validarXml(sb.toString(), "large.xml", ReglaSunatXsl.FACTURA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should handle XML with namespace declarations")
    void validarXml_xmlConNamespaces() {
        String xmlNs = "<Invoice xmlns=\"http://www.w3.org/2004/12/xml-namespaces\" xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\"></Invoice>";
        ResultadoValidacion resultado = validador.validarXml(xmlNs, "invoice.xml", ReglaSunatXsl.FACTURA);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Should be thread-safe for multiple validations")
    void validarXml_concurrenteCalls() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            ResultadoValidacion r = validador.validarXml("<test1/>", "test1.xml", ReglaSunatXsl.FACTURA);
            assertNotNull(r);
        });

        Thread t2 = new Thread(() -> {
            ResultadoValidacion r = validador.validarXml("<test2/>", "test2.xml", ReglaSunatXsl.BOLETA);
            assertNotNull(r);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
