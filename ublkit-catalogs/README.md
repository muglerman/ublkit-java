# ublkit-catalogs

Módulo para la gestión y validación de catálogos normativos, principalmente los definidos por la SUNAT en el Anexo 08.

## Responsabilidad
- Proveer acceso a las tablas de referencia (Catálogo 01, 06, 07, etc.).
- Validar códigos de tipos de documentos, monedas, impuestos y otros.

## Componentes Clave
- `ProveedorCatalogos`: Interfaz para consultar entradas.
- `CatalogoTiposDocumento`: Acceso a códigos como "01", "03", "07".
- `CatalogoMonedas`: Validación de códigos ISO 4217 (PEN, USD).

## Dependencias
- `ublkit-core`

## Ejemplo de Uso
```java
// Próximamente: implementación de búsqueda por código
// CatalogoTiposDocumento.getInstance().buscar("01");
```
