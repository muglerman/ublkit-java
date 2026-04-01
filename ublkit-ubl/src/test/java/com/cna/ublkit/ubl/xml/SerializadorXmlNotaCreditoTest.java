package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.ensamblador.EnsambladorNota;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializadorXmlNotaCreditoTest {

    private final SerializadorXmlNotaCredito serializador = new SerializadorXmlNotaCredito();

    @Test
    void serializar_notaCreditoMinima_generaXmlValido() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertNotNull(xml);
        Document doc = parsear(xml);
        assertEquals("CreditNote", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneNamespaceCreditNote() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"));
    }

    @Test
    void serializar_contieneDiscrepancyResponse() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("DiscrepancyResponse"));
        assertTrue(xml.contains("F001-1"));
        assertTrue(xml.contains("Anulacion de la operacion"));
    }

    @Test
    void serializar_contieneBillingReference() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("BillingReference"));
        assertTrue(xml.contains("InvoiceDocumentReference"));
    }

    @Test
    void serializar_contieneCreditNoteLines() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("CreditNoteLine"));
        assertTrue(xml.contains("CreditedQuantity"));
        assertTrue(xml.contains("Producto devuelto"));
    }

    @Test
    void serializar_contieneImpuestosYTotales() {
        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("TaxTotal"));
        assertTrue(xml.contains("LegalMonetaryTotal"));
        assertTrue(xml.contains("PayableAmount"));
    }

    static BorradorNotaCredito crearNotaCreditoMinima() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setMoneda("PEN");

        nota.setTipoNota("01");
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Anulacion de la operacion");

        nota.setEmisor(new EmisorDocumento(
                "20000000001", "Mi Empresa SAC", "Mi Empresa S.A.C.",
                new Direccion("150101", "0000", null, "Lima", "Lima", "Lima", "Av. Principal 123", "PE"),
                null
        ));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto devuelto");
        linea.setCantidad(new BigDecimal("1"));
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));

        return nota;
    }

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
}
