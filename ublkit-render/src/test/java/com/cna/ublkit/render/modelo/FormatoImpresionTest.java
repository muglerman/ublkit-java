package com.cna.ublkit.render.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for FormatoImpresion enum.
 * Tests all print format constants and enum operations.
 *
 * @since 0.2.0
 */
@DisplayName("FormatoImpresion - Print Format Enum")
class FormatoImpresionTest {

    @Nested
    @DisplayName("Enum Constants Tests")
    class EnumConstantsTests {

        @Test
        @DisplayName("Should have A4 format")
        void shouldHaveA4Format() {
            assertNotNull(FormatoImpresion.A4);
            assertEquals("A4", FormatoImpresion.A4.name());
        }

        @Test
        @DisplayName("Should have A5 format")
        void shouldHaveA5Format() {
            assertNotNull(FormatoImpresion.A5);
            assertEquals("A5", FormatoImpresion.A5.name());
        }

        @Test
        @DisplayName("Should have TICKET_80MM format")
        void shouldHaveTicket80MmFormat() {
            assertNotNull(FormatoImpresion.TICKET_80MM);
            assertEquals("TICKET_80MM", FormatoImpresion.TICKET_80MM.name());
        }

        @Test
        @DisplayName("Should have TICKET_58MM format")
        void shouldHaveTicket58MmFormat() {
            assertNotNull(FormatoImpresion.TICKET_58MM);
            assertEquals("TICKET_58MM", FormatoImpresion.TICKET_58MM.name());
        }
    }

    @Nested
    @DisplayName("Enum Values Tests")
    class EnumValuesTests {

        @Test
        @DisplayName("Should have exactly 4 formats")
        void shouldHaveExactly4Formats() {
            FormatoImpresion[] formats = FormatoImpresion.values();

            assertEquals(4, formats.length, "Should have exactly 4 formats");
        }

        @Test
        @DisplayName("Should return all formats with values()")
        void shouldReturnAllFormatsWithValues() {
            FormatoImpresion[] formats = FormatoImpresion.values();

            assertTrue(contains(formats, FormatoImpresion.A4));
            assertTrue(contains(formats, FormatoImpresion.A5));
            assertTrue(contains(formats, FormatoImpresion.TICKET_80MM));
            assertTrue(contains(formats, FormatoImpresion.TICKET_58MM));
        }
    }

    @Nested
    @DisplayName("Enum Operations Tests")
    class EnumOperationsTests {

        @ParameterizedTest
        @EnumSource(FormatoImpresion.class)
        @DisplayName("Should parse all format names")
        void shouldParseAllFormatNames(FormatoImpresion formato) {
            FormatoImpresion parsed = FormatoImpresion.valueOf(formato.name());

            assertEquals(formato, parsed);
        }

        @Test
        @DisplayName("Should parse A4 by name")
        void shouldParseA4ByName() {
            FormatoImpresion formato = FormatoImpresion.valueOf("A4");

            assertEquals(FormatoImpresion.A4, formato);
        }

        @Test
        @DisplayName("Should parse A5 by name")
        void shouldParseA5ByName() {
            FormatoImpresion formato = FormatoImpresion.valueOf("A5");

            assertEquals(FormatoImpresion.A5, formato);
        }

        @Test
        @DisplayName("Should parse TICKET_80MM by name")
        void shouldParseTicket80MmByName() {
            FormatoImpresion formato = FormatoImpresion.valueOf("TICKET_80MM");

            assertEquals(FormatoImpresion.TICKET_80MM, formato);
        }

        @Test
        @DisplayName("Should parse TICKET_58MM by name")
        void shouldParseTicket58MmByName() {
            FormatoImpresion formato = FormatoImpresion.valueOf("TICKET_58MM");

            assertEquals(FormatoImpresion.TICKET_58MM, formato);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for invalid name")
        void shouldThrowIllegalArgumentExceptionForInvalidName() {
            assertThrows(IllegalArgumentException.class, () -> FormatoImpresion.valueOf("INVALID"));
        }
    }

    @Nested
    @DisplayName("Enum Comparison Tests")
    class EnumComparisonTests {

        @Test
        @DisplayName("Should compare enum instances correctly")
        void shouldCompareEnumInstancesCorrectly() {
            FormatoImpresion a4_1 = FormatoImpresion.A4;
            FormatoImpresion a4_2 = FormatoImpresion.A4;

            assertEquals(a4_1, a4_2);
            assertTrue(a4_1 == a4_2);
        }

        @Test
        @DisplayName("Should distinguish different formats")
        void shouldDistinguishDifferentFormats() {
            FormatoImpresion a4 = FormatoImpresion.A4;
            FormatoImpresion a5 = FormatoImpresion.A5;

            assertNotEquals(a4, a5);
            assertFalse(a4 == a5);
        }

