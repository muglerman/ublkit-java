# UBLKit

**UBLKit** es una librería comunitaria diseñada para modelar, validar, firmar, enviar y renderizar comprobantes de pago electrónicos o documentos UBL desde Java.

## Objetivo del proyecto
El propósito principal es ofrecer una solución integral, estructurada bajo **arquitectura hexagonal**, para aislar la lógica de los documentos de cualquier infraestructura o framework (como Spring o Quarkus). Se busca que el manejo de documentos electrónicos se oriente al **dominio** de uso amigable y no de forma exclusiva a través de APIs XML crudas o dependientes del medio de distribución.

## Estado del Proyecto
*   **Version 0.1.0-SNAPSHOT**
*   ✅ Core agnostic, modelos de dominio y objetos de valor.
*   ✅ Modelo documental: Factura, Boleta, Notas, Guía, Resumen, Baja, Percepción, Retención.
*   ✅ Ensambladores con auto-cálculo de impuestos (IGV, ISC, ICBPER), totales, leyendas SUNAT y reglas de boleta.
*   ✅ Serialización XML UBL 2.1 (javax.xml DOM): Invoice, CreditNote, DebitNote, DespatchAdvice.
*   ✅ Validación estructurada de documentos (8 validadores).
*   ✅ Firma digital de XML con estándar X509 RSA-SHA1.
*   ✅ Integración con Pasarela SUNAT (SOAP nativo + REST GRE + OAuth2).
*   ✅ Renderizado multiformato (HTML Pebble, PDF OpenHTMLtoPDF, Ticket 58mm/80mm).
*   ✅ Testkit con fixtures, mocks y aserciones.
*   ✅ Adaptadores oficiales para **Spring Boot** y **Quarkus**.
*   ✅ 72+ tests, 0 failures.

## Ejemplo de uso rápido

```java
// 1. Crear el borrador del documento
BorradorFactura factura = new BorradorFactura();
factura.setSerie("F001");
factura.setNumero(1);
factura.setFechaEmision(LocalDate.now());
factura.setEmisor(new EmisorDocumento("20123456789", "Mi Empresa", "Mi Empresa SAC", null, null));
factura.setReceptor(new ReceptorDocumento("6", "20999999999", "Cliente SAC", null, null));

LineaDetalle linea = new LineaDetalle();
linea.setDescripcion("Producto");
linea.setCantidad(BigDecimal.TEN);
linea.setPrecio(new BigDecimal("100.00"));
factura.setDetalles(List.of(linea));

// 2. Ensamblar (auto-calcula IGV, totales, leyendas)
EnsambladorFactura.ensamblar(factura);

// 3. Validar
ValidadorFactura validador = new ValidadorFactura();
ResultadoValidacion resultado = validador.validar(factura);

if (resultado.esValido()) {
    // 4. Serializar a XML UBL 2.1
    SerializadorXmlFactura serializador = new SerializadorXmlFactura();
    String xml = serializador.serializar(factura);
    
    // 5. Firmar
    DetallesCertificado cert = CargadorCertificado.cargar(
        new OrigenCertificado(inputStream, "password", "PKCS12"));
    ResultadoFirma firma = ServicioFirma.firmarXml(xml, "#UBLKIT-SIGN", cert);
    
    // 6. Enviar a SUNAT
    PasarelaSunat pasarela = new PasarelaSunatDefecto(credenciales, TipoAmbiente.BETA);
    ResultadoEnvio envio = pasarela.enviarComprobante(firma.xmlFirmadoStr());
}
```

## Módulos
- [ublkit-core](./ublkit-core): Modelos de negocio y excepciones base.
- [ublkit-validation](./ublkit-validation): Reglas de validación funcional.
- [ublkit-ubl](./ublkit-ubl): Mapeo a estándar UBL 2.1.
- [ublkit-sign](./ublkit-sign): Firma digital de archivos XML.
- [ublkit-gateway](./ublkit-gateway): Envío a SUNAT/OSE (SOAP/REST).
- [ublkit-render](./ublkit-render): Generación de PDF, HTML y Tickets.
- [ublkit-catalogs](./ublkit-catalogs): Catálogos normativos SUNAT.
- [ublkit-testkit](./ublkit-testkit): Herramientas para pruebas unitarias.
- [ublkit-storage](./ublkit-storage): Almacenamiento de documentos (S3, GCS, Local).
- [ublkit-spring-boot-starter](./ublkit-spring-boot-starter): Adaptador Spring Boot.
- [ublkit-quarkus](./ublkit-quarkus): Adaptador Quarkus.

Ver detalles en [MODULES.md](MODULES.md) y [ARCHITECTURE.md](ARCHITECTURE.md).

## Matriz comparativa de módulos

