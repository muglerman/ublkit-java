package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.creanexusatreus.ublkit.core.modelo.Contacto;
import com.creanexusatreus.ublkit.core.modelo.Direccion;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlFactura;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlNota;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.creanexusatreus.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.creanexusatreus.ublkit.ubl.modelo.guia.Conductor;
import com.creanexusatreus.ublkit.ubl.modelo.guia.DatosEnvio;
import com.creanexusatreus.ublkit.ubl.modelo.guia.DestinatarioGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.LineaGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.TerceroGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.TransportistaGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.Vehiculo;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImporte;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;

/**
 * Gate anti-regresión: verifica que <strong>todos</strong> los estilos de plantilla
 * ({@link EstiloPlantilla}) cablean los datos reales del documento — no solo el estilo
 * {@code DEFAULT}.
 *
 * <p>Motivo: las suites {@code Renderizador*DataValidationTest} renderizan únicamente con el
 * estilo por defecto, por lo que un estilo con bindings rotos (variables Pebble incorrectas)
 * salía con campos en blanco sin que ningún test fallara (Pebble corre con
 * {@code strictVariables=false}). Este gate recorre los 5 estilos × factura/nota/guía y exige
 * la presencia de los campos núcleo en el HTML generado.</p>
 *
 * @since 0.4.0
 */
@DisplayName("🎨 Estilos - Validación de datos cableados en los 5 estilos")
class RenderizadorEstilosDataValidationTest {

    @ParameterizedTest(name = "factura · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void facturaCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorFactura factura = crearFactura();
        String html = renderizarFactura(factura, estilo);

        assertContiene(html, factura.getEmisor().ruc(), estilo, "RUC del emisor");
        assertContiene(html, factura.getEmisor().razonSocial(), estilo, "razón social del emisor");
        assertContiene(html, factura.getReceptor().nombre(), estilo, "nombre del receptor");
        assertContiene(html, factura.getReceptor().numDocIdentidad(), estilo, "documento del receptor");
        assertContiene(html, "Servicio de consultoría", estilo, "descripción del ítem");
        assertContiene(html, "Licencia de software anual", estilo, "descripción del segundo ítem");
        assertContiene(html, "T001-4912", estilo, "guía de remisión relacionada");
        assertContiene(html, "F001-99", estilo, "documento relacionado");
        assertContiene(html, "Entrega en Lima Metropolitana", estilo, "observaciones");
        assertSinPlaceholders(html, estilo);
    }

    @ParameterizedTest(name = "nota de crédito · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void notaCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorNotaCredito nota = crearNotaCredito();
        String html = renderizarNota(nota, estilo);

        assertContiene(html, nota.getEmisor().ruc(), estilo, "RUC del emisor");
        assertContiene(html, nota.getEmisor().razonSocial(), estilo, "razón social del emisor");
        assertContiene(html, nota.getReceptor().nombre(), estilo, "nombre del receptor");
        assertContiene(html, "F001-123", estilo, "comprobante afectado");
        assertContiene(html, "Ajuste", estilo, "descripción del ítem");
        assertSinPlaceholders(html, estilo);
    }

    @ParameterizedTest(name = "guía de remisión · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorGuiaRemision guia = crearGuia();
        String html = renderizarGuia(guia, estilo);

        assertContiene(html, guia.getRemitente().ruc(), estilo, "RUC del remitente");
        assertContiene(html, guia.getRemitente().razonSocial(), estilo, "razón social del remitente");
        assertContiene(html, guia.getDestinatario().nombre(), estilo, "nombre del destinatario");
        assertContiene(html, guia.getDestinatario().numeroDocumentoIdentidad(), estilo, "documento del destinatario");
        assertContiene(html, "Producto A", estilo, "descripción del bien trasladado");
        assertSinPlaceholders(html, estilo);
    }

