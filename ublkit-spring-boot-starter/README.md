# ublkit-spring-boot-starter

Starter oficial para integrar UBLKit en Spring Boot mediante autoconfiguracion.

Este modulo no crea endpoints HTTP por si solo: su objetivo es registrar beans listos para que tu aplicacion implemente los flujos de negocio (validar, serializar, firmar, enviar, consultar ticket, renderizar).

## 1. Alcance real del starter

El starter encapsula wiring de dependencias entre modulos UBLKit y expone beans por defecto con `@ConditionalOnMissingBean`.

En la version actual:
- Si agregas la dependencia, Spring Boot detecta automaticamente `UblKitAutoConfiguration`.
- Se crean beans de renderizado, serializacion XML, validacion y gateway SUNAT.
- Puedes reemplazar cualquier bean definiendo otro del mismo tipo en tu `@Configuration`.
- Se exponen propiedades `ublkit.gateway.*` para timeout y reintentos del gateway.

## 2. Integracion en tu proyecto

Agrega el modulo en Maven:

```xml
<dependency>
  <groupId>com.cna</groupId>
  <artifactId>ublkit-spring-boot-starter</artifactId>
  <version>${ublkit.version}</version>
</dependency>
```

Requisito minimo:
- Spring Boot 3.x
- Java 17+

## 3. Como se activa la autoconfiguracion

El archivo `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` contiene:

```text
com.cna.ublkit.spring.UblKitAutoConfiguration
```

Con eso, Spring Boot incluye `UblKitAutoConfiguration` sin necesidad de `@Import` manual.

## 4. Inventario completo de beans registrados

### 4.1 Renderizadores PDF
- `RenderizadorPdfFactura`
- `RenderizadorPdfGuiaRemision`
- `RenderizadorPdfNota`
- `RenderizadorPdfComunicacionBaja`
- `RenderizadorPdfResumenDiario`
- `RenderizadorTicketFactura`

### 4.2 Renderizadores HTML
- `RenderizadorHtmlFactura`
- `RenderizadorHtmlGuiaRemision`
- `RenderizadorHtmlNota`
- `RenderizadorHtmlComunicacionBaja`
- `RenderizadorHtmlResumenDiario`

### 4.3 Serializadores XML UBL
- `SerializadorXmlFactura`
- `SerializadorXmlNotaCredito`
- `SerializadorXmlNotaDebito`
- `SerializadorXmlGuiaRemision`
- `SerializadorXmlComunicacionBaja`
- `SerializadorXmlResumenDiario`
- `SerializadorXmlPercepcion`
- `SerializadorXmlRetencion`

### 4.4 Validadores
- `ValidadorFactura`
- `ValidadorNotaCredito`
- `ValidadorNotaDebito`
- `ValidadorGuiaRemision`
- `ValidadorComunicacionBaja`
- `ValidadorResumenDiario`
- `ValidadorPercepcion`
- `ValidadorRetencion`

### 4.5 Gateway y transporte SUNAT
- `ConfiguracionGateway` (desde `ublkit.gateway.*`)
- `ClienteSoap` -> `HttpClienteNativoSoap`
- `ClienteRest` -> `HttpClienteNativoRest`
- `ProveedorToken` -> `ProveedorTokenNativo`
- `PasarelaSunat` -> `PasarelaSunatDefecto`

Total: 32 beans registrados por autoconfiguracion.

## 5. Flujo de implementacion recomendado

El flujo end-to-end en una aplicacion emisora suele ser:

1. Construir el documento (`BorradorFactura`, `BorradorGuiaRemision`, etc.).
2. Validar (`Validador*`).
3. Serializar a XML UBL (`SerializadorXml*`).
4. Firmar XML (`ServicioFirma`, modulo `ublkit-sign`, API estatica).
5. Enviar a SUNAT (`PasarelaSunat`).
6. Si aplica, consultar ticket (`consultarTicketSoap` o `consultarTicketRest`).
7. Renderizar salida visual (`RenderizadorHtml*`, `RenderizadorPdf*`, ticket).

## 6. Ejemplos completos por capacidad

### 6.1 Validar una factura

```java
@Service
public class ValidacionService {

    private final ValidadorFactura validadorFactura;

    public ValidacionService(ValidadorFactura validadorFactura) {
        this.validadorFactura = validadorFactura;
    }

    public ResultadoValidacion validar(BorradorFactura factura) {
        ResultadoValidacion resultado = validadorFactura.validar(factura);
        if (!resultado.esValido()) {
            resultado.getIncidencias().forEach(i ->
                    System.err.printf("[%s] %s - %s%n", i.severidad(), i.codigo(), i.mensaje()));
        }
        return resultado;
    }
}
```

### 6.2 Serializar factura a XML

```java
@Service
public class SerializacionService {

    private final SerializadorXmlFactura serializador;

    public SerializacionService(SerializadorXmlFactura serializador) {
        this.serializador = serializador;
    }

    public String generarXml(BorradorFactura factura) {
        return serializador.serializar(factura);
    }
}
```

