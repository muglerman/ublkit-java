package com.cna.ublkit.ubl.modelo.guia;

import com.cna.ublkit.ubl.modelo.guia.builder.*;
import com.cna.ublkit.ubl.modelo.guia.fixture.GuiaTestFactories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PuntoPartida Builder Tests")
class PuntoPartidaBuilderTest {

    @Test
    @DisplayName("Should create punto partida with all fields using builder")
    void testCreaPuntoPartidaConBuilder() {
        PuntoPartida punto = PuntoPartidaBuilder.aPuntoPartida()
                .withUbigeo("150131")
                .withDireccion("Jr. Principal 123")
                .withCodigoLocal("0000")
                .withRuc("20123456789")
                .build();

        assertEquals("150131", punto.ubigeo());
        assertEquals("Jr. Principal 123", punto.direccion());
        assertEquals("0000", punto.codigoLocal());
        assertEquals("20123456789", punto.ruc());
    }

    @Test
    @DisplayName("Should create punto partida with defaults")
    void testCreaPuntoPartidaConDefectos() {
        PuntoPartida punto = PuntoPartidaBuilder.aPuntoPartida().build();

        assertNotNull(punto.ubigeo());
        assertNotNull(punto.direccion());
        assertNotNull(punto.codigoLocal());
        assertNotNull(punto.ruc());
    }

    @Test
    @DisplayName("Should create punto partida using factory")
    void testCreaPuntoPartidaConFactory() {
        PuntoPartida punto = GuiaTestFactories.puntoPartida("150131", "Jr. Test", "0000", "20123456789");

        assertEquals("150131", punto.ubigeo());
        assertEquals("Jr. Test", punto.direccion());
    }
}

@DisplayName("PuntoDestino Builder Tests")
class PuntoDestinoBuilderTest {

    @Test
    @DisplayName("Should create punto destino with all fields")
    void testCreaPuntoDestinoConBuilder() {
        PuntoDestino punto = PuntoDestinoBuilder.aPuntoDestino()
                .withUbigeo("080131")
                .withDireccion("Av. Lima 456")
                .withCodigoLocal("0001")
                .withRuc("20987654321")
                .build();

        assertEquals("080131", punto.ubigeo());
        assertEquals("Av. Lima 456", punto.direccion());
        assertEquals("0001", punto.codigoLocal());
        assertEquals("20987654321", punto.ruc());
    }
}

@DisplayName("Vehiculo Builder Tests")
class VehiculoBuilderTest {

    @Test
    @DisplayName("Should create vehiculo with placa using builder")
    void testCreaVehiculoConPlaca() {
        Vehiculo vehiculo = VehiculoBuilder.aVehiculo()
                .withPlaca("AXZ-999")
                .withNumeroCirculacion("TUC12345")
                .withNumeroAutorizacion("AUTH67890")
                .build();

        assertEquals("AXZ-999", vehiculo.placa());
        assertEquals("TUC12345", vehiculo.numeroCirculacion());
    }

    @Test
    @DisplayName("Should create vehiculo with defaults")
    void testCreaVehiculoConDefectos() {
        Vehiculo vehiculo = GuiaTestFactories.vehiculo("AXZ-999");

        assertEquals("AXZ-999", vehiculo.placa());
    }
}

@DisplayName("Conductor Builder Tests")
class ConductorBuilderTest {

