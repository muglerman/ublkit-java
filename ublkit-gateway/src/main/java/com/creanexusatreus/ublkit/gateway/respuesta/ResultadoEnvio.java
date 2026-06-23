package com.cna.ublkit.gateway.respuesta;

/**
 * Resultado de un envío síncrono o asíncrono.
 *
 * @param estado El estado resultante del envío (ej. ACEPTADO, TICKET_PENDIENTE).
 * @param cdr Si fue un envío sincrónico y fue procesado, aquí se aloja el Constancia de Recepción.
 * @param numeroTicket Si fue un envío asincrónico (Resumen, Guías, etc.), este es el ticket asignado.
 * @param codigoError En caso de excepción REST/SOAP, el código de error.
 * @param mensajeError El mensaje de la excepción de red/sistema.
 *
 * @since 0.1.0
 */
public record ResultadoEnvio(
        EstadoEnvio estado,
        ArchivoCdr cdr,
        String numeroTicket,
        String codigoError,
        String mensajeError
) {
    public static ResultadoEnvio asincrono(String ticket) {
        return new ResultadoEnvio(EstadoEnvio.TICKET_PENDIENTE, null, ticket, null, null);
    }

    public static ResultadoEnvio sincronoProcesado(EstadoEnvio estado, ArchivoCdr cdr) {
        return new ResultadoEnvio(estado, cdr, null, null, null);
    }

    public static ResultadoEnvio error(String codigoError, String mensajeError) {
        return new ResultadoEnvio(EstadoEnvio.EXCEPCION, null, null, codigoError, mensajeError);
    }
}
