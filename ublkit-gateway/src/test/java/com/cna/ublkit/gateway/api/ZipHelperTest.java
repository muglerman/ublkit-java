package com.cna.ublkit.gateway.api;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ZipHelperTest {

    @Test
    void testComprimir() throws Exception {
        String xml = "<test>Hola</test>";
        String nombre = "test.xml";

        byte[] zipBytes = ZipHelper.comprimir(xml, nombre);
        assertThat(zipBytes).isNotEmpty();

        // Verificar que realmente es un ZIP válido y contiene el archivo
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry = zis.getNextEntry();
            assertThat(entry).isNotNull();
            assertThat(entry.getName()).isEqualTo(nombre);

            byte[] contenido = zis.readAllBytes();
            assertThat(new String(contenido, StandardCharsets.ISO_8859_1)).isEqualTo(xml);
        }
    }

    @Test
    void testComprimirBase64() throws Exception {
        String xml = "<test>Hola</test>";
        String nombre = "test.xml";

        String zipBase64 = ZipHelper.comprimirBase64(xml, nombre);
        assertThat(zipBase64).isNotBlank();

        byte[] zipBytes = Base64.getDecoder().decode(zipBase64);
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry = zis.getNextEntry();
            assertThat(entry).isNotNull();
            assertThat(entry.getName()).isEqualTo(nombre);

            byte[] contenido = zis.readAllBytes();
            assertThat(new String(contenido, StandardCharsets.ISO_8859_1)).isEqualTo(xml);
        }
    }
}
