package com.creanexusatreus.ublkit.render.html;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.creanexusatreus.ublkit.core.modelo.Contacto;
import com.creanexusatreus.ublkit.core.modelo.Direccion;
import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.ExtensionPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImporte;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;

@DisplayName("Renderizado condicional de detracción, entrega y orden de compra")
class RenderizadorHtmlConditionalSectionsTest {

    private static final List<FormatoImpresion> INVOICE_FORMATS =
            List.of(FormatoImpresion.A4, FormatoImpresion.A5);

    private static final String ENTREGA_REAL = "Centro logístico Chilca - puerta 3";
    private static final String ORDEN_COMPRA_REAL = "OC-REAL-2026-7788";
    private static final String GUIA_REAL = "T010-00004567";
    private static final String DOC_REL_REAL = "F900-77";
    private static final String CUENTA_DETRACCION_REAL = "00-123-456789";
    private static final String MONTO_DETRACCION_REAL = "321.45";

    private static final String DEMO_ENTREGA = "Almacén Los Portales — Callao";
    private static final String DEMO_ORDEN_COMPRA = "OC-2026-00487";
    private static final String DEMO_GUIA = "GRE T001-00452";
    private static final String DEMO_CUENTA_BN = "00-032-019287";
    private static final String DEMO_MONTO_DETRACCION = "1,427.09";
    private static final String DEMO_LEYENDA_DETRACCION = "Operación sujeta a detracción";

