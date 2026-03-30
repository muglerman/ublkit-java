# Naming en UBLKit

Un proyecto maduro comunitario requiere una fuerte estandarización léxica en **Español**.

## Convenciones Principales
- **Sustantivos claros.** Modelos de negocio deben entenderse como sustantivos (Ej: `LineaFactura`, `Moneda`).
- **Nada de nombres ambiguos.** Evitar `Manager`, `Util`, `Helper`, o `Processor`. Reemplazar con roles descifrables (Ej: `SerializadorXmlUbl`, `FirmadorXml`).
- **Comportamiento en métodos:** Deben estar denotados por el verbo `+` intención (Ej. `validar`, `ensamblar`, `firmar`).

## Traducciones Preferentes frente al Inglés
La API e interfaces exponen nombres en español (la infraestructura puede depender de nomenclatura UBL natural):
- `DocumentType` -> `TipoDocumento`
- `EnvironmentType` -> `TipoAmbiente`
- `OperationResult` -> `ResultadoOperacion`
- `InvoiceDraft` -> `BorradorFactura`
- `SignatureResult` -> `ResultadoFirma`
- `GatewayResult` -> `ResultadoEnvio`

## Restricciones Adicionales
- Cero "God classes". Un emisor externo no debe ser al mismo tiempo el que firme el XML y el que parsee una respuesta síncrona SOAP.
- Los enumerados y records son fuertemente incentivados para evitar sobrecarga de pojos anémicos.
