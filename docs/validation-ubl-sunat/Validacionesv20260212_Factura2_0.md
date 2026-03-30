# Validacionesv20260212

## Hoja: Factura2_0

## -. -

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -

### Validaciones

1. **-**
   - **Validación:** <<< REVISAR HOJA "GENERAL" >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos de la Factura electrónica
## 1. Versión del UBL

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.1"
- **Tag:** /Invoice/cbc:UBLVersionID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de "2.1"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** "2.0"
- **Tag:** /Invoice/cbc:CustomizationID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no existe informacion de CustomizationID
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente de "2.0"
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 3. Numeración, conformada por serie y número correlativo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Invoice/cbc:ID
- **Uso del campo:** 1

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
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [F][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
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
## 4. Fecha de emisión

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cbc:IssueDate
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si serie del documento no inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al plazo máximo vigente.
   - **Mensaje:** Presentacion fuera de fecha
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es mayor a dos días de la fecha de envío del comprobante
   - **Mensaje:** La fecha de emision se encuentra fuera del limite permitido
   - **Listados:** -
## 5. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** hh:mm:ss
- **Tag:** /Invoice/cbc:IssueTime
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 6. Tipo de documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /Invoice/cbc:InvoiceTypeCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de InvoiceTypeCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al tipo de documento del archivo
   - **Mensaje:** InvoiceTypeCode - El valor del tipo de documento es invalido o no coincide con el nombre del archivo
   - **Listados:** Catálogo<br>(001)
## 6. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 6. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Documento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 6. Tipo de documento

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda  en la cual se emite la factura electrónica

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Invoice/cbc:DocumentCurrencyCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de DocumentCurrencyCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado.
   - **Mensaje:** El valor ingresado como moneda del comprobante no es valido (catalogo nro 02).
   - **Listados:** Catálogo<br>(002)

3. **ERROR**
   - **Validación:** La moneda de los totales de línea y totales de comprobantes (excepto para los totales de Percepción y Detracción) es diferente al valor del Tag UBL
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 7. Tipo de moneda  en la cual se emite la factura electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 4217 Alpha"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 4217 Alpha'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda  en la cual se emite la factura electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Currency"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Currency'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 7. Tipo de moneda  en la cual se emite la factura electrónica

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 8. Fecha de vencimiento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cbc:DueDate
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos de la Firma electrónica
## 9. Firma digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Formato / Valor:** -
- **Tag:** -
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< REVISAR HOJA FIRMA >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del Emisor
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de RUC)
- **Uso del campo:** 1

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
   - **Validación:** El valor del Tag UBL tiene un ind_estado diferente "00" en el listado
   - **Mensaje:** El contribuyente no esta activo
   - **Listados:** Contribuyentes

4. **ERROR**
   - **Validación:** El valor del Tag UBL tiene un ind_condicion igual a "12" en el listado
   - **Mensaje:** El contribuyente no esta habido
   - **Listados:** Contribuyentes

5. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0201 Exportación de Servicios – Prestación servicios realizados íntegramente en el país', no existe ind_padron igual a "05" en el listado para el valor del Tag UBL
   - **Mensaje:** El emisor a la fecha no se encuentra registrado ó habilitado en el Registro de exportadores de servicios SUNAT
   - **Listados:** Padrones<br>Contribuyentes

6. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el padrón de obligados a emitir a través de SEE-OSE<br>Validación no aplica para SEE-OSE
   - **Mensaje:** El emisor no se encuentra autorizado a emitir en el SEE-Desde los sistemas del contribuyente
   - **Listados:** Padrones<br>Contribuyentes

7. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2106 Venta nacional a turistas - Tax Free', no existe ind_padron igual a "14" en el listado para el valor del Tag UBL
   - **Mensaje:** El emisor electrónico no se encuentra inscrito en el Registro de Establecimientos Autorizados (REA)
   - **Listados:** Padrones<br>Contribuyentes

8. **ERROR**
   - **Validación:** El valor del Tag UBL se encuentra en el listado de Sujetos sin capacidad operativa (ind_padron igual a "02") y "Fecha de emisión" del comprobante comprendida en el rango de vigencia
   - **Mensaje:** El emisor electrónico es un Sujeto sin capacidad operativa (SSCO)
   - **Listados:** Padrones con vigencia
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** "6"
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion en tipo de documento del emisor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "6"
   - **Mensaje:** El dato ingresado no cumple con el estandar
   - **Listados:** -
## 10. Número de RUC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
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

1. **OBSERV**
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

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 11. Nombre comercial

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyName/cbc:Name
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del emisor no cumple con el formato establecido
   - **Listados:** -
## 12. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line<br>(Dirección completa y detallada)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
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

1. **OBSERV**
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

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, El formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a PE
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** Catálogo<br>(004)
## 13. Domicilio fiscal

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
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

1. **OBSERV**
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

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cac:AddressLine/cbc:Line (Dirección completa y detallada)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como direccion completa y detallada no cumple con el formato establecido.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:CitySubdivisionName (Urbanización)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 25 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como urbanización no cumple con el formato establecido
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:CityName (Provincia)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como provincia no cumple con el formato establecido
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:ID (Código de ubigeo)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** El código de Ubigeo no existe en el listado.
   - **Listados:** Catálogo<br>(013)
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:CountrySubentity (Departamento)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como departamento no cumple con el formato establecido
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:District (Distrito)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como distrito no cumple con el formato establecido
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cac:Country/cbc:IdentificationCode (Código de país)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe y 'Tipo de operación' es diferente de '0201' y '0208', el valor del Tag UBL es diferente a 'PE'
   - **Mensaje:** El codigo de pais debe ser PE
   - **Listados:** Catálogo<br>(004)
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 14. Dirección del lugar en el que se entrega el bien. Dato exclusivo para ventas itinerantes.<br>- Dirección completa y detallada<br>- Urbanización<br>- Provincia<br>- Código de ubigeo<br>- Departamento<br>- Distrito<br>- Código de país

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 15. Código del pais del uso, explotación o aprovechamiento del servicio

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Invoice/cac:Delivery/cac:DeliveryLocation/cac:Address/cac:Country/cbc:IdentificationCode (Código de país)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0201' o '0208', si el Tag UBL no existe o es vacio.
   - **Mensaje:** El XML no contiene el tag o no existe información del pais de uso, exploración o aprovechamiento
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0201' o '0208' y el Tag UBL existe, el valor es diferente al Catálogo 04  o el valor es igual a 'PE'
   - **Mensaje:** El dato ingresado como pais de uso, exploracion o aprovechamiento es incorrecto.
   - **Listados:** Catálogo<br>(004)
## 15. Código del pais del uso, explotación o aprovechamiento del servicio

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'ISO 3166-1'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 15. Código del pais del uso, explotación o aprovechamiento del servicio

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 15. Código del pais del uso, explotación o aprovechamiento del servicio

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Country'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 16. Código asignado por la SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** n4
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:AddressTypeCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si Serie del documento no inicia con número, no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del código de local anexo del emisor
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si Serie del documento inicia con número, no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del código de local anexo del emisor
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si Serie del documento no inicia con número y el Tag UBL es diferente de '0000', el valor del Tag UBL no está en el listado
   - **Mensaje:** El código de local anexo consignado no se encuentra declarado en el RUC
   - **Listados:** Establecimientos Anexos

4. **OBSERV**
   - **Validación:** Si el Tag UBL existe y es diferente de vacío y es diferente de '0000' y Serie del documento inicia con número, el valor del Tag UBL no está en el listado
   - **Mensaje:** El código de local anexo consignado no se encuentra declarado en el RUC
   - **Listados:** Establecimientos Anexos

5. **OBSERV**
   - **Validación:** Si el Tag UBL existe y es diferente de vacío, el valor del Tag es diferente a numérico de 4 dígitos.
   - **Mensaje:** El dato ingresado como local anexo no cumple con el formato establecido
   - **Listados:** -
## 16. Código asignado por la SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 16. Código asignado por la SUNAT para el establecimiento anexo declarado en el RUC. De informar un código distinto a 0000, se verificará que corresponda al código del establecimiento anexo que SUNAT tiene registrado en sus sistemas. El citado código puede ser revisado en la opción consulta de RUC de SUNAT Virtual.

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Establecimientos anexos"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Establecimientos anexos'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## Datos del adquirente o usuario
## 17. Tipo y número de documento del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Existe más de un Tag UBL en el XML cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification
   - **Mensaje:** El XML contiene mas de un tag como elemento de numero de documento del receptor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del número de documento de identidad del receptor del documento
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de documento del adquiriente o usuario' es '6', el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El numero de documento de identidad del receptor debe ser  RUC
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de documento del adquiriente o usuario' es '6', el valor del Tag UBL no está en el listado
   - **Mensaje:** El numero de RUC del receptor no existe.
   - **Listados:** Contribuyentes

5. **OBSERV**
   - **Validación:** Si 'Tipo de documento del adquiriente o usuario' es '6', el valor del Tag UBL tiene un ind_estado diferente a 00 en el listado
   - **Mensaje:** El RUC  del receptor no esta activo
   - **Listados:** Contribuyentes

6. **OBSERV**
   - **Validación:** Si 'Tipo de documento del adquiriente o usuario' es '6', el valor del Tag UBL tiene un ind_condicion igual a '12' en el listado
   - **Mensaje:** El RUC del receptor no esta habido
   - **Listados:** Contribuyentes

7. **ERROR**
   - **Validación:** Si 'Tipo de documento del adquiriente o usuario' es '4' o '7' o '0' o 'A' o 'B' o 'C' o 'D' o 'E' o 'G', el formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres (se considera cualquier carácter, no permite 'whitespace character': espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado como numero de documento de identidad del receptor no cumple con el formato establecido
   - **Listados:** -

8. **ERROR**
   - **Validación:** Si el 'Tipo de documento de identidad del adquiriente o usuario' es '1', el formato del Tag UBL es diferente de numérico de 8 dígitos
   - **Mensaje:** El DNI ingresado no cumple con el estandar.
   - **Listados:** -
## 17. Tipo y número de documento del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo
   - **Mensaje:** El XML no contiene el tag o no existe informacion del tipo de documento de identidad del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0200' o '0201' o '0204', y no existe Leyenda con 'Código de leyenda' igual a '2008', el valor del Tag UBL es  '6'
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** Parámetros (006)

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0200' o '0201' o '0202' o '0203' '0204' o '0205' '0206' o '0207' '0208' o '0401', y el valor del Tag UBL es diferente al listado y guion '-'
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** Parámetros (006)

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0112 Venta Interna - Sustenta Gastos Deducibles Persona Natural', el valor del Tag UBL es diferente de '1' y '6'
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2106 Venta nacional a turistas - Tax Free', el valor del Tag UBL es diferente de '7', 'B' y 'G'.
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si no es uno de los cuatro casos anteriores, el valor del Tag UBL es diferente de '6'
   - **Mensaje:** El dato ingresado en el tipo de documento de identidad del receptor no esta permitido.
   - **Listados:** -
## 17. Tipo y número de documento del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 17. Tipo y número de documento del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 17. Tipo y número de documento del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 18. Apellidos y nombres, denominación o razón social del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:AddressLine/cbc:Line<br>(Dirección completa y detallada)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CitySubdivisionName (Urbanización)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CityName (Provincia)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:ID (Código de ubigeo)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(013)
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:CountrySubentity (Departamento)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cbc:District (Distrito)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:RegistrationAddress/cac:Country/cbc:IdentificationCode (Código de país)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(004)
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "ISO 3166-1"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 19. Dirección del adquiriente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Country"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 20. Tipo y número de documento de identidad de otros participantes asociados a la transacción<br>Apellidos y nombres, denominación o razón social de otros participantes asociados a la transacción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cac:ShareholderParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName (Nombre)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional - Datos del sujeto que realiza la operación por cuenta del adquirente o usuario
## 21. Tipo y número de documento de identidad  del sujeto que realiza la operación por cuenta del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Tag:** /Invoice/cac:BuyerCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 21. Tipo y número de documento de identidad  del sujeto que realiza la operación por cuenta del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:BuyerCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 21. Tipo y número de documento de identidad  del sujeto que realiza la operación por cuenta del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 21. Tipo y número de documento de identidad  del sujeto que realiza la operación por cuenta del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 21. Tipo y número de documento de identidad  del sujeto que realiza la operación por cuenta del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional - Documentos relacionados. Registro Información adicional - Documentos relacionados

- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 22. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:DespatchDocumentReference/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a:<br>- [T][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{4}-[0-9]{1,8}<br>- [EG0][1-4]{1}-[0-9]{1,8}<br>- [EG07] {4}-[0-9]{1,8}<br>- [G][0-9]{3}-[0-9]{1,8}<br>- [V][A-Z0-9]{3}-[0-9]{1,8}
   - **Mensaje:** El ID de las guias debe tener informacion de la SERIE-NUMERO de guia.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de la guía de remisión relacionada" concatenada con el valor del Tag UBL se repite en el /Invoice
   - **Mensaje:** El comprobante contiene un tipo y número de Guía de Remisión repetido
   - **Listados:** -
## 22. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /Invoice/cac:DespatchDocumentReference/cbc:DocumentTypeCode (Tipo de guía relacionada)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el "Número de la guía de remisión relacionada", el formato del Tag UBL es diferente de '09' y '31'
   - **Mensaje:** El DocumentTypeCode de las guias debe ser 09 o 31
   - **Listados:** Catálogo<br>(001)
## 22. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 22. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "Tipo de Documento "
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 22. Tipo y numeración de la guía de remisión relacionada con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 23. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:ID (Número de documento)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El ID de los documentos relacionados no cumplen con el estandar.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de otro documento relacionado" concatenada con el valor del Tag UBL se repite en el /Invoice
   - **Mensaje:** El comprobante contiene un tipo y número de Documento Relacionado repetido
   - **Listados:** -
## 23. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 12)
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de documento relacionado)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el "Número de otro documento relacionado", el formato del Tag UBL es diferente de '02', '03', '04', '05', '06', '07', '08', '09', '99'
   - **Mensaje:** El DocumentTypeCode de Otros documentos relacionados tiene valores incorrectos.
   - **Listados:** Catálogo<br>(012)
## 23. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 23. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento Relacionado"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 23. Tipo y número de otro documento  relacionado con la operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## Datos del detalle o Ítem de la Factura
## 24. Número de orden del Ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos; o, es igual cero.
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro cac:InvoiceLine con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 25. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo N.° 03)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:InvoicedQuantity@unitCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo del Tag UBL o es vacío
   - **Mensaje:** Es obligatorio indicar la unidad de medida del ítem
   - **Listados:** -
