# Guía de Elaboración de Documentos XML - Nota de Crédito Electrónica UBL 2.1

## Página 1

Guía de Elaboración de  
Documentos XML  
Nota de Crédito Electrónica  
UBL 2.1

PROYECTO DE COMPROBANTES DE PAGO  
ELECTRONICOS

Versión 1.0  
Mayo 2017

## Página 2

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 1 ~

INDICE

1 NOTA DE CRÉDITO ELECTRONICA............................................................. 3  
1.1 Requisitos de la Nota de Crédito electrónica................................................... 3  
1.2 Estructura de Nota de crédito vs FormatoXML................................................ 9  
1.3 Normas de Uso del Formato de la Nota de Crédito Electrónica .................... 13  
1. Firma Digital ...................................................................................................... 14  
2. Versión del UBL ................................................................................................ 16  
3. Versión de la estructura del documento............................................................. 16  
4. Numeración, conformada por serie y número correlativo................................... 17  
5. Fecha de emisión .............................................................................................. 17  
6. Leyendas........................................................................................................... 18  
7. Tipo de moneda en la cual se emite la nota de crédito electrónica.................... 18  
8. Código del tipo de Nota de crédito electrónica................................................... 19  
9. Motivo o Sustento.............................................................................................. 20  
10. Serie y número del documento que modifica..................................................... 20  
11. Tipo de documento del documento quemodifica................................................ 21  
12. Documento dereferencia ................................................................................... 21  
13. Apellidos y nombres o denominación o razón social.......................................... 23  
14. Tipo y Número de RUC del Emisor.................................................................... 24  
15. Código del domicilio fiscal o de local anexo del emisor. .................................... 25  
16. Apellidos y nombres o denominación o razón social del adquirente o usuario... 25  
17. Tipo y número de documento de identidad del adquirente o usuario. ................ 26  
18. Monto Total de Impuestos. ................................................................................ 27  
19. Total valor de venta - operaciones gravadas. .................................................... 27  
20. Total valor de venta - operaciones inafectas. .................................................... 28  
21. Total valor de venta - operaciones exoneradas. ................................................ 29  
22. Sumatoria de IGV.............................................................................................. 30  
23. Sumatoria de ISC. ............................................................................................. 31  
24. Sumatoria de Otros Tributos.............................................................................. 32  
25. Total de Descuentos.......................................................................................... 33  
26. Importe total de la venta, de la cesión en uso o del servicio prestado. .............. 33  
27. Número de orden del Ítem................................................................................. 34  
28. Cantidad de unidades por ítem.......................................................................... 34  
29. Valor de venta por ítem. .................................................................................... 35  
30. Precio de Venta unitario por ítem que modifica y código. .................................. 35  
31. Valor unitario por ítem en operaciones no onerosas y código............................ 36  

## Página 3

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 2 ~

32. Afectación al IGV del ítem que modifica. ........................................................... 36  
33. Afectación al ISC del ítem que modifica. ........................................................... 37  
34. Descripción detallada del servicio prestado, bien vendido o cedido en uso. ...... 38  
35. Código Producto................................................................................................ 38  
36. Código Producto de SUNAT.............................................................................. 39  
37. Valor unitario del ítem........................................................................................ 40  
B.2 Detalle de elementos complejos........................................................................... 41  
B.2.1 Tag UBLExtension.......................................................................................... 41  
1.5 Ejemplos de casos identificados ................................................................... 45

## Página 4

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 3 ~

# 1 NOTA DE CRÉDITO ELECTRONICA

La nota de crédito electrónica es la nota de crédito regulada por el Reglamento de  
Comprobantes de pago soportado en un formato digital que cumple con las  
especificaciones reguladas en la R.S.097-2012/SUNAT y que se encuentra firmada  
digitalmente.

El contenido de información ha sido regulado por el Anexo 03 de la R.S. 097-2012 y por el  
Anexo 09 en relación al uso del estándar UBL. Así como sus normas modificatorias, siendo  
la última materia de la presente guía la R.S. 117-2017. En el presente documento se  
desarrolla el detalle de los campos (tag) indicados en dicho anexo.

## 1.1 Requisitos de la Nota de Crédito electrónica

En el cuadro siguiente, se describe el contenido (campos) de la Nota de Credito  
electrónica. Para lo cual, de manera previa, es necesario establecer la nomenclatura de  
representación del valor de los datos, para una comprensión correcta del contenido

- a: caracter alfabético
- n: caracter numérico
- an: carácter alfanumérico
- a3: 3 caracteres alfabéticos de longitud fija
- n3: 3 caracteres numéricos de longitud fija
- an3: 3 caracteres alfa-numéricos de longitud fija
- a..3: hasta 3 caracteres alfabéticos
- n..3: hasta 3 caracteres numéricos
- an..3: hasta 3 caracteres alfa-numéricos

Asimismo, la obligatoriedad o no de un determinado elemento se identifica por la siguiente nomenclatura:

- M: Mandatorio u obligatorio
- C: Condicional u opcional

En relación a la identificación del formato de los elementos de datos se especifica lo siguiente:

- n(12,2): elemento numérico hasta 12 enteros + punto decimal + hasta dos decimales
- n(2,2): elemento numérico hasta 2 enteros + punto decimal + hasta dos decimales
- F#####: elemento inicia con la letra F seguida de cinco dígitos
- YYYY-MM-DD: formato fecha yyyy=año, mm=mes, dd=día

## Página 5

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 4 ~

## CONTENIDO DE LA NOTA DE CRÉDITO ELECTRONICA

| Raíz | Nodo / Atributo | Dato | Cardinalidad UBL | Valor / Formato | Observaciones |
|---|---|---|---|---|---|
| /CreditNote | - | - | - | - | - |
| /CreditNote/ext:UBLExtensions |  |  | 0..1 |  |  |
| /CreditNote/ext:UBLExtensions/ext:UBLExtension |  |  | 1..n |  |  |
| /CreditNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent |  |  | 1 |  |  |
|  | ds:Signature | Firma Digital |  |  |  |
|  | cbc:UBLVersionID | Versión del UBL | 0..1 | "2.1" |  |
|  | cbc:CustomizationID | Versión de la estructura del documento | 0..1 | "2.0" |  |
|  | cbc:ID | Serie y número del comprobante | 1 | F###-NNNNNNNN |  |
|  | cbc:IssueDate | Fecha de emisión | 1 | yyyy-mm-dd |  |
|  | cbc:IssueTime | Hora de emisión | 0..1 | hh-mm-ss.0z |  |
|  | cbc:Note | Leyenda | 0..n | an..100 |  |
|  | @languageLocaleID | Código de leyenda | 0..1 | "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo52" | Catálogo 52 |
|  | cbc:DocumentCurrencyCode | Código de tipo de moneda en la cual se emite la nota de crédito electrónica | 0..1 | an3 | Catálogo 02 |
| /CreditNote/cac:DiscrepancyResponse |  |  | 0..n |  |  |
|  | cbc:ReferenceID | Serie y número de comprobante afectado | 1 | NNNN-NNNNNNNN / F###-NNNNNNNN |  |
|  | cbc:ResponseCode | Código de tipo de nota de crédito | 0..1 | n2 | Catálogo 09 |
|  | cbc:Description | Motivo o sustento | 0..n | an..250 |  |
| /CreditNote/cac:BillingReference/cac:CreditNoteDocumentReference |  |  | 0..1 |  |  |
|  | cbc:ID | Serie y número del comprobante que modifica | 1 | NNNN-NNNNNNNN / F###-NNNNNNNN |  |
|  | cbc:DocumentTypeCode | Código de tipo de comprobante que modifica | 0..1 | n2 | Catálogo 01 |

## Página 6

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 5 ~

