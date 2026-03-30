# Validacionesv20260212

## Hoja: Resumen Diario1_1

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
## Datos del resumen diario. Datos del resumen diario
## 01. Versión del UBL utilizado para establecer el formato XML

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** "2.0"
- **Tag:** /SummaryDocuments/cbc:UBLVersionID

### Validaciones

1. **an..10 2075**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag o no existe informacion de UBLVersionID
   - **Listados:** -

2. **an..10 2074**
   - **Validación:** El valor del Tag UBL es diferente de "2.0"
   - **Mensaje:** UBLVersionID - La versión del UBL no es correcta
   - **Listados:** -
## 02. Versión de la estructura del documento

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** "1.1"
- **Tag:** /SummaryDocuments/cbc:CustomizationID

### Validaciones

1. **an..10 2072**
   - **Validación:** El valor del Tag UBL es diferente de "1.1"
   - **Mensaje:** CustomizationID - La versión del documento no es la correcta
   - **Listados:** -
## 03. Identificador del resumen

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..17
- **Formato / Valor:** [R][C]-[0-9]{8}-[0-9]{1,5}
- **Tag:** /SummaryDocuments/cbc:ID

### Validaciones

1. **an..17 2220**
   - **Validación:** El valor del Tag UBL es diferente al nombre del archivo
   - **Mensaje:** El ID debe coincidir con el nombre del archivo
   - **Listados:** -

2. **an..17 2223**
   - **Validación:** El valor del Tag UBL ya ha sido presentado anteriormente
   - **Mensaje:** El archivo ya fue presentado anteriormente
   - **Listados:** -
## 04. Fecha de generación del resumen

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /SummaryDocuments/cbc:IssueDate

### Validaciones

1. **an..10 2346**
   - **Validación:** El valor del Tag UBL es diferente a la fecha del nombre del archivo
   - **Mensaje:** La fecha de generación del resumen debe ser igual a la fecha consignada en el nombre del archivo
   - **Listados:** -

2. **an..10 2236**
   - **Validación:** El valor del Tag UBL es mayor que el día de hoy
   - **Mensaje:** La fecha del IssueDate no debe ser mayor a la fecha de recepción
   - **Listados:** -
## 05. Fecha de emisión de los documentos

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** YYYY-MM-DD
- **Tag:** /SummaryDocuments/cbc:ReferenceDate

### Validaciones

1. **an..10 2671**
   - **Validación:** El valor del Tag UBL es mayor a la "Fecha de generación del resumen"
   - **Mensaje:** La fecha de generación de la comunicación/resumen debe ser mayor o igual a la fecha de generación/emisión de los documentos
   - **Listados:** -

2. **an..10 4443**
   - **Validación:** El valor del Tag UBL es mayor a 7 días desde la fecha de recepción del resumen
   - **Mensaje:** El comprobante fue informado fuera del plazo establecido.
   - **Listados:** -
## 06. Firma Digital

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
## 07. Emisor

- **Condición informática:** M
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/cac:AccountingSupplierParty
## 07.1. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n11
- **Tag:** /SummaryDocuments/cac:AccountingSupplierParty/cbc:CustomerAssignedAccountID

### Validaciones

1. **n11 1034**
   - **Validación:** No existe el Tag UBL o es vacío o el valor del Tagl UBL es diferente al RUC del nombre del archivo
   - **Mensaje:** Número de RUC del nombre del archivo no coincide con el consignado en el contenido del archivo XML
   - **Listados:** -

2. **n11 1078**
   - **Validación:** El valor del Tag UBL se encuentra en el padrón de obligados a emitir a través de SEE-OSE<br>Validación no aplica para OSE
   - **Mensaje:** El emisor no se encuentra autorizado a emitir en el SEE-Desde los sistemas del contribuyente
   - **Listados:** -
## 07.1. Número de RUC

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 06)
- **Tag:** /SummaryDocuments/cac:AccountingSupplierParty/cbc:AdditionalAccountID

### Validaciones

1. **n1 2219**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag AdditionalAccountID del emisor del documento
   - **Listados:** -

2. **n1 2218**
   - **Validación:** El valor del Tag UBL es diferente a 6 (RUC)
   - **Mensaje:** AdditionalAccountID - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 07.2. Apellidos y nombres o denominación o razón social

- **Nivel:** Global
- **Condición informática:** M
- **Tipo y longitud:** an..100
- **Tag:** /SummaryDocuments/cac:AccountingSupplierParty/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName

### Validaciones

1. **an..100 2229**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag RegistrationName del emisor del documento
   - **Listados:** -

