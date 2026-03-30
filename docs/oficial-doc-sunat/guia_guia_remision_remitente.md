# Guía de Elaboración de Documentos Electrónicos XML
## 1. Guía Electrónica de Remisión del Remitente

## Página 1

GUIA DE ELABORACION DE DOCUMENTOS ELECTRONICOS XML

1. Guía Electrónica de Remisión del Remitente

SUPERINTENDENCIA NACIONAL DE ADUANAS Y ADMINISTRACIÓN TRIBUTARIA  
SUNAT - Lima – Perú

Noviembre 2013

Emisión electrónica desde los Sistemas del Contribuyente

RS 097-2012/SUNAT

## Página 2

Guía de elaboración de documentos electrónicos XML - UBL 2.0

INDICE

1. INTRODUCCION  
2. GUIA ELECTRONICA DE REMISION DEL REMITENTE  
2.1 CONTENIDO DE LA GUÍA ELECTRÓNICA DE REMISIÓN DEL REMITENTE  
2.2 ESTRUCTURA DE LA GUÍA ELECTRÓNICA SEGÚN EL ESTÁNDAR UBL  
2.3 ESTRUCTURA DE GUÍA ELECTRÓNICA VS FORMATO XML  
2.4 NORMAS DE USO DEL FORMATO DE LA GUÍA ELECTRÓNICA  

### A. Normas de Uso
### A.1 Elementos de la Guía electrónica

1. Versión del UBL  
2. Versión de la estructura del documento  
3. Numeracion, conformada por serie y numero correlativo  
4. Fecha de emission  
5. Tipo de documento  
6. Observaciones  
7. Serie y número de documento de Guía de Remisión dada de Baja  
8. Código del tipo de documento de Guía de Remisión dada de Baja  
9. Tipo de documento de Guía de Remisión dada de Baja  
10. Número de documento del documento relacionado  
11. Código del tipo de documento del documento relacionado  
12. Firma Digital  
13. Número de document de identida del remitente  
14. Tipo de documento de identidad del remitente  
15. Apellidos y nombres, denominacion o razon social del remitente  
16. Número de document de identidad del destinatario  
17. Tipo de documento de identidad del destinatario  
18. Apellidos y nombres, denominacion o razon social del destinatario  
19. Número de documento de identidad del proveedor  
20. Tipo de documento de identidad del proveedor  
21. Apellidos y nombres, denominación o razón social del proveedor  
22. Motivo del traslado  
23. Descripción de motivo de traslado  
24. Indicador de motivo de traslado  
25. Peso bruto total de los bienes  
26. Unidad de medida del peso bruto  
27. Número de Bulltos o Pallets  
28. Modalidad de traslado  

## Página 3

29. Fecha de inicio del traslado  
30. Número de RUC transportista  
31. Tio de documento del trasportista  
32. Apellidos y nombres o denominación o razón social del transportista  
33. Número de placa del vehículo de transporte privado  
34. Número de documento de identidad del conductor de transporte privado  
35. Tipo de documento de identidad del conductor de transporte privado  
36. Ubigeo de la dirección del punto de llegada  
37. Dirección completa detallada del punto de llegada  
38. Número del contenedor  
39. Ubigeo del punto de partida  
40. Dirección complete y detallada del punto de partida  
41. Código del puerto o aeropuerto de embarque / desembarque  
42. Número del orden del ítem del bien a transportar  
43. Cantidad del ítem del bien a transportar  
44. Unidad de medida del ítem del bien a transportar  
45. Descripción detallada del ítem del bien a transportar  
46. Código del ítem del bien a transportar  

## Página 4

## Registros de Cambios de la Guía

| Fecha | Versión | Elemento de Cambio | Motivo de Cambio | Autor |
|---|---|---|---|---|
| 30/09/2015 | 1.0 |  |  | Proyecto libros y comprobantes de pago electrónicos |

## Página 5

# 1 INTRODUCCION

