# Validacionesv20260212

## Hoja: CDR-OSE-Comprobante

## 1. Número de versión de UBL

- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** 2.1
- **Tag:** /ApplicationResponse/cbc:UBLVersionID

### Validaciones

1. **OBSERV 2111**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID

2. **OBSERV 2110**
   - **Validación:** El valor del tag es diferente de "2.1"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
## 2. Número de versión del CDR OSE

- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** 1.0
- **Tag:** /ApplicationResponse/cbc:CustomizationID

### Validaciones

1. **OBSERV 2113**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de CustomizationID

2. **OBSERV 2112**
   - **Validación:** El valor del tag es diferente de "1.0"
   - **Mensaje:** CustomizationID - La version del documento no es correcta
## 3. Número de autorización del comprobante (UUID)

- **Condición informática:** M
- **Tipo y longitud:** an..36
- **Tag:** /ApplicationResponse/cbc:ID

### Validaciones

1. **OBSERV 1002**
   - **Validación:** El valor del tag es vacío
   - **Mensaje:** El XML no contiene informacion en el tag ID

2. **OBSERV 2803**
   - **Validación:** El valor del tag no cumple con:<br>Estructura: 8-4-4-4-12 (hexadecimal)
   - **Mensaje:** ID - No cumple con el formato UUID
## 4. Fecha de recepción del comprobante por OSE

- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /ApplicationResponse/cbc:IssueDate

### Validaciones

1. **OBSERV 1009**
   - **Validación:** El formato del tag es diferente de YYYY-MM-DD
   - **Mensaje:** IssueDate - El dato ingresado  no cumple con el patrón YYYY-MM-DD

2. **OBSERV 2804**
   - **Validación:** El valor del tag es mayor a la fecha de recepción en SUNAT
   - **Mensaje:** La fecha de recepcion del comprobante por ose es mayor a la fecha de recepcion de SUNAT

3. **OBSERV 2876**
   - **Validación:** Para Factura, Boleta, Notas y DAE-Operador y DAE-Adquirente:<br>- La fecha de recepción es menor a la fecha de emisión del comprobante enviado menos dos días<br>Para resto de documentos:<br>- La fecha de recepción es menor a la fecha de emisión del comprobante enviado
   - **Mensaje:** La fecha de recepción del comprobante por OSE es inconsistente con respecto a la fecha de emisión del comprobante

4. **OBSERV 2950**
   - **Validación:** Si tipo de comprobante es Factura,  Retenciones, Percepciones, DAE-Operador y DAE-Adquirente y serie no inicia en número:<br>La diferencia entre la 'Fecha de recepción del comprobante por OSE' y la 'Fecha de emisión del comprobante' es mayor al límite del listado
   - **Mensaje:** El comprobante ha sido presentado fuera de plazo

5. **OBSERV 2950**
   - **Validación:** Si tipo de comprobante es Boleta y serie no inicia en número:<br>La diferencia entre la 'Fecha de recepción del comprobante por OSE' y la 'Fecha de emisión del comprobante' es mayor a 5 días
   - **Mensaje:** El comprobante ha sido presentado fuera de plazo

6. **OBSERV 2950**
   - **Validación:** Si tipo de comprobante es Nota de Crédito o Nota de Débito y serie no inicia en número:<br>Si serie no empieza con "B":<br>La diferencia entre la 'Fecha de recepción del comprobante por OSE' y la 'Fecha de emisión del comprobante' es mayor al límite del listado<br>Si serie empieza con "B":<br>La diferencia entre la 'Fecha de recepción del comprobante por OSE' y la 'Fecha de emisión del comprobante' es mayor a 5 días
   - **Mensaje:** El comprobante ha sido presentado fuera de plazo
## 5. Hora de recepción del comprobante por OSE

- **Condición informática:** M
- **Tipo y longitud:** an..12
- **Formato / Valor:** hh:mm:ss.sssss
- **Tag:** /ApplicationResponse/cbc:IssueTime

### Validaciones

1. **OBSERV 2805**
   - **Validación:** No existe el tag
   - **Mensaje:** El XML no contiene el tag IssueTime