## 25. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo N.° 03)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:InvoicedQuantity@unitCode

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al Catálogo N.° 03
   - **Mensaje:** El dato ingresado como unidad de medida no corresponde al valor esperado
   - **Listados:** -
## 25. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "UN/ECE rec 20"
- **Tag:** @unitCodeListID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UN/ECE rec 20'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListID es incorrecto.
   - **Listados:** Catálogo<br>(003)
## 25. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** '"United Nations Economic Commission for Europe"
- **Tag:** @unitCodeListAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListAgencyName es incorrecto.
   - **Listados:** -
## 26. Cantidad de unidades por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:InvoicedQuantity
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es cero (0)
   - **Mensaje:** El XML no contiene el tag InvoicedQuantity en el detalle de los Items o es cero (0)
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales
   - **Mensaje:** InvoicedQuantity El dato ingresado no cumple con el estandar
   - **Listados:** -
## 27. Código de producto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente de alfanumérico de 1 a 30 carácteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como codigo de producto no cumple con el formato establecido.
   - **Listados:** -
## 28. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** (Catálogo N.° 25, 25.1, 25.2 y 25.3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:CommodityClassification/cbc:ItemClassificationCode
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Número de RUC' del emisor es obligado a enviar Código de producto (se encuentra en el listado con ind_padron = '12'), y no existe el Tag UBL y no existe el 'Código de producto GTIN'
   - **Mensaje:** Debe consignar obligatoriamente Codigo de producto SUNAT o Codigo de producto GTIN
   - **Listados:** Padrones<br>Contribuyentes

2. **OBSERV**
   - **Validación:** Si el tag existe, el formato del Tag UBL es diferente a numérico de 8 dígitos
   - **Mensaje:** El Código producto de SUNAT no es válido

3. **OBSERV**
   - **Validación:** Si el tag existe, el valor del Tag UBL es diferente de 8 ceros ('00000000') y diferente de 8 nueves ('99999999'), y no se encuentra en el listado
   - **Mensaje:** El Código producto de SUNAT no es válido
   - **Listados:** Catálogo<br>(025), (025.1), (025.2) y (025.3)

4. **OBSERV**
   - **Validación:** Si el tag existe, si el valor del Tag UBL no se encuentra en el listado
   - **Mensaje:** El Código producto de SUNAT no es válido
   - **Listados:** Catálogo<br>(025)

5. **OBSERV**
   - **Validación:** Si el tag existe y tiene una longitud de 8 posiciones, el valor del Tag UBL termina en 6 ceros ('000000') o termina en 4 ceros ('0000')
   - **Mensaje:** El Codigo de producto SUNAT debe especificarse como minimo al tercer nivel jerarquico (a nivel de clase del codigo UNSPSC)
   - **Listados:** Catálogo<br>(025)

6. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0112 Venta Interna - Sustenta Gastos Deducibles Persona Natural', el comprobante no contiene ninguna línea con 'Código de producto SUNAT' con valor '84121901' o '80131501'
   - **Mensaje:** El dato ingresado como Codigo de producto SUNAT no corresponde al valor esperado para tipo de operación.
   - **Listados:** -
## 28. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** "UNSPSC"
- **Tag:** @listID
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'UNSPSC'
   - **Mensaje:** El dato ingresado como atributo @listID es incorrecto.
   - **Listados:** -
## 28. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** "GS1 US"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'GS1 US'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 28. Codigo de producto SUNAT

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..8
- **Formato / Valor:** "Item Classification"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Item Classification'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 29. Código de producto GTIN

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..14
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:StandardItemIdentification/cbc:ID (Código de producto GTIN)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-8, y la longitud  del Tag UBL es diferente de 8 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-12, y la longitud  del Tag UBL es diferente de 12 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-13, y la longitud  del Tag UBL es diferente de 13 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si el atributo @schemeID del tag es GTIN-14, y la longitud  del Tag UBL es diferente de 14 caracteres
   - **Mensaje:** El código de producto GS1 no cumple el estandar
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el tag existe y no existe el atributo @schemeID (Tipo de estructura GTIN)
   - **Mensaje:** Si utiliza el estandar GS1 debe especificar el tipo de estructura GTIN
   - **Listados:** -
## 29. Código de producto GTIN

- **Nivel:** Ítem
- **Condición informática:** C
- **Tag:** @schemeID (Tipo de estructura GTIN)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'GTIN-8', 'GTIN-12', 'GTIN-13' y 'GTIN-14'
   - **Mensaje:** El tipo de estructura GS1 no tiene un valor permitido
   - **Listados:** -
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 30. Número de placa del vehículo automotor

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..8
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '7000' y no existe el tag.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -
## 31. Descripción detallada del servicio prestado, bien vendido o cedido en uso, indicando las características

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..500
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cbc:Description
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:Item/cbc:Description en el detalle de los Items
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 1 hasta 500 caracteres (se considera cualquier carácter, permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El XML no contiene el tag o no existe informacion de cac:Item/cbc:Description del item
   - **Listados:** -
## 32. Valor unitario por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Price/cbc:PriceAmount
- **Uso del campo:** 1

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
   - **Validación:** Si existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es mayor a 0 (cero)
   - **Mensaje:** Operacion gratuita, solo debe consignar un monto referencial
   - **Listados:** -
## 32. Valor unitario por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceAmount (Valor)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** Debe existir el tag cac:AlternativeConditionPrice
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PriceAmount del Precio de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y el valor del Tag UBL es diferente al resultado de dividir: la sumatoria del 'Valor de venta por ítem' más el 'Monto total de tributos del ítem' menos los 'Monto de descuentos' que no afectan la base imponible del ítem ('Código de motivo de descuento' igual a '01') más los 'Monto de cargos' que no afectan la base imponible del ítem ('Código de motivo de cargo' igual a '48'), entre la 'Cantidad de unidades por ítem' (con una tolerancia + -1)
   - **Mensaje:** El precio unitario de la operación que está informando difiere de los cálculos realizados en base a la información remitida
   - **Listados:** -
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 16)<br>"01"
- **Tag:** /Invoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceTypeCode (Código de precio)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** Se ha consignado un valor invalido en el campo cbc:PriceTypeCode
   - **Listados:** Catálogo<br>(016)

2. **ERROR**
   - **Validación:** Existe en el mismo ítem otro cac:AlternativeConditionPrice con el mismo valor del Tag UBL (cbc:PriceTypeCode)
   - **Mensaje:** Existe mas de un tag cac:AlternativeConditionPrice con el mismo cbc:PriceTypeCode
   - **Listados:** -
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Precio"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Precio'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 33. Precio de venta unitario por item

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceAmount (Valor)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PriceAmount del Precio de venta unitario por item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) (Operaciones gratuitas), y 'Código de precio' es '02' (Valor referencial en operaciones no onerosa), el Tag UBL es mayor a 0 (cero).
   - **Mensaje:** Si existe 'Valor referencial unitario en operac. no onerosas' con monto mayor a cero, la operacion debe ser gratuita (codigo de tributo 9996)
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) (Operaciones gratuitas), y 'Código de precio' es diferente de '02' (Valor referencial en operaciones no onerosa).
   - **Mensaje:** El código de precio '02' es sólo para operaciones gratuitas
   - **Listados:** -
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 16)<br>"02"
- **Tag:** /Invoice/cac:InvoiceLine/cac:PricingReference/cac:AlternativeConditionPrice/cbc:PriceTypeCode (Código de precio)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** Se ha consignado un valor invalido en el campo cbc:PriceTypeCode
   - **Listados:** Catálogo<br>(016)

2. **ERROR**
   - **Validación:** Existe en el mismo ítem otro cac:AlternativeConditionPrice con el mismo valor del Tag UBL (cbc:PriceTypeCode)
   - **Mensaje:** Existe mas de un tag cac:AlternativeConditionPrice con el mismo cbc:PriceTypeCode
   - **Listados:** -
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "Tipo de Precio"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Precio'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 34. Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 35. Monto total de tributos del ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount (Monto total de tributos del ítem)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag cac:InvoiceLine/cac:TaxTotal
   - **Mensaje:** El xml no contiene el tag de impuesto por linea (TaxtTotal).
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Monto de tributo por línea' (cbc:TaxAmount)  de los tributos '1000', '1016', '2000', '7152' y '9999', con una tolerancia + -1
   - **Mensaje:** El importe total de impuestos por línea no coincide con la sumatoria de los impuestos por línea.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Existe en el mismo ítem más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de Item
   - **Listados:** -
## 35. Monto total de tributos del ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente de la suma del 'Valor de Venta por ítem' más el 'Monto del tributo de la línea del ISC', con una tolerancia + - 1
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si no existe en la misma línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente del 'Valor de Venta por ítem'
   - **Mensaje:** La base imponible a nivel de línea difiere de la información consignada en el comprobante
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID (Moneda base)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de tributo por línea de IGV/IVAP)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9995' o '9997' o '9998', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), y la 'Afectación al IGV o IVAP' es '11', '12', '13', '14', '15', '16' o '17', el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y la 'Afectación al IGV o IVAP' es  '21', '31', '32', '33', '34', '35', '36', '37' o '40', el valor del tag UBL es diferente de 0
   - **Mensaje:** El monto de afectacion de IGV por linea debe ser igual a 0.00 para Exoneradas, Inafectas, Exportación, Gratuitas de exoneradas o Gratuitas de inafectas.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000' o '1016' y<br>'Monto base' mayor a 'seis centésimas' (cbc:TaxableAmount > 0.06), el valor del tag UBL es igual a 0
   - **Mensaje:** El monto de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si 'Afectación al IGV o IVAP' es '10','11', '12', '13', '14', '15', '16' o '17', el valor del tag es diferente a la tasa del tributo por el monto base Imponible IGV/IVAP de la línea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación del IGV/IVAP no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del IGV o  Tasa del IVAP)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0) y la 'Afectación al IGV o IVAP' es '11', '12', '13', '14', '15', '16' o '17, el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '1000' o '1016', y  'Monto base' mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de IGV por linea debe ser diferente a 0.00.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 07)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode (Afectación al IGV o IVAP cuando corresponda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '2000' (ISC) o '9999' (Otros tributos), cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cbc:TaxExemptionReasonCode de Afectacion al IGV
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '2000' (ISC) o '9999' (Otros tributos), existe el tag UBL
   - **Mensaje:** Afectación de IGV no corresponde al código de tributo de la linea.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es diferente a '2000' (ISC) o '9999' (Otros tributos), cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al listado según su código de tributo.
   - **Mensaje:** El tipo de afectacion del IGV es incorrecto
   - **Listados:** Catálogo<br>(007)

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es exportación '0200', '0201', '0202', '0203', '0204', '0205', '0206', '0207' o '0208', el valor del tag UBL es diferente a '40'.
   - **Mensaje:** Operaciones de exportacion, deben consignar Tipo Afectacion igual a 40
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Afectación al IGV o IVAP' es '17' y  'Monto base' es mayor a cero, y existe otra línea con 'Afectación al IGV o IVAP por ítem' diferente de '17' y 'Monto base' mayor a cero
   - **Mensaje:** Comprobante operacion sujeta IVAP solo debe tener ítems con código de afectación del IGV igual a 17
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Afectacion del IGV"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Afectacion del IGV'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)
- **Uso del campo:** 1

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
   - **Validación:** Si 'Tipo de operación' es diferente de '2100', '2101', '2102', '2103', '2104' y '0112', y no existe en el ítem un cac:TaxSubtotal con monto base mayor a cero (cbc:TaxableAmount > 0) y cbc:ID con alguno de los siguientes valores: '1000', '1016', '9995', '9996', '9997' o '9998'
   - **Mensaje:** El XML debe contener al menos un tributo por linea de afectacion por IGV
   - **Listados:** -

