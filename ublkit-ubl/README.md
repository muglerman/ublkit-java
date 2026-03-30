# ublkit-ubl

Módulo encargado del modelamiento documental amigable y su transformación al estándar XML UBL 2.1.

## Responsabilidad
- Proveer un modelo documental racional para el desarrollador.
- Automatizar la serialización XML siguiendo los esquemas de la SUNAT.
- Normalizar los campos documentales necesarios para la emisión.

## Componentes Clave
- `BorradorFactura`: El modelo central para Facturas y Boletas.
- `BorradorGuiaRemision`: Modelo para Guías de Remisión Remitente y Transportista.
- `DocumentoBase`: Raíz común para actoría (emisor, receptor) e identificación.
- `SerializadorXmlUbl`: Clase encargada de la transformación XML.

## Dependencias
- `ublkit-core`
- `ublkit-catalogs`

## Ejemplo de Uso
```java
BorradorFactura factura = new BorradorFactura();
factura.setSerie("F001");
factura.setNumero(1);
// ...
```
