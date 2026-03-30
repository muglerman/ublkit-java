package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.resumen.ComprobanteResumen;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ItemResumenDiario;
import com.cna.ublkit.ubl.modelo.sunat.resumen.ResumenDiario;

import java.time.LocalDate;

/**
 * Ensamblador para Resumen Diario de Boletas.
 * <p>
 * Aplica valores por defecto cuando el usuario no los proporcionó:
 * fecha de emisión, número correlativo, moneda de comprobantes, etc.
 * </p>
 *
 * @since 0.1.0
 */
public final class EnsambladorResumenDiario {

    private EnsambladorResumenDiario() {
    }

    /**
     * Ensambla un resumen diario, aplicando defectos si faltan campos.
     *
     * @param resumen resumen a ensamblar (se modifica in-place)
     * @return el mismo resumen con defectos aplicados
     */
    public static ResumenDiario ensamblar(ResumenDiario resumen) {
        aplicarDefectos(resumen);
        ensamblarLineas(resumen);
        return resumen;
    }

    private static void aplicarDefectos(ResumenDiario resumen) {
        if (resumen.getFechaEmision() == null) {
            resumen.setFechaEmision(LocalDate.now());
        }
        if (resumen.getFechaEmisionComprobantes() == null) {
            resumen.setFechaEmisionComprobantes(resumen.getFechaEmision());
        }
        if (resumen.getNumero() == null) {
            resumen.setNumero(1);
        }
        if (resumen.getMoneda() == null) {
            resumen.setMoneda("PEN");
        }
    }

    private static void ensamblarLineas(ResumenDiario resumen) {
        if (resumen.getComprobantes() == null) return;

        for (ItemResumenDiario item : resumen.getComprobantes()) {
            ComprobanteResumen comp = item.comprobante();
            if (comp.getMoneda() == null) {
                comp.setMoneda(resumen.getMoneda());
            }
        }
    }
}