## 5. Hora de recepción del comprobante por OSE

- **Tipo y longitud:** an..12
- **Formato / Valor:** hh:mm:ss.sssss
- **Tag:** /ApplicationResponse/cbc:IssueTime

### Validaciones

1. **OBSERV 2806**
   - **Validación:** El valor del tag no cumple con el formato
   - **Mensaje:** IssueTime - El dato ingresado  no cumple con el patrón hh:mm:ss.sssss
## 6. Fecha de comprobación del comprobante (OSE)

- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /ApplicationResponse/cbc:ResponseDate

### Validaciones

1. **ERROR 2807**
   - **Validación:** No existe el tag
   - **Mensaje:** El XML no contiene el tag ResponseDate

2. **ERROR 2808**
   - **Validación:** El valor del tag no cumple con el formato
   - **Mensaje:** ResponseDate - El dato ingresado  no cumple con el patrón YYYY-MM-DD

3. **OBSERV 2809**
   - **Validación:** El valor del tag es menor a la 'Fecha de recepción del comprobante por OSE'
   - **Mensaje:** La fecha de recepcion del comprobante por ose, no debe de ser mayor a la fecha de comprobacion del ose

4. **OBSERV 2810**
   - **Validación:** El valor del tag es mayor a la fecha de recepción en SUNAT (TODAY)
   - **Mensaje:** La fecha de comprobacion del comprobante en OSE no puede ser mayor a la fecha de recepcion en SUNAT.

5. **OBSERV 4196**
   - **Validación:** La fecha de recepción en SUNAT es mayor a 1 hora respecto a la fecha de comprobación por OSE<br>(se compara la fecha y hora de comprobación OSE contra la fecha y hora de procesamiento )
   - **Mensaje:** La fecha de recepción en SUNAT es mayor a 1 hora(s) respecto a la fecha de comprobación por OSE
## 7. Hora de comprobación del comprobante (OSE)

- **Condición informática:** M
- **Tipo y longitud:** an..12
- **Formato / Valor:** hh:mm:ss.sssss
- **Tag:** /ApplicationResponse/cbc:ResponseTime

### Validaciones

1. **OBSERV 2811**
   - **Validación:** No existe el tag
   - **Mensaje:** El XML no contiene el tag ResponseTime

2. **OBSERV 2812**
   - **Validación:** El valor del tag no cumple con el formato
   - **Mensaje:** ResponseTime - El dato ingresado  no cumple con el patrón hh:mm:ss.sssss
## 8. Número de documento de identificación del que envía el CPE (emisor o PSE)

- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /ApplicationResponse/cac:SenderParty/cac:PartyLegalEntity/cbc:CompanyID

### Validaciones

1. **OBSERV 2813**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Número de documento de identificación del que envía el CPE (emisor o PSE)

2. **OBSERV 2814**
   - **Validación:** El formato del tag es diferente de alfanumérico de hasta 15 caracteres
   - **Mensaje:** El valor ingresado como Número de documento de identificación del que envía el CPE (emisor o PSE) es incorrecto
## 9. Tipo de documento de identidad del que envía el CPE (emisor o PSE)

- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** Catálogo 06
- **Tag:** /ApplicationResponse/cac:SenderParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeID

### Validaciones

1. **OBSERV 2816**
   - **Validación:** No existe el atributo schemeID
   - **Mensaje:** El XML no contiene el atributo schemeID o no existe información del Tipo de documento de identidad del que envía el CPE (emisor o PSE)

2. **OBSERV 2817**
   - **Validación:** El valor del atributo es diferente de '6'
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del que envía el CPE (emisor o PSE) es incorrecto
## 9. Tipo de documento de identidad del que envía el CPE (emisor o PSE)

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:SenderParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeAgencyName

### Validaciones

1. **OBSERV 2818**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo schemeAgencyName o no existe información del Tipo de documento de identidad del que envía el CPE (emisor o PSE)

