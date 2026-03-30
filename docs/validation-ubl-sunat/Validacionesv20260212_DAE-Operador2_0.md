# Validacionesv20260212

## Hoja: DAE-Operador2_0

## Fecha de emisión y Mecanismo de seguridad. Fecha de emisión y Mecanismo de seguridad

- **Nivel:** Fecha de emisión y Mecanismo de seguridad
- **Condición informática:** Fecha de emisión y Mecanismo de seguridad
- **Tipo y longitud:** Fecha de emisión y Mecanismo de seguridad
- **Formato / Valor:** Fecha de emisión y Mecanismo de seguridad
- **Tag:** Fecha de emisión y Mecanismo de seguridad
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

### Validaciones

1. **Validación**
   - **Validación:** <<< REVISAR HOJA FIRMA >>>
   - **Mensaje:** -
## Datos del documento autorizado - Documento del operador. Datos del documento autorizado - Documento del operador

- **Nivel:** Datos del documento autorizado - Documento del operador
- **Condición informática:** Datos del documento autorizado - Documento del operador
- **Tipo y longitud:** Datos del documento autorizado - Documento del operador
- **Formato / Valor:** Datos del documento autorizado - Documento del operador
- **Tag:** Datos del documento autorizado - Documento del operador
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
   - **Validación:** El valor del Tag UBL es diferente de "2.1"
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
   - **Validación:** El valor del Tag UBL es diferente de "2.0"
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
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
## 7. Tipo de documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** "34"
- **Tag:** /Invoice/cbc:InvoiceTypeCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de InvoiceTypeCode
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "34"
   - **Mensaje:** InvoiceTypeCode - El valor del tipo de documento es invalido o no coincide con el nombre del archivo
   - **Listados:** Catálogo<br>(001)
## 8. Fecha de vencimiento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Invoice/cbc:DueDate

### Validaciones

1. **Validación**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
## Datos del Operador. Datos del Operador

- **Nivel:** Datos del Operador
- **Condición informática:** Datos del Operador
- **Tipo y longitud:** Datos del Operador
- **Formato / Valor:** Datos del Operador
- **Tag:** Datos del Operador
## 9. Número de RUC

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
## 9. Tipo de documento de identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** a1
- **Formato / Valor:** "6"
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion en tipo de documento del emisor.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "6"
   - **Mensaje:** El dato ingresado no cumple con el estandar
   - **Listados:** -
## 10. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si existe el tag, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## Datos del adquirente o usuario (receptor). Datos del adquirente o usuario (receptor)

- **Nivel:** Datos del adquirente o usuario (receptor)
- **Condición informática:** Datos del adquirente o usuario (receptor)
- **Tipo y longitud:** Datos del adquirente o usuario (receptor)
- **Formato / Valor:** Datos del adquirente o usuario (receptor)
- **Tag:** Datos del adquirente o usuario (receptor)
## 11. Numero de documento de identidad

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
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del número de documento de identidad del receptor del documento
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es 6, el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El numero de documento de identidad del receptor debe ser  RUC
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es '6', el valor del Tag UBL no está en el listado
   - **Mensaje:** El numero de RUC del receptor no existe.
   - **Listados:** Contribuyentes

5. **OBSERV**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es '6', el valor del Tag UBL tiene un ind_estado diferente a '00' en el listado
   - **Mensaje:** El RUC  del receptor no esta activo
   - **Listados:** Contribuyentes

6. **OBSERV**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es '6', el valor del Tag UBL tiene un ind_condicion igual a '12' en el listado
   - **Mensaje:** El RUC del receptor no esta habido
   - **Listados:** Contribuyentes

7. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del adquiriente" es "4" o "7" o "0" o "A" o "B" o "C" o "D" o "E", el formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado como numero de documento de identidad del receptor no cumple con el formato establecido
   - **Listados:** -

8. **ERROR**
   - **Validación:** Si el "Tipo de documento de identidad del adquiriente o usuario" es 1, el formato del Tag UBL es diferente de numérico de 8 dígitos
   - **Mensaje:** El DNI ingresado no cumple con el estandar.
   - **Listados:** -
## 11. Tipo de documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** a1
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
## 12. Apellidos y nombres, denominación o razón social del adquirente o usuario

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:AccountingCustomerParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## Documentos de referencia. Documentos de referencia

