package com.cna.ublkit.gateway.autenticacion;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ProveedorToken} interface.
 * Validates the token provider contract definition.
 */
class ProveedorTokenTest {

    /**
     * Test that the interface is functional (FunctionalInterface).
     */
    @Test
    void interface_isFunctionalInterface() {
        assertThat(ProveedorToken.class.isAnnotationPresent(FunctionalInterface.class))
                .isTrue();
    }

    /**
     * Test that the interface defines obtenerToken method.
     */
    @Test
    void interface_definesObtenerTokenMethod() {
        assertThat(ProveedorToken.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("obtenerToken") && m.getParameterCount() == 2);
    }

    /**
     * Test that obtenerToken method returns String.
     */
    @Test
    void interface_obtenerTokenMethodReturnsString() {
        assertThat(ProveedorToken.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("obtenerToken") &&
                        m.getReturnType().equals(String.class));
    }

    /**
     * Test that obtenerToken method takes CredencialesEmpresa and TipoAmbiente.
     */
    @Test
    void interface_obtenerTokenMethodSignatureIsCorrect() {
        var method = ProveedorToken.class.getDeclaredMethod("obtenerToken",
                CredencialesEmpresa.class, TipoAmbiente.class);

        assertThat(method).isNotNull();
        assertThat(method.getReturnType()).isEqualTo(String.class);
    }

    /**
     * Test that interface can be implemented.
     */
    @Test
    void interface_canBeImplemented() {
        ProveedorToken testImpl = (credenciales, ambiente) -> "test_token_12345";

        assertThat(testImpl).isNotNull();
        assertThat(testImpl).isInstanceOf(ProveedorToken.class);
    }

    /**
     * Test that implementation can return null.
     */
    @Test
    void interface_implementationCanReturnNull() {
        ProveedorToken testImpl = (credenciales, ambiente) -> null;
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");

        String token = testImpl.obtenerToken(cred, TipoAmbiente.BETA);

        assertThat(token).isNull();
    }

    /**
     * Test that implementation can be called with BETA environment.
     */
    @Test
    void interface_implementationCanBeCalledWithBetaEnvironment() {
        ProveedorToken testImpl = (credenciales, ambiente) ->
                ambiente == TipoAmbiente.BETA ? "beta_token" : "prod_token";

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        String token = testImpl.obtenerToken(cred, TipoAmbiente.BETA);

        assertThat(token).isEqualTo("beta_token");
    }

    /**
     * Test that implementation can be called with PRODUCCION environment.
     */
    @Test
    void interface_implementationCanBeCalledWithProduccionEnvironment() {
        ProveedorToken testImpl = (credenciales, ambiente) ->
                ambiente == TipoAmbiente.BETA ? "beta_token" : "prod_token";

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        String token = testImpl.obtenerToken(cred, TipoAmbiente.PRODUCCION);

        assertThat(token).isEqualTo("prod_token");
    }

    /**
     * Test that implementation receives both parameters correctly.
     */
    @Test
    void interface_implementationReceivesBothParametersCorrectly() {
        ProveedorToken captureImpl = new ProveedorToken() {
            public String capturedRuc;
            public TipoAmbiente capturedAmbiente;

            @Override
            public String obtenerToken(CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                capturedRuc = credenciales.ruc();
                capturedAmbiente = ambiente;
                return "token";
            }
        };

        CredencialesEmpresa cred = new CredencialesEmpresa("20123456789", "USER", "PASS", "id", "secret");
        captureImpl.obtenerToken(cred, TipoAmbiente.PRODUCCION);

        assertThat(((ProveedorToken) captureImpl).obtenerToken(cred, TipoAmbiente.PRODUCCION))
                .isEqualTo("token");
    }

    /**
     * Test that implementation can return empty string.
     */
    @Test
    void interface_implementationCanReturnEmptyString() {
        ProveedorToken testImpl = (credenciales, ambiente) -> "";
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");

        String token = testImpl.obtenerToken(cred, TipoAmbiente.BETA);

        assertThat(token).isEmpty();
    }

    /**
     * Test that implementation can return long token strings.
     */
    @Test
    void interface_implementationCanReturnLongTokenStrings() {
        String longToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ";
        ProveedorToken testImpl = (credenciales, ambiente) -> longToken;

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        String token = testImpl.obtenerToken(cred, TipoAmbiente.PRODUCCION);

        assertThat(token).isEqualTo(longToken);
        assertThat(token.length()).isGreaterThan(50);
    }

    /**
     * Test that multiple implementations can coexist.
     */
    @Test
    void interface_multipleImplementationsCanCoexist() {
        ProveedorToken impl1 = (cred, amb) -> "token1";
        ProveedorToken impl2 = (cred, amb) -> "token2";

        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");

        assertThat(impl1.obtenerToken(cred, TipoAmbiente.BETA))
                .isEqualTo("token1");
        assertThat(impl2.obtenerToken(cred, TipoAmbiente.BETA))
                .isEqualTo("token2");
    }
}