    @ParameterizedTest(name = "boleta · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void boletaCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorFactura boleta = crearBoleta();
        String html = renderizarFactura(boleta, estilo);

        assertContiene(html, boleta.getEmisor().ruc(), estilo, "RUC del emisor");
        assertContiene(html, boleta.getEmisor().razonSocial(), estilo, "razón social del emisor");
        assertContiene(html, boleta.getReceptor().nombre(), estilo, "nombre del receptor");
        assertContiene(html, boleta.getReceptor().numDocIdentidad(), estilo, "DNI del receptor");
        assertContiene(html, "Polo algodón pima", estilo, "descripción del ítem");
        assertContiene(html, "B001", estilo, "serie de la boleta");
        assertTrue(html.toUpperCase().contains("BOLETA"),
                "El estilo " + estilo.carpeta() + " debe rotular el documento como BOLETA");
        assertSinPlaceholders(html, estilo);
    }

    @ParameterizedTest(name = "guía transportista · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaTransportistaCablaDatosRealesEnCadaEstilo(EstiloPlantilla estilo) {
        BorradorGuiaRemision guia = crearGuiaTransportista();
        String html = renderizarGuia(guia, estilo, carrierAttributes());

        assertContiene(html, guia.getRemitente().ruc(), estilo, "RUC del transportista emisor");
        assertContiene(html, guia.getRemitente().razonSocial(), estilo, "razón social del transportista emisor");
        assertContiene(html, guia.getTercero().nombre(), estilo, "nombre del remitente (tercero)");
        assertContiene(html, guia.getDestinatario().nombre(), estilo, "nombre del destinatario");
        assertContiene(html, "Av. Ferrocarril 1250", estilo, "dirección del emisor");
        assertContiene(html, "Junín", estilo, "departamento del emisor");
        assertContiene(html, "Huancayo", estilo, "provincia del emisor");
        assertContiene(html, "El Tambo", estilo, "distrito del emisor");
        assertContiene(html, "120114", estilo, "ubigeo del emisor");
        assertContiene(html, "contacto@mantaro.pe", estilo, "email del emisor");
        assertContiene(html, "064-555100", estilo, "teléfono del emisor");
        assertContiene(html, guia.getSubcontratado().nombre(), estilo, "empresa subcontratante");
        assertContiene(html, guia.getSubcontratado().numeroRegistroMTC(), estilo,
                "registro MTC del subcontratante");
        assertContiene(html, "MTC-123456", estilo, "registro MTC del emisor");
        assertContiene(html, "Estado de pago", estilo, "estado de pago en cabecera");
        assertContiene(html, "TRACK-2026-001", estilo, "tracking en cabecera");
        assertContiene(html, "Flete", estilo, "rótulo de flete");
        assertContiene(html, "245.50", estilo, "monto del flete");
        assertContiene(html, "Retorno de vehículo vacío", estilo, "indicador de retorno");
        assertContiene(html, "Transbordo programado", estilo, "indicador de transbordo");
        assertContiene(html, "Transporte subcontratado", estilo, "indicador de subcontratación");
        assertContiene(html, "Pagador del flete: destinatario", estilo, "indicador visual de pagador");
        assertContiene(html, "Producto A", estilo, "descripción del bien transportado");
        assertContiene(html, "GUÍA DE REMISIÓN TRANSPORTISTA", estilo, "rótulo de GRE transportista");
        assertTrue(html.indexOf("Estado de pago") < html.indexOf("Punto de partida"),
                "El estilo " + estilo.carpeta() + " debe mostrar el estado de pago en la cabecera");
        assertTrue(html.indexOf("Nro. tracking") < html.indexOf("Punto de partida"),
                "El estilo " + estilo.carpeta() + " debe mostrar el tracking en la cabecera");
        assertTrue(html.indexOf("Producto A") < html.indexOf("Flete"),
                "El estilo " + estilo.carpeta() + " debe mostrar el flete después de los bienes");
        assertTrue(html.indexOf("Subcontratación") < html.indexOf("Vehículos"),
                "El estilo " + estilo.carpeta() + " debe mostrar subcontratación antes de vehículos");
        assertEquals(1, contar(html, "class=\"subcontract-fields\""),
                "El estilo " + estilo.carpeta() + " debe mostrar una sola empresa de subcontratación");
        assertTrue(html.contains("grid-template-columns: repeat(3, minmax(0, 1fr))"),
                "El estilo " + estilo.carpeta() + " debe definir tres columnas para la subcontratación");
        assertTrue(html.contains("grid-template-columns: minmax(0, 1fr)"),
                "El estilo " + estilo.carpeta() + " debe dar todo el ancho a la empresa presente");
        assertTrue(html.contains("issuer-mtc"),
                "El estilo " + estilo.carpeta() + " debe identificar el MTC en la cabecera");
        assertTrue(contar(html, "class=\"vehicle-field\"") >= 9,
                "El estilo " + estilo.carpeta() + " debe presentar label y valor para cada dato del vehículo");
        assertTrue(html.contains(".vehicle-field { display: flex; align-items: baseline;"),
                "El estilo " + estilo.carpeta() + " debe alinear horizontalmente cada label y valor");
        assertTrue(html.contains("white-space: nowrap;"),
                "El estilo " + estilo.carpeta() + " no debe separar el label de su valor");
        assertTrue(html.contains(".vehicle-field .k { display: inline;"),
                "El estilo " + estilo.carpeta() + " no debe renderizar el label como bloque");
        assertTrue(html.contains(".vehicle-field .v { display: inline;"),
                "El estilo " + estilo.carpeta() + " no debe renderizar el valor como bloque");
        assertContiene(html, "ABC-123", estilo, "placa principal");
        assertContiene(html, "TUC-001", estilo, "TUC/CHV principal");
        assertContiene(html, "HAB-2024-001", estilo, "autorización principal");
        assertContiene(html, "VOLVO", estilo, "marca principal");
        assertContiene(html, "FH16", estilo, "modelo principal");
        assertContiene(html, "REM-456", estilo, "placa secundaria");
        assertFalse(html.contains("Transportista emisor"),
                "El estilo " + estilo.carpeta() + " no debe repetir el emisor como transportista");
        assertFalse(html.contains("20111111111"),
                "El estilo " + estilo.carpeta() + " no debe renderizar el bloque de transportista del XML");
        assertFalse(html.contains("class=\"signs\""),
                "El estilo " + estilo.carpeta() + " no debe incluir bloques de firmas");
        assertFalse(html.contains("Recepción / Destinatario"));
        assertFalse(html.contains("Nro. taquito"));
        assertSinPlaceholders(html, estilo);
    }

