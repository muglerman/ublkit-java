package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;
import com.cna.ublkit.validation.api.Validador;
import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;

/**
 * Validador funcional para comprobantes de percepción (tipo 40).
 *
 * @since 0.1.0
 */
public class ValidadorPercepcion implements Validador<ComprobantePercepcion> {

    @Override
    public ResultadoValidacion validar(ComprobantePercepcion objetivo) {
        ResultadoValidacion resultado = new ResultadoValidacion();
        if (objetivo == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-001", "El comprobante de percepción no puede ser nulo", SeveridadValidacion.ERROR));
            return resultado;
        }
        if (objetivo.getSerie() == null || objetivo.getSerie().isBlank() || !objetivo.getSerie().startsWith("P")) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-002", "La serie de percepción debe iniciar con P", SeveridadValidacion.ERROR));
        }
        if (objetivo.getNumero() == null || objetivo.getNumero() <= 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-003", "El número de percepción debe ser mayor a cero", SeveridadValidacion.ERROR));
        }
        if (objetivo.getFechaEmision() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-004", "La fecha de emisión es obligatoria", SeveridadValidacion.ERROR));
        }
        if (objetivo.getEmisor() == null || objetivo.getEmisor().ruc() == null || !objetivo.getEmisor().ruc().matches("\\d{11}")) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-005", "El emisor de percepción debe tener RUC válido (11 dígitos)", SeveridadValidacion.ERROR));
        }
        if (objetivo.getCliente() == null || objetivo.getCliente().numDocIdentidad() == null || objetivo.getCliente().numDocIdentidad().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-006", "El cliente es obligatorio", SeveridadValidacion.ERROR));
        }
        if (objetivo.getTipoRegimen() == null || objetivo.getTipoRegimen().isBlank()) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-007", "El tipo de régimen de percepción es obligatorio", SeveridadValidacion.ERROR));
        }
        if (objetivo.getTipoRegimenPorcentaje() == null || objetivo.getTipoRegimenPorcentaje().signum() < 0) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-008", "El porcentaje del régimen debe ser mayor o igual a cero", SeveridadValidacion.ERROR));
        }
        if (objetivo.getOperacion() == null) {
            resultado.agregar(new IncidenciaValidacion("VAL-PR-009", "Debe existir al menos una operación de percepción", SeveridadValidacion.ERROR));
        }
        return resultado;
    }
}

