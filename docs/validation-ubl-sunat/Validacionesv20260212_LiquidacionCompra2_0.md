# Validacionesv20260212

## Hoja: LiquidacionCompra2_0

## Datos de Cabecera
## 1. Fecha de emisión

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /SelfBilledInvoice/cbc:IssueDate

### Validaciones

1. **ERROR**
   - **Validación:** Si serie del documento no inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al límite del listado  (1 día)
   - **Mensaje:** Presentacion fuera de fecha
   - **Listados:** Parámetros (004)<br>Plazos Excepcionales

2. **OBSERV**
   - **Validación:** Si serie del documento inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor a 7 días
   - **Mensaje:** El comprobante fue enviado fuera del plazo permitido.

3. **ERROR**
   - **Validación:** El valor del Tag UBL es mayor a la fecha de envío del comprobante
   - **Mensaje:** La fecha de emision se encuentra fuera del limite permitido
   - **Listados:** -
## 2. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "hh:mm:ss"
- **Tag:** /SelfBilledInvoice/cbc:IssueTime

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 3. Firma Digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Formato / Valor:** -
- **Tag:** -

### Validaciones

1. **-**
   - **Validación:** <<< REVISAR HOJA FIRMA >>>
   - **Mensaje:** -
   - **Listados:** -
## 4. Versión del UBL

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.1"
- **Tag:** /SelfBilledInvoice/cbc:UBLVersionID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de "2.1"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 5. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.0"
- **Tag:** /SelfBilledInvoice/cbc:CustomizationID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no existe informacion de CustomizationID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de "2.0"
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 5. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 6. Codigo de tipo de operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 51)
- **Tag:** /SelfBilledInvoice/cbc:InvoiceTypeCode@listID

### Validaciones

1. **ERROR**
   - **Validación:** Si no existe el atributo o es vacío
   - **Mensaje:** Debe consignar el tipo de operación
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si valor del atributo es diferente al listado según el 'Tipo de documento'
   - **Mensaje:** El dato ingresado como tipo de operación no corresponde a un valor esperado (catálogo nro. 51)
   - **Listados:** Catálogo<br>(051)
## 6. Codigo de tipo de operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Operacion"
- **Tag:** @name

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Operacion'
   - **Mensaje:** El dato ingresado como atributo @name es incorrecto.
   - **Listados:** -
## 6. Codigo de tipo de operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51"
- **Tag:** @listSchemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51'
   - **Mensaje:** El dato ingresado como atributo @listSchemeURI es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda en la cual se emite la Liquidación de Compra Electrónica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** /SelfBilledInvoice/cbc:DocumentCurrencyCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de DocumentCurrencyCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El valor ingresado como moneda del comprobante no es valido (catalogo nro 02).
   - **Listados:** Catálogo<br>(002)
## 7. Tipo de moneda en la cual se emite la Liquidación de Compra Electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 4217 Alpha"
- **Tag:** @listID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 4217 Alpha'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda en la cual se emite la Liquidación de Compra Electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Currency"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Currency'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda en la cual se emite la Liquidación de Compra Electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 8. Tipo de documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "04"
- **Tag:** /SelfBilledInvoice/cbc:InvoiceTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de InvoiceTypeCode
   - **Listados:** -
## 8. Tipo de documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /SelfBilledInvoice/cbc:InvoiceTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al tipo de documento del archivo
   - **Mensaje:** InvoiceTypeCode - El valor del tipo de documento es invalido o no coincide con el nombre del archivo
   - **Listados:** Catálogo<br>(001)
## 8. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 8. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Documento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 8. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 9. Numeración, conformada por serie y número correlativo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /SelfBilledInvoice/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El número de serie del Tag UBL es diferente al número de serie del archivo
   - **Mensaje:** Numero de Serie del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **ERROR**
   - **Validación:** El número de comprobante del Tag UBL es diferente al número de comprobante del archivo
   - **Mensaje:** Número de documento en el nombre del archivo no coincide con el consignado en el contenido del XML
   - **Listados:** -

3. **ERROR**
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [L][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 1
   - **Mensaje:** El comprobante fue registrado previamente con otros datos
   - **Listados:** Comprobantes de pago electrónico

5. **ERROR**
   - **Validación:** Si la serie empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 2<br>Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 0 o 2
   - **Mensaje:** El comprobante ya esta informado y se encuentra con estado anulado o rechazado
   - **Listados:** Comprobantes de pago electrónico

6. **OBSERV**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado como comprobante de contingencia
   - **Listados:** Autorizaciones de comprobantes contingencia

7. **ERROR**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## Datos del emisor electrónico (comprador)
## 10. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## 11. Nombre comercial, si lo tuviere

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyName/cbc:Name

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line (Dirección completa y detallada)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo No. 13)
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no existe  en el listado
   - **Mensaje:** El codigo de ubigeo del domicilio fiscal del emisor no es válido
   - **Listados:** Catálogo<br>(013)
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 04)
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a PE
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** Catálogo<br>(004)
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 12. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 13. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de RUC)

