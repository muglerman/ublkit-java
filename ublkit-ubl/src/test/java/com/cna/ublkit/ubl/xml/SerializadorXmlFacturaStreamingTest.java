package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Serializador XML Factura Streaming Tests")
class SerializadorXmlFacturaStreamingTest {

    @Test
    @DisplayName("Should serialize massive invoice lines with streaming")
    void serializarStreaming_facturaMasiva_conservaTodasLasLineas() {
        BorradorFactura factura = crearFactura(3000);

        SerializadorXmlFactura serializador = new SerializadorXmlFactura();
        String xml = serializador.serializarStreaming(factura);
        Document doc = parseXml(xml);

        assertTrue(xml.contains("<Invoice"));
        assertEquals(3000, doc.getElementsByTagNameNS("*", "InvoiceLine").getLength());
    }

    @Test
    @DisplayName("Should keep line count parity between DOM and streaming modes")
    void serializarStreaming_debeMantenerParidadConModoDom() {
        BorradorFactura factura = crearFactura(25);

        SerializadorXmlFactura serializador = new SerializadorXmlFactura();
        String xmlDom = serializador.serializar(factura);
        String xmlStreaming = serializador.serializarStreaming(factura);
        Document docDom = parseXml(xmlDom);
        Document docStreaming = parseXml(xmlStreaming);

        assertEquals(
            docDom.getElementsByTagNameNS("*", "InvoiceLine").getLength(),
            docStreaming.getElementsByTagNameNS("*", "InvoiceLine").getLength()
        );
    }

    private static BorradorFactura crearFactura(int lineas) {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2026, 4, 15));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");
        factura.setMoneda("PEN");
        factura.setEmisor(new EmisorDocumento("20123456789", "ACME", "ACME SAC", null, null));
        factura.setReceptor(new ReceptorDocumento("6", "10456789012", "CLIENTE SAC", null, null));

        List<LineaDetalle> detalles = new ArrayList<>();
        for (int i = 0; i < lineas; i++) {
            LineaDetalle linea = new LineaDetalle();
            linea.setDescripcion("ITEM " + (i + 1));
            linea.setCantidad(BigDecimal.ONE);
            linea.setUnidadMedida("NIU");
            linea.setPrecio(new BigDecimal("10.00"));
            linea.setIgvTipo("10");
            detalles.add(linea);
        }
        factura.setDetalles(detalles);

        return EnsambladorFactura.ensamblar(factura);
    }

    private static Document parseXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            return factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.ISO_8859_1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
