package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorComunicacionBajaTest {

    private final ValidadorComunicacionBaja validador = new ValidadorComunicacionBaja();

    @Test
    void validar_bajaValida_sinErrores() {
        ComunicacionBaja baja = crearBajaValida();
        ResultadoValidacion resultado = validador.validar(baja);
        assertTrue(resultado.esValido(), "Baja válida no debe tener errores: " + resultado.getIncidencias());
    }

    @Test
    void validar_nulo_retornaError() {
        ResultadoValidacion resultado = validador.validar(null);
        assertFalse(resultado.esValido());
        assertEquals("VAL-CB-001", resultado.getIncidencias().getFirst().codigo());
    }

    @Test
    void validar_sinFechaEmision_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setFechaEmision(null);
        assertFalse(validador.validar(baja).esValido());
    }

    @Test
    void validar_sinFechaComprobantes_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setFechaEmisionComprobantes(null);
        assertFalse(validador.validar(baja).esValido());
    }

    @Test
    void validar_referenceDateMayorQueIssueDate_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setFechaEmision(LocalDate.of(2026, 4, 14));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 15));
        ResultadoValidacion resultado = validador.validar(baja);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-CB-003b".equals(i.codigo())));
    }

    @Test
    void validar_sinEmisor_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setEmisor(null);
        assertFalse(validador.validar(baja).esValido());
    }

    @Test
    void validar_emisorRucInvalido_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setEmisor(new EmisorDocumento("12345", "Empresa", "Empresa", null, null));
        ResultadoValidacion resultado = validador.validar(baja);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-CB-005b".equals(i.codigo())));
    }

    @Test
    void validar_sinComprobantes_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setComprobantes(null);
        assertFalse(validador.validar(baja).esValido());
    }

    @Test
    void validar_comprobantesSinSustento_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setComprobantes(List.of(new ItemBaja("F001", 1, "01", "")));
        ResultadoValidacion resultado = validador.validar(baja);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-CB-010".equals(i.codigo())));
    }

    @Test
    void validar_comprobanteSinTipo_retornaError() {
        ComunicacionBaja baja = crearBajaValida();
        baja.setComprobantes(List.of(new ItemBaja("F001", 1, null, "Motivo")));
        ResultadoValidacion resultado = validador.validar(baja);
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-CB-007".equals(i.codigo())));
    }

    static ComunicacionBaja crearBajaValida() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(1);
        baja.setEmisor(new EmisorDocumento("20123456789", "EMPRESA PRUEBA S.A.C.", "EMPRESA PRUEBA S.A.C.", null, null));
        baja.setComprobantes(List.of(new ItemBaja("F001", 1, "01", "Error en RUC del cliente")));
        return baja;
    }
}
