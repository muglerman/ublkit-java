# ublkit-render

Módulo encargado de generar representaciones gráficas amigables para los documentos UBL.

## Responsabilidad
- Transformar modelos de dominio o XML a formatos visuales.
- Generar archivos PDF para impresión y envío por correo.
- Soporte para formatos de Ticket Térmico de 58mm y 80mm.

## Componentes Clave
- `RenderizadorPdfFactura`: Genera PDF para Facturas y Boletas en formato A4/A5.
- `RenderizadorTicketFactura`: Versión optimizada para impresoras térmicas.
- `RenderizadorHtmlGuiaRemision`: Proporciona la base HTML para Guías.
- `FormatoImpresion`: Enum con las opciones de tamaño (A4, A5, TICKET_58MM, TICKET_80MM).

## Dependencias
- `ublkit-core`
- `ublkit-ubl`
- `io.pebbletemplates:pebble` (Motor de plantillas)
- `io.github.openhtmltopdf:openhtmltopdf-pdfbox` (Convertidor HTML a PDF)

## Ejemplo de Uso
```java
RenderizadorPdfFactura render = new RenderizadorPdfFactura();
ResultadoRender pdf = render.renderizar(ContextoRender.of(factura));
byte[] bytes = pdf.contenidoPdf();
```

## Personalización de plantillas HTML
Puedes pasar atributos adicionales por contexto para ser usados por tus plantillas:
```java
ContextoRender<BorradorFactura> ctx = ContextoRender.of(
    factura, "hash", "qrBase64", Map.of("CAMPO_EXTRA", "Valor")
);
```
