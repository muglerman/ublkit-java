package com.cna.ublkit.validation.validador.sunat;

import com.cna.ublkit.validation.modelo.IncidenciaValidacion;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.modelo.SeveridadValidacion;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ejecuta reglas XSL de referencia SUNAT empaquetadas en el módulo.
 */
public final class ValidadorSunatXsl {

    private static final String FACTORY_SAXON = "net.sf.saxon.TransformerFactoryImpl";
    private static final Pattern CODIGO_PATTERN = Pattern.compile("(?:errorCode\\s+)?(\\d{3,5})");
    private static final String PARAM_NOMBRE_ARCHIVO = "nombreArchivoEnviado";
    private static final String PROP_VALIDACION_SUNAT = "ublkit.validation.sunat.enabled";

    public ResultadoValidacion validarXml(String xml, String nombreArchivo, ReglaSunatXsl regla) {
        ResultadoValidacion resultado = new ResultadoValidacion();
        if (!isValidacionSunatHabilitada()) {
            return resultado;
        }
        if (xml == null || xml.isBlank()) {
            resultado.agregar(new IncidenciaValidacion("SUNAT-000", "XML vacío para validación SUNAT", SeveridadValidacion.ERROR));
            return resultado;
        }

        InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(regla.recursoClasspath());
        if (xslStream == null) {
            resultado.agregar(new IncidenciaValidacion("SUNAT-001",
                    "No se encontró recurso XSL de validación: " + regla.recursoClasspath(),
                    SeveridadValidacion.ADVERTENCIA));
            return resultado;
        }

        List<MensajeTransformacion> mensajes = new ArrayList<>();
        try (InputStream in = xslStream) {
            TransformerFactory factory = crearFactorySaxon();
            factory.setURIResolver(new ResolverSunatClasspath());

            String xslContenido = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("current-date()", "date:date()");
            StreamSource xslSource = new StreamSource(new StringReader(xslContenido));
            xslSource.setSystemId(regla.recursoClasspath());
            Transformer transformer = factory.newTransformer(xslSource);
            transformer.setErrorListener(new ListenerMensajes(mensajes));
            transformer.setParameter(PARAM_NOMBRE_ARCHIVO, nombreArchivo);
            transformer.transform(
                    new StreamSource(new StringReader(xml)),
                    new StreamResult(new StringWriter())
            );
        } catch (Exception ex) {
            String codigo = extraerCodigo(ex.getMessage());
            resultado.agregar(new IncidenciaValidacion(
                    codigo != null ? codigo : "SUNAT-002",
                    "Validación SUNAT falló: " + mensajeLimpio(ex.getMessage()),
                    SeveridadValidacion.ERROR
            ));
            return resultado;
        }

        for (MensajeTransformacion msg : mensajes) {
            String codigo = extraerCodigo(msg.mensaje());
            if (codigo == null) {
                codigo = msg.severidad() == SeveridadValidacion.ERROR ? "SUNAT-ERR" : "SUNAT-WRN";
            }
            resultado.agregar(new IncidenciaValidacion(codigo, mensajeLimpio(msg.mensaje()), msg.severidad()));
        }
        return resultado;
    }

    private static boolean isValidacionSunatHabilitada() {
        String enabled = System.getProperty(PROP_VALIDACION_SUNAT);
        return enabled != null && Boolean.parseBoolean(enabled);
    }

    private static TransformerFactory crearFactorySaxon() throws Exception {
        System.setProperty("jdk.xml.xpathExprOpLimit", "0");
        System.setProperty("jdk.xml.xpathTotalOpLimit", "0");
        System.setProperty("jdk.xml.xpathExprGrpLimit", "0");
        return (TransformerFactory) Class.forName(FACTORY_SAXON).getDeclaredConstructor().newInstance();
    }

    private static String extraerCodigo(String mensaje) {
        if (mensaje == null) return null;
        Matcher m = CODIGO_PATTERN.matcher(mensaje);
        return m.find() ? m.group(1) : null;
    }

    private static String mensajeLimpio(String mensaje) {
        if (mensaje == null) return "";
        return mensaje.replaceAll("\\s+", " ").trim();
    }

    private record MensajeTransformacion(String mensaje, SeveridadValidacion severidad) {
    }

    private static final class ListenerMensajes implements ErrorListener {
        private final List<MensajeTransformacion> mensajes;

        private ListenerMensajes(List<MensajeTransformacion> mensajes) {
            this.mensajes = mensajes;
        }

        @Override
        public void warning(TransformerException exception) {
            mensajes.add(new MensajeTransformacion(exception.getMessage(), SeveridadValidacion.ADVERTENCIA));
        }

        @Override
        public void error(TransformerException exception) {
            mensajes.add(new MensajeTransformacion(exception.getMessage(), SeveridadValidacion.ERROR));
            throw new RuntimeException(exception);
        }

        @Override
        public void fatalError(TransformerException exception) {
            mensajes.add(new MensajeTransformacion(exception.getMessage(), SeveridadValidacion.ERROR));
            throw new RuntimeException(exception);
        }
    }

    private static final class ResolverSunatClasspath implements URIResolver {
        @Override
        public Source resolve(String href, String base) {
            if (href == null || href.isBlank()) return null;
            String normalizado = href.replace('\\', '/').trim();
            String nombreArchivo = extraerNombreArchivo(normalizado);
            String lower = normalizado.toLowerCase(Locale.ROOT);

            if (normalizado.endsWith("validate_utils.xsl") || "validate_utils.xsl".equals(nombreArchivo)) {
                return classpathSource("sunat/validation/error/validate_utils.xsl");
            }
            if (normalizado.endsWith("error_utils.xsl") || "error_utils.xsl".equals(nombreArchivo)) {
                return classpathSource("sunat/validation/error/error_utils.xsl");
            }
            if (normalizado.endsWith("StringTemplates.xsl") || "stringtemplates.xsl".equals(nombreArchivo)) {
                return classpathSource("sunat/validation/error/StringTemplates.xsl");
            }
            if (lower.contains("/catalogo/") || nombreArchivo.startsWith("cat_") || "catalogoerrores.xml".equals(nombreArchivo)) {
                return classpathSource("sunat/validation/catalogo/" + nombreArchivo);
            }
            return classpathSource(normalizado);
        }

        private static String extraerNombreArchivo(String path) {
            int idx = path.lastIndexOf('/');
            String nombre = idx >= 0 ? path.substring(idx + 1) : path;
            return nombre.toLowerCase(Locale.ROOT);
        }

        private Source classpathSource(String path) {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            if (in == null) return null;
            StreamSource source = new StreamSource(in);
            source.setSystemId(path);
            return source;
        }
    }
}
