package com.cna.ublkit.ubl.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConstantesUbl Class Tests")
class ConstantesUblTest {

    @Test
    @DisplayName("Should define correct Invoice namespace")
    void testNamespaceInvoice() {
        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", ConstantesUbl.NS_INVOICE);
    }

    @Test
    @DisplayName("Should define correct CreditNote namespace")
    void testNamespaceCreditNote() {
        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", ConstantesUbl.NS_CREDIT_NOTE);
    }

    @Test
    @DisplayName("Should define correct DebitNote namespace")
    void testNamespaceDebitNote() {
        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2", ConstantesUbl.NS_DEBIT_NOTE);
    }

    @Test
    @DisplayName("Should define correct CAC namespace")
    void testNamespaceCac() {
        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", ConstantesUbl.NS_CAC);
    }

    @Test
    @DisplayName("Should define correct CBC namespace")
    void testNamespaceCbc() {
        assertEquals("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", ConstantesUbl.NS_CBC);
    }

    @Test
    @DisplayName("Should define correct SAC namespace")
    void testNamespaceSac() {
        assertEquals("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1", ConstantesUbl.NS_SAC);
    }

    @Test
    @DisplayName("Should define correct Perception namespace")
    void testNamespacePerception() {
        assertEquals("urn:sunat:names:specification:ubl:peru:schema:xsd:Perception-1", ConstantesUbl.NS_PERCEPTION);
    }

    @Test
    @DisplayName("Should define correct Retention namespace")
    void testNamespaceRetention() {
        assertEquals("urn:sunat:names:specification:ubl:peru:schema:xsd:Retention-1", ConstantesUbl.NS_RETENTION);
    }

    @Test
    @DisplayName("Should define correct UBL version")
    void testUblVersion() {
        assertEquals("2.1", ConstantesUbl.UBL_VERSION);
    }

    @Test
    @DisplayName("Should define correct UBL SUNAT version")
    void testUblVersionSunat() {
        assertEquals("2.0", ConstantesUbl.UBL_VERSION_SUNAT);
    }

    @Test
    @DisplayName("Should define correct Customization ID")
    void testCustomizationId() {
        assertEquals("2.0", ConstantesUbl.CUSTOMIZATION_ID);
    }

    @Test
    @DisplayName("Should define correct Customization ID for Voided")
    void testCustomizationIdVoided() {
        assertEquals("1.0", ConstantesUbl.CUSTOMIZATION_ID_VOIDED);
    }

    @Test
    @DisplayName("Should define correct Customization ID for Summary")
    void testCustomizationIdSummary() {
        assertEquals("1.1", ConstantesUbl.CUSTOMIZATION_ID_SUMMARY);
    }

    @Test
    @DisplayName("Should have private constructor to prevent instantiation")
    void testPrivateConstructor() {
        assertThrows(Exception.class, () -> {
            ConstantesUbl.class.getDeclaredConstructor().newInstance();
        });
    }

    @Test
    @DisplayName("All namespaces should be non-empty strings")
    void testNamespacesAreNonEmpty() {
        assertFalse(ConstantesUbl.NS_INVOICE.isEmpty());
        assertFalse(ConstantesUbl.NS_CREDIT_NOTE.isEmpty());
        assertFalse(ConstantesUbl.NS_CAC.isEmpty());
        assertFalse(ConstantesUbl.NS_PERCEPTION.isEmpty());
    }

    @Test
    @DisplayName("All versions should be non-empty strings")
    void testVersionsAreNonEmpty() {
        assertFalse(ConstantesUbl.UBL_VERSION.isEmpty());
        assertFalse(ConstantesUbl.UBL_VERSION_SUNAT.isEmpty());
        assertFalse(ConstantesUbl.CUSTOMIZATION_ID.isEmpty());
    }
}
