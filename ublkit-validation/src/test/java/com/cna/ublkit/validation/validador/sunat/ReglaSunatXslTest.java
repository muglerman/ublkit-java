package com.cna.ublkit.validation.validador.sunat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReglaSunatXsl Tests")
class ReglaSunatXslTest {

    @Test
    @DisplayName("FACTURA should have correct XSL resource path")
    void regla_FACTURA_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegFactura-2.0.1.xsl", ReglaSunatXsl.FACTURA.recursoClasspath());
    }

    @Test
    @DisplayName("BOLETA should have correct XSL resource path")
    void regla_BOLETA_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegBoleta-2.0.1.xsl", ReglaSunatXsl.BOLETA.recursoClasspath());
    }

    @Test
    @DisplayName("NOTA_CREDITO should have correct XSL resource path")
    void regla_NOTACREDITO_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegNC-2.0.1.xsl", ReglaSunatXsl.NOTA_CREDITO.recursoClasspath());
    }

    @Test
    @DisplayName("NOTA_DEBITO should have correct XSL resource path")
    void regla_NOTADEBITO_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegND-2.0.1.xsl", ReglaSunatXsl.NOTA_DEBITO.recursoClasspath());
    }

    @Test
    @DisplayName("GUIA_REMITENTE should have correct XSL resource path")
    void regla_GUIA_REMITENTE_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegGreRemitente-2.0.1.xsl", ReglaSunatXsl.GUIA_REMITENTE.recursoClasspath());
    }

    @Test
    @DisplayName("GUIA_TRANSPORTISTA should have correct XSL resource path")
    void regla_GUIA_TRANSPORTISTA_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/2.X/ValidaExprRegGreTransportista-2.0.1.xsl", ReglaSunatXsl.GUIA_TRANSPORTISTA.recursoClasspath());
    }

    @Test
    @DisplayName("RESUMEN_DIARIO should have correct XSL resource path")
    void regla_RESUMEN_DIARIO_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/1.X/ValidaExprRegSummary-1.1.0.xsl", ReglaSunatXsl.RESUMEN_DIARIO.recursoClasspath());
    }

    @Test
    @DisplayName("COMUNICACION_BAJA should have correct XSL resource path")
    void regla_COMUNICACION_BAJA_tieneRutaCorrecta() {
        assertEquals("sunat/validation/xsl/1.X/ValidaExprRegSummaryVoided-1.0.1.xsl", ReglaSunatXsl.COMUNICACION_BAJA.recursoClasspath());
    }

    @Test
    @DisplayName("Should have exactly 8 rules")
    void regla_tamanio() {
        ReglaSunatXsl[] reglas = ReglaSunatXsl.values();
        assertEquals(8, reglas.length);
    }

    @Test
    @DisplayName("Should support valueOf for FACTURA")
    void regla_valueOf_FACTURA() {
        assertEquals(ReglaSunatXsl.FACTURA, ReglaSunatXsl.valueOf("FACTURA"));
    }

    @Test
    @DisplayName("Should support valueOf for BOLETA")
    void regla_valueOf_BOLETA() {
        assertEquals(ReglaSunatXsl.BOLETA, ReglaSunatXsl.valueOf("BOLETA"));
    }

    @Test
    @DisplayName("Should support valueOf for NOTA_CREDITO")
    void regla_valueOf_NOTA_CREDITO() {
        assertEquals(ReglaSunatXsl.NOTA_CREDITO, ReglaSunatXsl.valueOf("NOTA_CREDITO"));
    }

    @Test
    @DisplayName("All rules should return non-null resource path")
    void regla_recursoPaths_noNulos() {
        for (ReglaSunatXsl regla : ReglaSunatXsl.values()) {
            assertNotNull(regla.recursoClasspath());
            assertFalse(regla.recursoClasspath().isBlank());
        }
    }

    @Test
    @DisplayName("All rule resource paths should start with sunat/validation")
    void regla_recursoPaths_tienenPrefijo() {
        for (ReglaSunatXsl regla : ReglaSunatXsl.values()) {
            assertTrue(regla.recursoClasspath().startsWith("sunat/validation/"));
        }
    }

    @Test
    @DisplayName("All rule resource paths should end with .xsl")
    void regla_recursoPaths_tienenExtension() {
        for (ReglaSunatXsl regla : ReglaSunatXsl.values()) {
            assertTrue(regla.recursoClasspath().endsWith(".xsl"));
        }
    }

    @Test
    @DisplayName("Version 2.X rules should reference 2.X directory")
    void regla_version2x_referenciaDirectorio() {
        assertTrue(ReglaSunatXsl.FACTURA.recursoClasspath().contains("/2.X/"));
        assertTrue(ReglaSunatXsl.BOLETA.recursoClasspath().contains("/2.X/"));
        assertTrue(ReglaSunatXsl.NOTA_CREDITO.recursoClasspath().contains("/2.X/"));
        assertTrue(ReglaSunatXsl.NOTA_DEBITO.recursoClasspath().contains("/2.X/"));
        assertTrue(ReglaSunatXsl.GUIA_REMITENTE.recursoClasspath().contains("/2.X/"));
        assertTrue(ReglaSunatXsl.GUIA_TRANSPORTISTA.recursoClasspath().contains("/2.X/"));
    }

    @Test
    @DisplayName("Version 1.X rules should reference 1.X directory")
    void regla_version1x_referenciaDirectorio() {
        assertTrue(ReglaSunatXsl.RESUMEN_DIARIO.recursoClasspath().contains("/1.X/"));
        assertTrue(ReglaSunatXsl.COMUNICACION_BAJA.recursoClasspath().contains("/1.X/"));
    }

    @Test
    @DisplayName("Different rules should have different resource paths")
    void regla_recursosPaths_unicos() {
        ReglaSunatXsl[] reglas = ReglaSunatXsl.values();
        for (int i = 0; i < reglas.length; i++) {
            for (int j = i + 1; j < reglas.length; j++) {
                assertNotEquals(reglas[i].recursoClasspath(), reglas[j].recursoClasspath(),
                        "Rules " + reglas[i].name() + " and " + reglas[j].name() + " should have different paths");
            }
        }
    }
}
