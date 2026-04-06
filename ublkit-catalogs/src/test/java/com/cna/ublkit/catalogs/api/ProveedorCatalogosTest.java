package com.cna.ublkit.catalogs.api;

import com.cna.ublkit.catalogs.modelo.EntradaCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive test suite for ProveedorCatalogos interface contract.
 * Tests catalog loading patterns, searching capabilities, and edge cases.
 */
@DisplayName("ProveedorCatalogos Interface Tests")
class ProveedorCatalogosTest {

    private ProveedorCatalogos proveedor;
    private TestEntrada testEntrada01;
    private TestEntrada testEntrada02;

    @BeforeEach
    void setUp() {
        testEntrada01 = new TestEntrada("01", "Boleta");
        testEntrada02 = new TestEntrada("03", "Factura");

        proveedor = new ProveedorCatalogosTestImpl(
            List.of(testEntrada01, testEntrada02),
            Map.of("01", List.of(testEntrada01, testEntrada02))
        );
    }

    @Nested
    @DisplayName("obtenerCatalogo() Tests")
    class ObtenerCatalogoTests {

        @Test
        @DisplayName("Should return catalog entries when catalog exists")
        void obtenerCatalogo_existente() {
            List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo("01");

            assertThat(resultado).isNotNull();
            assertThat(resultado).hasSize(2);
            assertThat(resultado.get(0).getCodigo()).isEqualTo("01");
            assertThat(resultado.get(1).getCodigo()).isEqualTo("03");
        }

        @Test
        @DisplayName("Should return empty list when catalog does not exist")
        void obtenerCatalogo_noExiste() {
            List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo("99");

            assertThat(resultado).isNotNull();
            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should return unmodifiable list")
        void obtenerCatalogo_listaInmodificable() {
            List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo("01");

            assertThatThrownBy(() -> resultado.add(new TestEntrada("05", "NC")))
                .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Should handle null catalog ID gracefully")
        void obtenerCatalogo_conIdNulo() {
            assertThatNoException().isThrownBy(() -> {
                List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo(null);
                assertThat(resultado).isNotNull();
            });
        }

        @Test
        @DisplayName("Should handle empty string catalog ID")
        void obtenerCatalogo_conIdVacio() {
            List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo("");

            assertThat(resultado).isNotNull();
            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should return catalog by normalized ID")
        void obtenerCatalogo_conIdNormalizado() {
            List<EntradaCatalogo> resultado = proveedor.obtenerCatalogo("01");

            assertThat(resultado).isNotEmpty();
            assertThat(resultado.get(0).getCodigo()).isIn("01", "03");
        }
    }

    @Nested
    @DisplayName("buscar() Tests")
    class BuscarTests {

        @Test
        @DisplayName("Should find entry when it exists")
        void buscar_entradaExistente() {
            Optional<EntradaCatalogo> resultado = proveedor.buscar("01", "01");

            assertThat(resultado).isPresent();
            assertThat(resultado.get().getCodigo()).isEqualTo("01");
            assertThat(resultado.get().getDescripcion()).isEqualTo("Boleta");
        }

        @Test
        @DisplayName("Should return empty Optional when entry does not exist")
        void buscar_entradaNoExiste() {
            Optional<EntradaCatalogo> resultado = proveedor.buscar("01", "99");

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should return empty Optional when catalog does not exist")
        void buscar_catalogoNoExiste() {
            Optional<EntradaCatalogo> resultado = proveedor.buscar("99", "01");

            assertThat(resultado).isEmpty();
        }

        @Test
        @DisplayName("Should handle null catalog ID in search")
        void buscar_conIdCatalogoNulo() {
            assertThatNoException().isThrownBy(() -> {
                Optional<EntradaCatalogo> resultado = proveedor.buscar(null, "01");
                assertThat(resultado).isEmpty();
            });
        }

        @Test
        @DisplayName("Should handle null code in search")
        void buscar_conCodigoNulo() {
            assertThatNoException().isThrownBy(() -> {
                Optional<EntradaCatalogo> resultado = proveedor.buscar("01", null);
                assertThat(resultado).isEmpty();
            });
        }

        @Test
        @DisplayName("Should find multiple codes in same catalog")
        void buscar_multiplesCodigos() {
            Optional<EntradaCatalogo> resultado1 = proveedor.buscar("01", "01");
            Optional<EntradaCatalogo> resultado2 = proveedor.buscar("01", "03");

            assertThat(resultado1).isPresent();
            assertThat(resultado2).isPresent();
            assertThat(resultado1.get().getCodigo()).isNotEqualTo(resultado2.get().getCodigo());
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should retrieve and search in same catalog")
        void integrationObtenerYBuscar() {
            List<EntradaCatalogo> catalogo = proveedor.obtenerCatalogo("01");

            for (EntradaCatalogo entrada : catalogo) {
                Optional<EntradaCatalogo> resultado = proveedor.buscar("01", entrada.getCodigo());
                assertThat(resultado).isPresent();
                assertThat(resultado.get().getCodigo()).isEqualTo(entrada.getCodigo());
            }
        }

        @Test
        @DisplayName("Should maintain consistency across calls")
        void consistenciaEnCalls() {
            List<EntradaCatalogo> resultado1 = proveedor.obtenerCatalogo("01");
            List<EntradaCatalogo> resultado2 = proveedor.obtenerCatalogo("01");

            assertThat(resultado1).hasSameSizeAs(resultado2);
            assertThat(resultado1).containsExactlyElementsOf(resultado2);
        }
    }

    // Test implementation for interface testing
    private static class ProveedorCatalogosTestImpl implements ProveedorCatalogos {
        private final List<EntradaCatalogo> allEntradas;
        private final Map<String, List<EntradaCatalogo>> catalogos;

        ProveedorCatalogosTestImpl(List<EntradaCatalogo> allEntradas,
                                   Map<String, List<EntradaCatalogo>> catalogos) {
            this.allEntradas = allEntradas;
            this.catalogos = catalogos;
        }

        @Override
        public List<EntradaCatalogo> obtenerCatalogo(String idCatalogo) {
            if (idCatalogo == null || idCatalogo.isEmpty()) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(
                catalogos.getOrDefault(idCatalogo, Collections.emptyList())
            );
        }

        @Override
        public Optional<EntradaCatalogo> buscar(String idCatalogo, String codigo) {
            if (idCatalogo == null || codigo == null) {
                return Optional.empty();
            }
            return obtenerCatalogo(idCatalogo).stream()
                .filter(e -> e.getCodigo().equals(codigo))
                .findFirst();
        }
    }

    // Test implementation of EntradaCatalogo
    private static class TestEntrada implements EntradaCatalogo {
        private final String codigo;
        private final String descripcion;
        private final Map<String, String> atributos;

        TestEntrada(String codigo, String descripcion) {
            this.codigo = codigo;
            this.descripcion = descripcion;
            this.atributos = new HashMap<>();
            this.atributos.put("codigo", codigo);
            this.atributos.put("descripcion", descripcion);
        }

        @Override
        public String getCodigo() {
            return codigo;
        }

        @Override
        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public Optional<String> getAtributoAdicional(String clave) {
            return Optional.ofNullable(atributos.get(clave));
        }

        @Override
        public Map<String, String> getTodosAtributos() {
            return Collections.unmodifiableMap(new HashMap<>(atributos));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestEntrada that = (TestEntrada) o;
            return codigo.equals(that.codigo);
        }

        @Override
        public int hashCode() {
            return codigo.hashCode();
        }
    }
}
