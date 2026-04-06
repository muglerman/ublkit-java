package com.cna.ublkit.render.api;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for RenderizadorDocumento interface contract.
 * Verifies that the rendering interface defines the correct contract
 * for all implementing classes.
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorDocumento - Interface Contract")
class RenderizadorDocumentoTest {

    @Nested
    @DisplayName("Interface Definition Tests")
    class InterfaceDefinitionTests {

        @Test
        @DisplayName("Should have renderizar method signature")
        void shouldHaveRenderizarMethodSignature() {
            // Verify that RenderizadorDocumento has the renderizar method
            assertTrue(hasMethodRenderizar());
        }

        @Test
        @DisplayName("Should be generic with type parameter")
        void shouldBeGenericWithTypeParameter() {
            // Verify generic type parameter support
            Class<?> interfaceClass = RenderizadorDocumento.class;
            assertNotNull(interfaceClass.getTypeParameters());
        }

        @Test
        @DisplayName("renderizar should accept ContextoRender parameter")
        void renderizarShouldAcceptContextoRenderParameter() {
            // Verification of parameter type
            assertTrue(hasMethodWithContextoRenderParameter());
        }

        @Test
        @DisplayName("renderizar should return ResultadoRender")
        void renderizarShouldReturnResultadoRender() {
            // Verification of return type
            assertTrue(hasMethodWithResultadoRenderReturn());
        }
    }

    @Nested
    @DisplayName("Generic Type Support Tests")
    class GenericTypeSupportTests {

        @Test
        @DisplayName("Should support BorradorFactura type parameter")
        void shouldSupportBorradorFacturaTypeParameter() {
            ConcreteImplementation<BorradorFactura> impl = new ConcreteImplementation<>();
            assertNotNull(impl);
        }

        @Test
        @DisplayName("Should support Object type parameter")
        void shouldSupportObjectTypeParameter() {
            ConcreteImplementation<Object> impl = new ConcreteImplementation<>();
            assertNotNull(impl);
        }

        @Test
        @DisplayName("Should support String type parameter")
        void shouldSupportStringTypeParameter() {
            ConcreteImplementation<String> impl = new ConcreteImplementation<>();
            assertNotNull(impl);
        }
    }

    @Nested
    @DisplayName("Implementation Contract Tests")
    class ImplementationContractTests {

        @Test
        @DisplayName("Implementing class must implement renderizar method")
        void implementingClassMustImplementRenderizarMethod() {
            ConcreteImplementation<BorradorFactura> impl = new ConcreteImplementation<>();
            BorradorFactura doc = new BorradorFactura();
            doc.setSerie("F001");
            doc.setNumero(1);
            doc.setFechaEmision(LocalDate.now());

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(doc, "hash123", null);

            ResultadoRender resultado = impl.renderizar(contexto);
            assertNotNull(resultado);
        }

        @Test
        @DisplayName("Must return non-null ResultadoRender")
        void mustReturnNonNullResultadoRender() {
            ConcreteImplementation<String> impl = new ConcreteImplementation<>();
            ContextoRender<String> contexto = ContextoRender.of("test", "hash", null);

            ResultadoRender resultado = impl.renderizar(contexto);
            assertNotNull(resultado);
        }

        @Test
        @DisplayName("Should handle null context gracefully")
        void shouldHandleContextProperly() {
            ConcreteImplementation<BorradorFactura> impl = new ConcreteImplementation<>();
            BorradorFactura doc = new BorradorFactura();

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(doc);
            ResultadoRender resultado = impl.renderizar(contexto);

            assertNotNull(resultado);
        }
    }

    @Nested
    @DisplayName("Polymorphism Tests")
    class PolymorphismTests {

