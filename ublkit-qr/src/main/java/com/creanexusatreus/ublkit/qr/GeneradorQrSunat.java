package com.creanexusatreus.ublkit.qr;

import com.creanexusatreus.ublkit.ubl.modelo.BorradorFactura;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaDebito;
import com.creanexusatreus.ublkit.ubl.modelo.DocumentoBase;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

/**
 * Genera códigos QR según la normativa SUNAT para comprobantes electrónicos.
 *
 * <h3>Formato de la trama QR (SUNAT):</h3>
 * <pre>
 * RUC | TIPO_DOC | SERIE | NUMERO | IGV | TOTAL | FECHA |
 * TIPO_DOC_ADQUIRENTE | NUM_DOC_ADQUIRENTE | VALOR_RESUMEN |
 * </pre>
 * <p>
 * Separador de campos: carácter pipe "|". La trama termina con pipe (trailing pipe).
 * </p>
 *
 * <h3>Características de impresión (SUNAT):</h3>
 * <ul>
 *   <li>Tamaño máximo: 6cm alto x 6cm ancho (incluye zona de silencio)</li>
 *   <li>Zona de silencio mínima (Quiet Zone): 1mm alrededor del código</li>
 *   <li>Color de impresión: Negro</li>
 * </ul>
 *
 * @since 0.1.0
 */
public class GeneradorQrSunat {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Tamaño en píxeles del código QR (6cm ≈ 227px a 96 DPI, usamos 230px con margen). */
    private static final int QR_SIZE_PX = 230;

    /** Margen (quiet zone) en módulos QR. 4 módulos ≈ 1mm+ a esta resolución. */
    private static final int QR_QUIET_ZONE = 4;

    /**
     * Genera un código QR en formato Base64 (imagen PNG) con la trama SUNAT completa.
     *
     * @param documento      documento del cual extraer datos de emisor, receptor, impuestos y totales.
     * @param hashDocumento  hash (DigestValue) de la firma digital del XML.
     * @return imagen QR codificada en Base64.
     */
    public String generarQrBase64(DocumentoBase documento, String hashDocumento) {
        String contenidoQr = construirTrama(documento, hashDocumento);
        return generarImagenBase64(contenidoQr);
    }

    /**
     * Construye la trama de datos QR según el formato normativo SUNAT.
     * <p>
     * Formato: {@code RUC|TIPO|SERIE|NUMERO|IGV|TOTAL|FECHA|TIPO_DOC_ADQ|NUM_DOC_ADQ|HASH|}
     * </p>
     *
     * @param doc            documento base.
     * @param hashDocumento  hash de la firma digital.
     * @return trama SUNAT con trailing pipe.
     */
    String construirTrama(DocumentoBase doc, String hashDocumento) {
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "";

        String tipo = resolverTipoComprobante(doc);
        String serie = doc.getSerie() != null ? doc.getSerie() : "";
        String numero = doc.getNumero() != null ? String.valueOf(doc.getNumero()) : "";

        BigDecimal igv = extraerIgv(doc);
        BigDecimal total = extraerTotal(doc);

        String igvStr = igv.stripTrailingZeros().toPlainString();
        String totalStr = total.stripTrailingZeros().toPlainString();
        String fecha = doc.getFechaEmision() != null ? doc.getFechaEmision().format(DATE_FORMATTER) : "";

        // Datos del adquirente/receptor (campos normativos SUNAT)
        ReceptorDocumento receptor = doc.getReceptor();
        String tipoDocAdquirente = (receptor != null && receptor.tipoDocIdentidad() != null)
                ? receptor.tipoDocIdentidad() : "-";
        String numDocAdquirente = (receptor != null && receptor.numDocIdentidad() != null)
                ? receptor.numDocIdentidad() : "-";

        // Trama con trailing pipe según formato SUNAT
        return String.join("|",
                ruc,
                tipo,
                serie,
                numero,
                igvStr,
                totalStr,
                fecha,
                tipoDocAdquirente,
                numDocAdquirente,
                hashDocumento != null ? hashDocumento : ""
        ) + "|";
    }

    private String resolverTipoComprobante(DocumentoBase doc) {
        if (doc instanceof BorradorFactura f) {
            return f.getTipoComprobante() != null ? f.getTipoComprobante() : "";
        } else if (doc instanceof BorradorNotaCredito) {
            return "07";
        } else if (doc instanceof BorradorNotaDebito) {
            return "08";
        }
        return "";
    }

    private BigDecimal extraerIgv(DocumentoBase doc) {
        if (doc.getTotalImpuestos() != null && doc.getTotalImpuestos().total() != null) {
            return doc.getTotalImpuestos().total();
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal extraerTotal(DocumentoBase doc) {
        if (doc instanceof BorradorFactura f
                && f.getTotalImporte() != null
                && f.getTotalImporte().importeConImpuestos() != null) {
            return f.getTotalImporte().importeConImpuestos();
        }
        if (doc instanceof BorradorNotaCredito nc
                && nc.getTotalImporte() != null
                && nc.getTotalImporte().importeConImpuestos() != null) {
            return nc.getTotalImporte().importeConImpuestos();
        }
        if (doc instanceof BorradorNotaDebito nd
                && nd.getTotalImporte() != null
                && nd.getTotalImporte().importeConImpuestos() != null) {
            return nd.getTotalImporte().importeConImpuestos();
        }
        return BigDecimal.ZERO;
    }

    private String generarImagenBase64(String texto) {
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, QR_QUIET_ZONE);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, QR_SIZE_PX, QR_SIZE_PX, hints);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar imagen QR", e);
        }
    }
}
