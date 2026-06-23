package com.cna.ublkit.testkit.mock;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimuladorGatewayTest {

    private static final CredencialesEmpresa CREDENCIALES =
            new CredencialesEmpresa("20100070970", "MODDATOS", "moddatos", "cid", "secret");

    @Test
    @DisplayName("retorna respuesta de envio simulada en todos los metodos de envio")
    void debeRetornarRespuestaEnvioSimulada() {
        SimuladorGateway gateway = new SimuladorGateway();
        ResultadoEnvio esperado = ResultadoEnvio.error("500", "Error temporal");
        gateway.simularRespuestaEnvio(esperado);

        assertThat(gateway.enviarComprobante("<xml/>", "a.xml", CREDENCIALES, TipoAmbiente.BETA)).isEqualTo(esperado);
        assertThat(gateway.enviarRetencionPercepcion("<xml/>", "a.xml", CREDENCIALES, TipoAmbiente.BETA)).isEqualTo(esperado);
        assertThat(gateway.enviarResumen("<xml/>", "a.xml", CREDENCIALES, TipoAmbiente.BETA)).isEqualTo(esperado);
        assertThat(gateway.enviarGuiaRemision("<xml/>", "a.xml", CREDENCIALES, TipoAmbiente.BETA)).isEqualTo(esperado);
    }

    @Test
    @DisplayName("retorna pendiente cuando no existe ticket simulado")
    void debeRetornarPendienteCuandoNoExisteTicket() {
        SimuladorGateway gateway = new SimuladorGateway();

        ResultadoConsulta soap = gateway.consultarTicketSoap("T-1", CREDENCIALES, TipoAmbiente.BETA);
        ResultadoConsulta rest = gateway.consultarTicketRest("T-1", CREDENCIALES, TipoAmbiente.BETA);

        assertThat(soap.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
        assertThat(rest.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
    }

    @Test
    @DisplayName("retorna ticket simulado en consultas soap/rest")
    void debeRetornarTicketSimuladoEnConsultasSync() {
        SimuladorGateway gateway = new SimuladorGateway();
        ResultadoConsulta respuesta = ResultadoConsulta.completado(EstadoEnvio.ACEPTADO, null);
        gateway.simularRespuestaTicket("T-OK", respuesta);

        assertThat(gateway.consultarTicketSoap("T-OK", CREDENCIALES, TipoAmbiente.PRODUCCION)).isEqualTo(respuesta);
        assertThat(gateway.consultarTicketRest("T-OK", CREDENCIALES, TipoAmbiente.PRODUCCION)).isEqualTo(respuesta);
    }
}
