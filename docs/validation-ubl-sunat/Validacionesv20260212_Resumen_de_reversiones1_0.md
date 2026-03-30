# Validacionesv20260212

## Hoja: Resumen de reversiones1_0

## Registro


### Validaciones

1. **0127**
   - **Mensaje:** El ticket no existe
   - **Listados:** -
## Datos del resumen de reversiones
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
- **Formato / Valor:** RR-<Fecha>-#####
- **Tag:** /VoidedDocuments/cbc:ID

### Validaciones

1. **an..17 2220**
   - **Validación:** El ID del nombre del archivo es diferente al Tag UBL
   - **Mensaje:** El ID debe coincidir con el nombre del archivo
   - **Listados:** -

2. **an..17 2223**
   - **Validación:** El valor del Tag UBL ya ha sido presentado anteriormente
   - **Mensaje:** El archivo ya fue presentado anteriormente
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
   - **Validación:** <<< REVISAR HOJA "FIRMA" >>>
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
## 7. Tipo de Documento del Emisor

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /VoidedDocuments/cac:AccountingSupplierParty/cbc:AdditionalAccountID

### Validaciones

1. **n1 2288**
   - **Validación:** No existe el Tag UBL
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
   - **Validación:** El valor del Tag UBL es vacío
   - **Mensaje:** El tag LineID de VoidedDocumentsLine esta vacío
   - **Listados:** -

2. **n..5 2305**
   - **Validación:** El formato del Tag UBL es numérico positivo hasta 5 dígitos
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
   - **Validación:** El valor del Tag UBL es vacío
   - **Mensaje:** El tag DocumentTypeCode es vacío
   - **Listados:** -

2. **an2 2308**
   - **Validación:** El valor del Tag UBL es diferente a "20" y "40"
   - **Mensaje:** DocumentTypeCode - El valor del tipo de documento es invalido
   - **Listados:** -

3. **an2 2308**
   - **Validación:** El valor del Tag UBL es diferente a '20', '40' y '04'<br>No aplica para SEE-OSE
   - **Mensaje:** DocumentTypeCode - El valor del tipo de documento es invalido
   - **Listados:** -
## 11. Serie del documento dado de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:DocumentSerialID

### Validaciones

1. **an4 2311**
   - **Validación:** El valor del Tag UBL es vacío
   - **Mensaje:** El tag DocumentSerialID es vacío
   - **Listados:** -

2. **an4 2674**
   - **Validación:** Si "Tipo de documento" es 20, el formato del Tag UBL es diferente a:<br>- [R][A-Z0-9]{3}<br>- [0-9]{1,4}
   - **Mensaje:** El dato ingresado  no cumple con el formato de DocumentSerialID, para DocumentTypeCode con valor 20.

3. **an4 2675**
   - **Validación:** Si "Tipo de documento" es 40, el formato del Tag UBL es diferente a:<br>- [P][A-Z0-9]{3}<br>- [0-9]{1,4}
   - **Mensaje:** El dato ingresado  no cumple con el formato de DocumentSerialID, para DocumentTypeCode con valor 40.

4. **an4 2345**
   - **Validación:** Si 'Tipo de documento' es '04', el formato del Tag UBL es diferente a:<br>- [L][A-Z0-9]{3}<br>- [0-9]{1,4}<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** La serie no corresponde al tipo de comprobante

5. **an4 2958**
   - **Validación:** Si 'Tipo de documento' es '04' y la 'Serie del documento dado de baja' no inicia con número y la diferencia entre la fecha de recepción del resumen de reversión  y la 'Fecha de generación del documento dado de baja' es mayor a 7 días<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** El comprobante no puede ser dado de baja por exceder el plazo desde su fecha de recepcion
## 12. Número correlativo del documento dado de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n..8
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:DocumentNumberID

### Validaciones

1. **n..8 2313**
   - **Validación:** El valor del Tag UBL es vacío
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

4. **n..8 2750**
   - **Validación:** El "Tipo de documento" concatenado con "Serie del documento dado de baja" concatenado con el Tag UBL no se encuentra en el listado
   - **Mensaje:** El comprobante que desea revertir no existe.
   - **Listados:** Comprobantes de pagos electrónicos

5. **n..8 2398**
   - **Validación:** El 'Tipo de documento'  es '04' y  'Serie del documento dado de baja' concatenado con el Tag UBL se encuentra en el listado con estado '0'<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** El documento a dar de baja se encuentra rechazado
   - **Listados:** Comprobantes de pagos electrónicos

6. **n..8 2751**
   - **Validación:** El "Tipo de documento" concatenado con "Serie del documento dado de baja" concatenado con el Tag UBL se encuentra en el listado con estado 2
   - **Mensaje:** El comprobante fue informado previamente en una reversión.
   - **Listados:** Comprobantes de pagos electrónicos

7. **n..8 2471**
   - **Validación:** Si 'Tipo de documento' es '04' y la 'Serie del documento dado de baja' concatenado con el Tag UBL tiene pagos registrados<br>Nota: No aplica para SEE-OSE
   - **Mensaje:** La liquidacion de compra a dar de baja no debe tener pagos registrados
   - **Listados:** Comprobantes de pagos electrónicos
## 13. Motivo de baja

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..100
- **Tag:** /VoidedDocuments/sac:VoidedDocumentsLine/sac:VoidReasonDescription

### Validaciones

1. **an..100 2315**
   - **Validación:** El valor del Tag UBL es vacío
   - **Mensaje:** El tag VoidReasonDescription esta vacío
   - **Listados:** -

2. **an..100 4203**
   - **Validación:** La longitud del Tag UBL es menor de 3 caracteres
   - **Mensaje:** El dato ingresado en VoidReasonDescription debe contener información válida
   - **Listados:** -
