# ublkit-testkit

## Nombre y Descripción del Proyecto
**ublkit-testkit** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo de utilidades que provee herramientas para acelerar la creación de pruebas unitarias y de integración sobre los flujos de UBLKit, sin depender de redes externas.

## Stack Tecnológico
- Java 21+
- Dependencias de Testing (JUnit 5, AssertJ, Mockito)

## Arquitectura del Proyecto
Módulo de utilería auxiliar. No forma parte del core de producción. Funciona proporcionando fixtures y validadores XML/CDR que pueden ser utilizados por otros módulos de UBLKit o por los proyectos que consumen la librería durante sus propios tests.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-testkit</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura incluye:
- Fixtures documentales en clases como `FacturasEjemplo`, `NotasCreditoEjemplo`, `GuiasRemisionEjemplo`, etc.
- Aserciones personalizadas: `AssertsXml` y `AssertsCdr`.
- Simuladores: `SimuladorGateway` y `RespuestasSunatSimuladas`.

## Características Principales
- Provisión de 'fixtures' para construir rápidamente objetos de dominio de prueba.
- Aserciones fluídas (AssertJ-style) para validar la estructura de XML y CDR generados.
- Simulador de la Pasarela SUNAT (`SimuladorGateway`) para probar flujos completos de envío y recepción de tickets sin conexión real a internet.

## Flujo de Desarrollo
- Desarrollo y actualización de 'fixtures' a medida que se agregan nuevas casuísticas o cambian los modelos documentales.
- Los módulos principales de UBLKit (como `ublkit-ubl`, `ublkit-gateway`) importan este módulo en su fase `test`.

## Estándares de Código
- Código de prueba legible y con nombres descriptivos.
- Simuladores que no dependan de frameworks complejos, basándose en el cumplimiento de interfaces (ej. implementando `PasarelaSunat`).

## Pruebas
- El testkit en sí mismo es usado para pruebas, por lo que su correcto funcionamiento se valida implícitamente en los tests de los otros módulos.
- Se debe asegurar que las respuestas simuladas respeten los formatos reales conocidos de SUNAT.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
