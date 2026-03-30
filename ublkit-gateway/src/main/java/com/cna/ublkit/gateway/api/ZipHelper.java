package com.cna.ublkit.gateway.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Utilitario simple para generar archivos ZIP requeridos por los envíos de SUNAT.
 *
 * @since 0.1.0
 */
public final class ZipHelper {

    private ZipHelper() { }

    /**
     * Comprime un String (XML) en un archivo ZIP devolviendo los bytes.
     *
     * @param contenidoXml El contenido del documento XML.
     * @param nombreArchivo El nombre del archivo dentro del ZIP (incluyendo extensión .xml).
     * @return Arreglo de bytes del archivo ZIP comprimido.
     */
    public static byte[] comprimir(String contenidoXml, String nombreArchivo) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            
            ZipEntry entry = new ZipEntry(nombreArchivo);
            zos.putNextEntry(entry);
            zos.write(contenidoXml.getBytes(StandardCharsets.ISO_8859_1)); // SUNAT usa ISO-8859-1
            zos.closeEntry();
            zos.finish();
            
            return baos.toByteArray();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al comprimir el archivo XML a ZIP", e);
        }
    }
}
