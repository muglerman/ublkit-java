package com.cna.ublkit.gateway.api;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.autenticacion.ProveedorToken;
import com.cna.ublkit.gateway.autenticacion.ProveedorTokenNativo;
import com.cna.ublkit.gateway.endpoint.ResolvedorEndpoints;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import com.cna.ublkit.gateway.transporte.ClienteRest;
import com.cna.ublkit.gateway.transporte.ClienteSoap;
import com.cna.ublkit.gateway.transporte.HttpClienteNativoRest;
import com.cna.ublkit.gateway.transporte.HttpClienteNativoSoap;

import java.util.logging.Logger;
import java.util.function.Supplier;

/**
 * Implementación por defecto de {@link PasarelaSunat}.
 * Orquesta automáticamente las llamadas utilizando {@link HttpClienteNativoSoap} e {@link HttpClienteNativoRest}.
 *
 * @since 0.1.0
 */
public class PasarelaSunatDefecto implements PasarelaSunat {
    private static final int MAX_INTENTOS = 3;
    private static final Logger log = Logger.getLogger(PasarelaSunatDefecto.class.getName());

    private final ClienteSoap clienteSoap;
    private final ClienteRest clienteRest;
    private final ProveedorToken proveedorToken;

    public PasarelaSunatDefecto() {
        this.clienteSoap = new HttpClienteNativoSoap();
        this.clienteRest = new HttpClienteNativoRest();
        this.proveedorToken = new ProveedorTokenNativo();
    }

    public PasarelaSunatDefecto(ClienteSoap clienteSoap, ClienteRest clienteRest, ProveedorToken proveedorToken) {
        this.clienteSoap = clienteSoap;
        this.clienteRest = clienteRest;
        this.proveedorToken = proveedorToken;
    }

    @Override
    public ResultadoEnvio enviarComprobante(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        String raiz = detectarRaizXml(xmlFirmado);
        if ("SummaryDocuments".equals(raiz) || "VoidedDocuments".equals(raiz)) {
            String endpoint = ResolvedorEndpoints.urlSoapFactura(ambiente);
            return conRetry(() -> clienteSoap.enviarAsincrono(xmlFirmado, nombreArchivo, endpoint, credenciales));
        }
        if ("DespatchAdvice".equals(raiz)) {
            return enviarGuiaRemision(xmlFirmado, nombreArchivo, credenciales, ambiente);
        }

        String endpoint = ResolvedorEndpoints.urlSoapFactura(ambiente);
        return conRetry(() -> clienteSoap.enviarSincrono(xmlFirmado, nombreArchivo, endpoint, credenciales));
    }

    @Override
    public ResultadoEnvio enviarRetencionPercepcion(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        String endpoint = ResolvedorEndpoints.urlSoapRetencion(ambiente);
        return conRetry(() -> clienteSoap.enviarSincrono(xmlFirmado, nombreArchivo, endpoint, credenciales));
    }

    @Override
    public ResultadoEnvio enviarResumen(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        String endpoint = ResolvedorEndpoints.urlSoapFactura(ambiente);
        return conRetry(() -> clienteSoap.enviarAsincrono(xmlFirmado, nombreArchivo, endpoint, credenciales));
    }

    @Override
    public ResultadoEnvio enviarGuiaRemision(String xmlFirmado, String nombreArchivo, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        // 1. Obtener Token
        String token = proveedorToken.obtenerToken(credenciales, ambiente);
        
        // 2. Resolver Endpoint y armar con nombre de archivo
        // El API REST de SUNAT requiere agregar {numRucEmisor}-{codCpe}-{numSerie}-{numCpe} al final de la URL
        String urlBase = ResolvedorEndpoints.urlRestEnvio(ambiente);
        String nameWithoutExtension = nombreArchivo.replace(".xml", "");
        String finalEndpoint = urlBase.endsWith("/") ? urlBase + nameWithoutExtension : urlBase + "/" + nameWithoutExtension;
        log.info(String.format(
                "[UBLKIT][GRE] ambiente=%s, archivo=%s, urlBase=%s, finalEndpoint=%s, usernameConcatenado=%s, tokenMask=%s",
                ambiente, nombreArchivo, urlBase, finalEndpoint, mask(credenciales.getUsernameConcatenado()), mask(token)));

        // 3. Enviar
        return conRetry(() -> clienteRest.enviarGuia(xmlFirmado, nombreArchivo, finalEndpoint, token));
    }

    @Override
    public ResultadoConsulta consultarTicketSoap(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        String endpoint = ResolvedorEndpoints.urlSoapConsulta(ambiente);
        return conRetry(() -> clienteSoap.consultarTicket(ticket, endpoint, credenciales));
    }

    @Override
    public ResultadoConsulta consultarTicketRest(String ticket, CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        String token = proveedorToken.obtenerToken(credenciales, ambiente);
        String urlBase = ResolvedorEndpoints.urlRestTicket(ambiente);
        String finalEndpoint = urlBase.endsWith("/") ? urlBase : urlBase + "/";
        log.info(String.format(
                "[UBLKIT][GRE] ambiente=%s, ticket=%s, urlBase=%s, finalEndpoint=%s, usernameConcatenado=%s, tokenMask=%s",
                ambiente, ticket, urlBase, finalEndpoint + ticket, mask(credenciales.getUsernameConcatenado()), mask(token)));
        return conRetry(() -> clienteRest.consultarTicket(ticket, finalEndpoint, token));
    }

    private String detectarRaizXml(String xml) {
        if (xml == null) return "";
        String limpio = xml.replaceFirst("^\\s*<\\?xml[^>]*\\?>\\s*", "").trim();
        int ini = limpio.indexOf('<');
        if (ini < 0) return "";
        int fin = limpio.indexOf('>', ini + 1);
        if (fin < 0) return "";
        String tag = limpio.substring(ini + 1, fin).trim();
        if (tag.startsWith("/")) return "";
        tag = tag.split("\\s+")[0];
        int ns = tag.indexOf(':');
        return ns >= 0 ? tag.substring(ns + 1) : tag;
    }

    private <T> T conRetry(Supplier<T> accion) {
        RuntimeException ultima = null;
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            try {
                return accion.get();
            } catch (RuntimeException e) {
                ultima = e;
                if (intento == MAX_INTENTOS) break;
                dormirBackoff(intento);
            }
        }
        throw ultima != null ? ultima : new RuntimeException("No se pudo completar la operación");
    }

    private void dormirBackoff(int intento) {
        try {
            long esperaMs = (long) Math.pow(2, intento - 1) * 250L;
            Thread.sleep(esperaMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String mask(String value) {
        if (value == null || value.isBlank()) {
            return "null";
        }
        if (value.length() <= 8) {
            return "***";
        }
        return value.substring(0, 4) + "***" + value.substring(value.length() - 4);
    }
}