| Modulo | Rol principal | API/Contrato clave | Flujo tipico |
|---|---|---|---|
| `ublkit-core` | Tipos y resultados comunes | `ResultadoOperacion`, enums, value objects | Base transversal para todos los modulos |
| `ublkit-catalogs` | Catalogos normativos | `ProveedorCatalogos` | Lookup de codigos SUNAT/atributos |
| `ublkit-ubl` | Modelado + serializacion XML | `SerializadorXml*` | Documento de dominio -> XML UBL |
| `ublkit-validation` | Validacion documental | `Validador*`, `ResultadoValidacion` | Documento -> incidencias/validez |
| `ublkit-sign` | Firma digital XML | `ServicioFirma` | XML -> XML firmado + digest |
| `ublkit-gateway` | Envio/consulta SUNAT | `PasarelaSunat` | XML firmado -> envio/ticket/CDR |
| `ublkit-render` | Salida visual | `Renderizador*`, `ContextoRender` | Documento + hash/qr -> HTML/PDF |
| `ublkit-qr` | Generacion QR SUNAT | `GeneradorQrSunat` | Documento + hash -> QR Base64 |
| `ublkit-storage` | Persistencia de artefactos | `AlmacenDocumentos` | Guardar/descargar XML, CDR, PDF |
| `ublkit-testkit` | Soporte de testing | Fixtures + Asserts + simulador gateway | Pruebas unitarias/integracion |
| `ublkit-spring-boot-starter` | Autoconfiguracion Spring | `UblKitAutoConfiguration`, `ublkit.gateway.*` | Inyeccion de beans UBLKit |
| `ublkit-quarkus` | Productores CDI Quarkus | `UblKitProducers` | Inyeccion CDI de render/serializacion/validacion |

## Quick Start por modulo

- Core: [ublkit-core/README.md](ublkit-core/README.md)
- Catalogos: [ublkit-catalogs/README.md](ublkit-catalogs/README.md)
- UBL: [ublkit-ubl/README.md](ublkit-ubl/README.md)
- Validation: [ublkit-validation/README.md](ublkit-validation/README.md)
- Sign: [ublkit-sign/README.md](ublkit-sign/README.md)
- Gateway: [ublkit-gateway/README.md](ublkit-gateway/README.md)
- Render: [ublkit-render/README.md](ublkit-render/README.md)
- QR: [ublkit-qr/README.md](ublkit-qr/README.md)
- Storage: [ublkit-storage/README.md](ublkit-storage/README.md)
- Testkit: [ublkit-testkit/README.md](ublkit-testkit/README.md)
- Spring Boot Starter: [ublkit-spring-boot-starter/README.md](ublkit-spring-boot-starter/README.md)
- Quarkus: [ublkit-quarkus/README.md](ublkit-quarkus/README.md)

## Homologación SUNAT (Reglas de Referencia)

### Migración desde SFS (`*.ftl` y `*.properties`)
Los archivos de `SFS_v-2.1/sunat_archivos/sfs/VALI/*.ftl` y `*.properties` pertenecen al motor interno de conversión/configuración del SFS.
- En UBLKit no son dependencias de runtime.
- UBLKit serializa XML con código Java (`ublkit-ubl`) y valida con XSL embebidos (`ublkit-validation`).
- Por eso no se referencian desde los módulos productivos.

### Envío, reenvío y consulta
En `ublkit-gateway` ya está contemplado:
- Envío SOAP síncrono/asíncrono y consulta de ticket.
- Envío REST de GRE, OAuth2 y consulta de ticket.
- Retry con backoff y caché de token.
- Resolución de endpoints configurable por ambiente y overrides.

### Validaciones oficiales (`VALI/*`)
Los validadores ejecutan validación local de UBLKit y validación XSL de referencia SUNAT.
- Factura: `ValidaExprRegFactura-2.0.1.xsl`
- Boleta: `ValidaExprRegBoleta-2.0.1.xsl`
- Nota de crédito: `ValidaExprRegNC-2.0.1.xsl`
- Nota de débito: `ValidaExprRegND-2.0.1.xsl`
- GRE remitente: `ValidaExprRegGreRemitente-2.0.1.xsl`
- GRE transportista: `ValidaExprRegGreTransportista-2.0.1.xsl`
- Resumen diario (RC): `ValidaExprRegSummary-1.1.0.xsl`
- Comunicación de baja (RA): `ValidaExprRegSummaryVoided-1.0.1.xsl`

Ejecución de validación XSL SUNAT:
- `-Dublkit.validation.sunat.enabled=true` para activar la homologación XSL oficial.
- Sin esa propiedad, solo aplica validación funcional de UBLKit.

### Render PDF
Renderizadores PDF usan una sola estrategia: plantillas HTML (Pebble/Twig) + OpenHTMLtoPDF.
No se utiliza JasperReports/JRXML en esta versión.

## Instalación

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-spring-boot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Contribuir
Por favor, lee [CONTRIBUTING.md](CONTRIBUTING.md) antes de enviar Pull Requests.

## Licencia
Este proyecto está bajo la licencia [MIT](LICENSE).
