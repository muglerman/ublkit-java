package com.cna.ublkit.testkit.fixture;

/**
 * Respuestas SUNAT simuladas para pruebas unitarias/integración.
 *
 * @since 0.1.0
 */
public final class RespuestasSunatSimuladas {

    private RespuestasSunatSimuladas() {
    }

    public static String cdrAceptado() {
        return """
                <ApplicationResponse xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                  <cbc:ResponseCode>0</cbc:ResponseCode>
                  <cbc:Description>La factura ha sido aceptada</cbc:Description>
                </ApplicationResponse>
                """;
    }

    public static String cdrRechazado1033() {
        return """
                <ApplicationResponse xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                  <cbc:ResponseCode>1033</cbc:ResponseCode>
                  <cbc:Description>El número de RUC del receptor es inválido</cbc:Description>
                </ApplicationResponse>
                """;
    }

    public static String cdrObservado2001() {
        return """
                <ApplicationResponse xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2">
                  <cbc:ResponseCode>2001</cbc:ResponseCode>
                  <cbc:Description>Documento con observaciones</cbc:Description>
                </ApplicationResponse>
                """;
    }
}

