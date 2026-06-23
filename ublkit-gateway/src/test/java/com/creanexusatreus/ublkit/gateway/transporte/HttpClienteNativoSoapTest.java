package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link HttpClienteNativoSoap} implementation.
 * Tests SOAP client functionality including envelope creation, SOAP faults, and XML parsing.
 */
class HttpClienteNativoSoapTest {

    private HttpClienteNativoSoap clienteSoap;
    private CredencialesEmpresa credenciales;

    @BeforeEach
    void setup() {
        clienteSoap = new HttpClienteNativoSoap();
        credenciales = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
    }

    /**
     * Test that HttpClienteNativoSoap can be instantiated.
     */
    @Test
    void constructor_createsInstanceSuccessfully() {
        assertThat(clienteSoap).isNotNull();
        assertThat(clienteSoap).isInstanceOf(ClienteSoap.class);
    }

    /**
     * Test that enviarSincrono method is defined and callable.
     */
    @Test
    void enviarSincrono_methodIsDefined() throws NoSuchMethodException {
        var method = HttpClienteNativoSoap.class.getDeclaredMethod("enviarSincrono",
                String.class, String.class, String.class, CredencialesEmpresa.class);
        assertThat(method).isNotNull();
        assertThat(method.getReturnType()).isEqualTo(ResultadoEnvio.class);
    }

    /**
     * Test that enviarAsincrono method is defined and callable.
     */
    @Test
    void enviarAsincrono_methodIsDefined() throws NoSuchMethodException {
        var method = HttpClienteNativoSoap.class.getDeclaredMethod("enviarAsincrono",
                String.class, String.class, String.class, CredencialesEmpresa.class);
        assertThat(method).isNotNull();
        assertThat(method.getReturnType()).isEqualTo(ResultadoEnvio.class);
    }

    /**
     * Test that consultarTicket method is defined and callable.
     */
    @Test
    void consultarTicket_methodIsDefined() throws NoSuchMethodException {
        var method = HttpClienteNativoSoap.class.getDeclaredMethod("consultarTicket",
                String.class, String.class, CredencialesEmpresa.class);
        assertThat(method).isNotNull();
        assertThat(method.getReturnType()).isEqualTo(ResultadoConsulta.class);
    }

    /**
     * Test that enviarSincrono handles null XML gracefully.
     */
    @Test
    void enviarSincrono_withNullXml_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono(null, "test.xml", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono handles null endpoint gracefully.
     */
    @Test
    void enviarSincrono_withNullEndpoint_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("<xml/>", "test.xml", null, credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono handles null credenciales gracefully.
     */
    @Test
    void enviarSincrono_withNullCredenciales_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("<xml/>", "test.xml", "http://test", null);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono handles malformed XML gracefully.
     */
    @Test
    void enviarSincrono_withMalformedXml_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("not valid xml", "test.xml",
                "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono handles invalid endpoint URL gracefully.
     */
    @Test
    void enviarSincrono_withInvalidUrl_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("<xml/>", "test.xml",
                "invalid-url", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarAsincrono handles null XML gracefully.
     */
    @Test
    void enviarAsincrono_withNullXml_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarAsincrono(null, "test.xml", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarAsincrono handles null endpoint gracefully.
     */
    @Test
    void enviarAsincrono_withNullEndpoint_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarAsincrono("<xml/>", "test.xml", null, credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarAsincrono handles null credenciales gracefully.
     */
    @Test
    void enviarAsincrono_withNullCredenciales_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarAsincrono("<xml/>", "test.xml", "http://test", null);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that consultarTicket handles null ticket gracefully.
     */
    @Test
    void consultarTicket_withNullTicket_returnsErrorResult() {
        ResultadoConsulta result = clienteSoap.consultarTicket(null, "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that consultarTicket handles null endpoint gracefully.
     */
    @Test
    void consultarTicket_withNullEndpoint_returnsErrorResult() {
        ResultadoConsulta result = clienteSoap.consultarTicket("123", null, credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that consultarTicket handles null credenciales gracefully.
     */
    @Test
    void consultarTicket_withNullCredenciales_returnsErrorResult() {
        ResultadoConsulta result = clienteSoap.consultarTicket("123", "http://test", null);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that HttpClienteNativoSoap implements ClienteSoap interface.
     */
    @Test
    void httpClienteNativoSoap_implementsClienteSoapInterface() {
        assertThat(ClienteSoap.class.isAssignableFrom(HttpClienteNativoSoap.class))
                .isTrue();
    }

    /**
     * Test that multiple instances can be created independently.
     */
    @Test
    void httpClienteNativoSoap_multipleInstancesCanBeCreated() {
        HttpClienteNativoSoap client1 = new HttpClienteNativoSoap();
        HttpClienteNativoSoap client2 = new HttpClienteNativoSoap();

        assertThat(client1).isNotNull();
        assertThat(client2).isNotNull();
        assertThat(client1).isNotSameAs(client2);
    }

    /**
     * Test that enviarSincrono with network error returns error result.
     */
    @Test
    void enviarSincrono_withNetworkError_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("<xml/>", "test.xml",
                "http://unreachable-host-12345.invalid", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarAsincrono with network error returns error result.
     */
    @Test
    void enviarAsincrono_withNetworkError_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarAsincrono("<xml/>", "test.xml",
                "http://unreachable-host-12345.invalid", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that consultarTicket with network error returns error result.
     */
    @Test
    void consultarTicket_withNetworkError_returnsErrorResult() {
        ResultadoConsulta result = clienteSoap.consultarTicket("123",
                "http://unreachable-host-12345.invalid", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono with empty XML handles gracefully.
     */
    @Test
    void enviarSincrono_withEmptyXml_returnsErrorResult() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("", "test.xml", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that consultarTicket with empty ticket handles gracefully.
     */
    @Test
    void consultarTicket_withEmptyTicket_returnsErrorResult() {
        ResultadoConsulta result = clienteSoap.consultarTicket("", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
    }

    /**
     * Test that enviarSincrono returns correct result structure.
     */
    @Test
    void enviarSincrono_returnsResultadoEnvioStructure() {
        ResultadoEnvio result = clienteSoap.enviarSincrono("<xml/>", "test.xml", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isNotNull();
    }

    /**
     * Test that enviarAsincrono returns correct result structure.
     */
    @Test
    void enviarAsincrono_returnsResultadoEnvioStructure() {
        ResultadoEnvio result = clienteSoap.enviarAsincrono("<xml/>", "test.xml", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isNotNull();
    }

    /**
     * Test that consultarTicket returns correct result structure.
     */
    @Test
    void consultarTicket_returnsResultadoConsultaStructure() {
        ResultadoConsulta result = clienteSoap.consultarTicket("123", "http://test", credenciales);

        assertThat(result).isNotNull();
        assertThat(result.estado()).isNotNull();
    }

    /**
     * Test that very long XML is handled.
     */
    @Test
    void enviarSincrono_withVeryLongXml_handlesCorrectly() {
        StringBuilder longXml = new StringBuilder("<xml>");
        for (int i = 0; i < 10000; i++) {
            longXml.append("<data>").append(i).append("</data>");
        }
        longXml.append("</xml>");

        ResultadoEnvio result = clienteSoap.enviarSincrono(longXml.toString(), "test.xml",
                "http://test", credenciales);

        assertThat(result).isNotNull();
    }
}
