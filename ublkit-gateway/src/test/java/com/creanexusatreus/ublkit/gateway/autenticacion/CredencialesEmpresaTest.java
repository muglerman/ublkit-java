package com.cna.ublkit.gateway.autenticacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link CredencialesEmpresa} record.
 * Tests RUC validation, username/password handling, and null checks.
 */
class CredencialesEmpresaTest {

    @Test
    void constructor_withAllValidFields_createsInstanceSuccessfully() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20123456789", "USUARIO", "CLAVE123", "client_id", "client_secret");

        assertThat(cred).isNotNull();
        assertThat(cred.ruc()).isEqualTo("20123456789");
        assertThat(cred.usuarioSol()).isEqualTo("USUARIO");
        assertThat(cred.claveSol()).isEqualTo("CLAVE123");
        assertThat(cred.clientId()).isEqualTo("client_id");
        assertThat(cred.clientSecret()).isEqualTo("client_secret");
    }

    @Test
    void constructor_withRuc11Digits_acceptsValidRuc() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        assertThat(cred.ruc()).isEqualTo("20000000000");
    }

    @Test
    void constructor_withNullRuc_acceptsNullAndHandlesInUsernameConcatenado() {
        CredencialesEmpresa cred = new CredencialesEmpresa(null, "USUARIO", "PASS", "id", "secret");
        assertThat(cred.ruc()).isNull();
        assertThat(cred.getUsernameConcatenado()).isEqualTo("USUARIO");
    }

    @Test
    void constructor_withNullUsuario_acceptsNullAndHandlesInUsernameConcatenado() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", null, "PASS", "id", "secret");
        assertThat(cred.usuarioSol()).isNull();
        assertThat(cred.getUsernameConcatenado()).isEqualTo("20000000000");
    }

    @Test
    void constructor_withBothRucAndUsuarioNull_usernameConcatenadoReturnsEmptyString() {
        CredencialesEmpresa cred = new CredencialesEmpresa(null, null, "PASS", "id", "secret");
        assertThat(cred.getUsernameConcatenado()).isEqualTo("");
    }

    @Test
    void constructor_withEmptyStrings_acceptsAndHandlesCorrectly() {
        CredencialesEmpresa cred = new CredencialesEmpresa("", "", "PASS", "id", "secret");
        assertThat(cred.ruc()).isEmpty();
        assertThat(cred.usuarioSol()).isEmpty();
        assertThat(cred.getUsernameConcatenado()).isEmpty();
    }

    @Test
    void constructor_withNullClave_acceptsNull() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", null, "id", "secret");
        assertThat(cred.claveSol()).isNull();
    }

    @Test
    void constructor_withNullClientId_acceptsNull() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", null, "secret");
        assertThat(cred.clientId()).isNull();
    }

    @Test
    void constructor_withNullClientSecret_acceptsNull() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", null);
        assertThat(cred.clientSecret()).isNull();
    }

    @Test
    void getUsernameConcatenado_withBothNonNull_concatenatesRucAndUsuario() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20123456789", "MODDATOS", "PASS", "id", "secret");
        assertThat(cred.getUsernameConcatenado()).isEqualTo("20123456789MODDATOS");
    }

    @Test
    void getUsernameConcatenado_withSpecialCharactersInUsuario_preservesCharacters() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER-001", "PASS", "id", "secret");
        assertThat(cred.getUsernameConcatenado()).isEqualTo("20000000000USER-001");
    }

    @Test
    void record_implementsEqualsAndHashCode_correctlyComparesInstances() {
        CredencialesEmpresa cred1 = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        CredencialesEmpresa cred2 = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");

        assertThat(cred1).isEqualTo(cred2);
        assertThat(cred1.hashCode()).isEqualTo(cred2.hashCode());
    }

    @Test
    void record_implementsEqualsAndHashCode_distinguishesDifferentInstances() {
        CredencialesEmpresa cred1 = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        CredencialesEmpresa cred2 = new CredencialesEmpresa("20111111111", "USER", "PASS", "id", "secret");

        assertThat(cred1).isNotEqualTo(cred2);
        assertThat(cred1.hashCode()).isNotEqualTo(cred2.hashCode());
    }

    @Test
    void record_implementsToString_providesReadableRepresentation() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");
        String toString = cred.toString();

        assertThat(toString).contains("CredencialesEmpresa");
        assertThat(toString).contains("20000000000");
        assertThat(toString).contains("USER");
    }

    @Test
    void record_withLongPasswordStrings_acceptsAndHandlesCorrectly() {
        String longPassword = "VERYLONGPASSWORD1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", longPassword, "id", "secret");

        assertThat(cred.claveSol()).isEqualTo(longPassword);
    }

    @Test
    void record_withSpecialCharactersInClave_preservesCharacters() {
        String claveSpecial = "P@$$w0rd!#$%^&*()";
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", claveSpecial, "id", "secret");

        assertThat(cred.claveSol()).isEqualTo(claveSpecial);
    }

    @Test
    void record_withUnicodeCharactersInFieldsHandleCorrectly() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USÉR", "PASSWÓRD", "id", "secret");

        assertThat(cred.usuarioSol()).isEqualTo("USÉR");
        assertThat(cred.claveSol()).isEqualTo("PASSWÓRD");
    }

    @Test
    void constructor_withAllNullFields_acceptsAndHandlesGracefully() {
        CredencialesEmpresa cred = new CredencialesEmpresa(null, null, null, null, null);

        assertThat(cred.ruc()).isNull();
        assertThat(cred.usuarioSol()).isNull();
        assertThat(cred.claveSol()).isNull();
        assertThat(cred.clientId()).isNull();
        assertThat(cred.clientSecret()).isNull();
        assertThat(cred.getUsernameConcatenado()).isEmpty();
    }

    @Test
    void getUsernameConcatenado_calledMultipleTimes_returnsConsistentResults() {
        CredencialesEmpresa cred = new CredencialesEmpresa("20000000000", "USER", "PASS", "id", "secret");

        String username1 = cred.getUsernameConcatenado();
        String username2 = cred.getUsernameConcatenado();

        assertThat(username1).isEqualTo(username2);
        assertThat(username1).isEqualTo("20000000000USER");
    }
}
