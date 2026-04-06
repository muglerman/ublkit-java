package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ClienteSoap} interface.
 * Tests the SOAP client contract definition.
 */
class ClienteSoapTest {

    /**
     * Test that the interface defines enviarSincrono method.
     */
    @Test
    void interface_definesEnviarSincronoMethod() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarSincrono") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines enviarAsincrono method.
     */
    @Test
    void interface_definesEnviarAsincronoMethod() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarAsincrono") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines consultarTicket method.
     */
    @Test
    void interface_definesConsultarTicketMethod() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicket") && m.getParameterCount() == 3);
    }

    /**
     * Test that enviarSincrono returns ResultadoEnvio.
     */
    @Test
    void interface_enviarSincronoReturnsResultadoEnvio() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarSincrono") &&
                        m.getReturnType().equals(ResultadoEnvio.class));
    }

    /**
     * Test that enviarAsincrono returns ResultadoEnvio.
     */
    @Test
    void interface_enviarAsincronoReturnsResultadoEnvio() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarAsincrono") &&
                        m.getReturnType().equals(ResultadoEnvio.class));
    }

    /**
     * Test that consultarTicket returns ResultadoConsulta.
     */
    @Test
    void interface_consultarTicketReturnsResultadoConsulta() {
        assertThat(ClienteSoap.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicket") &&
                        m.getReturnType().equals(ResultadoConsulta.class));
    }

    /**
     * Test that interface can be implemented.
     */
    @Test
    void interface_canBeImplemented() {
        ClienteSoap testImpl = new ClienteSoap() {
            @Override
            public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return ResultadoEnvio.asincrono("123");
            }

            @Override
            public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return ResultadoEnvio.asincrono("456");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return ResultadoConsulta.pendiente();
            }
        };

        assertThat(testImpl).isNotNull();
        assertThat(testImpl).isInstanceOf(ClienteSoap.class);
    }

    /**
     * Test that enviarSincrono can be called with all parameters.
     */
    @Test
    void interface_enviarSincronoCanBeCalledWithAllParameters() {
        ClienteSoap testImpl = new ClienteSoap() {
            @Override
            public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                assertThat(xmlFirmado).isNotNull();
                assertThat(nombreArchivo).isNotNull();
                assertThat(endpointUrl).isNotNull();
                assertThat(credenciales).isNotNull();
                return ResultadoEnvio.asincrono("ticket");
            }

            @Override
            public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }
        };

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        ResultadoEnvio result = testImpl.enviarSincrono("<xml/>", "test.xml", "http://test", cred);

        assertThat(result).isNotNull();
    }

    /**
     * Test that enviarAsincrono can be called with all parameters.
     */
    @Test
    void interface_enviarAsincronoCanBeCalledWithAllParameters() {
        ClienteSoap testImpl = new ClienteSoap() {
            @Override
            public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }

            @Override
            public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                assertThat(xmlFirmado).isNotNull();
                assertThat(nombreArchivo).isNotNull();
                assertThat(endpointUrl).isNotNull();
                assertThat(credenciales).isNotNull();
                return ResultadoEnvio.asincrono("ticket");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }
        };

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        ResultadoEnvio result = testImpl.enviarAsincrono("<xml/>", "test.xml", "http://test", cred);

        assertThat(result).isNotNull();
    }

    /**
     * Test that consultarTicket can be called with all parameters.
     */
    @Test
    void interface_consultarTicketCanBeCalledWithAllParameters() {
        ClienteSoap testImpl = new ClienteSoap() {
            @Override
            public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }

            @Override
            public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                assertThat(numeroTicket).isNotNull();
                assertThat(endpointUrl).isNotNull();
                assertThat(credenciales).isNotNull();
                return ResultadoConsulta.pendiente();
            }
        };

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        ResultadoConsulta result = testImpl.consultarTicket("123", "http://test", cred);

        assertThat(result).isNotNull();
    }

    /**
     * Test that separate send methods can be used independently.
     */
    @Test
    void interface_separateSendMethodsCanBeUsedIndependently() {
        ClienteSoap testImpl = new ClienteSoap() {
            @Override
            public ResultadoEnvio enviarSincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return ResultadoEnvio.asincrono("SYNC_123");
            }

            @Override
            public ResultadoEnvio enviarAsincrono(String xmlFirmado, String nombreArchivo, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return ResultadoEnvio.asincrono("ASYNC_456");
            }

            @Override
            public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl,
                    CredencialesEmpresa credenciales) {
                return null;
            }
        };

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        ResultadoEnvio syncResult = testImpl.enviarSincrono("<xml/>", "test.xml", "http://test", cred);
        ResultadoEnvio asyncResult = testImpl.enviarAsincrono("<xml/>", "test.xml", "http://test", cred);

        assertThat(syncResult.numeroTicket()).isEqualTo("SYNC_123");
        assertThat(asyncResult.numeroTicket()).isEqualTo("ASYNC_456");
    }
}
