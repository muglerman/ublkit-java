# Validacionesv20260212

## Hoja: Retenciones1_0

## -. -

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -

### Validaciones

1. **- -**
   - **Validación:** <<< REVISAR HOJA "GENERAL" >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos de la percepción
## 1. Versión del UBL

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** 2.0
- **Tag:** /Retention/cbc:UBLVersionID

### Validaciones

1. **an3 2111**
   - **Validación:** El Tag UBL está vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **an3 2110**
   - **Validación:** El valor del Tag UBL es diferente a "2.0"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** 1.0
- **Tag:** /Retention/cbc:CustomizationID

### Validaciones

1. **an3 2113**
   - **Validación:** El Tag UBL está vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de CustomizationID
   - **Listados:** -

2. **an3 2112**
   - **Validación:** El valor del Tag UBL es diferente a "1.0"
   - **Mensaje:** CustomizationID - La version del documento no es correcta
   - **Listados:** -
## 3. Firma digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Formato / Valor:** -
- **Tag:** /Retention/ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature<br>/Retention/cac:Signature

### Validaciones

1. **an..3000 -**
   - **Validación:** <<< REVISAR HOJA "FIRMA" >>>
   - **Mensaje:** -
   - **Listados:** -
## 4. Numeración, conformada por serie y número correlativo

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Retention/cbc:ID

### Validaciones

1. **an..13 1049**
   - **Validación:** El valor del Tag UBL es diferente al nombre del archivo
   - **Mensaje:** ID - Serie y Número del archivo no coincide con el consignado en el contenido del XML.
   - **Listados:** -

