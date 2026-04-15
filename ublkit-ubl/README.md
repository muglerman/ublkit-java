# ublkit-ubl

Modulo de modelado documental y serializacion XML UBL 2.1.

## Alcance
- Modelos de dominio para comprobantes y documentos SUNAT.
- Ensambladores para normalizacion y armado de contenido documental.
- Serializadores XML especializados por tipo de documento.

## Modelos principales
- `DocumentoBase`
- `BorradorFactura`
- `BorradorNotaCredito`
- `BorradorNotaDebito`
- `BorradorGuiaRemision`
- `ComunicacionBaja`
- `ResumenDiario`
- `ComprobantePercepcion`
- `ComprobanteRetencion`

## Serializadores XML disponibles
- `SerializadorXmlFactura`
- `SerializadorXmlNotaCredito`
- `SerializadorXmlNotaDebito`
- `SerializadorXmlGuiaRemision`
- `SerializadorXmlComunicacionBaja`
- `SerializadorXmlResumenDiario`
- `SerializadorXmlPercepcion`
- `SerializadorXmlRetencion`

## Modo streaming (StAX) para facturas masivas
- `SerializadorXmlFactura` ahora expone `serializarStreaming(...)` para escenarios con miles de líneas.
- Este modo escribe `InvoiceLine` con StAX (`XMLStreamWriter`) hacia `OutputStream`, reduciendo el pico de memoria frente a DOM puro.
- Recomendado para lotes de alto volumen o alta concurrencia.

## Dependencias
- `ublkit-core`
- `ublkit-catalogs`

## Ejemplo rapido

```java
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;

BorradorFactura factura = new BorradorFactura();
factura.setTipoComprobante("01");
factura.setSerie("F001");
factura.setNumero(1);

SerializadorXmlFactura serializador = new SerializadorXmlFactura();
String xml = serializador.serializar(factura);

// Opcion para alto volumen: salida streaming
String xmlStreaming = serializador.serializarStreaming(factura);
```

## Recomendacion de flujo
1. Construir modelo (`Borrador*` o documento SUNAT).
2. Validar en `ublkit-validation`.
3. Serializar con el `SerializadorXml*` correspondiente.
4. Firmar en `ublkit-sign`.
5. Enviar en `ublkit-gateway`.

## Errores frecuentes
- Usar serializador incorrecto para el tipo de documento.
- Omitir datos obligatorios del modelo y esperar XML valido sin pasar por validacion.

## Checklist de produccion
- Ejecutar validacion previa a serializacion.
- Versionar muestras XML por tipo documental para regresion.
