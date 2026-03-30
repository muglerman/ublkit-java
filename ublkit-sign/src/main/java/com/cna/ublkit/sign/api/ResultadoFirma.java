package com.cna.ublkit.sign.api;

/**
 * Resultado de una operación de firma digital.
 *
 * @param xmlFirmado     XML firmado como bytes (codificación ISO-8859-1).
 * @param xmlFirmadoStr  XML firmado como String (conveniencia).
 * @param digestValue    Valor del digest (hash SHA-1) del documento firmado.
 * @param exitoso        {@code true} si la firma fue exitosa.
 * @param mensajeError   Mensaje de error si la firma falló, {@code null} si fue exitosa.
 *
 * @since 0.1.0
 */
public record ResultadoFirma(
        byte[] xmlFirmado,
        String xmlFirmadoStr,
        String digestValue,
        boolean exitoso,
        String mensajeError
) {

    /**
     * Crea un resultado exitoso.
     */
    public static ResultadoFirma exitoso(byte[] xmlFirmado, String xmlFirmadoStr, String digestValue) {
        return new ResultadoFirma(xmlFirmado, xmlFirmadoStr, digestValue, true, null);
    }

    /**
     * Crea un resultado fallido.
     */
    public static ResultadoFirma fallido(String mensajeError) {
        return new ResultadoFirma(null, null, null, false, mensajeError);
    }
}
