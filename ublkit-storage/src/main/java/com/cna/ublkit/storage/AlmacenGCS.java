package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;

import java.io.InputStream;

/**
 * Implementación de {@link AlmacenDocumentos} para Google Cloud Storage.
 */
public class AlmacenGCS implements AlmacenDocumentos {

    private final Storage storage;
    private final String bucketName;

    public AlmacenGCS(Storage storage, String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, byte[] contenido, String contentType) {
        try {
            BlobId blobId = BlobId.of(bucketName, rutaDestino);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
            storage.create(blobInfo, contenido);

            return ResultadoOperacion.ok("gs://" + bucketName + "/" + rutaDestino);
        } catch (StorageException e) {
            return ResultadoOperacion.error("GCS_ERROR", "Error al guardar archivo en GCS: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al guardar archivo en GCS: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, InputStream contenido, String contentType) {
        try {
            byte[] bytes = contenido.readAllBytes();
            return guardar(rutaDestino, bytes, contentType);
        } catch (StorageException e) {
            return ResultadoOperacion.error("GCS_ERROR", "Error al guardar archivo desde flujo en GCS: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al guardar archivo desde flujo en GCS: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<byte[]> descargar(String rutaArchivo) {
        try {
            BlobId blobId = BlobId.of(bucketName, rutaArchivo);
            Blob blob = storage.get(blobId);

            if (blob == null || !blob.exists()) {
                return ResultadoOperacion.error("NOT_FOUND", "Archivo no encontrado en GCS: " + rutaArchivo);
            }

            byte[] bytes = blob.getContent();
            return ResultadoOperacion.ok(bytes);
        } catch (StorageException e) {
            return ResultadoOperacion.error("GCS_ERROR", "Error al descargar archivo de GCS: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al descargar archivo de GCS: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<Boolean> eliminar(String rutaArchivo) {
        try {
            BlobId blobId = BlobId.of(bucketName, rutaArchivo);
            boolean deleted = storage.delete(blobId);
            return ResultadoOperacion.ok(deleted);
        } catch (StorageException e) {
            return ResultadoOperacion.error("GCS_ERROR", "Error al eliminar archivo de GCS: " + e.getMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al eliminar archivo de GCS: " + e.getMessage());
        }
    }
}