2. **OBSERV 2819**
   - **Validación:** El valor del atributo es diferente de "PE:SUNAT"
   - **Mensaje:** El valor ingresado en el atributo schemeAgencyName del Tipo de documento de identidad del que envía el CPE (emisor o PSE) es incorrecto
## 9. Tipo de documento de identidad del que envía el CPE (emisor o PSE)

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:SenderParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeURI

### Validaciones

1. **OBSERV 2820**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo schemeURI o no existe información del Tipo de documento de identidad del que envía el CPE (emisor o PSE)

2. **OBSERV 2821**
   - **Validación:** El valor del atributo es diferente de "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo6"
   - **Mensaje:** El valor ingresado en el atributo schemeURI del Tipo de documento de identidad del que envía el CPE (emisor o PSE) es incorrecto
## 10. Número de documento de identificación del OSE

- **Condición informática:** M
- **Tipo y longitud:** an..11
- **Tag:** /ApplicationResponse/cac:ReceiverParty/cac:PartyLegalEntity/cbc:CompanyID

### Validaciones

1. **ERROR 2822**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Número de documento de identificación del OSE

2. **ERROR 2823**
   - **Validación:** El formato del tag es diferente de numérico de 11 dígitos
   - **Mensaje:** El valor ingresado como Número de documento de identificación del OSE es incorrecto

3. **OBSERV 2824**
   - **Validación:** El certificado digital con el que se firma el CDR OSE no corresponde al RUC del OSE
   - **Mensaje:** El certificado digital con el que se firma el CDR OSE no corresponde con el RUC del OSE informado

4. **ERROR 2825**
   - **Validación:** El número de RUC no corresponde a un OSE registrado en el padrón
   - **Mensaje:** El Número de documento de identificación del OSE informado no esta registrado en el padron.

5. **OBSERV 2874**
   - **Validación:** El OSE no se encuentra vinculado al Emisor del comprobante, a la fecha de recepción en SUNAT.<br>La relación OSE y emisor, se considera vigente hasta el 7mo día calendario del mes siguiente de solicitado la baja.
   - **Mensaje:** El Número de documento de identificación del OSE informado no se encuentra vinculado al emisor del comprobante en la fecha de comprobación.
## 11. Tipo de documento de identidad del OSE

- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** Catálogo 06
- **Tag:** /ApplicationResponse/cac:ReceiverParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeID

### Validaciones

1. **OBSERV 2826**
   - **Validación:** No existe el atributo schemeID o es vacío
   - **Mensaje:** El XML no contiene el atributo schemeID o no existe información del Tipo de documento de identidad del OSE

2. **OBSERV 2827**
   - **Validación:** El valor del atributo es diferente de '6'
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del OSE es incorrecto
## 11. Tipo de documento de identidad del OSE

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:ReceiverParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeAgencyName

### Validaciones

1. **OBSERV 2828**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo schemeAgencyName o no existe información del Tipo de documento de identidad del OSE

2. **OBSERV 2829**
   - **Validación:** El valor del atributo es diferente de "PE:SUNAT"
   - **Mensaje:** El valor ingresado en el atributo schemeAgencyName del Tipo de documento de identidad del OSE es incorrecto
## 11. Tipo de documento de identidad del OSE

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:ReceiverParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeURI

### Validaciones

1. **OBSERV 2830**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo schemeURI o no existe información del Tipo de documento de identidad del OSE

2. **OBSERV 2831**
   - **Validación:** El valor del atributo es diferente de "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo6"
   - **Mensaje:** El valor ingresado en el atributo schemeURI del Tipo de documento de identidad del OSE es incorrecto
## 12. Código de Respuesta

- **Condición informática:** M
- **Tipo y longitud:** n1
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:ResponseCode

### Validaciones

1. **OBSERV 2832**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Código de Respuesta

2. **OBSERV 2833**
   - **Validación:** El valor del tag es diferente de '0' (Valor fijo: '0', indica que el documento electrónico fue aceptado)
   - **Mensaje:** El valor ingresado como Código de Respuesta es incorrecto
## 12. Código de Respuesta

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:ResponseCode/@listAgencyName

### Validaciones

