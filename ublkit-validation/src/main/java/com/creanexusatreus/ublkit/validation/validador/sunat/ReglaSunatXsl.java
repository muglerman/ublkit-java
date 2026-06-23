package com.cna.ublkit.validation.validador.sunat;

/**
 * Mapa de reglas XSL de validación de referencia SUNAT.
 */
public enum ReglaSunatXsl {
    FACTURA("sunat/validation/xsl/2.X/ValidaExprRegFactura-2.0.1.xsl"),
    BOLETA("sunat/validation/xsl/2.X/ValidaExprRegBoleta-2.0.1.xsl"),
    NOTA_CREDITO("sunat/validation/xsl/2.X/ValidaExprRegNC-2.0.1.xsl"),
    NOTA_DEBITO("sunat/validation/xsl/2.X/ValidaExprRegND-2.0.1.xsl"),
    GUIA_REMITENTE("sunat/validation/xsl/2.X/ValidaExprRegGreRemitente-2.0.1.xsl"),
    GUIA_TRANSPORTISTA("sunat/validation/xsl/2.X/ValidaExprRegGreTransportista-2.0.1.xsl"),
    RESUMEN_DIARIO("sunat/validation/xsl/1.X/ValidaExprRegSummary-1.1.0.xsl"),
    COMUNICACION_BAJA("sunat/validation/xsl/1.X/ValidaExprRegSummaryVoided-1.0.1.xsl");

    private final String recursoClasspath;

    ReglaSunatXsl(String recursoClasspath) {
        this.recursoClasspath = recursoClasspath;
    }

    public String recursoClasspath() {
        return recursoClasspath;
    }
}