| Raíz | Nodo / Atributo | Dato | Cardinalidad UBL | Valor / Formato | Observaciones |
|---|---|---|---|---|---|
| /CreditNote/cac:DespatchDocumentReference |  |  | 0..n |  |  |
|  | cbc:ID | Serie y número de la guía de remisión (comprobante de referencia) | 1 | NNNN-NNNNNNNN / G###-NNNNNNNN / R###-NNNNNNNN |  |
|  | cbc:DocumentTypeCode | Código de tipo de comprobante (comprobante de referencia) | 0..1 | n2 | Catálogo 01 |
| /CreditNote/cac:AdditionalDocumentReference |  |  | 0..n |  |  |
|  | cbc:ID | Serie y número del comprobante de referencia | 1 | an..30 |  |
|  | cbc:DocumentTypeCode | Código de tipo de comprobante de referencia | 0..1 | n2 | Catálogo 12 |
| /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme |  |  | 0..n |  |  |
|  | cbc:RegistrationName | Nombre o razón social del emisor | 0..1 | an..100 |  |
|  | cbc:CompanyID | Número de RUC del emisor | 0..1 | n11 |  |
|  | @schemeID | Tipo de Documento de Identidad del Emisor | 0..1 | an1 | Catálogo 06 |
|  | @schemeName | - | 0..1 | "SUNAT:Identificador de Documento de Identidad" |  |
|  | @schemeAgencyName | - | 0..1 | "PE:SUNAT" |  |
|  | @schemeURI | - | 0..1 | "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06" |  |
| /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cac:RegistrationAddress |  | 0..1 |  |  |  |
|  | cbc:AddressTypeCode | Código del domicilio fiscal o de local anexo del emisor | 0..1 | n4 |  |
| /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme |  | 0..n |  |  |  |
|  | cbc:RegistrationName | Nombre o razón social del adquirente o usuario | 0..1 | an..100 |  |
|  | cbc:CompanyID | Número de RUC del adquirente o usuario | 0..1 | n11 |  |
|  | @schemeID | Tipo de Documento de Identidad del Emisor | 0..1 | an1 | Catálogo 06 |
|  | @schemeName | - | 0..1 | "SUNAT:Identificador de Documento de Identidad" |  |
|  | @schemeAgencyName | - | 0..1 | "PE:SUNAT" |  |
|  | @schemeURI | - | 0..1 | "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06" |  |

## Página 7

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 6 ~

| Raíz | Nodo / Atributo | Dato | Cardinalidad UBL | Valor / Formato | Observaciones |
|---|---|---|---|---|---|
| /CreditNote/cac:TaxTotal |  | 0..n |  |  |  |
|  | cbc:TaxAmount | Monto total del tributo | 1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto total del tributo | 1 | an3 | Catálogo 02 |
| /CreditNote/cac:TaxTotal/cac:TaxSubtotal |  | 0..n |  |  |  |
|  | cbc:TaxAmount | Monto total del tributo | 1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto total del tributo | 1 | an3 | Catálogo 02 |
| /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme | 1 |  |  |  |  |
|  | cbc:ID | Código de tributo | 0..1 | an4 | Catálogo 05 |
|  | cbc:Name | Nombre de tributo | 0..1 | an..6 | Catálogo 05 |
|  | cbc:TaxTypeCode | Código internacional tributo | 0..1 | an3 | Catálogo 05 |
| /CreditNote/cac:LegalMonetaryTotal | 1 |  |  |  |  |
|  | cbc:AllowanceTotalAmount | Monto total de descuentos globales del comprobante | 0..1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto total de descuentos globales del comprobante | 1 | an3 | Catálogo 02 |
|  | cbc:ChargeTotalAmount | Monto total de otros cargos del comprobante | 0..1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto total de otros cargos del comprobante | 1 | an3 | Catálogo 02 |
|  | cbc:PrepaidAmount | Monto total de anticipos del comprobante | 0..1 | n(15,2) |  |
|  | @currencyID | Código de tipo de moneda del monto total de anticipos del comprobante | 1 | an3 | Catálogo 02 |
|  | cbc:PayableAmount | Importe total de la venta, cesión en uso o del servicio prestado | 1 | n(12,2) |  |
|  | @currencyID | Código tipo de moneda del importe total de la venta, cesión en uso o del servicio prestado | 1 | an3 | Catálogo 02 |

## Página 8

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 7 ~

| Raíz | Nodo / Atributo | Dato | Cardinalidad UBL | Valor / Formato | Observaciones |
|---|---|---|---|---|---|
| /CreditNote/cac:CreditNoteLine | 1..n |  |  |  |  |
|  | cbc:ID | Número de orden del Ítem | 1 | n..3 |  |
|  | cbc:CreditedQuantity | Cantidad de unidades del ítem | 0..1 | n(12,10) |  |
|  | @unitCode | Unidad de medida del ítem | 0..1 | an..3 | Catálogo 03 |
|  | @unitCodeListID | - | 0..1 | UN/ECE rec 20 |  |
|  | @unitCodeListAgencyName | - | 0..1 | United Nations Economic Commission for Europe |  |
|  | cbc:LineExtensionAmount | Valor de venta del ítem | 1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del valor de venta del ítem | 1 | an3 | Catálogo 02 |
| /CreditNote/cac:CreditNoteLine/cac:PricingReference/cac:AlternativeConditionPrice | 0..n |  |  |  |  |
|  | cbc:PriceAmount | Precio de venta unitario / Valor referencial unitario en operaciones no onerosas | 1 | n(12,10) |  |
|  | @currencyID | Código de tipo de moneda del precio de venta unitario o valor referencial unitario | 1 | an3 | Catálogo 02 |
|  | cbc:PriceTypeCode | Código de tipo de precio | 0..1 | an2 | Catálogo 16 |
| /CreditNote/cac:CreditNoteLine/cac:TaxTotal | 0..n |  |  |  |  |
|  | cbc:TaxAmount | Monto de tributo del ítem | 1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto de tributo del ítem | 1 | an3 | Catálogo 02 |
| /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal | 0..n |  |  |  |  |
|  | cbc:TaxAmount | Monto de tributo del ítem | 1 | n(12,2) |  |
|  | @currencyID | Código de tipo de moneda del monto de tributo del ítem | 1 | an3 | Catálogo 02 |
| /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory | 1 |  |  |  |  |
|  | cbc:TaxExemptionReasonCode | Código de tipo de afectación del IGV | 0..1 | an2 | Catálogo 07 |
|  | cbc:TierRange | Código de tipo de sistema de ISC | 0..1 | an2 | Catálogo 08 |
| /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme | 1 |  |  |  |  |
|  | cbc:ID | Código de tributo | 0..1 | an4 | Catálogo 05 |
|  | cbc:Name | Nombre de tributo | 0..1 | an..6 | Catálogo 05 |
|  | cbc:TaxTypeCode | Código internacional tributo | 0..1 | an3 | Catálogo 05 |

## Página 9

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 8 ~

| Raíz | Nodo / Atributo | Dato | Cardinalidad UBL | Valor / Formato | Observaciones |
|---|---|---|---|---|---|
| /CreditNote/cac:CreditNoteLine/cac:Item | 1 |  |  |  |  |
|  | cbc:Description | Descripción detallada del servicio prestado, bien vendido o cedido en uso, indicando las características | 0..n | an..250 |  |
| /CreditNote/cac:CreditNoteLine/cac:Item/cac:SellersItemIdentification | 0..1 |  |  |  |  |
|  | cbc:ID | Código de producto del ítem | 1 | an..30 |  |
| /CreditNote/cac:CreditLine/cac:Item/cac:CommodityClassification | 0..1 |  |  |  |  |
|  | cbc:ItemClassificationCode | Código de producto (SUNAT) | 1 | n8 |  |
|  | @listID |  | 0..1 | UNSPSC |  |
|  | @listAgencyName |  | 0..1 | GS1 US |  |
|  | @listName |  | 0..1 | Item Classification |  |
| /CreditNote/cac:CreditLine/cac:Item/cac:AdditionalItemProperty | 0..n |  |  |  |  |
|  | cbc:Name | Nombre del concepto o elemento a consignar | 1 | an..100 |  |
|  | cbc:NameCode | Código del concepto o elemento a consignar | 0..1 | 8000 ó 8001 | Catálogo 55 |
|  | @listName | - | 0..1 | "SUNAT:Identificador de la propiedad del ítem" |  |
|  | @listAgencyName | - | 0..1 | "PE:SUNAT" |  |
|  | cbc:Value | Valor del concepto o elemento a consignar | 0..1 | an..100 |  |
| /CreditNote/cac:CreditLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod | 0..1 |  |  |  |  |
|  | cbc:StartDate | Fecha de inicio | 0..1 | yyyy-mm-dd |  |
| /CreditNote/cac:CreditNoteLine/cac:Price | 0..1 |  |  |  |  |
|  | cbc:PriceAmount | Valor unitario del ítem | 1 | n(12,10) |  |
|  | @currencyID | Código de tipo de moneda del valor unitario del ítem | 1 | an3 | Catálogo 02 |

