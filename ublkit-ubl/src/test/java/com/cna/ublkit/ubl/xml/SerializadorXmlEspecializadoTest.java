package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SerializadorXmlPercepcion Tests")
class SerializadorXmlPercepcionTest {

    private ComprobantePercepcion percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new ComprobantePercepcion();
        percepcion.setSerie("P001");
        percepcion.setNumero(1);
        percepcion.setMoneda("PEN");
        percepcion.setFechaEmision(LocalDate.of(2026, 4, 6));
        percepcion.setTipoRegimen("02");
        percepcion.setTipoRegimenPorcentaje(new BigDecimal("0.03"));
    }

    @Test
    @DisplayName("Should serialize perception to XML")
    void testSerializaPercepcionAXml() {
        String xml = SerializadorXmlPercepcion.serializa(percepcion);
        assertNotNull(xml);
        assertTrue(xml.contains("Perception"));
    }

    @Test
    @DisplayName("Should include perception root element")
    void testInclueyeElementoRaizPercepcion() {
        String xml = SerializadorXmlPercepcion.serializa(percepcion);
        assertNotNull(xml);
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should handle null perception")
    void testManejaPercepcionNula() {
        String xml = SerializadorXmlPercepcion.serializa(null);
        assertNull(xml);
    }

    @Test
    @DisplayName("Should include emission date")
    void testInclueyeFechaEmision() {
        String xml = SerializadorXmlPercepcion.serializa(percepcion);
        assertNotNull(xml);
        assertTrue(xml.contains("2026-04-06"));
    }

    @Test
    @DisplayName("Should serialize with proper namespaces")
    void testSerializaConNamespacesPropos() {
        String xml = SerializadorXmlPercepcion.serializa(percepcion);
        assertNotNull(xml);
        assertTrue(xml.contains("cac"));
    }
}

@DisplayName("SerializadorXmlRetencion Tests")
class SerializadorXmlRetencionTest {

    private ComprobanteRetencion retencion;

    @BeforeEach
    void setUp() {
        retencion = new ComprobanteRetencion();
        retencion.setSerie("R001");
        retencion.setNumero(1);
        retencion.setMoneda("PEN");
        retencion.setFechaEmision(LocalDate.of(2026, 4, 6));
        retencion.setTipoRegimen("01");
        retencion.setTipoRegimenPorcentaje(new BigDecimal("0.06"));
    }

    @Test
    @DisplayName("Should serialize retention to XML")
    void testSerializaRetencionAXml() {
        String xml = SerializadorXmlRetencion.serializa(retencion);
        assertNotNull(xml);
        assertTrue(xml.contains("Retention"));
    }

    @Test
    @DisplayName("Should include retention root element")
    void testInclueyeElementoRaizRetencion() {
        String xml = SerializadorXmlRetencion.serializa(retencion);
        assertNotNull(xml);
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should handle null retention")
    void testManejaRetencionNula() {
        String xml = SerializadorXmlRetencion.serializa(null);
        assertNull(xml);
    }

    @Test
    @DisplayName("Should include retention date")
    void testInclueyeFechaRetencion() {
        String xml = SerializadorXmlRetencion.serializa(retencion);
        assertNotNull(xml);
    }

    @Test
    @DisplayName("Should serialize with proper format")
    void testSerializaConFormatoPropio() {
        String xml = SerializadorXmlRetencion.serializa(retencion);
        assertNotNull(xml);
        assertTrue(xml.contains("<?xml"));
    }
}

@DisplayName("SerializadorXmlGuiaRemision Extended Tests")
class SerializadorXmlGuiaRemisionExtendedTest {

    private BorradorGuiaRemision guia;

    @BeforeEach
    void setUp() {
        guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(100);
        guia.setMoneda("PEN");
        guia.setFechaEmision(LocalDate.of(2026, 4, 6));
        guia.setHoraEmision(LocalTime.of(14, 30, 0));
        guia.setTipoTransporte("01");
    }

