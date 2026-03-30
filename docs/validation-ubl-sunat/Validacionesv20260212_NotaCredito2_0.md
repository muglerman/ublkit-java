# Validacionesv20260212

## Hoja: NotaCredito2_0

## -. -

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -

### Validaciones

1. **- -**
   - **Validación:** <<< REVISAR HOJA "GENERAL" >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos de la nota de crédito
## 1. Versión del UBL

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.1"
- **Tag:** /CreditNote/cbc:UBLVersionID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2075**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **ERROR 2074**
   - **Validación:** El valor del Tag UBL es diferente de "2.1"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.0"
- **Tag:** /CreditNote/cbc:CustomizationID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2073**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no existe informacion de CustomizationID
   - **Listados:** -

2. **ERROR 2072**
   - **Validación:** El valor del Tag UBL es diferente de "2.0"
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 3. Numeración, conformada por serie y número correlativo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /CreditNote/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 1035**
   - **Validación:** El número de serie del Tag UBL es diferente al número de serie del archivo
   - **Mensaje:** Numero de Serie del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **ERROR 1036**
   - **Validación:** El número de comprobante del Tag UBL es diferente al número de comprobante del archivo
   - **Mensaje:** Número de documento en el nombre del archivo no coincide con el consignado en el contenido del XML
   - **Listados:** -

3. **ERROR 1001**
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [B][A-Z0-9]{3}-[0-9]{1,8}<br>- [F][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante
   - **Listados:** -

4. **ERROR 1033**
   - **Validación:** Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 1
   - **Mensaje:** El comprobante fue registrado previamente con otros datos
   - **Listados:** Comprobantes de pago electrónico

5. **ERROR 1032**
   - **Validación:** Si la serie empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 2<br>Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 0 o 2
   - **Mensaje:** El comprobante ya esta informado y se encuentra con estado anulado o rechazado
   - **Listados:** Comprobantes de pago electrónico

6. **ERROR 1084**
   - **Validación:** Si la serie empieza con número, y el comprobante a modificar es de Tipo '03', '12', '16' o '55', y el valor del Tag UBL se encuentra en el listado, y el comprobante ha sido informado en un Resumen Diario.<br>Validación no aplica para OSE
   - **Mensaje:** Comprobante de contingencia ya fue informado por su resumen, si desea modificarse debe realizarse por su primer canal de presentación
   - **Listados:** Comprobantes de pago electrónico

7. **OBSERV 4204**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado como comprobante de contingencia
   - **Listados:** Autorizaciones de comprobantes contingencia

8. **ERROR 3207**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## 4. Fecha de emisión

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /CreditNote/cbc:IssueDate
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2108**
   - **Validación:** Si serie del documento no inicia con número y:<br>Si serie no empieza con "B":<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al plazo máximo vigente.
   - **Mensaje:** Presentacion fuera de fecha
   - **Listados:** -

2. **ERROR 1079**
   - **Validación:** Si serie empieza con "B":<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor a 5 días
   - **Mensaje:** Solo puede enviar el comprobante en un resumen diario
   - **Listados:** -

3. **ERROR 2329**
   - **Validación:** La fecha de emisión es mayor a dos días de la fecha de envío del comprobante
   - **Mensaje:** La fecha de emision se encuentra fuera del limite permitido
   - **Listados:** -
## 5. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** hh:mm:ss
- **Tag:** /CreditNote/cbc:IssueTime
- **Uso del campo:** 1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 6. Código de tipo de nota de crédito

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 09)
- **Tag:** /CreditNote/cac:DiscrepancyResponse/cbc:ResponseCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2128**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de ResponseCode
   - **Listados:** -

2. **ERROR 2172**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** ResponseCode - El dato ingresado no cumple con la estructura
   - **Listados:** Catálogo<br>(009)

3. **ERROR 3203**
   - **Validación:** El tag UBL se repite dentro del mismo documento
   - **Mensaje:** El tipo de nota es un dato único
   - **Listados:** -
## 6. Código de tipo de nota de crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 6. Código de tipo de nota de crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de nota de credito"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de nota de credito'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 6. Código de tipo de nota de crédito

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo09"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo09'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 7. Motivo que sustenta la emisión de la nota de crédito electrónica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..500
- **Tag:** /CreditNote/cac:DiscrepancyResponse/cbc:Description
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2136**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de cac:DiscrepancyResponse/cbc:Description
   - **Listados:** -

2. **ERROR 2135**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 hasta 500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** cac:DiscrepancyResponse/cbc:Description - El dato ingresado no cumple con la estructura
   - **Listados:** -
## 8. Tipo de moneda en la cual se emite la nota de crédito electrónica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /CreditNote/cbc:DocumentCurrencyCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2070**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de DocumentCurrencyCode
   - **Listados:** -

2. **ERROR 2071**
   - **Validación:** La moneda de los totales de línea y totales de comprobantes es diferente al valor del Tag UBL
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -

3. **ERROR 3088**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no existe en el listado
   - **Mensaje:** El valor ingresado como moneda del comprobante no es valido (catalogo nro 02).
   - **Listados:** Catálogo<br>(002)
## Datos de la firma electrónica
## 9. Firma digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Formato / Valor:** -
- **Tag:** /CreditNote/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature<br>/CreditNote/cac:Signature
- **Uso del campo:** 1

### Validaciones

1. **- -**
   - **Validación:** <<< REVISAR HOJA GENERAL (FIRMA) >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del emisor electrónico
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de RUC)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 1034**
   - **Validación:** El Tag UBL es diferente al RUC del nombre del XML
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **ERROR 2010**
   - **Validación:** El Tag UBL tiene un estado diferente a activo (ind_estado diferente "00") en el listado
   - **Mensaje:** El contribuyente no esta activo
   - **Listados:** Contribuyentes

3. **ERROR 2011**
   - **Validación:** El Tag UBL tiene la condición no habido (ind_condicion igual a "12") en el listado
   - **Mensaje:** El contribuyente no esta habido
   - **Listados:** Contribuyentes

4. **ERROR 1078**
   - **Validación:** El valor del Tag UBL se encuentra en el padrón de obligados a emitir a través de SEE-OSE<br>Validación no aplica para OSE
   - **Mensaje:** El emisor no se encuentra autorizado a emitir en el SEE-Desde los sistemas del contribuyente
   - **Listados:** -

5. **ERROR 1080**
   - **Validación:** Si la 'Serie del comprobante' empieza con 'S' y el 'Número de RUC' pertenece al 'SEE-Empresas supervisadas'<br>Validación no aplica para OSE
   - **Mensaje:** Debe enviar su comprobante por el SEE-Empresas supervisadas
   - **Listados:** -

6. **ERROR 1086**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01', el valor del Tag UBL se encuentra en el listado de Sujetos sin capacidad operativa (ind_padron igual a "02") y "Fecha de emisión" del comprobante comprendida en el rango de vigencia
   - **Mensaje:** El emisor electrónico es un Sujeto sin capacidad operativa (SSCO)
   - **Listados:** Padrones con vigencia
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** "6"
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3029**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del tipo de documento de identidad del emisor
   - **Listados:** -

2. **ERROR 2511**
   - **Validación:** El Tag UBL es diferente a "6"
   - **Mensaje:** El tipo de documento no es aceptado.
   - **Listados:** -
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 11. Nombre comercial

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyName/cbc:Name
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4092**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName
- **Uso del campo:** 1

### Validaciones

1. **ERROR 1037**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **OBSERV 4338**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line<br>(Dirección completa y detallada)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4094**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4095**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4096**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4093**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** El codigo de ubigeo del domicilio fiscal del emisor no es válido
   - **Listados:** Catálogo<br>(013)
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4097**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4098**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4041**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a PE
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4254**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 14. Código asignado por SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** n4
- **Tag:** /CreditNote/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:AddressTypeCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3030**
   - **Validación:** Si 'Serie del comprobante' inicia con 'F' y 'Tipo de documento que modifica' es '01', no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del código de local anexo del emisor
   - **Listados:** -

2. **OBSERV 4198**
   - **Validación:** Si 'Serie del comprobante' no inicia con 'F' o 'Tipo de documento que modifica' es diferente de '01', no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del código de local anexo del emisor
   - **Listados:** -

3. **ERROR 3239**
   - **Validación:** Si 'Serie del comprobante' inicia con 'F' y 'Tipo de documento que modifica' es '01' y el Tag UBL es diferente de '0000', el valor del Tag no se encuentra en el listado
   - **Mensaje:** El código de local anexo consignado no se encuentra declarado en el RUC
   - **Listados:** Establecimientos Anexos

