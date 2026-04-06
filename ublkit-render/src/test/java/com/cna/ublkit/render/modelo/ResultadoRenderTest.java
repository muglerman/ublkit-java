package com.cna.ublkit.render.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for ResultadoRender record.
 * Tests rendering results that contain HTML or PDF content.
 *
 * @since 0.2.0
 */
@DisplayName("ResultadoRender - Rendering Result Record")
class ResultadoRenderTest {

    @Nested
    @DisplayName("Factory Method Tests")
    class FactoryMethodTests {

        @Test
        @DisplayName("Should create HTML result with html() factory")
        void shouldCreateHtmlResultWithHtmlFactory() {
            String htmlContent = "<html><body>Test</body></html>";

            ResultadoRender resultado = ResultadoRender.html(htmlContent);

            assertNotNull(resultado);
            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
            assertEquals(htmlContent, resultado.contenidoHtml());
            assertNull(resultado.contenidoPdf());
        }

        @Test
        @DisplayName("Should create PDF result with pdf() factory")
        void shouldCreatePdfResultWithPdfFactory() {
            byte[] pdfContent = new byte[]{0x25, 0x50, 0x44, 0x46}; // "%PDF"

            ResultadoRender resultado = ResultadoRender.pdf(pdfContent);

            assertNotNull(resultado);
            assertTrue(resultado.isPdf());
            assertFalse(resultado.isHtml());
            assertNull(resultado.contenidoHtml());
            assertNotNull(resultado.contenidoPdf());
        }

        @Test
        @DisplayName("Should handle null PDF bytes in factory")
        void shouldHandleNullPdfBytesInFactory() {
            ResultadoRender resultado = ResultadoRender.pdf(null);

            assertNotNull(resultado);
            assertFalse(resultado.isPdf());
            assertNull(resultado.contenidoPdf());
        }

        @Test
        @DisplayName("Should handle null HTML string in factory")
        void shouldHandleNullHtmlStringInFactory() {
            ResultadoRender resultado = ResultadoRender.html(null);

            assertNotNull(resultado);
            assertFalse(resultado.isHtml());
            assertNull(resultado.contenidoHtml());
        }
    }

    @Nested
    @DisplayName("HTML Result Tests")
    class HtmlResultTests {

        @Test
        @DisplayName("Should indicate HTML result correctly")
        void shouldIndicateHtmlResultCorrectly() {
            ResultadoRender resultado = ResultadoRender.html("<html>Test</html>");

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
        }

        @Test
        @DisplayName("Should contain HTML content")
        void shouldContainHtmlContent() {
            String htmlContent = "<html><body><h1>Title</h1></body></html>";

            ResultadoRender resultado = ResultadoRender.html(htmlContent);

            assertEquals(htmlContent, resultado.contenidoHtml());
            assertTrue(resultado.contenidoHtml().contains("<html>"));
        }

        @Test
        @DisplayName("Should have null PDF for HTML result")
        void shouldHaveNullPdfForHtmlResult() {
            ResultadoRender resultado = ResultadoRender.html("<html></html>");

            assertNull(resultado.contenidoPdf());
        }

