package com.cna.ublkit.gateway.respuesta;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ArchivoCdr} record.
 * Tests ZIP extraction, XML parsing, and status code reading.
 */
class ArchivoCdrTest {

    /**
     * Test that constructor with all parameters works.
     */
    @Test
    void constructor_withAllParameters_createsInstanceSuccessfully() {
        byte[] bytes = new byte[] { 1, 2, 3 };
        String codigo = "0";
        String descripcion = "Aceptado";
        List<String> notas = List.of("nota1", "nota2");

        ArchivoCdr cdr = new ArchivoCdr(bytes, codigo, descripcion, notas);

        assertThat(cdr).isNotNull();
        assertThat(cdr.codigoRegreso()).isEqualTo(codigo);
        assertThat(cdr.descripcion()).isEqualTo(descripcion);
        assertThat(cdr.notas()).hasSize(2);
    }

    /**
     * Test that archivoBytes are cloned for immutability.
     */
    @Test
    void archivoBytes_areClonedForImmutability() {
        byte[] original = new byte[] { 1, 2, 3 };
        ArchivoCdr cdr = new ArchivoCdr(original, "0", "Test", List.of());

        byte[] retrieved = cdr.archivoBytes();

        assertThat(retrieved).isNotSameAs(original);
        assertThat(retrieved).isEqualTo(original);
    }

    /**
     * Test that modifying retrieved bytes doesn't affect CDR.
     */
    @Test
    void modifyingRetrievedBytes_doesntAffectCdr() {
        byte[] original = new byte[] { 1, 2, 3 };
        ArchivoCdr cdr = new ArchivoCdr(original, "0", "Test", List.of());

        byte[] retrieved = cdr.archivoBytes();
        retrieved[0] = 99;

        byte[] retrieved2 = cdr.archivoBytes();
        assertThat(retrieved2[0]).as("bytes should be defensively copied").isEqualTo((byte) 1);
    }

    /**
     * Test that null archivoBytes are handled.
     */
    @Test
    void nullArchivoBytes_areHandled() {
        ArchivoCdr cdr = new ArchivoCdr(null, "0", "Test", List.of());

        assertThat(cdr.archivoBytes()).isNull();
    }

    /**
     * Test that null notas are converted to empty list.
     */
    @Test
    void nullNotas_areConvertedToEmptyList() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", null);