4. **OBSERV 4199**
   - **Validación:** Si el Tag UBL existe y es diferente de vacío y es diferente de '0000', y 'Serie del comprobante' no inicia con 'F' o 'Tipo de documento que modifica' es diferente de '01', el valor del Tag UBL no está en el listado
   - **Mensaje:** El código de local anexo consignado no se encuentra declarado en el RUC
   - **Listados:** Establecimientos Anexos

5. **OBSERV 4242**
   - **Validación:** Si el Tag UBL existe y es diferente de vacío, el valor del Tag es diferente a numérico de 4 dígitos.
   - **Mensaje:** El dato ingresado como local anexo no cumple con el formato establecido
   - **Listados:** -
## 14. Código asignado por SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 14. Código asignado por SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Establecimientos anexos"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Establecimientos anexos'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## Datos del adquirente o usuario
## 15. Tipo y número de documento de identidad del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2679**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de documento de identidad del cliente
   - **Listados:** -

2. **ERROR 2017**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es RUC (6), el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El numero de documento de identidad del receptor debe ser  RUC
   - **Listados:** -

3. **ERROR 1083**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es RUC (6), el Tag UBL no existe en el listado
   - **Mensaje:** El numero de RUC del receptor no existe.
   - **Listados:** Contribuyentes

4. **OBSERV 4013**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es RUC (6), el Tag UBL tiene un estado diferente a activo (ind_estado diferente "00") en el listado "Contribuyentes"
   - **Mensaje:** El RUC  del receptor no esta activo
   - **Listados:** Contribuyentes

5. **OBSERV 4014**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es RUC (6), el Tag UBL tiene un ind_condicion igual a "12" en el listado "Contribuyentes"
   - **Mensaje:** El RUC del receptor no esta habido
   - **Listados:** Contribuyentes
## 15. Tipo y número de documento de identidad del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2679**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de documento de identidad del cliente
   - **Listados:** -

2. **ERROR 2016**
   - **Validación:** El Tag UBL es diferente al listado  y diferente de guión '-'
   - **Mensaje:** El dato ingresado  en el tipo de documento de identidad del receptor no cumple con el estandar o no esta permitido.
   - **Listados:** Catálogo<br>(006)
## 15. Tipo y número de documento de identidad del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 15. Tipo y número de documento de identidad del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 15. Tipo y número de documento de identidad del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 16. Apellidos y nombres, denominación o razón social del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1000
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2021**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **ERROR 2022**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1000 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 17. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /CreditNote/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName (Nombre)
- **Uso del campo:** 1

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del documento que se modifica
## 18. Serie y número correlativo del documento que modifica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /CreditNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2524**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10-Otros', y no existe un tag /CreditNote/cac:BillingReference/cac:InvoiceDocumentReference
   - **Mensaje:** Debe indicar el documento afectado por la nota
   - **Listados:** -

2. **ERROR 3261**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '13', y existe más de un tag /CreditNote/cac:BillingReference/cac:InvoiceDocumentReference
   - **Mensaje:** Para el tipo de nota de credito 13 no se puede modificar mas de una factura en la nota
   - **Listados:** -

3. **ERROR 3194**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '11-Ajustes de operaciones de exportación', y existe más de un tag /CreditNote/cac:BillingReference/cac:InvoiceDocumentReference
   - **Mensaje:** Para los ajustes de operaciones de exportación solo es permitido registrar un documento que modifica.
   - **Listados:** -

4. **ERROR 2117**
   - **Validación:** Si documento que se modifica es una factura (Tipo de documento que modifica es '01'), el formato del Tag UBL es diferente a:<br>- [F][A-Z0-9]{3}-[0-9]{1,8}<br>- (E001)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** La serie o numero del documento modificado por la Nota de Credito no cumple con el formato establecido
   - **Listados:** -

5. **ERROR 2117**
   - **Validación:** Si documento que se modifica es una boleta (Tipo de documento que modifica es '03'), el formato del Tag UBL es diferente a:<br>- [B][A-Z0-9]{3}-[0-9]{1,8}<br>- (EB01)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** La serie o numero del documento modificado por la Nota de Credito no cumple con el formato establecido
   - **Listados:** -

6. **ERROR 2117**
   - **Validación:** Si la nota de crédito modifica un Documento autorizado (tipo de comprobante '05', '06', '12', '13', '15', '16', '18', '21', '28', '30', '34', '37', '42', '43', '45', '55', '11', '17', '23', '24', '56'), el formato del Tag UBL es diferente a:<br>- [a-zA-Z0-9-]{1,20}-[a-zA-Z0-9-]{1,20}
   - **Mensaje:** La serie o numero del documento modificado por la Nota de Credito no cumple con el formato establecido
   - **Listados:** -

7. **ERROR 2117**
   - **Validación:** Si 'Tipo de comprobante que modifica' es vacío o guion, el formato del Tag UBL es diferente a:<br>- [a-zA-Z0-9-]{1,20}-[a-zA-Z0-9-]{1,20}<br>- Vacio
   - **Mensaje:** La serie o numero del documento modificado por la Nota de Credito no cumple con el formato establecido
   - **Listados:** -

8. **ERROR 2119**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' o '03' o '30' o '34' o '42' y 'Serie del documento que modifica' empieza con B o F o E, el Tag UBL no se encuentra en el listado
   - **Mensaje:** El documento modificado en la Nota de credito no esta registrada.
   - **Listados:** Comprobantes de pago electrónico

9. **ERROR 2120**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' o '03' o '30' o '34' o '42' y 'Serie del documento que modifica' empieza con B o F o E, el Tag UBL se encuentra en el listado con estado 'Anulado'
   - **Mensaje:** El documento modificado en la Nota de credito se encuentra de baja
   - **Listados:** Comprobantes de pago electrónico

10. **ERROR 2121**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' o '03' o '30' o '34' o '42' y 'Serie del documento que modifica' empieza con B o F o E, el Tag UBL se encuentra en el listado con estado 'Rechazado'
   - **Mensaje:** El documento modificado en la Nota de credito esta registrada como rechazada
   - **Listados:** Comprobantes de pago electrónico

11. **OBSERV 2404**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' o '03' y 'Serie del documento que modifica' empieza con número, el Tag UBL no se encuentra en el listado
   - **Mensaje:** Documento afectado por la nota electronica no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos

12. **ERROR 2885**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' o '03' o '30' o '34' o '42' y 'Serie del documento que modifica' empieza con B o F o E, la 'Fecha de emisión' de la nota de crédito es menor a la fecha de emisión del documento que modifica
   - **Mensaje:** La fecha de emisión de la nota debe ser mayor o igual a la fecha de emisión de los documentos que modifica
   - **Listados:** Comprobantes de pago electrónico

13. **ERROR 3286**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '01' y 'Serie del documento que modifica' empieza con F o E, el 'Importe Total' de la nota de crédito (/CreditNote/cac:LegalMonetaryTotal/cbc:PayableAmount) es mayor a la sumatoria de los Importes totales de los documentos que modifica<br>Validación no aplica para OSE
   - **Mensaje:** El monto total de la nota de credito debe ser menor o igual al monto de la factura

14. **OBSERV 4028**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' y 'Tipo de documento que modifica' es '03' o '30' o '34' o '42' y 'Serie del documento que modifica' empieza con B o F o E, el 'Importe Total' de la nota de crédito (/CreditNote/cac:LegalMonetaryTotal/cbc:PayableAmount) es mayor a la sumatoria de los Importes totales de los documentos que modifica<br>Validación no aplica para OSE
   - **Mensaje:** El monto total de la nota de credito debe ser menor o igual al monto de la factura

15. **ERROR 3209**
   - **Validación:** Si el "Tipo de documento que modifica" es "01", "30", "34", "35" o "42", y 'Serie del documento que modifica' empieza con F o E, el 'Tipo de moneda en la cual se emite la nota de crédito electrónica' es diferente al "Tipo de moneda del documento modificado"
   - **Mensaje:** El tipo de moneda de la nota debe ser el mismo que el declarado en el documento que modifica
   - **Listados:** Comprobantes de pago electrónicos

16. **OBSERV 4368**
   - **Validación:** Si el "Tipo de documento que modifica" es "03", y 'Serie del documento que modifica' empieza con B o E, el 'Tipo de moneda en la cual se emite la nota de crédito electrónica' es diferente al "Tipo de moneda del documento modificado"
   - **Mensaje:** El tipo de moneda de la nota debe ser el mismo que el declarado en el documento que modifica

