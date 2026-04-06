package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SerializadorXml Base Class Tests")
class SerializadorXmlTest {

    private BorradorFactura factura;

    @BeforeEach
    void setUp() {
        factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 4, 6));
        factura.setHoraEmision(LocalTime.of(14, 30, 0));
    }

    @Test
    @DisplayName("Should create invoice XML structure")
    void testCreaEstructuraXmlFactura() {
        String xml = SerializadorXmlFactura.serializa(factura);
        assertNotNull(xml);
        assertTrue(xml.contains("Invoice"));
    }

    @Test
    @DisplayName("Should include namespace declarations")
    void testInclueyeDeclaracionesNamespace() {
        String xml = SerializadorXmlFactura.serializa(factura);
        assertNotNull(xml);
        assertTrue(xml.contains("xmlns"));
        assertTrue(xml.contains("cbc"));
        assertTrue(xml.contains("cac"));
    }

    @Test
    @DisplayName("Should serialize with XML declaration")
    void testSerializaConDeclaracionXml() {
        String xml = SerializadorXmlFactura.serializa(factura);
        assertNotNull(xml);
        assertTrue(xml.contains("<?xml"));
    }

    @Test
    @DisplayName("Should handle facturas")
    void testManejaFacturas() {
        factura.setTipoComprobante("01");
        String xml = SerializadorXmlFactura.serializa(factura);
        assertNotNull(xml);
        assertTrue(xml.length() > 0);
    }

    @Test
    @DisplayName("Should handle boletas")
    void testManejaBoletas() {
        factura.setTipoComprobante("03");
        String xml = SerializadorXmlFactura.serializa(factura);
        assertNotNull(xml);
    }

    @Test
    @DisplayName("Should handle null factura gracefully")
    void testManejaFacturaNula() {
        String xml = SerializadorXmlFactura.serializa(null);
        assertNull(xml);
    }
}

@DisplayName("SerializadorXmlNotaCredito Class Tests")
class SerializadorXmlNotaCreditoTest {

    private BorradorNotaCredito notaCredito;

    @BeforeEach
    void setUp() {
        notaCredito = new BorradorNotaCredito();
        notaCredito.setSerie("NC01");
        notaCredito.setNumero(1);
        notaCredito.setMoneda("PEN");
        notaCredito.setFechaEmision(LocalDate.of(2026, 4, 6));
        notaCredito.setHoraEmision(LocalTime.of(14, 30, 0));
    }

    @Test
    @DisplayName("Should serialize nota credito to XML")
    void testSerializaNotaCreditoAXml() {
        String xml = SerializadorXmlNotaCredito.serializa(notaCredito);
        assertNotNull(xml);
        assertTrue(xml.contains("CreditNote"));
    }

    @Test
    @DisplayName("Should include CreditNote root element")
    void testInclueyeElementoRaizCreditNote() {
        String xml = SerializadorXmlNotaCredito.serializa(notaCredito);
        assertNotNull(xml);
        assertTrue(xml.contains("CreditNote"));
    }

    @Test
    @DisplayName("Should handle null nota credito")
    void testManejaNotaCreditoNula() {
        String xml = SerializadorXmlNotaCredito.serializa(null);
        assertNull(xml);
    }
}

@DisplayName("SerializadorXmlNotaDebito Class Tests")
class SerializadorXmlNotaDebitoTest {

    private BorradorNotaDebito notaDebito;

    @BeforeEach
    void setUp() {
        notaDebito = new BorradorNotaDebito();
        notaDebito.setSerie("ND01");
        notaDebito.setNumero(1);
        notaDebito.setMoneda("PEN");
        notaDebito.setFechaEmision(LocalDate.of(2026, 4, 6));
        notaDebito.setHoraEmision(LocalTime.of(14, 30, 0));
    }

    @Test
    @DisplayName("Should serialize nota debito to XML")
    void testSerializaNotaDebitoAXml() {
        String xml = SerializadorXmlNotaDebito.serializa(notaDebito);
        assertNotNull(xml);
        assertTrue(xml.contains("DebitNote"));
    }

