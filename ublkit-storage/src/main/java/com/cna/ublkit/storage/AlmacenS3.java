package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;

/**
 * Implementación de {@link AlmacenDocumentos} para AWS S3 usando AWS SDK v2.
 */
public class AlmacenS3 implements AlmacenDocumentos {

    private final S3Client s3Client;
    private final String bucketName;

    public AlmacenS3(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, byte[] contenido, String contentType) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(rutaDestino)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(contenido));

            // Retorna un formato S3 URI, pero podria ser http URL
            return ResultadoOperacion.ok("s3://" + bucketName + "/" + rutaDestino);
        } catch (S3Exception e) {
            return ResultadoOperacion.error("S3_ERROR", "Error al guardar archivo en S3: " + e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al guardar archivo en S3: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<String> guardar(String rutaDestino, InputStream contenido, String contentType) {
        try {
            // El SDK v2 requiere el content length para InputStreams en general si se usa RequestBody.fromInputStream,
            // pero si no se sabe, puede dar problemas. Una alternativa es usar RequestBody.fromContentProvider o leer los bytes
            // Como esto es flexible, usamos fromBytes si es pequeño o RequestBody de InputStream.
            // Para simplicidad leemos a byte[] (asumiendo XML/PDF no gigantes). Si son grandes, debería proveerse longitud.
            byte[] bytes = contenido.readAllBytes();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(rutaDestino)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));

            return ResultadoOperacion.ok("s3://" + bucketName + "/" + rutaDestino);
        } catch (S3Exception e) {
            return ResultadoOperacion.error("S3_ERROR", "Error al guardar archivo desde flujo en S3: " + e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al guardar archivo desde flujo en S3: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<byte[]> descargar(String rutaArchivo) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(rutaArchivo)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return ResultadoOperacion.ok(objectBytes.asByteArray());
        } catch (NoSuchKeyException e) {
            return ResultadoOperacion.error("NOT_FOUND", "Archivo no encontrado en S3: " + rutaArchivo);
        } catch (S3Exception e) {
            return ResultadoOperacion.error("S3_ERROR", "Error al descargar archivo de S3: " + e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al descargar archivo de S3: " + e.getMessage());
        }
    }

    @Override
    public ResultadoOperacion<Boolean> eliminar(String rutaArchivo) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(rutaArchivo)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            // S3 devuelve 204 No Content incluso si no existía el objeto, así que asumimos éxito.
            return ResultadoOperacion.ok(true);
        } catch (S3Exception e) {
            return ResultadoOperacion.error("S3_ERROR", "Error al eliminar archivo de S3: " + e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            return ResultadoOperacion.error("STORAGE_ERROR", "Error inesperado al eliminar archivo de S3: " + e.getMessage());
        }
    }
}
