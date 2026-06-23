package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.xml.SerializadorXmlGuiaRemision;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

public class ValidadorGuiaRemision implements Validador<BorradorGuiaRemision> {
    private final SerializadorXmlGuiaRemision serializadorXml = new SerializadorXmlGuiaRemision();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(BorradorGuiaRemision objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-001", "La Guía de Remisión no puede ser nula", SeveridadValidacion.ERROR));
            return resultado;
        }

        if (objetivo.getSerie() == null || objetivo.getSerie().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-002", "La serie es requerida", SeveridadValidacion.ERROR));
        } else {
            String serie = objetivo.getSerie();
            if (objetivo.isGRERemitente() && !serie.startsWith("T") && !serie.startsWith("E")) {
                resultado.agregar(new IncidenciaValidacion("VAL-GRE-003", "La serie de GRE-Remitente debe iniciar con T o E", SeveridadValidacion.ERROR));
            } else if (objetivo.isGRETransportista() && !serie.startsWith("V") && !serie.startsWith("E")) {
                resultado.agregar(new IncidenciaValidacion("VAL-GRE-004", "La serie de GRE-Transportista debe iniciar con V o E", SeveridadValidacion.ERROR));
            }
        }

        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-005", "El número de comprobante es requerido y debe ser mayor a 0", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-006", "La fecha de emisión es obligatoria", SeveridadValidacion.ERROR));
        }

        if (objetivo.getRemitente() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-007", "El remitente es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getEnvio() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-008", "Los datos de envío son requeridos", SeveridadValidacion.ERROR));
        }

        if (objetivo.getDetalles() == null || objetivo.getDetalles().isEmpty()) {
            resultado.agregar(new IncidenciaValidacion("VAL-GRE-009", "Se requiere al menos una línea de detalle en la guía", SeveridadValidacion.ERROR));
        }

        agregarValidacionSunat(resultado, objetivo);
        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, BorradorGuiaRemision objetivo) {
        if (objetivo.getSerie() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            ReglaSunatXsl regla = objetivo.isGRETransportista()
                    ? ReglaSunatXsl.GUIA_TRANSPORTISTA
                    : ReglaSunatXsl.GUIA_REMITENTE;
            String nombreArchivo = construirNombreArchivo(objetivo);
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
                    "No se pudo ejecutar validación oficial SUNAT de Guía: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(BorradorGuiaRemision doc) {
        String ruc = doc.getRemitente() != null ? doc.getRemitente().ruc() : "00000000000";
        String tipo = doc.getTipoComprobante() != null ? doc.getTipoComprobante() : "09";
        return ruc + "-" + tipo + "-" + doc.getSerie() + "-" + doc.getNumero() + ".xml";
    }
}
