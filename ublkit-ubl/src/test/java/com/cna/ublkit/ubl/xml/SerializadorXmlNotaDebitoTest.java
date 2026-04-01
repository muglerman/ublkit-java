package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.ensamblador.EnsambladorNota;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
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

class SerializadorXmlNotaDebitoTest {

    private final SerializadorXmlNotaDebito serializador = new SerializadorXmlNotaDebito();

    @Test
    void serializar_notaDebitoMinima_generaXmlValido() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertNotNull(xml);
        Document doc = parsear(xml);
        assertEquals("DebitNote", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneNamespaceDebitNote() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2"));
    }

    @Test
    void serializar_contieneDiscrepancyResponse() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("DiscrepancyResponse"));
        assertTrue(xml.contains("F001-1"));
        assertTrue(xml.contains("Aumento en el valor"));
    }

    @Test
    void serializar_contieneRequestedMonetaryTotal() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("RequestedMonetaryTotal"));
        assertTrue(xml.contains("PayableAmount"));
        assertFalse(xml.contains("LegalMonetaryTotal"));
    }

    @Test
    void serializar_contieneDebitNoteLines() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("DebitNoteLine"));
        assertTrue(xml.contains("DebitedQuantity"));
        assertTrue(xml.contains("Servicio adicional"));
    }

    @Test
    void serializar_contieneBillingReference() {
        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);

        String xml = serializador.serializar(nota);

        assertTrue(xml.contains("BillingReference"));
        assertTrue(xml.contains("InvoiceDocumentReference"));
    }

    static BorradorNotaDebito crearNotaDebitoMinima() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(2);
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setMoneda("PEN");

        nota.setTipoNota("01");
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Aumento en el valor");

        nota.setEmisor(new EmisorDocumento(
                "20000000001", "Mi Empresa SAC", "Mi Empresa S.A.C.",
                new Direccion("150101", "0000", null, "Lima", "Lima", "Lima", "Av. Principal 123", "PE"),
                null
        ));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Servicio adicional");
        linea.setCantidad(new BigDecimal("1"));
        linea.setPrecio(new BigDecimal("50.00"));
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
