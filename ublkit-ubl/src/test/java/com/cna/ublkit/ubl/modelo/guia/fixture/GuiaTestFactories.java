package com.cna.ublkit.ubl.modelo.guia.fixture;

import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.guia.builder.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory para crear instancias de modelos de guía de remisión para tests.
 * Proporciona valores por defecto sensatos y métodos de conveniencia.
 */
public class GuiaTestFactories {

    // ── PuntoPartida ──────────────────────────────────────
    public static PuntoPartida puntoPartida(String ubigeo, String direccion,
                                            String codigoLocal, String ruc) {
        return PuntoPartidaBuilder.aPuntoPartida()
            .withUbigeo(ubigeo)
            .withDireccion(direccion)
            .withCodigoLocal(codigoLocal)
            .withRuc(ruc)
            .build();
    }

    public static PuntoPartida puntoPartidaPorDefecto() {
        return PuntoPartidaBuilder.aPuntoPartida().build();
    }

    // ── PuntoDestino ──────────────────────────────────────
    public static PuntoDestino puntoDestino(String ubigeo, String direccion,
                                            String codigoLocal, String ruc) {
        return PuntoDestinoBuilder.aPuntoDestino()
            .withUbigeo(ubigeo)
            .withDireccion(direccion)
            .withCodigoLocal(codigoLocal)
            .withRuc(ruc)
            .build();
    }

    // ── DocumentoRelacionadoGuia ──────────────────────────
    public static DocumentoRelacionadoGuia documentoRelacionado(String tipoDocumento,
                                                                String serieNumero) {
        return DocumentoRelacionadoGuiaBuilder.aDocumentoRelacionadoGuia()
            .withTipoDocumento(tipoDocumento)
            .withSerieNumero(serieNumero)
            .build();
    }

    public static DocumentoRelacionadoGuia facturaRelacionada() {
        return documentoRelacionado("01", "F001-000001");
    }

    // ── AtributoItem ──────────────────────────────────────
    public static AtributoItem atributoItem(String codigo, String valor) {
        return AtributoItemBuilder.anAtributoItem()
            .withCodigo(codigo)
            .withValor(valor)
            .build();
    }

    // ── Contenedor ────────────────────────────────────────
    public static Contenedor contenedor(String numero, String precinto) {
        return ContenedorBuilder.aContenedor()
            .withNumero(numero)
            .withPrecinto(precinto)
            .build();
    }

    // ── LineaGuia ─────────────────────────────────────────
    public static LineaGuia lineaGuia(String codigo, String descripcion, BigDecimal cantidad) {
        return LineaGuiaBuilder.aLineaGuia()
            .withCodigo(codigo)
            .withDescripcion(descripcion)
            .withCantidad(cantidad)
            .build();
    }

    // ── DocumentoAdicionalGuia ────────────────────────────
    public static DocumentoAdicionalGuia documentoAdicional(String tipoDocumento, String serieNumero, String emisor) {
        return DocumentoAdicionalGuiaBuilder.aDocumentoAdicionalGuia()
            .withTipoDocumento(tipoDocumento)
            .withSerieNumero(serieNumero)
            .withEmisor(emisor)
            .build();
    }

    // ── DocumentoBajaGuia ─────────────────────────────────
    public static DocumentoBajaGuia documentoBaja(String tipoDocumento, String serieNumero) {
        return DocumentoBajaGuiaBuilder.aDocumentoBajaGuia()
            .withTipoDocumento(tipoDocumento)
            .withSerieNumero(serieNumero)
            .build();
    }

    // ── DeclaracionAduanera ───────────────────────────────
    public static DeclaracionAduanera declaracionAduanera(String tipoDocumento, String numero, String serieAduana) {
        return DeclaracionAduaneraBuilder.aDeclaracionAduanera()
            .withTipoDocumento(tipoDocumento)
            .withNumero(numero)
            .withSerieAduana(serieAduana)
            .build();
    }

    // ── TransportistaGuia ─────────────────────────────────
    public static TransportistaGuia transportista(String tipoDoc, String numeroDoc, String nombre, String registroMTC) {
        return TransportistaGuiaBuilder.aTransportistaGuia()
            .withTipoDocumentoIdentidad(tipoDoc)
            .withNumeroDocumentoIdentidad(numeroDoc)
            .withNombre(nombre)
            .withNumeroRegistroMTC(registroMTC)
            .build();
    }

    // ── Vehiculo ──────────────────────────────────────────
    public static Vehiculo vehiculo(String placa) {
        return VehiculoBuilder.aVehiculo()
            .withPlaca(placa)
            .build();
    }

    // ── Conductor ─────────────────────────────────────────
    public static Conductor conductor(String nombres, String apellidos) {
        return ConductorBuilder.aConductor()
            .withNombres(nombres)
            .withApellidos(apellidos)
            .build();
    }

    // ── BorradorGuiaRemision ──────────────────────────────
    public static BorradorGuiaRemision guiaRemisionPorDefecto() {
        return BorradorGuiaRemisionBuilder.aBorradorGuiaRemision().build();
    }

    public static BorradorGuiaRemision guiaRemitente() {
        return BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
            .withTipoComprobante("09")
            .withSerie("T001")
            .build();
    }

    public static BorradorGuiaRemision guiaTransportista() {
        return BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
            .withTipoComprobante("31")
            .withSerie("V001")
            .build();
    }

    public static BorradorGuiaRemision guiaCompleta() {
        return BorradorGuiaRemisionBuilder.aBorradorGuiaRemision()
            .withSerie("T001")
            .withNumero(1)
            .withTipoComprobante("09")
            .withDocumentoRelacionado(facturaRelacionada())
            .build();
    }
}
