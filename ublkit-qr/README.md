<!-- prettier-ignore -->
<div align="center">

# ublkit-qr

**Generación de QR SUNAT**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![ZXing](https://img.shields.io/badge/ZXing-QR-000000?style=flat-square)](https://github.com/zxing/zxing)
[![SUNAT](https://img.shields.io/badge/SUNAT-QR-1f4e79?style=flat-square)](https://cpe.sunat.gob.pe)

Construye la trama oficial y genera imagen PNG/Base64 para representaciones impresas.

[Uso](#uso) |
[Características](#características) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-qr` genera el código QR requerido por SUNAT a partir del documento ensamblado y la firma XML.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-qr</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Características

- Trama SUNAT con RUC, tipo, serie, número, IGV, total, fecha, receptor y hash.
- PNG en memoria.
- Salida Base64 o `byte[]`.
- Dependencia ZXing aislada del core.

## Pruebas

```bash
mvn test -pl ublkit-qr
```

Validar composición exacta de trama y decodificación inversa del QR.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
