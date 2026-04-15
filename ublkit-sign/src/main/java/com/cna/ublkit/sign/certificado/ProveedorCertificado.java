package com.cna.ublkit.sign.certificado;

/**
 * Proveedor dinámico de certificados digitales.
 * <p>
 * Útil en arquitecturas multi-tenant masivas para desencriptar e inyectar el .p12
 * directamente desde una base de datos o desde servicios como GCP KMS / AWS KMS
 * en tiempo de ejecución.
 * </p>
 *
 * @since 0.1.0
 */
public interface ProveedorCertificado {

    /**
     * Obtiene los detalles de un certificado por su identificador (ej. RUC, ID tenant).
     *
     * @param id Identificador del certificado.
     * @return Detalles del certificado, o null si no se encuentra.
     */
    DetallesCertificado obtenerCertificado(String id);
}
