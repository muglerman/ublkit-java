# Guía de Elaboración de Documentos XML - Nota de Débito Electrónica UBL 2.1

## Página 1

Guía de Elaboración de  
Documentos XML  
Nota de Débito Electrónica  
UBL 2.1  
PROYECTO DE COMPROBANTES DE PAGO  
ELECTRONICOS  
Versión 1.0  
Mayo 2017

## Página 2

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 1 ~

INDICE

1 NOTA DE DÉBITO ELECTRONICA................................................................ 3  
1.1 Requisitos de la Nota de Débito electrónica.................................................... 3  
1.2 Estructura de Nota de débito vs FormatoXML................................................. 9  
1.3 Normas de Uso del Formato de la Nota de Débito Electrónica...................... 13  
1. Firma Digital ...................................................................................................... 14  
2. Versión del UBL ................................................................................................ 16  
3. Versión de la estructura del documento............................................................. 16  
4. Numeración, conformada por serie y número correlativo................................... 17  
5. Fecha de emisión .............................................................................................. 17  
6. Leyendas........................................................................................................... 18  
7. Tipo de moneda en la cual se emite la nota de débito electrónica..................... 18  
8. Código del tipo de Nota de débito electrónica ................................................... 19  
9. Motivo o Sustento.............................................................................................. 20  
10. Serie y número del documento que modifica..................................................... 20  
11. Tipo de documento del documento quemodifica................................................ 21  
12. Documento dereferencia ................................................................................... 21  
13. Apellidos y nombres o denominación o razón social.......................................... 22  
14. Tipo y Número de RUC del Emisor.................................................................... 23  
15. Código del domicilio fiscal o de local anexo del emisor. .................................... 24  
16. Apellidos y nombres o denominación o razón social del adquirente o usuario... 25  
17. Tipo y número de documento de identidad del adquirente o usuario. ................ 25  
18. Monto Total de Impuestos. ................................................................................ 26  
19. Total valor de venta - operaciones gravadas. .................................................... 26  
20. Total valor de venta - operaciones inafectas. .................................................... 27  
21. Total valor de venta - operaciones exoneradas. ................................................ 29  
22. Sumatoria de IGV.............................................................................................. 30  
23. Sumatoria de ISC. ............................................................................................. 30  
24. Sumatoria de Otros Tributos.............................................................................. 31  
25. Total de Descuentos.......................................................................................... 32  
26. Importe total de la venta, de la cesión en uso o del servicio prestado. .............. 32  
27. Número de orden del Ítem................................................................................. 33  
28. Cantidad de unidades por ítem.......................................................................... 33  
29. Valor de venta por ítem. .................................................................................... 34  
30. Precio de Venta unitario por ítem que modifica y código. .................................. 34  
31. Valor unitario por ítem en operaciones no onerosas y código............................ 35

## Página 3

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 2 ~

32. Afectación al IGV del ítem que modifica. ........................................................... 36  
33. Afectación al ISC del ítem que modifica. ........................................................... 36  
34. Descripción detallada del servicio prestado, bien vendido o cedido en uso. ...... 37  
35. Código Producto................................................................................................ 38  
36. Código Producto de SUNAT.............................................................................. 38  
37. Valor unitario del ítem........................................................................................ 39  
B.2 Detalle de elementos complejos........................................................................... 40  
B.2.1 Tag UBLExtension.......................................................................................... 40  
1.5 Ejemplos de casos identificados ................................................................... 44

## Página 4

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 3 ~

### 1 NOTA DE DÉBITO ELECTRONICA

La nota de débito electrónica es la nota de débito regulada por el Reglamento de  
Comprobantes de pago soportada en un formato digital que cumple con las  
especificaciones reguladas en la R.S.097-2012/SUNAT y que se encuentra firmada  
digitalmente.

El contenido de información ha sido regulado por el Anexo 04 de la referida Resoluion de  
Superintendencia y por el Anexo 09 en relación al uso del estándar UBL. En el presente  
documento se desarrolla el detalle de los campos (tag) indicados en dicho anexo.

### 1.1 Requisitos de la Nota de Débito electrónica

En el cuadro siguiente, se describe el contenido (campos) de la Nota de Credito  
electrónica. Para lo cual, de manera previa, es necesario establecer la nomenclatura de  
representación del valor de los datos, para una comprensión correcta delcontenido

a caracter alfabético  
n caracter numérico  
an carácter alfanumérico  
a3 3 caracteres alfabéticos de longitud fija  
n3 3 caracteres numéricos de longitud fija  
an3 3 caracteres alfa-numéricos de longitud fija  
a..3 hasta 3 caracteres alfabéticos  
n..3 hasta 3 caracteres numéricos  
an..3 hasta 3 caracteres alfa-numéricos

Asímismo, la obligatoriedad o no de un determinado elemento se identifica por la siguiente  
nomenclatura:

M : Mandatorio u obligatorio  
C: Condicional u opcional

En relación a la identificación del formato de los elementos de datos se especifica lo  
siguiente:

n(12,2) elemento numérico hasta12 enteros+punto decimal+hastadosdecimales  
n(2,2) elemento numérico hasta 2 enteros+punto decimal+hastadosdecimales  
F##### elemento inicia con la letra F seguida decincodígitosYYYY-MM-DD formato fecha yyyy=año, mm=mes,dd=día

## Página 5

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 4 ~

### CONTENIDO DE LA NOTA DE DÉBITO ELECTRONICA

```text
Raíz Nodo Atributo DATO Cardinalidad UBL Valor/Formato Observaciones
/DebitNote -
/DebitNote/ext:UBLExtensions 0..1
/DebitNote/ext:UBLExtensions/ext:UBLExtension 1..n
/DebitNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent 1
ds:Signature Firma Digital
cbc:UBLVersionID Versión del UBL 0..1 "2.1"
cbc:CustomizationID Versión de la estructura del documento 0..1 "2.0"
cbc:ID Serie y número del comprobante 1 F###-NNNNNNNN
cbc:IssueDate Fecha de emisión 1 yyyy-mm-dd
cbc:IssueTime Hora de emisión 0..1 hh-mm-ss.0z
cbc:Note Leyenda 0..n an..100
@languageLocaleID Código de leyenda 0..1 "urn:pe:gob:sunat:cpe:see: gem:catalogos:catalogo52" Catálogo 52
cbc:DocumentCurrencyCode Código de tipo de moneda en la cual se emite la nota de Débito electrónica 0..1 an3 Catálogo 02
/DebitNote/cac:DiscrepancyResponse 0..n
cbc:ReferenceID Serie y número de comprobante afectado 1 NNNN-NNNNNNNN\ F###-NNNNNNNN
cbc:ResponseCode Código de tipo de nota de Débito 0..1 n2 Catálogo 09
cbc:Description Motivo o sustento 0..n an..250
/DebitNote/cac:BillingReference/cac:DebitNoteDocumentReference 0..1
cbc:ID Serie y número del comprobante que modifica 1 NNNN-NNNNNNNN/ F###-NNNNNNNN
cbc:DocumentTypeCode Código de tipo de comprobante que modifica 0..1 n2 Catálogo 01
```

## Página 6

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 5 ~

```text
/DebitNote/cac:DespatchDocumentReference 0..n
cbc:ID Serie y número de la guía de remisión (comprobante de referencia) 1 NNNN-NNNNNNNN/ G###-NNNNNNNN R###-NNNNNNNN
cbc:DocumentTypeCode Código de tipo de comprobante (comprobante de referencia) 0..1 n2 Catálogo 01
/DebitNote/cac:AdditionalDocumentReference 0..n
cbc:ID Serie y número del comprobante de referencia 1 an..30
cbc:DocumentTypeCode Código de tipo de comprobante de referencia 0..1 n2 Catálogo 12
/DebitNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme 0..n
cbc:RegistrationName Nombre o razón social del emisor 0..1 an..100
cbc:CompanyID Número de RUC del emisor 0..1 n11
@schemeID Tipo de Documento de Identidad del Emisor 0..1 an1 Catálogo 06
@schemeName - 0..1 "SUNAT:Identificador de Documento de Identidad"
@schemeAgencyName - 0..1 "PE:SUNAT"
@schemeURI - 0..1 "urn:pe:gob:sunat:cpe:see: gem:catalogos:catalogo06"
/DebitNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cac:RegistrationAddress 0..1
cbc:AddressTypeCode Código del domicilio fiscal o de local anexo del emisor 0..1 n4
/DebitNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme 0..n
cbc:RegistrationName Nombre o razón social del adquirente o usuario 0..1 an..100
cbc:CompanyID Número de RUC del adquirente o usuario 0..1 n11
@schemeID Tipo de Documento de Identidad del Emisor 0..1 an1 Catálogo 06
@schemeName - 0..1 "SUNAT:Identificador de Documento de Identidad"
@schemeAgencyName - 0..1 "PE:SUNAT"
@schemeURI - 0..1 "urn:pe:gob:sunat:cpe:see: gem:catalogos:catalogo06"
```

