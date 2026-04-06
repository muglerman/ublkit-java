package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DeclaracionAduanera Record Tests")
class DeclaracionAduaneraTest {

    @Test
    @DisplayName("Should create declaracion aduanera with all fields")
    void testCreaDeclaracionConTodosCampos() {
        DeclaracionAduanera declaracion = new DeclaracionAduanera(
                "2000123456",
                "04",
                LocalDate.of(2026, 4, 1)
        );

        assertEquals("2000123456", declaracion.numeroDeclaracion());
        assertEquals("04", declaracion.tipoDeclaracion());
        assertEquals(LocalDate.of(2026, 4, 1), declaracion.fechaDeclaracion());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        DeclaracionAduanera declaracion = new DeclaracionAduanera(null, null, null);

        assertNull(declaracion.numeroDeclaracion());
        assertNull(declaracion.tipoDeclaracion());
        assertNull(declaracion.fechaDeclaracion());
    }
}

@DisplayName("Puerto Record Tests")
class PuertoTest {

    @Test
    @DisplayName("Should create puerto with all fields")
    void testCreaPuertoConTodosCampos() {
        Puerto puerto = new Puerto(
                "CALLAO",
                "CALLAO"
        );

        assertEquals("CALLAO", puerto.ubigeo());
        assertEquals("CALLAO", puerto.nombre());
    }
}

@DisplayName("DocumentoAdicionalGuia Class Tests")
class DocumentoAdicionalGuiaTest {

    private DocumentoAdicionalGuia documento;

    @BeforeEach
    void setUp() {
        documento = new DocumentoAdicionalGuia();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        documento.setTipoDocumento("09");
        assertEquals("09", documento.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        documento.setNumeroDocumento("DOC123");
        assertEquals("DOC123", documento.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        documento.setDescripcion("Documento adicional");
        assertEquals("Documento adicional", documento.getDescripcion());
    }
}

@DisplayName("DocumentoBajaGuia Class Tests")
class DocumentoBajaGuiaTest {

    private DocumentoBajaGuia documento;

    @BeforeEach
    void setUp() {
        documento = new DocumentoBajaGuia();
    }

    @Test
    @DisplayName("Should set and get motivoBaja")
    void testSetYGetMotivoBaja() {
        documento.setMotivoBaja("01");
        assertEquals("01", documento.getMotivoBaja());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        documento.setNumeroDocumento("GRE123");
        assertEquals("GRE123", documento.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        documento.setDescripcion("Baja por error");
        assertEquals("Baja por error", documento.getDescripcion());
    }
}

@DisplayName("DocumentoRelacionadoGuia Class Tests")
class DocumentoRelacionadoGuiaTest {

    private DocumentoRelacionadoGuia documento;

    @BeforeEach
    void setUp() {
        documento = new DocumentoRelacionadoGuia();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        documento.setTipoDocumento("01");
        assertEquals("01", documento.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        documento.setNumeroDocumento("F001-123");
        assertEquals("F001-123", documento.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        documento.setDescripcion("Factura relacionada");
        assertEquals("Factura relacionada", documento.getDescripcion());
    }
}

@DisplayName("AtributoItem Class Tests")
class AtributoItemTest {

    private AtributoItem atributo;

    @BeforeEach
    void setUp() {
        atributo = new AtributoItem();
    }

    @Test
    @DisplayName("Should set and get nombre")
    void testSetYGetNombre() {
        atributo.setNombre("Color");
        assertEquals("Color", atributo.getNombre());
    }

    @Test
    @DisplayName("Should set and get valor")
    void testSetYGetValor() {
        atributo.setValor("Rojo");
        assertEquals("Rojo", atributo.getValor());
    }

    @Test
    @DisplayName("Should set and get opcion")
    void testSetYGetOpcion() {
        atributo.setOpcion("Estándar");
        assertEquals("Estándar", atributo.getOpcion());
    }
}

@DisplayName("Reversion Record Tests")
class ReversionTest {

    @Test
    @DisplayName("Should create reversion with fields")
    void testCreaReversionConCampos() {
        Reversion reversion = new Reversion(
                "F001-123",
                LocalDate.of(2026, 4, 5),
                "Anulación"
        );

        assertEquals("F001-123", reversion.numeroDocumento());
        assertEquals(LocalDate.of(2026, 4, 5), reversion.fecha());
        assertEquals("Anulación", reversion.tipo());
    }
}

@DisplayName("BorradorGuiaRemision Full Integration Tests")
class BorradorGuiaRemisionExtendedTest {

    private BorradorGuiaRemision guia;

    @BeforeEach
    void setUp() {
        guia = new BorradorGuiaRemision();
    }

    @Test
    @DisplayName("Should set and get tipoTransporte")
    void testSetYGetTipoTransporte() {
        guia.setTipoTransporte("01");
        assertEquals("01", guia.getTipoTransporte());
    }

    @Test
    @DisplayName("Should set and get pesoTotal")
    void testSetYGetPesoTotal() {
        guia.setPesoTotal(new BigDecimal("5000.00"));
        assertEquals(0, new BigDecimal("5000.00").compareTo(guia.getPesoTotal()));
    }

    @Test
    @DisplayName("Should set and get volumenTotal")
    void testSetYGetVolumenTotal() {
        guia.setVolumenTotal(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(guia.getVolumenTotal()));
    }

    @Test
    @DisplayName("Should handle complex guia structure")
    void testManejaEstructuraComplejaGuia() {
        guia.setSerie("T001");
        guia.setNumero(100);
        guia.setMoneda("PEN");
        guia.setFechaEmision(LocalDate.of(2026, 4, 6));
        guia.setTipoTransporte("01");

        assertEquals("T001", guia.getSerie());
        assertEquals(100, guia.getNumero());
        assertEquals("PEN", guia.getMoneda());
        assertNotNull(guia.getFechaEmision());
        assertEquals("01", guia.getTipoTransporte());
    }
}
