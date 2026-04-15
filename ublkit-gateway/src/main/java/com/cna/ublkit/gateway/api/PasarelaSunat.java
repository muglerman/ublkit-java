package com.cna.ublkit.gateway.api;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

/**
 * Fachada principal del módulo Gateway para enviar y consultar comprobantes.
 * Encapsula la delegación hacia clientes SOAP o REST según corresponda,
 * simplificando la integración para la capa de aplicación.
 *
 * @since 0.1.0
 */
public interface PasarelaSunat {

    /**
     * Envía un comprobante tradicional (Factura, Boleta, NC, ND) de forma síncrona
     * mediante SOAP (billService.sendBill).
     *
     * @param xmlFirmado Contenido XML ya firmado del comprobante.
     * @param nombreArchivo Nombre del comprobante XML (ej. 20123456789-01-F001-1.xml).
     * @param credenciales Credenciales de la empresa.
     * @param ambiente Entorno de destino (BETA o PRODUCCION).
     * @return El resultado con el CDR de SUNAT incluido, si fue aceptado o rechazado.
     */
    ResultadoEnvio enviarComprobante(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

    /**
     * Envía un comprobante de Retención (20) o Percepción (40) de forma síncrona
     * mediante SOAP. (Usa un endpoint distinto internamente).
     *
     * @param xmlFirmado Contenido XML firmado.
     * @param nombreArchivo Nombre del archivo (ej. 20123456789-20-R001-1.xml).
     * @param credenciales Credenciales.
     * @param ambiente Ambiente.
     * @return El resultado con el CDR de SUNAT incluido.
     */
    ResultadoEnvio enviarRetencionPercepcion(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

    /**
     * Envía un Resumen Diario o Comunicación de Baja de forma asíncrona mediante SOAP.
     * (billService.sendSummary).
     *
     * @param xmlFirmado Contenido XML del resumen firmado.
     * @param nombreArchivo Nombre del archivo de resumen (ej. 20123456789-RC-20230101-1.xml).
     * @param credenciales Credenciales de la empresa.
     * @param ambiente Entorno de destino.
     * @return El ticket asíncrono asignado por SUNAT.
     */
    ResultadoEnvio enviarResumen(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

    /**
     * Envía una Guía de Remisión Electrónica mediante el API REST de SUNAT.
     * Orquesta automáticamente la petición OAuth2 para el token temporal.
     *
     * @param xmlFirmado Contenido XML de la GRE.
     * @param nombreArchivo Nombre del archivo de la GRE.
     * @param credenciales Credenciales que deben incluir clientId y clientSecret.
     * @param ambiente Entorno de envío.
     * @return El ticket asignado por el API REST.
     */
    ResultadoEnvio enviarGuiaRemision(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

    /**
     * Consulta el estado de un ticket SOAP (Resúmenes, Bajas).
     */
    ResultadoConsulta consultarTicketSoap(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

    /**
     * Consulta el estado de un ticket REST (Guías de Remisión).
     */
    ResultadoConsulta consultarTicketRest(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente);

}
