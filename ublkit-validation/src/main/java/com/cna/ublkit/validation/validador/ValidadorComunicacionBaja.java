package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.Reversion;
import com.cna.ublkit.ubl.xml.SerializadorXmlComunicacionBaja;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

import java.time.format.DateTimeFormatter;

/**
 * Validador de Comunicación de Baja (VoidedDocuments) según reglas SUNAT v2026.
 *
 * @since 0.1.0
 */
public class ValidadorComunicacionBaja implements Validador<ComunicacionBaja> {
    private static final DateTimeFormatter FORMATO_FECHA_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final SerializadorXmlComunicacionBaja serializadorXml = new SerializadorXmlComunicacionBaja();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(ComunicacionBaja objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-001", "El documento no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }

        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-002", "La fecha de emisión es requerida", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmisionComprobantes() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-003", "La fecha de emisión de comprobantes es requerida", SeveridadValidacion.ERROR));
        }

        // SUNAT: ReferenceDate no puede ser mayor a IssueDate
        if (objetivo.getFechaEmision() != null && objetivo.getFechaEmisionComprobantes() != null
                && objetivo.getFechaEmisionComprobantes().isAfter(objetivo.getFechaEmision())) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-003b",
                    "La fecha de generación de la comunicación debe ser mayor o igual a la fecha de emisión de los comprobantes",
                    SeveridadValidacion.ERROR));
        }

        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-004", "El número correlativo es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
        }

        if (objetivo.getEmisor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-005", "El emisor es requerido", SeveridadValidacion.ERROR));
        } else {
            if (objetivo.getEmisor().ruc() == null || !objetivo.getEmisor().ruc().matches("\\d{11}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-CB-005b", "El RUC del emisor debe tener 11 dígitos", SeveridadValidacion.ERROR));
            }
        }

        if (objetivo.getComprobantes() == null || objetivo.getComprobantes().isEmpty()) {
            resultado.agregar(new IncidenciaValidacion("VAL-CB-006", "Debe contener al menos un comprobante a dar de baja", SeveridadValidacion.ERROR));
        } else {
            for (int i = 0; i < objetivo.getComprobantes().size(); i++) {
                var item = objetivo.getComprobantes().get(i);
                String prefijo = "Línea " + (i + 1) + ": ";

                if (item.tipoComprobante() == null || item.tipoComprobante().isBlank()) {
                    resultado.agregar(new IncidenciaValidacion("VAL-CB-007", prefijo + "El tipo de comprobante es requerido", SeveridadValidacion.ERROR));
                }
                if (item.serie() == null || item.serie().isBlank()) {
                    resultado.agregar(new IncidenciaValidacion("VAL-CB-008", prefijo + "La serie es requerida", SeveridadValidacion.ERROR));
                }
                if (item.numero() == null || item.numero() <= 0) {
                    resultado.agregar(new IncidenciaValidacion("VAL-CB-009", prefijo + "El número es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
                }
                if (item.descripcionSustento() == null || item.descripcionSustento().isBlank()) {
                    resultado.agregar(new IncidenciaValidacion("VAL-CB-010", prefijo + "La descripción del sustento es requerida", SeveridadValidacion.ERROR));
                }
            }
        }

        agregarValidacionSunat(resultado, objetivo);
        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, ComunicacionBaja objetivo) {
        if (objetivo.getFechaEmision() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            String nombreArchivo = construirNombreArchivo(objetivo);
            ResultadoValidacion validacionSunat = validadorSunatXsl.validarXml(xml, nombreArchivo, ReglaSunatXsl.COMUNICACION_BAJA);
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
                    "No se pudo ejecutar validación oficial SUNAT de Comunicación de Baja: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(ComunicacionBaja doc) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "00000000000";
        String prefijo = doc instanceof Reversion ? "RR" : "RA";
        String fecha = doc.getFechaEmision().format(FORMATO_FECHA_ARCHIVO);
        return ruc + "-" + prefijo + "-" + fecha + "-" + doc.getNumero() + ".xml";
    }
}
