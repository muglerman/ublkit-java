package com.cna.ublkit.ubl.modelo.complemento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentoRelacionado Record Tests")
class DocumentoRelacionadoTest {

    @Test
    @DisplayName("Should create DocumentoRelacionado with tipoDocumento and serieNumero")
    void testCrearDocumentoRelacionado() {
        DocumentoRelacionado documento = new DocumentoRelacionado(
            "01",
            "F001-123"
        );

        assertEquals("01", documento.tipoDocumento());
        assertEquals("F001-123", documento.serieNumero());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        DocumentoRelacionado doc1 = new DocumentoRelacionado("01", "F001-123");
        DocumentoRelacionado doc2 = new DocumentoRelacionado("01", "F001-123");

        assertEquals(doc1, doc2);
        assertEquals(doc1.hashCode(), doc2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct documents")
    void testDesigualdadPorDiferentesValores() {
        DocumentoRelacionado doc1 = new DocumentoRelacionado("01", "F001-123");
        DocumentoRelacionado doc2 = new DocumentoRelacionado("07", "F001-124");

        assertNotEquals(doc1, doc2);
    }

    @Test
    @DisplayName("Should support factura type")
    void testTipoFactura() {
        DocumentoRelacionado documento = new DocumentoRelacionado("01", "F001-123");
        assertEquals("01", documento.tipoDocumento());
    }

    @Test
    @DisplayName("Should support boleta type")
    void testTipoBoleta() {
        DocumentoRelacionado documento = new DocumentoRelacionado("03", "B001-456");
        assertEquals("03", documento.tipoDocumento());
    }

    @Test
    @DisplayName("Should support nota de credito type")
    void testTipoNotaCredito() {
        DocumentoRelacionado documento = new DocumentoRelacionado("07", "NC-001-789");
        assertEquals("07", documento.tipoDocumento());
    }

    @Test
    @DisplayName("Should preserve serieNumero format")
    void testPreservaSerieNumero() {
        String serieNumero = "F001-123456";
        DocumentoRelacionado documento = new DocumentoRelacionado("01", serieNumero);
        assertEquals(serieNumero, documento.serieNumero());
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        DocumentoRelacionado documento = new DocumentoRelacionado("01", "F001-123");
        String toString = documento.toString();

        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values")
    void testValoresNulos() {
        DocumentoRelacionado documento = new DocumentoRelacionado(null, null);
        assertNull(documento.tipoDocumento());
        assertNull(documento.serieNumero());
    }

    @Test
    @DisplayName("Should support various serie formats")
    void testVariasFormatosSerie() {
        String[] serieNumeros = {
            "F001-1",
            "F001-123",
            "F001-12345",
            "B001-999",
            "NC-001-1"
        };

        for (String serie : serieNumeros) {
            DocumentoRelacionado documento = new DocumentoRelacionado("01", serie);
            assertEquals(serie, documento.serieNumero());
        }
    }
}