        @Test
        @DisplayName("Should handle empty HTML string")
        void shouldHandleEmptyHtmlString() {
            ResultadoRender resultado = ResultadoRender.html("");

            assertTrue(resultado.isHtml());
            assertEquals("", resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should handle large HTML content")
        void shouldHandleLargeHtmlContent() {
            String largeHtml = "<html>" + "x".repeat(100000) + "</html>";

            ResultadoRender resultado = ResultadoRender.html(largeHtml);

            assertTrue(resultado.isHtml());
            assertEquals(largeHtml, resultado.contenidoHtml());
        }
    }

    @Nested
    @DisplayName("PDF Result Tests")
    class PdfResultTests {

        @Test
        @DisplayName("Should indicate PDF result correctly")
        void shouldIndicatePdfResultCorrectly() {
            byte[] pdfBytes = new byte[]{0x25, 0x50, 0x44, 0x46}; // "%PDF"

            ResultadoRender resultado = ResultadoRender.pdf(pdfBytes);

            assertTrue(resultado.isPdf());
            assertFalse(resultado.isHtml());
        }

        @Test
        @DisplayName("Should contain PDF bytes")
        void shouldContainPdfBytes() {
            byte[] pdfBytes = new byte[]{0x25, 0x50, 0x44, 0x46, 0x2D, 0x31, 0x2E, 0x34};

            ResultadoRender resultado = ResultadoRender.pdf(pdfBytes);

            assertNotNull(resultado.contenidoPdf());
            assertTrue(resultado.contenidoPdf().length > 0);
        }

        @Test
        @DisplayName("Should have null HTML for PDF result")
        void shouldHaveNullHtmlForPdfResult() {
            byte[] pdfBytes = new byte[]{0x25, 0x50, 0x44, 0x46};

            ResultadoRender resultado = ResultadoRender.pdf(pdfBytes);

            assertNull(resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should handle empty PDF byte array")
        void shouldHandleEmptyPdfByteArray() {
            byte[] pdfBytes = new byte[0];

            ResultadoRender resultado = ResultadoRender.pdf(pdfBytes);

            assertTrue(resultado.isPdf());
            assertEquals(0, resultado.contenidoPdf().length);
        }

        @Test
        @DisplayName("Should handle large PDF byte array")
        void shouldHandleLargePdfByteArray() {
            byte[] largePdfBytes = new byte[1000000];
            Arrays.fill(largePdfBytes, (byte) 0x42); // Fill with 'B'

            ResultadoRender resultado = ResultadoRender.pdf(largePdfBytes);

            assertTrue(resultado.isPdf());
            assertEquals(1000000, resultado.contenidoPdf().length);
        }
    }

    @Nested
    @DisplayName("PDF Bytes Defensive Copy Tests")
    class PdfBytesDefensiveCopyTests {

        @Test
        @DisplayName("Should create defensive copy of PDF bytes")
        void shouldCreateDefensiveCopyOfPdfBytes() {
            byte[] originalBytes = new byte[]{1, 2, 3, 4};

            ResultadoRender resultado = ResultadoRender.pdf(originalBytes);

            // Modify original array
            originalBytes[0] = 99;

            // Result should not be affected
            assertEquals(1, resultado.contenidoPdf()[0]);
        }

        @Test
        @DisplayName("Should return defensive copy on access")
        void shouldReturnDefensiveCopyOnAccess() {
            byte[] originalBytes = new byte[]{1, 2, 3, 4};

            ResultadoRender resultado = ResultadoRender.pdf(originalBytes);

            byte[] retrieved1 = resultado.contenidoPdf();
            byte[] retrieved2 = resultado.contenidoPdf();

            // Should be different objects (defensive copies)
            assertNotSame(retrieved1, retrieved2);

            // But same content
            assertArrayEquals(retrieved1, retrieved2);
        }

        @Test
        @DisplayName("Should prevent mutation of PDF through accessor")
        void shouldPreventMutationOfPdfThroughAccessor() {
            byte[] originalBytes = new byte[]{1, 2, 3, 4};

            ResultadoRender resultado = ResultadoRender.pdf(originalBytes);

            byte[] accessed = resultado.contenidoPdf();
            accessed[0] = 99;

            // Original result should not be affected
            assertEquals(1, resultado.contenidoPdf()[0]);
        }
    }

    @Nested
    @DisplayName("Type Check Tests")
    class TypeCheckTests {

        @Test
        @DisplayName("Should correctly identify HTML-only results")
        void shouldCorrectlyIdentifyHtmlOnlyResults() {
            ResultadoRender resultado = ResultadoRender.html("<html></html>");

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
        }

        @Test
        @DisplayName("Should correctly identify PDF-only results")
        void shouldCorrectlyIdentifyPdfOnlyResults() {
            ResultadoRender resultado = ResultadoRender.pdf(new byte[]{1, 2, 3});

            assertTrue(resultado.isPdf());
            assertFalse(resultado.isHtml());
        }

        @Test
        @DisplayName("Should identify neither HTML nor PDF when both null")
        void shouldIdentifyNeitherHtmlNorPdfWhenBothNull() {
            ResultadoRender resultado = new ResultadoRender(null, null);

            assertFalse(resultado.isHtml());
            assertFalse(resultado.isPdf());
        }

        @Test
        @DisplayName("Should identify HTML when PDF is null")
        void shouldIdentifyHtmlWhenPdfIsNull() {
            ResultadoRender resultado = new ResultadoRender("<html></html>", null);

            assertTrue(resultado.isHtml());
            assertFalse(resultado.isPdf());
        }

        @Test
        @DisplayName("Should identify PDF when HTML is null")
        void shouldIdentifyPdfWhenHtmlIsNull() {
            ResultadoRender resultado = new ResultadoRender(null, new byte[]{1, 2, 3});

            assertFalse(resultado.isHtml());
            assertTrue(resultado.isPdf());
        }
    }

    @Nested
    @DisplayName("Equality Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should have correct equals for same HTML content")
        void shouldHaveCorrectEqualsForSameHtmlContent() {
            ResultadoRender resultado1 = ResultadoRender.html("<html>Test</html>");
            ResultadoRender resultado2 = ResultadoRender.html("<html>Test</html>");

            assertEquals(resultado1, resultado2);
        }

        @Test
        @DisplayName("Should have correct equals for same PDF content")
        void shouldHaveCorrectEqualsForSamePdfContent() {
            byte[] pdfBytes = new byte[]{1, 2, 3, 4};

            ResultadoRender resultado1 = ResultadoRender.pdf(pdfBytes.clone());
            ResultadoRender resultado2 = ResultadoRender.pdf(pdfBytes.clone());

            assertEquals(resultado1, resultado2);
        }

        @Test
        @DisplayName("Should have correct equals for different HTML content")
        void shouldHaveCorrectEqualsForDifferentHtmlContent() {
            ResultadoRender resultado1 = ResultadoRender.html("<html>Test1</html>");
            ResultadoRender resultado2 = ResultadoRender.html("<html>Test2</html>");

            assertNotEquals(resultado1, resultado2);
        }

        @Test
        @DisplayName("Should have correct equals for different PDF content")
        void shouldHaveCorrectEqualsForDifferentPdfContent() {
            ResultadoRender resultado1 = ResultadoRender.pdf(new byte[]{1, 2, 3});
            ResultadoRender resultado2 = ResultadoRender.pdf(new byte[]{4, 5, 6});

            assertNotEquals(resultado1, resultado2);
        }

        @Test
        @DisplayName("Should have correct equals for HTML vs PDF")
        void shouldHaveCorrectEqualsForHtmlVsPdf() {
            ResultadoRender htmlResult = ResultadoRender.html("<html></html>");
            ResultadoRender pdfResult = ResultadoRender.pdf(new byte[]{1, 2, 3});

            assertNotEquals(htmlResult, pdfResult);
        }
    }

    @Nested
    @DisplayName("HashCode Tests")
    class HashCodeTests {

        @Test
        @DisplayName("Should have same hashCode for equal objects")
        void shouldHaveSameHashCodeForEqualObjects() {
            ResultadoRender resultado1 = ResultadoRender.html("<html></html>");
            ResultadoRender resultado2 = ResultadoRender.html("<html></html>");

            assertEquals(resultado1.hashCode(), resultado2.hashCode());
        }

        @Test
        @DisplayName("Should have different hashCode for different content")
        void shouldHaveDifferentHashCodeForDifferentContent() {
            ResultadoRender resultado1 = ResultadoRender.html("<html>A</html>");
            ResultadoRender resultado2 = ResultadoRender.html("<html>B</html>");

            assertNotEquals(resultado1.hashCode(), resultado2.hashCode());
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should have meaningful toString for HTML result")
        void shouldHaveMeaningfulToStringForHtmlResult() {
            ResultadoRender resultado = ResultadoRender.html("<html></html>");

            String toString = resultado.toString();
            assertNotNull(toString);
            assertTrue(toString.length() > 0);
            assertTrue(toString.contains("ResultadoRender"));
        }

        @Test
        @DisplayName("Should have meaningful toString for PDF result")
        void shouldHaveMeaningfulToStringForPdfResult() {
            ResultadoRender resultado = ResultadoRender.pdf(new byte[]{1, 2, 3});

            String toString = resultado.toString();
            assertNotNull(toString);
            assertTrue(toString.length() > 0);
            assertTrue(toString.contains("ResultadoRender"));
        }

        @Test
        @DisplayName("Should show content type in toString")
        void shouldShowContentTypeInToString() {
            ResultadoRender htmlResult = ResultadoRender.html("<html></html>");
            ResultadoRender pdfResult = ResultadoRender.pdf(new byte[]{1, 2, 3});

            String htmlString = htmlResult.toString();
            String pdfString = pdfResult.toString();

            assertNotEquals(htmlString, pdfString);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle empty HTML")
        void shouldHandleEmptyHtml() {
            ResultadoRender resultado = ResultadoRender.html("");

            assertTrue(resultado.isHtml());
            assertEquals("", resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should handle very large HTML")
        void shouldHandleVeryLargeHtml() {
            String largeHtml = "<html>" + "x".repeat(1000000) + "</html>";

            ResultadoRender resultado = ResultadoRender.html(largeHtml);

            assertTrue(resultado.isHtml());
            assertEquals(largeHtml, resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should handle single byte PDF")
        void shouldHandleSingleBytePdf() {
            byte[] singleByte = new byte[]{42};

            ResultadoRender resultado = ResultadoRender.pdf(singleByte);

            assertTrue(resultado.isPdf());
            assertEquals(1, resultado.contenidoPdf().length);
        }

        @Test
        @DisplayName("Should handle HTML with special characters")
        void shouldHandleHtmlWithSpecialCharacters() {
            String html = "<html><body>Éàü&<>\"'</body></html>";

            ResultadoRender resultado = ResultadoRender.html(html);

            assertTrue(resultado.isHtml());
            assertEquals(html, resultado.contenidoHtml());
        }

        @Test
        @DisplayName("Should handle PDF with all byte values")
        void shouldHandlePdfWithAllByteValues() {
            byte[] allBytes = new byte[256];
            for (int i = 0; i < 256; i++) {
                allBytes[i] = (byte) i;
            }

            ResultadoRender resultado = ResultadoRender.pdf(allBytes);

            assertTrue(resultado.isPdf());
            assertEquals(256, resultado.contenidoPdf().length);
        }
    }
}
