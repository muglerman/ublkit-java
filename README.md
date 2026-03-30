# UBLKit

**UBLKit** es una librería comunitaria diseñada para modelar, validar, firmar, enviar y renderizar comprobantes de pago electrónicos o documentos UBL desde Java.

## Objetivo del proyecto
El propósito principal es ofrecer una solución integral, estructurada bajo **arquitectura hexagonal**, para aislar la lógica de los documentos de cualquier infraestructura o framework (como Spring o Quarkus). Se busca que el manejo de documentos electrónicos se oriente al **dominio** de uso amigable y no de forma exclusiva a través de APIs XML crudas o dependientes del medio de distribución.

## Estado del Proyecto
* MVP (En desarrollo) - Enfocado en estructurar un Core agnóstico y validaciones robustas (Etapa 1).

## Módulos
- `ublkit-core`: Modelos de negocio, enums y objetos de valor 100% Java puro.
- `ublkit-validation`: Contrato de validaciones sin dependencia en excepciones de control.
- `ublkit-ubl`: Modelamiento documental estructurado.
- `ublkit-sign`: Manejo criptográfico (Firma digital).
- `ublkit-gateway`: Integración con plataformas (SUNAT/OSE/etc.).
- `ublkit-render`: Representación canónica final (PDF/HTML/etc.).
- `ublkit-catalogs`: Manejo y consulta de catálogos estandarizados.
- `ublkit-testkit`: Herramientas de assertions y pruebas con fixtures.
- `ublkit-spring-boot-starter`: Integración transparente al framework de Spring.
- `ublkit-quarkus`: Soporte nativo y rápido en ecosistemas Quarkus.

Ver detalles en [MODULES.md](MODULES.md) y [ARCHITECTURE.md](ARCHITECTURE.md).

## Cómo Contribuir
Por favor, asegúrese de leer nuestras directrices de [NAMING.md](NAMING.md) antes de enviar *Pull Requests*. El núcleo y la mayor cantidad de validaciones posibles son hechas en Java estándar. El proyecto promueve un esquema de inmutabilidad y pruebas robustas.
