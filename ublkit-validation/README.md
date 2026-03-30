# ublkit-validation

Módulo responsable de la validación sintáctica, semántica y de negocio de los documentos antes de su procesamiento.

## Responsabilidad
- Validar documentos y datos (BorradorFactura, etc.).
- Informar sobre errores y advertencias mediante un resultado estructurado.
- No depender de excepciones de control para el flujo normal de validación.

## Componentes Clave
- `Validador<T>`: Interfaz base para validadores.
- `ResultadoValidacion`: Acumula las incidencias detectadas.
- `IncidenciaValidacion`: Detalle de un error o advertencia con código y severidad.
- `ValidadorFactura`: Implementación específica para Facturas y Boletas.

## Dependencias
- `ublkit-core`
- `ublkit-ubl` (para modelos de documento)

## Ejemplo de Uso
```java
ValidadorFactura validador = new ValidadorFactura();
ResultadoValidacion resultado = validador.validar(factura);

if (!resultado.esValido()) {
    resultado.getIncidencias().forEach(error -> 
        System.err.println(error.mensaje()));
}
```
