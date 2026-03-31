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
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementación de {@link ClienteRest} utilizando {@link HttpClient} nativo.
 * Se encarga de enviar las Guías de Remisión (GRE) como JSON con hash SHA-256.
 *
 * @since 0.1.0
 */
public class HttpClienteNativoRest implements ClienteRest {
    private static final Logger log = Logger.getLogger(HttpClienteNativoRest.class.getName());
    private static final String USER_AGENT = "UBLKit/1.0";
    private static final Map<String, String> SUNAT_GRE_ERROR_DESCRIPTIONS = Map.ofEntries(
            // Ticket / procesamiento (documentación SUNAT CódigosRetorno)
            Map.entry("0126", "El ticket no le pertenece al usuario"),
            Map.entry("0127", "El ticket no existe"),
            Map.entry("0130", "No se pudo obtener el ticket de proceso"),
            Map.entry("0155", "El archivo ZIP está vacío"),
            Map.entry("0156", "El archivo ZIP está corrupto"),
            Map.entry("0250", "Error desconocido al hacer unzip"),
            // GRE validaciones frecuentes
            Map.entry("3383", "Debe consignar el número de documento de identidad del remitente"),
            Map.entry("3384", "El número de documento de identidad del remitente no cumple el formato"),
            Map.entry("3385", "El número de RUC del remitente no existe"),
            Map.entry("3386", "El número de DNI del remitente no existe"),
            Map.entry("3387", "Debe consignar el nombre o razón social del remitente"),
            Map.entry("3433", "La GRE remitente no existe"),
            Map.entry("3434", "Destinatario no coincide con la GRE remitente relacionada"));

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
            log.info(String.format(
                    "[UBLKIT][REST] enviarGuia endpoint=%s, archivoXml=%s, archivoZip=%s, hashZip=%s, zipBytes=%s, tokenMask=%s",
                    endpointUrl, nombreArchivo, nombreZip, hashZip, zipBytes.length, mask(tokenBearer)));

            // 3. Ejecutar POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .header("Authorization", "Bearer " + tokenBearer)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.format("[UBLKIT][REST] enviarGuia status=%s, body=%s",
                    response.statusCode(), sanitizeBody(response.body())));

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
            log.info(String.format("[UBLKIT][REST] consultarTicket endpoint=%s, ticket=%s, tokenMask=%s",
                    endpointUrl + numeroTicket, numeroTicket, mask(tokenBearer)));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl + numeroTicket))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .header("Authorization", "Bearer " + tokenBearer)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.format("[UBLKIT][REST] consultarTicket status=%s, body=%s",
                    response.statusCode(), sanitizeBody(response.body())));

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
                String errorMsg = construirMensajeErrorTicket(response.body(), codRespuesta, indCdrGenerado, arcCdr);
                return ResultadoConsulta.error(codRespuesta, errorMsg);
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

    private static String sanitizeBody(String body) {
        if (body == null) {
            return null;
        }
        return body.replaceAll("(\"arcCdr\"\\s*:\\s*\")([^\"]+)(\")", "$1***$3");
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

    private String construirMensajeErrorTicket(String body, String codRespuesta, String indCdrGenerado, String arcCdr) {
        String msgError = extraerCampoJson(body, "msgError");
        if (msgError != null && !msgError.isBlank()) {
            return msgError;
        }

        String numError = extraerCampoJson(body, "numError");
        String desError = extraerCampoJson(body, "desError");
        String descripcionCatalogo = numError != null ? SUNAT_GRE_ERROR_DESCRIPTIONS.get(numError) : null;
        String sugerencia = construirSugerencia(numError);

        StringBuilder message = new StringBuilder();
        message.append("Consulta ticket GRE sin CDR");
        if (codRespuesta != null) {
            message.append(" (codRespuesta=").append(codRespuesta).append(")");
        }
        if (numError != null && !numError.isBlank()) {
            message.append(", numError=").append(numError);
        }
        if (descripcionCatalogo != null) {
            message.append(", sunatCatalogo=").append(descripcionCatalogo);
        }
        if (desError != null && !desError.isBlank()) {
            message.append(", desError=").append(desError);
        }
        if (indCdrGenerado != null) {
            message.append(", indCdrGenerado=").append(indCdrGenerado);
        }
        message.append(", cdrPresente=").append(arcCdr != null);

        if ("3383".equals(numError)) {
            message.append(" [SUNAT informó estado sin CDR definitivo]");
        }
        if (sugerencia != null) {
            message.append(" | accionSugerida=").append(sugerencia);
        }
        return message.toString();
    }

    private String construirSugerencia(String numError) {
        if (numError == null || numError.isBlank()) {
            return null;
        }
        return switch (numError) {
            case "0126" -> "Verificar credenciales SOL/OAuth y RUC emisor del ticket";
            case "0127" -> "Confirmar ticket, ambiente y endpoint; podría ser ticket inválido o expirado";
            case "0130" -> "Reintentar consulta; si persiste, escalar como incidencia SUNAT";
            case "0155", "0156", "0250" -> "Revisar ZIP/XML enviado y volver a enviar la GRE";
            case "3383", "3384", "3385", "3386", "3387" ->
                    "Corregir datos del remitente en la GRE (tipo/número/nombre) y emitir nueva guía";
            case "3433", "3434" ->
                    "Validar documento relacionado GRE remitente y consistencia de datos del destinatario";
            default -> null;
        };
    }
}
