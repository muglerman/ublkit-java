package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.api.ZipHelper;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ArchivoCdr;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.LectorCdr;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementación de {@link ClienteSoap} utilizando {@link HttpClient} nativo de Java 11+.
 * Elimina la necesidad de frameworks JAX-WS, CXF o Spring WS.
 *
 * @since 0.1.0
 */
public class HttpClienteNativoSoap implements ClienteSoap {

    private final HttpClient httpClient;
    private final Duration readTimeout;

    public HttpClienteNativoSoap() {
        this(Duration.ofSeconds(10), Duration.ofSeconds(60));
    }

    public HttpClienteNativoSoap(Duration connectTimeout, Duration readTimeout) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(connectTimeout)
                .build();
        this.readTimeout = readTimeout;
    }

    @Override
    public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales) {
        try {
            byte[] zipBytes = ZipHelper.comprimir(xmlFirmado, nombreArchivo);
            String base64Zip = Base64.getEncoder().encodeToString(zipBytes);
            String nombreZip = nombreArchivo.replace(".xml", ".zip");

            String payload = buildSendBillPayload(credenciales, nombreZip, base64Zip);
            String response = executePost(endpointUrl, payload);

            String applicationResponseBase64 = extractValue(response, "applicationResponse");
            if (applicationResponseBase64 == null) {
                String fault = extractValue(response, "faultstring");
                return ResultadoEnvio.error("SOAP_FAULT", fault != null ? fault : "No se encontró CDR ni Fault en la respuesta");
            }

            byte[] cdrZipBytes = Base64.getDecoder().decode(applicationResponseBase64);
            ArchivoCdr cdr = LectorCdr.extraer(cdrZipBytes);
            EstadoEnvio estado = LectorCdr.determinarEstado(cdr);

            return ResultadoEnvio.sincronoProcesado(estado, cdr);

        } catch (com.cna.ublkit.core.error.ExcepcionTransporte e) {
            return ResultadoEnvio.error("HTTP_5XX", e.getMessage());
        } catch (Exception e) {
            return ResultadoEnvio.error("IO_ERROR", e.getMessage());
        }
    }

    @Override
    public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales) {
        try {
            byte[] zipBytes = ZipHelper.comprimir(xmlFirmado, nombreArchivo);
            String base64Zip = Base64.getEncoder().encodeToString(zipBytes);
            String nombreZip = nombreArchivo.replace(".xml", ".zip");

            String payload = buildSendSummaryPayload(credenciales, nombreZip, base64Zip);
            String response = executePost(endpointUrl, payload);

            String ticket = extractValue(response, "ticket");
            if (ticket == null) {
                String fault = extractValue(response, "faultstring");
                return ResultadoEnvio.error("SOAP_FAULT", fault != null ? fault : "No se encontró Ticket ni Fault en la respuesta");
            }

            return ResultadoEnvio.asincrono(ticket);

        } catch (com.cna.ublkit.core.error.ExcepcionTransporte e) {
            return ResultadoEnvio.error("HTTP_5XX", e.getMessage());
        } catch (Exception e) {
            return ResultadoEnvio.error("IO_ERROR", e.getMessage());
        }
    }

    @Override
    public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, CredencialesEmpresa credenciales) {
        try {
            String payload = buildGetStatusPayload(credenciales, numeroTicket);
            String response = executePost(endpointUrl, payload);

            // status/statusCode (SUNAT dice: 0=Procesado OK, 98=En Proceso, 99=Con Error)
            String statusCode = extractValue(response, "statusCode");
            if ("98".equals(statusCode)) {
                return ResultadoConsulta.pendiente();
            }

            String contentBase64 = extractValue(response, "content");
            if (contentBase64 == null || contentBase64.isBlank()) {
                String fault = extractValue(response, "faultstring");
                if (fault != null) {
                    return ResultadoConsulta.error("SOAP_FAULT", fault);
                }
                return ResultadoConsulta.error("UNKOWN_STATUS", "No se encontró contenido CDR ni fault en estado: " + statusCode);
            }

            byte[] cdrZipBytes = Base64.getDecoder().decode(contentBase64);
            ArchivoCdr cdr = LectorCdr.extraer(cdrZipBytes);
            EstadoEnvio estado = LectorCdr.determinarEstado(cdr);

            return ResultadoConsulta.completado(estado, cdr);

        } catch (com.cna.ublkit.core.error.ExcepcionTransporte e) {
            return ResultadoConsulta.error("HTTP_5XX", e.getMessage());
        } catch (Exception e) {
            return ResultadoConsulta.error("IO_ERROR", e.getMessage());
        }
    }

    private String executePost(String endpointUrl, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .timeout(readTimeout)
                .header("Content-Type", "text/xml;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 500) {
            String fault = extractValue(response.body(), "faultstring");
            if (fault == null || fault.isBlank()) {
                throw new com.cna.ublkit.core.error.ExcepcionTransporte("HTTP_" + response.statusCode() + " - " + response.body());
            }
        }

        return response.body();
    }

    private String buildSendBillPayload(CredencialesEmpresa cred, String fileName, String base64Content) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.sunat.gob.pe" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                   <soapenv:Header>
                      <wsse:Security>
                         <wsse:UsernameToken>
                            <wsse:Username>%s</wsse:Username>
                            <wsse:Password>%s</wsse:Password>
                         </wsse:UsernameToken>
                      </wsse:Security>
                   </soapenv:Header>
                   <soapenv:Body>
                      <ser:sendBill>
                         <fileName>%s</fileName>
                         <contentFile>%s</contentFile>
                      </ser:sendBill>
                   </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(cred.getUsernameConcatenado(), cred.claveSol(), fileName, base64Content);
    }

    private String buildSendSummaryPayload(CredencialesEmpresa cred, String fileName, String base64Content) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.sunat.gob.pe" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                   <soapenv:Header>
                      <wsse:Security>
                         <wsse:UsernameToken>
                            <wsse:Username>%s</wsse:Username>
                            <wsse:Password>%s</wsse:Password>
                         </wsse:UsernameToken>
                      </wsse:Security>
                   </soapenv:Header>
                   <soapenv:Body>
                      <ser:sendSummary>
                         <fileName>%s</fileName>
                         <contentFile>%s</contentFile>
                      </ser:sendSummary>
                   </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(cred.getUsernameConcatenado(), cred.claveSol(), fileName, base64Content);
    }

    private String buildGetStatusPayload(CredencialesEmpresa cred, String ticket) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.sunat.gob.pe" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                   <soapenv:Header>
                      <wsse:Security>
                         <wsse:UsernameToken>
                            <wsse:Username>%s</wsse:Username>
                            <wsse:Password>%s</wsse:Password>
                         </wsse:UsernameToken>
                      </wsse:Security>
                   </soapenv:Header>
                   <soapenv:Body>
                      <ser:getStatus>
                         <ticket>%s</ticket>
                      </ser:getStatus>
                   </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(cred.getUsernameConcatenado(), cred.claveSol(), ticket);
    }

    private String extractValue(String xml, String tag) {
        Pattern pattern = Pattern.compile("<" + tag + "[^>]*>(.*?)</" + tag + ">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        
        // Handle namespace prefixes (e.g. <ns2:applicationResponse>)
        pattern = Pattern.compile("<[^>]+:" + tag + "[^>]*>(.*?)</[^>]+:" + tag + ">", Pattern.DOTALL);
        matcher = pattern.matcher(xml);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