Las Guías de Elaboración de documentos electrónicos XML son documentos que presentan el análisis e identificación de los campos tributarios requeridos para la emisión de los comprobantes de pago y demás documentos electrónicos regulados por la Resolución de Superintendencia 097-2012/SUNAT.

Describen las normas de uso para la construcción de dichos documentos utilizando el estándar UBL 2.0 en relación a los datos tributarios, regulados en los anexos 1, 2, 3, 4, 5, 9 y 10 de la referida resolución. Ello no limita el uso de campos adicionales no tributarios.

El estándar UBL es extenso y permite especificar gran cantidad de información relacionada con los procesos comerciales. Se recomienda revisar también la propia definición del estándar UBL y contar con una aplicación que permita validar y editar archivos XML.

Las guías de elaboración de documentos electrónicos XML se han definido para los siguientes documentos:

1. Factura Electrónica
2. Boleta de Venta Electrónica
3. Nota de Crédito Electrónica
4. Nota de Débito electrónica
5. Resumen Diario de Boletas de Venta y notas de crédito y débito relacionadas
6. Comunicación de Baja
7. Guía Electrónica

## Página 6

# 2 GUIA ELECTRONICA DE REMISION DEL REMITENTE

La guía electrónica es la guía regulada por el Reglamento de Comprobantes de pago soportado en un formato digital que cumple con las especificaciones reguladas en la R.S. 097-2012/SUNAT y que se encuentra firmada digitalmente.

## 2.1 Contenido de la guía electrónica de remisión del remitente

Nomenclatura de representación del valor de los datos:

| Código | Significado |
|---|---|
| a | carácter alfabético |
| n | carácter numérico |
| an | carácter alfanumérico |
| a3 | 3 caracteres alfabéticos de longitud fija |
| n3 | 3 caracteres numéricos de longitud fija |
| an3 | 3 caracteres alfa-numéricos de longitud fija |
| a..3 | hasta 3 caracteres alfabéticos |
| n..3 | hasta 3 caracteres numéricos |
| an..3 | hasta 3 caracteres alfa-numéricos |

Obligatoriedad:
- **M**: Mandatorio u obligatorio
- **C**: Condicional u opcional

Formato de datos:

| Formato | Descripción |
|---|---|
| n(12,2) | elemento numérico hasta 12 enteros + punto decimal; hasta dos decimales |
| n(2,2) | elemento numérico hasta 2 enteros + punto decimal; hasta dos decimales |
| G##### | elemento inicia con la letra G seguida de cinco dígitos |
| YYYY-MM-DD | formato fecha yyyy=año, mm=mes, dd=día |

## Página 7

## CONTENIDO DE LA GUÍA ELECTRÓNICA

| Nro | Dato | Nivel | Condición informática | Tipo y longitud | Formato | Tag UBL |
|---|---|---|---|---|---|---|
| 1 | Versión del UBL | Global | M | an3 |  | /DespatchAdvice/cbc:UBLVersionID |
| 2 | Versión de la estructura del documento | Global | M | an3 |  | /DespatchAdvice/cbc:CustomizationID |
| 3 | Numeración, conformada por serie y número correlativo | Global | M | an..13 | T###-NNNNNNNN | /DespatchAdvice/cbc:ID |
| 4 | Fecha de emisión | Global | M | an..10 | YYYY-MM-DD | /DespatchAdvice/cbc:IssueDate |
| 5 | Tipo de documento (Guía) | Global | M | an2 | Catálogo N° 01 | /DespatchAdvice/cbc:DespatchAdviceTypeCode |
| 6 | Observaciones (Texto) | Global | C | an..250 |  | /DespatchAdvice/cbc:Note |
| 7 | Serie y número de documento |  | M | an..13 | T###-NNNNNNNN / EG01-NNNNNNNN | /DespatchAdvice/cac:OrderReference/cbc:ID |
| 8 | Código del tipo de documento |  | M | an2 | Catálogo N° 01, ser = 09 | /DespatchAdvice/cac:OrderReference/cbc:OrderTypeCode |
| 9 | Tipo de documento |  | C | an..50 |  | /DespatchAdvice/cac:OrderReference/cbc:OrderTypeCode@name |
| 10 | Número de documento | Global | M | an..20 |  | /DespatchAdvice/cac:AdditionalDocumentReference/cbc:ID |
| 11 | Código del tipo de documento | Global | M | an2 | Catálogo N° 23 | /DespatchAdvice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode |

## Página 8

| Nro | Dato | Nivel | Condición informática | Tipo y longitud | Formato | Tag UBL |
|---|---|---|---|---|---|---|
| 12 | Firma Digital | Global | M | an..3000 |  | /DespatchAdvice/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature / /DespatchAdvice/cac:Signature |
| 13 | Número de documento de identidad |  | M |  |  | /DespatchAdvice/cac:DespatchSupplierParty/cbc:CustomerAssignedAccountID |
| 14 | Tipo de documento de identidad |  | M |  | Catálogo N° 06 | /DespatchAdvice/cac:DespatchSupplierParty/cbc:CustomerAssignedAccountID@schemeID |
| 15 | Apellidos y nombres, denominación o razón social |  | M |  |  | /DespatchAdvice/cac:DespatchSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName |
| 16 | Número de documento de identidad |  | M | n15 | n(15) | /DespatchAdvice/cac:DeliveryCustomerParty/cbc:CustomerAssignedAccountID |
| 17 | Tipo de documento de identidad |  | M | an2 | Catálogo N° 06 | /DespatchAdvice/cac:DeliveryCustomerParty/cbc:CustomerAssignedAccountID@schemeID |
| 18 | Apellidos y nombres, denominación o razón social del destinatario |  | M | an..100 | an | /DespatchAdvice/cac:DeliveryCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName |
| 19 | Número de documento de identidad |  | M | n11 | n(11) | /DespatchAdvice/cac:SellerSupplierParty/cbc:CustomerAssignedAccountID |
| 20 | Tipo de documento de identidad |  | M | an2 | Catálogo N° 06 | /DespatchAdvice/cac:SellerSupplierParty/cbc:CustomerAssignedAccountID@schemeID |
| 21 | Apellidos y nombres, denominación o razón social |  | M | an2 | Catálogo N° 06 | /DespatchAdvice/cac:SellerSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName |

## Página 9

| Nro | Dato | Nivel | Condición informática | Tipo y longitud | Formato | Tag UBL |
|---|---|---|---|---|---|---|
| 22 | Motivo del traslado | Global | M | an2 | Catálogo N° 20 | /DespatchAdvice/cac:Shipment/cbc:HandlingCode |
| 23 | Descripción de motivo de traslado | Global | C | an..100 | an | /DespatchAdvice/cac:Shipment/cbc:Information |
| 24 | Indicador de Transbordo Programado | Global | M | boolean | true/false | /DespatchAdvice/cac:Shipment/cbc:SplitConsignmentIndicator |
| 25 | Peso bruto total de los bienes | Global | M | n..16 | n(12,3) | /DespatchAdvice/cac:Shipment/cbc:GrossWeightMeasure |
| 26 | Unidad de medida del peso bruto | Global | M | an4 | Catálogo N° 03 = KG | /DespatchAdvice/cac:Shipment/cbc:GrossWeightMeasure@unitCode |
| 27 | Número de bultos o pallets |  | C | n11 | an | /DespatchAdvice/cac:Shipment/cbc:TotalTransportHandlingUnitQuantity |
| 28 | Modalidad de traslado | Global | M | an2 | Catálogo N° 18 | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cbc:TransportModeCode |
| 29 | Fecha de inicio del traslado | Global | M | an..10 | YYYY-MM-DD | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:TransitPeriod/cbc:StartDate |
| 30 | Número de RUC transportista | Global | M | n11 |  | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyIdentification/cbc:ID |
| 31 | Tipo de documento del transportista | Global | M | an2 | Catálogo N° 06 | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyIdentification/cbc:ID@schemeID |
| 32 | Apellidos y nombres o denominación o razón social del transportista | Global | M | an..100 |  | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyName/cbc:Name |
| 33 | Número de placa del vehículo | Global | M | an..8 |  | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:TransportMeans/cac:RoadTransport/cbc:LicensePlateID |
| 34 | Número de documento de identidad del conductor |  | M | n11 |  | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:ID |
| 35 | Tipo de documento de identidad del conductor |  | M | an2 | Catálogo N° 06 | /DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:DriverPerson/cbc:ID@schemeID |

