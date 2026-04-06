package com.cna.ublkit.catalogs.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive test suite for EntradaCatalogo model interface.
 * Tests entry creation, property access, and attribute handling.
 */
@DisplayName("EntradaCatalogo Model Tests")
class EntradaCatalogoTest {

    private TestEntradaCatalogo entrada;

    @BeforeEach
    void setUp() {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("codigo", "01");
        atributos.put("descripcion", "Boleta");
        atributos.put("codigo_internacional", "INT-001");
        atributos.put("activo", "true");

        entrada = new TestEntradaCatalogo(atributos);
    }

    @Nested
    @DisplayName("getCodigo() Tests")
    class GetCodigoTests {

        @Test
        @DisplayName("Should return código correctly")
        void getCodigo_valido() {
            assertThat(entrada.getCodigo()).isEqualTo("01");
        }

        @Test
        @DisplayName("Should return non-empty código")
        void getCodigo_noVacio() {
            assertThat(entrada.getCodigo()).isNotBlank();
        }

        @Test
        @DisplayName("Should return consistent código across calls")
        void getCodigo_consistente() {
            String codigo1 = entrada.getCodigo();
            String codigo2 = entrada.getCodigo();

            assertThat(codigo1).isEqualTo(codigo2);
        }

        @Test
        @DisplayName("Should handle alphanumeric código")
        void getCodigo_alfanumerico() {
            Map<String, String> atributos = new HashMap<>();
            atributos.put("codigo", "A01B");
            atributos.put("descripcion", "Test");
            TestEntradaCatalogo testEntrada = new TestEntradaCatalogo(atributos);

            assertThat(testEntrada.getCodigo()).matches("[A-Z0-9]+");
        }
    }

    @Nested
    @DisplayName("getDescripcion() Tests")
    class GetDescripcionTests {

        @Test
        @DisplayName("Should return descripción correctly")
        void getDescripcion_valida() {
            assertThat(entrada.getDescripcion()).isEqualTo("Boleta");
        }

        @Test
        @DisplayName("Should return non-empty descripción")
        void getDescripcion_noVacia() {
            assertThat(entrada.getDescripcion()).isNotBlank();
        }

        @Test
        @DisplayName("Should return consistent descripción")
        void getDescripcion_consistente() {
            String desc1 = entrada.getDescripcion();
            String desc2 = entrada.getDescripcion();

            assertThat(desc1).isEqualTo(desc2);
        }

        @Test
        @DisplayName("Should handle descripción with special characters")
        void getDescripcion_conCaracteresEspeciales() {
            Map<String, String> atributos = new HashMap<>();
            atributos.put("codigo", "01");
            atributos.put("descripcion", "Boleta - Comprobante especial (2024)");
            TestEntradaCatalogo testEntrada = new TestEntradaCatalogo(atributos);

            assertThat(testEntrada.getDescripcion()).contains("especial");
        }
    }

    @Nested
    @DisplayName("getAtributoAdicional() Tests")
    class GetAtributoAdicionalTests {

        @Test
        @DisplayName("Should return Optional with existing attribute")
        void getAtributoAdicional_existe() {
            Optional<String> resultado = entrada.getAtributoAdicional("codigo_internacional");

            assertThat(resultado).isPresent();
            assertThat(resultado.get()).isEqualTo("INT-001");
        }

