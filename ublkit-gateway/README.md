# ublkit-gateway

Modulo de integracion con servicios SUNAT/OSE para envio y consulta de comprobantes.

## Alcance
- Envio SOAP sincrono para CPE tradicionales.
- Envio SOAP asincrono para resumenes y bajas.
- Envio REST para GRE con OAuth2.
- Consulta de tickets SOAP y REST.
- Parseo de CDR y mapeo a estados de negocio.

## API principal
- `PasarelaSunat`
  - `enviarComprobante`
  - `enviarRetencionPercepcion`
  - `enviarResumen`
  - `enviarGuiaRemision`
  - `consultarTicketSoap`
  - `consultarTicketRest`

## Implementacion incluida
- `PasarelaSunatDefecto`
- `HttpClienteNativoSoap`
- `HttpClienteNativoRest`
- `ProveedorTokenNativo`
- `ResolvedorEndpoints`
- `LectorCdr`

## Modelos de entrada/salida
- `CredencialesEmpresa`
- `ResultadoEnvio`
- `ResultadoConsulta`
- `EstadoEnvio`
- `ArchivoCdr`
- `ConfiguracionGateway`

## Dependencias
- `ublkit-core`

## Ejemplo rapido

```java
import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.api.PasarelaSunatDefecto;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;

PasarelaSunat pasarela = new PasarelaSunatDefecto();

CredencialesEmpresa cred = new CredencialesEmpresa(
	"20123456789",
	"MODDATOS",
	"moddatos",
	"clientId",
	"clientSecret"
);

ResultadoEnvio envio = pasarela.enviarComprobante(
	xmlFirmado,
	"20123456789-01-F001-1.xml",
	cred,
	TipoAmbiente.BETA
);
```

## Configuracion
- `PasarelaSunatDefecto` acepta `ConfiguracionGateway` para timeout y reintentos.
- Los errores temporales (`IO_ERROR`, `HTTP_5XX`) aplican reintento con backoff exponencial.

## Consideraciones por flujo
- SOAP sincrono: devuelve CDR en la misma respuesta cuando SUNAT procesa de inmediato.
- SOAP asincrono: devuelve ticket, requiere consulta posterior.
- REST GRE: requiere `clientId` y `clientSecret` en credenciales para obtener token.
