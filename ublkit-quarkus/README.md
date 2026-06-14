<!-- prettier-ignore -->
<div align="center">

# ublkit-quarkus

**Integración CDI para Quarkus**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Quarkus](https://img.shields.io/badge/Quarkus-CDI-4695eb?style=flat-square&logo=quarkus&logoColor=white)](https://quarkus.io)
[![GraalVM](https://img.shields.io/badge/GraalVM-ready-f29111?style=flat-square)](https://www.graalvm.org)

Adaptador framework que expone servicios UBLKit como beans CDI.

[Uso](#uso) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-quarkus` conecta los servicios puros de UBLKit con aplicaciones Quarkus mediante productores CDI. No contiene reglas de negocio.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-quarkus</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Características

- Productores CDI para serializadores, validadores, renderizadores y firma.
- Beans sin estado aptos para inyección.
- Preparado para entornos AOT/GraalVM cuando el consumidor lo configure.

## Reglas

- Solo wiring de framework.
- No agregar lógica de negocio.
- Nuevos servicios UBLKit deben tener productor explícito.

## Pruebas

```bash
mvn test -pl ublkit-quarkus
```

Validar carga de contexto CDI y ausencia de ciclos.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
