package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;

import java.time.LocalDate;

/**
 * Ensamblador para Comunicación de Baja y Reversión.
 * <p>
 * Aplica valores por defecto cuando el usuario no los proporcionó:
 * fecha de emisión, número correlativo, etc.
 * </p>
 *
 * @since 0.1.0
 */
public final class EnsambladorComunicacionBaja {

    private EnsambladorComunicacionBaja() {
    }

    /**
     * Ensambla una comunicación de baja, aplicando defectos si faltan campos.
     *
     * @param comunicacion comunicación a ensamblar (se modifica in-place)
     * @return la misma comunicación con defectos aplicados
     */
    public static ComunicacionBaja ensamblar(ComunicacionBaja comunicacion) {
        aplicarDefectos(comunicacion);
        return comunicacion;
    }

    private static void aplicarDefectos(ComunicacionBaja comunicacion) {
        if (comunicacion.getFechaEmision() == null) {
            comunicacion.setFechaEmision(LocalDate.now());
        }
        if (comunicacion.getFechaEmisionComprobantes() == null) {
            comunicacion.setFechaEmisionComprobantes(comunicacion.getFechaEmision());
        }
        if (comunicacion.getNumero() == null) {
            comunicacion.setNumero(1);
        }
    }
}
