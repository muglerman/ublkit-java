package com.cna.ublkit.sign.api;

import java.util.Arrays;
import java.util.Objects;

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
    public ResultadoFirma {
        xmlFirmado = xmlFirmado == null ? null : xmlFirmado.clone();
    }

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

    @Override
    public byte[] xmlFirmado() {
        return xmlFirmado == null ? null : xmlFirmado.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResultadoFirma other)) return false;
        return Arrays.equals(this.xmlFirmado, other.xmlFirmado)
                && Objects.equals(this.xmlFirmadoStr, other.xmlFirmadoStr)
                && Objects.equals(this.digestValue, other.digestValue)
                && this.exitoso == other.exitoso
                && Objects.equals(this.mensajeError, other.mensajeError);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(xmlFirmado);
        result = 31 * result + Objects.hash(xmlFirmadoStr, digestValue, exitoso, mensajeError);
        return result;
    }

    @Override
    public String toString() {
        return "ResultadoFirma[" +
                "xmlFirmado(length)=" + (xmlFirmado == null ? 0 : xmlFirmado.length) +
                ", digestValue=" + digestValue +
                ", exitoso=" + exitoso +
                ", mensajeError=" + mensajeError +
                ']';
    }
}
