package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ClienteRest} interface.
 * Tests the REST client contract definition.
 */
class ClienteRestTest {

    /**
     * Test that the interface defines enviarGuia method.
     */
    @Test
    void interface_definesEnviarGuiaMethod() {
        assertThat(ClienteRest.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarGuia") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines consultarTicket method.
     */
    @Test
    void interface_definesConsultarTicketMethod() {
        assertThat(ClienteRest.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicket") && m.getParameterCount() == 3);
    }

    /**
     * Test that enviarGuia returns ResultadoEnvio.
     */
    @Test
    void interface_enviarGuiaReturnsResultadoEnvio() {
        assertThat(ClienteRest.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarGuia") &&
                        m.getReturnType().equals(ResultadoEnvio.class));
    }

    /**
     * Test that consultarTicket returns ResultadoConsulta.
     */
    @Test
    void interface_consultarTicketReturnsResultadoConsulta() {
        assertThat(ClienteRest.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicket") &&
                        m.getReturnType().equals(ResultadoConsulta.class));
    }

    /**
     * Test that interface can be implemented.
     */
    @Test
    void interface_canBeImplemented() {
        ClienteRest testImpl = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                return ResultadoEnvio.asincrono("123");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return ResultadoConsulta.pendiente();
            }
        };

        assertThat(testImpl).isNotNull();
        assertThat(testImpl).isInstanceOf(ClienteRest.class);
    }

    /**
     * Test that enviarGuia can be called with all parameters.
     */
    @Test
    void interface_enviarGuiaCanBeCalledWithAllParameters() {
        ClienteRest testImpl = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                assertThat(xmlFirmado).isNotNull();
                assertThat(nombreArchivo).isNotNull();
                assertThat(endpointUrl).isNotNull();
                assertThat(tokenBearer).isNotNull();
                return ResultadoEnvio.asincrono("ticket");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return null;
            }
        };

        ResultadoEnvio result = testImpl.enviarGuia("<xml/>", "test.xml", "http://test", "token");

        assertThat(result).isNotNull();
    }

    /**
     * Test that consultarTicket can be called with all parameters.
     */
    @Test
    void interface_consultarTicketCanBeCalledWithAllParameters() {
        ClienteRest testImpl = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                return null;
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                assertThat(numeroTicket).isNotNull();
                assertThat(endpointUrl).isNotNull();
                assertThat(tokenBearer).isNotNull();
                return ResultadoConsulta.pendiente();
            }
        };

        ResultadoConsulta result = testImpl.consultarTicket("123", "http://test/", "token");

        assertThat(result).isNotNull();
    }

    /**
     * Test that implementations can return different results.
     */
    @Test
    void interface_implementationsCanReturnDifferentResults() {
        ClienteRest impl1 = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xml, String nombre, String endpoint, String token) {
                return ResultadoEnvio.asincrono("111");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpoint, String token) {
                return ResultadoConsulta.pendiente();
            }
        };

        ClienteRest impl2 = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xml, String nombre, String endpoint, String token) {
                return ResultadoEnvio.asincrono("222");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpoint, String token) {
                return ResultadoConsulta.pendiente();
            }
        };

        ResultadoEnvio result1 = impl1.enviarGuia("<xml/>", "test.xml", "http://test", "token");
        ResultadoEnvio result2 = impl2.enviarGuia("<xml/>", "test.xml", "http://test", "token");

        assertThat(result1.numeroTicket()).isEqualTo("111");
        assertThat(result2.numeroTicket()).isEqualTo("222");
    }

    /**
     * Test that implementations can handle null parameters.
     */
    @Test
    void interface_implementationsCanHandleNullParameters() {
        ClienteRest testImpl = new ClienteRest() {
            @Override
            public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
                if (xmlFirmado == null || nombreArchivo == null) {
                    return ResultadoEnvio.error("NULL_PARAM", "XML o nombre nulo");
                }
                return ResultadoEnvio.asincrono("123");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
                return null;
            }
        };

        ResultadoEnvio result = testImpl.enviarGuia(null, "test.xml", "http://test", "token");

        assertThat(result.estado()).isNotNull();
    }
}