5. **ERROR**
   - **Validación:** En una línea sólo pueden existir las siguientes combinaciones de códigos de tributos con 'Monto base' mayor a cero (cbc:TaxableAmount  > 0):<br>- '1000', '2000' y/o '9999'<br>- '1016' y '9999'<br>- '9995' y 9999'<br>- '9996', '2000' y/o '9999'<br>- '9997', '2000 'y/o '9999'<br>- '9998', '2000' y/o '9999'
   - **Mensaje:** La combinación de tributos no es permitida
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 36. Afectación al IGV por ítem<br>Afectación al IVAP por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxableAmount de la linea no cumple con el formato establecido
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo de la línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si  el 'Código de tributo por línea' es '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente a la tasa del tributo por el monto base ISC de la linea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación del ISC no corresponde al monto de afectacion de linea.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el 'Código de tributo por línea' es '9999' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag es diferente a la tasa del tributo por el monto base Otros tributos de la linea (con una tolerancia + - 1)
   - **Mensaje:** El producto del factor y monto base de la afectación de otros tributos no corresponde al monto de afectacion de linea.
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent (Tasa del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag de la tasa del tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado como factor de afectacion por linea no cumple con el formato establecido.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '2000' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del tag UBL es igual a 0
   - **Mensaje:** El factor de afectación de ISC por linea debe ser diferente a 0.00.
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 08)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TierRange (Tipo de sistema de ISC)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es '2000' (ISC) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), no existe el Tag UBL
   - **Mensaje:** Si existe monto de ISC en el ITEM debe especificar el sistema de calculo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es diferente '2000' (ISC), existe el Tag UBL
   - **Mensaje:** Solo debe consignar sistema de calculo si el tributo es ISC
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es '2000' (ISC) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor del Tag UBL es diferente al listado
   - **Mensaje:** El sistema de calculo del ISC es incorrecto
   - **Listados:** Catálogo<br>(008)
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)
- **Uso del campo:** 1

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
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 37. Sistema de ISC por ítem<br>Afectacion otros tributos por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo de la línea)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código de tributo por línea' es '7152' y 'Cantidad de bolsas plásticas' es mayor a cero (cbc:BaseUnitMeasure > 0), el valor del tag es diferente al 'Monto unitario' (cbc:PerUnitAmount) por la 'Cantidad de bolsas de plástico' (cbc:BaseUnitMeasure)
   - **Mensaje:** El dato ingresado en el campo cac:TaxSubtotal/cbc:TaxAmount del ítem no coincide con el valor calculado
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an5
- **Formato / Valor:** n5
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:BaseUnitMeasure (Cantidad de bolsas de plástico)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de entero mayor o igual a cero, y de hasta 5 dígitos
   - **Mensaje:** El valor del tag no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y no existe el Tag UBL
   - **Mensaje:** Debe consignar el campo cac:TaxSubtotal/cbc:BaseUnitMeasure a nivel de ítem
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el Tag UBL existe y el valor del Tag UBL es mayor a cero, el valor del tag es diferente de 'Cantidad de unidades por ítem'
   - **Mensaje:** El valor ingresado en el campo cac:TaxSubtotal/cbc:BaseUnitMeasure no corresponde al valor esperado
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** "NIU"
- **Tag:** @unitCode
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** El valor del atributo es diferente de 'NIU'
   - **Mensaje:** El dato ingresado como unidad de medida no corresponde al valor esperado
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:PerUnitAmount (Monto unitario)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El valor del tag no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y 'Cantidad de bolsas de plástico' es mayor a cero (cbc:BaseUnitMeasure > 0), el valor del tag UBL es igual a cero
   - **Mensaje:** El valor ingresado en el campo cac:TaxSubtotal/cbc:PerUnitAmount del ítem no corresponde al valor esperado
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código de tributo por línea' es igual a '7152' y 'Cantidad de bolsas de plástico' es mayor a cero (cbc:BaseUnitMeasure > 0) y 'Tipo de moneda  en la cual se emite la factura electrónica' es igual a 'PEN', el valor del tag UBL es diferente de la tasa vigente de ICBPER a la fecha de emisión
   - **Mensaje:** La tasa del tributo de la línea no corresponde al valor esperado
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por línea)
- **Uso del campo:** 1

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
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del nombre de tributo de la línea
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** Catálogo<br>(005)
## 37-A. Impuesto al consumo de bolsas de plástico por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo por línea' (Catálogo 5)
   - **Mensaje:** El Name o TaxTypeCode debe corresponder al codigo de tributo del item
   - **Listados:** Catálogo<br>(005)
## 38. Valor de venta por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:LineExtensionAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en LineExtensionAmount del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el importe es diferente al resultado de multiplicar el 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' por 'Cantidad de unidades por ítem', menos los descuentos que afecten la base imponible del ítem ('Código de motivo de descuento' igual a '00') más los cargos que afecten la base imponible del ítem ('Código de motivo de cargo' igual a '47'),  con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si no existe en la línea un cac:TaxSubtotal con 'Código de tributo por línea' igual a '9996' cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), el valor difiere del resultado del Valor unitario por ítem por la Cantidad de unidades por ítem, menos los descuentos que afecten la base imponible del ítem ('Código de motivo de descuento' igual a '00') más los cargos que afecten la base imponible del ítem ('Código de motivo de cargo' igual a '47'),  con una tolerancia + - 1.
   - **Mensaje:** El valor de venta por ítem difiere de los importes consignados.
   - **Listados:** -
## 38. Valor de venta por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an5
- **Formato / Valor:** "true" / "false"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:ChargeIndicator (Indicador de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'true' para 'código de motivo de cargo' igual a '47' y '48'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para 'Código de motivo de descuento' igual a '00' y '01'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 53)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:AllowanceChargeReasonCode (Código de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento por item.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El valor ingresado como codigo de motivo de cargo/descuento por linea no es valido (catalogo 53)
   - **Listados:** Catálogo<br>(053)

3. **OBSERV**
   - **Validación:** El valor del tag es diferente de '00', '01', '47' y '48'
   - **Mensaje:** El dato ingresado como cargo/descuento no es valido a nivel de ítem.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:MultiplierFactorNumeric (Factor de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El factor de cargo/descuento por linea no cumple con el formato establecido.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:Amount (Monto de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El formato ingresado en el tag cac:InvoiceLine/cac:Allowancecharge/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag 'Código de motivo de cargo/descuento' y existe 'Factor de cargo/descuento' con monto mayor a cero, el importe difiere del resultado de multiplicar 'Monto base del cargo/descuento' por el 'Factor de cargo/descuento', con una tolerancia + - 1.
   - **Mensaje:** El valor de cargo/descuento por ítem difiere de los importes consignados.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:BaseAmount (Monto base del cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El Monto base de cargo/descuento por linea no cumple con el formato establecido.
   - **Listados:** -
## 39. Cargo/descuento por ítem

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## Totales de la Factura. Registro Totales de la Factura

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 40. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cbc:TaxAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag /Invoice/cac:TaxTotal
   - **Mensaje:** El Monto total de impuestos es obligatorio
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el monto total de impuestos no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente de la sumatoria de 'Monto de la sumatoria' (cbc:TaxAmount) de los  tributos '1000', '1016', '2000', '7152' y '9999',  con una tolerancia + - 1
   - **Mensaje:** La sumatoria de impuestos globales no corresponde al monto total de impuestos.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Existe a nivel global más de un tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de totales
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si existe alguna línea (/Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal) con 'Monto base' mayor a cero (cbc:TaxableAmount) para los tributos '1000', '1016', '9995', '9996', '9997' o '9998', y no existe su respectivo tag de totales del tributo (/Invoice/cac:TaxTotal/cac:TaxSubtotal con cbc:ID igual al tributo de la línea)
   - **Mensaje:** Si tiene operaciones de un tributo en alguna línea, debe consignar el tag del total del tributo
   - **Listados:** -
## 40. Monto total de tributos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el 'Código de tributo' es '9995', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones de exportación con 'Código de tributo de línea' igual a '9995' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - Exportaciones de línea no corresponden al total
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si el 'Código de tributo' es '9997', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones exoneradas con 'Código de tributo de línea' igual a '9997' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), menos los 'Montos de Descuentos globales' por anticipos exonerados (Código '05'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones exoneradas de línea no corresponden al total
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si el 'Código de tributo' es '9998', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por ítem' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones inafectas con 'Código de tributo de línea' igual a '9998' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), menos los 'Montos de Descuentos globales' por anticipos inafectos (Código '06'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones inafectas de línea no corresponden al total
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si existe 'Código de leyenda' igual a '2001', no existe el ‘Total valor de venta de operaciones exoneradas’ (cbc:TaxableAmount con 'Código de tributo' igual a '9997') o existe con valor igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2001, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

7. **ERROR**
   - **Validación:** Si existe 'Código de leyenda' igual a '2002', no existe el ‘Total valor de venta de operaciones exoneradas’ (cbc:TaxableAmount con 'Código de tributo' igual a '9997') o existe con valor igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2002, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

8. **ERROR**
   - **Validación:** Si existe 'Código de leyenda' igual a '2003', no existe el ‘Total valor de venta de operaciones exoneradas’ (cbc:TaxableAmount con 'Código de tributo' igual a '9997') o existe con valor igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2003, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)

9. **ERROR**
   - **Validación:** Si existe 'Código de leyenda' igual a '2008', no existe el ‘Total valor de venta de operaciones exoneradas’ (cbc:TaxableAmount con 'Código de tributo' igual a '9997') o existe con valor igual a 0 (cero)
   - **Mensaje:** Si se utiliza la leyenda con código 2008, el total de operaciones exoneradas debe ser mayor a 0.00
   - **Listados:** Catálogo<br>(052)
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "0.00"
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Importe del tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag Ubl es diferente de 0 (cero), cuando el 'Código de tributo' es '9995', '9997' y '9998'
   - **Mensaje:** El monto total del impuestos sobre el valor de venta de operaciones gratuitas/inafectas/exoneradas debe ser igual a 0.00
   - **Listados:** -
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

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

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es de exportación '0200' o '0201' o '0202' o '0203' o '0204' o '0205' o '0206' o '0207' o '0208' y existe un ID '9997' o '9998' a nivel global
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 41<br>42<br>43. Total valor de venta - exportación<br>Total valor de venta - operaciones inafectas<br>Total valor de venta - operaciones exoneradas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Total valor de venta - operaciones gratuitas)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo' es '9996', el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gratuitas de línea no corresponden al total
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo' es '9996' (Gratuita) y existe una línea con 'Valor referencial unitario por ítem en operaciones gratuitas (no onerosas)' ('Código de precio' igual a '02') con monto mayor a cero, el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Operacion gratuita,  debe consignar Total valor venta - operaciones gratuitas  mayor a cero
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo' es '9996' (Gratuita) y 'Código de leyenda' es '1002', el valor del Tag UBL es igual a 0 (cero)
   - **Mensaje:** Si existe leyenda Transferencia Gratuita debe consignar Total Valor de Venta de Operaciones Gratuitas
   - **Listados:** -
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Sumatoria de tributos de operaciones gratuitas)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si  'Código de tributo' es '9996', el valor del Tag UBL es diferente de la sumatoria de 'Monto de IGV' (cbc:TaxAmount) que correspondan a ítems de operaciones gratuitas con 'Código de tributo por línea' igual a '9996' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount>0), con una tolerancia + - 1
   - **Mensaje:** La sumatoria de los IGV de operaciones gratuitas de la línea (codigo tributo 9996) no corresponden al total
   - **Listados:** -
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

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
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 44<br>45. Total valor de venta - operaciones gratuitas<br>Sumatoria de tributos de operaciones gratuitas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount  (Total valor de venta operaciones gravadas)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo' es '1000' y  el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IGV con 'Código de tributo por línea' igual a '1000' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), menos los 'Monto de descuento global' que afectan la base imponible ('Código de motivo de descuento' igual a '02' y '04') más 'Montos de cargo global' que afectan la base imponible ('Código de motivo de cargo' igual a  '49'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - operaciones gravadas de línea no corresponden al total
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de tributo' es '1016' y  el Tag UBL existe, el valor del Tag UBL es diferente a la sumatoria de 'Valor de venta por item' (cbc:LineExtensionAmount) que correspondan a ítems de operaciones gravadas con el IVAP con 'Código de tributo por línea' igual a '1016' y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), menos los 'Monto de descuento global' que afectan la base imponible ('Código de motivo de descuento' igual a '02' y '04'), más los 'Monto de cargo global' que afectan la base ('Código de motivo de cargo' igual a '49'), con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total valor de venta - IVAP de línea no corresponden al total
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de la sumatoria de IGV/IVAP según corresponda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si  'Código de tributo' es '1000', el valor del Tag Ubl es diferente al resultado de multiplicar la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '1000', menos 'Monto de descuentos' globales que afectan la base (Código '02' y '04'), más los 'Montos de cargos' globales que afectan la base (Código 49), menos los 'anticipos ISC' (Codigo 20) por la tasa vigente al IGV (18.00 o 10.50) a la fecha de emisión, con una tolerancia + - 1<br>*Nota: Dado que la tasa vigente del IGV es 10.5% y 18%, la validación debe cumplir realizando el cálculo según la tasa consignada en las líneas
   - **Mensaje:** El cálculo del IGV es Incorrecto
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto de la sumatoria de IGV/IVAP según corresponda)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe alguna línea con ('Código de tributo por línea' igual a '1000' y 'Monto base' (cbc:TaxableAmount) mayor a cero) o ('Código de tributo por línea' igual a '9996' y 'Afectación del IGV por ítem' igual a '11', '12', '13', '14', '15' o '16' y 'Monto base' (cbc:TaxableAmount) mayor a cero), y diferente tasa de tributo (cbc:Percent)<br>*Nota: Todas las líneas afectas al IGV deben tener la misma tasa vigente.
   - **Mensaje:** La tasa del IGV debe ser la misma en todas las líneas o ítems del documento y debe corresponder con una tasa vigente.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si la "Tasa del IGV" (/Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent) es igual a 10.5, no existe ind_padron igual a "01" en el listado para el "Número de RUC" del Emisor y "Fecha de emisión" del comprobante  comprendida en el rango de vigencia
   - **Mensaje:** El emisor no se encuentra en el Padrón de Tasa especial del IGV - Restaurantes y hoteles
   - **Listados:** Padrones con vigencia

