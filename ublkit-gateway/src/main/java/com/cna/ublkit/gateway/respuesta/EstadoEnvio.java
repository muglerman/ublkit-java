package com.cna.ublkit.gateway.respuesta;

/**
 * Estado general del envío o consulta a los servicios de SUNAT/OSE.
 *
 * @since 0.1.0
 */
public enum EstadoEnvio {
    /** El documento ha sido aceptado exitosamente por SUNAT (Código 0). */
    ACEPTADO,

    /** El documento fue procesado pero contiene observaciones (Código 0, pero con notas). */
    ACEPTADO_CON_OBSERVACIONES,

    /** El documento fue rechazado por reglas de negocio de SUNAT. */
    RECHAZADO,

    /** Error a nivel del servicio o problema técnico (Excepción devuelta por SUNAT). */
    EXCEPCION,

    /** 
     * El documento fue recibido asíncronamente y tiene un Ticket asignado.
     * Requiere consulta posterior.
     */
    TICKET_PENDIENTE,

    /** El Ticket o envío está todavía en procesamiento interno en SUNAT. */
    EN_PROCESAMIENTO
}