## Página 7

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 6 ~

```text
/DebitNote/cac:TaxTotal 0..n
cbc:TaxAmount Monto total del tributo 1 n(12,2)
@currencyID Código de tipo de moneda del monto total del tributo 1 an3 Catálogo 02
/DebitNote/cac:TaxTotal/cac:TaxSubtotal 0..n
cbc:TaxAmount Monto total del tributo 1 n(12,2)
@currencyID Código de tipo de moneda del monto total del tributo 1 an3 Catálogo 02
/DebitNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme 1
cbc:ID Código de tributo 0..1 an4 Catálogo 05
cbc:Name Nombre de tributo 0..1 an..6 Catálogo 05
cbc:TaxTypeCode Código internacional tributo 0..1 an3 Catálogo 05
/DebitNote/cac:LegalMonetaryTotal 1
cbc:AllowanceTotalAmount Monto total de descuentos globales del comprobante 0..1 n(12,2)
@currencyID Código de tipo de moneda del monto total de descuentos globales del comprobante 1 an3 Catálogo 02
cbc:ChargeTotalAmount Monto total de otros cargos del comprobante 0..1 n(12,2)
@currencyID Código de tipo de moneda del monto total de otros cargos del comprobante 1 an3 Catálogo 02
cbc:PrepaidAmount Monto total de anticipos del comprobante 0..1 n(15,2)
@currencyID Código de tipo de moneda del monto total de anticipos del comprobante 1 an3 Catálogo 02
cbc:PayableAmount Importe total de la venta, cesión en uso o del servicio prestado 1 n(12,2)
@currencyID Código tipo de moneda del importe total de la venta, cesión en uso o del servicio prestado 1 an3 Catálogo 02
```

## Página 8

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 7 ~

```text
/DebitNote/cac:DebitNoteLine 1..n
cbc:ID Número de orden del Ítem 1 n..3
cbc:DebitedQuantity Cantidad de unidades del ítem 0..1 n(12,10)
@unitCode Unidad de medida del ítem 0..1 an..3 Catálogo 03
@unitCodeListID - 0..1 UN/ECE rec 20
@unitCodeListAgencyName - 0..1 United Nations Economic Commission for Europe
cbc:LineExtensionAmount Valor de venta del ítem 1 n(12,2)
@currencyID Código de tipo de moneda del valor de venta del ítem 1 an3 Catálogo 02
/DebitNote/cac:DebitNoteLine/cac:PricingReference/cac:AlternativeConditionPrice 0..n
cbc:PriceAmount Precio de venta unitario/ Valor referencial unitario en operaciones no onerosas 1 n(12,10)
@currencyID Código de tipo de moneda del precio de venta unitario o valor referencial unitario 1 an3 Catálogo 02
cbc:PriceTypeCode Código de tipo de precio 0..1 an2 Catálogo 16
/DebitNote/cac:DebitNoteLine/cac:TaxTotal 0..n
cbc:TaxAmount Monto de tributo del ítem 1 n(12,2)
@currencyID Código de tipo de moneda del monto de tributo del ítem 1 an3 Catálogo 02
/DebitNote/cac:DebitNoteLine/cac:TaxTotal/cac:TaxSubtotal 0..n
cbc:TaxAmount Monto de tributo del ítem 1 n(12,2)
@currencyID Código de tipo de moneda del monto de tributo del ítem 1 an3 Catálogo 02
/DebitNote/cac:DebitNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory 1
cbc:TaxExemptionReasonCode Código de tipo de afectación del IGV 0..1 an2 Catálogo 07
cbc:TierRange Código de tipo de sistema de ISC 0..1 an2 Catálogo 08
/DebitNote/cac:DebitNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme 1
cbc:ID Código de tributo 0..1 an4 Catálogo 05
cbc:Name Nombre de tributo 0..1 an..6 Catálogo 05
cbc:TaxTypeCode Código internacional tributo 0..1 an3 Catálogo 05
```

## Página 9

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 8 ~

```text
/DebitNote/cac:DebitNoteLine/cac:Item 1
cbc:Description Descripción detallada del servicio prestado, bien vendido o cedido en uso, indicando las características. 0..n an..250
/DebitNote/cac:DebitNoteLine/cac:Item/cac:SellersItemIdentification 0..1
cbc:ID Código de producto del ítem 1 an..30
/DebitNote/cac:CreditLine/cac:Item/cac:CommodityClassification 0..1
cbc:ItemClassificationCode Código de producto (SUNAT) 1 n8
@listID 0..1 UNSPSC
@listAgencyName 0..1 GS1 US
@listName 0..1 Item Classification
/DebitNote/cac:CreditLine/cac:Item/cac:AdditionalItemProperty 0..n
cbc:Name Nombre del concepto o elemento a consignar 1 an..100
cbc:NameCode Código del concepto o elemento a consignar 0..1 8000 ó 8001 Catálogo 55
@listName - 0..1 "SUNAT:Identificador de la propiedad del ítem"
@listAgencyName - 0..1 "PE:SUNAT"
cbc:Value Valor del concepto o elemento a consignar 0..1 an..100
/DebitNote/cac:CreditLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod 0..1
cbc:StartDate Fecha de inicio 0..1 yyyy-mm-dd
/DebitNote/cac:DebitNoteLine/cac:Price 0..1
cbc:PriceAmount Valor unitario del ítem 1 n(12,10)
@currencyID Código de tipo de moneda del valor unitario del ítem 1 an3 Catálogo 02
```

## Página 10

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 9 ~

### 1.2 Estructura de Nota de Débito vs FormatoXML

```xml
<ext:UBLExtensions>
<ext:UBLExtension>
<ext:ExtensionContent>
<ds:Signature Id="signatureKG">
<ds:SignedInfo>
<ds:CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n￾20010315#WithComments"/>
<ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#dsa-sha1"/>
<ds:Reference URI="">
<ds:Transforms>
<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped￾signature"/>
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
<ds:X509Certificate>MIIF9TCCBN2gAwIBAgIGAK0oRTg/MA0GCSqGSIb3DQEBCwUAMFkxCzAJBgNVB
AYTAlRSMUowSAYD
VQQDDEFNYWxpIE3DvGjDvHIgRWxla3Ryb25payBTZXJ0aWZpa2EgSGl6bWV0IFNhxJ9sYXnEsWPE
sXPEsSAtIFRlc3QgMTAeFw0wOTEwMjAxMTM3MTJaFw0xNDEwMTkxMTM3MTJaMIGgMRowGAYDVQQL
DBFHZW5lbCBNw7xkw7xybMO8azEUMBIGA1UEBRMLMTAwMDAwMDAwMDIxbDBqBgNVBAMMY0F5ZM
Sx bGFtYSBEYW7EscWfbWFubMSxayDFnmlya2V0bGVyIEd1cnVidTCCASIwDQYJKoZIhvcNAQEBBQAD
ggEPADCCAQoCggEBAKDt8WamB8ZCGqkLVP0rzY/BHGEXy8lT56m2dK7tswsvZxZYkV2qLGAxRlIY
ETAqBggrBgEFBQcCARYeaHR0cDovL2RlcG8ua2FtdXNtLmdvdi50ci9pbGtlMIHiBggrBgEFBQcC
AjCB1R6B0gBCAHUAIABzAGUAcgB0AGkAZgBpAGsAYQAgAGkAbABlACAAaQBsAGcAaQBsAGkAIABz
AGUAcgB0AGkAZgBpAGsAYQAgAHUAeQBnAHUAbABhAG0AYQAgAGUAcwBhAHMAbABhAHIBMQBuAT EA
IABvAGsAdQBtAGEAawAgAGkA5wBpAG4AIABiAGUAbABpAHIAdABpAGwAZQBuACAAdwBlAGIAIABz
AGkAdABlAHMAaQBuAGkAIAB6AGkAeQBhAHIAZQB0ACAAZQBkAGkAbgBpAHoALjAMBgNVHRMBAf8E
AjAAMBYGA1UdJQQPMA0GC2CGGAECAQEFBzIBMEEGA1UdHwQ6MDgwNqA0oDKGMGh0dHA6Ly9kZX
Bv LmthbXVzbS5nb3YudHIva3VydW1zYWwvbW1lc2hzLXQxLmNybDCBggYIKwYBBQUHAQEEdjB0MDwG
CCsGAQUFBzAChjBodHRwOi8vZGVwby5rYW11c20uZ292LnRyL2t1cnVtc2FsL21tZXNocy10MS5j
6Q3R1ZRSA49fYz6tDB4Ia5HVBXZODmrCs26XisHF6kuS5N/yGg8E7VC1BRr/SmxXeLTdjQYAfo7l
xCz4dT6wP5TOiBvF+lyWW1bi9nbliXyb/e5HjCp4k/ra9LTskjbY/Ukl5O8G9JEAViZkjvxDX7T0
yVRHgMGiioIKVMwU6Lrtln607BNurLwED0OeoZ4wBgkBiB5vXofreXrfN2pHZ24=</ds:X509Certificate>
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
Nota de Débito Electrónica ~ 10 ~

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
<cbc:CustomizationID>2.0</cbc:CustomizationID>
<cbc:ID>FD02-10</cbc:ID>
<cbc:IssueDate>2017-06-28</cbc:IssueDate>
<cbc:Note languageLocaleID="3000">05010020170628000785</cbc:Note>
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>

<cac:DiscrepancyResponse>
  <cbc:ReferenceID>F002-6</cbc:ReferenceID>
  <cbc:ResponseCode>07</cbc:ResponseCode>
</cac:DiscrepancyResponse>

<cac:DiscrepancyResponse>
  ...
  <cbc:Description><![CDATA[Devolución por ítem]]></cbc:Description>
</cac:DiscrepancyResponse>

<cac:BillingReference>
  <cac:DebitNoteDocumentReference>
    <cbc:ID>F002-6</cbc:ID>
    <cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
  </cac:DebitNoteDocumentReference>
</cac:BillingReference>

<cac:DespatchDocumentReference>
  <cbc:ID>031-002020</cbc:ID>
  <cbc:DocumentTypeCode>09</cbc:DocumentTypeCode>
</cac:DespatchDocumentReference>

<cac:AdditionalDocumentReference>
  <cbc:ID>10000120094</cbc:ID>
  <cbc:DocumentTypeCode>05</cbc:DocumentTypeCode>
</cac:AdditionalDocumentReference>

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
Nota de Débito Electrónica ~ 11 ~

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
  <cbc:ChargeTotalAmount currencyID="PEN">600.00</cbc:ChargeTotalAmount>
  <cbc:PrepaidAmount currencyID="PEN">100.00</cbc:PrepaidAmount>
  <cbc:PayableAmount currencyID="PEN">500.00</cbc:PayableAmount>
</cac:LegalMonetaryTotal>

<cbc:ID>1</cbc:ID>
<cbc:DebitedQuantity unitCode="CS" unitCodeListID="UN/ECE rec 20" unitCodeListAgencyName="United Nations Economic Commission for Europe">50</cbc:DebitedQuantity>
<cbc:LineExtensionAmount currencyID="PEN">1439.48</cbc:LineExtensionAmount>
```