## Página 10

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 9 ~

## 1.2 Estructura de Nota de crédito vs FormatoXML

### 1. Firma Digital

```xml
<ext:UBLExtensions>
<ext:UBLExtension>
<ext:ExtensionContent>
<ds:Signature Id="signatureKG">
<ds:SignedInfo>
<ds:CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments"/>
<ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#dsa-sha1"/>
<ds:Reference URI="">
<ds:Transforms>
<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
</ds:Transforms>
<ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
<ds:DigestValue>+pruib33lOapq6GSw58GgQLR8VGIGqANloj4EqB1cb4=</ds:DigestValue>
</ds:Reference>
</ds:SignedInfo>
<ds:SignatureValue>Oatv5xMfFInuGqiX9SoLDTy2yuLf0tTlMFkWtkdw1z/Ss6kiDz+vIgZhgKfIaxp+JbVy57 GT52f1
8D6+WMYZ0xOxTK2mojNkJNewwTTXzqOqrrAlObs9YoS5JAQAMi/TwkR4brNniU9tVwyybirHxw0H 
WVzN2bB43yQd9hOlXzRUYpC8/sXw78h7ME3E/zeu882aOFySOnHWB63imBQGcYBV+LIGR/JW8ER+ 
0VLMLatdwPVRbrWmz1/NIy5CWp1xWMaM6fC/9SXV0O1Lqopk0UeX2I2yuf05QhmVfjgUu6GnS3m6 
o6zM9J36iDvMVZyj7vbJTwI8SfWjTSNqxXlqPQ==</ds:SignatureValue>
<ds:KeyInfo>
<ds:X509Data>
<ds:X509Certificate>MIIF9TCCBN2gAwIBAgIGAK0oRTg/MA0GCSqGSIb3DQEBCwUAMFkxCzAJBgNVB...</ds:X509Certificate>
</ds:X509Data>
</ds:KeyInfo>
</ds:Signature>
</ext:ExtensionContent>
</ext:UBLExtension>
</ext:UBLExtensions>
<cac:Signature>
<cbc:ID>IDSignKG</cbc:ID>
<cac:SignatoryParty>
<cac:PartyIdentification>
<cbc:ID>20100113612</cbc:ID>
</cac:PartyIdentification>
<cac:PartyName>
<cbc:Name><![CDATA[K&G Laboratorios]]></cbc:Name>
</cac:PartyName>
</cac:SignatoryParty>
<cac:DigitalSignatureAttachment>
<cac:ExternalReference>
<cbc:URI>#signatureKG</cbc:URI>
</cac:ExternalReference>
</cac:DigitalSignatureAttachment>
</cac:Signature>
```

## Página 11

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 10 ~

### 2. Versión del UBL

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
```

### 3. Versión de la estructura del documento

```xml
<cbc:CustomizationID>2.0</cbc:CustomizationID>
```

### 4. Numeración, conformada por serie y número correlativo

```xml
<cbc:ID>FC02-10</cbc:ID>
```

### 5. Fecha de emisión

```xml
<cbc:IssueDate>2017-06-28</cbc:IssueDate>
```

### 6. Leyenda

Código interno generado por el software de facturación

```xml
<cbc:Note languageLocaleID="3000">05010020170628000785</cbc:Note>
```

### 7. Tipo de moneda en la cual se emite la nota de crédito electrónica

```xml
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
```

### 8. Código del tipo de nota de crédito electrónica

```xml
<cac:DiscrepancyResponse>
  <cbc:ReferenceID>F002-6</cbc:ReferenceID>
  <cbc:ResponseCode>07</cbc:ResponseCode>
</cac:DiscrepancyResponse>
```

### 9. Motivo o Sustento

```xml
<cac:DiscrepancyResponse>
  ...
  <cbc:Description><![CDATA[Devolución por ítem]]></cbc:Description>
</cac:DiscrepancyResponse>
```

### 10 y 11. Serie y número del documento que modifica / Tipo de documento del documento que modifica

```xml
<cac:BillingReference>
  <cac:CreditNoteDocumentReference>
    <cbc:ID>F002-6</cbc:ID>
    <cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
  </cac:CreditNoteDocumentReference>
</cac:BillingReference>
```

### 12 a 15. Documento de referencia

```xml
<cac:DespatchDocumentReference>
  <cbc:ID>031-002020</cbc:ID>
  <cbc:DocumentTypeCode>09</cbc:DocumentTypeCode>
</cac:DespatchDocumentReference>

<cac:AdditionalDocumentReference>
  <cbc:ID>10000120094</cbc:ID>
  <cbc:DocumentTypeCode>05</cbc:DocumentTypeCode>
</cac:AdditionalDocumentReference>
```

### 16 a 18. Datos del emisor

```xml
<cac:AccountingSupplierParty>
  <cac:Party>
    <cac:PartyTaxScheme>
      <cbc:RegistrationName><![CDATA[K&G Asociados S. A.]]></cbc:RegistrationName>
      <CompanyID 
        schemeID="6" 
        schemeName="SUNAT:Identificador de Documento de Identidad" 
        schemeAgencyName="PE:SUNAT" 
        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20100113612</CompanyID>
      <cac:RegistrationAddress>
        <cbc:AddressTypeCode>0001</cbc:AddressTypeCode>
      </cac:RegistrationAddress>
    </cac:PartyTaxScheme>
  </cac:Party>
</cac:AccountingSupplierParty>
```

## Página 12

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 11 ~

### 19 a 21. Datos del adquirente o usuario

```xml
<cac:Party>
  <cac:PartyTaxScheme>
    <cbc:RegistrationName><![CDATA[CECI FARMA IMPORT S.R.L.]]></cbc:RegistrationName>
    <cbc:CompanyID 
      schemeID="6" 
      schemeName="SUNAT:Identificador de Documento de Identidad" 
      schemeAgencyName="PE:SUNAT" 
      schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20102420706</cbc:CompanyID>
  </cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingCustomerParty>
```

### 22 a 26. Impuestos y totales

```xml
<cac:TaxTotal>
  <cbc:TaxAmount currencyID="PEN">2124.00</cbc:TaxAmount>
  <cac:TaxSubtotal>
    <cbc:TaxableAmount currencyID="PEN">11800.00</cbc:TaxableAmount>
    <cbc:TaxAmount currencyID="PEN">2124.00</cbc:TaxAmount>
    <cac:TaxCategory>
      <cbc:ID>S</cbc:ID>
      <cac:TaxScheme>
        <cbc:ID>1000</cbc:ID>
        <cbc:Name>IGV</cbc:Name>
        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
      </cac:TaxScheme>
    </cac:TaxCategory>
  </cac:TaxSubtotal>
  <cac:TaxSubtotal>
    <cbc:TaxableAmount currencyID="PEN">31250.00</cbc:TaxableAmount>
    <cbc:TaxAmount currencyID="PEN">1250.00</cbc:TaxAmount>
    <cac:TaxCategory>
      <cbc:ID>S</cbc:ID>
      <cac:TaxScheme>
        <cbc:ID>9999</cbc:ID>
        <cbc:Name>OTROS</cbc:Name>
        <cbc:TaxTypeCode>OTH</cbc:TaxTypeCode>
      </cac:TaxScheme>
    </cac:TaxCategory>
  </cac:TaxSubtotal>
</cac:TaxTotal>

