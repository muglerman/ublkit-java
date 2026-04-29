# ublkit-quarkus

## Nombre y Descripción del Proyecto
**ublkit-quarkus** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo que proporciona integración nativa de la librería UBLKit con el framework Quarkus a través de Context and Dependency Injection (CDI).

## Stack Tecnológico
- Java 21+
- Quarkus (CDI, `jakarta.enterprise.context`, `jakarta.enterprise.inject.Produces`)
- GraalVM (Compatible para generación de binarios nativos)

## Arquitectura del Proyecto
Módulo de Adaptador de Framework en la Arquitectura Hexagonal. Conecta el mundo de la infraestructura de UBLKit (serializadores, renderizadores, etc.) con el ecosistema de dependencias de una aplicación Quarkus cliente. Facilita la inyección de los puertos de la librería como Beans.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-quarkus</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo consiste principalmente en:
- `src/main/java/com/cna/ublkit/quarkus/`: La clase `UblKitProducers` que expone constructores CDI (`@Produces`) para cada servicio de la librería.

## Características Principales
- Provisión de **Productores CDI** singleton para:
  - Validadores de negocio y SUNAT.
  - Serializadores XML.
  - Renderizadores HTML y PDF.
  - Servicio de Firma.
- Evita el cableado manual (wiring) repetitivo de los objetos inmutables y sin estado de UBLKit dentro de los microservicios Quarkus.
- Habilitación de la librería en modo de compilación AOT de GraalVM.

## Flujo de Desarrollo
- Instalar la dependencia en el proyecto Quarkus final.
- Inyectar (`@Inject`) directamente interfaces como `SerializadorXml<BorradorFactura>` en los controladores REST o servicios de negocio.
- Cualquier adición de un nuevo servicio o renderizador en la suite UBLKit requiere agregar un nuevo método `@Produces` en este módulo.

## Estándares de Código
- Exponer las clases funcionales puras como `@ApplicationScoped` o `@Singleton` pues no mantienen estado entre peticiones.
- No incluir lógica de negocio aquí, solo instanciación.

## Pruebas
- Validar que los contextos CDI carguen exitosamente en una aplicación de prueba embebida de Quarkus sin ciclos de dependencia.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
