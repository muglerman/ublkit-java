package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;

/**
 * Test de DIAGNÓSTICO: Genera HTML de Nota de Crédito y lo guarda para inspección manual.
 */
@DisplayName("💳 NOTA DE CRÉDITO - Diagnóstico HTML")
class RenderizadorPdfNotaDiagnosticTest {

	@Test
	@DisplayName("📝 Generar y guardar HTML de Nota para inspección")
	void generarHtmlYGuardarParaInspeccion() throws Exception {
		BorradorNotaCredito nota = crearNotaCompleta();

		@SuppressWarnings("unchecked")
		ContextoRender<Object> contexto = ContextoRender.of((Object) nota, "hash123", null);
		RenderizadorHtmlNota renderer = new RenderizadorHtmlNota(FormatoImpresion.A4);
		ResultadoRender resultado = renderer.renderizar(contexto);
		String html = resultado.contenidoHtml();

		// Guardar HTML a archivo temporal para inspección
		Path outputFile = Files.createTempFile("nota-credito-", ".html");
		Files.write(outputFile, html.getBytes());

		System.out.println("\n");
		System.out.println("=".repeat(80));
		System.out.println("💳 HTML DE NOTA DE CRÉDITO GENERADO");
		System.out.println("=".repeat(80));
		System.out.println("\nGuardado en: " + outputFile.toAbsolutePath());
		System.out.println("\n--- VERIFICACIONES DE CONTENIDO ---\n");

		// Verificaciones
		verifyContent("Tipo Nota (07 - Crédito)", html, "07");
		verifyContent("RUC Emisor (20123456789)", html, "20123456789");
		verifyContent("Nombre Emisor (Mi Empresa SAC)", html, "Mi Empresa SAC");
		verifyContent("Serie-Número (FC01-1)", html, "FC01-1");
		verifyContent("Fecha Emisión (2026-04-01)", html, "2026-04-01");
		verifyContent("Moneda (PEN)", html, "PEN");
		verifyContent("Cliente (Cliente Ejemplo EIRL)", html, "Cliente Ejemplo EIRL");
		verifyContent("Comprobante Afectado (F001-123)", html, "F001-123");
		verifyContent("Sustento (Ajuste por error)", html, "Ajuste por error");
		verifyContent("Total (100)", html, "100");

		System.out.println("\n" + "=".repeat(80));
		System.out.println("✅ Inspección completada");
		System.out.println("=".repeat(80) + "\n");

		assertTrue(html.length() > 100, "HTML debe contener contenido");
	}

	private void verifyContent(String label, String html, String content) {
		boolean found = html.contains(content);
		String status = found ? "✅" : "❌";
		String record = String.format("%-50s : %s [%s]", label, status, content);
		System.out.println(record);
	}

	private BorradorNotaCredito crearNotaCompleta() {
		BorradorNotaCredito nota = new BorradorNotaCredito();

		nota.setSerie("FC01");
		nota.setNumero(1);
		nota.setMoneda("PEN");
		nota.setFechaEmision(LocalDate.of(2026, 4, 1));
		nota.setHoraEmision(LocalTime.of(10, 30));

		// Emisor
		Direccion direccionEmisor = new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
				"Av. Principal 999", "PE");
		nota.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa SAC", "Mi Empresa Sociedad Anónima Cerrada",
				direccionEmisor, new Contacto("info@miempresa.com", "015551234", null)));

		// Receptor
		nota.setReceptor(new ReceptorDocumento("6", "10987654321", "Cliente Ejemplo EIRL",
				new Direccion(null, null, null, null, null, null, "Calle Secundaria 456", "PE"), null));

		// Comprobante afectado
		nota.setComprobanteAfectadoSerieNumero("F001-123");
		nota.setComprobanteAfectadoTipo("01");

		// Sustento
		nota.setTipoNota("01");
		nota.setSustentoDescripcion("Ajuste por error en facturación");

		// Líneas
		LineaDetalle linea = new LineaDetalle();
		linea.setDescripcion("Ajuste");
		linea.setCantidad(new BigDecimal("1"));
		linea.setUnidadMedida("ZZ");
		linea.setPrecio(new BigDecimal("100.00"));
		nota.setDetalles(List.of(linea));

		// Totales
		nota.setTotalImporte(new TotalImporte(
				new BigDecimal("100.00"),
				new BigDecimal("100.00"),
				new BigDecimal("100.00"),
				null,
				null
		));

		return nota;
	}
}