2. **an..100 2228**
   - **Validación:** El formato del Tag UBL es diferente a alfanumérico de 3 hasta 100 caracteres (se considera cualquier carácter incluido espacio, no se permite ningún otro "whitespace character": salto de línea, tab, fin de línea, etc.)
   - **Mensaje:** RegistrationName - El dato ingresado no cumple con el estandar
   - **Listados:** -
## Linea de documento. Linea de documento

- **Condición informática:** M
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine
## 08. Número de fila

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n..5
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cbc:LineID

### Validaciones

1. **n..5 2238**
   - **Validación:** El formato del Tag UBL es numérico hasta 5 dígitos
   - **Mensaje:** LineID - El dato ingresado no cumple con el estandar
   - **Listados:** -

2. **n..5 2239**
   - **Validación:** El valor del Tag UBL es menor a 1
   - **Mensaje:** LineID - El dato ingresado debe ser correlativo mayor a cero
   - **Listados:** -

3. **n..5 2752**
   - **Validación:** El valor del Tag UBL no puede repetirse en /SummaryDocuments
   - **Mensaje:** El número de ítem no puede estar duplicado.
   - **Listados:** -
## 09. Boleta de venta

- **Condición informática:** M
## 9.1. Serie y número de correlativo del documento

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an…13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cbc:ID

### Validaciones

1. **an…13 2512**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** No existe información de serie o número.
   - **Listados:** -

2. **an…13 2513**
   - **Validación:** Si "Tipo de documento" es 03, 07 o 08, el formato del Tag UBL es diferente:<br>- ([B][A-Z0-9]{3})-(?!0+$)([0-9]{1,8})<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** Dato no cumple con formato de acuerdo al tipo de documento
   - **Listados:** -

3. **an…13 2663**
   - **Validación:** Si el comprobante no existe en el listado y el 'Código de operacion del ítem' es '2' o '3'
   - **Mensaje:** El documento indicado no existe no puede ser modificado
   - **Listados:** Comprobantes de pagos electrónicos

4. **an…13 4204**
   - **Validación:** Si "Tipo de documento" es 03, 07 o 08 y la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado como comprobante de contingencia
   - **Listados:** Autorizaciones de comprobantes contingencia

5. **an…13 3207**
   - **Validación:** Si "Tipo de documento" es 03, 07 o 08 y la serie empieza con número,  el Tag UBL no se encuentra en el listado
   - **Mensaje:** Comprobante físico no se encuentra autorizado
   - **Listados:** Autorizaciones de comprobantes físicos
## 9.2. Tipo de Comprobante

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cbc:DocumentTypeCode

### Validaciones

1. **an2 2242**
   - **Validación:** El Tag UBL es vacío
   - **Mensaje:** El XML no contiene el tag DocumentTypeCode
   - **Listados:** -

2. **an2 2241**
   - **Validación:** El valor del Tag UBL es diferente a 03, 07, 08
   - **Mensaje:** DocumentTypeCode - El valor del tipo de documento es invalido
   - **Listados:** -

3. **an2 2987**
   - **Validación:** Si el comprobante existe en el listado:<br>el comprobante tiene el estado igual a (0 ó 2)
   - **Mensaje:** El comprobante ya fue informado y se encuentra anulado o rechazado.
   - **Listados:** Comprobantes de pago electrónico

4. **an2 2282**
   - **Validación:** Si el comprobante existe en el listado y el 'Código de operación del ítem' es '1'
   - **Mensaje:** Existe documento ya informado anteriormente
   - **Listados:** Comprobantes de pago electrónico

5. **an2 2957**
   - **Validación:** Si la 'Serie del documento' no inicia con número y el 'Código de operación del ítem' es igual a '3':<br>La diferencia entre la fecha de recepción del resumen y la 'Fecha de emisión de los documentos' es mayor 7 días
   - **Mensaje:** El comprobante no puede ser dado de baja por exceder el plazo desde su fecha de emision
   - **Listados:** Comprobantes de pago electrónico

6. **an2 3094**
   - **Validación:** El 'Tipo de Comprobante', 'Serie y número de correlativo del documento' y 'Código de operación del ítem' se repite en otra línea /SummaryDocumentsLine
   - **Mensaje:** El comprobante más "código de operación del ítem" no debe repetirse
   - **Listados:** -

7. **an2 3095**
   - **Validación:** El comprobante es adicionado y modificado en el mismo envio
   - **Mensaje:** El comprobante no debe ser emitido y editado en el mismo envío
   - **Listados:** -

8. **an2 3096**
   - **Validación:** El comprobante es modificado y anulado en el mismo envio
   - **Mensaje:** El comprobante no debe ser editado y anulado en el mismo envío
   - **Listados:** -