    @ParameterizedTest(name = "subcontratista exclusivo · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaTransportistaUsaTodoElAnchoParaSubcontratista(EstiloPlantilla estilo) {
        BorradorGuiaRemision guia = crearGuiaTransportista();
        guia.setSubcontratado(null);

        String html = renderizarGuia(guia, estilo, subcontractorAttributes());

        assertContiene(html, "Transportes Aliado S.A.C.", estilo, "transportista subcontratista");
        assertContiene(html, "MTC-SUB-44", estilo, "registro MTC del subcontratista");
        assertEquals(1, contar(html, "class=\"subcontract-fields\""),
                "El estilo " + estilo.carpeta() + " debe mostrar solo el subcontratista");
        assertFalse(html.contains("Operador Logístico Andino S.A.C."),
                "El estilo " + estilo.carpeta() + " no debe reservar una segunda empresa");
    }

    @ParameterizedTest(name = "PDF guía transportista · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaTransportistaGeneraPdfEnCadaEstilo(EstiloPlantilla estilo) {
        ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(
                crearGuiaTransportista(), "hash123", null, carrierAttributes(), estilo);

        byte[] pdf = new RenderizadorPdfGuiaRemision(FormatoImpresion.A4)
                .renderizar(contexto)
                .contenidoPdf();

        assertTrue(pdf.length > 1_000,
                "El estilo " + estilo.carpeta() + " debe generar un PDF de GRE Transportista");
        assertTrue(new String(pdf, 0, 5, StandardCharsets.US_ASCII).equals("%PDF-"),
                "El estilo " + estilo.carpeta() + " debe generar una cabecera PDF válida");
    }

