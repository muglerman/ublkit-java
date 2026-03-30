package com.cna.ublkit.testkit.fixture;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.Reversion;

import java.time.LocalDate;
import java.util.List;

/**
 * Provee instancias pre-configuradas de {@link ComunicacionBaja} y {@link Reversion}
 * para pruebas unitarias y de integración.
 *
 * @since 0.1.0
 */
public final class ComunicacionesBajaEjemplo {

    private ComunicacionesBajaEjemplo() {}

    /**
     * Comunicación de baja mínima válida con una factura.
     */
    public static ComunicacionBaja comunicacionBajaMinima() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(1);
        baja.setEmisor(emisorEstandar());
        baja.setComprobantes(List.of(
                new ItemBaja("F001", 1, "01", "Error en RUC del cliente")
        ));
        return baja;
    }

    /**
     * Comunicación de baja con múltiples comprobantes.
     */
    public static ComunicacionBaja comunicacionBajaMultiple() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(2);
        baja.setEmisor(emisorEstandar());
        baja.setComprobantes(List.of(
                new ItemBaja("F001", 100, "01", "Comprobante mal emitido"),
                new ItemBaja("F001", 101, "01", "Datos incorrectos del cliente"),
                new ItemBaja("F002", 50, "01", "Error en monto total")
        ));
        return baja;
    }

    /**
     * Reversión de percepción/retención mínima.
     */
    public static Reversion reversionMinima() {
        Reversion reversion = new Reversion();
        reversion.setFechaEmision(LocalDate.of(2026, 4, 15));
        reversion.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        reversion.setNumero(1);
        reversion.setEmisor(emisorEstandar());
        reversion.setComprobantes(List.of(
                new ItemBaja("P001", 1, "40", "Anulación de comprobante de percepción")
        ));
        return reversion;
    }

    private static EmisorDocumento emisorEstandar() {
        return new EmisorDocumento(
                "20123456789",
                "EMPRESA PRUEBA S.A.C.",
                "EMPRESA PRUEBA S.A.C.",
                null,
                null
        );
    }
}
