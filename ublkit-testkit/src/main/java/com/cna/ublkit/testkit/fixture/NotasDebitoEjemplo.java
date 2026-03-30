package com.cna.ublkit.testkit.fixture;

import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provee instancias pre-configuradas de {@link BorradorNotaDebito}
 * para pruebas unitarias y de integración.
 *
 * @since 0.1.0
 */
public final class NotasDebitoEjemplo {

    private NotasDebitoEjemplo() {}

    /**
     * Nota de débito mínima válida que ajusta una factura.
     */
    public static BorradorNotaDebito notaDebitoMinima() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("FD01");
        nota.setNumero(1);
        nota.setTipoNota("01");
        nota.setFechaEmision(LocalDate.now());
        nota.setMoneda("PEN");
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Intereses por mora");

        nota.setEmisor(new EmisorDocumento(
                "20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.",
                new Direccion("150101", "0000", null, "LIMA", "LIMA", "LIMA", "AV. SIEMPRE VIVA 123", "PE"),
                null));

        nota.setReceptor(new ReceptorDocumento(
                "6", "20999999999", "CLIENTE DE PRUEBA S.A.",
                new Direccion(null, null, null, null, null, null, "CALLE SECUNDARIA 456", "PE"),
                null));

        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Intereses");
        linea.setCantidad(BigDecimal.ONE);
        linea.setUnidadMedida("ZZ");
        linea.setPrecio(new BigDecimal("50.00"));
        nota.setDetalles(List.of(linea));

        return nota;
    }
}
