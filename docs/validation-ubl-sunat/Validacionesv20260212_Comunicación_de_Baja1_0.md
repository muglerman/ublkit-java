# Validacionesv20260212

## Hoja: Comunicación de Baja1_0

## Registro


### Validaciones

1. **0127**
   - **Mensaje:** El ticket no existe
   - **Listados:** -
## Datos de la comunicación de baja
## 1. Versión del UBL utilizado para establecer el formato XML

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** 2.0
- **Tag:** /VoidedDocuments/cbc:UBLVersionID

### Validaciones

1. **an..10 2075**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **an..10 2074**
   - **Validación:** El valor del Tag UBL es diferente a "2.0"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 2. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** 1.0
- **Tag:** /VoidedDocuments/cbc:CustomizationID

### Validaciones

1. **an..10 2072**
   - **Validación:** El valor del Tag UBL es diferente a "1.0"
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 3. Identificador de la comunicación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..17
- **Formato / Valor:** RA-<Fecha>-#####
- **Tag:** /VoidedDocuments/cbc:ID

### Validaciones

1. **an..17 2220**
   - **Validación:** El ID del nombre del archivo es diferente al Tag UBL
   - **Mensaje:** El ID debe coincidir con el nombre del archivo
   - **Listados:** -

2. **an..17 2324**
   - **Validación:** El valor del Tag UBL ya ha sido presentado anteriormente
   - **Mensaje:** El archivo de comunicacion de baja ya fue presentado anteriormente
   - **Listados:** -
## 4. Fecha de generación de la comunicación

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /VoidedDocuments/cbc:IssueDate

### Validaciones

1. **an..10 2346**
   - **Validación:** La fecha del nombre del archivo es diferente al tag UBL
   - **Mensaje:** La fecha de generación del resumen debe ser igual a la fecha consignada en el nombre del archivo
   - **Listados:** -

2. **an..10 2301**
   - **Validación:** El valor del Tag UBL es mayor a la fecha de envío
   - **Mensaje:** La fecha del IssueDate no debe ser mayor a la fecha de recepción
   - **Listados:** -
## 5. Fecha de generación del documento dado de baja

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /VoidedDocuments/cbc:ReferenceDate

### Validaciones

1. **an..10 2671**
   - **Validación:** El valor del Tag UBL es mayor a "Fecha de generación de la comunicación"
   - **Mensaje:** La fecha de generación de la comunicación/resumen debe ser mayor o igual a la fecha de generación/emisión de los documentos
   - **Listados:** -
## 6. Firma Digital

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..3000
- **Formato / Valor:** -
- **Tag:** -

### Validaciones

1. **an..3000 -**
   - **Validación:** <<< REVISAR HOJA GENERAL "FIRMA" >>>
   - **Mensaje:** -
   - **Listados:** -
## 7. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /VoidedDocuments/cac:AccountingSupplierParty/cbc:CustomerAssignedAccountID

### Validaciones

1. **n11 1034**
   - **Validación:** El RUC del nombre del archivo es diferente al Tag UBL
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **n11 2011**
   - **Validación:** El valor del Tag UBL tiene un ind_condicion igual a "12" en el listado
   - **Mensaje:** El contribuyente no esta habido
   - **Listados:** Contribuyentes
## 7. Tipo de Documento del Emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /VoidedDocuments/cac:AccountingSupplierParty/cbc:AdditionalAccountID

### Validaciones

1. **n1 2288**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag AdditionalAccountID del emisor del documento
   - **Listados:** -

2. **n1 2287**
   - **Validación:** El valor del Tag UBL es diferente de "6" (RUC)
   - **Mensaje:** AdditionalAccountID - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 8. Apellidos y nombres o denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..100
- **Tag:** /VoidedDocuments/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **an..100 2229**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag RegistrationName del emisor del documento
   - **Listados:** -

2. **an..100 2228**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El dato ingresado no cumple con el estandar
   - **Listados:** -
## Datos de Línea
## 9. Número de ítem

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n..5
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/cbc:LineID

### Validaciones

1. **n..5 2307**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El tag LineID de VoidedDocumentsLine esta vacío
   - **Listados:** -

2. **n..5 2305**
   - **Validación:** El formato del Tag UBL es numérico hasta 5 dígitos
   - **Mensaje:** LineID - El dato ingresado no cumple con el estandar
   - **Listados:** -

3. **n..5 2306**
   - **Validación:** El valor del Tag UBL es menor a 1
   - **Mensaje:** LineID - El dato ingresado debe ser correlativo mayor a cero
   - **Listados:** -

4. **n..5 2752**
   - **Validación:** El valor del Tag UBL se repite en el /VoidedDocuments
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 10. Tipo de Documento

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/cbc:DocumentTypeCode

### Validaciones

1. **an2 2309**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El tag DocumentTypeCode es vacío
   - **Listados:** -

2. **an2 2308**
   - **Validación:** El valor del Tag UBL es diferente a "01", "07", "08", "30", "34" y "42"
   - **Mensaje:** DocumentTypeCode - El valor del tipo de documento es invalido
   - **Listados:** -
## 11. Serie del documento dado de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:DocumentSerialID

### Validaciones

1. **an4 2311**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El tag DocumentSerialID es vacío
   - **Listados:** -

