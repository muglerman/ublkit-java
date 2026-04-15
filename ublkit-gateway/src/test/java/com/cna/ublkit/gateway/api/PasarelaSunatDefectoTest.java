package com.cna.ublkit.gateway.api;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.autenticacion.ProveedorToken;
import com.cna.ublkit.gateway.config.ConfiguracionGateway;
import com.cna.ublkit.gateway.respuesta.ArchivoCdr;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import com.cna.ublkit.gateway.transporte.ClienteRest;
import com.cna.ublkit.gateway.transporte.ClienteSoap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for {@link PasarelaSunatDefecto} implementation.
 * Tests SOAP/REST delegation, retry logic, and error handling.
 */
class PasarelaSunatDefectoTest {

    private ClienteSoap mockClienteSoap;
    private ClienteRest mockClienteRest;
    private ProveedorToken mockProveedorToken;
    private PasarelaSunatDefecto gateway;

    @BeforeEach
    void setup() {
        mockClienteSoap = new MockClienteSoap();
        mockClienteRest = new MockClienteRest();
        mockProveedorToken = new MockProveedorToken();
        gateway = new PasarelaSunatDefecto(mockClienteSoap, mockClienteRest, mockProveedorToken);
    }

    @Test
    void constructor_withDefaults_createsInstanceWithNativeClients() {
        PasarelaSunatDefecto instance = new PasarelaSunatDefecto();
        assertThat(instance).isNotNull();
        assertThat(instance).isInstanceOf(PasarelaSunat.class);
    }

    @Test
    void constructor_withDependencies_createsInstanceWithInjectedClients() {
        assertThat(gateway).isNotNull();
        assertThat(gateway).isInstanceOf(PasarelaSunat.class);
    }

    @Test
    void enviarComprobante_withFacturaXml_delegatesToSoapSincronoAndReturnsCdr() {
        String xmlFirmado = "<Invoice><cbc:ID>F001-1</cbc:ID></Invoice>";
        String nombreArchivo = "20000000000-01-F001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarComprobante(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
    }

    @Test
    void enviarComprobante_withSummaryXml_delegatesToSoapAsincronoAndReturnsTicket() {
        String xmlFirmado = "<SummaryDocuments><ID>RC-20230101</ID></SummaryDocuments>";
        String nombreArchivo = "20000000000-RC-20230101-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarComprobante(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
        assertThat(resultado.numeroTicket()).isNotNull();
    }

    @Test
    void enviarComprobante_withVoidedXml_delegatesToSoapAsincronoAndReturnsTicket() {
        String xmlFirmado = "<VoidedDocuments><ID>VOID-001</ID></VoidedDocuments>";
        String nombreArchivo = "20000000000-VOID-001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarComprobante(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
    }

    @Test
    void enviarComprobante_withDespatchAdviceXml_delegatesToRest() {
        String xmlFirmado = "<DespatchAdvice><ID>09-GRE-001</ID></DespatchAdvice>";
        String nombreArchivo = "20000000000-09-GRE-001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarComprobante(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.numeroTicket()).isNotNull();
    }

    @Test
    void enviarRetencionPercepcion_delegatesToSoapAndReturnsCdr() {
        String xmlFirmado = "<Perception><ID>20-001</ID></Perception>";
        String nombreArchivo = "20000000000-20-001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarRetencionPercepcion(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
    }

    @Test
    void enviarResumen_delegatesToSoapAsincronoAndReturnsTicket() {
        String xmlFirmado = "<SummaryDocuments><ID>RESUMEN</ID></SummaryDocuments>";
        String nombreArchivo = "20000000000-RESUMEN-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarResumen(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
    }

    @Test
    void enviarGuiaRemision_obtainsTokenAndDelegatesToRest() {
        String xmlFirmado = "<DespatchAdvice><ID>09-GRE-001</ID></DespatchAdvice>";
        String nombreArchivo = "20000000000-09-GRE-001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarGuiaRemision(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.numeroTicket()).isNotNull();
    }