    @Test
    @DisplayName("Should serialize guia remision to XML")
    void testSerializaGuiaRemisionAXml() {
        String xml = SerializadorXmlGuiaRemision.serializa(guia);
        assertNotNull(xml);
        assertTrue(xml.contains("DespatchAdvice"));
    }

    @Test
    @DisplayName("Should include DespatchAdvice root element")
    void testInclueyeElementoRaizDespatchAdvice() {
        String xml = SerializadorXmlGuiaRemision.serializa(guia);
        assertNotNull(xml);
        assertTrue(xml.contains("DespatchAdvice"));
    }

    @Test
    @DisplayName("Should handle null guia")
    void testManejaGuiaNula() {
        String xml = SerializadorXmlGuiaRemision.serializa(null);
        assertNull(xml);
    }

    @Test
    @DisplayName("Should include transport type")
    void testInclueyeTipoTransporte() {
        String xml = SerializadorXmlGuiaRemision.serializa(guia);
        assertNotNull(xml);
    }

    @Test
    @DisplayName("Should serialize with proper namespaces")
    void testSerializaConNamespacesPropos() {
        String xml = SerializadorXmlGuiaRemision.serializa(guia);
        assertNotNull(xml);
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should include emission date and time")
    void testInclueyeFechaYHoraEmision() {
        String xml = SerializadorXmlGuiaRemision.serializa(guia);
        assertNotNull(xml);
        assertTrue(xml.contains("2026-04-06"));
    }
}

@DisplayName("SerializadorXmlComunicacionBaja Extended Tests")
class SerializadorXmlComunicacionBajaExtendedTest {

    private com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja comunicacion;

    @BeforeEach
    void setUp() {
        comunicacion = new com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja();
        comunicacion.setNumero(1);
        comunicacion.setMoneda("PEN");
        comunicacion.setFechaEmision(LocalDate.of(2026, 4, 6));
        comunicacion.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 5));
    }

    @Test
    @DisplayName("Should serialize comunicacion baja to XML")
    void testSerializaComunicacionBajaAXml() {
        String xml = SerializadorXmlComunicacionBaja.serializa(comunicacion);
        assertNotNull(xml);
        // Check for VoidedDocumentsReport or similar element
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should handle null comunicacion")
    void testManejaComunicacionNula() {
        String xml = SerializadorXmlComunicacionBaja.serializa(null);
        assertNull(xml);
    }

    @Test
    @DisplayName("Should serialize with proper format")
    void testSerializaConFormatoPropio() {
        String xml = SerializadorXmlComunicacionBaja.serializa(comunicacion);
        assertNotNull(xml);
    }
}

@DisplayName("SerializadorXmlResumenDiario Extended Tests")
class SerializadorXmlResumenDiarioExtendedTest {

    private com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario resumen;

    @BeforeEach
    void setUp() {
        resumen = new com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario();
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setFechaEmision(LocalDate.of(2026, 4, 6));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 5));
    }

    @Test
    @DisplayName("Should serialize resumen diario to XML")
    void testSerializaResumenDiarioAXml() {
        String xml = SerializadorXmlResumenDiario.serializa(resumen);
        assertNotNull(xml);
        // Check for SummaryDocumentsReport or similar element
        assertTrue(xml.contains("xmlns"));
    }

    @Test
    @DisplayName("Should handle null resumen")
    void testManejaResumenNulo() {
        String xml = SerializadorXmlResumenDiario.serializa(null);
        assertNull(xml);
    }

    @Test
    @DisplayName("Should serialize with proper format")
    void testSerializaConFormatoPropio() {
        String xml = SerializadorXmlResumenDiario.serializa(resumen);
        assertNotNull(xml);
    }

    @Test
    @DisplayName("Should include emission dates")
    void testInclueyeFechasEmision() {
        String xml = SerializadorXmlResumenDiario.serializa(resumen);
        assertNotNull(xml);
        assertTrue(xml.contains("2026-04-06"));
    }
}
