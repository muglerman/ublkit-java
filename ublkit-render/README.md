# ublkit-render

## Nombre y Descripción del Proyecto
**ublkit-render** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo responsable de la representación visual humana (PDF y HTML) y generación de tickets térmicos (58mm y 80mm) a partir de los documentos electrónicos UBLKit. Transforma el documento en un comprobante listo para entregar al cliente.

## Stack Tecnológico
- Java 21+
- `io.pebbletemplates` para procesamiento de plantillas HTML y lógica visual de manera ágil.
- `io.github.openhtmltopdf` para la conversión precisa y programática de HTML a PDF/A.
- `openhtmltopdf-svg-support` para renderizar gráficos vectoriales (como códigos QR).
- Fuentes tipográficas embebidas (`Roboto`) y perfil de color (`sRGB.icc`) para cumplimiento PDF/A-3b.

## Arquitectura del Proyecto
Módulo de Infraestructura que implementa puertos `RenderizadorDocumento<T>`. Depende estrechamente de los modelos ensamblados en el dominio y de los fragmentos de firma/QR (`ublkit-qr`). El enfoque de `HTML to PDF` evita la dependencia de motores pesados como JasperReports (JRXML).

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-render</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo abarca:
- `src/main/java/com/cna/ublkit/render/`: Clases renderizadoras HTML y PDF separadas por tipo de comprobante.
- `src/main/java/com/cna/ublkit/render/context/`: El `ContextoRender` donde se inyectan variables externas (logos, parámetros de sistema, variables de marca).
- `src/main/resources/oti/templates/`: Archivos `.html` y `.html.twig` organizados por documento, formato y tema visual.
- `src/main/resources/fonts/`: Fuentes requeridas para el motor PDF.

## Características Principales
- Soporte para visualización `HTML` directa o exportación a `PDF`.
- Variedad de Formatos: A4, A5, Rollos Térmicos (Ticket 58mm, Ticket 80mm).
- Renderizadores con multitenencia integrada inyectando configuraciones visuales dinámicas al `ContextoRender`.
- **In-Memory**: Funciona 100% en memoria en la JVM, ideal para ambientes serverless, requiriendo que los logos e imágenes se envíen como `Data URIs` (Base64).
- Paginación robusta de tablas gestionada por CSS tradicional (`page-break-inside`).

## Flujo de Desarrollo
- Cualquier modificación visual requiere ajustar los archivos Pebble (`.twig`) en la carpeta de recursos.
- Para cambios CSS usar estilos estrictamente pre-CSS3/Flexbox para maximizar la compatibilidad con el motor de OpenHTMLtoPDF.
- Al testear tickets térmicos, asegurarse de que el PDF generado tenga una altura dinámica para evitar papel sobrante.

## Estándares de Código
- Las fuentes y plantillas deben cargarse exclusivamente desde el Classpath (`ClasspathLoader`) para asegurar portabilidad en Docker.
- Usar fuentes monoespaciadas para los templates de tickets (ej. `Consolas`, `Courier New`) para mantener alineación matemática de importes.
- El objeto `doc` en los templates Pebble contiene el modelo Java, y el objeto `params` contiene la inyección de ambiente/branding.

## Pruebas
- Comprobar la generación de PDF desde facturas complejas (muchos ítems) para probar el salto de página automático en las tablas HTML.
- Verificación de la compilación Pebble en tiempos de inicio para detectar errores de sintaxis tempranos.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
