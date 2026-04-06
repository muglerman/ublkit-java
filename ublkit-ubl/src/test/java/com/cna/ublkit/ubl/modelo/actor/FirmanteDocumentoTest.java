package com.cna.ublkit.ubl.modelo.actor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FirmanteDocumento Record Tests")
class FirmanteDocumentoTest {

    @Test
    @DisplayName("Should create FirmanteDocumento with ruc and razonSocial")
    void testCrearFirmante() {
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            "MI EMPRESA S.A.C."
        );

        assertEquals("20123456789", firmante.ruc());
        assertEquals("MI EMPRESA S.A.C.", firmante.razonSocial());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        FirmanteDocumento firmante1 = new FirmanteDocumento(
            "20123456789",
            "MI EMPRESA S.A.C."
        );

        FirmanteDocumento firmante2 = new FirmanteDocumento(
            "20123456789",
            "MI EMPRESA S.A.C."
        );

        assertEquals(firmante1, firmante2);
        assertEquals(firmante1.hashCode(), firmante2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct signers")
    void testDesigualdadPorDiferentesValores() {
        FirmanteDocumento firmante1 = new FirmanteDocumento(
            "20123456789",
            "EMPRESA 1 S.A.C."
        );

        FirmanteDocumento firmante2 = new FirmanteDocumento(
            "20999999999",
            "EMPRESA 2 S.A.C."
        );

        assertNotEquals(firmante1, firmante2);
    }

    @Test
    @DisplayName("Should accept RUC of exactly 11 digits")
    void testRucValidoOnceDigitos() {
        FirmanteDocumento firmante = new FirmanteDocumento(
            "12345678901",
            "EMPRESA S.A.C."
        );

        assertEquals("12345678901", firmante.ruc());
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            "MI EMPRESA S.A.C."
        );

        String toString = firmante.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should support various RUC prefixes")
    void testVariosRucPrefijos() {
        String[] rucValidos = {
            "20123456789",  // RUC nuevo
            "10123456789",  // RUC antiguo
            "15123456789",
            "17123456789"
        };

        for (String ruc : rucValidos) {
            FirmanteDocumento firmante = new FirmanteDocumento(ruc, "EMPRESA");
            assertEquals(ruc, firmante.ruc());
        }
    }

    @Test
    @DisplayName("Should preserve razonSocial exactly")
    void testPreservaRazonSocial() {
        String razonSocial = "EMPRESA & CIA. S.A.C. - FILIAL";
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            razonSocial
        );

        assertEquals(razonSocial, firmante.razonSocial());
    }

    @Test
    @DisplayName("Should handle special characters in razonSocial")
    void testRazonSocialConCaracteresEspeciales() {
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            "EMPRESA & CIA. S.A.C.-Ñ"
        );

        assertTrue(firmante.razonSocial().contains("&"));
        assertTrue(firmante.razonSocial().contains("Ñ"));
    }

    @Test
    @DisplayName("Should support uppercase and mixed case")
    void testSoportaMayusculasYMixto() {
        FirmanteDocumento firmante1 = new FirmanteDocumento(
            "20123456789",
            "EMPRESA S.A.C."
        );

        FirmanteDocumento firmante2 = new FirmanteDocumento(
            "20123456789",
            "Empresa S.A.C."
        );

        assertNotEquals(firmante1, firmante2);
    }

    @Test
    @DisplayName("Should handle long razonSocial")
    void testRazonSocialLarga() {
        String razonSocialLarga = "EMPRESA MUY LARGA CON MUCHAS PALABRAS PARA IDENTIFICARLA CORRECTAMENTE S.A.C.";
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            razonSocialLarga
        );

        assertEquals(razonSocialLarga, firmante.razonSocial());
    }

    @Test
    @DisplayName("Should handle minimal razonSocial")
    void testRazonSocialMinima() {
        FirmanteDocumento firmante = new FirmanteDocumento(
            "20123456789",
            "E"
        );

        assertEquals("E", firmante.razonSocial());
    }
}
