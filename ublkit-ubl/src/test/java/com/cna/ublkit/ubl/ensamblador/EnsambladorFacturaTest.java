package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.FormaDePago;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnsambladorFacturaTest {

    @Test
    void ensamblar_facturaMinima_calculaImpuestosYTotales() {
        BorradorFactura factura = crearFacturaMinima();

        EnsambladorFactura.ensamblar(factura);

        // Defectos aplicados
        assertEquals("PEN", factura.getMoneda());
        assertEquals("01", factura.getTipoComprobante());
        assertEquals("0101", factura.getTipoOperacion());
        assertNotNull(factura.getTasaIgv());

        // Línea ensamblada
        LineaDetalle linea = factura.getDetalles().getFirst();
        assertNotNull(linea.getIgvBaseImponible());
        assertEquals(0, new BigDecimal("200.00").compareTo(linea.getIgvBaseImponible()));
        assertNotNull(linea.getIgv());
        assertEquals(0, new BigDecimal("36.00").compareTo(linea.getIgv()));
        assertEquals("10", linea.getIgvTipo());
        assertNotNull(linea.getTotalImpuestos());
        assertEquals(0, new BigDecimal("36.00").compareTo(linea.getTotalImpuestos()));
        assertEquals("01", linea.getPrecioReferenciaTipo());

        // Totales
        TotalImpuestos ti = factura.getTotalImpuestos();
        assertNotNull(ti);
        assertEquals(0, new BigDecimal("36.00").compareTo(ti.total()));
        assertEquals(0, new BigDecimal("200.00").compareTo(ti.gravadoBaseImponible()));
        assertEquals(0, new BigDecimal("36.00").compareTo(ti.gravadoImporte()));

        TotalImporte tm = factura.getTotalImporte();
        assertNotNull(tm);
        assertEquals(0, new BigDecimal("200.00").compareTo(tm.importeSinImpuestos()));
        assertEquals(0, new BigDecimal("236.00").compareTo(tm.importeConImpuestos()));
        assertEquals(0, new BigDecimal("236.00").compareTo(tm.importe()));
    }

    @Test
    void ensamblar_boletaSerie_deduceTipoComprobante() {
        BorradorFactura boleta = crearFacturaMinima();
        boleta.setSerie("B001");

        EnsambladorFactura.ensamblar(boleta);

        assertEquals("03", boleta.getTipoComprobante());
    }

    @Test
    void ensamblar_generaLeyendaMontoEnLetras() {
        BorradorFactura factura = crearFacturaMinima();

        EnsambladorFactura.ensamblar(factura);

        assertNotNull(factura.getLeyendas());
        assertTrue(factura.getLeyendas().containsKey("1000"));
        assertTrue(factura.getLeyendas().get("1000").startsWith("SON: "));
    }

    @Test
    void ensamblar_boletaMenor700_sinReceptor_asignaConsumidorFinal() {
        BorradorFactura boleta = crearFacturaMinima();
        boleta.setSerie("B001");
        boleta.setReceptor(null);

        EnsambladorFactura.ensamblar(boleta);

        assertNotNull(boleta.getReceptor());
        assertEquals("0", boleta.getReceptor().tipoDocIdentidad());
        assertEquals("0", boleta.getReceptor().numDocIdentidad());
    }

    @Test
    void ensamblar_valoresYaCalculados_noSobrescribe() {
        BorradorFactura factura = crearFacturaMinima();

        // Proveer valores calculados manualmente (como fractuyo)
        LineaDetalle linea = factura.getDetalles().getFirst();
        linea.setIgvBaseImponible(new BigDecimal("300.00"));
        linea.setIgv(new BigDecimal("54.00"));
        linea.setTotalImpuestos(new BigDecimal("54.00"));

        factura.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("54.00"),
                new BigDecimal("54.00"), new BigDecimal("300.00"),
                null, null, null, null, null, null, null, null,
                null, null, null, null, null
        ));
        factura.setTotalImporte(new TotalImporte(
                new BigDecimal("354.00"),
                new BigDecimal("300.00"),
                new BigDecimal("354.00"),
                null, null
        ));

        EnsambladorFactura.ensamblar(factura);

        // No sobrescribió los valores
        assertEquals(0, new BigDecimal("300.00").compareTo(linea.getIgvBaseImponible()));
        assertEquals(0, new BigDecimal("54.00").compareTo(linea.getIgv()));
        assertEquals(0, new BigDecimal("54.00").compareTo(factura.getTotalImpuestos().total()));
        assertEquals(0, new BigDecimal("354.00").compareTo(factura.getTotalImporte().importe()));
    }

    @Test
    void ensamblar_conVariasLineas_calculaTotalesCorrectamente() {
        BorradorFactura factura = crearFacturaMinima();

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Servicio B");
        linea2.setCantidad(new BigDecimal("3"));
        linea2.setPrecio(new BigDecimal("50.00"));

        factura.setDetalles(List.of(factura.getDetalles().getFirst(), linea2));

        EnsambladorFactura.ensamblar(factura);

        // Línea 1: 2 * 100 = 200, IGV = 36
        // Línea 2: 3 * 50 = 150, IGV = 27
        assertEquals(0, new BigDecimal("63.00").compareTo(factura.getTotalImpuestos().total()));
        assertEquals(0, new BigDecimal("350.00").compareTo(factura.getTotalImporte().importeSinImpuestos()));
        assertEquals(0, new BigDecimal("413.00").compareTo(factura.getTotalImporte().importe()));
    }

    @Test
    void ensamblar_precioConImpuestos_calculaCorrectamente() {
        BorradorFactura factura = crearFacturaMinima();
        LineaDetalle linea = factura.getDetalles().getFirst();
        linea.setPrecio(new BigDecimal("118.00"));
        linea.setPrecioConImpuestos(true);
        linea.setCantidad(BigDecimal.ONE);

        EnsambladorFactura.ensamblar(factura);

        // 118 / 1.18 = 100.00
        assertEquals(0, new BigDecimal("100.00").compareTo(linea.getIgvBaseImponible()));
        assertEquals(0, new BigDecimal("18.00").compareTo(linea.getIgv()));
    }

    // ── Helper ───────────────────────────────────────────────────

    public static BorradorFactura crearFacturaMinima() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));

        factura.setEmisor(new EmisorDocumento(
                "20000000001",
                "Mi Empresa SAC",
                "Mi Empresa S.A.C.",
                new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
                        "Av. Principal 123", "PE"),
                null
        ));

        factura.setReceptor(new ReceptorDocumento(
                "6", "20100000002", "Cliente SAC",
                null, null
        ));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("2"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(List.of(linea));

        factura.setFormaDePago(new FormaDePago("Contado", null, null));

        return factura;
    }
}
