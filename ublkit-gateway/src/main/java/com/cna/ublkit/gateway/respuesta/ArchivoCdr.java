package com.cna.ublkit.gateway.respuesta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representa el Constancia de Recepción (CDR) u otra constancia XML/ZIP
 * retornada por SUNAT.
 *
 * @param archivoBytes El archivo ZIP del CDR tal como es retornado (bytes crudos).
 * @param codigoRegreso El `cac:Response/cbc:ResponseCode` extraído del CDR (ej. "0").
 * @param descripcion La descripción oficial extraída del CDR.
 * @param notas Lista de observaciones/notas menores devueltas (si aplica).
 *
 * @since 0.1.0
 */
public record ArchivoCdr(
        byte[] archivoBytes,
        String codigoRegreso,
        String descripcion,
        List<String> notas
) {
    public ArchivoCdr {
        archivoBytes = archivoBytes == null ? null : archivoBytes.clone();
        notas = notas == null ? List.of() : List.copyOf(notas);
    }

    @Override
    public byte[] archivoBytes() {
        return archivoBytes == null ? null : archivoBytes.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArchivoCdr other)) return false;
        return Arrays.equals(this.archivoBytes, other.archivoBytes)
                && Objects.equals(this.codigoRegreso, other.codigoRegreso)
                && Objects.equals(this.descripcion, other.descripcion)
                && Objects.equals(this.notas, other.notas);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(archivoBytes);
        result = 31 * result + Objects.hash(codigoRegreso, descripcion, notas);
        return result;
    }

    @Override
    public String toString() {
        return "ArchivoCdr[" +
                "archivoBytes(length)=" + (archivoBytes == null ? 0 : archivoBytes.length) +
                ", codigoRegreso=" + codigoRegreso +
                ", descripcion=" + descripcion +
                ", notas=" + notas +
                ']';
    }
}