## 10. Adquirente o usuario

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AccountingCustomerParty

### Validaciones

1. **2514**
   - **Validación:** Si el campo 'Importe total de la venta' es mayor a 700 nuevos soles y no existe el tag
   - **Mensaje:** No existe información de receptor de documento.
   - **Listados:** -
## 10.1. Número de documento de Identidad

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an20
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID

### Validaciones

1. **an20 2014**
   - **Validación:** Si existe tag de "Adquiriente o usuario", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del número de documento de identidad del receptor del documento
   - **Listados:** -

2. **an20 2017**
   - **Validación:** Si existe tag de "Adquiriente o usuario" y "Tipo de documento de identidad del adquiriente" es "6", el formato del Tag UBL es diferente a numérico de 11 dígitos
   - **Mensaje:** El numero de documento de identidad del receptor debe ser  RUC
   - **Listados:** -

3. **an20 4207**
   - **Validación:** Si existe tag de "Adquiriente o usuario" y "Tipo de documento de identidad del adquiriente" es "1", el formato del Tag UBL es diferente a numérico de 8 dígitos
   - **Mensaje:** El DNI debe tener 8 caracteres numéricos
   - **Listados:** -

4. **an20 4208**
   - **Validación:** Si existe tag de "Adquiriente o usuario" y "Tipo de documento del adquiriente o usuario" es "4" o "7" o "0" o "A" o "B" o "C" o "D" o "E" o "F" o "G", el formato del Tag UBL es diferente a alfanumérico de hasta 20 caracteres (se considera cualquier carácter, no permite 'whitespace character': espacio, salto de línea, fin de línea, tab, etc.)
   - **Mensaje:** El dato ingresado como numero de documento de identidad del receptor no cumple con el formato establecido
   - **Listados:** -
## 10.2. Tipo de documento de Identidad

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AccountingCustomerParty/cbc:AdditionalAccountID

### Validaciones

1. **n1 2015**
   - **Validación:** Si existe tag de "Adquiriente o usuario", no existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag o no existe informacion del tipo de documento de identidad del receptor del documento
   - **Listados:** -

2. **n1 2016**
   - **Validación:** Si existe tag de "Adquiriente o usuario", el valor del Tag UBL es diferente al listado y guión (-)
   - **Mensaje:** El dato ingresado  en el tipo de documento de identidad del receptor no cumple con el estandar o no esta permitido.
   - **Listados:** Parámetros (006)
## 11. Comprobante de referencia

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:BillingReference

### Validaciones

1. **2582**
   - **Validación:** Si existe el nodo y el tipo de comprobante es diferente de '07' y '08'
   - **Mensaje:** Solo se debe incluir el tag de Comprobante de referencia cuando se trata de una nota de credito o debito
   - **Listados:** -
## 11.1. Serie y número de documento de la boleta de venta que modifica

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an…13
- **Formato / Valor:** <Serie>-<Número>
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:BillingReference/cac:InvoiceDocumentReference/cbc:ID

### Validaciones

1. **an…13 2524**
   - **Validación:** Si "Tipo de documento" es '07' o '08' y 'Tipo de operación' es diferente de '3', el Tag UBL es vacio
   - **Mensaje:** Debe indicar el documento afectado por la nota
   - **Listados:** -

2. **an…13 2920**
   - **Validación:** Si "Tipo de documento que modifica" es '12', '16' o '55' y 'Tipo de operación' es diferente de '3', el formato del Tag UBL es diferente a:<br>- [a-zA-Z0-9-]{1,20}-[a-zA-Z0-9-]{1,20}
   - **Mensaje:** Dato no cumple con formato de acuerdo al tipo de documento
   - **Listados:** -

3. **an…13 2920**
   - **Validación:** Si "Tipo de documento que modifica" es '03' y 'Tipo de operación' es diferente de '3', el formato del Tag UBL es diferente a:<br>- ([B][A-Z0-9]{3})-(?!0+$)([0-9]{1,8})<br>- (EB01)-[0-9]{1,8}<br>- [0-9]{1,4}-[0-9]{1,8}
   - **Mensaje:** Dato no cumple con formato de acuerdo al tipo de documento
   - **Listados:** -
## 11.2. Tipo de documento que modifica

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 01)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:BillingReference/cac:InvoiceDocumentReference/cbc:DocumentTypeCode

### Validaciones

1. **an2 2583**
   - **Validación:** Si "Tipo de documento" es '07' o '08' y 'Tipo de operación' es diferente de '3', no existe el Tag UBL
   - **Mensaje:** Debe consignar tipo de documento que modifica
   - **Listados:** -