<cac:LegalMonetaryTotal>
  <cbc:AllowanceTotalAmount currencyID="PEN">60.00</cbc:AllowanceTotalAmount>
  <cbc:PrepaidAmount currencyID="PEN">100.00</cbc:PrepaidAmount>
  <cbc:PayableAmount currencyID="PEN">1858.59</cbc:PayableAmount>
</cac:LegalMonetaryTotal>
```

### 30 a 33. Ítem

```xml
<cbc:ID>1</cbc:ID>
<cbc:CreditedQuantity 
  unitCode="CS"
  unitCodeListID="UN/ECE rec 20" 
  unitCodeListAgencyName="United Nations Economic Commission for Europe">50</cbc:CreditedQuantity>
<cbc:LineExtensionAmount currencyID="PEN">1439.48</cbc:LineExtensionAmount>
```

## Página 13

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 12 ~

### 34. Precio de venta unitario por item que modifica y código

```xml
<cac:PricingReference>
  <cac:AlternativeConditionPrice>
    <cbc:PriceAmount currencyID="PEN">34.99</cbc:PriceAmount>
    <cbc:PriceTypeCode>01</cbc:PriceTypeCode>
  </cac:AlternativeConditionPrice>
</cac:PricingReference>
```

### 35. Valor referencial unitario por ítem en operaciones no onerosas

```xml
<cac:PricingReference>
  <cac:AlternativeConditionPrice>
    <cbc:PriceAmount currencyID="PEN">250.00</cbc:PriceAmount>
    <cbc:PriceTypeCode>02</cbc:PriceTypeCode>
  </cac:AlternativeConditionPrice>
</cac:PricingReference>
```

### 36. Monto de tributo del ítem (IGV / ISC)

```xml
<cac:TaxTotal>
  <cbc:TaxAmount currencyID="PEN">259.11</cbc:TaxAmount>
  <cac:TaxSubtotal>
    <cbc:TaxableAmount currencyID="PEN">1439.50</cbc:TaxableAmount>
    <cbc:TaxAmount currencyID="PEN">259.11</cbc:TaxAmount>
    <cac:TaxCategory>
      <cbc:ID>S</cbc:ID>
      <cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
      <cac:TaxScheme>
        <cbc:ID>1000</cbc:ID>
        <cbc:Name>IGV</cbc:Name>
        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
      </cac:TaxScheme>
    </cac:TaxCategory>
  </cac:TaxSubtotal>
</cac:TaxTotal>

<cac:TaxTotal>
  <cbc:TaxAmount currencyID="PEN">400.00</cbc:TaxAmount>
  <cac:TaxSubtotal>
    <cbc:TaxableAmount currencyID="PEN">3333.33</cbc:TaxableAmount>
    <cbc:TaxAmount currencyID="PEN">400.00</cbc:TaxAmount>
    <cac:TaxCategory>
      <cbc:ID>S</cbc:ID>
      <cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
      <cac:TaxScheme>
        <cbc:ID>2000</cbc:ID>
        <cbc:Name>ISC</cbc:Name>
        <cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>
      </cac:TaxScheme>
    </cac:TaxCategory>
  </cac:TaxSubtotal>
</cac:TaxTotal>
```

### 37 a 40. Descripción, código y valor unitario

```xml
<cbc:Description><![CDATA[CAPTOPRIL 1000mg X 30]]></cbc:Description>

<cbc:SellersItemIdentification>
  <ID>Cap-258963</ID>
</cbc:SellersItemIdentification>

<cac:CommodityClassification>
  <ItemClassificationCode
    listID="UNSPSC" 
    listAgencyName="GS1 US"
    listName="Item Classification">51121703</ItemClassificationCode>
</cac:CommodityClassification>

<cbc:PriceAmount CurrencyID="PEN">785.20</cbc:PriceAmount>
```

## Página 14

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 13 ~

## 1.3 Normas de Uso del Formato de la Nota de Crédito Electrónica

### A. Normas de Uso

Como se ha indicado, el formato UBL está basado en el uso de un documento XML para  
presentar todos los datos en forma jerárquica. El formato especifica que para un archivo se  
especifique toda la información de una y solo una nota de crédito. Como dicha representación  
se basa en XML debe existir un único tag que engloba a todos los demás, dicha etiqueta es `CreditNote`.

```xml
<CreditNote>
......
</CreditNote>
```

Para un mejor entendimiento de la estructura del archivo XML, se describe a continuación los  
elementos que conforman la nota de crédito para el modelo Peruano, así como también los  
elementos complejos más importantes.

### A.1 Elementos de la Nota de Crédito

A continuación se detallan los elementos que forman parte del documento Nota de Crédito. En  
cada uno de ellos se indica una explicación de la información que almacena, si es obligatorio  
o no para que el documento sea correcto, su ubicación dentro del documento, un ejemplo y  
una breve explicación de acuerdo al estándar UBL.

En la descripción UBL, para una mejor comprensión de los elementos de datos, se describen  
solo aquellos tags que son necesarios para el uso tributario y que son requeridos por la  
administración.

## Página 15

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 14 ~

## 1. Firma Digital

**Obligatorio.**

Es el conjunto de datos asociados al documento electrónico que se firma y permite la  
identificación del signatario (emisor de la factura electrónica) y ha sido creada por medios  
que éste mantiene bajo su control, de manera que está vinculada únicamente al signatario  
y a los datos a los que refiere.

La firma deberá realizarse con el certificado digital que el emisor de la nota de crédito  
comunicó previamente a SUNAT.

La firma se consignará en dos contenedores que corresponden a tipos complejos. Estos son  
la firma digital de acuerdo a UBL y un componente de extensión.

### Ubicación

- `//CreditNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature`
- `//CreditNote/cac:Signature`

### Ejemplo

Un ejemplo de declaración de firma electrónica en el contenedor `UBLExtensions` sería:

```xml
<ext:UBLExtensions>
<ext:UBLExtension>
<ext:ExtensionContent>
<ds:Signature Id="signatureKG">
...
</ds:Signature>
</ext:ExtensionContent>
</ext:UBLExtension>
</ext:UBLExtensions>
```

## Página 16

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 15 ~

Un ejemplo de declaración de firma electrónica en el contenedor `cac:Signature` sería:

```xml
<cac:Signature>
<cbc:ID>IDSignKG</cbc:ID>
<cac:SignatoryParty>
<cac:PartyIdentification>
<cbc:ID>20100113612</cbc:ID>
</cac:PartyIdentification>
<cac:PartyName>
<cbc:Name><![CDATA[K&G Laboratorios]]></cbc:Name>
</cac:PartyName>
</cac:SignatoryParty>
<cac:DigitalSignatureAttachment>
<cac:ExternalReference>
<cbc:URI>#signatureKG</cbc:URI>
</cac:ExternalReference>
</cac:DigitalSignatureAttachment>
</cac:Signature>
```

### Descripción UBL

- **UBLExtensions.** Contenedor de Componentes de extensión. Se incorporan definiciones estructuradas cuando sean de interés conjunto para emisores y receptores, y no estén ya definidas en el esquema de la nota de crédito. Se detalla más adelante (punto B.2.1).
- Se utilizará el componente Extensions de UBL 2.1 para incorporar la firma electrónica XMLDSIG1.
- **cac:Signature.** Utilizado para identificar al firmante y otro tipo de información relacionada con el mismo.
  - `cbc:ID`. Obligatorio. Identificador de la firma.
  - `cac:SignatoryParty`. Obligatorio. Asociación con la parte firmante.
  - `PartyIdentification`. Obligatorio. A través del elemento ID, se consigna el RUC de la parte firmante.
  - `PartyName`. Obligatorio. A través del elemento Name, se consigna el nombre o razón social de la parte firmante.

## Página 17

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

- `cac:DigitalSignatureAttachment`. Obligatorio. En este componente se puede referenciar la firma del documento como una `ExternalReference` a una URI local o remota.
  - `ExternalReference`. Obligatorio. Información acerca de un documento vinculado. En este caso será una referencia dentro del mismo documento `Creditnote`, específicamente en el componente `UBLExtensions`.

