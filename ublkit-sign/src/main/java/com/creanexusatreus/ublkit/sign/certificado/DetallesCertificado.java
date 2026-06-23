package com.cna.ublkit.sign.certificado;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * Contiene la clave privada y el certificado X.509 extraídos de un keystore.
 * Inmutable una vez creado por el {@link CargadorCertificado}.
 *
 * @param clavePrivada  Clave privada para firmar.
 * @param certificado   Certificado X.509 con la clave pública correspondiente.
 *
 * @since 0.1.0
 */
public record DetallesCertificado(
        PrivateKey clavePrivada,
        X509Certificate certificado
) {
    /**
     * Verifica que el certificado y la clave privada no sean nulos.
     */
    public DetallesCertificado {
        if (clavePrivada == null) throw new IllegalArgumentException("clavePrivada no puede ser nula");
        if (certificado == null) throw new IllegalArgumentException("certificado no puede ser nulo");
    }
}
