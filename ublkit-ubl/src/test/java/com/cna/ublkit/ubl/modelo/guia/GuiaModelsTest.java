package com.cna.ublkit.ubl.modelo.guia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Guia-related Record Tests")
class GuiaModelsTest {

    @Test
    @DisplayName("PuntoPartida should be creatable")
    void testPuntoPartida() {
        PuntoPartida partida = new PuntoPartida(
            "150131",
            "Calle Principal 123",
            "LIMA"
        );

        assertEquals("150131", partida.ubigeo());
        assertEquals("Calle Principal 123", partida.direccion());
        assertEquals("LIMA", partida.ubicacion());
    }

    @Test
    @DisplayName("PuntoDestino should be creatable")
    void testPuntoDestino() {
        PuntoDestino destino = new PuntoDestino(
            "150232",
            "Avenida Secundaria 456",
            "LIMA"
        );

        assertEquals("150232", destino.ubigeo());
        assertEquals("Avenida Secundaria 456", destino.direccion());
        assertEquals("LIMA", destino.ubicacion());
    }

    @Test
    @DisplayName("Puerto should be creatable")
    void testPuerto() {
        Puerto puerto = new Puerto("5006", "CALLAO");

        assertEquals("5006", puerto.codigo());
        assertEquals("CALLAO", puerto.nombre());
    }

    @Test
    @DisplayName("TransportistaGuia should be creatable")
    void testTransportistaGuia() {
        TransportistaGuia transportista = new TransportistaGuia(
            "6",
            "20123456789",
            "Transporte XYZ S.A.C."
        );

        assertEquals("6", transportista.tipoDocumentoIdentidad());
        assertEquals("20123456789", transportista.numeroDocumentoIdentidad());
        assertEquals("Transporte XYZ S.A.C.", transportista.nombre());
    }

    @Test
    @DisplayName("DeclaracionAduanera should be creatable")
    void testDeclaracionAduanera() {
        DeclaracionAduanera declaracion = new DeclaracionAduanera(
            "DAM001",
            "08"
        );

        assertEquals("DAM001", declaracion.numero());
        assertEquals("08", declaracion.tipo());
    }

    @Test
    @DisplayName("DocumentoAdicionalGuia should be creatable")
    void testDocumentoAdicionalGuia() {
        DocumentoAdicionalGuia documento = new DocumentoAdicionalGuia(
            "12",
            "FAC-2024-001"
        );

        assertEquals("12", documento.tipoDocumento());
        assertEquals("FAC-2024-001", documento.numeroDocumento());
    }

    @Test
    @DisplayName("DocumentoBajaGuia should be creatable")
    void testDocumentoBajaGuia() {
        DocumentoBajaGuia documento = new DocumentoBajaGuia(
            "09",
            "T001-100"
        );

        assertEquals("09", documento.tipoDocumento());
        assertEquals("T001-100", documento.numeroDocumento());
    }

    @Test
    @DisplayName("DocumentoRelacionadoGuia should be creatable")
    void testDocumentoRelacionadoGuia() {
        DocumentoRelacionadoGuia documento = new DocumentoRelacionadoGuia(
            "01",
            "F001-500"
        );

        assertEquals("01", documento.tipoDocumento());
        assertEquals("F001-500", documento.numeroDocumento());
    }

    @Test
    @DisplayName("CompradorGuia should be creatable")
    void testCompradorGuia() {
        CompradorGuia comprador = new CompradorGuia(
            "6",
            "20987654321",
            "CLIENTE S.A.C."
        );

        assertEquals("6", comprador.tipoDocumento());
        assertEquals("20987654321", comprador.numeroDocumento());
    }

    @Test
    @DisplayName("DestinatarioGuia should be creatable")
    void testDestinatarioGuia() {
        DestinatarioGuia destinatario = new DestinatarioGuia(
            "6",
            "20555555555",
            "DESTINO S.A.C."
        );

        assertEquals("6", destinatario.tipoDocumento());
        assertEquals("20555555555", destinatario.numeroDocumento());
    }

    @Test
    @DisplayName("TerceroGuia should be creatable")
    void testTerceroGuia() {
        TerceroGuia tercero = new TerceroGuia(
            "1",
            "12345678",
            "Persona Tercera"
        );

        assertEquals("1", tercero.tipoDocumento());
        assertEquals("12345678", tercero.numeroDocumento());
    }

    @Test
    @DisplayName("AtributoItem should be creatable")
    void testAtributoItem() {
        AtributoItem atributo = new AtributoItem(
            "COLOR",
            "ROJO"
        );

        assertEquals("COLOR", atributo.nombre());
        assertEquals("ROJO", atributo.valor());
    }
}
