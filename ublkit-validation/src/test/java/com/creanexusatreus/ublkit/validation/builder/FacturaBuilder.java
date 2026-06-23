package com.cna.ublkit.validation.builder;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder para construir instancias de BorradorFactura de forma fluida.
 *
 * Ejemplo:
 * <pre>
 * BorradorFactura factura = new FacturaBuilder()
 *     .conSerie("F001")
 *     .conNumero(1)
 *     .conTipoComprobante("01")
 *     .conEmisor("20000000001", "Mi Empresa S.A.C.")
 *     .conReceptor("20100000002", "Cliente SAC")
 *     .agregarLinea(new LineaDetalleBuilder()
 *         .conDescripcion("Producto A")
 *         .conCantidad("2")
 *         .conPrecio("100.00")
 *         .build())
 *     .build();
 * </pre>
 */
public class FacturaBuilder {

    private String serie = "F001";
    private Integer numero = 1;
    private String tipoComprobante = "01"; // 01=Factura, 03=Boleta
    private LocalDate fechaEmision = LocalDate.now();
    private String moneda = "PEN";
    private EmisorDocumento emisor;
    private ReceptorDocumento receptor;
    private List<LineaDetalle> detalles = new ArrayList<>();

    public FacturaBuilder() {
        this.emisor = new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa", null, null);
        this.receptor = new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null);
    }

    public FacturaBuilder conSerie(String serie) {
        this.serie = serie;
        return this;
    }

    public FacturaBuilder conNumero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public FacturaBuilder conTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        return this;
    }

    public FacturaBuilder conFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public FacturaBuilder conMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

    public FacturaBuilder conEmisor(String ruc, String razonSocial) {
        this.emisor = new EmisorDocumento(ruc, razonSocial, razonSocial, null, null);
        return this;
    }

    public FacturaBuilder conEmisor(EmisorDocumento emisor) {
        this.emisor = emisor;
        return this;
    }

    public FacturaBuilder conReceptor(String ruc, String razonSocial) {
        this.receptor = new ReceptorDocumento("6", ruc, razonSocial, null, null);
        return this;
    }

    public FacturaBuilder conReceptor(ReceptorDocumento receptor) {
        this.receptor = receptor;
        return this;
    }

    public FacturaBuilder sinReceptor() {
        this.receptor = null;
        return this;
    }

    public FacturaBuilder agregarLinea(LineaDetalle linea) {
        this.detalles.add(linea);
        return this;
    }

    public FacturaBuilder agregarLineas(List<LineaDetalle> lineas) {
        this.detalles.addAll(lineas);
        return this;
    }

    public FacturaBuilder conDetalles(List<LineaDetalle> detalles) {
        this.detalles = new ArrayList<>(detalles);
        return this;
    }

    public FacturaBuilder agregarLineaPorDefecto() {
        this.detalles.add(new LineaDetalleBuilder()
            .conDescripcion("Producto A")
            .conCantidad("2")
            .conPrecio("100.00")
            .build());
        return this;
    }

    public BorradorFactura build() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie(serie);
        factura.setNumero(numero);
        factura.setTipoComprobante(tipoComprobante);
        factura.setFechaEmision(fechaEmision);
        factura.setMoneda(moneda);
        factura.setEmisor(emisor);
        factura.setReceptor(receptor);

        if (detalles.isEmpty()) {
            factura.setDetalles(List.of(new LineaDetalleBuilder().build()));
        } else {
            factura.setDetalles(new ArrayList<>(detalles));
        }

        return factura;
    }
}
