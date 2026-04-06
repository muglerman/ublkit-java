package com.cna.ublkit.ubl.modelo.linea;

import com.cna.ublkit.ubl.modelo.guia.builder.*;
import com.cna.ublkit.ubl.modelo.guia.fixture.GuiaTestFactories;
import com.cna.ublkit.ubl.modelo.guia.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LineaDetalle Builder Tests")
class LineaDetalleBuilderTest {

    @Test
    @DisplayName("Should create linea detalle using factory")
    void testCreaLineaDetalleConFactory() {
        LineaGuia linea = GuiaTestFactories.lineaGuia("ART001", "Artículo 1", new BigDecimal("150.50"));

        assertEquals("ART001", linea.codigo());
        assertEquals("Artículo 1", linea.descripcion());
        assertEquals(0, new BigDecimal("150.50").compareTo(linea.cantidad()));
    }

    @Test
    @DisplayName("Should create multiple lineas with atributos")
    void testCreaMultiplesLineasConAtributos() {
        LineaGuia linea1 = LineaGuiaBuilder.aLineaGuia()
                .withCodigo("ART001")
                .withDescripcion("Artículo 1")
                .withCantidad(new BigDecimal("100"))
                .withCodigoSunat("30000000")
                .addAtributo(GuiaTestFactories.atributoItem("LOTE", "L-2026-001"))
                .build();

        LineaGuia linea2 = LineaGuiaBuilder.aLineaGuia()
                .withCodigo("ART002")
                .withDescripcion("Artículo 2")
                .withCantidad(new BigDecimal("200"))
                .addAtributo(GuiaTestFactories.atributoItem("VENCIMIENTO", "2027-12-31"))
                .build();

        assertEquals("ART001", linea1.codigo());
        assertEquals("ART002", linea2.codigo());
        assertEquals(1, linea1.atributos().size());
        assertEquals(1, linea2.atributos().size());
    }

    @Test
    @DisplayName("Should create linea with multiple attributes")
    void testCreaLineaConMultiplesAtributos() {
        LineaGuia linea = LineaGuiaBuilder.aLineaGuia()
                .withCodigo("PROD-COMPLEJO")
                .withDescripcion("Producto Complejo")
                .withCantidad(new BigDecimal("50"))
                .addAtributo(GuiaTestFactories.atributoItem("LOTE", "L-001"))
                .addAtributo(GuiaTestFactories.atributoItem("VENCIMIENTO", "2025-12-31"))
                .addAtributo(GuiaTestFactories.atributoItem("CERTIFICADO", "CERT-2026-001"))
                .build();

        assertEquals(3, linea.atributos().size());
        assertTrue(linea.atributos().stream()
                .anyMatch(a -> "LOTE".equals(a.codigo()))
        );
    }

    @Test
    @DisplayName("Should validate quantity formats")
    void testValidaCantidades() {
        LineaGuia linea = LineaGuiaBuilder.aLineaGuia()
                .withCantidad(new BigDecimal("100.50"))
                .build();

        assertEquals(0, new BigDecimal("100.50").compareTo(linea.cantidad()));
    }
}
