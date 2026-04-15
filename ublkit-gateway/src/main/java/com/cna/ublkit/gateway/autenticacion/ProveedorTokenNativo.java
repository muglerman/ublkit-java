package com.cna.ublkit.gateway.autenticacion;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.core.error.ExcepcionUblKit;
import com.cna.ublkit.gateway.endpoint.ResolvedorEndpoints;
import com.cna.ublkit.gateway.transporte.HttpClientNativoFactory;

/**
 * Proveedor de Token por defecto que utiliza {@link HttpClient} nativo. Consume el endpoint OAuth2 resuelto por
 * ambiente (`SUNAT` en producción y `gre-test.nubefact.com` para GRE en BETA).
 * <p>
 *
 * @since 0.1.0
 */
public class ProveedorTokenNativo implements ProveedorToken {
    private static final Logger log = Logger.getLogger(ProveedorTokenNativo.class.getName());
    private static final String AUTH_IMPL_REV = "2026-03-31-gre-ct-fix";
    private static final String BETA_SOL_USUARIO = "MODDATOS";
    private static final String BETA_SOL_CLAVE = "MODDATOS";
    private static final String BETA_CREDENTIAL_PREFIX = "test-";

    private static final Pattern PATRON_TOKEN = Pattern.compile("\"access_token\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern PATRON_EXPIRES_IN = Pattern.compile("\"expires_in\"\\s*:\\s*(\\d+)");
    private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String USER_AGENT = "UBLKit/1.0";
    private static final long MARGEN_SEGURIDAD_SEGUNDOS = 30;

    private final HttpClient httpClient;
    private final Map<String, TokenCacheado> cacheTokens;
    private final Duration readTimeout;

    public ProveedorTokenNativo() {
        this(Duration.ofSeconds(10), Duration.ofSeconds(60), 100);
    }

    public ProveedorTokenNativo(Duration connectTimeout, Duration readTimeout) {
        this(connectTimeout, readTimeout, 100);
    }

    public ProveedorTokenNativo(Duration connectTimeout, Duration readTimeout, int maxConnections) {
        this.httpClient = HttpClientNativoFactory.crear(connectTimeout, maxConnections);
        this.cacheTokens = new ConcurrentHashMap<>();
        this.readTimeout = readTimeout;
    }

    @Override
    public String obtenerToken(CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        boolean mostrarSensiblesEnLog = ambiente == TipoAmbiente.BETA;
        CredencialesEmpresa credencialesNormalizadas = normalizarCredencialesParaAmbiente(credenciales, ambiente);
        if (credencialesNormalizadas.clientId() == null || credencialesNormalizadas.clientSecret() == null) {
            throw new ExcepcionUblKit("Faltan credenciales OAuth2 (clientId/clientSecret) para solicitar token REST");
        }

        String claveCache = claveCache(credencialesNormalizadas, ambiente);
        TokenCacheado cacheado = cacheTokens.get(claveCache);
        if (cacheado != null && !cacheado.expirado()) {
            if (log.isLoggable(Level.INFO)) {
                log.info(String.format(
                        "[UBLKIT][TOKEN] cacheHit ambiente=%s, cacheKey=%s, expiraEn=%s, usernameConcatenado=%s",
                        ambiente, mostrarSensiblesEnLog ? claveCache : mask(claveCache), cacheado.expiraEn(),
                        mostrarSensiblesEnLog ? credencialesNormalizadas.getUsernameConcatenado()
                                : mask(credencialesNormalizadas.getUsernameConcatenado())));
            }
            return cacheado.token();
        }

        String url = ResolvedorEndpoints.urlRestToken(ambiente, credencialesNormalizadas.clientId());
        String body = buildUrlEncodedParams(credencialesNormalizadas);
        if (log.isLoggable(Level.INFO)) {
            log.info(String.format(
                    "[UBLKIT][TOKEN] rev=%s, solicitandoToken ambiente=%s, url=%s, contentType=%s, ruc=%s, usuarioSol=%s, usernameConcatenado=%s, clientId=%s, clientSecret=%s, bodyPreview=%s",
                    AUTH_IMPL_REV, ambiente, url, CONTENT_TYPE_FORM_URLENCODED,
                    mostrarSensiblesEnLog ? credencialesNormalizadas.ruc() : mask(credencialesNormalizadas.ruc()),
                    mostrarSensiblesEnLog ? credencialesNormalizadas.usuarioSol()
                            : mask(credencialesNormalizadas.usuarioSol()),
                    mostrarSensiblesEnLog ? credencialesNormalizadas.getUsernameConcatenado()
                            : mask(credencialesNormalizadas.getUsernameConcatenado()),
                    mostrarSensiblesEnLog ? credencialesNormalizadas.clientId()
                            : mask(credencialesNormalizadas.clientId()),
                    mostrarSensiblesEnLog ? credencialesNormalizadas.clientSecret()
                            : mask(credencialesNormalizadas.clientSecret()),
                    mostrarSensiblesEnLog ? body : maskFormBody(body)));
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(readTimeout)
                    .header("Content-Type", CONTENT_TYPE_FORM_URLENCODED)
                    .header("Accept", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (log.isLoggable(Level.INFO)) {
                log.info(String.format("[UBLKIT][TOKEN] respuestaToken status=%s, body=%s", response.statusCode(),
                        mostrarSensiblesEnLog ? response.body() : sanitizeBody(response.body())));
            }

            if (response.statusCode() >= 500) {
                throw new com.cna.ublkit.core.error.ExcepcionTransporte("HTTP_" + response.statusCode() + " - " + response.body());
            }

            if (response.statusCode() != 200) {
                throw new ExcepcionUblKit(
                        "Error al solicitar token. HTTP " + response.statusCode() + ": " + response.body());
            }

            String token = extraerToken(response.body());
            if (token == null) {
                throw new ExcepcionUblKit("La respuesta de autenticación no contiene access_token: " + response.body());
            }

            long expiresIn = extraerExpiresIn(response.body());
            Instant expiraEn = Instant.now().plusSeconds(Math.max(1, expiresIn - MARGEN_SEGURIDAD_SEGUNDOS));
            cacheTokens.put(claveCache, new TokenCacheado(token, expiraEn));
            if (log.isLoggable(Level.INFO)) {
                log.info(String.format("[UBLKIT][TOKEN] tokenObtenido ambiente=%s, expiraEn=%s, tokenMask=%s", ambiente,
                        expiraEn, mostrarSensiblesEnLog ? token : mask(token)));
            }
            return token;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new com.cna.ublkit.core.error.ExcepcionTransporte("Solicitud de token interrumpida", e);
        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (java.io.IOException e) {
            throw new com.cna.ublkit.core.error.ExcepcionTransporte("Error de I/O al solicitar token", e);
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error de red solicitando token a SUNAT: " + e.getMessage(), e);
        }
    }

    private String buildUrlEncodedParams(CredencialesEmpresa cred) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("grant_type", "password");
        params.put("scope", "https://api-cpe.sunat.gob.pe");
        params.put("client_id", cred.clientId());
        params.put("client_secret", cred.clientSecret());
        params.put("username", cred.getUsernameConcatenado());
        params.put("password", cred.claveSol());

        StringBuilder body = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!body.isEmpty()) {
                body.append('&');
            }
            body.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            body.append('=');
            body.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return body.toString();
    }

