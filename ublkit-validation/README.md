<!-- prettier-ignore -->
<div align="center">

# ublkit-validation

**Validación funcional y normativa de documentos UBL**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![XSLT](https://img.shields.io/badge/XSLT-SUNAT-9c27b0?style=flat-square)](https://www.w3.org/TR/xslt-30/)
[![UBL](https://img.shields.io/badge/UBL-validation-2f855a?style=flat-square)](https://docs.oasis-open.org/ubl/UBL-2.1.html)
[![SUNAT](https://img.shields.io/badge/SUNAT-rules-1f4e79?style=flat-square)](https://cpe.sunat.gob.pe)

Reglas Java y validación SUNAT XSLT con acumulación de incidencias.

[Uso](#uso) |
[Estructura](#estructura) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-validation` valida documentos antes de firma/envío. Acumula errores y advertencias en `ResultadoValidacion` y puede ejecutar reglas XSLT oficiales SUNAT.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-validation</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Paquete | Contenido |
| --- | --- |
| `validation/api/` | `Validador`, `ResultadoValidacion`, incidencias |
| `validation/document/` | Validadores funcionales por documento |
| `validation/sunat/` | Validación XSLT SUNAT |
| `src/main/resources/sunat/` | XSL oficiales |

## Características

- Validación acumulativa.
- Severidad de errores y advertencias.
- Reglas funcionales Java.
- XSLT SUNAT cacheado con `Templates`.

## Reglas

- No usar excepciones para errores de negocio de validación.
- No recompilar XSL desde disco por cada request.
- Nuevas reglas deben ser testeables y trazables.

## Pruebas

```bash
mvn test -pl ublkit-validation
```

Cubrir ruta local y ruta XSLT, incluyendo documentos mal formados.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
