package com.creanexusatreus.ublkit.validation.validador;

import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.guia.*;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlGuiaRemision;
import com.creanexusatreus.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorGuiaRemisionTest {

    private final ValidadorGuiaRemision validador = new ValidadorGuiaRemision();

    @Test
    void validar_guiaValida_sinErrores() {
        BorradorGuiaRemision guia = crearGuiaValida();
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.esValido(), "Guía válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-GRE-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinSerie_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setSerie(null);
        assertFalse(validador.validar(guia).esValido());
    }

    @Test
    void validar_sinFecha_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setFechaEmision(null);
        assertFalse(validador.validar(guia).esValido());
    }

    @Test
    void validar_sinRemitente_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setRemitente(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-007".equals(i.codigo())));
    }

    @Test
    void validar_sinDatosEnvio_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setEnvio(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-008".equals(i.codigo())));
    }

    @Test
    void validar_sinDetalles_retornaError() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setDetalles(null);
        ResultadoValidacion resultado = validador.validar(guia);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-GRE-009".equals(i.codigo())));
    }

    @Test
    void validar_pagadorTerceroIncompleto_retornaErrorContextual() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setPagadorFleteTercero(new TerceroGuia("1", null, "Pagador", null));

        assertTrue(validador.validar(guia).getIncidencias().stream()
                .anyMatch(i -> "VAL-GRE-010".equals(i.codigo())));
    }

    @Test
    void validar_subcontratadoIncompleto_retornaErrorContextual() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setSubcontratado(new TerceroGuia("6", "", "Subcontratador", null));

        assertTrue(validador.validar(guia).getIncidencias().stream()
                .anyMatch(i -> "VAL-GRE-011".equals(i.codigo())));
    }

    @Test
    void xsd_rechazaOriginatorCustomerPartyDentroDeConsignment() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setSubcontratado(new TerceroGuia("6", "20123456789", "Subcontratador", null));
        guia.setPagadorFleteTercero(new TerceroGuia("1", "74049995", "Pagador", null));
        String xml = new SerializadorXmlGuiaRemision().serializar(guia);
        String originator = xml.substring(xml.indexOf("<cac:OriginatorCustomerParty>"),
                xml.indexOf("</cac:OriginatorCustomerParty>") + "</cac:OriginatorCustomerParty>".length());
        String misplaced = xml.replace(originator, "")
                .replace("</cac:Consignment>", originator + "</cac:Consignment>");

        assertThrows(org.xml.sax.SAXException.class, () -> ValidadorXsdDespatchAdvice.validar(misplaced));
    }

    @Test
    void xsd_rechazaCustomerAssignedAccountIDDentroDeLogisticsOperatorParty() {
        BorradorGuiaRemision guia = crearGuiaValida();
        guia.setSubcontratado(new TerceroGuia("6", "20123456789", "Subcontratador", null));
        String xml = new SerializadorXmlGuiaRemision().serializar(guia);
        String invalid = xml.replace("<cac:LogisticsOperatorParty>",
                "<cac:LogisticsOperatorParty><cbc:CustomerAssignedAccountID schemeID=\"6\">"
                        + "20123456789</cbc:CustomerAssignedAccountID>");

        assertThrows(org.xml.sax.SAXException.class, () -> ValidadorXsdDespatchAdvice.validar(invalid));
    }

    @Test
    void xsd_resuelveImportDesdeUrlNestedDeSpringBoot() throws Exception {
        LSResourceResolver resolver = ValidadorXsdDespatchAdvice.resolverParaPruebas(getClass().getClassLoader());
        String baseUri = "jar:nested:/app/app.jar/!BOOT-INF/lib/ublkit-validation-1.0.0.jar!/"
                + "sunat/schema/ubl-2.1/maindoc/UBL-DespatchAdvice-2.1.xsd";

        for (String nombre : List.of(
                "CCTS_CCT_SchemaModule-2.1.xsd", "UBL-CommonAggregateComponents-2.1.xsd",
                "UBL-CommonBasicComponents-2.1.xsd", "UBL-CommonExtensionComponents-2.1.xsd",
                "UBL-CommonSignatureComponents-2.1.xsd", "UBL-CoreComponentParameters-2.1.xsd",
                "UBL-ExtensionContentDataType-2.1.xsd", "UBL-QualifiedDataTypes-2.1.xsd",
                "UBL-SignatureAggregateComponents-2.1.xsd", "UBL-SignatureBasicComponents-2.1.xsd",
                "UBL-UnqualifiedDataTypes-2.1.xsd", "UBL-XAdESv132-2.1.xsd",
                "UBL-XAdESv141-2.1.xsd", "UBL-xmldsig-core-schema-2.1.xsd")) {
            LSInput input = resolver.resolveResource(null, null, null, "../common/" + nombre, baseUri);
            assertNotNull(input);
            assertNotNull(input.getByteStream());
            assertEquals("classpath:/sunat/schema/ubl-2.1/common/" + nombre, input.getSystemId());
            input.getByteStream().close();
        }
    }

    @Test
    void xsd_rechazaImportFueraDelBundleSunat() {
        LSResourceResolver resolver = ValidadorXsdDespatchAdvice.resolverParaPruebas(getClass().getClassLoader());

        assertThrows(org.w3c.dom.ls.LSException.class,
                () -> resolver.resolveResource(null, null, null, "https://example.test/malicioso.xsd", null));
    }

    static BorradorGuiaRemision crearGuiaValida() {
        BorradorGuiaRemision guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.now());
        guia.setRemitente(new EmisorDocumento("20000000001", "Empresa Remitente S.A.C.", "Empresa Remitente", null, null));
        guia.setDestinatario(new DestinatarioGuia("6", "20100000000", "Cliente destinatario"));

        DatosEnvio envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setMotivoTraslado("Venta");
        envio.setPesoTotal(new BigDecimal("10.00"));
        envio.setPesoTotalUnidadMedida("KGM");
        guia.setEnvio(envio);

        guia.setDetalles(List.of(new LineaGuia("NIU", BigDecimal.ONE, "Producto transportado", null, null, null)));

        return guia;
    }
}
