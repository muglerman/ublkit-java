package com.cna.ublkit.gateway.endpoint;

/**
 * Definición de URLs públicas oficiales de SUNAT para los distintos servicios.
 * Estas URLs actúan como constantes maestras. Si cambian, se actualizan aquí.
 *
 * @since 0.1.0
 */
public final class ConstantesEndpoint {

    private ConstantesEndpoint() { }

    // --- SOAP (Facturas, Boletas, Notas, Resúmenes) ---

    // Facturas y Boletas
    public static final String SOAP_BETA_FACTURA = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    public static final String SOAP_PROD_FACTURA = "https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService";

    // Retenciones y Percepciones
    public static final String SOAP_BETA_RETENCION = "https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService";
    public static final String SOAP_PROD_RETENCION = "https://e-factura.sunat.gob.pe/ol-ti-itemision-otroscpe-gem/billService";

    // Consulta de validez / Ticket (Opcional, distintos WSDL según el caso)
    public static final String SOAP_BETA_CONSULTA = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService"; 
    public static final String SOAP_PROD_CONSULTA = "https://e-factura.sunat.gob.pe/ol-it-wsconscpegem/billConsultService"; 


    // --- REST (Guías de Remisión Electrónica - GRE) ---

    // Autenticación (Obtención de Token)
    // SUNAT documenta la generación del token en api-seguridad.sunat.gob.pe.
    // El slash final es importante para respetar la forma publicada del recurso.
    public static final String REST_BETA_TOKEN = "https://api-seguridad.sunat.gob.pe/v1/clientessol/{client_id}/oauth2/token/";
    public static final String REST_PROD_TOKEN = "https://api-seguridad.sunat.gob.pe/v1/clientessol/{client_id}/oauth2/token/";

    // Envío GRE
    public static final String REST_BETA_ENVIO = "https://gre-test.nubefact.com/v1/contribuyente/gem/comprobantes/"; //+{numRucEmisor}-{codCpe}-{numSerie}-{numCpe}
    public static final String REST_PROD_ENVIO = "https://api-cpe.sunat.gob.pe/v1/contribuyente/gem/comprobantes/";

    // Consulta de Ticket GRE
    public static final String REST_BETA_TICKET = "https://gre-test.nubefact.com/v1/contribuyente/gem/comprobantes/envios/"; //+{numTicket}
    public static final String REST_PROD_TICKET = "https://api-cpe.sunat.gob.pe/v1/contribuyente/gem/comprobantes/envios/";
}