## 2. Versión del UBL

**Obligatorio.** Versión del esquema UBL que define todos los elementos que se podrían  
encontrar en este documento. Para el caso peruano se ha utilizado la versión “2.1”.

### Ubicación

`//CreditNote/cbc:UBLVersionID`

### Ejemplo

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
```

## 3. Versión de la estructura del documento

**Obligatorio.** Identifica una personalización de UBL definida para un uso específico. Para  
nuestro caso corresponderá a la versión 2.0 de la nota de crédito electrónica.

### Ubicación

`//CreditNote/cbc:CustomizationID`

### Ejemplo

```xml
<cbc:CustomizationID>2.0</cbc:CustomizationID>
```

## Página 18

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 4. Numeración, conformada por serie y número correlativo

**Obligatorio.** Identificador de documento. Para el caso peruano este elemento contendrá serie  
de la nota de crédito y el número correlativo de la misma.

La serie será alfanumérica compuesta por cuatro caracteres:

- Si la nota modifica una factura, el primer carácter de la izquierda debe iniciar en `F`.
- Si la nota modifica una boleta de venta, el primer carácter de la izquierda debe iniciar en `B`.

Esta numeración será independiente del número correlativo de las notas de crédito emitidas en  
formato impreso y/o importado por imprenta autorizada.

### Ubicación

`//CreditNote/cbc:ID`

### Ejemplo

```xml
<cbc:ID>FC01-10</cbc:ID>
```

## 5. Fecha de emisión

**Obligatorio.**

Corresponde a la fecha del mes en que se produzcan las rectificaciones, devoluciones o  
anulaciones de las operaciones originales.

### Ubicación

`//CreditNote/cbc:IssueDate`

### Ejemplo

```xml
<cbc:IssueDate>2011-06-28</cbc:IssueDate>
```

## Página 19

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 6. Leyendas

Elemento utilizado para consignar la siguiente leyenda:

- Código interno generado por el software de Facturación.

Se consignará la llave única o clave única o clave primaria del software donde se generó el  
ingreso de información para la generación del comprobante de pago electrónico.

En el atributo `@languageLocaleID` se debe consignar el código `3000` (según Catálogo No. 52).

### Ubicación

`//CreditNote/cbc:Note@languageLocaleID`

### Ejemplo

```xml
<CreditNote>
…
<cbc:Note languageLocaleID="3000">05010020170428000005</cbc:Note>
…
</CreditNote>
```

## 7. Tipo de moneda en la cual se emite la nota de crédito electrónica

**Obligatorio.** Código de moneda empleada genéricamente en el documento y que debe ser  
igual al tipo de moneda del o los comprobantes de pago que se modifican.

### Ubicación

`//CreditNote/cbc:DocumentCurrencyCode`

### Ejemplo

```xml
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
```

## Página 20

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 8. Código del tipo de Nota de crédito electrónica

**Obligatorio.** Motivo por el cual se emite la nota de crédito. Para el caso peruano el elemento  
`cbc:ResponseCode` podrá adoptar los siguientes valores:

1. Anulación de la operación.  
2. Anulación por error en el RUC.  
3. Corrección por error en la descripción.  
4. Descuento global.  
5. Descuento por Item.  
6. Devolución total.  
7. Devolución parcial.  
8. Bonificación.  
9. Disminución en el valor.

### Ubicación

`//CreditNote/cac:DiscrepancyResponse/cbc:ResponseCode`

### Ejemplo

```xml
<cac:DiscrepancyResponse>
<cbc:ReferenceID>F002-6</cbc:ReferenceID>
<cbc:ResponseCode>07</cbc:ResponseCode>
<cbc:Description><![CDATA[Devolucion por deterioro del producto]]></cbc:Description>
</cac:DiscrepancyResponse>
```

## Página 21

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### Descripción UBL `cac:DiscrepancyResponse`

Contiene el tipo de nota de crédito, el sustento de la emisión de la nota de crédito, así como la  
identificación de la factura o boleta de venta electrónica afectada.

- `cbc:ReferenceID`: Obligatorio. Identifica al documento modificado por la nota de crédito.
- `cbc:ResponseCode`: Obligatorio. Código del tipo de nota de crédito.
- `cbc:Description`: Obligatorio. Sustento o descripción del motivo de la nota de crédito.

## 9. Motivo o Sustento

**Obligatorio.** Elemento usado para describir el motivo o sustento de la emisión de la nota de  
crédito.

### Ubicación

`//CreditNote/cac:DiscrepancyResponse/cbc:Description`

## 10. Serie y número del documento que modifica

**Obligatorio.** Asocia la nota de crédito al comprobante de pago modificado.

### Ubicación

`//CreditNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:ID`

### Ejemplo

```xml
<cac:BillingReference>
<cac:InvoiceDocumentReference>
<cbc:ID>F001-2</cbc:ID>
<cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
</cac:InvoiceDocumentReference>
</cac:BillingReference>
```

## Página 22

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### Descripción UBL `cac:BillingReference`

Obligatorio. Tipo complejo que contiene los siguientes elementos:

- `cac:InvoiceDocumentReference`. Obligatorio. Identificación del documento afectado por la nota de crédito.
  - `cbc:ID`: Obligatorio. Identificación del número del documento modificado.
  - `cbc:DocumentTypeCode`: Opcional. Identificación del código del tipo de documento. Por defecto:
    - `01` - Factura Comercial
    - `03` - Boleta de Venta
    - `12` - Ticket de máquina registradora

## 11. Tipo de documento del documento que modifica

**Obligatorio.** Se consigna el tipo de comprobante de pago. Se utilizará el Catálogo No. 01.

### Ubicación

`//CreditNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:DocumentTypeCode`

## 12. Documento de referencia

**Opcional.** Referencia a las guías de remisión remitente o transportista, según corresponda,  
autorizado por la SUNAT para sustentar el traslado de los bienes. También referencia a  
cualquier otro documento asociado a la nota de crédito.

## Página 23

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

Para estos casos se utilizará el Catálogo No. 12: “Códigos - Documentos Relacionados Tributarios”.

### Ubicación

a) En el caso de Guías de Remisión:

- `//CreditNote/cac:DespatchDocumentReference/cbc:ID`
- `//CreditNote/cac:DespatchDocumentReference/cbc:DocumentTypeCode`

b) En el caso de Otros documentos relacionados:

- `//CreditNote/cac:AdditionalDocumentReference/cbc:ID`
- `//CreditNote/cac:AdditionalDocumentReference/cbc:DocumentTypeCode`

### Ejemplo

```xml
<cac:DespatchDocumentReference>
<cbc:ID>0001-002020</cbc:ID>
<cbc:DocumentTypeCode>09</cbc:DocumentTypeCode>
</cac:DespatchDocumentReference>

<cac:AdditionalDocumentReference>
<cbc:ID>F092-024099</cbc:ID>
<cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
</cac:AdditionalDocumentReference>
```

## Página 24

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### Descripción UBL

- **`cac:DespatchDocumentReference`**: hace referencia a documentos de transporte asociados a la nota de crédito.
  - `cbc:ID`: Obligatorio. Identificación del número de guía autorizado por SUNAT.
  - `cbc:DocumentTypeCode`: Obligatorio. Corresponde al código del tipo de documento al que se hace referencia (Catálogo No. 01).
- **`cac:AdditionalDocumentReference`**: hace referencia a documentos asociados a la nota de crédito.
  - `cbc:ID`: Obligatorio. Identificación del número de documento asociado.
  - `cbc:DocumentTypeCode`: Obligatorio. Corresponde al código del tipo de documento (Catálogo No. 12).

## 13. Apellidos y nombres o denominación o razón social

**Obligatorio.** Corresponde a los apellidos y nombres o denominación o razón social del emisor  
de la nota de crédito electrónica.

### Ubicación

`//CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:RegistrationName`

### Ejemplo

```xml
<cac:AccountingSupplierParty>
<cac:Party>
<cac:PartyTaxScheme>
…
<cbc:RegistrationName><![CDATA[K&G Asociados S. A.]]></cbc:RegistrationName>
…
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingSupplierParty>
```

