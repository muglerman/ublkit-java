package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.ensamblador.EnsambladorComunicacionBaja;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.Reversion;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializadorXmlComunicacionBajaTest {

    private final SerializadorXmlComunicacionBaja serializador = new SerializadorXmlComunicacionBaja();

    @Test
    void serializar_comunicacionBajaMinima_generaXmlValido() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertNotNull(xml);
        assertFalse(xml.isEmpty());

        Document doc = parsear(xml);
        assertNotNull(doc);
        assertEquals("VoidedDocuments", doc.getDocumentElement().getLocalName());
    }

    @Test
    void serializar_contieneVersionYCustomizacion() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("<cbc:UBLVersionID>2.0</cbc:UBLVersionID>"));
        assertTrue(xml.contains("<cbc:CustomizationID>1.0</cbc:CustomizationID>"));
    }

    @Test
    void serializar_generaIdConFormatoCorrecto() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setNumero(3);
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("RA-20260415-3"));
    }

    @Test
    void serializar_reversionGeneraPrefijoRR() throws Exception {
        Reversion reversion = new Reversion();
        reversion.setFechaEmision(LocalDate.of(2026, 4, 15));
        reversion.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        reversion.setNumero(1);
        reversion.setEmisor(emisorEstandar());
        reversion.setComprobantes(List.of(
                new ItemBaja("P001", 1, "40", "Anulación de percepción")
        ));

        String xml = serializador.serializar(reversion);

        assertTrue(xml.contains("RR-20260415-1"));
    }

    @Test
    void serializar_contieneReferenceDateEIssueDate() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("<cbc:ReferenceDate>2026-04-14</cbc:ReferenceDate>"));
        assertTrue(xml.contains("<cbc:IssueDate>2026-04-15</cbc:IssueDate>"));
    }

    @Test
    void serializar_contieneEmisor() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("20123456789"));
        assertTrue(xml.contains("EMPRESA PRUEBA S.A.C."));
        assertTrue(xml.contains("CustomerAssignedAccountID"));
        assertTrue(xml.contains("AdditionalAccountID"));
    }

    @Test
    void serializar_contieneFirma() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("Signature"));
        assertTrue(xml.contains("SignatoryParty"));
        assertTrue(xml.contains("#UBLKIT-SIGN"));
    }

    @Test
    void serializar_contieneLineasDeBaja() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("VoidedDocumentsLine"));
        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>"));
        assertTrue(xml.contains("DocumentSerialID"));
        assertTrue(xml.contains("F001"));
        assertTrue(xml.contains("DocumentNumberID"));
        assertTrue(xml.contains("VoidReasonDescription"));
        assertTrue(xml.contains("Error en RUC del cliente"));
    }

    @Test
    void serializar_multipleLineas_generaLineIdsSecuenciales() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        baja.setComprobantes(List.of(
                new ItemBaja("F001", 100, "01", "Motivo 1"),
                new ItemBaja("F001", 101, "01", "Motivo 2"),
                new ItemBaja("F002", 50, "01", "Motivo 3")
        ));
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("<cbc:LineID>1</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:LineID>2</cbc:LineID>"));
        assertTrue(xml.contains("<cbc:LineID>3</cbc:LineID>"));
    }

    @Test
    void serializar_contieneNamespaceVoidedDocuments() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1"));
    }

    @Test
    void serializar_contieneExtensionsParaFirma() throws Exception {
        ComunicacionBaja baja = crearBajaMinima();
        EnsambladorComunicacionBaja.ensamblar(baja);

        String xml = serializador.serializar(baja);

        assertTrue(xml.contains("UBLExtensions"));
        assertTrue(xml.contains("ExtensionContent"));
    }

    // ── Utilidades ───────────────────────────────────────────────

    static ComunicacionBaja crearBajaMinima() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(1);
        baja.setEmisor(emisorEstandar());
        baja.setComprobantes(List.of(
                new ItemBaja("F001", 1, "01", "Error en RUC del cliente")
        ));
        return baja;
    }

    private static EmisorDocumento emisorEstandar() {
        return new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null);
    }

    private Document parsear(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }
}
