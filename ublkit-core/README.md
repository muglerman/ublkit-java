# ublkit-core

## Nombre y Descripción del Proyecto
**ublkit-core** es un módulo que pertenece a la librería comunitaria UBLKit.
Define la base transversal para el ecosistema UBLKit. Contiene los modelos comunes, contratos, excepciones base, objetos de valor y enumeraciones que son agnósticos a cualquier framework.

## Stack Tecnológico
- Java 21+
- Paradigma de programación orientada a objetos (uso extensivo de `record` para inmutabilidad)
- Sin dependencias de terceros (cero dependencias externas para mantener el núcleo ligero)

## Arquitectura del Proyecto
Sigue la **Arquitectura Hexagonal**. Este módulo representa la capa más profunda (Dominio/Core). No tiene dependencias externas ni conoce de infraestructura (como frameworks, XML, HTTP o SOAP). Todas las dependencias de otros módulos apuntan hacia este.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-core</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del código fuente de `ublkit-core` se organiza de la siguiente manera:
- `src/main/java/com/cna/ublkit/core/domain/valueobject/`: Contiene los Objetos de Valor inmutables como `Dinero`, `Moneda`, `NumeroSerie`.
- `src/main/java/com/cna/ublkit/core/domain/enums/`: Enums transversales como `TipoDocumento`, `TipoAmbiente`.
- `src/main/java/com/cna/ublkit/core/domain/exception/`: Excepciones base como `ExcepcionUblKit`, y excepciones de validación, firma o transporte.
- `src/main/java/com/cna/ublkit/core/domain/`: Clases comunes como `ResultadoOperacion`.

## Características Principales
- Tipos canónicos para ambiente, documento, transporte y datos monetarios.
- Resultados de operaciones comunes (`ResultadoOperacion`) sin depender de excepciones para el control de flujo.
- Modelado inmutable (uso de records).
- Excepciones base tipadas para facilitar el manejo de errores en módulos superiores.

## Flujo de Desarrollo
- Proceso guiado por PRs y revisiones.
- Uso estricto de Java puro sin librerías externas.
- Los cambios deben mantener la inmutabilidad y no acoplar lógica de infraestructura.

## Estándares de Código
- Nombres de clases y métodos descriptivos y en español.
- Tipos de dominio inmutables preferiblemente implementados como `record`.
- Diseño limpio y libre de dependencias ajenas a Java puro.

## Pruebas
- Se utilizan pruebas unitarias estándar con JUnit 5.
- Enfoque en la validación de objetos de valor inmutables, aserciones de excepciones y flujos de `ResultadoOperacion`.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
