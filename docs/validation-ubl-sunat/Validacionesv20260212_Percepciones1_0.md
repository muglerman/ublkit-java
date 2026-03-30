# Validacionesv20260212

## Hoja: Percepciones1_0

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
- **Tag:** /Perception/cbc:UBLVersionID

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
- **Tag:** /Perception/cbc:CustomizationID

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
- **Tag:** -

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
- **Tag:** /Perception/cbc:ID

### Validaciones

1. **an..13 1049**
   - **Validación:** El valor del Tag UBL es diferente al nombre del archivo
   - **Mensaje:** ID - Serie y Número del archivo no coincide con el consignado en el contenido del XML.
   - **Listados:** -

2. **an..13 1001**
   - **Validación:** El formato del Tag UBL no tiene el formato:<br>- [P][A-Z0-9]{3}-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante
   - **Listados:** -

3. **an..13 1033**
   - **Validación:** Si la serie empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 2<br>Si la serie NO empieza con número, y el valor del Tag UBL se encuentra en el listado con indicador de estado igual a 1 o 2
   - **Mensaje:** El comprobante fue registrado previamente con otros datos
   - **Listados:** Listado de comprobantes de pago electrónicos

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
- **Tag:** /Perception/cbc:IssueDate

### Validaciones

1. **an10 2600**
   - **Validación:** Si serie del documento no inicia con número:<br>La diferencia entre la fecha de recepción del XML y el valor del Tag UBL es mayor al plazo máximo vigente.
   - **Mensaje:** El comprobante fue enviado fuera del plazo permitido.
   - **Listados:** -
## 6. Hora de emisión

- **Nivel:** Global
- **Condición informática:** C
- **Tag:** /Perception/cbc:IssueTime

### Validaciones

1. **-**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 7. Indicador de emisión excepcional

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an2
- **Formato / Valor:** n2
- **Tag:** /Perception/sac:ExceptionalIndicator

### Validaciones

1. **an2 3322**
   - **Validación:** Si existe el TAG, el valor es diferente de '01'.
   - **Mensaje:** El valor debe ser 01 que corresponde a Emisión de Percepción Excepcional
## Datos del emisor electrónico
## 8. Número de documento de identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Perception/cac:AgentParty/cac:PartyIdentification/cbc:ID

### Validaciones

1. **n11 1034**
   - **Validación:** El valor del Tag UBL es diferente al RUC del nombre del XML
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **n11 4285**
   - **Validación:** No existe ind_padrón igual a "01" o “02” en el listado para el valor del Tag UBL.
   - **Mensaje:** El emisor a la fecha no se encuentra registrado ó habilitado con la condición de Agente de percepción
   - **Listados:** Padrones de contribuyentes
## 9. Tipo de documento de Identidad del emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Perception/cac:AgentParty/cac:PartyIdentification/cbc:ID@schemeID

### Validaciones

1. **n1 2678**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el atributo o no existe información del tipo de documento del emisor
   - **Listados:** -

2. **n1 2511**
   - **Validación:** El valor del Tag UBL es diferente a 6
   - **Mensaje:** El tipo de documento no es aceptado.
   - **Listados:** -
## 10. Nombre comercial del emisor

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Perception/cac:AgentParty/cac:PartyName/cbc:Name

### Validaciones

1. **an..1500 2901**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del emisor no cumple con el formato establecido
   - **Listados:** -
## 11. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Perception/cac:AgentParty/cac:PartyLegalEntity/cbc:RegistrationName

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
## 12. Ubigeo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:ID

### Validaciones

1. **an6 2917**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Parámetros (016)
## 13. Dirección completa y detallada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:StreetName

### Validaciones

1. **an..100 2916**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 14. Urbanización

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:CitySubdivisionName

### Validaciones

1. **an..30 2902**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 15. Provincia

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:CityName

### Validaciones

1. **an..30 2903**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 16. Departamento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:CountrySubentity

### Validaciones

1. **an..30 2904**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 17. Distrito

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cbc:District

### Validaciones

1. **an..30 2905**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del emisor no cumple con el formato establecido
   - **Listados:** -
## 18. Código del país de la dirección

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Perception/cac:AgentParty/cac:PostalAddress/cac:Country/cbc:IdentificationCode

### Validaciones

