# ublkit-validation

## Nombre y Descripción del Proyecto
**ublkit-validation** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo encargado de la validación estructural, funcional y normativa de los documentos UBL antes de proceder con su firma o envío a SUNAT. Garantiza que el documento cumple con las reglas del dominio y, opcionalmente, con las directrices oficiales XSLT.

## Stack Tecnológico
- Java 21+
- Procesamiento XSLT nativo (`javax.xml.transform`) pre-compilado en objetos `Templates` para alto rendimiento.
- Diseño de validación acumulativa sin excepciones de corto circuito.

## Arquitectura del Proyecto
Parte de la capa de Aplicación de la Arquitectura Hexagonal. Define implementaciones de la interfaz `Validador<T>` definida en core. No depende de bases de datos y funciona en memoria.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-validation</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo se divide en:
- `src/main/java/com/cna/ublkit/validation/api/`: Interfaces de validación, `ResultadoValidacion`, `IncidenciaValidacion`.
- `src/main/java/com/cna/ublkit/validation/document/`: Clases concretas de validadores funcionales por cada tipo de comprobante.
- `src/main/java/com/cna/ublkit/validation/sunat/`: Lógica de validación XSL (`ValidadorSunatXsl`, `ReglaSunatXsl`).
- `src/main/resources/sunat/`: Archivos XSL originales extraídos del SFS de SUNAT para homologación.

## Características Principales
- API de validación genérica que acumula errores sin interrumpir el flujo en el primer fallo.
- Diferenciación de `SeveridadValidacion` (Errores críticos vs Advertencias).
- **Validación Funcional**: Reglas Java que verifican coherencia de importes, ruc, series, etc.
- **Validación SUNAT XSLT (Homologación)**: Ejecución de las reglas oficiales SUNAT de sus archivos XSL (`ValidaExprRegFactura-2.0.1.xsl`, etc.), cacheando el motor XSLT para evitar bloqueos de CPU en concurrencia.

## Flujo de Desarrollo
- Si SUNAT publica nuevas validaciones, se agregan a las clases Java o se actualiza el `.xsl` oficial en recursos.
- La validación XSL SUNAT es opcional y se activa mediante la propiedad `-Dublkit.validation.sunat.enabled=true`.

## Estándares de Código
- **No lanzar excepciones durante la validación** para representar errores de negocio; siempre usar `ResultadoValidacion` para acumular todas las `Incidencias`.
- **Rendimiento**: Nunca instanciar el motor XSL (`Transformer`) desde disco en cada validación. Usar el caché de `Templates` implementado.

## Pruebas
- Evaluaciones de comprobantes mal formados para confirmar que las aserciones acumulen los códigos de error SUNAT correctos.
- Tests que validan tanto la ruta rápida (solo validación local) como la ruta exhaustiva (con XSL).

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