2. **an2 2513**
   - **Validación:** Si "Tipo de documento" es '07' o '08' y 'Tipo de operación' es diferente de '3', el valor del Tag UBL es diferente a '03', '12', '16' y '55'
   - **Mensaje:** Dato no cumple con formato de acuerdo al tipo de documento
   - **Listados:** -

3. **an2 2988**
   - **Validación:** Si "Tipo de documento que modifica" es "03" y "Serie del documento que modifica" empieza con número, el comprobante de referencia  UBL no se encuentra en el listado
   - **Mensaje:** El comprobante (fisico) a la que hace referencia la nota, no se encuentra autorizado.
   - **Listados:** Autorizaciones de comprobantes físicos

4. **an2 2989**
   - **Validación:** Si "Tipo del documento del documento que modifica" es "03" y "Serie del documento que modifica" empieza con B, el comprobante de referencia  no se encuentra en el listado
   - **Mensaje:** El comprobante (electronico) a la que hace referencia la nota, no se encuentra informado.
   - **Listados:** Comprobantes de pago electrónico

5. **an2 2990**
   - **Validación:** Si "Tipo del documento del documento que modifica" es "03" y "Serie del documento que modifica" empieza con B, el comprobante de referencia se encuentra en el listado con estado "Anulado" o "Rechazado"
   - **Mensaje:** El comprobante (electronico) a la que hace referencia la nota, se encuentra anulado o rechazada.
   - **Listados:** Comprobantes de pago electrónico
## 12. Informacion de percepcion

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference

### Validaciones

1. **2986**
   - **Validación:** Si existe nodo y el tipo de comprobante no es boleta (diferente de 03) o es una operación de modificación (cac:Status/cbc:ConditionCode = 2)
   - **Mensaje:** Solo se acepta informacion de percepcion para nuevas boletas.
   - **Listados:** -

2. **2605**
   - **Validación:** Si existe  informacion de percepcion y "Tipo de documento de identidad del adquiriente" es 6 y el "Numero de documento de identidad del adquiriente"  no está en el listado
   - **Mensaje:** Número de RUC no existe.
   - **Listados:** Contribuyentes

3. **4089**
   - **Validación:** Si existe  informacion de percepcion y existe ind_padron = "03" en el listado para el "Número de documento de identidad del adquiriente" de la misma línea (/SummaryDocuments/sac:SummaryDocumentsLine/cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID)
   - **Mensaje:** La operación con este cliente está excluida del sistema de percepción. Es agente de retención.
   - **Listados:** Contribuyentes

4. **4090**
   - **Validación:** Si existe  informacion de percepcion y existe ind_padron = "04" en el listado para el "Número de documento de identidad del adquiriente" de la misma línea (/SummaryDocuments/sac:SummaryDocumentsLine/cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID)
   - **Mensaje:** La operación con este cliente está excluida del sistema de percepción. Es entidad exceptuada de la percepción.
   - **Listados:** Contribuyentes

5. **4086**
   - **Validación:** Si existe  informacion de percepcion, y existe ind_padron = "02" en el listado para el "Número de documento de identidad del adquiriente" de la misma línea, y existe ind_padron = "02" en el listado para el "Número de RUC" del emisor
   - **Mensaje:** El emisor y el cliente son Agentes de percepción de combustible en la fecha de emisión.
   - **Listados:** Contribuyentes
## 12.1. Regimen de percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n2
- **Formato / Valor:** (Catálogo N.° 22)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/sac:SUNATPerceptionSystemCode

### Validaciones

1. **n2 2517**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** Dato no cumple con formato establecido.
   - **Listados:** Parámetros (019)
## 12.2. Tasa de la percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,3)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/sac:SUNATPerceptionPercent

### Validaciones

1. **an..15 2891**
   - **Validación:** El valor del Tag UBL es diferente a la tasa del listado para el "Regimen de percepción"
   - **Mensaje:** La tasa de percepción no existe en el catálogo
   - **Listados:** Parámetros (019)
## 12.299999999999999. Monto de la percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/cbc:TotalInvoiceAmount

### Validaciones

1. **an..15 2893**
   - **Validación:** El formato del Tag UBL es diferente a númerico de 12 enteros y 2 decimales
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -

2. **an..15 2893**
   - **Validación:** El valor del Tag UBL es menor o igual a cero (0)
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -

3. **an..15 2601**
   - **Validación:** Si el valor del Tag es mayor a cero y  no existe ind_padrón igual a "01" o “02” en el listado para el "Numero de RUC" del emisor
   - **Mensaje:** Senor contribuyente a la fecha no se encuentra registrado ó habilitado con la condición de Agente de percepción.
   - **Listados:** Padrones de Contribuyentes

4. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del comprobante" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "/SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/cbc:TaxableAmount" multiplicado por "Tasa de percepción" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 12.299999999999999. Monto de la percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** A3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/cbc:TotalInvoiceAmount@currencyID

### Validaciones

1. **A3 2685**
   - **Validación:** El valor de la propiedad no existe o es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Percibido debe ser PEN
   - **Listados:** -
## 12.399999999999999. Monto total a cobrar incluida la percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/sac:SUNATTotalCashed

### Validaciones

1. **an..15 2895**
   - **Validación:** El formato del Tag UBL es diferente a númerico de 12 enteros y 2 decimales
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -

2. **an..15 2895**
   - **Validación:** El valor del Tag UBL es menor o igual a cero (0)
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -

3. **an..15 2608**
   - **Validación:** Si "Tipo de moneda del comprobante" es "PEN" y el Tag UBL existe, el valor del Tag UBL es diferente a "/SummaryDocuments/sac:SummaryDocumentsLine/sac:TotalAmount" más "Monto de la percepción" con una tolerancia de más/menos uno (1)
   - **Mensaje:** Los montos de pago, percibidos y montos cobrados consignados para el documento relacionado no son correctos.
   - **Listados:** -
## 12.399999999999999. Monto total a cobrar incluida la percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 02)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/sac:SUNATTotalCashed@currencyID

### Validaciones

1. **an3 2690**
   - **Validación:** El valor del Tag UBL es diferente "PEN"
   - **Mensaje:** El valor de la moneda del Importe total Cobrado debe ser PEN
   - **Listados:** -
## 12.499999999999998. Base imponible percepción

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:SUNATPerceptionSummaryDocumentReference/cbc:TaxableAmount

### Validaciones

1. **an..15 2897**
   - **Validación:** El formato del Tag UBL es diferente a númerico de 12 enteros y 2 decimales
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -

2. **an..15 2897**
   - **Validación:** El valor del Tag UBL es menor o igual a cero (0)
   - **Mensaje:** El valor no cumple con el formato establecido o es menor o igual a cero (0)
   - **Listados:** -
## 13. Código de operación del ítem

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** n1
- **Formato / Valor:** (Catálogo N.° 19)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:Status/cbc:ConditionCode

### Validaciones

1. **n1 2522**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** No existe información del documento del anticipo.
   - **Listados:** -

2. **n1 2896**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El código ingresado como estado del ítem no existe en el catálogo
   - **Listados:** Parámetros (018)
## 14. Importe total de la venta<br>Tipo de moneda del Comprobante

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:TotalAmount

### Validaciones

1. **an..15 2251**
   - **Validación:** El formato del Tag UBL es diferente de decimal de 12 enteros y hasta 2 decimales o menor a cero
   - **Mensaje:** El dato ingresado en TotalAmount debe ser numerico mayor o igual a cero
   - **Listados:** -

2. **an..15 4027**
   - **Validación:** El valor del tag es diferente al resultado del siguiente cálculo:<br>Total valor de venta-operaciones gravadas + Total valor de venta-operaciones inafectas + Total valor de venta-operaciones exoneradas + Total valor de venta-operaciones Exportacion + Sumatoria IGV/IVAP + Sumatoria ISC + Sumatoria otros tributos + Total impuestos a las bolsas plásticas + Sumatoria otros Cargos<br>Utilizar una tolerancia de +/- 5 para realizar la comparación.
   - **Mensaje:** El importe total no coincide con la sumatoria de los valores de venta mas los tributos mas los cargos
   - **Listados:** -
## 14. Importe total de la venta<br>Tipo de moneda del Comprobante

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:TotalAmount@currencyID (Tipo de moneda del comprobante)

### Validaciones

1. **an..15 2071**
   - **Validación:** Si existe algún atributo currencyID en la misma línea  /SummaryDocuments/sac:SummaryDocumentsLine/ con valor diferente a "Tipo de moneda del comprobante" (excepto las monedas contenidas en el tag sac:SUNATPerceptionSummaryDocumentReference/)
   - **Mensaje:** La moneda debe ser la misma en todo el documento. Salvo las percepciones que sólo son en moneda nacional
   - **Listados:** -
## 15. Operaciones gravadas

- **Nivel:** Item
- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment

### Validaciones

1. **-**
   - **Validación:** Solo de corresponder. Sumatoria de valor de venta de las operaciones gravadas con IGV<br>sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID[text()='01']
   - **Mensaje:** -
   - **Listados:** -