1. **a2 2548**
   - **Validación:** Si el Tag UBL existe, el valor es diferente a "PE"
   - **Mensaje:** El valor del país inválido.
   - **Listados:** -
## Información del cliente. Registro Información del cliente

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 19. Número de documento de identidad del cliente

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /Perception/cac:ReceiverParty/cac:PartyIdentification/cbc:ID

### Validaciones

1. **n11 2679**
   - **Validación:** El tag UBL esta vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de documento de identidad del cliente
   - **Listados:** -

2. **n11 2680**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de hasta 15 caracteres
   - **Mensaje:** El valor ingresado como documento de identidad del cliente es incorrecto
   - **Listados:** -

3. **n11 2604**
   - **Validación:** El valor del Tag UBL es igual al "Número de documento de identidad del emisor"
   - **Mensaje:** El Cliente no puede ser el mismo que el Emisor del comprobante de percepción.
   - **Listados:** -

4. **n11 2605**
   - **Validación:** Si "Tipo de documento de identidad del cliente" es 6, el valor del Tag UBL no está en el listado
   - **Mensaje:** Número de RUC no existe.
   - **Listados:** Contribuyentes

5. **n11 4089**
   - **Validación:** Si ind_padron = "03" para el valor del Tag UBL en el listado
   - **Mensaje:** La operación con este cliente está excluida del sistema de percepción. Es agente de retención.
   - **Listados:** Padrones de contribuyentes

6. **n11 4090**
   - **Validación:** Si ind_padron = "04" para el valor del Tag UBL en el listado
   - **Mensaje:** La operación con este cliente está excluida del sistema de percepción. Es entidad exceptuada de la percepción.
   - **Listados:** Padrones de contribuyentes

7. **n11 4086**
   - **Validación:** Si ind_padron = "02" para el "Número de documento de identidad del emisor" en el listado y ind_padron = "02" para el valor del Tag UBL en el listado
   - **Mensaje:** El emisor y el cliente son Agentes de percepción de combustible en la fecha de emisión.
   - **Listados:** Padrones de contribuyentes
## 20. Tipo de documento de identidad del cliente

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /Perception/cac:ReceiverParty/cac:PartyIdentification/cbc:ID@schemeID

### Validaciones

1. **n1 2516**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** Debe indicar tipo de documento.
   - **Listados:** -

2. **n1 2511**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El tipo de documento no es aceptado.
   - **Listados:** Parámetros (006)
## 21. Nombre comercial del cliente

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..1500
- **Tag:** /Perception/cac:ReceiverParty/cac:PartyName/cbc:Name

### Validaciones

1. **an..1500 2911**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El nombre comercial del cliente no cumple con el formato establecido
   - **Listados:** -
## 22. Apellidos y nombres, denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..1500
- **Tag:** /Perception/cac:ReceiverParty/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **an..1500 2134**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe informacion de RegistrationName del receptor del documento
   - **Listados:** -

2. **an..1500 2133**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 1500 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName -  El dato ingresado no cumple con el estandar
   - **Listados:** -
## Domicilio fiscal del cliente. Registro Domicilio fiscal del cliente

- **Condición informática:** -
- **Tipo y longitud:** -
- **Formato / Valor:** -
- **Tag:** -
## 23. Ubigeo

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an6
- **Formato / Valor:** (Catálogo N.° 13)
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:ID

### Validaciones

1. **an6 2917**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL no está en el listado
   - **Mensaje:** Debe corresponder a algún valor válido establecido en el catálogo 13
   - **Listados:** Parámetros (016)
## 24. Dirección completa y detallada

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..100
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:StreetName

### Validaciones

1. **an..100 2919**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La dirección completa y detallada del domicilio fiscal del cliente no cumple con el formato establecido
   - **Listados:** -
## 25. Urbanización

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:CitySubdivisionName

### Validaciones

1. **an..30 2912**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La urbanización del domicilio fiscal del cliente no cumple con el formato establecido
   - **Listados:** -
## 26. Provincia

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:CityName

### Validaciones

1. **an..30 2913**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** La provincia del domicilio fiscal del cliente no cumple con el formato establecido
   - **Listados:** -
## 27. Departamento

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:CountrySubentity

### Validaciones

1. **an..30 2914**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El departamento del domicilio fiscal del cliente no cumple con el formato establecido
   - **Listados:** -