### Validaciones

1. **ERROR**
   - **Validación:** Existe más de un Tag UBL cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification
   - **Mensaje:** El XML contiene mas de un tag como elemento de numero de documento del emisor
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al RUC del nombre del XML
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del Tag UBL tiene un ind_estado diferente "00" en el listado
   - **Mensaje:** El contribuyente no esta activo
   - **Listados:** Contribuyentes

4. **ERROR**
   - **Validación:** El valor del Tag UBL tiene un ind_condicion igual a "12" en el listado
   - **Mensaje:** El contribuyente no esta habido
   - **Listados:** Contribuyentes

5. **ERROR**
   - **Validación:** El valor del Tag UBL no se encuentra afecto a Renta de tercera categoría (no se considera el NRUS como tributo de Renta de tercera categoría)
   - **Mensaje:** Emisor no se encuentra afecto a Renta de tercera categoría
   - **Listados:** -

6. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el listado de Sujetos sin capacidad operativa (ind_padron igual a "02") y "Fecha de emisión" del comprobante comprendida en el rango de vigencia
   - **Mensaje:** El emisor electrónico es un Sujeto sin capacidad operativa (SSCO)
   - **Listados:** Padrones con vigencia
## 13. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** "6"
- **Tag:** /SelfBilledInvoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion en tipo de documento del emisor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del atributo es diferente a "6"
   - **Mensaje:** El dato ingresado no cumple con el estandar
   - **Listados:** -
## 13. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 13. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 13. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## Datos del vendedor
## 14. Tipo y número de documento de identidad del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)

### Validaciones

1. **ERROR**
   - **Validación:** Existe más de un Tag UBL en el XML cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification
   - **Mensaje:** El XML contiene mas de un tag como elemento de numero de documento del receptor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del número de documento de identidad del receptor del documento
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del vendedor' es '1', el valor del Tag UBL no está en el listado
   - **Mensaje:** Número de DNI no existe
   - **Listados:** Contribuyentes

4. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del vendedor' es '1', el valor del Tag UBL existe y se encuentra en condición de fallecido a la fecha de emisión
   - **Mensaje:** Número de DNI corresponde a una persona fallecida a la fecha de emision
   - **Listados:** Contribuyentes

5. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del vendedor' es '1', el valor del Tag UBL existe y pertenece a un menor de edad (menor a 18 años) a la fecha de emisión
   - **Mensaje:** Número de DNI corresponde a una persona menor de edad
   - **Listados:** Contribuyentes

6. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del vendedor' es '1', el valor del Tag UBL existe y tiene un Numero de RUC con estado diferente a '10', '11', '12','20' ,'30' y '31'
   - **Mensaje:** Número de DNI tiene un Numero de RUC asignado activo

7. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del vendedor' es igual a  '4' o '7',  el formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres (se considera cualquier carácter, no permite 'whitespace character': espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado como numero de documento de identidad del receptor no cumple con el formato establecido
   - **Listados:** -
