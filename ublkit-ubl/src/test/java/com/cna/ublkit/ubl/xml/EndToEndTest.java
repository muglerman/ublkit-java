package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorComunicacionBaja;
import com.cna.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.cna.ublkit.ubl.ensamblador.EnsambladorNota;
import com.cna.ublkit.ubl.ensamblador.EnsambladorResumenDiario;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests end-to-end: flujo completo modelo → ensamblar → XML → parseo → verificación.
 * Cubren Invoice (01/03), CreditNote, DebitNote, DespatchAdvice, VoidedDocuments, SummaryDocuments.
 *
 * @since 0.1.0
 */
class EndToEndTest {

    // ══════════════════════════════════════════════════════════════
    // FACTURA (Invoice 01)
    // ══════════════════════════════════════════════════════════════

    @Test
    void factura_flujoCompleto_modelo_ensamblar_xml_parseo() throws Exception {
        // 1. Crear modelo
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(100);
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setHoraEmision(LocalTime.of(10, 30, 0));
        factura.setMoneda("PEN");
        factura.setEmisor(emisor());
        factura.setReceptor(new ReceptorDocumento("6", "20500000003", "CORPORACION ABC S.A.", null, null));
        factura.setFormaDePago(new FormaDePago("Contado", null, null));

        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Laptop Dell Inspiron");
        linea1.setCantidad(new BigDecimal("2"));
        linea1.setPrecio(new BigDecimal("2500.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Mouse inalámbrico");
        linea2.setCantidad(new BigDecimal("5"));
        linea2.setPrecio(new BigDecimal("50.00"));

        factura.setDetalles(List.of(linea1, linea2));

        // 2. Ensamblar
        EnsambladorFactura.ensamblar(factura);

        // Verificar ensamblaje
        assertNotNull(factura.getTotalImpuestos());
        assertNotNull(factura.getTotalImporte());
        assertEquals("01", factura.getTipoComprobante());
        assertEquals("0101", factura.getTipoOperacion());

        // 3. Serializar a XML
        String xml = new SerializadorXmlFactura().serializar(factura);
        assertNotNull(xml);
        assertFalse(xml.isEmpty());

        // 4. Parsear XML y verificar
        Document doc = parsear(xml);
        assertEquals("Invoice", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("F001-100"));
        assertTrue(xml.contains("2026-03-30"));
        assertTrue(xml.contains("Laptop Dell Inspiron"));
        assertTrue(xml.contains("Mouse inal"));
        assertTrue(xml.contains("20500000003"));
    }

    // ══════════════════════════════════════════════════════════════
    // BOLETA (Invoice 03)
    // ══════════════════════════════════════════════════════════════

    @Test
    void boleta_flujoCompleto_modelo_ensamblar_xml() throws Exception {
        BorradorFactura boleta = new BorradorFactura();
        boleta.setSerie("B001");
        boleta.setNumero(50);
        boleta.setFechaEmision(LocalDate.of(2026, 3, 30));
        boleta.setEmisor(emisor());
        boleta.setReceptor(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        boleta.setFormaDePago(new FormaDePago("Contado", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Arroz 1kg");
        linea.setCantidad(new BigDecimal("10"));
        linea.setPrecio(new BigDecimal("4.00"));
        boleta.setDetalles(List.of(linea));

        EnsambladorFactura.ensamblar(boleta);

        assertEquals("03", boleta.getTipoComprobante());
        String xml = new SerializadorXmlFactura().serializar(boleta);

        Document doc = parsear(xml);
        assertEquals("Invoice", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("B001-50"));
        assertTrue(xml.contains("Arroz 1kg"));
    }

    // ══════════════════════════════════════════════════════════════
    // FACTURA CON DETRACCIÓN y CUOTAS
    // ══════════════════════════════════════════════════════════════

    @Test
    void factura_conDetraccionYCuotas_flujoCompleto() throws Exception {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(200);
        factura.setFechaEmision(LocalDate.of(2026, 4, 1));
        factura.setEmisor(emisor());
        factura.setReceptor(new ReceptorDocumento("6", "20400000004", "DISTRIBUIDORA S.A.", null, null));
        factura.setTipoOperacion("1001"); // Detracción

        // Detracción
        factura.setDetraccion(new Detraccion("001", "00-123-456789", "014",
                new BigDecimal("0.12"), new BigDecimal("2832.00")));

        // Forma de pago crédito con cuotas
        factura.setFormaDePago(new FormaDePago("Credito", new BigDecimal("23600.00"),
                List.of(
                        new CuotaDePago(new BigDecimal("11800.00"), LocalDate.of(2026, 5, 1)),
                        new CuotaDePago(new BigDecimal("11800.00"), LocalDate.of(2026, 6, 1))
                )));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Servicio de alquiler");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("20000.00"));
        factura.setDetalles(List.of(linea));

        EnsambladorFactura.ensamblar(factura);
        String xml = new SerializadorXmlFactura().serializar(factura);

        assertTrue(xml.contains("Detraccion"));
        assertTrue(xml.contains("00-123-456789"));
        assertTrue(xml.contains("Cuota001"));
        assertTrue(xml.contains("Cuota002"));
    }

    // ══════════════════════════════════════════════════════════════
    // NOTA DE CRÉDITO
    // ══════════════════════════════════════════════════════════════

    @Test
    void notaCredito_flujoCompleto_modelo_ensamblar_xml() throws Exception {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.of(2026, 3, 31));
        nota.setMoneda("PEN");
        nota.setEmisor(emisor());
        nota.setReceptor(new ReceptorDocumento("6", "20500000003", "CORPORACION ABC S.A.", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-100");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Anulación de la operación");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Laptop Dell Inspiron");
        linea.setCantidad(new BigDecimal("2"));
        linea.setPrecio(new BigDecimal("2500.00"));
        nota.setDetalles(List.of(linea));

        EnsambladorNota.ensamblar(nota);
        String xml = new SerializadorXmlNotaCredito().serializar(nota);

        Document doc = parsear(xml);
        assertEquals("CreditNote", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("F001-100"));
        assertTrue(xml.contains("DiscrepancyResponse"));
        assertTrue(xml.contains("CreditNoteLine"));
    }

    // ══════════════════════════════════════════════════════════════
    // NOTA DE DÉBITO
    // ══════════════════════════════════════════════════════════════

    @Test
    void notaDebito_flujoCompleto_modelo_ensamblar_xml() throws Exception {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.of(2026, 3, 31));
        nota.setMoneda("PEN");
        nota.setEmisor(emisor());
        nota.setReceptor(new ReceptorDocumento("6", "20500000003", "CORPORACION ABC S.A.", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-100");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Intereses por mora");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Penalidad por demora");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("150.00"));
        nota.setDetalles(List.of(linea));

        EnsambladorNota.ensamblar(nota);
        String xml = new SerializadorXmlNotaDebito().serializar(nota);

        Document doc = parsear(xml);
        assertEquals("DebitNote", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("F001-100"));
        assertTrue(xml.contains("DiscrepancyResponse"));
        assertTrue(xml.contains("DebitNoteLine"));
        assertTrue(xml.contains("RequestedMonetaryTotal"));
    }

    // ══════════════════════════════════════════════════════════════
    // GUÍA DE REMISIÓN
    // ══════════════════════════════════════════════════════════════

    @Test
    void guiaRemision_flujoCompleto_modelo_xml() throws Exception {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.of(2026, 4, 1));
        guia.setHoraEmision(LocalTime.of(8, 0, 0));
        guia.setRemitente(emisor());

        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("25.50"));
        envio.setPesoTotalUnidadMedida("KGM");
        envio.setTipoModalidadTraslado("01");
        envio.setFechaTraslado(LocalDate.of(2026, 4, 2));
        guia.setEnvio(envio);

        guia.setDetalles(List.of(new LineaGuia("NIU", new BigDecimal("10"), "Cajas de producto", null, null, null)));

        String xml = new SerializadorXmlGuiaRemision().serializar(guia);

        Document doc = parsear(xml);
        assertEquals("DespatchAdvice", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("T001-1"));
        assertTrue(xml.contains("Cajas de producto"));
        assertTrue(xml.contains("DespatchAdviceTypeCode"));
    }

    // ══════════════════════════════════════════════════════════════
    // COMUNICACIÓN DE BAJA
    // ══════════════════════════════════════════════════════════════

    @Test
    void comunicacionBaja_flujoCompleto_modelo_ensamblar_xml() throws Exception {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 2));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 1));
        baja.setNumero(1);
        baja.setEmisor(emisor());
        baja.setComprobantes(List.of(
                new ItemBaja("F001", 100, "01", "Error en datos del cliente"),
                new ItemBaja("F001", 101, "01", "Operación anulada")
        ));

        EnsambladorComunicacionBaja.ensamblar(baja);
        String xml = new SerializadorXmlComunicacionBaja().serializar(baja);

        Document doc = parsear(xml);
        assertEquals("VoidedDocuments", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("RA-20260402-1"));
        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:LineID>2</cbc:LineID>"));
        assertTrue(xml.contains("Error en datos del cliente"));
    }

    // ══════════════════════════════════════════════════════════════
    // RESUMEN DIARIO
    // ══════════════════════════════════════════════════════════════

    @Test
    void resumenDiario_flujoCompleto_modelo_ensamblar_xml() throws Exception {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 2));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 1));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(emisor());

        ComprobanteResumen boleta1 = new ComprobanteResumen();
        boleta1.setTipoComprobante("03");
        boleta1.setSerieNumero("B001-1");
        boleta1.setMoneda("PEN");
        boleta1.setCliente(new ReceptorDocumento("1", "12345678", "CONSUMIDOR", null, null));
        boleta1.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("59.00"), null, new BigDecimal("50.00"), null, null, null));
        boleta1.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("9.00"), new BigDecimal("0.18"), null, null, null, null));

        ComprobanteResumen boleta2 = new ComprobanteResumen();
        boleta2.setTipoComprobante("03");
        boleta2.setSerieNumero("B001-2");
        boleta2.setMoneda("PEN");
        boleta2.setCliente(new ReceptorDocumento("1", "87654321", "OTRO CONSUMIDOR", null, null));
        boleta2.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("236.00"), null, new BigDecimal("200.00"), null, null, null));
        boleta2.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("36.00"), new BigDecimal("0.18"), null, null, null, null));

        resumen.setComprobantes(List.of(
                new ItemResumenDiario("1", boleta1),
                new ItemResumenDiario("1", boleta2)
        ));

        EnsambladorResumenDiario.ensamblar(resumen);
        String xml = new SerializadorXmlResumenDiario().serializar(resumen);

        Document doc = parsear(xml);
        assertEquals("SummaryDocuments", doc.getDocumentElement().getLocalName());
        assertTrue(xml.contains("RC-20260402-1"));
        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:LineID>2</cbc:LineID>"));
        assertTrue(xml.contains("B001-1"));
        assertTrue(xml.contains("B001-2"));
    }

    // ══════════════════════════════════════════════════════════════
    // FLUJO COMPLETO: Factura → XML → ZIP (verificar nombre archivo)
    // ══════════════════════════════════════════════════════════════

    @Test
    void flujoCompleto_facturaHastaArchivoComprimido() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(500);
        factura.setFechaEmision(LocalDate.of(2026, 4, 1));
        factura.setEmisor(emisor());
        factura.setReceptor(new ReceptorDocumento("6", "20500000003", "CORP ABC S.A.", null, null));
        factura.setFormaDePago(new FormaDePago("Contado", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Servicio");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("500.00"));
        factura.setDetalles(List.of(linea));

        // 1. Ensamblar
        EnsambladorFactura.ensamblar(factura);
        assertNotNull(factura.getTotalImporte());

        // 2. XML
        String xml = new SerializadorXmlFactura().serializar(factura);
        assertNotNull(xml);

        // 3. Nombre de archivo SUNAT: {ruc}-{tipoDoc}-{serie}-{numero}.xml
        String nombreArchivo = factura.getEmisor().ruc() + "-"
                + factura.getTipoComprobante() + "-"
                + factura.getSerie() + "-"
                + factura.getNumero() + ".xml";
        assertEquals("20000000001-01-F001-500.xml", nombreArchivo);

        // 4. El XML tiene datos correctos de totales
        assertTrue(xml.contains("500.00")); // Line extension
        assertTrue(xml.contains("590.00")); // Tax inclusive
    }

    // ── Helpers ──────────────────────────────────────────────────

    private EmisorDocumento emisor() {
        return new EmisorDocumento("20000000001", "MI EMPRESA S.A.C.", "MI EMPRESA S.A.C.", null, null);
    }

    private Document parsear(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }
}
