package com.cna.ublkit.validation.factory;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.validation.builder.FacturaBuilder;
import com.cna.ublkit.validation.builder.LineaDetalleBuilder;
import com.cna.ublkit.validation.builder.NotaCreditoBuilder;
import com.cna.ublkit.validation.builder.NotaDebitoBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Factory con métodos convenientes para crear datos de prueba para validadores.
 *
 * Ejemplo:
 * <pre>
 * BorradorFactura factura = ValidatorTestFactory.facturaValida();
 * BorradorFactura boleta = ValidatorTestFactory.boletaValida();
 * LineaDetalle linea = ValidatorTestFactory.lineaDetalle();
 * </pre>
 */
public class ValidatorTestFactory {

    /**
     * Crea una factura válida con valores por defecto.
     */
    public static BorradorFactura facturaValida() {
        return new FacturaBuilder()
            .conSerie("F001")
            .conNumero(1)
            .conTipoComprobante("01")
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .conReceptor("20100000002", "Cliente SAC")
            .agregarLineaPorDefecto()
            .build();
    }

    /**
     * Crea una boleta válida con valores por defecto.
     */
    public static BorradorFactura boletaValida() {
        return new FacturaBuilder()
            .conSerie("B001")
            .conNumero(1)
            .conTipoComprobante("03")
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .conReceptor("6", "Consumidor Final")
            .agregarLineaPorDefecto()
            .build();
    }

    /**
     * Crea una boleta válida menor a 700 (sin receptor requerido).
     */
    public static BorradorFactura boletaMenor700() {
        return new FacturaBuilder()
            .conSerie("B001")
            .conNumero(1)
            .conTipoComprobante("03")
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .sinReceptor()
            .agregarLinea(new LineaDetalleBuilder()
                .conDescripcion("Producto A")
                .conCantidad("1")
                .conPrecio("100.00")
                .build())
            .build();
    }

    /**
     * Crea una boleta válida mayor a 700 (requiere receptor).
     */
    public static BorradorFactura boletaMayorIgual700() {
        return new FacturaBuilder()
            .conSerie("B001")
            .conNumero(1)
            .conTipoComprobante("03")
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .conReceptor("20100000002", "Cliente SAC")
            .agregarLinea(new LineaDetalleBuilder()
                .conDescripcion("Producto A")
                .conCantidad("8")
                .conPrecio("100.00")
                .build())
            .build();
    }

    /**
     * Crea una nota de crédito válida.
     */
    public static BorradorNotaCredito notaCreditoValida() {
        return new NotaCreditoBuilder()
            .conSerie("F001")
            .conNumero(1)
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .conReceptor("20100000002", "Cliente SAC")
            .conSustentoDescripcion("Devolución de mercancía")
            .agregarLinea(new LineaDetalleBuilder()
                .conDescripcion("Producto A")
                .conCantidad("1")
                .conPrecio("50.00")
                .build())
            .build();
    }

    /**
     * Crea una nota de débito válida.
     */
    public static BorradorNotaDebito notaDebitoValida() {
        return new NotaDebitoBuilder()
            .conSerie("F001")
            .conNumero(1)
            .conEmisor("20000000001", "Mi Empresa S.A.C.")
            .conReceptor("20100000002", "Cliente SAC")
            .conSustentoDescripcion("Ajuste por cobranza adicional")
            .agregarLinea(new LineaDetalleBuilder()
                .conDescripcion("Producto A")
                .conCantidad("1")
                .conPrecio("50.00")
                .build())
            .build();
    }

    /**
     * Crea una línea de detalle válida.
     */
    public static LineaDetalle lineaDetalle() {
        return new LineaDetalleBuilder()
            .conDescripcion("Producto A")
            .conCantidad("1")
            .conPrecio("100.00")
            .build();
    }

    /**
     * Crea una línea de detalle personalizada.
     */
    public static LineaDetalle lineaDetalle(String descripcion, String cantidad, String precio) {
        return new LineaDetalleBuilder()
            .conDescripcion(descripcion)
            .conCantidad(cantidad)
            .conPrecio(precio)
            .build();
    }

    /**
     * Crea múltiples líneas con la misma especificación.
     */
    public static List<LineaDetalle> lineasDetalle(int cantidad, String descripcion, String precioUnitario) {
        return List.of(
            new LineaDetalleBuilder()
                .conDescripcion(descripcion)
                .conCantidad(String.valueOf(cantidad))
                .conPrecio(precioUnitario)
                .build()
        );
    }
}