- **Nivel:** Documentos de referencia
- **Condición informática:** Documentos de referencia
- **Tipo y longitud:** Documentos de referencia
- **Formato / Valor:** Documentos de referencia
- **Tag:** Documentos de referencia
## 13. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:DespatchDocumentReference/cbc:ID (Número de documento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a:<br>- [T][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{4}-[0-9]{1,8}<br>- [EG0][1-4]{1}-[0-9]{1,8}<br>- [EG07] {4}-[0-9]{1,8}<br>- [G][0-9]{3}-[0-9]{1,8}<br>- [V][A-Z0-9]{3}-[0-9]{1,8}
   - **Mensaje:** El ID de las guias debe tener informacion de la SERIE-NUMERO de guia.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de la guía de remisión relacionada" concatenado con el valor del Tag UBL se repite en el /Invoice
   - **Mensaje:** El comprobante contiene un tipo y número de Guía de Remisión repetido
   - **Listados:** -
## 13. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 01)
- **Tag:** /Invoice/cac:DespatchDocumentReference/cbc:DocumentTypeCode (Tipo de guía relacionado)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el "Número de la guía de remisión relacionada", el formato del Tag UBL es diferente de "09" o "31"
   - **Mensaje:** El DocumentTypeCode de las guias debe ser 09 o 31
   - **Listados:** Catálogo<br>(001)
## 13. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 13. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Documento "
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 13. Tipo y número de la guía de remisión relacionada

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 14. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:ID (Número de documento)

### Validaciones

1. **OBSERV**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El ID de los documentos relacionados no cumplen con el estandar.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El "Tipo de otro documento relacionado" concatenado con el valor del Tag UBL se repite en el /Invoice
   - **Mensaje:** El comprobante contiene un tipo y número de Documento Relacionado repetido
   - **Listados:** -
## 14. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 12)
- **Tag:** /Invoice/cac:AdditionalDocumentReference/cbc:DocumentTypeCode (Tipo de documento relacionado)

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el "Número de otro documento relacionado", el formato del Tag UBL es diferente de "05" y "99"
   - **Mensaje:** El DocumentTypeCode de Otros documentos relacionados tiene valores incorrectos.
   - **Listados:** Catálogo<br>(012)