17. **ERROR 3260**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '13' y 'Tipo de documento que modifica' es '01', el documento que modifica debe ser una Factura al crédito
   - **Mensaje:** Para el tipo de nota de credito 13 el documento afectado debe ser Factura al credito

18. **ERROR 2365**
   - **Validación:** El 'Tipo de documento que modifica' concatenado con el valor del Tag UBL se repite en el /CreditNote
   - **Mensaje:** El comprobante contiene un tipo y número de Documento Relacionado repetido
   - **Listados:** -
## 19. Tipo de documento que modifica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /CreditNote/cac:BillingReference/cac:InvoiceDocumentReference/cbc:DocumentTypeCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2116**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' (Otros conceptos) y  la Serie del comprobante empieza con 'F', el Tag UBL es diferente de  '01', '05', '06', '12', '13', '15', '16', '18', '21', '28', '30', '34', '37', '42', '43', '45', '55', '11', '17', '23', '24' y '56'
   - **Mensaje:** El tipo de documento modificado por la Nota de credito debe ser factura electronica o ticket
   - **Listados:** -

2. **ERROR 2116**
   - **Validación:** Si 'Código de tipo de nota de crédito' es igual a '10' (Otros conceptos) y  la Serie del comprobante empieza con 'F', el Tag UBL es diferente de '01', '05', '06', '12', '13', '15', '16', '18', '21', '28', '30', '34', '37', '42', '43', '45', '55', '11', '17', '23', '24', '56', vacío y guion ('-')
   - **Mensaje:** El tipo de documento modificado por la Nota de credito debe ser factura electronica o ticket
   - **Listados:** -

3. **ERROR 2399**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' (Otros conceptos) y la Serie del comprobante empieza con 'B', el Tag UBL es diferente de '03', '12', '16' y '55'
   - **Mensaje:** El tipo de documento modificado por la Nota de credito debe ser boleta electronica
   - **Listados:** -

4. **ERROR 2399**
   - **Validación:** Si 'Código de tipo de nota de crédito' es igual a '10' (Otros conceptos) y la Serie del comprobante empieza con 'B', el Tag UBL es diferente de '03', '12', '16', '55', vacío y guion ('-')
   - **Mensaje:** El tipo de documento modificado por la Nota de credito debe ser boleta electronica
   - **Listados:** -

5. **ERROR 2594**
   - **Validación:** Si 'Código de tipo de nota de crédito' es diferente de '10' (Otros conceptos) y  la Serie del comprobante empieza con número, el Tag UBL es diferente de '01', '03', '05', '06', '12', '13', '15', '16', '18', '21', '28', '30', '34', '37', '42', '43', '45', '55', '11', '17', '23', '24' y '56'
   - **Mensaje:** El tipo de documento modificado por la nota electronica no es valido
   - **Listados:** -

6. **ERROR 2594**
   - **Validación:** Si 'Código de tipo de nota de crédito' es igual a '10' (Otros conceptos) y  la Serie del comprobante empieza con número, el Tag UBL es diferente de '01', '03', '05', '06', '12', '13', '15', '16', '18', '21', '28', '30', '34', '37', '42', '43', '45', '55', '11', '17', '23', '24', '56', vacío y guion ('-')
   - **Mensaje:** El tipo de documento modificado por la nota electronica no es valido
   - **Listados:** -

7. **ERROR 1080**
   - **Validación:** Si el valor del Tag UBL es '13' y la 'Serie del documento que modifica' empieza con 'F', el 'Numero de RUC' del emisor se encuentra afiliado al 'SEE-Empresas supervisadas'
   - **Mensaje:** Debe enviar su comprobante por el SEE-Empresas supervisadas
   - **Listados:** Padrones<br>Contribuyentes

8. **ERROR 3259**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '13' y valor del Tag UBL es diferente de '01'
   - **Mensaje:** Para el tipo de nota de credito 13 el documento afectado debe ser Factura
   - **Listados:** -

9. **ERROR 2884**
   - **Validación:** Si existe más de un documento que se modifica (más de un cac:BillingReference), y no todos tienen el mismo 'Tipo de documento que modifica' (cbc:DocumentTypeCode)
   - **Mensaje:** Los comprobantes modificados por la nota deben ser del mismo tipo
   - **Listados:** -
## 19. Tipo de documento que modifica

- **Nivel:** Global
- **Condición informática:** M

### Validaciones

1. **OBSERV 4367**
   - **Validación:** Si 'Código tipo de nota de crédito' es '04', '05' o '08' y 'Tipo de documento que modifica' es '03'
   - **Mensaje:** El tipo de nota de crédito 04, 05 y 08 no debería estar vinculado a una boleta
   - **Listados:** -
## 19. Tipo de documento que modifica

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 19. Tipo de documento que modifica

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "Tipo de Documento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 19. Tipo de documento que modifica

- **Nivel:** Global
- **Condición informática:** M
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 20. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:DespatchDocumentReference/cbc:ID (Número de la guía de remisión)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4006**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a:<br>- [T][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{4}-[0-9]{1,8}<br>- [EG0][1-4]{1}-[0-9]{1,8}<br>- [EG07] {4}-[0-9]{1,8}<br>- [G][0-9]{3}-[0-9]{1,8}<br>- [V][A-Z0-9]{3}-[0-9]{1,8}
   - **Mensaje:** El ID de las guias debe tener informacion de la SERIE-NUMERO de guia.
   - **Listados:** -

2. **ERROR 2364**
   - **Validación:** El "Tipo de la guía de remisión relacionada" concatenado con el valor del Tag UBL se repite en el /CreditNote
   - **Mensaje:** El comprobante contiene un tipo y número de Guía de Remisión repetido
   - **Listados:** -
## 20. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /CreditNote/cac:DespatchDocumentReference/cbc:DocumentTypeCode (Tipo de la guía de remisión)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4005**
   - **Validación:** Si existe el Tag UBL, el formato del Tag UBL es diferente de "09" o "31"
   - **Mensaje:** El DocumentTypeCode de las guias debe ser 09 o 31
   - **Listados:** -
## 20. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 20. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Documento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 20. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 21. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:AdditionalDocumentReference/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4010**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de entre 6 y 30 caracteres  (se considera cualquier carácter no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El ID de los documentos relacionados no cumplen con el estandar.
   - **Listados:** -

2. **ERROR 2426**
   - **Validación:** El "Tipo de otro documento relacionado" concatenado con el valor del Tag UBL, se repite en el /CreditNote
   - **Mensaje:** Documentos relacionados duplicados en el comprobante.
   - **Listados:** -

3. **ERROR 2636**
   - **Validación:** Si "Código de tipo de nota de crédito" es diferente de 10 (Otros) y "Tipo de otro documento relacionado" es 99, el Tag UBL es vacío
   - **Mensaje:** No existe datos del ID de los documentos relacionados con valor 99 para un tipo codigo Nota Credito 10.
   - **Listados:** -
## 21. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 12)
- **Tag:** /CreditNote/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de documento)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4009**
   - **Validación:** El valor del Tag UBL es diferente de '01', '04', '05', '99'
   - **Mensaje:** El DocumentTypeCode de Otros documentos relacionados tiene valores incorrectos.
   - **Listados:** -

2. **ERROR 2635**
   - **Validación:** Si "Código de tipo de nota de crédito" es 10 (Otros), existe más de un Tag UBL igual a "99"
   - **Mensaje:** Debe existir DocumentTypeCode de Otros documentos relacionados con valor 99 para un tipo codigo Nota Credito 10.
   - **Listados:** -

3. **ERROR 2637**
   - **Validación:** Si "Código de tipo de nota de crédito" es  10 (Otros) y "Tipo de otro documento relacionado"es diferente de '99'
   - **Mensaje:** No existe datos del DocumentType de los documentos relacionados con valor 99 para un tipo codigo Nota Credito 10.
   - **Listados:** -
## 21. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 21. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento Relacionado"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento Relacionado'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 21. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## Datos del detalle o ítem de la nota de crédito
## 22. Número de orden del Ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..3
- **Tag:** /CreditNote/cac:CreditNoteLine/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2137**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos; o, es igual cero.
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 2752**
   - **Validación:** Existe otro cac:CreditNoteLine con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 23. Unidad de medida por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo N.° 03)
