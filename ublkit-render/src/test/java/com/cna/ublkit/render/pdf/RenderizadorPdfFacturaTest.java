package com.cna.ublkit.render.pdf;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de integración que verifica la generación correcta de un PDF
 * a partir un BorradorFactura renderizado como HTML y luego convertido
 * con OpenHTMLtoPDF.
 *
 * @since 0.1.0
 */
class RenderizadorPdfFacturaTest {

    @Test
    void debeGenerarPdfConContenidoValido() throws IOException {
        // Arrange — Construir un BorradorFactura completo
        BorradorFactura factura = crearFacturaEjemplo();
        ContextoRender<BorradorFactura> contexto = ContextoRender.of(
                factura,
                "abc123hashejemplo",
                null // sin QR en este test
        );

        RenderizadorPdfFactura renderizador = new RenderizadorPdfFactura(FormatoImpresion.A4);

        // Act
        ResultadoRender resultado = renderizador.renderizar(contexto);

        // Assert — El resultado debe ser PDF con contenido
        assertTrue(resultado.isPdf(), "El resultado debe ser de tipo PDF");
        assertFalse(resultado.isHtml(), "El resultado NO debe ser HTML");
        assertNotNull(resultado.contenidoPdf(), "El contenido PDF no debe ser nulo");
        assertTrue(resultado.contenidoPdf().length > 100, "El PDF debe tener un tamaño razonable");

        // Verificar cabecera PDF (%PDF-)
        String cabecera = new String(resultado.contenidoPdf(), 0, 5);
        assertEquals("%PDF-", cabecera, "El binario debe iniciar con la cabecera PDF estándar");

        // Guardar archivo temporal para inspección manual
        Path archivoPdf = Files.createTempFile("ublkit-factura-test-", ".pdf");
        Files.write(archivoPdf, resultado.contenidoPdf());
        System.out.println("PDF generado en: " + archivoPdf.toAbsolutePath());
    }

    @Test
    void debeGenerarPdfConFormatoA5() throws IOException {
        BorradorFactura factura = crearFacturaEjemplo();
        ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash456", null);

        RenderizadorPdfFactura renderizador = new RenderizadorPdfFactura(FormatoImpresion.A5);

        ResultadoRender resultado = renderizador.renderizar(contexto);

        assertTrue(resultado.isPdf());
        assertTrue(resultado.contenidoPdf().length > 100);
    }

    @Test
    void debeGenerarPdfConConstructorPorDefecto() {
        BorradorFactura factura = crearFacturaEjemplo();
        ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, "hash789", null);

        RenderizadorPdfFactura renderizador = new RenderizadorPdfFactura();

        ResultadoRender resultado = renderizador.renderizar(contexto);

        assertTrue(resultado.isPdf());
        assertNotNull(resultado.contenidoPdf());
    }

    // ── Helper ──────────────────────────────────────────────────

    private BorradorFactura crearFacturaEjemplo() {
        BorradorFactura factura = new BorradorFactura();

        factura.setSerie("F001");
        factura.setNumero(123);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setTipoComprobante("01");

        Direccion direccionEmisor = new Direccion(
                "150101", "0000", null,
                "Lima", "Lima", "Lima",
                "Av. Javier Prado 123", "PE"
        );
        factura.setEmisor(new EmisorDocumento(
                "20123456789",
                "Mi Empresa SAC",
                "Mi Empresa Sociedad Anónima Cerrada",
                direccionEmisor,
                null
        ));

        factura.setReceptor(new ReceptorDocumento(
                "6", "10987654321",
                "Cliente Ejemplo EIRL",
                new Direccion(null, null, null, null, null, null, "Calle Falsa 456", "PE"),
                null
        ));

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
                new BigDecimal("2596.00"),
                new BigDecimal("2200.00"),
                new BigDecimal("2596.00"),
                null, null
        ));

        factura.setTotalImpuestos(new TotalImpuestos(
                new BigDecimal("396.00"),
                new BigDecimal("396.00"),
                new BigDecimal("2200.00"),
                null, null, null, null, null, null, null, null, null, null, null, null, null
        ));

        factura.setLeyendas(Map.of("1000", "SON: DOS MIL QUINIENTOS NOVENTA Y SEIS CON 00/100 SOLES"));

        return factura;
    }
}