## 15.1. Total valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:PaidAmount

### Validaciones

1. **an..15 2255**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag PaidAmount
   - **Listados:** -

2. **an..15 2254**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** PaidAmount - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 15.2. Códigos de tipo de valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 11)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID

### Validaciones

1. **an2 2257**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InstructionID
   - **Listados:** -

2. **an2 2256**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** InstructionID - El dato ingresado no cumple con el estandar
   - **Listados:** Parámetros (017)

3. **an2 2357**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** No debe existir un elemento sac:BillingPayment a nivel de item con el mismo valor de cbc:InstructionID
   - **Listados:** -
## 16. Operaciones exoneradas

- **Nivel:** Item
- **Condición informática:** C
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment

### Validaciones

1. **-**
   - **Validación:** Solo de corresponder. Sumatoria de valor de venta de las operaciones exoneradas con IGV<br>sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID[text()='02']
   - **Mensaje:** -
   - **Listados:** -
## 16.1. Total valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:PaidAmount

### Validaciones

1. **an..15 2255**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag PaidAmount
   - **Listados:** -

2. **an..15 2254**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** PaidAmount - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 16.200000000000003. Códigos de tipo de valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 11)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID

### Validaciones

1. **an2 2257**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InstructionID
   - **Listados:** -

2. **an2 2256**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** InstructionID - El dato ingresado no cumple con el estandar
   - **Listados:** Parámetros (017)

3. **an2 2357**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** No debe existir un elemento sac:BillingPayment a nivel de item con el mismo valor de cbc:InstructionID
   - **Listados:** -
## 17. Operaciones inafectas

- **Condición informática:** C
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment

### Validaciones

1. **-**
   - **Validación:** Solo de corresponder. Sumatoria de valor de venta de las operaciones inafectas con IGV<br>sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID[text()='03']
   - **Mensaje:** -
   - **Listados:** -
## 17.1. Total valor de venta

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:PaidAmount

### Validaciones

1. **an..15 2255**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag PaidAmount
   - **Listados:** -

2. **an..15 2254**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** PaidAmount - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 17.200000000000003. Códigos de tipo de valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 11)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID

### Validaciones

1. **an2 2257**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InstructionID
   - **Listados:** -

2. **an2 2256**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** InstructionID - El dato ingresado no cumple con el estandar
   - **Listados:** Parámetros (017)

3. **an2 2357**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** No debe existir un elemento sac:BillingPayment a nivel de item con el mismo valor de cbc:InstructionID
   - **Listados:** -
## 18. Operaciones Gratuitas

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment

### Validaciones

1. **-**
   - **Validación:** sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID[text()='05']
   - **Mensaje:** -
   - **Listados:** -
## 18.1. Total Valor Venta

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:PaidAmount

### Validaciones

1. **an..15 2255**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag PaidAmount
   - **Listados:** -

2. **an..15 2254**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** PaidAmount - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 18.200000000000003. Códigos de tipo de valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 11)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID

### Validaciones

1. **an2 2257**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InstructionID
   - **Listados:** -

2. **an2 2256**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** InstructionID - El dato ingresado no cumple con el estandar
   - **Listados:** Parámetros (017)

3. **an2 2357**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** No debe existir un elemento sac:BillingPayment a nivel de item con el mismo valor de cbc:InstructionID
   - **Listados:** -
## 19. Operaciones Exportación

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment

### Validaciones

1. **-**
   - **Validación:** sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID[text()='04']
   - **Mensaje:** -
   - **Listados:** -
## 19.1. Total Valor Venta

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:PaidAmount

### Validaciones

1. **an..15 2255**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag PaidAmount
   - **Listados:** -

2. **an..15 2254**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** PaidAmount - El dato ingresado no cumple con el estandar
   - **Listados:** -
## 19.200000000000003. Códigos de tipo de valor de venta

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an2
- **Formato / Valor:** (Catálogo N.° 11)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/sac:BillingPayment/cbc:InstructionID

### Validaciones

1. **an2 2257**
   - **Validación:** No existe el Tag UBL
   - **Mensaje:** El XML no contiene el tag InstructionID
   - **Listados:** -

2. **an2 2256**
   - **Validación:** El Tag UBL no existe en el listado
   - **Mensaje:** InstructionID - El dato ingresado no cumple con el estandar
   - **Listados:** Parámetros (017)

3. **an2 2357**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** No debe existir un elemento sac:BillingPayment a nivel de item con el mismo valor de cbc:InstructionID
   - **Listados:** -
