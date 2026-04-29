# UBLKit

## Nombre y Descripción del Proyecto
**UBLKit** es una librería comunitaria diseñada para modelar, validar, firmar, enviar y renderizar comprobantes de pago electrónicos o documentos UBL desde Java. El propósito principal es ofrecer una solución integral para el manejo de documentos electrónicos orientada al dominio, amigable para el desarrollador, y sin depender de forma exclusiva de APIs XML crudas o frameworks específicos.

## Stack Tecnológico
- Java 21+
- Estructura Multi-módulo manejada por Maven
- `javax.xml` (DOM nativo para serialización) y `javax.xml.crypto` (Firma digital estándar nativa)
- `java.net.http.HttpClient` para transporte (SOAP/REST)
- OpenHTMLtoPDF & Pebble (Para renderización de PDFs)
- Frameworks de prueba: JUnit 5, AssertJ, Mockito
- Starter integrations: Spring Boot Starter, Quarkus Extension

## Arquitectura del Proyecto
UBLKit está diseñado estrictamente bajo el paradigma de **Arquitectura Hexagonal** (Puertos y Adaptadores). Esta decisión separa las reglas de negocio (el modelo de comprobantes, reglas de validación) de los detalles técnicos (generación de XML, clientes HTTP, renderizadores visuales).

La regla de dependencia exige que las interacciones siempre fluyan hacia el dominio:
`[Infraestructura] ---depende de---> [Aplicación] ---depende de---> [Dominio]`

Esto asegura que la lógica crítica no se acople a frameworks como Spring o Quarkus. Estos frameworks actúan como un adaptador final en las capas más externas de la aplicación.

## Empezando
### Requisitos Previos
- Java 21 o superior instalado.
- Apache Maven (3.8+) para construir o descargar el proyecto.

### Instalación
Si utilizas **Spring Boot**, añade el siguiente starter a tu `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-spring-boot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Si usas Java plano (o necesitas solo módulos puntuales), puedes importar partes específicas como el core o el ubl:
```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-ubl</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### Uso Rápido
Ejemplo de creación y envío de una factura:
```java
// 1. Crear el borrador
BorradorFactura factura = new BorradorFactura();
factura.setSerie("F001");
factura.setNumero(1);
// ... Llenar datos, emisor, receptor, lineas de detalle.

// 2. Ensamblar (auto-calcula impuestos)
EnsambladorFactura.ensamblar(factura);

// 3. Serializar y Firmar
String xml = new SerializadorXmlFactura().serializar(factura);
ResultadoFirma firma = ServicioFirma.firmarXml(xml, "#UBLKIT-SIGN", certificado);

// 4. Enviar a SUNAT
PasarelaSunat pasarela = new PasarelaSunatDefecto(credenciales, TipoAmbiente.BETA);
ResultadoEnvio envio = pasarela.enviarComprobante(firma.xmlFirmadoStr());
```

## Estructura del Proyecto
El proyecto está estructurado como un agregador Maven con 12 módulos principales:
- `ublkit-core`: Modelos de negocio y puertos base.
- `ublkit-catalogs`: Datos normativos SUNAT.
- `ublkit-testkit`: Utilidades de prueba, mocks, assertions.
- `ublkit-ubl`: DOM serialización de modelos y ensambladores.
- `ublkit-validation`: Reglas y validadores formales y XSLT.
- `ublkit-qr`: Generación de trama y códigos.
- `ublkit-storage`: Abstracciones persistentes (Local, S3, GCS).
- `ublkit-sign`: Motor de firma digital.
- `ublkit-gateway`: Clientes API y SOAP (SUNAT, OSE).
- `ublkit-render`: Plantillas visuales, conversión PDF/Ticket.
- `ublkit-quarkus` / `ublkit-spring-boot-starter`: Integraciones de frameworks.

Para ver el detalle de un módulo en particular, entra a su respectivo directorio y revisa su `README.md` individual.

## Características Principales
- **Core Agnóstico**: Dominio puro e independiente de la infraestructura web.
- **Modelado Robusto**: Cobertura amplia de comprobantes (Facturas, Boletas, Notas Crédito/Débito, Guías de Remisión, Resúmenes, Bajas, Retenciones, Percepciones).
- **Ensamblado Inteligente**: Cálculo automático de impuestos (IGV, ISC) basados en ítems.
- **Validación Dual**: Verificación funcional en Java y homologación normativa a través de XSLT pre-compilado de SUNAT.
- **Multiformato Visual**: Generación PDF/A compatible con A4, A5, y formatos térmicos de tickets POS (58/80mm).
- **Adaptable**: Listo para ser introducido en entornos tradicionales, Serverless (GCP, AWS) con soporte para S3, y Cloud Native (Quarkus/GraalVM).

## Flujo de Desarrollo
- La librería prioriza el desarrollo *Test-Driven* y está respaldada por una extensa suite de pruebas automatizadas.
- Cada cambio en la normativa SUNAT o adición de estructura se modela en `ublkit-ubl`, testeando contra "Golden XMLs".
- Integración Continua (CI) se encarga de ejecutar todas las compilaciones sin tests (`mvn clean install -DskipTests`) y las validaciones de las pruebas.

## Estándares de Código
- Código 100% en Java puro donde sea posible.
- **Lenguaje Transversal**: Todo el código, nombres de clases, métodos, dominios y documentación de UBLKit está en estricto **Español** para alinearse a las normativas de la facturación en Perú.
- Nomenclaturas descriptivas y orientadas al dominio (`BorradorFactura`, no `InvoiceDraft`).
- Preferencia abrumadora por la inmutabilidad: Se usa `record` en objetos de valor extensamente.
- `ResultadoOperacion<T>` como estándar de notificación de respuesta (evitar el uso de excepciones ocultas para control de flujo de negocio).

## Pruebas
Las pruebas son primordiales y se organizan usando JUnit 5 y AssertJ en todos los módulos de manera independiente.
- Para probar todo el ecosistema ejecuta: `mvn test`
- Para probar un solo módulo: `mvn test -pl <module_name>`
- El módulo `ublkit-testkit` provee aserciones dedicadas y respuestas MOCK para emular conectividad externa.

## Contribución
¡La comunidad es bienvenida a contribuir!
Por favor revisa nuestro archivo `CONTRIBUTING.md` para las directrices. Como resumen:
1. Haz un fork del repositorio.
2. Crea tu feature branch: `git checkout -b feature/nueva-funcionalidad`
3. Asegúrate que las reglas de inmutabilidad y separación de dominios se respeten.
4. Escribe tus pruebas, asegura que pasen y lanza tu **Pull Request**.

## Licencia
El ecosistema UBLKit está distribuido bajo la licencia abierta MIT. Consulta el archivo `LICENSE` en la raíz para más información.
