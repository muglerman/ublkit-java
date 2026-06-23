# ublkit-core

**Dominio base y tipos transversales de UBLKit**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Hexagonal](https://img.shields.io/badge/Architecture-Hexagonal-2f855a?style=flat-square)](https://alistair.cockburn.us/hexagonal-architecture/)
[![Records](https://img.shields.io/badge/Java-records-455a64?style=flat-square)](https://docs.oracle.com/en/java/javase/21/language/records.html)

Núcleo sin dependencias externas para objetos de valor, enums, resultados y excepciones.

[Uso](#uso) |
[Estructura](#estructura) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

---

## Descripción General

`ublkit-core` es la capa más interna de la arquitectura hexagonal. No conoce XML, HTTP, SOAP, Spring ni Quarkus. Otros módulos dependen de este, nunca al revés.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Paquete | Contenido |
| --- | --- |
| `valor` | `Dinero`, `Moneda`, `NumeroSerie` y objetos inmutables |
| `enumerado` | Tipos de documento, ambiente y enums transversales |
| `error` | Excepciones base |
| `modelo` | `ResultadoOperacion` y clases comunes |

## Reglas

- Sin dependencias de infraestructura.
- Tipos de dominio en español.
- Preferir `record` para objetos de valor.
- Usar resultados explícitos para flujo de negocio.

## Pruebas

```bash
mvn test -pl ublkit-core
```

Cubrir objetos de valor, invariantes, excepciones y `ResultadoOperacion`.

---

Desarrollado por **Crea Nexus Atreus**

