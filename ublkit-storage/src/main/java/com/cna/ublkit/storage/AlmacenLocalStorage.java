package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

/**
 * Implementación de {@link AlmacenDocumentos} que guarda los archivos en el sistema de archivos local.
 */
public class AlmacenLocalStorage implements AlmacenDocumentos {

    private static final Logger log = Logger.getLogger(AlmacenLocalStorage.class.getName());

    private final Path directorioBase;

    public AlmacenLocalStorage(String rutaDirectorioBase) {
        log.warning("[UBLKIT-STORAGE] ADVERTENCIA: Usando AlmacenLocalStorage. Si la aplicación se ejecuta en Docker, asegúrese de mapear los volúmenes correctamente (ej. -v /ruta/host:/ruta/contenedor) para evitar pérdida de datos o inconsistencias.");
        this.directorioBase = Paths.get(rutaDirectorioBase).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.directorioBase);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio base: " + this.directorioBase, e);
        }
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, byte[] contenido, String contentType) {
        try {
            Path destinoFinal = resolverRuta(rutaDestino);
            Files.write(destinoFinal, contenido);
            return ResultadoOperacion.ok(destinoFinal.toString());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error al guardar archivo en local: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, InputStream contenido, String contentType) {
        try {
            Path destinoFinal = resolverRuta(rutaDestino);
            Files.copy(contenido, destinoFinal, StandardCopyOption.REPLACE_EXISTING);
            return ResultadoOperacion.ok(destinoFinal.toString());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error al guardar archivo desde flujo en local: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<byte[]> descargar(String rutaArchivo) {
        try {
            Path target = this.directorioBase.resolve(rutaArchivo).normalize();
            if (!target.startsWith(this.directorioBase)) {
                return ResultadoOperacion.error("SECURITY_ERROR", "Ruta de archivo inválida");
            }
            if (!Files.exists(target)) {
                return ResultadoOperacion.error("NOT_FOUND", "Archivo no encontrado: " + rutaArchivo);
            }
            byte[] bytes = Files.readAllBytes(target);
            return ResultadoOperacion.ok(bytes);
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error al leer el archivo en local: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<Boolean> eliminar(String rutaArchivo) {
        try {
            Path target = this.directorioBase.resolve(rutaArchivo).normalize();
            if (!target.startsWith(this.directorioBase)) {
                return ResultadoOperacion.error("SECURITY_ERROR", "Ruta de archivo inválida");
            }
            boolean deleted = Files.deleteIfExists(target);
            return ResultadoOperacion.ok(deleted);
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error al eliminar archivo en local: " + e.getMessage());
        }
    }

    private Path resolverRuta(String rutaDestino) throws IOException {
        Path destinoFinal = this.directorioBase.resolve(rutaDestino).normalize();
        if (!destinoFinal.startsWith(this.directorioBase)) {
            throw new IllegalArgumentException("Ruta de destino inválida");
        }
        Files.createDirectories(destinoFinal.getParent());
        return destinoFinal;
    }
}
