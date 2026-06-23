package com.creanexusatreus.ublkit.qr;

import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImporte;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorQrSunatTest {

    private final GeneradorQrSunat generador = new GeneradorQrSunat();

    @Test
    void generarQrBase64_facturaCorrecta() {
        BorradorFactura factura = buildFactura();

        String qrBase64 = generador.generarQrBase64(factura, "abc123hash");

        assertNotNull(qrBase64);
        assertFalse(qrBase64.isEmpty());
    }

    @Test
    void construirTrama_facturaCompleta_formatoSunatCorrecto() {
        BorradorFactura factura = buildFactura();

        String trama = generador.construirTrama(factura, "hashXYZ");

        // Formato: RUC|TIPO|SERIE|NUMERO|IGV|TOTAL|FECHA|TIPO_DOC_ADQ|NUM_DOC_ADQ|HASH|
        assertEquals("20123456789|01|F001|1|18|118|2023-10-01|6|10234567890|hashXYZ|", trama);
    }

    @Test
    void construirTrama_notaCredito_tipo07() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setEmisor(new EmisorDocumento("20111111111", null, null, null, null));
        nota.setReceptor(new ReceptorDocumento("1", "12345678", "Cliente", null, null));
        nota.setSerie("FC01");
        nota.setNumero(5);
        nota.setFechaEmision(LocalDate.of(2024, 1, 15));

        TotalImpuestos impuestos = new TotalImpuestos(
                new BigDecimal("9.00"), new BigDecimal("50.00"),
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );
        nota.setTotalImpuestos(impuestos);
        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("59.00"), new BigDecimal("50.00"),
                new BigDecimal("59.00"), null, null));

        String trama = generador.construirTrama(nota, "digest==");

        assertTrue(trama.startsWith("20111111111|07|FC01|5|"));
        assertTrue(trama.contains("|1|12345678|"));
        assertTrue(trama.endsWith("|"));
    }

    @Test
    void construirTrama_sinReceptor_usaGuionesPorDefecto() {
        BorradorFactura factura = new BorradorFactura();
        factura.setEmisor(new EmisorDocumento("20999999999", null, null, null, null));
        factura.setTipoComprobante("03");
        factura.setSerie("B001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2024, 6, 1));

        String trama = generador.construirTrama(factura, null);

        assertTrue(trama.contains("|-|-|"));
        assertTrue(trama.endsWith("|"));
    }

    private BorradorFactura buildFactura() {
        BorradorFactura factura = new BorradorFactura();
        factura.setEmisor(new EmisorDocumento("20123456789", null, null, null, null));
        factura.setReceptor(new ReceptorDocumento("6", "10234567890", "Cliente Test", null, null));
        factura.setTipoComprobante("01");
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2023, 10, 1));

        TotalImpuestos impuestos = new TotalImpuestos(
                new BigDecimal("18.00"), new BigDecimal("100.00"),
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );
        factura.setTotalImpuestos(impuestos);

        TotalImporte importe = new TotalImporte(
                new BigDecimal("100.00"), new BigDecimal("100.00"),
                new BigDecimal("118.00"), null, null
        );
        factura.setTotalImporte(importe);
        return factura;
    }
}