## 14. Tipo y número de documento de identidad del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo No. 06)
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo
   - **Mensaje:** El XML no contiene el tag o no existe informacion del tipo de documento de identidad del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el valor del atributo es diferente de  '1', '4' y '7'
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** Parámetros (006)
## 14. Tipo y número de documento de identidad del vendedor

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 14. Tipo y número de documento de identidad del vendedor

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 14. Tipo y número de documento de identidad del vendedor

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 15. Apellidos y nombres del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..250
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line<br>(Dirección y los datos referenciales que permitan ubicar el domicilio del vendedor)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de la dirección completa y detallada en domicilio del vendedor
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 3 a 250 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe el tag, el valor del Tag UBL empieza con espacio en blanco o TAB
   - **Mensaje:** La dirección completa y detallada del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..25
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo No. 13)
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del ubigeo del domicilio del vendedor
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** El codigo de ubigeo del domicilio del vendedor no es válido
   - **Listados:** Catálogo<br>(013)
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an6
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an6
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio del vendedor no cumple con el formato establecido
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 04)
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a PE
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** Catálogo<br>(004)
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 16. Domicilio del vendedor

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "Country"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 17. Condición del domicilio del vendedor: punto de venta, producción, extracción y/o explotación de los productos o ninguno

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 60)
- **Tag:** /SelfBilledInvoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:AddressTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** Debe consignar el tipo de domicilio del vendedor
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El dato ingresado en el tipo de domicilio del vendedor no corresponde al valor esperado
   - **Listados:** Catálogo<br>(60)
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..250
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cac:AddressLine/cbc:Line<br>(Dirección y los datos referenciales que permitan ubicar el lugar donde se realiza la operación)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de la dirección completa y detallada del lugar donde se realiza la operación
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 3 a 250 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.).
   - **Mensaje:** El valor ingresado como direccion completa y detallada no cumple con el estandar.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe el tag, el valor del Tag UBL empieza con espacio en blanco o TAB
   - **Mensaje:** El valor ingresado como direccion completa y detallada no cumple con el estandar.
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..25
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cbc:CitySubdivisionName (Urbanización)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como urbanización no cumple con el formato establecido
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cbc:CityName (Provincia)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como provincia no cumple con el formato establecido
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo No. 13)
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cbc:ID (Código de ubigeo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del ubigeo del lugar donde se realiza la operación
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Catálogo<br>(013)
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cbc:CountrySubentity (Departamento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como departamento no cumple con el formato establecido
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cbc:District (Distrito)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como distrito no cumple con el formato establecido
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 04)
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cac:Address/cac:Country/cbc:IdentificationCode (Código de país)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a PE
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** Catálogo<br>(004)
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 18. Ubicación del lugar donde se realiza la operación

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "Country"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 19. Condición de la ubicación del lugar donde se realiza la operación: punto de venta, producción, extracción y/o explotación de los productos o ninguno

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 60)
- **Tag:** /SelfBilledInvoice/cac:DeliveryTerms/cac:DeliveryLocation/cbc:LocationTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** Debe consignar el tipo de ubicación del lugar donde se realiza la operación
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado.
   - **Mensaje:** El dato ingresado en el tipo de ubicación del lugar donde se realiza la operación no corresponde al valor esperado
   - **Listados:** Catálogo<br>(60)
## DETALLE POR CADA ÍTEM. Registro DETALLE POR CADA ÍTEM
## 20. Número de orden del ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..5
- **Formato / Valor:** n..3
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos; o, es igual cero.
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro cac:InvoiceLine con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 21. Cantidad de unidades por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cbc:InvoicedQuantity

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es cero (0)
   - **Mensaje:** El XML no contiene el tag InvoicedQuantity en el detalle de los Items o es cero (0)
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales
   - **Mensaje:** InvoicedQuantity El dato ingresado no cumple con el estandar
   - **Listados:** -
## 22. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo No. 03)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cbc:InvoicedQuantity/@unitCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** Es obligatorio indicar la unidad de medida del ítem
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al Catálogo N.° 03
   - **Mensaje:** El dato ingresado como unidad de medida no corresponde al valor esperado
   - **Listados:** Catálogo<br>(003)
## 22. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Formato / Valor:** "UN/ECE rec 20"
- **Tag:** @unitCodeListID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UN/ECE rec 20'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListID es incorrecto.
   - **Listados:** Catálogo<br>(003)
## 22. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @unitCodeListAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListAgencyName es incorrecto.
   - **Listados:** -
