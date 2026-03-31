package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.xml.SerializadorXmlGuiaRemision;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Convierte un {@link BorradorGuiaRemision} en un documento PDF.
 * <p>
 * Internamente utiliza {@link RenderizadorHtmlGuiaRemision} para generar el HTML
 * base y luego lo transforma a PDF usando OpenHTMLtoPDF.
 * </p>
 *
 * @since 0.1.0
 */
public class RenderizadorPdfGuiaRemision implements RenderizadorDocumento<BorradorGuiaRemision> {
    private static final Logger log = LoggerFactory.getLogger(RenderizadorPdfGuiaRemision.class);

    private final RenderizadorHtmlGuiaRemision renderizadorHtml;
    private final SerializadorXmlGuiaRemision serializadorXmlGuiaRemision;

    public RenderizadorPdfGuiaRemision() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfGuiaRemision(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlGuiaRemision(formato);
        this.serializadorXmlGuiaRemision = new SerializadorXmlGuiaRemision();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorGuiaRemision> contexto) {
        if (usarPdfSunatJasper()) {
            try {
                return renderizarConJasperSunat(contexto);
            } catch (Exception e) {
                log.warn("Fallo render Jasper/SUNAT para Guía. Se usará fallback HTML: {}", e.getMessage());
            }
        }

        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo Guía de Remisión a PDF: " + e.getMessage(), e);
        }
    }

    private ResultadoRender renderizarConJasperSunat(ContextoRender<BorradorGuiaRemision> contexto) throws Exception {
        BorradorGuiaRemision doc = contexto.documento();
        String xml = xmlFuente(contexto, doc);
        String xmlSinNamespaces = removerNamespaces(xml);
        log.info("[UBLKIT][PDF][JASPER][GUIA] serieNumero={}-{}, tipo={}, hashPresent={}, qrPresent={}, xmlBytes={}",
                doc.getSerie(),
                doc.getNumero(),
                doc.getTipoComprobante(),
                contexto.hashDocumento() != null && !contexto.hashDocumento().isBlank(),
                contexto.qrBase64() != null && !contexto.qrBase64().isBlank(),
                xmlSinNamespaces.getBytes(StandardCharsets.UTF_8).length);
        log.debug("[UBLKIT][PDF][JASPER][GUIA] xmlPreview={}",
                xmlSinNamespaces.length() > 1200 ? xmlSinNamespaces.substring(0, 1200) : xmlSinNamespaces);

        JasperReport principal = cargarYCompilarPlantilla("Plantilla_reporte_guiaRemitente.jrxml");
        Map<String, Object> params = new HashMap<>(ParametrosJasper.construir(contexto));
        params.put("RUTA_IMAGEN_QR", "");
        params.put("ADICIONALTXT", doc.getObservaciones() != null ? doc.getObservaciones() : "");

        try (InputStream in = new java.io.ByteArrayInputStream(xmlSinNamespaces.getBytes(StandardCharsets.UTF_8))) {
            JRXmlDataSource ds = new JRXmlDataSource(in, "/DespatchAdvice/DespatchLine");
            JasperPrint print = JasperFillManager.fillReport(principal, params, ds);
            byte[] pdf = JasperExportManager.exportReportToPdf(print);
            if (pdf != null && pdf.length > 0) {
                return ResultadoRender.pdf(pdf);
            }
        }
        throw new IllegalStateException("No se pudo generar PDF de Guía con Jasper/SUNAT");
    }

    private String xmlFuente(ContextoRender<BorradorGuiaRemision> contexto, BorradorGuiaRemision doc) {
        Object xmlFromContext = contexto.atributosPlantilla() != null
                ? contexto.atributosPlantilla().get("xmlFuente")
                : null;
        if (xmlFromContext instanceof String xml && !xml.isBlank()) {
            return xml;
        }
        return serializadorXmlGuiaRemision.serializar(doc);
    }

    private JasperReport cargarYCompilarPlantilla(String nombrePlantilla) throws Exception {
        String ruta = "templates/sunat/" + nombrePlantilla;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ruta)) {
            if (in == null) {
                throw new IllegalStateException("No se encontró plantilla SUNAT: " + ruta);
            }
            JasperDesign design = JRXmlLoader.load(in);
            return JasperCompileManager.compileReport(design);
        }
    }

    private String removerNamespaces(String xml) {
        return xml
                .replaceAll("xmlns(:[A-Za-z0-9_\\-]+)?=\"[^\"]*\"", "")
                .replaceAll("<(/?)([A-Za-z0-9_\\-]+):", "<$1")
                .replaceAll("\\s+>", ">");
    }

    private boolean usarPdfSunatJasper() {
        String flag = System.getProperty("ublkit.render.pdf.sunat.enabled");
        if (flag != null) return Boolean.parseBoolean(flag);
        return false;
    }
}
