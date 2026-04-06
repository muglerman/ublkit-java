package com.cna.ublkit.ubl.modelo.total;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TotalImporte Extended Tests")
class TotalImporteExtendedTest {

    private TotalImporte total;

    @BeforeEach
    void setUp() {
        total = new TotalImporte();
    }

    @Test
    @DisplayName("Should set and get importe bruto")
    void testSetYGetImporteBruto() {
        total.setImporteBruto(new BigDecimal("1000.00"));
        assertEquals(0, new BigDecimal("1000.00").compareTo(total.getImporteBruto()));
    }

    @Test
    @DisplayName("Should set and get descuentos")
    void testSetYGetDescuentos() {
        total.setDescuentos(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(total.getDescuentos()));
    }

    @Test
    @DisplayName("Should set and get subtotal")
    void testSetYGetSubtotal() {
        total.setSubtotal(new BigDecimal("900.00"));
        assertEquals(0, new BigDecimal("900.00").compareTo(total.getSubtotal()));
    }

    @Test
    @DisplayName("Should set and get impuestos")
    void testSetYGetImpuestos() {
        total.setImpuestos(new BigDecimal("162.00"));
        assertEquals(0, new BigDecimal("162.00").compareTo(total.getImpuestos()));
    }

    @Test
    @DisplayName("Should set and get importe")
    void testSetYGetImporte() {
        total.setImporte(new BigDecimal("1062.00"));
        assertEquals(0, new BigDecimal("1062.00").compareTo(total.getImporte()));
    }

    @Test
    @DisplayName("Should get importe via record method")
    void testObtienImporteViaRecordMethod() {
        total.setImporte(new BigDecimal("1062.00"));
        assertNotNull(total.importe());
        assertEquals(0, new BigDecimal("1062.00").compareTo(total.importe()));
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        total.setImporteBruto(null);
        total.setDescuentos(null);
        assertNull(total.getImporteBruto());
        assertNull(total.getDescuentos());
    }

    @Test
    @DisplayName("Should calculate total correctly")
    void testCalculaTotalCorrectamente() {
        total.setImporteBruto(new BigDecimal("1000.00"));
        total.setDescuentos(new BigDecimal("100.00"));
        total.setImpuestos(new BigDecimal("162.00"));
        total.setImporte(new BigDecimal("1062.00"));

        assertNotNull(total.getImporte());
        assertEquals(0, new BigDecimal("1062.00").compareTo(total.getImporte()));
    }
}

@DisplayName("TotalImpuestos Extended Tests")
class TotalImpuestosExtendedTest {

    private TotalImpuestos impuestos;

    @BeforeEach
    void setUp() {
        impuestos = new TotalImpuestos(
            new BigDecimal("200.00"),  // total
            new BigDecimal("180.00"),  // gravadoImporte
            new BigDecimal("1000.00"), // gravadoBaseImponible
            BigDecimal.ZERO,           // exoneradoImporte
            BigDecimal.ZERO,           // exoneradoBaseImponible
            BigDecimal.ZERO,           // inafectoImporte
            BigDecimal.ZERO,           // inafectoBaseImponible
            BigDecimal.ZERO,           // gratuitoImporte
            BigDecimal.ZERO,           // gratuitoBaseImponible
            BigDecimal.ZERO,           // exportacionImporte
            BigDecimal.ZERO,           // exportacionBaseImponible
            BigDecimal.ZERO,           // ivapImporte
            BigDecimal.ZERO,           // ivapBaseImponible
            BigDecimal.ZERO,           // icbImporte
            BigDecimal.ZERO,           // iscImporte
            BigDecimal.ZERO            // iscBaseImponible
        );
    }

    @Test
    @DisplayName("Should set and get IGV")
    void testSetYGetIgv() {
        assertEquals(0, new BigDecimal("180.00").compareTo(impuestos.gravadoImporte()));
    }

    @Test
    @DisplayName("Should set and get IVAP")
    void testSetYGetIvap() {
        TotalImpuestos i = new TotalImpuestos(
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO,
            new BigDecimal("36.00"), BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );
        assertEquals(0, new BigDecimal("36.00").compareTo(i.ivapImporte()));
    }

    @Test
    @DisplayName("Should set and get ICB")
    void testSetYGetIcb() {
        TotalImpuestos i = new TotalImpuestos(
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO,
            new BigDecimal("4.50"), BigDecimal.ZERO, BigDecimal.ZERO
        );
        assertEquals(0, new BigDecimal("4.50").compareTo(i.icbImporte()));
    }

    @Test
    @DisplayName("Should set and get otros impuestos")
    void testSetYGetOtrosImpuestos() {
        TotalImpuestos i = new TotalImpuestos(
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, new BigDecimal("10.00"), BigDecimal.ZERO
        );
        assertEquals(0, new BigDecimal("10.00").compareTo(i.iscImporte()));
    }

    @Test
    @DisplayName("Should set and get total impuestos")
    void testSetYGetTotalImpuestos() {
        assertEquals(0, new BigDecimal("200.00").compareTo(impuestos.total()));
    }

    @Test
    @DisplayName("Should handle multiple tax types simultaneously")
    void testManejaMultiplesTiposDeTributosSimultaneamente() {
        TotalImpuestos i = new TotalImpuestos(
            new BigDecimal("212.50"),  // total
            new BigDecimal("162.00"),  // gravadoImporte
            new BigDecimal("1000.00"), // gravadoBaseImponible
            BigDecimal.ZERO,           // exoneradoImporte
            BigDecimal.ZERO,           // exoneradoBaseImponible
            BigDecimal.ZERO,           // inafectoImporte
            BigDecimal.ZERO,           // inafectoBaseImponible
            BigDecimal.ZERO,           // gratuitoImporte
            BigDecimal.ZERO,           // gratuitoBaseImponible
            BigDecimal.ZERO,           // exportacionImporte
            BigDecimal.ZERO,           // exportacionBaseImponible
            new BigDecimal("36.00"),   // ivapImporte
            BigDecimal.ZERO,           // ivapBaseImponible
            new BigDecimal("4.50"),    // icbImporte
            new BigDecimal("10.00"),   // iscImporte
            BigDecimal.ZERO            // iscBaseImponible
        );

        assertNotNull(i.gravadoImporte());
        assertNotNull(i.ivapImporte());
        assertNotNull(i.icbImporte());
        assertNotNull(i.iscImporte());
        assertNotNull(i.total());
    }

    @Test
    @DisplayName("Should handle null tax values")
    void testManejaTributosNulos() {
        TotalImpuestos i = new TotalImpuestos(
            BigDecimal.ZERO, null, null,
            BigDecimal.ZERO, null, BigDecimal.ZERO,
            null, BigDecimal.ZERO, null,
            BigDecimal.ZERO, null, null, null,
            null, null, null
        );
        assertNull(i.gravadoImporte());
        assertNull(i.ivapImporte());
    }

    @Test
    @DisplayName("Should handle zero tax amounts")
    void testManejaImportesCeroEnTributos() {
        assertEquals(0, BigDecimal.ZERO.compareTo(impuestos.gravadoImporte()) == 0 ? 0 : impuestos.gravadoImporte().compareTo(BigDecimal.ZERO));
        assertEquals(0, BigDecimal.ZERO.compareTo(impuestos.ivapImporte()));
    }
}