    @ParameterizedTest(name = "guías sin total monetario · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaSoloMuestraFleteEnTransportista(EstiloPlantilla estilo) {
        Map<String, Object> atributos = Map.of("totalGuia", 9876.54);

        String remitente = renderizarGuia(crearGuia(), estilo, atributos);
        String transportista = renderizarGuia(crearGuiaTransportista(), estilo, atributos);

        assertFalse(remitente.contains("Monto total"));
        assertFalse(remitente.contains("9876"));
        assertFalse(transportista.contains("Monto total"));
        assertTrue(transportista.contains("Flete"));
        assertTrue(transportista.contains("9876.54"));
    }

    @ParameterizedTest(name = "contacto opcional · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaTransportistaOcultaContactoVacio(EstiloPlantilla estilo) {
        BorradorGuiaRemision guia = crearGuiaTransportista();
        EmisorDocumento emisor = guia.getRemitente();
        guia.setRemitente(new EmisorDocumento(
                emisor.ruc(), emisor.nombreComercial(), emisor.razonSocial(), emisor.direccion(),
                new Contacto(null, null, null)));

        String html = renderizarGuia(guia, estilo, carrierAttributes());

        assertFalse(html.contains("issuer-contact"),
                "El estilo " + estilo.carpeta() + " no debe mostrar el contacto vacío");
    }

    @ParameterizedTest(name = "contacto parcial · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void guiaTransportistaMuestraContactoParcialSinSeparadorHuerfano(EstiloPlantilla estilo) {
        BorradorGuiaRemision guia = crearGuiaTransportista();
        EmisorDocumento emisor = guia.getRemitente();
        guia.setRemitente(new EmisorDocumento(
                emisor.ruc(), emisor.nombreComercial(), emisor.razonSocial(), emisor.direccion(),
                new Contacto(null, null, "solo-email@mantaro.pe")));

        String html = renderizarGuia(guia, estilo, carrierAttributes());

        assertContiene(html, "solo-email@mantaro.pe", estilo, "email sin teléfono");
        assertTrue(html.contains("issuer-contact"),
                "El estilo " + estilo.carpeta() + " debe mostrar el contacto parcial");
        assertFalse(html.contains("solo-email@mantaro.pe ·"),
                "El estilo " + estilo.carpeta() + " no debe dejar un separador tras el email");
    }

    // ---- helpers de render ----

    private String renderizarFactura(BorradorFactura factura, EstiloPlantilla estilo) {
        ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash123", null, estilo);
        return new RenderizadorHtmlFactura(FormatoImpresion.A4).renderizar(contexto).contenidoHtml();
    }

    private String renderizarNota(BorradorNotaCredito nota, EstiloPlantilla estilo) {
        ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash123", null, estilo);
        return new RenderizadorHtmlNota(FormatoImpresion.A4).renderizar(contexto).contenidoHtml();
    }