3. **ERROR**
   - **Validación:** Si  'Código de tributo' es '1016', el valor del Tag UBL es diferente al resultado de multiplicar la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '1016', menos los 'Monto de descuentos' globales que afectan la base ('Código de motivo de descuento' igual a '02' y '04'), más los 'Monto de cargos' globales que afectan la base ('Código de motivo de cargo' igual a '49') por la tasa vigente del IVAP, con una tolerancia + - 1
   - **Mensaje:** El importe del IVAP no corresponden al determinado por la informacion consignada.
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

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

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es de exportación '0200' o '0201' o '0202' o '0203' o '0204' o '0205' o '0206' o '0207' o '0208' y existe un ID '1000' o '1016' a nivel global
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 46<br>47. Total valor de venta - operaciones gravadas (IGV/IVAP)<br>Sumatoria de IGV/IVAP

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código de tributo' es diferente de '7152' y no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de total valor de venta globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el total valor de venta globales no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo' es '2000', si el Tag UBL existe y el valor del Tag UBL es diferente a la sumatoria de los 'Monto base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del monto base - ISC de línea no corresponden al total
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe el Tag y el 'Código de tributo' es '9999', el valor del Tag UBL es diferente a la sumatoria de los 'Montos base' (cbc:TaxableAmount) de los ítems con 'Código de tributo por línea' igual a '9999'
   - **Mensaje:** La sumatoria del monto base - Otros tributos de línea no corresponden al total
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount  (Monto de la sumatoria)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si  'Código de tributo' es '2000', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto de tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '2000' y que no correspondan a una operación gratuita (*) menos los 'Anticipo de ISC' ('Código de motivo de descuento' igual a '20'), con una tolerancia + - 1.<br>(*) No considerar en la sumatoria aquellas líneas que tienen un 'Código de tributo por línea' igual a '9996' con monto base mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** La sumatoria del total del importe del tributo ISC de línea no corresponden al total
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si  'Código de tributo' es '7152', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto del tributo de la línea'  (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '7152'
   - **Mensaje:** La sumatoria del total del importe del tributo ICBPER de línea no corresponden al total
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si  'Código de tributo' es '7152' y la 'Fecha de emisión' es menor a '2019-08-01', el valor del Tag Ubl es mayor a cero
   - **Mensaje:** El impuesto ICBPER no se encuentra vigente
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si  'Código de tributo' es '9999', el valor del Tag Ubl es diferente de la sumatoria de los 'Monto del tributo de la línea' (cbc:TaxAmount) de los ítems con 'Código de tributo por línea' igual a '9999', con una tolerancia + - 1
   - **Mensaje:** La sumatoria del total del importe del tributo Otros tributos de línea no corresponden al total
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)
- **Uso del campo:** 1

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

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es de exportación '0200' o '0201' o '0202' o '0203' o '0204' o '0205' o '0206' o '0207' o '0208' y existe un ID '2000' o '9999' a nivel global
   - **Mensaje:** El dato ingresado como codigo de tributo global es invalido para tipo de operación.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Código de tributo' es '2000' y 'Monto base' es mayor a cero, y existe un ítem con código de 'Afectación al IGV o IVAP' con valor '17' (IVAP) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0)
   - **Mensaje:** Factura de operacion sujeta al IVAP , no debe consignar valor para ISC o debe ser 0
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name (Nombre de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Nombre del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag nombre del tributo no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 48<br>49<br>49-A. Sumatoria ISC<br>Sumatoria otros tributos<br>Sumatoria ICBPER

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode (Código internacional de tributo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag código de tributo internacional de impuestos globales
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el tag es diferente al 'Código internacional del tributo' del listado según el 'Código de tributo' (Catálogo 5)
   - **Mensaje:** El valor del tag codigo de tributo internacional no corresponde al esperado.
   - **Listados:** Catálogo<br>(005)
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true" / "false"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'true' para 'Código de motivo de cargo' igual a '45', '46', '49', '50', '51', '52' y '53'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'false' para 'Código de motivo de descuento' igual a '02', '03', '04', '05', '06' y '20'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 53)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es igual a '00', '01', '47' o '48'
   - **Mensaje:** El dato ingresado como cargo/descuento no es valido a nivel global.
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:MultiplierFactorNumeric (Factor de cargo/descuento)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en factor de cargo o descuento global no cumple con el formato establecido.
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:Amount (Monto del cargo/descuento global)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag 'Código de motivo de cargo/descuento' y existe 'Factor de cargo/descuento' con monto mayor a cero, el importe difiere del resultado de multiplicar 'Monto base del cargo/descuento' por el 'Factor de cargo/descuento', con una tolerancia + - 1.
   - **Mensaje:** El valor de cargo/descuento global difiere de los importes consignados
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe el tag 'Código de motivo de cargo/descuento' con valor igual a '04', '05', '06' o '20', el valor del tag UBL es mayor a cero, y el 'Total de anticipos' no existe o es cero (cac:LegalMonetaryTotal/cbc:PrepaidAmount)
   - **Mensaje:** Si se informa descuentos globales por anticipo debe existir 'Total de anticipos' con monto mayor a cero
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:BaseAmount (Monto base del cargo/descuento)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en base monto por cargo/descuento globales no cumple con el formato establecido
   - **Listados:** -
## 50. Cargos y/o descuentos globales

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 51. Sumatoria otros descuentos (que no afectan la base imponible del IGV)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:AllowanceTotalAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el campo Total Descuentos no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es diferente a la sumatoria de los 'Montos de descuentos' de línea que no afectan la base (con 'Código de motivo de descuento' igual a '01') y los 'Montos de descuentos' globales que no afectan la base (con 'Código de motivo de descuento' igual a '03' y '63'), con una tolerancia de + - 1
   - **Mensaje:** La sumatoria consignados en descuentos globales no corresponden al total.
   - **Listados:** -
## 51. Sumatoria otros descuentos (que no afectan la base imponible del IGV)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 52. Sumatoria otros cargos (que no afectan la base imponible del IGV)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:ChargeTotalAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en ChargeTotalAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es diferente a la sumatoria de los 'Montos de cargos' de línea que no afectan la base (con 'Código de motivo de cargo' igual a '48') y los 'Montos de cargos' globales que no afectan la base (con 'Código de motivo de cargo' igual a '45, '46' y '50'), con una tolerancia de + - 1
   - **Mensaje:** La sumatoria consignados en cargos globales no corresponden al total
   - **Listados:** -
## 52. Sumatoria otros cargos (que no afectan la base imponible del IGV)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 53. Importe total de la venta, cesión en uso o del servicio prestado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en PayableAmount no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el valor del tag difiere de la sumatoria del 'Total precio de venta' más 'Sumatoria otros cargos (que no afectan la base imponible del IGV)' menos 'Sumatoria otros descuentos (que no afectan la base imponible del IGV)' menos 'Total anticipos' de corresponder y más 'Monto de redondeo del importe total',  con una tolerancia + - 1.
   - **Mensaje:** El importe total del comprobante no coincide con el valor calculado
   - **Listados:** -
## 53. Importe total de la venta, cesión en uso o del servicio prestado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 54. Total valor de venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:LineExtensionAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL no existe
   - **Mensaje:** Debe consignar el Total Valor de Venta
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en total valor de venta no cumple con el estandar
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del tag es diferente de la sumatoria del 'Valor de venta por ítem' (cbc:LineExtensionAmount) de los ítems con 'Código de tributo por línea' igual a  '1000', '1016', '9995', '9997' y '9998'  y cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), menos 'Montos de descuentos globales' que afectan la base ('Código de motivo de descuento' igual a '02') más 'Montos de cargos globales' que afectan la base ('Código de motivo de cargo' igual a '49'), con una tolerancia de + - 1
   - **Mensaje:** La sumatoria de valor de venta no corresponde a los importes consignados
   - **Listados:** -
## 54. Total valor de venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 55. Total precio de venta (Subtotal de la factura)

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:TaxInclusiveAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL no existe
   - **Mensaje:** Debe consignar el Total Precio de Venta
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en total precio de venta no cumple con el formato establecido
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe el Tag UBL, y no es una factura sujeta al IVAP*, y el valor es diferente de la sumatoria de 'Total valor de venta' más 'Sumatoria ISC' más los 'Anticipos de ISC' ('Código de motivo de descuento' igual a '20') más 'Sumatoria Otros Tributos' más 'Sumatoria ICBPER' más el resultado de:<br>Multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1000', menos 'Monto de descuentos' globales que afectan la base (Código '02'), más los 'Montos de cargos' globales que afectan la base (Código '49') por la tasa vigente del IGV (18.00 o 10.50) a la fecha de emisión, con una tolerancia + - 1<br>* Se entiende que la factura no está sujeta al IVAP si no existe ninguna línea afecta al IVAP, es decir, no debe existir en la línea un cac:TaxSubtotal con cbc:ID igual a '1016' y cbc:TaxableAmount mayor a cero.<br>*Nota: se debe hacer la comprobación según la tasa indicada en la línea
   - **Mensaje:** La sumatoria del Total del valor de venta más los impuestos no concuerda con la base imponible
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe el Tag UBL, y es una factura sujeta al IVAP*, y el valor es diferente de la sumatoria de 'Total valor de venta' más 'Sumatoria Otros Tributos' más 'Sumatoria ICBPER' más el resultado de:<br>Multiplicar la sumatoria de los 'Monto base' de las líneas (cbc:TaxableAmount) con 'Código de tributo por línea' igual a '1016', menos 'Monto de descuentos' globales que afectan la base (Código '02'), más los 'Montos de cargos' globales que afectan la base (Código '49') por la tasa vigente del IVAP a la fecha de emisión, con una tolerancia + - 1<br>* Se entiende que esta sujeta al IVAP si las líneas son afectas al IVAP (cbc:ID igual a '1016' y cbc:TaxableAmount mayor a cero)
   - **Mensaje:** La sumatoria del Total del valor de venta más los impuestos no concuerda con la base imponible
   - **Listados:** -
## 55. Total precio de venta (Subtotal de la factura)

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 56. Monto de redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PayableRoundingAmount

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el tag UBL, el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 56. Monto de redondeo del importe total

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## Información adicional. Registro Información adicional

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 57. Leyenda

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 52)
- **Tag:** /Invoice/cbc:Note@languageLocaleID (Código de la leyenda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo existe, el valor del atributo no existe en el listado
   - **Mensaje:** El valor del atributo no se encuentra en el catálogo
   - **Listados:** Catálogo<br>(052)

2. **ERROR**
   - **Validación:** El valor del atributo se repite en el comprobante
   - **Mensaje:** El codigo de leyenda no debe repetirse en el comprobante.
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si existe una línea con código de 'Afectación al IGV o IVAP' con valor '17' (IVAP) cuyo 'Monto base' es mayor a cero (cbc:TaxableAmount > 0), y no existe código de leyenda igual a '2007'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2007 para el tipo de operación IVAP
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es '1001 Operación Sujeta a Detracción', y no existe código de leyenda igual a '2006'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2006 para tipo de operación de detracciones
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es '1002 - Operación Sujeta a Detracción- Recursos Hidrobiológicos', y no existe código de leyenda igual a '2006'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2006 para tipo de operación de detracciones
   - **Listados:** -

6. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es '1003 - Operación Sujeta a Detracción- Servicios de Transporte - Pasajeros', y no existe código de leyenda igual a '2006'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2006 para tipo de operación de detracciones
   - **Listados:** -

7. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es '1004 - Operación Sujeta a Detracción- Servicios de Transporte - Carga', y no existe código de leyenda igual a '2006'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2006 para tipo de operación de detracciones
   - **Listados:** -

8. **OBSERV**
   - **Validación:** Si existe Dirección del lugar en el que se entrega el bien (tag Dirección completa y detallada) y no existe código de leyenda igual a '2005'
   - **Mensaje:** El XML no contiene el codigo de leyenda 2005 para el tipo de operación Venta itinerante
   - **Listados:** -
## 57. Leyenda

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cbc:Note  (Descripción de la leyenda)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el formato del Tag UBL es diferente a alfanumérico de 1 a 200 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en descripcion de leyenda no cumple con el formato establecido.
   - **Listados:** -
## 58. Tipo de operación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 51)
- **Tag:** /Invoice/cbc:InvoiceTypeCode@listID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si no existe el atributo o es vacío
   - **Mensaje:** Debe consignar el tipo de operación
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si valor del atributo es diferente al listado (catálogo 51) según el 'Tipo de documento'
   - **Mensaje:** El dato ingresado como tipo de operación no corresponde a un valor esperado (catálogo nro. 51)
   - **Listados:** Catálogo<br>(051)

3. **ERROR**
   - **Validación:** Si tipo de operación es '2100' o '2101' o '2102' o '2103' o '2104' o '0112'  y Número de RUC se encuentra afiliado al 'SEE-Empresas supervisadas'
   - **Mensaje:** Debe enviar su comprobante por el SEE-Empresas supervisadas
   - **Listados:** Padrones<br>Contribuyentes
## 58. Tipo de operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Operacion"
- **Tag:** @name
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Tipo de Operacion'
   - **Mensaje:** El dato ingresado como atributo @name es incorrecto.
   - **Listados:** -
## 58. Tipo de operación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51"
- **Tag:** @listSchemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51'
   - **Mensaje:** El dato ingresado como atributo @listSchemeURI es incorrecto.
   - **Listados:** -
## 59. Número de la orden de compra o servicio

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..20
- **Tag:** /Invoice/cac:OrderReference/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 1 a 20 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en order de compra no cumple con el formato establecido.
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente 'true' para código de cargo igual a '45'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 53)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo del cargo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:Amount (Monto del cargo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el valor del Tag Ubl es  0 (cero), cuando el código de motivo de cargo igual a '45'
   - **Mensaje:** El monto del cargo para el para FISE debe ser igual mayor a 0.00
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:BaseAmount (Monto base del cargo)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en base monto por cargo/descuento globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag UBL es igual a 0 o no existe, cuando el código de motivo de cargo es igual a '45'
   - **Mensaje:** Para cargo/descuento FISE, debe ingresar monto base y debe ser mayor a 0.00
   - **Listados:** -
## 60. FISE (Ley N.° 29852) Fondo de Inclusión Social Energético

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 61. Restitución simplificada de derechos arancelarios

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "2010"
- **Tag:** /Invoice/cbc:Note@languageLocaleID (Código)

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(052)
## 61. Restitución simplificada de derechos arancelarios

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cbc:Note  (Descripción)

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 62. Incoterm

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Tag:** /Invoice/cac:DeliveryTerms/cbc:ID

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional - percepciones. Registro Información adicional - percepciones
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente 'true' para 'Código de motivo de cargo/descuento' igual a '51' o '52' o '53'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 53)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de cargo/descuento: Código de régimen de percepción)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto a los valores del catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2001 - Operación sujeta a percepción' y 'Forma de pago' es 'Contado', no existe un 'Código de motivo de cargo/descuento' igual a '51' o '52' o '53'
   - **Mensaje:** Si operación es sujeta a percepción y la forma de pago es Contado, debe ingresar cargo para Percepción
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si el valor del tag es igual a '51' o '52' o '53' y el 'Tipo de operación' es diferente de '2001 - Operación Sujeta a Percepción'
   - **Mensaje:** Solo debe consignar informacion de percepciones si el tipo de operación es 2001-Operación sujeta a Percepcion
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2001 - Operación sujeta a percepción', y el valor del tag es igual a '51' o '52' o '53', la 'Forma de pago' es diferente de 'Contado'
   - **Mensaje:** Solo debe consignar informacion de percepciones si la forma de pago es "Contado"
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:MultiplierFactorNumeric (Tasa percepción expresado como factor)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales
   - **Mensaje:** El dato ingresado en factor de cargo o descuento global no cumple con el formato establecido.
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:Amount (Monto de la percepción)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de motivo de cargo/descuento' es '51' o '52' o '53' (Percepción), el valor del Tag UBL es diferente a  'Base imponible de la percepción' por 'Tasa percepción expresado como factor', con una tolerancia + -1
   - **Mensaje:** El Monto de percepcion no tiene el valor correcto según el tipo de percepcion.
   - **Listados:** Parámetros (019)
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si "Código de motivo de cargo/descuento" es '51' o '52' o '53' (Percepción), el atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** El dato ingresado en moneda del monto de cargo/descuento para percepcion debe ser PEN
   - **Listados:** Catálogo<br>(002)
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:BaseAmount (Base imponible de la percepción)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en base monto por cargo/descuento globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si "Código de motivo de cargo/descuento" es '51' o '52' o '53' (Percepción) y "Tipo de moneda" del comprobante es "PEN", el valor del Tag UBL es mayor a "Importe total"
   - **Mensaje:** El Monto de percepcion no puede ser mayor al importe total del comprobante.
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del tag UBL es igual a 0 o no existe, cuando el código de motivo de cargo es igual a '51' o '52' o '53'
   - **Mensaje:** Para cargo Percepción, debe ingresar monto base y debe ser mayor a 0.00
   - **Listados:** -
