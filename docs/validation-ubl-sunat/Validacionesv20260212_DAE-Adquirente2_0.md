# Validacionesv20260212

## Hoja: DAE-Adquirente2_0

## Fecha de emisión y Mecanismo de seguridad
## 1. Fecha de emisión

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cbc:IssueDate

### Validaciones

1. **ERROR**
   - **Validación:** Si serie del documento no inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al plazo máximo vigente.
   - **Mensaje:** Presentacion fuera de fecha
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es mayor a dos días de la fecha de envío del comprobante
   - **Mensaje:** La fecha de emision se encuentra fuera del limite permitido
   - **Listados:** -
## 2. Firma digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Tag:** /Invoice/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature<br>/Invoice/cac:Signature

### Validaciones

1. **Validación**
   - **Validación:** <<< REVISAR HOJA FIRMA >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del documento autorizado - Adquirente en los sistemas de pago con tarjetas crédito y débito. Datos del documento autorizado - Adquirente en los sistemas de pago con tarjetas crédito y débito

- **Nivel:** Datos del documento autorizado - Adquirente en los sistemas de pago con tarjetas crédito y débito
- **Condición informática:** Datos del documento autorizado - Adquirente en los sistemas de pago con tarjetas crédito y débito
## 3. Versión del UBL

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.1"
- **Tag:** /Invoice/cbc:UBLVersionID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '2.1'
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 4. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.0"
- **Tag:** /Invoice/cbc:CustomizationID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no existe informacion de CustomizationID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '2.0'
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 4. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 5. Numeración, conformada por serie y número correlativo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Invoice/cbc:ID

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
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [F][A-Z0-9]{3}-[0-9]{1,8}
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante
   - **Listados:** -

4. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el listado con indicador de estado igual a 1
   - **Mensaje:** El comprobante fue registrado previamente con otros datos
   - **Listados:** Comprobantes de pago electrónico

5. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el listado con indicador de estado igual a 0 o 2
   - **Mensaje:** El comprobante ya esta informado y se encuentra con estado anulado o rechazado
   - **Listados:** Comprobantes de pago electrónico
## 6. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** hh:mm:ss
- **Tag:** /Invoice/cbc:IssueTime

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 7. Tipo de documento autorizado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "30"<br>"42"
- **Tag:** /Invoice/cbc:InvoiceTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de InvoiceTypeCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a '30' y '42'
   - **Mensaje:** InvoiceTypeCode - El valor del tipo de documento es invalido o no coincide con el nombre del archivo
   - **Listados:** -
## 8. Periodo de abono: Fecha desde

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoicePeriod/cbc:StartDate

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El documento no contiene la fecha de inicio del periodo de abono
   - **Listados:** -
## 9. Periodo de abono: Fecha hasta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoicePeriod/cbc:EndDate

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El documento no contiene la fecha de fin del periodo de abono
   - **Listados:** -
## 10. Tipo de canal facturado (fisico/virtual)

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "01" : Físico<br>"02" : Virtual
- **Tag:** /Invoice/cbc:InvoiceTypeCode@listID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El documento no contiene el 'Tipo de canal facturado'
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a '01' y '02'
   - **Mensaje:** El dato ingresado como 'Tipo de canal facturado' es incorrecto
   - **Listados:** -
## Datos del Adquirente en los sistemas de pago con tarjeta crédito y débito. Datos del Adquirente en los sistemas de pago con tarjeta crédito y débito

- **Nivel:** Datos del Adquirente en los sistemas de pago con tarjeta crédito y débito
- **Condición informática:** Datos del Adquirente en los sistemas de pago con tarjeta crédito y débito
## 11. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de RUC)

### Validaciones

1. **ERROR**
   - **Validación:** Existe más de un Tag UBL cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification
   - **Mensaje:** El XML contiene mas de un tag como elemento de numero de documento del emisor
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al RUC del nombre del XML
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del Tag UBL tiene un ind_estado diferente '00' en el listado
   - **Mensaje:** El contribuyente no esta activo
   - **Listados:** Contribuyentes

4. **ERROR**
   - **Validación:** El valor del Tag UBL tiene un ind_condicion igual a '12' en el listado
   - **Mensaje:** El contribuyente no esta habido
   - **Listados:** Contribuyentes

5. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el listado de Sujetos sin capacidad operativa (ind_padron igual a "02") y "Fecha de emisión" del comprobante comprendida en el rango de vigencia
   - **Mensaje:** El emisor electrónico es un Sujeto sin capacidad operativa (SSCO)
   - **Listados:** Padrones con vigencia
## 11. Tipo de documento de identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** "6"
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion en tipo de documento del emisor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a '6'
   - **Mensaje:** El dato ingresado no cumple con el estandar
   - **Listados:** -
## 12. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## Datos del  Establecimiento afiliado (receptor)
## 13. Numero de documento de identidad del receptor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)

### Validaciones

1. **ERROR**
   - **Validación:** Existe más de un Tag UBL en el XML cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification
   - **Mensaje:** El XML contiene mas de un tag como elemento de numero de documento del receptor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion del número de documento de identidad del receptor del documento
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es '6', el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El numero de documento de identidad del receptor debe ser  RUC
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es 6, el valor del Tag UBL no está en el listado
   - **Mensaje:** El numero de RUC del receptor no existe.
   - **Listados:** Contribuyentes

5. **OBSERV**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es '6', el valor del Tag UBL tiene un ind_estado diferente a '00' en el listado
   - **Mensaje:** El RUC  del receptor no esta activo
   - **Listados:** Contribuyentes

6. **OBSERV**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es 6, el valor del Tag UBL tiene un ind_condicion igual a '12' en el listado
   - **Mensaje:** El RUC del receptor no esta habido
   - **Listados:** Contribuyentes

7. **OBSERV**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es '1', el formato del Tag UBL es diferente a numérico de 8 dígitos
   - **Mensaje:** El DNI debe tener 8 caracteres numéricos
   - **Listados:** -

8. **OBSERV**
   - **Validación:** Si 'Tipo de documento de identidad del adquiriente' es '4' o '7' o '0' o 'A' o 'B' o 'C' o 'D' o 'E', el formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres (se considera cualquier carácter, no permite 'whitespace character': espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado como numero de documento de identidad del receptor no cumple con el formato establecido
   - **Listados:** -
## 13. Tipo de documento  de identidad del receptor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo No. 06)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del tipo de documento de identidad del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** Catálogo<br>(006)
## 14. Apellidos y nombres, denominación o razón social del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## 15. Nombre Comercial

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyName/cbc:Name

### Validaciones

1. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del cliente no cumple con el formato establecido
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line<br>(Dirección completa y detallada)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo No. 13)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL debe estar en el listado
   - **Mensaje:** El código de Ubigeo no existe en el listado.
   - **Listados:** Catálogo<br>(013)
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 04)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 16. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas. Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas

- **Nivel:** Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas
- **Condición informática:** Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas
## 17. Número de orden

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos, o es igual cero
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro ítem (cac:InvoiceLine) con el mismo valor del Tag UBL
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 18. Indicador de tipo de comisión

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** "1"<br>(Bancos emisores)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag
   - **Mensaje:** Debe registrarse el 'Indicador de tipo de comisión'
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '1' y '2'
   - **Mensaje:** El dato ingresado en el 'Indicador de tipo de comisión' no corresponde al valor esperado
   - **Listados:** -
## 19. Indicador de institución financiera

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** "1" Emisor Local<br>"2" Emisor Foráneo
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID@schemeID

### Validaciones

1. **ERROR**
   - **Validación:** Si el indicador de tipo de comisión es igual a '1' y el atributo no existe
   - **Mensaje:** Para Bancos emisores debe ingresar el 'Indicador de institución financiera'
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el indicador de tipo de comisión es igual a '1' y el valor del atributo es diferente de '1' y '2'
   - **Mensaje:** El dato ingresado en el 'Indicador de institución financiera' no corresponde al valor esperado
   - **Listados:** -
## 20. Total comisiones

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en LineExtensionAmount del item no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente de la sumatoria de  'Comisión del banco emisor' del detalle por banco emisor (/Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:LineExtensionAmount), con una tolerancia de + - 1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cbc:LineExtensionAmount no coincide con el valor calculado
   - **Listados:** -
