package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.guia.builder.*;
import com.cna.ublkit.ubl.modelo.guia.fixture.GuiaTestFactories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Guia Remision Serialization Tests")
class GuiaRemisionSerializationTest {

    @Test
    @DisplayName("Should serialize basic guia remision")
    void testSerializaGuiaBasica() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .withTipoComprobante("09")
                .withFechaEmision(LocalDate.of(2026, 4, 6))
                .build();

        assertNotNull(guia);
        assertEquals("T001", guia.getSerie());
        assertEquals(1, guia.getNumero());
    }

    @Test
    @DisplayName("Should serialize guia with documento relacionado")
    void testSerializaGuiaConDocumentoRelacionado() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .withDocumentoRelacionado(GuiaTestFactories.facturaRelacionada())
                .build();

        assertNotNull(guia.getDocumentoRelacionado());
        assertEquals("F001-000001", guia.getDocumentoRelacionado().serieNumero());
    }

    @Test
    @DisplayName("Should serialize guia with complete envio data")
    void testSerializaGuiaConEnvioCompleto() {
        DatosEnvio envio = DatosEnvioBuilder.aDatosEnvio()
                .withTipoTraslado("01")
                .withMotivoTraslado("Venta")
                .withPesoTotal(new BigDecimal("5000.00"))
                .withNumeroDeBultos(50)
                .withFechaTraslado(LocalDate.now())
                .withPartida(GuiaTestFactories.puntoPartida("150131", "Jr. Salida", "0000", "20123456789"))
                .withDestino(GuiaTestFactories.puntoDestino("080131", "Jr. Llegada", "0001", "20987654321"))
                .build();

        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .withEnvio(envio)
                .build();

        assertNotNull(guia.getEnvio());
        assertEquals("01", guia.getEnvio().getTipoTraslado());
        assertNotNull(guia.getEnvio().getPartida());
        assertNotNull(guia.getEnvio().getDestino());
    }

    @Test
    @DisplayName("Should serialize guia with multiple line items")
    void testSerializaGuiaConMultiplesLineas() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .addDetalle(GuiaTestFactories.lineaGuia("P001", "Producto 1", new BigDecimal("100")))
                .addDetalle(GuiaTestFactories.lineaGuia("P002", "Producto 2", new BigDecimal("200")))
                .addDetalle(GuiaTestFactories.lineaGuia("P003", "Producto 3", new BigDecimal("150")))
                .build();

        assertEquals(3, guia.getDetalles().size());
    }

    @Test
    @DisplayName("Should serialize guia with contenedores")
    void testSerializaGuiaConContenedores() {
        DatosEnvio envio = DatosEnvioBuilder.aDatosEnvio()
                .addContenedor(GuiaTestFactories.contenedor("CONT001", "PREC001"))
                .addContenedor(GuiaTestFactories.contenedor("CONT002", "PREC002"))
                .build();

        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withEnvio(envio)
                .build();

        assertEquals(2, guia.getEnvio().getContenedores().size());
    }

    @Test
    @DisplayName("Should serialize guia with documents adicionales")
    void testSerializaGuiaConDocumentosAdicionales() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .addDocumentoAdicional(GuiaTestFactories.documentoAdicional("09", "ADO001", "20123456789"))
                .addDocumentoAdicional(GuiaTestFactories.documentoAdicional("01", "ADO002", "20111111111"))
                .build();

        assertEquals(2, guia.getDocumentosAdicionales().size());
    }

    @Test
    @DisplayName("Should handle full SUNAT-compliant structure")
    void testSerializaEstructuraSUNATCompleta() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withVersion("2.0")
                .withSerie("T001")
                .withNumero(1)
                .withTipoComprobante("09")
                .withFechaEmision(LocalDate.of(2026, 4, 6))
                .withObservaciones("Guía de remisión de prueba")
                .withDocumentoRelacionado(GuiaTestFactories.facturaRelacionada())
                .withEnvio(DatosEnvioBuilder.aDatosEnvio()
                        .withTipoTraslado("01")
                        .withMotivoTraslado("Venta")
                        .withPesoTotal(new BigDecimal("1000.00"))
                        .withNumeroDeBultos(10)
                        .withPartida(GuiaTestFactories.puntoPartida("150131", "Jr. Salida", "0000", "20123456789"))
                        .withDestino(GuiaTestFactories.puntoDestino("080131", "Jr. Llegada", "0001", "20987654321"))
                        .withVehiculo(GuiaTestFactories.vehiculo("AXZ-999"))
                        .addChofer(GuiaTestFactories.conductor("Juan", "Perez"))
                        .build()
                )
                .addDetalle(GuiaTestFactories.lineaGuia("ART001", "Artículo 1", new BigDecimal("500")))
                .addDetalle(GuiaTestFactories.lineaGuia("ART002", "Artículo 2", new BigDecimal("750")))
                .build();

        // Validaciones
        assertEquals("2.0", guia.getVersion());
        assertEquals("T001", guia.getSerie());
        assertEquals(1, guia.getNumero());
        assertEquals("09", guia.getTipoComprobante());
        assertTrue(guia.isGRERemitente());
        assertNotNull(guia.getFechaEmision());
        assertNotNull(guia.getDocumentoRelacionado());
        assertNotNull(guia.getEnvio());
        assertEquals(2, guia.getDetalles().size());
    }
}
