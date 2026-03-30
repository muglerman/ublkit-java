package com.cna.ublkit.gateway.transporte;

import com.cna.ublkit.gateway.api.HashHelper;
import com.cna.ublkit.gateway.api.ZipHelper;
import com.cna.ublkit.gateway.respuesta.ArchivoCdr;
import com.cna.ublkit.gateway.respuesta.EstadoEnvio;
import com.cna.ublkit.gateway.respuesta.LectorCdr;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementación de {@link ClienteRest} utilizando {@link HttpClient} nativo.
 * Se encarga de enviar las Guías de Remisión (GRE) como JSON con hash SHA-256.
 *
 * @since 0.1.0
 */
public class HttpClienteNativoRest implements ClienteRest {

    private final HttpClient httpClient;

    public HttpClienteNativoRest() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public ResultadoEnvio enviarGuia(String xmlFirmado, String nombreArchivo, String endpointUrl, String tokenBearer) {
        try {
            // 1. Zipear y obtener hash
            byte[] zipBytes = ZipHelper.comprimir(xmlFirmado, nombreArchivo);
            String base64Zip = Base64.getEncoder().encodeToString(zipBytes);
            String hashZip = HashHelper.sha256Hex(zipBytes);
            String nombreZip = nombreArchivo.replace(".xml", ".zip");

            // 2. Construir payload JSON
            String jsonPayload = """
                    {
                        "archivo": {
                            "nomArchivo": "%s",
                            "hashZip": "%s",
                            "arcGreZip": "%s"
                        }
                    }
                    """.formatted(nombreZip, hashZip, base64Zip);

            // 3. Ejecutar POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + tokenBearer)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String ticket = extraerCampoJson(response.body(), "numTicket");
                if (ticket != null) {
                    return ResultadoEnvio.asincrono(ticket);
                }
            }

            // Tratamiento de fallos
            String codError = extraerCampoJson(response.body(), "codError");
            String msgError = extraerCampoJson(response.body(), "msgError");
            return ResultadoEnvio.error(codError != null ? codError : String.valueOf(response.statusCode()),
                                        msgError != null ? msgError : response.body());

        } catch (Exception e) {
            return ResultadoEnvio.error("IO_ERROR", e.getMessage());
        }
    }

    @Override
    public ResultadoConsulta consultarTicket(String numeroTicket, String endpointUrl, String tokenBearer) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl + numeroTicket))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + tokenBearer)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Posibles estados: 0=Aprobado, 98=En Proceso, 99=Error
                String codRespuesta = extraerCampoJson(response.body(), "codRespuesta");
                String indCdrGenerado = extraerCampoJson(response.body(), "indCdrGenerado");

                if ("98".equals(codRespuesta)) {
                    return ResultadoConsulta.pendiente();
                }

                if ("1".equals(indCdrGenerado)) {
                    String arcCdr = extraerCampoJson(response.body(), "arcCdr");
                    if (arcCdr != null) {
                        byte[] cdrBytes = Base64.getDecoder().decode(arcCdr);
                        ArchivoCdr cdr = LectorCdr.extraer(cdrBytes);
                        EstadoEnvio estado = LectorCdr.determinarEstado(cdr);
                        return ResultadoConsulta.completado(estado, cdr);
                    }
                }

                // Error de negocio
                String arcCdr = extraerCampoJson(response.body(), "arcCdr");
                String errorMsg = extraerCampoJson(response.body(), "msgError");
                return ResultadoConsulta.error(codRespuesta, errorMsg != null ? errorMsg : "Sin detalles. CDR Presente: " + (arcCdr != null));
            }

            return ResultadoConsulta.error(String.valueOf(response.statusCode()), response.body());

        } catch (Exception e) {
            return ResultadoConsulta.error("IO_ERROR", e.getMessage());
        }
    }

    private String extraerCampoJson(String json, String nombreCampo) {
        Pattern p = Pattern.compile("\"" + nombreCampo + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
}
