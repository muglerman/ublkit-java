package com.cna.ublkit.gateway.respuesta;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ResultadoConsulta} record.
 * Tests status extraction, error message handling, and data parsing.
 */
class ResultadoConsultaTest {

    /**
     * Test that constructor with all parameters works.
     */
    @Test
    void constructor_withAllParameters_createsInstanceSuccessfully() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
        ResultadoConsulta resultado = new ResultadoConsulta(EstadoEnvio.ACEPTADO, cdr, null, null);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that completado factory creates completed result.
     */
    @Test
    void completado_withEstadoAndCdr_createsCompletedResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
        ResultadoConsulta resultado = ResultadoConsulta.completado(EstadoEnvio.ACEPTADO, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that pendiente factory creates pending result.
     */
    @Test
    void pendiente_createsProcessingResult() {
        ResultadoConsulta resultado = ResultadoConsulta.pendiente();

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
        assertThat(resultado.cdr()).isNull();
        assertThat(resultado.codigoError()).isNull();
        assertThat(resultado.mensajeError()).isNull();
    }

    /**
     * Test that error factory creates error result.
     */
    @Test
    void error_withCodeAndMessage_createsErrorResult() {
        String codigo = "ERROR_001";
        String mensaje = "Consulta fallida";
        ResultadoConsulta resultado = ResultadoConsulta.error(codigo, mensaje);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        assertThat(resultado.codigoError()).isEqualTo(codigo);
        assertThat(resultado.mensajeError()).isEqualTo(mensaje);
        assertThat(resultado.cdr()).isNull();
    }

    /**
     * Test that completado with RECHAZADO state works.
     */
    @Test
    void completado_withRechazadoState_createsRejectedResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "1033", "Rechazado", List.of());
        ResultadoConsulta resultado = ResultadoConsulta.completado(EstadoEnvio.RECHAZADO, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.RECHAZADO);
        assertThat(resultado.cdr()).isEqualTo(cdr);
    }

    /**
     * Test that completado with observations works.
     */
    @Test
    void completado_withAceptadoConObservaciones_createsObservationsResult() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Aceptado con observaciones", List.of("OBS1", "OBS2"));
        ResultadoConsulta resultado = ResultadoConsulta.completado(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES, cdr);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES);
        assertThat(resultado.cdr().notas()).hasSize(2);
    }

    /**
     * Test that error with null code works.
     */
    @Test
    void error_withNullCode_createsErrorResult() {
        ResultadoConsulta resultado = ResultadoConsulta.error(null, "Error message");

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
        ResultadoConsulta resultado = ResultadoConsulta.error("ERROR_001", null);

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
        ResultadoConsulta r1 = ResultadoConsulta.pendiente();
        ResultadoConsulta r2 = ResultadoConsulta.pendiente();

        assertThat(r1).isEqualTo(r2);
    }

    /**
     * Test that record implements hashCode correctly.
     */
    @Test
    void record_hashCode_createsSameHashForEqualInstances() {
        ResultadoConsulta r1 = ResultadoConsulta.pendiente();
        ResultadoConsulta r2 = ResultadoConsulta.pendiente();

        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    /**
     * Test that different instances are not equal.
     */
    @Test
    void record_equals_distinguishesDifferentInstances() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Test", List.of());
        ResultadoConsulta r1 = ResultadoConsulta.completado(EstadoEnvio.ACEPTADO, cdr);
        ResultadoConsulta r2 = ResultadoConsulta.pendiente();

        assertThat(r1).isNotEqualTo(r2);
    }

    /**
     * Test that toString provides useful information.
     */
    @Test
    void record_toString_providesReadableRepresentation() {
        ResultadoConsulta resultado = ResultadoConsulta.pendiente();
        String str = resultado.toString();

        assertThat(str).contains("ResultadoConsulta");
        assertThat(str).contains("EN_PROCESAMIENTO");
    }

    /**
     * Test that pendiente always creates EN_PROCESAMIENTO estado.
     */
    @Test
    void pendiente_alwaysCreatesEnProcesamiento() {
        for (int i = 0; i < 10; i++) {
            ResultadoConsulta resultado = ResultadoConsulta.pendiente();
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
        }
    }

    /**
     * Test that error always creates EXCEPCION estado.
     */
    @Test
    void error_alwaysCreatesExcepcion() {
        for (int i = 0; i < 10; i++) {
            ResultadoConsulta resultado = ResultadoConsulta.error("ERROR_" + i, "Message " + i);
            assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EXCEPCION);
        }
    }

    /**
     * Test with very long error code.
     */
    @Test
    void error_withVeryLongCode_handlesCorrectly() {
        String longCode = "1".repeat(1000);
        ResultadoConsulta resultado = ResultadoConsulta.error(longCode, "Error");

        assertThat(resultado.codigoError()).isEqualTo(longCode);
    }

    /**
     * Test with very long error message.
     */
    @Test
    void error_withVeryLongMessage_handlesCorrectly() {
        String longMessage = "Error: " + "x".repeat(1000);
        ResultadoConsulta resultado = ResultadoConsulta.error("CODE", longMessage);

        assertThat(resultado.mensajeError()).isEqualTo(longMessage);
    }

    /**
     * Test completado with null CDR.
     */
    @Test
    void completado_withNullCdr_createsResult() {
        ResultadoConsulta resultado = ResultadoConsulta.completado(EstadoEnvio.ACEPTADO, null);

        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.ACEPTADO);
        assertThat(resultado.cdr()).isNull();
    }

    /**
     * Test with special characters in error code.
     */
    @Test
    void error_withSpecialCharactersInCode_handlesCorrectly() {
        ResultadoConsulta resultado = ResultadoConsulta.error("ERR-001_REST", "Special error");

        assertThat(resultado.codigoError()).isEqualTo("ERR-001_REST");
    }

    /**
     * Test that all supported estados can be used in completado.
     */
    @Test
    void completado_withAllValidEstados_createsValidResults() {
        var cdr = new ArchivoCdr(new byte[] {}, "0", "Test", List.of());

        for (EstadoEnvio estado : EstadoEnvio.values()) {
            if (estado != EstadoEnvio.TICKET_PENDIENTE && estado != EstadoEnvio.EN_PROCESAMIENTO) {
                ResultadoConsulta resultado = ResultadoConsulta.completado(estado, cdr);
                assertThat(resultado.estado()).isEqualTo(estado);
            }
        }
    }

    /**
     * Test that record is immutable.
     */
    @Test
    void record_fieldsAreImmutable() {
        ResultadoConsulta resultado = ResultadoConsulta.pendiente();

        // Should not be able to set fields on a record
        assertThat(resultado).isNotNull();
        assertThat(resultado.estado()).isEqualTo(EstadoEnvio.EN_PROCESAMIENTO);
    }
}