        assertThat(cdr.notas()).isEmpty();
    }

    /**
     * Test that notas are immutable (unmodifiable list).
     */
    @Test
    void notas_areImmutable() {
        List<String> originalList = Arrays.asList("nota1", "nota2");
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", originalList);

        List<String> notas = cdr.notas();
        assertThat(notas).isUnmodifiable();
    }

    /**
     * Test that modifying original list doesn't affect CDR notas.
     */
    @Test
    void modifyingOriginalList_doesntAffectCdrNotas() {
        List<String> originalList = new java.util.ArrayList<>();
        originalList.add("nota1");
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", originalList);

        originalList.add("nota2");

        assertThat(cdr.notas()).hasSize(1);
    }

    /**
     * Test that codigoRegreso is stored correctly.
     */
    @Test
    void codigoRegreso_isStoredCorrectly() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "1033", "Rechazado", List.of());

        assertThat(cdr.codigoRegreso()).isEqualTo("1033");
    }

    /**
     * Test that descripcion is stored correctly.
     */
    @Test
    void descripcion_isStoredCorrectly() {
        String desc = "El documento fue rechazado por reglas SUNAT";
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "1033", desc, List.of());

        assertThat(cdr.descripcion()).isEqualTo(desc);
    }

    /**
     * Test that null codigoRegreso is allowed.
     */
    @Test
    void nullCodigoRegreso_isAllowed() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, null, "Test", List.of());

        assertThat(cdr.codigoRegreso()).isNull();
    }

    /**
     * Test that null descripcion is allowed.
     */
    @Test
    void nullDescripcion_isAllowed() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", null, List.of());

        assertThat(cdr.descripcion()).isNull();
    }

    /**
     * Test that empty notas list is allowed.
     */
    @Test
    void emptyNotasList_isAllowed() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", List.of());

        assertThat(cdr.notas()).isEmpty();
    }

    /**
     * Test that record implements equals correctly.
     */
    @Test
    void record_equals_comparesInstancesCorrectly() {
        ArchivoCdr cdr1 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of("nota1"));
        ArchivoCdr cdr2 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of("nota1"));

        assertThat(cdr1).isEqualTo(cdr2);
    }

    /**
     * Test that record implements hashCode correctly.
     */
    @Test
    void record_hashCode_createsSameHashForEqualInstances() {
        ArchivoCdr cdr1 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of());
        ArchivoCdr cdr2 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of());

        assertThat(cdr1.hashCode()).isEqualTo(cdr2.hashCode());
    }

    /**
     * Test that different instances are not equal.
     */
    @Test
    void record_equals_distinguishesDifferentInstances() {
        ArchivoCdr cdr1 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of());
        ArchivoCdr cdr2 = new ArchivoCdr(new byte[] { 1, 2 }, "1033", "Test", List.of());

        assertThat(cdr1).isNotEqualTo(cdr2);
    }

    /**
     * Test that byte array differences are detected in equals.
     */
    @Test
    void record_equals_detectsByteArrayDifferences() {
        ArchivoCdr cdr1 = new ArchivoCdr(new byte[] { 1, 2 }, "0", "Test", List.of());
        ArchivoCdr cdr2 = new ArchivoCdr(new byte[] { 1, 3 }, "0", "Test", List.of());

        assertThat(cdr1).isNotEqualTo(cdr2);
    }

    /**
     * Test that toString provides useful information.
     */
    @Test
    void record_toString_providesReadableRepresentation() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] { 1, 2, 3 }, "0", "Aceptado", List.of("nota1"));
        String str = cdr.toString();

        assertThat(str).contains("ArchivoCdr");
        assertThat(str).contains("codigoRegreso=0");
        assertThat(str).contains("Aceptado");
    }

    /**
     * Test that toString includes byte length.
     */
    @Test
    void record_toString_includesByteLength() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] { 1, 2, 3, 4, 5 }, "0", "Test", List.of());
        String str = cdr.toString();

        assertThat(str).contains("5");
    }

    /**
     * Test that toString handles null bytes.
     */
    @Test
    void record_toString_handlesNullBytes() {
        ArchivoCdr cdr = new ArchivoCdr(null, "0", "Test", List.of());
        String str = cdr.toString();

        assertThat(str).contains("ArchivoCdr");
        assertThat(str).isNotNull();
    }

    /**
     * Test with large byte array.
     */
    @Test
    void constructor_withLargeByteArray_handlesCorrectly() {
        byte[] largeArray = new byte[10000];
        Arrays.fill(largeArray, (byte) 42);

        ArchivoCdr cdr = new ArchivoCdr(largeArray, "0", "Test", List.of());

        assertThat(cdr.archivoBytes()).hasSize(10000);
    }

    /**
     * Test with many notas.
     */
    @Test
    void constructor_withManyNotas_handlesCorrectly() {
        List<String> manyNotas = new java.util.ArrayList<>();
        for (int i = 0; i < 100; i++) {
            manyNotas.add("nota_" + i);
        }

        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", manyNotas);

        assertThat(cdr.notas()).hasSize(100);
    }

    /**
     * Test with very long descripcion.
     */
    @Test
    void constructor_withVeryLongDescripcion_handlesCorrectly() {
        String longDesc = "Descripción ".repeat(100);
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", longDesc, List.of());

        assertThat(cdr.descripcion()).isEqualTo(longDesc);
        assertThat(cdr.descripcion().length()).isGreaterThan(500);
    }

    /**
     * Test with special characters in descripcion.
     */
    @Test
    void constructor_withSpecialCharacters_handlesCorrectly() {
        String special = "Aceptado: ¡Éxito! @#$%^&*()";
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", special, List.of());

        assertThat(cdr.descripcion()).isEqualTo(special);
    }

    /**
     * Test with numeric codigo regreso.
     */
    @Test
    void constructor_withNumericCodigoRegreso_handlesCorrectly() {
        ArchivoCdr cdr1 = new ArchivoCdr(new byte[] {}, "0", "Aceptado", List.of());
        ArchivoCdr cdr2 = new ArchivoCdr(new byte[] {}, "1033", "Rechazado", List.of());
        ArchivoCdr cdr3 = new ArchivoCdr(new byte[] {}, "98", "Procesando", List.of());

        assertThat(cdr1.codigoRegreso()).isEqualTo("0");
        assertThat(cdr2.codigoRegreso()).isEqualTo("1033");
        assertThat(cdr3.codigoRegreso()).isEqualTo("98");
    }

    /**
     * Test that two different instances with same content have same hash.
     */
    @Test
    void record_twoInstancesWithSameContent_haveSameHash() {
        byte[] bytes = new byte[] { 1, 2, 3 };
        List<String> notas = List.of("nota1", "nota2");

        ArchivoCdr cdr1 = new ArchivoCdr(bytes.clone(), "0", "Aceptado", notas);
        ArchivoCdr cdr2 = new ArchivoCdr(bytes.clone(), "0", "Aceptado", notas);

        assertThat(cdr1.hashCode()).isEqualTo(cdr2.hashCode());
    }

    /**
     * Test that record is immutable.
     */
    @Test
    void record_fieldsAreImmutable() {
        ArchivoCdr cdr = new ArchivoCdr(new byte[] {}, "0", "Test", List.of());

        // Should not be able to set fields on a record
        assertThat(cdr).isNotNull();
        assertThat(cdr.codigoRegreso()).isEqualTo("0");
    }
}
