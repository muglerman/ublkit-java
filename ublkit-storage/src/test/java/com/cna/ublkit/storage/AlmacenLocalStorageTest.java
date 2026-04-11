package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AlmacenLocalStorageTest {

    private Path tempDir;
    private AlmacenLocalStorage almacen;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("ublkit-storage-test");
        almacen = new AlmacenLocalStorage(tempDir.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempDir)) {
            try (Stream<Path> walk = Files.walk(tempDir)) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(java.io.File::delete);
            }
        }
    }

    @Test
    void guardar_bytes_exito() throws IOException {
        String ruta = "2024/01/archivo.txt";
        byte[] contenido = "Hola Mundo".getBytes();

        ResultadoOperacion<String> resultado = almacen.guardar(ruta, contenido, "text/plain");

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).endsWith("archivo.txt");

        Path archivoGuardado = tempDir.resolve(ruta);
        assertThat(Files.exists(archivoGuardado)).isTrue();
        assertThat(Files.readString(archivoGuardado)).isEqualTo("Hola Mundo");
    }

    @Test
    void guardar_inputStream_exito() throws IOException {
        String ruta = "test-stream.xml";
        InputStream stream = new ByteArrayInputStream("<xml></xml>".getBytes());

        ResultadoOperacion<String> resultado = almacen.guardar(ruta, stream, "application/xml");

        assertThat(resultado.exito()).isTrue();

        Path archivoGuardado = tempDir.resolve(ruta);
        assertThat(Files.exists(archivoGuardado)).isTrue();
        assertThat(Files.readString(archivoGuardado)).isEqualTo("<xml></xml>");
    }

    @Test
    void descargar_exito() throws IOException {
        String ruta = "doc.pdf";
        Path path = tempDir.resolve(ruta);
        Files.write(path, "pdf_content".getBytes());

        ResultadoOperacion<byte[]> resultado = almacen.descargar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(new String(resultado.dato())).isEqualTo("pdf_content");
    }

    @Test
    void descargar_archivo_no_existente_devuelve_error() {
        ResultadoOperacion<byte[]> resultado = almacen.descargar("no-existe.txt");

        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.codigoError()).isEqualTo("NOT_FOUND");
    }

    @Test
    void eliminar_exito() throws IOException {
        String ruta = "eliminar.txt";
        Path path = tempDir.resolve(ruta);
        Files.write(path, "content".getBytes());

        ResultadoOperacion<Boolean> resultado = almacen.eliminar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isTrue();
        assertThat(Files.exists(path)).isFalse();
    }
}
