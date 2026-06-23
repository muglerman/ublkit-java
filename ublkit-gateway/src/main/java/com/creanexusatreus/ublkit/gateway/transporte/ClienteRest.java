package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

/**
 * Define el contrato para el cliente de transporte REST (usado para
 * Guías de Remisión Electrónica - GRE).
 *
 * @since 0.1.0
 */
public interface ClienteRest {

    /**
     * Envía una Guía de Remisión XML vía el API REST de envío.
     *
     * @param xmlFirmado    Contenido XML firmado de la Guía.
     * @param nombreArchivo Nombre del archivo (ej. "20000000000-09-T001-1.xml").
     * @param endpointUrl   URL final de envío REST (incluyendo el sufijo si es necesario).
     * @param tokenBearer   Token obtenido vía OAuth2.
     * @return El resultado que, tratándose de Guías, típicamente provee un Ticket (Asíncrono).
     */
    ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer);

    /**
     * Consulta el estado de un ticket de Guía de Remisión a través de la API REST.
     *
     * @param numeroTicket Número de ticket devuelto en el envío.
     * @param endpointUrl URL final de consulta REST.
     * @param tokenBearer Token obtenido vía OAuth2.
     * @return Resultado de la consulta con el CDR si ya finalizó.
     */
    ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer);
}
