package com.cna.ublkit.render.html;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.Conductor;
import com.cna.ublkit.ubl.modelo.guia.Vehiculo;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Convierte un BorradorGuiaRemision en HTML utilizando la plantilla Pebble de despacho (despatch.html).
 * Parsea el modelo de dominio en los diccionarios compatibles con las llaves que espera la vista.
 * Soporta formatos A4 y A5.
 *
 * @since 0.1.0
 */
public class RenderizadorHtmlGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {

    private final PebbleEngine engine;
    private final FormatoImpresion formato;

    public RenderizadorHtmlGuiaRemision() {
         this(FormatoImpresion.A4);
    }

    public RenderizadorHtmlGuiaRemision(FormatoImpresion formato) {
         this.engine = new PebbleEngine.Builder().build();
         this.formato = formato;
    }

    private String obtenerRutaPlantilla() {
        return switch(formato) {
            case A5 -> "templates/despatch.a5.html";
            default -> "templates/despatch.a4.html";
        };
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        BorradorGuiaRemision doc = contexto.documento();

        Map<String, Object> receipt = new HashMap<>();

        // Identificadores y fechas.
        receipt.put("type", parseTipo(doc.getTipoComprobante()));
        receipt.put("typedIdentity", texto(doc.getTipoComprobante()));
        receipt.put("identity", (texto(doc.getSerie()).isBlank() ? "-" : texto(doc.getSerie())) + "-" + (doc.getNumero() != null ? doc.getNumero() : "-"));
        receipt.put("name", "31".equals(doc.getTipoComprobante()) ? "GUÍA DE REMISIÓN TRANSPORTISTA" : "GUÍA DE REMISIÓN REMITENTE");
        receipt.put("issueDate", texto(doc.getFechaEmision()));
        receipt.put("note", texto(doc.getObservaciones()));

        // Metadatos y firmas.
        receipt.put("hash", texto(contexto.hashDocumento()));
        receipt.put("qr", texto(contexto.qrBase64()));
        receipt.put("logo", "logo.jpg");

        // Taxpayer / Remitente.
        if (doc.getRemitente() != null) {
            Map<String, Object> taxpayer = new HashMap<>();
            taxpayer.put("identity", texto(doc.getRemitente().ruc()));
            taxpayer.put("name", texto(doc.getRemitente().razonSocial()));
            taxpayer.put("address", doc.getEnvio() != null && doc.getEnvio().getPartida() != null
                    ? texto(doc.getEnvio().getPartida().direccion())
                    : "");

            Map<String, Object> contact = new HashMap<>();
            contact.put("email", "");
            contact.put("telephone", "");
            contact.put("web", "");
            taxpayer.put("contact", contact);

            receipt.put("taxpayer", taxpayer);
        }

        // Customer / Destinatario.
        if (doc.getDestinatario() != null) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("identity", texto(doc.getDestinatario().numeroDocumentoIdentidad()));
            customer.put("name", texto(doc.getDestinatario().nombre()));
            customer.put("address", doc.getEnvio() != null && doc.getEnvio().getDestino() != null
                    ? texto(doc.getEnvio().getDestino().direccion())
                    : "");
            receipt.put("customer", customer);
        }

        // Datos del traslado (envío).
        if (doc.getEnvio() != null) {
            receipt.put("startDate", texto(doc.getEnvio().getFechaTraslado()));
            receipt.put("weight", texto(doc.getEnvio().getPesoTotal()));
            receipt.put("unitCode", texto(doc.getEnvio().getPesoTotalUnidadMedida()));
            receipt.put("packages", doc.getEnvio().getNumeroDeBultos());
            receipt.put("handling", descripcionMotivoTraslado(doc.getEnvio().getTipoTraslado(), doc.getEnvio().getMotivoTraslado()));
            receipt.put("lightVehicle", esVehiculoM1L(doc.getEnvio().getIndicadores()));
            receipt.put("ownTransport", "01".equals(doc.getEnvio().getTipoModalidadTraslado()));
            receipt.put("transportModeCode", texto(doc.getEnvio().getTipoModalidadTraslado()));
            receipt.put("transportModeName", descripcionModalidadTraslado(doc.getEnvio().getTipoModalidadTraslado()));

            Map<String, Object> address = new HashMap<>();
            if (doc.getEnvio().getPartida() != null) {
                Map<String, String> origin = new HashMap<>();
                origin.put("ubigeo", texto(doc.getEnvio().getPartida().ubigeo()));
                origin.put("line", texto(doc.getEnvio().getPartida().direccion()));
                address.put("origin", origin);
            }
            if (doc.getEnvio().getDestino() != null) {
                Map<String, String> delivery = new HashMap<>();
                delivery.put("ubigeo", texto(doc.getEnvio().getDestino().ubigeo()));
                delivery.put("line", texto(doc.getEnvio().getDestino().direccion()));
                address.put("delivery", delivery);
            }
            receipt.put("address", address);

            if (doc.getEnvio().getTransportista() != null) {
                Map<String, Object> carrier = new HashMap<>();
                carrier.put("name", texto(doc.getEnvio().getTransportista().nombre()));
                carrier.put("identity", texto(doc.getEnvio().getTransportista().numeroDocumentoIdentidad()));
                carrier.put("documentType", texto(doc.getEnvio().getTransportista().tipoDocumentoIdentidad()));
                carrier.put("mtc", texto(doc.getEnvio().getTransportista().numeroRegistroMTC()));
                receipt.put("carrier", carrier);
            }

            if (doc.getEnvio().getVehiculo() != null) {
                List<Map<String, Object>> vehicles = new ArrayList<>();
                vehicles.add(vehiculoMap(doc.getEnvio().getVehiculo(), true));
                if (doc.getEnvio().getVehiculo().secundarios() != null) {
                    for (Vehiculo secundario : doc.getEnvio().getVehiculo().secundarios()) {
                        vehicles.add(vehiculoMap(secundario, false));
                    }
                }
                receipt.put("vehicles", vehicles);
            }

            if (doc.getEnvio().getChoferes() != null) {
                List<Map<String, Object>> drivers = doc.getEnvio().getChoferes().stream()
                        .map(this::conductorMap)
                        .collect(Collectors.toList());
                receipt.put("drivers", drivers);
            }
        }

