package com.cna.ublkit.ubl.modelo.total;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TotalImpuestos Record Tests")
class TotalImpuestosTest {

    private BigDecimal totalGeneral;
    private BigDecimal gravadoImporte;
    private BigDecimal gravadoBaseImponible;

    @BeforeEach
    void setUp() {
        totalGeneral = new BigDecimal("36.00");
        gravadoImporte = new BigDecimal("36.00");
        gravadoBaseImponible = new BigDecimal("200.00");
    }

    @Test
    @DisplayName("Should create TotalImpuestos with gravado only")
    void testCrearConGravado() {
        TotalImpuestos total = new TotalImpuestos(
            totalGeneral, gravadoImporte, gravadoBaseImponible,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        assertEquals(0, totalGeneral.compareTo(total.total()));
        assertEquals(0, gravadoImporte.compareTo(total.gravadoImporte()));
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        TotalImpuestos total1 = new TotalImpuestos(
            totalGeneral, gravadoImporte, gravadoBaseImponible,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        TotalImpuestos total2 = new TotalImpuestos(
            totalGeneral, gravadoImporte, gravadoBaseImponible,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        assertEquals(total1, total2);
    }

    @Test
    @DisplayName("Should create TotalImpuestos with multiple tax categories")
    void testConMultiplesCategorias() {
        TotalImpuestos total = new TotalImpuestos(
            new BigDecimal("336.00"),      // total
            new BigDecimal("36.00"),       // gravadoImporte
            new BigDecimal("200.00"),      // gravadoBaseImponible
            new BigDecimal("100.00"),      // exoneradoImporte
            new BigDecimal("100.00"),      // exoneradoBaseImponible
            null, null, null, null,
            null, null, null, null, null, null, null
        );

        assertNotNull(total.gravadoImporte());
        assertNotNull(total.exoneradoImporte());
    }

    @Test
    @DisplayName("Should support all tax types")
    void testTodosTiposImpuestos() {
        TotalImpuestos total = new TotalImpuestos(
            new BigDecimal("400.00"),      // total
            new BigDecimal("36.00"),       // gravadoImporte
            new BigDecimal("200.00"),      // gravadoBaseImponible
            new BigDecimal("100.00"),      // exoneradoImporte
            new BigDecimal("100.00"),      // exoneradoBaseImponible
            new BigDecimal("50.00"),       // inafectoImporte
            new BigDecimal("100.00"),      // inafectoBaseImponible
            new BigDecimal("50.00"),       // gratuitoImporte
            new BigDecimal("100.00"),      // gratuitoBaseImponible
            new BigDecimal("60.00"),       // exportacionImporte
            new BigDecimal("100.00"),      // exportacionBaseImponible
            new BigDecimal("8.00"),        // ivapImporte
            new BigDecimal("100.00"),      // ivapBaseImponible
            new BigDecimal("2.00"),        // icbImporte
            new BigDecimal("10.00"),       // iscImporte
            new BigDecimal("100.00")       // iscBaseImponible
        );

        assertNotNull(total.gravadoImporte());
        assertNotNull(total.exoneradoImporte());
        assertNotNull(total.inafectoImporte());
    }

    @Test
    @DisplayName("Should preserve precision of all amounts")
    void testPreservaPrecision() {
        BigDecimal precioConDecimales = new BigDecimal("123.456789");
        TotalImpuestos total = new TotalImpuestos(
            precioConDecimales, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        assertEquals(0, precioConDecimales.compareTo(total.total()));
    }

    @Test
    @DisplayName("Should handle zero total")
    void testTotalZero() {
        TotalImpuestos total = new TotalImpuestos(
            BigDecimal.ZERO, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null
        );

        assertEquals(0, BigDecimal.ZERO.compareTo(total.total()));
    }

    @Test
    @DisplayName("Should handle large values")
    void testValoresGrandes() {
        BigDecimal montoGrande = new BigDecimal("999999999.99");
        TotalImpuestos total = new TotalImpuestos(
            montoGrande, montoGrande, montoGrande,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        assertEquals(0, montoGrande.compareTo(total.total()));
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        TotalImpuestos total = new TotalImpuestos(
            totalGeneral, gravadoImporte, gravadoBaseImponible,
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        String toString = total.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle all null optional fields")
    void testTodosCamposNulos() {
        TotalImpuestos total = new TotalImpuestos(
            new BigDecimal("100.00"),
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null
        );

        assertEquals(0, new BigDecimal("100.00").compareTo(total.total()));
        assertNull(total.gravadoImporte());
    }

    @Test
    @DisplayName("Should support ICBPER calculation")
    void testSoportaIcbper() {
        TotalImpuestos total = new TotalImpuestos(
            new BigDecimal("37.50"),       // total (36.00 IGV + 1.50 ICB)
            new BigDecimal("36.00"),       // gravadoImporte
            new BigDecimal("200.00"),      // gravadoBaseImponible
            null, null, null, null, null, null,
            null, null, null, null,
            new BigDecimal("1.50"),        // icbImporte
            null, null
        );

        assertEquals(0, new BigDecimal("1.50").compareTo(total.icbImporte()));
    }

    @Test
    @DisplayName("Should support ISC calculation")
    void testSoportaIsc() {
        TotalImpuestos total = new TotalImpuestos(
            new BigDecimal("46.00"),       // total (36.00 IGV + 10.00 ISC)
            new BigDecimal("36.00"),       // gravadoImporte
            new BigDecimal("200.00"),      // gravadoBaseImponible
            null, null, null, null, null, null,
            null, null, null, null, null,
            new BigDecimal("10.00"),       // iscImporte
            new BigDecimal("100.00")       // iscBaseImponible
        );

        assertEquals(0, new BigDecimal("10.00").compareTo(total.iscImporte()));
    }
}