## 20. Total comisiones

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount (Total IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubTotal/cbc:TaxAmount (Total IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente de la sumatoria de los 'Monto de IGV' del detalle por banco emisor (/Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount) con 'Código de tributo' igual a '1000', con una tolerancia de + - 1
   - **Mensaje:** El monto de IGV de la línea no coincide con el valor calculado
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo IGV)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '1000'
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Existe en el ítem otro tag cac:TaxSubtotal con el mismo  de código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 21. Total IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 22. Importe total

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL /cac:InvoiceLine/cac:ItemPriceExtension
   - **Mensaje:** Debe consignar el tag /cac:InvoiceLine/cac:ItemPriceExtension
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** El valor del tag es diferente de la sumatoria del 'Total comisiones' más el 'Total del IGV' de corresponder, con una tolerancia + - 1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no coincide con el valor calculado
   - **Listados:** -
## 22. Importe total

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Entidades financieras  - Detalle por banco emisor. Entidades financieras  - Detalle por banco emisor
## 23. Número de orden

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:ID

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Indicador de institución financiera' es igual a '1' (Emisor  local) y no existe al menos un tag /cac:SubInvoiceLine
   - **Mensaje:** Para entidades emisoras locales debe informar el detalle de las comisiones y cargos
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos, o es igual cero
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Existe otro subítem (cac:InvoiceLine/cac:SubInvoiceLine) con el mismo valor del Tag UBL
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 24. RUC Banco emisor de la tarjeta

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n11
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:OriginatorParty/cac:PartyIdentification/cbc:ID (Número de RUC)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador de institución financiera' es igual a '1' (Emisor local) y el tag UBL no existe
   - **Mensaje:** Para Bancos emisores locales debe ingresar el Numero de RUC
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de documento de identidad del emisor' es '6', el valor del tag UBL no existe en el listado
   - **Mensaje:** Número de RUC no existe.
   - **Listados:** Contribuyentes
## 24. Tipo de documento de identidad del emisor

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n1
- **Formato / Valor:** "6"
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:OriginatorParty/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador de institución financiera' es igual a '1' (Emisor local) y el tag UBL no existe
   - **Mensaje:** Debe indicar tipo de documento.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Indicador de institución financiera' es igual a '1' (Emisor local) y el valor del tag UBL es diferente de '6'
   - **Mensaje:** Tipo de documento de identidad debe ser RUC
   - **Listados:** -
## 25. Nombre Banco emisor de la tarjeta

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:OriginatorParty/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 25. Nombre Banco emisor de la tarjeta

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Item/cbc:Description

### Validaciones

1. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre o razon social registrado no cumple con el estandar
   - **Listados:** -
## 26. Comisión del banco emisor

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cbc:LineExtensionAmount no cumple con el formato establecido
   - **Listados:** -
## 27. Tipo de moneda de la comisión

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:LineExtensionAmount@currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cbc:TaxAmount (Monto de IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount no cumple el formato establecido
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Base imponible de IGV)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount no cumple el formato establecido
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el valor del tag es mayor a cero y es diferente del resultado de multiplicar la 'Base Imponible de IGV' por la tasa vigente del IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El monto de IGV a nivel de /cac:SubInvoiceLine no coincide con el valor calculado
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo IGV)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '1000'
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Existe otro tag cac:TaxSubtotal con el mismo código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 28. Monto de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 29. Importe total Entidad Emisor

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador de institución financiera' es igual a '1' (Banco local) y no existe el Tag UBL cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension
   - **Mensaje:** Debe consignar el tag /cac:SubInvoiceLine/cac:ItemPriceExtension
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en el tag cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount no cumple con el formato establecido

3. **OBSERV**
   - **Validación:** Si el tag existe, el valor del Tag UBL es diferente de la sumatoria de 'Comisión del banco emisor' (cbc:LineExtensionAmount) más 'Monto del IGV' (cbc:TaxAmount), con una tolerancia de +- 1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount no coincide con el valor calculado
   - **Listados:** -
## 29. Importe total Entidad Emisor

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Adquirente en los sistemas de pago - Resumen de comisiones y gastos. Registro Adquirente en los sistemas de pago - Resumen de comisiones y gastos
## 30. Número de orden del Ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos, o es igual cero
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro ítem (cac:InvoiceLine) con el mismo valor del Tag UBL
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 31. Monto de la Comisión

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en LineExtensionAmount del item no cumple con el formato establecido
   - **Listados:** -
