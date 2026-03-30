package com.cna.ublkit.sign.certificado;

import com.cna.ublkit.core.error.ExcepcionUblKit;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Carga certificados digitales desde un {@link OrigenCertificado}.
 * <p>
 * Soporta formatos JKS y PKCS12. Busca automáticamente el primer alias
 * que contenga una clave privada en el keystore.
 * </p>
 *
 * @since 0.1.0
 */
public final class CargadorCertificado {

    private CargadorCertificado() {
        // Utility class
    }

    /**
     * Carga un certificado y su clave privada desde el origen especificado.
     *
     * @param origen Datos de origen del certificado (stream, password, tipo).
     * @return Detalles del certificado extraído.
     * @throws ExcepcionUblKit si no se encuentra una clave privada o hay problemas de lectura.
     */
    public static DetallesCertificado cargar(OrigenCertificado origen) {
        try {
            KeyStore keyStore = KeyStore.getInstance(origen.tipo());
            keyStore.load(origen.inputStream(), origen.password().toCharArray());

            String aliasConClavePrivada = buscarAliasConClavePrivada(keyStore);
            if (aliasConClavePrivada == null) {
                throw new ExcepcionUblKit("No se encontró un alias con clave privada en el keystore");
            }

            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                    aliasConClavePrivada,
                    new KeyStore.PasswordProtection(origen.password().toCharArray())
            );

            PrivateKey clavePrivada = pkEntry.getPrivateKey();
            Certificate[] cadena = keyStore.getCertificateChain(aliasConClavePrivada);

            return new DetallesCertificado(clavePrivada, (X509Certificate) cadena[0]);

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException
                 | IOException | UnrecoverableEntryException e) {
            throw new ExcepcionUblKit("Error al cargar el certificado: " + e.getMessage(), e);
        }
    }

    private static String buscarAliasConClavePrivada(KeyStore keyStore) throws KeyStoreException {
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (keyStore.isKeyEntry(alias)) {
                return alias;
            }
        }
        return null;
    }
}
