<!-- prettier-ignore -->
<div align="center">

# ublkit-gateway

**Clientes SUNAT/OSE para envío y consulta**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![SUNAT](https://img.shields.io/badge/SUNAT-gateway-1f4e79?style=flat-square)](https://cpe.sunat.gob.pe)
[![SOAP](https://img.shields.io/badge/SOAP-services-795548?style=flat-square)](https://www.w3.org/TR/soap/)
[![REST](https://img.shields.io/badge/REST-GRE-009688?style=flat-square)](https://restfulapi.net)

Infraestructura HTTP/SOAP/REST para comprobantes, tickets, CDR y GRE.

[Uso](#uso) |
[Estructura](#estructura) |
[Características](#características) |
[Pruebas](#pruebas)

</div>

---

## Descripción General

`ublkit-gateway` implementa el puerto de pasarela SUNAT/OSE. Maneja transporte, empaquetado ZIP, Base64, parseo de CDR, tickets y autenticación REST cuando aplica.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-gateway</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Paquete | Contenido |
| --- | --- |
| `gateway/` | `PasarelaSunat`, `ResultadoEnvio`, `ArchivoCdr` |
| `gateway/client/` | Clientes SOAP y REST |
| `gateway/auth/` | Proveedores OAuth2 |
| `gateway/config/` | Endpoints y configuración |

## Características

- Envío SOAP síncrono para comprobantes.
- Envío con ticket para resúmenes y bajas.
- REST OAuth2 para GRE.
- Empaquetado ZIP y codificación Base64.
- Parseo de CDR y estados.
- Timeouts configurables.

## Pruebas

```bash
mvn test -pl ublkit-gateway
```

Usar mocks de transporte para 2xx, 4xx, 5xx, tickets y CDR ZIP.

---

<div align="center">

Desarrollado por **Crea Nexus Atreus**

</div>
