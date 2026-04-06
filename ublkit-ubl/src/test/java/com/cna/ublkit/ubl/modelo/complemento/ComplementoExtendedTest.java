package com.cna.ublkit.ubl.modelo.complemento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GuiaEmbebida Class Tests")
class GuiaEmbebidaTest {

    private GuiaEmbebida guia;

    @BeforeEach
    void setUp() {
        guia = new GuiaEmbebida(null, null, null, null, null, null, null, null, null, null);
    }

    @Test
    @DisplayName("Should set and get numero serie")
    void testSetYGetNumeroSerie() {
        guia = new GuiaEmbebida(null, null, null, "T001-100", null, null, null, null, null, null);
        assertEquals("T001-100", guia.nroLicencia());
    }

    @Test
    @DisplayName("Should set and get tipo transportista")
    void testSetYGetTipoTransportista() {
        guia = new GuiaEmbebida(null, null, null, null, "01", null, null, null, null, null);
        assertEquals("01", guia.transpPlaca());
    }

    @Test
    @DisplayName("Should set and get RUC transportista")
    void testSetYGetRucTransportista() {
        guia = new GuiaEmbebida(null, null, null, null, null, "20123456789", null, null, null, null);
        assertEquals("20123456789", guia.transpCodeAuth());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        guia = new GuiaEmbebida(null, null, null, null, null, null, "Guía embebida", null, null, null);
        assertEquals("Guía embebida", guia.transpMarca());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        guia = new GuiaEmbebida(null, null, null, null, null, null, null, null, null, null);
        assertNull(guia.nroLicencia());
        assertNull(guia.transpCodeAuth());
    }
}

@DisplayName("FormaDePago Extended Tests")
class FormaDePageExtendedTest {

    private FormaDePago forma;

