# ublkit-core

Módulo base que contiene los conceptos fundamentales del dominio y objetos de valor compartidos por todo el proyecto UBLKit.

## Responsabilidad
- Definir tipos base del dominio sin dependencias externas.
- Centralizar excepciones y resultados comunes.
- Mantener la integridad de los datos mediante objetos de valor (Value Objects).

## Componentes Clave
- `TipoDocumento`: Enum con los tipos soportados (Factura, Boleta, etc.).
- `TipoAmbiente`: Beta y Producción.
- `Dinero`: Representación inmutable de montos y monedas.
- `ExcepcionUblKit`: Excepción base del sistema.

## Dependencias
- Ninguna (Java 21 Puro).

## Ejemplo de Uso
```java
TipoDocumento tipo = TipoDocumento.FACTURA;
TipoAmbiente ambiente = TipoAmbiente.PRODUCCION;
```
