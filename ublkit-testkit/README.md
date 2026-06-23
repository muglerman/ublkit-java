# ublkit-testkit

**Fixtures, mocks y assertions para pruebas UBLKit**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-25a162?style=flat-square&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![AssertJ](https://img.shields.io/badge/AssertJ-assertions-455a64?style=flat-square)](https://assertj.github.io/doc/)
[![Mockito](https://img.shields.io/badge/Mockito-mocks-78a641?style=flat-square)](https://site.mockito.org)

Utilidades de test para validar XML, CDR y flujos sin depender de SUNAT real.

[Uso](#uso) |
[Características](#características) |
[Reglas](#reglas)

---

## Descripción General

`ublkit-testkit` no forma parte del runtime productivo. Provee fixtures documentales, assertions y simuladores usados por otros módulos de UBLKit.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-testkit</artifactId>
  <version>1.0.0</version>
  <scope>test</scope>
</dependency>
```

## Características

- Fixtures como facturas, notas y guías de ejemplo.
- Assertions XML/CDR.
- Simulador de gateway SUNAT.
- Respuestas SUNAT simuladas.

## Reglas

- Sin red externa.
- Fixtures legibles y orientados a casos normativos.
- Simuladores deben implementar interfaces reales.

---

Desarrollado por **Crea Nexus Atreus**

