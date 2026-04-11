package com.cna.ublkit.qr;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneradorQrSunatTest {

    @Test
    void generarQrBase64_facturaCorrecta() {
        GeneradorQrSunat generador = new GeneradorQrSunat();

        BorradorFactura factura = new BorradorFactura();
        factura.setEmisor(new EmisorDocumento("20123456789", null, null, null, null));
        factura.setTipoComprobante("01");
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2023, 10, 1));

        TotalImpuestos impuestos = new TotalImpuestos(
                new BigDecimal("18.00"), // total
                new BigDecimal("100.00"), // gravadoImporte
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );
        factura.setTotalImpuestos(impuestos);

        TotalImporte importe = new TotalImporte(
                new BigDecimal("100.00"),
                new BigDecimal("100.00"),
                new BigDecimal("118.00"),
                null, null
        );
        factura.setTotalImporte(importe);

        String hash = "hash123";

        String qrBase64 = generador.generarQrBase64(factura, hash);

        assertNotNull(qrBase64);
        assertTrue(qrBase64.length() > 0);
    }
}