    @Test
    @DisplayName("muestra entrega, O/C y detracción reales en A4/A5 para los cinco estilos")
    void facturaMuestraValoresRealesEnTodosLosEstilosYFormatos() {
        BorradorFactura factura = crearFacturaBase();
        factura.setDireccionEntrega(new Direccion(
                "150401", null, null, "Lima", "Cañete", "Chilca", ENTREGA_REAL, "PE"));
        factura.setOrdenDeCompra(ORDEN_COMPRA_REAL);
        factura.setDetraccion(new Detraccion(
                "001",
                CUENTA_DETRACCION_REAL,
                "037",
                new BigDecimal("12"),
                new BigDecimal(MONTO_DETRACCION_REAL)));

        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            for (FormatoImpresion formato : INVOICE_FORMATS) {
                String html = renderizarFactura(factura, estilo, formato);
                String contexto = contexto(estilo, formato);

                assertTrue(contieneEtiquetaEntrega(html),
                        () -> contexto + " debe mostrar la etiqueta de entrega");
                assertTrue(html.contains(ENTREGA_REAL),
                        () -> contexto + " debe mostrar la dirección de entrega real");
                assertTrue(contieneEtiquetaOrdenCompra(html, estilo),
                        () -> contexto + " debe mostrar la etiqueta de orden de compra");
                assertTrue(html.contains(ORDEN_COMPRA_REAL),
                        () -> contexto + " debe mostrar la orden de compra real");
                assertTrue(contieneEtiquetaDetraccion(html),
                        () -> contexto + " debe mostrar el bloque de detracción");
                assertTrue(html.contains("S/ " + MONTO_DETRACCION_REAL),
                        () -> contexto + " debe mostrar el monto real de detracción");
                assertTrue(html.contains(CUENTA_DETRACCION_REAL),
                        () -> contexto + " debe mostrar la cuenta BN real");
                assertTrue(html.contains(GUIA_REAL),
                        () -> contexto + " debe mostrar la guía real");
                assertTrue(html.contains(DOC_REL_REAL),
                        () -> contexto + " debe mostrar el documento relacionado real");

                assertFalse(html.contains(DEMO_ENTREGA),
                        () -> contexto + " no debe dejar la entrega demo");
                assertFalse(html.contains(DEMO_ORDEN_COMPRA),
                        () -> contexto + " no debe dejar la orden de compra demo");
                assertFalse(html.contains(DEMO_GUIA),
                        () -> contexto + " no debe dejar la guía demo");
                assertFalse(html.contains(DEMO_CUENTA_BN),
                        () -> contexto + " no debe dejar la cuenta BN demo");
                assertFalse(html.contains(DEMO_MONTO_DETRACCION),
                        () -> contexto + " no debe dejar el monto demo de detracción");
            }
        }
    }

    @Test
    @DisplayName("oculta entrega, O/C y detracción cuando los valores son nulos o vacíos")
    void facturaOcultaSeccionesCuandoLosValoresNoExisten() {
        BorradorFactura factura = crearFacturaBase();
        factura.setDireccionEntrega(new Direccion(
                "150401", null, null, "Lima", "Cañete", "Chilca", "", "PE"));
        factura.setOrdenDeCompra("");
        factura.setDetraccion(new Detraccion("", "", "", null, null));

        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            for (FormatoImpresion formato : INVOICE_FORMATS) {
                String html = renderizarFactura(factura, estilo, formato);
                String contexto = contexto(estilo, formato);

                assertFalse(contieneEtiquetaEntrega(html),
                        () -> contexto + " no debe mostrar la etiqueta de entrega");
                assertFalse(html.contains(ENTREGA_REAL),
                        () -> contexto + " no debe mostrar una dirección de entrega");
                assertFalse(contieneEtiquetaOrdenCompra(html, estilo),
                        () -> contexto + " no debe mostrar la fila de orden de compra");
                assertFalse(html.contains(ORDEN_COMPRA_REAL),
                        () -> contexto + " no debe mostrar una orden de compra");
                assertFalse(contieneEtiquetaDetraccion(html),
                        () -> contexto + " no debe mostrar el bloque de detracción");
                assertFalse(html.contains(MONTO_DETRACCION_REAL),
                        () -> contexto + " no debe mostrar el monto de detracción");
                assertFalse(html.contains(CUENTA_DETRACCION_REAL),
                        () -> contexto + " no debe mostrar la cuenta BN");

                assertTrue(html.contains(GUIA_REAL),
                        () -> contexto + " debe seguir mostrando la guía relacionada");
                assertTrue(html.contains(DOC_REL_REAL),
                        () -> contexto + " debe seguir mostrando el documento relacionado");

                assertFalse(html.contains(DEMO_ENTREGA),
                        () -> contexto + " no debe filtrar la entrega demo");
                assertFalse(html.contains(DEMO_ORDEN_COMPRA),
                        () -> contexto + " no debe filtrar la orden demo");
                assertFalse(html.contains(DEMO_GUIA),
                        () -> contexto + " no debe filtrar la guía demo");
                assertFalse(html.contains(DEMO_CUENTA_BN),
                        () -> contexto + " no debe filtrar la cuenta demo");
                assertFalse(html.contains(DEMO_MONTO_DETRACCION),
                        () -> contexto + " no debe filtrar el monto demo");
            }
        }
    }

    @Test
    @DisplayName("classic-mono note A5 elimina la detracción demo de notas de crédito")
    void notaCreditoClassicMonoA5NoMuestraDetraccionDemo() {
        BorradorNotaCredito nota = crearNotaCreditoBase();

        String html = renderizarNotaCreditoA5(nota, EstiloPlantilla.CLASSIC_MONO);

        assertTrue(html.contains(nota.getComprobanteAfectadoSerieNumero()),
                "La nota A5 classic-mono debe seguir mostrando el comprobante afectado real");
        assertTrue(html.contains(nota.getSustentoDescripcion()),
                "La nota A5 classic-mono debe seguir mostrando el sustento real");
        assertFalse(contieneEtiquetaDetraccion(html),
                "La nota A5 classic-mono no debe mostrar bloque de detracción");
        assertFalse(html.contains(DEMO_CUENTA_BN),
                "La nota A5 classic-mono no debe mostrar la cuenta BN demo");
        assertFalse(html.contains(DEMO_MONTO_DETRACCION),
                "La nota A5 classic-mono no debe mostrar el monto demo");
        assertFalse(html.contains(DEMO_LEYENDA_DETRACCION),
                "La nota A5 classic-mono no debe mostrar la leyenda demo de detracción");
    }

    private String renderizarFactura(BorradorFactura factura, EstiloPlantilla estilo,
                                     FormatoImpresion formato) {
        return new RenderizadorHtmlFactura(formato)
                .renderizar(ContextoRender.of(
                        factura, "hash-condicional", null, Map.of(), estilo, ExtensionPlantilla.TWIG))
                .contenidoHtml();
    }

    private String renderizarNotaCreditoA5(BorradorNotaCredito nota, EstiloPlantilla estilo) {
        return new RenderizadorHtmlNota(FormatoImpresion.A5)
                .renderizar(ContextoRender.of(
                        (Object) nota, "hash-condicional", null, Map.of(), estilo, ExtensionPlantilla.TWIG))
                .contenidoHtml();
    }

    private boolean contieneEtiquetaEntrega(String html) {
        return html.contains(">Entrega</span>") || html.contains(">Entrega</div>");
    }

    private boolean contieneEtiquetaOrdenCompra(String html, EstiloPlantilla estilo) {
        return estilo == EstiloPlantilla.CORPORATE_BLUE
                ? html.contains(">Orden compra</span>")
                : html.contains(">O/C</span>");
    }

    private boolean contieneEtiquetaDetraccion(String html) {
        return html.contains("Detracción SPOT") || html.contains(">Detracción</div>");
    }

    private String contexto(EstiloPlantilla estilo, FormatoImpresion formato) {
        return "estilo " + estilo.carpeta() + " / formato " + formato;
    }

    private BorradorFactura crearFacturaBase() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F009");
        factura.setNumero(77);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 9, 7));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");

        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null, "Lima", "Lima", "San Isidro", "Av. Canaval y Moreyra 250", "PE");
        factura.setEmisor(new EmisorDocumento(
                "20601234567",
                "Logística Real",
                "Logística Real S.A.C.",
                direccionEmisor,
                new Contacto("facturacion@logisticareal.pe", "015551111", null)));

        Direccion direccionReceptor = new Direccion(
                "150401", "0000", null, "Lima", "Cañete", "Chilca", "Parcela Industrial 8", "PE");
        factura.setReceptor(new ReceptorDocumento(
                "6", "20567890123", "Cliente Industrial S.A.C.", direccionReceptor, null));

        LineaDetalle linea = new LineaDetalle();
        linea.setCodigoProducto("SERV-LOG-01");
        linea.setDescripcion("Servicio de distribución nacional");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("590.00"));
        linea.setIgvTipo("10");
        factura.setDetalles(List.of(linea));

        factura.setTotalImporte(new TotalImporte(
                new BigDecimal("696.20"),
                new BigDecimal("590.00"),
                new BigDecimal("696.20"),
                null,
                null));
        factura.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("106.20"),
                new BigDecimal("106.20"),
                new BigDecimal("590.00"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        factura.setLeyendas(Map.of("1000", "SEISCIENTOS NOVENTA Y SEIS CON 20/100 SOLES"));
        factura.setGuias(List.of(new GuiaRelacionada(GUIA_REAL, "09")));
        factura.setDocumentosRelacionados(List.of(new DocumentoRelacionado("01", DOC_REL_REAL)));
        return factura;
    }

    private BorradorNotaCredito crearNotaCreditoBase() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("FC01");
        nota.setNumero(45);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 9, 7));
        nota.setTipoComprobante("07");
        nota.setTipoNota("01");

        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null, "Lima", "Lima", "San Isidro", "Av. Canaval y Moreyra 250", "PE");
        nota.setEmisor(new EmisorDocumento(
                "20601234567",
                "Logística Real",
                "Logística Real S.A.C.",
                direccionEmisor,
                new Contacto("facturacion@logisticareal.pe", "015551111", null)));

        Direccion direccionReceptor = new Direccion(
                "150401", "0000", null, "Lima", "Cañete", "Chilca", "Parcela Industrial 8", "PE");
        nota.setReceptor(new ReceptorDocumento(
                "6", "20567890123", "Cliente Industrial S.A.C.", direccionReceptor, null));

        nota.setComprobanteAfectadoTipo("01");
        nota.setComprobanteAfectadoSerieNumero("F001-00000045");
        nota.setSustentoDescripcion("Ajuste comercial por devolución parcial");

        LineaDetalle linea = new LineaDetalle();
        linea.setCodigoProducto("AJUSTE-01");
        linea.setDescripcion("Ajuste por devolución");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("100.00"));
        linea.setIgvTipo("10");
        nota.setDetalles(List.of(linea));

        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("118.00"),
                new BigDecimal("100.00"),
                new BigDecimal("118.00"),
                null,
                null));
        nota.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("18.00"),
                new BigDecimal("18.00"),
                new BigDecimal("100.00"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        nota.setLeyendas(Map.of("1000", "CIENTO DIECIOCHO CON 00/100 SOLES"));
        return nota;
    }
}
