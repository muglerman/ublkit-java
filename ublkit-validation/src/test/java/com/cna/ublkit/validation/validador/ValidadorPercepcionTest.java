package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobantePercepcion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorPercepcion Tests")
class ValidadorPercepcionTest {

    private final ValidadorPercepcion validador = new ValidadorPercepcion();

    @Test
    @DisplayName("Valid perception should pass validation")
    void validar_percepcionValida_sinErrores() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.esValido(), "Valid perception should not have errors: " + resultado.getIncidencias());
    }

    @Test
    @DisplayName("Null perception should return error VAL-PR-001")
    void validar_nulo_retornaErrorVALPR001() {
        ResultadoValidacion resultado = validador.validar(null);

        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-001".equals(i.codigo())));
        assertEquals(1, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Missing series should return error VAL-PR-002")
    void validar_serieFaltante_retornaErrorVALPR002() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setSerie(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Empty series should return error VAL-PR-002")
    void validar_serieVacia_retornaErrorVALPR002() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setSerie("");
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Series not starting with P should return error VAL-PR-002")
    void validar_serieInvalida_noComienza_P_retornaErrorVALPR002() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setSerie("A001");
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Blank series should return error VAL-PR-002")
    void validar_serieEnBlanco_retornaErrorVALPR002() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setSerie("   ");
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Null number should return error VAL-PR-003")
    void validar_numeroNulo_retornaErrorVALPR003() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setNumero(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Zero number should return error VAL-PR-003")
    void validar_numeroCero_retornaErrorVALPR003() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setNumero(0);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Negative number should return error VAL-PR-003")
    void validar_numeroNegativo_retornaErrorVALPR003() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setNumero(-5);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing issue date should return error VAL-PR-004")
    void validar_fechaEmisionNula_retornaErrorVALPR004() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setFechaEmision(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-004".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing issuer should return error VAL-PR-005")
    void validar_emisorNulo_retornaErrorVALPR005() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setEmisor(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Invalid RUC (less than 11 digits) should return error VAL-PR-005")
    void validar_rucInvalido_menosDeDiezDigitos_retornaErrorVALPR005() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setEmisor(new EmisorDocumento("12345678", "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Invalid RUC (non-numeric) should return error VAL-PR-005")
    void validar_rucInvalido_noNumerico_retornaErrorVALPR005() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setEmisor(new EmisorDocumento("1234567890A", "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing RUC should return error VAL-PR-005")
    void validar_rucNulo_retornaErrorVALPR005() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setEmisor(new EmisorDocumento(null, "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing client should return error VAL-PR-006")
    void validar_clienteNulo_retornaErrorVALPR006() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setCliente(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Client with null document should return error VAL-PR-006")
    void validar_clienteDocumentoNulo_retornaErrorVALPR006() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setCliente(new ReceptorDocumento("1", null, "Cliente", null, null));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Client with blank document should return error VAL-PR-006")
    void validar_clienteDocumentoEnBlanco_retornaErrorVALPR006() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setCliente(new ReceptorDocumento("1", "   ", "Cliente", null, null));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing regime type should return error VAL-PR-007")
    void validar_tipoRegimenNulo_retornaErrorVALPR007() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimen(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-007".equals(i.codigo())));
    }

    @Test
    @DisplayName("Empty regime type should return error VAL-PR-007")
    void validar_tipoRegimenVacio_retornaErrorVALPR007() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimen("");
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-007".equals(i.codigo())));
    }

    @Test
    @DisplayName("Negative percentage should return error VAL-PR-008")
    void validar_porcentajeNegativo_retornaErrorVALPR008() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimenPorcentaje(new BigDecimal("-1.5"));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-008".equals(i.codigo())));
    }

    @Test
    @DisplayName("Null percentage should return error VAL-PR-008")
    void validar_porcentajeNulo_retornaErrorVALPR008() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimenPorcentaje(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-008".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing operation should return error VAL-PR-009")
    void validar_operacionNula_retornaErrorVALPR009() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setOperacion(null);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-PR-009".equals(i.codigo())));
    }

    @Test
    @DisplayName("Zero percentage should be valid")
    void validar_porcentajeCero_esValido() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimenPorcentaje(new BigDecimal("0"));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Valid percentage should be accepted")
    void validar_porcentajeValido_esAceptado() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setTipoRegimenPorcentaje(new BigDecimal("15.75"));
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Should allow high positive number")
    void validar_numeroAlto_esValido() {
        ComprobantePercepcion percepcion = crearPercepcionValida();
        percepcion.setNumero(999999);
        ResultadoValidacion resultado = validador.validar(percepcion);

        assertTrue(resultado.esValido());
    }

    // ── Helpers ──────────────────────────────────────────────────

    static ComprobantePercepcion crearPercepcionValida() {
        ComprobantePercepcion percepcion = new ComprobantePercepcion();
        percepcion.setSerie("P001");
        percepcion.setNumero(1);
        percepcion.setMoneda("PEN");
        percepcion.setFechaEmision(LocalDate.now());
        percepcion.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa", "Mi Empresa S.A.C.", null, null));
        percepcion.setCliente(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        percepcion.setTipoRegimen("01");
        percepcion.setTipoRegimenPorcentaje(new BigDecimal("5.0"));
        percepcion.setOperacion(new OperacionPR(1, LocalDate.now(), new BigDecimal("1000.00"), null, null));
        return percepcion;
    }
}
