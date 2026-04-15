# ublkit-testkit

Modulo de utilidades para pruebas unitarias e integracion sobre flujos UBLKit.

## Alcance
- Fixtures listos para construir documentos de prueba.
- Aserciones de XML y CDR para tests de regresion.
- Simulador de pasarela SUNAT para desacoplar pruebas de red.

## Fixtures disponibles
- `FacturasEjemplo`
- `NotasCreditoEjemplo`
- `NotasDebitoEjemplo`
- `GuiasRemisionEjemplo`
- `ResumenesDiarioEjemplo`
- `ComunicacionesBajaEjemplo`
- `RespuestasSunatSimuladas`

## Aserciones disponibles
- `AssertsXml`
- `AssertsCdr`
- `GoldenXml`

## Simulador
- `SimuladorGateway` implementa `PasarelaSunat` para tests de capa aplicacion.

## Dependencias
- `ublkit-core`
- `ublkit-ubl`
- `ublkit-gateway`
- `junit-jupiter`

## Ejemplo rapido

```java
import com.cna.ublkit.testkit.assertion.AssertsXml;
import com.cna.ublkit.testkit.fixture.FacturasEjemplo;
import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;

BorradorFactura factura = FacturasEjemplo.facturaMinima();
String xml = new SerializadorXmlFactura().serializar(factura);

AssertsXml.assertElementExists(xml, "//Invoice");
AssertsXml.assertXPath(xml, "//ID", "F001-1");
```

## Nota sobre XPath
`AssertsXml` parsea en modo sin namespaces para simplificar pruebas rapidas.
Si necesitas validaciones estrictas con namespace, conviene agregar assertions especificas en tu suite.

## Errores frecuentes
- Asumir que `AssertsXml` valida namespaces XML estrictamente.
- Usar fixtures sin ajustar datos para escenarios limite.

## Checklist de produccion
- Mantener pruebas golden de XML por tipo documental critico.
- Simular respuestas de gateway para escenarios de retry, rechazo y pendiente.
