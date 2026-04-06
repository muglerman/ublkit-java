package com.cna.ublkit.ubl.modelo.actor;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmisorDocumento Record Tests")
class EmisorDocumentoTest {

    @Test
    @DisplayName("Should create EmisorDocumento with all fields")
    void testCrearEmisorConTodosCampos() {
        Contacto contacto = new Contacto();
        Direccion direccion = new Direccion();

        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "Mi Empresa",
            "MI EMPRESA S.A.C.",
            direccion,
            contacto
        );

        assertEquals("20123456789", emisor.ruc());
        assertEquals("Mi Empresa", emisor.nombreComercial());
        assertEquals("MI EMPRESA S.A.C.", emisor.razonSocial());
        assertSame(direccion, emisor.direccion());
        assertSame(contacto, emisor.contacto());
    }

    @Test
    @DisplayName("Should create EmisorDocumento with null optional fields")
    void testCrearEmisorConOpcionalesNulos() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            null,
            "MI EMPRESA S.A.C.",
            null,
            null
        );

        assertEquals("20123456789", emisor.ruc());
        assertNull(emisor.nombreComercial());
        assertEquals("MI EMPRESA S.A.C.", emisor.razonSocial());
        assertNull(emisor.direccion());
        assertNull(emisor.contacto());
    }

    @Test
    @DisplayName("Should maintain equality based on record values")
    void testIgualdadPorValores() {
        EmisorDocumento emisor1 = new EmisorDocumento(
            "20123456789",
            "Empresa",
            "EMPRESA S.A.C.",
            null,
            null
        );

        EmisorDocumento emisor2 = new EmisorDocumento(
            "20123456789",
            "Empresa",
            "EMPRESA S.A.C.",
            null,
            null
        );

        assertEquals(emisor1, emisor2);
        assertEquals(emisor1.hashCode(), emisor2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct EmisorDocumento instances")
    void testDesigualdadPorDiferentesValores() {
        EmisorDocumento emisor1 = new EmisorDocumento(
            "20123456789",
            "Empresa1",
            "EMPRESA1 S.A.C.",
            null,
            null
        );

        EmisorDocumento emisor2 = new EmisorDocumento(
            "20999999999",
            "Empresa2",
            "EMPRESA2 S.A.C.",
            null,
            null
        );

        assertNotEquals(emisor1, emisor2);
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "Empresa",
            "EMPRESA S.A.C.",
            null,
            null
        );

        String toString = emisor.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("EmisorDocumento") || toString.contains("20123456789"));
    }

    @Test
    @DisplayName("Should accept RUC of exactly 11 digits")
    void testRucValidoOnceDigitos() {
        EmisorDocumento emisor = new EmisorDocumento("12345678901", "Empresa", "EMPRESA", null, null);
        assertEquals("12345678901", emisor.ruc());
    }

    @Test
    @DisplayName("Should accept various valid RUC formats")
    void testRucVariosFormatos() {
        String[] rucsValidos = {
            "20123456789",
            "10123456789",
            "15123456789",
            "17123456789"
        };

        for (String ruc : rucsValidos) {
            EmisorDocumento emisor = new EmisorDocumento(ruc, "Empresa", "EMPRESA", null, null);
            assertEquals(ruc, emisor.ruc());
        }
    }

    @Test
    @DisplayName("Should handle empty strings for optional text fields")
    void testCamposTextoVacios() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "",
            "",
            null,
            null
        );

        assertEquals("", emisor.nombreComercial());
        assertEquals("", emisor.razonSocial());
    }

    @Test
    @DisplayName("Should preserve direccion object reference")
    void testPreservaDireccionReference() {
        Direccion direccion = new Direccion();
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "Empresa",
            "EMPRESA S.A.C.",
            direccion,
            null
        );

        assertSame(direccion, emisor.direccion());
    }

    @Test
    @DisplayName("Should preserve contacto object reference")
    void testPreservaContactoReference() {
        Contacto contacto = new Contacto();
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "Empresa",
            "EMPRESA S.A.C.",
            null,
            contacto
        );

        assertSame(contacto, emisor.contacto());
    }

    @Test
    @DisplayName("Should handle special characters in nombres")
    void testCamposConCaracteresEspeciales() {
        EmisorDocumento emisor = new EmisorDocumento(
            "20123456789",
            "Empresa & Cia.",
            "EMPRESA & CIA. S.A.C.-Ñ",
            null,
            null
        );

        assertTrue(emisor.nombreComercial().contains("&"));
        assertTrue(emisor.razonSocial().contains("&"));
        assertTrue(emisor.razonSocial().contains("Ñ"));
    }
}