## Página 25

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 14. Tipo y Número de RUC del Emisor

**Obligatorio.** El tipo de documento del emisor siempre es 6, que corresponde al RUC.

### Ubicación

`//CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID @schemeID @schemeName @schemeAgencyName @schemeURI`

### Atributos

- `schemeName`: `"SUNAT:Identificador de Documento de Identidad"`
- `schemeAgencyName`: `"PE:SUNAT"`
- `schemeURI`: `"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"`

### Valor de Códigos Catálogo N° 06

| Código | Concepto |
|---|---|
| 6 | REG. UNICO DE CONTRIBUYENTES |

### Ejemplo

```xml
<cac:AccountingSupplierParty>
<cac:Party>
<cac:PartyTaxScheme>
…
<cbc:CompanyID schemeID="6" schemeName="SUNAT:Identificador de Documento de Identidad"
schemeAgencyName="PE:SUNAT"
schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20100113612</cbc:CompanyID>
…
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingSupplierParty>
```

## Página 26

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 15. Código del domicilio fiscal o de local anexo del emisor

Corresponde informar el código del establecimiento donde se está realizando la venta de los bienes.

### Ubicación

`//CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cac:RegistrationAddress/cbc:AddressTypeCode`

### Descripción UBL

`cac:AddressTypeCode`: Código de cuatro dígitos asignado por SUNAT, que identifica al establecimiento anexo. Tratándose del domicilio fiscal y en el caso de no poder determinar el lugar de la venta, informar `0000`.

## 16. Apellidos y nombres o denominación o razón social del adquirente o usuario

**Obligatorio.** Corresponde a los apellidos y nombres o denominación o razón social del adquirente o usuario.

### Ubicación

`/CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme/cbc:RegistrationName`

## Página 27

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 17. Tipo y número de documento de identidad del adquirente o usuario

**Obligatorio.** El tipo de documento será de acuerdo al Catálogo N° 06 del anexo N° 8.

### Ubicación

`/CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID @schemeID @schemeName @schemeAgencyName @schemeURI`

### Atributos

- `schemeName`: `"SUNAT:Identificador de Documento de Identidad"`
- `schemeAgencyName`: `"PE:SUNAT"`
- `schemeURI`: `"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"`

### Valor de Códigos Catálogo N° 06

| Código | Concepto |
|---|---|
| 0 | DOC.TRIB.NO.DOM.SIN.RUC |
| 1 | DOC. NACIONAL DE IDENTIDAD |
| 4 | CARNET DE EXTRANJERIA |
| 6 | REG. UNICO DE CONTRIBUYENTES |
| 7 | PASAPORTE |
| A | CED. DIPLOMATICA DE IDENTIDAD |
| B | DOC.IDENT.PAIS.RESIDENCIA-NO.D |
| C | Tax Identification Number - TIN – Doc Trib PP.NN |
| D | Identification Number - IN – Doc Trib PP. JJ |

## Página 28

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 18. Monto Total de Impuestos

Corresponde al importe total de impuestos ISC, IGV e IVAP de corresponder.

### Ubicación

`//CreditNote/cac:TaxTotal/cbc:TaxAmount`

## 19. Total valor de venta - operaciones gravadas

Este elemento es usado solo si al menos una línea de ítem está gravada con el IGV.

### Ubicación

`/CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

### Ejemplo

```xml
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">59210.65</cbc:TaxAmount>
…
</cac:TaxTotal>
<cac:TaxTotal>
…
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">8560.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">1540.80</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:ID>S</cbc:ID>
<cac:TaxScheme>
<cbc:ID>1000</cbc:ID>
<cbc:Name>IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
…
</cac:TaxTotal>
```

## Página 29

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### `cac:TaxCategory`

Valor de Códigos `cbc:ID` Catálogo N° 05:

| Código | Descripción |
|---|---|
| S | IGV |

### `cac:TaxScheme`

- `cbc:ID`: `1000`
- `cbc:Name`: `IGV`
- `cbc:TaxTypeCode`: `VAT`

## 20. Total valor de venta - operaciones inafectas

Este elemento es usado solo si al menos una línea de ítem se encuentra inafecta al IGV.

### Ubicación

`/CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

### Ejemplo

```xml
<cac:TaxTotal>
…
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">320.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">0.00</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:ID>O</cbc:ID>
<cac:TaxScheme>
<cbc:ID>9998</cbc:ID>
<cbc:Name> INAFECTO</cbc:Name>
<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
…
</cac:TaxTotal>
```

## Página 30

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

Para operaciones inafectas:

- `cbc:TaxAmount` se consigna como `0.00`
- Categoría (`cbc:ID`): `O`
- Código de tributo (`cbc:ID`): `9998`
- Nombre: `INAFECTO`
- `cbc:TaxTypeCode`: `FRE`

## 21. Total valor de venta - operaciones exoneradas

Este elemento es usado solo si al menos una línea de ítem se encuentra exonerada al IGV.

### Ubicación

`/CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

## Página 31

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

Para operaciones exoneradas:

- Categoría (`cbc:ID`): `E`
- Código de tributo (`cbc:ID`): `9997`
- Nombre: `EXONERADO`
- `cbc:TaxTypeCode`: `FRE` (en el texto también aparece ejemplo con `VAT`)

### Ejemplo

```xml
<cac:TaxTotal>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">8560.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">0.00</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:ID>E</cbc:ID>
<cac:TaxScheme>
<cbc:ID>9997</cbc:ID>
<cbc:Name> EXONERADO</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
```

## 22. Sumatoria de IGV

Corresponde a la sumatoria del IGV del ajuste realizado con la nota de crédito.

## Página 32

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 23. Sumatoria de ISC

Corresponde a la sumatoria del ISC del ajuste realizado con la nota de crédito.

- Código: `2000`
- Nombre: `ISC`
- Tipo: `EXC`

### Ejemplo

```xml
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">8745.00</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">43725.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">8745.00</cbc:TaxAmount>
<cac:TaxCategory>
<cac:TaxScheme>
<cbc:ID>2000</cbc:ID>
<cbc:Name>ISC</cbc:Name>
<cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
```

## Página 33

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 24. Sumatoria de Otros Tributos

- Código: `9999`
- Nombre: `OTROS`
- Tipo: `OTH`

### Ejemplo

```xml
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">39000.0</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">97500.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">39000.0</cbc:TaxAmount>
<cac:TaxCategory>
<cac:TaxScheme>
<cbc:ID>9999</cbc:ID>
<cbc:Name>OTROS</cbc:Name>
<cbc:TaxTypeCode>OTH</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
```

## 25. Total de Descuentos

Ubicación: `//CreditNote/cac:LegalMonetaryTotal/cbc:AllowanceTotalAmount`

## 26. Importe total de la venta, de la cesión en uso o del servicio prestado

Se calcula como la suma de:

- Total valor de venta - operaciones gravadas
- Total valor de venta - operaciones inafectas
- Total valor de venta - operaciones exoneradas
- Sumatoria IGV
- Sumatoria ISC
- Sumatoria otros tributos

Ubicación: `//CreditNote/cac:LegalMonetaryTotal/cbc:PayableAmount`

## Página 34

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 27. Número de orden del Ítem

Ubicación: `//CreditNote/cac:CreditNoteLine/cbc:ID`

## 28. Cantidad de unidades por ítem

Ubicación: `/CreditNote/cac:CreditNoteLine/cbc:CreditedQuantity @unitCode @unitCodeListID @unitCodeListAgencyName`

### Atributos

- `unitCode`: Catálogo N° 3
- `unitCodeListID`: `"UN/ECE rec 20"`
- `unitCodeListAgencyName`: `"United Nations Economic Commission for Europe"`

### Códigos frecuentes

| Código | Descripción |
|---|---|
| NIU | UNIDAD (BIENES) |
| ZZ | UNIDAD (SERVICIOS) |

### Ejemplo

```xml
<cbc:CreditedQuantity unitCode="CS" unitCodeListID="UN/ECE rec 20"
unitCodeListAgencyName="United Nations Economic Commission for
Europe">50</cbc:CreditedQuantity>
```