## 28. Distrito

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..30
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cbc:District

### Validaciones

1. **an..30 2915**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a alfanumérico de hasta 30 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** El distrito del domicilio fiscal del cliente no cumple con el formato establecido
   - **Listados:** -
## 29. Código del país de la dirección

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** a2
- **Formato / Valor:** (Catálogo N.° 04)
- **Tag:** /Perception/cac:ReceiverParty/cac:PostalAddress/cac:Country/cbc:IdentificationCode

### Validaciones

1. **a2 2548**
   - **Validación:** Si el Tag UBL existe, el valor es diferente a "PE"
   - **Mensaje:** El valor del país inválido.
   - **Listados:** -
## Datos de la percepción del CPE
## 30. Código del régimen de percepción

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n2
- **Formato / Valor:** (Catálogo N.° 22)
- **Tag:** /Perception/sac:SUNATPerceptionSystemCode

### Validaciones

1. **n2 2602**
   - **Validación:** El valor del Tag UBL no está en el listado
   - **Mensaje:** El régimen percepción enviado no corresponde con su condición de Agente de percepción.
   - **Listados:** Catálogo<br>(022)
## 30. Código del régimen de percepción

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n2

### Validaciones

1. **n2 3327**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01" y el valor del tag es "01" (Percepción Venta Interna) y el regimen del documento relacionado es "02" (Percepción a la adquisición de combustible).<br>Validación no aplica para OSE
   - **Mensaje:** No esta permitido referenciar el Código del régimen de percepción con el regimen del documento relacionado.

2. **n2 3327**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01" y el valor del tag es "02" (Percepción a la adquisión de combustible) y el regimen del documento relacionado es "01" (Percepción Venta Interna).<br>Validación no aplica para OSE
   - **Mensaje:** No esta permitido referenciar el Código del régimen de percepción con el regimen del documento relacionado.
## 31. Porcentaje de percepción

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..4
- **Formato / Valor:** n(1,2)
- **Tag:** /Perception/sac:SUNATPerceptionPercent

### Validaciones

1. **an..4 2603**
   - **Validación:** El valor del Tag UBL es diferente al  Porcentaje de percepción del listado para el "Código del régimen de percepción"
   - **Mensaje:** La tasa de percepción enviada no corresponde con el régimen de percepción.
   - **Listados:** Catálogo<br>(022)
## 32. Observaciones

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..250
- **Tag:** /Perception/cbc:Note

### Validaciones

1. **an..250 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 33. Importe total percibido

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/cbc:TotalInvoiceAmount

### Validaciones

1. **an..15 2669**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en TotalInvoiceAmount debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2667**
   - **Validación:** El valor de Tag UBL es diferente a la suma de "Importe Percibido", sin considerar los tipos de documentos “07” y “40”
   - **Mensaje:** Importe total percibido debe ser igual a la suma de los importes percibidos por cada documento relacionado.
   - **Listados:** -
## 34. Moneda del importe total percibido

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/cbc:TotalInvoiceAmount@currencyID

### Validaciones

1. **an3 2685**
   - **Validación:** El valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Percibido debe ser PEN
   - **Listados:** -
## 35. Importe total cobrado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/sac:SUNATTotalCashed

### Validaciones

1. **an..15 2687**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en SUNATTotalCashed debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2668**
   - **Validación:** El valor de Tag UBL es diferente a la suma de "Importe total a cobrar (neto)" más el "Monto de redondeo del importe total cobrado", sin considerar los tipos de documentos “07” y “40”
   - **Mensaje:** Importe total cobrado debe ser igual a la suma de los importes cobrados por cada documento relacionado.
   - **Listados:** -
## 36. Moneda del importe total cobrado

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATTotalCashed@currencyID

### Validaciones

1. **an3 2690**
   - **Validación:** El valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Cobrado debe ser PEN
   - **Listados:** -
## 37. Monto de redondeo del importe total cobrado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/cbc:PayableRoundingAmount

### Validaciones

1. **an..15 3303**
   - **Validación:** Si existe el tag UBL, el valor absoluto es mayor a 1
   - **Mensaje:** El monto para el redondeo del Importe Total excede el valor permitido
   - **Listados:** -
## 37. Monto de redondeo del importe total cobrado

