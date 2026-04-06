package com.cna.ublkit.validation.api;

import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validador Interface Tests")
class ValidadorTest {

    @Test
    @DisplayName("Should define validar method with Object type")
    void interfaz_validar_existente() {
        try {
            Validador.class.getMethod("validar", Object.class);
        } catch (NoSuchMethodException e) {
            fail("Interface Validador should define validar(Object) method");
        }
    }

    @Test
    @DisplayName("Should define validar method returning ResultadoValidacion")
    void interfaz_validar_retornaResultado() {
        try {
            java.lang.reflect.Method method = Validador.class.getMethod("validar", Object.class);
            assertEquals(ResultadoValidacion.class, method.getReturnType());
        } catch (NoSuchMethodException e) {
            fail("Interface Validador should define validar(Object): ResultadoValidacion");
        }
    }

    @Test
    @DisplayName("Should support generic type parameter")
    void interfaz_generico() {
        try {
            java.lang.reflect.TypeVariable<?>[] typeVars = Validador.class.getTypeParameters();
            assertEquals(1, typeVars.length);
            assertEquals("T", typeVars[0].getName());
        } catch (Exception e) {
            fail("Interface Validador should support generic type parameter T");
        }
    }

    @Test
    @DisplayName("Concrete implementation should implement Validador")
    void implementacion_concreto_Validador() {
        TestValidator validator = new TestValidator();
        assertTrue(validator instanceof Validador<?>);
    }

    @Test
    @DisplayName("Concrete implementation should return ResultadoValidacion")
    void implementacion_retornaResultado() {
        TestValidator validator = new TestValidator();
        ResultadoValidacion result = validator.validar("test");
        assertNotNull(result);
        assertTrue(result instanceof ResultadoValidacion);
    }

    @Test
    @DisplayName("Multiple implementations can exist")
    void multiplicasImplementaciones() {
        TestValidator1 v1 = new TestValidator1();
        TestValidator2 v2 = new TestValidator2();

        assertTrue(v1 instanceof Validador<?>);
        assertTrue(v2 instanceof Validador<?>);
    }

    @Test
    @DisplayName("Interface should allow null handling in implementations")
    void interfaz_permiteLlenarNull() {
        TestValidatorWithNull validator = new TestValidatorWithNull();
        ResultadoValidacion result = validator.validar(null);
        assertNotNull(result);
    }

    // Concrete test implementations
    private static class TestValidator implements Validador<String> {
        @Override
        public ResultadoValidacion validar(String objetivo) {
            return new ResultadoValidacion();
        }
    }

    private static class TestValidator1 implements Validador<Integer> {
        @Override
        public ResultadoValidacion validar(Integer objetivo) {
            return new ResultadoValidacion();
        }
    }

    private static class TestValidator2 implements Validador<Object> {
        @Override
        public ResultadoValidacion validar(Object objetivo) {
            return new ResultadoValidacion();
        }
    }

    private static class TestValidatorWithNull implements Validador<String> {
        @Override
        public ResultadoValidacion validar(String objetivo) {
            return new ResultadoValidacion();
        }
    }
}
