package com.cna.ublkit.validation.validador;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorFacturaTest {

    private final ValidadorFactura validador = new ValidadorFactura();

    @Test
    void validar_facturaValida_sinErrores() {
        BorradorFactura factura = crearFacturaValida();
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.esValido(), "Una factura válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinSerie_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setSerie(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-002".equals(i.codigo())));
    }

    @Test
    void validar_serieBoleta_conTipoFactura_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setSerie("B001");
        factura.setTipoComprobante("01");
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-003".equals(i.codigo())));
    }

    @Test
    void validar_serieFactura_conTipoBoleta_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setSerie("F001");
        factura.setTipoComprobante("03");
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-004".equals(i.codigo())));
    }

    @Test
    void validar_sinNumero_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setNumero(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-005".equals(i.codigo())));
    }

    @Test
    void validar_sinFechaEmision_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setFechaEmision(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-006".equals(i.codigo())));
    }

    @Test
    void validar_sinEmisor_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setEmisor(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-007".equals(i.codigo())));
    }

    @Test
    void validar_sinReceptor_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setReceptor(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-008".equals(i.codigo())));
    }

    @Test
    void validar_sinDetalles_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setDetalles(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-009".equals(i.codigo())));
    }

    @Test
    void validar_detalleSinCantidad_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.getDetalles().getFirst().setCantidad(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-009b".equals(i.codigo())));
    }

    @Test
    void validar_detalleSinPrecio_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.getDetalles().getFirst().setPrecio(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-009c".equals(i.codigo())));
    }

    @Test
    void validar_detalleSinDescripcion_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.getDetalles().getFirst().setDescripcion(null);
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-009d".equals(i.codigo())));
    }

    @Test
    void validar_boletaValida_sinErrores() {
        BorradorFactura boleta = crearFacturaValida();
        boleta.setSerie("B001");
        boleta.setTipoComprobante("03");
        boleta.setReceptor(new ReceptorDocumento("1", "12345678", "CONSUMIDOR", null, null));
        ResultadoValidacion resultado = validador.validar(boleta);
        assertTrue(resultado.esValido(), "Una boleta válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_tipoComprobanteInvalido_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setTipoComprobante("99");
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-006b".equals(i.codigo())));
    }

    @Test
    void validar_emisorRucInvalido_retornaError() {
        BorradorFactura factura = crearFacturaValida();
        factura.setEmisor(new EmisorDocumento("12345", "Empresa", "Empresa", null, null));
        ResultadoValidacion resultado = validador.validar(factura);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-007b".equals(i.codigo())));
    }

    @Test
    void validar_boletaMenor700_sinReceptor_esValida() {
        BorradorFactura boleta = crearFacturaValida();
        boleta.setSerie("B001");
        boleta.setTipoComprobante("03");
        boleta.setReceptor(null);

        ResultadoValidacion resultado = validador.validar(boleta);
        assertTrue(resultado.esValido(), "Boleta menor a 700 debe permitir receptor vacío");
    }

    @Test
    void validar_boletaMayorIgual700_sinReceptor_retornaError() {
        BorradorFactura boleta = crearFacturaValida();
        boleta.setSerie("B001");
        boleta.setTipoComprobante("03");
        boleta.setReceptor(null);
        boleta.getDetalles().getFirst().setCantidad(new BigDecimal("8"));
        boleta.getDetalles().getFirst().setPrecio(new BigDecimal("100.00"));

        ResultadoValidacion resultado = validador.validar(boleta);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-008".equals(i.codigo())));
    }

    // ── Helpers ──────────────────────────────────────────────────

    static BorradorFactura crearFacturaValida() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setTipoComprobante("01");
        factura.setFechaEmision(LocalDate.now());
        factura.setMoneda("PEN");
        factura.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa", null, null));
        factura.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("2"));
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setUnidadMedida("NIU");
        factura.setDetalles(List.of(linea));

        return factura;
    }
}
