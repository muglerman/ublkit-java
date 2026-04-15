package com.cna.ublkit.testkit.mock;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

import java.util.concurrent.CompletableFuture;
import java.util.HashMap;
import java.util.Map;

/**
 * Simulación de {@link PasarelaSunat} para pruebas unitarias de la capa de aplicación.
 * <p>
 * Permite pre-configurar respuestas exitosas o de error sin realizar peticiones
 * reales a SUNAT u OSE.
 * </p>
 *
 * @since 0.1.0
 */
public class SimuladorGateway implements PasarelaSunat {

    private ResultadoEnvio respuestaEnvio = ResultadoEnvio.sincronoProcesado(EstadoEnvio.ACEPTADO, null);
    private final Map<String, ResultadoConsulta> respuestasTickets = new HashMap<>();

    /**
     * Configura la respuesta que devolverán los métodos de envío.
     *
     * @param respuesta respuesta simulada
     */
    public void simularRespuestaEnvio(ResultadoEnvio respuesta) {
        this.respuestaEnvio = respuesta;
    }

    /**
     * Configura la respuesta para una consulta de ticket específica.
     *
     * @param ticket ID del ticket
     * @param respuesta respuesta simulada
     */
    public void simularRespuestaTicket(String ticket, ResultadoConsulta respuesta) {
        this.respuestasTickets.put(ticket, respuesta);
    }

    @Override
    public ResultadoEnvio enviarComprobante(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestaEnvio;
    }

    @Override
    public ResultadoEnvio enviarRetencionPercepcion(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestaEnvio;
    }

    @Override
    public ResultadoEnvio enviarResumen(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestaEnvio;
    }

    @Override
    public ResultadoEnvio enviarGuiaRemision(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestaEnvio;
    }

    @Override
    public ResultadoConsulta consultarTicketSoap(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestasTickets.getOrDefault(ticket, ResultadoConsulta.pendiente());
    }

    @Override
    public ResultadoConsulta consultarTicketRest(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestasTickets.getOrDefault(ticket, ResultadoConsulta.pendiente());
    }

    @Override
    public CompletableFuture<ResultadoConsulta> consultarTicketAsincrono(String ticket,
                                                                         CredencialesEmpresa credenciales,
                                                                         TipoAmbiente ambiente,
                                                                         boolean esRest) {
        ResultadoConsulta resultado = esRest
                ? consultarTicketRest(ticket, credenciales, ambiente)
                : consultarTicketSoap(ticket, credenciales, ambiente);
        return CompletableFuture.completedFuture(resultado);
    }
}
