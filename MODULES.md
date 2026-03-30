# Módulos del Proyecto

Con el fin de evitar la construcción de librerías monolíticas, UBLKit se divide en unidades compuestas de manera jerárquica:

### 1. `ublkit-core`
- **Responsabilidad:** Define tipos de dato general del paradigma (TiposDocumento, TipoAmbiente, resultado global de operaciones).
- **No debe contener:** Interacciones SOAP, JAXB, Spring, Quarkus.

### 2. `ublkit-catalogs`
- **Responsabilidad:** Validar y soportar operaciones basadas en catálogos de listas y códigos de estado normativos (como los de la SUNAT).

### 3. `ublkit-validation`
- **Responsabilidad:** Procesar de forma íntegra un documento y escupirlo con un listado de errores o incidencias (resultado de validación), sin interrumpir flujos de control por el primer fallo.

### 4. `ublkit-ubl`
- **Responsabilidad:** Serializa e interopera la lógica del documento amigable para llevarla a un XML con el formato UBL y viceversa.

### 5. `ublkit-sign`
- **Responsabilidad:** Encapsulamiento del material criptográfico para la provisión autónoma de una firma digital.

### 6. `ublkit-gateway`
- **Responsabilidad:** Provee estrategias unificadas para enviar (y consultar respuesta) usando endpoints (SUNAT u OSE). Trabaja con XMLs ya firmados o construidos (él NO los fabrica).

### 7. `ublkit-render`
- **Responsabilidad:** Convierte un modelo de dominio o XML/UBL a PDF, ticket térmico o HTML utilizable por humanos.

### 8. `ublkit-testkit`
- **Responsabilidad:** Centraliza mocks y fixtures (archivos estáticos, aserciones) para acelerar y normar el testing en implementaciones que adopten este kit.

### Integraciones (Spring Boot & Quarkus)
Solo proveen autoconfiguración (`Beans`, inyección, propiedades de application.properties/yaml) basadas en los *ports* y constructores exportados de los módulos listados previamente.
