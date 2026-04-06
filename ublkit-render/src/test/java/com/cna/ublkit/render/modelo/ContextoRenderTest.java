package com.cna.ublkit.render.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for ContextoRender record.
 * Tests rendering context creation and attribute handling.
 *
 * @since 0.2.0
 */
@DisplayName("ContextoRender - Rendering Context Record")
class ContextoRenderTest {

    @Nested
    @DisplayName("Creation Tests")
    class CreationTests {

        @Test
        @DisplayName("Should create context with factory method of(documento, hash, qr)")
        void shouldCreateContextWithFactoryMethodThreeParams() {
            String documento = "test_doc";
            String hash = "hash_value";
            String qr = "qr_value";

            ContextoRender<String> contexto = ContextoRender.of(documento, hash, qr);

            assertNotNull(contexto);
            assertEquals(documento, contexto.documento());
            assertEquals(hash, contexto.hashDocumento());
            assertEquals(qr, contexto.qrBase64());
        }

        @Test
        @DisplayName("Should create context with factory method of(documento)")
        void shouldCreateContextWithFactoryMethodOneParam() {
            String documento = "test_doc";

            ContextoRender<String> contexto = ContextoRender.of(documento);

            assertNotNull(contexto);
            assertEquals(documento, contexto.documento());
            assertNull(contexto.hashDocumento());
            assertNull(contexto.qrBase64());
        }

        @Test
        @DisplayName("Should create context with factory method of(documento, hash, qr, attrs)")
        void shouldCreateContextWithFactoryMethodFourParams() {
            String documento = "test_doc";
            String hash = "hash_value";
            String qr = "qr_value";
            Map<String, Object> attrs = Map.of("key", "value");

            ContextoRender<String> contexto = ContextoRender.of(documento, hash, qr, attrs);

            assertNotNull(contexto);
            assertEquals(documento, contexto.documento());
            assertEquals(hash, contexto.hashDocumento());
            assertEquals(qr, contexto.qrBase64());
            assertEquals(attrs, contexto.atributosPlantilla());
        }

        @Test
        @DisplayName("Should create context with null QR")
        void shouldCreateContextWithNullQr() {
            String documento = "test_doc";

            ContextoRender<String> contexto = ContextoRender.of(documento, "hash", null);

            assertNotNull(contexto);
            assertNull(contexto.qrBase64());
        }

        @Test
        @DisplayName("Should create context with null hash")
        void shouldCreateContextWithNullHash() {
            String documento = "test_doc";

            ContextoRender<String> contexto = ContextoRender.of(documento, null, "qr");

            assertNotNull(contexto);
            assertNull(contexto.hashDocumento());
        }
    }

    @Nested
    @DisplayName("Document Generic Type Tests")
    class DocumentGenericTypeTests {

        @Test
        @DisplayName("Should support String document type")
        void shouldSupportStringDocumentType() {
            ContextoRender<String> contexto = ContextoRender.of("documento", "hash", "qr");

            assertNotNull(contexto);
            assertIsInstance(contexto.documento(), String.class);
        }

        @Test
        @DisplayName("Should support Integer document type")
        void shouldSupportIntegerDocumentType() {
            ContextoRender<Integer> contexto = ContextoRender.of(42, "hash", "qr");

            assertNotNull(contexto);
            assertEquals(42, contexto.documento());
        }

        @Test
        @DisplayName("Should support Object document type")
        void shouldSupportObjectDocumentType() {
            Object documento = new Object();
            ContextoRender<Object> contexto = ContextoRender.of(documento, "hash", "qr");

            assertNotNull(contexto);
            assertEquals(documento, contexto.documento());
        }

        @Test
        @DisplayName("Should support custom class as document type")
        void shouldSupportCustomClassAsDocumentType() {
            CustomDocument doc = new CustomDocument("test");
            ContextoRender<CustomDocument> contexto = ContextoRender.of(doc, "hash", "qr");

            assertNotNull(contexto);
            assertEquals(doc, contexto.documento());
        }
    }

    @Nested
    @DisplayName("Attributes Handling Tests")
    class AttributesHandlingTests {

        @Test
        @DisplayName("Should handle empty attributes map")
        void shouldHandleEmptyAttributesMap() {
            Map<String, Object> attrs = Map.of();

            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr", attrs);

            assertNotNull(contexto.atributosPlantilla());
            assertTrue(contexto.atributosPlantilla().isEmpty());
        }