## 23. Descripción detallada del producto comprado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..500
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cbc:Description

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag cac:Item/cbc:Description en el detalle de los Items
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 hasta 500 caracteres (se considera cualquier carácter, permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El XML no contiene el tag o no existe informacion de cac:Item/cbc:Description del item
   - **Listados:** -
## 24. Código de producto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID

### Validaciones

1. **OBSERV**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente de alfanumérico de 1 a 30 carácteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como codigo de producto no cumple con el formato establecido.
   - **Listados:** -
## 25. Código de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** (Catálogo No. 25)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:CommodityClassification/cbc:ItemClassificationCode

### Validaciones

1. **OBSERV**
   - **Validación:** Si el valor del Tag UBL es diferente de vacío, el valor no se encuentra en el listado
   - **Mensaje:** El Código producto de SUNAT no es válido
   - **Listados:** Catálogo<br>(025)

2. **OBSERV**
   - **Validación:** Si el tag existe y tiene una longitud de 8 posiciones, el valor del Tag UBL termina en 6 ceros ('000000') o termina en 4 ceros ('0000')
   - **Mensaje:** El Codigo de producto SUNAT debe especificarse como minimo al tercer nivel jerarquico (a nivel de clase del codigo UNSPSC)
   - **Listados:** Catálogo<br>(025)
## 25. Código de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "UNSPSC"
- **Tag:** @listID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UNSPSC'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 25. Código de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "GS1 US"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'GS1 US'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 25. Código de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Item Classification"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Item Classification'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 26. Valor unitario por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Price/cbc:PriceAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:Price/cbc:PriceAmount en el detalle de los Items
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero.<br>Nota: En casos de enteros, el valor monetario no debe tener ceros a la izquierda.
   - **Mensaje:** El dato ingresado en PriceAmount del Valor de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe en la línea un cac:TaxSubTotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es mayor a 0 (cero)
   - **Mensaje:** Operacion gratuita, solo debe consignar un monto referencial
   - **Listados:** -
## 26. Valor unitario por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceAmount (Monto de precio de compra)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** Debe existir el tag cac:AlternativeConditionPrice
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PriceAmount del Precio de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al resultado de dividir: la sumatoria del 'Valor de venta por ítem' más el 'Monto total de tributos del ítem', entre la 'Cantidad de unidades por ítem' (con una tolerancia + -1)
   - **Mensaje:** El precio unitario de la operación que está informando difiere de los cálculos realizados en base a la información remitida
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) (Operaciones gratuitas), y 'Código de precio' es '02' (Valor referencial en operaciones no onerosas), el Tag UBL es mayor a 0 (cero).
   - **Mensaje:** Si existe 'Valor referencial unitario en operac. no onerosas' con monto mayor a cero, la operacion debe ser gratuita (codigo de tributo 9996)
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) (Operaciones gratuitas), y 'Código de precio' es diferente de '02' (Valor referencial en operaciones no onerosas).
   - **Mensaje:** El código de precio '02' es sólo para operaciones gratuitas
   - **Listados:** -
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 16)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceTypeCode (Código de tipo de precio)

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** Se ha consignado un valor invalido en el campo cbc:PriceTypeCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe en el mismo ítem otro cac:AlternativeConditionPrice con el mismo valor del Tag UBL (cbc:PriceTypeCode)
   - **Mensaje:** Existe mas de un tag cac:AlternativeConditionPrice con el mismo cbc:PriceTypeCode
   - **Listados:** Catálogo<br>(016)
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Precio"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Precio'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 27. Precio de venta unitario por Item<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 28. Valor de venta por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en LineExtensionAmount del item no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si existe en la línea un cac:TaxSubTotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el importe es diferente al resultado de multiplicar el 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' por 'Cantidad de unidades por ítem',  con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si no existe en la línea un cac:TaxSubTotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor difiere del resultado del Valor unitario por ítem por la Cantidad de unidades por ítem, con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -
## 28. Valor de venta por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## Información adicional  - comercialización del oro. Registro Información adicional  - comercialización del oro
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo No. 55)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 55)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '0503', y no existe el tag con valor '6000'
   - **Mensaje:** El XML no contiene el tag de Comercializacion del oro: Codigo unico de concesion minera
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '0503', y no existe el tag con valor '6004'
   - **Mensaje:** El XML no contiene el tag de Comercializacion del oro: Ley mineral
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '0503', y no existe el tag con valor '6005'
   - **Mensaje:** El XML no contiene el tag de Comercializacion del oro: Naturaleza del mineral
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '0503', y no existe el tag con valor '6006'
   - **Mensaje:** El XML no contiene el tag de Comercializacion del oro: Nombre del derecho minero
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código del derecho minero)

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '6000', '6004',6005' o '6006', no existe el tag, o existe con valor vacío
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **-**
   - **Validación:** <<<SIN VALIDACION>>>
   - **Mensaje:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(3,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ley Mineral (contenido metalico))

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '6004' y el formato del tag es diferente de decimal de hasta 3 enteros y 2 decimales
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** n1
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Naturaleza del mineral)

### Validaciones

1. **-**
   - **Validación:** <<<SIN VALIDACION>>>
   - **Mensaje:** -
## 29. Código del derecho minero<br>Ley mineral (contenido metalico)<br>Naturaleza del mineral<br>Nombre del derecho minero

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Nombre del derecho minero)

### Validaciones

1. **-**
   - **Validación:** <<<SIN VALIDACION>>>
   - **Mensaje:** -
## 30. Monto total de tributos del ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount (Monto total de tributos del item)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag cac:InvoiceLine/cac:TaxTotal
   - **Mensaje:** El xml no contiene el tag de impuesto por linea (TaxtTotal).
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Monto de tributo por línea' (cbc:TaxAmount)  de los tributos '1000' y '9999', con una tolerancia + -1
   - **Mensaje:** El importe total de impuestos por línea no coincide con la sumatoria de los impuestos por línea.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Existe en el mismo ítem más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de Item
   - **Listados:** -
