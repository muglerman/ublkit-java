package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.api.RenderizadorDocumento;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.FormatoImpresion;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaCredito;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaDebito;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Convierte un modelo de Nota Electrónica en un documento PDF.
 * <p>
 * Internamente utiliza {@link RenderizadorHtmlNota} para generar el HTML
 * base y luego lo transforma a PDF usando OpenHTMLtoPDF.
 * </p>
 *
 * @since 0.1.0
 */
public class RenderizadorPdfNota implements RenderizadorDocumento<Object> {

    private final RenderizadorHtmlNota renderizadorHtml;
    private final SerializadorXmlNotaCredito serializadorNotaCredito;
    private final SerializadorXmlNotaDebito serializadorNotaDebito;

    public RenderizadorPdfNota() {
        this(FormatoImpresion.A4);
    }

    public RenderizadorPdfNota(FormatoImpresion formato) {
        this.renderizadorHtml = new RenderizadorHtmlNota(formato);
        this.serializadorNotaCredito = new SerializadorXmlNotaCredito();
        this.serializadorNotaDebito = new SerializadorXmlNotaDebito();
    }

    @Override
    public ResultadoRender renderizar(ContextoRender<Object> contexto) {
        if (usarPdfSunatJasper()) {
            try {
                return renderizarConJasperSunat(contexto);
            } catch (Exception ignored) {
                // Fallback seguro al render actual.
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
            throw new RuntimeException("Error convirtiendo Nota Electrónica a PDF: " + e.getMessage(), e);
        }
    }

    private ResultadoRender renderizarConJasperSunat(ContextoRender<Object> contexto) throws Exception {
        Object doc = contexto.documento();
        String xml;
        String plantillaPrincipal;
        String subreportSubtotal;
        String subreportImpuesto;
        String queryRaiz;

        if (doc instanceof BorradorNotaCredito nc) {
            xml = serializadorNotaCredito.serializar(nc);
            plantillaPrincipal = "Plantilla_reporte_notacredito.jrxml";
            subreportSubtotal = "Plantilla_reporte_notacredito_subtotal.jrxml";
            subreportImpuesto = "Plantilla_reporte_notacredito_impuesto.jrxml";
            queryRaiz = "/CreditNote/CreditNoteLine";
        } else if (doc instanceof BorradorNotaDebito nd) {
            xml = serializadorNotaDebito.serializar(nd);
            plantillaPrincipal = "Plantilla_reporte_notadebito.jrxml";
            subreportSubtotal = "Plantilla_reporte_notadebito_subtotal.jrxml";
            subreportImpuesto = "Plantilla_reporte_notadebito_impuesto.jrxml";
            queryRaiz = "/DebitNote/DebitNoteLine";
        } else {
            throw new IllegalArgumentException("El documento debe ser BorradorNotaCredito o BorradorNotaDebito");
        }

        String xmlSinNamespaces = removerNamespaces(xml);

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        f.setNamespaceAware(false);
        Document xmlDoc = f.newDocumentBuilder().parse(new InputSource(new StringReader(xmlSinNamespaces)));

        Path dirCompilado = Files.createTempDirectory("ublkit-sunat-jrxml-nota-");
        compilarPlantillaSunat(subreportSubtotal, dirCompilado);
        compilarPlantillaSunat(subreportImpuesto, dirCompilado);

        JasperReport principal = cargarYCompilarPlantilla(plantillaPrincipal);
        Map<String, Object> params = new HashMap<>(ParametrosJasper.construir(contexto));
        params.put("SUBREPORT_DIR", dirCompilado.toAbsolutePath().toString() + "/");
        params.put("RUTA_IMAGEN_QR", "");
        params.put("ADICIONALTXT", "");
        params.put("XML_DATA_DOCUMENT", xmlDoc);

        try (InputStream in = new java.io.ByteArrayInputStream(xmlSinNamespaces.getBytes(StandardCharsets.UTF_8))) {
            JRXmlDataSource ds = new JRXmlDataSource(in, queryRaiz);
            JasperPrint print = JasperFillManager.fillReport(principal, params, ds);
            byte[] pdf = JasperExportManager.exportReportToPdf(print);
            if (pdf != null && pdf.length > 0) {
                return ResultadoRender.pdf(pdf);
            }
        }
        throw new IllegalStateException("No se pudo generar PDF de Nota con Jasper/SUNAT");
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
        if (flag != null) return Boolean.parseBoolean(flag);
        return false;
    }
}