## 32. Tipo de moneda de la comisión

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:LineExtensionAmount@currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 33. Indicador de tipo de comisión

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** "2"<br>(Operador de tarjeta)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag
   - **Mensaje:** Debe registrarse el 'Indicador de tipo de comisión'
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '1' y '2'
   - **Mensaje:** El dato ingresado en el 'Indicador de tipo de comisión' no corresponde al valor esperado
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:ID (Descripción de abono, cargo/descuento)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true" / "false"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:ChargeIndicator (Indicador de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'true' para 'Código de motivo de cargo' igual a '47' y '48'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para 'Código de motivo de descuento' igual a '00' y '01'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 53)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:AllowanceChargeReasonCode (Código de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento por item.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es distinto a '00', '01', '47' y '48'
   - **Mensaje:** El dato ingresado como cargo/descuento no es valido a nivel de ítem.
   - **Listados:** Catálogo<br>(053)
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:Amount (Monto de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El formato ingresado en el tag cac:InvoiceLine/cac:Allowancecharge/cbc:Amount no cumple con el formato establecido
   - **Listados:** -
## 34. Otros Abonos<br>Otros Cargos/descuentos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount (Total IGV)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador de tipo de comisión' es igual a '2' y no existe el tag cac:InvoiceLine/cac:TaxTotal
   - **Mensaje:** El xml no contiene el tag de impuesto por linea (TaxtTotal).
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe más de un tag cac:TaxTotal en el ítem
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de Item
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Base imponible de IGV)

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubTotal/cbc:TaxAmount (Total IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es diferente del resultado de multiplicar la 'Base Imponible de IGV' por la tasa vigente del IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El monto de IGV de la línea no coincide con el valor calculado
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo IGV)

### Validaciones

1. **ERROR**
   - **Validación:** No existe en la línea un tag cac:TaxTotal/cac:TaxSubtotal con cac:TaxCategory/cac:TaxScheme/cbc:ID igual a '1000'
   - **Mensaje:** Debe indicar el IGV. Es un campo obligatorio
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de '1000'
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** -

4. **ERROR**
   - **Validación:** Existe en el ítem otro tag cac:TaxSubtotal con el mismo  de código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 35. Total de IGV

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 36. Importe total

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL /cac:InvoiceLine/cac:ItemPriceExtension
   - **Mensaje:** Debe consignar el tag /cac:InvoiceLine/cac:ItemPriceExtension
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** El valor del tag diferente de la sumatoria de 'Monto de la comisión' más el 'Total del IGV' más 'Otros Abonos' más 'Otros cargos' menos 'Otros descuentos' de corresponder, con una tolerancia + - 1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no coincide con el valor calculado
   - **Listados:** -
## 36. Importe total

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Importes Totales del Documento Electrónico Autorizado. Importes Totales del Documento Electrónico Autorizado

- **Nivel:** Importes Totales del Documento Electrónico Autorizado
- **Condición informática:** Importes Totales del Documento Electrónico Autorizado
## 37. Importe total procesado en el periodo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag
   - **Mensaje:** El XML no contiene el tag cac:LegalMonetaryTotal/cbc:LineExtensionAmount
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag cac:LegalMonetaryTotal/cbc:LineExtensionAmount no cumple con el formato establecido
   - **Listados:** -
## 37. Importe total procesado en el periodo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:LineExtensionAmount@currencyID

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado.
   - **Mensaje:** El valor ingresado como moneda del comprobante no es valido (catalogo nro 02).
   - **Listados:** Catálogo<br>(002)
## 38. Monto abonado en el periodo facturado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en PayableAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es diferente del siguiente cálculo:<br>'Importe total procesado en el periodo'<br>menos<br>'Importe Total' de Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas de bancos locales<br>menos<br>'Importe Total' de Entidades financieras  - Resumen de comisiones y gastos de emisores de las tarjetas de bancos foraneos<br>menos 'Importe Total' de Adquirente en los sistemas de pago - Resumen de comisiones y gastos<br>con una tolerancia de + - 1
   - **Mensaje:** El importe del campo /cac:LegalMonetaryTotal/cbc:PayableAmount no coincide con el valor calculado
   - **Listados:** -
## 38. Monto abonado en el periodo facturado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount@currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Importe total procesado en el periodo'
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
