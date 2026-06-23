package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaDebito;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

public class ValidadorNotaDebito implements Validador<BorradorNotaDebito> {
    private final SerializadorXmlNotaDebito serializadorXml = new SerializadorXmlNotaDebito();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(BorradorNotaDebito objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-001", "El documento no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }

        if (objetivo.getSerie() == null || objetivo.getSerie().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-002", "La serie es requerida", SeveridadValidacion.ERROR));
        } else {
            String serie = objetivo.getSerie();
            boolean modificandoFactura = serie.startsWith("F");
            boolean modificandoBoleta = serie.startsWith("B");

            if (!modificandoFactura && !modificandoBoleta) {
                resultado.agregar(new IncidenciaValidacion("VAL-ND-003", "La serie de la nota de débito debe iniciar con F o B", SeveridadValidacion.ERROR));
            }
        }

        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-005", "El número de comprobante es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-006", "La fecha de emisión es requerida", SeveridadValidacion.ERROR));
        }

        if (objetivo.getEmisor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-007", "El emisor es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getReceptor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-008", "El receptor es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getComprobanteAfectadoSerieNumero() == null || objetivo.getComprobanteAfectadoSerieNumero().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-009", "La nota de débito debe afectar a un documento previo (Documento Relacionado)", SeveridadValidacion.ERROR));
        }

        if (objetivo.getSustentoDescripcion() == null || objetivo.getSustentoDescripcion().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-010", "El sustento (motivo) es requerido para la nota de débito", SeveridadValidacion.ERROR));
        }

        if (objetivo.getTipoNota() == null || objetivo.getTipoNota().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-ND-011", "El tipo de nota de débito (código de catálogo 10) es requerido", SeveridadValidacion.ERROR));
        }

        agregarValidacionSunat(resultado, objetivo);
        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, BorradorNotaDebito objetivo) {
        if (objetivo.getSerie() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            String nombreArchivo = construirNombreArchivo(objetivo);
            ResultadoValidacion validacionSunat = validadorSunatXsl.validarXml(xml, nombreArchivo, ReglaSunatXsl.NOTA_DEBITO);
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
                    "No se pudo ejecutar validación oficial SUNAT de Nota de Débito: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(BorradorNotaDebito doc) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "00000000000";
        return ruc + "-08-" + doc.getSerie() + "-" + doc.getNumero() + ".xml";
    }
}
