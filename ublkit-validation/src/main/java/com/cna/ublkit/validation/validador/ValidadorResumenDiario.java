package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;
import com.cna.ublkit.ubl.xml.SerializadorXmlResumenDiario;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

import java.time.format.DateTimeFormatter;

/**
 * Validador de Resumen Diario (SummaryDocuments) según reglas SUNAT.
 *
 * @since 0.1.0
 */
public class ValidadorResumenDiario implements Validador<ResumenDiario> {
    private static final DateTimeFormatter FORMATO_FECHA_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final SerializadorXmlResumenDiario serializadorXml = new SerializadorXmlResumenDiario();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(ResumenDiario objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-001", "El documento no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }

        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-002", "La fecha de emisión (IssueDate) es requerida", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmisionComprobantes() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-003", "La fecha de emisión de comprobantes (ReferenceDate) es requerida", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmision() != null && objetivo.getFechaEmisionComprobantes() != null
                && objetivo.getFechaEmisionComprobantes().isAfter(objetivo.getFechaEmision())) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-004",
                    "La fecha de generación del resumen debe ser mayor o igual a la fecha de emisión de los comprobantes",
                    SeveridadValidacion.ERROR));
        }

        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-005", "El número correlativo es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
        }

        if (objetivo.getEmisor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-006", "El emisor es requerido", SeveridadValidacion.ERROR));
        } else {
            if (objetivo.getEmisor().ruc() == null || objetivo.getEmisor().ruc().isBlank()) {
                resultado.agregar(new IncidenciaValidacion("VAL-RC-007", "El RUC del emisor es requerido", SeveridadValidacion.ERROR));
            } else if (!objetivo.getEmisor().ruc().matches("\\d{11}")) {
                resultado.agregar(new IncidenciaValidacion("VAL-RC-008", "El RUC del emisor debe tener 11 dígitos", SeveridadValidacion.ERROR));
            }
        }

        if (objetivo.getComprobantes() == null || objetivo.getComprobantes().isEmpty()) {
            resultado.agregar(new IncidenciaValidacion("VAL-RC-009", "Debe contener al menos un comprobante en el resumen", SeveridadValidacion.ERROR));
        } else {
            for (int i = 0; i < objetivo.getComprobantes().size(); i++) {
                ItemResumenDiario item = objetivo.getComprobantes().get(i);
                String prefijo = "Línea " + (i + 1) + ": ";

                if (item.tipoOperacion() == null || item.tipoOperacion().isBlank()) {
                    resultado.agregar(new IncidenciaValidacion("VAL-RC-010", prefijo + "El tipo de operación (ConditionCode) es requerido", SeveridadValidacion.ERROR));
                } else if (!item.tipoOperacion().matches("[123]")) {
                    resultado.agregar(new IncidenciaValidacion("VAL-RC-011", prefijo + "El tipo de operación debe ser 1 (adicionar), 2 (modificar) o 3 (anular)", SeveridadValidacion.ERROR));
                }

                if (item.comprobante() == null) {
                    resultado.agregar(new IncidenciaValidacion("VAL-RC-012", prefijo + "El comprobante es requerido", SeveridadValidacion.ERROR));
                } else {
                    var comp = item.comprobante();
                    if (comp.getTipoComprobante() == null || comp.getTipoComprobante().isBlank()) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-013", prefijo + "El tipo de comprobante es requerido", SeveridadValidacion.ERROR));
                    } else if (!"03".equals(comp.getTipoComprobante()) && !"07".equals(comp.getTipoComprobante()) && !"08".equals(comp.getTipoComprobante())) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-014",
                                prefijo + "El tipo de comprobante debe ser 03 (Boleta), 07 (Nota Crédito) u 08 (Nota Débito)",
                                SeveridadValidacion.ERROR));
                    }
                    if (comp.getSerieNumero() == null || comp.getSerieNumero().isBlank()) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-015", prefijo + "La serie-número del comprobante es requerida", SeveridadValidacion.ERROR));
                    }
                    if (comp.getValorVenta() == null) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-016", prefijo + "Los valores de venta son requeridos", SeveridadValidacion.ERROR));
                    }
                    if (comp.getImpuestos() == null) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-017", prefijo + "Los impuestos del comprobante son requeridos", SeveridadValidacion.ERROR));
                    }
                    if (("07".equals(comp.getTipoComprobante()) || "08".equals(comp.getTipoComprobante()))
                            && comp.getComprobanteAfectado() == null) {
                        resultado.agregar(new IncidenciaValidacion("VAL-RC-018",
                                prefijo + "Las notas de crédito/débito en resumen requieren comprobante afectado (BillingReference)",
                                SeveridadValidacion.ERROR));
                    }
                }
            }
        }

        agregarValidacionSunat(resultado, objetivo);
        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, ResumenDiario objetivo) {
        if (objetivo.getFechaEmision() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            String nombreArchivo = construirNombreArchivo(objetivo);
            ResultadoValidacion validacionSunat = validadorSunatXsl.validarXml(xml, nombreArchivo, ReglaSunatXsl.RESUMEN_DIARIO);
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
                    "No se pudo ejecutar validación oficial SUNAT de Resumen Diario: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(ResumenDiario doc) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "00000000000";
        String fecha = doc.getFechaEmision().format(FORMATO_FECHA_ARCHIVO);
        return ruc + "-RC-" + fecha + "-" + doc.getNumero() + ".xml";
    }
}
