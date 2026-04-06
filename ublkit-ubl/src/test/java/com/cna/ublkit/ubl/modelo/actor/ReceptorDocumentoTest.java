package com.cna.ublkit.ubl.modelo.actor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReceptorDocumento Record Tests")
class ReceptorDocumentoTest {

    @Test
    @DisplayName("Should create ReceptorDocumento with all fields")
    void testCrearReceptorConTodosCampos() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE S.A.C.",
            null,
            null
        );

        assertEquals("6", receptor.tipoDocIdentidad());
        assertEquals("20123456789", receptor.numDocIdentidad());
        assertEquals("CLIENTE S.A.C.", receptor.nombre());
        assertNull(receptor.direccion());
        assertNull(receptor.contacto());
    }

    @Test
    @DisplayName("Should create ReceptorDocumento with null direccion and contacto")
    void testCrearReceptorMinimo() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE S.A.C.",
            null,
            null
        );

        assertNotNull(receptor.tipoDocIdentidad());
        assertNotNull(receptor.numDocIdentidad());
        assertNotNull(receptor.nombre());
    }

    @Test
    @DisplayName("Should support consumidor final (tipo 0)")
    void testReceptorConsumidorFinal() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "0",
            "0",
            "Consumidor Final",
            null,
            null
        );

        assertEquals("0", receptor.tipoDocIdentidad());
        assertEquals("0", receptor.numDocIdentidad());
        assertEquals("Consumidor Final", receptor.nombre());
    }

    @Test
    @DisplayName("Should support DNI type document")
    void testReceptorConDni() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "1",
            "12345678",
            "Juan Pérez",
            null,
            null
        );

        assertEquals("1", receptor.tipoDocIdentidad());
        assertEquals("12345678", receptor.numDocIdentidad());
    }

    @Test
    @DisplayName("Should support pasaporte type document")
    void testReceptorConPasaporte() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "3",
            "AD123456",
            "Foreigner Person",
            null,
            null
        );

        assertEquals("3", receptor.tipoDocIdentidad());
    }

    @Test
    @DisplayName("Should maintain equality based on values")
    void testIgualdadPorValores() {
        ReceptorDocumento receptor1 = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE S.A.C.",
            null,
            null
        );

        ReceptorDocumento receptor2 = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE S.A.C.",
            null,
            null
        );

        assertEquals(receptor1, receptor2);
        assertEquals(receptor1.hashCode(), receptor2.hashCode());
    }

    @Test
    @DisplayName("Should differentiate distinct receptors")
    void testDesigualdadPorDiferentesValores() {
        ReceptorDocumento receptor1 = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE 1 S.A.C.",
            null,
            null
        );

        ReceptorDocumento receptor2 = new ReceptorDocumento(
            "6",
            "20999999999",
            "CLIENTE 2 S.A.C.",
            null,
            null
        );

        assertNotEquals(receptor1, receptor2);
    }

    @Test
    @DisplayName("Should generate meaningful string representation")
    void testToString() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "CLIENTE S.A.C.",
            null,
            null
        );

        String toString = receptor.toString();
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    @DisplayName("Should support RUC for legal entities")
    void testReceptorConRuc() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "EMPRESA CLIENTE S.A.C.",
            null,
            null
        );

        assertEquals(11, receptor.numDocIdentidad().length());
    }

    @Test
    @DisplayName("Should support various identity document lengths")
    void testDiferentesLongitudesDocumento() {
        ReceptorDocumento dniReceptor = new ReceptorDocumento(
            "1",
            "12345678",
            "PERSON NAME",
            null,
            null
        );
        assertEquals(8, dniReceptor.numDocIdentidad().length());

        ReceptorDocumento rucReceptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "EMPRESA",
            null,
            null
        );
        assertEquals(11, rucReceptor.numDocIdentidad().length());
    }

    @Test
    @DisplayName("Should handle special characters in nombre")
    void testNombreConCaracteresEspeciales() {
        ReceptorDocumento receptor = new ReceptorDocumento(
            "6",
            "20123456789",
            "EMPRESA & CIA. S.A.C.-Ñ",
            null,
            null
        );

        assertTrue(receptor.nombre().contains("&"));
        assertTrue(receptor.nombre().contains("Ñ"));
    }
}
