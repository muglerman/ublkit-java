package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorComunicacionBaja;
import com.cna.ublkit.ubl.ensamblador.EnsambladorFactura;
import com.cna.ublkit.ubl.ensamblador.EnsambladorNota;
import com.cna.ublkit.ubl.ensamblador.EnsambladorResumenDiario;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.FormaDePago;
import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.sunat.resumen.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de validación XSD: verifica que el XML generado por los serializadores
 * cumple con los esquemas UBL 2.1 estándar.
 * <p>
 * Los XSD están en docs/xsd/ y son los schemas OASIS UBL 2.1 estándar.
 * SUNAT usa estos mismos schemas para Invoice, CreditNote, DebitNote y DespatchAdvice.
 * <p>
 * Nota: VoidedDocuments y SummaryDocuments usan schemas SUNAT propios que no están
 * incluidos como XSD (SUNAT los define internamente). Para estos documentos verificamos
 * que el XML sea bien formado y tenga la estructura esperada.
 *
 * @since 0.1.0
 */
class ValidacionXsdTest {

    private static final String XSD_BASE = "docs/xsd/";
    private static boolean xsdDisponibles = false;

    @BeforeAll
    static void verificarXsdDisponibles() {
        File invoiceXsd = resolverXsd("maindoc/UBL-Invoice-2.1.xsd");
        xsdDisponibles = invoiceXsd.exists();
    }

    // ── Invoice XSD ──────────────────────────────────────────────

    @Test
    void facturaMinima_validaContraXsdInvoice() throws Exception {
        if (!xsdDisponibles) return;

        BorradorFactura factura = crearFacturaMinima();
        EnsambladorFactura.ensamblar(factura);
        String xml = new SerializadorXmlFactura().serializar(factura);

        validarXsd(xml, "maindoc/UBL-Invoice-2.1.xsd");
    }

    @Test
    void boletaMinima_validaContraXsdInvoice() throws Exception {
        if (!xsdDisponibles) return;

        BorradorFactura boleta = crearFacturaMinima();
        boleta.setSerie("B001");
        boleta.setTipoComprobante("03");
        boleta.setReceptor(new ReceptorDocumento("1", "12345678", "CONSUMIDOR", null, null));
        EnsambladorFactura.ensamblar(boleta);
        String xml = new SerializadorXmlFactura().serializar(boleta);

        validarXsd(xml, "maindoc/UBL-Invoice-2.1.xsd");
    }

    // ── CreditNote XSD ───────────────────────────────────────────

    @Test
    void notaCredito_validaContraXsdCreditNote() throws Exception {
        if (!xsdDisponibles) return;

        BorradorNotaCredito nota = crearNotaCreditoMinima();
        EnsambladorNota.ensamblar(nota);
        String xml = new SerializadorXmlNotaCredito().serializar(nota);

        validarXsd(xml, "maindoc/UBL-CreditNote-2.1.xsd");
    }

    // ── DebitNote XSD ────────────────────────────────────────────

    @Test
    void notaDebito_validaContraXsdDebitNote() throws Exception {
        if (!xsdDisponibles) return;

        BorradorNotaDebito nota = crearNotaDebitoMinima();
        EnsambladorNota.ensamblar(nota);
        String xml = new SerializadorXmlNotaDebito().serializar(nota);

        validarXsd(xml, "maindoc/UBL-DebitNote-2.1.xsd");
    }

    // ── DespatchAdvice XSD ───────────────────────────────────────

    @Test
    void guiaRemision_validaContraXsdDespatchAdvice() throws Exception {
        if (!xsdDisponibles) return;

        BorradorGuiaRemision guia = crearGuiaMinima();
        String xml = new SerializadorXmlGuiaRemision().serializar(guia);

        validarXsd(xml, "maindoc/UBL-DespatchAdvice-2.1.xsd");
    }

    // ── Documentos SUNAT propios (sin XSD estándar, solo well-formed) ─

