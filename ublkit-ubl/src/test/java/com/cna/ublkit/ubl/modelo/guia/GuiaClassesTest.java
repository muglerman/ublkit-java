package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransportistaGuia Record Tests")
class TransportistaGuiaTest {

    @Test
    @DisplayName("Should create transportista with all fields")
    void testCreaTransportistaConTodosCampos() {
        TransportistaGuia transportista = new TransportistaGuia(
                "01",
                "20123456789",
                "Empresa Transporte SA",
                "MTC123456"
        );

        assertEquals("01", transportista.tipoDocumentoIdentidad());
        assertEquals("20123456789", transportista.numeroDocumentoIdentidad());
        assertEquals("Empresa Transporte SA", transportista.nombre());
        assertEquals("MTC123456", transportista.numeroRegistroMTC());
    }

    @Test
    @DisplayName("Should handle null values in constructor")
    void testManejaValoresNulosEnConstructor() {
        TransportistaGuia transportista = new TransportistaGuia(
                null,
                null,
                null,
                null
        );

        assertNull(transportista.tipoDocumentoIdentidad());
        assertNull(transportista.numeroDocumentoIdentidad());
        assertNull(transportista.nombre());
        assertNull(transportista.numeroRegistroMTC());
    }

    @Test
    @DisplayName("Should be immutable record")
    void testEsRecordInmutable() {
        TransportistaGuia t1 = new TransportistaGuia("01", "20123456789", "Empresa", "MTC123");
        TransportistaGuia t2 = new TransportistaGuia("01", "20123456789", "Empresa", "MTC123");

        assertEquals(t1, t2);
    }
}

@DisplayName("DestinatarioGuia Record Tests")
class DestinatarioGuiaTest {

    @Test
    @DisplayName("Should create destinatario with all fields")
    void testCreaDestinatarioConTodosCampos() {
        DestinatarioGuia destinatario = new DestinatarioGuia(
                "06",
                "20987654321",
                "Cliente SA"
        );

        assertEquals("06", destinatario.tipoDocumentoIdentidad());
        assertEquals("20987654321", destinatario.numeroDocumentoIdentidad());
        assertEquals("Cliente SA", destinatario.nombre());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        DestinatarioGuia destinatario = new DestinatarioGuia(null, null, null);

        assertNull(destinatario.tipoDocumentoIdentidad());
        assertNull(destinatario.numeroDocumentoIdentidad());
        assertNull(destinatario.nombre());
    }
}

@DisplayName("CompradorGuia Record Tests")
class CompradorGuiaTest {

    @Test
    @DisplayName("Should create comprador with all fields")
    void testCreaCompradorConTodosCampos() {
        CompradorGuia comprador = new CompradorGuia(
                "06",
                "20111111111",
                "Comprador Empresa"
        );

        assertEquals("06", comprador.tipoDocumentoIdentidad());
        assertEquals("20111111111", comprador.numeroDocumentoIdentidad());
        assertEquals("Comprador Empresa", comprador.nombre());
    }
}

@DisplayName("TerceroGuia Record Tests")
class TerceroGuiaTest {

    @Test
    @DisplayName("Should create tercero with all fields")
    void testCreaTerceroConTodosCampos() {
        TerceroGuia tercero = new TerceroGuia(
                "06",
                "20222222222",
                "Tercero Empresa"
        );

        assertEquals("06", tercero.tipoDocumentoIdentidad());
        assertEquals("20222222222", tercero.numeroDocumentoIdentidad());
        assertEquals("Tercero Empresa", tercero.nombre());
    }
}

@DisplayName("PuntoPartida Record Tests")
class PuntoPartidaTest {

    @Test
    @DisplayName("Should create punto partida with all fields")
    void testCreaPuntoPartidaConTodosCampos() {
        PuntoPartida punto = new PuntoPartida(
                "150131",
                "Av. Principal 100",
                "Lima"
        );

        assertEquals("150131", punto.ubigeo());
        assertEquals("Av. Principal 100", punto.direccion());
        assertEquals("Lima", punto.ciudad());
    }

    @Test
    @DisplayName("Should handle null values")
    void testManejaValoresNulos() {
        PuntoPartida punto = new PuntoPartida(null, null, null);

        assertNull(punto.ubigeo());
        assertNull(punto.direccion());
        assertNull(punto.ciudad());
    }
}

@DisplayName("PuntoDestino Record Tests")
class PuntoDestinoTest {

    @Test
    @DisplayName("Should create punto destino with all fields")
    void testCreaPuntoDestinoConTodosCampos() {
        PuntoDestino punto = new PuntoDestino(
                "150000",
                "Av. Secundaria 200",
                "Lima"
        );

        assertEquals("150000", punto.ubigeo());
        assertEquals("Av. Secundaria 200", punto.direccion());
        assertEquals("Lima", punto.ciudad());
    }
}

@DisplayName("Vehiculo Class Tests")
class VehiculoTest {

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
    }

    @Test
    @DisplayName("Should set and get placa")
    void testSetYGetPlaca() {
        vehiculo.setPlaca("AXZ-999");
        assertEquals("AXZ-999", vehiculo.getPlaca());
    }

    @Test
    @DisplayName("Should set and get marcaModelo")
    void testSetYGetMarcaModelo() {
        vehiculo.setMarcaModelo("Volvo FM 2025");
        assertEquals("Volvo FM 2025", vehiculo.getMarcaModelo());
    }

    @Test
    @DisplayName("Should set and get capacidadCarga")
    void testSetYGetCapacidadCarga() {
        vehiculo.setCapacidadCarga(new BigDecimal("25000"));
        assertEquals(0, new BigDecimal("25000").compareTo(vehiculo.getCapacidadCarga()));
    }

    @Test
    @DisplayName("Should set and get unidadMedida")
    void testSetYGetUnidadMedida() {
        vehiculo.setUnidadMedida("KG");
        assertEquals("KG", vehiculo.getUnidadMedida());
    }
}

