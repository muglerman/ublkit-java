package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Ensambla comprobantes de percepción calculando montos derivados si faltan.
 *
 * @since 0.1.0
 */
public final class EnsambladorPercepcion {

    private static final int ESCALA = 2;
    private static final RoundingMode REDONDEO = RoundingMode.HALF_UP;

    private EnsambladorPercepcion() {
    }

    public static ComprobantePercepcion ensamblar(ComprobantePercepcion percepcion) {
        if (percepcion == null) return null;

        if (percepcion.getMoneda() == null || percepcion.getMoneda().isBlank()) {
            percepcion.setMoneda("PEN");
        }

        BigDecimal importeOperacion = percepcion.getOperacion() != null
                && percepcion.getOperacion().importeOperacion() != null
                ? percepcion.getOperacion().importeOperacion()
                : BigDecimal.ZERO;

        if (percepcion.getImporteTotalCobrado() == null) {
            percepcion.setImporteTotalCobrado(importeOperacion.setScale(ESCALA, REDONDEO));
        }

        if (percepcion.getImporteTotalPercibido() == null) {
            BigDecimal tasa = percepcion.getTipoRegimenPorcentaje() != null
                    ? percepcion.getTipoRegimenPorcentaje()
                    : BigDecimal.ZERO;
            BigDecimal percibido = importeOperacion.multiply(tasa);
            percepcion.setImporteTotalPercibido(percibido.setScale(ESCALA, REDONDEO));
        }

        return percepcion;
    }
}

