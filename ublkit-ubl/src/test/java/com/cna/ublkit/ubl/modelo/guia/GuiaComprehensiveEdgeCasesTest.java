package com.cna.ublkit.ubl.modelo.guia;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Complete Guia Records Comprehensive Tests")
class GuiaRecordsComprehensiveTest {

    @Test
    @DisplayName("Should create and immute TransportistaGuia")
    void testTransportistaGuiaImmutability() {
        TransportistaGuia t1 = new TransportistaGuia("01", "20123456789", "Transport Co", "MTR001");
        TransportistaGuia t2 = new TransportistaGuia("01", "20123456789", "Transport Co", "MTR001");

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    @DisplayName("Should create DestinatarioGuia with varying document types")
    void testDestinatarioGuiaVariousDocTypes() {
        DestinatarioGuia destinatario1 = new DestinatarioGuia("06", "12345678", "Juan Perez");
        DestinatarioGuia destinatario2 = new DestinatarioGuia("01", "20123456789", "Empresa SA");

        assertNotNull(destinatario1);
        assertNotNull(destinatario2);
        assertEquals("06", destinatario1.tipoDocumentoIdentidad());
        assertEquals("01", destinatario2.tipoDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should create CompradorGuia")
    void testCompradorGuiaCreation() {
        CompradorGuia comprador = new CompradorGuia("06", "20111111111", "Buyer Corp");

        assertNotNull(comprador);
        assertEquals("20111111111", comprador.numeroDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should create TerceroGuia")
    void testTerceroGuiaCreation() {
        TerceroGuia tercero = new TerceroGuia("06", "20222222222", "Third Party SA");

        assertNotNull(tercero);
        assertEquals("20222222222", tercero.numeroDocumentoIdentidad());
    }

    @Test
    @DisplayName("Should create PuntoPartida with UBIGEO")
    void testPuntoPartidaWithUbigeo() {
        PuntoPartida punto = new PuntoPartida("150131", "Calle Principal 100", "Lima");

        assertEquals("150131", punto.ubigeo());
        assertEquals("Calle Principal 100", punto.direccion());
        assertEquals("Lima", punto.ciudad());
    }

    @Test
    @DisplayName("Should create PuntoDestino")
    void testPuntoDestinoCreation() {
        PuntoDestino punto = new PuntoDestino("080131", "Calle Secundaria 200", "Huancayo");

        assertEquals("080131", punto.ubigeo());
        assertNotNull(punto.direccion());
        assertNotNull(punto.ciudad());
    }

    @Test
    @DisplayName("Should create Puerto")
    void testPuertoCreation() {
        Puerto puerto = new Puerto("150100", "CALLAO");

        assertEquals("150100", puerto.ubigeo());
        assertEquals("CALLAO", puerto.nombre());
    }

    @Test
    @DisplayName("Should create DeclaracionAduanera")
    void testDeclaracionAduaneraCreation() {
        DeclaracionAduanera decl = new DeclaracionAduanera("2000123456", "04", LocalDate.of(2026, 4, 6));

        assertEquals("2000123456", decl.numeroDeclaracion());
        assertEquals("04", decl.tipoDeclaracion());
        assertEquals(LocalDate.of(2026, 4, 6), decl.fechaDeclaracion());
    }

    @Test
    @DisplayName("Should create Reversion record")
    void testReversionRecordCreation() {
        Reversion reversion = new Reversion("F001-100", LocalDate.of(2026, 4, 5), "01");

        assertEquals("F001-100", reversion.numeroDocumento());
        assertEquals(LocalDate.of(2026, 4, 5), reversion.fecha());
        assertEquals("01", reversion.tipo());
    }
}

@DisplayName("Additional Guia Model Classes Tests")
class GuiaAdditionalModelsTest {

    private DocumentoAdicionalGuia docAdicional;
    private DocumentoBajaGuia docBaja;
    private DocumentoRelacionadoGuia docRelacionado;
    private AtributoItem atributo;

    @BeforeEach
    void setUp() {
        docAdicional = new DocumentoAdicionalGuia();
        docBaja = new DocumentoBajaGuia();
        docRelacionado = new DocumentoRelacionadoGuia();
        atributo = new AtributoItem();
    }

    @Test
    @DisplayName("Should handle DocumentoAdicionalGuia properties")
    void testDocumentoAdicionalGuiaProperties() {
        docAdicional.setTipoDocumento("09");
        docAdicional.setNumeroDocumento("CERT123");
        docAdicional.setDescripcion("Certificado");

        assertEquals("09", docAdicional.getTipoDocumento());
        assertEquals("CERT123", docAdicional.getNumeroDocumento());
        assertEquals("Certificado", docAdicional.getDescripcion());
    }

    @Test
    @DisplayName("Should handle DocumentoBajaGuia properties")
    void testDocumentoBajaGuiaProperties() {
        docBaja.setMotivoBaja("01");
        docBaja.setNumeroDocumento("T001-100");
        docBaja.setDescripcion("Anulación de guía");

        assertEquals("01", docBaja.getMotivoBaja());
        assertEquals("T001-100", docBaja.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should handle DocumentoRelacionadoGuia properties")
    void testDocumentoRelacionadoGuiaProperties() {
        docRelacionado.setTipoDocumento("01");
        docRelacionado.setNumeroDocumento("F001-100");
        docRelacionado.setDescripcion("Factura relacionada");

        assertEquals("01", docRelacionado.getTipoDocumento());
        assertEquals("F001-100", docRelacionado.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should handle AtributoItem properties")
    void testAtributoItemProperties() {
        atributo.setNombre("Color");
        atributo.setValor("Rojo");
        atributo.setOpcion("Brillante");

        assertEquals("Color", atributo.getNombre());
        assertEquals("Rojo", atributo.getValor());
        assertEquals("Brillante", atributo.getOpcion());
    }

    @Test
    @DisplayName("Should handle multiple attributes")
    void testManejaMultiplesAtributos() {
        atributo.setNombre("Tamaño");
        atributo.setValor("Grande");

        AtributoItem otro = new AtributoItem();
        otro.setNombre("Peso");
        otro.setValor("10kg");

        assertNotNull(atributo.getNombre());
        assertNotNull(otro.getNombre());
    }
}

@DisplayName("Vehiculo and Conductor Edge Cases")
class VehiculoConductorEdgeCasesTest {

    private Vehiculo vehiculo;
    private Conductor conductor;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        conductor = new Conductor();
    }

    @Test
    @DisplayName("Should handle special characters in placa")
    void testManejaPilacaConCaracteresEspeciales() {
        vehiculo.setPlaca("A-1Z-999");
        assertEquals("A-1Z-999", vehiculo.getPlaca());
    }

    @Test
    @DisplayName("Should handle large capacity")
    void testManejaCapacidadGrande() {
        vehiculo.setCapacidadCarga(new BigDecimal("100000"));
        assertEquals(0, new BigDecimal("100000").compareTo(vehiculo.getCapacidadCarga()));
    }

    @Test
    @DisplayName("Should handle conductor full name")
    void testManejaNombreCompletoCondutor() {
        conductor.setNombres("Juan Carlos");
        conductor.setApellidos("Perez Garcia");

        assertEquals("Juan Carlos", conductor.getNombres());
        assertEquals("Perez Garcia", conductor.getApellidos());
    }

    @Test
    @DisplayName("Should handle conductor license categories")
    void testManejaCategoriasLicenciaCondutor() {
        conductor.setCategoriaLicencia("A2");
        assertEquals("A2", conductor.getCategoriaLicencia());

        conductor.setCategoriaLicencia("C3");
        assertEquals("C3", conductor.getCategoriaLicencia());
    }
}

@DisplayName("Contenedor and LineaGuia Edge Cases")
class ContenedorLineaGuiaEdgeCasesTest {

    private Contenedor contenedor;
    private LineaGuia linea;

    @BeforeEach
    void setUp() {
        contenedor = new Contenedor();
        linea = new LineaGuia();
    }

    @Test
    @DisplayName("Should handle contenedor con peso decimal")
    void testManejaContenedorConPesoDecimal() {
        contenedor.setNumero("CONT20123456");
        contenedor.setCantidad(25);
        contenedor.setPeso(new BigDecimal("2500.75"));

        assertEquals(25, contenedor.getCantidad());
        assertEquals(0, new BigDecimal("2500.75").compareTo(contenedor.getPeso()));
    }

    @Test
    @DisplayName("Should handle linea guia with decimal weight")
    void testManejaLineaGuiaConPesoDecimal() {
        linea.setItem(1);
        linea.setDescripcion("Mercadería");
        linea.setCantidad(new BigDecimal("100.50"));
        linea.setPeso(new BigDecimal("1005.75"));
        linea.setVolumen(new BigDecimal("50.25"));

        assertEquals(0, new BigDecimal("100.50").compareTo(linea.getCantidad()));
        assertEquals(0, new BigDecimal("1005.75").compareTo(linea.getPeso()));
        assertEquals(0, new BigDecimal("50.25").compareTo(linea.getVolumen()));
    }

    @Test
    @DisplayName("Should handle contenedor volumes")
    void testManejaVolumenContenedor() {
        contenedor.setVolumenes(new BigDecimal("75.50"));
        assertEquals(0, new BigDecimal("75.50").compareTo(contenedor.getVolumenes()));
    }
}