## Página 35

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 29. Valor de venta por ítem

Es el producto de la cantidad por el valor unitario y la deducción de los descuentos aplicados a dicho ítem.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cbc:LineExtensionAmount @currencyID`

## 30. Precio de Venta unitario por ítem que modifica y código

Para identificar este valor, se debe consignar el código `01` (Catálogo No. 16).

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:PricingReference/cac:AlternativeConditionPrice @currencyID`

### Ejemplo

```xml
<cbc:LineExtensionAmount currencyID="PEN">172890.0</cbc:LineExtensionAmount>
<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">18.75</cbc:PriceAmount>
<cbc:PriceTypeCode>01</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>
```

## Página 36

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### `cac:PriceTypeCode`

| Código | Descripción |
|---|---|
| 01 | Precio unitario (incluye el IGV) |
| 02 | Valor referencial unitario en operaciones no onerosas |

## 31. Valor unitario por ítem en operaciones no onerosas y código

Cuando la transferencia se efectúe gratuitamente, se consignará el valor de venta unitario que hubiera correspondido en operaciones onerosas con terceros.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:PricingReference/cac:AlternativeConditionPrice`

### Ejemplo

```xml
<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">18.75</cbc:PriceAmount>
<cbc:PriceTypeCode>02</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>
```

## 32. Afectación al IGV del ítem que modifica

Ubicación: `//CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode`

## Página 37

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 33. Afectación al ISC del ítem que modifica

Indica el sistema que fue utilizado para determinar la base imponible cuando el bien está gravado con el ISC.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TierRange`

### Ejemplo

```xml
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">26361.55</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">146453.06</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">26361.55</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
<cac:TaxScheme>
<cbc:ID>1000</cbc:ID>
<cbc:Name>IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">8745.00</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">72875.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">8745.00</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:TierRange>02</cbc:TierRange>
<cac:TaxScheme>
<cbc:ID>2000</cbc:ID>
<cbc:Name>ISC</cbc:Name>
<cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
```

## Página 38

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 34. Descripción detallada del servicio prestado, bien vendido o cedido en uso

Se deberá colocar el número de serie y/o número de motor, si se trata de un bien identificable, de corresponder.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:Item/cbc:Description`

## 35. Código Producto

Código del producto de acuerdo al tipo de codificación interna que se utilice.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:Item/cac:SellersItemIdentification/cbc:ID`

### Ejemplo

```xml
<cac:Item>
<cbc:Description><![CDATA[CAPTOPRIL 25mg X 30]]></cbc:Description>
…
</cac:Item>
```

## Página 39

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 36. Código Producto de SUNAT

Código del producto de acuerdo al estándar internacional denominado UNSPSC.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:Item/cac:CommodityClassification/cbc:ItemClassificationCode`

## Página 40

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### Atributos de `cbc:ItemClassificationCode`

- `listID`: `"UNSPSC"`
- `listAgencyName`: `"GS1 US"`
- `listName`: `"Item Classification"`

### Ejemplos de códigos

| Código | Descripción |
|---|---|
| 43233201 | Software de Servicios de Autenticación |
| 80101511 | Servicio de asesoramiento en recursos humanos |
| 50201706 | Café |

### Ejemplo

```xml
<cac:Item>
…
<cac:CommodityClassification>
<cbc:ItemClassificationCode listID="UNSPSC" listAgencyName="GS1 US" listName="Item Classification">51121703</cbc:ItemClassificationCode>
</cac:CommodityClassification>
…
</cac:Item>
```

## Página 41

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 37. Valor unitario del ítem

**Obligatorio.** Se consignará el importe correspondiente al valor o monto unitario del bien vendido, cedido o servicio prestado, indicado en una línea o ítem de la factura.

### Ubicación

`//CreditNote/cac:CreditNoteLine/cac:Price/cbc:PriceAmount`

### Ejemplo

```xml
<CreditNote>
…
<cac:Price>
<cbc:PriceAmount currencyID="PEN">678.0</cbc:PriceAmount>
</cac:Price>
…
</CreditNote>
```

## Página 42

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## B.2 Detalle de elementos complejos

En esta sección se describe aquellos tag que por su complejidad requieren de una mayor explicación.

## B.2.1 Tag UBLExtension

Uno o más `<ext:UBLExtension>` están contenidos dentro de un elemento `<ext:UBLExtensions>` descendiente directo del elemento raíz del documento. Estos elementos están disponibles en UBL 2.1 para la inclusión de datos no UBL.

Se hará uso de este tipo de componente de extensión para especificar solamente la firma digital.

### 1. `ext:UBLExtension/ext:ExtensionContent/ds:Signature`

No es objeto de este informe especificar el tipo de firma que se utilizará en el contexto de la factura electrónica, sin embargo se sientan las bases para declarar un certificado y se tomará como ejemplo una firma sencilla XMLdSig.

- `ExtensionContent`: aquí se incluyen las firmas XMLDSig de todos los firmantes del documento.
- La firma se realizará sobre el documento completo.
- Se utilizará para firmar la clave privada de un certificado digital X509 válido no vencido.

## Página 43

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

El atributo `Id` es opcional pero es muy útil para identificar la firma dentro de un documento, sobre todo cuando se trabaja con firmas múltiples.

Ejemplo:

```xml
<ds:Signature Id="signatureKG">
```

### i. `ds:SignedInfo`

Este elemento puede dividirse en dos partes:

1. Información sobre el valor de la firma.
2. Información sobre los datos a firmar.

#### 1. `ds:CanonicalizationMethod`

Posee un atributo `Algorithm` que indica cómo se debe transformar a forma canónica el elemento `<ds:SignedInfo>` antes de realizar la firma.

## Página 44

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### 2. `ds:SignatureMethod`

Especifica qué tipo de algoritmo de firma se utilizará para obtener la firma.

### 3. `ds:Reference`

Cada elemento `Reference` incluye el hash de un objeto de datos y las transformaciones aplicadas a ese objeto para producir dicho hash.

- `ds:Transforms`: opcional; lista de transformaciones aplicadas.
- `ds:DigestMethod`: define la función hash utilizada.
- `ds:DigestValue`: valor hash codificado en Base64.

### ii. `ds:SignatureValue`

Contiene la firma codificada en Base64.

## Página 45

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

### iii. `ds:KeyInfo`

Es una estructura opcional que identifica al firmante. Su contenido suele utilizarse en procesos de verificación de firmas.

1. `ds:X509Data`: contiene información del certificado firmante.
2. `ds:KeyValue`: contiene información de la clave pública.

En caso de no incluir la estructura `<ds:KeyInfo>`, la firma no podría considerarse como “Firma Electrónica Avanzada” puesto que el firmante no podría ser identificado.

## Página 46

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## 1.5 Ejemplos de casos identificados

### A. Nota de Crédito sobre Factura

La empresa **Soporte Tecnológicos EIRL**, cuyo nombre comercial es **“Tu soporte”**, identificada con RUC **20100454523**, debe emitir la nota de crédito electrónica **N° FC01-211** con la siguiente información:

- Fecha de Emisión: 25 de junio del 2017
- Adquirente o Usuario: Servicabinas S.A.
- RUC: 20587896411
- Motivo: Devolución de unidades defectuosas
- Código de Software de Facturación: 0501002017062500125
- Código de Establecimiento Anexo: 0001

### Devolución de mercadería vendida

| Código | Código SUNAT | Unidad de Medida | Cantidad | Descripción | Afectación al IGV | Precio Unitario |
|---|---|---|---|---|---|---|
| GLG199 | 45111723 | Unidad | 100 | Grabadora LG Externo Modelo:GE20LU10 | Gravado | 98.00 |

| Valor Unitario (1) | Cantidad | Valor venta item | IGV | Importe Total |
|---|---|---|---|---|
| 83.05 | 100 | 8305.08 | 1,494.92 | 9,799.99 |

(1) s/. 98 / 1.18 = 83.05

## Página 47

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito electrónica Versión 2.0 ~ 40 ~