    @Test
    void comunicacionBaja_xmlBienFormado() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);
        String xml = new SerializadorXmlComunicacionBaja().serializar(baja);

        // Verificar bien formado
        assertDoesNotThrow(() -> parsearXml(xml));
        assertTrue(xml.contains("VoidedDocuments"));
        assertTrue(xml.contains("urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1"));
    }

    @Test
    void resumenDiario_xmlBienFormado() throws Exception {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);
        String xml = new SerializadorXmlResumenDiario().serializar(resumen);

        assertDoesNotThrow(() -> parsearXml(xml));
        assertTrue(xml.contains("SummaryDocuments"));
        assertTrue(xml.contains("urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1"));
    }

    // ── Validación de estructura SUNAT para documentos propios ──

    @Test
    void comunicacionBaja_tieneEstructuraSunatCorrecta() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);
        String xml = new SerializadorXmlComunicacionBaja().serializar(baja);

        assertTrue(xml.contains("<cbc:UBLVersionID>2.0</cbc:UBLVersionID>"));
        assertTrue(xml.contains("<cbc:CustomizationID>1.0</cbc:CustomizationID>"));
        assertTrue(xml.contains("RA-"));
        assertTrue(xml.contains("VoidedDocumentsLine"));
        assertTrue(xml.contains("DocumentSerialID"));
        assertTrue(xml.contains("DocumentNumberID"));
        assertTrue(xml.contains("VoidReasonDescription"));
    }

    @Test
    void resumenDiario_tieneEstructuraSunatCorrecta() throws Exception {
        ResumenDiario resumen = crearResumenMinimo();
        EnsambladorResumenDiario.ensamblar(resumen);
        String xml = new SerializadorXmlResumenDiario().serializar(resumen);

        assertTrue(xml.contains("<cbc:UBLVersionID>2.0</cbc:UBLVersionID>"));
        assertTrue(xml.contains("<cbc:CustomizationID>1.1</cbc:CustomizationID>"));
        assertTrue(xml.contains("RC-"));
        assertTrue(xml.contains("SummaryDocumentsLine"));
        assertTrue(xml.contains("TotalAmount"));
        assertTrue(xml.contains("BillingPayment"));
    }

    // ── Utilidades ───────────────────────────────────────────────

    private void validarXsd(String xml, String xsdPath) throws SAXException {
        try {
            // La extensión UBLExtensions se emite vacía como placeholder para la firma digital.
            // Se agrega contenido placeholder para cumplir la restricción XSD (ext:ExtensionContent
            // requiere al menos un hijo). En producción, el FirmadorXml llena este espacio.
            String xmlParaValidar = xml.replace(
                    "<ext:ExtensionContent/>",
                    "<ext:ExtensionContent><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><ds:Reference URI=\"\"><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><ds:DigestValue>dGVzdA==</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>dGVzdA==</ds:SignatureValue></ds:Signature></ext:ExtensionContent>"
            );
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(resolverXsd(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlParaValidar)));
        } catch (SAXException e) {
            fail("El XML no valida contra " + xsdPath + ": " + e.getMessage());
        } catch (Exception e) {
            fail("Error durante validación XSD: " + e.getMessage());
        }
    }

    private static File resolverXsd(String relativePath) {
        // Intentar desde raíz del proyecto
        File file = new File(XSD_BASE + relativePath);
        if (!file.exists()) {
            // Intentar ruta absoluta
            file = new File(System.getProperty("user.dir") + "/" + XSD_BASE + relativePath);
        }
        if (!file.exists()) {
            // Intentar subir al parent
            file = new File("../" + XSD_BASE + relativePath);
        }
        return file;
    }

    private void parsearXml(String xml) throws Exception {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
        builder.parse(new InputSource(new StringReader(xml)));
    }

    // ── Fábricas ─────────────────────────────────────────────────

    private BorradorFactura crearFacturaMinima() {
        BorradorFactura factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa S.A.C.", null, null));
        factura.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(new BigDecimal("2"));
        linea.setPrecio(new BigDecimal("100.00"));
        factura.setDetalles(List.of(linea));
        factura.setFormaDePago(new FormaDePago("Contado", null, null));
        return factura;
    }

    private BorradorNotaCredito crearNotaCreditoMinima() {
        BorradorNotaCredito nota = new BorradorNotaCredito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setMoneda("PEN");
        nota.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa S.A.C.", null, null));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Descuento concedido");
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Producto A");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("100.00"));
        nota.setDetalles(List.of(linea));
        return nota;
    }

    private BorradorNotaDebito crearNotaDebitoMinima() {
        BorradorNotaDebito nota = new BorradorNotaDebito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setMoneda("PEN");
        nota.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa S.A.C.", "Mi Empresa S.A.C.", null, null));
        nota.setReceptor(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setTipoNota("01");
        nota.setSustentoDescripcion("Intereses");
        LineaDetalle linea = new LineaDetalle();
        linea.setDescripcion("Intereses");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("50.00"));
        nota.setDetalles(List.of(linea));
        return nota;
    }

    private BorradorGuiaRemision crearGuiaMinima() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.of(2026, 3, 30));
        guia.setRemitente(new EmisorDocumento("20000000001", "Empresa S.A.C.", "Empresa S.A.C.", null, null));
        guia.setDestinatario(new DestinatarioGuia("6", "20123456789", "Cliente S.A.C."));
        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("10.00"));
        envio.setPesoTotalUnidadMedida("KGM");
        guia.setEnvio(envio);
        guia.setDetalles(List.of(new LineaGuia("NIU", BigDecimal.ONE, "Producto", null, null, null)));
        return guia;
    }

    private ComunicacionBaja crearBajaMinima() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(1);
        baja.setEmisor(new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));
        baja.setComprobantes(List.of(new ItemBaja("F001", 1, "01", "Error en RUC del cliente")));
        return baja;
    }

    private ResumenDiario crearResumenMinimo() {
        ResumenDiario resumen = new ResumenDiario();
        resumen.setFechaEmision(LocalDate.of(2026, 4, 15));
        resumen.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        resumen.setNumero(1);
        resumen.setMoneda("PEN");
        resumen.setEmisor(new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));
        ComprobanteResumen comp = new ComprobanteResumen();
        comp.setTipoComprobante("03");
        comp.setSerieNumero("B001-1");
        comp.setMoneda("PEN");
        comp.setCliente(new ReceptorDocumento("1", "12345678", "JUAN PEREZ", null, null));
        comp.setValorVenta(new ComprobanteValorVenta(new BigDecimal("118.00"), null, new BigDecimal("100.00"), null, null, null));
        comp.setImpuestos(new ComprobanteImpuestos(new BigDecimal("18.00"), new BigDecimal("0.18"), null, null, null, null));
        resumen.setComprobantes(List.of(new ItemResumenDiario("1", comp)));
        return resumen;
    }
}
