package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.cna.ublkit.ubl.ensamblador.EnsambladorFacturaTest;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SerializadorXmlFacturaTest {

    private final SerializadorXmlFactura serializador = new SerializadorXmlFactura();

    @Test
    void serializar_facturaMinima_generaXmlValido() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        assertNotNull(xml);
        assertFalse(xml.isEmpty());

        // Parsear para verificar que es XML válido
        Document doc = parsear(xml);
        assertNotNull(doc);
        assertEquals("Invoice", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneElementosRequeridos() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);
        Document doc = parsear(xml);
        XPath xpath = crearXPath();

        // UBL Version
        assertEquals("2.1", evalXPath(xpath, doc, "//cbc:UBLVersionID"));
        assertEquals("2.0", evalXPath(xpath, doc, "//cbc:CustomizationID"));

        // ID
        assertEquals("F001-1", evalXPath(xpath, doc, "//cbc:ID[1]"));

        // Issue Date
        assertEquals("2026-03-30", evalXPath(xpath, doc, "//cbc:IssueDate"));

        // InvoiceTypeCode
        assertEquals("01", evalXPath(xpath, doc, "//cbc:InvoiceTypeCode"));

        // Currency
        assertEquals("PEN", evalXPath(xpath, doc, "//cbc:DocumentCurrencyCode"));
    }

    @Test
    void serializar_contieneEmisorYReceptor() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        // Emisor
        assertTrue(xml.contains("20000000001"));
        assertTrue(xml.contains("Mi Empresa S.A.C."));

        // Receptor
        assertTrue(xml.contains("20100000002"));
        assertTrue(xml.contains("Cliente SAC"));
    }

    @Test
    void serializar_contieneImpuestosYTotales() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        // Total impuestos
        assertTrue(xml.contains("<cbc:TaxAmount"));
        assertTrue(xml.contains("36.00"));

        // LegalMonetaryTotal
        assertTrue(xml.contains("LineExtensionAmount"));
        assertTrue(xml.contains("200.00"));
        assertTrue(xml.contains("TaxInclusiveAmount"));
        assertTrue(xml.contains("236.00"));
        assertTrue(xml.contains("PayableAmount"));
    }

    @Test
    void serializar_contieneLineasDetalle() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        // Línea
        assertTrue(xml.contains("InvoiceLine"));
        assertTrue(xml.contains("InvoicedQuantity"));
        assertTrue(xml.contains("Producto A"));
        assertTrue(xml.contains("PricingReference"));
        assertTrue(xml.contains("PriceAmount"));
    }

    @Test
    void serializar_contieneNamespacesCorrectos() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        assertTrue(xml.contains("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"));
        assertTrue(xml.contains("xmlns:cac="));
        assertTrue(xml.contains("xmlns:cbc="));
        assertTrue(xml.contains("xmlns:ext="));
    }

    @Test
    void serializar_contieneExtensionsParaFirma() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        assertTrue(xml.contains("UBLExtensions"));
        assertTrue(xml.contains("ExtensionContent"));
    }

    @Test
    void serializar_contieneFormaDePago() {
        BorradorFactura factura = EnsambladorFacturaTest.crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);

        String xml = serializador.serializar(factura);

        assertTrue(xml.contains("FormaPago"));
        assertTrue(xml.contains("Contado"));
    }

    @Test
    void serializar_boleta_conLeyendas_generaInvoiceType03YNote() {
        BorradorFactura boleta = EnsambladorFacturaTest.crearFacturaMinima();
        boleta.setSerie("B001");
        boleta.setLeyendas(Map.of("1000", "SON: DOSCIENTOS TREINTA Y SEIS CON 00/100 SOLES"));
        EnsambladorFactura.ensamblar(boleta);

        String xml = serializador.serializar(boleta);
        Document doc = parsear(xml);
        XPath xpath = crearXPath();

        assertEquals("03", evalXPath(xpath, doc, "//cbc:InvoiceTypeCode"));
        assertEquals("1000", evalXPath(xpath, doc, "(//cbc:Note/@languageLocaleID)[1]"));
    }

    // ── Utilidades ───────────────────────────────────────────────

    private Document parsear(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo parsear XML de prueba", e);
        }
    }

    private XPath crearXPath() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(new javax.xml.namespace.NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                return switch (prefix) {
                    case "cbc" -> "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
                    case "cac" -> "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
                    case "ext" -> "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";
                    default -> null;
                };
            }

            @Override
            public String getPrefix(String namespaceURI) { return null; }

            @Override
            public java.util.Iterator<String> getPrefixes(String namespaceURI) { return null; }
        });
        return xpath;
    }

    private String evalXPath(XPath xpath, Document doc, String expr) {
        try {
            return (String) xpath.evaluate(expr, doc, XPathConstants.STRING);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo evaluar XPath de prueba: " + expr, e);
        }
    }
}
