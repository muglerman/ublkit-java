# ublkit-catalogs

Modulo de acceso a catalogos normativos (principalmente SUNAT) mediante una API comun.

## Alcance
- Cargar catalogos desde archivos CSV en classpath.
- Exponer busqueda por id de catalogo y codigo.
- Entregar metadatos dinamicos por columna para catalogos con estructura extendida.

## API publica real
- `ProveedorCatalogos`:
	- `obtenerCatalogo(String idCatalogo)`
	- `buscar(String idCatalogo, String codigo)`
- `EntradaCatalogo`:
	- `getCodigo()`
	- `getDescripcion()`
	- `getAtributoAdicional(String clave)`
	- `getTodosAtributos()`

## Implementacion incluida
- `LectorCsvCatalogos` implementa `ProveedorCatalogos`.
- Ruta de carga esperada por defecto:

```text
/sunat/catalogos/{idCatalogo}.csv
```

Ejemplo: para id `01`, intenta leer `/sunat/catalogos/01.csv`.

## Dependencias
- `ublkit-core`

## Ejemplo rapido

```java
import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.sunat.LectorCsvCatalogos;

ProveedorCatalogos proveedor = new LectorCsvCatalogos();

proveedor.buscar("01", "01").ifPresent(entrada -> {
		System.out.println("Codigo: " + entrada.getCodigo());
		System.out.println("Descripcion: " + entrada.getDescripcion());
});

int totalMonedas = proveedor.obtenerCatalogo("02").size();
System.out.println("Entradas catalogo 02: " + totalMonedas);
```

## Comportamientos importantes
- Si el CSV no existe, retorna catalogo vacio (no lanza error).
- Si hay error de parseo, lanza `ExcepcionUblKit`.
- Las entradas retornadas son inmutables desde la API publica.

## Errores frecuentes
- Consultar un catalogo que no existe esperando excepcion; la API retorna lista vacia.
- Buscar columnas adicionales sin respetar exactamente el nombre del header CSV.

## Checklist de produccion
- Versionar y validar los CSV normativos antes de desplegar.
- Agregar monitoreo de catalogos vacios para detectar recursos faltantes en classpath.