        // Referencia documental.
        Map<String, Object> reference = new HashMap<>();
        reference.put("note", construirReferencia(doc));
        receipt.put("reference", reference);

        // Ítems.
        if (doc.getDetalles() != null) {
            List<Map<String, Object>> items = doc.getDetalles().stream().map(linea -> {
                Map<String, Object> item = new HashMap<>();
                item.put("quantity", texto(linea.cantidad()));
                item.put("unitCode", texto(linea.unidadMedida()));
                item.put("description", texto(linea.descripcion()));
                item.put("code", texto(linea.codigo()));
                return item;
            }).collect(Collectors.toList());
            receipt.put("items", items);
        }

        Map<String, Object> scope = new HashMap<>();
        scope.put("receipt", receipt);

        try {
            PebbleTemplate compiledTemplate = engine.getTemplate(obtenerRutaPlantilla());
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, scope);
            return ResultadoRender.html(writer.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error renderizando guía HTML: " + e.getMessage(), e);
        }
    }

    private int parseTipo(String tipoComprobante) {
        try {
            return tipoComprobante != null ? Integer.parseInt(tipoComprobante) : 9;
        } catch (NumberFormatException ex) {
            return 9;
        }
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }

    private String descripcionMotivoTraslado(String tipo, String descripcion) {
        String cod = texto(tipo);
        String desc = texto(descripcion);
        String catalogo = switch (cod) {
            case "01" -> "Venta";
            case "02" -> "Compra";
            case "04" -> "Traslado entre establecimientos";
            case "08" -> "Importación";
            case "09" -> "Exportación";
            case "13" -> "Otros";
            default -> "";
        };
        if (desc.isBlank()) {
            desc = catalogo;
        }
        if (!cod.isBlank() && !desc.isBlank()) {
            return cod + " - " + desc;
        }
        return !desc.isBlank() ? desc : cod;
    }

    private String descripcionModalidadTraslado(String tipoModalidad) {
        return switch (texto(tipoModalidad)) {
            case "01" -> "Privado";
            case "02" -> "Público";
            default -> "";
        };
    }

    private boolean esVehiculoM1L(List<String> indicadores) {
        if (indicadores == null) {
            return false;
        }
        return indicadores.stream()
                .filter(i -> i != null && !i.isBlank())
                .map(String::toUpperCase)
                .anyMatch(i -> i.contains("M1") || i.contains("L"));
    }

    private Map<String, Object> vehiculoMap(Vehiculo vehiculo, boolean principal) {
        Map<String, Object> v = new HashMap<>();
        v.put("identity", texto(vehiculo.placa()));
        v.put("label", principal ? "Principal" : "Secundario");
        v.put("number", texto(vehiculo.numeroCirculacion()));
        v.put("authorization", texto(vehiculo.numeroAutorizacion()));
        return v;
    }

    private Map<String, Object> conductorMap(Conductor conductor) {
        Map<String, Object> driver = new HashMap<>();
        driver.put("type", texto(conductor.tipo()));
        driver.put("name", texto(conductor.nombres()));
        driver.put("surname", texto(conductor.apellidos()));
        driver.put("identity", texto(conductor.numeroDocumentoIdentidad()));
        driver.put("documentType", texto(conductor.tipoDocumentoIdentidad()));
        driver.put("license", texto(conductor.licencia()));
        return driver;
    }

    private String construirReferencia(BorradorGuiaRemision doc) {
        if (doc.getDocumentoRelacionado() != null) {
            return texto(doc.getDocumentoRelacionado().tipoDocumento()) + " " + texto(doc.getDocumentoRelacionado().serieNumero()).trim();
        }
        if (doc.getDocumentoBaja() != null) {
            return texto(doc.getDocumentoBaja().tipoDocumento()) + " " + texto(doc.getDocumentoBaja().serieNumero()).trim();
        }
        if (doc.getDocumentosRelacionados() != null && !doc.getDocumentosRelacionados().isEmpty()) {
            return texto(doc.getDocumentosRelacionados().get(0).tipoDocumento()) + " "
                    + texto(doc.getDocumentosRelacionados().get(0).serieNumero()).trim();
        }
        return "";
    }
}