## 20. Sumatoria otros cargos del item

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AllowanceCharge
## 20.1. Indicador de cargo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..5
- **Formato / Valor:** true/false
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AllowanceCharge/cbc:ChargeIndicator

### Validaciones

1. **an..5 2263**
   - **Validación:** El valor del Tag UBL es diferente de "true"
   - **Mensaje:** ChargeIndicator - El dato ingresado no cumple con el estandar
   - **Listados:** -

2. **an..5 2411**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** Ha consignado mas de un elemento cac:AllowanceCharge con el mismo campo cbc:ChargeIndicator
   - **Listados:** -
## 20.200000000000003. Importe total

- **Nivel:** Item
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:AllowanceCharge/cbc:Amount

### Validaciones

1. **an..15 2261**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** cbc:Amount - El dato ingresado no cumple con el estandar
   - **Listados:** -

2. **an..15 2266**
   - **Validación:** El valor del Tag UBL es cero (0)
   - **Mensaje:** Debe indicar cargos mayores o iguales a cero
   - **Listados:** -
## 21. IGV/IVAP

- **Nivel:** Item
- **Condición informática:** M
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal

### Validaciones

1. **2278**
   - **Validación:** Si no existe /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID = "1000" o "1016"
   - **Mensaje:** Debe indicar Información acerca del importe total de IGV/IVAP
   - **Listados:** -
## 21.1. Total IGV/IVAP

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cbc:TaxAmount

### Validaciones

1. **an..15 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -

2. **an..15 4019**
   - **Validación:** Si Código de tributo es "1000", y "Tipo de comprobante" es igual a "03", y el valor del tag es mayor a 0 y el valor del tag es diferente de ("Total valor de venta - operaciones gravadas" + "Sumatoria ISC") x TASA VIGENTE A LA FECHA DE EMISION (18.00 o 10.50) con una tolerancia de +/-5<br>Nota: Dado que la tasa vigente del IGV es de 10.5 % y 18%, la validación debe cumplir realizando el cálculo haciendo uso de alguna de las dos tasas
   - **Mensaje:** El calculo del IGV no es correcto
   - **Listados:** -

3. **an..15 4019**
   - **Validación:** Si Código de tributo es "1000", y "Tipo de comprobante" es diferente de "03" y el valor del tag es mayor a 0 y el valor del tag es diferente de ("Total valor de venta - operaciones gravadas" + "Sumatoria ISC") x TASA VIGENTE A LA FECHA DE EMISION (18.00, 10.50 o 10.00) con una tolerancia de +/-5<br>Nota: Dado que la tasa del IGV del Comprobante de referencia puede ser 10.00%, 10.5 % y 18% debe realizar el cálculo haciendo uso de alguna de ellas.
   - **Mensaje:** El calculo del IGV no es correcto
   - **Listados:** -

4. **an..15 4302**
   - **Validación:** Si Código de tributo es "1016" y el valor del tag es mayor a 0 y el valor del tag es diferente de ("Total valor de venta - operaciones gravadas") x TASA VIGENTE A LA FECHA DE EMISION  con una tolerancia de +/-5
   - **Mensaje:** El importe del IVAP no corresponden al determinado por la informacion consignada.
   - **Listados:** -
## 21.1. Total IGV/IVAP

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount

### Validaciones

1. **an..15 2344**
   - **Validación:** El valor del Tag UBL es diferente al Tag anterior
   - **Mensaje:** El XML no contiene el tag cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount
   - **Listados:** -
## 21.200000000000003. Código de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID

### Validaciones

1. **an4 2269**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme ID de Información acerca del importe total de un tipo particular de impuesto
   - **Listados:** -

2. **an4 2268**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Parámetros (005)

3. **an4 2355**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** Debe consignar solo un elemento cac:TaxTotal a nivel de item por codigo de tributo
   - **Listados:** -
## 21.300000000000004. Nombre de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name

### Validaciones

1. **an..10 2271**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme Name de impuesto
   - **Listados:** -

2. **an..10 2276**
   - **Validación:** Si "Código de tributo" es 1000, el valor del Tag UBL es diferente a "IGV"
   - **Mensaje:** Si el codigo de tributo es 1000, el nombre del tributo debe ser IGV
   - **Listados:** -

3. **an..10 3051**
   - **Validación:** Si "Código de tributo" es 1016, el valor del Tag UBL es diferente a "IVAP"
   - **Mensaje:** Nombre de tributo no corresponde al código de tributo de la linea.
   - **Listados:** -
## 21.400000000000006. Código internacional de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** Parámetros (005)
## 22. ISC

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal

### Validaciones

1. **-**
   - **Validación:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID = 2000
   - **Mensaje:** -
   - **Listados:** -
