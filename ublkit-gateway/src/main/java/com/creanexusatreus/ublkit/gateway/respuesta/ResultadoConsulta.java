package com.cna.ublkit.gateway.respuesta;

/**
 * Resultado de consultar un ticket previamente enviado de forma asíncrona
 * (ej. Resúmenes o Guías de Remisión).
 *
 * @param estado El estado resultante de la consulta (ej. ACEPTADO, RECHAZADO, EN_PROCESAMIENTO).
 * @param cdr Si finalizó satisfactoriamente, contiene el CDR.
 * @param codigoError Código de error en caso de fallo (SOAP fault o REST error).
 * @param mensajeError El detalle del error.
 *
 * @since 0.1.0
 */
public record ResultadoConsulta(
        EstadoEnvio estado,
        ArchivoCdr cdr,
        String codigoError,
        String mensajeError
) {

    public static ResultadoConsulta completado(EstadoEnvio estado, ArchivoCdr cdr) {
        return new ResultadoConsulta(estado, cdr, null, null);
    }

    public static ResultadoConsulta pendiente() {
        return new ResultadoConsulta(EstadoEnvio.EN_PROCESAMIENTO, null, null, null);
    }

    public static ResultadoConsulta error(String codigoError, String mensajeError) {
        return new ResultadoConsulta(EstadoEnvio.EXCEPCION, null, codigoError, mensajeError);
    }
}
