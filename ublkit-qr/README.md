# ublkit-qr

Modulo de generacion de codigo QR SUNAT en Base64 para comprobantes electronicos.

## Alcance
- Construccion de trama QR para documentos UBL.
- Generacion de imagen QR PNG codificada en Base64.
- Soporte directo para Factura, Nota de credito y Nota de debito.

## Componente principal
- `GeneradorQrSunat`

## API
- `generarQrBase64(DocumentoBase documento, String hashDocumento)`

## Formato de trama
La trama se construye en este orden:

```text
RUC|Tipo|Serie|Numero|IGV|Total|Fecha|Hash
```

- Fecha en formato `yyyy-MM-dd`.
- Tipo se infiere segun el documento:
  - Factura/Boleta: desde `getTipoComprobante()`
  - Nota credito: `07`
  - Nota debito: `08`

## Dependencias
- `ublkit-ubl`
- `com.google.zxing:core`
- `com.google.zxing:javase`

## Ejemplo rapido

```java
import com.cna.ublkit.qr.GeneradorQrSunat;

GeneradorQrSunat generador = new GeneradorQrSunat();
String qrBase64 = generador.generarQrBase64(documento, hashDocumento);
```

## Uso tipico en flujo de emision
1. Validar y serializar documento.
2. Firmar XML y obtener digest/hash.
3. Generar QR Base64 con `GeneradorQrSunat`.
4. Enviar QR al render para PDF/HTML.

## Consideraciones
- Si faltan datos en el documento, se completan como cadena vacia en la trama.
- El modulo lanza `RuntimeException` si falla la generacion del QR.

## Errores frecuentes
- Generar QR sin hash de firma cuando el flujo exige trazabilidad completa.
- Asumir soporte de todos los tipos documentales sin validar trama esperada.

## Checklist de produccion
- Verificar con SUNAT/OSE la trama final esperada por tipo de comprobante.
- Agregar pruebas de regresion visual del QR generado.
