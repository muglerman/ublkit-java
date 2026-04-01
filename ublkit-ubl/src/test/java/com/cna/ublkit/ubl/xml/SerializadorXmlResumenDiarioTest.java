package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorResumenDiario;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
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

class SerializadorXmlResumenDiarioTest {

    private final SerializadorXmlResumenDiario serializador = new SerializadorXmlResumenDiario();

    @Test
    void serializar_resumenMinimo_generaXmlValido() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertNotNull(xml);
        assertFalse(xml.isEmpty());

        Document doc = parsear(xml);
        assertNotNull(doc);
        assertEquals("SummaryDocuments", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneVersionYCustomizacion() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:UBLVersionID>2.0</cbc:UBLVersionID>"));
        assertTrue(xml.contains("<cbc:CustomizationID>1.1</cbc:CustomizationID>"));
    }

    @Test
    void serializar_generaIdConFormatoCorrecto() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setNumero(5);
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("RC-20260415-5"));
    }

    @Test
    void serializar_contieneReferenceDateEIssueDate() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:ReferenceDate>2026-04-14</cbc:ReferenceDate>"));
        assertTrue(xml.contains("<cbc:IssueDate>2026-04-15</cbc:IssueDate>"));
    }

    @Test
    void serializar_contieneEmisorSunat() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("20123456789"));
        assertTrue(xml.contains("EMPRESA PRUEBA S.A.C."));
        assertTrue(xml.contains("CustomerAssignedAccountID"));
    }

    @Test
    void serializar_contieneFirma() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("Signature"));
        assertTrue(xml.contains("SignatoryParty"));
    }

    @Test
    void serializar_contieneLineaResumen() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("SummaryDocumentsLine"));
        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:DocumentTypeCode>03</cbc:DocumentTypeCode>"));
        assertTrue(xml.contains("B001-1"));
    }

    @Test
    void serializar_contieneCliente() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("CustomerAssignedAccountID"));
        assertTrue(xml.contains("12345678"));
        assertTrue(xml.contains("AdditionalAccountID"));
    }

    @Test
    void serializar_contieneConditionCode() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:ConditionCode>1</cbc:ConditionCode>"));
    }

    @Test
    void serializar_contieneTotalAmount() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("TotalAmount"));
        assertTrue(xml.contains("118.00"));
    }

    @Test
    void serializar_contieneBillingPaymentGravado() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("BillingPayment"));
        assertTrue(xml.contains("PaidAmount"));
        assertTrue(xml.contains("100.00"));
        assertTrue(xml.contains("<cbc:InstructionID>01</cbc:InstructionID>"));
    }

    @Test
    void serializar_contieneIgvTaxTotal() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("TaxTotal"));
        assertTrue(xml.contains("18.00"));
        assertTrue(xml.contains("<cbc:ID>1000</cbc:ID>"));
        assertTrue(xml.contains("<cbc:Name>IGV</cbc:Name>"));
    }

    @Test
    void serializar_contieneNamespaceSummaryDocuments() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1"));
    }

    @Test
    void serializar_contieneNamespacePerception() {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("urn:sunat:names:specification:ubl:peru:schema:xsd:Perception-1"));
    }

    @Test
    void serializar_conComprobanteAfectado() {
        ResumenDiario resumen = crearResumenMinimo();
        ComprobanteResumen comp = resumen.getComprobantes().getFirst().comprobante();
        comp.setTipoComprobante("07");
        comp.setComprobanteAfectado(new ComprobanteAfectadoResumen("03", "B001-1"));
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("BillingReference"));
        assertTrue(xml.contains("InvoiceDocumentReference"));
    }

    @Test
    void serializar_conPercepcion() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.getComprobantes().getFirst().comprobante().setPercepcion(
                new PercepcionResumen("01", new BigDecimal("2.00"),
                        new BigDecimal("1180.00"), new BigDecimal("23.60"), new BigDecimal("1203.60"))
        );
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("SUNATPerceptionSummaryDocumentReference"));
        assertTrue(xml.contains("SUNATPerceptionSystemCode"));
        assertTrue(xml.contains("SUNATPerceptionPercent"));
        assertTrue(xml.contains("SUNATTotalCashed"));
    }

    @Test
    void serializar_multipleLineas() {
        ResumenDiario resumen = crearResumenMinimo();
        ComprobanteResumen comp2 = crearComprobanteBasico("B001-2", "87654321", "MARIA LOPEZ");
        resumen.setComprobantes(List.of(
                resumen.getComprobantes().getFirst(),
                new ItemResumenDiario("1", comp2)
        ));
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:LineID>2</cbc:LineID>"));
    }

    @Test
    void serializar_conExoneradoEInafecto() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.getComprobantes().getFirst().comprobante().setValorVenta(
                new ComprobanteValorVenta(new BigDecimal("100.00"), null,
                        null, new BigDecimal("60.00"), new BigDecimal("40.00"), null)
        );
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:InstructionID>02</cbc:InstructionID>"));
        assertTrue(xml.contains("<cbc:InstructionID>03</cbc:InstructionID>"));
    }

    @Test
    void serializar_conOtrosCargos() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.getComprobantes().getFirst().comprobante().setValorVenta(
                new ComprobanteValorVenta(new BigDecimal("128.00"), new BigDecimal("10.00"),
                        new BigDecimal("100.00"), null, null, null)
        );
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("AllowanceCharge"));
        assertTrue(xml.contains("ChargeIndicator"));
        assertTrue(xml.contains("10.00"));
    }

    @Test
    void serializar_conIcbper() {
        ResumenDiario resumen = crearResumenMinimo();
        resumen.getComprobantes().getFirst().comprobante().setImpuestos(
                new ComprobanteImpuestos(new BigDecimal("18.00"), new BigDecimal("0.18"),
                        new BigDecimal("0.30"), null, null, null)
        );
        EnsambladorResumenDiario.ensamblar(resumen);

        String xml = serializador.serializar(resumen);

        assertTrue(xml.contains("<cbc:ID>7152</cbc:ID>"));
        assertTrue(xml.contains("ICBPER"));
    }

    // ── Utilidades ───────────────────────────────────────────────

    static ResumenDiario crearResumenMinimo() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));

        ComprobanteResumen comprobante = crearComprobanteBasico("B001-1", "12345678", "JUAN PEREZ");

        resumen.setComprobantes(List.of(new ItemResumenDiario("1", comprobante)));
        return resumen;
    }

    private static ComprobanteResumen crearComprobanteBasico(String serieNumero, String docId, String nombre) {
        ComprobanteResumen comp = new ComprobanteResumen();
        comp.setTipoComprobante("03");
        comp.setSerieNumero(serieNumero);
        comp.setMoneda("PEN");
        comp.setCliente(new ReceptorDocumento("1", docId, nombre, null, null));
        comp.setValorVenta(new ComprobanteValorVenta(
                new BigDecimal("118.00"), null,
                new BigDecimal("100.00"), null, null, null
        ));
        comp.setImpuestos(new ComprobanteImpuestos(
                new BigDecimal("18.00"), new BigDecimal("0.18"),
                null, null, null, null
        ));
        return comp;
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