    @Test
    @DisplayName("Should create conductor with all fields")
    void testCreaConductorConTodosCampos() {
        Conductor conductor = ConductorBuilder.aConductor()
                .withNombres("Juan")
                .withApellidos("Perez Garcia")
                .withNumeroDocumentoIdentidad("12345678")
                .withTipoDocumentoIdentidad("01")
                .withLicencia("LIC123456")
                .withTipo("Principal")
                .build();

        assertEquals("Juan", conductor.nombres());
        assertEquals("Perez Garcia", conductor.apellidos());
        assertEquals("12345678", conductor.numeroDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should create conductor using factory")
    void testCreaConductorConFactory() {
        Conductor conductor = GuiaTestFactories.conductor("Carlos", "Lopez");

        assertEquals("Carlos", conductor.nombres());
        assertEquals("Lopez", conductor.apellidos());
    }
}

@DisplayName("LineaGuia Builder Tests")
class LineaGuiaBuilderTest {

    @Test
    @DisplayName("Should create linea guia with all fields")
    void testCreaLineaGuiaConTodosCampos() {
        LineaGuia linea = LineaGuiaBuilder.aLineaGuia()
                .withCodigo("PROD001")
                .withDescripcion("Producto A")
                .withCantidad(new BigDecimal("100.00"))
                .withUnidadMedida("KGM")
                .withCodigoSunat("30000000")
                .build();

        assertEquals("PROD001", linea.codigo());
        assertEquals("Producto A", linea.descripcion());
        assertEquals(0, new BigDecimal("100.00").compareTo(linea.cantidad()));
        assertEquals("KGM", linea.unidadMedida());
    }

    @Test
    @DisplayName("Should create linea guia using factory")
    void testCreaLineaGuiaConFactory() {
        LineaGuia linea = GuiaTestFactories.lineaGuia("PROD002", "Producto B", new BigDecimal("50.00"));

        assertEquals("PROD002", linea.codigo());
        assertEquals("Producto B", linea.descripcion());
    }

    @Test
    @DisplayName("Should add atributos to linea")
    void testAgregaAtributosALinea() {
        LineaGuia linea = LineaGuiaBuilder.aLineaGuia()
                .withCodigo("PROD003")
                .addAtributo(GuiaTestFactories.atributoItem("LOTE", "L-001"))
                .addAtributo(GuiaTestFactories.atributoItem("VENCIMIENTO", "2025-12-31"))
                .build();

        assertEquals(2, linea.atributos().size());
    }
}

@DisplayName("Contenedor Builder Tests")
class ContenedorBuilderTest {

    @Test
    @DisplayName("Should create contenedor with all fields")
    void testCreaContenedorConTodosCampos() {
        Contenedor contenedor = ContenedorBuilder.aContenedor()
                .withNumero("CONT001")
                .withPrecinto("PREC001")
                .build();

        assertEquals("CONT001", contenedor.numero());
        assertEquals("PREC001", contenedor.precinto());
    }

    @Test
    @DisplayName("Should create contenedor using factory")
    void testCreaContenedorConFactory() {
        Contenedor contenedor = GuiaTestFactories.contenedor("CONT002", "PREC002");

        assertEquals("CONT002", contenedor.numero());
        assertEquals("PREC002", contenedor.precinto());
    }
}

@DisplayName("DocumentoRelacionadoGuia Builder Tests")
class DocumentoRelacionadoGuiaBuilderTest {

    @Test
    @DisplayName("Should create documento relacionado")
    void testCreaDocumentoRelacionado() {
        DocumentoRelacionadoGuia doc = DocumentoRelacionadoGuiaBuilder.aDocumentoRelacionadoGuia()
                .withTipoDocumento("01")
                .withSerieNumero("F001-000001")
                .build();

        assertEquals("01", doc.tipoDocumento());
        assertEquals("F001-000001", doc.serieNumero());
    }

    @Test
    @DisplayName("Should create factura relacionada using factory")
    void testCreaFacturaRelacionadaConFactory() {
        DocumentoRelacionadoGuia doc = GuiaTestFactories.facturaRelacionada();

        assertEquals("01", doc.tipoDocumento());
        assertEquals("F001-000001", doc.serieNumero());
    }
}

@DisplayName("DocumentoAdicionalGuia Builder Tests")
class DocumentoAdicionalGuiaBuilderTest {

    @Test
    @DisplayName("Should create documento adicional")
    void testCreaDocumentoAdicional() {
        DocumentoAdicionalGuia doc = DocumentoAdicionalGuiaBuilder.aDocumentoAdicionalGuia()
                .withTipoDocumento("09")
                .withSerieNumero("DOC123")
                .withEmisor("20123456789")
                .build();

        assertEquals("09", doc.tipoDocumento());
        assertEquals("DOC123", doc.serieNumero());
        assertEquals("20123456789", doc.emisor());
    }

    @Test
    @DisplayName("Should create documento adicional using factory")
    void testCreaDocumentoAdicionalConFactory() {
        DocumentoAdicionalGuia doc = GuiaTestFactories.documentoAdicional("01", "ADO001", "20111111111");

        assertEquals("01", doc.tipoDocumento());
        assertEquals("ADO001", doc.serieNumero());
    }
}

@DisplayName("DocumentoBajaGuia Builder Tests")
class DocumentoBajaGuiaBuilderTest {

    @Test
    @DisplayName("Should create documento baja")
    void testCreaDocumentoBaja() {
        DocumentoBajaGuia doc = DocumentoBajaGuiaBuilder.aDocumentoBajaGuia()
                .withTipoDocumento("01")
                .withSerieNumero("GRE123")
                .build();

        assertEquals("01", doc.tipoDocumento());
        assertEquals("GRE123", doc.serieNumero());
    }

    @Test
    @DisplayName("Should create documento baja using factory")
    void testCreaDocumentoBajaConFactory() {
        DocumentoBajaGuia doc = GuiaTestFactories.documentoBaja("09", "GRE-T001-001");

        assertEquals("09", doc.tipoDocumento());
        assertEquals("GRE-T001-001", doc.serieNumero());
    }
}

@DisplayName("DeclaracionAduanera Builder Tests")
class DeclaracionAduaneraBuilderTest {

    @Test
    @DisplayName("Should create declaracion aduanera")
    void testCreaDeclaracionAduanera() {
        DeclaracionAduanera decl = DeclaracionAduaneraBuilder.aDeclaracionAduanera()
                .withTipoDocumento("61")
                .withNumero("2000123456")
                .withSerieAduana("04")
                .build();

        assertEquals("61", decl.tipoDocumento());
        assertEquals("2000123456", decl.numero());
        assertEquals("04", decl.serieAduana());
    }
}

@DisplayName("TransportistaGuia Builder Tests")
class TransportistaGuiaBuilderTest {

    @Test
    @DisplayName("Should create transportista")
    void testCreaTransportista() {
        TransportistaGuia transportista = TransportistaGuiaBuilder.aTransportistaGuia()
                .withTipoDocumentoIdentidad("06")
                .withNumeroDocumentoIdentidad("20123456789")
                .withNombre("Empresa Transporte SA")
                .withNumeroRegistroMTC("MTC123456")
                .build();

        assertEquals("06", transportista.tipoDocumentoIdentidad());
        assertEquals("20123456789", transportista.numeroDocumentoIdentidad());
        assertEquals("Empresa Transporte SA", transportista.nombre());
    }

    @Test
    @DisplayName("Should create transportista using factory")
    void testCreaTransportistaConFactory() {
        TransportistaGuia transportista = GuiaTestFactories.transportista("06", "20123456789", "Empresa Trans", "MTC001");

        assertEquals("06", transportista.tipoDocumentoIdentidad());
        assertEquals("Empresa Trans", transportista.nombre());
    }
}

@DisplayName("DatosEnvio Builder Tests")
class DatosEnvioBuilderTest {

    @Test
    @DisplayName("Should create datos envio with all fields")
    void testCreaDatosEnvioConTodosCampos() {
        DatosEnvio datos = DatosEnvioBuilder.aDatosEnvio()
                .withTipoTraslado("01")
                .withMotivoTraslado("Venta")
                .withPesoTotal(new BigDecimal("5000.00"))
                .withPesoTotalUnidadMedida("KGM")
                .withNumeroDeBultos(50)
                .withFechaTraslado(LocalDate.now())
                .build();

        assertEquals("01", datos.getTipoTraslado());
        assertEquals("Venta", datos.getMotivoTraslado());
        assertEquals(0, new BigDecimal("5000.00").compareTo(datos.getPesoTotal()));
        assertEquals("KGM", datos.getPesoTotalUnidadMedida());
        assertEquals(50, datos.getNumeroDeBultos());
    }

    @Test
    @DisplayName("Should create datos envio with complex structure")
    void testCreaDatosEnvioConEstructuraCompleja() {
        DatosEnvio datos = DatosEnvioBuilder.aDatosEnvio()
                .withPartida(GuiaTestFactories.puntoPartida("150131", "Jr. Test", "0000", "20123456789"))
                .withDestino(GuiaTestFactories.puntoDestino("080131", "Av. Lima", "0001", "20987654321"))
                .withVehiculo(GuiaTestFactories.vehiculo("AXZ-999"))
                .addChofer(GuiaTestFactories.conductor("Juan", "Perez"))
                .addContenedor(GuiaTestFactories.contenedor("CONT001", "PREC001"))
                .build();

        assertNotNull(datos.getPartida());
        assertNotNull(datos.getDestino());
        assertNotNull(datos.getVehiculo());
        assertEquals(1, datos.getChoferes().size());
        assertEquals(1, datos.getContenedores().size());
    }
}

@DisplayName("BorradorGuiaRemision Builder Tests - COMPREHENSIVE")
class BorradorGuiaRemisionBuilderTest {

    @Test
    @DisplayName("Should create guia remitente (09)")
    void testCreaGuiaRemitente() {
        BorradorGuiaRemision guia = GuiaTestFactories.guiaRemitente();

        assertEquals("09", guia.getTipoComprobante());
        assertEquals("T001", guia.getSerie());
        assertTrue(guia.isGRERemitente());
        assertFalse(guia.isGRETransportista());
    }

    @Test
    @DisplayName("Should create guia transportista (31)")
    void testCreaGuiaTransportista() {
        BorradorGuiaRemision guia = GuiaTestFactories.guiaTransportista();

        assertEquals("31", guia.getTipoComprobante());
        assertEquals("V001", guia.getSerie());
        assertFalse(guia.isGRERemitente());
        assertTrue(guia.isGRETransportista());
    }

    @Test
    @DisplayName("Should create complete guia with fluent builder")
    void testCreaGuiaCompletaConBuilder() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(1)
                .withTipoComprobante("09")
                .withFechaEmision(LocalDate.of(2026, 4, 6))
                .withDocumentoRelacionado(GuiaTestFactories.facturaRelacionada())
                .addDocumentoAdicional(GuiaTestFactories.documentoAdicional("09", "DOC001", "20123456789"))
                .build();

        assertEquals("T001", guia.getSerie());
        assertEquals(1, guia.getNumero());
        assertEquals("09", guia.getTipoComprobante());
        assertEquals(LocalDate.of(2026, 4, 6), guia.getFechaEmision());
        assertNotNull(guia.getDocumentoRelacionado());
        assertEquals(1, guia.getDocumentosAdicionales().size());
    }

    @Test
    @DisplayName("Should create guia with envio details")
    void testCreaGuiaConDetallesEnvio() {
        DatosEnvio envio = DatosEnvioBuilder.aDatosEnvio()
                .withTipoTraslado("01")
                .withMotivoTraslado("Venta")
                .withPesoTotal(new BigDecimal("1000.00"))
                .withPartida(GuiaTestFactories.puntoPartida("150131", "Jr. Salida", "0000", "20123456789"))
                .withDestino(GuiaTestFactories.puntoDestino("080131", "Jr. Llegada", "0001", "20987654321"))
                .build();

        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T001")
                .withNumero(100)
                .withTipoComprobante("09")
                .withEnvio(envio)
                .build();

        assertEquals("T001", guia.getSerie());
        assertEquals(100, guia.getNumero());
        assertNotNull(guia.getEnvio());
        assertEquals("01", guia.getEnvio().getTipoTraslado());
    }

    @Test
    @DisplayName("Should create guia con múltiples detalles")
    void testCreaGuiaConMultiplesDetalles() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withSerie("T002")
                .withNumero(50)
                .withTipoComprobante("09")
                .withDocumentoRelacionado(GuiaTestFactories.facturaRelacionada())
                .addDetalle(GuiaTestFactories.lineaGuia("PROD001", "Producto 1", new BigDecimal("100")))
                .addDetalle(GuiaTestFactories.lineaGuia("PROD002", "Producto 2", new BigDecimal("50")))
                .build();

        assertEquals("T002", guia.getSerie());
        assertEquals(50, guia.getNumero());
        assertEquals(2, guia.getDetalles().size());
    }

