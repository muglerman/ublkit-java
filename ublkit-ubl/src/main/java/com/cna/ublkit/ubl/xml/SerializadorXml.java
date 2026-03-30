package com.cna.ublkit.ubl.xml;

/**
 * Contrato para serializar un modelo de dominio a XML UBL 2.1.
 *
 * @param <T> tipo del documento a serializar
 * @since 0.1.0
 */
@FunctionalInterface
public interface SerializadorXml<T> {

    /**
     * Serializa el documento a XML UBL 2.1.
     *
     * @param documento modelo de dominio a serializar
     * @return XML como String
     */
    String serializar(T documento);
}