## Página 13

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 12 ~

```xml
<cac:PricingReference>
  <cac:AlternativeConditionPrice>
    <cbc:PriceAmount currencyID="PEN">34.99</cbc:PriceAmount>
    <cbc:PriceTypeCode>01</cbc:PriceTypeCode>
  </cac:AlternativeConditionPrice>
</cac:PricingReference>

<cac:PricingReference>
  <cac:AlternativeConditionPrice>
    <cbc:PriceAmount currencyID="PEN">250.00</cbc:PriceAmount>
    <cbc:PriceTypeCode>02</cbc:PriceTypeCode>
  </cac:AlternativeConditionPrice>
</cac:PricingReference>

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

<cbc:Description><![CDATA[Por aplicación de intereses compensatorios y moratorios según contrato N° 9685112]]></cbc:Description>

<cbc:SellersItemIdentification>
  <ID>Cap-258963</ID>
</cbc:SellersItemIdentification>

<cac:CommodityClassification>
  <ItemClassificationCode listID="UNSPSC" listAgencyName="GS1 US" listName="Item Classification">51121703</ItemClassificationCode>
</cac:CommodityClassification>

<cbc:PriceAmount CurrencyID="PEN">785.20</cbc:PriceAmount>
```

## Página 14

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 13 ~

### 1.3 Normas de Uso del Formato de la Nota de Débito Electrónica

**A. Normas de Uso**

Como se ha indicado, el formato UBL está basado en el uso de un documento XML para  
presentar todos los datos en forma jerárquica. El formato especifica que para un archivo se  
especifique toda la información de una y solo una nota de Débito. Como dicha representación se  
basa en XML debe existir un único tag que engloba a todos los demás, dicha etiqueta es  
`DebitNote`.

```xml
<DebitNote>
......
</DebitNote>
```

Para un mejor entendimiento de la estructura del archivo XML, se describe a continuación los  
elementos que conforman la nota de Débito para el modelo Peruano, así como también los  
elementos complejos más importantes.

**A.1 Elementos de la Nota de Débito**

A continuación se detallan los elementos que forman parte del documento Nota de Débito. En  
cada uno de ellos se indica una explicación de la información que almacena, si es obligatorio  
o no para que el documento sea correcto, su ubicación dentro del documento, un ejemplo y  
una breve explicación de acuerdo al estándar UBL.

En la descripción UBL, para una mejor comprensión de los elementos de datos, se describen  
solo aquellos tags que son necesarios para el uso tributario y que son requeridos por la  
administración.

## Página 15

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 14 ~

### 1. Firma Digital

Obligatorio.

Es el conjunto de datos asociados al documento electrónico que se firma y permite la  
identificación del signatario (emisor de la factura electrónica) y ha sido creada por medios  
que éste mantiene bajo su control, de manera que está vinculada únicamente al signatario  
y a los datos a los que refiere.

La firma deberá realizarse con el certificado digital que el emisor de la nota de débito  
comunicó previamente a SUNAT.

La firma se consignará en dos contenedores que corresponden a tipos complejos. Estos son  
la firma digital de acuerdo a UBL y un componente de extensión.

**Ubicación**

- `//DebitNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature`
- `//DebitNote/cac:Signature`

**Ejemplo**

Un ejemplo de declaración de firma electrónica en el contenedor `UBLExtensions` sería:

```xml
<ext:UBLExtensions>
<ext:UBLExtension>
<ext:ExtensionContent>
<ds:SignatureId="signatureKG">
<ds:SignedInfo>
<ds:CanonicalizationMethodAlgorithm="http://www.w3.org/TR/2001/REC-xml-c14n￾20010315#WithComments"/>
<ds:SignatureMethodAlgorithm="http://www.w3.org/2000/09/xmldsig#dsa-sha1"/>
<ds:ReferenceURI="">
<ds:Transforms>
<ds:TransformAlgorithm="http://www.w3.org/2000/09/xmldsig#enveloped￾signature"/>
</ds:Transforms>
<ds:DigestMethodAlgorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
<ds:DigestValue>+pruib33lOapq6GSw58GgQLR8VGIGqANloj4EqB1cb4=</ds:DigestValue>
</ds:Reference>
</ds:SignedInfo>
<ds:SignatureValue>Oatv5xMfFInuGqiX9SoLDTy2yuLf0tTlMFkWtkdw1z/Ss6kiDz+vIgZhgKfIaxp+JbVy57GT5
8D6+WMYZ0xOxTK2mojNkJNewwTTXzqOqrrAlObs9YoS5JAQAMi/TwkR4brNniU9tVwyybirHxw0H
WVzN2bB43yQd9hOlXzRUYpC8/sXw78h7ME3E/zeu882aOFySOnHWB63imBQGcYBV+LIGR/JW8ER+
0VLMLatdwPVRbrWmz1/NIy5CWp1xWMaM6fC/9SXV0O1Lqopk0UeX2I2yuf05QhmVfjgUu6GnS3m6
o6zM9J36iDvMVZyj7vbJTwI8SfWjTSNqxXlqPQ==</ds:SignatureValue>
...
</ext:ExtensionContent>
</ext:UBLExtension>
</ext:UBLExtensions>
```

## Página 16

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 15 ~

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

**Descripción UBL**

`UBLExtensions`. Contenedor de Componentes de extensión. Se incorporan definiciones  
estructuradas cuando sean de interés conjunto para emisores y receptores, y no estén ya  
definidas en el esquema de la nota de débito. Se detalla más adelante (punto B.2.1).

Se utilizará el componente Extensions de UBL 2.1 para incorporar la firma electrónica  
XMLDSIG1.

`cac:Signature`. Utilizado para identificar al firmante y otro tipo de información relacionada  
con el mismo. Su uso se da principalmente para especificar la ubicación de la firma  
electrónica ya sea que este embebida (dentro del mansaje) o desacoplada.

