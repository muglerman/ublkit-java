package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

/**
 * Define el contrato para el cliente de transporte SOAP (usado para Facturas,
 * Boletas, Notas, Retenciones, Percepciones y Resúmenes).
 *
 * @since 0.1.0
 */
public interface ClienteSoap {

    /**
     * Envía un comprobante XML de forma síncrona (ej. billService.sendBill).
     *
     * @param xmlFirmado    Contenido XML ya firmado.
     * @param nombreArchivo Nombre del archivo (ej. "20000000000-01-F001-1.xml").
     * @param endpointUrl   URL del servicio SOAP de SUNAT/OSE.
     * @param credenciales  Credenciales SOAP tradicionales.
     * @return El resultado del envío síncrono que debería contener el CDR.
     */
    ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales);

    /**
     * Envía un lote o resumen XML de forma asíncrona (ej. billService.sendSummary).
     *
     * @param xmlFirmado    Contenido XML ya firmado.
     * @param nombreArchivo Nombre del archivo (ej. "20000000000-RC-20261231-1.xml").
     * @param endpointUrl   URL del servicio SOAP.
     * @param credenciales  Credenciales SOAP tradicionales.
     * @return El resultado que contiene el número de Ticket.
     */
    ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales);

    /**
     * Consulta el estado de un ticket previamente asignado (ej. billConsultService.getStatus).
     */
    ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, CredencialesEmpresa credenciales);
}
