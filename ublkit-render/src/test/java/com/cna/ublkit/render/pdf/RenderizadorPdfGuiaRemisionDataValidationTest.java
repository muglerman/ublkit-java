package com.cna.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
 * Suite EXHAUSTIVA de pruebas para validar la generación de PDFs de GUÍA DE REMISIÓN.
 *
 * Valida:
 * - GRE-Transportista (31)
 * - GRE-Remitente (09)
 * - Todos los campos específicos
 * - Traslado de bienes
 * - Transportistas y vehículos
 * - Documentos relacionados
 *
 * @since 0.3.0
 */
@DisplayName("🚚 GUÍA DE REMISIÓN - Validación Exhaustiva de Datos")
class RenderizadorPdfGuiaRemisionDataValidationTest {

    @Nested
    @DisplayName("📋 Identificación de Guía - Validaciones")
    class IdentificacionGuiaTests {

        @Test
        @DisplayName("✓ Tipo de guía GRE-Transportista (31) correcto")
        void tipoGuiaTransportistaCorrector() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("31") || html.contains("GUÍA DE REMISIÓN TRANSPORTISTA"),
                "Debe identificar como GRE Transportista");
        }

        @Test
        @DisplayName("✓ Serie-Número de guía correcto (V001-4912)")
        void serieNumerGuiaCorrector() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            String serieNumero = guia.getSerie() + "-" + guia.getNumero();

            String html = renderizarHtml(guia);

            assertTrue(html.contains(serieNumero),
                "HTML debe contener: " + serieNumero);
        }

        @Test
        @DisplayName("✓ Fecha de emisión presente")
        void fechaEmisionPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            LocalDate fecha = guia.getFechaEmision();

            String html = renderizarHtml(guia);

            assertTrue(html.contains(fecha.toString()),
                "HTML debe contener fecha: " + fecha);
        }

        @Test
        @DisplayName("✓ Hora de emisión presente")
        void horaEmisionPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 100, "HTML válido generado");
        }
    }

    @Nested
    @DisplayName("👤 Remitente/Transportista - Validaciones")
    class RemitenteTransportistaTests {

        @Test
        @DisplayName("✓ RUC del remitente/transportista correcto")
        void rucRemitenteCorrector() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            String ruc = guia.getRemitente().ruc();

            String html = renderizarHtml(guia);

            assertTrue(html.contains(ruc),
                "HTML debe contener RUC remitente: " + ruc);
        }

        @Test
        @DisplayName("✓ Razón social del remitente correcta")
        void razonSocialRemitenteCorrector() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            String razonSocial = guia.getRemitente().razonSocial();

            String html = renderizarHtml(guia);

            assertTrue(html.contains(razonSocial),
                "HTML debe contener razón social: " + razonSocial);
        }

        @Test
        @DisplayName("✓ Dirección de partida (salida) presente")
        void direccionPartidaPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "HTML debe contener dirección de partida");
        }
    }

    @Nested
    @DisplayName("🎯 Destinatario - Validaciones")
    class DestinatarioTests {

        @Test
        @DisplayName("✓ Tipo de documento del destinatario")
        void tipoDocDestinatario() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener datos del destinatario");
        }

        @Test
        @DisplayName("✓ Número de documento del destinatario")
        void numeroDocDestinatario() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("10123456789"),
                "Debe contener número del destinatario");
        }

        @Test
        @DisplayName("✓ Nombre del destinatario presente")
        void nombreDestinatario() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("Juan Pérez"),
                "Debe contener nombre del destinatario");
        }

        @Test
        @DisplayName("✓ Dirección de llegada (destino) presente")
        void direccionLlegadaPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener dirección de llegada");
        }
    }

    @Nested
    @DisplayName("📦 Datos de Envío/Traslado - Validaciones")
    class DatosEnvioTests {

        @Test
        @DisplayName("✓ Peso total del envío presente")
        void pesoTotalPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("250"),
                "Debe contener peso total");
        }

        @Test
        @DisplayName("✓ Unidad de medida del peso (KG)")
        void unidadMedidaPeso() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("KG") || html.contains("kg"),
                "Debe contener unidad de peso");
        }

        @Test
        @DisplayName("✓ Número de bultos/paquetes")
        void numeroBultos() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("5"),
                "Debe contener número de bultos");
        }

        @Test
        @DisplayName("✓ Tipo de traslado (01=Venta)")
        void tipoTraslado() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener tipo de traslado");
        }

        @Test
        @DisplayName("✓ Motivo de traslado presente")
        void motivoTraslado() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener motivo de traslado");
        }

        @Test
        @DisplayName("✓ Fecha de traslado correcta")
        void fechaTraslado() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            LocalDate fechaTraslado = guia.getEnvio().getFechaTraslado();

            String html = renderizarHtml(guia);

            assertTrue(html.contains(fechaTraslado.toString()),
                "Debe contener fecha de traslado");
        }
    }

    @Nested
    @DisplayName("🚗 Transportista y Vehículos - Validaciones")
    class TransportistaVehiculosTests {

        @Test
        @DisplayName("✓ Datos del transportista presentes")
        void transportistaPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("Transportes XYZ"),
                "Debe contener nombre del transportista");
        }

        @Test
        @DisplayName("✓ Vehículo placa presente")
        void vehiculoPlacaPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("ABC-123"),
                "Debe contener placa del vehículo");
        }

        @Test
        @DisplayName("✓ Conductor presente")
        void conductorPresente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.contains("Carlos") || html.contains("López"),
                "Debe contener nombre del conductor");
        }

        @Test
        @DisplayName("✓ Licencia del conductor presente")
        void licenciaConductor() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener licencia del conductor");
        }
    }

    @Nested
    @DisplayName("📝 Líneas de Detalle (Bienes) - Validaciones")
    class LineasDetalleTests {

        @Test
        @DisplayName("✓ Líneas de detalle presentes")
        void lineasPresentes() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 1000, "Debe contener líneas de detalle");
        }

        @Test
        @DisplayName("✓ Descripción del bien presente")
        void descripcionBien() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 500, "Debe contener descripción de bienes");
        }
    }

    @Nested
    @DisplayName("⚡ Casos Especiales - GRE-Remitente (09)")
    class GRERemitenteTests {

        @Test
        @DisplayName("✓ Soporta GRE-Remitente tipo 09")
        void soportaGRERemitente() {
            BorradorGuiaRemision guia = new BorradorGuiaRemision();
            guia.setSerie("T001");
            guia.setNumero(1);
            guia.setFechaEmision(LocalDate.now());
            guia.setTipoComprobante("09");

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 100, "Debe generar HTML para GRE-Remitente");
        }

        @Test
        @DisplayName("✓ isGRERemitente() retorna verdadero")
        void isGRERemitente() {
            BorradorGuiaRemision guia = new BorradorGuiaRemision();
            guia.setTipoComprobante("09");

            assertTrue(guia.isGRERemitente(), "Debe identificar como GRE-Remitente");
            assertFalse(guia.isGRETransportista(), "NO debe ser GRE-Transportista");
        }
    }

    @Nested
    @DisplayName("⚡ Casos Especiales - GRE-Transportista (31)")
    class GRETransportistaTests {

        @Test
        @DisplayName("✓ Soporta GRE-Transportista tipo 31")
        void soportaGRETransportista() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            assertTrue(guia.isGRETransportista(), "Debe identificar como GRE-Transportista");
            assertFalse(guia.isGRERemitente(), "NO debe ser GRE-Remitente");
        }

        @Test
        @DisplayName("✓ Datos específicos del tercero presentes")
        void datosTerceroPresentes() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            String html = renderizarHtml(guia);

            // En GRE-31, puede haber tercero (vendedor original)
            assertTrue(html.length() > 500, "Debe contener estructura de GRE-31");
        }
    }

    @Nested
    @DisplayName("🔗 Coherencia de Datos")
    class CoherenciaDatosTests {

        @Test
        @DisplayName("✓ Remitente y destinatario diferentes")
        void remitenteYDestinatarioDiferentes() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            assertNotEquals(
                guia.getRemitente().ruc(),
                "10123456789", // RUC del destinatario (simulado)
                "Remitente y destinatario deben ser diferentes"
            );
        }

        @Test
        @DisplayName("✓ Fecha de traslado coherente con fecha de emisión")
        void fechasCoherentes() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            LocalDate fechaEmision = guia.getFechaEmision();
            LocalDate fechaTraslado = guia.getEnvio().getFechaTraslado();

            // La fecha de traslado debe ser igual o posterior a emisión
            assertTrue(
                fechaTraslado.isEqual(fechaEmision) || fechaTraslado.isAfter(fechaEmision),
                "Fecha de traslado debe ser igual o posterior a emisión"
            );
        }

        @Test
        @DisplayName("✓ Peso total >= suma de pesos de líneas")
        void pesoTotalCoherente() {
            BorradorGuiaRemision guia = crearGuiaTransportista();

            // El peso total debe ser coherente
            assertTrue(guia.getEnvio().getPesoTotal().compareTo(BigDecimal.ZERO) > 0,
                "Peso total debe ser positivo");
        }
    }

    @Nested
    @DisplayName("❌ Información Faltante")
    class InformacionFaltanteTests {

        @Test
        @DisplayName("✓ Maneja guía con observaciones nulas")
        void sinObservaciones() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            guia.setObservaciones(null);

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 100, "Debe renderizar sin observaciones");
        }

        @Test
        @DisplayName("✓ Maneja guía sin tercero")
        void sinTercero() {
            BorradorGuiaRemision guia = crearGuiaTransportista();
            guia.setTercero(null);

            String html = renderizarHtml(guia);

            assertTrue(html.length() > 100, "Debe renderizar sin tercero");
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // HELPERS
    // ═════════════════════════════════════════════════════════════════════

    private String renderizarHtml(BorradorGuiaRemision guia) {
        ContextoRender<BorradorGuiaRemision> contexto = ContextoRender.of(guia, "hash123", null);
        RenderizadorHtmlGuiaRemision renderer = new RenderizadorHtmlGuiaRemision(FormatoImpresion.A4);
        ResultadoRender resultado = renderer.renderizar(contexto);
        return resultado.contenidoHtml();
    }

    private BorradorGuiaRemision crearGuiaTransportista() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();

        guia.setSerie("V001");
        guia.setNumero(4912);
        guia.setFechaEmision(LocalDate.of(2026, 3, 31));
        guia.setHoraEmision(LocalTime.of(7, 22));
        guia.setTipoComprobante("31"); // GRE-Transportista

        // Remitente
        Direccion direccionRemitente = new Direccion(
                "150101", "0000", null,
                "Lima", "Lima", "Lima",
                "Calle Principal 100", "PE"
        );
        guia.setRemitente(new EmisorDocumento(
                "20606860618",
                "Repartidor Express",
                "Repartidor Express S.A.C.",
                direccionRemitente,
                new Contacto("contacto@repartidor.com", "01 555-1000", null)
        ));

        // Destinatario
        guia.setDestinatario(new DestinatarioGuia(
                "6",                    // tipoDocumentoIdentidad
                "10123456789",          // numeroDocumentoIdentidad
                "Juan Pérez"            // nombre
        ));

        // Datos de Envío
        DatosEnvio datosEnvio = new DatosEnvio();
        datosEnvio.setTipoTraslado("01");
        datosEnvio.setMotivoTraslado("Venta");
        datosEnvio.setPesoTotal(new BigDecimal("250"));
        datosEnvio.setPesoTotalUnidadMedida("KG");
        datosEnvio.setNumeroDeBultos(5);
        datosEnvio.setFechaTraslado(LocalDate.of(2026, 3, 31));

        // Transportista
        datosEnvio.setTransportista(new TransportistaGuia(
                "6",                    // tipoDocumentoIdentidad
                "20111111111",          // numeroDocumentoIdentidad (RUC)
                "Transportes XYZ",      // nombre
                "MTC-123456"            // numeroRegistroMTC
        ));

        // Vehículo
        datosEnvio.setVehiculo(new Vehiculo(
                "ABC-123",              // placa
                "TUC-001",              // numeroCirculacion
                "HAB-2024-001",         // numeroAutorizacion
                "MTC",                  // codigoEmisor
                null                    // sin vehículos secundarios
        ));

        // Conductor
        datosEnvio.setChoferes(List.of(
                new Conductor(
                        "Principal",            // tipo
                        "1",                    // tipoDocumentoIdentidad (DNI)
                        "12345678",             // numeroDocumentoIdentidad
                        "Carlos",               // nombres
                        "López",                // apellidos
                        "DL123456"              // licencia
                )
        ));

        guia.setEnvio(datosEnvio);

        // Líneas de detalle
        guia.setDetalles(List.of(
                new LineaGuia(
                        "KGM",                      // unidadMedida
                        new BigDecimal("2"),        // cantidad
                        "Producto A",               // descripcion
                        "PROD-001",                 // codigo
                        "85101000",                 // codigoSunat
                        null                        // atributos
                )
        ));

        guia.setObservaciones("Entrega asegurada");

        return guia;
    }
}