- `cbc:ID`. Obligatorio. Identificador de lafirma  
- `cac:SignatoryParty`. Obligatorio. Asociación con la parte firmante, la cual para nuestro caso deberá estar relacionado con el emisor de la nota dedébito.  
- `PartyIdentification`. Obligatorio. A través del elemento ID, se consigna el RUC de la partefirmante.  
- `PartyName`. Obligatorio. A través del elemento Name, se consigna el nombre o razón social de la partefirmante.

## Página 17

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 16 ~

- `cac:DigitalSignatureAttachment`. Obligatorio. En este componente se puede referenciar la firma del documento como una `ExternalReference` a una URI local o remota.
  - `ExternalReference`. Obligatorio. Información acerca de un documento vinculado. Los vínculos pueden ser externos (referenciados mediante un elemento URI), internos (accesibles mediante un elemento MIME) o pueden estar contenidos dentro del mismo documento en el que se alude a ellos (mediante elementos Documento Incrustado). Este último será el caso a utilizar, es decir una referencia dentro del mismo documento `DebitNote`, específicamente en el componente `UBLExtensions`.

### 2. Versión del UBL

Obligatorio. Versión del esquema UBL que define todos los elementos que se podrían  
encontrar en este documento. Para el caso peruano se ha utilizado la versión “2.1”.

**Ubicación**

`//DebitNote/cbc:UBLVersionID`

**Ejemplo**

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
```

**Descripción UBL cbc:UBLVersionID**

Versión UBL usada para esquematizar y definir los elementos contenidos en el documento.

### 3. Versión de la estructura del documento

Obligatorio. Identifica una personalización de UBL definida para un uso específico. Para  
nuestro caso corresponderá a la versión 2.0 de la nota de débito electrónica. Por cada  
variación o adecuación del esquema se deberá de aumentar la versión, la cual contemplará las  
nuevas validaciones para los elementos de datos establecidos.

**Ubicación**

`//DebitNote/cbc:CustomizationID`

**Ejemplo**

```xml
<cbc:CustomizationID>2.0</cbc:CustomizationID>
```

## Página 18

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 17 ~

### 4. Numeración, conformada por serie y número correlativo

Obligatorio. Identificador de documento, para el caso peruano este elemento contendrá serie  
de la nota de débito y el número correlativo de la misma.

La serie será alfanumérica compuesta por cuatro caracteres:

- Si la nota modifica una factura, el primer carácter de la izquierda debe iniciar en” F”, los otros tres caracteres pueden ser alfanuméricos. El número correlativo podrá ser hasta de ocho (8) caracteres iniciando en1.
- Si la nota modifica una boleta de venta, el primer carácter de la izquierda debe iniciar en B, los otros tres caracteres pueden ser alfanuméricos. El número correlativo podrá ser hasta de ocho (8) caracteres iniciando en1.

Esta numeración será independiente del número correlativo de las notas de débito emitidas en  
formato impreso y/o importado por imprenta autorizada.

La serie de la nota de débito, salvo el primer carácter, no necesariamente debe coincidir con la  
la serie del comprobante de pago que es materia deajuste.

**Ubicación**

`//DebitNote/cbc:ID`

**Ejemplo**

```xml
<cbc:ID>FD01-10</cbc:ID>
```

### 5. Fecha de emisión

Obligatorio. Corresponde a la fecha que, de conformidad con las normas legales, debe emitirse  
el documento por aquellas circunstancias que impliquen el aumento del valor de las operaciones,  
sustentadas en un comprobante de pago previamente emitido.

**Ubicación**

`//DebitNote/cbc:IssueDate`

**Ejemplo**

```xml
<cbc:IssueDate>2011-06-28</cbc:IssueDate>
```

## Página 19

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 18 ~

### 6. Leyendas

Elemento utilizado para consignar la siguiente leyenda:

- Código interno generado por el software de Facturación.

Se consignará la llave única o clave única o clave primaria del software donde se generó el  
ingreso de información para la generación del comprobante de pago electrónico.  
Tratándode de software contables intregados (ERP) se podrá consignar el código contable del  
asiento del libro diario que generó la transacción.

En el atributo `@languageLocaleID` se debe consignar el código “3000” (según Catálogo No. 52).

**Ubicación**

`//DebitNote/cbc:Note@languageLocaleID`

### 7. Tipo de moneda en la cual se emite la nota de débito electrónica

Obligatorio. Código de moneda empleada genéricamente en el documento y que debe ser  
igual al tipo de moneda de el(los) comprobante(s) de pago que se modifica(n). Los códigos se  
especifican en un archivo de tipo CodeList incluido en los esquemas UBL y que corresponde a  
la norma ISO 4217 – Currency.

**Ubicación**

`//DebitNote/cbc:DocumentCurrencyCode`

**Ejemplo**

```xml
<DebitNote>
…
<cbc:Note languageLocaleID="3000">05010020170428000005</cbc:Note>
…
</DebitNote>
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
```

## Página 20

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 19 ~

### 8. Código del tipo de Nota de débito electrónica

Obligatorio. Motivo por el cual se emite la nota de débito. Para el caso peruano el elemento  
`cbc:ResponseCode` podrá adoptar los siguientes valores:

- `01` Interes por mora
- `02` Aumento en el valor

**Ubicación**

- `//DebitNote/cac:DiscrepancyResponse/cbc:ReferenceID`
- `//DebitNote/cac:DiscrepancyResponse/cbc:ResponseCode`

**Ejemplo**

```xml
<cac:DiscrepancyResponse>
<cbc:ReferenceID>F002-6</cbc:ReferenceID>
<cbc:ResponseCode>01</cbc:ResponseCode>
<cbc:Description><![CDATA[Interes por pago extemporáneo de Octubre 2016]]></cbc:Description>
</cac:DiscrepancyResponse>
```

**Descripción UBL**

`cac:DiscrepancyResponse`

Contiene el detalle del motivo de la emisión del documento en su conjunto. El uso de este tag  
implica que obligatoriamente se consigne un campo `cbc:ReferenceID`, el cual para el caso  
Peruano contendrá la identificación del documento modificado, a pesar de que este dato se  
consigna también en otro elemento (`cac:BillingReference`).

Los elementos a utilizarse son los siguientes:

- `cbc:ReferenceID`: Obligatorio. Identifica al documento modificado por la nota de débito. Se consignará en el formato: Serie-Número correlativo dedocumento.
- `cbc:ResponseCode`: Obligatorio. Código que representa el tipo de nota de débito utilizada. Se utilizará el Catálogo No.09.
- `cbc:Description`: Obligatorio. Sustento o descripción del motivo de la nota de débito. Sólo se puede consignar unadescripción.

## Página 21

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 20 ~

### 9. Motivo o Sustento

Obligatorio. Elemento usado para describir el motivo o sustento de la emisión de la nota de  
débito, explicación que debe ser acorde con el tipo de nota emitida.

**Ubicación**

`//DebitNote/cac:DiscrepancyResponse/cbc:Description`

### 10. Serie y número del documento que modifica

Obligatorio. Asocia la nota de débito al comprobante de pago modificado.

**Ubicación**

`//DebitNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:ID`

**Ejemplo**

```xml
<cac:BillingReference>
<cac:InvoiceDocumentReference>
<cbc:ID>F001-2</cbc:ID>
<cbc:DocumentTypeCode>01</cbc:DocumentTypeCode>
</cac:InvoiceDocumentReference>
</cac:BillingReference>
```

**Descripción UBL**

`cac:BillingReference`

Obligatorio: Tipo complejo, que contiene los siguientes elementos:

- `cac:InvoiceDocumentReference`. Obligatorio. Identificación del documento afectado por la nota dedébito:
  - `cbc:ID`: Obligatorio. Identificación del número del documentomodificado.
  - `cbc:DocumentTypeCode`: Opcional. Identificación del código del tipo de documento.

## Página 22

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 21 ~

Valores por defecto del tipo de documento:

- `01` Factura Comercial
- `03` Boleta de Venta
- `12` Ticket de máquina registradora

### 11. Tipo de documento del documento quemodifica

Obligatorio. Se consigna el tipo de comprobante de pago. Se utilizará el Catálogo No. 01 del  
anexo N° 8: “Código de Tipo de documento”.

**Ubicación**

`//DebitNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:DocumentTypeCode`

### 12. Documento dereferencia

Opcional. Referencia a las guías de remisión remitente o transportista, según corresponda,  
autorizado por la SUNAT para sustentar el traslado de los bienes. Pueden existir múltiples  
guías de remisión, por lo que el número de elementos de este tipo es ilimitado. Se utilizará el  
Catálogo N° 01: “Código de Tipo deDocumento”.

También referencia a cualquier otro documento, distintos a los señalados en el párrafo anterior,  
asociado a la nota de débito. Para estos casos se utilizará el Catálogo No. 12: “Códigos -  
Documentos Relacionados Tributarios”.

