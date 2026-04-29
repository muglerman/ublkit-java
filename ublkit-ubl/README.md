# ublkit-ubl

## Nombre y Descripción del Proyecto
**ublkit-ubl** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo central para el modelado de comprobantes y documentos electrónicos, así como su respectiva serialización al estándar XML UBL 2.1 exigido por SUNAT. Convierte el modelo de dominio de alto nivel en un documento XML estructurado.

## Stack Tecnológico
- Java 21+
- Serialización XML nativa (`javax.xml.parsers`, DOM) en lugar de plantillas de texto para mayor seguridad y consistencia en los nodos XML.
- Modelado rico orientado a objetos (Clases Sealed, Records).

## Arquitectura del Proyecto
Pertenece a las capas de Aplicación e Infraestructura. Define los modelos concretos de documentos (`BorradorFactura`, etc.), Ensambladores de la capa de aplicación (que aplican reglas de negocio para calcular totales e impuestos), y finalmente los Serializadores en la capa de Infraestructura, implementando los puertos de serialización del core.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-ubl</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo se divide en:
- `src/main/java/com/cna/ublkit/ubl/domain/`: Contiene los modelos base (e.g. `DocumentoBase`, borradores).
- `src/main/java/com/cna/ublkit/ubl/assembler/`: Clases que ensamblan los documentos (e.g. `EnsambladorFactura`, auto-calculando montos).
- `src/main/java/com/cna/ublkit/ubl/xml/`: Lógica de creación de XML (`SerializadorXmlFactura`, `FragmentosXml`, DOM puro).
- `src/main/java/com/cna/ublkit/ubl/util/`: Utilidades, como conversión de números a letras (`NumeroALetras`).

## Características Principales
- Modelos completos para: Factura, Boleta, Notas (Crédito/Débito), Guías de Remisión, Resumen Diario, Comunicación de Baja, Percepciones y Retenciones.
- **Ensambladores inteligentes**: Auto-calculan impuestos (IGV, ISC, ICBPER), totales, detracción, y añaden leyendas requeridas.
- **Serializadores XML**: Mapean los borradores ensamblados al esquema oficial UBL 2.1 sin usar librerías externas de binding (como JAXB) previniendo problemas de versiones.

## Flujo de Desarrollo
- Los cambios en la estructura XML (si SUNAT actualiza la norma) se implementan ajustando `FragmentosXml` y los serializadores pertinentes.
- Cualquier nueva regla de cálculo se implementa en los ensambladores (ej. `EnsambladorFactura`).
- Se validan mediante aserciones estrictas sobre el XML de salida.

## Estándares de Código
- **Cero JAXB / FreeMarker**: Se requiere la generación de XML a través de la API DOM nativa de Java para asegurar namespaces correctos y codificación UTF-8.
- Respetar la inmutabilidad una vez que el documento es ensamblado.

## Pruebas
- Extensa suite de pruebas utilizando `ublkit-testkit`.
- Validación estructural comparando el XML generado en memoria contra `GoldenXml` (ejemplos aprobados por SUNAT).

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
