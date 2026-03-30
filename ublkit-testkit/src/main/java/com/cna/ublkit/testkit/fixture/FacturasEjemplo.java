package com.cna.ublkit.testkit.fixture;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Provee instancias pre-configuradas de {@link BorradorFactura} para facilitar
 * las pruebas unitarias y de integración.
 *
 * @since 0.1.0
 */
public final class FacturasEjemplo {

    private FacturasEjemplo() {}

    /**
     * Crea una factura mínima válida para venta interna (PEN).
     *
     * @return instancia de BorradorFactura
     */
    public static BorradorFactura facturaMinima() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setTipoComprobante("01");
        factura.setFechaEmision(LocalDate.now());
        factura.setMoneda("PEN");

        factura.setEmisor(emisorEstandar());
        factura.setReceptor(receptorEstandar());

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto de prueba");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("NIU");
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(List.of(linea));

        factura.setTotalImporte(new TotalImporte(
                new BigDecimal("118.00"),
                new BigDecimal("100.00"),
                new BigDecimal("118.00"),
                null, null
        ));

        factura.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("18.00"),
                new BigDecimal("18.00"),
                new BigDecimal("100.00"),
                null, null, null, null, null, null, null, null, null, null, null, null, null
        ));

        factura.setLeyendas(Map.of("1000", "SON: CIENTO DIECIOCHO CON 00/100 SOLES"));

        return factura;
    }

    private static EmisorDocumento emisorEstandar() {
        return new EmisorDocumento(
                "20123456789",
                "EMPRESA PRUEBA S.A.C.",
                "EMPRESA PRUEBA S.A.C.",
                new Direccion("150101", "0000", null, "LIMA", "LIMA", "LIMA", "AV. SIEMPRE VIVA 123", "PE"),
                null
        );
    }

    private static ReceptorDocumento receptorEstandar() {
        return new ReceptorDocumento(
                "6", "20999999999",
                "CLIENTE DE PRUEBA S.A.",
                new Direccion(null, null, null, null, null, null, "CALLE SECUNDARIA 456", "PE"),
                null
        );
    }
}