## 63. Monto de la percepción en moneda nacional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si "Código de motivo de cargo/descuento" es '51' o '52' o '53' (Percepción), el atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** El dato ingresado en moneda debe ser PEN
   - **Listados:** Catálogo<br>(002)
## 64. Monto total incluido la percepción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** "Percepcion"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2001 - Operación sujeta a percepción' y 'Forma de pago' es 'Contado', no existe un cac:PaymentTerms con cbc:ID con valor igual a 'Percepcion'
   - **Mensaje:** Si forma de pago es Contado debe consignar un Payment Terms con indicador Percepcion
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es diferente de '2001', el valor del Tag UBL es igual a 'Percepcion'
   - **Mensaje:** Solo debe consignar informacion de percepciones si el tipo de operación es 2001-Operación sujeta a Percepcion
## 64. Monto total incluido la percepción

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2001 - Operación sujeta a percepción' y 'Forma de pago' es diferente de 'Contado', el valor del Tag UBL es igual a 'Percepcion'
   - **Mensaje:** Solo debe consignar informacion de percepciones si la forma de pago es "Contado"
## 64. Monto total incluido la percepción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:Amount (Monto total incluido la percepción)

### Validaciones

1. **ERROR**
   - **Validación:** Si  'Indicador' es igual a 'Percepcion' y no existe el tag
   - **Mensaje:** Debe consignar el Monto total incluido la percepcion
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El Monto total incluido la percepción no cumple con el formato establecido
## 64. Monto total incluido la percepción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo y el "Indicador" (/Invoice/cac:PaymentTerms/cbc:ID) es 'Percepción' y el atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** El dato ingresado en moneda debe ser PEN
   - **Listados:** Catálogo<br>(002)
## Información adicional  - anticipos. Registro Información adicional  - anticipos

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:PrepaidPayment/cbc:ID (Identificador del pago)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Importe del anticipo' existe y no existe el Tag UBL o es vacio
   - **Mensaje:** Falta identificador del pago del Monto de anticipo para relacionarlo con el comprobante que se realizo el  anticipo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe más de un 'Identificador de pago' con el mismo valor
   - **Mensaje:** El comprobante contiene un identificador de pago repetido en los montos anticipados
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si no existe documento con 'Tipo de comprobante que se realizó el anticipo' '02' o '03' con el mismo 'Identificador de pago' (cbc:DocumentStatusCode) que el valor del Tag UBL
   - **Mensaje:** El comprobante contiene un pago anticipado pero no se ha consignado el documento que se realizo el anticipo
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Anticipo"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Anticipo'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:PrepaidPayment/cbc:PaidAmount (Importe del anticipo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe y es menor o igual a 0 (cero)
   - **Mensaje:** PaidAmount: monto anticipado por documento debe ser mayor a cero.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe Tag UBL con valor mayor a cero, y no existe 'Total Anticipos' con monto mayor a cero
   - **Mensaje:** Si consigna montos de anticipo debe informar el Total de Anticipos
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:PrepaidPayment/cbc:PaidDate (Fecha de pago)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:DocumentStatusCode (Identificador del pago)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '02' o '03', y no existe un 'Importe del anticipo' con 'Identificador de pago' igual al valor del tag UBL
   - **Mensaje:** No existe información del Monto Anticipado para el comprobante que se realizo el anticipo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '02' o '03', y existe más de un comprobante de anticipo con el mismo identificador de pago en el comprobante
   - **Mensaje:** El comprobante contiene un identificador de pago repetido en los comprobantes que se realizo el anticipo
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de comprobante que se realizó el anticipo' es '02' o '03', y no existe el tag UBL
   - **Mensaje:** Falta identificador del pago del comprobante para relacionarlo con el monto de  anticipo
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Anticipo"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Anticipo'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:ID (Serie y Número de comprobante que se realizó el anticipo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de documento del emisor del anticipo' existe y 'Tipo de comprobante que se realizo el anticipo' es '02' (Factura), el formato del Tag UBL  es diferente a:<br>- [F][A-Z0-9]{3}-[0-9]{1,8}<br>- (E001)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** El dato ingresado debe indicar SERIE-CORRELATIVO del documento que se realizo el anticipo.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de documento del emisor del anticipo' existe y 'Tipo de comprobante que se realizo el anticipo' es '03' (Boleta), el formato del Tag UBL  es diferente a:<br>- [B][A-Z0-9]{3}-[0-9]{1,8}<br>- (EB01)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** El dato ingresado debe indicar SERIE-CORRELATIVO del documento que se realizo el anticipo.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 12)
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de comprobante que se realizó el anticipo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode), y el valor del tag UBL es diferente a '02' (Factura) o '03' (Boleta)
   - **Mensaje:** Código de documento de referencia debe ser 02 o 03.
   - **Listados:** Catálogo<br>(012)
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento Relacionado"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento Relacionado'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an11
- **Formato / Valor:** n11
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification/cbc:ID (Número de documento del emisor del anticipo)
- **Uso del campo:** 1

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
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y 'Serie del comprobante que realizó el anticipo' empieza con B o F o E, y RUC del emisor del anticipo es igual al RUC emisor de la factura, la 'Serie y número del comprobante que realizó el anticipo' no existe con estado aceptado en el listado para el RUC consignado en el emisor del anticipo
   - **Mensaje:** El comprobante que se realizo el anticipo no existe
   - **Listados:** Comprobantes de pago electrónico

4. **ERROR**
   - **Validación:** Si existe identificador de pago (cbc:DocumentStatusCode) y 'Serie del comprobante que realizó el anticipo' empieza con número, y RUC del emisor del anticipo es igual al RUC emisor de la factura, la 'Serie y número del comprobante que realizó el anticipo' no existe en el listado para el RUC consignado en el emisor del anticipo
   - **Mensaje:** El comprobante que se realizo el anticipo no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cac:IssuerParty/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento del emisor del anticipo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo del Tag UBL no existe o es diferente a 6 (RUC)
   - **Mensaje:** El tipo documento del emisor que realiza el anticipo debe ser 6 del catalogo de tipo de documento.
   - **Listados:** Catálogo<br>(006)
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 65. Importe del anticipo

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:<br>catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 66. Total de anticipos

- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PrepaidAmount
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe Tag UBL con valor mayor a cero, la suma de los 'Importe del anticipo' es diferente al valor del tag UBL
   - **Mensaje:** Total de anticipos diferente a los montos anticipados por documento.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe Tag UBL con valor mayor a cero, y no existe al menos un 'Cargos y/o descuentos globales' (cac:AllowanceCharge) con 'Indicador de cargo/descuento global' con valor '04' o '05' o '06' y con monto mayor a cero (cbc:Amount)
   - **Mensaje:** Si se informa 'Total de anticipos' debe consignar los descuentos globales por anticipo con monto mayor a cero
   - **Listados:** -
## 66. Total de anticipos

- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## Información adicional  - transporte terrestre de pasajeros. Registro Información adicional  - transporte terrestre de pasajeros

- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..20
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de asiento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3050', '3051', '3052', '3053', '3054', 3055', '3056', '3057' o '3058' y no existe el tag o es vacío
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3050', el formato del Tag UBL es diferente a alfanumérico de 1 a 20 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..20
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Información de manifiesto de pasajeros)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3051', el formato del Tag UBL es diferente a alfanumérico de 3 a 20 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de documento de identidad del pasajero

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3052', el formato del Tag UBL es diferente a alfanumérico de 3 a 15 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Tipo de documento de identidad del pasajero

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3053', el valor del tag es distinto al catálogo nro 06.
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(006)
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Nombres y apellidos del pasajero

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3054', el formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** Ciudad o lugar de destino - Código de ubigeo/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3055', el valor del tag es distinto al catálogo nro 13.
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ciudad o lugar de destino - Dirección detallada

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3056', el formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ciudad o lugar de origen - Código de ubigeo

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3057', el valor del tag es distinto al catálogo nro 13.
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)
## 95<br>96<br>97<br>98<br>99<br>100<br>101. Número de asiento<br>Información de manifiesto de pasajeros<br>Número de documento de identidad del pasajero<br>Tipo de documento de identidad del pasajero<br>Nombres y apellidos del pasajero<br>Ciudad o lugar de destino<br>Ciudad o lugar de origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ciudad o lugar de origen - Dirección detallada

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '3058', el formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 102. Fecha de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate (Fecha de inicio)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3059' y no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 103. Hora de inicio programado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** hh:mm:ss
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartTime (Hora de inicio)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3060' y no existe el tag.
   - **Mensaje:** El XML no contiene tag de la Hora del concepto por linea.
   - **Listados:** -
## Información adicional  - detracciones. Registro Información adicional  - detracciones

- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 104. Código del bien o servicio sujeto a detracción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** "Detraccion"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:ID (Indicador PaymentTerms)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '1001', '1002', '1003' o '1004', y no existe al menos un cac:PaymentTerms con cbc:ID con valor igual a 'Detraccion'
   - **Mensaje:** El XML no contiene el tag o no existe información del Codigo de BBSS de detracción para el tipo de operación.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si  'Tipo de operación' es diferente de '1001', '1002', '1003' y '1004', el valor del Tag UBL es igual a 'Detraccion'
   - **Mensaje:** El XML contiene información de codigo de bien y servicio de detracción que no corresponde al tipo de operación.
   - **Listados:** -
## 104. Código del bien o servicio sujeto a detracción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 54)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentMeansID (Código de bien o servicio)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion', no existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Codigo de BBSS de detracción para el tipo de operación.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion' y existe el Tag UBL, el valor del tag es diferente al listado
   - **Mensaje:** El codigo de bien o servicio sujeto a detracción no existe en el listado.
   - **Listados:** Catálogo<br>(054)

3. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion' y 'Tipo de operación' es '1002 - Operación Sujeta a Detracción- Recursos Hidrobiológicos', el valor del tag es diferente a '004'
   - **Mensaje:** El dato ingresado como codigo de BBSS de detracción no corresponde al valor esperado.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion' y 'Tipo de operación' es '1003 - Operación Sujeta a Detracción- Servicios de Transporte Pasajeros', el valor del tag es diferente a '028'
   - **Mensaje:** El dato ingresado como codigo de BBSS de detracción no corresponde al valor esperado.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion' y  'Tipo de operación' es '1004 - Operación Sujeta a Detracción- Servicios de Transporte Carga', el valor del tag es diferente a '027'
   - **Mensaje:** El dato ingresado como codigo de BBSS de detracción no corresponde al valor esperado.
   - **Listados:** -
## 104. Código del bien o servicio sujeto a detracción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de detraccion"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Codigo de detraccion'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 104. Código del bien o servicio sujeto a detracción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 104. Código del bien o servicio sujeto a detracción

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo54"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo54'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..10
- **Formato / Valor:** "Detraccion"
- **Tag:** /Invoice/cac:PaymentMeans/cbc:ID (Indicador PaymentMeans)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '1001', '1002', '1003' o '1004', y no existe al menos un cac:PaymentMeans con cbc:ID con valor igual a 'Detraccion'
   - **Mensaje:** El xml no contiene el tag o no existe información en el nro de cuenta de detracción
   - **Listados:** -
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:PaymentMeans/cac:PayeeFinancialAccount/cbc:ID (Número de cuenta)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador PaymentMeans' es igual a 'Detraccion', no existe el Tag UBL o es vacío.
   - **Mensaje:** El xml no contiene el tag o no existe información en el nro de cuenta de detracción
   - **Listados:** -
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 59)
- **Tag:** /Invoice/cac:PaymentMeans/cbc:PaymentMeansCode (Medio de pago)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el tag, el valor del tag es diferente al listado.
   - **Mensaje:** El dato ingreso como Forma de Pago o Medio de Pago no corresponde al valor esperado (catalogo nro 59)
   - **Listados:** Catálogo<br>(059)
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Medio de pago"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Medio de pago'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 105. Número de cuenta en el Banco de la Nación

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 106. Monto y porcentaje de la detracción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:Amount (Monto de detraccion)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion', no existe el Tag UBL
   - **Mensaje:** El xml no contiene el tag o no existe información en el monto de detraccion
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en monto de detraccion no cumple con el formato establecido
   - **Listados:** -
