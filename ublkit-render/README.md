# ublkit-render

Modulo de renderizacion visual (HTML/PDF/ticket) para documentos UBL.

## Alcance
- Render HTML para visualizacion web.
- Render PDF para distribucion e impresion.
- Render ticket termico para escenarios POS.

## Contratos centrales
- `ContextoRender<T>`: contiene documento, hash, qr y atributos de plantilla.
- `ResultadoRender`: salida HTML o PDF segun renderizador.
- `RenderizadorDocumento<T>`: contrato comun de render.

## Renderizadores disponibles

### Factura y boleta
- `RenderizadorHtmlFactura`
- `RenderizadorPdfFactura`
- `RenderizadorTicketFactura`

### Notas
- `RenderizadorHtmlNota`
- `RenderizadorPdfNota`

### Guia de remision
- `RenderizadorHtmlGuiaRemision`
- `RenderizadorPdfGuiaRemision`

### Resumen y baja
- `RenderizadorHtmlResumenDiario`
- `RenderizadorPdfResumenDiario`
- `RenderizadorHtmlComunicacionBaja`
- `RenderizadorPdfComunicacionBaja`

### Formatos de impresion
- `FormatoImpresion`: A4, A5, TICKET_58MM, TICKET_80MM

## Dependencias
- `ublkit-core`
- `ublkit-ubl`
- `io.pebbletemplates:pebble`
- `io.github.openhtmltopdf:openhtmltopdf-pdfbox`

## Ejemplo rapido

```java
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;

RenderizadorPdfFactura render = new RenderizadorPdfFactura();
ResultadoRender resultado = render.renderizar(ContextoRender.of(factura, hash, qrBase64));
byte[] pdf = resultado.contenidoPdf();
```

## Atributos de plantilla

```java
ContextoRender<BorradorFactura> ctx = ContextoRender.of(
        factura,
        hash,
        qrBase64,
        Map.of("CAMPO_EXTRA", "Valor")
);
```

## Recomendaciones
- Proveer logo y recursos estaticos con ruta resoluble en tu runtime para evitar warnings de recursos no encontrados.
- Usar `hashDocumento` y `qrBase64` cuando el documento ya fue firmado para consistencia visual legal.
