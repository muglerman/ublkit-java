package com.cna.ublkit.gateway.endpoint;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.core.error.ExcepcionUblKit;

/**
 * Resuelve la URL final hacia donde se debe enviar la petición dependiendo del tipo
 * de documento (Factura, Retención, Guía) y el ambiente (BETA, PRODUCCION).
 *
 * @since 0.1.0
 */
public final class ResolvedorEndpoints {

    private ResolvedorEndpoints() { }

    /**
     * Resuelve el endpoint SOAP para el envío de comprobantes principales (01, 03, 07, 08).
     */
    public static String urlSoapFactura(TipoAmbiente ambiente) {
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.SOAP_PROD_FACTURA 
                : ConstantesEndpoint.SOAP_BETA_FACTURA;
        return override("ublkit.sunat.endpoint.soap.factura", ambiente, defecto);
    }

    /**
     * Resuelve el endpoint SOAP para retenciones (20) y percepciones (40).
     */
    public static String urlSoapRetencion(TipoAmbiente ambiente) {
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.SOAP_PROD_RETENCION 
                : ConstantesEndpoint.SOAP_BETA_RETENCION;
        return override("ublkit.sunat.endpoint.soap.retencion", ambiente, defecto);
    }

    /**
     * Resuelve el endpoint SOAP para consulta de estatus o tickets.
     */
    public static String urlSoapConsulta(TipoAmbiente ambiente) {
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.SOAP_PROD_CONSULTA
                : ConstantesEndpoint.SOAP_BETA_CONSULTA;
        return override("ublkit.sunat.endpoint.soap.consulta", ambiente, defecto);
    }

    /**
     * Resuelve el endpoint REST para obtener el token OAuth2.
     * @param clientId El Client ID (requerido para armar la URL final).
     */
    public static String urlRestToken(TipoAmbiente ambiente, String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new ExcepcionUblKit("Client ID es requerido para resolver la URL de Token REST");
        }
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.REST_PROD_TOKEN 
                : ConstantesEndpoint.REST_BETA_TOKEN;
        String urlBase = override("ublkit.sunat.endpoint.rest.token", ambiente, defecto);
        return urlBase.replace("{client_id}", clientId);
    }

    /**
     * Resuelve el endpoint REST base para el envío de Guías de Remisión.
     * Al usar, es probable que deba agregarse el patrón: `{numRucEmisor}-{codCpe}-{numSerie}-{numCpe}` final.
     */
    public static String urlRestEnvio(TipoAmbiente ambiente) {
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.REST_PROD_ENVIO 
                : ConstantesEndpoint.REST_BETA_ENVIO;
        return override("ublkit.sunat.endpoint.rest.envio", ambiente, defecto);
    }

    /**
     * Resuelve el endpoint REST base para la consulta de tickets GRE.
     * Al usar, es probable que deba agregarse el patrón: `{numTicket}` final.
     */
    public static String urlRestTicket(TipoAmbiente ambiente) {
        String defecto = ambiente == TipoAmbiente.PRODUCCION
                ? ConstantesEndpoint.REST_PROD_TICKET 
                : ConstantesEndpoint.REST_BETA_TICKET;
        return override("ublkit.sunat.endpoint.rest.ticket", ambiente, defecto);
    }

    private static String override(String claveBase, TipoAmbiente ambiente, String defecto) {
        String porAmbiente = System.getProperty(claveBase + "." + ambiente.name().toLowerCase());
        if (porAmbiente != null && !porAmbiente.isBlank()) return porAmbiente;

        String general = System.getProperty(claveBase);
        if (general != null && !general.isBlank()) return general;

        return defecto;
    }
}