@DisplayName("Conductor Class Tests")
class ConductorTest {

    private Conductor conductor;

    @BeforeEach
    void setUp() {
        conductor = new Conductor();
    }

    @Test
    @DisplayName("Should set and get tipoDocumento")
    void testSetYGetTipoDocumento() {
        conductor.setTipoDocumento("01");
        assertEquals("01", conductor.getTipoDocumento());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento")
    void testSetYGetNumeroDocumento() {
        conductor.setNumeroDocumento("12345678");
        assertEquals("12345678", conductor.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get nombres")
    void testSetYGetNombres() {
        conductor.setNombres("Juan");
        assertEquals("Juan", conductor.getNombres());
    }

    @Test
    @DisplayName("Should set and get apellidos")
    void testSetYGetApellidos() {
        conductor.setApellidos("Perez Garcia");
        assertEquals("Perez Garcia", conductor.getApellidos());
    }

    @Test
    @DisplayName("Should set and get licencia")
    void testSetYGetLicencia() {
        conductor.setLicencia("LIC123456");
        assertEquals("LIC123456", conductor.getLicencia());
    }

    @Test
    @DisplayName("Should set and get categoriaLicencia")
    void testSetYGetCategoriaLicencia() {
        conductor.setCategoriaLicencia("A3");
        assertEquals("A3", conductor.getCategoriaLicencia());
    }
}

@DisplayName("Contenedor Class Tests")
class ContenedorTest {

    private Contenedor contenedor;

    @BeforeEach
    void setUp() {
        contenedor = new Contenedor();
    }

    @Test
    @DisplayName("Should set and get numero")
    void testSetYGetNumero() {
        contenedor.setNumero("CONT123456");
        assertEquals("CONT123456", contenedor.getNumero());
    }

    @Test
    @DisplayName("Should set and get cantidad")
    void testSetYGetCantidad() {
        contenedor.setCantidad(10);
        assertEquals(10, contenedor.getCantidad());
    }

    @Test
    @DisplayName("Should set and get peso")
    void testSetYGetPeso() {
        contenedor.setPeso(new BigDecimal("2000.00"));
        assertEquals(0, new BigDecimal("2000.00").compareTo(contenedor.getPeso()));
    }

    @Test
    @DisplayName("Should set and get volumenes")
    void testSetYGetVolumenes() {
        contenedor.setVolumenes(new BigDecimal("50.00"));
        assertEquals(0, new BigDecimal("50.00").compareTo(contenedor.getVolumenes()));
    }
}

@DisplayName("LineaGuia Class Tests")
class LineaGuiaTest {

    private LineaGuia linea;

    @BeforeEach
    void setUp() {
        linea = new LineaGuia();
    }

    @Test
    @DisplayName("Should set and get item")
    void testSetYGetItem() {
        linea.setItem(1);
        assertEquals(1, linea.getItem());
    }

    @Test
    @DisplayName("Should set and get codigo")
    void testSetYGetCodigo() {
        linea.setCodigo("PROD001");
        assertEquals("PROD001", linea.getCodigo());
    }

    @Test
    @DisplayName("Should set and get descripcion")
    void testSetYGetDescripcion() {
        linea.setDescripcion("Producto A");
        assertEquals("Producto A", linea.getDescripcion());
    }

    @Test
    @DisplayName("Should set and get cantidad")
    void testSetYGetCantidad() {
        linea.setCantidad(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(linea.getCantidad()));
    }

    @Test
    @DisplayName("Should set and get unidadMedida")
    void testSetYGetUnidadMedida() {
        linea.setUnidadMedida("KG");
        assertEquals("KG", linea.getUnidadMedida());
    }

    @Test
    @DisplayName("Should set and get peso")
    void testSetYGetPeso() {
        linea.setPeso(new BigDecimal("500.00"));
        assertEquals(0, new BigDecimal("500.00").compareTo(linea.getPeso()));
    }

    @Test
    @DisplayName("Should set and get volumen")
    void testSetYGetVolumen() {
        linea.setVolumen(new BigDecimal("25.00"));
        assertEquals(0, new BigDecimal("25.00").compareTo(linea.getVolumen()));
    }
}

@DisplayName("DatosEnvio Class Tests")
class DatosEnvioTest {

    private DatosEnvio datos;

    @BeforeEach
    void setUp() {
        datos = new DatosEnvio();
    }

    @Test
    @DisplayName("Should set and get fechaEmisionGRE")
    void testSetYGetFechaEmisionGRE() {
        LocalDate fecha = LocalDate.of(2026, 4, 6);
        datos.setFechaEmisionGRE(fecha);
        assertEquals(fecha, datos.getFechaEmisionGRE());
    }

    @Test
    @DisplayName("Should set and get peso")
    void testSetYGetPeso() {
        datos.setPeso(new BigDecimal("5000.00"));
        assertEquals(0, new BigDecimal("5000.00").compareTo(datos.getPeso()));
    }

    @Test
    @DisplayName("Should set and get volumen")
    void testSetYGetVolumen() {
        datos.setVolumen(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(datos.getVolumen()));
    }

    @Test
    @DisplayName("Should set and get modotransporte")
    void testSetYGetModotransporte() {
        datos.setModotransporte("01");
        assertEquals("01", datos.getModotransporte());
    }
}