## 106. Monto y porcentaje de la detracción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Indicador PaymentTerms' es igual a 'Detraccion', el atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## 106. Monto y porcentaje de la detracción

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)<br>(Catálogo N.° 54)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentPercent (Tasa o porcentaje de detracción)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional - detracciones - recursos hidrobiológicos. Registro Información adicional - detracciones - recursos hidrobiológicos
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con valor '3001'
   - **Mensaje:** El XML no contiene el tag de matricula de embarcación en Detracciones para recursos hidrobiologicos.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con valor '3002'
   - **Mensaje:** El XML no contiene el tag de nombre de embarcación en Detracciones para recursos hidrobiologicos.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con valor '3003'
   - **Mensaje:** El XML no contiene el tag de tipo de especie vendidas en Detracciones para recursos hidrobiologicos.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con valor '3004'
   - **Mensaje:** El XML no contiene el tag de lugar de descarga en Detracciones para recursos hidrobiologicos.
   - **Listados:** -
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 107<br>108<br>109<br>110. Matrícula de la embarcación pesquera<br>Nombre de la embarcación pesquera<br>Descripción del tipo de la especie vendida<br>Lugar de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15<br>an..50<br>an..100<br>an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Matrícula de la Embarcación Pesquera)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Nombre de la Embarcación Pesquera)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Descripción del Tipo de la Especie vendida)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Lugar de descarga)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3001', '3002', '3003', o '3004', no existe el tag o es vacio.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '3001' y el formato del Tag UBL es diferente a alfanumérico de 1 a 15 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '3002' y el formato del Tag UBL es diferente a alfanumérico de 1 a 100 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '3003' y el formato del Tag UBL es diferente a alfanumérico de 1 a 150 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '3004' y el formato del Tag UBL es diferente a alfanumérico de 1 a 100 caractéres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con ID '3006'
   - **Mensaje:** El XML no contiene el tag de cantidad de especies vendidas en Detracciones para recursos hidrobiologicos.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:ValueQuantity (Cantidad de la Especie vendida)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3006', no existe el tag.
   - **Mensaje:** El XML no contiene tag de la cantidad del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '3006' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales.
   - **Mensaje:** El dato ingresado como cantidad del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 111. Cantidad de la especie vendida

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..3
- **Formato / Valor:** "TNE"
- **Tag:** @unitCode (Unidad de Medida)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente de 'TNE'
   - **Mensaje:** El dato ingresado como unidad de medida de cantidad de especie vendidas no corresponde al valor esperado.
   - **Listados:** -
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1002', y no existe el tag con valor '3005'
   - **Mensaje:** El XML no contiene el tag de fecha de descarga en Detracciones para recursos hidrobiologicos.
   - **Listados:** Catálogo<br>055
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 112. Fecha de descarga

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate (Fecha de descarga)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '3005', no existe el tag
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -
## Información adicional - detracciones - servicio de transporte de carga. Registro Información adicional - detracciones - servicio de transporte de carga

- **Formato / Valor:** -
- **Tag:** -
- **Uso del campo:** -
## 113. Punto de origen<br>- Código de ubigeo<br>- Dirección detallada del origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Despatch/cac:DespatchAddress/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', no existe el tag o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información del ubigeo de punto de origen en Detracciones - Servicio de transporte de carga.
   - **Listados:** Catálogo<br>(013)

2. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Catálogo<br>(013)
## 113. Punto de origen<br>- Código de ubigeo<br>- Dirección detallada del origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 113. Punto de origen<br>- Código de ubigeo<br>- Dirección detallada del origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 113. Punto de origen<br>- Código de ubigeo<br>- Dirección detallada del origen

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Despatch/cac:DespatchAddress/cac:AddressLine/cbc:Line
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información de la dirección del punto de origen en Detracciones - Servicio de transporte de carga.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como direccion completa y detallada no cumple con el formato establecido.
   - **Listados:** -
## 114. Punto de destino<br>- Código de ubigeo<br>- Dirección detallada del destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:ID (Código de Ubigeo)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información del ubigeo de punto de destino en Detracciones - Servicio de transporte de carga.
   - **Listados:** Catálogo<br>(013)

2. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Catálogo<br>(013)
## 114. Punto de destino<br>- Código de ubigeo<br>- Dirección detallada del destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 114. Punto de destino<br>- Código de ubigeo<br>- Dirección detallada del destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 114. Punto de destino<br>- Código de ubigeo<br>- Dirección detallada del destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryLocation/cac:Address/cac:AddressLine/cbc:Line (Dirección detallada)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información de la dirección del punto de destino en Detracciones - Servicio de transporte de carga.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 3 a 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como direccion completa y detallada no cumple con el formato establecido.
   - **Listados:** -
## 115. Detalle del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..500
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Despatch/cbc:Instructions
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información del Detalle del viaje en Detracciones - Servicio de transporte de carga.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de 3 a 500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como detalle del viaje no cumple con el formato establecido.
   - **Listados:** -
## 116. Valor referencial del servicio de transporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "01"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:ID (Tipo valor Referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe o existe mas de un tipo valor referencial = 01
   - **Mensaje:** Detracciones - Servicio de transporte de carga, debe tener un (y solo uno) Valor Referencial del Servicio de Transporte.
   - **Listados:** -
## 116. Valor referencial del servicio de transporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:Amount (Valor referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información del monto del valor referencial en Detracciones - Servicios de transporte de carga.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como monto valor referencial en Detracciones - Servicios de transporte de carga no cumple con el formato establecido.
   - **Listados:** -
## 116. Valor referencial del servicio de transporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## 117. Valor referencial sobre la carga efectiva

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "02"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:ID (Tipo valor Referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe o existe mas de un tipo valor referencial = 02
   - **Mensaje:** Detracciones - Servicio de transporte de carga, debe tener un (y solo uno) Valor Referencial sobre la carga efectiva.
   - **Listados:** -
## 117. Valor referencial sobre la carga efectiva

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:Amount (Valor referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información del monto del valor referencial en Detracciones - Servicios de transporte de carga.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', El formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como monto valor referencial en Detracciones - Servicios de transporte de carga no cumple con el formato establecido.
   - **Listados:** -
## 117. Valor referencial sobre la carga efectiva

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## 118. Valor referencial sobre la carga útil nominal

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "03"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:ID (Tipo valor Referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe o existe mas de un tipo valor referencial = 03
   - **Mensaje:** Detracciones - Servicio de transporte de carga, debe tener un (y solo uno) Valor Referencial sobre la carga util nominal.
   - **Listados:** -
## 118. Valor referencial sobre la carga útil nominal

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:DeliveryTerms/cbc:Amount (Valor referencial)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información del monto del valor referencial en Detracciones - Servicios de transporte de carga.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es igual a '1004', el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como monto valor referencial en Detracciones - Servicios de transporte de carga no cumple con el formato establecido.
   - **Listados:** -
## 118. Valor referencial sobre la carga útil nominal

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## Información adicional - detracciones - servicio de transporte de carga - detalle de tramos. Registro Información adicional - detracciones - servicio de transporte de carga - detalle de tramos
## 119. Punto de origen del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:PlannedPickupTransportEvent/cac:Location/cbc:ID

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el Tag existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Catálogo<br>(013)
## 119. Punto de origen del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 119. Punto de origen del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 119. Punto de origen del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "01"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cbc:ID (Identificador del servicio -valor fijo)

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 120. Punto de destino del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:PlannedDeliveryTransportEvent/cac:Location/cbc:ID

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el Tag existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Catálogo<br>(013)
## 120. Punto de destino del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:INEI"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:INEI'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 120. Punto de destino del viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Ubigeos"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Ubigeos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 121. Descripción del tramo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cbc:CarrierServiceInstructions (Descripción del tramo)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente a alfanumérico de 3 a 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como descripcion del tramo no cumple con el formato establecido.
   - **Listados:** -
## 121. Descripción del tramo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cbc:ID (Identificador de tramo)

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 122. Valor preliminar referencial sobre la carga efectiva (Por el tramo virtual recorrido)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:DeliveryTerms/cbc:Amount
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor refrencia del tramo virtual no cumple con el formato establecido.
   - **Listados:** -
## 122. Valor preliminar referencial sobre la carga efectiva (Por el tramo virtual recorrido)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## Información adicional - detracciones - servicio de transporte de carga - detalle de el(los) vehículo(s). Registro Información adicional - detracciones - servicio de transporte de carga - detalle de el(los) vehículo(s)
## 123. Configuracion vehicular del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** Códigos del D.S. 058-2003-MTC y modificatorias
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:TransportEquipment/cbc:SizeTypeCode

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente a alfanumérico de 1 a 15 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como configuración vehicular no cumple con el formato establecido.
   - **Listados:** -
## 123. Configuracion vehicular del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:MTC"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:MTC'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 123. Configuracion vehicular del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Configuracion Vehícular"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Configuracion Vehícular'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 123. Configuracion vehicular del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "01"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:TransportEquipment/cbc:SizeTypeCode
- **Uso del campo:** 0..1
## 124. Carga útil en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..14
- **Formato / Valor:** "01"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:MeasurementDimension/cbc:AttributeID (Tipo de carga: Carga útil)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, es diferente de '01' y '02'
   - **Mensaje:** El dato ingresado como tipo de carga util es incorrecto.
   - **Listados:** -
## 124. Carga útil en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:MeasurementDimension/cbc:Measure (Valor de la carga en TM)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y existe tipo de carga, y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información del valor de la carga en TM.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor de la carga en TM cumple con el formato establecido.
   - **Listados:** -
## 124. Carga útil en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "TNE"
- **Tag:** @unitCode
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el atributo existe, el valor del atributo es diferente 'TNE'
   - **Mensaje:** El dato ingresado como unidad de medida de la carga  del vehiculo no corresponde al valor esperado.
   - **Listados:** -
## 125. Carga efectiva en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..14
- **Formato / Valor:** "02"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:MeasurementDimension/cbc:AttributeID (Tipo de carga: Carga Efectiva)

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, es diferente de '01' y '02'
   - **Mensaje:** El dato ingresado como tipo de carga util es incorrecto.
   - **Listados:** -
## 125. Carga efectiva en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:MeasurementDimension/cbc:Measure (Valor de la carga en TM)

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y existe tipo de carga, y no existe el tag
   - **Mensaje:** El XML no contiene el tag o no existe información del valor de la carga en TM.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor de la carga en TM cumple con el formato establecido.
   - **Listados:** -
## 125. Carga efectiva en toneladas métricas del vehículo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "TNE"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:MeasurementDimension/cbc:Measure@unitCode
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el atributo existe, el valor del atributo es diferente 'TNE'
   - **Mensaje:** El dato ingresado como unidad de medida de la carga  del vehiculo no corresponde al valor esperado.
   - **Listados:** -
## 126. Valor referencial por tonelada métrica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an5
- **Formato / Valor:** Importes del Anexo II del D.S. 010-2006-MTC
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:TransportEquipment/cac:Delivery/cac:DeliveryTerms/cbc:Amount

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 126. Valor referencial por tonelada métrica

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## 127. Valor preliminar referencial por carga útil nominal (Tratándose de más de 1 vehículo)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cbc:DeclaredForCarriageValueAmount

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Tipo de operación' es igual a '1004' y el tag existe, el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor referencial de carga util nominal no cumple con el formato establecido.
   - **Listados:** -
## 127. Valor preliminar referencial por carga útil nominal (Tratándose de más de 1 vehículo)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El atributo @currencyID del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto de la detracción debe ser PEN
   - **Listados:** -
## 128. Indicador de aplicación de factor de retorno al vacío

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** Boolean
- **Formato / Valor:** "true"/"false"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Delivery/cac:Shipment/cac:Consignment/cac:TransportHandlingUnit/cac:TransportEquipment/cbc:ReturnabilityIndicator

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional  - exportación de servicios de hospedaje. Registro Información adicional  - exportación de servicios de hospedaje

- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4009'
   - **Mensaje:** El XML no contiene el tag de numero de documentos del huesped.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4008'
   - **Mensaje:** El XML no contiene el tag de tipo de documentos del huesped.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4000'
   - **Mensaje:** El XML no contiene el tag de codigo de pais de emision del documento de identidad
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4007'
   - **Mensaje:** El XML no contiene el tag de apellidos y nombres del huesped.
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4001'
   - **Mensaje:** El XML no contiene el tag de codigo del pais de residencia.
   - **Listados:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 129<br>130<br>131<br>132<br>133. Número de documento del sujeto no domiciliado<br>Código de tipo de documento de identidad del sujeto no domiciliado<br>Código país de emisión del pasaporte<br>Apellidos y Nombres o denominación o razón social del sujeto no domiciliado<br>Código del país de residencia del sujeto no domiciliado

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..20<br>an1<br>an2<br>an..200<br>an2
- **Formato / Valor:** (Catálogo N.° 06)<br>(Catálogo N.° 04)<br>(Catálogo N.° 04)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de documento del sujeto no domiciliado)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código de tipo de documento de identidad del sujeto no domiciliado)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código país de emisión del pasaporte)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Apellidos y Nombres o denominación o razón social del sujeto no domiciliado)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código del país de residencia del sujeto no domiciliado)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Código del concepto' es igual a '4001', '4000', '4007', '4008' o '4009', no existe el tag o es vacio.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4008' y el valor del tag es distinto al catálogo 06
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(006)

3. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4000' y el valor del tag es distinto al catálogo 04
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(004)

4. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4001' y el valor del tag es distinto al catálogo 04
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(004)

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4007' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

6. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4009' y el valor del tag es diferente a alfanumérico de 3 hasta 20 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4002'
   - **Mensaje:** El XML no contiene el tag de fecha de ingreso del pais.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4003'
   - **Mensaje:** El XML no contiene el tag de fecha de ingreso al establecimiento.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4004'
   - **Mensaje:** El XML no contiene el tag de fecha de salida del establecimiento.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con valor '4006'
   - **Mensaje:** El XML no contiene el tag de fecha de consumo.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 134<br>135<br>136<br>137. Fecha de Ingreso al país<br>Fecha de Ingreso al Establecimiento<br>Fecha de salida del Establecimiento<br>Fecha de consumo

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate (Fecha)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4002', no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -

2. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4003', no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -

3. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4004', no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -

4. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4006', no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4004' (fecha de salida del establecimiento) y el valor del tag es menor que el valor consignado en el tag con 'Código del concepto' '4003' (fecha de ingreso al establecimiento)
   - **Mensaje:** La fecha de ingreso al establecimiento es mayor a la fecha de salida al establecimiento.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0202 Exportación de servicios – prestación de servicios de hospedaje No Dom', y no existe el tag con código '4005'
   - **Mensaje:** El XML no contiene el tag de numero de dias de permanencia.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..4
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:DurationMeasure (Número de días de permanencia)

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' '4005' y no existe el tag.
   - **Mensaje:** El XML no contiene tag de la cantidad del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El formato del tag es diferente de numérico de hasta 4 dígitos
   - **Mensaje:** El dato ingresado como cantidad del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 138. Número de días de permanencia

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "DAY"
- **Tag:** @unitCode

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor es diferente de 'DAY'
   - **Mensaje:** El dato ingresado como unidad de medida de los dias de permanencia no corresponde al valor esperado.
   - **Listados:** -
## Información adicional  - beneficio de hospedaje - paquete turístico. Registro Información adicional  - beneficio de hospedaje - paquete turístico

- **Tipo y longitud:** -
- **Formato / Valor:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0205 Exportación de servicios  - Servicios que conformen un Paquete Turístico', y no existe el tag con código '4000'
   - **Mensaje:** El XML no contiene el tag de codigo de pais de emision del documento de identidad
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0205 Exportación de servicios  - Servicios que conformen un Paquete Turístico', y no existe el tag con código '4007'
   - **Mensaje:** El XML no contiene el tag de apellidos y nombres del huesped.
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0205 Exportación de servicios  - Servicios que conformen un Paquete Turístico', y no existe el tag con código '4008'
   - **Mensaje:** El XML no contiene el tag de tipo de documentos del huesped.
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0205 Exportación de servicios  - Servicios que conformen un Paquete Turístico', y no existe el tag con código '4009'
   - **Mensaje:** El XML no contiene el tag de numero de documentos del huesped.
   - **Listados:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 139<br>140<br>141<br>142. Apellidos y Nombres o denominación o razón social del huesped<br>Número de documento del huesped<br>Código de tipo de documento de identidad del huesped<br>Código país de emisión del pasaporte

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200<br>an..20<br>an1<br>an2
- **Formato / Valor:** (Catálogo N.° 06)<br>(Catálogo N.° 04)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Apellidos y Nombres o denominación o razón social del huesped)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de documento del huesped)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código de tipo de documento de identidad del huesped)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código país de emisión del pasaporte)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' '4000', '4007', '4008' o '4009'  y no existe el tag o es vacio.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4008' y el valor del tag es distinto al catálogo 06
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(006)

3. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4000' y el valor del tag es distinto al catálogo 04
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(004)

4. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4007' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4009' y el valor del tag es diferente a alfanumérico de 3 hasta 20 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional - ventas al sector público. Registro Información adicional - ventas al sector público
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe un 'Código del concepto' con valor '5001' o '5002' o '5003' y no existe el tag con código '5000'
   - **Mensaje:** El XML no contiene el tag de Proveedores Estado: Número de Expediente
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe un 'Código del concepto' con valor '5000' o '5002' o '5003', y no existe el tag con código '5001'
   - **Mensaje:** El XML no contiene el tag de Proveedores Estado: Código de Unidad Ejecutora
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe un 'Código del concepto' con valor '5000' o '5001' o '5003', y no existe el tag con código '5002'
   - **Mensaje:** El XML no contiene el tag de Proveedores Estado: N° de Proceso de Selección
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe un 'Código del concepto' con valor '5000' o '5001' o '5002', y no existe el tag con código '5003'
   - **Mensaje:** El XML no contiene el tag de Proveedores Estado: N° de Contrato
   - **Listados:** -
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 143<br>144<br>145<br>146. Número de expediente<br>Código de unidad ejecutora<br>Número de contrato<br>Número de proceso de selección

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n..20<br>n..10<br>an..30<br>an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de expediente)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Código de unidad ejecutora)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de contrato)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de proceso de selección)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual '5000', '5001', '5002' o '5003' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '5000' y el valor del tag es diferente a alfanumérico de 1 hasta 20 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '5001' y el valor del tag es diferente a alfanumérico de 1 hasta 10 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '5002' y el valor del tag es diferente a alfanumérico de 1 hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '5003' y el valor del tag es diferente a alfanumérico de 1 hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional - migración de documentos autorizados - Carta Porte Aéreo. Registro Información adicional - migración de documentos autorizados - Carta Porte Aéreo
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0301 - Carta de porte aéreo (emitidas en el ámbito nacional)', no existe el tag con código '4030'
   - **Mensaje:** El XML no contiene el tag de Carta Porte Aéreo:  Lugar de origen - Código de ubigeo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0301 - Carta de porte aéreo (emitidas en el ámbito nacional)', no existe el tag con código '4031'
   - **Mensaje:** El XML no contiene el tag de Carta Porte Aéreo:  Lugar de origen - Dirección detallada
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0301 - Carta de porte aéreo (emitidas en el ámbito nacional)', no existe el tag con código '4032'
   - **Mensaje:** El XML no contiene el tag de Carta Porte Aéreo:  Lugar de destino - Código de ubigeo
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0301 - Carta de porte aéreo (emitidas en el ámbito nacional)', no existe el tag con código '4033'
   - **Mensaje:** El XML no contiene el tag de Carta Porte Aéreo:  Lugar de destino - Dirección detallada
   - **Listados:** -
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 147<br>148. Lugar de origen<br>Lugar de destino

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6<br>an..200<br>an6<br>an..200
- **Formato / Valor:** (Catálogo N.° 13)<br>(Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Lugar de origen - Código de Ubigeo)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Lugar de origen - Dirección detallada)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Lugar de destino - Código de Ubigeo)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Lugar de destino - Dirección detallada)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual '4030','4031', '4032' o '4033' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4030' y el valor del tag es distinto al catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)

3. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4032' y el valor del tag es distinto al catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)

4. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4031' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4033' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional - migración de documentos autorizados - BVME para transporte ferroviario de pasajeros. Registro Información adicional - migración de documentos autorizados - BVME para transporte ferroviario de pasajeros
## 149. Número de RUC del agente de ventas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an11
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:AgentParty/cac:PartyIdentification/cbc:ID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Agente de Viajes: Numero de Ruc
   - **Listados:** -
## 150. Tipo de documento del agente de ventas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:AgentParty/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el numero de RUC del agente de ventas, y no existe el atributo
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Agente de Viajes: Tipo de documento
   - **Listados:** Catálogo<br>(006)

2. **ERROR**
   - **Validación:** Si existe el numero de RUC del agente de ventas, y existe el tag, el valor es diferente a '6'
   - **Mensaje:** El dato ingresado como Agente de Viajes-Tipo de documento no corresponde al valor esperado.
   - **Listados:** -
## 150. Tipo de documento del agente de ventas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Documento de Identidad"
- **Tag:** @schemeName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Documento de Identidad'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 150. Tipo de documento del agente de ventas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 150. Tipo de documento del agente de ventas

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"
- **Tag:** @schemeURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4040'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Pasajero - Apellidos y Nombres
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4041'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Pasajero - Tipo de documento de identidad
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4049'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Pasajero - Número de documento de identidad
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4042'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Ciudad o lugar de origen - Código de ubigeo
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4043'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Ciudad o lugar de origen - Dirección detallada
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4044'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Ciudad o lugar de destino - Código de ubigeo
   - **Listados:** -

7. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4045'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Ciudad o lugar de destino - Dirección detallada
   - **Listados:** -

8. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4046'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte:Número de asiento
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 151<br>152<br>153<br>154<br>155. Pasajero - apellidos y nombres<br>Pasajero - tipo y número de documento de identidad<br>Servicio de transporte: Ciudad o lugar de origen<br>Servicio de transporte: Ciudad o lugar de destino<br>Servicio de transporte: Número de asiento

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200<br>an..20<br>an1<br>an6<br>an..200<br>an6<br>an..200<br>an..100
- **Formato / Valor:** (Catálogo N.° 06)<br>(Catálogo N.° 13)<br>(Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Pasajero - Apellidos y nombres)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Pasajero - número de documento de identidad)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Pasajero - tipo de documento de identidad)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Servicio de transporte: Ciudad o lugar de origen - Código de ubigeo)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Servicio de transporte: Ciudad o lugar de origen - Dirección detallada)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Servicio de transporte: Ciudad o lugar de destino - Código de ubigeo)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Servicio de transporte: Ciudad o lugar de destino - Dirección detallada)<br>/Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Servicio de transporte:Número de asiento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual '4040', '4041', '4042', '4043', '4044', '4045' o '4046' o '4049' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es igual a '4040' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4041' y el valor del tag es distinto al catálogo 06
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

4. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4042' y el valor del tag es distinto al catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

5. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4043' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

6. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4044' y el valor del tag es distinto al catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

7. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4045' y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

8. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4046' y el valor del tag es diferente a alfanumérico de 1 hasta 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -

9. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '4049' y el valor del tag es diferente a alfanumérico de 1 hasta 20 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4048''
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Fecha programada de inicio de viaje
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 156. Servicio de transporte: Fecha programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4048' y no existe el tag.
   - **Mensaje:** El XML no contiene tag de la fecha del concepto por linea.
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag con código igual a '4047'
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Hora programada de inicio de viaje
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 157. Servicio de transporte: Hora programada de inicio de viaje

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** hh:mm:ss
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartTime
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '4047' y no existe el tag.
   - **Mensaje:** El XML no contiene tag de la Hora del concepto por linea.
   - **Listados:** -
## 158. Servicio de transporte: Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 59)
- **Tag:** /Invoice/cac:PaymentMeans/cbc:PaymentMeansCode
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio transporte: Forma de Pago
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe el tag, el valor del tag es diferente al listado.
   - **Mensaje:** El dato ingreso como Forma de Pago o Medio de Pago no corresponde al valor esperado (catalogo nro 59)
   - **Listados:** Catálogo<br>(059)
## 158. Servicio de transporte: Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Medio de pago"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Medio de pago'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 158. Servicio de transporte: Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 158. Servicio de transporte: Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 159. Servicio de transporte: Número de autorización de la transacción y el sistema de tarjeta de crédito y/o débito

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:PaymentMeans/cbc:PaymentID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '0302 - BVME para transporte ferroviario de pasajeros', no existe el tag
   - **Mensaje:** El XML no contiene el tag de BVME transporte ferroviario: Servicio de transporte: Número de autorización de la transacción
   - **Listados:** -
## Información adicional a nivel de ítem. Información adicional a nivel de ítem

- **Nivel:** Información adicional a nivel de ítem
- **Condición informática:** Información adicional a nivel de ítem
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** n10
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Partida Arancelaria)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 160. Partida Arancelaria<br>Declaración Aduanera de Mercancías (DAM)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..20
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (DAM)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si 'Código del concepto' es '7021', el formato del Tag UBL es diferente a:<br>- [0-9]{4}-[0-9]{2}-[0-9]{3}-[0-9]{6}
   - **Mensaje:** El valor ingresado como numero de DAM no cumple con el estandar
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)
- **Uso del campo:** 1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Catálogo<br>(055)
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..8
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Numero de placa)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..10
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Categoria)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Marca)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Modelo)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Color)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Motor)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Combustible)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..10
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Form. Rodante)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an17
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (VIN)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Serie/Chasis)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** n4
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Año de Fabricacion)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** n4
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Año Modelo)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Version)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ejes)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..4
- **Formato / Valor:** n4
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Asientos)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..4
- **Formato / Valor:** n4
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Pasajeros)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ruedas)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Carroceria)
- **Uso del campo:** 0..1

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..10
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Potencia)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Cilindros)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Cilindrada)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Peso Bruto)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Peso Neto)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Carga Util)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Longitud)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Altura)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 161. Datos de vehículos

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..6
- **Formato / Valor:** n(2,3)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Ancho)
- **Uso del campo:** 0..1

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Información adicional a nivel de ítem - comprobante emitido por empresas del sistema financiero y cooperativas de ahorro y crédito no autorizadas a captar recursos del público. Registro Información adicional a nivel de ítem - comprobante emitido por empresas del sistema financiero y cooperativas de ahorro y crédito no autorizadas a captar recursos del público
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901', y no existe el tag con código '7001'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Tipo de préstamo
   - **Listados:** Catálogo<br>(055)

2. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002), y no existe el tag con código '7003'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Partida Registral
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901', y no existe el tag con código '7004'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Número de contrato
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901', y no existe el tag con código '7005'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Fecha de otorgamiento del crédito
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002), no existe el tag con código '7006'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Dirección del predio - Código de ubigeo
   - **Listados:** -

6. **ERROR**
   - **Validación:** Si 'Código de producto SUNAT' de la linea es '84121901' y el  indicador de primera vivienda = 3 (código concepto 7002),  no existe el tag con código '7007'
   - **Mensaje:** El XML no contiene el tag de Créditos Hipotecarios: Dirección del predio - Dirección completa
   - **Listados:** -

7. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2100' o '2101' o '2102' y no existe al menos una línea que contenga simultáneamente los códigos '7004', '7005' y '7012'
   - **Mensaje:** Para el tipo de operación 2100, 2101 y 2102 (Creditos) debe consignar Numero de contrato, Fecha de otorgamiento y Monto del crédito otorgado (capital)
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Número de contrato)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '7001', '7002', '7003', '7004', '7005', '7006', '7007', '7008', '7009', '7010', '7011' o '7012' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Fecha del otorgamiento del crédito)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7001 y el valor del tag es distinto al catálogo 26
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(026)
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** (Catálogo N.° 26)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Tipo de préstamo)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7002 y el valor del tag es distinto al catálogo 27
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(027)
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Partida Registral)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7003 y el valor del tag es diferente a alfanumérico de 3 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an1
- **Formato / Valor:** (Catálogo N.° 27)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Indicador de primera vivienda)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7004 y el valor del tag es diferente a alfanumérico de 3 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Código de ubigeo)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7005 y el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..200
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Dirección completa y detallada)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7006 y el valor del tag es distinto al catálogo 13
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** Catálogo<br>(013)
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Urbanización)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..25
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Provincia)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Departamento)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Dirección - Distrito)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es 7007 y el valor del tag es diferente a alfanumérico de 3 hasta 200 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 162<br>163<br>164<br>165<br>166<br>167<br>168. Número de contrato<br>Fecha del otorgamiento del crédito<br>Tipo de préstamo<br>Partida Registral<br>Indicador de primera vivienda<br>Dirección completa del predio<br>Monto del crédito otorgado (capital)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..18
- **Formato / Valor:** n(15,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Monto del crédito otorgado)
- **Uso del campo:** 1

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7012' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 15 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional  a nivel de ítem - comprobante emitido por empresas de seguros. Registro Información adicional  a nivel de ítem - comprobante emitido por empresas de seguros
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2104', y no existe al menos una línea que contenga el código '7015'
   - **Mensaje:** Para el tipo de operación 2104 - Empresas del sistema de seguros, debe consignar Información adicional  a nivel de ítem
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..50
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Numero de póliza)

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '7013', '7015' o '7016' y no existe el tag o es vacío.
   - **Mensaje:** El XML no contiene tag o no existe información del valor del concepto por linea.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7013' y el valor del tag es diferente a alfanumérico de 1 hasta 50 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** n1
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Tipo de seguro)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7015' y el valor del tag es diferente de '1', '2' o '3'
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2104' y el 'Código del concepto' es '7015' y el valor del tag es igual a '1' o '2', no existe en la misma línea los 'Código del concepto' con valor '7013', '7014' y '7016'
   - **Mensaje:** Para los tipos de seguro 1 y 2, debe consignar el numero de poliza, la fecha de cobertura y el monto asegurado

2. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2104' y el 'Código del concepto' es '7015' y el valor del tag es igual a '3' y no existe en la misma línea un 'Código del concepto' con valor '7013' (Número de póliza)
   - **Mensaje:** Para el tipo de seguro 3 - Otros debe consignar el numero de poliza
## 169<br>170<br>171. Número de póliza<br>Tipo de seguro<br>Suma asegurada / alcance de cobertura o monto

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..18
- **Formato / Valor:** n(15,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Value (Suma asegurada / alcance de cobertura o monto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7016' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 15 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:Name (Nombre del concepto)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag y es vacío
   - **Mensaje:** No existe información en el nombre del concepto.
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 55)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cbc:NameCode (Código del concepto)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2104', y no existe al menos una línea que contenga el código '7015'
   - **Mensaje:** Para el tipo de operación 2104 - Empresas del sistema de seguros, debe consignar Información adicional  a nivel de ítem
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Propiedad del item"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Propiedad del item'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:StartDate (Fecha de inicio de vigencia)

### Validaciones

1. **ERROR**
   - **Validación:** De existir 'Código del concepto' igual a '7014' y no existe el tag.
   - **Mensaje:** El XML no contiene tag o no existe información de la fecha del concepto por linea
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7014' y el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
## 172<br>173. Fecha de inicio de vigencia de cobertura<br>Fecha de término de vigencia de cobertura

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cac:AdditionalItemProperty/cac:UsabilityPeriod/cbc:EndDate (Fecha de término de vigencia)

### Validaciones

1. **OBSERV**
   - **Validación:** De existir 'Código del concepto' igual a '7014' y no existe el tag.
   - **Mensaje:** El XML no contiene tag o no existe información de la fecha del concepto por linea

2. **OBSERV**
   - **Validación:** Si el 'Código del concepto' es '7014' y el tag existe, el formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** El dato ingresado como valor del concepto de la linea no cumple con el formato establecido.
   - **Listados:** -
## Información adicional - Forma de pago al contado. Registro Información adicional - Forma de pago al contado
## 174. Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a9
- **Formato / Valor:** "FormaPago"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR**
   - **Validación:** No existe al menos un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe consignar la informacion del tipo de transaccion del comprobante
   - **Listados:** -
## 174. Forma de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a7
- **Formato / Valor:** "Contado"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentMeansID (Forma de pago)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago' y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe informar si el tipo de transaccion es al Contado o al Credito
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es diferente de:<br>- Contado<br>- Credito<br>- Cuota[0-9]{3}<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no cumple con el formato esperado
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag 'Contado' y también existe tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag 'Credito'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion no puede ser a la vez al Contado y al Credito
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con el mismo valor del tag cbc:PaymentMeansID (se repite)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no debe repetirse en el comprobante
   - **Listados:** -
## Información adicional - Forma de pago al crédito. Registro Información adicional - Forma de pago al crédito
## 175. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an9
- **Formato / Valor:** "FormaPago"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR**
   - **Validación:** No existe al menos un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe consignar la informacion del tipo de transaccion del comprobante
   - **Listados:** -
## 175. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an7
- **Formato / Valor:** "Credito"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentMeansID (Forma de pago)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago' y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe informar si el tipo de transaccion es al Contado o al Credito
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe más de un tag cbc:PaymentMeansID dentro del mismo cac:PaymentTerms
   - **Mensaje:** La forma de pago y/o número de cuota no pueden estar contenidos en el mismo cac:PaymentTerms
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es diferente de:<br>- Contado<br>- Credito<br>- Cuota[0-9]{3}<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no cumple con el formato esperado
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con el mismo valor del tag cbc:PaymentMeansID (se repite)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no debe repetirse en el comprobante
   - **Listados:** -

5. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es 'Credito', el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe al menos una cuota (no existe al menos un tag cac:PaymentTerms con cbc:ID con valor 'FormaPago' y cbc:PaymentMeansID con formato Cuota[0-9]{3}<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si el tipo de transaccion es al Credito debe existir al menos información de una cuota de pago
   - **Listados:** -
## 175. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:Amount (Monto neto pendiente de pago)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El Monto neto pendiente de pago no cumple el formato definido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con 'Forma de pago' igual a 'Credito', el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si el tipo de transaccion es al Credito debe consignarse el Monto neto pendiente de pago
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con 'Forma de pago' igual a 'Credito' y el valor del tag UBL es mayor al 'Importe total de la venta, cesión en uso o del servicio prestado' (/Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El Monto neto pendiente de pago debe ser menor o igual al Importe total del comprobante
   - **Listados:** -
## 175. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con 'Forma de pago' igual a 'Credito' el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y el valor del tag UBL es diferente de la sumatoria del 'Monto de pago unico o de las cuotas'.<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** La suma de las cuotas debe ser igual al Monto neto pendiente de pago.
   - **Listados:** -
## 175. Forma de pago<br>Monto neto pendiente de pago

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'<br>* Validación a partir del 01/09/2021 es ERROR
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 176<br>177. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an9
- **Formato / Valor:** "FormaPago"
- **Tag:** /Invoice/cac:PaymentTerms/cbc:ID (Indicador)

### Validaciones

1. **ERROR**
   - **Validación:** No existe al menos un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe consignar la informacion del tipo de transaccion del comprobante
   - **Listados:** -
## 176<br>177. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an8
- **Formato / Valor:** Cuota<NNN><br>Ejemplo:<br>Cuota001<br>Cuota010
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentMeansID (Identificador de la cuota)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago' y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Debe informar si el tipo de transaccion es al Contado o al Credito
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', el valor del tag es diferente de:<br>- Contado<br>- Credito<br>- Cuota[0-9]{3}<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no cumple con el formato esperado
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe más de un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con el mismo valor del tag cbc:PaymentMeansID (se repite)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El tipo de transaccion o el identificador de la cuota no debe repetirse en el comprobante
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y con valor del tag con formato: Cuota[0-9]{3} y no existe un tag cac:PaymentTerms con cbc:ID igual a 'FormaPago' y con valor del tag igual a 'Credito'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si existe información de cuota de pago, el tipo de transaccion debe ser al credito
   - **Listados:** -
## 176<br>177. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:PaymentTerms/cbc:Amount (Monto del pago único o de las cuotas)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', y el formato del Identificador de la cuota es: Cuota[0-9]{3} y si existe el tag, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El Monto del pago único o de las cuotas no cumple el formato definido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3}, el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si se consigna información de la cuota de pago, debe indicarse el monto de la cuota
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3} y el valor del tag UBL es mayor al 'Importe total de la venta, cesión en uso o del servicio prestado' (/Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El Monto del pago único o de las cuotas debe ser menor o igual al Importe total del comprobante
   - **Listados:** -
## 176<br>177. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'<br>* Validación a partir del 01/09/2021 es ERROR
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 176<br>177. Monto del pago único o de las cuotas<br>Fecha del pago único o de las cuotas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cac:PaymentTerms/cbc:PaymentDueDate (Fecha del pago único o de las cuotas)

### Validaciones

1. **ERROR**
   - **Validación:** Si el 'Indicador' es 'FormaPago', y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3} y si existe el tag, el formato es diferente de YYYY-MM-DD<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Fecha del pago único o de las cuotas no cumple el formato definido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3}, el 'Tipo de Documento del adquiriente o usuario' es igual a RUC (6) y no existe el tag UBL<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si se consigna información de la cuota de pago, debe indicarse la fecha del pago único o de las cuotas
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si existe un tag cac:PaymentTerms con cbc:ID<br>igual a 'FormaPago' y el formato del 'Identificador de la cuota' es: Cuota[0-9]{3} y el valor del tag UBL es menor o igual a la 'Fecha de emisión' (/Invoice/cbc:IssueDate)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Fecha del pago único o de las cuotas no puede ser anterior o igual a la fecha de emisión del comprobante
   - **Listados:** -
## Información adicional - Retenciones de IGV. Registro Información adicional - Retenciones de IGV
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "false"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de cargo/descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para 'Código de motivo de cargo/descuento' igual a '62'<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "62"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de cargo/descuento: Retención del IGV)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto a los valores del catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)

3. **ERROR**
   - **Validación:** Si el valor del tag es '62' y el Tipo de documento de identidad del receptor del comprobante (/Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID) es diferente de 6-RUC<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si existe retencion de IGV en el comprobante, el receptor debe ser un Agente de Retencion
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si el valor del tag es '62' y el receptor del comprobante (/Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification/cbc:ID) no es un agente de retención (no existe ind_padrón igual a "03" en el listado)<br>* Validación a partir del 01/09/2021 es ERROR
   - **Mensaje:** Si existe retencion de IGV en el comprobante, el receptor debe ser un Agente de Retencion
   - **Listados:** Padrones<br>Contribuyentes
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C

### Validaciones

1. **ERROR**
   - **Validación:** Si el valor del tag es '62' y el emisor del comprobante (/Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID) es un agente de retención<br>(existe ind_padrón igual a "03" en el listado)<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** Si existe retencion de IGV en el comprobante, el emisor no debe ser un Agente de Retencion
   - **Listados:** Padrones<br>Contribuyentes
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:MultiplierFactorNumeric (Porcentaje de la retención expresado como factor)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales
   - **Mensaje:** El dato ingresado en factor de cargo o descuento global no cumple con el formato establecido.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:Amount (Importe de la retención)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de motivo de cargo/descuento' es '62', el valor del Tag UBL es diferente a  Importe de la operación' por 'Porcentaje de la retención expresado como factor', con una tolerancia + -1<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El Importe de la retencion no tiene el valor correcto
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:BaseAmount (Importe de la operación)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en base monto por cargo/descuento globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si "Código de motivo de cargo/descuento" es '62' , el valor del Tag UBL es mayor a "Importe total"<br>* Validación a partir del 01/01/2022 es ERROR
   - **Mensaje:** El importe total de la operación (base imponible de retencion) no puede ser mayor al importe total del comprobante.
   - **Listados:** -
## 178<br>179<br>180. Importe de la operación<br>Porcentaje de la retención<br>Importe de la retención

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor del atributo es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
## Información adicional - Retenciones de Renta de segunda categoría. Registro Información adicional - Retenciones de Renta de segunda categoría
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "false"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:ChargeIndicator (Indicador de descuento)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para código de descuento igual a '63'
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** "63"
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:AllowanceChargeReasonCode (Código de motivo de cargo/descuento: Retención de segunda categoría)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento global.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del tag es distinto al Catálogo 53
   - **Mensaje:** El dato ingresado como codigo de motivo de cargo/descuento global no es valido (catalogo nro 53)
   - **Listados:** Catálogo<br>(053)

3. **ERROR**
   - **Validación:** Si 'Tipo de operación' es '2002 - Operación sujeta a Retención de segunda' y no existe un cac:AllowanceCharge con cbc:AllowanceChargeReasonCode igual a '63'
   - **Mensaje:** Si el tipo de operación es 2002, debe informar los datos de la retención de segunda categoria
   - **Listados:** Catálogo<br>(053)

4. **ERROR**
   - **Validación:** Si el valor del tag es igual a '63' y el 'Tipo de operación' es diferente de '2002 - Operación sujeta a Retención de segunda'
   - **Mensaje:** Si consigna infomacion de la retencion de segunda categoria, el tipo de operacion debe ser 2002
   - **Listados:** Catálogo<br>(053)
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI
- **Uso del campo:** 0..1

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:Amount (Monto de la retención)
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de motivo de cargo/descuento' es igual a '63' y el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en cac:AllowanceCharge/cbc:Amount no cumple con el formato establecido.
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:AllowanceCharge/cbc:BaseAmount (Monto base)
- **Uso del campo:** 0..1

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal (positivo mayor a cero) de 12 enteros y hasta 2 decimales
   - **Mensaje:** El dato ingresado en base monto por cargo/descuento globales no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de motivo de cargo/descuento' es igual a '63' y no existe el tag UBL
   - **Mensaje:** Debe consignar la base de la retencion de segunda categoria
   - **Listados:** -
## 181. Retenciones de Renta de segunda categoría

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** @currencyID
- **Uso del campo:** 1

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en 'Tipo de moneda'
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** Catálogo<br>(002)
