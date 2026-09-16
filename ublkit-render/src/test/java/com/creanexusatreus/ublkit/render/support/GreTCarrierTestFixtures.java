package com.creanexusatreus.ublkit.render.support;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import com.creanexusatreus.ublkit.core.modelo.Contacto;
import com.creanexusatreus.ublkit.core.modelo.Direccion;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.creanexusatreus.ublkit.ubl.modelo.guia.Conductor;
import com.creanexusatreus.ublkit.ubl.modelo.guia.DatosEnvio;
import com.creanexusatreus.ublkit.ubl.modelo.guia.DestinatarioGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.DocumentoRelacionadoGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.LineaGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.PuntoDestino;
import com.creanexusatreus.ublkit.ubl.modelo.guia.PuntoPartida;
import com.creanexusatreus.ublkit.ubl.modelo.guia.TerceroGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.TransportistaGuia;
import com.creanexusatreus.ublkit.ubl.modelo.guia.Vehiculo;

public final class GreTCarrierTestFixtures {

    public static final String HASH = "sha1-gret-2026-09-16-visual-regression";
    public static final String QR_BASE64 = buildQrLikePngBase64();
    public static final String LOGO_DATA_URI = buildLogoDataUri();

    private GreTCarrierTestFixtures() {
    }

    public record CarrierScenario(String snapshotName, BorradorGuiaRemision guia, Map<String, Object> atributos) {
    }

