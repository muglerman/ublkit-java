# Arquitectura de UBLKit

UBLKit está diseñado bajo el paradigma de **Arquitectura Hexagonal** (Puertos y Adaptadores). Esta decisión separa las reglas de negocio de los detalles técnicos de implementación.

## Capas de la Arquitectura

### 1. Dominio (Core)
Es el corazón de la librería. No tiene dependencias externas.
- **Modelos**: `BorradorFactura`, `BorradorNotaCredito`, `BorradorNotaDebito`, `BorradorGuiaRemision`, `ComunicacionBaja`, `ResumenDiario`.
- **Enumerados**: `TipoDocumento`, `TipoAmbiente`, `ProtocoloTransporte`.
- **Value Objects**: `NumeroSerie`, `Direccion`, `Contacto`, `TipoCambio`.
- **Puertos**: Interfaces que definen qué acciones se pueden realizar (`Validador<T>`, `SerializadorXml<T>`, `RenderizadorDocumento<T>`, `PasarelaSunat`).

### 2. Aplicación
Contiene los casos de uso y la orquestación.
- **Ensambladores**: `EnsambladorFactura` (auto-cálculo IGV/ISC/ICBPER/totales), `EnsambladorNota`.
- **Validadores**: `ValidadorFactura`, `ValidadorNotaCredito`, `ValidadorNotaDebito`, `ValidadorGuiaRemision`.

### 3. Infraestructura (Adaptadores)
Implementaciones técnicas de los puertos definidos en el dominio.
- **XML**: Serialización UBL 2.1 con `javax.xml` DOM puro (`SerializadorXmlFactura`, `SerializadorXmlNotaCredito`, `SerializadorXmlNotaDebito`, `SerializadorXmlGuiaRemision`).
- **Firma**: Implementación de firma X509 RSA-SHA1 en `ublkit-sign` (`FirmadorXml`).
- **Gateway**: Clientes SOAP y REST nativos (`java.net.http.HttpClient`) en `ublkit-gateway`.
- **Render**: Pebble templates + OpenHTMLtoPDF en `ublkit-render`.
- **Frameworks**: Adaptadores específicos para **Spring Boot** (`@AutoConfiguration`) y **Quarkus** (`@Produces`).

## Regla de Dependencia
Las dependencias fluyen **siempre** hacia el núcleo (el dominio/core).

```text
[Infraestructura] ---depende de---> [Aplicación] ---depende de---> [Dominio]
```

- **Core** no sabe nada de Spring, SOAP, XML o PDF.
- **UBL** solo sabe de XML y depende de Core.
- **Gateway** sabe de protocolos y depende de Core.
- **Spring/Quarkus** son adaptadores finales que orquestan todo.

## Decisiones Claves (ADR)
1. **Java puro primero**: Antes de acoplarnos a Spring o Quarkus, la funcionalidad funciona limpiamente por interfaces Java.
2. **javax.xml DOM para serialización**: Se eligió generación DOM programática sobre templates (Qute/Pebble) y JAXB. Cero dependencias adicionales, control total sobre la estructura XML.
3. **Validaciones por Resultado**: Optamos por un patrón de notificación (`ResultadoValidacion`) en lugar de excepciones de control.
4. **Ensamblador estilo fractuyo**: El ensamblador auto-calcula impuestos y totales pero respeta valores ya proporcionados por el usuario.
5. **No exponer XML crudo**: La abstracción superior oculta la estructura UBL compleja tras un modelo con sentido de negocio.
6. **Inmutabilidad**: Los objetos de valor y el modelo de dominio priorizan records inmutables (45+ records). Solo los borradores de documentos son mutables.
