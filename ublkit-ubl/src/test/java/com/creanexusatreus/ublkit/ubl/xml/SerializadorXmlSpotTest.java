package com.creanexusatreus.ublkit.ubl.xml;

import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorNota;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaDebito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DatosHidrobiologicos;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DatosTransporteCarga;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.PuntoTransporte;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.TramoTransporteCarga;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Serializador XML SPOT")
class SerializadorXmlSpotTest {

    private static final String NS_INVOICE = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    private static final String NS_DEBIT_NOTE = "urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2";
    private static final String NS_CAC =
            "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    private static final String NS_CBC =
            "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    @Test
    @DisplayName("1001 emite porcentaje humano, PEN y atributos Cat54/Cat59")
    void factura1001_emiteDetraccionConPorcentajeHumano() {
        BorradorFactura factura = ensamblarFactura("1001",
                new Detraccion("001", "00-032-019287", "037",
                        new BigDecimal("12"), new BigDecimal("120.00")));

        Document xml = parse(new SerializadorXmlFactura().serializar(factura));

        assertDetraccion(xml, "inv", "037", "00-032-019287");
        assertEquals("12", texto(xml,
                "/inv:Invoice/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentPercent"));
        assertEquals("PEN", atributo(xml,
                "/inv:Invoice/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:Amount", "currencyID"));
        assertEquals("OPERACION SUJETA A DETRACCION", texto(xml,
                "/inv:Invoice/cbc:Note[@languageLocaleID='2006']"));
    }

    @Test
    @DisplayName("1001 falla explícitamente si faltan porcentaje o monto")
    void factura1001_rechazaDetraccionSinPorcentajeOMonto() {
        BorradorFactura sinPorcentaje = facturaBase("1001");
        sinPorcentaje.setDetraccion(new Detraccion("001", "00-032-019287", "037",
                null, new BigDecimal("120.00")));

        BorradorFactura sinMonto = facturaBase("1001");
        sinMonto.setDetraccion(new Detraccion("001", "00-032-019287", "037",
                new BigDecimal("12"), null));

        assertThrows(IllegalArgumentException.class, () -> EnsambladorFactura.ensamblar(sinPorcentaje));
        assertThrows(IllegalArgumentException.class, () -> EnsambladorFactura.ensamblar(sinMonto));
    }

