package com.cna.ublkit.validation.validador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.ComprobanteRetencion;
import com.cna.ublkit.ubl.modelo.sunat.percepcionretencion.OperacionPR;
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorRetencion Tests")
class ValidadorRetencionTest {

    private final ValidadorRetencion validador = new ValidadorRetencion();

    @Test
    @DisplayName("Valid retention should pass validation")
    void validar_retencionValida_sinErrores() {
        ComprobanteRetencion retencion = crearRetencionValida();
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido(), "Valid retention should not have errors: " + resultado.getIncidencias());
    }

    @Test
    @DisplayName("Null retention should return error VAL-RT-001")
    void validar_nulo_retornaErrorVALRT001() {
        ResultadoValidacion resultado = validador.validar(null);

        assertFalse(resultado.esValido());
        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-001".equals(i.codigo())));
        assertEquals(1, resultado.getIncidencias().size());
    }

    @Test
    @DisplayName("Missing series should return error VAL-RT-002")
    void validar_serieFaltante_retornaErrorVALRT002() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Empty series should return error VAL-RT-002")
    void validar_serieVacia_retornaErrorVALRT002() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie("");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Series not starting with R should return error VAL-RT-002")
    void validar_serieInvalida_noComienza_R_retornaErrorVALRT002() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie("P001");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Blank series should return error VAL-RT-002")
    void validar_serieEnBlanco_retornaErrorVALRT002() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie("   ");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-002".equals(i.codigo())));
    }

    @Test
    @DisplayName("Null number should return error VAL-RT-003")
    void validar_numeroNulo_retornaErrorVALRT003() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setNumero(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Zero number should return error VAL-RT-003")
    void validar_numeroCero_retornaErrorVALRT003() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setNumero(0);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Negative number should return error VAL-RT-003")
    void validar_numeroNegativo_retornaErrorVALRT003() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setNumero(-5);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-003".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing issue date should return error VAL-RT-004")
    void validar_fechaEmisionNula_retornaErrorVALRT004() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setFechaEmision(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-004".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing issuer should return error VAL-RT-005")
    void validar_emisorNulo_retornaErrorVALRT005() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setEmisor(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Invalid RUC (less than 11 digits) should return error VAL-RT-005")
    void validar_rucInvalido_menosDeDiezDigitos_retornaErrorVALRT005() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setEmisor(new EmisorDocumento("12345678", "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Invalid RUC (non-numeric) should return error VAL-RT-005")
    void validar_rucInvalido_noNumerico_retornaErrorVALRT005() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setEmisor(new EmisorDocumento("1234567890A", "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing RUC should return error VAL-RT-005")
    void validar_rucNulo_retornaErrorVALRT005() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setEmisor(new EmisorDocumento(null, "Nombre", "Razón", null, null));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-005".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing client should return error VAL-RT-006")
    void validar_clienteNulo_retornaErrorVALRT006() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setCliente(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Client with null document should return error VAL-RT-006")
    void validar_clienteDocumentoNulo_retornaErrorVALRT006() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setCliente(new ReceptorDocumento("1", null, "Cliente", null, null));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Client with blank document should return error VAL-RT-006")
    void validar_clienteDocumentoEnBlanco_retornaErrorVALRT006() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setCliente(new ReceptorDocumento("1", "   ", "Cliente", null, null));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-006".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing regime type should return error VAL-RT-007")
    void validar_tipoRegimenNulo_retornaErrorVALRT007() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimen(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-007".equals(i.codigo())));
    }

    @Test
    @DisplayName("Empty regime type should return error VAL-RT-007")
    void validar_tipoRegimenVacio_retornaErrorVALRT007() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimen("");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-007".equals(i.codigo())));
    }

    @Test
    @DisplayName("Negative percentage should return error VAL-RT-008")
    void validar_porcentajeNegativo_retornaErrorVALRT008() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimenPorcentaje(new BigDecimal("-1.5"));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-008".equals(i.codigo())));
    }

    @Test
    @DisplayName("Null percentage should return error VAL-RT-008")
    void validar_porcentajeNulo_retornaErrorVALRT008() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimenPorcentaje(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-008".equals(i.codigo())));
    }

    @Test
    @DisplayName("Missing operation should return error VAL-RT-009")
    void validar_operacionNula_retornaErrorVALRT009() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setOperacion(null);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.getIncidencias().stream().anyMatch(i -> "VAL-RT-009".equals(i.codigo())));
    }

    @Test
    @DisplayName("Zero percentage should be valid")
    void validar_porcentajeCero_esValido() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimenPorcentaje(new BigDecimal("0"));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Valid percentage should be accepted")
    void validar_porcentajeValido_esAceptado() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setTipoRegimenPorcentaje(new BigDecimal("15.75"));
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Should allow high positive number")
    void validar_numeroAlto_esValido() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setNumero(999999);
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Valid retention with R001 series")
    void validar_serieValidaR001_esValido() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie("R001");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido());
    }

    @Test
    @DisplayName("Valid retention with R999 series")
    void validar_serieValidaR999_esValido() {
        ComprobanteRetencion retencion = crearRetencionValida();
        retencion.setSerie("R999");
        ResultadoValidacion resultado = validador.validar(retencion);

        assertTrue(resultado.esValido());
    }

    // ── Helpers ──────────────────────────────────────────────────

    static ComprobanteRetencion crearRetencionValida() {
        ComprobanteRetencion retencion = new ComprobanteRetencion();
        retencion.setSerie("R001");
        retencion.setNumero(1);
        retencion.setMoneda("PEN");
        retencion.setFechaEmision(LocalDate.now());
        retencion.setEmisor(new EmisorDocumento("20000000001", "Mi Empresa", "Mi Empresa S.A.C.", null, null));
        retencion.setCliente(new ReceptorDocumento("6", "20100000002", "Cliente SAC", null, null));
        retencion.setTipoRegimen("01");
        retencion.setTipoRegimenPorcentaje(new BigDecimal("3.0"));
        retencion.setOperacion(new OperacionPR(1, LocalDate.now(), new BigDecimal("1000.00"), null, null));
        return retencion;
    }
}
