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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Proveedor de Token por defecto que utiliza {@link HttpClient} nativo.
 * Consume el API de seguridad de SUNAT (/v1/clientessol/{client_id}/oauth2/token).
 * <p>
 * @since 0.1.0
 */
public class ProveedorTokenNativo implements ProveedorToken {

    private static final Pattern PATRON_TOKEN = Pattern.compile("\"access_token\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern PATRON_EXPIRES_IN = Pattern.compile("\"expires_in\"\\s*:\\s*(\\d+)");
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
            return cacheado.token();
        }

        String url = ResolvedorEndpoints.urlRestToken(ambiente, credenciales.clientId());
        String body = buildUrlEncodedParams(credenciales);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() != 200) {
                throw new ExcepcionUblKit("Error al solicitar token. HTTP " + response.statusCode() + ": " + response.body());
            }

            String token = extraerToken(response.body());
            if (token == null) {
                throw new ExcepcionUblKit("El token no se encontró en la respuesta JSON: " + response.body());
            }

            long expiresIn = extraerExpiresIn(response.body());
            Instant expiraEn = Instant.now().plusSeconds(Math.max(1, expiresIn - MARGEN_SEGURIDAD_SEGUNDOS));
            cacheTokens.put(claveCache, new TokenCacheado(token, expiraEn));
            return token;

        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error de red solicitando token a SUNAT: " + e.getMessage(), e);
        }
    }

    private String buildUrlEncodedParams(CredencialesEmpresa cred) {
        String username = URLEncoder.encode(cred.getUsernameConcatenado(), StandardCharsets.UTF_8);
        String password = URLEncoder.encode(cred.claveSol(), StandardCharsets.UTF_8);
        String clientId = URLEncoder.encode(cred.clientId(), StandardCharsets.UTF_8);
        String clientSecret = URLEncoder.encode(cred.clientSecret(), StandardCharsets.UTF_8);

        return "grant_type=password" +
               "&scope=https://api-cpe.sunat.gob.pe" +
               "&client_id=" + clientId +
               "&client_secret=" + clientSecret +
               "&username=" + username +
               "&password=" + password;
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
}
