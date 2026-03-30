package com.cna.ublkit.gateway.api;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
            assertThat(new String(contenido, "ISO-8859-1")).isEqualTo(xml);
        }
    }
}