2. **an4 2581**
   - **Validación:** Si la 'Serie del documento dado de baja' empieza con 'S' y el 'Número de RUC' pertenece al 'SEE-Empresas supervisadas'<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** No puede dar de baja 'Recibos de servicios publicos' por SEE-Desde los sistemas del contribuyente
   - **Listados:** -

3. **an4 2310**
   - **Validación:** Si "Tipo de documento" es "01"  y el formato del Tag UBL es diferente a<br>- [F][A-Z0-9]{3}<br>- [0-9]{1,4}
   - **Mensaje:** El dato ingresado  no cumple con el patron SERIE
   - **Listados:** -

4. **an4 2310**
   - **Validación:** Si "Tipo de documento" es "30", "34" o "42"  y el formato del Tag UBL es diferente a<br>- [F][A-Z0-9]{3}
   - **Mensaje:** El dato ingresado  no cumple con el patron SERIE
   - **Listados:** -

5. **an4 2310**
   - **Validación:** Si "Tipo de documento" es "07" o "08" y el formato del Tag UBL es diferente a<br>- [F][A-Z0-9]{3}<br>- [0-9]{1,4}
   - **Mensaje:** El dato ingresado  no cumple con el patron SERIE
   - **Listados:** -
## 12. Número correlativo del documento dado de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n..8
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:DocumentNumberID

### Validaciones

1. **n..8 2313**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El tag DocumentNumberID esta vacío
   - **Listados:** -

2. **n..8 2312**
   - **Validación:** El formato del Tag UBL es numérico de hasta 8 dígitos
   - **Mensaje:** El dato ingresado en DocumentNumberID debe ser numerico y como maximo de 8 digitos
   - **Listados:** -

3. **n..8 2348**
   - **Validación:** El "Tipo de documento" concatenado con "Serie del documento dado de baja" concatenado con el Tag UBL se repite en el /VoidedDocuments
   - **Mensaje:** Los documentos informados en el archivo XML se encuentran duplicados
   - **Listados:** -

4. **n..8 2105**
   - **Validación:** Si el 'Tipo de documento' es igual a '01', '07' o '08' y 'Serie del documento de baja' empieza con 'F' o número, el 'Tipo de documento' concatenado con 'Serie del documento dado de baja' concatenado con el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante a dar de baja no se encuentra registrado en SUNAT
   - **Listados:** Comprobantes de pagos electrónicos

5. **n..8 2105**
   - **Validación:** Si el 'Tipo de documento' es igual a '30', '34' o '42' y 'Serie del documento de baja' empieza con 'F', el 'Tipo de documento' concatenado con 'Serie del documento dado de baja' concatenado con el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante a dar de baja no se encuentra registrado en SUNAT
   - **Listados:** Comprobantes de pagos electrónicos

6. **n..8 2398**
   - **Validación:** El 'Tipo de documento' concatenado con 'Serie del documento dado de baja' concatenado con el Tag UBL se encuentra en el listado con estado 0
   - **Mensaje:** El documento a dar de baja se encuentra rechazado
   - **Listados:** Comprobantes de pagos electrónicos

7. **n..8 2323**
   - **Validación:** El 'Tipo de documento' concatenado con 'Serie del documento dado de baja' concatenado con el Tag UBL se encuentra en el listado con estado 2
   - **Mensaje:** Existe documento ya informado anteriormente en una comunicacion de baja
   - **Listados:** Comprobantes de pagos electrónicos

8. **n..8 2957**
   - **Validación:** Si la 'Serie del documento dado de baja' no inicia con número y la diferencia entre la fecha de recepción de la comunicación de baja y la fecha de emisión del documento dado de baja es mayor a 7 días
   - **Mensaje:** El comprobante no puede ser dado de baja por exceder el plazo desde su fecha de emision
   - **Listados:** -

9. **n..8 2375**
   - **Validación:** Si el "Tipo de documento" es '01' (Factura); o, "Tipo de documento" es '07' o '08' y "Serie del documento de baja" empieza con "F" o número; la fecha de emisión del comprobante en el listado es diferente a la "Fecha de generación del documento dado de baja"
   - **Mensaje:** Fecha de emision del comprobante no coincide con la fecha de emision consignada en la comunicación
   - **Listados:** Comprobantes de pagos electrónicos

10. **n..8 2375**
   - **Validación:** Si el "Tipo de documento" es '30' o '34' o '42' y "Serie del documento de baja" empieza con "F", la fecha de emisión del comprobante en el listado es diferente a la "Fecha de generación del documento dado de baja"
   - **Mensaje:** Fecha de emision del comprobante no coincide con la fecha de emision consignada en la comunicación
   - **Listados:** Comprobantes de pagos electrónicos

11. **n..8 2375**
   - **Validación:** Si el "Tipo de documento" es '14' (Servicio Publico); o, "Tipo de documento" es '07' o '08' y "Serie del documento de baja" empieza con "S" o número; la fecha de emisión del comprobante en el listado es diferente a la "Fecha de generación del documento dado de baja"<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** Fecha de emision del comprobante no coincide con la fecha de emision consignada en la comunicación
   - **Listados:** Comprobantes de pagos electrónicos
## 13. Motivo de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..100
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:VoidReasonDescription

### Validaciones

1. **an..100 2315**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El tag VoidReasonDescription esta vacío
   - **Listados:** -

2. **an..100 4203**
   - **Validación:** La longitud del Tag UBL es menor de 3 caracteres
   - **Mensaje:** El dato ingresado en VoidReasonDescription debe contener información válida
   - **Listados:** -