## Página 10

| Nro | Dato | Nivel | Condición informática | Tipo y longitud | Formato | Tag UBL |
|---|---|---|---|---|---|---|
| 36 | Ubigeo |  | M | an8 | Catálogo N° 13 | /DespatchAdvice/cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:ID |
| 37 | Dirección completa y detallada |  | M | an..100 |  | /DespatchAdvice/cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:StreetName |
| 38 | Número de contenedor |  | M | an..17 | na | /DespatchAdvice/cac:Shipment/cac:TransportHandlingUnit/cac:TransportEquipment/cbc:ID |
| 39 | Ubigeo |  | M | an8 | Catálogo N° 13 | /DespatchAdvice/cac:Shipment/cac:OriginAddress/cbc:ID |
| 40 | Dirección completa y detallada |  | M | an..100 |  | /DespatchAdvice/cac:Shipment/cac:OriginAddress/cbc:StreetName |
| 41 | Código del puerto |  | M | an3 |  | /DespatchAdvice/cac:Shipment/cac:FirstArrivalPortLocation/cbc:ID |
| 42 | Número de orden del item | ítem | M | n..4 |  | /DespatchAdvice/cac:DespatchLine/cac:OrderLineReference/cbc:ID |
| 43 | Cantidad del item | ítem | M | n..8 |  | /DespatchAdvice/cac:DespatchLine/cbc:DeliveredQuantity |
| 44 | Unidad de medida del item | ítem | M |  | Catálogo N° 03 | /DespatchAdvice/cac:DespatchLine/cbc:DeliveredQuantity@unitCode |
| 45 | Descripción detallada del item | ítem | M | an..250 |  | /DespatchAdvice/cac:DespatchLine/cac:Item/cbc:Name |
| 46 | Código del item | ítem | C | an..16 |  | /DespatchAdvice/cac:DespatchLine/cac:Item/cac:SellersItemIdentification/cbc:ID |

## Página 11

## 2.2 Estructura de la Guía Electrónica según el estándar UBL

Principales elementos y cardinalidades del documento `DespatchAdvice`:

- `./ext:UBLExtensions`: contenedor de componentes de extensión.
- `./ext:UBLExtension/ext:ExtensionContent`: componente de extensión para especificar la firma XMLDSIG.
- `./ds:Signature @Id`: identificación de la firma dentro del documento.
- `./ds:SignedInfo`: información sobre el valor de la firma e información sobre los datos a firmar.
- `./ds:CanonicalizationMethod@Algorithm`
- `./ds:SignatureMethod@Algorithm`
- `./ds:Reference@URI`
- `./ds:Reference/ds:Transforms/ds:Transform@Algorithm`
- `./ds:Reference/ds:DigestMethod@Algorithm`
- `./ds:Reference/ds:DigestValue`
- `./ds:SignatureValue`
- `./ds:KeyInfo/ds:X509Data/ds:X509Certificate`

## Página 12

Continuación de la estructura UBL:

- `./cbc:UBLVersionID`
- `./cbc:CustomizationID`
- `./cbc:ID`
- `./cbc:IssueDate`
- `./cbc:IssueTime`
- `./cac:DespatchAdviceTypeCode`
- `./cbc:Note`
- `./cac:OrderReference`
- `./cbc:OrderTypeCode`
- `./cac:AdditionalDocumentReference`
- `./cac:Signature`
- `./cac:SignatoryParty`
- `./cac:PartyIdentification`

## Página 13

Más elementos de la estructura UBL:

- `./cac:PartyName`
- `./cbc:Name`
- `./cac:DigitalSignatureAttachment`
- `./cac:ExternalReference`
- `./cbc:URI`
- `./cac:DespatchSupplierParty`
- `./cbc:CustomerAssignedAccountID`
- `./cbc:CustomerAssignedAccountID/@schemeID`
- `./cac:Party`
- `./cac:DeliveryCustomerParty`

## Página 14

- `./cac:PartyLegalEntity`
- `./cbc:RegistrationName`
- `./cac:SellerSupplierParty`
- `./cac:Shipment`
- `./cbc:ID`
- `./cbc:HandlingCode`
- `./cbc:Information`
- `./cbc:SplitConsignmentIndicator`
- `./cbc:GrossWeightMeasure`
- `./cbc:GrossWeightMeasure@unitCode`
- `./cbc:TotalTransportHandlingUnitQuantity`
- `./cac:ShipmentStage`
- `./cbc:TransportModeCode`

## Página 15

- `./cac:TransitPeriod`
- `/cbc:StartDate`
- `./cac:CarrierParty`
- `./cac:PartyIdentification`
- `./cbc:ID`
- `./cbc:ID@schemeID`
- `./cac:PartyName`
- `./cbc:RegistrationName`
- `./cac:TransportMeans`
- `./cac:RoadTransport`
- `./cbc:LicensePlateID`
- `./cac:DriverPerson`
- `./cac:DeliveryAddress`
- `./cbc:StreetName`

## Página 16

- `./cac:TransportHandlingUnit`
- `./cbc:ID`
- `./cac:TransportEquipment`
- `./cbc:ID`
- `./cac:TransportHandlingUnit`
- `./cbc:ID`
- `./cac:OriginAddress`
- `./cbc:ID`
- `./cbc:StreetName`
- `./cac:LoadingPortLocation`
- `./cbc:ID`
- `./cac:FirstArrivalPortLocation`
- `./cbc:ID`
- `./cac:DespatchLine`
- `./cbc:ID`

## Página 17

- `./cac:DeliveredQuantity`
- `./cac:DeliveredQuantity/@unitCode`
- `./cac:OrderLineReference`
- `./cbc:LineID`
- `./cac:Item`
- `./cbc:Description`
- `./cac:SellersItemIdentification/cbc:ID`

## Página 18

## 2.3 Estructura de Guía electrónica vs Formato XML

```xml
<cbc:UBLVersionID>12.0</cbc:UBLVersionID>
<cbc:CustomizationID>1.0</cbc:CustomizationID>
<cbc:ID>G001-8</cbc:ID>
<cbc:IssueDate>2015-10-22</cbc:IssueDate>
<cbc:DespatchAdviceTypeCode>61</cbc:DespatchAdviceTypeCode>
<cbc:Note><![CDATA[Transporto bolsas para basura]]></cbc:Note>

<cac:OrderReference>
  <cbc:ID>T001-8</cbc:ID>
  <cbc:OrderTypeCode name="Guía de Remisión">09</cbc:OrderTypeCode>
</cac:OrderReference>

<cac:AdditionalDocumentReference>
  <cbc:ID>T001-8</cbc:ID>
  <cbc:DocumentTypeCode>09</cbc:DocumentTypeCode>
</cac:AdditionalDocumentReference>
```

## Página 19