### 6.3 Firmar XML (modulo sign)

`ServicioFirma` es utilitario estatico, no requiere bean Spring.

```java
@Service
public class FirmaService {

    public ResultadoFirma firmar(String xml, InputStream pfxStream, String passwordPfx) {
        OrigenCertificado origen = new OrigenCertificado(pfxStream, passwordPfx);
        DetallesCertificado cert = CargadorCertificado.cargar(origen);
        return ServicioFirma.firmarXml(xml, cert);
    }
}
```

### 6.4 Enviar comprobante sincrono (Factura/Boleta/NC/ND)

```java
@Service
public class EnvioComprobanteService {

    private final PasarelaSunat pasarelaSunat;

    public EnvioComprobanteService(PasarelaSunat pasarelaSunat) {
        this.pasarelaSunat = pasarelaSunat;
    }

    public ResultadoEnvio enviar(String xmlFirmado,
                                 String nombreArchivo,
                                 CredencialesEmpresa credenciales,
                                 TipoAmbiente ambiente) {
        return pasarelaSunat.enviarComprobante(xmlFirmado, nombreArchivo, credenciales, ambiente);
    }
}
```

### 6.5 Enviar resumen/baja y consultar ticket SOAP

```java
@Service
public class EnvioAsincronoSoapService {

    private final PasarelaSunat pasarelaSunat;

    public EnvioAsincronoSoapService(PasarelaSunat pasarelaSunat) {
        this.pasarelaSunat = pasarelaSunat;
    }

    public ResultadoConsulta enviarYConsultar(String xmlFirmado,
                                              String nombreArchivo,
                                              CredencialesEmpresa credenciales,
                                              TipoAmbiente ambiente) {
        ResultadoEnvio envio = pasarelaSunat.enviarResumen(xmlFirmado, nombreArchivo, credenciales, ambiente);
        if (envio.estado() != EstadoEnvio.TICKET_PENDIENTE || envio.numeroTicket() == null) {
            return ResultadoConsulta.error("SIN_TICKET", "SUNAT no devolvio ticket");
        }
        return pasarelaSunat.consultarTicketSoap(envio.numeroTicket(), credenciales, ambiente);
    }
}
```

### 6.6 Enviar guia de remision y consultar ticket REST

```java
@Service
public class GuiaRemisionService {

    private final PasarelaSunat pasarelaSunat;

    public GuiaRemisionService(PasarelaSunat pasarelaSunat) {
        this.pasarelaSunat = pasarelaSunat;
    }

    public ResultadoConsulta enviarYConsultarGre(String xmlFirmado,
                                                 String nombreArchivo,
                                                 CredencialesEmpresa credenciales,
                                                 TipoAmbiente ambiente) {
        ResultadoEnvio envio = pasarelaSunat.enviarGuiaRemision(xmlFirmado, nombreArchivo, credenciales, ambiente);
        if (envio.numeroTicket() == null) {
            return ResultadoConsulta.error("SIN_TICKET", "No se recibio ticket GRE");
        }
        return pasarelaSunat.consultarTicketRest(envio.numeroTicket(), credenciales, ambiente);
    }
}
```

### 6.7 Renderizar HTML y PDF de una factura

```java
@Service
public class RenderFacturaService {

    private final RenderizadorHtmlFactura htmlFactura;
    private final RenderizadorPdfFactura pdfFactura;

    public RenderFacturaService(RenderizadorHtmlFactura htmlFactura,
                                RenderizadorPdfFactura pdfFactura) {
        this.htmlFactura = htmlFactura;
        this.pdfFactura = pdfFactura;
    }

    public ResultadoRender renderHtml(BorradorFactura factura) {
        return htmlFactura.renderizar(ContextoRender.of(factura));
    }

    public ResultadoRender renderPdf(BorradorFactura factura, String hash, String qrBase64) {
        ContextoRender<BorradorFactura> contexto = ContextoRender.of(factura, hash, qrBase64);
        return pdfFactura.renderizar(contexto);
    }
}
```

### 6.8 Render ticket termico

```java
@Service
public class TicketService {

    private final RenderizadorTicketFactura ticketFactura;

    public TicketService(RenderizadorTicketFactura ticketFactura) {
        this.ticketFactura = ticketFactura;
    }

    public byte[] generarTicket(BorradorFactura factura, String hash, String qrBase64) {
        ResultadoRender resultado = ticketFactura.renderizar(ContextoRender.of(factura, hash, qrBase64));
        return resultado.contenidoPdf();
    }
}
```

## 7. Configuracion en application.yml

El starter define `@ConfigurationProperties` con prefijo `ublkit`.

Propiedades soportadas:
- `ublkit.gateway.connect-timeout-ms` (default: `10000`)
- `ublkit.gateway.read-timeout-ms` (default: `60000`)
- `ublkit.gateway.max-intentos` (default: `3`, minimo efectivo: `1`)
- `ublkit.gateway.max-connections` (default: `100`, minimo efectivo: `1`)

