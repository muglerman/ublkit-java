package com.cna.ublkit.ubl.modelo.sunat;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentoSunat Abstract Base Class Tests")
class DocumentoSunatTest {

    private ComunicacionBaja documento;

    @BeforeEach
    void setUp() {
        documento = new ComunicacionBaja();
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
        LocalDate fecha = LocalDate.of(2026, 4, 6);
        documento.setFechaEmision(fecha);
        assertEquals(fecha, documento.getFechaEmision());
    }

    @Test
    @DisplayName("Should set and get emisor")
    void testSetYGetEmisor() {
        EmisorDocumento emisor = new EmisorDocumento("20123456789", null, "Empresa SA", null, null);
        documento.setEmisor(emisor);
        assertNotNull(documento.getEmisor());
        assertEquals("20123456789", documento.getEmisor().ruc());
    }

    @Test
    @DisplayName("Should set and get firmante")
    void testSetYGetFirmante() {
        FirmanteDocumento firmante = new FirmanteDocumento("20111111111", "Firmante SA");
        documento.setFirmante(firmante);
        assertNotNull(documento.getFirmante());
    }

    @Test
    @DisplayName("Should set and get numero")
    void testSetYGetNumero() {
        documento.setNumero(1);
        assertEquals(1, documento.getNumero());
    }

    @Test
    @DisplayName("Should set and get fechaEmisionComprobantes")
    void testSetYGetFechaEmisionComprobantes() {
        LocalDate fecha = LocalDate.of(2026, 4, 5);
        documento.setFechaEmisionComprobantes(fecha);
        assertEquals(fecha, documento.getFechaEmisionComprobantes());
    }

    @Test
    @DisplayName("Should handle null moneda")
    void testManejaMonedaNula() {
        documento.setMoneda(null);
        assertNull(documento.getMoneda());
    }

    @Test
    @DisplayName("Should handle null emisor")
    void testManejaEmisorNulo() {
        documento.setEmisor(null);
        assertNull(documento.getEmisor());
    }

    @Test
    @DisplayName("Should handle null firmante")
    void testManejaFirmanteNulo() {
        documento.setFirmante(null);
        assertNull(documento.getFirmante());
    }

    @Test
    @DisplayName("Should support ComunicacionBaja subclass")
    void testSoportaComunicacionBajaSubclass() {
        ComunicacionBaja comunicacion = new ComunicacionBaja();
        comunicacion.setMoneda("PEN");
        comunicacion.setFechaEmision(LocalDate.of(2026, 4, 6));

        assertEquals("PEN", comunicacion.getMoneda());
        assertNotNull(comunicacion.getFechaEmision());
    }

    @Test
    @DisplayName("Should support ResumenDiario subclass")
    void testSoportaResumenDiarioSubclass() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setMoneda("PEN");
        resumen.setFechaEmision(LocalDate.of(2026, 4, 6));

        assertEquals("PEN", resumen.getMoneda());
        assertNotNull(resumen.getFechaEmision());
    }

    @Test
    @DisplayName("Should handle multiple currency types")
    void testManejaMultiplosToposMoneda() {
        documento.setMoneda("USD");
        assertEquals("USD", documento.getMoneda());

        documento.setMoneda("EUR");
        assertEquals("EUR", documento.getMoneda());
    }

    @Test
    @DisplayName("Should maintain all properties consistently")
    void testMantienePropiedadesConsistentes() {
        EmisorDocumento emisor = new EmisorDocumento("20123456789", null, "Empresa SA", null, null);
        FirmanteDocumento firmante = new FirmanteDocumento("20111111111", "Firmante SA");
        LocalDate fecha = LocalDate.of(2026, 4, 6);

        documento.setMoneda("PEN");
        documento.setFechaEmision(fecha);
        documento.setEmisor(emisor);
        documento.setFirmante(firmante);
        documento.setNumero(5);
        documento.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 5));

        assertEquals("PEN", documento.getMoneda());
        assertEquals(fecha, documento.getFechaEmision());
        assertNotNull(documento.getEmisor());
        assertNotNull(documento.getFirmante());
        assertEquals(5, documento.getNumero());
        assertNotNull(documento.getFechaEmisionComprobantes());
    }
}