```xml
<cac:Signature>
  <cbc:ID>G001-8</cbc:ID>
  <cac:SignatoryParty>
    <cac:PartyIdentification>
      <cbc:ID>20131312955</cbc:ID>
    </cac:PartyIdentification>
    <cac:PartyName>
      <cbc:Name><![CDATA[SUNAT]]></cbc:Name>
    </cac:PartyName>
  </cac:SignatoryParty>
  <cac:DigitalSignatureAttachment>
    <cac:ExternalReference>
      <cbc:URI>SignSUNAT</cbc:URI>
    </cac:ExternalReference>
  </cac:DigitalSignatureAttachment>
</cac:Signature>

<cac:DespatchSupplierParty>
  <cbc:CustomerAssignedAccountID schemeID="6">20262520243</cbc:CustomerAssignedAccountID>
  <cac:Party>
    <cac:PartyLegalEntity>
      <cbc:RegistrationName><![CDATA[PERUQUIMICOS S.A.C.]]></cbc:RegistrationName>
    </cac:PartyLegalEntity>
  </cac:Party>
</cac:DespatchSupplierParty>

<cac:DeliveryCustomerParty>
  <cbc:CustomerAssignedAccountID schemeID="6">10209865209</cbc:CustomerAssignedAccountID>
  <cac:Party>
    <cac:PartyLegalEntity>
      <cbc:RegistrationName><![CDATA[RODRIGUEZ ROQUE AQUILES RUFO]]></cbc:RegistrationName>
    </cac:PartyLegalEntity>
  </cac:Party>
</cac:DeliveryCustomerParty>
```

## Página 20

```xml
<cac:Shipment>
  <cbc:HandlingCode>01</cbc:HandlingCode>
  <cbc:Information><![CDATA[VENTA]]></cbc:Information>
  <cbc:SplitConsignmentIndicator>01</cbc:SplitConsignmentIndicator>
  <cbc:GrossWeightMeasure unitCode="KGM">10000.00</cbc:GrossWeightMeasure>
  <cbc:TotalTransportHandlingUnitQuantity>5</cbc:TotalTransportHandlingUnitQuantity>
  <cac:ShipmentStage>
    <cbc:TransportModeCode>1</cbc:TransportModeCode>
    <cac:TransitPeriod>
      <cbc:StartDate>2015-10-23</cbc:StartDate>
    </cac:TransitPeriod>
  </cac:ShipmentStage>
</cac:Shipment>

<cac:CarrierParty>
  <cac:PartyIdentification>
    <cbc:ID schemeID="6">10209865209</cbc:ID>
  </cac:PartyIdentification>
  <cac:PartyName>
    <cbc:Name><![CDATA[PERUQUIMICOS S.A.C.]]></cbc:Name>
  </cac:PartyName>
</cac:CarrierParty>

<cac:DriverPerson>
  <cbc:ID schemeID="1">10101010</cbc:ID>
</cac:DriverPerson>

<cac:TransportMeans>
  <cac:RoadTransport>
    <cbc:LicensePlateID>PGY-0988</cbc:LicensePlateID>
  </cac:RoadTransport>
</cac:TransportMeans>
```

## Página 21

```xml
<cac:TransportHandlingUnit>
  <cac:TransportEquipment>
    <cbc:ID>PGY-645</cbc:ID>
  </cac:TransportEquipment>
  <cac:TransportEquipment>
    <cbc:ID>PGY-645</cbc:ID>
  </cac:TransportEquipment>
</cac:TransportHandlingUnit>

<cac:DeliveryAddress>
  <cbc:ID>120606</cbc:ID>
  <cbc:StreetName><![CDATA[JR. MANTARO NRO. 257]]></cbc:StreetName>
</cac:DeliveryAddress>

<cac:OriginAddress>
  <cbc:ID>150123</cbc:ID>
  <cbc:StreetName><![CDATA[CAR. PANAM SUR KM 25 NO. 25050 NRO. 050 Z.I. CONCHAN]]></cbc:StreetName>
</cac:OriginAddress>

<cac:FirstArrivalPortLocation>
  <cbc:ID>PAI</cbc:ID>
</cac:FirstArrivalPortLocation>

<cac:DespatchLine>
  <cbc:ID>1</cbc:ID>
  <cbc:DeliveredQuantity unitCode="KGM">10</cbc:DeliveredQuantity>
  <cac:OrderLineReference>
    <cbc:LineID>1</cbc:LineID>
  </cac:OrderLineReference>
  <cac:Item>
    <cbc:Description><![CDATA[ACETONA - 500.50 BALDE - 500.50 BALAS]]></cbc:Description>
    <cac:SellersItemIdentification>
      <cbc:ID>COD1</cbc:ID>
    </cac:SellersItemIdentification>
  </cac:Item>
</cac:DespatchLine>
```

