package com.cna.ublkit.ubl.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("XmlUblHelper Utility Class Tests")
class XmlUblHelperTest {

    private static final String NAMESPACE_UBL = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    private static final String ELEMENT_ROOT = "Invoice";

    @Test
    @DisplayName("Should create document with namespace")
    void testCreaDocumentoConNamespace() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);

        assertNotNull(doc);
        assertNotNull(doc.getDocumentElement());
        assertEquals(ELEMENT_ROOT, doc.getDocumentElement().getLocalName());
    }

    @Test
    @DisplayName("Should set XML standalone declaration")
    void testFijaXmlStandalone() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);

        assertTrue(doc.getXmlStandalone());
    }

    @Test
    @DisplayName("Should create element with namespace")
    void testCreaElementoConNamespace() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCac(doc, "Party");

        assertNotNull(elem);
        assertEquals("Party", elem.getLocalName());
    }

    @Test
    @DisplayName("Should set element text content")
    void testFijaContenidoTextoElemento() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "ID");
        XmlUblHelper.fijaTexto(elem, "F001");

        assertEquals("F001", elem.getTextContent());
    }

    @Test
    @DisplayName("Should set BigDecimal value with currency code")
    void testFijaValorBigDecimal() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "LineExtensionAmount");
        XmlUblHelper.fijaValor(elem, new BigDecimal("100.50"), "PEN");

        assertNotNull(elem.getAttribute("currencyID"));
        assertEquals("PEN", elem.getAttribute("currencyID"));
    }

    @Test
    @DisplayName("Should set LocalDate value")
    void testFijaValorLocalDate() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "IssueDate");
        LocalDate fecha = LocalDate.of(2026, 4, 6);
        XmlUblHelper.fijaValor(elem, fecha);

        assertEquals("2026-04-06", elem.getTextContent());
    }

    @Test
    @DisplayName("Should set LocalTime value")
    void testFijaValorLocalTime() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "IssueTime");
        LocalTime hora = LocalTime.of(14, 30, 45);
        XmlUblHelper.fijaValor(elem, hora);

        assertTrue(elem.getTextContent().contains("14:30:45"));
    }

    @Test
    @DisplayName("Should format BigDecimal to string")
    void testFormateaBigDecimal() {
        BigDecimal valor = new BigDecimal("100.50");
        String resultado = XmlUblHelper.formato(valor);

        assertNotNull(resultado);
        assertTrue(resultado.contains("100"));
    }

    @Test
    @DisplayName("Should serialize document to string")
    void testSerializaDocumentoAString() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        String xml = XmlUblHelper.serializaDocumento(doc);

        assertNotNull(xml);
        assertTrue(xml.contains("Invoice"));
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should create cac namespace element")
    void testCreaElementoCac() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCac(doc, "PartyIdentification");

        assertNotNull(elem);
        assertEquals("PartyIdentification", elem.getLocalName());
    }

    @Test
    @DisplayName("Should create cbc namespace element")
    void testCreaElementoCbc() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "ID");

        assertNotNull(elem);
        assertEquals("ID", elem.getLocalName());
    }

    @Test
    @DisplayName("Should create ext namespace element")
    void testCreaElementoExt() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoExt(doc, "UBLExtensions");

        assertNotNull(elem);
        assertEquals("UBLExtensions", elem.getLocalName());
    }

    @Test
    @DisplayName("Should handle null values in fijaValor")
    void testManejaValoresNulosEnFijaValor() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "LineExtensionAmount");

        assertDoesNotThrow(() -> XmlUblHelper.fijaValor(elem, (BigDecimal) null, "PEN"));
    }

    @Test
    @DisplayName("Should handle decimal precision")
    void testManejaPrecisionDecimal() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element elem = XmlUblHelper.elementoCbc(doc, "Amount");
        BigDecimal valor = new BigDecimal("100.999");
        XmlUblHelper.fijaValor(elem, valor, "PEN");

        assertNotNull(elem.getTextContent());
    }

    @Test
    @DisplayName("Should append element to parent")
    void testAñadeElementoAPadre() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        Element padre = doc.getDocumentElement();
        Element hijo = XmlUblHelper.elementoCbc(doc, "ID");
        padre.appendChild(hijo);

        assertEquals(1, padre.getChildNodes().getLength());
    }

    @Test
    @DisplayName("Should serialize with proper XML declaration")
    void testSerializaConDeclaracionXml() {
        Document doc = XmlUblHelper.crearDocumento(NAMESPACE_UBL, ELEMENT_ROOT);
        String xml = XmlUblHelper.serializaDocumento(doc);

        assertTrue(xml.contains("<?xml"));
    }
}
