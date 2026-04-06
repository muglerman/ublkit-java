package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.core.modelo.TipoCambio;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentoBase Abstract Class Tests")
class DocumentoBaseTest {

    private BorradorFactura documento;

    @BeforeEach
    void setUp() {
        documento = new BorradorFactura();
    }

    @Test
    @DisplayName("Should set and get serie")
    void testSetYGetSerie() {
        documento.setSerie("F001");
        assertEquals("F001", documento.getSerie());
    }

    @Test
    @DisplayName("Should set and get numero")
    void testSetYGetNumero() {
        documento.setNumero(1);
        assertEquals(1, documento.getNumero());
    }

    @Test
    @DisplayName("Should set and get moneda")
    void testSetYGetMoneda() {
        documento.setMoneda("PEN");
        assertEquals("PEN", documento.getMoneda());
    }

    @Test
    @DisplayName("Should set and get fechaEmision")
    void testSetYGetFechaEmision() {
        LocalDate fecha = LocalDate.of(2026, 1, 15);
        documento.setFechaEmision(fecha);
        assertEquals(fecha, documento.getFechaEmision());
    }

    @Test
    @DisplayName("Should set and get horaEmision")
    void testSetYGetHoraEmision() {
        LocalTime hora = LocalTime.of(14, 30, 45);
        documento.setHoraEmision(hora);
        assertEquals(hora, documento.getHoraEmision());
    }

    @Test
    @DisplayName("Should set and get emisor")
    void testSetYGetEmisor() {
        EmisorDocumento emisor = new EmisorDocumento("20123456789", "Tienda Principal", "Empresa SA", null, null);
        documento.setEmisor(emisor);
        assertNotNull(documento.getEmisor());
        assertEquals("20123456789", documento.getEmisor().ruc());
    }

    @Test
    @DisplayName("Should set and get receptor")
    void testSetYGetReceptor() {
        ReceptorDocumento receptor = new ReceptorDocumento("06", "20987654321", "Cliente SA", null, null);
        documento.setReceptor(receptor);
        assertNotNull(documento.getReceptor());
        assertEquals("20987654321", documento.getReceptor().numDocIdentidad());
    }

    @Test
    @DisplayName("Should set and get firmante")
    void testSetYGetFirmante() {
        FirmanteDocumento firmante = new FirmanteDocumento("20111111111", "Firmante SA");
        documento.setFirmante(firmante);
        assertNotNull(documento.getFirmante());
    }

    @Test
    @DisplayName("Should set and get tasaIgv")
    void testSetYGetTasaIgv() {
        documento.setTasaIgv(new BigDecimal("0.18"));
        assertEquals(0, new BigDecimal("0.18").compareTo(documento.getTasaIgv()));
    }

    @Test
    @DisplayName("Should set and get tasaIvap")
    void testSetYGetTasaIvap() {
        documento.setTasaIvap(new BigDecimal("0.04"));
        assertEquals(0, new BigDecimal("0.04").compareTo(documento.getTasaIvap()));
    }

    @Test
    @DisplayName("Should set and get tasaIcb")
    void testSetYGetTasaIcb() {
        documento.setTasaIcb(new BigDecimal("0.005"));
        assertEquals(0, new BigDecimal("0.005").compareTo(documento.getTasaIcb()));
    }