## Página 22

## 2.4 Normas de Uso del Formato de la Guía Electrónica

### A. Normas de Uso

El formato UBL está basado en el uso de un documento XML para presentar todos los datos de forma jerárquica. Para un archivo se debe especificar toda la información de una y solo una guía.

La etiqueta raíz es:

```xml
<DespatchAdvice>
......
</DespatchAdvice>
```

## Página 23

### A.1 Elementos de la Guía electrónica

#### 1. Versión del UBL
Obligatorio. Para el caso peruano se ha utilizado la versión `2.0`.  
Ubicación: `/DespatchAdvice/cbc:UBLVersionID`

#### 2. Versión de la estructura del documento
Ubicación: `/DespatchAdvice/cbc:CustomizationID`

#### 3. Numeración, conformada por serie y número correlativo
Ubicación: `/DespatchAdvice/cbc:ID`

#### 4. Fecha de emisión
Ubicación: `/DespatchAdvice/cbc:IssueDate`

#### 5. Tipo de documento
Valor: `09 GUIA REMISION REMITENTE`

## Página 24

Ubicación del tipo de documento: `/DespatchAdvice/cbc:DespatchAdviceTypeCode`

#### 6. Observaciones
Ubicación: `/DespatchAdvice/cbc:Note`

#### 7. Serie y número de documento de Guía de Remisión dada de Baja
Ubicación: `/DespatchAdvice/cac:OrderReference/cbc:ID`

#### 8. Código del tipo de documento de Guía de Remisión dada de Baja
Ubicación: `/DespatchAdvice/cac:OrderReference/cbc:OrderTypeCode`

#### 9. Tipo de documento de Guía de Remisión dada de Baja
Ubicación: `/DespatchAdvice/cac:OrderReference/cbc:OrderTypeCode@name`

#### 10. Número de documento del documento relacionado
Ubicación: `/DespatchAdvice/cac:AdditionalDocumentReference/cbc:ID`

## Página 25

#### 11. Código del tipo de documento del documento relacionado
Ubicación: `/DespatchAdvice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode`

#### 12. Firma Digital
Ubicación: `/DespatchAdvice/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature`

#### 13. Número de documento de identidad del remitente
Ubicación: `/DespatchAdvice/cac:DespatchSupplierParty/cbc:CustomerAssignedAccountID`

#### 14. Tipo de documento de identidad del remitente
Ubicación: `/DespatchAdvice/cac:DespatchSupplierParty/cbc:CustomerAssignedAccountID@schemeID`

#### 15. Apellidos y nombres, denominación o razón social del remitente
Ubicación: `/DespatchAdvice/cac:DespatchSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName`

## Página 26

#### 16. Número de documento de identidad del destinatario
Ubicación: `/DespatchAdvice/cac:DeliveryCustomerParty/cbc:CustomerAssignedAccountID`

#### 17. Tipo de documento de identidad del destinatario
Ubicación: `/DespatchAdvice/cac:DeliveryCustomerParty/cbc:CustomerAssignedAccountID@schemeID`

#### 18. Apellidos y nombres, denominación o razón social del destinatario
Ubicación: `/DespatchAdvice/cac:DeliveryCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName`