- **Tag:** /CreditNote/cac:CreditNoteLine/cbc:CreditedQuantity@unitCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2138**
   - **Validación:** Si el Tag UBL existe, no existe el atributo del Tag UBL o es vacío
   - **Mensaje:** CreditedQuantity/@unitCode - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 23. Unidad de medida por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo N.° 03)
- **Tag:** /CreditNote/cac:CreditNoteLine/cbc:CreditedQuantity@unitCode

### Validaciones

1. **ERROR 2936**
   - **Validación:** Si existe el atributo, el valor es diferente al Catálogo N.° 03
   - **Mensaje:** El dato ingresado como unidad de medida no corresponde al valor esperado
   - **Listados:** -
## 23. Unidad de medida por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "UN/ECE rec 20"
- **Tag:** @unitCodeListID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4258**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UN/ECE rec 20'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListID es incorrecto.
   - **Listados:** -
## 23. Unidad de medida por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "United Nations Economic Commission for Europe"
- **Tag:** @unitCodeListAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4259**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListAgencyName es incorrecto.
   - **Listados:** -
## 24. Cantidad de unidades por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /CreditNote/cac:CreditNoteLine/cbc:CreditedQuantity
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2139**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales
   - **Mensaje:** CreditedQuantity - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 25. Código de producto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:SellersItemIdentification/cbc:ID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4234**
   - **Validación:** Si el tag UBL existe,  el formato del Tag UBL es diferente a alfanumérico de 1 hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El código de producto no cumple con el formato establecido
   - **Listados:** -
## 26. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** (Catálogo N.° 25, 25.1, 25.2 ó 25.3)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:CommodityClassification/cbc:ItemClassificationCode
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 3496**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente a numérico de 8 dígitos
   - **Mensaje:** El Código producto de SUNAT no es válido

2. **OBSERV 3496**
   - **Validación:** Si el tag existe, el valor del Tag UBL es diferente de 8 ceros ('00000000') y diferente de 8 nueves ('99999999'), y no se encuentra en el listado
   - **Mensaje:** El Código producto de SUNAT no es válido
   - **Listados:** Catálogo<br>(025, 25.1, 25.2 ó 25.3)

3. **OBSERV 4332**
   - **Validación:** Si el tag UBL existe, el valor del Tag UBL no se encuentra en el listado
   - **Mensaje:** El Código producto de SUNAT no es válido
   - **Listados:** Catálogo<br>(025)

4. **OBSERV 4337**
   - **Validación:** Si el tag existe y tiene una longitud de 8 posiciones, el valor del Tag UBL termina en 6 ceros ('000000') o termina en 4 ceros ('0000')
   - **Mensaje:** El Codigo de producto SUNAT debe especificarse como minimo al tercer nivel jerarquico (a nivel de clase del codigo UNSPSC)
   - **Listados:** Catálogo<br>(025)
## 26. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "UNSPSC"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4254**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UNSPSC'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 26. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "GS1 US"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'GS1 US'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 26. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Item Classification"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Item Classification'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 27. Código de producto GTIN

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..14
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:StandardItemIdentification/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4334**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-8, y la longitud  del Tag UBL es diferente de 8 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

2. **OBSERV 4334**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-12, y la longitud  del Tag UBL es diferente de 12 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

3. **OBSERV 4334**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-13, y la longitud  del Tag UBL es diferente de 13 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

4. **OBSERV 4334**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-14, y la longitud  del Tag UBL es diferente de 14 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

5. **OBSERV 4333**
   - **Validación:** Si el tag existe y no existe el atributo @schemeID (Tipo de estructura GTIN)
   - **Mensaje:** Si utiliza el estandar GS1 debe especificar el tipo de estructura GTIN
   - **Listados:** -
## 27. Código de producto GTIN

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..14
- **Tag:** @schemeID (Tipo de estructura GTIN)

### Validaciones

1. **OBSERV 4335**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'GTIN-8', 'GTIN-12', 'GTIN-13' y 'GTIN-14'
   - **Mensaje:** El tipo de estructura GS1 no tiene un valor permitido
   - **Listados:** -
## 28. Descripción detallada del servicio prestado, bien vendido o cedido en uso, indicando las características.

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..500
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cbc:Description
- **Uso del campo:** 1

### Validaciones

1. **OBSERV 4084**
   - **Validación:** Si el tag UBL existe,  el formato del Tag UBL es diferente a alfanumérico de 3 hasta 500 caracteres (se considera cualquier carácter, permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** Descripción del Ítem - El dato ingresado no cumple con el formato establecido.
   - **Listados:** -
## 29. Valor unitario por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Price/cbc:PriceAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2369**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero.<br>Nota: En casos de enteros, el valor monetario no debe tener ceros a la izquierda.
   - **Mensaje:** El dato ingresado en PriceAmount del Valor de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 2640**
   - **Validación:** Si existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es mayor a 0 (cero)
   - **Mensaje:** Operacion gratuita, solo debe consignar un monto referencial
   - **Listados:** -
## 29. Valor unitario por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceAmount (Valor)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2367**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PriceAmount del Precio de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3224**
   - **Validación:** Si no existe en misma la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) (Operaciones gratuitas), y 'Código de precio' es '02' (Valor referencial en operaciones no onerosa), el Tag UBL es mayor a 0 (cero).
   - **Mensaje:** Si existe 'Valor referencial unitario en operac. no onerosas' con monto mayor a cero, la operacion debe ser gratuita (codigo de tributo 9996)
   - **Listados:** -
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 16)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceTypeCode (Código de tipo de precio)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2410**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente al Catálogo 16
   - **Mensaje:** Se ha consignado un valor invalido en el campo cbc:PriceTypeCode
   - **Listados:** Catálogo<br>(016)

2. **ERROR 2410**
   - **Validación:** Si existe 'Precio de venta unitario por ítem que modifica' o existe 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' y no existe el Tag UBL o es vacío
   - **Mensaje:** Se ha consignado un valor invalido en el campo cbc:PriceTypeCode
   - **Listados:** -

3. **ERROR 2409**
   - **Validación:** Existe en el mismo ítem otro cac:AlternativeConditionPrice con el mismo valor del Tag UBL (cbc:PriceTypeCode)
   - **Mensaje:** Existe mas de un tag cac:AlternativeConditionPrice con el mismo cbc:PriceTypeCode
   - **Listados:** -
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Precio"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Precio'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 30<br>31. Precio de venta unitario por item que modifica<br>Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 32. Monto total de tributos del ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cbc:TaxAmount (Monto total de impuestos por linea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3195**
   - **Validación:** No existe el tag cac:CreditNoteLine/cac:TaxTotal
   - **Mensaje:** El xml no contiene el tag de impuesto por linea (TaxtTotal).
   - **Listados:** -

2. **ERROR 3021**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -

3. **ERROR 3292**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', el valor del Tag UBL es diferente a la sumatoria de 'Monto de tributo por línea' (cbc:TaxAmount)  de los tributos '1000', '1016', '2000', '7152' y '9999', con una tolerancia + -1
   - **Mensaje:** El importe total de impuestos por línea no coincide con la sumatoria de los impuestos por línea.
   - **Listados:** -

4. **OBSERV 4293**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', el valor del Tag UBL es diferente a la sumatoria de 'Monto de tributo por línea' (cbc:TaxAmount)  de los tributos '1000', '1016', '2000', '7152' y '9999', con una tolerancia + -1
   - **Mensaje:** El importe total de impuestos por línea no coincide con la sumatoria de los impuestos por línea.
   - **Listados:** -

5. **ERROR 3026**
   - **Validación:** Existe en el mismo ítem más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de Item
   - **Listados:** -
## 32. Monto total de tributos del ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base IGV/IVAP)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3031**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3272**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente de la suma del 'Valor de venta por ítem que modifica' más el 'Monto del tributo de la línea del ISC', con una tolerancia + - 1
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -

3. **OBSERV 4294**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente de la suma del 'Valor de venta por ítem que modifica' más el 'Monto del tributo de la línea del ISC', con una tolerancia + - 1
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -

4. **ERROR 3272**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01', y no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente del 'Valor de venta por ítem que modifica'
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -

5. **OBSERV 4294**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01', y no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente del 'Valor de venta por ítem que modifica'
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo de la línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2033**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3110**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9995' o '9997' o '9998', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

3. **ERROR 3111**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), y la 'Afectación al IGV o IVAP' es '11', '12', '13', '14', '15', '16' o '17', el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR 3110**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y la 'Afectación al IGV o IVAP' es  '21', '31', '32', '33', '34', '35', '36', '37' o '40', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

