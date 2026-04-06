package com.cna.ublkit.gateway.respuesta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link EstadoEnvio} enum.
 * Tests all status codes and enum functionality.
 */
class EstadoEnvioTest {

    /**
     * Test that ACEPTADO enum value exists.
     */
    @Test
    void aceptado_enumValueExists() {
        assertThat(EstadoEnvio.ACEPTADO).isNotNull();
    }

    /**
     * Test that ACEPTADO_CON_OBSERVACIONES enum value exists.
     */
    @Test
    void aceptadoConObservaciones_enumValueExists() {
        assertThat(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES).isNotNull();
    }

    /**
     * Test that RECHAZADO enum value exists.
     */
    @Test
    void rechazado_enumValueExists() {
        assertThat(EstadoEnvio.RECHAZADO).isNotNull();
    }

    /**
     * Test that EXCEPCION enum value exists.
     */
    @Test
    void excepcion_enumValueExists() {
        assertThat(EstadoEnvio.EXCEPCION).isNotNull();
    }

    /**
     * Test that TICKET_PENDIENTE enum value exists.
     */
    @Test
    void ticketPendiente_enumValueExists() {
        assertThat(EstadoEnvio.TICKET_PENDIENTE).isNotNull();
    }

    /**
     * Test that EN_PROCESAMIENTO enum value exists.
     */
    @Test
    void enProcesamiento_enumValueExists() {
        assertThat(EstadoEnvio.EN_PROCESAMIENTO).isNotNull();
    }

    /**
     * Test that all enum values are distinct.
     */
    @Test
    void allEnumValues_areDistinct() {
        EstadoEnvio[] values = EstadoEnvio.values();

        assertThat(values).hasSize(6);
        assertThat(values).contains(
                EstadoEnvio.ACEPTADO,
                EstadoEnvio.ACEPTADO_CON_OBSERVACIONES,
                EstadoEnvio.RECHAZADO,
                EstadoEnvio.EXCEPCION,
                EstadoEnvio.TICKET_PENDIENTE,
                EstadoEnvio.EN_PROCESAMIENTO
        );
    }

    /**
     * Test that each enum value is not null.
     */
    @ParameterizedTest
    @EnumSource(EstadoEnvio.class)
    void eachEnumValue_isNotNull(EstadoEnvio estado) {
        assertThat(estado).isNotNull();
    }

    /**
     * Test that ACEPTADO is identified correctly.
     */
    @Test
    void aceptado_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.ACEPTADO;