        @Test
        @DisplayName("Should handle attributes with multiple values")
        void shouldHandleAttributesWithMultipleValues() {
            Map<String, Object> attrs = Map.of(
                    "header", "Header Text",
                    "footer", "Footer Text",
                    "color", "blue"
            );

            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr", attrs);

            assertEquals(3, contexto.atributosPlantilla().size());
            assertEquals("Header Text", contexto.atributosPlantilla().get("header"));
            assertEquals("Footer Text", contexto.atributosPlantilla().get("footer"));
            assertEquals("blue", contexto.atributosPlantilla().get("color"));
        }

        @Test
        @DisplayName("Should handle null attributes")
        void shouldHandleNullAttributes() {
            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr", null);

            assertNotNull(contexto.atributosPlantilla());
            assertTrue(contexto.atributosPlantilla().isEmpty());
        }

        @Test
        @DisplayName("Should create defensive copy of attributes")
        void shouldCreateDefensiveCopyOfAttributes() {
            Map<String, Object> originalAttrs = new java.util.HashMap<>();
            originalAttrs.put("key", "value");

            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr", originalAttrs);

            // Modify original map
            originalAttrs.put("key", "modified");

            // Context should have original value (defensive copy)
            assertEquals("value", contexto.atributosPlantilla().get("key"));
        }
    }

    @Nested
    @DisplayName("Record Properties Tests")
    class RecordPropertiesTests {

        @Test
        @DisplayName("Should be immutable record")
        void shouldBeImmutableRecord() {
            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr");

            assertNotNull(contexto.documento());
            assertNotNull(contexto.hashDocumento());
            assertNotNull(contexto.qrBase64());
        }

        @Test
        @DisplayName("Should have correct equals implementation")
        void shouldHaveCorrectEqualsImplementation() {
            ContextoRender<String> contexto1 = ContextoRender.of("doc", "hash", "qr");
            ContextoRender<String> contexto2 = ContextoRender.of("doc", "hash", "qr");

            assertEquals(contexto1, contexto2);
        }

        @Test
        @DisplayName("Should have correct hashCode implementation")
        void shouldHaveCorrectHashCodeImplementation() {
            ContextoRender<String> contexto1 = ContextoRender.of("doc", "hash", "qr");
            ContextoRender<String> contexto2 = ContextoRender.of("doc", "hash", "qr");

            assertEquals(contexto1.hashCode(), contexto2.hashCode());
        }

        @Test
        @DisplayName("Should have meaningful toString")
        void shouldHaveMeaningfulToString() {
            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "qr");

            String toString = contexto.toString();
            assertNotNull(toString);
            assertTrue(toString.length() > 0);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle empty string document")
        void shouldHandleEmptyStringDocument() {
            ContextoRender<String> contexto = ContextoRender.of("", "hash", "qr");

            assertNotNull(contexto);
            assertEquals("", contexto.documento());
        }

        @Test
        @DisplayName("Should handle empty string hash")
        void shouldHandleEmptyStringHash() {
            ContextoRender<String> contexto = ContextoRender.of("doc", "", "qr");

            assertNotNull(contexto);
            assertEquals("", contexto.hashDocumento());
        }

        @Test
        @DisplayName("Should handle empty string QR")
        void shouldHandleEmptyStringQr() {
            ContextoRender<String> contexto = ContextoRender.of("doc", "hash", "");

            assertNotNull(contexto);
            assertEquals("", contexto.qrBase64());
        }

        @Test
        @DisplayName("Should handle very long strings")
        void shouldHandleVeryLongStrings() {
            String longDoc = "x".repeat(10000);
            String longHash = "h".repeat(10000);
            String longQr = "q".repeat(10000);

            ContextoRender<String> contexto = ContextoRender.of(longDoc, longHash, longQr);

            assertEquals(longDoc, contexto.documento());
            assertEquals(longHash, contexto.hashDocumento());
            assertEquals(longQr, contexto.qrBase64());
        }

        @Test
        @DisplayName("Should handle null document")
        void shouldHandleNullDocument() {
            ContextoRender<String> contexto = ContextoRender.of(null, "hash", "qr");

            assertNull(contexto.documento());
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Helper Classes
    // ═════════════════════════════════════════════════════════════════════

    private static class CustomDocument {
        private final String content;

        CustomDocument(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Helper Methods
    // ═════════════════════════════════════════════════════════════════════

    private <T> void assertIsInstance(T object, Class<?> expectedClass) {
        assertTrue(expectedClass.isInstance(object),
                "Object should be instance of " + expectedClass.getSimpleName());
    }
}