## 22.1. Total ISC

- **Nivel:** Ítem
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cbc:TaxAmount

### Validaciones

1. **an..15 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -
## 22.1. Total ISC

- **Nivel:** Ítem
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount

### Validaciones

1. **an..15 2344**
   - **Validación:** El valor del Tag UBL es diferente al Tag anterior
   - **Mensaje:** El XML no contiene el tag cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount
   - **Listados:** -
## 22.200000000000003. Código de tributo

- **Nivel:** Ítem
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID

### Validaciones

1. **an4 2269**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme ID de Información acerca del importe total de un tipo particular de impuesto
   - **Listados:** -

2. **an4 2268**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Parámetros (005)

3. **an4 2355**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** Debe consignar solo un elemento cac:TaxTotal a nivel de item por codigo de tributo
   - **Listados:** -
## 22.300000000000004. Nombre de tributo

- **Nivel:** Ítem
- **Tipo y longitud:** an..10
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name

### Validaciones

1. **an..10 2275**
   - **Validación:** Si "Código de tributo" es 2000, el valor del Tag UBL es diferente a "ISC"
   - **Mensaje:** Si el codigo de tributo es 2000, el nombre del tributo debe ser ISC
   - **Listados:** -
## 22.400000000000006. Código internacional de tributo

- **Nivel:** Ítem
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 23. Otros tributos

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal

### Validaciones

1. **-**
   - **Validación:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID = 9999
   - **Mensaje:** -
   - **Listados:** -
## 23.1. Total Otros tributos

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cbc:TaxAmount

### Validaciones

1. **an..15 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -
## 23.1. Total Otros tributos

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount

### Validaciones

1. **an..15 2344**
   - **Validación:** El valor del Tag UBL es diferente al Tag anterior
   - **Mensaje:** El XML no contiene el tag cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount
   - **Listados:** -
## 23.200000000000003. Código de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID

### Validaciones

1. **an4 2269**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme ID de Información acerca del importe total de un tipo particular de impuesto
   - **Listados:** -

2. **an4 2268**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Parámetros (005)

3. **an4 2355**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** Debe consignar solo un elemento cac:TaxTotal a nivel de item por codigo de tributo
   - **Listados:** -
## 23.300000000000004. Nombre de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name

### Validaciones

1. **an..10 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 23.400000000000006. Código internacional de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 24. Impuesto a las bolsas plásticas

- **Condición informática:** C
- **Formato / Valor:** Nodo
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal

### Validaciones

1. **-**
   - **Validación:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID = 7152
   - **Mensaje:** -
   - **Listados:** -
## 24.1. Total ICBPER

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cbc:TaxAmount

### Validaciones

1. **an..15 2048**
   - **Validación:** El formato del Tag UBL es diferente de decimal positivo de 12 enteros y hasta 2 decimales y diferente de cero
   - **Mensaje:** El dato ingresado en TaxAmount no cumple con el formato establecido
   - **Listados:** -
## 24.1. Total ICBPER

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..15
- **Formato / Valor:** n(12,2)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount

### Validaciones

1. **an..15 2344**
   - **Validación:** El valor del Tag UBL es diferente al Tag anterior
   - **Mensaje:** El XML no contiene el tag cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount
   - **Listados:** -
## 24.2. Código de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an4
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID

### Validaciones

1. **an4 2269**
   - **Validación:** No existe el Tag UBL o es vacío
   - **Mensaje:** El XML no contiene el tag TaxScheme ID de Información acerca del importe total de un tipo particular de impuesto
   - **Listados:** -

2. **an4 2268**
   - **Validación:** El valor del Tag UBL es diferente al listado
   - **Mensaje:** El codigo del tributo es invalido
   - **Listados:** Parámetros (005)

3. **an4 2355**
   - **Validación:** El valor del Tag UBL se repite en el /SummaryDocuments/sac:SummaryDocumentsLine
   - **Mensaje:** Debe consignar solo un elemento cac:TaxTotal a nivel de item por codigo de tributo
   - **Listados:** -
## 24.3. Nombre de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an..10
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:Name

### Validaciones

1. **an..10 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
## 24.4. Código internacional de tributo

- **Nivel:** Ítem
- **Condición informática:** M
- **Tipo y longitud:** an3
- **Formato / Valor:** (Catálogo N.° 05)
- **Tag:** /SummaryDocuments/sac:SummaryDocumentsLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:TaxTypeCode

### Validaciones

1. **an3 -**
   - **Validación:** <<< SIN VALIDACIÓN >>>
   - **Mensaje:** -
   - **Listados:** -