1. **OBSERV 2834**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo listAgencyName o no existe información del Código de Respuesta

2. **OBSERV 2835**
   - **Validación:** El valor del atributo es diferente de "PE:SUNAT"
   - **Mensaje:** El valor ingresado en el atributo listAgencyName del Código de Respuesta es incorrecto
## 13. Descripción de la Respuesta

- **Condición informática:** M
- **Tipo y longitud:** an..250
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:Description

### Validaciones

1. **OBSERV 2836**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de la Descripción de la Respuesta

2. **OBSERV 2837**
   - **Validación:** El valor del tag tiene más de 250 caracteres
   - **Mensaje:** El valor ingresado como Descripción de la Respuesta es incorrecto
## 14. Código de observación

- **Condición informática:** C
- **Tipo y longitud:** n4
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cac:Status/cbc:StatusReasonCode

### Validaciones

1. **OBSERV 2838**
   - **Validación:** El valor del tag no cumple con el formato de numérico de 4 dígitos
   - **Mensaje:** El valor ingresado como Código de observación es incorrecto

2. **OBSERV 2844**
   - **Validación:** No existe el tag
   - **Mensaje:** No se encontro el tag cbc:StatusReasonCode cuando ingresó la Descripción de la observación
## 14. Código de observación

- **Condición informática:** C
- **Tipo y longitud:** n4
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cac:Status/cbc:StatusReasonCode/@listURI

### Validaciones

1. **OBSERV 2839**
   - **Validación:** Si existe el 'Código de observación' (cbc:Status ReasonCode) y no existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo listURI o no existe información del Código de observación

2. **OBSERV 2840**
   - **Validación:** El valor del atributo es diferente de "urn:pe:gob:sunat:cpe:see:gem:codigos:codigoretorno"
   - **Mensaje:** El valor ingresado en el atributo listURI del Código de observación es incorrecto
## 15. Descripción de la observación

- **Condición informática:** C
- **Tipo y longitud:** an..1000
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:Response/cac:Status/cbc:StatusReason

### Validaciones

1. **OBSERV 2841**
   - **Validación:** Si existe el 'Código de observación' (cbc:Status ReasonCode) y no existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de la Descripción de la observación

2. **OBSERV 2842**
   - **Validación:** Si existe el 'Código de observación' (cbc:Status ReasonCode) y el valor del tag contiene más de 1000 caracteres
   - **Mensaje:** El valor ingresado como Descripción de la observación es incorrecto

3. **OBSERV 2843**
   - **Validación:** Si existe más de un tag
   - **Mensaje:** Se ha encontrado mas de una Descripción de la observación, tag cac:Response/cac:Status/cbc:StatusReason
## 16. Serie y número del comprobante

- **Condición informática:** M
- **Tipo y longitud:** an..13
- **Formato / Valor:** ####-########
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:ID

### Validaciones

1. **ERROR 2845**
   - **Validación:** Existe más de un tag  cac:DocumentReference
   - **Mensaje:** El XML contiene mas de un elemento cac:DocumentReference

2. **ERROR 2846**
   - **Validación:** El tag es vacío
   - **Mensaje:** El XML no contiene informacion en el tag cac:DocumentReference/cbc:ID

3. **ERROR 1001**
   - **Validación:** El valor del tag no cumple con el formato <Serie>-<Número>
   - **Mensaje:** ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante

4. **ERROR 2848**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Serie y número del comprobante no corresponde con el del comprobante
## 17. Fecha de emisión del comprobante

- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:IssueDate

### Validaciones

1. **OBSERV 2849**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de la Fecha de emisión del comprobante

2. **OBSERV 1009**
   - **Validación:** El valor del tag no cumple con el formato YYYY-MM-DD
   - **Mensaje:** IssueDate - El dato ingresado  no cumple con el patron YYYY-MM-DD

3. **OBSERV 2851**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Fecha de emisión del comprobante no corresponde con el del comprobante
## 18. Hora de emisión del comprobante

- **Condición informática:** M
- **Tipo y longitud:** an..12
- **Formato / Valor:** hh:mm:ss.sssss
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:IssueTime

