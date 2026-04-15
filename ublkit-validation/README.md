# ublkit-validation

Modulo de validacion funcional y estructural para documentos UBL antes de firma y envio.

## Alcance
- API generica de validacion por tipo de documento.
- Acumulacion de incidencias con severidad y codigo.
- Validaciones especificas por comprobante y documentos SUNAT.

## API principal
- `Validador<T>`
- `ResultadoValidacion`
- `IncidenciaValidacion`
- `SeveridadValidacion`

## Validadores disponibles
- `ValidadorFactura`
- `ValidadorNotaCredito`
- `ValidadorNotaDebito`
- `ValidadorGuiaRemision`
- `ValidadorComunicacionBaja`
- `ValidadorResumenDiario`
- `ValidadorPercepcion`
- `ValidadorRetencion`

## Dependencias
- `ublkit-core`
- `ublkit-ubl`

## Ejemplo rapido

```java
import com.cna.ublkit.validation.modelo.ResultadoValidacion;
import com.cna.ublkit.validation.validador.ValidadorFactura;

ValidadorFactura validador = new ValidadorFactura();
ResultadoValidacion resultado = validador.validar(factura);

if (resultado.esValido()) {
    System.out.println("Documento valido");
} else {
    resultado.getIncidencias().forEach(i ->
            System.err.println(i.severidad() + " " + i.codigo() + " " + i.mensaje()));
}
```

## Recomendacion
- Ejecutar validacion antes de serializar/firmar para reducir rechazos en SUNAT.
- Tratar advertencias y errores de forma diferenciada en la capa de aplicacion.

## Errores frecuentes
- Tratar advertencias como errores bloqueantes sin criterio de negocio.
- Ejecutar envio SUNAT sin validar primero.

## Checklist de produccion
- Definir politica explicita de bloqueo para `ERROR` y `WARNING`.
- Persistir incidencias relevantes para auditoria y soporte.