    private String renderizarGuia(BorradorGuiaRemision guia, EstiloPlantilla estilo) {
        ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash123", null, estilo);
        return new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4).renderizar(contexto).contenidoHtml();
    }

    private String renderizarGuia(BorradorGuiaRemision guia, EstiloPlantilla estilo,
                                  Map<String, Object> atributos) {
        ContextoRender<BorradorGuiaRemision> contexto =
                ContextoRender.of(guia, "hash123", null, atributos, estilo);
        return new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4).renderizar(contexto).contenidoHtml();
    }

    private Map<String, Object> carrierAttributes() {
        return Map.of(
                "estadoPago", "PAGADO",
                "trackingNumber", "TRACK-2026-001",
                "totalGuia", new BigDecimal("245.50"),
                "tipoPagadorFlete", "Destinatario");
    }

    private Map<String, Object> subcontractorAttributes() {
        return Map.of(
                "subcontratistaNombre", "Transportes Aliado S.A.C.",
                "subcontratistaRuc", "20444444444",
                "subcontratistaMtc", "MTC-SUB-44");
    }

    private void assertContiene(String html, String esperado, EstiloPlantilla estilo, String campo) {
        assertTrue(esperado != null && !esperado.isBlank(),
                "fixture inválido: " + campo + " vacío");
        assertTrue(html.contains(esperado),
                "El estilo " + estilo.carpeta() + " debe renderizar " + campo + " (\"" + esperado + "\")");
    }

    /** Ningún binding Pebble sin resolver debe quedar en el HTML final. */
    private void assertSinPlaceholders(String html, EstiloPlantilla estilo) {
        assertFalse(html.contains("{{") || html.contains("{%"),
                "El estilo " + estilo.carpeta() + " dejó bindings Pebble sin resolver en el HTML");
    }

    private int contar(String texto, String fragmento) {
        return (texto.length() - texto.replace(fragmento, "").length()) / fragmento.length();
    }

    // ---- fixtures ----

    private BorradorFactura crearFactura() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(123);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setHoraEmision(LocalTime.of(14, 22));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");

        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null, "Lima", "Lima", "Lima", "Av. Javier Prado 123", "PE");
        factura.setEmisor(new EmisorDocumento(
                "20123456789", "Mi Empresa SAC", "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor, new Contacto("gerencia@miempresa.com", "015551234", null)));

        factura.setReceptor(new ReceptorDocumento(
                "6", "10987654321", "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Falsa 456", "PE"), null));

        LineaDetalle linea1 = new LineaDetalle();
        linea1.setDescripcion("Servicio de consultoría");
        linea1.setCantidad(new BigDecimal("2"));
        linea1.setUnidadMedida("ZZ");
        linea1.setPrecio(new BigDecimal("500.00"));

        LineaDetalle linea2 = new LineaDetalle();
        linea2.setDescripcion("Licencia de software anual");
        linea2.setCantidad(new BigDecimal("1"));
        linea2.setUnidadMedida("ZZ");
        linea2.setPrecio(new BigDecimal("1200.00"));

        factura.setDetalles(List.of(linea1, linea2));

        factura.setTotalImporte(new TotalImporte(
                new BigDecimal("2596.00"), new BigDecimal("2200.00"),
                new BigDecimal("2596.00"), null, null));
        factura.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("396.00"), new BigDecimal("396.00"), new BigDecimal("2200.00"),
                null, null, null, null, null, null, null, null, null, null, null, null, null));

        factura.setLeyendas(Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));
        factura.setOrdenDeCompra("OC-2026-001");
        factura.setObservaciones("Entrega en Lima Metropolitana");
        factura.setGuias(List.of(new GuiaRelacionada("T001-4912", "09")));
        factura.setDocumentosRelacionados(List.of(new DocumentoRelacionado("01", "F001-99")));
        return factura;
    }

    private BorradorFactura crearBoleta() {
        BorradorFactura boleta = crearFactura();
        boleta.setSerie("B001");
        boleta.setNumero(4912);
        boleta.setTipoComprobante("03");
        boleta.setReceptor(new ReceptorDocumento(
                "1", "45128734", "María Elena Castillo Rodríguez",
                new Direccion(null, null, null, null, null, null, "Calle Las Begonias 178", "PE"), null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Polo algodón pima Premium");
        linea.setCantidad(new BigDecimal("2"));
        linea.setUnidadMedida("NIU");
        linea.setPrecio(new BigDecimal("65.00"));
        boleta.setDetalles(List.of(linea));
        return boleta;
    }

    private BorradorGuiaRemision crearGuiaTransportista() {
        BorradorGuiaRemision guia = crearGuia();
        guia.setSerie("V001");
        guia.setNumero(128);
        guia.setTipoComprobante("31");
        // En GRE-31 el emisor (remitente del documento) es la empresa de transporte
        // y el remitente real de la carga viaja como tercero.
        Direccion direccionEmisor = new Direccion(
                "120114", "0000", null, "Junín", "Huancayo", "El Tambo", "Av. Ferrocarril 1250", "PE");
        guia.setRemitente(new EmisorDocumento(
                "20600456789", "Transportes Mantaro", "Transportes Mantaro E.I.R.L.",
                direccionEmisor, new Contacto(null, "064-555100", "contacto@mantaro.pe")));
        guia.setTercero(new TerceroGuia("6", "20512345678", "Manufacturas Andina Textil S.A.C.", null));
        guia.setSubcontratado(new TerceroGuia(
                "6", "20222222222", "Operador Logístico Andino S.A.C.", "MTC-SUBCONTRATANTE-22"));
        guia.getEnvio().setIndicadores(List.of(
                "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                "SUNAT_Envio_IndicadorTransbordoProgramado",
                "SUNAT_Envio_IndicadorTrasporteSubcontratado"));
        guia.getEnvio().setVehiculo(new Vehiculo(
                "ABC-123", "TUC-001", "HAB-2024-001", "MTC", "VOLVO", "FH16",
                List.of(new Vehiculo("REM-456", "TUC-002", "HAB-2024-002", "MTC",
                        "RANDON", "SR-2026", null))));
        return guia;
    }

    private BorradorNotaCredito crearNotaCredito() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("FC01");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 4, 1));
        nota.setHoraEmision(LocalTime.of(10, 30));
        nota.setTipoComprobante("07");

        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null, "Lima", "Lima", "Lima", "Av. Principal 999", "PE");
        nota.setEmisor(new EmisorDocumento(
                "20123456789", "Mi Empresa SAC", "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor, new Contacto("info@miempresa.com", "015551234", null)));

        nota.setReceptor(new ReceptorDocumento(
                "6", "10987654321", "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Secundaria 456", "PE"), null));

        nota.setComprobanteAfectadoSerieNumero("F001-123");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Ajuste por error en facturación");

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Ajuste");
        linea.setCantidad(new BigDecimal("1"));
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));

        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("100.00"), new BigDecimal("100.00"),
                new BigDecimal("100.00"), null, null));
        nota.setLeyendas(Map.of("1000", "SON: CIEN CON 00/100 SOLES"));
        return nota;
    }

    private BorradorGuiaRemision crearGuia() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(4912);
        guia.setFechaEmision(LocalDate.of(2026, 3, 31));
        guia.setHoraEmision(LocalTime.of(7, 22));
        guia.setTipoComprobante("09");

        Direccion direccionRemitente = new Direccion(
                "150101", "0000", null, "Lima", "Lima", "Lima", "Calle Principal 100", "PE");
        guia.setRemitente(new EmisorDocumento(
                "20606860618", "Repartidor Express", "Repartidor Express S.A.C.",
                direccionRemitente, new Contacto("contacto@repartidor.com", "01 555-1000", null)));

        guia.setDestinatario(new DestinatarioGuia("6", "10123456789", "Juan Pérez"));

        DatosEnvio datosEnvio = new DatosEnvio();
        datosEnvio.setTipoTraslado("01");
        datosEnvio.setMotivoTraslado("Venta");
        datosEnvio.setPesoTotal(new BigDecimal("250"));
        datosEnvio.setPesoTotalUnidadMedida("KG");
        datosEnvio.setNumeroDeBultos(5);
        datosEnvio.setFechaTraslado(LocalDate.of(2026, 3, 31));
        datosEnvio.setTransportista(new TransportistaGuia(
                "6", "20111111111", "Transportes XYZ", "MTC-123456"));
        datosEnvio.setVehiculo(new Vehiculo("ABC-123", "TUC-001", "HAB-2024-001", "MTC", "VOLVO", "FH16", null));
        datosEnvio.setChoferes(List.of(
                new Conductor("Principal", "1", "12345678", "Carlos", "López", "DL123456")));
        guia.setEnvio(datosEnvio);

        guia.setDetalles(List.of(
                new LineaGuia("KGM", new BigDecimal("2"), "Producto A", "PROD-001", "85101000", null)));
        guia.setObservaciones("Entrega asegurada");
        return guia;
    }
}
