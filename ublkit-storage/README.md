# ublkit-storage

Modulo de almacenamiento de artefactos de emision (XML, CDR, PDF, ZIP) con contrato comun y adaptadores por proveedor.

## Alcance
- Contrato unificado para guardar, descargar y eliminar archivos.
- Implementacion local para desarrollo y pruebas.
- Implementaciones cloud para AWS S3 y Google Cloud Storage.

## Contrato principal
- `AlmacenDocumentos`
  - `guardar(String rutaDestino, byte[] contenido, String contentType)`
  - `guardar(String rutaDestino, InputStream contenido, String contentType)`
  - `descargar(String rutaArchivo)`
  - `eliminar(String rutaArchivo)`

Todas las operaciones retornan `ResultadoOperacion<T>`.

## Implementaciones incluidas
- `AlmacenLocalStorage`
- `AlmacenS3`
- `AlmacenGCS`

## Dependencias
- `ublkit-core`
- AWS SDK S3 (opcional)
- Google Cloud Storage (opcional)

## Ejemplo local

```java
import com.cna.ublkit.storage.AlmacenDocumentos;
import com.cna.ublkit.storage.AlmacenLocalStorage;

AlmacenDocumentos almacen = new AlmacenLocalStorage("./data-ublkit");
var guardado = almacen.guardar("20123456789/2026/04/F001-1.xml", xmlBytes, "application/xml");

if (guardado.exito()) {
    System.out.println("Guardado en: " + guardado.dato());
}
```

## Ejemplo AWS S3

```java
import software.amazon.awssdk.services.s3.S3Client;

S3Client s3Client = S3Client.builder().build();
AlmacenDocumentos almacen = new AlmacenS3(s3Client, "mi-bucket");
```

## Ejemplo GCS

```java
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

Storage storage = StorageOptions.getDefaultInstance().getService();
AlmacenDocumentos almacen = new AlmacenGCS(storage, "mi-bucket-gcs");
```

## Notas operativas
- `AlmacenLocalStorage` protege contra path traversal validando que la ruta final permanezca dentro del directorio base.
- Adaptadores cloud retornan URI logica (`s3://...`, `gs://...`) como dato de exito al guardar.
- Dependencias cloud son opcionales para no forzar peso en escenarios locales.
