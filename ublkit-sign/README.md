# ublkit-sign

**Firma digital XMLDSig para UBL**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![XMLDSig](https://img.shields.io/badge/XMLDSig-signature-2f855a?style=flat-square)](https://www.w3.org/TR/xmldsig-core/)
[![X.509](https://img.shields.io/badge/X.509-certificates-455a64?style=flat-square)](https://www.itu.int/rec/T-REC-X.509)

Firma XML enveloped con certificados X.509 desde PKCS12/JKS.

[Uso](#uso) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

---

## Descripción General

`ublkit-sign` firma XML UBL con APIs criptográficas estándar de Java. Trabaja en memoria y devuelve XML firmado junto con datos útiles para QR y envío.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-sign</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Características

- Carga de certificados PKCS12 y JKS.
- Firma enveloped XMLDSig.
- Extracción de digest/hash y XML firmado.
- Apta para SaaS multi-tenant: certificado por solicitud.

## Reglas

- Cero archivos temporales.
- No cachear estáticamente keystores de tenant.
- Fallos técnicos deben ser explícitos y trazables.

## Pruebas

```bash
mvn test -pl ublkit-sign
```

Validar estructura XMLDSig, referencias y ruptura ante alteración del XML.

---

Desarrollado por **Crea Nexus Atreus**

