package com.creanexusatreus.ublkit.render.html;

import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.ExtensionPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.creanexusatreus.ublkit.ubl.ensamblador.EnsambladorNota;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaDebito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Renderizador SPOT")
class RenderizadorSpotTest {

    private static final String CUENTA_BN = "00-032-019287";
    private static final String MONTO_DETRACCION = "123.45";

    @Test
    @DisplayName("muestra monto y cuenta BN reales en todas las notas de débito")
    void notaDebito_muestraDatosSpotEnTodasLasRutas() {
        BorradorNotaDebito nota = notaDebito();

        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            assertContieneSpot(new RenderizadorHtmlNota(FormatoImpresion.A4)
                    .renderizar(contextoNota(nota, estilo)).contenidoHtml());
            assertContieneSpot(new RenderizadorHtmlNota(FormatoImpresion.A5)
                    .renderizar(contextoNota(nota, estilo)).contenidoHtml());
        }
        assertContieneSpot(new RenderizadorHtmlNota(FormatoImpresion.TICKET_58MM)
                .renderizar(contextoNota(nota, EstiloPlantilla.DEFAULT)).contenidoHtml());
        assertContieneSpot(new RenderizadorHtmlNota(FormatoImpresion.TICKET_80MM)
                .renderizar(contextoNota(nota, EstiloPlantilla.DEFAULT)).contenidoHtml());
    }

    @Test
    @DisplayName("muestra monto y cuenta BN reales en todas las boletas")
    void boleta_muestraDatosSpotEnTodosLosEstilos() {
        BorradorFactura boleta = boleta();

        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            String html = new RenderizadorHtmlFactura(FormatoImpresion.A4)
                    .renderizar(ContextoRender.of(boleta, "hash", null, Map.of(), estilo, ExtensionPlantilla.TWIG))
                    .contenidoHtml();
            assertContieneSpot(html);
        }
    }

    @Test
    @DisplayName("muestra monto y cuenta BN reales en todas las facturas")
    void factura_muestraDatosSpotEnTodosLosEstilos() {
        BorradorFactura factura = factura();

        for (EstiloPlantilla estilo : EstiloPlantilla.values()) {
            String html = new RenderizadorHtmlFactura(FormatoImpresion.A4)
                    .renderizar(ContextoRender.of(factura, "hash", null, Map.of(), estilo, ExtensionPlantilla.TWIG))
                    .contenidoHtml();
            assertContieneSpot(html);
        }
    }

    @Test
    @DisplayName("etiqueta la detracción en PEN aunque el comprobante esté en USD")
    void facturaUsd_muestraDetraccionEnSolesSinDolarEnSuBloque() {
        BorradorFactura factura = comprobante("F001", "01", "USD");
        String html = new RenderizadorHtmlFactura(FormatoImpresion.A4)
                .renderizar(ContextoRender.of(factura, "hash", null, Map.of(),
                        EstiloPlantilla.BOLD_ACCENT, ExtensionPlantilla.TWIG))
                .contenidoHtml();

        String bloqueDetraccion = extraerBloqueDetraccion(html);
        assertTrue(bloqueDetraccion.contains("S/ " + MONTO_DETRACCION), bloqueDetraccion);
        assertFalse(bloqueDetraccion.contains("$"), bloqueDetraccion);
    }

    private static ContextoRender<Object> contextoNota(BorradorNotaDebito nota, EstiloPlantilla estilo) {
        return ContextoRender.of((Object) nota, "hash", null, Map.of(), estilo, ExtensionPlantilla.TWIG);
    }

    private static void assertContieneSpot(String html) {
        assertTrue(html.contains(MONTO_DETRACCION), () -> "No contiene monto SPOT: " + html);
        assertTrue(html.contains(CUENTA_BN), () -> "No contiene cuenta BN: " + html);
    }

    private static BorradorNotaDebito notaDebito() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(2);
        nota.setFechaEmision(LocalDate.of(2026, 9, 1));
        nota.setTipoComprobante("08");
        nota.setMoneda("PEN");
        nota.setTipoNota("01");
        nota.setComprobanteAfectadoSerieNumero("F001-00000001");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Intereses por pago fuera de plazo");
        nota.setEmisor(emisor());
        nota.setReceptor(receptor());
        nota.setDetalles(List.of(linea()));
        nota.setDetraccion(new Detraccion("001", CUENTA_BN, "037",
                new BigDecimal("12"), new BigDecimal(MONTO_DETRACCION)));
        return EnsambladorNota.ensamblar(nota);
    }

    private static BorradorFactura boleta() {
        return comprobante("B001", "03");
    }

    private static BorradorFactura factura() {
        return comprobante("F001", "01");
    }

    private static BorradorFactura comprobante(String serie, String tipoComprobante) {
        return comprobante(serie, tipoComprobante, "PEN");
    }

    private static BorradorFactura comprobante(String serie, String tipoComprobante, String moneda) {
        BorradorFactura comprobante = new BorradorFactura();
        comprobante.setSerie(serie);
        comprobante.setNumero(1);
        comprobante.setFechaEmision(LocalDate.of(2026, 9, 1));
        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setTipoOperacion("1001");
        comprobante.setMoneda(moneda);
        comprobante.setEmisor(emisor());
        comprobante.setReceptor(receptor());
        comprobante.setDetalles(List.of(linea()));
        comprobante.setDetraccion(new Detraccion("001", CUENTA_BN, "037",
                new BigDecimal("12"), new BigDecimal(MONTO_DETRACCION)));
        return EnsambladorFactura.ensamblar(comprobante);
    }

    private static String extraerBloqueDetraccion(String html) {
        int inicio = html.indexOf("Detracción");
        int fin = html.indexOf(CUENTA_BN, inicio);
        assertTrue(inicio >= 0 && fin >= inicio, () -> "No se encontró bloque de detracción: " + html);
        return html.substring(inicio, fin + CUENTA_BN.length());
    }

    private static EmisorDocumento emisor() {
        return new EmisorDocumento("20123456789", "ACME", "ACME SAC", null, null);
    }

    private static ReceptorDocumento receptor() {
        return new ReceptorDocumento("6", "10456789012", "CLIENTE SAC", null, null);
    }

    private static LineaDetalle linea() {
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("SERVICIO GRAVADO");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("NIU");
        linea.setPrecio(new BigDecimal("1000.00"));
        linea.setIgvTipo("10");
        return linea;
    }
}
