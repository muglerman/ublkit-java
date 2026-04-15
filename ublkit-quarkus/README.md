# ublkit-quarkus

Integracion CDI para usar UBLKit en aplicaciones Quarkus.

## Alcance
- Provee productores CDI singleton para renderizadores, serializadores y validadores.
- Evita wiring manual repetitivo en servicios Quarkus.

## Componente principal
- `UblKitProducers`

## Que beans produce hoy

### Render
- Factura, Nota, Guia, Resumen, Comunicacion de baja (HTML/PDF)

### Serializacion XML
- Factura, Nota credito/debito, Guia, Resumen, Baja, Percepcion, Retencion

### Validacion
- Factura, Nota credito/debito, Guia, Resumen, Baja, Percepcion, Retencion

## Importante
Este modulo no produce actualmente beans de gateway ni de firma.
Si los necesitas en CDI, debes registrarlos en tus propios productores.

## Dependencias
- `quarkus-arc`
- `ublkit-core`
- `ublkit-ubl`
- `ublkit-sign`
- `ublkit-gateway`
- `ublkit-render`
- `ublkit-validation`

## Ejemplo rapido

```java
import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;
import jakarta.inject.Inject;

public class EmisionService {

    @Inject
    RenderizadorPdfFactura renderizador;

    public byte[] emitirPdf(BorradorFactura factura) {
        return renderizador.renderizar(ContextoRender.of(factura)).contenidoPdf();
    }
}
```

## Extension recomendada
Para inyectar `PasarelaSunat` o servicios de firma, crea un productor CDI en tu aplicacion:

```java
@Produces
@Singleton
PasarelaSunat pasarela() {
    return new PasarelaSunatDefecto();
}
```
