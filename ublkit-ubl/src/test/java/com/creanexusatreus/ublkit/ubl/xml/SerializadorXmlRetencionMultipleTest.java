package com.creanexusatreus.ublkit.ubl.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteAfectadoPR;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;
import com.creanexusatreus.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;

class SerializadorXmlRetencionMultipleTest {
    @Test
    void serializesEverySelectedDocument() {
        var retention = new ComprobanteRetencion();
        retention.setSerie("R001");
        retention.setNumero(1);
        retention.setFechaEmision(LocalDate.of(2026, 8, 31));
        retention.setMoneda("PEN");
        retention.setEmisor(new EmisorDocumento("20123456789", null, "EMISOR SAC", null, null));
        retention.setCliente(new ReceptorDocumento("6", "20987654321", "RECEPTOR SAC", null, null));
        retention.setTipoRegimen("01");
        retention.setTipoRegimenPorcentaje(new BigDecimal("0.03"));
        retention.setOperaciones(List.of(operation(1, "F001-1"), operation(2, "F001-2")));

        String xml = new SerializadorXmlRetencion().serializar(retention);

        assertEquals(2, xml.split("SUNATRetentionDocumentReference", -1).length / 2);
    }

    private OperacionPR operation(int number, String reference) {
        return new OperacionPR(number, LocalDate.of(2026, 8, 31), new BigDecimal("100.00"),
                new ComprobanteAfectadoPR("PEN", "01", reference,
                        LocalDate.of(2026, 8, 30), new BigDecimal("100.00")), null);
    }
}
