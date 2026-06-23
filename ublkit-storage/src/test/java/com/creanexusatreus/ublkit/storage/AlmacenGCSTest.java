package com.cna.ublkit.storage;

import com.cna.ublkit.core.modelo.ResultadoOperacion;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AlmacenGCSTest {

    private Storage storageMock;
    private AlmacenGCS almacen;
    private final String bucket = "test-bucket";

    @BeforeEach
    void setUp() {
        storageMock = mock(Storage.class);
        almacen = new AlmacenGCS(storageMock, bucket);
    }

    @Test
    void guardar_bytes_exito() {
        String ruta = "docs/file.xml";
        byte[] contenido = "<xml></xml>".getBytes();

        ResultadoOperacion<String> resultado = almacen.guardar(ruta, contenido, "application/xml");

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo("gs://test-bucket/docs/file.xml");

        ArgumentCaptor<BlobInfo> blobInfoCaptor = ArgumentCaptor.forClass(BlobInfo.class);
        verify(storageMock).create(blobInfoCaptor.capture(), eq(contenido));

        BlobInfo capturedInfo = blobInfoCaptor.getValue();
        assertThat(capturedInfo.getBlobId().getBucket()).isEqualTo(bucket);
        assertThat(capturedInfo.getBlobId().getName()).isEqualTo(ruta);
        assertThat(capturedInfo.getContentType()).isEqualTo("application/xml");
    }

    @Test
    void descargar_exito() {
        String ruta = "docs/file.pdf";
        byte[] bytesMock = "pdf-content".getBytes();

        Blob blobMock = mock(Blob.class);
        when(blobMock.exists()).thenReturn(true);
        when(blobMock.getContent()).thenReturn(bytesMock);
        when(storageMock.get(any(BlobId.class))).thenReturn(blobMock);

        ResultadoOperacion<byte[]> resultado = almacen.descargar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isEqualTo(bytesMock);

        ArgumentCaptor<BlobId> blobIdCaptor = ArgumentCaptor.forClass(BlobId.class);
        verify(storageMock).get(blobIdCaptor.capture());

        BlobId capturedId = blobIdCaptor.getValue();
        assertThat(capturedId.getBucket()).isEqualTo(bucket);
        assertThat(capturedId.getName()).isEqualTo(ruta);
    }

    @Test
    void descargar_archivo_no_existente_devuelve_error() {
        when(storageMock.get(any(BlobId.class))).thenReturn(null);

        ResultadoOperacion<byte[]> resultado = almacen.descargar("no-existe.txt");

        assertThat(resultado.exito()).isFalse();
        assertThat(resultado.codigoError()).isEqualTo("NOT_FOUND");
    }

    @Test
    void eliminar_exito() {
        String ruta = "eliminar.txt";
        when(storageMock.delete(any(BlobId.class))).thenReturn(true);

        ResultadoOperacion<Boolean> resultado = almacen.eliminar(ruta);

        assertThat(resultado.exito()).isTrue();
        assertThat(resultado.dato()).isTrue();

        ArgumentCaptor<BlobId> blobIdCaptor = ArgumentCaptor.forClass(BlobId.class);
        verify(storageMock).delete(blobIdCaptor.capture());

        BlobId capturedId = blobIdCaptor.getValue();
        assertThat(capturedId.getBucket()).isEqualTo(bucket);
        assertThat(capturedId.getName()).isEqualTo(ruta);
    }
}
