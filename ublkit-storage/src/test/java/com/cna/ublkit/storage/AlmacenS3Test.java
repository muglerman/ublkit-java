package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlmacenS3Test {

    private S3Client s3ClientMock;
    private AlmacenS3 almacen;
    private final String bucket = "test-bucket";

    @BeforeEach
    void setUp() {
        s3ClientMock = mock(S3Client.class);
        almacen = new AlmacenS3(s3ClientMock, bucket);
    }

    @Test
    void guardar_bytes_exito() {
        String ruta = "docs/file.xml";
        byte[] contenido = "<xml></xml>".getBytes();

        ResultadoOperacion<String> resultado = almacen.guardar(ruta, contenido, "application/xml");

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo("s3://test-bucket/docs/file.xml");

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3ClientMock).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.bucket()).isEqualTo(bucket);
        assertThat(capturedRequest.key()).isEqualTo(ruta);
        assertThat(capturedRequest.contentType()).isEqualTo("application/xml");
    }

    @Test
    void descargar_exito() {
        String ruta = "docs/file.pdf";
        byte[] bytesMock = "pdf-content".getBytes();

        ResponseBytes<GetObjectResponse> responseBytesMock = ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), bytesMock);
        when(s3ClientMock.getObjectAsBytes(any(GetObjectRequest.class))).thenReturn(responseBytesMock);

        ResultadoOperacion<byte[]> resultado = almacen.descargar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo(bytesMock);

        ArgumentCaptor<GetObjectRequest> requestCaptor = ArgumentCaptor.forClass(GetObjectRequest.class);
        verify(s3ClientMock).getObjectAsBytes(requestCaptor.capture());

        GetObjectRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.bucket()).isEqualTo(bucket);
        assertThat(capturedRequest.key()).isEqualTo(ruta);
    }

    @Test
    void descargar_archivo_no_existente_devuelve_error() {
        when(s3ClientMock.getObjectAsBytes(any(GetObjectRequest.class)))
                .thenThrow(NoSuchKeyException.builder().message("No key").build());

        ResultadoOperacion<byte[]> resultado = almacen.descargar("no-existe.txt");

        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.codigoError()).isEqualTo("NOT_FOUND");
    }

    @Test
    void eliminar_exito() {
        String ruta = "eliminar.txt";

        ResultadoOperacion<Boolean> resultado = almacen.eliminar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isTrue();

        ArgumentCaptor<DeleteObjectRequest> requestCaptor = ArgumentCaptor.forClass(DeleteObjectRequest.class);
        verify(s3ClientMock).deleteObject(requestCaptor.capture());

        DeleteObjectRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.bucket()).isEqualTo(bucket);
        assertThat(capturedRequest.key()).isEqualTo(ruta);
    }
}
