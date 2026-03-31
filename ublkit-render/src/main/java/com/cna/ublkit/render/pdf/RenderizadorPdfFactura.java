package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convierte un {@link BorradorFactura} en un documento PDF.
 * <p>
 * Internamente, primero genera la representación HTML mediante
 * {@link RenderizadorHtmlFactura} y luego la transforma a PDF
 * usando OpenHTMLtoPDF (basado en PDFBox).
 * </p>
 *
 * @since 0.1.0
 */
public class RenderizadorPdfFactura implements RenderizadorDocumento<BorradorFactura> {
    private static final Logger log = LoggerFactory.getLogger(RenderizadorPdfFactura.class);

    private final RenderizadorHtmlFactura renderizadorHtml;
    private final SerializadorXmlFactura serializadorXmlFactura;

    /**
     * Construye el renderizador PDF usando formato A4 por defecto.
     */
    public RenderizadorPdfFactura() {
        this(FormatoImpresion.A4);
    }

    /**
     * Construye el renderizador PDF usando el formato de impresión indicado.
     *
     * @param formato formato de impresión para la plantilla HTML base
     */
    public RenderizadorPdfFactura(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlFactura(formato);
        this.serializadorXmlFactura = new SerializadorXmlFactura();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<BorradorFactura> contexto) {
        if (usarPdfSunatJasper()) {
            try {
                return renderizarConJasperSunat(contexto);
            } catch (Exception e) {
                log.warn("Fallo render Jasper/SUNAT para Factura-Boleta. Se usará fallback HTML: {}", e.getMessage());
            }
        }

        // 1. Generar HTML desde la plantilla Pebble
        ResultadoRender resultadoHtml = renderizadorHtml.renderizar(contexto);
        String html = resultadoHtml.contenidoHtml();

        // 2. Convertir HTML a PDF usando OpenHTMLtoPDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            return ResultadoRender.pdf(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo HTML a PDF: " + e.getMessage(), e);
        }
    }

    private ResultadoRender renderizarConJasperSunat(ContextoRender<BorradorFactura> contexto) throws Exception {
        BorradorFactura doc = contexto.documento();
        String xml = xmlFuente(contexto, doc);
        String xmlSinNamespaces = removerNamespaces(xml);

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        f.setNamespaceAware(false);
        Document xmlDoc = f.newDocumentBuilder().parse(new InputSource(new StringReader(xmlSinNamespaces)));

        String plantillaPrincipal = "03".equals(doc.getTipoComprobante())
                ? "Plantilla_reporte_boleta.jrxml"
                : "Plantilla_reporte_factura.jrxml";

        Path dirCompilado = Files.createTempDirectory("ublkit-sunat-jrxml-");
        compilarPlantillaSunat("Plantilla_reporte_factura_subtotal.jrxml", dirCompilado);
        compilarPlantillaSunat("Plantilla_reporte_factura_impuesto.jrxml", dirCompilado);

        JasperReport principal = cargarYCompilarPlantilla(plantillaPrincipal);
        Map<String, Object> params = new HashMap<>(ParametrosJasper.construir(contexto));
        params.put("SUBREPORT_DIR", dirCompilado.toAbsolutePath().toString() + "/");
        params.put("RUTA_IMAGEN_QR", "");
        params.put("ADICIONALTXT", doc.getObservaciones() != null ? doc.getObservaciones() : "");
        params.put("XML_DATA_DOCUMENT", xmlDoc);

        try (InputStream in = new java.io.ByteArrayInputStream(xmlSinNamespaces.getBytes(StandardCharsets.UTF_8))) {
            JRXmlDataSource ds = new JRXmlDataSource(in, "/Invoice/InvoiceLine");
            JasperPrint print = JasperFillManager.fillReport(principal, params, ds);
            byte[] pdf = JasperExportManager.exportReportToPdf(print);
            if (pdf != null && pdf.length > 0) {
                return ResultadoRender.pdf(pdf);
            }
        }
        throw new IllegalStateException("No se pudo generar PDF con Jasper/SUNAT");
    }

    private String xmlFuente(ContextoRender<BorradorFactura> contexto, BorradorFactura doc) {
        Object xmlFromContext = contexto.atributosPlantilla() != null
                ? contexto.atributosPlantilla().get("xmlFuente")
                : null;
        if (xmlFromContext instanceof String xml && !xml.isBlank()) {
            return xml;
        }
        return serializadorXmlFactura.serializar(doc);
    }

    private void compilarPlantillaSunat(String nombrePlantilla, Path destinoDir) throws Exception {
        JasperReport jr = cargarYCompilarPlantilla(nombrePlantilla);
        String out = destinoDir.resolve(nombrePlantilla.replace(".jrxml", ".jasper")).toString();
        JRSaver.saveObject(jr, out);
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
        if (flag == null) {
            flag = System.getProperty("ublkit.render.pdf.sunat.enabled");
        }
        if (flag != null) return Boolean.parseBoolean(flag);

        // Por defecto se prioriza estabilidad de runtime. Jasper/SUNAT se puede
        // activar explícitamente con -Dublkit.render.pdf.sunat.enabled=true.
        return false;
    }
}
