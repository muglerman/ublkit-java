package com.creanexusatreus.ublkit.gateway.transporte;

import com.creanexusatreus.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.creanexusatreus.ublkit.gateway.respuesta.EstadoEnvio;
import com.creanexusatreus.ublkit.gateway.respuesta.ResultadoConsulta;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpClienteNativoSoapConsultarTicketTest {

    private static final CredencialesEmpresa CREDENCIALES =
            new CredencialesEmpresa("20000000000", "MODDATOS", "moddatos", "client", "secret");

    private final HttpClienteNativoSoap cliente =
            new HttpClienteNativoSoap(Duration.ofSeconds(2), Duration.ofSeconds(5), 10);

    @Test
    void consultarTicket_status98_retornaPendiente() throws Exception {
        String soap = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>98</statusCode>
                  </status>
                </ns1:getStatusResponse>
                """);

        AtomicReference<String> requestBody = new AtomicReference<>();
        HttpServer server = crearServidor(200, soap, requestBody);
        try {
            ResultadoConsulta resultado = cliente.consultarTicket("TK-98", urlServidor(server), CREDENCIALES);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
            assertThat(resultado.cdr()).isNull();
            assertThat(requestBody.get()).contains("<ticket>TK-98</ticket>");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void consultarTicket_status0_conContent_validaCdrYRetornaEstadoFinal() throws Exception {
        String cdrBase64 = cdrZipBase64("0", "Aceptado");
        String soap = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>0</statusCode>
                    <content>%s</content>
                  </status>
                </ns1:getStatusResponse>
                """.formatted(cdrBase64));

        HttpServer server = crearServidor(200, soap, new AtomicReference<>());
        try {
            ResultadoConsulta resultado = cliente.consultarTicket("TK-0", urlServidor(server), CREDENCIALES);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
            assertThat(resultado.cdr()).isNotNull();
            assertThat(resultado.cdr().codigoRegreso()).isEqualTo("0");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void consultarTicket_status99_conContent_derivaEstadoPorResponseCodeCdr() throws Exception {
        String cdrBase64 = cdrZipBase64("2050", "Rechazado");
        String soap = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>99</statusCode>
                    <content>%s</content>
                  </status>
                </ns1:getStatusResponse>
                """.formatted(cdrBase64));

        HttpServer server = crearServidor(200, soap, new AtomicReference<>());
        try {
            ResultadoConsulta resultado = cliente.consultarTicket("TK-99", urlServidor(server), CREDENCIALES);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.RECHAZADO);
            assertThat(resultado.cdr()).isNotNull();
            assertThat(resultado.cdr().codigoRegreso()).isEqualTo("2050");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void consultarTicket_status99_sinContent_retornaExcepcionConCodigoReal() throws Exception {
        String soap = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>99</statusCode>
                  </status>
                </ns1:getStatusResponse>
                """);

        HttpServer server = crearServidor(200, soap, new AtomicReference<>());
        try {
            ResultadoConsulta resultado = cliente.consultarTicket("TK-NOCONTENT", urlServidor(server), CREDENCIALES);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
            assertThat(resultado.codigoError()).isEqualTo("99");
            assertThat(resultado.mensajeError()).contains("statusCode 99");
        } finally {
            server.stop(0);
        }
    }

    @Test
    void consultarTicket_status0126Y0127_retornaExcepcionConCodigoExplicito() throws Exception {
        String soap0126 = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>0126</statusCode>
                  </status>
                </ns1:getStatusResponse>
                """);
        HttpServer server0126 = crearServidor(200, soap0126, new AtomicReference<>());
        try {
            ResultadoConsulta resultado0126 = cliente.consultarTicket("TK-0126", urlServidor(server0126), CREDENCIALES);
            assertThat(resultado0126.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
            assertThat(resultado0126.codigoError()).isEqualTo("0126");
            assertThat(resultado0126.mensajeError()).contains("statusCode 0126");
        } finally {
            server0126.stop(0);
        }

        String soap0127 = soapEnvelope("""
                <ns1:getStatusResponse xmlns:ns1="http://service.sunat.gob.pe">
                  <status>
                    <statusCode>0127</statusCode>
                  </status>
                </ns1:getStatusResponse>
                """);
        HttpServer server0127 = crearServidor(200, soap0127, new AtomicReference<>());
        try {
            ResultadoConsulta resultado0127 = cliente.consultarTicket("TK-0127", urlServidor(server0127), CREDENCIALES);
            assertThat(resultado0127.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
            assertThat(resultado0127.codigoError()).isEqualTo("0127");
            assertThat(resultado0127.mensajeError()).contains("statusCode 0127");
        } finally {
            server0127.stop(0);
        }
    }

    @Test
    void consultarTicket_soapFault_retornaExcepcionConFaultCodeYFaultString() throws Exception {
        String fault = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                  <soapenv:Body>
                    <soapenv:Fault>
                      <faultcode>soapenv:Server</faultcode>
                      <faultstring>Ticket no existe</faultstring>
                    </soapenv:Fault>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        HttpServer server = crearServidor(500, fault, new AtomicReference<>());
        try {
            ResultadoConsulta resultado = cliente.consultarTicket("TK-FAULT", urlServidor(server), CREDENCIALES);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
            assertThat(resultado.codigoError()).isEqualTo("soapenv:Server");
            assertThat(resultado.mensajeError()).isEqualTo("Ticket no existe");
        } finally {
            server.stop(0);
        }
    }

    private static HttpServer crearServidor(int statusCode, String body, AtomicReference<String> requestBody) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/", exchange -> responder(exchange, statusCode, body, requestBody));
        server.start();
        return server;
    }

    private static void responder(HttpExchange exchange, int statusCode, String body, AtomicReference<String> requestBody) throws IOException {
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        requestBody.set(new String(requestBytes, StandardCharsets.UTF_8));

        byte[] responseBytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/xml;charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private static String urlServidor(HttpServer server) {
        return "http://localhost:" + server.getAddress().getPort() + "/";
    }

    private static String soapEnvelope(String body) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                  <soapenv:Body>
                    %s
                  </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(body);
    }

    private static String cdrZipBase64(String responseCode, String description) throws IOException {
        String cdrXml = """
                <ApplicationResponse xmlns="urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2"
                                     xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
                                     xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                  <cac:DocumentResponse>
                    <cac:Response>
                      <cbc:ResponseCode>%s</cbc:ResponseCode>
                      <cbc:Description>%s</cbc:Description>
                    </cac:Response>
                  </cac:DocumentResponse>
                </ApplicationResponse>
                """.formatted(responseCode, description);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            zos.putNextEntry(new ZipEntry("R-20000000000-01-F001-1.xml"));
            zos.write(cdrXml.getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
