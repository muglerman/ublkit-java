# ublkit-quarkus

Adaptador oficial para integrar UBLKit de forma nativa en aplicaciones Quarkus.

## Responsabilidad
- Proveer productores CDI para los componentes de la librería.
- Soporte para inyección de dependencias de firmadores y renderizadores.
- Permitir el arranque rápido y eficiente en entornos serverless o contenedores.

## Componentes Clave
- `UblKitProducers`: Provee beans CDI como @Singleton.
- `io.quarkus:quarkus-arc`: Inyección de dependencias centralizada.

## Dependencias
- `ublkit-*` (Módulos nucleares)
- `jakarta.enterprise:jakarta.enterprise.cdi-api`

## Ejemplo de Uso
En tu recurso o servicio Quarkus:
```java
@Inject
RenderizadorPdfFactura renderizador;

public byte[] emitirPdf(BorradorFactura factura) {
    return renderizador.renderizar(ContextoRender.of(factura)).contenidoPdf();
}
```