#### 19. Número de documento de identidad del proveedor
Ubicación: `/DespatchAdvice/cac:SellerSupplierParty/cbc:CustomerAssignedAccountID`

#### 20. Tipo de documento de identidad del proveedor
Ubicación: `/DespatchAdvice/cac:SellerSupplierParty/cbc:CustomerAssignedAccountID@schemeID`

#### 21. Apellidos y nombres, denominación o razón social del proveedor
Ubicación: `/DespatchAdvice/cac:SellerSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName`

## Página 27

#### 22. Motivo del traslado
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:HandlingCode`

#### 23. Descripción de motivo de traslado
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:Information`

#### 24. Indicador de motivo de traslado
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:SplitConsignmentIndicator`

#### 25. Peso bruto total de los bienes
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:GrossWeightMeasure`

#### 26. Unidad de medida del peso bruto
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:GrossWeightMeasure@unitCode`

#### 27. Número de bultos o pallets
Ubicación: `/DespatchAdvice/cac:Shipment/cbc:TotalTransportHandlingUnitQuantity`

## Página 28

#### 28. Modalidad de traslado
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cbc:TransportModeCode`

#### 29. Fecha de inicio del traslado
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:TransitPeriod/cbc:StartDate`

#### 30. Número de RUC transportista
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyIdentification/cbc:ID`

#### 31. Tipo de documento del transportista
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyIdentification/cbc:ID@schemeID`

#### 32. Apellidos y nombres o denominación o razón social del transportista
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:CarrierParty/cac:PartyName/cbc:Name`

## Página 29

#### 33. Número de placa del vehículo de transporte privado
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:TransportMeans/cac:RoadTransport/cbc:LicensePlateID`

#### 34. Número de documento de identidad del conductor de transporte privado
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:DriverPerson/ID`

#### 35. Tipo de documento de identidad del conductor de transporte privado
Ubicación: `/DespatchAdvice/cac:Shipment/cac:ShipmentStage/cac:DriverPerson/ID@schemeID`

#### 36. Ubigeo de la dirección del punto de llegada
Ubicación: `/DespatchAdvice/cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:ID`

#### 37. Dirección completa detallada del punto de llegada
Ubicación: `/DespatchAdvice/cac:Shipment/cac:Delivery/cac:DeliveryAddress/cbc:StreetName`

## Página 30

#### 38. Número del contenedor
Ubicación: `/DespatchAdvice/cac:Shipment/cac:TransportHandlingUnit/cbc:ID`

#### 39. Ubigeo del punto de partida
Ubicación: `/DespatchAdvice/cac:Shipment/cac:OriginAddress/cbc:ID`

#### 40. Dirección completa y detallada del punto de partida
Ubicación: `/DespatchAdvice/cac:Shipment/cac:OriginAddress/cbc:StreetName`

#### 41. Código del puerto o aeropuerto de embarque / desembarque
Ubicación: `/DespatchAdvice/cac:Shipment/cac:FirstArrivalPortLocation/cbc:ID`

#### 42. Número del orden del ítem del bien a transportar
Ubicación: `/DespatchAdvice/cac:DespatchLine/cac:OrderLineReference/cbc:ID`

#### 43. Cantidad del ítem del bien a transportar
Ubicación: `/DespatchAdvice/cac:DespatchLine/cbc:DeliveredQuantity`

## Página 31

#### 44. Unidad de medida del ítem del bien a transportar
Ubicación: `/DespatchAdvice/cac:DespatchLine/cbc:DeliveredQuantity@unitCode`

#### 45. Descripción detallada del ítem del bien a transportar
Ubicación: `/DespatchAdvice/cac:DespatchLine/cac:Item/cbc:Name`

#### 46. Código del ítem del bien a transportar
Ubicación: `/DespatchAdvice/cac:DespatchLine/cac:Item/cac:SellersItemIdentification/cbc:ID`