### Validaciones

1. **OBSERV 2852**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información de la Hora de emisión del comprobante

2. **OBSERV 2853**
   - **Validación:** El valor del tag no cumple con el formato
   - **Mensaje:** El valor ingresado como Hora de emisión del comprobante no cumple con el patrón hh:mm:ss.sssss

3. **OBSERV 2854**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Hora de emisión del comprobante no corresponde con el del comprobante
## 19. Tipo de comprobante

- **Condición informática:** M
- **Tipo y longitud:** n2
- **Formato / Valor:** Catálogo 01
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:DocumentTypeCode

### Validaciones

1. **OBSERV 2855**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Tipo de comprobante

2. **OBSERV 2856**
   - **Validación:** El valor del tag no corresponde a un tipo de comprobante válido
   - **Mensaje:** El valor ingresado como Tipo de comprobante es incorrecto

3. **OBSERV 2857**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Tipo de comprobante no corresponde con el del comprobante
## 20. Hash del comprobante

- **Condición informática:** M
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cac:Attachment/cac:ExternalReference/cbc:DocumentHash

### Validaciones

1. **OBSERV 2858**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Hash del comprobante

2. **OBSERV 2859**
   - **Validación:** El valor del tag no cumple con el formato de mínimo 3 caracteres y hasta una longitud máxima de 250 caracteres
   - **Mensaje:** El valor ingresado como Hash del comprobante es incorrecto

3. **OBSERV 2860**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Hash del comprobante no corresponde con el del comprobante
## 21. Número de documento de identificación del emisor

- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:IssuerParty/cac:PartyLegalEntity/cbc:CompanyID

### Validaciones

1. **ERROR 2861**
   - **Validación:** No existe el tag o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Número de documento de identificación del emisor

2. **ERROR 2862**
   - **Validación:** El formato del tag es diferente de alfanumérico de hasta 15 caracteres
   - **Mensaje:** El valor ingresado como Número de documento de identificación del emisor es incorrecto

3. **ERROR 2863**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Número de documento de identificación del emisor no corresponde con el del comprobante

4. **OBSERV 2873**
   - **Validación:** Validar solo si el envío es realizado por un PSE:<br>El PSE no se encuentra vinculado al Emisor del comprobante, a la fecha de comprobación.<br>La relación PSE y emisor, se considera vigente hasta el 7mo día calendario del mes siguiente de solicitado la baja.
   - **Mensaje:** El PSE informado no se encuentra vinculado con el  emisor del comprobante en la fecha de comprobación.
## 22. Tipo de documento de identidad del emisor

- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** Catálogo 06
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:IssuerParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeID

### Validaciones

1. **OBSERV 2864**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo o no existe información del Tipo de documento de identidad del emisor

2. **OBSERV 2865**
   - **Validación:** El valor del atributo es  diferente al catálogo
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del emisor es incorrecto

3. **OBSERV 2866**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del emisor no corresponde con el del comprobante
## 23. Número de documento de identificación del receptor

- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:RecipientParty/cac:PartyLegalEntity/cbc:CompanyID

### Validaciones

1. **OBSERV 2867**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe información del Número de documento de identificación del receptor

2. **OBSERV 2869**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Número de documento de identificación del receptor no corresponde con el del comprobante
## 24. Tipo de documento de identidad del receptor

- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** Catálogo 06
- **Tag:** /ApplicationResponse/cac:DocumentResponse/cac:RecipientParty/cac:PartyLegalEntity/cbc:CompanyID/@schemeID

### Validaciones

1. **OBSERV 2870**
   - **Validación:** No existe el atributo o es vacío
   - **Mensaje:** El XML no contiene el atributo o no existe información del Tipo de documento de identidad del receptor

2. **OBSERV 2871**
   - **Validación:** El valor del atributo es  diferente al catálogo y guion "-"
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del receptor es incorrecto

3. **OBSERV 2872**
   - **Validación:** Valor no corresponde con el consignado en el comprobante
   - **Mensaje:** El valor ingresado como Tipo de documento de identidad del receptor no corresponde con el del comprobante
