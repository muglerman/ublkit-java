package com.cna.ublkit.sign.certificado;

import java.io.InputStream;

/**
 * Origen de un certificado digital.
 * Encapsula los datos necesarios para cargar un certificado desde distintas fuentes.
 *
 * @param inputStream Flujo de entrada con el contenido del keystore.
 * @param password    Contraseña del keystore.
 * @param tipo        Tipo de keystore ("JKS", "PKCS12", etc.). Por defecto "PKCS12".
 *
 * @since 0.1.0
 */
public record OrigenCertificado(
        InputStream inputStream,
        String password,
        String tipo
) {
    /**
     * Constructor conveniente para PKCS12 (el formato más común).
     */
    public OrigenCertificado(InputStream inputStream, String password) {
        this(inputStream, password, "PKCS12");
    }
}