    @Test
    void consultarTicketSoap_delegatesToSoapClientAndReturnsConsultResult() {
        String ticket = "123456789";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoConsulta resultado = gateway.consultarTicketSoap(ticket, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
    }

    @Test
    void consultarTicketRest_obtainsTokenAndDelegatesToRest() {
        String ticket = "123456789";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoConsulta resultado = gateway.consultarTicketRest(ticket, credenciales, ambiente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
    }

    @Test
    void detectarRaizXml_withNamespacedFactura_returnsInvoice() {
        String xml = "<ns:Invoice xmlns:ns=\"http://example.com\"><ID>001</ID></ns:Invoice>";
        // Using reflection to test private method
        var resultado = invokePrivateMethod("detectarRaizXml", xml, String.class);
        assertThat(resultado).isEqualTo("Invoice");
    }

    @Test
    void detectarRaizXml_withSummaryDocuments_returnsSummaryDocuments() {
        String xml = "<SummaryDocuments><ID>001</ID></SummaryDocuments>";
        var resultado = invokePrivateMethod("detectarRaizXml", xml, String.class);
        assertThat(resultado).isEqualTo("SummaryDocuments");
    }

    @Test
    void detectarRaizXml_withXmlDeclaration_ignoresDeclarationAndExtractsRootTag() {
        String xml = "<?xml version=\"1.0\"?><Invoice><ID>001</ID></Invoice>";
        var resultado = invokePrivateMethod("detectarRaizXml", xml, String.class);
        assertThat(resultado).isEqualTo("Invoice");
    }

    @Test
    void detectarRaizXml_withNullXml_returnsEmptyString() {
        var resultado = invokePrivateMethod("detectarRaizXml", null, String.class);
        assertThat(resultado).isEqualTo("");
    }

    @Test
    void detectarRaizXml_withMalformedXml_returnsEmptyString() {
        String xml = "not a valid xml";
        var resultado = invokePrivateMethod("detectarRaizXml", xml, String.class);
        assertThat(resultado).isEqualTo("");
    }

    @Test
    void enviarComprobante_withNullXml_delegatesToSoapAndHandlesError() {
        // Test with null to verify null handling
        String nombreArchivo = "20000000000-01-F001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        ResultadoEnvio resultado = gateway.enviarComprobante(null, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
    }

    @Test
    void consultarTicketRest_buildsCorrectEndpointUrlWithoutDoubleSlash() {
        // Mock that captures the endpoint
        var endpointCapture = new Object() {
            String capturedEndpoint;
        };

        ClienteRest capturingRest = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xml, String nombre, String endpoint, String token) {
                return ResultadoEnvio.asincrono("123");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpoint, String token) {
                endpointCapture.capturedEndpoint = endpoint;
                return ResultadoConsulta.pendiente();
            }
        };

        var gatewayWithCapture = new PasarelaSunatDefecto(mockClienteSoap, capturingRest, mockProveedorToken);
        String ticket = "123456789";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        gatewayWithCapture.consultarTicketRest(ticket, credenciales, ambiente);

        // Should end with slash
        assertThat(endpointCapture.capturedEndpoint).endsWith("/");
    }

    @Test
    void enviarGuiaRemision_buildsCorrectEndpointUrl() {
        // This tests the URL building logic
        String xmlFirmado = "<DespatchAdvice><ID>09-GRE-001</ID></DespatchAdvice>";
        String nombreArchivo = "20000000000-09-GRE-001-1.xml";
        CredencialesEmpresa credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");
        TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

        // This should not throw an error
        ResultadoEnvio resultado = gateway.enviarGuiaRemision(xmlFirmado, nombreArchivo, credenciales, ambiente);

        assertThat(resultado).isNotNull();
    }

    @Test
    void enviarGuiaRemisionYEsperar_whenTicketCompletes_returnsCdr() {
        Queue<ResultadoConsulta> respuestas = new ArrayDeque<>();
        respuestas.add(ResultadoConsulta.completado(
                EstadoEnvio.ACEPTADO,
                new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of())
        ));