- **Nivel:** Global
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/cbc:PayableRoundingAmount@currencyID

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
## 38. Tipo de documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference

### Validaciones

1. **3323**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01" y existe más de un (01) documento relacionado.
   - **Mensaje:** Solo se permite 1 documento relacionado cuando el Indicador de emisión excepcional es '01'
   - **Listados:** -
## 38. Tipo de documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cbc:ID@schemeID

### Validaciones

1. **an2 2691**
   - **Validación:** No existe el Tag UBL o es vacio
   - **Mensaje:** El XML no contiene el tag o no existe información del tipo de documento relacionado
   - **Listados:** -

2. **an2 2692**
   - **Validación:** El valor del Tag UBL es diferente a "01", "03", "12", "07", "08", "40"
   - **Mensaje:** El tipo de documento relacionado no es válido
   - **Listados:** -

3. **an2 3324**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01", el valor del "Tipo de documento relacionado" es diferente de '01'.
   - **Mensaje:** Solo se permite '01' para el Tipo de documento relacionado cuando el valor del Indicador de emisión excepcional es '01'
   - **Listados:** -
## 39. Serie y número correlativo del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cbc:ID

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
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "12", el formato del Tag UBL es diferente a:<br>(E001|EB01|((F|P|B)[A-Z0-9]{3})|([0-9]{4}))-(?!0+$)([0-9]{1,8})
   - **Mensaje:** El número de documento relacionado no está permitido o no es valido
   - **Listados:** -

4. **an..13 2609**
   - **Validación:** Si el "Tipo de documento relacionado" es "01", "03", "07" o "08" y el Tag UBL empieza con "E001" o "EB01", el valor del Tag UBL no existe en el listado
   - **Mensaje:** El comprobante electrónico enviado no se encuentra registrado en la SUNAT.
   - **Listados:** Listado de comprobantes de pago electrónicos

5. **an..13 2609**
   - **Validación:** Si el "Tipo de documento relacionado" es "01", "07" o "08" y el Tag UBL empieza con "F", el valor del Tag UBL no existe en el listado
   - **Mensaje:** El comprobante electrónico enviado no se encuentra registrado en la SUNAT.
   - **Listados:** Listado de comprobantes de pago electrónicos

6. **an..13 3228**
   - **Validación:** Si el "Tipo de documento relacionado" es "01", "03", "07" o "08" y el Tag UBL empieza con un número, el valor del Tag UBL no existe en el listado
   - **Mensaje:** El Comprobante de Pago no está autorizado en los Sistemas de la SUNAT.
   - **Listados:** Listado de autorizaciones de comprobantes de pago físicos

7. **an..13 3312**
   - **Validación:** Si no existe el "Indicador de emisión excepcional", el "Tipo de documento relacionado" es '01' y el valor del tag no empieza con número, el documento relacionado tiene 'Indicador de percepción' igual a "1" en el listado
   - **Mensaje:** El documento relacionado tiene monto informado de percepción
   - **Listados:** Listado de autorizaciones de comprobantes de pago físicos

8. **an..13 3328**
   - **Validación:** Si el "Tipo de documento relacionado" es '03' y el valor del tag no empieza con número, el 'Tipo de operación' del documento relacionado es igual a '2001-Operación sujeta a Percepción' o contiene "Monto de la percepción" mayor a '0' (tiene 'Indicador de percepción' igual a "1" en el listado)
   - **Mensaje:** La boleta de venta relacionada tiene monto informado de percepción.
   - **Listados:** Listado de comprobantes de pago electrónicos

9. **an..13 3325**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01", el Tipo de documento relacionado es "01" y el valor del tag no empieza con número, el documento relacionado tiene "Forma de pago" igual a "Crédito" (tiene 'Indicador de forma de pago' igual a "1" en el listado)
   - **Mensaje:** Se permite emitir comprobante de percepción excepcional cuando el documento de referencia es al contado.
   - **Listados:** Listado de autorizaciones de comprobantes de pago físicos

10. **an..13 3329**
   - **Validación:** Si no existe el "Indicador de emisión excepcional", y el "Tipo de documento relacionado" es '01' y el valor del tag no empieza con número, el documento relacionado tiene "Forma de pago" igual a "Contado" (tiene 'Indicador de forma de pago' igual a "0" en el listado)
   - **Mensaje:** Se permite emitir comprobante de percepción (no excepcional) cuando documento de referencia es al crédito o no tiene indicador de forma de pago.
   - **Listados:** Listado de comprobantes de pago electrónicos

