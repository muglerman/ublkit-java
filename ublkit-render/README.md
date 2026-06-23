# ublkit-render

**Render HTML, PDF y tickets térmicos**

[![Java](https://img.shields.io/badge/Java-21-f89820?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-module-c71a36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org)
[![Pebble](https://img.shields.io/badge/Pebble-templates-455a64?style=flat-square)](https://pebbletemplates.io)
[![Playwright](https://img.shields.io/badge/Playwright-PDF-2ead33?style=flat-square&logo=playwright&logoColor=white)](https://playwright.dev)
[![HTML](https://img.shields.io/badge/HTML-templates-e34f26?style=flat-square&logo=html5&logoColor=white)](https://developer.mozilla.org/docs/Web/HTML)

Plantillas Pebble + Playwright (Chromium) para representaciones visuales de documentos UBLKit.

[Uso](#uso) |
[Estructura](#estructura) |
[Características](#características) |
[Reglas](#reglas) |
[Pruebas](#pruebas)

---

## Descripción General

`ublkit-render` convierte documentos ensamblados a HTML y PDF. Soporta A4/A5 con temas visuales y tickets térmicos 58/80mm con plantillas genéricas.

## Uso

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-render</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Estructura

| Ruta | Contenido |
| --- | --- |
| `src/main/java/com/cna/ublkit/render/html/` | Renderizadores HTML |
| `src/main/java/com/cna/ublkit/render/pdf/` | Renderizadores PDF y `PlaywrightBrowserManager` |
| `src/main/java/com/cna/ublkit/render/pebble/` | Motor Pebble, extensión y fuentes embebidas |
| `src/main/java/com/cna/ublkit/render/modelo/` | `ContextoRender`, formatos, estilos y rutas de plantilla |
| `src/main/resources/templates/` | Plantillas `.html.twig` por estilo/formato |
| `src/main/resources/fonts/` | Fuentes embebidas |

## Características

- HTML directo y PDF.
- A4/A5 por estilo visual.
- Tickets 58mm/80mm genéricos.
- Logos e imágenes como Data URI.
- Operación 100% en memoria.

## Reglas

- Plantillas desde classpath.
- HTML/CSS renderizado a PDF con Playwright (Chromium headless).
- Tickets con fuentes monoespaciadas.
- `doc` contiene el modelo; `params` contiene branding/ambiente.

## Pruebas

```bash
mvn test -pl ublkit-render
```

Validar compilación Pebble, PDF con muchos ítems y cambios visuales relevantes.

---

Desarrollado por **Crea Nexus Atreus**

