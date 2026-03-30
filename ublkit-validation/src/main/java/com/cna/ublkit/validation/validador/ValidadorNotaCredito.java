package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaCredito;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;
import com.cna.ublkit.validation.validador.sunat.ReglaSunatXsl;
import com.cna.ublkit.validation.validador.sunat.ValidadorSunatXsl;

public class ValidadorNotaCredito implements Validador<BorradorNotaCredito> {
    private final SerializadorXmlNotaCredito serializadorXml = new SerializadorXmlNotaCredito();
    private final ValidadorSunatXsl validadorSunatXsl = new ValidadorSunatXsl();

    @Override
    public ResultadoValidacion validar(BorradorNotaCredito objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();

        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-001", "El documento no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }

        if (objetivo.getSerie() == null || objetivo.getSerie().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-002", "La serie es requerida", SeveridadValidacion.ERROR));
        } else {
            String serie = objetivo.getSerie();
            boolean modificandoFactura = serie.startsWith("F");
            boolean modificandoBoleta = serie.startsWith("B");

            if (!modificandoFactura && !modificandoBoleta) {
                resultado.agregar(new IncidenciaValidacion("VAL-NC-003", "La serie de la nota de crédito debe iniciar con F o B", SeveridadValidacion.ERROR));
            }
        }

        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-005", "El número de comprobante es requerido y debe ser mayor que cero", SeveridadValidacion.ERROR));
        }

        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-006", "La fecha de emisión es requerida", SeveridadValidacion.ERROR));
        }

        if (objetivo.getEmisor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-007", "El emisor es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getReceptor() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-008", "El receptor es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getComprobanteAfectadoSerieNumero() == null || objetivo.getComprobanteAfectadoSerieNumero().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-009", "La nota de crédito debe afectar a un documento previo (Documento Relacionado)", SeveridadValidacion.ERROR));
        }

        if (objetivo.getSustentoDescripcion() == null || objetivo.getSustentoDescripcion().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-010", "El sustento de la nota es requerido", SeveridadValidacion.ERROR));
        }

        if (objetivo.getTipoNota() == null || objetivo.getTipoNota().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-NC-011", "El tipo de nota de crédito (código de catálogo 09) es requerido", SeveridadValidacion.ERROR));
        }

        agregarValidacionSunat(resultado, objetivo);
        return resultado;
    }

    private void agregarValidacionSunat(ResultadoValidacion resultado, BorradorNotaCredito objetivo) {
        if (objetivo.getSerie() == null || objetivo.getNumero() == null) return;
        try {
            String xml = serializadorXml.serializar(objetivo);
            String nombreArchivo = construirNombreArchivo(objetivo);
            ResultadoValidacion validacionSunat = validadorSunatXsl.validarXml(xml, nombreArchivo, ReglaSunatXsl.NOTA_CREDITO);
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
                    "No se pudo ejecutar validación oficial SUNAT de Nota de Crédito: " + e.getMessage(),
                    SeveridadValidacion.ADVERTENCIA
            ));
        }
    }

    private String construirNombreArchivo(BorradorNotaCredito doc) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "00000000000";
        return ruc + "-07-" + doc.getSerie() + "-" + doc.getNumero() + ".xml";
    }
}
