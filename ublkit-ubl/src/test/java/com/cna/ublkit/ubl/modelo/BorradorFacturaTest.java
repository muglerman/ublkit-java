package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BorradorFactura Class Tests")
class BorradorFacturaTest {

    private BorradorFactura factura;

    @BeforeEach
    void setUp() {
        factura = new BorradorFactura();
    }

    @Test
    @DisplayName("Should create empty BorradorFactura")
    void testCrearFacturaVacia() {
        assertNotNull(factura);
        assertNull(factura.getTipoComprobante());
    }

    @Test
    @DisplayName("Should set and get tipoComprobante")
    void testTipoComprobante() {
        factura.setTipoComprobante("01");
        assertEquals("01", factura.getTipoComprobante());
    }

    @Test
    @DisplayName("Should set and get tipoOperacion")
    void testTipoOperacion() {
        factura.setTipoOperacion("0101");
        assertEquals("0101", factura.getTipoOperacion());
    }

    @Test
    @DisplayName("Should set and get serie and numero")
    void testSerieNumero() {
        factura.setSerie("F001");
        factura.setNumero(123);

        assertEquals("F001", factura.getSerie());
        assertEquals(123, factura.getNumero());
    }

    @Test
    @DisplayName("Should set and get fechaEmision")
    void testFechaEmision() {
        LocalDate fecha = LocalDate.of(2024, 12, 31);
        factura.setFechaEmision(fecha);
        assertEquals(fecha, factura.getFechaEmision());
    }

    @Test
    @DisplayName("Should set and get moneda")
    void testMoneda() {
        factura.setMoneda("PEN");
        assertEquals("PEN", factura.getMoneda());
    }

    @Test
    @DisplayName("Should set and get emisor")
    void testEmisor() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789", "Mi Empresa", "MI EMPRESA S.A.C.", null, null
        );
        factura.setEmisor(emisor);
        assertEquals(emisor, factura.getEmisor());
    }

    @Test
    @DisplayName("Should set and get receptor")
    void testReceptor() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6", "20987654321", "CLIENTE S.A.C.", null, null
        );
        factura.setReceptor(receptor);
        assertEquals(receptor, factura.getReceptor());
    }

    @Test
    @DisplayName("Should set and get tasaIgv")
    void testTasaIgv() {
        BigDecimal tasa = new BigDecimal("0.18");
        factura.setTasaIgv(tasa);
        assertEquals(0, tasa.compareTo(factura.getTasaIgv()));
    }

    @Test
    @DisplayName("Should set and get detalles")
    void testDetalles() {
        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Producto A");
        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Producto B");

        factura.setDetalles(Arrays.asList(linea1, linea2));
        assertEquals(2, factura.getDetalles().size());
    }

    @Test
    @DisplayName("Should set and get formaDePago")
    void testFormaDePago() {
        FormaDePago forma = new FormaDePago(
            "Contado", new BigDecimal("1000.00"), null
        );
        factura.setFormaDePago(forma);
        assertEquals(forma, factura.getFormaDePago());
    }

    @Test
    @DisplayName("Should set and get totalImporte")
    void testTotalImporte() {
        TotalImporte total = new TotalImporte(
            new BigDecimal("1000.00"),
            new BigDecimal("800.00"),
            new BigDecimal("1000.00"),
            null, null
        );
        factura.setTotalImporte(total);
        assertEquals(total, factura.getTotalImporte());
    }

    @Test
    @DisplayName("Should set and get detraccion")
    void testDetraccion() {
        Detraccion detraccion = new Detraccion(
            "01", "123-456-789012", "015",
            new BigDecimal("0.12"), new BigDecimal("240.00")
        );
        factura.setDetraccion(detraccion);
        assertEquals(detraccion, factura.getDetraccion());
    }

    @Test
    @DisplayName("Should set and get percepcion")
    void testPercepcion() {
        Percepcion percepcion = new Percepcion(
            "02", new BigDecimal("2000.00"),
            new BigDecimal("0.03"), new BigDecimal("60.00"),
            new BigDecimal("2060.00")
        );
        factura.setPercepcion(percepcion);
        assertEquals(percepcion, factura.getPercepcion());
    }

    @Test
    @DisplayName("Should set and get anticipos")
    void testAnticipos() {
        Anticipo anticipo = new Anticipo(
            "04", "F001-123", "12", new BigDecimal("500.00")
        );
        factura.setAnticipos(Arrays.asList(anticipo));
        assertEquals(1, factura.getAnticipos().size());
    }

    @Test
    @DisplayName("Should set and get descuentos")
    void testDescuentos() {
        Descuento descuento = new Descuento(
            "10", new BigDecimal("100.00"),
            new BigDecimal("0.10"), new BigDecimal("1000.00")
        );
        factura.setDescuentos(Arrays.asList(descuento));
        assertEquals(1, factura.getDescuentos().size());
    }

    @Test
    @DisplayName("Should set and get observaciones")
    void testObservaciones() {
        String obs = "Nota especial del documento";
        factura.setObservaciones(obs);
        assertEquals(obs, factura.getObservaciones());
    }

    @Test
    @DisplayName("Should support factura complete data")
    void testFacturaCompleta() {
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.now());

        assertEquals("01", factura.getTipoComprobante());
        assertEquals("0101", factura.getTipoOperacion());
        assertEquals("F001", factura.getSerie());
        assertEquals(1, factura.getNumero());
    }

    @Test
    @DisplayName("Should support boleta (tipo 03)")
    void testBoleta() {
        factura.setTipoComprobante("03");
        factura.setSerie("B001");

        assertEquals("03", factura.getTipoComprobante());
        assertEquals("B001", factura.getSerie());
    }

    @Test
    @DisplayName("Should handle multiple updates to fields")
    void testMultiplesActualizaciones() {
        factura.setTipoComprobante("01");
        factura.setTipoComprobante("03");
        assertEquals("03", factura.getTipoComprobante());

        factura.setNumero(1);
        factura.setNumero(100);
        assertEquals(100, factura.getNumero());
    }

    @Test
    @DisplayName("Should inherit from DocumentoBase")
    void testHeredaDeDocumentoBase() {
        factura.setOrdenDeCompra("PO-2024-001");
        assertEquals("PO-2024-001", factura.getOrdenDeCompra());
    }
}