11. **an..13 3326**
   - **Validación:** Si el valor del "Indicador de emisión excepcional" es "01", el Tipo de documento relacionado es "01" y la "Serie y número correlativo del documento relacionado" se encuentra referenciado en otro comprobante de percepción excepcional con estado activo.<br>Validación no aplica para OSE
   - **Mensaje:** Solo se permite referenciar siempre y cuando el comprobante de percepción excepcional en el que se referencia al documento relacionado haya sido revertido.
   - **Listados:** Listado de comprobantes de pago electrónicos
## 40. Fecha de emisión del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cbc:IssueDate

### Validaciones

1. **an..10 2610**
   - **Validación:** Si el "Tipo de documento relacionado" es "01", "03", "07" o "08" y la "Serie del documento relacionado" empieza con "E001" o "F" o "B", el valor del Tag UBL es diferente a la fecha de emisión del comprobante del listado
   - **Mensaje:** La fecha de emisión, Importe total del comprobante y la moneda del comprobante electrónico enviado no son los registrados en los Sistemas de SUNAT.
   - **Listados:** Listado de comprobantes de pago electrónicos
## 41. Importe total del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cbc:TotalInvoiceAmount

### Validaciones

1. **an..15 2696**
   - **Validación:** El formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el importe total documento relacionado debe ser numérico mayor a cero
   - **Listados:** -
## 42. Tipo de moneda del documento relacionado

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cbc:TotalInvoiceAmount@currencyID

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## Datos del pago (3)
## 43. Fecha de cobro

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cac:Payment/cbc:PaidDate

### Validaciones

1. **an10 2702**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la fecha de cobro del documento Relacionado
   - **Listados:** -

2. **an10 2659**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es de mes/año (periodo) diferente a otra "Fecha de cobro" en /Perception
   - **Mensaje:** La fecha de cobro de cada documento relacionado deben ser del mismo Periodo (mm/aaaa), asimismo estas fechas podrán ser menores o iguales a la fecha de emisión del comprobante de percepción
   - **Listados:** -

3. **an10 2612**
   - **Validación:** Si el Tag UBL existe, y la "fecha de emision documento relacionado" es del mismo mes/año (periodo) de la "fecha de emision", el valor del Tag UBL es menor a "Fecha de emisión documento relacionado"
   - **Mensaje:** La fecha de cobro debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de percepción o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

4. **an10 2612**
   - **Validación:** Si el Tag UBL existe, y la "fecha de emision documento relacionado" es del mismo mes/año (periodo) de la "fecha de emision", el valor del Tag UBL es mayor a "Fecha de emisión"
   - **Mensaje:** La fecha de cobro debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de percepción o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

5. **an10 2612**
   - **Validación:** Si el Tag UBL existe, y la "fecha de emision documento relacionado" es de diferente mes/año (periodo) de la "fecha de emision", el valor del Tag UBL es menor al primer día del mes de "fecha de emision"
   - **Mensaje:** La fecha de cobro debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de percepción o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -

6. **an10 2612**
   - **Validación:** Si el Tag UBL existe, y la "fecha de emision documento relacionado" es de diferente mes/año (periodo) de la "fecha de emision", el valor del Tag UBL es mayor a "Fecha de emisión"
   - **Mensaje:** La fecha de cobro debe estar entre el primer día calendario del mes al cual corresponde la fecha de emisión del comprobante de percepción o desde la fecha de emisión del comprobante relacionado.
   - **Listados:** -
## 44. Número de cobro

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** n..9
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cac:Payment/cbc:ID

### Validaciones

1. **n..9 2697**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del número de cobro
   - **Listados:** -

2. **n..9 2698**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el formato del Tag UBL es diferente a numérico de hasta 9 dígitos
   - **Mensaje:** El dato ingresado en el número de cobro no es válido
   - **Listados:** -

3. **n..9 2626**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", la "Serie y número correlativo del documento relacionado" concatenado con el valor del Tag se repite en /Perception
   - **Mensaje:** El Nro. de documento con el número de pago ya se encuentra en la Relación de Documentos Relacionados agregados.
   - **Listados:** -
