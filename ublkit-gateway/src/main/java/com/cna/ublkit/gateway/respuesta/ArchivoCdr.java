package com.cna.ublkit.gateway.respuesta;

import java.util.List;

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
}