        @Test
        @DisplayName("Should return empty Optional for non-existing attribute")
        void getAtributoAdicional_noExiste() {
            Optional<String> resultado = entrada.getAtributoAdicional("atributo_inexistente");

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional for null key")
        void getAtributoAdicional_claveNula() {
            Optional<String> resultado = entrada.getAtributoAdicional(null);

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional for empty key")
        void getAtributoAdicional_claveVacia() {
            Optional<String> resultado = entrada.getAtributoAdicional("");

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should retrieve multiple different attributes")
        void getAtributoAdicional_multiples() {
            Optional<String> cod = entrada.getAtributoAdicional("codigo");
            Optional<String> desc = entrada.getAtributoAdicional("descripcion");
            Optional<String> intl = entrada.getAtributoAdicional("codigo_internacional");

            assertThat(cod).isPresent();
            assertThat(desc).isPresent();
            assertThat(intl).isPresent();
        }

        @Test
        @DisplayName("Should handle attribute with empty value")
        void getAtributoAdicional_valorVacio() {
            Map<String, String> atributos = new HashMap<>();
            atributos.put("codigo", "01");
            atributos.put("descripcion", "Test");
            atributos.put("atributo_vacio", "");
            TestEntradaCatalogo testEntrada = new TestEntradaCatalogo(atributos);

            Optional<String> resultado = testEntrada.getAtributoAdicional("atributo_vacio");

            assertThat(resultado).isPresent();
            assertThat(resultado.get()).isEmpty();
        }
    }

    @Nested
    @DisplayName("getTodosAtributos() Tests")
    class GetTodosAtributosTests {

        @Test
        @DisplayName("Should return all attributes in a map")
        void getTodosAtributos_completo() {
            Map<String, String> resultado = entrada.getTodosAtributos();

            assertThat(resultado).containsKey("codigo");
            assertThat(resultado).containsKey("descripcion");
            assertThat(resultado).containsKey("codigo_internacional");
        }

        @Test
        @DisplayName("Should return unmodifiable map")
        void getTodosAtributos_inmodificable() {
            Map<String, String> resultado = entrada.getTodosAtributos();

            assertThatThrownBy(() -> resultado.put("nueva_clave", "valor"))
                .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should contain expected key-value pairs")
        void getTodosAtributos_contenido() {
            Map<String, String> resultado = entrada.getTodosAtributos();

            assertThat(resultado).containsEntry("codigo", "01");
            assertThat(resultado).containsEntry("descripcion", "Boleta");
            assertThat(resultado).containsEntry("codigo_internacional", "INT-001");
        }

        @Test
        @DisplayName("Should return non-empty map when attributes exist")
        void getTodosAtributos_noVacio() {
            Map<String, String> resultado = entrada.getTodosAtributos();

            assertThat(resultado).isNotEmpty();
            assertThat(resultado).hasSizeGreaterThanOrEqualTo(4);
        }

        @Test
        @DisplayName("Should return consistent map across calls")
        void getTodosAtributos_consistente() {
            Map<String, String> resultado1 = entrada.getTodosAtributos();
            Map<String, String> resultado2 = entrada.getTodosAtributos();

            assertThat(resultado1).containsAllEntriesOf(resultado2);
            assertThat(resultado2).containsAllEntriesOf(resultado1);
        }

        @Test
        @DisplayName("Should handle empty attributes map")
        void getTodosAtributos_vacio() {
            TestEntradaCatalogo testEntrada = new TestEntradaCatalogo(new HashMap<>());
            Map<String, String> resultado = testEntrada.getTodosAtributos();

            assertThat(resultado).isEmpty();
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should retrieve all attributes via getTodosAtributos and getAtributoAdicional")
        void consistenciaAtributos() {
            Map<String, String> todos = entrada.getTodosAtributos();

            for (String clave : todos.keySet()) {
                Optional<String> individual = entrada.getAtributoAdicional(clave);
                assertThat(individual).isPresent();
                assertThat(individual.get()).isEqualTo(todos.get(clave));
            }
        }

        @Test
        @DisplayName("Should maintain entry data integrity")
        void integridadDatos() {
            String codigoOriginal = entrada.getCodigo();
            String descripcionOriginal = entrada.getDescripcion();
            Map<String, String> atributosOriginales = entrada.getTodosAtributos();

            // Call multiple times to ensure no modification
            for (int i = 0; i < 5; i++) {
                assertThat(entrada.getCodigo()).isEqualTo(codigoOriginal);
                assertThat(entrada.getDescripcion()).isEqualTo(descripcionOriginal);
                assertThat(entrada.getTodosAtributos()).isEqualTo(atributosOriginales);
            }
        }
    }

    // Test implementation of EntradaCatalogo interface
    private static class TestEntradaCatalogo implements EntradaCatalogo {
        private final Map<String, String> atributos;

        TestEntradaCatalogo(Map<String, String> atributos) {
            this.atributos = new HashMap<>(atributos);
        }

        @Override
        public String getCodigo() {
            return atributos.get("codigo");
        }

        @Override
        public String getDescripcion() {
            return atributos.get("descripcion");
        }

        @Override
        public Optional<String> getAtributoAdicional(String clave) {
            if (clave == null || clave.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(atributos.get(clave));
        }

        @Override
        public Map<String, String> getTodosAtributos() {
            return Collections.unmodifiableMap(new HashMap<>(atributos));
        }
    }
}
