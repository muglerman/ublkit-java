package com.cna.ublkit.ubl.modelo.guia;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BorradorGuiaRemision Class Tests")
class BorradorGuiaRemisionTest {

    private BorradorGuiaRemision guia;

    @BeforeEach
    void setUp() {
        guia = new BorradorGuiaRemision();
    }

    @Test
    @DisplayName("Should create empty BorradorGuiaRemision")
    void testCrearGuiaVacia() {
        assertNotNull(guia);
        assertNull(guia.getTipoComprobante());
    }

    @Test
    @DisplayName("Should set and get serie")
    void testSerie() {
        guia.setSerie("T001");
        assertEquals("T001", guia.getSerie());
    }

    @Test
    @DisplayName("Should set and get numero")
    void testNumero() {
        guia.setNumero(100);
        assertEquals(100, guia.getNumero());
    }

    @Test
    @DisplayName("Should set and get fechaEmision")
    void testFechaEmision() {
        LocalDate fecha = LocalDate.of(2024, 12, 31);
        guia.setFechaEmision(fecha);
        assertEquals(fecha, guia.getFechaEmision());
    }

    @Test
    @DisplayName("Should set and get tipoComprobante")
    void testTipoComprobante() {
        guia.setTipoComprobante("09");
        assertEquals("09", guia.getTipoComprobante());
    }

    @Test
    @DisplayName("Should support GRE-Remitente (09)")
    void testGuiaRemitente() {
        guia.setTipoComprobante("09");
        guia.setSerie("T001");

        assertEquals("09", guia.getTipoComprobante());
        assertEquals("T001", guia.getSerie());
    }

    @Test
    @DisplayName("Should support GRE-Transportista (31)")
    void testGuiaTransportista() {
        guia.setTipoComprobante("31");
        guia.setSerie("V001");

        assertEquals("31", guia.getTipoComprobante());
        assertEquals("V001", guia.getSerie());
    }

    @Test
    @DisplayName("Should set and get emisor")
    void testEmisor() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789", "Mi Empresa", "MI EMPRESA S.A.C.", null, null
        );
        guia.setEmisor(emisor);
        assertEquals(emisor, guia.getEmisor());
    }

    @Test
    @DisplayName("Should set and get datosEnvio")
    void testDatosEnvio() {
        DatosEnvio datos = new DatosEnvio();
        guia.setDatosEnvio(datos);
        assertEquals(datos, guia.getDatosEnvio());
    }

    @Test
    @DisplayName("Should set and get lineas")
    void testLineas() {
        LineaGuia linea1 = new LineaGuia(
            "1", "Producto A", "001",
            new java.math.BigDecimal("10.00"), "KGM"
        );
        guia.setLineas(Arrays.asList(linea1));
        assertEquals(1, guia.getLineas().size());
    }

    @Test
    @DisplayName("Should set and get observaciones")
    void testObservaciones() {
        String obs = "Nota especial del documento";
        guia.setObservaciones(obs);
        assertEquals(obs, guia.getObservaciones());
    }

    @Test
    @DisplayName("Should handle multiple updates")
    void testMultiplesActualizaciones() {
        guia.setTipoComprobante("09");
        guia.setTipoComprobante("31");
        assertEquals("31", guia.getTipoComprobante());

        guia.setNumero(1);
        guia.setNumero(100);
        assertEquals(100, guia.getNumero());
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    void testValoresNulos() {
        assertNull(guia.getTipoComprobante());
        assertNull(guia.getSerie());
        assertNull(guia.getEmisor());
        assertNull(guia.getDatosEnvio());
    }

    @Test
    @DisplayName("Should support complete guia data")
    void testGuiaCompleta() {
        guia.setTipoComprobante("09");
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setFechaEmision(LocalDate.now());
        guia.setHoraEmision(LocalTime.now());

        assertEquals("09", guia.getTipoComprobante());
        assertEquals("T001", guia.getSerie());
        assertEquals(1, guia.getNumero());
        assertNotNull(guia.getFechaEmision());
    }
}
