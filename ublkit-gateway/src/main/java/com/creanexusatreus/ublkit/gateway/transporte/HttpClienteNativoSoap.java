package com.creanexusatreus.ublkit.gateway.transporte;

import com.creanexusatreus.ublkit.gateway.api.ZipHelper;
import com.creanexusatreus.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.creanexusatreus.ublkit.gateway.respuesta.ArchivoCdr;
import com.creanexusatreus.ublkit.gateway.respuesta.EstadoEnvio;
import com.creanexusatreus.ublkit.gateway.respuesta.LectorCdr;
import com.creanexusatreus.ublkit.gateway.respuesta.ResultadoConsulta;
import com.creanexusatreus.ublkit.gateway.respuesta.ResultadoEnvio;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

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
        this(Duration.ofSeconds(10), Duration.ofSeconds(60), 100);
    }

    public HttpClienteNativoSoap(Duration connectTimeout, Duration readTimeout) {
        this(connectTimeout, readTimeout, 100);
    }

    public HttpClienteNativoSoap(Duration connectTimeout, Duration readTimeout, int maxConnections) {
        this.httpClient = HttpClientNativoFactory.crear(connectTimeout, maxConnections);
        this.readTimeout = readTimeout;
    }

    @Override
    public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales) {
        try {
            String base64Zip = ZipHelper.comprimirBase64(xmlFirmado, nombreArchivo);
            String nombreZip = com.creanexusatreus.ublkit.core.valor.NombresArchivosSunat.submissionZip(nombreArchivo);

            String payload = buildSendBillPayload(credenciales, nombreZip, base64Zip);
            String response = executePost(endpointUrl, payload);
            Document responseDoc = parsearXmlSeguro(response);

            String applicationResponseBase64 = extractValue(responseDoc, "applicationResponse");
            if (applicationResponseBase64 == null) {
                String fault = extractValue(responseDoc, "faultstring");
                return ResultadoEnvio.error("SOAP_FAULT", fault != null ? fault : "No se encontró CDR ni Fault en la respuesta");
            }

            byte[] cdrZipBytes = Base64.getDecoder().decode(applicationResponseBase64);
            ArchivoCdr cdr = LectorCdr.extraer(cdrZipBytes);
            EstadoEnvio estado = LectorCdr.determinarEstado(cdr);

            return ResultadoEnvio.sincronoProcesado(estado, cdr);

        } catch (com.creanexusatreus.ublkit.core.error.ExcepcionTransporte e) {
            return ResultadoEnvio.error("HTTP_5XX", e.getMessage());
        } catch (Exception e) {
            return ResultadoEnvio.error("IO_ERROR", e.getMessage());
        }
    }

    @Override
    public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl, CredencialesEmpresa credenciales) {
        try {
            String base64Zip = ZipHelper.comprimirBase64(xmlFirmado, nombreArchivo);
            String nombreZip = com.creanexusatreus.ublkit.core.valor.NombresArchivosSunat.submissionZip(nombreArchivo);

            String payload = buildSendSummaryPayload(credenciales, nombreZip, base64Zip);
            String response = executePost(endpointUrl, payload);
            Document responseDoc = parsearXmlSeguro(response);

            String ticket = extractValue(responseDoc, "ticket");
            if (ticket == null) {
                String fault = extractValue(responseDoc, "faultstring");
                return ResultadoEnvio.error("SOAP_FAULT", fault != null ? fault : "No se encontró Ticket ni Fault en la respuesta");
            }

            return ResultadoEnvio.asincrono(ticket);

        } catch (com.creanexusatreus.ublkit.core.error.ExcepcionTransporte e) {
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
            Document responseDoc = parsearXmlSeguro(response);

            String fault = extractValue(responseDoc, "faultstring");
            if (fault != null && !fault.isBlank()) {
                String faultCode = extractValue(responseDoc, "faultcode");
                return ResultadoConsulta.error(
                        faultCode != null && !faultCode.isBlank() ? faultCode : "SOAP_FAULT",
                        fault
                );
            }

            // status/statusCode (XSD billService: 0 = procesado, 98 = en proceso, 99 = procesado con error)
            String statusCode = extractValue(responseDoc, "statusCode");
            if ("98".equals(statusCode)) {
                return ResultadoConsulta.pendiente();
            }

            if ("0".equals(statusCode) || "99".equals(statusCode)) {
                String contentBase64 = extractValue(responseDoc, "content");
                if (contentBase64 == null || contentBase64.isBlank()) {
                    return ResultadoConsulta.error(statusCode,
                            "SUNAT devolvió statusCode " + statusCode + " sin content CDR");
                }

                byte[] cdrZipBytes = Base64.getDecoder().decode(contentBase64);
                ArchivoCdr cdr = LectorCdr.extraer(cdrZipBytes);
                EstadoEnvio estado = LectorCdr.determinarEstado(cdr);

                return ResultadoConsulta.completado(estado, cdr);
            }

            if (statusCode == null || statusCode.isBlank()) {
                return ResultadoConsulta.error("UNKNOWN_STATUS",
                        "Respuesta SOAP sin statusCode para la consulta de ticket");
            }

            return ResultadoConsulta.error(statusCode, "SUNAT devolvió statusCode " + statusCode);

        } catch (com.creanexusatreus.ublkit.core.error.ExcepcionTransporte e) {
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
            String fault = extractValueSeguro(response.body(), "faultstring");
            if (fault == null || fault.isBlank()) {
                throw new com.creanexusatreus.ublkit.core.error.ExcepcionTransporte("HTTP_" + response.statusCode() + " - " + response.body());
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

    private Document parsearXmlSeguro(String xml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private String extractValue(Document doc, String localName) {
        NodeList namespaced = doc.getElementsByTagNameNS("*", localName);
        if (namespaced.getLength() > 0) {
            String value = namespaced.item(0).getTextContent();
            return value != null ? value.trim() : null;
        }

        NodeList plain = doc.getElementsByTagName(localName);
        if (plain.getLength() > 0) {
            String value = plain.item(0).getTextContent();
            return value != null ? value.trim() : null;
        }

        NodeList allNodes = doc.getElementsByTagName("*");
        for (int i = 0; i < allNodes.getLength(); i++) {
            Node node = allNodes.item(i);
            String nodeName = node.getNodeName();
            if (nodeName != null && nodeName.endsWith(":" + localName)) {
                String value = node.getTextContent();
                return value != null ? value.trim() : null;
            }
        }
        return null;
    }

    private String extractValueSeguro(String xml, String localName) {
        try {
            Document doc = parsearXmlSeguro(xml);
            return extractValue(doc, localName);
        } catch (Exception e) {
            return null;
        }
    }
}