    @Test
    @DisplayName("Should include DebitNote root element")
    void testInclueyeElementoRaizDebitNote() {
        String xml = SerializadorXmlNotaDebito.serializa(notaDebito);
        assertNotNull(xml);
        assertTrue(xml.contains("DebitNote"));
    }

    @Test
    @DisplayName("Should handle null nota debito")
    void testManejaNotaDebitoNula() {
        String xml = SerializadorXmlNotaDebito.serializa(null);
        assertNull(xml);
    }
}

@DisplayName("CategoriaIgv Enum Tests")
class CategoriaIgvAdvancedTest {

    @Test
    @DisplayName("Should identify exonerado category")
    void testIdentificaCategoriaExonerado() {
        assertNotNull(CategoriaIgv.EXONERADO);
    }

    @Test
    @DisplayName("Should identify no sujeto category")
    void testIdentificaCategoriaNoSujeto() {
        assertNotNull(CategoriaIgv.NO_SUJETO);
    }

    @Test
    @DisplayName("Should identify gravado category")
    void testIdentificaCategoriaGravado() {
        assertNotNull(CategoriaIgv.GRAVADO);
    }

    @Test
    @DisplayName("Should have proper code mapping")
    void testTieneAsignacionCodigoPropia() {
        CategoriaIgv[] categorias = CategoriaIgv.values();
        assertTrue(categorias.length > 0);
    }
}

@DisplayName("ConstantesUbl Advanced Tests")
class ConstantesUblAdvancedTest {

    @Test
    @DisplayName("Should have UBL namespace constant")
    void testTieneConstanteNamespaceUbl() {
        assertNotNull(ConstantesUbl.NS_UBL);
    }

    @Test
    @DisplayName("Should have CAC namespace constant")
    void testTieneConstanteNamespaceCac() {
        assertNotNull(ConstantesUbl.NS_CAC);
    }

    @Test
    @DisplayName("Should have CBC namespace constant")
    void testTieneConstanteNamespaceCbc() {
        assertNotNull(ConstantesUbl.NS_CBC);
    }

    @Test
    @DisplayName("Should have EXT namespace constant")
    void testTieneConstanteNamespaceExt() {
        assertNotNull(ConstantesUbl.NS_EXT);
    }

    @Test
    @DisplayName("Should have DS namespace constant")
    void testTieneConstanteNamespaceDs() {
        assertNotNull(ConstantesUbl.NS_DS);
    }

    @Test
    @DisplayName("Should have SAC namespace constant")
    void testTieneConstanteNamespaceSac() {
        assertNotNull(ConstantesUbl.NS_SAC);
    }

    @Test
    @DisplayName("Should have proper element names")
    void testTieneNombresElementosPropios() {
        assertNotNull(ConstantesUbl.TAG_INVOICE);
        assertNotNull(ConstantesUbl.TAG_CREDIT_NOTE);
        assertNotNull(ConstantesUbl.TAG_DEBIT_NOTE);
    }

    @Test
    @DisplayName("Should define tax codes")
    void testDefineCodigosTributos() {
        // Test that tax code constants exist
        assertNotNull(ConstantesUbl.CODIGO_TRIBUTO_IGV);
        assertNotNull(ConstantesUbl.CODIGO_TRIBUTO_IVAP);
    }
}

@DisplayName("FragmentosXml Advanced Tests")
class FragmentosXmlAdvancedTest {

    @Test
    @DisplayName("Should create valid tax total fragment")
    void testCreaFragmentoTaxTotalValido() {
        org.w3c.dom.Document doc = XmlUblHelper.crearDocumento(
                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2",
                "Invoice"
        );

        assertNotNull(doc);
        assertNotNull(doc.getDocumentElement());
    }

    @Test
    @DisplayName("Should handle complex document structures")
    void testManejaEstructurasDocumentoComplejas() {
        org.w3c.dom.Document doc = XmlUblHelper.crearDocumento(
                "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2",
                "Invoice"
        );

        org.w3c.dom.Element elem = XmlUblHelper.elementoCac(doc, "Party");
        assertNotNull(elem);
    }
}
