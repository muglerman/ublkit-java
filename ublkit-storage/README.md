<!-- prettier-ignore -->
<div align="center">

# ublkit-storage

**Abstracción de almacenamiento documental**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Amazon S3](https://img.shields.io/badge/S3-storage-569a31?style=flat-square&logo=amazons3&logoColor=white)](https://aws.amazon.com/s3/)
[![Google Cloud](https://img.shields.io/badge/GCS-storage-4285f4?style=flat-square&logo=googlecloud&logoColor=white)](https://cloud.google.com/storage)
[![Java NIO](https://img.shields.io/badge/Java-NIO-455a64?style=flat-square)](https://docs.oracle.com/javase/8/docs/api/java/nio/file/package-summary.html)

Puerto y adaptadores para XML, CDR, PDF, ZIP y artefactos de emisión.

[Uso](#uso) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-storage` abstrae dónde viven los artefactos documentales. Implementa almacenamiento local y adaptadores cloud para aplicaciones stateless.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-storage</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Características

- Contrato `AlmacenDocumentos`.
- Adaptador local para desarrollo/pruebas.
- Adaptadores cloud S3/GCS.
- Operaciones de guardar, recuperar y eliminar.

## Reglas

- Paths seguros y normalizados.
- Errores de proveedor se traducen a errores comunes.
- Nuevos proveedores deben implementar el mismo contrato.

## Pruebas

```bash
mvn test -pl ublkit-storage
```

Validar escritura/lectura local y adaptadores cloud con mocks o entornos controlados.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
