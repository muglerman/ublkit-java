package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.guia.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SerializadorXmlGuiaRemisionTest {

    private final SerializadorXmlGuiaRemision serializador = new SerializadorXmlGuiaRemision();

    static BorradorGuiaRemision crearGuiaMinima() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(123);
        guia.setFechaEmision(LocalDate.of(2026, 4, 15));
        guia.setHoraEmision(LocalTime.of(10, 30, 0));
        guia.setTipoComprobante("09");

        guia.setRemitente(new EmisorDocumento("20000000001", "Mi Empresa", "Mi Empresa S.A.C.", null, null));
        guia.setDestinatario(new DestinatarioGuia("6", "20100000002", "Cliente SAC"));

        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("25.500"));
        envio.setPesoTotalUnidadMedida("KGM");
        envio.setTipoModalidadTraslado("01");
        envio.setFechaTraslado(LocalDate.of(2026, 4, 16));

        envio.setPartida(new PuntoPartida("150101", "Av. Lima 123", null, null));
        envio.setDestino(new PuntoDestino("040101", "Calle Arequipa 456", null, null));

        Conductor chofer = new Conductor("Principal", "1", "12345678", "Juan", "Perez", "Q12345678");
        envio.setChoferes(List.of(chofer));
        envio.setVehiculo(new Vehiculo("ABC-123", null, null, null, null));

        guia.setEnvio(envio);

        guia.setDetalles(List.of(
                new LineaGuia("NIU", new BigDecimal("10"), "Producto A", "PROD001", null, null),
                new LineaGuia("KGM", new BigDecimal("5.5"), "Producto B", "PROD002", "10101501", null)
        ));

        return guia;
    }

    @Test
    void serializar_guiaMinima_generaXmlValido() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        assertNotNull(xml);
        assertFalse(xml.isEmpty());

        Document doc = parsear(xml);
        assertNotNull(doc);
        assertEquals("DespatchAdvice", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneDatosGenerales() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);
        Document doc = parsear(xml);
        XPath xpath = crearXPath(doc);

        assertEquals("2.1", evalXPath(xpath, doc, "//cbc:UBLVersionID"));
        assertEquals("T001-123", evalXPath(xpath, doc, "//*[local-name()='DespatchAdvice']/cbc:ID"));
        assertEquals("2026-04-15", evalXPath(xpath, doc, "//cbc:IssueDate"));
        assertEquals("10:30:00", evalXPath(xpath, doc, "//cbc:IssueTime"));
        assertEquals("09", evalXPath(xpath, doc, "//cbc:DespatchAdviceTypeCode"));
    }

    @Test
    void serializar_contieneRemitente() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("20000000001"));
        assertTrue(xml.contains("Mi Empresa S.A.C."));
        assertTrue(xml.contains("DespatchSupplierParty"));
    }

    @Test
    void serializar_contieneDestinatario() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("20100000002"));
        assertTrue(xml.contains("Cliente SAC"));
        assertTrue(xml.contains("DeliveryCustomerParty"));
    }

    @Test
    void serializar_contieneShipment() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);
        Document doc = parsear(xml);
        XPath xpath = crearXPath(doc);

        // HandlingCode (motivo traslado)
        assertEquals("01", evalXPath(xpath, doc, "//cac:Shipment/cbc:HandlingCode"));
        assertEquals("Venta", evalXPath(xpath, doc, "//cac:Shipment/cbc:HandlingInstructions"));

        // Peso
        assertTrue(xml.contains("25.500"));
        assertTrue(xml.contains("KGM"));

        // Modalidad
        assertEquals("01", evalXPath(xpath, doc, "//cac:ShipmentStage/cbc:TransportModeCode"));

        // Fecha traslado
        assertEquals("2026-04-16", evalXPath(xpath, doc, "//cac:TransitPeriod/cbc:StartDate"));
    }

    @Test
    void serializar_contieneChoferYVehiculo() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        // Chofer
        assertTrue(xml.contains("12345678"));
        assertTrue(xml.contains("Juan"));
        assertTrue(xml.contains("Perez"));
        assertTrue(xml.contains("Q12345678"));

        // Vehículo
        assertTrue(xml.contains("ABC-123"));
    }

    @Test
    void serializar_contieneDirecciones() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        // Partida
        assertTrue(xml.contains("150101"));
        assertTrue(xml.contains("Av. Lima 123"));

        // Destino
        assertTrue(xml.contains("040101"));
        assertTrue(xml.contains("Calle Arequipa 456"));
    }

    @Test
    void serializar_contieneLineas() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("DespatchLine"));
        assertTrue(xml.contains("Producto A"));
        assertTrue(xml.contains("Producto B"));
        assertTrue(xml.contains("PROD001"));
        assertTrue(xml.contains("10101501"));
    }

    @Test
    void serializar_conTransportista() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.getEnvio().setTransportista(
                new TransportistaGuia("6", "20300000003", "Transportes SAC", "0001-MTC"));
        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("20300000003"));
        assertTrue(xml.contains("Transportes SAC"));
        assertTrue(xml.contains("0001-MTC"));
        assertTrue(xml.contains("CarrierParty"));
    }

    @Test
    void serializar_conCompradorYTercero() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.setComprador(new CompradorGuia("6", "20400000004", "Comprador SAC"));
        guia.setTercero(new TerceroGuia("6", "20500000005", "Tercero SAC"));

        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("BuyerCustomerParty"));
        assertTrue(xml.contains("20400000004"));
        assertTrue(xml.contains("Comprador SAC"));

        assertTrue(xml.contains("SellerSupplierParty"));
        assertTrue(xml.contains("20500000005"));
        assertTrue(xml.contains("Tercero SAC"));
    }

    @Test
    void serializar_conContenedores() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.getEnvio().setContenedores(List.of(
                new Contenedor("CONT001", "PREC001"),
                new Contenedor("CONT002", null)));

        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("CONT001"));
        assertTrue(xml.contains("PREC001"));
        assertTrue(xml.contains("CONT002"));
    }

    @Test
    void serializar_conPuerto() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.getEnvio().setPuerto(new Puerto("CAL", "Puerto del Callao"));

        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("FirstArrivalPortLocation"));
        assertTrue(xml.contains("CAL"));
        assertTrue(xml.contains("Puerto del Callao"));
    }

    @Test
    void serializar_conDocumentoBaja() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.setDocumentoBaja(new DocumentoBajaGuia("09", "T001-100"));

        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("OrderReference"));
        assertTrue(xml.contains("T001-100"));
    }

    @Test
    void serializar_conDocumentosRelacionados() {
        BorradorGuiaRemision guia = crearGuiaMinima();
        guia.setDocumentoRelacionado(new DocumentoRelacionadoGuia("01", "F001-1"));
        guia.setDocumentosRelacionados(List.of(
                new DocumentoRelacionadoGuia("03", "B001-5")));
        guia.setDocumentosAdicionales(List.of(
                new DocumentoAdicionalGuia("01", "D001-1", "20000000001")));

        String xml = serializador.serializar(guia);

        assertTrue(xml.contains("AdditionalDocumentReference"));
        assertTrue(xml.contains("F001-1"));
        assertTrue(xml.contains("B001-5"));
        assertTrue(xml.contains("D001-1"));
    }

    // ── Helpers ──────────────────────────────────────────────────

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

    private XPath crearXPath(Document doc) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new javax.xml.namespace.NamespaceContext() {
            private static final Map<String, String> NS = Map.of(
                    "cbc", ConstantesUbl.NS_CBC,
                    "cac", ConstantesUbl.NS_CAC,
                    "ext", ConstantesUbl.NS_EXT
            );

            @Override
            public String getNamespaceURI(String prefix) {
                return NS.getOrDefault(prefix, javax.xml.XMLConstants.NULL_NS_URI);
            }

            @Override
            public String getPrefix(String uri) { return null; }

            @Override
            public Iterator<String> getPrefixes(String uri) { return null; }
        });
        return xpath;
    }

    private String evalXPath(XPath xpath, Document doc, String expression) {
        try {
            return (String) xpath.evaluate(expression, doc, XPathConstants.STRING);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo evaluar XPath de prueba: " + expression, e);
        }
    }
}
