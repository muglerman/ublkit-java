package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Ensambla comprobantes de retención calculando montos derivados si faltan.
 *
 * @since 0.1.0
 */
public final class EnsambladorRetencion {

    private static final int ESCALA = 2;
    private static final RoundingMode REDONDEO = RoundingMode.HALF_UP;

    private EnsambladorRetencion() {
    }

    public static ComprobanteRetencion ensamblar(ComprobanteRetencion retencion) {
        if (retencion == null) return null;

        if (retencion.getMoneda() == null || retencion.getMoneda().isBlank()) {
            retencion.setMoneda("PEN");
        }

        BigDecimal importeOperacion = retencion.getOperacion() != null
                && retencion.getOperacion().importeOperacion() != null
                ? retencion.getOperacion().importeOperacion()
                : BigDecimal.ZERO;

        if (retencion.getImporteTotalPagado() == null) {
            retencion.setImporteTotalPagado(importeOperacion.setScale(ESCALA, REDONDEO));
        }

        if (retencion.getImporteTotalRetenido() == null) {
            BigDecimal tasa = retencion.getTipoRegimenPorcentaje() != null
                    ? retencion.getTipoRegimenPorcentaje()
                    : BigDecimal.ZERO;
            BigDecimal retenido = importeOperacion.multiply(tasa);
            retencion.setImporteTotalRetenido(retenido.setScale(ESCALA, REDONDEO));
        }

        return retencion;
    }
}

