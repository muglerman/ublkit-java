package com.creanexusatreus.ublkit.qr;

import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImporte;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

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
    void generarQrBase64_conContenidoGeneraPng() {
        String qrBase64 = generador.generarQrBase64(
                "https://e-factura.sunat.gob.pe/v1/contribuyente/gre/comprobantes/descargaqr?hashqr=abc");

        byte[] png = Base64.getDecoder().decode(qrBase64);
        assertArrayEquals(new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 },
                java.util.Arrays.copyOf(png, 4));
    }

    @Test
    void generarQrBase64_rechazaContenidoVacio() {
        assertThrows(IllegalArgumentException.class, () -> generador.generarQrBase64(" "));
    }

    @Test
    void construirTrama_facturaCompleta_formatoSunatCorrecto() {
        BorradorFactura factura = buildFactura();

        String trama = generador.construirTrama(factura, "hashXYZ");

        // Formato: RUC|TIPO|SERIE|NUMERO|IGV|TOTAL|FECHA|TIPO_DOC_ADQ|NUM_DOC_ADQ|HASH|
        assertEquals("20123456789|01|F001|1|18|118|2023-10-01|6|10234567890|hashXYZ|", trama);
    }

    @Test
    void construirTrama_boletaUsaPaddingImportesYFirma() {
        BorradorFactura factura = buildFactura();
        factura.setEmisor(new EmisorDocumento("10710201396", null, null, null, null));
        factura.setReceptor(new ReceptorDocumento("6", "20606860618", "Cliente", null, null));
        factura.setTipoComprobante("03");
        factura.setSerie("B001");
        factura.setNumero(6);
        factura.setFechaEmision(LocalDate.of(2026, 9, 16));
        factura.setTotalImpuestos(new TotalImpuestos(new BigDecimal("76.27"), new BigDecimal("423.73"),
                null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        factura.setTotalImporte(new TotalImporte(new BigDecimal("423.73"), new BigDecimal("423.73"),
                new BigDecimal("500.00"), null, null));

        assertEquals("10710201396|03|B001|00000006|76.27|500.00|2026-09-16|6|20606860618|DIGEST|SIGNATURE|",
                generador.construirTrama(factura, "DIGEST", "SIGNATURE"));
    }

    @Test
    void generarQrBase64_decodificaLaTramaExacta() throws Exception {
        BorradorFactura factura = buildFactura();
        String expected = generador.construirTrama(factura, "DIGEST", "SIGNATURE");
        String png = generador.generarQrBase64(factura, "DIGEST", "SIGNATURE");
        var image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(png)));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        assertEquals(expected, new MultiFormatReader().decode(bitmap).getText());
    }

    @Test
    void generarQrBase64_tramaFirmadaLarga_seLeeEnLasEscalasDeImpresion(TestReporter reporter) throws Exception {
        String digestValue = "QmFzZTY0RGlnZXN0VmFsdWVDb25UaWVuZVN1ZmljaWVudGVEZW5zaWRhZA==";
        String signatureValue = ("MIICXQIBAAKBgQDC5T4v7h2Jr0mD6y4s8q1b3c9eF5gH7iK9lM2nP4rS6tU8vW0xYz"
                + "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789").repeat(3);
        String expected = "20123456789|01|F001|00000001|18.00|118.00|2026-09-16|6|10234567890|"
                + digestValue + "|" + signatureValue + "|";

        String png = generador.generarQrBase64(expected);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(png)));

        assertEquals(512, image.getWidth());
        assertEquals(512, image.getHeight());
        assertEquals(expected, decodificar(image), "La trama QR firmada debe leerse a su tamaño real");
        for (double scale : new double[] { .75, .50 }) {
            int percentage = (int) (scale * 100);
            try {
                boolean readable = expected.equals(decodificar(escalarSinSuavizado(image, scale)));
                reporter.publishEntry("QR " + percentage + "% readable", Boolean.toString(readable));
            } catch (Exception exception) {
                reporter.publishEntry("QR " + percentage + "% readable", "false: "
                        + exception.getClass().getSimpleName());
            }
        }
    }

    private String decodificar(BufferedImage image) throws Exception {
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        return new MultiFormatReader().decode(bitmap).getText();
    }

    private BufferedImage escalarSinSuavizado(BufferedImage source, double scale) {
        int size = (int) Math.round(source.getWidth() * scale);
        BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = scaled.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            graphics.drawImage(source, 0, 0, size, size, null);
        } finally {
            graphics.dispose();
        }
        return scaled;
    }

    @Test
    void generarQrFirmado_boletaB00100000009_usaImportesFinalesDelXml() throws Exception {
        BorradorFactura factura = buildFactura();
        factura.setEmisor(new EmisorDocumento("10710201396", null, null, null, null));
        factura.setReceptor(new ReceptorDocumento("6", "20606860618", "Cliente", null, null));
        factura.setTipoComprobante("03");
        factura.setSerie("B001");
        factura.setNumero(9);
        factura.setFechaEmision(LocalDate.of(2026, 9, 16));
        factura.setTotalImpuestos(null);
        factura.setTotalImporte(null);

        String expectedPrefix = "10710201396|03|B001|00000009|27.00|377.00|2026-09-16|6|20606860618|";
        String trama = generador.construirTrama(factura, new BigDecimal("27"), new BigDecimal("377"),
                "DIGEST", "SIGNATURE");
        assertTrue(trama.startsWith(expectedPrefix));
        assertFalse(trama.contains("|0.00|0.00|"));

        String png = generador.generarQrBase64(factura, new BigDecimal("27"), new BigDecimal("377"),
                "DIGEST", "SIGNATURE");
        var image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(png)));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        assertEquals(trama, new MultiFormatReader().decode(bitmap).getText());
    }

    @Test
    void construirTramaFirmada_rechazaImportesObligatoriosAusentes() {
        BorradorFactura factura = buildFactura();

        assertThrows(IllegalArgumentException.class,
                () -> generador.construirTrama(factura, null, new BigDecimal("118"), "DIGEST", "SIGNATURE"));
        assertThrows(IllegalArgumentException.class,
                () -> generador.construirTrama(factura, new BigDecimal("18"), null, "DIGEST", "SIGNATURE"));
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
