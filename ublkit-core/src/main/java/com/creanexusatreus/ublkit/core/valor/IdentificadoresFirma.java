package com.creanexusatreus.ublkit.core.valor;

/** Identificadores compartidos por las referencias de firma UBL y XMLDSIG. */
public final class IdentificadoresFirma {
    public static final String SIGNATURE_ID = "UBLKIT-SIGN";

    private IdentificadoresFirma() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciable");
    }

    public static String uri(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador de firma es obligatorio");
        }
        return "#" + id;
    }
}