## 30. Monto total de tributos del ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es diferente del 'Valor de venta  por ítem'
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID (Moneda base)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a  '9997' o '9998', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), y la 'Afectación al IGV' es '11', '12', '13', '14', '15' o '16', el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y la 'Afectación al IGV ' es  '21', '31', '32', '33', '34', '35', '36' o '37', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000' y<br>'Monto base' mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si 'Afectación al IGV  es '10','11', '12', '13', '14', '15' o '16', el valor del tag es diferente a la tasa del tributo por el monto base Imponible IGVde la línea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación del IGV/IVAP no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del IGV)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) y la 'Afectación al IGV' es '11', '12', '13', '14', '15' o '16', el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000'  y  'Monto base' mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 07)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode (Afectación al IGV)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '9999' y  (Otros tributos) y '3000', cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cbc:TaxExemptionReasonCode de Afectacion al IGV
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9999' (Otros tributos) o '3000', existe el tag UBL
   - **Mensaje:** Afectación de IGV no corresponde al código de tributo de la linea.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '9999' (Otros tributos) y '3000', cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al listado según su código de tributo.
   - **Mensaje:** El tipo de afectacion del IGV es incorrecto
   - **Listados:** Catálogo<br>(007)
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "Afectacion del IGV"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Afectacion del IGV'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -

4. **ERROR**
   - **Validación:** No existe en el ítem un cac:TaxSubtotal con monto base mayor a cero (cbc:TaxableAmount > 0) y cbc:ID con alguno de los siguientes valores: '1000', '9996', '9997' o '9998'
   - **Mensaje:** El XML debe contener al menos un tributo por linea de afectacion por IGV
   - **Listados:** -

5. **ERROR**
   - **Validación:** En una línea sólo pueden existir las siguientes combinaciones de códigos de tributos con 'Monto base' mayor a cero (cbc:TaxableAmount  > 0):<br>- '1000', '3000', y/o '9999'<br>- '9996', '3000'  y/o  '9999'<br>- '9997', '3000'  y/o  '9999'<br>- '9998', '3000'  y/o  '9999'
   - **Mensaje:** La combinación de tributos no es permitida
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 31. Afectación al IGV por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** El valor del tag es diferente del 'Valor de venta  por ítem'
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de Retención de Renta)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es diferente a la tasa del tributo por el monto base de la línea (con una tolerancia + - 1)
   - **Mensaje:** El producto de la tasa por el monto base de la afectación de la retención de renta no corresponde al monto de afectacion de linea
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del impuesto)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código de tributo por ítem' es igual a '3000' y 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es diferente a alguna de las tasas vigentes de Retención de renta a la fecha de emisión
   - **Mensaje:** La tasa del tributo de la línea no corresponde al valor esperado
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 32. Impuesto a la Renta- Retención por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de otros tributos)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el 'Código de tributo por línea' es '9999' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente a la tasa del tributo por el monto base Otros tributos de la linea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación de otros tributos no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del impuesto)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 33. Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional tributo - Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## RESUMEN DEL IMPORTE TOTAL DE LA LIQUIDACIÓN DE COMPRA ELECTRÓNICA. Registro RESUMEN DEL IMPORTE TOTAL DE LA LIQUIDACIÓN DE COMPRA ELECTRÓNICA
## 34. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cbc:TaxAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag /SelfBilledInvoice/cac:TaxTotal
   - **Mensaje:** El Monto total de impuestos es obligatorio

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente de la sumatoria de 'Monto de la sumatoria' (cbc:TaxAmount) de los  tributos '1000' y '9999',  con una tolerancia + - 1
   - **Mensaje:** La sumatoria de impuestos globales no corresponde al monto total de impuestos.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Existe a nivel global más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de totales
   - **Listados:** -
