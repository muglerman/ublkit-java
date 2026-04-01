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
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.Conductor;
import com.cna.ublkit.ubl.modelo.guia.DatosEnvio;
import com.cna.ublkit.ubl.modelo.guia.DestinatarioGuia;
import com.cna.ublkit.ubl.modelo.guia.LineaGuia;
import com.cna.ublkit.ubl.modelo.guia.TransportistaGuia;
import com.cna.ublkit.ubl.modelo.guia.Vehiculo;

/**
 * Test de DIAGNÓSTICO: Genera HTML de Guía de Remisión y lo guarda para inspección manual.
 */
@DisplayName("📦 GUÍA DE REMISIÓN - Diagnóstico HTML")
class RenderizadorPdfGuiaRemisionDiagnosticTest {

	@Test
	@DisplayName("📝 Generar y guardar HTML de Guía para inspección")
	void generarHtmlYGuardarParaInspeccion() throws Exception {
		BorradorGuiaRemision guia = crearGuiaCompleta();

		ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash123", null);
		RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4);
		ResultadoRender resultado = renderer.renderizar(contexto);
		String html = resultado.contenidoHtml();

		// Guardar HTML a archivo temporal para inspección
		Path outputFile = Files.createTempFile("guia-remision-", ".html");
		Files.write(outputFile, html.getBytes());

		System.out.println("\n");
		System.out.println("=".repeat(80));
		System.out.println("📦 HTML DE GUÍA DE REMISIÓN GENERADO");
		System.out.println("=".repeat(80));
		System.out.println("\nGuardado en: " + outputFile.toAbsolutePath());
		System.out.println("\n--- VERIFICACIONES DE CONTENIDO ---\n");

		// Verificaciones
		verifyContent("Tipo GRE (31 - Transportista)", html, "31");
		verifyContent("RUC Remitente (20606860618)", html, "20606860618");
		verifyContent("Nombre Remitente (Repartidor Express)", html, "Repartidor Express");
		verifyContent("Serie-Número (V001-4912)", html, "V001-4912");
		verifyContent("Fecha Traslado (2026-03-31)", html, "2026-03-31");
		verifyContent("Destinatario (Juan Pérez)", html, "Juan Pérez");
		verifyContent("Peso Total (250)", html, "250");
		verifyContent("Unidad Peso (KG)", html, "KG");
		verifyContent("Número Bultos (5)", html, "5");
		verifyContent("Transportista (Transportes XYZ)", html, "Transportes XYZ");
		verifyContent("Vehículo (ABC-123)", html, "ABC-123");
		verifyContent("Conductor (Carlos)", html, "Carlos");
		verifyContent("Producto (Producto A)", html, "Producto A");

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

	private BorradorGuiaRemision crearGuiaCompleta() {
		BorradorGuiaRemision guia = new BorradorGuiaRemision();

		guia.setSerie("V001");
		guia.setNumero(4912);
		guia.setFechaEmision(LocalDate.of(2026, 3, 31));
		guia.setHoraEmision(LocalTime.of(7, 22));
		guia.setTipoComprobante("31");

		// Remitente
		Direccion direccionRemitente = new Direccion("150101", "0000", null, "Lima", "Lima", "Lima",
				"Calle Principal 100", "PE");
		guia.setRemitente(new EmisorDocumento("20606860618", "Repartidor Express", "Repartidor Express S.A.C.",
				direccionRemitente, new Contacto("contacto@repartidor.com", "01 555-1000", null)));

		// Destinatario
		guia.setDestinatario(new DestinatarioGuia("6", "10123456789", "Juan Pérez"));

		// Datos de Envío
		DatosEnvio datosEnvio = new DatosEnvio();
		datosEnvio.setTipoTraslado("01");
		datosEnvio.setMotivoTraslado("Venta");
		datosEnvio.setPesoTotal(new BigDecimal("250"));
		datosEnvio.setPesoTotalUnidadMedida("KG");
		datosEnvio.setNumeroDeBultos(5);
		datosEnvio.setFechaTraslado(LocalDate.of(2026, 3, 31));

		// Transportista
		datosEnvio.setTransportista(new TransportistaGuia("6", "20111111111", "Transportes XYZ", "MTC-123456"));

		// Vehículo
		datosEnvio.setVehiculo(new Vehiculo("ABC-123", "TUC-001", "HAB-2024-001", "MTC", null));

		// Conductor
		datosEnvio.setChoferes(List.of(
				new Conductor("Principal", "1", "12345678", "Carlos", "López", "DL123456")
		));

		guia.setEnvio(datosEnvio);

		// Líneas de detalle
		guia.setDetalles(List.of(
				new LineaGuia("KGM", new BigDecimal("2"), "Producto A", "PROD-001", "85101000", null)
		));

		guia.setObservaciones("Entrega asegurada");

		return guia;
	}
}
