# ublkit-catalogs

## Nombre y Descripción del Proyecto
**ublkit-catalogs** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo encargado de proporcionar acceso unificado a los catálogos normativos (principalmente de la SUNAT) mediante una API común. Contiene datos en formato CSV para validaciones y consultas estandarizadas.

## Stack Tecnológico
- Java 21+
- Lectura de archivos CSV (incorporados como recursos)
- Caché en memoria usando estructuras concurrentes nativas de Java (ej. `ConcurrentHashMap`)

## Arquitectura del Proyecto
Parte del dominio y aplicación base. Expone un `ProveedorCatalogos` que sirve como puerto para consultar los códigos de catálogos (como tipos de documento de identidad, monedas, afectaciones del IGV, etc.). Está desacoplado de bases de datos externas para garantizar alta disponibilidad en memoria.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-catalogs</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del código fuente se organiza así:
- `src/main/java/com/cna/ublkit/catalogs/`: Contiene `ProveedorCatalogos`, `EntradaCatalogo` y `LectorCsvCatalogos`.
- `src/main/resources/catalogs/`: Archivos `.csv` estáticos que representan los Catálogos normativos SUNAT (01 a 60).

## Características Principales
- Carga rápida de catálogos desde archivos CSV estáticos en el `classpath`.
- Búsqueda eficiente por ID de catálogo y código específico.
- Metadatos dinámicos por columna para catálogos que poseen una estructura extendida.
- Prevención de acceso a disco recurrente mediante lectura única y cacheo en memoria.

## Flujo de Desarrollo
- Actualización de archivos CSV cuando SUNAT publica nuevas versiones de catálogos.
- Verificación de concurrencia y validación de tipos al exponer los datos.

## Estándares de Código
- Mantenimiento de la inmutabilidad de los datos cargados.
- Nomenclatura en español para la API de consulta (`ProveedorCatalogos`).
- Manejo seguro de concurrencia.

## Pruebas
- Pruebas orientadas a garantizar que todos los CSV requeridos estén presentes y sean correctamente parseados.
- Verificación del correcto funcionamiento de las búsquedas y el comportamiento concurrente.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