**Ubicación**

- En el caso de Guías de Remisión:
  - `//DebitNote/cac:DespatchDocumentReference/cbc:ID`
  - `//DebitNote/cac:DespatchDocumentReference/cbc:DocumentTypeCode`
- En el caso de Otros documentos relacionados:
  - `//DebitNote/cac:AdditionalDocumentReference/cbc:ID`
  - `//DebitNote/cac:AdditionalDocumentReference/cbc:DocumentTypeCode`

**Ejemplo**

```xml
<cac:DespatchDocumentReference>
<cbc:ID>0001-002020</cbc:ID>
<cbc:DocumentTypeCode>09</cbc:DocumentTypeCode>
</cac:DespatchDocumentReference>
<cac:AdditionalDocumentReference>
<cbc:ID>0081-024099</cbc:ID>
<cbc:DocumentTypeCode>12</cbc:DocumentTypeCode>
</cac:AdditionalDocumentReference>
```

## Página 23

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 22 ~

**Descripción UBL**

**a) `cac:DespatchDocumentReference`**

Tag que hace referencia a documentos de transporte asociados a la nota de débito.

- `cbc:ID`: Obligatorio. Identificación del número de guía autorizado por SUNAT. Estará conformado por la serie y el número de documento, separado por un guión.
- `cbc:DocumentTypeCode`: Obligatorio. Corresponde al código del tipo de documento al que se hace referencia. Se utilizará de acuerdo al catálogo de códigos establecidos para documentos (Catálogo No. 01).

**b) `cac:AdditionalDocumentReference`**

Tag que hace referencia a documentos asociados a la nota de débito.

- `cbc:ID`: Obligatorio. Identificación del número de documento asociado a la nota de débito.
- `cbc:DocumentTypeCode`: Obligatorio. Corresponde al código del tipo de documento al que se hace referencia. Se utilizará de acuerdo al catálogo de códigos establecidos para documentos (Catálogo No. 12).

### 13. Apellidos y nombres o denominación o razón social

Obligatorio. Corresponde a los apellidos y nombres o denominación o razón social del emisor  
de la nota de débito electrónica. Este debe ser acorde a lo registrado en el Registro Único de  
Contribuyentes - RUC. Este requisito se encuentra contenido en el elemento complejo `cac:Party`  
ubicado en el componente `cac:AccountingSupplierParty`.

**Ubicación**

`//DebitNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:RegistrationName`

## Página 24

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 23 ~

**Ejemplo**

**Descripción UBL `cbc:RegistrationName`**

Se usa para indicar el nombre o razón social del contribuyente de acuerdo a la información  
proporcianada al momento de su inscripción o modificación hacia la SUNAT.

### 14. Tipo y Número de RUC del Emisor.

Obligatorio. El tipo de documento del emisor siempre es 6, que corresponde al RUC. Además  
de esto se debe consignar el número de RUC del emisor de la factura electrónicael cual debe  
ser válido.

**Ubicación**

`//DebitNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID @schemeID @schemeName @schemeAgencyName @schemeURI`

**Ejemplo**

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

## Página 25

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 24 ~

**Atributos**

- `schemeName` `"SUNAT:Identificador de Documento de Identidad"`
- `schemeAgencyName` `"PE:SUNAT"`
- `schemeURI` `"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"`

**Valor de Códigos Catálogo N° 06**

- `6` REG. UNICO DE CONTRIBUYENTES

### 15. Código del domicilio fiscal o de local anexo del emisor.

Corresponde informar el código del establecimiento donde se esta realizando la venta de los  
bienes.

**Ubicación**

`//DebitNote/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cac:RegistrationAddress/cbc:AddressTypeCode`

**Descripción UBL `cac:AddressTypeCode`**

Código de cuatro dígitos asignado por SUNAT, que identifica al establecimiento anexo. Dicho  
código se genera al momento la respectiva comunicación del establecimiento. Tratándose del  
domicilio fiscal y en el caso de no poder determinar el lugar de la venta, informar “0000”.

## Página 26

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 25 ~

### 16. Apellidos y nombres o denominación o razón social del adquirente o usuario.

Obligatorio. Corresponde a los apellidos y nombres o denominación o razón social del  
adquirente o usuario.

**Ubicación**

`/DebitNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme/cbc:RegistrationName`

**Descripción UBL `cbc:RegistrationName`**

Se usará para indicar el nombre o razón social, según fuera el caso del cliente.

### 17. Tipo y número de documento de identidad del adquirente o usuario.

Obligatorio. El tipo de documento será de acuerdo al Catálogo N° 06 del anexo N° 8: “Códigos  
de Tipos de Documentos de Identidad”.

**Ubicación**

`/DebitNote/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID @schemeID @schemeName @schemeAgencyName @schemeURI`

**Ejemplo**

```xml
<cac:AccountingCustomerParty>
…
<cac:Party>
<cac:PartyTaxScheme>
…
<cbc:RegistrationName><![CDATA[CECI FARMA IMPORT S.R.L.]]></cbc:RegistrationName>
</cac:PartyTaxScheme>
…
</cac:Party>
</cac:AccountingCustomerParty>

<cac:AccountingCustomerParty>
<cac:Party>
<cac:PartyTaxScheme>
<cbc:RegistrationName><![CDATA[CECI FARMA IMPORT S.R.L.]]></cbc:RegistrationName>
<cbc:CompanyID schemeID="6" schemeName="SUNAT:Identificador de Documento de
Identidad" schemeAgencyName="PE:SUNAT" schemeURI="
urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">20100113612</cbc:CompanyID>
<cac:TaxScheme>
<cbc:ID>-</cbc:ID>
</cac:TaxScheme>
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingCustomerParty>
```

## Página 27

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 26 ~

**Atributos**

- `schemeName` `"SUNAT:Identificador de Documento de Identidad"`
- `schemeAgencyName` `"PE:SUNAT"`
- `schemeURI` `"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"`

**Valor de Códigos Catálogo N° 06**

- `0` DOC.TRIB.NO.DOM.SIN.RUC
- `1` DOC. NACIONAL DE IDENTIDAD
- `4` CARNET DE EXTRANJERIA
- `6` REG. UNICO DE CONTRIBUYENTES
- `7` PASAPORTE
- `A` CED. DIPLOMATICA DE IDENTIDAD
- `B` DOC.IDENT.PAIS.RESIDENCIA-NO.D
- `C` Tax Identification Number - TIN – Doc Trib PP.NN
- `D` Identification Number - IN – Doc Trib PP. JJ

### 18. Monto Total de Impuestos.

Corresponde al importe total de impuestos ISC, IGV e IVAP de Corresponder.

**Ubicación**

`//DebitNote/cac:TaxTotal/cbc:TaxAmount`

**Ejemplo**

```xml
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">59210.65</cbc:TaxAmount>
…
</cac:TaxTotal>
```

### 19. Total valor de venta - operaciones gravadas.

Este elemento es usado solo si al menos una línea de ítem está gravada con el IGV. Contiene a  
la sumatoria de los valores de venta gravados por ítem (ver definición de valor de venta en  
punto 26). El total valor de venta no incluye IGV, ISC, cargos y otros Tributos si los hubiera.  
La sumatoria tampoco debe contener el valor de venta de las transferencias de bienes o  
servicios prestados a título gratuito comprendidos en la factura y que estuviesen gravados con  
el IGV.

**Ubicación**

`/DebitNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

## Página 28

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 27 ~

**Descripción UBL `cac:TaxSubTotal`**

Para hacer uso de este elemento, es necesario consignar el monto base sobre el cual se está  
aplicando el impuesto informado, esto se consigna en el elemento `cbc:TaxableAmount`. En el  
elemento `cbc:TaxAmount` se coloco el total de impuestos de ser el caso.

**`cac:TaxCategory`**

Valor de Códigos `cbc:ID` Catálogo N° 05:

- `S` IGV

**`cac:TaxScheme`**

Para el caso de IGV es el código `1000` y a los siguientes atributos:

- `cbc:Name`: `IGV`
- `cbc:TaxTypeCode`: `VAT`

**Ejemplo**

```xml
<cac:TaxTotal>
…
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">8560.00</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">1540.80</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:ID>S</cbc:ID>
<cac:TaxScheme>
<cbc:ID>1000</cbc:ID>
<cbc:Name> IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
…
</cac:TaxTotal>
```

### 20. Total valor de venta - operaciones inafectas.

Este elemento es usado solo si al menos una línea de ítem se encuentra inafecta al IGV.

## Página 29

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 28 ~

Contiene a la sumatoria de valor de venta por item inafectos (ver definición de valor de venta  
x ítem en punto 26). El valor de venta no incluye ISC, cargos u otros tributos si los hubiera. La  
sumatoria tampoco debe contener el valor de venta de las transferencias de bienes o servicios  
prestados a título gratuito comprendidos en la factura y que estuviesen inafectos al IGV.

**Ubicación**

`/DebitNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

