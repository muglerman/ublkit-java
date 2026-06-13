package com.cna.ublkit.render.pebble;

import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.modelo.EntradaCatalogo;
import com.cna.ublkit.catalogs.sunat.LectorCsvCatalogos;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Extensiones de Pebble con compatibilidad para los templates de UBLKit.
 *
 * @since 0.3.0
 */
public final class UblkitPebbleExtension extends AbstractExtension {

    private static final List<String> DATE_ARGUMENTS = List.of("format");
    private static final List<String> NUMBER_ARGUMENTS = List.of();
    private static final List<String> PAD_ARGUMENTS = List.of("width", "pad", "side");
    private static final List<String> CATALOG_ARGUMENTS = List.of("catalogo");
    private static final List<String> CATALOG_ATTR_ARGUMENTS = List.of("catalogo", "clave");
    private static final List<String> IMAGE_ARGUMENTS = List.of("mimeType");

    private final ProveedorCatalogos proveedorCatalogos = new LectorCsvCatalogos();

    @Override
    public Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("date", new TwigDateFilter());
        filters.put("n_format", new NumberFormatFilter());
        filters.put("q_format", new QuantityFormatFilter());
        filters.put("str_pad", new StringPadFilter());
        filters.put("catalog", new CatalogFilter());
        filters.put("catalogAttr", new CatalogAttrFilter());
        filters.put("image_b64", new ImageBase64Filter());
        return filters;
    }

    private static final class TwigDateFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return DATE_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
            String format = stringArg(args, "format", "dd/MM/yyyy");
            if (input == null) {
                return "";
            }

            if (input instanceof LocalDateTime localDateTime) {
                return formatDateTime(localDateTime, format);
            }
            if (input instanceof LocalDate localDate) {
                return formatDate(localDate, format);
            }
            if (input instanceof LocalTime localTime) {
                return formatTime(localTime, format);
            }
            if (input instanceof Instant instant) {
                return formatDateTime(instant.atZone(ZoneId.systemDefault()).toLocalDateTime(), format);
            }
            if (input instanceof java.util.Date date) {
                return formatDateTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), format);
            }
            return input.toString();
        }

        private String formatDate(LocalDate date, String format) {
            if (wantsTime(format)) {
                return date.atStartOfDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            }
            return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        private String formatTime(LocalTime time, String format) {
            if (wantsDate(format)) {
                return LocalDate.of(1970, 1, 1).atTime(time).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            }
            return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        private String formatDateTime(LocalDateTime dateTime, String format) {
            if (wantsDate(format) && wantsTime(format)) {
                return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            }
            if (wantsTime(format)) {
                return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
            return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        private boolean wantsDate(String format) {
            // OJO: no usar la 'm' minúscula como indicador de fecha: en los formatos de hora Java
            // (HH:mm, HH:mm:ss) la 'm' son los minutos, y al confundirla con "mes" el filtro
            // devolvía fecha+hora completa → fecha duplicada en el PDF. La fecha se detecta por
            // día (d/D), mes Java (M) o año (Y/y); los formatos de fecha del proyecto siempre los incluyen.
            return format != null && (format.contains("d") || format.contains("D")
                    || format.contains("M") || format.contains("Y") || format.contains("y"));
        }

        private boolean wantsTime(String format) {
            return format != null && (format.contains("H") || format.contains("i") || format.contains("s"));
        }
    }

    /**
     * Parsea la entrada del filtro a {@link BigDecimal}. Devuelve {@code null} si la entrada es
     * {@code null}; lanza {@link NumberFormatException} si no es numérica (el filtro decide el fallback).
     */
    private static BigDecimal parseNumber(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (input instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return new BigDecimal(input.toString().trim());
    }

    /** Formatea importes monetarios con exactamente 2 decimales (sin separador de miles). */
    private static final class NumberFormatFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return NUMBER_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
            if (input == null) {
                return "";
            }
            try {
                return parseNumber(input).setScale(2, RoundingMode.HALF_UP).toPlainString();
            } catch (NumberFormatException ex) {
                return input.toString();
            }
        }
    }

    /** Formatea cantidades/peso conservando los decimales significativos (sin ceros finales). */
    private static final class QuantityFormatFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return NUMBER_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
            if (input == null) {
                return "";
            }
            try {
                return parseNumber(input).stripTrailingZeros().toPlainString();
            } catch (NumberFormatException ex) {
                return input.toString();
            }
        }
    }

    private static final class StringPadFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return PAD_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
            String value = input == null ? "" : input.toString();
            int width = parseInt(args.get("width"), value.length());
            String pad = stringArg(args, "pad", " ");
            String side = stringArg(args, "side", "left");

            if (pad.isEmpty()) {
                pad = " ";
            }
            StringBuilder builder = new StringBuilder(value);
            while (builder.length() < width) {
                if ("right".equalsIgnoreCase(side)) {
                    builder.append(pad.charAt(0));
                } else {
                    builder.insert(0, pad.charAt(0));
                }
            }
            return builder.toString();
        }
    }

    private final class CatalogFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return CATALOG_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
            String catalogo = stringArg(args, "catalogo", "");
            String codigo = input == null ? "" : input.toString();
            if (catalogo.isBlank() || codigo.isBlank()) {
                return codigo;
            }
            return proveedorCatalogos.buscar(normalizarCatalogo(catalogo), codigo)
                    .map(EntradaCatalogo::getDescripcion)
                    .filter(descripcion -> !descripcion.isBlank())
                    .orElse(codigo);
        }
    }

    /**
     * Resuelve un atributo arbitrario de una entrada de catálogo (p. ej. la abreviatura
     * del tipo de documento de identidad o el símbolo de la moneda). Si no existe la
     * entrada o el atributo, devuelve el código original como respaldo.
     */
    private final class CatalogAttrFilter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return CATALOG_ATTR_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) throws PebbleException {
            String catalogo = stringArg(args, "catalogo", "");
            String clave = stringArg(args, "clave", "");
            String codigo = input == null ? "" : input.toString();
            if (catalogo.isBlank() || clave.isBlank() || codigo.isBlank()) {
                return codigo;
            }
            return proveedorCatalogos.buscar(normalizarCatalogo(catalogo), codigo)
                    .flatMap(entrada -> entrada.getAtributoAdicional(clave))
                    .filter(valor -> !valor.isBlank())
                    .orElse(codigo);
        }
    }

    private static final class ImageBase64Filter implements Filter {
        @Override
        public List<String> getArgumentNames() {
            return IMAGE_ARGUMENTS;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
            if (input == null) {
                return "";
            }
            if (input instanceof byte[] bytes) {
                return Base64.getEncoder().encodeToString(bytes);
            }
            return input.toString();
        }
    }

    private static String normalizarCatalogo(String catalogo) {
        if (catalogo.length() == 1) {
            return "0" + catalogo;
        }
        return catalogo;
    }

    private static String stringArg(Map<String, Object> args, String key, String defaultValue) {
        Object value = args.get(key);
        return value == null ? defaultValue : value.toString();
    }

    private static int parseInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.toString().trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