5. **ERROR 3111**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000' o '1016' y<br>'Monto base' mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

6. **ERROR 3103**
   - **Validación:** Si 'Tipo de documento que modifica' es diferente de '30' y '42', y la 'Afectación al IGV o IVAP' es '10','11', '12', '13', '14', '15', '16' o '17', el valor del tag es diferente a la tasa del tributo por el monto base IGV/IVAP de la línea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación del IGV/IVAP no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2992**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR 3102**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -

3. **ERROR 2993**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) y la 'Afectación al IGV o IVAP' es '11', '12', '13', '14', '15', '16' o '17, el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR 2993**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000' o '1016', y  'Monto base' mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 07)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode  (Afectación al IGV e IVAP cuando corresponda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2371**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '2000' (ISC) o '9999' (Otros tributos), cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cbc:TaxExemptionReasonCode de Afectacion al IGV
   - **Listados:** -

2. **ERROR 3050**
   - **Validación:** Si 'Código de tributo por línea' es igual a '2000' (ISC) o '9999' (Otros tributos), existe el tag UBL
   - **Mensaje:** Afectación de IGV no corresponde al código de tributo de la linea.
   - **Listados:** -

3. **ERROR 2040**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '2000' (ISC) o '9999' (Otros tributos), cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al listado según su código de tributo.
   - **Mensaje:** El tipo de afectacion del IGV es incorrecto
   - **Listados:** Catálogo<br>(007)

4. **ERROR 2642**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '11', el valor del Tag UBL es diferente de '40'
   - **Mensaje:** Operaciones de exportacion, deben consignar Tipo Afectacion igual a 40
   - **Listados:** -

5. **ERROR 2644**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '12', el valor del Tag UBL es diferente de '17'
   - **Mensaje:** Comprobante operacion sujeta IVAP solo debe tener ítems con código de afectación del IGV igual a 17
   - **Listados:** -

6. **ERROR 3230**
   - **Validación:** Si valor Tag UBL es '17' y 'Código de tipo de nota de crédito' es diferente de '12'
   - **Mensaje:** Tipo de nota debe ser 'Ajustes afectos al IVAP'
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Afectacion del IGV"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Afectacion del IGV'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2037**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR 2036**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3067**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -

4. **ERROR 3105**
   - **Validación:** No existe en el ítem un cac:TaxSubtotal con cbc:ID con alguno de los siguientes valores: '1000', '1016', '9995', '9996', '9997' o '9998'
   - **Mensaje:** El XML debe contener al menos un tributo por linea de afectacion por IGV
   - **Listados:** -

5. **ERROR 3223**
   - **Validación:** En una línea sólo pueden existir las siguientes combinaciones de códigos de tributos con 'Monto base' mayor a cero (cbc:TaxableAmount  > 0):<br>- '1000', '2000' y/o '9999'<br>- '1016' y '9999'<br>- '9995' y 9999'<br>- '9996', '2000' y/o '9999'<br>- '9997', '2000 'y/o '9999'<br>- '9998', '2000' y/o '9999'
   - **Mensaje:** La combinación de tributos no es permitida
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2996**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR 3051**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 33. Afectación al IGV por ítem<br>Afectación IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2377**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3031**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo de la línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2033**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3108**
   - **Validación:** Si  el 'Código de tributo por línea' es '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente a la tasa del tributo por el monto base ISC de la linea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación del ISC no corresponde al monto de afectacion de linea.
   - **Listados:** -

3. **ERROR 3109**
   - **Validación:** Si el 'Código de tributo por línea' es '9999' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente a la tasa del tributo por el monto base Otros tributos de la linea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación de otros tributos no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2992**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR 3102**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -

3. **ERROR 3104**
   - **Validación:** Si 'Código de tributo por línea' es igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de ISC por linea debe ser diferente a 0.00.
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 08)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TierRange (Tipo de sistema de ISC)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2373**
   - **Validación:** Si 'Código de tributo por línea' es '2000' (ISC) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), no existe el Tag UBL
   - **Mensaje:** Si existe monto de ISC en el ITEM debe especificar el sistema de calculo
   - **Listados:** -

2. **ERROR 3210**
   - **Validación:** Si 'Código de tributo por línea' es diferente '2000' (ISC), existe el Tag UBL
   - **Mensaje:** Solo debe consignar sistema de calculo si el tributo es ISC
   - **Listados:** -

3. **ERROR 2199**
   - **Validación:** Si 'Código de tributo por línea' es '2000' (ISC) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al listado
   - **Mensaje:** El sistema de calculo del ISC es incorrecto
   - **Listados:** Catálogo<br>(008)
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2037**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR 2036**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3067**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:<br>catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2996**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR 3051**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 34. Sistema de ISC por ítem<br>Afectación otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2377**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo de la línea)

### Validaciones

1. **ERROR 2033**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV 4318**
   - **Validación:** Si el 'Código de tributo por línea' es '7152' y 'Cantidad de bolsas de plástico' es mayor a cero (cbc:BaseUnitMeasure > 0), el valor del tag es diferente al 'Monto unitario' (cbc:PerUnitAmount) por la 'Cantidad de bolsas de plástico' (cbc:BaseUnitAmount)
   - **Mensaje:** El dato ingresado en el campo cac:TaxSubtotal/cbc:TaxAmount del ítem no coincide con el valor calculado
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an5
- **Formato / Valor:** n5
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cbc:BaseUnitMeasure (Cantidad de bolsas de plástico)

### Validaciones

1. **ERROR 2892**
   - **Validación:** El formato del Tag UBL es diferente de entero mayor o igual a cero, y de hasta 5 dígitos
   - **Mensaje:** El valor del tag no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3237**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y no existe el Tag UBL
   - **Mensaje:** Debe consignar el campo cac:TaxSubtotal/cbc:BaseUnitMeasure a nivel de ítem
   - **Listados:** -

3. **ERROR 3236**
   - **Validación:** Si el Tag UBL existe y el valor del Tag UBL es mayor a cero, el valor del tag es diferente de 'Cantidad de unidades por ítem'
   - **Mensaje:** El valor ingresado en el campo cac:TaxSubtotal/cbc:BaseUnitMeasure no corresponde al valor esperado
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** "NIU"
- **Tag:** @unitCode

### Validaciones

1. **OBSERV 4320**
   - **Validación:** El valor del atributo es diferente de 'NIU'
   - **Mensaje:** El dato ingresado como unidad de medida no corresponde al valor esperado
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:PerUnitAmount (Monto unitario)

### Validaciones

1. **ERROR 2892**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El valor del tag no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3238**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y 'Cantidad de bolsas de plástico' es mayor a cero (cbc:BaseUnitMeasure > 0), el valor del tag UBL es igual a cero
   - **Mensaje:** El valor ingresado en el campo cac:TaxSubtotal/cbc:PerUnitAmount del ítem no corresponde al valor esperado
   - **Listados:** -

3. **OBSERV 4237**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y 'Cantidad de bolsas de plástico' es mayor a cero (cbc:BaseUnitMeasure > 0) y 'Tipo de moneda  en la cual se emite la nota de crédito electrónica' es igual a 'PEN', el valor del tag UBL es diferente de la tasa vigente de ICBPER a la fecha de emisión
   - **Mensaje:** La tasa del tributo de la línea no corresponde al valor esperado
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)

### Validaciones

1. **ERROR 2037**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR 2036**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3067**
   - **Validación:** Existe en el mismo ítem más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR 2996**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR 3051**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 35. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR 2377**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 36. Valor de venta por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cbc:LineExtensionAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2370**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en LineExtensionAmount del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3271**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01', y existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el importe es diferente al resultado de multiplicar el 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' por 'Cantidad de unidades por ítem que modifica', con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -

3. **OBSERV 4288**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01', y existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el importe es diferente al resultado de multiplicar el 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' por 'Cantidad de unidades por ítem que modifica', con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -

4. **ERROR 3271**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01', y no existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor difiere del resultado del 'Valor unitario por ítem que modifica' por la 'Cantidad de unidades por ítem que modifica', con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -

5. **OBSERV 4288**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01', y no existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor difiere del resultado del 'Valor unitario por ítem que modifica' por la 'Cantidad de unidades por ítem que modifica', con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -
## 36. Valor de venta por ítem que modifica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## Totales de la nota de crédito. Registro Totales de la nota de crédito

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
## 37. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cbc:TaxAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2956**
   - **Validación:** No existe el tag /CreditNote/cac:TaxTotal
   - **Mensaje:** El Monto total de impuestos es obligatorio
   - **Listados:** -