**Ejemplo**

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

**Valores**

- `O` = Inafecto
- `9998` = INAFECTO
- `FRE` = código de tipo de tributo

## Página 30

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 29 ~

### 21. Total valor de venta - operaciones exoneradas.

Este elemento es usado solo si al menos una línea de ítem se encuentra exonerada al IGV.  
Contiene a la sumatoria de valor de venta por ítem exonerados por item (ver definición de valor  
de venta x ítem en punto 26).

**Ubicación**

`/DebitNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount @currencyID`

**Ejemplo**

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

## Página 31

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 30 ~

`cbc:TaxTypeCode`

Este elemento se utiliza para expresar a través de un código que la información que se está  
reportando es exonerada, el valor de acuerdo Catálogo N° 5 es: `FRE`.

### 22. Sumatoria de IGV.

Corresponde a la sumatoria del IGV del ajuste realizado con la nota de credito.

El IGV = 18% de la suma: `[Total valor de venta operaciones gravadas] + [Sumatoria ISC]`.

Para el caso peruano los elementos de identificación del tributo contenidos en:  
`.../cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/…` adoptarán los valores `1000`, `IGV` y  
`VAT` respectivamente. (Catálogo No. 05)

**Ubicación**

`//DebitNote/cac:TaxTotal/cbc:TaxAmount`

### 23. Sumatoria de ISC.

Corresponde a la sumatoria del ISC del ajuste realizado con la nota de credito.  
Para el caso peruano los elementos de identificación del tributo contenidos en:  
`.../cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/…` adoptarán los valores  
`2000`, `ISC` y `EXC` respectivamente.

**Ejemplo parcial**

```xml
<cac:TaxTotal>
…
<cbc:TaxAmount currencyID="PEN">59210.65</cbc:TaxAmount>
…
<cac:TaxTotal>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">328948.05</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">59210.65</cbc:TaxAmount>
<cac:TaxCategory>
<cbc:ID>S</cbc:ID>
<cac:TaxScheme>
<cbc:ID>1000</cbc:ID>
<cbc:Name>IGV</cbc:Name>
<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
</cac:TaxScheme>
</cac:TaxCategory>
</cac:TaxSubtotal>
</cac:TaxTotal>
```

## Página 32

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 31 ~

**Ubicación**

`//DebitNote/cac:TaxTotal/cbc:TaxAmount`

**Descripción UBL `cbc:TaxAmount`**

Este campo se consigna dentro de un elemento complejo `cac:TaxTotal`. Para hacer uso de este  
elemento, es necesario además colocar datos que permita identificar el tributo que se está  
informando. Además, se debe tomar en cuenta que el campo `cbc:TaxAmount` se consigna a  
nivel del `cac:TaxTotal` y a nivel del `cac:TaxSubtotal`. En ambos casos se consignará el mismo valor  
correspondiente al monto del tributo.

### 24. Sumatoria de Otros Tributos.

Sumatoria de otros tributos, distintos al IGV o ISC, correspondientes al ajuste realizado con la  
nota de débito, y que conforme a la regulación correspondiente deban estar desagregados en  
la nota dedébito.

Para el caso peruano los elementos de identificación de este concepto contenidos en:  
`.../cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/…` adoptarán los valores `9999`,  
`OTROS` y `OTH` respectivamente. (Catálogo No. 05)

**Ejemplo**

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

<cac:TaxTotal>
<cbc:TaxAmountcurrencyID="PEN">39000.0</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmountcurrencyID="PEN">97500.00</cbc:TaxableAmount>
<cbc:TaxAmountcurrencyID="PEN">39000.0</cbc:TaxAmount>
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

## Página 33

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 32 ~

### 25. Total de Descuentos.

A través de este elemento se debe indicar el valor total de los descuentos realizados de ser el  
caso.

Su propósito es permitir consignar en el comprobante de pago:

- la sumatoria de los descuentos de cada línea (descuentos por ítem), o
- la sumatoria de los descuentos de línea (ítem)

**Ubicación**

`//DebitNote/cac:LegalMonetaryTotal/cbc:AllowanceTotalAmount`

### 26. Importe total de la venta, de la cesión en uso o del servicio prestado.

`Total valor de venta - operaciones gravadas (19)` +  
`Total valor de venta - operaciones inafectas (20)` +  
`Total valor de venta - operaciones exoneradas (21)` +  
`Sumatoria IGV (22)` +  
`Sumatoria ISC (23)` +  
`Sumatoria otros tributos (24)`

**Ubicación**

`//DebitNote/cac:LegalMonetaryTotal/cbc:PayableAmount`

**Ejemplo**

```xml
<cac:LegalMonetaryTotal>
<cbc:AllowanceTotalAmount currencyID="PEN">9420.50</cbc:AllowanceTotalAmount>
</cac:LegalMonetaryTotal>
```

## Página 34

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 33 ~

**Descripción UBL `cbc:PayableAmount`**

El campo `cbc:PayableAmount` se consigna dentro del elemento complejo  
`cac:LegalMonetaryTotal`.

### 27. Número de orden del Ítem.

Número de la línea que es secuencial y se encuentra en cada línea que contiene la nota de  
débito.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cbc:ID`

### 28. Cantidad de unidades por ítem.

Se consignará la cantidad de bienes devueltos. En el caso de servicios se deberá consignar el  
número “1”.

**Ubicación**

`/DebitNote/cac:DebitNoteLine/cbc:DebitedQuantity @unitCode @unitCodeListID @unitCodeListAgencyName`

**Ejemplo**

```xml
<cac:DebitNoteLine>
<cbc:ID>1</cbc:ID>
….
</cac:DebitNoteLine>

<cbc:DebitedQuantity unitCode="CS" unitCodeListID="UN/ECE rec 20"
unitCodeListAgencyName="United Nations Economic Commission for
Europe">50</cbc:DebitedQuantity>

<cac:LegalMonetaryTotal>
…
<cbc:PayableAmountcurrencyID="PEN">45.34</cbc:PayableAmount>
</cac:LegalMonetaryTotal>
```

## Página 35

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 34 ~

**Atributos**

- `unitCode` Catálogo N° 3
- `unitCodeListID` `"UN/ECE rec 20"`
- `unitCodeListAgencyName` `"United Nations Economic Commission for Europe"`

**Valor de Códigos cbc:ID Catálogo N° 03**

- `NIU` UNIDAD (BIENES)
- `ZZ` UNIDAD (SERVICIOS)

### 29. Valor de venta por ítem.

Este elemento es el producto de la cantidad por el valor unitario (Q x Valor Unitario) y la deducción  
de los descuentos aplicados a dicho ítem (de existir). Este importe no incluye los tributos (IGV, ISC  
y otros Tributos).

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cbc:LineExtensionAmount @currencyID`

**Ejemplo**

```xml
<cbc:LineExtensionAmount currencyID="PEN">172890.0</cbc:LineExtensionAmount>
```

### 30. Precio de Venta unitario por ítem que modifica y código.

Se consignará el importe del ajuste correspondiente al precio unitario facturado del bien vendido  
o servicio vendido.

El precio unitario facturado, es la suma total que quedó obligado a pagar el adquirente o usuario  
por cada bien o servicio. Esto incluye los tributos (IGV, ISC, cargos y otros Tributos) y la  
deducción de descuentos. Para identificar este valor, se debe de consignar el código “01”  
(incluido en el Catálogo No. 16).

## Página 36

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 35 ~

En casos de notas de débito que ajusten comprobantes de pago por transferencias gratuitas, de  
corresponder, deberá consignarse el monto del ajuste correspondiente al valor referencial  
unitario indicado en el numeral 35 del presente documento.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:PricingReference/cac:AlternativeConditionPrice @currencyID`

**Valores de `cac:PriceTypeCode` (Catálogo N° 16)**

- `01` Precio unitario (incluye el IGV)
- `02` Valor referencial unitario en operaciones no onerosas

### 31. Valor unitario por ítem en operaciones no onerosas y código.

Cuando la transferencia de bienes o de servicios se efectúe gratuitamente, se consignará el  
importe del valor de venta unitario que hubiera correspondido a dicho bien o servicio, en  
operaciones onerosas con terceros. En su defecto se aplicará el valor de mercado.

Para identificar este valor, se debe de consignar el código “02” (incluido en el Catálogo No. 16).

**Ejemplo**

```xml
<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">18.75</cbc:PriceAmount>
<cbc:PriceTypeCode>01</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>