Compatibilidad:
- Si usas `ublkit.gateway.connect-timeout` y `ublkit.gateway.read-timeout` (formato `Duration`), se siguen soportando para no romper versiones anteriores.
- Cuando se define `*-timeout-ms`, ese valor tiene prioridad sobre `Duration`.

Ejemplo:

```yaml
ublkit:
    gateway:
        connect-timeout-ms: 3000
        read-timeout-ms: 10000
        max-intentos: 5
        max-connections: 100
```

Estas propiedades alimentan:
- `ClienteSoap` (`HttpClienteNativoSoap`)
- `ClienteRest` (`HttpClienteNativoRest`)
- `ProveedorToken` (`ProveedorTokenNativo`)
- `PasarelaSunatDefecto` (estrategia de retry)

Nota tecnica de `max-connections`:
- En Java `HttpClient` nativo, el tamaño de pool se controla con la propiedad JVM `jdk.httpclient.connectionPoolSize`.
- Es una configuracion global del proceso, por lo que el primer valor aplicado en la JVM es el que prevalece.
- Si otro componente ya definio un valor diferente, UBLKit registra advertencia y mantiene el valor existente para evitar cambios inesperados.

La personalizacion avanzada sigue disponible reemplazando beans (`@Bean`) por tipo.

Ejemplo de customizacion del gateway con timeout y reintentos propios:

```java
@Configuration
public class UblKitCustomConfiguration {

    @Bean
    public PasarelaSunat pasarelaSunatPersonalizada(ClienteSoap clienteSoap,
                                                    ClienteRest clienteRest,
                                                    ProveedorToken proveedorToken) {
        ConfiguracionGateway cfg = new ConfiguracionGateway(
                Duration.ofSeconds(5),
                Duration.ofSeconds(30),
            5,
            100
        );
        return new PasarelaSunatDefecto(clienteSoap, clienteRest, proveedorToken, cfg);
    }
}
```

## 8. Sobrescritura de beans (extension points)

Todos los beans del starter usan `@ConditionalOnMissingBean`, asi que puedes:
- Sustituir transporte SOAP/REST por clientes propios.
- Sustituir `ProveedorToken` para estrategia OAuth custom.
- Sustituir validadores o serializadores por politicas internas.
- Mantener el resto del wiring automatico sin duplicar configuracion.

Ejemplo reemplazando `ClienteRest`:

```java
@Configuration
public class TransporteConfig {
    @Bean
    public ClienteRest clienteRest() {
        return new MiClienteRestObservado();
    }
}
```

## 9. Tipos de documento cubiertos por el starter

Por combinacion de serializadores/validadores/renderizadores, cubre:
- Factura y Boleta
- Nota de Credito
- Nota de Debito
- Guia de Remision
- Comunicacion de Baja
- Resumen Diario
- Comprobante de Percepcion
- Comprobante de Retencion

## 10. Comportamientos tecnicos relevantes en gateway

`PasarelaSunatDefecto` incorpora:
- Deteccion de tipo de XML por tag raiz para enrutar envios (sincrono, asincrono o GRE).
- Reintentos con backoff exponencial para errores temporales (`IO_ERROR`, `HTTP_5XX`).
- Obtencion de token OAuth para flujos REST de GRE.
- Logging con enmascaramiento parcial de credenciales/token.

## 11. Que NO hace este starter

- No define controladores REST.
- No persiste documentos, XML, CDR ni tickets.
- No agenda polling automatico de tickets.
- No administra certificados en memoria o HSM: la firma se resuelve en `ublkit-sign`.

## 12. API HTTP de referencia

Este modulo incluye un contrato OpenAPI de referencia en `api.yml` para implementar una API Spring Boot sobre los beans del starter.

El archivo describe endpoints sugeridos para:
- validacion
- serializacion XML
- firma
- envio/consulta SUNAT
- render HTML/PDF

Puedes adaptarlo a tu arquitectura (sync, async, colas, eventos).

## 13. Verificacion automatica incluida

`UblKitAutoConfigurationTest` valida que el contexto registre al menos:
- `RenderizadorPdfFactura`
- `RenderizadorHtmlFactura`
- `SerializadorXmlFactura`
- `ValidadorFactura`
- `PasarelaSunat`

Esto confirma el wiring basico de autoconfiguracion.

## 14. Errores frecuentes

- Esperar endpoints REST automaticos del starter (el starter solo registra beans).
- Configurar `ublkit.gateway.*` y luego sobreescribir manualmente los mismos beans sin querer.
- No incluir `clientId/clientSecret` para operaciones GRE via REST.

## 15. Checklist de produccion

- Validar propiedades `ublkit.gateway.*` por ambiente (`BETA`/`PRODUCCION`).
- Definir estrategia de override solo para beans que realmente quieras customizar.
- Añadir pruebas de contexto (`ApplicationContextRunner`) para tus personalizaciones.