## 14. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 14. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Tipo de Documento "
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Tipo de Documento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 14. Tipo y número de otro documento relacionado

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 15. Leyendas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 52)
- **Tag:** /Invoice/cbc:Note@languageLocaleID (Código de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo existe, el valor del atributo no existe en el listado
   - **Mensaje:** El valor del atributo no se encuentra en el catálogo
   - **Listados:** Catálogo<br>(052)

2. **OBSERV**
   - **Validación:** El valor del Tag UBL no debe repetirse en el comprobante
   - **Mensaje:** El codigo de leyenda no debe repetirse en el comprobante
   - **Listados:** -
## 15. Leyendas

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..500
- **Tag:** /Invoice/cbc:Note  (Descripción de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en descripcion de leyenda no cumple con el formato establecido.
   - **Listados:** -
## Datos de cada partícipe. Datos de cada partícipe

- **Nivel:** Datos de cada partícipe
- **Condición informática:** Datos de cada partícipe
- **Tipo y longitud:** Datos de cada partícipe
- **Formato / Valor:** Datos de cada partícipe
- **Tag:** Datos de cada partícipe
## 16. Número de orden

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos; o, es igual cero.
   - **Mensaje:** El Numero de orden del item no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro ítem (cac:InvoiceLine) con el mismo valor del Tag UBL
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 17. Número de documento de identidad

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an11
- **Formato / Valor:** n11
- **Tag:** /Invoice/cac:InvoiceLine/cac:OriginatorParty/cac:PartyIdentification/cbc:ID (Número de RUC)

### Validaciones

1. **ERROR**
   - **Validación:** Existe en el ítem (línea) más de un Tag UBL cac:OriginatorParty/cac:PartyIdentification
   - **Mensaje:** Existe más de un Tag UBL cac:OriginatorParty/cac:PartyIdentification
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** Debe consignar el Tag UBL cac:OriginatorParty/cac:PartyIdentification/cbc:ID
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del partícipe" es 6, el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El dato ingresado en el 'Tipo de documento de identidad' no cumple el formato establecido
   - **Listados:** -

4. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del partícipe" es '6', el valor del Tag UBL no está en el listado
   - **Mensaje:** Número de RUC no existe.
   - **Listados:** Contribuyentes

5. **OBSERV**
   - **Validación:** Si "Tipo de documento de identidad del partícipe" es '6', el valor del Tag UBL tiene un ind_estado diferente a 00 en el listado
   - **Mensaje:** El Numero de RUC no esta activo
   - **Listados:** Contribuyentes

6. **OBSERV**
   - **Validación:** Si "Tipo de documento de identidad del partícipe" es '6', el valor del Tag UBL tiene un ind_condicion igual a 12 en el listado
   - **Mensaje:** El Numero de RUC es no habido
   - **Listados:** Contribuyentes

7. **ERROR**
   - **Validación:** Si "Tipo de documento de identidad del partícipe" es "4" o "7" o "0" o "A" o "B" o "C" o "D" o "E", el formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado en el 'Tipo de documento de identidad' no cumple el formato establecido
   - **Listados:** -
## 17. Número de documento de identidad

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** a1
- **Formato / Valor:** (Catálogo No. 06)
- **Tag:** /Invoice/cac:InvoiceLine/cac:OriginatorParty/cac:PartyIdentification/cbc:ID@schemeID (Tipo de documento de identidad)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo del Tag UBL
   - **Mensaje:** Debe indicar tipo de documento.
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del atributo es diferente al listado
   - **Mensaje:** El dato ingresado  en el tipo de documento de identidad del receptor no cumple con el estandar o no esta permitido.
   - **Listados:** Catálogo<br>(006)
## 18. Apellidos y nombres, denominación o razón social del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:InvoiceLine/cac:OriginatorParty/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 18. Apellidos y nombres, denominación o razón social del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Invoice/cac:InvoiceLine/cac:Item/cbc:Description

### Validaciones

1. **OBSERV**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 1500 caracteres (se considera cualquier carácter, no permite "whitespace character": espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El nombre o razon social registrado no cumple con el estandar
   - **Listados:** -
## 19. Total valor de venta del partícipe

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
   - **Validación:** El valor del Tag UBL es diferente a la sumatoria de "Valor de venta  - detalle del partícipe" de las líneas informadas para el partícipe menos los 'Monto de descuento' del partícipe que afectan la base imponible ('Código de motivo de descuento' igual a '02') más 'Montos de cargo' del partícipe que afectan la base imponible ('Código de motivo de cargo' igual a  '49'), con una tolerancia + - 1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cbc:LineExtensionAmount no coincide con el valor calculado
   - **Listados:** -
## 19. Total valor de venta del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 20. Total impuestos del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount (Total impuestos)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag /Invoice/cac:InvoiceLine/cac:TaxTotal
   - **Mensaje:** El xml no contiene el tag de impuesto por linea (TaxtTotal).

2. **ERROR**
   - **Validación:** Existe en el mismo ítem otro tag cac:TaxTotal
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de Item
   - **Listados:** -

3. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en el monto total de impuestos por línea no cumple con el formato establecido
   - **Listados:** -

4. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente a la suma de "Sumatoria de ISC del partícipe" + "Sumatoria de IGV del partícipe, con una tolerancia + - 1
   - **Mensaje:** El importe total de impuestos por línea no coincide con la sumatoria de los impuestos por línea.
   - **Listados:** -
## 20. Total impuestos del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Sumatoria ISC)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '2000', el valor del Tag UBL es diferente a la sumatoria de los 'Monto de ISC - detalle del partícipe' (cac:SubInvoiceLine/cac:TaxTotal/cbc:TaxAmount) de las de las líneas informadas para el partícipe con 'Código de tributo' igual a '2000' (con una tolerancia + - 1)
   - **Mensaje:** El monto de ISC de la línea no coincide con el valor calculado
   - **Listados:** -
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "2000"
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El codigo del tributo es invalido

3. **ERROR**
   - **Validación:** Existe en el ítem otro tag cac:TaxSubtotal con el mismo código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 21. Sumatoria ISC del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Sumatoria IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en TaxAmount de la linea no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '1000', el valor del Tag UBL es diferente a la  siguiente operación:<br>Sumatoria de "Valor de venta  - detalle del partícipe" de las líneas informadas para el partícipe  con 'Código de tributo por ítem' igual a '1000' más la 'Sumatoria ISC del partícipe', menos los 'Monto de descuento' del partícipe que afectan la base imponible ('Código de motivo de descuento' igual a '02') más 'Montos de cargo' del partícipe que afectan la base imponible ('Código de motivo de cargo' igual a  '49')<br>por la tasa vigente al IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El monto de IGV de la línea no coincide con el valor calculado

3. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '9997', el valor del Tag UBL es diferente cero
   - **Mensaje:** El monto de IGV de la línea no coincide con el valor calculado
   - **Listados:** -
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"<br>"9997"
- **Tag:** /Invoice/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe en la línea un tag cac:TaxTotal/cac:TaxSubtotal con cac:TaxCategory/cac:TaxScheme/cbc:ID igual a "1000" o "9997"
   - **Mensaje:** El XML debe contener al menos un tributo por linea de afectacion por IGV
   - **Listados:** -

2. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del Item
   - **Listados:** -

3. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El codigo del tributo es invalido

4. **ERROR**
   - **Validación:** Existe en el ítem otro tag cac:TaxSubtotal con el mismo código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de item
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 22. Sumatoria IGV del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true" / "false"
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:ChargeIndicator (Indicador de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'true' para 'código de motivo de cargo' igual a "45","49", "50" y "52"
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.

2. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para 'Código de motivo de descuento' igual a "02" y "03"
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento no corresponde al valor esperado.
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** n2
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:AllowanceChargeReasonCode (Código de motivo de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento por item.

2. **OBSERV**
   - **Validación:** El valor del tag es distinto a "02", "03", "45", "49", "50" y "52"
   - **Mensaje:** El dato ingresado como cargo/descuento no es valido a nivel de ítem.
   - **Listados:** Catálogo<br>(053)
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:AllowanceCharge/cbc:MultiplierFactorNumeric (Factor de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El factor de cargo/descuento por linea no cumple con el formato establecido.
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:Amount (Monto de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El formato ingresado en el tag cac:InvoiceLine/cac:Allowancecharge/cbc:Amount no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** Si existe el tag 'Código de motivo de cargo/descuento' y existe 'Factor de cargo/descuento' con monto mayor a cero, el importe difiere del resultado de multiplicar 'Monto base del cargo/descuento' por el 'Factor de cargo/descuento', con una tolerancia + - 1.
   - **Mensaje:** El valor de cargo/descuento por ítem difiere de los importes consignados.
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:Allowancecharge/cbc:BaseAmount (Monto base del cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El Monto base de cargo/descuento por linea no cumple con el formato establecido.
## 23. Fondo de Inclusión Social Energético (FISE) del partícipe<br>Otros Cargos/Descuentos del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 24. Importe total del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL cac:InvoiceLine/cac:ItemPriceExtension
   - **Mensaje:** Debe consignar el tag /cac:InvoiceLine/cac:ItemPriceExtension
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** El valor del tag difiere de la sumatoria del "Total valor de venta del participe" más "Sumatoria ISC del partícipe" más "Sumatoria IGV del partícipe" más "Fondo de Inclusión Social Energético (FISE) del partícipe" (Código de motivo de cargo '45') más "Otros Cargos del partícipe" que no afectan la base (Código de motivo de cargo '50') menos "Descuentos del partícipe" que no afectan la base (Código de motivo de descuento '03') con una tolerancia de +-1
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:ItemPriceExtension/cbc:Amount no coincide con el valor calculado
## 24. Importe total del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 25. Leyendas a nivel del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo No. 52)
- **Tag:** /Invoice/cac:InvoiceLine/cbc:Note@languageLocaleID (Código de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** Si el atributo existe, el valor del atributo no existe en el listado
   - **Mensaje:** El valor del atributo no se encuentra en el catálogo
   - **Listados:** Catálogo<br>(052)
## 25. Leyendas a nivel del partícipe

- **Nivel:** Ítem
- **Condición informática:** C

### Validaciones

1. **OBSERV**
   - **Validación:** El valor del Tag UBL no debe repetirse en el comprobante
   - **Mensaje:** El codigo de leyenda no debe repetirse en el comprobante
## 25. Leyendas a nivel del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..500
- **Tag:** /Invoice/cac:InvoiceLine/cbc:Note  (Descripción de la leyenda)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El dato ingresado en descripcion de leyenda no cumple con el formato establecido.
## Datos de cada participe -  Detalle del partícipe. Datos de cada participe -  Detalle del partícipe

- **Nivel:** Datos de cada participe -  Detalle del partícipe
- **Condición informática:** Datos de cada participe -  Detalle del partícipe
- **Tipo y longitud:** Datos de cada participe -  Detalle del partícipe
- **Formato / Valor:** Datos de cada participe -  Detalle del partícipe
- **Tag:** Datos de cada participe -  Detalle del partícipe
## 26. Número de orden del ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** n..3
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:ID

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de numérico de hasta 3 dígitos; o, es igual cero.
   - **Mensaje:** El dato ingresado en el tag cac:SubInvoiceLine/cbc:ID no cumple con el formato establecido
   - **Listados:** -

2. **ERROR**
   - **Validación:** Existe otro subítem (cac:InvoiceLine/cac:SubInvoiceLine) con el mismo valor del Tag UBL
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cbc:ID no debe repetirse en el mismo cac:InvoiceLine
   - **Listados:** -
## 27. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..3
- **Formato / Valor:** (Catálogo No. 03)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:InvoicedQuantity@unitCode

### Validaciones

1. **ERROR**
   - **Validación:** No existe el atributo del Tag UBL o es vacío
   - **Mensaje:** Es obligatorio indicar la unidad de medida del ítem
   - **Listados:** Catálogo<br>(003)
## 27. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tag:** @unitCodeListID

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'UN/ECE rec 20'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListID es incorrecto.
   - **Listados:** Catálogo<br>(003)
## 27. Unidad de medida por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tag:** @unitCodeListAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'United Nations Economic Commission for Europe'
   - **Mensaje:** El dato ingresado como atributo @unitCodeListAgencyName es incorrecto.
   - **Listados:** -
## 28. Cantidad de unidades por ítem

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:InvoicedQuantity

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InvoicedQuantity en el detalle de los Items o es cero (0)
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales
   - **Mensaje:** InvoicedQuantity El dato ingresado no cumple con el estandar
   - **Listados:** -
## 29. Descripción del bien

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..500
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Item/cbc:Description

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag cac:Item/cbc:Description en el detalle de los Items
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 500 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El XML no contiene el tag o no existe informacion de cac:Item/cbc:Description del item
   - **Listados:** -
## 30. Valor unitario por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Price/cbc:PriceAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:Price/cbc:PriceAmount en el detalle de los Items
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 10 decimales y diferente de cero.<br>Nota: En casos de enteros, el valor monetario no debe tener ceros a la izquierda.
   - **Mensaje:** El dato ingresado en PriceAmount del Valor de venta unitario por item no cumple con el formato establecido
   - **Listados:** -
## 30. Valor unitario por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..23
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 31. Valor de venta por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cbc:LineExtensionAmount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor difiere del resultado de multiplicar el "Valor unitario por ítem" por la "Cantidad de unidades por ítem", menos los descuentos que afecten la base imponible del ítem ('Código de motivo de descuento' igual a '00' y '07') más los cargos que afecten la base imponible del ítem ('Código de motivo de cargo' igual a '47' y '54'),  con una tolerancia + - 1.
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:SubInvoiceLine/cbc:LineExtensionAmount no coincide con el valor calculado
   - **Listados:** -
## 31. Valor de venta por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 32. Total Impuestos por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cbc:TaxAmount (Total impuestos)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag cac:TaxTotal en el /Invoice/cac:InvoiceLine/cac:SubInvoiceLine
   - **Mensaje:** No existe el tag cac:TaxTotal en el /Invoice/cac:InvoiceLine/cac:SubInvoiceLine

2. **ERROR**
   - **Validación:** Existe más de un tag cac:TaxTotal en el /Invoice/cac:InvoiceLine/cac:SubInvoiceLine
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse en el /Invoice/cac:InvoiceLine/cac:SubInvoiceLine

3. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cbc:TaxAmount no cumple el formato establecido

4. **OBSERV**
   - **Validación:** El valor del tag es diferente a la sumatoria de 'Monto de tributo por ítem' (cbc:TaxAmount de los tributos '1000' y '2000'), con una tolerancia + -1
   - **Mensaje:** El importe del campo /cac:SubInvoiceLine/cac:TaxTotal/cbc:TaxAmount no coincide con el valor calculado
## 32. Total Impuestos por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo del ítem)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount no cumple el formato establecido
   - **Listados:** -
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 08)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TierRange (Tipo de sistema de ISC)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de tributo por ítem' es '2000' (ISC) cuyo 'Monto del tributo' es mayor a cero (cbc:TaxAmount > 0), no existe el Tag UBL
   - **Mensaje:** Si existe monto de ISC en el ITEM debe especificar el sistema de calculo
   - **Listados:** -

2. **ERROR**
   - **Validación:** Si 'Código de tributo por ítem' es diferente '2000' (ISC), existe el Tag UBL
   - **Mensaje:** Solo debe consignar sistema de calculo si el tributo es ISC
   - **Listados:** -

3. **ERROR**
   - **Validación:** Si 'Código de tributo por ítem' es '2000' (ISC) cuyo 'Monto del tributo' es mayor a cero (cbc:TaxAmount > 0), el valor del Tag UBL es diferente al listado
   - **Mensaje:** El sistema de calculo del ISC es incorrecto
   - **Listados:** Catálogo<br>(008)
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "2000"
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por ítem)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del /cac:SubInvoiceLine

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El codigo del tributo es invalido

3. **ERROR**
   - **Validación:** Existe en el ítem otro tag cac:TaxSubtotal con el mismo código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel del /cac:SubInvoiceLine
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 33. Monto de ISC por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount (Monto base del IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount no cumple el formato establecido
   - **Listados:** -
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto del tributo del ítem)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag /cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount no cumple el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si 'Código de tributo por línea' es '1000', el valor del tag es diferente del resultado de multiplicar el 'Monto base del IGV' por la tasa vigente del IGV a la fecha de emisión, con una tolerancia + - 1
   - **Mensaje:** El monto de IGV a nivel de /cac:SubInvoiceLine no coincide con el valor calculado
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si 'Código de tributo por línea' es '9997', el valor del tag es diferente de cero
   - **Mensaje:** El monto de IGV a nivel de /cac:SubInvoiceLine no coincide con el valor calculado
   - **Listados:** -
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo No. 07)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:TaxExemptionReasonCode (Afectación al IGV)

### Validaciones

1. **ERROR**
   - **Validación:** Si 'Código de tributo por línea' es '1000' o '9997', el valor del Tag UBL es diferente al listado según su código de tributo.
   - **Mensaje:** El tipo de afectacion del IGV es incorrecto
   - **Listados:** Catálogo<br>(007)
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"<br>"9997"
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo por ítem)

### Validaciones

1. **ERROR**
   - **Validación:** No existe en el ítem del detalle del partícipe un tag cac:TaxTotal/cac:TaxSubtotal con cac:TaxCategory/cac:TaxScheme/cbc:ID igual a "1000" o "9997"
   - **Mensaje:** El XML debe contener al menos un tributo de IGV en el /cac:SubInvoiceLine

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag cac:TaxCategory/cac:TaxScheme/cbc:ID del /cac:SubInvoiceLine

3. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El codigo del tributo es invalido

4. **ERROR**
   - **Validación:** Existe otro tag cac:TaxSubtotal con el mismo código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel del /cac:SubInvoiceLine
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
   - **Listados:** -
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
   - **Listados:** -
## 34. Monto de IGV por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Allowancecharge/cbc:ID (Descripción de cargo/descuento)

### Validaciones

1. **Validación**
   - **Validación:** <<SIN VALIDACION>>
   - **Mensaje:** -
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..5
- **Formato / Valor:** "true" / "false"
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Allowancecharge/cbc:ChargeIndicator (Indicador de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si valor del tag es diferente de 'true' para 'Código de motivo de cargo' igual a "47" y "54"
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento a nivel de /cac:SubInvoiceLine no corresponde al valor esperado

2. **ERROR**
   - **Validación:** Si valor del tag es diferente 'false' para 'Código de motivo de descuento' igual a "00" y "07"
   - **Mensaje:** El dato ingresado como indicador de cargo/descuento a nivel de /cac:SubInvoiceLine no corresponde al valor esperado
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Allowancecharge/cbc:AllowanceChargeReasonCode (Código de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si existe 'Indicador de cargo/descuento', y no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de codigo de motivo de cargo/descuento a nivel de /cac:SubInvoiceLine
   - **Listados:** -

2. **OBSERV**
   - **Validación:** El valor del tag es diferente de "00", "07", "47" y "54"
   - **Mensaje:** El dato ingresado como cargo/descuento no es valido a nivel de /cac:SubInvoiceLine
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @listAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @listAgencyName es incorrecto.
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "Cargo/descuento"
- **Tag:** @listName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'Cargo/descuento'
   - **Mensaje:** El dato ingresado como atributo @listName es incorrecto.
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53"
- **Tag:** @listURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el atributo, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo53'
   - **Mensaje:** El dato ingresado como atributo @listURI es incorrecto.
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..9
- **Formato / Valor:** n(3,5)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:AllowanceCharge/cbc:MultiplierFactorNumeric (Factor de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente de decimal positivo de 3 enteros y hasta 5 decimales y diferente de cero
   - **Mensaje:** El factor de cargo/descuento a nivel de /cac:SubInvoiceLine no cumple con el formato establecido
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Allowancecharge/cbc:Amount (Monto de cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag cac:SubInvoiceLine/cac:Allowancecharge/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

2. **OBSERV**
   - **Validación:** Si existe el tag 'Código de motivo de cargo/descuento' y existe 'Factor de cargo/descuento' con monto mayor a cero, el importe difiere del resultado de multiplicar 'Monto base del cargo/descuento' por el 'Factor de cargo/descuento', con una tolerancia + - 1.
   - **Mensaje:** El valor de cargo/descuento a nivel de /cac:SubInvoiceLine difiere de los importes consignados.
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..23
- **Formato / Valor:** n(12,10)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:Allowancecharge/cbc:BaseAmount (Monto base del cargo/descuento)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El Monto base de cargo/descuento a nivel de /cac:SubInvoiceLine no cumple con el formato establecido
   - **Listados:** -
## 35. Cargos y Descuentos por ítem - detalle por partícipe

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 36. Importe total por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension
   - **Mensaje:** Debe consignar el tag /cac:SubInvoiceLine/cac:ItemPriceExtension
   - **Listados:** -

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en el tag cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount no cumple con el formato establecido
   - **Listados:** -

3. **OBSERV**
   - **Validación:** Si el valor del tag difiere de la sumatoria del "Valor de venta por ítem" más "Monto de ISC por ítem" más "Monto de IGV por ítem",  con una tolerancia + - 1.
   - **Mensaje:** El importe del campo /cac:InvoiceLine/cac:SubInvoiceLine/cac:ItemPriceExtension/cbc:Amount no coincide con el valor calculado
   - **Listados:** -
## 36. Importe total por ítem - detalle del partícipe

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## Totales del DAE - Documento Operador. Totales del DAE - Documento Operador

- **Nivel:** Totales del DAE - Documento Operador
- **Condición informática:** Totales del DAE - Documento Operador
- **Tipo y longitud:** Totales del DAE - Documento Operador
- **Formato / Valor:** Totales del DAE - Documento Operador
- **Tag:** Totales del DAE - Documento Operador
## 37. Importe total  - Valor de Venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:LineExtensionAmount

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag
   - **Mensaje:** El XML no contiene el tag cac:LegalMonetaryTotal/cbc:LineExtensionAmount

2. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite valor cero)
   - **Mensaje:** El dato ingresado en el tag cac:LegalMonetaryTotal/cbc:LineExtensionAmount no cumple con el formato establecido

3. **OBSERV**
   - **Validación:** El valor del tag es diferente de la sumatoria de "Total valor de venta del partícipe", con una tolerancia +-1
   - **Mensaje:** La sumatoria de valor de venta no corresponde a los importes consignados
## 37. Importe total  - Valor de Venta

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto total - IGV)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '1000', el valor del Tag UBL es diferente a la sumatoria de las 'Sumatoria IGV del partícipe' (cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount) de las de las líneas informadas con 'Código de tributo' igual a '1000' (con una tolerancia + - 1)
   - **Mensaje:** El cálculo del IGV es Incorrecto
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** "1000"<br>"9997"
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe en el XML un tag cac:TaxTotal/cac:TaxSubtotal con cac:TaxCategory/cac:TaxScheme/cbc:ID igual a "1000" o "9997"
   - **Mensaje:** Debe indicar Información acerca del importe total de IGV/IVAP

2. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.

3. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.

4. **ERROR**
   - **Validación:** Existe a nivel global  más de un cac:TaxSubtotal con el mismo valor del Tag UBL (cbc:ID)
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
## 38. Importe Total -  IGV

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount (Monto total - ISC)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** Si 'Código de tributo' es '2000', el valor del Tag UBL es diferente a la sumatoria de las 'Sumatoria ISC del partícipe' (cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount) de las de las líneas informadas con 'Código de tributo' igual a '2000' (con una tolerancia + - 1)
   - **Mensaje:** La sumatoria del total del importe del tributo ISC de línea no corresponden al total
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an4
- **Formato / Valor:** "2000"
- **Tag:** /Invoice/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID (Código de tributo)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de código de tributo.

2. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente a "1000", "9997" y "2000"
   - **Mensaje:** El dato ingresado como codigo de tributo global no corresponde al valor esperado.

3. **ERROR**
   - **Validación:** Si existe otro tag cac:TaxSubtotal con el mismo valor de código de tributo
   - **Mensaje:** El código de tributo no debe repetirse a nivel de totales
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "Codigo de tributos"
- **Tag:** @schemeName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'Codigo de tributos'
   - **Mensaje:** El dato ingresado como atributo @schemeName es incorrecto.
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "PE:SUNAT"
- **Tag:** @schemeAgencyName

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'PE:SUNAT'
   - **Mensaje:** El dato ingresado como atributo @schemeAgencyName es incorrecto.
## 39. Importe Total -  ISC

- **Nivel:** Global
- **Condición informática:** C
- **Formato / Valor:** "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05"
- **Tag:** @schemeURI

### Validaciones

1. **OBSERV**
   - **Validación:** Si existe el tag, el valor ingresado es diferente a 'urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05'
   - **Mensaje:** El dato ingresado como atributo @schemeURI es incorrecto.
## 40. Importe Total - Impuestos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:TaxTotal/cbc:TaxAmount (Monto total impuestos)

### Validaciones

1. **ERROR**
   - **Validación:** No existe el tag cac:TaxTotal en el /Invoice
   - **Mensaje:** El Monto total de impuestos es obligatorio

2. **ERROR**
   - **Validación:** Existe más de un tag cac:TaxTotal en el /Invoice
   - **Mensaje:** El tag cac:TaxTotal no debe repetirse a nivel de totales

3. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente a la sumatoria del 'Importe Total - ISC' más 'Importe Total - IGV' , con una tolerancia + - 1
   - **Mensaje:** La sumatoria de impuestos globales no corresponde al monto total de impuestos.
## 40. Importe Total - Impuestos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 41. Importe Total - Descuentos<br>(Que no afectan la base)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en el campo Total Descuentos no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente de la sumatoria de "Descuentos del partícipe",  con una tolerancia + - 1
   - **Mensaje:** La sumatoria consignados en descuentos globales no corresponden al total.
## 41. Importe Total - Descuentos<br>(Que no afectan la base)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 42. Importe Total - Cargos<br>(Que no afectan la base)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:ChargeTotalAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en ChargeTotalAmount no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente de la sumatoria de "Fondo de Inclusión Social Energético (FISE) del partícipe" más "Otros cargos del partícipe",  con una tolerancia + - 1
   - **Mensaje:** La sumatoria consignados en cargos globales no corresponden al total
## 42. Importe Total - Cargos<br>(Que no afectan la base)

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** Si existe el atributo, el valor es diferente al ingresado en "Importe total de DAE"
   - **Mensaje:** La moneda debe ser la misma en todo el documento
   - **Listados:** Catálogo<br>(002)
## 43. Importe total de DAE

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Invoice/cac:LegalMonetaryTotal/cbc:PayableAmount

### Validaciones

1. **ERROR**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales (se permite cero)
   - **Mensaje:** El dato ingresado en PayableAmount no cumple con el formato establecido

2. **OBSERV**
   - **Validación:** El valor del Tag UBL es diferente de la sumatoria de "Importe total  - Valor de Venta" más "Importe Total -  Impuestos" menos "Importe Total - Descuentos" más"Importe Total - Cargos", con una tolerancia de + - 1
   - **Mensaje:** El importe total del comprobante no coincide con el valor calculado
## 43. Importe total de DAE

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo No. 02)
- **Tag:** @currencyID

### Validaciones

1. **ERROR**
   - **Validación:** El valor del Tag UBL es diferente al listado.
   - **Mensaje:** El valor ingresado como moneda del comprobante no es valido (catalogo nro 02).
   - **Listados:** Catálogo<br>(002)
