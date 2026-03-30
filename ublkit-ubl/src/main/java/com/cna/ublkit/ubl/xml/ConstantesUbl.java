package com.cna.ublkit.ubl.xml;

/**
 * Constantes de namespaces UBL 2.1 y SUNAT.
 *
 * @since 0.1.0
 */
public final class ConstantesUbl {

    public static final String NS_INVOICE = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    public static final String NS_CREDIT_NOTE = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2";
    public static final String NS_DEBIT_NOTE = "urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2";
    public static final String NS_CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    public static final String NS_CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
    public static final String NS_EXT = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";
    public static final String NS_DS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String NS_SAC = "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1";
    public static final String NS_CCTS = "urn:un:unece:uncefact:documentation:2";
    public static final String NS_QDT = "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2";
    public static final String NS_UDT = "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2";

    // Namespaces SUNAT propios
    public static final String NS_VOIDED_DOCUMENTS = "urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1";
    public static final String NS_SUMMARY_DOCUMENTS = "urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1";
    public static final String NS_PERCEPTION = "urn:sunat:names:specification:ubl:peru:schema:xsd:Perception-1";
    public static final String NS_RETENTION = "urn:sunat:names:specification:ubl:peru:schema:xsd:Retention-1";

    // Versiones UBL
    public static final String UBL_VERSION = "2.1";
    public static final String UBL_VERSION_SUNAT = "2.0";
    public static final String CUSTOMIZATION_ID = "2.0";
    public static final String CUSTOMIZATION_ID_VOIDED = "1.0";
    public static final String CUSTOMIZATION_ID_SUMMARY = "1.1";

    private ConstantesUbl() {
    }
}