        @Test
        @DisplayName("Should have unique ordinals")
        void shouldHaveUniqueOrdinals() {
            int a4_ordinal = FormatoImpresion.A4.ordinal();
            int a5_ordinal = FormatoImpresion.A5.ordinal();
            int ticket80mm_ordinal = FormatoImpresion.TICKET_80MM.ordinal();
            int ticket58mm_ordinal = FormatoImpresion.TICKET_58MM.ordinal();

            assertEquals(0, a4_ordinal);
            assertEquals(1, a5_ordinal);
            assertEquals(2, ticket80mm_ordinal);
            assertEquals(3, ticket58mm_ordinal);
        }
    }

    @Nested
    @DisplayName("Format Classification Tests")
    class FormatClassificationTests {

        @Test
        @DisplayName("Should classify A4 and A5 as standard formats")
        void shouldClassifyA4AndA5AsStandardFormats() {
            assertTrue(isStandardFormat(FormatoImpresion.A4));
            assertTrue(isStandardFormat(FormatoImpresion.A5));
        }

        @Test
        @DisplayName("Should classify TICKET formats as thermal")
        void shouldClassifyTicketFormatsAsThermal() {
            assertTrue(isTicketFormat(FormatoImpresion.TICKET_80MM));
            assertTrue(isTicketFormat(FormatoImpresion.TICKET_58MM));
        }

        @Test
        @DisplayName("Should have different classification for each category")
        void shouldHaveDifferentClassificationForEachCategory() {
            assertTrue(isStandardFormat(FormatoImpresion.A4));
            assertFalse(isTicketFormat(FormatoImpresion.A4));

            assertTrue(isTicketFormat(FormatoImpresion.TICKET_80MM));
            assertFalse(isStandardFormat(FormatoImpresion.TICKET_80MM));
        }
    }

    @Nested
    @DisplayName("Enum Iteration Tests")
    class EnumIterationTests {

        @Test
        @DisplayName("Should iterate through all formats")
        void shouldIterateThroughAllFormats() {
            int count = 0;
            for (FormatoImpresion formato : FormatoImpresion.values()) {
                assertNotNull(formato);
                count++;
            }

            assertEquals(4, count);
        }

        @Test
        @DisplayName("Should process each format individually")
        void shouldProcessEachFormatIndividually() {
            for (FormatoImpresion formato : FormatoImpresion.values()) {
                String name = formato.name();
                assertNotNull(name);
                assertTrue(name.length() > 0);
            }
        }
    }

    @Nested
    @DisplayName("Use Case Tests")
    class UseCaseTests {

        @Test
        @DisplayName("Should use A4 as default format")
        void shouldUseA4AsDefaultFormat() {
            FormatoImpresion defaultFormat = FormatoImpresion.A4;

            assertNotNull(defaultFormat);
            assertEquals("A4", defaultFormat.name());
        }

        @Test
        @DisplayName("Should select format based on document type")
        void shouldSelectFormatBasedOnDocumentType() {
            FormatoImpresion invoiceFormat = FormatoImpresion.A4;
            FormatoImpresion ticketFormat = FormatoImpresion.TICKET_80MM;

            assertNotEquals(invoiceFormat, ticketFormat);
        }

        @Test
        @DisplayName("Should support switching between formats")
        void shouldSupportSwitchingBetweenFormats() {
            FormatoImpresion currentFormat = FormatoImpresion.A4;

            // Switch to A5
            currentFormat = FormatoImpresion.A5;
            assertEquals(FormatoImpresion.A5, currentFormat);

            // Switch to TICKET_80MM
            currentFormat = FormatoImpresion.TICKET_80MM;
            assertEquals(FormatoImpresion.TICKET_80MM, currentFormat);
        }

        @Test
        @DisplayName("Should handle format selection in switch statement")
        void shouldHandleFormatSelectionInSwitchStatement() {
            FormatoImpresion formato = FormatoImpresion.A4;

            String result = switch (formato) {
                case A4 -> "Standard A4";
                case A5 -> "Standard A5";
                case TICKET_80MM -> "Thermal 80mm";
                case TICKET_58MM -> "Thermal 58mm";
            };

            assertEquals("Standard A4", result);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Helper Methods
    // ═════════════════════════════════════════════════════════════════════

    private boolean contains(FormatoImpresion[] array, FormatoImpresion format) {
        for (FormatoImpresion f : array) {
            if (f == format) {
                return true;
            }
        }
        return false;
    }

    private boolean isStandardFormat(FormatoImpresion formato) {
        return formato == FormatoImpresion.A4 || formato == FormatoImpresion.A5;
    }

    private boolean isTicketFormat(FormatoImpresion formato) {
        return formato == FormatoImpresion.TICKET_80MM || formato == FormatoImpresion.TICKET_58MM;
    }
}
