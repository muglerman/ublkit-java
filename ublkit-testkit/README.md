# ublkit-testkit

Módulo de herramientas y utilidades para facilitar el testing en aplicaciones que usan UBLKit.

## Responsabilidad
- Proveer fixtures y modelos de ejemplo (FacturasEjemplo, etc.).
- Proporcionar utilidades de aserción para XML UBL.
- Facilitar la emulación de pasarelas de envío para pruebas de integración.

## Componentes Clave
- `FacturasEjemplo`: Retorna borradores listos para pruebas.
- `AssertsXml`: Métodos estáticos para validar valores en XML mediante XPath.
- `SimuladorGateway`: Mock configurable de una pasarela SUNAT.

## Dependencias
- `ublkit-core`
- `ublkit-ubl`
- `ublkit-gateway`
- `org.junit.jupiter:junit-jupiter`

## Ejemplo de Uso
```java
BorradorFactura factura = FacturasEjemplo.facturaMinima();
String xml = serializador.serializar(factura);
AssertsXml.assertXPath(xml, "//ID", "F001-1");
```
