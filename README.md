# UBLKit Java

**Librería Java para UBL 2.1, firma digital, SUNAT, QR, storage y PDF**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-multimodule-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-starter-6db33f?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Quarkus](https://img.shields.io/badge/Quarkus-extension-4695eb?style=flat-square&logo=quarkus&logoColor=white)](https://quarkus.io)
[![UBL](https://img.shields.io/badge/UBL-2.1-2f855a?style=flat-square)](https://docs.oasis-open.org/ubl/UBL-2.1.html)

Toolkit hexagonal para modelar, validar, firmar, enviar y renderizar documentos electrónicos.
Usado por Quantus Flow para XML UBL, firma X.509, SUNAT, QR y PDF corporativo.

[Inicio Rápido](#inicio-rápido) |
[Características](#características) |
[Arquitectura](#arquitectura) |
[Módulos](#módulos) |
[Desarrollo](#desarrollo) |
[Documentación](#documentación)

---

## Descripción General

UBLKit Java es una librería multi-módulo para comprobantes electrónicos peruanos y documentos UBL. Modela el dominio en español, evita APIs XML crudas en la aplicación y separa reglas de negocio de detalles técnicos mediante arquitectura hexagonal.

El proyecto cubre modelado, ensamblado, serialización UBL, firma digital, envío SUNAT/OSE, validación, QR, storage y renderizado PDF/ticket.

## Características

### Dominio UBL/SUNAT

- Facturas, boletas, notas de crédito/débito.
- Guías de remisión remitente y transportista.
- Resúmenes, bajas, retenciones y percepciones.
- Catálogos SUNAT y reglas normativas.
- Modelos y APIs en español para alinearse al dominio peruano.

### Firma, Envío y Validación

- Firma digital XML con X.509.
- Clientes SOAP/REST para SUNAT y OSE.
- Validación funcional en Java.
- Validación normativa con XSLT precompilado.
- Resultado de operaciones mediante `ResultadoOperacion<T>`.

### Render y Storage

- PDF A4/A5 y tickets POS 58/80mm.
- Plantillas Pebble renderizadas a PDF con Playwright (Chromium).
- Generación de QR SUNAT.
- Abstracciones de storage local, S3 y GCS.

### Integraciones

- Starter Spring Boot para integración directa con `quantus-flow-api`.
- Extensión Quarkus para entornos cloud-native.
- Testkit con fixtures, mocks y assertions.

## Inicio Rápido

### Prerrequisitos

| Requisito | Versión / Nota |
| --- | --- |
| Java | 21 |
| Maven | 3.8+ |
| Certificado digital | Requerido para firma real |
| Credenciales SUNAT/OSE | Requeridas para envío real |

### Build Local

```bash
git clone <repo-url>
cd ublkit-java

mvn clean install
```

### Uso con Spring Boot

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Uso por Módulo

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-ubl</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Ejemplo Básico

```java
BorradorFactura factura = new BorradorFactura();
factura.setSerie("F001");
factura.setNumero(1);

EnsambladorFactura.ensamblar(factura);

String xml = new SerializadorXmlFactura().serializar(factura);
ResultadoFirma firma = ServicioFirma.firmarXml(xml, certificado);

PasarelaSunat pasarela = new PasarelaSunatDefecto();
ResultadoEnvio envio = pasarela.enviarComprobante(
    firma.xmlFirmadoStr(), nombreArchivo, credenciales, TipoAmbiente.BETA);
```

## Arquitectura

UBLKit usa **Arquitectura Hexagonal**. Las dependencias apuntan hacia el dominio; los frameworks y detalles externos viven como adaptadores.

```
Infraestructura / Frameworks
  → Aplicación
      → Dominio
```

### Reglas de Diseño

- El dominio no depende de Spring, Quarkus ni clientes HTTP.
- Los adaptadores implementan puertos definidos por aplicación/dominio.
- XML, firma, gateways, storage y render son detalles reemplazables.
- El lenguaje del código es español por consistencia con la normativa local.
- Se prefiere inmutabilidad y `record` para objetos de valor.

## Módulos

| Módulo | Responsabilidad |
| --- | --- |
| `ublkit-core` | Modelos de negocio y puertos base |
| `ublkit-catalogs` | Catálogos normativos SUNAT |
| `ublkit-testkit` | Fixtures, mocks y assertions |
| `ublkit-ubl` | Ensambladores y serialización DOM UBL |
| `ublkit-validation` | Reglas Java y validadores XSLT |
| `ublkit-qr` | Trama y generación de QR |
| `ublkit-storage` | Storage local, S3 y GCS |
| `ublkit-sign` | Firma digital XML |
| `ublkit-gateway` | Clientes SUNAT/OSE |
| `ublkit-render` | Plantillas visuales y conversión PDF/ticket |
| `ublkit-spring-boot-starter` | Auto-configuración Spring Boot |
| `ublkit-quarkus` | Extensión Quarkus |

Cada módulo puede tener su propio README con detalle específico.

## Desarrollo

### Comandos

| Comando | Descripción |
| --- | --- |
| `mvn clean install` | Compila e instala todos los módulos |
| `mvn test` | Ejecuta pruebas de todo el ecosistema |
| `mvn test -pl ublkit-render` | Ejecuta pruebas de un módulo |
| `mvn clean install -DskipTests` | Build rápido sin tests |

Desde el workspace raíz:

```bash
make ublkit-build
make ublkit-test
make ublkit-install
make ublkit-clean
```

### Testing

- JUnit 5, AssertJ y Mockito.
- Golden XMLs para cambios normativos y serialización.
- `ublkit-testkit` provee mocks y assertions reutilizables.
- Cada cambio de plantilla/render debe validarse con salidas PDF o tests visuales cuando aplique.

### Estándares

- Java puro donde sea razonable.
- Nombres de dominio en español (`BorradorFactura`, no `InvoiceDraft`).
- Separación estricta entre dominio, aplicación e infraestructura.
- Excepciones para fallos técnicos; resultados explícitos para flujo de negocio.

## Documentación

| Recurso | Contenido |
| --- | --- |
| `ublkit-core/README.md` | Dominio y puertos base |
| `ublkit-ubl/README.md` | Ensamblado y serialización UBL |
| `ublkit-sign/README.md` | Firma digital |
| `ublkit-gateway/README.md` | SUNAT/OSE |
| `ublkit-render/README.md` | PDF, tickets y plantillas |
| `ublkit-validation/README.md` | Validadores |
| `ublkit-catalogs/README.md` | Catálogos SUNAT |
| `ublkit-qr/README.md` | Trama y generación de QR |
| `ublkit-storage/README.md` | Storage |
| `ublkit-testkit/README.md` | Utilidades de prueba |
| `ublkit-spring-boot-starter/README.md` | Integración Spring Boot |
| `ublkit-quarkus/README.md` | Integración Quarkus |

## Solución de Problemas

### Un módulo no resuelve dependencias internas

Instala desde la raíz del multi-módulo:

```bash
mvn clean install
```

O desde el workspace Perseo:

```bash
make ublkit-install
```

### El PDF no refleja cambios de plantilla

Verifica que el cambio esté en `ublkit-render`, que la plantilla seleccionada sea la usada por el caller y que el build haya instalado el módulo actualizado.

### La firma falla con certificado real

Confirma contraseña, formato del certificado, alias esperado y que el XML contenga el nodo de firma configurado.

---

Desarrollado por **Crea Nexus Atreus**