## 34. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** an3
- **Tag:** (Catálogo N.° 02)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount  (Total valor de venta - operaciones gravadas )

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '1000' y  el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IGV con 'Código de tributo por línea' igual a '1000' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), menos los 'Monto del valor de venta de anticipos gravados' (Código '04'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gravadas de línea no corresponden al total
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Total Importe IGV/Total IGV Crédito)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si  'Código de tributo' es '1000', el valor del Tag UBL es diferente al resultado de multiplicar la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '1000',  menos los 'Monto del valor de venta de anticipos gravados' (Código '04') por la tasa vigente al IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El cálculo del IGV es Incorrecto
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -
## 35<br>36. Total valor de venta  - operaciones gravadas<br>Total importe IGV/Total IGV Crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta - operaciones inafectas / Total valor de venta - operaciones exoneradas)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el 'Código de tributo' es '9997', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones exoneradas con 'Código de tributo de línea' igual a '9997' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), menos los 'Monto del valor de venta de anticipos exonerados' (Código '05'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones exoneradas de línea no corresponden al total
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si el 'Código de tributo' es '9998', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones inafectas con 'Código de tributo de línea' igual a '9998' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), menos los 'Monto del valor de venta de anticipos inafectos' (Código '06'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones inafectas de línea no corresponden al total
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si 'Código de tributo' igual a '9997' (Exonerada)  y existe 'Código de leyenda' igual a '2001', el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2001, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

6. **OBSERV**
   - **Validación:** Si 'Código de tributo' igual a '9997' (Exonerada) y existe 'Código de leyenda' igual a '2002', el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2002, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

7. **OBSERV**
   - **Validación:** Si 'Código de tributo' igual a '9997' (Exonerada) y existe 'Código de leyenda' igual a '2003', el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2003, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

8. **OBSERV**
   - **Validación:** Si 'Código de tributo' igual a '9997' (Exonerada) y 'Código de leyenda' es '2008', el valor del Tab UBL es igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2008, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** "0.00"
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Importe del tributo)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag Ubl es diferente de 0 (cero), cuando el 'Código de tributo' es  '9997' y '9998'
   - **Mensaje:** El monto total del impuestos sobre el valor de venta de operaciones gratuitas/inafectas/exoneradas debe ser igual a 0.00
   - **Listados:** -
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 37<br>38. Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta - operaciones gratuitas)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '9996', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gratuitas de línea no corresponden al total
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo' es '9996' (Gratuita) y existe una línea con 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' ('Código de precio' igual a '02') con monto mayor a cero, el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Operacion gratuita,  debe consignar Total valor venta - operaciones gratuitas  mayor a cero
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Código de tributo' es '9996' (Gratuita) y 'Código de leyenda' es '1002', el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Si existe leyenda Transferencia Gratuita debe consignar Total Valor de Venta de Operaciones Gratuitas
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Importe del tributo)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si  'Código de tributo' es '9996', el valor del Tag Ubl es diferente de la sumatoria de 'Monto de IGV' (cbc:TaxAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria de los IGV de operaciones gratuitas de la línea (codigo tributo 9996) no corresponden al total
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 39<br>40. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount  (Monto Base)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El monto base de la retencion de renta global no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '3000' y  el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones afectas a la Retención de renta con 'Código de tributo por línea' igual a '3000' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) y que no correspondan a una operación gratuita (*), con una tolerancia + - 1<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** El monto base global de la retencion de renta no coincide con el valor calculado
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Importe de la retención)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '3000', el valor del Tag UBL es diferente a la sumatoria de 'Monto de retención de renta por item' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '3000'  y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0)  y que no correspondan a una operación gratuita (*), menos 'Monto de retención de renta de anticipo' (Código de motivo de descuento '61'), con una tolerancia + - 1<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** El importe de la retencion de renta no coincide con el valor calculado
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -
## 41. Importe de la retención del impuesto a la renta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si existe el Tag y el 'Código de tributo' es '9999', el valor del Tag UBL es diferente a la sumatoria de los 'Montos base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '9999' (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del monto base - Otros tributos de línea no corresponden al total
   - **Listados:** -
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount  (Monto de la Sumatoria)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si  'Código de tributo' es '9999', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '9999', con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total del importe del tributo Otros tributos de línea no corresponden al total
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 42. Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 05)
- **Tag:** /SelfBilledInvoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 43. Total valor de venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:LegalMonetaryTotal/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag UBL
   - **Mensaje:** No existe el tag cac:LegalMonetaryTotal/cbc:LineExtensionAmount
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en total valor de venta no cumple con el estandar
   - **Listados:** -

3. **OBSERV**
   - **Validación:** El valor del tag es diferente de la sumatoria del 'Valor de venta por ítem' (cbc:LineExtensionAmount) de los ítems con 'Código de tributo por línea' igual a  '1000',  '9997' y '9998'  y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia de + - 1
   - **Mensaje:** La sumatoria de valor de venta no corresponde a los importes consignados
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si serie del comprobante de pago inicia con L,<br>el vendedor supera el monto máximo anual permitido para la emisión (no puede sobrepasar el monto límite de emisión de 75 UIT anuales contabilizados a partir del 1 de enero de cada año y hasta el día anterior a la fecha de emisión (cbc:IssueDate menos 1 día))
   - **Mensaje:** Vendedor supera el monto permitido para la emision de una liquidacion de compra
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si serie del comprobante de pago inicia con número, el vendedor supera el monto máximo anual permitido para la emisión (no puede sobrepasar el monto límite de emisión de 75 UIT anuales contabilizados a partir del 1 de enero de cada año y hasta el día anterior a la fecha de emisión (cbc:IssueDate menos 1 día))
   - **Mensaje:** Vendedor supera el monto permitido para la emision de una liquidacion de compra
   - **Listados:** -
