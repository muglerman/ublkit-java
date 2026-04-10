# ublkit-spring-boot-starter TODO

Adaptador oficial para integrar UBLKit de forma transparente en aplicaciones Spring Boot.

## Responsabilidad
- Proveer autoconfiguración de beans para el core y servicios.
- Inyección de dependencias centralizada basada en puertos y filtros.
- Configuración opcional por `application.properties/yaml`.

## Componentes Clave
- `UblKitAutoConfiguration`: Registra validadores, firmadores y renderizadores.
- `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`: Registro de autoconfig.

## Dependencias
- `ublkit-*` (Todos los módulos nucleares)
- `org.springframework.boot:spring-boot-starter`

## Ejemplo de Uso
En tu aplicación Spring Boot:
```java
@Autowired
private RenderizadorPdfFactura renderizador;

public byte[] emitirPdf(BorradorFactura factura) {
    return renderizador.renderizar(ContextoRender.of(factura)).contenidoPdf();
}
```