2. **an..13 1001**
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [R][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante
   - **Listados:** -

3. **an..13 1033**
   - **Validación:** Si la serie empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 2<br>Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 1 o 2
   - **Mensaje:** El comprobante fue registrado previamente con otros datos
   - **Listados:** Comprobantes de pago electronicos

4. **an..13 3207**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes contingencia

5. **an..13 3207**
   - **Validación:** Si la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## 5. Fecha de emisión

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Retention/cbc:IssueDate

### Validaciones

1. **an10 2600**
   - **Validación:** Si serie del documento no inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al plazo máximo vigente.
   - **Mensaje:** El comprobante fue enviado fuera del plazo permitido.
   - **Listados:** -
## 6. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Tag:** /Retention/cbc:IssueTime

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del emisor electrónico
## 7. Número de documento de identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Retention/cac:AgentParty/cac:PartyIdentification/cbc:ID

### Validaciones

1. **n11 1034**
   - **Validación:** El valor del Tag UBL es diferente al RUC del nombre del XML
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **n11 2617**
   - **Validación:** No existe ind_padrón igual a "03" en el listado para el valor del Tag UBL
   - **Mensaje:** Senor contribuyente a la fecha no se encuentra registrado ó habilitado con la condición de Agente de retención.
   - **Listados:** Padrones de contribuyentes
## 8. Tipo de documento de Identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Retention/cac:AgentParty/cac:PartyIdentification/cbc:ID@schemeID

### Validaciones

1. **n1 2678**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el atributo o no existe información del tipo de documento del emisor
   - **Listados:** -

2. **n1 2511**
   - **Validación:** El valor del Tag UBL es diferente a 6
   - **Mensaje:** El tipo de documento no es aceptado.
   - **Listados:** -
## 9. Nombre comercial del emisor

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Retention/cac:AgentParty/cac:PartyName/cbc:Name

### Validaciones

1. **an..1500 2901**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del emisor no cumple con el formato establecido
   - **Listados:** -
## 10. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Retention/cac:AgentParty/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **an..1500 1037**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del emisor del documento
   - **Listados:** -

2. **an..1500 1038**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El nombre o razon social del emisor no cumple con el estandar
   - **Listados:** -
## Domicilio fiscal del emisor electrónico. Registro Domicilio fiscal del emisor electrónico

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 11. Ubigeo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:ID

### Validaciones

1. **an6 2917**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Parámetros (016)
## 12. Dirección completa y detallada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:StreetName

### Validaciones

1. **an..100 2916**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 100 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 13. Urbanización

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:CitySubdivisionName

### Validaciones

1. **an..30 2902**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 14. Provincia

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:CityName

### Validaciones

1. **an..30 2903**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 15. Departamento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:CountrySubentity

### Validaciones

1. **an..30 2904**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 16. Distrito

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cbc:District

### Validaciones

1. **an..30 2905**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 17. Código del país de la dirección

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Retention/cac:AgentParty/cac:PostalAddress/cac:Country/cbc:IdentificationCode

### Validaciones

1. **a2 2548**
   - **Validación:** Si el Tag UBL existe, el valor es diferente a "PE"
   - **Mensaje:** El valor del país inválido.
   - **Listados:** -
## Información del proveedor. Registro Información del proveedor

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 18. Número de documento de identidad del proveedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Retention/cac:ReceiverParty/cac:PartyIdentification/cbc:ID

### Validaciones

1. **n11 2723**
   - **Validación:** El valor del Tag UBL esta vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de documento de identidad del proveedor
   - **Listados:** -

2. **n11 2724**
   - **Validación:** El formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El valor ingresado como documento de identidad del proveedor es incorrecto
   - **Listados:** -

3. **n11 2620**
   - **Validación:** El valor del Tag UBL es igual al "Número de documento de identidad del emisor"
   - **Mensaje:** El Proveedor no puede ser el mismo que el Emisor del comprobante de retención.
   - **Listados:** -

4. **n11 2621**
   - **Validación:** El valor del Tag UBL no está en el listado
   - **Mensaje:** Número de RUC del Proveedor no existe.
   - **Listados:** Contribuyentes

5. **n11 4091**
   - **Validación:** Si ind_padrón es igual a "01", "02", "03" o "10" en el listado para el valor del Tag UBL
   - **Mensaje:** La operación con este proveedor está excluida del sistema de retención. Es agente de percepción, agente de retención o buen contribuyente.
   - **Listados:** Padrones de contribuyentes
## 19. Tipo de documento de Identidad del proveedor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Retention/cac:ReceiverParty/cac:PartyIdentification/cbc:ID@schemeID

### Validaciones

1. **n1 2516**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** Debe indicar tipo de documento.
   - **Listados:** -

2. **n1 2511**
   - **Validación:** El valor del Tag UBL es diferente a 6
   - **Mensaje:** El tipo de documento no es aceptado.
   - **Listados:** -
## 20. Nombre comercial del proveedor

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Retention/cac:ReceiverParty/cac:PartyName/cbc:Name

### Validaciones

1. **an..1500 2906**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del proveedor no cumple con el formato establecido
   - **Listados:** -
## 21. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Retention/cac:ReceiverParty/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **an..1500 2134**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **an..1500 2133**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## Domicilio fiscal del proveedor. Registro Domicilio fiscal del proveedor

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 22. Ubigeo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:ID

### Validaciones

1. **an6 2917**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Parámetros (016)
## 23. Dirección completa y detallada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:StreetName

### Validaciones

1. **an..100 2918**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 100 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del proveedor no cumple con el formato establecido
   - **Listados:** -
## 24. Urbanización

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:CitySubdivisionName

### Validaciones

1. **an..30 2907**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del proveedor no cumple con el formato establecido
   - **Listados:** -
## 25. Provincia

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:CityName

### Validaciones

1. **an..30 2908**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del proveedor no cumple con el formato establecido
   - **Listados:** -
## 26. Departamento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:CountrySubentity

### Validaciones

1. **an..30 2909**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del proveedor no cumple con el formato establecido
   - **Listados:** -
## 27. Distrito

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cbc:District

### Validaciones

1. **an..30 2910**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres  (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del proveedor no cumple con el formato establecido
   - **Listados:** -
## 28. Código del país de la dirección

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Retention/cac:ReceiverParty/cac:PostalAddress/cac:Country/cbc:IdentificationCode

### Validaciones

1. **a2 2548**
   - **Validación:** Si el Tag UBL existe, el valor es diferente a "PE"
   - **Mensaje:** El valor del país inválido.
   - **Listados:** -
## Datos de la retención del CRE
## 29. Código del régimen de retención

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n2
- **Formato / Valor:** (Catálogo N.° 23)
- **Tag:** /Retention/sac:SUNATRetentionSystemCode

### Validaciones

1. **n2 2618**
   - **Validación:** El valor del Tag UBL no está en el listado
   - **Mensaje:** El régimen retención enviado no corresponde con su condición de Agente de retención.
   - **Listados:** Catálogo<br>(023)
## 30. Tasa de retención

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..4
- **Formato / Valor:** n(1,2)
- **Tag:** /Retention/sac:SUNATRetentionPercent

### Validaciones

1. **an..4 2619**
   - **Validación:** El valor del Tag UBL es diferente a la Tasa de retención del listado para el "Código del regimen de retención"
   - **Mensaje:** La tasa de retención enviada no corresponde con el régimen de retención.
   - **Listados:** Catálogo<br>(023)
## 31. Observaciones

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..250
- **Tag:** /Retention/cbc:Note

### Validaciones

1. **an..250 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 32. Importe total retenido

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/cbc:TotalInvoiceAmount

### Validaciones

1. **an..15 2669**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en TotalInvoiceAmount debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2628**
   - **Validación:** El valor de Tag UBL es diferente a la suma de "Importe retenido", sin considerar los tipos de documentos “07” y “20”.
   - **Mensaje:** Importe total retenido debe ser igual a la suma de los importes retenidos por cada documento relacionado.
   - **Listados:** -
## 33. Moneda del importe total retenido

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/cbc:TotalInvoiceAmount@currencyID

### Validaciones

1. **an3 2728**
   - **Validación:** El valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Retenido debe ser PEN
   - **Listados:** -
## 34. Importe total Pagado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/sac:SUNATTotalPaid

### Validaciones

1. **an..15 2730**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en SUNATTotalPaid debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2629**
   - **Validación:** El valor de Tag UBL es diferente a la suma de "Importe total a pagar"  más el "Monto de redondeo del importe total pagado", sin considerar los tipos de documentos “07” y “20”
   - **Mensaje:** Importe total pagado debe ser igual a la suma de los importes pagados por cada documento relacionado.
   - **Listados:** -
## 35. Moneda del importe total pagado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATTotalPaid@currencyID

### Validaciones

1. **an3 2732**
   - **Validación:** El valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Pagado debe ser PEN
   - **Listados:** -
## 36. Monto de redondeo del importe total pagado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/cbc:PayableRoundingAmount

### Validaciones

1. **an..15 3303**
   - **Validación:** Si existe el tag UBL, el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 36. Monto de redondeo del importe total pagado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/cbc:PayableRoundingAmount@currencyID

### Validaciones

1. **an3 3304**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a "PEN"
   - **Mensaje:** La moneda del monto para el redondeo debe ser PEN
   - **Listados:** -
## Dato del comprobante relacionado. Registro Dato del comprobante relacionado

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 37. Tipo de documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cbc:ID@schemeID

### Validaciones

1. **an2 2691**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información del tipo de documento relacionado
   - **Listados:** -

2. **an2 2692**
   - **Validación:** El valor del Tag UBL es diferente a "01", "12", "07", "08", "20"
   - **Mensaje:** El tipo de documento relacionado no es válido
   - **Listados:** -
## 38. Serie y número del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cbc:ID

### Validaciones

1. **an..13 2693**
   - **Validación:** El valor del Tag UBL esta vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de documento relacionado
   - **Listados:** -

2. **an..13 2694**
   - **Validación:** Si "Tipo de documento relacionado" es "12", el formato del Tag UBL es diferente a:<br>- [a-zA-Z0-9-]{1,20}(-[0-9]{1,20})
   - **Mensaje:** El número de documento relacionado no está permitido o no es valido
   - **Listados:** -

3. **an..13 2694**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "12", el formato del Tag UBL es diferente a:<br>(E001|((F|R)[A-Z0-9]{3})|([0-9]{4}))-(?!0+$)([0-9]{1,8})
   - **Mensaje:** El número de documento relacionado no está permitido o no es valido
   - **Listados:** -
## 39. Fecha de emisión documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cbc:IssueDate

### Validaciones

1. **an..10 2985**
   - **Validación:** Si el “Código del régimen de retención” es “02” (TASA 6%) y el valor del Tag UBL es mayor al 28/02/2014
   - **Mensaje:** Solo se acepta comprobantes con fecha de emisión hasta el 28/02/2014 si la tasa del comprobante de retencion 6%
   - **Listados:** -
## 40. Importe total del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cbc:TotalInvoiceAmount

### Validaciones

1. **an..15 2696**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el importe total documento relacionado debe ser numérico mayor a cero
   - **Listados:** -
## 41. Tipo de moneda del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cbc:TotalInvoiceAmount@currencyID

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del pago (3)
## 42. Fecha de pago

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cac:Payment/cbc:PaidDate

### Validaciones

1. **an10 2737**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la fecha de pago del documento Relacionado
   - **Listados:** -

2. **an10 2661**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es de mes/año (periodo) diferente a otra fecha de pago en /Retention
   - **Mensaje:** La fecha de cobro de cada documento relacionado deben ser del mismo Periodo (mm/aaaa), asimismo estas fechas podrán ser menores o iguales a la fecha de emisión del comprobante de retencion
   - **Listados:** -

3. **an10 2625**
   - **Validación:** Si el Tag UBL existe y la "Fecha de emisión del documento relacionado" es del mismo mes/año (periodo) de la "Fecha de emisión", el valor del Tag UBL es menor a "Fecha de emisión del documento relacionado"
   - **Mensaje:** La fecha de pago debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de retención o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

4. **an10 2625**
   - **Validación:** Si el Tag UBL existe y la "Fecha de emisión del documento relacionado" es del mismo mes/año (periodo) de la "Fecha de emisión", el valor del Tag UBL es mayor a "Fecha de emisión"
   - **Mensaje:** La fecha de pago debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de retención o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

5. **an10 2625**
   - **Validación:** Si el Tag UBL existe y la "Fecha de emisión del documento relacionado" es de diferente mes/año (periodo) de la "Fecha de emisión", el valor del Tag UBL es menor al primer día del mes de "Fecha de emisión"
   - **Mensaje:** La fecha de pago debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de retención o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

6. **an10 2625**
   - **Validación:** Si el Tag UBL existe y la "Fecha de emisión del documento relacionado" es de diferente mes/año (periodo) de la "Fecha de emisión", el valor del Tag UBL es mayor a "Fecha de emisión"
   - **Mensaje:** La fecha de pago debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de retención o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -
## 43. Número de pago

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..9
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cac:Payment/cbc:ID

### Validaciones

1. **n..9 2733**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de pago
   - **Listados:** -

2. **n..9 2734**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el formato del Tag UBL es diferente a numérico de hasta 9 dígitos
   - **Mensaje:** El dato ingresado en el número de pago no es válido
   - **Listados:** -

3. **n..9 2626**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el "Número de documento relacionado" concatenado con el valor del Tag, se repite en /Retention
   - **Mensaje:** El Nro. de documento con el número de pago ya se encuentra en la Relación de Documentos Relacionados agregados.
   - **Listados:** -
## 44. Importe del pago sin retención

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cac:Payment/cbc:PaidAmount

### Validaciones

1. **an..15 2735**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información del Importe del pago
   - **Listados:** -

2. **an..15 2736**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Importe del pago debe ser numérico mayor a cero
   - **Listados:** -
## 45. Moneda del importe del pago sin retención

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/cac:Payment/cbc:PaidAmount@currencyID

### Validaciones

1. **an3 2622**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el valor del Tag UBL es diferente al "Tipo de moneda del documento relacionado"
   - **Mensaje:** La moneda del importe de pago debe ser la misma que la del documento relacionado.
   - **Listados:** -
## Datos de la retención (4)
## 46. Importe retenido

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/sac:SUNATRetentionAmount

### Validaciones

1. **an..15 2740**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Importe retenido debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2623**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de pago sin retención" multiplicado por "Tasa de retención" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, retenidos y montos pagados consignados para el documento relacionado no son correctos.
   - **Listados:** -

3. **an..15 2623**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es diferente "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de pago sin retención" multiplicado por "Tasa de retención" multiplicado por "Tipo de cambio" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, retenidos y montos pagados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 47. Moneda de importe retenido

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/sac:SUNATRetentionAmount@currencyID

### Validaciones

1. **an3 2742**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a "PEN"
   - **Mensaje:** El valor de la moneda de importe retenido debe ser PEN
   - **Listados:** -
## 48. Fecha de retención

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/sac:SUNATRetentionDate

### Validaciones

1. **an10 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 49. Importe total a pagar (neto)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/sac:SUNATNetTotalPaid

### Validaciones

1. **an..15 2746**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Importe total a pagar (neto) debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2623**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe del pago sin retención" menos "Importe retenido" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, retenidos y montos pagados consignados para el documento relacionado no son correctos.
   - **Listados:** -

3. **an..15 2623**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es diferente "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe del pago sin retención" multiplicado por "Tipo de cambio" menos  "Importe retenido" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, retenidos y montos pagados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 50. Moneda del importe total a pagar (neto)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/sac:SUNATNetTotalPaid@currencyID

### Validaciones

1. **an3 2748**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a "PEN"
   - **Mensaje:** El valor de la Moneda del monto neto pagado debe ser PEN
   - **Listados:** -
## Datos del tipo de cambio (5)
## 51. Moneda de referencia para el Tipo de Cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/cac:ExchangeRate/cbc:SourceCurrencyCode

### Validaciones

1. **an3 2719**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la moneda de referencia para el tipo de cambio
   - **Listados:** -

2. **an3 2749**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el valor del Tag UBL es diferente "Tipo de moneda del documento relacionado"
   - **Mensaje:** La moneda de referencia para el tipo de cambio debe ser la misma que la del documento relacionado
   - **Listados:** -
## 52. Moneda objetivo para la Tasa de Cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/cac:ExchangeRate/cbc:TargetCurrencyCode

### Validaciones

1. **an3 2715**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda objetivo para la Tasa de Cambio debe ser PEN
   - **Listados:** -
## 53. Factor aplicado a la moneda de origen para calcular la moneda de destino (Tipo de cambio)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..11
- **Formato / Valor:** n(4,6)
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/cac:ExchangeRate/cbc:CalculationRate

### Validaciones

1. **an..11 2721**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información del tipo de cambio
   - **Listados:** -

2. **an..11 2716**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 4 enteros y 6 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el tipo de cambio debe ser numérico mayor a cero
   - **Listados:** -
## 54. Fecha de cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Retention/sac:SUNATRetentionDocumentReference/sac:SUNATRetentionInformation/cac:ExchangeRate/cbc:Date

### Validaciones

1. **an10 2722**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la fecha de cambio
   - **Listados:** -