2. **ERROR 3020**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos no cumple con el formato establecido
   - **Listados:** -

3. **ERROR 3294**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', el valor del Tag UBL es diferente de la sumatoria de 'Sumatoria de tributos' (cbc:TaxAmount) de los  tributos '1000', '1016', '2000', '7152' y '9999',  con una tolerancia + - 1
   - **Mensaje:** La sumatoria de impuestos globales no corresponde al monto total de impuestos.
   - **Listados:** -

4. **OBSERV 4301**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', el valor del Tag UBL es diferente de la sumatoria de 'Sumatoria de tributos' (cbc:TaxAmount) de los  tributos '1000', '1016', '2000', '7152' y '9999',  con una tolerancia + - 1
   - **Mensaje:** La sumatoria de impuestos globales no corresponde al monto total de impuestos.
   - **Listados:** -

5. **ERROR 3024**
   - **Validación:** Existe a nivel global más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de totales
   - **Listados:** -

6. **ERROR 2638**
   - **Validación:** Si existe alguna línea (/CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal) con 'Monto base' mayor a cero (cbc:TaxableAmount) para los tributos '1000', '1016', '9995', '9996', '9997' o '9998', y no existe su respectivo tag de totales del tributo (/CreditNote/cac:TaxTotal/cac:TaxSubtotal con cbc:ID igual al tributo de la línea)
   - **Mensaje:** Si tiene operaciones de un tributo en alguna línea, debe consignar el tag del total del tributo
   - **Listados:** -
## 37. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR 3003**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR 2999**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR 3273**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el 'Código de tributo' es '9995', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones de exportación con 'Código de tributo de línea' igual a '9995' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - Exportaciones de línea no corresponden al total
   - **Listados:** -

4. **OBSERV 4295**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el 'Código de tributo' es '9995', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones de exportación con 'Código de tributo de línea' igual a '9995' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - Exportaciones de línea no corresponden al total
   - **Listados:** -

5. **ERROR 3275**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el 'Código de tributo' es '9997', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones exoneradas con 'Código de tributo de línea' igual a '9997' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones exoneradas de línea no corresponden al total
   - **Listados:** -

6. **OBSERV 4297**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el 'Código de tributo' es '9997', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones exoneradas con 'Código de tributo de línea' igual a '9997' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones exoneradas de línea no corresponden al total
   - **Listados:** -

7. **ERROR 3274**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el 'Código de tributo' es '9998', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones inafectas con 'Código de tributo de línea' igual a '9998' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1

8. **OBSERV 4296**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el 'Código de tributo' es '9998', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones inafectas con 'Código de tributo de línea' igual a '9998' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones inafectas de línea no corresponden al total
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "0.00"
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Importe del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3000**
   - **Validación:** Si el Tag UBL existe, el valor del Tag Ubl es diferente de 0 (cero), cuando el 'Código de tributo' es '9995', '9997' y '9998'
   - **Mensaje:** El monto total del impuestos sobre el valor de venta de operaciones gratuitas/inafectas/exoneradas debe ser igual a 0.00
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3059**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR 3007**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3068**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -

4. **ERROR 3221**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '12' (IVAP) y existe un 'Código de tributo' (cbc:ID) con valor '9995' o '9997' o '9998' a nivel global con 'Total valor de venta' (cbc:TaxableAmount)  mayor a cero
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de nota
   - **Listados:** -

5. **ERROR 3221**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '11' (Exportación) y existe un 'Código de tributo' (cbc:ID) con valor '9997' o '9998' a nivel global con 'Total valor de venta' (cbc:TaxableAmount)  mayor a cero
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de nota
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2054**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR 2964**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 38<br>39<br>40. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2052**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR 2961**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2999**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3276**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el 'Código de tributo' es '9996', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gratuitas de línea no corresponden al total
   - **Listados:** -

3. **OBSERV 4298**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el 'Código de tributo' es '9996', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gratuitas de línea no corresponden al total
   - **Listados:** -

4. **ERROR 2641**
   - **Validación:** Si 'Código de tipo de tributo' es '9996' (Gratuita) y existe una línea con 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' ('Código de precio' igual a '02') con monto mayor a cero, el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Operacion gratuita,  debe consignar Total valor venta - operaciones gratuitas  mayor a cero
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Sumatoria de impuestos de operaciones gratuitas)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3059**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR 3007**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3068**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2054**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR 2964**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 41. Total valor de venta - operaciones gratuitas<br>Sumatoria de impuestos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2052**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR 2961**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount  (Total valor de venta operaciones gravadas)

### Validaciones

1. **ERROR 3003**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR 2999**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR 3277**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', y el 'Código de tributo' es '1000', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IGV con 'Código de tributo por línea igual a '1000' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gravadas de línea no corresponden al total
   - **Listados:** -

4. **OBSERV 4299**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', y el 'Código de tributo' es '1000', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IGV con 'Código de tributo por línea igual a '1000' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gravadas de línea no corresponden al total

5. **ERROR 3293**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', y el 'Código de tributo' es '1016', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IVAP con 'Código de tributo por línea igual a '1016' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - IVAP de línea no corresponden al total
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **OBSERV 4300**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', y el 'Código de tributo' es '1016', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item que modifica' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IVAP con 'Código de tributo por línea igual a '1016' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - IVAP de línea no corresponden al total
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de la sumatoria de IGV o IVAP, según corresponda)

### Validaciones

1. **ERROR 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3291**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el  'Código de tributo' es '1000', el valor del Tag Ubl es diferente al resultado de multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1000',  por la tasa vigente al IGV a la fecha de emisión (18.00,  10.50 o 10.00), con una tolerancia + - 1<br>Nota: Dado que la tasa del IGV  del "Documento que se modifica" es 10%, 10.5% o 18%, la validación se debe cumplir realizando el cálculo haciendo uso de la tasa consignada en las líneas
   - **Mensaje:** El cálculo del IGV es Incorrecto
   - **Listados:** -

3. **ERROR 3462**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y existe alguna línea con ('Código de tributo por línea' igual a '1000' y 'Monto base' (cbc:TaxableAmount) mayor a cero) o ('Código de tributo por línea' igual a '9996' y 'Afectación del IGV por ítem' igual a '11', '12', '13', '14', '15' o '16' y 'Monto base' (cbc:TaxableAmount) mayor a cero), y diferente tasa de tributo (cbc:Percent)<br>Nota: Todas las líneas afectas al IGV deben tener la misma tasa.
   - **Mensaje:** La tasa del IGV debe ser la misma en todas las líneas o ítems del documento y debe corresponder con una tasa vigente.

4. **OBSERV 4290**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el  'Código de tributo' es '1000', el valor del Tag Ubl es diferente al resultado de multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1000',  por la tasa vigente del IGV (18.00, 10.50 o 10.00) a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El cálculo del IGV es Incorrecto
   - **Listados:** -

5. **ERROR 3462**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01 y existe alguna línea con ('Código de tributo por línea' igual a '1000' y 'Monto base' (cbc:TaxableAmount) mayor a cero) o ('Código de tributo por línea' igual a '9996' y 'Afectación del IGV por ítem' igual a '11', '12', '13', '14', '15' o '16' y 'Monto base' (cbc:TaxableAmount) mayor a cero), y diferente tasa de tributo (cbc:Percent)<br>Nota: Todas las líneas afectas al IGV deben tener la misma tasa.
   - **Mensaje:** La tasa del IGV debe ser la misma en todas las líneas o ítems del documento y debe corresponder con una tasa vigente.
   - **Listados:** -

6. **OBSERV 4439**
   - **Validación:** Si la "Tasa del IGV" (/CreditNote/cac:CreditNoteLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent) es igual a 10 o 10.5, no existe ind_padron igual a "01" en el listado para el "Número de RUC" del Emisor y "Fecha de emisión" del comprobante  comprendida en el rango de vigencia
   - **Mensaje:** El emisor no se encuentra en el Padrón de Tasa especial del IGV - Restaurantes y hoteles
   - **Listados:** Padrones<br>con vigencia

7. **ERROR 3295**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el  'Código de tributo' es '1016', el valor del Tag Ubl es diferente al resultado de multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1016' por la tasa vigente del IVAP, con una tolerancia + - 1
   - **Mensaje:** El importe del IVAP no corresponden al determinado por la informacion consignada.
   - **Listados:** -