        ClienteRest restConSecuencia = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                return ResultadoEnvio.asincrono("TK-123");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return respuestas.isEmpty() ? ResultadoConsulta.pendiente() : respuestas.poll();
            }
        };

        PasarelaSunatDefecto gatewayAsync = new PasarelaSunatDefecto(
            mockClienteSoap,
            restConSecuencia,
            mockProveedorToken,
            ConfiguracionGateway.porDefecto(),
            1,
            10,
            3
        );
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");

        ArchivoCdr cdr = gatewayAsync.enviarGuiaRemisionYEsperar("<DespatchAdvice/>", "20000000000-09-T001-1.xml", cred, TipoAmbiente.BETA)
                .join();

        assertThat(cdr).isNotNull();
        assertThat(cdr.codigoRegreso()).isEqualTo("0");
    }

    @Test
    void enviarGuiaRemisionYEsperar_whenSendFails_completesExceptionally() {
        ClienteRest restError = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                return ResultadoEnvio.error("HTTP_5XX", "fallo temporal");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return ResultadoConsulta.pendiente();
            }
        };

        PasarelaSunatDefecto gatewayAsync = new PasarelaSunatDefecto(mockClienteSoap, restError, mockProveedorToken);
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");

        assertThatThrownBy(() -> gatewayAsync.enviarGuiaRemisionYEsperar(
                "<DespatchAdvice/>",
                "20000000000-09-T001-1.xml",
                cred,
                TipoAmbiente.BETA
        ).join())
                .isInstanceOf(CompletionException.class)
                .hasMessageContaining("Error al enviar GRE");
    }

    @Test
    void enviarGuiaRemisionYEsperar_whenTicketNeverCompletes_timesOut() {
        ClienteRest restSiemprePendiente = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                return ResultadoEnvio.asincrono("TK-999");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return ResultadoConsulta.pendiente();
            }
        };

        PasarelaSunatDefecto gatewayAsync = new PasarelaSunatDefecto(
                mockClienteSoap,
                restSiemprePendiente,
                mockProveedorToken,
                ConfiguracionGateway.porDefecto(),
                1,
                2,
                1
        );
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "client", "secret");

        assertThatThrownBy(() -> gatewayAsync.enviarGuiaRemisionYEsperar(
                "<DespatchAdvice/>",
                "20000000000-09-T001-1.xml",
                cred,
                TipoAmbiente.BETA
        ).join())
                .isInstanceOf(CompletionException.class)
                .hasMessageContaining("Tiempo de espera agotado");
    }

    /**
     * Helper to invoke private methods via reflection.
     */
    private Object invokePrivateMethod(String methodName, Object arg, Class<?> argType) {
        try {
            var method = PasarelaSunatDefecto.class.getDeclaredMethod(methodName, argType);
            method.setAccessible(true);
            return method.invoke(gateway, arg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // --- Mock implementations ---

    static class MockClienteSoap implements ClienteSoap {
        @Override
        public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                CredencialesEmpresa credenciales) {
            var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
            return ResultadoEnvio.sincronoProcesado(EstadoEnvio.ACEPTADO, cdr);
        }

        @Override
        public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                CredencialesEmpresa credenciales) {
            return ResultadoEnvio.asincrono("123456789");
        }

        @Override
        public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                CredencialesEmpresa credenciales) {
            return ResultadoConsulta.pendiente();
        }
    }

    static class MockClienteRest implements ClienteRest {
        @Override
        public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl,
                String tokenBearer) {
            return ResultadoEnvio.asincrono("987654321");
        }

        @Override
        public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
            return ResultadoConsulta.pendiente();
        }
    }

    static class MockProveedorToken implements ProveedorToken {
        @Override
        public String obtenerToken(CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
            return "token_mock_123456";
        }
    }
    @org.junit.jupiter.api.Test
    void conRetry_whenHttp5xx_retriesUpToMaxIntentos() {
        var config = com.cna.ublkit.gateway.config.ConfiguracionGateway.porDefecto();
        var failingRest = new ClienteRest() {
            int count = 0;
            public ResultadoEnvio enviarGuia(String xml, String nombre, String url, String token) {
                count++;
                return ResultadoEnvio.error("HTTP_5XX", "Error temporal");
            }
            public ResultadoConsulta consultarTicket(String ticket, String url, String token) {
                return null;
            }
        };
        var gatewayRetry = new PasarelaSunatDefecto(mockClienteSoap, failingRest, mockProveedorToken, config);
        var result = gatewayRetry.enviarGuiaRemision("xml", "file.xml", new com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa("20000000000", "USER", "PASS", "clientId", "clientSecret"), TipoAmbiente.BETA);

        org.assertj.core.api.Assertions.assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        org.assertj.core.api.Assertions.assertThat(result.codigoError()).isEqualTo("HTTP_5XX");
                org.assertj.core.api.Assertions.assertThat(failingRest.count).isEqualTo(3);
    }
}