## 43. Total valor de venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 44. Sub total de la liquidación de compra

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:LegalMonetaryTotal/cbc:TaxInclusiveAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag UBL
   - **Mensaje:** No existe el tag cac:LegalMonetaryTotal/cbc:TaxInclusiveAmount
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en total precio de venta no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si existe el Tag UBL, el valor es diferente de la sumatoria de 'Total valor de venta' más 'Sumatoria Otros Tributos' más el resultado de:<br>Multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1000' por la tasa vigente del IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** La sumatoria del Total del valor de venta más los impuestos no concuerda con la base imponible
   - **Listados:** -
## 44. Sub total de la liquidación de compra

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 45. Monto para redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:LegalMonetaryTotal/cbc:PayableRoundingAmount

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag UBL, el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 45. Monto para redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 46. Importe total neto

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:LegalMonetaryTotal/cbc:PayableAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PayableAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el valor del tag difiere del siguiente cálculo:<br>'Sub total de la liquidación de compra'<br>menos<br>sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '1000'<br>menos<br>sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '3000'<br>menos<br>sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '9999'<br>menos<br>'Total anticipos' de corresponder<br>más<br>'Monto de redondeo del importe total'<br>con una tolerancia de + - 1
   - **Mensaje:** El importe total del comprobante no coincide con el valor calculado
   - **Listados:** -
## 46. Importe total neto

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Información adicional  - anticipos. Registro Información adicional  - anticipos
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /SelfBilledInvoice/cac:PrepaidPayment/cbc:ID (Identificador del pago)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Importe del anticipo' existe y no existe el Tag UBL o es vacio
   - **Mensaje:** Falta identificador del pago del Monto de anticipo para relacionarlo con el comprobante que se realizo el  anticipo

2. **ERROR**
   - **Validación:** Si existe más de un 'Identificador de pago' con el mismo valor
   - **Mensaje:** El comprobante contiene un identificador de pago repetido en los montos anticipados
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si no existe documento con 'Tipo de comprobante que se realizó el anticipo' (/SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode con valor '10') con el mismo 'Identificador de pago' (cbc:DocumentStatusCode) que el valor del Tag UBL
   - **Mensaje:** El comprobante contiene un pago anticipado pero no se ha consignado el documento que se realizo el anticipo
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** "Anticipo"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Anticipo'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:PrepaidPayment/cbc:PaidAmount (Importe del anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe y es menor o igual a 0 (cero)
   - **Mensaje:** PaidAmount: monto anticipado por documento debe ser mayor a cero.

2. **ERROR**
   - **Validación:** Si existe Tag UBL con valor mayor a cero, y no existe 'Total Anticipos' con monto mayor a cero
   - **Mensaje:** Si consigna montos de anticipo debe informar el Total de Anticipos
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /SelfBilledInvoice/cac:PrepaidPayment/cbc:PaidDate (Fecha de pago)

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:DocumentStatusCode (Identificador del pago)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '10', y no existe un 'Importe del anticipo' con 'Identificador de pago' igual al valor del tag UBL
   - **Mensaje:** No existe información del Monto Anticipado para el comprobante que se realizo el anticipo

2. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '10', y existe más de un comprobante de anticipo con el mismo identificador de pago en el comprobante
   - **Mensaje:** El comprobante contiene un identificador de pago repetido en los comprobantes que se realizo el anticipo

3. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '10', y no existe el tag UBL
   - **Mensaje:** Falta identificador del pago del comprobante para relacionarlo con el monto de  anticipo
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** "Anticipo"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Anticipo'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:ID (Serie y Número de comprobante que se realizó el anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de documento del emisor del anticipo' existe y 'Tipo de comprobante que se realizo el anticipo' es '10' (Liquidación de compra), el formato del Tag UBL  es diferente a:<br>- [L][A-Z0-9]{3}-[0-9]{1,8}<br>- (E001)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** El dato ingresado debe indicar SERIE-CORRELATIVO del documento que se realizo el anticipo.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 12)
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de comprobante que se realizó el anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode), y el valor del tag UBL es diferente a '10' (Liquidación de compra)
   - **Mensaje:** Tipo de comprobante que realizo el anticipo debe ser 10-Liquidacion de compra
   - **Listados:** Catálogo<br>(012)
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento Relacionado"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento Relacionado'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an11
- **Formato / Valor:** n11
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification/cbc:ID (Número de documento del emisor del anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y no existe el tag o es vacío
   - **Mensaje:** Debe consignar Numero de RUC del emisor del comprobante de anticipo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y el valor del Tag UBL no existe en el listado
   - **Mensaje:** RUC que emitio documento de anticipo no existe.
   - **Listados:** Contribuyentes

3. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y 'Serie del comprobante que realizó el anticipo' empieza con 'L' o 'E', y RUC del emisor del anticipo es igual al RUC emisor de la liquidación de compra, la 'Serie y número del comprobante que realizó el anticipo' no existe con estado aceptado en el listado para el RUC consignado en el emisor del anticipo
   - **Mensaje:** El comprobante que se realizo el anticipo no existe
   - **Listados:** Comprobantes de pago electrónico

4. **OBSERV**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y 'Serie del comprobante que realizó el anticipo' empieza con número, y RUC del emisor del anticipo es igual al RUC emisor de la liquidación de compra, la 'Serie y número del comprobante que realizó el anticipo' no existe en el listado para el RUC consignado en el emisor del anticipo
   - **Mensaje:** El comprobante que se realizo el anticipo no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento del emisor del anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo no existe o es diferente a 6 (RUC)
   - **Mensaje:** El tipo documento del emisor que realiza el anticipo debe ser 6 del catalogo de tipo de documento.
   - **Listados:** Catálogo<br>(006)
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 47. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:<br>catalogo06"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "false"
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Codigo de motivo de descuento' es '04', '05', '06' o '61', el valor del Tag UBL es diferente de 'false'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 53)
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de descuento -  tipo de anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:Amount (Montos del valor de venta de anticipos)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -
## 48. Montos del valor de venta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "false"
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Codigo de motivo de descuento' es '04', '05', '06' o '61', el valor del Tag UBL es diferente de 'false'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "61"
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de descuento -  deducción de retención de renta en anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:AllowanceCharge/cbc:Amount (Monto de retención de renta de anticipo)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -
## 49. Montos de retenciones de renta de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## 50. Total de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SelfBilledInvoice/cac:LegalMonetaryTotal/cbc:PrepaidAmount

### Validaciones

1. **ERROR**
   - **Validación:** Si existe Tag UBL con valor mayor a cero, la suma de los 'Importe del anticipo' es diferente al valor del tag UBL
   - **Mensaje:** Total de anticipos diferente a los montos anticipados por documento.
   - **Listados:** -
## 50. Total de anticipos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Información adicional. Registro Información adicional
## 51. Leyenda “TRANSFERENCIA GRATUITA”

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 52)
- **Tag:** /SelfBilledInvoice/cbc:Note@languageLocaleID (Código de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo existe, el valor del atributo no existe en el listado
   - **Mensaje:** El valor del atributo no se encuentra en el catálogo
   - **Listados:** Catálogo<br>(052)

2. **ERROR**
   - **Validación:** El valor del atributo se repite en el comprobante
   - **Mensaje:** El codigo de leyenda no debe repetirse en el comprobante.
   - **Listados:** -
## 51. Leyenda “TRANSFERENCIA GRATUITA”

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Formato / Valor:** (Catálogo No. 52)
- **Tag:** /SelfBilledInvoice/cbc:Note  (Descripción de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** Si el formato del Tag UBL es diferente a alfanumérico de 1 a 200 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en descripcion de leyenda no cumple con el formato establecido.
   - **Listados:** -
## 52. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:DespatchDocumentReference/cbc:ID (Número de documento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a:<br>- [T][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{4}-[0-9]{1,8}<br>- [EG0][1-4]{1}-[0-9]{1,8}<br>- [EG07] {4}-[0-9]{1,8}<br>- [G][0-9]{3}-[0-9]{1,8}<br>- [V][A-Z0-9]{3}-[0-9]{1,8}
   - **Mensaje:** El ID de las guias debe tener informacion de la SERIE-NUMERO de guia.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de la guía de remisión relacionada" concatenada con el valor del Tag UBL se repite en el /SelfBilledInvoice
   - **Mensaje:** El comprobante contiene un tipo y número de Guía de Remisión repetido
   - **Listados:** -
## 52. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /SelfBilledInvoice/cac:DespatchDocumentReference/cbc:DocumentTypeCode (Tipo de guía relacionada)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el "Número de la guía de remisión relacionada", el formato del Tag UBL es diferente de '09', '31'
   - **Mensaje:** El DocumentTypeCode de las guias debe ser 09 o 31
   - **Listados:** Catálogo<br>(001)
## 52. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 52. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "Tipo de Documento "
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 52. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 53. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:ID<br>(Número de documento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El ID de los documentos relacionados no cumplen con el estandar.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de otro documento relacionado" concatenada con el valor del Tag UBL se repite en el /SelfBilledInvoice
   - **Mensaje:** El comprobante contiene un tipo y número de Documento Relacionado repetido
   - **Listados:** -
## 53. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 12)
- **Tag:** /SelfBilledInvoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de documento relacionado)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor del Tag UBL es diferente de '10' y '99'
   - **Mensaje:** El DocumentTypeCode de Otros documentos relacionados tiene valores incorrectos.
   - **Listados:** Catálogo<br>(012)
## 53. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 53. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento Relacionado"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a ' Documento Relacionado'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 53. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## Datos adicionales para el traslado de bienes. Registro Datos adicionales para el traslado de bienes