    @Test
    @DisplayName("Should create valid guia for SUNAT submission")
    void testCreaGuiaValidaParaSUNAT() {
        BorradorGuiaRemision guia = BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
                .withVersion("2.0")
                .withSerie("T001")
                .withNumero(1)
                .withTipoComprobante("09")
                .withFechaEmision(LocalDate.now())
                .withDocumentoRelacionado(GuiaTestFactories.facturaRelacionada())
                .withEnvio(
                    DatosEnvioBuilder.aDatosEnvio()
                        .withTipoTraslado("01")
                        .withPartida(GuiaTestFactories.puntoPartida("150131", "Jr. Test", "0000", "20123456789"))
                        .withDestino(GuiaTestFactories.puntoDestino("080131", "Av. Lima", "0001", "20987654321"))
                        .withVehiculo(GuiaTestFactories.vehiculo("AXZ-999"))
                        .build()
                )
                .build();

        // Validaciones SUNAT-compliant
        assertEquals("2.0", guia.getVersion());
        assertEquals("09", guia.getTipoComprobante());
        assertNotNull(guia.getFechaEmision());
        assertNotNull(guia.getDocumentoRelacionado());
        assertNotNull(guia.getEnvio());
        assertEquals("01", guia.getEnvio().getTipoTraslado());
    }
}
