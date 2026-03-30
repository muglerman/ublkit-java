package com.cna.ublkit.gateway.api;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HashHelperTest {

    @Test
    void testSha256Hex() {
        String input = "hola mundo";
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        String hash = HashHelper.sha256Hex(bytes);

        // Hash conocido de "hola mundo"
        assertThat(hash).isEqualTo("0b894166d3336435c800bea36ff21b29eaa801a52f584c006c49289a0dcf6e2f");
    }
}
