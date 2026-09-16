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
- GRE-T A4 por estilo (`classic-mono`, `corporate-blue`, `forest-modern`, `bold-accent`, `minimal-serif`)
  alineada visualmente con la Factura A4 del mismo tema.

## Reglas

- Plantillas desde classpath.
- HTML/CSS renderizado a PDF con Playwright (Chromium headless).
- Tickets con fuentes monoespaciadas.
- `doc` contiene el modelo; `params` contiene branding/ambiente.

## GRE-T A4: reglas de página

Las plantillas `despatch-carrier.a4.html.twig` siguen estas reglas específicas:

- **Página 1**
  - cabecera completa con logo, razón social, dirección, ubicación y **REG. MTC del emisor**;
  - bloque documental con **RUC + tipo + serie/correlativo**;
  - bloques operativos: ruta, remitente, destinatario, metadatos del traslado, indicadores,
    subcontrataciones, vehículos y conductores.
- **Páginas 2+**
  - solo se repite una **cabecera compacta GRE-T / RUC / número**;
  - se repite el encabezado de columnas de bienes;
  - **no** se repiten remitente, destinatario, subcontrataciones, vehículos, conductores ni ruta.
- **Bienes**
  - cada fila se mantiene indivisible (`break-inside: avoid` / `page-break-inside: avoid`);
  - el encabezado de columnas se vuelve a emitir en páginas de continuación;
  - el total del flete, observaciones, documentos relacionados y pie solo aparecen al final del documento.

## GRE-T A4: origen de datos visibles

| Bloque visible | Variable de plantilla | Origen real | Naturaleza |
| --- | --- | --- | --- |
| Razón social / RUC / dirección / contacto del emisor | `doc.remitente.*` | `BorradorGuiaRemision.remitente` | UBL renderizado |
| REG. MTC del emisor transportista | `doc.envio.transportista.numeroRegistroMTC` | empresa transportista emisora | UBL SUNAT |
| Remitente real de la carga (GRE-T) | `doc.tercero.*` | remitente/cliente original | UBL SUNAT |
| Destinatario | `doc.destinatario.*` | destinatario del traslado | UBL SUNAT |
| Punto de partida / llegada | `doc.envio.partida.*`, `doc.envio.destino.*`, `params.partida*`, `params.destino*` | ubigeo en UBL + jerarquía geográfica resuelta en presentación | mixto |
| Motivo, modalidad, peso, bultos, fecha traslado | `doc.envio.*` | shipment UBL | UBL SUNAT |
| Indicadores de traslado | `indicadoresTraslado` | `doc.envio.indicadores` + normalización visual | mixto |
| Estado de pago | `params.estadoPago` | `DocumentToBorradorMapper.buildDispatchPresentationAttrs` | solo presentación |
| Nro. tracking | `params.trackingNumber` | `DocumentToBorradorMapper.buildDispatchPresentationAttrs` | solo presentación |
| Sello GRE-T (`GRE-T VÁLIDA`, `GRE-T PENDIENTE`, `GRE-T RECHAZADA`) | `params.estadoGreT` | estados canónicos `SunatConstants.DocumentState` | solo presentación |
| Flete monetario | `params.totalGuia` | importe total de la guía | solo presentación |
| Subcontratación SUNAT / contratante del transporte | `doc.subcontratado.*` | `cac:LogisticsOperatorParty` | UBL SUNAT |
| Subcontratación interna / proveedor subcontratado | `params.subcontratistaNombre/Ruc/Mtc` | negocio interno | solo presentación |
| Pagador tercero del flete | `params.tipoPagadorFlete`, `params.pagadorFleteTercero*`, `doc.pagadorFleteTercero` | origenador del flete / detalle visual | mixto |
| Vehículos | `doc.envio.vehiculo.*` | datos del traslado | UBL SUNAT |
| Conductores | `doc.envio.choferes.*` | datos del traslado | UBL SUNAT |
| Documentos relacionados | `doc.documentosRelacionados` | referencias UBL | UBL SUNAT |
| Observaciones | `doc.observaciones` | texto del borrador ya normalizado | solo presentación |
| QR / hash | `qrBase64`, `hashDocumento` | generación de render / firma | presentación de validación |

### Distinciones que no deben mezclarse

- **Subcontratación SUNAT** (`doc.subcontratado`) y **subcontratación interna**
  (`params.subcontratista*`) son bloques distintos.
- Los **REG. MTC** visibles tienen tres orígenes separados:
  - emisor transportista: `doc.envio.transportista.numeroRegistroMTC`;
  - contratante SUNAT: `doc.subcontratado.numeroRegistroMTC`;
  - proveedor interno: `params.subcontratistaMtc`.

## Pruebas

```bash
cd ublkit-java && mvn test -pl ublkit-render -Dtest='RenderizadorHtmlGuiaRemisionTest,RenderizadorHtmlGuiaRemisionCarrierSemanticsTest,RenderizadorEstilosDataValidationTest,RenderizadorPdfGuiaRemisionMultipageTest,RenderizadorPdfGuiaRemisionVisualSnapshotTest'
```

Para refrescar snapshots visuales de GRE-T Classic Mono de forma explícita:

```bash
cd ublkit-java && mvn test -pl ublkit-render \
  -Dtest=RenderizadorPdfGuiaRemisionVisualSnapshotTest \
  -Dublkit.visual.update=true
```

Nunca se sobrescriben baselines durante una ejecución normal: el modo de actualización es opt-in.

---

Desarrollado por **Crea Nexus Atreus**
