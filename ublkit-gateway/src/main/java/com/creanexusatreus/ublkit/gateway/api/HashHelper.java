package com.cna.ublkit.gateway.api;

import com.cna.ublkit.core.error.ExcepcionUblKit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitario para cálculo de hashes requeridos por SUNAT.
 *
 * @since 0.1.0
 */
public final class HashHelper {

    private HashHelper() { }

    /**
     * Calcula el hash SHA-256 de un arreglo de bytes y lo devuelve
     * como una cadena en formato hexadecimal (usado para Guías de Remisión REST).
     *
     * @param input Bytes a hashear (ej. bytes del archivo ZIP).
     * @return Cadena hexadecimal en minúsculas.
     */
    public static String sha256Hex(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input);
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new ExcepcionUblKit("Algoritmo SHA-256 no disponible en el sistema", e);
        }
    }
}
