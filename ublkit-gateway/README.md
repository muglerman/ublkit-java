# ublkit-gateway

Módulo encargado de la integración con los servicios externos de la SUNAT u OSE.

## Responsabilidad
- Enviar documentos firmados (SOAP y REST).
- Consultar estados de tickets y comprobantes.
- Interpretar el CDR (Constancia de Recepción).
- Resolver endpoints dinámicos por ambiente y tipo.

## Componentes Clave
- `PasarelaSunat`: Fachada principal para envíos y consultas.
- `CredencialesEmpresa`: Información SOL de la empresa.
- `ResultadoEnvio`: Estado del envío (ACEPTADO, RECHAZADO, etc.).
- `LectorCdr`: Utilidad para extraer información de la respuesta SUNAT.

## Dependencias
- `ublkit-core`

## Ejemplo de Uso
```java
PasarelaSunat pasarela = new PasarelaSunatReal();
ResultadoEnvio resultado = pasarela.enviarComprobante(xmlFirmado, "20123456789-01-F001-1.xml", creds, ambiente);
```
