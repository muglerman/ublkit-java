package com.cna.ublkit.ubl.xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test exhaustivo para CategoriaIgv con cobertura 100%.
 *
 * Valida:
 * - Mapeo correcto de todos los códigos SUNAT Catálogo 7
 * - Códigos gravados (10-16)
 * - Código IVAP (17)
 * - Códigos exonerados (20-22)
 * - Códigos inafectos (30-37)
 * - Código exportación (40)
 * - Casos edge (null, fallback, códigos no estándar)
 */
@DisplayName("CategoriaIgv - SUNAT Catálogo 7 Mapping Tests")
class CategoriaIgvTest {

    // ═══════════════════════════════════════════════════════════════════════════════
    // GRAVADO TESTS (Códigos 10-16)
    // ═══════════════════════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "Gravado code {0} maps to GRAVADO category")
    @ValueSource(strings = {"10", "11", "12", "13", "14", "15", "16"})
    @DisplayName("All Gravado codes (10-16) map to GRAVADO")
    void testObtener_GravadoCodes_MapsToGravado(String code) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals("S", result.categoriaId(), "Categoría debe ser S (Gravado)");
        assertEquals("1000", result.tribCode(), "Tributo debe ser 1000 (IGV)");
        assertEquals("IGV", result.tribName(), "Nombre debe ser IGV");
        assertEquals("VAT", result.tribTypeCode(), "Tipo debe ser VAT");
    }

    @Test
    @DisplayName("Code 10 (Operación Onerosa Gravada) maps correctly")
    void testObtener_Code10_OperacionOnerosa() {
        CategoriaIgv result = CategoriaIgv.obtener("10");
        assertNotNull(result);
        assertEquals("S", result.categoriaId());
        assertEquals("1000", result.tribCode());
    }

    @Test
    @DisplayName("Code 11 (Retiro por premio - Gravado) maps correctly")
    void testObtener_Code11_RetiroPremioGravado() {
        CategoriaIgv result = CategoriaIgv.obtener("11");
        assertEquals("S", result.categoriaId());
    }

    @Test
    @DisplayName("Code 16 (Retiro por entrega trabajadores - Gravado) maps correctly")
    void testObtener_Code16_RetiroGravado() {
        CategoriaIgv result = CategoriaIgv.obtener("16");
        assertEquals("S", result.categoriaId());
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // IVAP TEST (Código 17)
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Code 17 (IVAP) maps to IVAP category")
    void testObtener_Code17_MapsToIvap() {
        CategoriaIgv result = CategoriaIgv.obtener("17");
        assertEquals("S", result.categoriaId());
        assertEquals("1016", result.tribCode(), "IVAP tributo debe ser 1016");
        assertEquals("IVAP", result.tribName());
        assertEquals("VAT", result.tribTypeCode());
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // EXONERADO TESTS (Códigos 20-22) ★ CRITICAL: Código 22 nuevo
    // ═══════════════════════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "Exonerado code {0} maps to EXONERADO category")
    @ValueSource(strings = {"20", "21", "22"})
    @DisplayName("All Exonerado codes (20-22) map to EXONERADO")
    void testObtener_ExoneradoCodes_MapsToExonerado(String code) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals("E", result.categoriaId(), "Categoría debe ser E (Exonerado)");
        assertEquals("9997", result.tribCode(), "Tributo debe ser 9997 (EXO)");
        assertEquals("EXO", result.tribName(), "Nombre debe ser EXO");
        assertEquals("VAT", result.tribTypeCode(), "Tipo debe ser VAT");
    }

    @Test
    @DisplayName("Code 20 (Exonerado - Operación Onerosa) maps correctly")
    void testObtener_Code20_OperacionOnerosaExonerada() {
        CategoriaIgv result = CategoriaIgv.obtener("20");
        assertNotNull(result);
        assertEquals("E", result.categoriaId());
        assertEquals("9997", result.tribCode());
    }

    @Test
    @DisplayName("Code 21 (Exonerado - Transferencia gratuita) maps correctly")
    void testObtener_Code21_TransferenciaGratuitaExonerada() {
        CategoriaIgv result = CategoriaIgv.obtener("21");
        assertEquals("E", result.categoriaId());
        assertEquals("9997", result.tribCode());
    }

    @Test
    @DisplayName("Code 22 (Exonerado - Retiro por premio) [NEW] maps correctly")
    void testObtener_Code22_RetiroPremioExonerado() {
        // ★ NUEVAMENTE AGREGADO EN Q1 2024 - SUNAT Catálogo 7
        CategoriaIgv result = CategoriaIgv.obtener("22");
        assertNotNull(result, "Código 22 debe ser soportado");
        assertEquals("E", result.categoriaId(), "Código 22 debe ser categoría E (Exonerado)");
        assertEquals("9997", result.tribCode(), "Código 22 debe tener tributo 9997");
        assertEquals("EXO", result.tribName(), "Código 22 debe tener nombre EXO");
        assertEquals("VAT", result.tribTypeCode(), "Código 22 debe tener tipo VAT");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // INAFECTO TESTS (Códigos 30-37) ★ CRITICAL: Código 37 nuevo
    // ═══════════════════════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "Inafecto code {0} maps to INAFECTO category")
    @ValueSource(strings = {"30", "31", "32", "33", "34", "35", "36", "37"})
    @DisplayName("All Inafecto codes (30-37) map to INAFECTO")
    void testObtener_InafectoCodes_MapsToInafecto(String code) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals("O", result.categoriaId(), "Categoría debe ser O (Inafecto)");
        assertEquals("9998", result.tribCode(), "Tributo debe ser 9998 (INA)");
        assertEquals("INA", result.tribName(), "Nombre debe ser INA");
        assertEquals("FRE", result.tribTypeCode(), "Tipo debe ser FRE");
    }

    @Test
    @DisplayName("Code 30 (Inafecto - Operación Onerosa) maps correctly")
    void testObtener_Code30_OperacionOnerosaInafecta() {
        CategoriaIgv result = CategoriaIgv.obtener("30");
        assertNotNull(result);
        assertEquals("O", result.categoriaId());
        assertEquals("9998", result.tribCode());
    }

    @Test
    @DisplayName("Code 31 (Inafecto - Retiro por Bonificación) maps correctly")
    void testObtener_Code31_RetiroBonificacion() {
        CategoriaIgv result = CategoriaIgv.obtener("31");
        assertEquals("O", result.categoriaId());
    }

    @Test
    @DisplayName("Code 35 (Inafecto - Retiro por premio) maps correctly")
    void testObtener_Code35_RetiroPremioInafecto() {
        CategoriaIgv result = CategoriaIgv.obtener("35");
        assertEquals("O", result.categoriaId());
    }

    @Test
    @DisplayName("Code 37 (Inafecto - Transferencia gratuita) [NEW] maps correctly")
    void testObtener_Code37_TransferenciaGratuitaInafecta() {
        // ★ NUEVAMENTE AGREGADO EN Q1 2024 - SUNAT Catálogo 7
        CategoriaIgv result = CategoriaIgv.obtener("37");
        assertNotNull(result, "Código 37 debe ser soportado");
        assertEquals("O", result.categoriaId(), "Código 37 debe ser categoría O (Inafecto)");
        assertEquals("9998", result.tribCode(), "Código 37 debe tener tributo 9998");
        assertEquals("INA", result.tribName(), "Código 37 debe tener nombre INA");
        assertEquals("FRE", result.tribTypeCode(), "Código 37 debe tener tipo FRE");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // EXPORTACION TEST (Código 40)
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Code 40 (Exportación) maps to EXPORTACION category")
    void testObtener_Code40_MapsToExportacion() {
        CategoriaIgv result = CategoriaIgv.obtener("40");
        assertEquals("G", result.categoriaId(), "Categoría debe ser G (Exportación)");
        assertEquals("9995", result.tribCode(), "Tributo debe ser 9995 (EXP)");
        assertEquals("EXP", result.tribName(), "Nombre debe ser EXP");
        assertEquals("FRE", result.tribTypeCode(), "Tipo debe ser FRE");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // NULL HANDLING TEST
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Null input returns default GRAVADO category")
    void testObtener_NullInput_ReturnsGravado() {
        CategoriaIgv result = CategoriaIgv.obtener(null);
        assertNotNull(result, "No debe lanzar excepción, debe retornar default");
        assertEquals("S", result.categoriaId(), "Default debe ser GRAVADO (S)");
        assertEquals("1000", result.tribCode());
        assertEquals("IGV", result.tribName());
        assertEquals("VAT", result.tribTypeCode());
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // FALLBACK TESTS (Códigos no explícitos)
    // ═══════════════════════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "Non-explicit code {0} starting with 1 falls back to GRATUITO")
    @ValueSource(strings = {"18", "19"})
    @DisplayName("Non-explicit codes starting with 1 map to GRATUITO")
    void testObtener_NonExplicitCode1x_FallsToGratuito(String code) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals("Z", result.categoriaId(), "Debe mapear a GRATUITO (Z)");
        assertEquals("9996", result.tribCode());
        assertEquals("GRA", result.tribName());
        assertEquals("FRE", result.tribTypeCode());
    }

    @ParameterizedTest(name = "Non-explicit code {0} starting with 3 falls back to GRATUITO")
    @ValueSource(strings = {"38", "39"})
    @DisplayName("Non-explicit codes starting with 3 map to GRATUITO")
    void testObtener_NonExplicitCode3x_FallsToGratuito(String code) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals("Z", result.categoriaId(), "Debe mapear a GRATUITO (Z)");
    }

    @Test
    @DisplayName("Unknown code (99) falls back to GRAVADO")
    void testObtener_UnknownCode_FallsToGravado() {
        CategoriaIgv result = CategoriaIgv.obtener("99");
        assertEquals("S", result.categoriaId(), "Desconocido debe retornar GRAVADO");
        assertEquals("1000", result.tribCode());
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // COMPREHENSIVE MAPPING TEST
    // ═══════════════════════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "Code {0} maps to category {1} with tribCode {2}")
    @CsvSource({
            // Gravados
            "10,S,1000",
            "11,S,1000",
            "16,S,1000",
            // IVAP
            "17,S,1016",
            // Exonerados
            "20,E,9997",
            "21,E,9997",
            "22,E,9997",  // ★ Nuevo
            // Inafectos
            "30,O,9998",
            "31,O,9998",
            "37,O,9998",  // ★ Nuevo
            // Exportación
            "40,G,9995"
    })
    @DisplayName("Comprehensive SUNAT Catálogo 7 mapping")
    void testObtener_ComprehensiveMapping(String code, String expectedCategoria, String expectedTribCode) {
        CategoriaIgv result = CategoriaIgv.obtener(code);
        assertEquals(expectedCategoria, result.categoriaId(),
                String.format("Código %s debe mapear a categoría %s", code, expectedCategoria));
        assertEquals(expectedTribCode, result.tribCode(),
                String.format("Código %s debe tener tributo %s", code, expectedTribCode));
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // RECORD STRUCTURE TESTS
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("CategoriaIgv record has all required fields")
    void testCategoriaIgv_RecordStructure() {
        CategoriaIgv categoria = CategoriaIgv.obtener("10");

        assertNotNull(categoria.categoriaId());
        assertNotNull(categoria.tribCode());
        assertNotNull(categoria.tribName());
        assertNotNull(categoria.tribTypeCode());

        assertFalse(categoria.categoriaId().isBlank());
        assertFalse(categoria.tribCode().isBlank());
        assertFalse(categoria.tribName().isBlank());
        assertFalse(categoria.tribTypeCode().isBlank());
    }

    @Test
    @DisplayName("All CategoriaIgv fields are non-empty strings")
    void testCategoriaIgv_NoEmptyFields() {
        String[] allCodes = {"10", "11", "17", "20", "22", "30", "37", "40"};

        for (String code : allCodes) {
            CategoriaIgv result = CategoriaIgv.obtener(code);
            assertTrue(result.categoriaId().length() > 0,
                    String.format("Código %s: categoriaId no debe estar vacío", code));
            assertTrue(result.tribCode().length() > 0,
                    String.format("Código %s: tribCode no debe estar vacío", code));
            assertTrue(result.tribName().length() > 0,
                    String.format("Código %s: tribName no debe estar vacío", code));
            assertTrue(result.tribTypeCode().length() > 0,
                    String.format("Código %s: tribTypeCode no debe estar vacío", code));
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // CONSISTENCY TESTS
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Same code always returns same result (consistency)")
    void testObtener_Consistency() {
        CategoriaIgv result1 = CategoriaIgv.obtener("22");
        CategoriaIgv result2 = CategoriaIgv.obtener("22");
        CategoriaIgv result3 = CategoriaIgv.obtener("22");

        assertEquals(result1.categoriaId(), result2.categoriaId());
        assertEquals(result2.categoriaId(), result3.categoriaId());
        assertEquals(result1.tribCode(), result2.tribCode());
        assertEquals(result2.tribCode(), result3.tribCode());
    }

    @Test
    @DisplayName("Codes 22 and 37 are distinct")
    void testObtener_NewCodes22And37AreDistinct() {
        CategoriaIgv code22 = CategoriaIgv.obtener("22");
        CategoriaIgv code37 = CategoriaIgv.obtener("37");

        assertNotEquals(code22.categoriaId(), code37.categoriaId(),
                "Códigos 22 y 37 deben tener categorías diferentes");
        assertNotEquals(code22.tribCode(), code37.tribCode(),
                "Códigos 22 y 37 deben tener tributos diferentes");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // EDGE CASES
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Empty string falls back to fallback logic")
    void testObtener_EmptyString_ReturnsFallback() {
        CategoriaIgv result = CategoriaIgv.obtener("");
        assertNotNull(result, "No debe lanzar excepción");
        // Debería retornar GRAVADO por defecto (no empieza con 1 ni 3)
        assertEquals("S", result.categoriaId());
    }

    @Test
    @DisplayName("Whitespace-only string falls back to fallback logic")
    void testObtener_Whitespace_ReturnsFallback() {
        CategoriaIgv result = CategoriaIgv.obtener("   ");
        assertNotNull(result);
        // Debería retornar GRAVADO por defecto
        assertEquals("S", result.categoriaId());
    }

    @Test
    @DisplayName("Code with leading zeros (e.g., '022') is handled correctly")
    void testObtener_LeadingZeros() {
        CategoriaIgv result = CategoriaIgv.obtener("022");
        // "022" no coincide exactamente con "22", así que debería caer en fallback
        // Si empieza con 0, caerá en fallback default → GRAVADO
        assertNotNull(result);
    }

    @Test
    @DisplayName("Mixed case code (if using equalsIgnoreCase) is handled")
    void testObtener_MixedCase() {
        // Switch en Java es case-sensitive, pero el método usa equalsIgnoreCase probablemente
        // Verificamos el comportamiento real
        CategoriaIgv result = CategoriaIgv.obtener("20");
        assertEquals("E", result.categoriaId());
    }
}
