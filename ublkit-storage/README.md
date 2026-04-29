# ublkit-storage

## Nombre y Descripción del Proyecto
**ublkit-storage** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo que proporciona una abstracción y múltiples implementaciones para el almacenamiento persistente de los artefactos de emisión electrónica, tales como archivos XML, Constancias de Recepción (CDR) y representaciones visuales (PDF, ZIP).

## Stack Tecnológico
- Java 21+
- SDKs Cloud (Opcionales, como AWS S3 SDK o Google Cloud Storage SDK)
- Java NIO (para la implementación en disco local)

## Arquitectura del Proyecto
Módulo de Infraestructura que implementa el patrón de adaptador. Expone la interfaz `AlmacenDocumentos` de la cual derivan los adaptadores específicos para Local, AWS S3 y Google Cloud, manteniendo la capa de aplicación abstraída de dónde residen físicamente los archivos.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-storage</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura incluye:
- `AlmacenDocumentos.java`: La interfaz/contrato principal.
- `AlmacenLocalStorage.java`: Implementación para guardar archivos en el sistema de archivos del servidor (recomendado para pruebas).
- `AlmacenS3.java` y `AlmacenGCS.java`: Implementaciones para servicios Cloud.

## Características Principales
- Contrato unificado para guardar, recuperar (descargar) y eliminar archivos independientemente del proveedor.
- Adaptador **Local** para entornos de desarrollo y pruebas.
- Adaptadores **Cloud** (S3, GCS) para entornos escalables y de alta disponibilidad sin estado en los nodos web.

## Flujo de Desarrollo
- Seleccionar el adaptador a instanciar en tiempo de arranque (usualmente mediante propiedades de Spring/Quarkus) dependiendo del entorno (DEV = Local, PROD = S3).
- Implementar nuevos adaptadores extendiendo `AlmacenDocumentos` si el proyecto lo requiere (ej. Azure Blob Storage).

## Estándares de Código
- Las implementaciones deben capturar excepciones del proveedor de la nube y devolver o lanzar excepciones comunes que entienda UBLKit.
- Las claves/paths de almacenamiento deben seguir convenciones seguras y unificadas.

## Pruebas
- Validar la correcta escritura y lectura utilizando el adaptador Local en directorios temporales de JUnit.
- Tests de integración controlados (o usando Testcontainers/LocalStack) para adaptadores S3/GCS.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
