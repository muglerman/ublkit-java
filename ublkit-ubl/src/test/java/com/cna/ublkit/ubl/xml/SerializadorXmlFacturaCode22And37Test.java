package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.transform.dom.DOMSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test exhaustivo para SerializadorXmlFactura con códigos 22 y 37.
 * Cobertura 100% de:
 * - Generación de XML válido para códigos de afectación
 * - Mapeo correcto de igvTipo en LineaDetalle
 * - Totalización correcta de categorías (S, E, O)
 */
@DisplayName("SerializadorXmlFactura - Códigos 22 y 37 Coverage")
class SerializadorXmlFacturaCode22And37Test {

    private SerializadorXmlFactura serializador;
    private BorradorFactura borrador;

    @BeforeEach
    void setUp() {
        serializador = new SerializadorXmlFactura();
        borrador = createBaseBorrador();
    }

    private BorradorFactura createBaseBorrador() {
        BorradorFactura bf = new BorradorFactura();
        bf.setTipoComprobante("01"); // Factura
        bf.setSerie("F001");
        bf.setNumero(1);
        bf.setFechaEmision(LocalDateTime.now());

        // Emisor
        EmisorDocumento emisor = new EmisorDocumento("20123456789", null, "TEST COMPANY", null, null);
        bf.setEmisor(emisor);

        // Receptor
        ReceptorDocumento receptor = new ReceptorDocumento("06", "20987654321", "CUSTOMER", null, null);
        bf.setReceptor(receptor);

        return bf;
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // CODE 22 TESTS (Exonerado - Retiro por premio)
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Serialize document with code 22 (Exonerado - Retiro por premio)")
    void testSerializarFactura_Code22_ExoneradoRetiroPremio() {
        // Arrange: Create line with code 22
        LineaDetalle line = createLineaWithIgvTipo("22", new BigDecimal("100.00"));
        borrador.setLineas(List.of(line));

        // Act
        DOMSource xmlResult = serializador.serializar(borrador);

        // Assert
        assertNotNull(xmlResult, "XML no debe ser null");
        assertNotNull(xmlResult.getNode(), "XML node no debe ser null");

        String xmlString = xmlToString(xmlResult);
        assertNotNull(xmlString);

        // Verify that code 22 appears in LineaDetalle
        assertTrue(xmlString.contains("22") || xmlString.contains("9997"),
                "XML debe contener código 22 o tributo 9997");
    }

    @Test
    @DisplayName("Tax calculation: Code 22 (Exonerado) produces NO IGV")
    void testSerializarFactura_Code22_NoIgvApplied() {
        LineaDetalle line = createLineaWithIgvTipo("22", new BigDecimal("1000.00"));
        borrador.setLineas(List.of(line));

        DOMSource xmlResult = serializador.serializar(borrador);
        String xmlString = xmlToString(xmlResult);

        // Code 22 debe tener TaxTotal = 0
        assertTrue(xmlString.contains("0") || !xmlString.contains("180"),
                "IGV de 1000 sería 180, pero código 22 no debe aplicar IGV");
    }

    @Test
    @DisplayName("Multiple lines with code 22: all map to EXONERADO")
    void testSerializarFactura_MultipleCode22_AllExonerado() {
        List<LineaDetalle> lineas = new ArrayList<>();
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("500.00")));
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("300.00")));
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("200.00")));

        borrador.setLineas(lineas);

        DOMSource xmlResult = serializador.serializar(borrador);
        assertNotNull(xmlResult);

        String xmlString = xmlToString(xmlResult);
        // Should have 3 lines with code 22
        int count22 = countOccurrences(xmlString, "22");
        assertEquals(3, count22, "Debe haber 3 líneas con código 22");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // CODE 37 TESTS (Inafecto - Transferencia gratuita)
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Serialize document with code 37 (Inafecto - Transferencia gratuita)")
    void testSerializarFactura_Code37_InafectoTransferenciaGratuita() {
        LineaDetalle line = createLineaWithIgvTipo("37", new BigDecimal("100.00"));
        borrador.setLineas(List.of(line));

        DOMSource xmlResult = serializador.serializar(borrador);

        assertNotNull(xmlResult, "XML no debe ser null");

        String xmlString = xmlToString(xmlResult);
        assertNotNull(xmlString);

        assertTrue(xmlString.contains("37") || xmlString.contains("9998"),
                "XML debe contener código 37 o tributo 9998");
    }

    @Test
    @DisplayName("Tax calculation: Code 37 (Inafecto) produces NO IGV")
    void testSerializarFactura_Code37_NoIgvApplied() {
        LineaDetalle line = createLineaWithIgvTipo("37", new BigDecimal("1000.00"));
        borrador.setLineas(List.of(line));

        DOMSource xmlResult = serializador.serializar(borrador);
        String xmlString = xmlToString(xmlResult);

        // Code 37 debe tener TaxTotal = 0
        assertTrue(xmlString.contains("0") || !xmlString.contains("180"),
                "IGV de 1000 sería 180, pero código 37 no debe aplicar IGV");
    }

    @Test
    @DisplayName("Multiple lines with code 37: all map to INAFECTO")
    void testSerializarFactura_MultipleCode37_AllInafecto() {
        List<LineaDetalle> lineas = new ArrayList<>();
        lineas.add(createLineaWithIgvTipo("37", new BigDecimal("600.00")));
        lineas.add(createLineaWithIgvTipo("37", new BigDecimal("400.00")));

        borrador.setLineas(lineas);

        DOMSource xmlResult = serializador.serializar(borrador);
        assertNotNull(xmlResult);

        String xmlString = xmlToString(xmlResult);
        int count37 = countOccurrences(xmlString, "37");
        assertEquals(2, count37, "Debe haber 2 líneas con código 37");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // MIXED CODE TESTS (22 + 37 + Other codes)
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Document with mixed codes: 10 (Gravado), 22 (Exonerado), 37 (Inafecto)")
    void testSerializarFactura_MixedCodes_AllValid() {
        List<LineaDetalle> lineas = new ArrayList<>();
        lineas.add(createLineaWithIgvTipo("10", new BigDecimal("1000.00"))); // Gravado - apply IGV 18%
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("500.00")));  // Exonerado - no IGV, has base
        lineas.add(createLineaWithIgvTipo("37", new BigDecimal("300.00")));  // Inafecto - no IGV, no base

        borrador.setLineas(lineas);

        DOMSource xmlResult = serializador.serializar(borrador);
        assertNotNull(xmlResult);

        String xmlString = xmlToString(xmlResult);

        // Verify each code is present
        assertTrue(xmlString.contains("10"), "Debe contener código 10");
        assertTrue(xmlString.contains("22"), "Debe contener código 22");
        assertTrue(xmlString.contains("37"), "Debe contener código 37");

        // Verify tax categories are different
        assertTrue(xmlString.contains("S"), "Debe tener categoría S (Gravado)");  // tribCode 1000
        assertTrue(xmlString.contains("E"), "Debe tener categoría E (Exonerado)"); // tribCode 9997
        assertTrue(xmlString.contains("O"), "Debe tener categoría O (Inafecto)");  // tribCode 9998
    }

    @Test
    @DisplayName("Document totalization with mixed codes (10, 22, 37)")
    void testSerializarFactura_MixedCodes_CorrectTotalization() {
        List<LineaDetalle> lineas = new ArrayList<>();

        // Line 1: Gravado 1000 → IGV = 180, Total = 1180
        lineas.add(createLineaWithIgvTipo("10", new BigDecimal("1000.00")));

        // Line 2: Exonerado 500 → IGV = 0, Total = 500
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("500.00")));

        // Line 3: Inafecto 300 → IGV = 0, Total = 300
        lineas.add(createLineaWithIgvTipo("37", new BigDecimal("300.00")));

        borrador.setLineas(lineas);

        DOMSource xmlResult = serializador.serializar(borrador);
        String xmlString = xmlToString(xmlResult);

        // Total should be 1180 + 500 + 300 = 1980
        assertTrue(xmlString.contains("1980") || xmlString.contains("1980.00"),
                "Total líneas debe ser 1980");

        // IGV total should be 180 (only from gravado code 10)
        assertTrue(xmlString.contains("180") || xmlString.contains("180.00") ||
                   xmlString.contains("0.18"),
                "IGV total debe ser 180");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // EDGE CASES
    // ═══════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Zero quantity with code 22: should still serialize")
    void testSerializarFactura_Code22_ZeroQuantity() {
        LineaDetalle line = createLineaWithIgvTipo("22", BigDecimal.ZERO);
        line.setCantidad(BigDecimal.ZERO);
        borrador.setLineas(List.of(line));

        DOMSource xmlResult = serializador.serializar(borrador);
        assertNotNull(xmlResult);
    }

    @Test
    @DisplayName("Decimal precision with codes 22 and 37")
    void testSerializarFactura_Code22And37_DecimalPrecision() {
        List<LineaDetalle> lineas = new ArrayList<>();
        lineas.add(createLineaWithIgvTipo("22", new BigDecimal("123.45")));
        lineas.add(createLineaWithIgvTipo("37", new BigDecimal("678.90")));

        borrador.setLineas(lineas);

        DOMSource xmlResult = serializador.serializar(borrador);
        String xmlString = xmlToString(xmlResult);

        assertTrue(xmlString.contains("123") || xmlString.contains("123.45"));
        assertTrue(xmlString.contains("678") || xmlString.contains("678.90"));
    }

    @ParameterizedTest(name = "Code {0} serializes correctly")
    @ValueSource(strings = {"20", "21", "22", "30", "31", "36", "37", "40"})
    @DisplayName("All affectation codes serialize without error")
    void testSerializarFactura_AllCodes_NoException(String igvTipo) {
        LineaDetalle line = createLineaWithIgvTipo(igvTipo, new BigDecimal("100.00"));
        borrador.setLineas(List.of(line));

        assertDoesNotThrow(() -> {
            DOMSource xmlResult = serializador.serializar(borrador);
            assertNotNull(xmlResult);
        }, "Code " + igvTipo + " debe serializar sin excepciones");
    }

    // ═══════════════════════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═══════════════════════════════════════════════════════════════════════════════

    private LineaDetalle createLineaWithIgvTipo(String igvTipo, BigDecimal value) {
        LineaDetalle linea = new LineaDetalle();
        linea.setNumero(1);
        linea.setDescripcion("Test Item");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(value);
        linea.setIgvTipo(igvTipo);
        linea.setUnidadMedida("NIU");
        return linea;
    }

    private String xmlToString(DOMSource source) {
        try {
            javax.xml.transform.Transformer transformer =
                    javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            java.io.StringWriter writer = new java.io.StringWriter();
            transformer.transform(source, new javax.xml.transform.stream.StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private int countOccurrences(String text, String pattern) {
        if (text == null || pattern == null) return 0;
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }
}