## 45. Importe de cobro sin percepción

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cac:Payment/cbc:PaidAmount

### Validaciones

1. **an..15 2699**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información del Importe del cobro
   - **Listados:** -

2. **an..15 2700**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Importe del cobro debe ser numérico mayor a cero
   - **Listados:** -
## 46. Moneda del importe de cobro sin percepción

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/cac:Payment/cbc:PaidAmount@currencyID

### Validaciones

1. **an3 2607**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el valor del Tag UBL es diferente al "Tipo de moneda del documento relacionado"
   - **Mensaje:** La moneda del importe de cobro debe ser la misma que la del documento relacionado.
   - **Listados:** -
## Datos de la percepción (4)
## 47. Importe percibido

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/sac:SUNATPerceptionAmount

### Validaciones

1. **an..15 2705**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Importe percibido debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de cobro sin percepción" multiplicado por "Porcentaje de percepción" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -

3. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es diferente "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de cobro sin percepción" multiplicado por "Porcentaje de percepción" multiplicado por "Tipo de cambio" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 48. Moneda de importe percibido

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/sac:SUNATPerceptionAmount@currencyID

### Validaciones

1. **an3 2707**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a "PEN"
   - **Mensaje:** El valor de la moneda de importe percibido debe ser PEN
   - **Listados:** -
## 49. Fecha de percepción

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/sac:SUNATPerceptionDate

### Validaciones

1. **an10 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 50. Importe total a cobrar (neto)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/sac:SUNATNetTotalCashed

### Validaciones

1. **an..15 2711**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 12 enteros y 2 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el Monto total a cobrar debe ser numérico mayor a cero
   - **Listados:** -

2. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de cobro sin percepción" más "Importe Percibido" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -

3. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del documento relacionado" es diferente "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "Importe de cobro sin percepción" multiplicado por "Tipo de cambio" más "Importe Percibido" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 51. Moneda del importe total a cobrar (neto)

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/sac:SUNATNetTotalCashed@currencyID

### Validaciones

1. **an3 2713**
   - **Validación:** Si el Tag UBL existe, el valor del Tag UBL es diferente a "PEN"
   - **Mensaje:** El valor de la moneda del Monto total a cobrar debe ser PEN
   - **Listados:** -
## Datos del tipo de cambio (5)
## 52. Moneda de origen para el tipo de cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/cac:ExchangeRate/cbc:SourceCurrencyCode

### Validaciones

1. **an3 2719**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la moneda de referencia para el tipo de cambio
   - **Listados:** -

2. **an3 2749**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07", el valor del Tag UBL es diferente "Tipo de moneda del documento relacionado"
   - **Mensaje:** La moneda de referencia para el tipo de cambio debe ser la misma que la del documento relacionado
   - **Listados:** -
## 53. Moneda de destino para el tipo de cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/cac:ExchangeRate/cbc:TargetCurrencyCode

### Validaciones

1. **an3 2715**
   - **Validación:** Si el tag existe y es diferente de PEN
   - **Mensaje:** El valor de la moneda objetivo para la Tasa de Cambio debe ser PEN
   - **Listados:** -
## 54. Factor aplicado a la moneda de origen para calcular la moneda de destino (Tipo de cambio)

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an..11
- **Formato / Valor:** n(4,6)
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/cac:ExchangeRate/cbc:CalculationRate

### Validaciones

1. **an..11 2721**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información del tipo de cambio
   - **Listados:** -

2. **an..11 2716**
   - **Validación:** Si el Tag UBL existe, el formato del Tag UBL es diferente a decimal positivo de 4 enteros y 6 decimales o es cero (0)
   - **Mensaje:** El dato ingresado en el tipo de cambio debe ser numérico mayor a cero
   - **Listados:** -
## 55. Fecha de tipo de cambio

- **Nivel:** Ítem
- **Condición informática:** C
- **Tipo y longitud:** an10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /Perception/sac:SUNATPerceptionDocumentReference/sac:SUNATPerceptionInformation/cac:ExchangeRate/cbc:Date

### Validaciones

1. **an10 2722**
   - **Validación:** Si "Tipo de documento relacionado" es diferente a "07" y "Tipo de moneda del documento relacionado" es diferente "PEN", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe información de la fecha de cambio
   - **Listados:** -
