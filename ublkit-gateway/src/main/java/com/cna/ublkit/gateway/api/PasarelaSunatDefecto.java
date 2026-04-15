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
import com.cna.ublkit.gateway.config.ConfiguracionGateway;
import java.util.function.Predicate;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import com.cna.ublkit.gateway.respuesta.ArchivoCdr;

/**
 * Implementación por defecto de {@link PasarelaSunat}.
 * Orquesta automáticamente las llamadas utilizando {@link HttpClienteNativoSoap} e {@link HttpClienteNativoRest}.
 *
 * @since 0.1.0
 */
public class PasarelaSunatDefecto implements PasarelaSunat {
    private final int maxIntentos;
    private static final Logger log = Logger.getLogger(PasarelaSunatDefecto.class.getName());
    private static final long POLLING_ESPERA_INICIAL_MS_DEFAULT = 2000L;
    private static final long POLLING_ESPERA_MAX_MS_DEFAULT = 30000L;
    private static final int POLLING_MAX_INTENTOS_DEFAULT = 8;
    private static final ExecutorService POLLING_EXECUTOR = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "ublkit-gre-polling");
        t.setDaemon(true);
        return t;
    });

    private final long pollingEsperaInicialMs;
    private final long pollingEsperaMaxMs;
    private final int pollingMaxIntentos;

    private final ClienteSoap clienteSoap;
    private final ClienteRest clienteRest;
    private final ProveedorToken proveedorToken;

    public PasarelaSunatDefecto() {
        this(ConfiguracionGateway.porDefecto());
    }

    public PasarelaSunatDefecto(ConfiguracionGateway config) {
        this.clienteSoap = new HttpClienteNativoSoap(config.connectTimeout(), config.readTimeout(), config.maxConnections());
        this.clienteRest = new HttpClienteNativoRest(config.connectTimeout(), config.readTimeout(), config.maxConnections());
        this.proveedorToken = new ProveedorTokenNativo(config.connectTimeout(), config.readTimeout(), config.maxConnections());
        this.maxIntentos = config.maxIntentos();
        this.pollingEsperaInicialMs = POLLING_ESPERA_INICIAL_MS_DEFAULT;
        this.pollingEsperaMaxMs = POLLING_ESPERA_MAX_MS_DEFAULT;
        this.pollingMaxIntentos = POLLING_MAX_INTENTOS_DEFAULT;
    }

    public PasarelaSunatDefecto(ClienteSoap clienteSoap, ClienteRest clienteRest, ProveedorToken proveedorToken) {
        this.clienteSoap = clienteSoap;
        this.clienteRest = clienteRest;
        this.proveedorToken = proveedorToken;
        this.maxIntentos = 3;
        this.pollingEsperaInicialMs = POLLING_ESPERA_INICIAL_MS_DEFAULT;
        this.pollingEsperaMaxMs = POLLING_ESPERA_MAX_MS_DEFAULT;
        this.pollingMaxIntentos = POLLING_MAX_INTENTOS_DEFAULT;
    }

    public PasarelaSunatDefecto(ClienteSoap clienteSoap, ClienteRest clienteRest, ProveedorToken proveedorToken, ConfiguracionGateway config) {
        this.clienteSoap = clienteSoap;
        this.clienteRest = clienteRest;
        this.proveedorToken = proveedorToken;
        this.maxIntentos = config.maxIntentos();
        this.pollingEsperaInicialMs = POLLING_ESPERA_INICIAL_MS_DEFAULT;
        this.pollingEsperaMaxMs = POLLING_ESPERA_MAX_MS_DEFAULT;
        this.pollingMaxIntentos = POLLING_MAX_INTENTOS_DEFAULT;
    }

    PasarelaSunatDefecto(
            ClienteSoap clienteSoap,
            ClienteRest clienteRest,
            ProveedorToken proveedorToken,
            ConfiguracionGateway config,
            long pollingEsperaInicialMs,
            long pollingEsperaMaxMs,
            int pollingMaxIntentos
    ) {
        this.clienteSoap = clienteSoap;
        this.clienteRest = clienteRest;
        this.proveedorToken = proveedorToken;
        this.maxIntentos = config.maxIntentos();
        this.pollingEsperaInicialMs = pollingEsperaInicialMs;
        this.pollingEsperaMaxMs = pollingEsperaMaxMs;
        this.pollingMaxIntentos = pollingMaxIntentos;
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
        String token = proveedorToken.obtenerToken(credenciales, ambiente);
        String urlBase = ResolvedorEndpoints.urlRestEnvio(ambiente);
        String nameWithoutExtension = nombreArchivo.replace(".xml", "");
        String finalEndpoint = urlBase.endsWith("/") ? urlBase + nameWithoutExtension : urlBase + "/" + nameWithoutExtension;
        if (log.isLoggable(Level.INFO)) {
            log.info(String.format(
                    "[UBLKIT][GRE] ambiente=%s, archivo=%s, urlBase=%s, finalEndpoint=%s, usernameConcatenado=%s, tokenMask=%s",
                    ambiente, nombreArchivo, urlBase, finalEndpoint, mask(credenciales.getUsernameConcatenado()), mask(token)));
        }
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
        if (log.isLoggable(Level.INFO)) {
            log.info(String.format(
                    "[UBLKIT][GRE] ambiente=%s, ticket=%s, urlBase=%s, finalEndpoint=%s, usernameConcatenado=%s, tokenMask=%s",
                    ambiente, ticket, urlBase, finalEndpoint + ticket, mask(credenciales.getUsernameConcatenado()), mask(token)));
        }
        return conRetry(() -> clienteRest.consultarTicket(ticket, finalEndpoint, token));
    }

    @Override
    public CompletableFuture<ArchivoCdr> enviarGuiaRemisionYEsperar(
            String xmlFirmado,
            String nombreArchivo,
            CredencialesEmpresa credenciales,
            TipoAmbiente ambiente
    ) {
        return CompletableFuture.supplyAsync(
                () -> enviarGuiaRemision(xmlFirmado, nombreArchivo, credenciales, ambiente),
                POLLING_EXECUTOR
        ).thenCompose(resultadoEnvio -> {
            if (resultadoEnvio == null) {
                return CompletableFuture.failedFuture(new IllegalStateException("No se obtuvo resultado de envío GRE"));
            }
            if (resultadoEnvio.estado() == EstadoEnvio.EXCEPCION) {
                return CompletableFuture.failedFuture(new IllegalStateException(
                        "Error al enviar GRE: " + resultadoEnvio.codigoError() + " - " + resultadoEnvio.mensajeError()
                ));
            }
            if (resultadoEnvio.numeroTicket() == null || resultadoEnvio.numeroTicket().isBlank()) {
                return CompletableFuture.failedFuture(new IllegalStateException("SUNAT no retornó ticket para GRE"));
            }

            return pollGreHastaFinal(
                    resultadoEnvio.numeroTicket(),
                    credenciales,
                    ambiente,
                    1,
                    pollingEsperaInicialMs
            );
        });
    }

    private CompletableFuture<ArchivoCdr> pollGreHastaFinal(
            String ticket,
            CredencialesEmpresa credenciales,
            TipoAmbiente ambiente,
            int intento,
            long esperaMs
    ) {
        return CompletableFuture.supplyAsync(
                () -> consultarTicketRest(ticket, credenciales, ambiente),
                CompletableFuture.delayedExecutor(esperaMs, TimeUnit.MILLISECONDS, POLLING_EXECUTOR)
        ).thenCompose(resultado -> {
            if (resultado == null) {
                return CompletableFuture.failedFuture(new IllegalStateException("Consulta de ticket GRE retornó null"));
            }

            EstadoEnvio estado = resultado.estado();
            if (estado == EstadoEnvio.ACEPTADO || estado == EstadoEnvio.ACEPTADO_CON_OBSERVACIONES || estado == EstadoEnvio.RECHAZADO) {
                if (resultado.cdr() == null) {
                    return CompletableFuture.failedFuture(new IllegalStateException(
                            "Ticket GRE finalizado sin CDR. Estado: " + estado
                    ));
                }
                return CompletableFuture.completedFuture(resultado.cdr());
            }

            if (estado == EstadoEnvio.EXCEPCION) {
                return CompletableFuture.failedFuture(new IllegalStateException(
                        "Error al consultar ticket GRE: " + resultado.codigoError() + " - " + resultado.mensajeError()
                ));
            }

            if (estado == EstadoEnvio.EN_PROCESAMIENTO || estado == EstadoEnvio.TICKET_PENDIENTE) {
                if (intento >= pollingMaxIntentos) {
                    return CompletableFuture.failedFuture(new IllegalStateException(
                            "Tiempo de espera agotado consultando ticket GRE " + ticket
                    ));
                }
                long siguienteEspera = siguienteBackoff(esperaMs);
                return pollGreHastaFinal(ticket, credenciales, ambiente, intento + 1, siguienteEspera);
            }

            return CompletableFuture.failedFuture(new IllegalStateException("Estado de ticket GRE no manejado: " + estado));
        });
    }

    private long siguienteBackoff(long esperaActualMs) {
        if (esperaActualMs < 5000L) {
            return 5000L;
        }
        return Math.min(esperaActualMs * 2L, pollingEsperaMaxMs);
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
        T ultimoResultado = null;
        RuntimeException ultima = null;
        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                T resultado = accion.get();
                if (resultado instanceof ResultadoEnvio r) {
                    if (r.estado() == EstadoEnvio.EXCEPCION && esErrorTemporal(r.codigoError())) {
                        ultimoResultado = resultado;
                        if (intento == maxIntentos) break;
                        dormirBackoff(intento);
                        continue;
                    }
                } else if (resultado instanceof ResultadoConsulta r) {
                    if (r.estado() == EstadoEnvio.EXCEPCION && esErrorTemporal(r.codigoError())) {
                        ultimoResultado = resultado;
                        if (intento == maxIntentos) break;
                        dormirBackoff(intento);
                        continue;
                    }
                }
                return resultado;
            } catch (com.cna.ublkit.core.error.ExcepcionTransporte e) {
                ultima = e;
                if (intento == maxIntentos) break;
                dormirBackoff(intento);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        if (ultimoResultado != null) {
            return ultimoResultado;
        }
        throw ultima != null ? ultima : new RuntimeException("No se pudo completar la operación");
    }

    private boolean esErrorTemporal(String codigoError) {
        return "IO_ERROR".equals(codigoError) || "HTTP_5XX".equals(codigoError);
    }

    private void dormirBackoff(int intento) {
        try {
            long esperaMs = (long) Math.pow(2D, intento - 1D) * 250L;
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
