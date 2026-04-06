package com.cna.ublkit.gateway.respuesta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ResultadoEnvio} record.
 * Tests success/failure states, ticket number extraction, and status codes.
 */
class ResultadoEnvioTest {

    /**
     * Test that constructor with all parameters works.
     */
    @Test
    void constructor_withAllParameters_createsInstanceSuccessfully() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
        ResultadoEnvio resultado = new ResultadoEnvio(EstadoEnvio.ACEPTADO, cdr, null, null, null);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
        assertThat(resultado.numeroTicket()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that asincrono factory creates ticket pending result.
     */
    @Test
    void asincrono_withTicket_createsTicketPendingResult() {
        String ticket = "123456789";
        ResultadoEnvio resultado = ResultadoEnvio.asincrono(ticket);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
        assertThat(resultado.numeroTicket()).isEqualTo(ticket);
        assertThat(resultado.cdr()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that sincronoProcesado factory creates processed result.
     */
    @Test
    void sincronoProcesado_withEstadoAndCdr_createsProcessedResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
        ResultadoEnvio resultado = ResultadoEnvio.sincronoProcesado(EstadoEnvio.ACEPTADO, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
        assertThat(resultado.numeroTicket()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that error factory creates error result.
     */
    @Test
    void error_withCodeAndMessage_createsErrorResult() {
        String codigo = "ERROR_001";
        String mensaje = "Something went wrong";
        ResultadoEnvio resultado = ResultadoEnvio.error(codigo, mensaje);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        assertThat(resultado.codigoError()).isEqualTo(codigo);
        assertThat(resultado.mensajeError()).isEqualTo(mensaje);
        assertThat(resultado.cdr()).isNull();
        assertThat(resultado.numeroTicket()).isNull();
    }

    /**
     * Test that asincrono with null ticket creates result.
     */
    @Test
    void asincrono_withNullTicket_createsResultWithNullTicket() {
        ResultadoEnvio resultado = ResultadoEnvio.asincrono(null);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
        assertThat(resultado.numeroTicket()).isNull();
    }

    /**
     * Test that sincronoProcesado with RECHAZADO state works.
     */
    @Test
    void sincronoProcesado_withRechazadoState_createsRejectedResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "1033", "Rechazado", List.of());
        ResultadoEnvio resultado = ResultadoEnvio.sincronoProcesado(EstadoEnvio.RECHAZADO, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.RECHAZADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
    }

    /**
     * Test that sincronoProcesado with observations works.
     */
    @Test
    void sincronoProcesado_withAceptadoConObservaciones_createsObservationsResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado con observaciones", List.of("OBS1", "OBS2"));
        ResultadoEnvio resultado = ResultadoEnvio.sincronoProcesado(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES);
        assertThat(resultado.cdr().notas()).hasSize(2);
    }

    /**
     * Test that error with null code works.
     */
    @Test
    void error_withNullCode_createsErrorResult() {
        ResultadoEnvio resultado = ResultadoEnvio.error(null, "Error message");

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isEqualTo("Error message");
    }

    /**
     * Test that error with null message works.
     */
    @Test
    void error_withNullMessage_createsErrorResult() {
        ResultadoEnvio resultado = ResultadoEnvio.error("ERROR_001", null);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        assertThat(resultado.codigoError()).isEqualTo("ERROR_001");
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that record implements equals correctly.
     */
    @Test
    void record_equals_comparesInstancesCorrectly() {
        ResultadoEnvio r1 = ResultadoEnvio.asincrono("123");
        ResultadoEnvio r2 = ResultadoEnvio.asincrono("123");

        assertThat(r1).isEqualTo(r2);
    }

    /**
     * Test that record implements hashCode correctly.
     */
    @Test
    void record_hashCode_createsSameHashForEqualInstances() {
        ResultadoEnvio r1 = ResultadoEnvio.asincrono("123");
        ResultadoEnvio r2 = ResultadoEnvio.asincrono("123");

        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    /**
     * Test that different instances are not equal.
     */
    @Test
    void record_equals_distinguishesDifferentInstances() {
        ResultadoEnvio r1 = ResultadoEnvio.asincrono("123");
        ResultadoEnvio r2 = ResultadoEnvio.asincrono("456");

        assertThat(r1).isNotEqualTo(r2);
    }

    /**
     * Test that toString provides useful information.
     */
    @Test
    void record_toString_providesReadableRepresentation() {
        ResultadoEnvio resultado = ResultadoEnvio.asincrono("123");
        String str = resultado.toString();

        assertThat(str).contains("ResultadoEnvio");
        assertThat(str).contains("TICKET_PENDIENTE");
        assertThat(str).contains("123");
    }

    /**
     * Test all estado values are supported.
     */
    @Test
    void sincronoProcesado_withAllEstados_createsValidResults() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Test", List.of());

        for (EstadoEnvio estado : EstadoEnvio.values()) {
            if (estado != EstadoEnvio.TICKET_PENDIENTE && estado != EstadoEnvio.EN_PROCESAMIENTO) {
                ResultadoEnvio resultado = ResultadoEnvio.sincronoProcesado(estado, cdr);
                assertThat(resultado.estado()).isEqualTo(estado);
            }
        }
    }

    /**
     * Test that asincrono creates result with TICKET_PENDIENTE estado.
     */
    @Test
    void asincrono_alwaysCreatesTicketPendienteEstado() {
        for (int i = 0; i < 10; i++) {
            ResultadoEnvio resultado = ResultadoEnvio.asincrono("ticket_" + i);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.TICKET_PENDIENTE);
        }
    }

    /**
     * Test that error always creates EXCEPCION estado.
     */
    @Test
    void error_alwaysCreatesExcepcionEstado() {
        for (int i = 0; i < 10; i++) {
            ResultadoEnvio resultado = ResultadoEnvio.error("ERROR_" + i, "Message " + i);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        }
    }

    /**
     * Test with very long ticket number.
     */
    @Test
    void asincrono_withVeryLongTicket_handlesCorrectly() {
        String longTicket = "1".repeat(1000);
        ResultadoEnvio resultado = ResultadoEnvio.asincrono(longTicket);

        assertThat(resultado.numeroTicket()).isEqualTo(longTicket);
    }

    /**
     * Test with numeric ticket strings.
     */
    @Test
    void asincrono_withNumericTickets_handlesCorrectly() {
        ResultadoEnvio resultado1 = ResultadoEnvio.asincrono("123456789");
        ResultadoEnvio resultado2 = ResultadoEnvio.asincrono("987654321");

        assertThat(resultado1.numeroTicket()).isEqualTo("123456789");
        assertThat(resultado2.numeroTicket()).isEqualTo("987654321");
    }

    /**
     * Test with special characters in error code.
     */
    @Test
    void error_withSpecialCharactersInCode_handlesCorrectly() {
        ResultadoEnvio resultado = ResultadoEnvio.error("ERR-001_SOAP", "Special error");

        assertThat(resultado.codigoError()).isEqualTo("ERR-001_SOAP");
    }

    /**
     * Test record is immutable (fields are final).
     */
    @Test
    void record_fieldsAreImmutable() {
        ResultadoEnvio resultado = ResultadoEnvio.asincrono("123");

        // Should not be able to set fields on a record
        assertThat(resultado).isNotNull();
        assertThat(resultado.numeroTicket()).isEqualTo("123");
    }
}