8. **OBSERV 4302**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el  'Código de tributo' es '1016', el valor del Tag Ubl es diferente al resultado de multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1016' por la tasa vigente del IVAP, con una tolerancia + - 1
   - **Mensaje:** El importe del IVAP no corresponden al determinado por la informacion consignada.
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR 3059**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR 3007**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3068**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -

4. **ERROR 3107**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '12' (IVAP) y existe un 'Código de tributo' (cbc:ID) con valor '1000' a nivel global con 'Total valor de venta' (cbc:TaxableAmount)  mayor a cero
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -

5. **ERROR 3107**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '11' (Exportación) y existe un 'Código de tributo' (cbc:ID) con valor '1000' o '1016' a nivel global con 'Total valor de venta' (cbc:TaxableAmount)  mayor a cero
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR 2054**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR 2964**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 42<br>43. Total valor de venta - operaciones gravadas (IGV o IVAP)<br>Sumatoria IGV o IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR 2052**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR 2961**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)

### Validaciones

1. **ERROR 3003**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR 2999**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR 3296**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', el valor del Tag UBL es diferente a la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del monto base - ISC de línea no corresponden al total
   - **Listados:** -

4. **OBSERV 4303**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', el valor del Tag UBL es diferente a la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del monto base - ISC de línea no corresponden al total
   - **Listados:** -

5. **ERROR 3297**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', y el 'Código de tributo' es '9999', el valor del Tag UBL es diferente a la sumatoria de los 'Montos base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '9999' (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del monto base - Otros tributos de línea no corresponden al total
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **OBSERV 4304**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', y el 'Código de tributo' es '9999', el valor del Tag UBL es diferente a la sumatoria de los 'Montos base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '9999' (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del monto base - Otros tributos de línea no corresponden al total
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de la Sumatoria)

### Validaciones

1. **ERROR 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR 3298**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el  'Código de tributo' es '2000', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto de tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del total del importe del tributo ISC de línea no corresponden al total
   - **Listados:** -

3. **OBSERV 4305**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el  'Código de tributo' es '2000', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto de tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del total del importe del tributo ISC de línea no corresponden al total
   - **Listados:** -

4. **ERROR 3299**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el  'Código de tributo' es '9999', el valor del Tag Ubl  y es diferente de la sumatoria de los importes de otros tributos (cbc:TaxAmount) con 'Código de tributo por línea' igual a '9999' de cada ítem (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del total del importe del tributo Otros tributos de línea no corresponden al total
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **OBSERV 4306**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el  'Código de tributo' es '9999', el valor del Tag Ubl  y es diferente de la sumatoria de los importes de otros tributos (cbc:TaxAmount) con 'Código de tributo por línea' igual a '9999' de cada ítem (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del total del importe del tributo Otros tributos de línea no corresponden al total
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR 3059**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -

2. **ERROR 3007**
   - **Validación:** El valor del Tag UBL es diferente al código del tributo del listado
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.
   - **Listados:** Catálogo<br>(005)

3. **ERROR 3068**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
   - **Listados:** -

4. **ERROR 3107**
   - **Validación:** Si "Código de tipo de nota de crédito" es 12 (IVAP)  y existe un Id '2000' con 'Monto base' mayor a cero
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -

5. **ERROR 3107**
   - **Validación:** si "Código de tipo de nota de crédito" es 11 (Exportación) y existe un ID '2000' o '9999' a nivel global
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)

### Validaciones

1. **ERROR 2054**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR 2964**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 44<br>45. Sumatoria ISC<br>Sumatoria otros tributos

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)

### Validaciones

1. **ERROR 2052**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR 2961**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Sumatoria ICBPER)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR 3306**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01' y el 'Código de tributo' es '7152', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '7152'
   - **Mensaje:** La sumatoria del total del importe del tributo ICBPER de línea no corresponden al total
   - **Listados:** -

2. **OBSERV 4321**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01' y el 'Código de tributo' es '7152', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '7152'
   - **Mensaje:** La sumatoria del total del importe del tributo ICBPER de línea no corresponden al total
   - **Listados:** -

3. **ERROR 2949**
   - **Validación:** Si  'Código de tributo' es '7152' y la 'Fecha de emisión' es menor a '2019-08-01', el valor del Tag Ubl es mayor a cero
   - **Mensaje:** El impuesto ICBPER no se encuentra vigente
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3059**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4255**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4256**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV 4257**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2054**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR 2964**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 46. Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /CreditNote/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2052**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR 2961**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 47. Sumatoria otros cargos<br>(que no afectan la base imponible)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:LegalMonetaryTotal/cbc:ChargeTotalAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2064**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en ChargeTotalAmount no cumple con el formato establecido
   - **Listados:** -
## 47. Sumatoria otros cargos<br>(que no afectan la base imponible)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 48. Importe total

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:LegalMonetaryTotal/cbc:PayableAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2062**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PayableAmount no cumple con el formato establecido
   - **Listados:** -
## 48. Importe total

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:LegalMonetaryTotal/cbc:PayableAmount

### Validaciones

1. **ERROR 3280**
   - **Validación:** Si el 'Tipo de documento que modifica' es '01', el  'Total valor de venta - operaciones gravadas' más 'Total valor de venta - operaciones inafectas' más 'Total valor de venta - operaciones exoneradas' más 'Total valor de venta - exportación' más "Total de operaciones afectas a IVAP" más 'Sumatoria IGV' más 'Sumatoria ISC' más 'Sumatoria IVAP' más 'Sumatoria otros tributos' más 'Sumatoria ICBPER' más "Sumatoria otros cargos' más 'Monto de redondeo del importe total', es diferente al valor del Tag UBL (con una tolerancia de más/menos uno)
   - **Mensaje:** El importe total del comprobante no coincide con el valor calculado
   - **Listados:** -

2. **OBSERV 4312**
   - **Validación:** Si el 'Tipo de documento que modifica' es diferente de '01', el  'Total valor de venta - operaciones gravadas' más 'Total valor de venta - operaciones inafectas' más 'Total valor de venta - operaciones exoneradas' más 'Total valor de venta - exportación' más "Total de operaciones afectas a IVAP" más 'Sumatoria IGV' más 'Sumatoria ISC' más 'Sumatoria IVAP' más 'Sumatoria otros tributos' más 'Sumatoria ICBPER' más "Sumatoria otros cargos' más 'Monto de redondeo del importe total', es diferente al valor del Tag UBL (con una tolerancia de más/menos uno)
   - **Mensaje:** El importe total del comprobante no coincide con el valor calculado
   - **Listados:** -

3. **ERROR 3315**
   - **Validación:** Si 'Código de tipo de nota de crédito' es '13' y valor del Tag UBL es diferente de cero
   - **Mensaje:** Si el tipo de nota de credito es 13, el Importe total debe ser cero
   - **Listados:** -
## 48. Importe total

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 49. Monto de redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:LegalMonetaryTotal/cbc:PayableRoundingAmount

### Validaciones

1. **ERROR 3303**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es '01', el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 49. Monto de redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **OBSERV 4314**
   - **Validación:** Si el Tag UBL existe y el 'Tipo de documento que modifica' es diferente de '01', el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 49. Monto de redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## Información adicional. Registro Información adicional
## 50. Leyendas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 52)
- **Tag:** /CreditNote/cbc:Note@languageLocaleID (Código de la leyenda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3027**
   - **Validación:** Si el atributo existe, el valor del atributo no existe en el listado
   - **Mensaje:** El valor del atributo no se encuentra en el catálogo
   - **Listados:** Catálogo<br>(052)
## 50. Leyendas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /CreditNote/cbc:Note  (Descripción de la leyenda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR 3006**
   - **Validación:** Si el formato del Tag UBL es diferente a alfanumérico de 1 a 200 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en descripcion de leyenda no cumple con el formato establecido.
   - **Listados:** -
## Información adicional - gastos por intereses de créditos hipotecarios. Registro Información adicional - gastos por intereses de créditos hipotecarios

- **Tag:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV 4235**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR 3150**
   - **Validación:** Si código producto de Sunat de la linea es '84121901', y no existe el tag con código '7001'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Tipo de préstamo
   - **Listados:** Catálogo<br>(055)

2. **ERROR 3151**
   - **Validación:** Si código producto de Sunat de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002), y no existe el tag con código '7003'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Partida Registral
   - **Listados:** -

3. **ERROR 3152**
   - **Validación:** Si código producto de Sunat de la linea es '84121901', y no existe el tag con código '7004'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Número de contrato
   - **Listados:** -