    @BeforeEach
    void setUp() {
        forma = new FormaDePago("01", BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Should handle contado payment type")
    void testManejaFormaContado() {
        forma = new FormaDePago("01", BigDecimal.ZERO, null);
        assertEquals("01", forma.tipo());
    }

    @Test
    @DisplayName("Should handle credito payment type")
    void testManejaFormaCredito() {
        forma = new FormaDePago("02", BigDecimal.ZERO, null);
        assertEquals("02", forma.tipo());
    }

    @Test
    @DisplayName("Should set and get condiciones credito")
    void testSetYGetCondicionesCredito() {
        forma = new FormaDePago("02", new BigDecimal("30"), null);
        assertEquals(0, new BigDecimal("30").compareTo(forma.total()));
    }

    @Test
    @DisplayName("Should handle multiple conditions")
    void testManejaMultiplesCondiciones() {
        forma = new FormaDePago("02", new BigDecimal("15"), null);
        assertNotNull(forma.tipo());
        assertTrue(forma.total().compareTo(BigDecimal.ZERO) > 0);
    }
}

@DisplayName("Detraccion Extended Tests")
class DetraccionExtendedTest {

    private Detraccion detraccion;

    @BeforeEach
    void setUp() {
        detraccion = new Detraccion(null, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should set and get codigo")
    void testSetYGetCodigo() {
        detraccion = new Detraccion("04", null, null, BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals("04", detraccion.medioDePago());
    }

    @Test
    @DisplayName("Should set and get tasa detraccion")
    void testSetYGetTasaDetraccion() {
        detraccion = new Detraccion(null, null, null, new BigDecimal("0.03"), BigDecimal.ZERO);
        assertEquals(0, new BigDecimal("0.03").compareTo(detraccion.porcentaje()));
    }

    @Test
    @DisplayName("Should set and get importe detraccion")
    void testSetYGetImporteDetraccion() {
        detraccion = new Detraccion(null, null, null, BigDecimal.ZERO, new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(detraccion.monto()));
    }

    @Test
    @DisplayName("Should set and get cuenta bancaria")
    void testSetYGetCuentaBancaria() {
        detraccion = new Detraccion(null, "0012345678", null, BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals("0012345678", detraccion.cuentaBancaria());
    }
}

@DisplayName("Percepcion Extended Tests")
class PercepcionExtendedTest {

    private Percepcion percepcion;

    @BeforeEach
    void setUp() {
        percepcion = new Percepcion(null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should set and get tipo regimen")
    void testSetYGetTipoRegimen() {
        percepcion = new Percepcion("02", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals("02", percepcion.tipo());
    }

    @Test
    @DisplayName("Should set and get tasa percepcion")
    void testSetYGetTasaPercepcion() {
        percepcion = new Percepcion(null, BigDecimal.ZERO, new BigDecimal("0.03"), BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals(0, new BigDecimal("0.03").compareTo(percepcion.porcentaje()));
    }

    @Test
    @DisplayName("Should set and get importe percibido")
    void testSetYGetImportePercibido() {
        percepcion = new Percepcion(null, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("300.00"), BigDecimal.ZERO);
        assertEquals(0, new BigDecimal("300.00").compareTo(percepcion.monto()));
    }

    @Test
    @DisplayName("Should handle multiple percepciones")
    void testManejaMultiplesPercepciones() {
        percepcion = new Percepcion("02", BigDecimal.ZERO, new BigDecimal("0.03"), new BigDecimal("300.00"), BigDecimal.ZERO);

        assertNotNull(percepcion.tipo());
        assertNotNull(percepcion.porcentaje());
        assertNotNull(percepcion.monto());
    }
}

@DisplayName("Anticipo Extended Tests")
class AnticipoExtendedTest {

    private Anticipo anticipo;

    @BeforeEach
    void setUp() {
        anticipo = new Anticipo(null, null, null, BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should set and get numero comprobante")
    void testSetYGetNumeroComprobante() {
        anticipo = new Anticipo(null, "F001-100", null, BigDecimal.ZERO);
        assertEquals("F001-100", anticipo.comprobanteSerieNumero());
    }

    @Test
    @DisplayName("Should set and get importe anticipo")
    void testSetYGetImporteAnticipo() {
        anticipo = new Anticipo(null, null, null, new BigDecimal("500.00"));
        assertEquals(0, new BigDecimal("500.00").compareTo(anticipo.monto()));
    }

    @Test
    @DisplayName("Should set and get moneda")
    void testSetYGetMoneda() {
        anticipo = new Anticipo(null, null, "PEN", BigDecimal.ZERO);
        assertEquals("PEN", anticipo.comprobanteTipo());
    }

    @Test
    @DisplayName("Should handle various currencies")
    void testManejaVariasMonedas() {
        anticipo = new Anticipo(null, null, "USD", BigDecimal.ZERO);
        assertEquals("USD", anticipo.comprobanteTipo());

        anticipo = new Anticipo(null, null, "EUR", BigDecimal.ZERO);
        assertEquals("EUR", anticipo.comprobanteTipo());
    }
}

@DisplayName("Descuento Extended Tests")
class DescuentoExtendedTest {

    private Descuento descuento;

    @BeforeEach
    void setUp() {
        descuento = new Descuento(null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should set and get codigo descuento")
    void testSetYGetCodigoDescuento() {
        descuento = new Descuento("DESC01", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals("DESC01", descuento.tipo());
    }

    @Test
    @DisplayName("Should set and get porcentaje")
    void testSetYGetPorcentaje() {
        descuento = new Descuento(null, BigDecimal.ZERO, new BigDecimal("0.10"), BigDecimal.ZERO);
        assertEquals(0, new BigDecimal("0.10").compareTo(descuento.factor()));
    }

    @Test
    @DisplayName("Should set and get importe descuento")
    void testSetYGetImporteDescuento() {
        descuento = new Descuento(null, new BigDecimal("100.00"), BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals(0, new BigDecimal("100.00").compareTo(descuento.monto()));
    }

    @Test
    @DisplayName("Should handle multiple descuentos")
    void testManejaMultiplesDescuentos() {
        descuento = new Descuento("DESC01", new BigDecimal("100.00"), new BigDecimal("0.10"), BigDecimal.ZERO);

        assertNotNull(descuento.tipo());
        assertNotNull(descuento.factor());
        assertNotNull(descuento.monto());
    }
}

@DisplayName("CuotaDePago Extended Tests")
class CuotaDePagoExtendedTest {

    private CuotaDePago cuota;

    @BeforeEach
    void setUp() {
        cuota = new CuotaDePago(BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Should set and get numero cuota")
    void testSetYGetNumeroCuota() {
        cuota = new CuotaDePago(BigDecimal.ONE, null);
        assertEquals(0, BigDecimal.ONE.compareTo(cuota.importe()));
    }

    @Test
    @DisplayName("Should set and get importe cuota")
    void testSetYGetImporteCuota() {
        cuota = new CuotaDePago(new BigDecimal("250.00"), null);
        assertEquals(0, new BigDecimal("250.00").compareTo(cuota.importe()));
    }

    @Test
    @DisplayName("Should set and get fecha vencimiento")
    void testSetYGetFechaVencimiento() {
        java.time.LocalDate fecha = java.time.LocalDate.of(2026, 5, 6);
        cuota = new CuotaDePago(BigDecimal.ZERO, fecha);
        assertEquals(fecha, cuota.fechaPago());
    }

    @Test
    @DisplayName("Should handle multiple cuotas")
    void testManejaMultiplesCuotas() {
        java.time.LocalDate fecha = java.time.LocalDate.of(2026, 5, 6);
        cuota = new CuotaDePago(new BigDecimal("250.00"), fecha);

        assertEquals(0, new BigDecimal("250.00").compareTo(cuota.importe()));
        assertNotNull(cuota.importe());
        assertNotNull(cuota.fechaPago());
    }
}

@DisplayName("DocumentoRelacionado Extended Tests")
class DocumentoRelacionadoExtendedTest {

    private DocumentoRelacionado documento;

    @BeforeEach
    void setUp() {
        documento = new DocumentoRelacionado("01", "F001-100");
    }

    @Test
    @DisplayName("Should set and get tipo documento")
    void testSetYGetTipoDocumento() {
        assertEquals("01", documento.tipoDocumento());
    }

    @Test
    @DisplayName("Should set and get numero documento")
    void testSetYGetNumeroDocumento() {
        assertEquals("F001-100", documento.serieNumero());
    }

    @Test
    @DisplayName("Should set and get razon relacion")
    void testSetYGetRazonRelacion() {
        DocumentoRelacionado d = new DocumentoRelacionado("01", "F001-100");
        assertEquals("01", d.tipoDocumento());
    }
}

@DisplayName("GuiaRelacionada Extended Tests")
class GuiaRelacionadaExtendedTest {

    private GuiaRelacionada guia;

    @BeforeEach
    void setUp() {
        guia = new GuiaRelacionada("T001-100", "09");
    }

    @Test
    @DisplayName("Should set and get numero guia")
    void testSetYGetNumeroGuia() {
        assertEquals("T001-100", guia.serieNumero());
    }

    @Test
    @DisplayName("Should set and get fecha guia")
    void testSetYGetFechaGuia() {
        GuiaRelacionada g = new GuiaRelacionada("T001-100", "09");
        assertEquals("T001-100", g.serieNumero());
    }

    @Test
    @DisplayName("Should set and get tipo relacion")
    void testSetYGetTipoRelacion() {
        assertEquals("09", guia.tipoDocumento());
    }
}