<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">18.75</cbc:PriceAmount>
<cbc:PriceTypeCode>02</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>
```

## Página 37

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 36 ~

### 32. Afectación al IGV del ítem que modifica.

Indica si el bien transferido, vendido o cedido en uso, servicio prestado u operación facturable y  
materia de ajuste está gravado, exonerado o inafecto al IGV. Se utilizará el Catálogo N° 07:  
“Código tipo de afectación del IGV”.

Para el caso peruano los elementos para identificar al tributo contenido en:  
`../cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/…` adoptarán los valores `1000`, `IGV` y  
`VAT` respectivamente.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode`

### 33. Afectación al ISC del ítem que modifica.

Indica el sistema que fue utilizado para determinar la base imponible cuando el bien  
transferido o vendido, materia de ajuste, está gravado con el ISC. Se utilizará el Catálogo No.  
08: “Códigos de Tipos de Sistema de Cálculo del ISC”.

Para el caso peruano los elementos para identificar al tributo contenido en:  
`.../cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/…`  
adoptarán los valores `2000`, `ISC` y `EXC` respectivamente.

## Página 38

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 37 ~

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TierRange`

**Descripción UBL `cbc:TierRange`**

Este campo se consigna dentro de un elemento complejo `cac:TaxTotal`. Para hacer uso de este  
elemento, es necesario además colocar datos que permita identificar el tributo que se está  
informando y el monto del tributo (`cbc:TaxAmount`), el cual es obligatorio por de acuerdo al  
estándar UBL. Además, se debe tomar en cuenta que el campo `cbc:TaxAmount` se consigna a  
nivel del `cac:TaxTotal` y a nivel del `cac:TaxSubtotal`. En ambos casos se consignará el mismo  
valor correspondiente al monto del tributo. Así mismo, en el elemento `cbc:TaxableAmount` se  
informará la base imponible sobre la cual se le aplica el impuesto.

### 34. Descripción detallada del servicio prestado, bien vendido o cedido en uso.

Este elemento es usado solo de corresponder. Consigna la descripción detallada del bien o  
servicio.

Otras consideraciones:

- Se deberá colocar el número de serie y/o número de motor, si se trata de un bien identificable, decorresponder.
- Tratándose de medicamentos y/o insumos para tratamiento de enfermedades oncológicas y del VIH/SIDA, se consignará adicionalmente la(s) partida(s) arancelaria(s) correspondiente(s).

No obligatorio cuando el tipo de nota de débito (numeral 7) es 02.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:Item/cbc:Description`

**Ejemplo**

```xml
<cac:Item>
<cbc:Description><![CDATA[Ajuste por ajustes de precios]]></cbc:Description>
…
</cac:Item>
```

## Página 39

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 38 ~

**Descripción UBL `cbc:Description`**

Este campo se encuentra ubicado en el elemento complejo `cac:DebitNoteLine`, aquí se detalla  
en forma detallada la descripción del ítem que se está vendiendo.

### 35. Código Producto.

Código del producto de acuerdo al tipo de codificación interna que se utilice.

Su uso será obligatorio si el emisor electrónico, opta por consignar este código, en reemplazo de  
la descripción detallada. Para tal efecto el código a usar será aquél, que las normas que regulan  
el llevado de libros y registros, denominan como código de existencia.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:Item/cac:SellersItemIdentification/cbc:ID`

### 36. Código Producto de SUNAT.

Código del producto de acuerdo al estándar internacional de la ONU denominado: United Nations  
Standard Products and Services Code - Código de productos y servicios estándar de las Naciones  
Unidas - UNSPSC v14_0801.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:Item/cac:CommodityClassification/cbc:ItemClassificationCode`

**Ejemplo**

```xml
<cac:Item>
…
<cac:SellersItemIdentification>
<cbc:ID>Cap-258963</cbc:ID>
</cac:SellersItemIdentification>
…
</cac:Item>

<cac:Item>
…
<cac:CommodityClassification>
<cbc:ItemClassificationCode listID="UNSPSC" listAgencyName="GS1 US" listName="Item
Classification">51121703</cbc:ItemClassificationCode>
</cac:CommodityClassification>
…
</cac:Item>
```

## Página 40

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 39 ~

**Descripción UBL `cbc:ItemClassificationCode`**

Este elemento se encuentra ubicado en el elemento complejo `cac:DebitNoteLine`.

**Atributos**

- `listID` `"UNSPSC"`
- `listAgencyName` `"GS1 US"`
- `listName` `"Item Classification"`

**Valor de Códigos cbc:ID Catálogo N° 25**

- `43233201` Software de Servicios de Autenticación
- `80101511` Servicio de asesoramiento en recursos humanos
- `50201706` Café

### 37. Valor unitario del ítem.

Obligatorio. Se consignará el importe correspondiente al valor o monto unitario del bien  
vendido, cedido o servicio prestado, indicado en una línea o ítem de la factura. Este importe no  
incluye los tributos (IGV, ISC y otros Tributos) ni los cargos globales.

**Ubicación**

`//DebitNote/cac:DebitNoteLine/cac:Price/cbc:PriceAmount`

**Ejemplo**

```xml
<DebitNote>
…
<cac:Price>
<cbc:PriceAmount currencyID="PEN">678.0</cbc:PriceAmount>
</cac:Price>
…
</DebitNote>
```

## Página 41

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 40 ~

## B.2 Detalle de elementos complejos

En esta sección se describe aquellos tag que por su complejidad requieren de una mayor  
explicación.

### B.2.1 Tag UBLExtension

Uno o más `<ext:UBLExtension>` están contenidos dentro de un elemento  
`<ext:UBLExtensions>` descendiente directo del elemento raíz del documento. Estos elementos  
están disponibles en UBL 2.1 para la inclusión de datos no [UBL], como es nuestro caso.

Se hará uso de este tipo de componente de extensión para especificar solamente la firma digital.

**1. `ext:UBLExtension/ext:ExtensionContent/ds:Signature`**

No es objeto de este informe especificar el tipo de firma que se utilizará en el contexto de la  
factura electrónica, sin embargo se sientan las bases para declarar un certificado y se tomará  
como ejemplo una firma sencilla XMLdSig.

La firma digital será alojada dentro del elemento `<ext:UBLExtension>`.

- `ExtensionContent`. Dentro de éste elemento es donde se incluyen las firmas [XMLDSig] de todos los firmantes del documento. Por tanto, en el documento únicamente habrá un solo `<ext:UBLExtension>` para la inclusión de firmas.
- La firma se realizará sobre el documento completo y podrá llevarse a cabo con un componente propio o externo de firma de documentos XML. En cualquier caso la firma satisfará como mínimo los requerimientos de “Firma Electrónica”. Se deberá utilizar [XMLDSig].
- Se utilizará para firmar la clave privada de un certificado digital X509 válido no vencido. Se firma todo el documento (nodo raíz). En esta implementación no podrán añadirse nuevos datos al documento después de firmar.
- En el estándar [XMLDSig] encontramos:
  - Definición de la estructura XML en la que almacenar la firma
  - Definición del proceso de firma
  - Definición del proceso de validación de firma
  - Agrupación y aceptación de los algoritmos y procesos para la transformación en forma canónica
  - Agrupación y aceptación de los algoritmos y procesos de transformación para la obtención de la firma

## Página 42

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 41 ~

A continuación se describen sus atributos y elementos.

El atributo `Id` es opcional pero es muy útil para identificar la firma dentro de un documento,  
sobre todo cuando se trabaja con firmas múltiples.

Por ejemplo:

```xml
<ds:Signature Id="signatureKG">
```

**i. `ds:SignedInfo`**

Este elemento puede dividirse en dos partes desde el punto de vista conceptual: información  
sobre el valor de la firma e información sobre los datos a firmar.

1. `ds:CanonicalizationMethod`: Posee un atributo `Algorithm` que indica cómo se debe transformar a forma canónica el elemento `<ds:SignedInfo>` antes de realizar la firma.

Distintos XML pueden diferir en su forma de ser escritos y sin embargo significar lo mismo.  
Como la firma se realiza a nivel de bytes, aunque un documento signifique lo mismo y tenga la  
misma información que otro, ambos pueden tener firmas diferentes si no están escritos  
exactamente igual.

## Página 43

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 42 ~

2. `ds:SignatureMethod`: Especifica qué tipo de algoritmo de firma se utilizará para obtener la firma.

3. `ds:Reference`: Cada elemento `Reference` incluye el hash de un objeto de datos y las transformaciones aplicadas a ese objeto para producir dicho hash. El atributo `URI` identifica al objeto de datos que se va a firmar.

`ds:Transforms` es opcional aunque es el elemento con más fuerza de `<ds:Reference>`. Si  
aparece, contendrá una lista de `<ds:Transform>`.