    @Test
    @DisplayName("1002 emite propiedades 3001 a 3006 por línea")
    void factura1002_emiteDatosHidrobiologicos() {
        BorradorFactura factura = facturaBase("1002");
        factura.getDetalles().getFirst().setDatosHidrobiologicos(new DatosHidrobiologicos(
                "CE-12345", "MAR AZUL", "ANCHOVETA", "Puerto del Callao",
                new BigDecimal("18.50"), LocalDate.of(2026, 8, 31)));
        factura.setDetraccion(new Detraccion("001", "00-032-019287", "004",
                new BigDecimal("12"), new BigDecimal("120.00")));

        Document xml = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));

        for (String codigo : List.of("3001", "3002", "3003", "3004", "3005", "3006")) {
            assertNotNull(nodo(xml, "/inv:Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty"
                    + "[cbc:NameCode='" + codigo + "']"));
        }
        assertEquals("CE-12345", texto(xml, propiedadItem("3001") + "/cbc:Value"));
        assertEquals("TNE", atributo(xml, propiedadItem("3006") + "/cbc:ValueQuantity", "unitCode"));
        assertEquals("18.50", texto(xml, propiedadItem("3006") + "/cbc:ValueQuantity"));
        assertEquals("2026-08-31", texto(xml,
                propiedadItem("3005") + "/cac:UsabilityPeriod/cbc:StartDate"));
        assertEquals("Propiedad del item", atributo(xml, propiedadItem("3001") + "/cbc:NameCode", "listName"));
        assertEquals("PE:SUNAT", atributo(xml, propiedadItem("3001") + "/cbc:NameCode", "listAgencyName"));
        assertEquals("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55",
                atributo(xml, propiedadItem("3001") + "/cbc:NameCode", "listURI"));

        Document streaming = parse(new SerializadorXmlFactura().serializarStreaming(factura));
        assertEquals("18.50", texto(streaming, propiedadItem("3006") + "/cbc:ValueQuantity"));
    }

    @Test
    @DisplayName("1003 exige el código 028 y conserva detracción válida")
    void factura1003_emiteDetraccionDeTransportePasajeros() {
        BorradorFactura factura = ensamblarFactura("1003",
                new Detraccion("001", "00-032-019287", "028",
                        new BigDecimal("12"), new BigDecimal("120.00")));

        Document xml = parse(new SerializadorXmlFactura().serializar(factura));

        assertDetraccion(xml, "inv", "028", "00-032-019287");
    }

    @Test
    @DisplayName("1004 emite origen, destino, valores 01-03 PEN y tramo opcional")
    void factura1004_emiteDatosTransporteCarga() {
        BorradorFactura factura = facturaBase("1004");
        factura.getDetalles().getFirst().setDatosTransporteCarga(new DatosTransporteCarga(
                new PuntoTransporte("150101", "Av. Argentina 123, Callao"),
                new PuntoTransporte("130101", "Av. España 456, Trujillo"),
                "Traslado de carga general desde Callao hacia Trujillo",
                new BigDecimal("1500.00"),
                new BigDecimal("1300.00"),
                new BigDecimal("1400.00"),
                List.of(new TramoTransporteCarga(
                        "01",
                        new PuntoTransporte("150101", "Av. Argentina 123, Callao"),
                        new PuntoTransporte("130101", "Av. España 456, Trujillo"),
                        "Tramo virtual Callao - Trujillo",
                        new BigDecimal("1300.00")))));
        factura.setDetraccion(new Detraccion("001", "00-032-019287", "027",
                new BigDecimal("12"), new BigDecimal("120.00")));

        Document xml = parse(new SerializadorXmlFactura().serializar(EnsambladorFactura.ensamblar(factura)));

        String entrega = "/inv:Invoice/cac:InvoiceLine/cac:Delivery";
        assertEquals("150101", texto(xml, entrega + "/cac:Despatch/cac:DespatchAddress/cbc:ID"));
        assertEquals("Av. Argentina 123, Callao", texto(xml,
                entrega + "/cac:Despatch/cac:DespatchAddress/cac:AddressLine/cbc:Line"));
        assertEquals("130101", texto(xml, entrega + "/cac:DeliveryLocation/cac:Address/cbc:ID"));
        assertEquals("Av. España 456, Trujillo", texto(xml,
                entrega + "/cac:DeliveryLocation/cac:Address/cac:AddressLine/cbc:Line"));
        assertEquals("Traslado de carga general desde Callao hacia Trujillo",
                texto(xml, entrega + "/cac:Despatch/cbc:Instructions"));
        for (String tipo : List.of("01", "02", "03")) {
            String terminos = entrega + "/cac:DeliveryTerms[cbc:ID='" + tipo + "']/cbc:Amount";
            assertNotNull(nodo(xml, terminos));
            assertEquals("PEN", atributo(xml, terminos, "currencyID"));
        }
        String tramo = entrega + "/cac:Shipment/cac:Consignment";
        assertEquals("01", texto(xml, tramo + "/cbc:ID"));
        assertEquals("150101", texto(xml,
                tramo + "/cac:PlannedPickupTransportEvent/cac:Location/cbc:ID"));
        assertEquals("130101", texto(xml,
                tramo + "/cac:PlannedDeliveryTransportEvent/cac:Location/cbc:ID"));
        assertEquals("PEN", atributo(xml, tramo + "/cac:DeliveryTerms/cbc:Amount", "currencyID"));

        Document streaming = parse(new SerializadorXmlFactura().serializarStreaming(factura));
        assertEquals("PEN", atributo(streaming,
                entrega + "/cac:DeliveryTerms[cbc:ID='03']/cbc:Amount", "currencyID"));
    }

    @Test
    @DisplayName("Nota de débito reutiliza SPOT y obtiene cuenta BN y leyenda 2006")
    void notaDebito_emiteDetraccionConCuentaBnYLeyenda() {
        BorradorNotaDebito nota = notaDebitoBase();
        nota.setDetraccion(new Detraccion("001", null, "037",
                new BigDecimal("12"), new BigDecimal("120.00")));

        BorradorNotaDebito ensamblada = EnsambladorNota.ensamblar(nota);
        Document xml = parse(new SerializadorXmlNotaDebito().serializar(ensamblada));

        assertDetraccion(xml, "deb", "037", "00-032-019287");
        assertEquals("12", texto(xml,
                "/deb:DebitNote/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentPercent"));
        assertEquals("PEN", atributo(xml,
                "/deb:DebitNote/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:Amount", "currencyID"));
        assertEquals("OPERACION SUJETA A DETRACCION", ensamblada.getLeyendas().get("2006"));
    }

    private static BorradorFactura ensamblarFactura(String tipoOperacion, Detraccion detraccion) {
        BorradorFactura factura = facturaBase(tipoOperacion);
        factura.setDetraccion(detraccion);
        return EnsambladorFactura.ensamblar(factura);
    }

    private static BorradorFactura facturaBase(String tipoOperacion) {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2026, 9, 1));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion(tipoOperacion);
        factura.setMoneda("USD");
        factura.setEmisor(new EmisorDocumento(
                "20123456789", "ACME", "ACME SAC", null, null, "00-032-019287"));
        factura.setReceptor(new ReceptorDocumento("6", "10456789012", "CLIENTE SAC", null, null));
        factura.setDetalles(List.of(lineaBase()));
        return factura;
    }

    private static BorradorNotaDebito notaDebitoBase() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(2);
        nota.setFechaEmision(LocalDate.of(2026, 9, 1));
        nota.setTipoComprobante("08");
        nota.setMoneda("USD");
        nota.setTipoNota("01");
        nota.setComprobanteAfectadoSerieNumero("F001-00000001");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Intereses por pago fuera de plazo");
        nota.setEmisor(new EmisorDocumento(
                "20123456789", "ACME", "ACME SAC", null, null, "00-032-019287"));
        nota.setReceptor(new ReceptorDocumento("6", "10456789012", "CLIENTE SAC", null, null));
        nota.setDetalles(List.of(lineaBase()));
        return nota;
    }

    private static LineaDetalle lineaBase() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("SERVICIO GRAVADO");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("NIU");
        linea.setPrecio(new BigDecimal("1000.00"));
        linea.setIgvTipo("10");
        return linea;
    }

    private static void assertDetraccion(Document xml, String raiz, String codigoBien, String cuentaBn) {
        String documento = "inv".equals(raiz) ? "Invoice" : "DebitNote";
        String base = "/" + raiz + ":" + documento;
        assertEquals("001", texto(xml,
                base + "/cac:PaymentMeans[cbc:ID='Detraccion']/cbc:PaymentMeansCode"));
        assertEquals("Medio de pago", atributo(xml,
                base + "/cac:PaymentMeans[cbc:ID='Detraccion']/cbc:PaymentMeansCode", "listName"));
        assertEquals("PE:SUNAT", atributo(xml,
                base + "/cac:PaymentMeans[cbc:ID='Detraccion']/cbc:PaymentMeansCode", "listAgencyName"));
        assertEquals("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59", atributo(xml,
                base + "/cac:PaymentMeans[cbc:ID='Detraccion']/cbc:PaymentMeansCode", "listURI"));
        assertEquals(cuentaBn, texto(xml,
                base + "/cac:PaymentMeans[cbc:ID='Detraccion']/cac:PayeeFinancialAccount/cbc:ID"));
        assertEquals(codigoBien, texto(xml,
                base + "/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentMeansID"));
        assertEquals("Codigo de detraccion", atributo(xml,
                base + "/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentMeansID", "schemeName"));
        assertEquals("PE:SUNAT", atributo(xml,
                base + "/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentMeansID", "schemeAgencyName"));
        assertEquals("urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo54", atributo(xml,
                base + "/cac:PaymentTerms[cbc:ID='Detraccion']/cbc:PaymentMeansID", "schemeURI"));
    }

    private static String propiedadItem(String codigo) {
        return "/inv:Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty[cbc:NameCode='" + codigo + "']";
    }

    private static Document parse(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            factory.setNamespaceAware(true);
            return factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            throw new AssertionError("No se pudo interpretar el XML generado", e);
        }
    }

    private static Node nodo(Document document, String expresion) {
        try {
            return xpath().evaluate(expresion, document, XPathConstants.NODE) instanceof Node node ? node : null;
        } catch (Exception e) {
            throw new AssertionError("XPath inválido: " + expresion, e);
        }
    }

    private static String texto(Document document, String expresion) {
        Node nodo = nodo(document, expresion);
        assertNotNull(nodo, () -> "No se encontró XPath: " + expresion);
        return nodo.getTextContent();
    }

    private static String atributo(Document document, String expresion, String atributo) {
        Node nodo = nodo(document, expresion);
        assertNotNull(nodo, () -> "No se encontró XPath: " + expresion);
        return nodo.getAttributes().getNamedItem(atributo).getNodeValue();
    }

    private static XPath xpath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                return switch (prefix) {
                    case "inv" -> NS_INVOICE;
                    case "deb" -> NS_DEBIT_NOTE;
                    case "cac" -> NS_CAC;
                    case "cbc" -> NS_CBC;
                    case XMLConstants.XML_NS_PREFIX -> XMLConstants.XML_NS_URI;
                    default -> XMLConstants.NULL_NS_URI;
                };
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public Iterator<String> getPrefixes(String namespaceURI) {
                return List.<String>of().iterator();
            }
        });
        return xpath;
    }
}