        assertThat(estado.name()).isEqualTo("ACEPTADO");
        assertThat(estado.ordinal()).isEqualTo(0);
    }

    /**
     * Test that ACEPTADO_CON_OBSERVACIONES is identified correctly.
     */
    @Test
    void aceptadoConObservaciones_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.ACEPTADO_CON_OBSERVACIONES;

        assertThat(estado.name()).isEqualTo("ACEPTADO_CON_OBSERVACIONES");
    }

    /**
     * Test that RECHAZADO is identified correctly.
     */
    @Test
    void rechazado_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.RECHAZADO;

        assertThat(estado.name()).isEqualTo("RECHAZADO");
    }

    /**
     * Test that EXCEPCION is identified correctly.
     */
    @Test
    void excepcion_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.EXCEPCION;

        assertThat(estado.name()).isEqualTo("EXCEPCION");
    }

    /**
     * Test that TICKET_PENDIENTE is identified correctly.
     */
    @Test
    void ticketPendiente_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.TICKET_PENDIENTE;

        assertThat(estado.name()).isEqualTo("TICKET_PENDIENTE");
    }

    /**
     * Test that EN_PROCESAMIENTO is identified correctly.
     */
    @Test
    void enProcesamiento_isIdentifiedCorrectly() {
        EstadoEnvio estado = EstadoEnvio.EN_PROCESAMIENTO;

        assertThat(estado.name()).isEqualTo("EN_PROCESAMIENTO");
    }

    /**
     * Test that enum values have correct ordinals.
     */
    @Test
    void enumValues_haveCorrectOrdinals() {
        EstadoEnvio[] values = EstadoEnvio.values();

        assertThat(values[0].ordinal()).isEqualTo(0);
        assertThat(values[1].ordinal()).isEqualTo(1);
        assertThat(values[2].ordinal()).isEqualTo(2);
        assertThat(values[3].ordinal()).isEqualTo(3);
        assertThat(values[4].ordinal()).isEqualTo(4);
        assertThat(values[5].ordinal()).isEqualTo(5);
    }

    /**
     * Test that enum can be used in switch statement.
     */
    @Test
    void enum_canBeUsedInSwitch() {
        EstadoEnvio estado = EstadoEnvio.ACEPTADO;
        String result = switch (estado) {
            case ACEPTADO -> "accepted";
            case ACEPTADO_CON_OBSERVACIONES -> "accepted_with_observations";
            case RECHAZADO -> "rejected";
            case EXCEPCION -> "exception";
            case TICKET_PENDIENTE -> "ticket_pending";
            case EN_PROCESAMIENTO -> "processing";
        };

        assertThat(result).isEqualTo("accepted");
    }

    /**
     * Test that enum can be compared with equals.
     */
    @Test
    void enum_canBeComparedWithEquals() {
        EstadoEnvio estado1 = EstadoEnvio.ACEPTADO;
        EstadoEnvio estado2 = EstadoEnvio.ACEPTADO;
        EstadoEnvio estado3 = EstadoEnvio.RECHAZADO;

        assertThat(estado1).isEqualTo(estado2);
        assertThat(estado1).isNotEqualTo(estado3);
    }

    /**
     * Test that enum can be used in collections.
     */
    @Test
    void enum_canBeUsedInCollections() {
        var states = java.util.List.of(EstadoEnvio.ACEPTADO, EstadoEnvio.RECHAZADO);

        assertThat(states).contains(EstadoEnvio.ACEPTADO);
        assertThat(states).hasSize(2);
    }

    /**
     * Test that valueOf works for enum values.
     */
    @Test
    void enum_valueOfWorks() {
        EstadoEnvio estado = EstadoEnvio.valueOf("ACEPTADO");

        assertThat(estado).isEqualTo(EstadoEnvio.ACEPTADO);
    }

    /**
     * Test that success states can be identified.
     */
    @Test
    void successStates_canBeIdentified() {
        assertThat(EstadoEnvio.ACEPTADO).isIn(EstadoEnvio.values());
        assertThat(EstadoEnvio.ACEPTADO_CON_OBSERVACIONES).isIn(EstadoEnvio.values());
    }

    /**
     * Test that error states can be identified.
     */
    @Test
    void errorStates_canBeIdentified() {
        assertThat(EstadoEnvio.RECHAZADO).isIn(EstadoEnvio.values());
        assertThat(EstadoEnvio.EXCEPCION).isIn(EstadoEnvio.values());
    }

    /**
     * Test that processing states can be identified.
     */
    @Test
    void processingStates_canBeIdentified() {
        assertThat(EstadoEnvio.TICKET_PENDIENTE).isIn(EstadoEnvio.values());
        assertThat(EstadoEnvio.EN_PROCESAMIENTO).isIn(EstadoEnvio.values());
    }

    /**
     * Test that enum can be streamed.
     */
    @Test
    void enum_canBeStreamed() {
        long count = java.util.Arrays.stream(EstadoEnvio.values()).count();

        assertThat(count).isEqualTo(6);
    }

    /**
     * Test that enum values are consistent.
     */
    @Test
    void enum_valuesAreConsistent() {
        EstadoEnvio[] values1 = EstadoEnvio.values();
        EstadoEnvio[] values2 = EstadoEnvio.values();

        assertThat(values1).isEqualTo(values2);
    }

    /**
     * Test that enum toString works.
     */
    @Test
    void enum_toStringWorks() {
        EstadoEnvio estado = EstadoEnvio.ACEPTADO;

        assertThat(estado.toString()).isEqualTo("ACEPTADO");
    }

    /**
     * Test each enum value individually.
     */
    @ParameterizedTest
    @EnumSource(EstadoEnvio.class)
    void eachEnumValue_returnsValidName(EstadoEnvio estado) {
        assertThat(estado.name()).isNotEmpty();
        assertThat(estado.name()).isNotBlank();
    }
}