4. **ERROR 3153**
   - **Validación:** Si código producto de Sunat de la linea es '84121901', y no existe el tag con código '7005'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Fecha de otorgamiento del crédito
   - **Listados:** -

5. **ERROR 3154**
   - **Validación:** Si código producto de Sunat de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002), no existe el tag con código '7006'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Dirección del predio - Código de ubigeo
   - **Listados:** -

6. **ERROR 3155**
   - **Validación:** Si código producto de Sunat de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002),  no existe el tag con código '7007'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Dirección del predio - Dirección completa
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (N° de Contrato)

### Validaciones

1. **ERROR 3064**
   - **Validación:** De existir código de concepto igual a '7001', '7002', '7003', '7004', '7005', '7006', '7007', '7008', '7009', '7010', '7011' o '7012' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Fecha del otorgamiento del crédito)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7001 y el valor del tag es distinto al Catálogo 26
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(026)
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** (Catálogo N.° 26)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código del tipo de préstamo)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7002 y el valor del tag es distinto al Catálogo 27
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(027)
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de la Partida Registral)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7003 y el valor del tag es diferente a alfanumérico de 3 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 27)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código de indicador de primera vivienda)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7004 y el valor del tag es diferente a alfanumérico de 3 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Código de ubigeo)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7005 y el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Dirección completa y detallada)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7006 y el valor del tag es distinto al Catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Urbanización)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Provincia)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Departamento)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Distrito)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el código de concepto es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 51<br>52<br>53<br>54<br>55<br>56<br>57. N° de Contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida regsitral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..18
- **Formato / Valor:** n(15,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Monto del crédito otorgado)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7012' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 15 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional  a nivel de ítem - comprobante emitido por empresas de seguros. Registro Información adicional  a nivel de ítem - comprobante emitido por empresas de seguros
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV 4235**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Numero de póliza)

### Validaciones

1. **ERROR 3064**
   - **Validación:** De existir 'Código del concepto' igual a '7013', '7015' o '7016' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7013' y el valor del tag es diferente a alfanumérico de 1 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n1
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Tipo de seguro)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7015' y el valor del tag es diferente de '1', '2' o '3'.
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 58<br>59<br>60. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..18
- **Formato / Valor:** n(15,2)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Suma asegurada / alcance de cobertura o monto)

### Validaciones

1. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7016' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 15 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV 4235**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **- -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV 4252**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV 4251**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV 4253**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate (Fecha de inicio de vigencia)

### Validaciones

1. **ERROR 3243**
   - **Validación:** De existir 'Código del concepto' igual a '7014' y no existe el tag.
   - **Mensaje:** El XML no contiene tag o no existe información de la fecha del concepto por linea
   - **Listados:** -

2. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7014' y el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
## 61<br>62. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /CreditNote/cac:CreditNoteLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:EndDate (Fecha de término de vigencia)

### Validaciones

1. **OBSERV 4366**
   - **Validación:** De existir 'Código del concepto' igual a '7014' y no existe el tag.
   - **Mensaje:** El XML no contiene tag o no existe información de la fecha del concepto por linea
   - **Listados:** -

2. **OBSERV 4280**
   - **Validación:** Si el 'Código del concepto' es '7014' y el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
## Información adicional - Forma de pago al crédito. Registro Información adicional - Forma de pago al crédito
## 63. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an9
- **Formato / Valor:** "FormaPago"
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR 3257**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '13' y no existe al menos un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago'
   - **Mensaje:** Para el tipo de nota de credito 13 debe consignar información de la operación al credito
   - **Listados:** -
## 63. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an7
- **Formato / Valor:** "Credito"
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:PaymentMeansID (Forma de pago)

### Validaciones

1. **ERROR 3245**
   - **Validación:** Si el 'Indicador' es 'FormaPago' y no existe el tag UBL
   - **Mensaje:** Debe informar si el tipo de transaccion es al Contado o al Credito
   - **Listados:** -

2. **ERROR 3246**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es diferente de:<br>- Credito<br>- Cuota[0-9]{3}
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no cumple con el formato esperado
   - **Listados:** -

3. **ERROR 3248**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con el mismo valor del tag cbc:PaymentMeansID (se repite)
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no debe repetirse en el comprobante
   - **Listados:** -

4. **ERROR 3249**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es 'Credito', el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe al menos una cuota (no existe al menos un tag cac:PaymentTerms con cbc:ID con valor 'FormaPago' y cbc:PaymentMeansID con formato Cuota[0-9]{3}
   - **Mensaje:** Si el tipo de transaccion es al Credito debe existir al menos información de una cuota de pago
   - **Listados:** -
## 63. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:Amount (Monto neto pendiente de pago)

### Validaciones

1. **ERROR 3250**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales
   - **Mensaje:** El Monto neto pendiente de pago no cumple el formato definido
   - **Listados:** -

2. **ERROR 3251**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con 'Forma de pago' igual a 'Credito', el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL
   - **Mensaje:** Si el tipo de transaccion es al Credito debe consignarse el Monto neto pendiente de pago
   - **Listados:** -
## 63. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR 3320**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es 'Credito' y  'Código de tipo de nota de crédito' es igual a '13', el valor del Tag UBL es mayor a 'Importe Total' de la factura que modifica.<br>Validación no aplica para OSE
   - **Mensaje:** El monto neto pendiente de pago debe ser menor o igual al monto de la factura

2. **ERROR 3319**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es 'Credito', el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y 'Código de tipo de nota de crédito' es igual a '13', el valor del Tag UBL es diferente de la sumatoria del 'Monto de pago unico o de las cuotas'.
   - **Mensaje:** La suma de las cuotas debe ser igual al Monto neto pendiente de pago.
## 63. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 64<br>65. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an9
- **Formato / Valor:** "FormaPago"
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR 3257**
   - **Validación:** Si  'Código de tipo de nota de crédito' es '13' y no existe al menos un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago'
   - **Mensaje:** Para el tipo de nota de credito 13 debe consignar información de la operación al credito
   - **Listados:** -
## 64<br>65. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** Cuota<NNN><br>Ejemplo:<br>Cuota001<br>Cuota010
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:PaymentMeansID (Identificador de la cuota)

### Validaciones

1. **ERROR 3245**
   - **Validación:** Si el 'Indicador' es 'FormaPago' y no existe el tag UBL
   - **Mensaje:** Debe informar si el tipo de transaccion es al Contado o al Credito
   - **Listados:** -

2. **ERROR 3246**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es diferente de:<br>- Credito<br>- Cuota[0-9]{3}
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no cumple con el formato esperado
   - **Listados:** -

3. **ERROR 3248**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con el mismo valor del tag cbc:PaymentMeansID (se repite)
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no debe repetirse en el comprobante
   - **Listados:** -

4. **ERROR 3252**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag con formato: Cuota[0-9]{3} y no existe un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago' y con valor del tag igual a 'Credito'
   - **Mensaje:** Si existe información de cuota de pago, el tipo de transaccion debe ser al credito
   - **Listados:** -
## 64<br>65. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an15
- **Formato / Valor:** n(12,2)
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:Amount (Monto del pago único o de las cuotas)

### Validaciones

1. **ERROR 3253**
   - **Validación:** Si el 'Indicador' es 'FormaPago', y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3} y si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El Monto del pago único o de las cuotas no cumple el formato definido
   - **Listados:** -

2. **ERROR 3254**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3}, el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL
   - **Mensaje:** Si se consigna información de la cuota de pago, debe indicarse el monto de la cuota
   - **Listados:** -
## 64<br>65. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR 2071**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 64<br>65. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /CreditNote/cac:PaymentTerms/cbc:PaymentDueDate (Fecha del pago único o de las cuotas)

### Validaciones

1. **ERROR 3255**
   - **Validación:** Si el 'Indicador' es 'FormaPago', y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3} y si existe el tag, el formato es diferente de YYYY-MM-DD
   - **Mensaje:** Fecha del pago único o de las cuotas no cumple el formato definido
   - **Listados:** -

2. **ERROR 3256**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag con formato: Cuota[0-9]{3}, el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL
   - **Mensaje:** Si se consigna información de la cuota de pago, debe indicarse la fecha del pago único o de las cuotas

3. **ERROR 3321**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag con formato: Cuota[0-9]{3}, el valor del Tag UBL es menor o igual a la fecha de emisión de la factura modificada.
   - **Mensaje:** La fecha de la cuota debe ser mayor a la fecha de emisión de la factura
   - **Listados:** -
