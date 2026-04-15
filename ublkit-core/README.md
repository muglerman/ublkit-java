# ublkit-core

Modulo base del ecosistema UBLKit.
Define contratos, enums, value objects y errores transversales reutilizados por los demas modulos.

## Alcance
- Modelo comun agnostico a framework.
- Contratos de resultado sin excepciones de flujo para operaciones de infraestructura.
- Tipos canonicos para ambiente, documento, transporte y datos monetarios.

## Componentes principales

### Enums de dominio
- `TipoDocumento`
- `TipoAmbiente`
- `ProtocoloTransporte`

### Value objects y modelo comun
- `Dinero`
- `Moneda`
- `NumeroSerie`
- `IdentificadorDocumento`
- `Direccion`
- `Contacto`
- `TipoCambio`

### Resultado de operaciones
- `ResultadoOperacion<T>`

### Jerarquia de errores
- `ExcepcionUblKit`
- `ExcepcionValidacion`
- `ExcepcionSerializacionXml`
- `ExcepcionFirmaDigital`
- `ExcepcionAutenticacionSunat`
- `ExcepcionTransporte`
- `ExcepcionEnsamblaje`

## Dependencias
- Sin dependencias de terceros en este modulo.
- Version de Java del proyecto: 21 (definida en el parent).

## Ejemplo rapido

```java
import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.core.enumerado.TipoDocumento;
import com.cna.ublkit.core.modelo.ResultadoOperacion;

TipoDocumento tipo = TipoDocumento.FACTURA;
TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;

ResultadoOperacion<String> ok = ResultadoOperacion.ok("XML generado");
ResultadoOperacion<String> error = ResultadoOperacion.error("VALIDATION_ERROR", "Documento invalido");
```

## Buenas practicas
- Reutilizar `ResultadoOperacion` en adaptadores IO para no mezclar errores tecnicos con validacion de negocio.
- Mantener enums y value objects de `ublkit-core` como unica fuente de verdad compartida.
