package com.cna.ublkit.testkit.assertion;

import org.junit.jupiter.api.Assertions;

/**
 * Utilidades de aserción para CDR (ApplicationResponse de SUNAT).
 *
 * @since 0.1.0
 */
public final class AssertsCdr {

    private AssertsCdr() {
    }

    public static void assertAceptado(String cdrXml) {
        String code = extraerCodigo(cdrXml);
        Assertions.assertEquals("0", code, "El CDR debe estar aceptado (ResponseCode=0)");
    }

    public static void assertRechazado(String cdrXml) {
        String code = extraerCodigo(cdrXml);
        Assertions.assertNotNull(code, "El CDR debe tener ResponseCode");
        Assertions.assertNotEquals("0", code, "El CDR esperado debe estar rechazado");
    }

    private static String extraerCodigo(String cdrXml) {
        int idxIni = cdrXml.indexOf("<cbc:ResponseCode>");
        int idxFin = cdrXml.indexOf("</cbc:ResponseCode>");
        if (idxIni < 0 || idxFin < 0 || idxFin <= idxIni) return null;
        return cdrXml.substring(idxIni + "<cbc:ResponseCode>".length(), idxFin).trim();
    }
}