`ds:DigestMethod`: Define la función hash utilizada a través del atributo `Algorithm`.  
`ds:DigestValue`: Es el valor hash codificado en Base64.

## Página 44

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 43 ~

ii. `ds:SignatureValue`: contiene la firma codificada en Base64. La firma es el resultado  
de una serie de transformaciones sobre los datos binarios del elemento `<ds:SignedInfo>`.

iii. `ds:KeyInfo`: Es una estructura opcional que identifica al firmante. Su contenido suele  
utilizarse en procesos de verificación de firmas.

Elementos relevantes:

1. `ds:X509Data`: Contiene información del certificado firmante.
2. `ds:KeyValue`: Contiene información de la clave pública.

La información que proporciona `<ds:KeyInfo>` en todos sus elementos debe corresponder al  
mismo certificado o clave.

En caso de no incluir la estructura `<ds:KeyInfo>`, la firma no podría considerarse como  
“Firma Electrónica Avanzada” puesto que el firmante no podría ser identificado.

## Página 45

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 44 ~

### 1.5 Ejemplos de casos identificados

#### A. Nota de Débito sobre Factura

La empresa Soporte Tecnológicos EIRL, cuyo nombre comercial es “Tu soporte”, identificada  
con RUC `20100454523`, debe emitir la nota de débito electrónica N° `FD01-211` con la siguiente  
información:

- Fecha de Emisión: `25 de junio del 2017`
- Adquirente o Usuario: `Servicabinas S.A.`
- RUC: `20587896411`
- Motivo: `Ampliación de garantía de memoria DDR-3B1333Kingston`
- Código de Software de Facturación: `0501002017062500125`
- Código de Establecimiento Anexo: `0001`

**Devolución de mercadería vendida:**

| Código | Código SUNAT | Unidad de Medida | Cantidad | Descripción | Afectación al IGV | Precio Unitario |
|---|---:|---|---:|---|---|---:|
| MPC35 | 32101622 | Unidad | 250 | Ampliación de garantía de memoria DDR-B1333 Kingston | Gravado | 5.00 |

| Valor Unitario (1) | Cantidad | Valor venta item | IGV | Importe Total |
|---:|---:|---:|---:|---:|
| 4.24 | 250 | 1059.32 | 190.68 | 1250.00 |

(1) s/. 5/1.18 = 4.24

## Página 46

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 45 ~

| REQUISITO | CASO 1 |
|---|---|
| Fecha de emission | 25/06/2017 |
| Firma Digital |  |
| Apellidos y nombres o denominación o razón social | Soporte Tecnológicos EIRL |
| Nombre Comercial | “Tu Soporte” |
| Número de RUC | 20100454523 |
| Código del tipo de Nota de débito electrónica | 07 |
| Numeración, conformada por serie y número correlativo | FD01-211 |
| Tipo y número de documento de identidad del adquirente o usuario | 20587896411 |
| Apellidos y nombres o denominación o razón social del adquirente o usuario | Servicabinas S.A. |
| Motivo o sustento | Ampliación de garantía de memoria DDR-B1333 Kingston |
| Número de orden del Ítem | 1 |
| Unidad de medida por ítem que modifica | ZZ |
| Cantidad de unidades por ítem que modifica | 1 |
| Código de producto | MPC35 |
| Código de producto SUNAT | 32101622 |
| Descripción detallada del bien vendido o cedido en uso, descripción o tipo de servicio prestado por ítem | Ampliación de garantía de 6 a 12meses de Memoria DDR-3 B1333 Kingston |
| Valor unitario por ítem que modifica | 4.24 |
| Precio de venta unitario por ítem que modifica | 5.00 |
| Afectación al IGV por ítem que modifica | 10 |
| IGV del ítem | 190.68 |
| Sistema de ISC por ítem que modifica |  |
| Valor de venta por ítem | 1059.32 |
| Total valor de venta - operaciones gravadas | 1059.32 |
| Total valor de venta - operaciones inafectas |  |
| Total valor de venta - operaciones exoneradas |  |
| Sumatoria IGV | 190.68 |
| Sumatoria ISC |  |
| Sumatoria otros tributes |  |
| Sumatoria otros Cargos |  |
| Total descuentos |  |
| Importe total | 1250.00 |
| Versión del UBL |  |
| Versión de la estructura del documento |  |
| Tipo de moneda en la cual se emite la nota de débito | PEN |
| Serie y número del documento que modifica | F001-4355 |
| Tipo de documento que modifica | 01 |
| Documento de referencia |  |
| Código de Establecimiento | 0001 |

## Página 47

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 46 ~

```xml
<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<DebitNote xmlns="urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2"
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
<ds:CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n￾20010315"/>
<ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
<ds:Reference URI="">
<ds:Transforms>
<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#envelopedsignature"/>
</ds:Transforms>
<ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
<ds:DigestValue>uy0/8Pg/62e+GIQ0ZRVRRCWmPBk=</ds:DigestValue>
</ds:Reference>
</ds:SignedInfo>
<ds:SignatureValue>
silqLF655RAWmwtd5llBQ2VqVa4gZus7e53ChvMBtXw+HOyR6oNPySTJKnrCZ0kRpfN3i3OgLlyC
b+Xfm9OlVOrVaYv0W4NM10hKrdCfWDxnGzhOxoXbqFL+jmRlhBsEQ+R6lcg9ctn60jyDWm+LtRR7
By6xzluFqdR0C5OtaiU=
</ds:SignatureValue>
...
</ext:ExtensionContent>
</ext:UBLExtension>
</ext:UBLExtensions>
```

## Página 48

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 47 ~

```xml
<cbc:UBLVersionID>2.1</cbc:UBLVersionID>
<cbc:CustomizationID>2.0</cbc:CustomizationID>
<cbc:ID>FD01-211</cbc:ID>
<cbc:IssueDate>2017-06-25</cbc:IssueDate>
<cbc:IssueTime>16:44:51</cbc:IssueTime>
<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
<cbc:Note languageLocaleID="3000">0501002017062500125</cbc:Note>
<cac:DiscrepancyResponse>
<cbc:ReferenceID>F001-4355</cbc:ReferenceID>
<cbc:ResponseCode>02</cbc:ResponseCode>
<cbc:Description> Ampliación garantía de memoria DDR-3 B1333
Kingston</cbc:Description>
</cac:DiscrepancyResponse>
<cac:BillingReference>
<cac:InvoiceDocumentReference>
<cbc:ID>F001-4355</cbc:ID>
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

## Página 49

Guía de elaboración de documentos electrónicos XML - UBL 2.1  
Nota de Débito Electrónica ~ 48 ~

```xml
<cac:TaxScheme>
<cbc:ID>-</cbc:ID>
</cac:TaxScheme>
</cac:PartyTaxScheme>
</cac:Party>
</cac:AccountingCustomerParty>
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">190.68</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">1059.32</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">190.68</cbc:TaxAmount>
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
<cbc:PayableAmount currencyID="PEN">1250.00</cbc:PayableAmount>
</cac:LegalMonetaryTotal>
<cac:DebitNoteLine>
<cbc:ID>1</cbc:ID>
<cbc:DebitedQuantity unitCode="ZZ">1</cbc:DebitedQuantity>
<cbc:LineExtensionAmount currencyID="PEN">1059.32</cbc:LineExtensionAmount>
<cac:PricingReference>
<cac:AlternativeConditionPrice>
<cbc:PriceAmount currencyID="PEN">5.00</cbc:PriceAmount>
<cbc:PriceTypeCode>01</cbc:PriceTypeCode>
</cac:AlternativeConditionPrice>
</cac:PricingReference>
<cac:TaxTotal>
<cbc:TaxAmount currencyID="PEN">190.68</cbc:TaxAmount>
<cac:TaxSubtotal>
<cbc:TaxableAmount currencyID="PEN">1059.32</cbc:TaxableAmount>
<cbc:TaxAmount currencyID="PEN">190.68</cbc:TaxAmount>
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
<cbc:Description> Ampliación de garantía de 6 a 12 meses de Memoria DDR-3 B1333
Kingston</cbc:Description>
<cac:SellersItemIdentification>
<cbc:ID> MPC35</cbc:ID>
</cac:SellersItemIdentification>
<cac:CommodityClassification>
<cbc:ItemClassificationCode listID="UNSPSC" listAgencyName="GS1 US"
listName="Item Classification">32101622</cbc:ItemClassificationCode>
</cac:CommodityClassification>
</cac:Item>
<cac:Price>
<cbc:PriceAmount currencyID="PEN">4.24</cbc:PriceAmount>
</cac:Price>
</cac:DebitNoteLine>
</DebitNote>
```