    private CredencialesEmpresa normalizarCredencialesParaAmbiente(CredencialesEmpresa credenciales,
            TipoAmbiente ambiente) {
        if (ambiente != TipoAmbiente.BETA) {
            return credenciales;
        }

        String clientId = asegurarPrefijoTest(credenciales.clientId());
        String clientSecret = asegurarPrefijoTest(credenciales.clientSecret());
        if (!BETA_SOL_USUARIO.equals(credenciales.usuarioSol()) || !BETA_SOL_CLAVE.equals(credenciales.claveSol())
                || !sameText(clientId, credenciales.clientId())
                || !sameText(clientSecret, credenciales.clientSecret())) {
            log.warning(String.format(
                    "[UBLKIT][TOKEN] BETA detectado: se forzara usuarioSOL=MODDATOS y client_id/client_secret con prefijo test-. ruc=%s, usuarioOriginal=%s, clientIdOriginal=%s, clientIdUsado=%s",
                    credenciales.ruc(), credenciales.usuarioSol(), credenciales.clientId(), clientId));
        }

        return new CredencialesEmpresa(credenciales.ruc(), BETA_SOL_USUARIO, BETA_SOL_CLAVE, clientId, clientSecret);
    }

    private String asegurarPrefijoTest(String value) {
        if (value == null || value.isBlank() || value.startsWith(BETA_CREDENTIAL_PREFIX)) {
            return value;
        }
        return BETA_CREDENTIAL_PREFIX + value;
    }

    private boolean sameText(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }

    private String claveCache(CredencialesEmpresa cred, TipoAmbiente ambiente) {
        return ambiente + "|" + cred.ruc() + "|" + cred.clientId() + "|" + cred.usuarioSol();
    }

    private String extraerToken(String json) {
        Matcher m = PATRON_TOKEN.matcher(json);
        return m.find() ? m.group(1) : null;
    }

    private long extraerExpiresIn(String json) {
        Matcher m = PATRON_EXPIRES_IN.matcher(json);
        if (!m.find())
            return 3600L;
        try {
            return Long.parseLong(m.group(1));
        } catch (NumberFormatException e) {
            return 3600L;
        }
    }

    private record TokenCacheado(String token, Instant expiraEn) {
        boolean expirado() {
            return Instant.now().isAfter(expiraEn);
        }
    }

    private static String sanitizeBody(String body) {
        if (body == null) {
            return null;
        }
        return body.replaceAll("(\"access_token\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3")
                .replaceAll("(\"refresh_token\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3");
    }

    private static String maskFormBody(String body) {
        if (body == null) {
            return null;
        }
        return body.replaceAll("(client_secret=)([^&]+)", "$1***").replaceAll("(password=)([^&]+)", "$1***");
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