    @Test
    @DisplayName("Should set and get detalles")
    void testSetYGetDetalles() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        List<LineaDetalle> detalles = Arrays.asList(linea);
        documento.setDetalles(detalles);
        assertNotNull(documento.getDetalles());
        assertEquals(1, documento.getDetalles().size());
    }

    @Test
    @DisplayName("Should set and get leyendas")
    void testSetYGetLeyendas() {
        Map<String, String> leyendas = new HashMap<>();
        leyendas.put("1000", "Leyenda por operación sujeta a tributación");
        documento.setLeyendas(leyendas);
        assertNotNull(documento.getLeyendas());
        assertEquals(1, documento.getLeyendas().size());
    }

    @Test
    @DisplayName("Should set and get tipoCambio")
    void testSetYGetTipoCambio() {
        TipoCambio tipoCambio = new TipoCambio();
        documento.setTipoCambio(tipoCambio);
        assertNotNull(documento.getTipoCambio());
    }

    @Test
    @DisplayName("Should set and get ordenDeCompra")
    void testSetYGetOrdenDeCompra() {
        documento.setOrdenDeCompra("OC-001");
        assertEquals("OC-001", documento.getOrdenDeCompra());
    }

    @Test
    @DisplayName("Should set and get guias")
    void testSetYGetGuias() {
        GuiaRelacionada guia = new GuiaRelacionada("T001-100", "09");
        List<GuiaRelacionada> guias = Arrays.asList(guia);
        documento.setGuias(guias);
        assertNotNull(documento.getGuias());
        assertEquals(1, documento.getGuias().size());
    }

    @Test
    @DisplayName("Should set and get documentosRelacionados")
    void testSetYGetDocumentosRelacionados() {
        DocumentoRelacionado docRel = new DocumentoRelacionado("01", "F001-100");
        List<DocumentoRelacionado> docs = Arrays.asList(docRel);
        documento.setDocumentosRelacionados(docs);
        assertNotNull(documento.getDocumentosRelacionados());
        assertEquals(1, documento.getDocumentosRelacionados().size());
    }

    @Test
    @DisplayName("Should set and get cargos")
    void testSetYGetCargos() {
        CargoDescuento cargo = new CargoDescuento("01", new BigDecimal("100.00"), new BigDecimal("0.10"), null);
        List<CargoDescuento> cargos = Arrays.asList(cargo);
        documento.setCargos(cargos);
        assertNotNull(documento.getCargos());
        assertEquals(1, documento.getCargos().size());
    }

    @Test
    @DisplayName("Should set and get totalImpuestos")
    void testSetYGetTotalImpuestos() {
        TotalImpuestos totalImpuestos = new TotalImpuestos(
            new BigDecimal("200.00"),  // total
            new BigDecimal("180.00"),  // gravadoImporte
            new BigDecimal("1000.00"), // gravadoBaseImponible
            BigDecimal.ZERO,           // exoneradoImporte
            BigDecimal.ZERO,           // exoneradoBaseImponible
            BigDecimal.ZERO,           // inafectoImporte
            BigDecimal.ZERO,           // inafectoBaseImponible
            BigDecimal.ZERO,           // gratuitoImporte
            BigDecimal.ZERO,           // gratuitoBaseImponible
            BigDecimal.ZERO,           // exportacionImporte
            BigDecimal.ZERO,           // exportacionBaseImponible
            BigDecimal.ZERO,           // ivapImporte
            BigDecimal.ZERO,           // ivapBaseImponible
            BigDecimal.ZERO,           // icbImporte
            BigDecimal.ZERO,           // iscImporte
            BigDecimal.ZERO            // iscBaseImponible
        );
        documento.setTotalImpuestos(totalImpuestos);
        assertNotNull(documento.getTotalImpuestos());
    }

    @Test
    @DisplayName("Should handle null values in detalles")
    void testManejaDetallesNulos() {
        documento.setDetalles(null);
        assertNull(documento.getDetalles());
    }

    @Test
    @DisplayName("Should handle empty detalles list")
    void testManejaDetallesVacio() {
        documento.setDetalles(Arrays.asList());
        assertNotNull(documento.getDetalles());
        assertEquals(0, documento.getDetalles().size());
    }

    @Test
    @DisplayName("Should handle multiple detalles")
    void testMultiplesDetalles() {
        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Producto A");
        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Producto B");
        documento.setDetalles(Arrays.asList(linea1, linea2));
        assertEquals(2, documento.getDetalles().size());
    }

    @Test
    @DisplayName("Should accept BorradorNotaCredito subclass")
    void testAceptaBorradorNotaCredito() {
        BorradorNotaCredito notaCredito = new BorradorNotaCredito();
        notaCredito.setSerie("NC01");
        notaCredito.setNumero(5);
        assertEquals("NC01", notaCredito.getSerie());
        assertEquals(5, notaCredito.getNumero());
    }

    @Test
    @DisplayName("Should accept BorradorNotaDebito subclass")
    void testAceptaBorradorNotaDebito() {
        BorradorNotaDebito notaDebito = new BorradorNotaDebito();
        notaDebito.setSerie("ND01");
        notaDebito.setNumero(3);
        assertEquals("ND01", notaDebito.getSerie());
        assertEquals(3, notaDebito.getNumero());
    }
}