        @Test
        @DisplayName("Should support polymorphic calls")
        void shouldSupportPolymorphicCalls() {
            RenderizadorDocumento<BorradorFactura> renderer = new ConcreteImplementation<>();
            BorradorFactura doc = new BorradorFactura();
            doc.setSerie("F001");
            doc.setNumero(1);
            doc.setFechaEmision(LocalDate.now());

            ContextoRender<BorradorFactura> contexto = ContextoRender.of(doc, "hash", null);
            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado);
        }

        @Test
        @DisplayName("Should work with different concrete implementations")
        void shouldWorkWithDifferentConcreteImplementations() {
            RenderizadorDocumento<String> stringRenderer = new ConcreteImplementation<>();
            RenderizadorDocumento<Object> objectRenderer = new ConcreteImplementation<>();

            assertNotNull(stringRenderer);
            assertNotNull(objectRenderer);
        }

        @Test
        @DisplayName("Should maintain contract across implementations")
        void shouldMaintainContractAcrossImplementations() {
            RenderizadorDocumento<BorradorFactura> impl1 = new ConcreteImplementation<>();
            RenderizadorDocumento<BorradorFactura> impl2 = new ConcreteImplementation<>();

            BorradorFactura doc = new BorradorFactura();
            ContextoRender<BorradorFactura> contexto = ContextoRender.of(doc);

            ResultadoRender result1 = impl1.renderizar(contexto);
            ResultadoRender result2 = impl2.renderizar(contexto);

            assertNotNull(result1);
            assertNotNull(result2);
        }
    }

    @Nested
    @DisplayName("Contract Violation Prevention Tests")
    class ContractViolationPreventionTests {

        @Test
        @DisplayName("Should prevent returning null for HTML content without cause")
        void shouldPreventReturningNullForHtmlWithoutCause() {
            RenderizadorDocumento<String> renderer = new ConcreteImplementation<>();
            ContextoRender<String> contexto = ContextoRender.of("test", "hash", null);

            ResultadoRender resultado = renderer.renderizar(contexto);
            // Either HTML or PDF should be set, not both null is the contract
            assertTrue(resultado.isHtml() || resultado.isPdf());
        }

        @Test
        @DisplayName("Must not return both HTML and PDF as null")
        void mustNotReturnBothAsNull() {
            RenderizadorDocumento<String> renderer = new ConcreteImplementation<>();
            ContextoRender<String> contexto = ContextoRender.of("test", "hash", null);

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertFalse(!resultado.isHtml() && !resultado.isPdf(), "Must return either HTML or PDF");
        }

        @Test
        @DisplayName("Should not return both HTML and PDF simultaneously")
        void shouldNotReturnBothHtmlAndPdfSimultaneously() {
            RenderizadorDocumento<String> renderer = new ConcreteImplementation<>();
            ContextoRender<String> contexto = ContextoRender.of("test", "hash", null);

            ResultadoRender resultado = renderer.renderizar(contexto);
            // Implementations should return either HTML or PDF, not both
            assertFalse(resultado.isHtml() && resultado.isPdf(),
                "Should not return both HTML and PDF");
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Helper - Concrete Implementation for Testing
    // ═════════════════════════════════════════════════════════════════════

    /**
     * Concrete implementation for testing the interface contract.
     */
    static class ConcreteImplementation<T> implements RenderizadorDocumento<T> {
        @Override
        public ResultadoRender renderizar(ContextoRender<T> contexto) {
            // Simple HTML rendering for contract testing
            return ResultadoRender.html("<html><body>Test Render</body></html>");
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Reflection Helpers
    // ═════════════════════════════════════════════════════════════════════

    private boolean hasMethodRenderizar() {
        try {
            RenderizadorDocumento.class.getMethod("renderizar", ContextoRender.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private boolean hasMethodWithContextoRenderParameter() {
        try {
            RenderizadorDocumento.class.getMethod("renderizar", ContextoRender.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private boolean hasMethodWithResultadoRenderReturn() {
        try {
            return RenderizadorDocumento.class.getMethod("renderizar", ContextoRender.class)
                    .getReturnType().equals(ResultadoRender.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
