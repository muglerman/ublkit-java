package com.cna.ublkit.qr;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.DocumentoBase;
import com.google.zxing.BarcodeFormat;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class GeneradorQrSunat {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String generarQrBase64(DocumentoBase documento, String hashDocumento) {
        String contenidoQr = construirTrama(documento, hashDocumento);
        return generarImagenBase64(contenidoQr);
    }

    private String construirTrama(DocumentoBase doc, String hashDocumento) {
        // Formato: RUC | Tipo | Serie | Numero | IGV | Total | Fecha | Hash
        String ruc = doc.getEmisor() != null ? doc.getEmisor().ruc() : "";

        String tipo = "";
        if (doc instanceof BorradorFactura f) {
            tipo = f.getTipoComprobante();
        } else if (doc instanceof BorradorNotaCredito) {
            tipo = "07";
        } else if (doc instanceof BorradorNotaDebito) {
            tipo = "08";
        }

        String serie = doc.getSerie();
        String numero = doc.getNumero() != null ? String.valueOf(doc.getNumero()) : "";

        BigDecimal igv = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        if (doc instanceof BorradorFactura f) {
            if (f.getTotalImpuestos() != null) {
                igv = f.getTotalImpuestos().total() != null ? f.getTotalImpuestos().total() : BigDecimal.ZERO;
            }
            if (f.getTotalImporte() != null) {
                total = f.getTotalImporte().importeConImpuestos() != null ? f.getTotalImporte().importeConImpuestos() : BigDecimal.ZERO;
            }
        } else if (doc instanceof BorradorNotaCredito nc) {
             if (nc.getTotalImpuestos() != null) {
                igv = nc.getTotalImpuestos().total() != null ? nc.getTotalImpuestos().total() : BigDecimal.ZERO;
            }
            if (nc.getTotalImporte() != null) {
                total = nc.getTotalImporte().importeConImpuestos() != null ? nc.getTotalImporte().importeConImpuestos() : BigDecimal.ZERO;
            }
        } else if (doc instanceof BorradorNotaDebito nd) {
             if (nd.getTotalImpuestos() != null) {
                igv = nd.getTotalImpuestos().total() != null ? nd.getTotalImpuestos().total() : BigDecimal.ZERO;
            }
            if (nd.getTotalImporte() != null) {
                total = nd.getTotalImporte().importeConImpuestos() != null ? nd.getTotalImporte().importeConImpuestos() : BigDecimal.ZERO;
            }
        }

        String igvStr = igv.stripTrailingZeros().toPlainString();
        String totalStr = total.stripTrailingZeros().toPlainString();
        String fecha = doc.getFechaEmision() != null ? doc.getFechaEmision().format(DATE_FORMATTER) : "";

        return String.join("|",
            ruc != null ? ruc : "",
            tipo != null ? tipo : "",
            serie != null ? serie : "",
            numero,
            igvStr,
            totalStr,
            fecha,
            hashDocumento != null ? hashDocumento : "");
    }

    private String generarImagenBase64(String texto) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 200, 200);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            StringBuilder svg = new StringBuilder();
            svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
            svg.append("width=\"").append(width).append("\" ");
            svg.append("height=\"").append(height).append("\" ");
            svg.append("viewBox=\"0 0 ").append(width).append(" ").append(height).append("\">");
            svg.append("<path d=\"");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        svg.append("M").append(x).append(",").append(y).append("h1v1h-1z ");
                    }
                }
            }

            svg.append("\" fill=\"#000000\"/>");
            svg.append("</svg>");

            byte[] svgData = svg.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(svgData);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar QR", e);
        }
    }
}
