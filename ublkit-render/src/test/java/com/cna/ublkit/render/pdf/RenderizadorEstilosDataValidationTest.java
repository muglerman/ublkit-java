package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.EstiloPlantilla;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.Conductor;
import com.cna.ublkit.ubl.modelo.guia.DatosEnvio;
import com.cna.ublkit.ubl.modelo.guia.DestinatarioGuia;
import com.cna.ublkit.ubl.modelo.guia.LineaGuia;
import com.cna.ublkit.ubl.modelo.guia.TerceroGuia;
import com.cna.ublkit.ubl.modelo.guia.TransportistaGuia;
import com.cna.ublkit.ubl.modelo.guia.Vehiculo;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

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
        String html = renderizarGuia(guia, estilo);

        assertContiene(html, guia.getRemitente().ruc(), estilo, "RUC del transportista emisor");
        assertContiene(html, guia.getRemitente().razonSocial(), estilo, "razón social del transportista emisor");
        assertContiene(html, guia.getTercero().nombre(), estilo, "nombre del remitente (tercero)");
        assertContiene(html, guia.getDestinatario().nombre(), estilo, "nombre del destinatario");
        assertContiene(html, "MTC-123456", estilo, "registro MTC del transportista");
        assertContiene(html, guia.getSubcontratado().nombre(), estilo, "transportista subcontratado");
        assertContiene(html, "Producto A", estilo, "descripción del bien transportado");
        assertContiene(html, "Tipo 31", estilo, "rótulo de GRE transportista");
        assertSinPlaceholders(html, estilo);
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
        guia.setRemitente(new EmisorDocumento(
                "20600456789", "Transportes Mantaro", "Transportes Mantaro E.I.R.L.",
                null, null));
        guia.setTercero(new TerceroGuia("6", "20512345678", "Manufacturas Andina Textil S.A.C."));
        guia.setSubcontratado(new TerceroGuia("6", "20222222222", "Subcontratista Andino S.A.C."));
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
        datosEnvio.setVehiculo(new Vehiculo("ABC-123", "TUC-001", "HAB-2024-001", "MTC", null));
        datosEnvio.setChoferes(List.of(
                new Conductor("Principal", "1", "12345678", "Carlos", "López", "DL123456")));
        guia.setEnvio(datosEnvio);

        guia.setDetalles(List.of(
                new LineaGuia("KGM", new BigDecimal("2"), "Producto A", "PROD-001", "85101000", null)));
        guia.setObservaciones("Entrega asegurada");
        return guia;
    }
}