## Caso 1

| Requisito | Caso 1 |
|---|---|
| Fecha de emisión | 25/06/2017 |
| Firma Digital |  |
| Apellidos y nombres o denominación o razón social | Soporte Tecnológicos EIRL |
| Nombre Comercial | “Tu Soporte” |
| Número de RUC | 20100454523 |
| Código del tipo de Nota de crédito electrónica | 07 |
| Numeración, conformada por serie y número correlativo | FC01-211 |
| Tipo y número de documento de identidad del adquirente o usuario | 20587896411 |
| Apellidos y nombres o denominación o razón social del adquirente o usuario | Servicabinas S.A. |
| Motivo o sustento | Unidades defectuosas, no leen CD que contengan archivos MP3 |
| Número de orden del Ítem | 1 |
| Unidad de medida por ítem que modifica | NIU |
| Cantidad de unidades por ítem que modifica | 100 |
| Código de producto | GLG199 |
| Código de producto SUNAT | 45111723 |
| Descripción detallada del bien vendido o cedido en uso, descripción o tipo de servicio prestado por ítem | Grabadora LG Externo Modelo: GE20LU10 |
| Valor unitario por ítem que modifica | 83.05 |
| Precio de venta unitario por ítem que modifica | 98.00 |
| Afectación al IGV por ítem que modifica | 10 |
| IGV del ítem | 1,494.92 |
| Sistema de ISC por ítem que modifica |  |
| Valor de venta por ítem | 8,305.08 |
| Total valor de venta - operaciones gravadas | 8,305.08 |
| Total valor de venta - operaciones inafectas |  |
| Total valor de venta - operaciones exoneradas |  |
| Sumatoria IGV | 1,494.92 |
| Sumatoria ISC |  |
| Sumatoria otros tributos |  |
| Sumatoria otros Cargos |  |
| Total descuentos |  |
| Importe total | 9,799.99 |
| Versión del UBL |  |
| Versión de la estructura del documento |  |
| Tipo de moneda en la cual se emite la nota de crédito | PEN |
| Serie y número del documento que modifica | F001-4355 |
| Tipo de documento que modifica | 01 |
| Documento de referencia |  |
| Código de Establecimiento | 0001 |

## Página 48

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 47 ~

```xml
<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<CreditNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"
xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
xmlns:ccts="urn:un:unece:uncefact:documentation:2"
xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"
xmlns:qdt="urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2"
xmlns:sac="urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1"
xmlns:udt="urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<ext:UBLExtensions>
<ext:UBLExtension>
<ext:ExtensionContent>
<ds:Signature Id="SignST">
<ds:SignedInfo>
<ds:CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315"/>
<ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
<ds:Reference URI="">
<ds:Transforms>
<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#envelopedsignature"/>
</ds:Transforms>
<ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
<ds:DigestValue>uy0/8Pg/62e+GIQ0ZRVRRCWmPBk=</ds:DigestValue>
</ds:Reference>
</ds:SignedInfo>
<ds:SignatureValue>...</ds:SignatureValue>
<ds:KeyInfo>
<ds:X509Data>
<ds:X509SubjectName>CN=Juan Robles, ... , C=PE</ds:X509SubjectName>
<ds:X509Certificate>MIIESTCCAzGgAwIBAgIKWOCRzgAAAAAAIjANBgkq...</ds:X509Certificate>
</ds:X509Data>
</ds:KeyInfo>
</ds:Signature>
</ext:ExtensionContent>
</ext:UBLExtension>
</ext:UBLExtensions>
```

## Página 49

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 48 ~

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
<cbc:CustomizationID>2.0</cbc:CustomizationID>
<cbc:ID>FC01-211</cbc:ID>
<cbc:IssueDate>2017-03-25</cbc:IssueDate>
<cbc:IssueTime>20:25:41</cbc:IssueTime>
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
<cbc:Note languageLocaleID="3000">0501002017062500125</cbc:Note>
<cac:DiscrepancyResponse>
<cbc:ReferenceID>FC01-4355</cbc:ReferenceID>
<cbc:ResponseCode>07</cbc:ResponseCode>
<cbc:Description>Unidades defectuosas, no leen CD que contengan archivos
MP3</cbc:Description>
</cac:DiscrepancyResponse>
<cac:BillingReference>
<cac:InvoiceDocumentReference>
<cbc:ID>FC01-4355</cbc:ID>
<cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
</cac:InvoiceDocumentReference>
</cac:BillingReference>
<cac:Signature>
<cbc:ID>IDSignST</cbc:ID>
<cac:SignatoryParty>
<cac:PartyIdentification>
<cbc:ID>20100454523</cbc:ID>
</cac:PartyIdentification>
<cac:PartyName>
<cbc:Name>SOPORTE TECNOLOGICOS EIRL</cbc:Name>
</cac:PartyName>
</cac:SignatoryParty>
<cac:DigitalSignatureAttachment>
<cac:ExternalReference>
<cbc:URI>#SignatureSP</cbc:URI>
</cac:ExternalReference>
</cac:DigitalSignatureAttachment>
</cac:Signature>
<cac:AccountingSupplierParty>
<cac:Party>
<cac:PartyName>
<cbc:Name>Tu Soporte</cbc:Name>
</cac:PartyName>
<cac:PartyTaxScheme>
<cbc:RegistrationName><![CDATA[Soporte Tecnológicos EIRL]]></cbc:RegistrationName>
<CompanyID schemeID="6" schemeName="SUNAT:Identificador de Documento de
Identidad" schemeAgencyName="PE:SUNAT"
schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20100454523</CompanyID>
<cac:RegistrationAddress>
<cbc:AddressTypeCode>0001</cbc:AddressTypeCode>
</cac:RegistrationAddress>
<cac:TaxScheme>
<cbc:ID>-</cbc:ID>
</cac:TaxScheme>
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingSupplierParty>
<cac:AccountingCustomerParty>
<cac:Party>
<cac:PartyTaxScheme>
<cbc:RegistrationName>Servicabinas S.A.</cbc:RegistrationName>
<CompanyID schemeID="6" schemeName="SUNAT:Identificador de Documento de
Identidad" schemeAgencyName="PE:SUNAT"
schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20587896411</CompanyID>
```

## Página 50

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Crédito Electrónica ~ 49 ~

```xml
<cac:TaxScheme>
<cbc:ID>-</cbc:ID>
</cac:TaxScheme>
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingCustomerParty>
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">1494.92</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">8305.08</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">1494.92</cbc:TaxAmount>
<cac:TaxCategory>
<cac:TaxScheme>
<cbc:ID schemeID="UN/ECE 5153" schemeAgencyID="6">1000</cbc:ID>
<cbc:Name>IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
<cac:LegalMonetaryTotal>
<cbc:PayableAmount currencyID="PEN">8379.00</cbc:PayableAmount>
</cac:LegalMonetaryTotal>
<cac:CreditNoteLine>
<cbc:ID>1</cbc:ID>
<cbc:CreditedQuantity unitCode="NIU">100</cbc:CreditedQuantity>
<cbc:LineExtensionAmount currencyID="PEN">8305.08</cbc:LineExtensionAmount>
<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">98.00</cbc:PriceAmount>
<cbc:PriceTypeCode>01</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">1494.92</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">8305.08</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">1494.92</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
<cac:TaxScheme>
<cbc:ID>1000</cbc:ID>
<cbc:Name>IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
<cac:Item>
<cbc:Description>Grabadora LG Externo Modelo: GE20LU10</cbc:Description>
<cac:SellersItemIdentification>
<cbc:ID>GLG199</cbc:ID>
</cac:SellersItemIdentification>
<cac:CommodityClassification>
<cbc:ItemClassificationCode listID="UNSPSC" listAgencyName="GS1 US"
listName="Item Classification">45111723</cbc:ItemClassificationCode>
</cac:CommodityClassification>
</cac:Item>
<cac:Price>
<cbc:PriceAmount currencyID="PEN">83.05</cbc:PriceAmount>
</cac:Price>
</cac:CreditNoteLine>
</CreditNote>
```
