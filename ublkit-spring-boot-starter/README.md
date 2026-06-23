# ublkit-spring-boot-starter

**Auto-configuración Spring Boot para UBLKit**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-starter-6db33f?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![AutoConfiguration](https://img.shields.io/badge/Spring-AutoConfiguration-2f855a?style=flat-square)](https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html)

Registra beans UBLKit por defecto y permite reemplazos con `@ConditionalOnMissingBean`.

[Uso](#uso) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

---

## Descripción General

`ublkit-spring-boot-starter` adapta UBLKit al contenedor Spring. No expone controllers; solo wiring de renderizadores, serializadores, validadores, gateway SUNAT, QR, storage y propiedades.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Características

- `UblKitAutoConfiguration`.
- `UblKitProperties` y subpropiedades.
- Beans por defecto reemplazables.
- Integración vía `application.yml`.

## Reglas

- Solo auto-configuración y wiring.
- Mantener fallbacks seguros.
- No agregar lógica HTTP ni reglas de negocio.
- Exponer propiedades explícitas y documentables.

## Pruebas

```bash
mvn test -pl ublkit-spring-boot-starter
```

Validar carga de contexto y reemplazo de beans por el consumidor.

---

Desarrollado por **Crea Nexus Atreus**

