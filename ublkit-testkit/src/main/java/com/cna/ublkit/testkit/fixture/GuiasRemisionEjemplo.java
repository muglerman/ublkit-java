package com.cna.ublkit.testkit.fixture;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.guia.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Provee instancias pre-configuradas de {@link BorradorGuiaRemision}
 * para pruebas unitarias y de integración.
 *
 * @since 0.1.0
 */
public final class GuiasRemisionEjemplo {

    private GuiasRemisionEjemplo() {}

    /**
     * Guía de remisión - remitente mínima válida.
     */
    public static BorradorGuiaRemision guiaRemitenteMinima() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.now());
        guia.setHoraEmision(LocalTime.of(10, 0));

        guia.setRemitente(new EmisorDocumento(
                "20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));
        guia.setDestinatario(new DestinatarioGuia("6", "20999999999", "CLIENTE DE PRUEBA S.A."));

        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("10.000"));
        envio.setPesoTotalUnidadMedida("KGM");
        envio.setTipoModalidadTraslado("02");
        envio.setFechaTraslado(LocalDate.now().plusDays(1));
        envio.setPartida(new PuntoPartida("150101", "AV. SIEMPRE VIVA 123, LIMA", null, null));
        envio.setDestino(new PuntoDestino("040101", "CALLE AREQUIPA 456", null, null));

        Conductor chofer = new Conductor("Principal", "1", "12345678", "JUAN", "PEREZ GOMEZ", "Q12345678");
        envio.setChoferes(List.of(chofer));
        envio.setVehiculo(new Vehiculo("ABC-123", null, null, null, null));

        guia.setEnvio(envio);

        guia.setDetalles(List.of(
                new LineaGuia("NIU", new BigDecimal("10"), "Producto de prueba A", "PROD001", null, null),
                new LineaGuia("KGM", new BigDecimal("5.5"), "Producto de prueba B", "PROD002", "10101501", null)
        ));

        return guia;
    }

    /**
     * Guía de remisión - transportista mínima válida.
     */
    public static BorradorGuiaRemision guiaTransportistaMinima() {
        BorradorGuiaRemision guia = guiaRemitenteMinima();
        guia.setTipoComprobante("31");
        guia.getEnvio().setTipoModalidadTraslado("01");
        guia.getEnvio().setTransportista(
                new TransportistaGuia("6", "20300000003", "TRANSPORTES PRUEBA S.A.C.", "0001-MTC"));
        return guia;
    }
}
