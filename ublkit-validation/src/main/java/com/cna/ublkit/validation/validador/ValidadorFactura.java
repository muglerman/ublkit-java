package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Validador de Factura y Boleta según reglas SUNAT (Factura2_0 / Boleta2_0).
 *
 * @since 0.1.0
 */
public class ValidadorFactura implements Validador<BorradorFactura> {

    private static final BigDecimal UMBRAL_IDENTIFICACION_BOLETA = new BigDecimal("700.00");
    private final SerializadorXmlFactura serializadorXml = new SerializadorXmlFactura();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(BorradorFactura objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-001", "El documento no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }

        // Serie
        if (objetivo.getSerie() == null || objetivo.getSerie().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-002", "La serie es requerida", SeveridadValidacion.ERROR));
        } else {
            String serie = objetivo.getSerie().toUpperCase();
            boolean esFactura = "01".equals(objetivo.getTipoComprobante()) || serie.startsWith("F");
            boolean esBoleta = "03".equals(objetivo.getTipoComprobante()) || serie.startsWith("B");

            if (esFactura && !serie.startsWith("F")) {
                resultado.agregar(new IncidenciaValidacion("VAL-003", "La serie de factura debe iniciar con F", SeveridadValidacion.ERROR));
            } else if (esBoleta && !serie.startsWith("B")) {
                resultado.agregar(new IncidenciaValidacion("VAL-004", "La serie de boleta debe iniciar con B", SeveridadValidacion.ERROR));
            }

            // Formato serie: [FB][A-Z0-9]{3}
            if (!serie.matches("[FB][A-Z0-9]{3}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-002b",
                        "La serie debe tener formato [F|B][A-Z0-9]{3} (ej: F001, B001)", SeveridadValidacion.ADVERTENCIA));
            }
        }

        // Número
        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-005", "El número de comprobante es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
        } else if (objetivo.getNumero() > 99999999) {
            resultado.agregar(new IncidenciaValidacion("VAL-005b", "El número de comprobante no puede exceder 99999999", SeveridadValidacion.ERROR));
        }

        // Fecha
        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-006", "La fecha de emisión es requerida", SeveridadValidacion.ERROR));
        }

        // Tipo comprobante
        if (objetivo.getTipoComprobante() != null
                && !"01".equals(objetivo.getTipoComprobante())
                && !"03".equals(objetivo.getTipoComprobante())) {
            resultado.agregar(new IncidenciaValidacion("VAL-006b",
                    "El tipo de comprobante debe ser 01 (Factura) o 03 (Boleta)", SeveridadValidacion.ERROR));
        }

        // Emisor
        if (objetivo.getEmisor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-007", "El emisor es requerido", SeveridadValidacion.ERROR));
        } else {
            if (objetivo.getEmisor().ruc() == null || !objetivo.getEmisor().ruc().matches("\\d{11}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-007b", "El RUC del emisor debe tener 11 dígitos", SeveridadValidacion.ERROR));
            }
            if (objetivo.getEmisor().razonSocial() == null || objetivo.getEmisor().razonSocial().isBlank()) {
                resultado.agregar(new IncidenciaValidacion("VAL-007c", "La razón social del emisor es requerida", SeveridadValidacion.ERROR));
            }
        }

        // Receptor
        validarReceptor(resultado, objetivo);

        // Detalles
        if (objetivo.getDetalles() == null || objetivo.getDetalles().isEmpty()) {
            resultado.agregar(new IncidenciaValidacion("VAL-009", "El documento debe contener al menos un detalle (línea)", SeveridadValidacion.ERROR));
        } else {
            validarDetalles(resultado, objetivo.getDetalles());
        }

        // Moneda
        if (objetivo.getMoneda() != null && !objetivo.getMoneda().matches("[A-Z]{3}")) {
            resultado.agregar(new IncidenciaValidacion("VAL-010", "La moneda debe ser un código ISO 4217 válido (3 letras mayúsculas)", SeveridadValidacion.ERROR));
        }

        // Validación de paridad SUNAT (XSL oficial)
        agregarValidacionSunat(resultado, objetivo);

        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, BorradorFactura objetivo) {
        if (objetivo.getSerie() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            String tipo = tipoComprobante(objetivo);
            ReglaSunatXsl regla = "03".equals(tipo) ? ReglaSunatXsl.BOLETA : ReglaSunatXsl.FACTURA;
            String nombreArchivo = construirNombreArchivo(objetivo, tipo);

            ResultadoValidacion validacionSunat = validadorSunatXsl.validarXml(xml, nombreArchivo, regla);
            for (IncidenciaValidacion incidencia : validacionSunat.getIncidencias()) {
                resultado.agregar(new IncidenciaValidacion(
                        "SUNAT-" + incidencia.codigo(),
                        incidencia.mensaje(),
                        incidencia.severidad()
                ));
            }
        } catch (Exception e) {
            resultado.agregar(new IncidenciaValidacion(
                    "SUNAT-999",
                    "No se pudo ejecutar validación oficial SUNAT: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(BorradorFactura doc, String tipoComprobante) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "00000000000";
        String tipo = tipoComprobante != null ? tipoComprobante : "01";
        return ruc + "-" + tipo + "-" + doc.getSerie() + "-" + doc.getNumero() + ".xml";
    }

    private void validarReceptor(ResultadoValidacion resultado, BorradorFactura objetivo) {
        String tipoComprobante = tipoComprobante(objetivo);
        ReceptorDocumento receptor = objetivo.getReceptor();

        if ("01".equals(tipoComprobante)) {
            validarReceptorFactura(resultado, receptor);
            return;
        }

        if ("03".equals(tipoComprobante)) {
            boolean requiereIdentificacion = requiereIdentificacionBoleta(objetivo);
            if (receptor == null) {
                if (requiereIdentificacion) {
                    resultado.agregar(new IncidenciaValidacion("VAL-008",
                            "Para boletas con importe mayor o igual a 700.00, el receptor es requerido",
                            SeveridadValidacion.ERROR));
                }
                return;
            }
            validarReceptorBoleta(resultado, receptor);
            return;
        }

        if (receptor == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-008", "El receptor es requerido", SeveridadValidacion.ERROR));
        }
    }

    private void validarReceptorFactura(ResultadoValidacion resultado, ReceptorDocumento receptor) {
        if (receptor == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-008", "El receptor es requerido", SeveridadValidacion.ERROR));
            return;
        }
        if (!"6".equals(receptor.tipoDocIdentidad())) {
            resultado.agregar(new IncidenciaValidacion("VAL-008b",
                    "Para facturas, el tipo de documento del receptor debe ser 6 (RUC)",
                    SeveridadValidacion.ERROR));
        }
        if (receptor.numDocIdentidad() == null || !receptor.numDocIdentidad().matches("\\d{11}")) {
            resultado.agregar(new IncidenciaValidacion("VAL-008c",
                    "Para facturas, el número de documento del receptor debe tener 11 dígitos (RUC)",
                    SeveridadValidacion.ERROR));
        }
        if (receptor.nombre() == null || receptor.nombre().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-008d",
                    "Para facturas, el nombre o razón social del receptor es requerido",
                    SeveridadValidacion.ERROR));
        }
    }

    private void validarReceptorBoleta(ResultadoValidacion resultado, ReceptorDocumento receptor) {
        if (receptor.tipoDocIdentidad() == null || receptor.tipoDocIdentidad().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-008e",
                    "Para boletas, el tipo de documento del receptor es requerido",
                    SeveridadValidacion.ERROR));
            return;
        }

        String tipoDoc = receptor.tipoDocIdentidad();
        String numero = receptor.numDocIdentidad();

        if ("6".equals(tipoDoc)) {
            if (numero == null || !numero.matches("\\d{11}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-008f",
                        "Para boletas con RUC, el número debe tener 11 dígitos",
                        SeveridadValidacion.ERROR));
            }
        } else if ("1".equals(tipoDoc)) {
            if (numero == null || !numero.matches("\\d{8}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-008g",
                        "Para boletas con DNI, el número debe tener 8 dígitos",
                        SeveridadValidacion.ERROR));
            }
        } else if (tipoDoc.matches("[047ABCDEFG]")) {
            if (numero == null || numero.isBlank() || !numero.matches("[A-Za-z0-9]{1,15}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-008h",
                        "Para boletas, el número de documento no cumple el formato permitido del tipo de documento",
                        SeveridadValidacion.ERROR));
            }
        } else {
            resultado.agregar(new IncidenciaValidacion("VAL-008i",
                    "Para boletas, el tipo de documento del receptor no es válido",
                    SeveridadValidacion.ERROR));
        }

        if (receptor.nombre() == null || receptor.nombre().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-008j",
                    "Para boletas, el nombre o razón social del receptor es requerido",
                    SeveridadValidacion.ERROR));
        }
    }

    private String tipoComprobante(BorradorFactura factura) {
        if (factura.getTipoComprobante() != null) return factura.getTipoComprobante();
        if (factura.getSerie() != null && factura.getSerie().toUpperCase().startsWith("F")) return "01";
        if (factura.getSerie() != null && factura.getSerie().toUpperCase().startsWith("B")) return "03";
        return null;
    }

    private boolean requiereIdentificacionBoleta(BorradorFactura factura) {
        BigDecimal total = null;
        if (factura.getTotalImporte() != null && factura.getTotalImporte().importe() != null) {
            total = factura.getTotalImporte().importe();
        } else if (factura.getDetalles() != null) {
            total = BigDecimal.ZERO;
            for (LineaDetalle linea : factura.getDetalles()) {
                if (linea.getCantidad() == null || linea.getPrecio() == null) continue;
                total = total.add(linea.getCantidad().multiply(linea.getPrecio()));
            }
        }
        if (total == null) return false;
        return total.compareTo(UMBRAL_IDENTIFICACION_BOLETA) >= 0;
    }

    private void validarDetalles(ResultadoValidacion resultado, List<LineaDetalle> detalles) {
        for (int i = 0; i < detalles.size(); i++) {
            LineaDetalle linea = detalles.get(i);
            String prefijo = "Línea " + (i + 1) + ": ";

            if (linea.getCantidad() == null || linea.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
                resultado.agregar(new IncidenciaValidacion("VAL-009b", prefijo + "La cantidad debe ser mayor a cero", SeveridadValidacion.ERROR));
            }

            if (linea.getPrecio() == null) {
                resultado.agregar(new IncidenciaValidacion("VAL-009c", prefijo + "El precio unitario es requerido", SeveridadValidacion.ERROR));
            }

            if (linea.getDescripcion() == null || linea.getDescripcion().isBlank()) {
                resultado.agregar(new IncidenciaValidacion("VAL-009d", prefijo + "La descripción del producto/servicio es requerida", SeveridadValidacion.ERROR));
            }
        }
    }
}
