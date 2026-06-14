<!-- prettier-ignore -->
<div align="center">

# ublkit-ubl

**Modelado, ensamblado y serialización XML UBL 2.1**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![UBL](https://img.shields.io/badge/UBL-2.1-2f855a?style=flat-square)](https://docs.oasis-open.org/ubl/UBL-2.1.html)
[![XML](https://img.shields.io/badge/XML-DOM-ff6600?style=flat-square)](https://www.w3.org/XML/)
[![SUNAT](https://img.shields.io/badge/SUNAT-CPE-1f4e79?style=flat-square)](https://cpe.sunat.gob.pe)

Convierte borradores de dominio en XML UBL exigido por SUNAT.

[Uso](#uso) |
[Estructura](#estructura) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-ubl` contiene modelos concretos de documentos, ensambladores de reglas de negocio y serializadores DOM hacia XML UBL 2.1.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-ubl</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Paquete | Contenido |
| --- | --- |
| `ubl/domain/` | Borradores y documentos base |
| `ubl/assembler/` | Ensambladores y cálculo de totales |
| `ubl/xml/` | Serializadores DOM y fragmentos XML |
| `ubl/util/` | Utilidades como números a letras |

## Características

- Factura, boleta, notas, guías, resúmenes, bajas, retenciones y percepciones.
- Cálculo de IGV, ISC, ICBPER, totales y leyendas.
- Serialización DOM sin JAXB.

## Reglas

- XML con DOM nativo para controlar namespaces.
- No usar plantillas de texto para XML fiscal.
- Mantener invariantes después del ensamblado.

## Pruebas

```bash
mvn test -pl ublkit-ubl
```

Validar XML estructural, golden XMLs y casos normativos.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