    public static CarrierScenario bothSubcontractingScenario() {
        return scenario("semantic-both-subcontracting", guia -> {
            guia.setSubcontratado(new TerceroGuia("6", "20456789012", "Contratante SUNAT del Transporte S.A.C.",
                    "MTC-SUNAT-221"));
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                    "SUNAT_Envio_IndicadorTransbordoProgramado",
                    "SUNAT_Envio_IndicadorTrasporteSubcontratado"));
        }, attrs -> {
            attrs.put("tipoPagadorFlete", "Subcontratador");
            attrs.put("estadoGreT", "GRE-T VÁLIDA");
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
        });
    }

    public static CarrierScenario thirdPartyPayerScenario() {
        return scenario("semantic-third-party-payer", guia -> {
            guia.setPagadorFleteTercero(new TerceroGuia("6", "20100099988", "Tercero Pagador de Flete S.A.C.", null));
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                    "SUNAT_Envio_IndicadorPagadorFlete_Tercero"));
        }, attrs -> {
            attrs.put("tipoPagadorFlete", "Tercero");
            attrs.put("pagadorFleteTerceroNombre", "Tercero Pagador de Flete S.A.C.");
            attrs.put("pagadorFleteTerceroDocumento", "20100099988");
        });
    }

    public static CarrierScenario multipageScenario() {
        return scenario("15-multipage", guia -> {
            guia.setNumero(9876);
            guia.setSubcontratado(new TerceroGuia("6", "20456789012", "Contratante SUNAT del Transporte S.A.C.",
                    "MTC-SUNAT-221"));
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                    "SUNAT_Envio_IndicadorRetornoVehiculoEnvaseVacio",
                    "SUNAT_Envio_IndicadorTransbordoProgramado",
                    "SUNAT_Envio_IndicadorTrasporteSubcontratado"));
            guia.setDetalles(buildItems(58, true));
            guia.setObservaciones("Validar descarga con patio 4\nNo repetir bloques operativos en páginas siguientes");
        }, attrs -> {
            attrs.put("tipoPagadorFlete", "Subcontratador");
            attrs.put("estadoGreT", "GRE-T PENDIENTE");
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
            attrs.put("trackingNumber", "TRACK-MULTI-2026-0099");
        });
    }

    public static List<CarrierScenario> classicMonoVisualScenarios() {
        List<CarrierScenario> scenarios = new ArrayList<>();
        scenarios.add(scenario("01-basic", guia -> {
            guia.setSubcontratado(null);
            guia.setObservaciones("Recepción previa coordinación");
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                    "SUNAT_Envio_IndicadorTransbordoProgramado"));
        }, attrs -> {
            attrs.put("estadoGreT", "GRE-T PENDIENTE");
            attrs.remove("subcontratistaNombre");
            attrs.remove("subcontratistaRuc");
            attrs.remove("subcontratistaMtc");
            attrs.put("tipoPagadorFlete", "Remitente");
        }));
        scenarios.add(scenario("02-sunat-subcontracting", guia -> {
            guia.setSubcontratado(new TerceroGuia("6", "20456789012", "Contratante SUNAT del Transporte S.A.C.",
                    "MTC-SUNAT-221"));
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorTrasporteSubcontratado",
                    "SUNAT_Envio_IndicadorTransbordoProgramado"));
        }, attrs -> {
            attrs.put("estadoGreT", "GRE-T VÁLIDA");
            attrs.remove("subcontratistaNombre");
            attrs.remove("subcontratistaRuc");
            attrs.remove("subcontratistaMtc");
            attrs.put("tipoPagadorFlete", "Subcontratador");
        }));
        scenarios.add(scenario("03-internal-subcontractor", guia -> {
            guia.setSubcontratado(null);
            guia.getEnvio().setIndicadores(List.of("SUNAT_Envio_IndicadorRetornoVehiculoVacio"));
        }, attrs -> {
            attrs.put("estadoGreT", "GRE-T RECHAZADA");
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
            attrs.put("tipoPagadorFlete", "Remitente");
        }));
        scenarios.add(scenario("04-both-subcontracting", guia -> {
            guia.setSubcontratado(new TerceroGuia("6", "20456789012", "Contratante SUNAT del Transporte S.A.C.",
                    "MTC-SUNAT-221"));
            guia.getEnvio().setIndicadores(List.of(
                    "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                    "SUNAT_Envio_IndicadorTransbordoProgramado",
                    "SUNAT_Envio_IndicadorTrasporteSubcontratado"));
        }, attrs -> {
            attrs.put("tipoPagadorFlete", "Subcontratador");
            attrs.put("estadoGreT", "GRE-T VÁLIDA");
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
        }));
        scenarios.add(scenario("05-issuer-mtc-only", guia -> {
            guia.setSubcontratado(null);
            guia.getEnvio().setTransportista(new TransportistaGuia("6", "20600456789",
                    "Transportes Mantaro E.I.R.L.", "MTC-EMISOR-001"));
        }, attrs -> {
            attrs.remove("subcontratistaNombre");
            attrs.remove("subcontratistaRuc");
            attrs.remove("subcontratistaMtc");
        }));
        scenarios.add(scenario("06-internal-mtc-only", guia -> {
            guia.setSubcontratado(null);
            guia.getEnvio().setTransportista(new TransportistaGuia("6", "20600456789",
                    "Transportes Mantaro E.I.R.L.", null));
        }, attrs -> {
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
        }));
        scenarios.add(scenario("07-dual-mtc-separate", guia -> {
            guia.setSubcontratado(new TerceroGuia("6", "20456789012", "Contratante SUNAT del Transporte S.A.C.",
                    "MTC-SUNAT-221"));
            guia.getEnvio().setTransportista(new TransportistaGuia("6", "20600456789",
                    "Transportes Mantaro E.I.R.L.", "MTC-EMISOR-001"));
        }, attrs -> {
            attrs.put("subcontratistaNombre", "Proveedor Interno de Ruta S.A.C.");
            attrs.put("subcontratistaRuc", "20500199887");
            attrs.put("subcontratistaMtc", "MTC-INTERNO-778");
        }));
        scenarios.add(scenario("08-tracking-present", guia -> {
        }, attrs -> attrs.put("trackingNumber", "TRACK-2026-0011")));
        scenarios.add(scenario("09-tracking-absent", guia -> {
        }, attrs -> attrs.remove("trackingNumber")));
        scenarios.add(scenario("10-observations-present", guia -> {
            guia.setObservaciones("Entrega coordinada con patio 5\nMantener sello interno en guía de arribo");
        }, attrs -> {
        }));
        scenarios.add(scenario("11-observations-empty", guia -> guia.setObservaciones(null), attrs -> {
        }));
        scenarios.add(scenario("12-long-company-name", guia -> guia.setRemitente(new EmisorDocumento(
                guia.getRemitente().ruc(),
                "Transportes Mantaro",
                "Transportes Mantaro Soluciones Intermodales y Operaciones Complementarias del Centro S.A.C.",
                guia.getRemitente().direccion(),
                guia.getRemitente().contacto())), attrs -> {
        }));
        scenarios.add(scenario("13-long-address", guia -> {
            guia.getEnvio().setPartida(new PuntoPartida("120101",
                    "Av. Circunvalación Logística Km 18.5, Asociación Parque Industrial del Valle, Lote 19-A, Nave 4",
                    "0001", "20600456789"));
            guia.getEnvio().setDestino(new PuntoDestino("150142",
                    "Centro de Distribución Sur, Sector B, Plataforma 14, Complejo Logístico Villa El Salvador",
                    "0002", "20567890123"));
        }, attrs -> {
        }));
        scenarios.add(scenario("14-multiple-items", guia -> guia.setDetalles(buildItems(24, true)), attrs -> {
        }));
        scenarios.add(multipageScenario());
        return scenarios;
    }

    public static BorradorGuiaRemision baseCarrierGuide() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("V001");
        guia.setNumero(9123);
        guia.setFechaEmision(LocalDate.of(2026, 9, 16));
        guia.setHoraEmision(LocalTime.of(6, 45));
        guia.setTipoComprobante("31");
        guia.setObservaciones("Recepción previa coordinación");
        guia.setRemitente(new EmisorDocumento(
                "20600456789",
                "Transportes Mantaro",
                "Transportes Mantaro E.I.R.L.",
                new Direccion("120114", "0001", null, "Junín", "Huancayo", "El Tambo",
                        "Av. Ferrocarril 1250", "PE"),
                new Contacto("Mesa logística", "064-555100", "contacto@mantaro.pe")));
        guia.setTercero(new TerceroGuia("6", "20123456789", "Manufacturas Andina Textil S.A.C.", null));
        guia.setDestinatario(new DestinatarioGuia("6", "20567890123", "Centro de Distribución Andino S.A.C."));
        guia.setDocumentosRelacionados(List.of(
                new DocumentoRelacionadoGuia("01", "F001-4567"),
                new DocumentoRelacionadoGuia("09", "T003-00881")));
        guia.setEnvio(baseShipment());
        guia.setDetalles(buildItems(12, false));
        return guia;
    }

    public static Map<String, Object> baseCarrierAttributes() {
        LinkedHashMap<String, Object> attrs = new LinkedHashMap<>();
        attrs.put("logo", LOGO_DATA_URI);
        attrs.put("consultaUrl", "https://consulta.quantus.test/gre");
        attrs.put("footer", "Ruta validada por Quantus · documento de prueba controlado");
        attrs.put("establishmentName", "Base Huancayo");
        attrs.put("partidaDistrito", "El Tambo");
        attrs.put("partidaProvincia", "Huancayo");
        attrs.put("partidaDepartamento", "Junín");
        attrs.put("destinoDistrito", "Villa El Salvador");
        attrs.put("destinoProvincia", "Lima");
        attrs.put("destinoDepartamento", "Lima");
        attrs.put("estadoPago", "PAGADO");
        attrs.put("trackingNumber", "TRACK-2026-001");
        attrs.put("tipoPagadorFlete", "Remitente");
        attrs.put("totalGuia", new BigDecimal("245.50"));
        attrs.put("estadoGreT", "GRE-T PENDIENTE");
        return attrs;
    }

    private static DatosEnvio baseShipment() {
        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("1480.50"));
        envio.setPesoTotalUnidadMedida("KGM");
        envio.setNumeroDeBultos(38);
        envio.setTipoModalidadTraslado("01");
        envio.setFechaTraslado(LocalDate.of(2026, 9, 16));
        envio.setTransportista(new TransportistaGuia("6", "20600456789", "Transportes Mantaro E.I.R.L.",
                "MTC-EMISOR-001"));
        envio.setIndicadores(List.of(
                "SUNAT_Envio_IndicadorRetornoVehiculoVacio",
                "SUNAT_Envio_IndicadorTransbordoProgramado"));
        envio.setPartida(new PuntoPartida("120101", "Av. Los Talleres 845, Zona Industrial 2", "0001",
                "20600456789"));
        envio.setDestino(new PuntoDestino("150142", "Parque Logístico Villa El Salvador, Puerta 7", "0002",
                "20567890123"));
        envio.setVehiculo(new Vehiculo(
                "ABC-123", "TUC-001", "HAB-2024-001", "MTC", "VOLVO", "FH16",
                List.of(new Vehiculo("REM-456", "TUC-002", "HAB-2024-002", "MTC", "RANDON", "SR-2026", null))));
        envio.setChoferes(List.of(
                new Conductor("Principal", "1", "44332211", "Carlos", "López", "DL123456"),
                new Conductor("Secundario", "1", "77889944", "María", "Rojas", "DL654321")));
        return envio;
    }

    private static List<LineaGuia> buildItems(int count, boolean longDescription) {
        List<LineaGuia> detalles = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String suffix = String.format("%03d", i);
            String descripcion = "Producto multipropósito de distribución controlada lote " + suffix;
            if (longDescription) {
                descripcion += " · embalaje reforzado para tránsito interprovincial y control documental en patio "
                        + ((i % 9) + 1);
            }
            detalles.add(new LineaGuia(
                    "KGM",
                    BigDecimal.valueOf((i % 7) + 1L),
                    descripcion,
                    "PROD-" + suffix,
                    "85101000",
                    null));
        }
        return detalles;
    }

    private static CarrierScenario scenario(String name, Consumer<BorradorGuiaRemision> guideCustomizer,
            Consumer<Map<String, Object>> attrsCustomizer) {
        BorradorGuiaRemision guia = baseCarrierGuide();
        LinkedHashMap<String, Object> attrs = new LinkedHashMap<>(baseCarrierAttributes());
        guideCustomizer.accept(guia);
        attrsCustomizer.accept(attrs);
        return new CarrierScenario(name, guia, Map.copyOf(attrs));
    }

    private static String buildLogoDataUri() {
        String svg = """
                <svg xmlns='http://www.w3.org/2000/svg' width='72' height='72' viewBox='0 0 72 72'>
                  <rect width='72' height='72' rx='14' fill='#111827'/>
                  <path d='M16 46 28 23l10 12 18-17' fill='none' stroke='#f97316' stroke-width='6' stroke-linecap='round' stroke-linejoin='round'/>
                </svg>
                """;
        return "data:image/svg+xml;utf8," + URLEncoder.encode(svg, StandardCharsets.UTF_8).replace("+", "%20");
    }

    private static String buildQrLikePngBase64() {
        try {
            int size = 33;
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);
            graphics.setColor(Color.BLACK);
            paintFinder(graphics, 2, 2);
            paintFinder(graphics, size - 11, 2);
            paintFinder(graphics, 2, size - 11);
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if ((x < 11 && y < 11) || (x >= size - 11 && y < 11) || (x < 11 && y >= size - 11)) {
                        continue;
                    }
                    if (((x * 7) + (y * 11)) % 5 == 0 || ((x + y) % 7 == 0)) {
                        image.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
            }
            graphics.dispose();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "png", output);
            return Base64.getEncoder().encodeToString(output.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el QR visual de prueba", e);
        }
    }

    private static void paintFinder(Graphics2D graphics, int x, int y) {
        graphics.fillRect(x, y, 9, 9);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x + 2, y + 2, 5, 5);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x + 3, y + 3, 3, 3);
    }
}
