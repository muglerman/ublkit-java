package com.cna.ublkit.gateway.autenticacion;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.core.error.ExcepcionUblKit;
import com.cna.ublkit.gateway.endpoint.ResolvedorEndpoints;

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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Proveedor de Token por defecto que utiliza {@link HttpClient} nativo.
 * Consume el endpoint OAuth2 resuelto por ambiente
 * (`SUNAT` en producción y `gre-test.nubefact.com` para GRE en BETA).
 * <p>
 * @since 0.1.0
 */
public class ProveedorTokenNativo implements ProveedorToken {
    private static final Logger log = Logger.getLogger(ProveedorTokenNativo.class.getName());

    private static final Pattern PATRON_TOKEN = Pattern.compile("\"access_token\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern PATRON_EXPIRES_IN = Pattern.compile("\"expires_in\"\\s*:\\s*(\\d+)");
    private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String USER_AGENT = "UBLKit/1.0";
    private static final long MARGEN_SEGURIDAD_SEGUNDOS = 30;

    private final HttpClient httpClient;
    private final Map<String, TokenCacheado> cacheTokens;

    public ProveedorTokenNativo() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.cacheTokens = new ConcurrentHashMap<>();
    }

    @Override
    public String obtenerToken(CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
        if (credenciales.clientId() == null || credenciales.clientSecret() == null) {
            throw new ExcepcionUblKit("Faltan credenciales OAuth2 (clientId/clientSecret) para solicitar token REST");
        }

        String claveCache = claveCache(credenciales, ambiente);
        TokenCacheado cacheado = cacheTokens.get(claveCache);
        if (cacheado != null && !cacheado.expirado()) {
            log.info(String.format(
                    "[UBLKIT][TOKEN] cacheHit ambiente=%s, cacheKey=%s, expiraEn=%s, usernameConcatenado=%s",
                    ambiente, mask(claveCache), cacheado.expiraEn(), mask(credenciales.getUsernameConcatenado())));
            return cacheado.token();
        }

        String url = ResolvedorEndpoints.urlRestToken(ambiente, credenciales.clientId());
        String body = buildUrlEncodedParams(credenciales);
        log.info(String.format(
                "[UBLKIT][TOKEN] solicitandoToken ambiente=%s, url=%s, contentType=%s, ruc=%s, usuarioSol=%s, usernameConcatenado=%s, clientId=%s, clientSecret=%s, bodyPreview=%s",
                ambiente,
                url,
                CONTENT_TYPE_FORM_URLENCODED,
                mask(credenciales.ruc()),
                mask(credenciales.usuarioSol()),
                mask(credenciales.getUsernameConcatenado()),
                mask(credenciales.clientId()),
                mask(credenciales.clientSecret()),
                maskFormBody(body)));

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", CONTENT_TYPE_FORM_URLENCODED)
                    .header("Accept", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.format("[UBLKIT][TOKEN] respuestaToken status=%s, body=%s",
                    response.statusCode(), sanitizeBody(response.body())));
            
            if (response.statusCode() != 200) {
                throw new ExcepcionUblKit("Error al solicitar token. HTTP " + response.statusCode() + ": " + response.body());
            }

            String token = extraerToken(response.body());
            if (token == null) {
                throw new ExcepcionUblKit("La respuesta de autenticación no contiene access_token: " + response.body());
            }

            long expiresIn = extraerExpiresIn(response.body());
            Instant expiraEn = Instant.now().plusSeconds(Math.max(1, expiresIn - MARGEN_SEGURIDAD_SEGUNDOS));
            cacheTokens.put(claveCache, new TokenCacheado(token, expiraEn));
            log.info(String.format("[UBLKIT][TOKEN] tokenObtenido ambiente=%s, expiraEn=%s, tokenMask=%s",
                    ambiente, expiraEn, mask(token)));
            return token;

        } catch (ExcepcionUblKit e) {
            throw e;
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

    private String claveCache(CredencialesEmpresa cred, TipoAmbiente ambiente) {
        return ambiente + "|" + cred.ruc() + "|" + cred.clientId() + "|" + cred.usuarioSol();
    }

    private String extraerToken(String json) {
        Matcher m = PATRON_TOKEN.matcher(json);
        return m.find() ? m.group(1) : null;
    }

    private long extraerExpiresIn(String json) {
        Matcher m = PATRON_EXPIRES_IN.matcher(json);
        if (!m.find()) return 3600L;
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
        return body
                .replaceAll("(\"access_token\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3")
                .replaceAll("(\"refresh_token\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3");
    }

    private static String maskFormBody(String body) {
        if (body == null) {
            return null;
        }
        return body
                .replaceAll("(client_secret=)([^&]+)", "$1***")
                .replaceAll("(password=)([^&]+)", "$1***");
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
