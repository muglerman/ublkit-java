# ublkit-spring-boot-starter

## Nombre y Descripción del Proyecto
**ublkit-spring-boot-starter** es un módulo que pertenece a la librería comunitaria UBLKit.
Starter oficial para integrar UBLKit dentro del ecosistema Spring Boot de forma automática (Auto-Configuration). Facilita el uso de la librería a través del contenedor de Inversión de Control de Spring.

## Stack Tecnológico
- Java 21+
- Spring Boot 3.x
- Spring Context (`@AutoConfiguration`, `@Bean`, `@ConditionalOnMissingBean`)

## Arquitectura del Proyecto
Módulo Adaptador de Framework. Siguiendo la arquitectura hexagonal, esta capa orquesta la creación y entrega de los puertos y adaptadores definidos en el core y la infraestructura, adaptándolos como `Beans` en el ApplicationContext de Spring. No expone controladores HTTP propios, solo provee los ladrillos de negocio.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-spring-boot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo es compacta:
- `src/main/java/com/cna/ublkit/spring/`: `UblKitAutoConfiguration` (que instancia los beans), `UblKitProperties` (para inyectar configuración desde el `application.yml`) y sus subpropiedades como `UblKitStorageProperties`.

## Características Principales
- **Autoconfiguración**: Registra beans por defecto (Renderizadores, Firmador, Serializadores, Pasarela SUNAT) si el desarrollador no provee implementaciones propias (`@ConditionalOnMissingBean`).
- Integración con el entorno de configuración (`application.yml` / `application.properties`) para ajustar parámetros globales de UBLKit, como credenciales de Gateway o rutas de storage.
- Fuerte desacople de dependencias ajenas, solo depende de las APIs estándar de Spring AutoConfigure.

## Flujo de Desarrollo
- Instalar la dependencia en el POM del proyecto Spring Boot cliente.
- Agregar las propiedades `ublkit.*` correspondientes en el archivo de configuración si se requiere sobrescribir comportamientos por defecto.
- Inyectar los Beans listos en los `@Service` del aplicativo cliente para orquestar los flujos de emisión.

## Estándares de Código
- Declaración clara de propiedades expuestas a través de `@ConfigurationProperties`.
- Proveer siempre fallbacks de Beans en caso de que ciertas propiedades no existan, manteniendo un 'arranque seguro' del aplicativo.

## Pruebas
- Validar que el contexto de Spring cargue en un entorno de test (`@SpringBootTest`).
- Asegurar que al definir un bean propio del cliente, se reemplace el provisto por la autoconfiguración.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
