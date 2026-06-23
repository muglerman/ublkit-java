package com.creanexusatreus.ublkit.testkit.mock;

import com.creanexusatreus.ublkit.core.enumerado.TipoAmbiente;
import com.creanexusatreus.ublkit.gateway.api.PasarelaSunat;
import com.creanexusatreus.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.creanexusatreus.ublkit.gateway.respuesta.EstadoEnvio;
import com.creanexusatreus.ublkit.gateway.respuesta.ResultadoConsulta;
import com.creanexusatreus.ublkit.gateway.respuesta.ResultadoEnvio;

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
    public java.util.concurrent.CompletableFuture<com.creanexusatreus.ublkit.gateway.respuesta.ArchivoCdr> enviarGuiaRemisionYEsperar(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return java.util.concurrent.CompletableFuture.completedFuture(
                new com.creanexusatreus.ublkit.gateway.respuesta.ArchivoCdr(new byte[0], "0", "Aceptado", java.util.List.of())
        );
    }

    @Override
    public ResultadoConsulta consultarTicketSoap(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestasTickets.getOrDefault(ticket, ResultadoConsulta.pendiente());
    }

    @Override
    public ResultadoConsulta consultarTicketRest(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        return respuestasTickets.getOrDefault(ticket, ResultadoConsulta.pendiente());
    }
}
