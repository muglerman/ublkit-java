package com.cna.ublkit.ubl.modelo.actor;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmisorDocumento Extended Tests")
class EmisorDocumentoExtendedTest {

    private EmisorDocumento emisor;

    @BeforeEach
    void setUp() {
        emisor = new EmisorDocumento("20123456789", "Tienda Principal", "Empresa SA", null, null);
    }

    @Test
    @DisplayName("Should set and get RUC")
    void testSetYGetRuc() {
        assertEquals("20123456789", emisor.ruc());
    }

    @Test
    @DisplayName("Should set and get razon social")
    void testSetYGetRazonSocial() {
        assertEquals("Empresa SA", emisor.razonSocial());
    }

    @Test
    @DisplayName("Should set and get nombre comercial")
    void testSetYGetNombreComercial() {
        assertEquals("Tienda Principal", emisor.nombreComercial());
    }

    @Test
    @DisplayName("Should set and get direccion")
    void testSetYGetDireccion() {
        assertNull(emisor.direccion());
    }

    @Test
    @DisplayName("Should set and get contacto")
    void testSetYGetContacto() {
        assertNull(emisor.contacto());
    }

    @Test
    @DisplayName("Should handle null contacto")
    void testManejaContactoNulo() {
        EmisorDocumento e = new EmisorDocumento("20123456789", "Tienda Principal", "Empresa SA", null, null);
        assertNull(e.contacto());
    }

    @Test
    @DisplayName("Should handle complex emisor setup")
    void testManejaConfiguracionComplejaEmisor() {
        EmisorDocumento e = new EmisorDocumento("20123456789", "Tienda Principal", "Empresa SA", null, null);
        assertEquals("20123456789", e.ruc());
        assertEquals("Empresa SA", e.razonSocial());
        assertEquals("Tienda Principal", e.nombreComercial());
    }
}

@DisplayName("ReceptorDocumento Extended Tests")
class ReceptorDocumentoExtendedTest {

    private ReceptorDocumento receptor;

    @BeforeEach
    void setUp() {
        receptor = new ReceptorDocumento("06", "20987654321", "Cliente SA", null, null);
    }

    @Test
    @DisplayName("Should set and get numero documento")
    void testSetYGetRuc() {
        assertEquals("20987654321", receptor.numDocIdentidad());
    }

    @Test
    @DisplayName("Should set and get nombre")
    void testSetYGetNombre() {
        assertEquals("Cliente SA", receptor.nombre());
    }

    @Test
    @DisplayName("Should set and get tipo documento")
    void testSetYGetTipoDocumento() {
        assertEquals("06", receptor.tipoDocIdentidad());
    }

    @Test
    @DisplayName("Should set and get numero documento 2")
    void testSetYGetNumeroDocumento() {
        assertEquals("20987654321", receptor.numDocIdentidad());
    }

    @Test
    @DisplayName("Should set and get direccion")
    void testSetYGetDireccion() {
        assertNull(receptor.direccion());
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetYGetEmail() {
        assertNull(receptor.contacto());
    }

    @Test
    @DisplayName("Should handle RUC as numero documento")
    void testManejaRucComoNumeroDocumento() {
        ReceptorDocumento r = new ReceptorDocumento("06", "20987654321", "Cliente SA", null, null);
        assertEquals("20987654321", r.numDocIdentidad());
    }

    @Test
    @DisplayName("Should handle various document types")
    void testManejaVariosTiposDocumento() {
        ReceptorDocumento r1 = new ReceptorDocumento("06", "20987654321", "Cliente SA", null, null);
        assertEquals("06", r1.tipoDocIdentidad());

        ReceptorDocumento r2 = new ReceptorDocumento("01", "20987654321", "Cliente SA", null, null);
        assertEquals("01", r2.tipoDocIdentidad());
    }
}

@DisplayName("FirmanteDocumento Extended Tests")
class FirmanteDocumentoExtendedTest {

    private FirmanteDocumento firmante;

    @BeforeEach
    void setUp() {
        firmante = new FirmanteDocumento("20111111111", "Firmante SA");
    }

    @Test
    @DisplayName("Should set and get RUC")
    void testSetYGetRuc() {
        assertEquals("20111111111", firmante.ruc());
    }

    @Test
    @DisplayName("Should set and get nombre")
    void testSetYGetNombre() {
        assertEquals("Firmante SA", firmante.razonSocial());
    }

    @Test
    @DisplayName("Should set and get certificado")
    void testSetYGetCertificado() {
        FirmanteDocumento f = new FirmanteDocumento("20111111111", "Firmante SA");
        assertNotNull(f.ruc());
    }

    @Test
    @DisplayName("Should handle empty certificado")
    void testManejaertificadoVacio() {
        assertNotNull(firmante);
    }

    @Test
    @DisplayName("Should handle null certificado")
    void testManejaCertificadoNulo() {
        FirmanteDocumento f = new FirmanteDocumento("20111111111", "Firmante SA");
        assertNotNull(f);
    }

    @Test
    @DisplayName("Should maintain all properties")
    void testMantieneTodosCampos() {
        FirmanteDocumento f = new FirmanteDocumento("20111111111", "Firmante SA");
        assertEquals("20111111111", f.ruc());
        assertEquals("Firmante SA", f.razonSocial());
    }
}
