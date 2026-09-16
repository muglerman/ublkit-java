package com.creanexusatreus.ublkit.ubl.xml;

import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImpuestosMixtosXmlTest {
    @ParameterizedTest(name = "afectación {0}: {1}/{2}")
    @CsvSource({
            "10,S,1000,IGV,VAT,18,300.00,54.00,10,01",
            "20,E,9997,EXO,VAT,0,290.00,0.00,20,01",
            "30,O,9998,INA,FRE,0,100.00,0.00,30,01",
            "11,Z,9996,GRA,FRE,0,50.00,0.00,11,02"
    })
    void cadaOperacionTributariaSeSerializaCompleta(String afectacion, String categoria, String tributo,
            String nombre, String tipoCodigo, String porcentaje, String base, String importe,
            String codigoEsperado, String tipoPrecio) {
        BorradorFactura factura = factura(List.of(lineaConBase("ITEM", "1", base, afectacion,
                new BigDecimal(base), new BigDecimal(importe))));
        Document doc = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));

        NodeList subtotales = doc.getElementsByTagNameNS("*", "TaxSubtotal");
        assertEquals(2, subtotales.getLength());
        assertSubtotalCompleto((Element) subtotales.item(0), base, importe, categoria, tributo, nombre,
                tipoCodigo, porcentaje, codigoEsperado, tipoPrecio);
        assertSubtotalCompleto((Element) subtotales.item(1), base, importe, categoria, tributo, nombre,
                tipoCodigo, porcentaje, codigoEsperado, tipoPrecio);
        assertTotales(doc, "10".equals(afectacion) ? "300.00" : ("11".equals(afectacion) ? "0.00" : base),
                "10".equals(afectacion) ? "354.00" : ("11".equals(afectacion) ? "0.00" : base),
                "10".equals(afectacion) ? "354.00" : ("11".equals(afectacion) ? "0.00" : base));
    }

    @Test
    void gravadoExoneradoInafectoYGratuitoGeneranCuatroGruposGlobales() {
        BorradorFactura factura = factura(List.of(
                lineaConBase("GRAVADO", "1", "300", "10", new BigDecimal("300"), new BigDecimal("54")),
                lineaConBase("EXONERADO", "1", "290", "20", new BigDecimal("290"), BigDecimal.ZERO),
                lineaConBase("INAfecto", "1", "100", "30", new BigDecimal("100"), BigDecimal.ZERO),
                lineaConBase("GRATUITO", "1", "50", "11", new BigDecimal("50"), BigDecimal.ZERO)));
        EnsambladorFactura.ensamblar(factura);
        Document doc = parse(new SerializadorXmlFactura().serializar(factura));

        NodeList subtotales = doc.getElementsByTagNameNS("*", "TaxSubtotal");
        assertEquals(8, subtotales.getLength()); // cuatro líneas + cuatro grupos globales
        Element global = globalTaxTotal(doc);
        NodeList globales = global.getElementsByTagNameNS("*", "TaxSubtotal");
        assertEquals(4, globales.getLength());
        assertSubtotalCompleto((Element) globales.item(0), "300.00", "54.00", "S", "1000", "IGV", "VAT", "18", "10", "01");
        assertSubtotalCompleto((Element) globales.item(1), "290.00", "0.00", "E", "9997", "EXO", "VAT", "0", "20", "01");
        assertSubtotalCompleto((Element) globales.item(2), "100.00", "0.00", "O", "9998", "INA", "FRE", "0", "30", "01");
        assertSubtotalCompleto((Element) globales.item(3), "50.00", "0.00", "Z", "9996", "GRA", "FRE", "0", "11", "02");
        assertFalse(doc.getDocumentElement().getTextContent().contains("S9997"));
        assertFalse(doc.getDocumentElement().getTextContent().contains("S9998"));
        assertFalse(doc.getDocumentElement().getTextContent().contains("S9996"));
        assertTotales(doc, "690.00", "744.00", "744.00");
    }
    @Test
    void gravadoYExoneradoConservanCategoriaYTributoEnResumenGlobal() {
        BorradorFactura factura = factura(List.of(linea("ATAUD", "2", "150", "10"),
                linea("CAJAS", "29", "10", "20")));

        String xml = new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura));
        Document doc = parse(xml);
        NodeList subtotales = doc.getElementsByTagNameNS("*", "TaxSubtotal");

        assertEquals(4, subtotales.getLength()); // dos líneas + dos grupos globales
        assertSubtotal((Element) subtotales.item(0), "300.00", "54.00", "S", "1000", "18");
        assertSubtotal((Element) subtotales.item(1), "290.00", "0.00", "E", "9997", "0");
        assertEquals("20", text((Element) subtotales.item(1), "TaxExemptionReasonCode"));
    }

    @Test
    void variasLineasDelMismoGrupoSeConsolidanSinMezclarCategorias() {
        BorradorFactura factura = factura(List.of(linea("A", "1", "100", "10"),
                linea("B", "1", "200", "10"), linea("C", "1", "50", "30")));
        Document doc = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));
        NodeList subtotales = doc.getElementsByTagNameNS("*", "TaxSubtotal");

        assertEquals(5, subtotales.getLength());
        assertSubtotal((Element) subtotales.item(0), "300.00", "54.00", "S", "1000", "18");
        assertSubtotal((Element) subtotales.item(1), "50.00", "0.00", "O", "9998", "0");
    }

    @ParameterizedTest
    @CsvSource({
            "10,S,1000,IGV,18", "20,E,9997,EXO,0", "30,O,9998,INA,0",
            "11,Z,9996,GRA,0", "21,Z,9996,GRA,0", "31,Z,9996,GRA,0", "40,G,9995,EXP,0"
    })
    void catalogoDeAfectacionDeterminaElGrupoCorrecto(String afectacion, String categoria,
                                                        String tributo, String nombre, String porcentaje) {
        BorradorFactura factura = factura(List.of(linea("ITEM", "1", "100", afectacion)));
        Document doc = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));
        Element subtotal = (Element) doc.getElementsByTagNameNS("*", "TaxSubtotal").item(0);
        assertSubtotal(subtotal, "100.00", "0".equals(porcentaje) ? "0.00" : "18.00",
                categoria, tributo, porcentaje);
        assertEquals(afectacion, text(subtotal, "TaxExemptionReasonCode"));
    }

    @Test
    void domYStreamingMantienenElMismoDesgloseTributario() {
        BorradorFactura factura = factura(List.of(linea("A", "1", "300", "10"),
                linea("B", "1", "290", "20")));
        EnsambladorFactura.ensamblar(factura);
        SerializadorXmlFactura serializador = new SerializadorXmlFactura();
        Document dom = parse(serializador.serializar(factura));
        Document streaming = parse(serializador.serializarStreaming(factura));
        assertEquals(globalSignature(dom), globalSignature(streaming));
    }

    @Test
    void mismaCategoriaConDiferenteAfectacionOPorcentajeNoSeAgrupa() {
        BorradorFactura factura = factura(List.of(
                lineaConBase("GRAVADO 18", "1", "100", "10", new BigDecimal("100"), new BigDecimal("18")),
                lineaConBase("GRAVADO 10", "1", "100", "10", new BigDecimal("100"), new BigDecimal("10"))));
        factura.getDetalles().get(1).setTasaIgv(new BigDecimal("0.10"));
        Document doc = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));
        Element global = globalTaxTotal(doc);
        assertEquals(2, global.getElementsByTagNameNS("*", "TaxSubtotal").getLength());
    }

    private static String globalSignature(Document doc) {
        StringBuilder result = new StringBuilder();
        Element taxTotal = (Element) doc.getElementsByTagNameNS("*", "TaxTotal").item(0);
        NodeList groups = taxTotal.getElementsByTagNameNS("*", "TaxSubtotal");
        for (int i = 0; i < groups.getLength(); i++) {
            Element group = (Element) groups.item(i);
            result.append(text(group, "TaxableAmount")).append('|').append(text(group, "TaxAmount"))
                    .append('|').append(text(group, "ID")).append('|')
                    .append(text((Element) group.getElementsByTagNameNS("*", "TaxScheme").item(0), "ID"));
        }
        return result.toString();
    }

    private static LineaDetalle linea(String descripcion, String cantidad, String precio, String tipo) {
        LineaDetalle l = new LineaDetalle();
        l.setDescripcion(descripcion);
        l.setCantidad(new BigDecimal(cantidad));
        l.setPrecio(new BigDecimal(precio));
        l.setIgvTipo(tipo);
        return l;
    }

    private static LineaDetalle lineaConBase(String descripcion, String cantidad, String precio, String tipo,
            BigDecimal base, BigDecimal importe) {
        LineaDetalle l = linea(descripcion, cantidad, precio, tipo);
        l.setIgvBaseImponible(base);
        l.setIgv(importe);
        l.setTotalImpuestos(importe);
        if ("10".equals(tipo)) {
            l.setTasaIgv(new BigDecimal("0.18"));
        }
        return l;
    }

    private static BorradorFactura factura(List<LineaDetalle> lineas) {
        BorradorFactura f = new BorradorFactura();
        f.setSerie("B001"); f.setNumero(2); f.setFechaEmision(LocalDate.of(2026, 1, 1));
        f.setTipoComprobante("03"); f.setTipoOperacion("0101"); f.setMoneda("PEN");
        f.setEmisor(new EmisorDocumento("20123456789", "ACME", "ACME SAC", null, null));
        f.setReceptor(new ReceptorDocumento("6", "20100000002", "CLIENTE", null, null));
        f.setDetalles(lineas);
        return f;
    }

    private static void assertSubtotal(Element subtotal, String base, String amount, String category,
                                       String scheme, String percent) {
        assertEquals(base, text(subtotal, "TaxableAmount"));
        assertEquals(amount, text(subtotal, "TaxAmount"));
        assertEquals(category, text(subtotal, "ID"));
        assertEquals(scheme, text((Element) subtotal.getElementsByTagNameNS("*", "TaxScheme").item(0), "ID"));
        assertEquals(0, new BigDecimal(percent).compareTo(new BigDecimal(
                text((Element) subtotal.getElementsByTagNameNS("*", "TaxCategory").item(0), "Percent"))));
    }

    private static void assertSubtotalCompleto(Element subtotal, String base, String amount, String category,
            String scheme, String name, String typeCode, String percent, String affectation, String priceType) {
        assertSubtotal(subtotal, base, amount, category, scheme, percent);
        Element taxCategory = (Element) subtotal.getElementsByTagNameNS("*", "TaxCategory").item(0);
        Element taxScheme = (Element) subtotal.getElementsByTagNameNS("*", "TaxScheme").item(0);
        assertEquals(name, text(taxScheme, "Name"));
        assertEquals(typeCode, text(taxScheme, "TaxTypeCode"));
        if ("01".equals(priceType)) {
            assertEquals(affectation, text(taxCategory, "TaxExemptionReasonCode"));
        } else {
            assertNotNull(taxCategory);
        }
    }

    private static void assertTotales(Document doc, String lineExtension, String taxInclusive, String payable) {
        Element total = (Element) doc.getElementsByTagNameNS("*", "LegalMonetaryTotal").item(0);
        assertEquals(lineExtension, text(total, "LineExtensionAmount"));
        assertEquals(taxInclusive, text(total, "TaxInclusiveAmount"));
        assertEquals(payable, text(total, "PayableAmount"));
    }

    private static Element globalTaxTotal(Document doc) {
        NodeList totals = doc.getElementsByTagNameNS("*", "TaxTotal");
        Element selected = null;
        int maxSubtotals = -1;
        for (int i = 0; i < totals.getLength(); i++) {
            Element candidate = (Element) totals.item(i);
            int count = candidate.getElementsByTagNameNS("*", "TaxSubtotal").getLength();
            if (count > maxSubtotals) {
                selected = candidate;
                maxSubtotals = count;
            }
        }
        assertNotNull(selected);
        return selected;
    }

    private static String text(Element parent, String name) {
        return ((Element) parent.getElementsByTagNameNS("*", name).item(0)).getTextContent();
    }

    private static Document parse(String xml) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            return factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
