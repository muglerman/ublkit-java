# ublkit-catalogs

**Catálogos normativos SUNAT en memoria**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![CSV](https://img.shields.io/badge/CSV-catalogs-2f855a?style=flat-square)](https://en.wikipedia.org/wiki/Comma-separated_values)
[![SUNAT](https://img.shields.io/badge/SUNAT-catalogs-1f4e79?style=flat-square)](https://cpe.sunat.gob.pe)

Módulo de consulta de catálogos CSV versionados en el classpath.

[Uso](#uso) |
[Estructura](#estructura) |
[Características](#características) |
[Pruebas](#pruebas)

---

## Descripción General

`ublkit-catalogs` provee acceso uniforme a catálogos normativos mediante `ProveedorCatalogos`. Carga CSV estáticos, cachea en memoria y evita dependencia de bases externas.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-catalogs</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Ruta | Contenido |
| --- | --- |
| `.../catalogs/api/` | `ProveedorCatalogos` |
| `.../catalogs/modelo/` | `EntradaCatalogo` |
| `.../catalogs/sunat/` | `LectorCsvCatalogos` |
| `src/main/resources/sunat/catalogos/` | CSV de catálogos SUNAT |

## Características

- Carga rápida desde classpath.
- Búsqueda por id de catálogo y código.
- Metadatos por columna para catálogos extendidos.
- Caché en memoria con estructuras concurrentes.

## Pruebas

```bash
mvn test -pl ublkit-catalogs
```

Validar presencia de CSV, parseo correcto y búsquedas concurrentes.

---

Desarrollado por **Crea Nexus Atreus**

