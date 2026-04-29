# ublkit-gateway

## Nombre y Descripción del Proyecto
**ublkit-gateway** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo que maneja las comunicaciones externas enviando los documentos y consultas de estado a los servicios web de la SUNAT (o una OSE).

## Stack Tecnológico
- Java 21+
- `java.net.http.HttpClient` nativo (introducido en Java 11) para peticiones sincrónicas y asincrónicas.
- Sin dependencias de terceros para SOAP (no Apache CXF ni Spring WebServices).

## Arquitectura del Proyecto
Módulo de Infraestructura que implementa el puerto `PasarelaSunat` del core. Centraliza toda la lógica de transporte de red, compresión, descompresión (ZIP) requerida por el estándar de facturación de SUNAT.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-gateway</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del paquete es:
- `src/main/java/com/cna/ublkit/gateway/`: Contrato `PasarelaSunat` y modelos de respuesta (`ResultadoEnvio`, `ArchivoCdr`).
- `src/main/java/com/cna/ublkit/gateway/client/`: Clientes subyacentes SOAP (`ClienteSoap`) y REST (`ClienteRest`).
- `src/main/java/com/cna/ublkit/gateway/auth/`: Proveedores de tokens OAuth2 (`ProveedorToken`) usados en la API REST (GRE).
- `src/main/java/com/cna/ublkit/gateway/config/`: `ResolvedorEndpoints` y `ConfiguracionGateway`.

## Características Principales
- Envío SOAP síncrono (tradicional para Facturas y Boletas a SUNAT/OSE).
- Envío SOAP asíncrono (con ticket) para Resúmenes y Comunicaciones de Baja.
- Envío REST nativo mediante OAuth2 para Guías de Remisión Electrónicas (GRE).
- Empaquetado automático del XML firmado en un `.zip` y codificación Base64 en un paso.
- Parseo automático y desempaquetado del archivo de Constancia de Recepción (CDR) determinando el `EstadoEnvio` (Aceptado, Rechazado, Observado).
- Configuración explícita de *timeouts* y políticas de reintentos en caso de caídas transitorias del servicio.

## Flujo de Desarrollo
- Cualquier comunicación HTTP debe ser tramitada a través del `HttpClient` pre-configurado para soportar reactividad (ej. con `CompletableFuture`) cuando la consulta es por un ticket asíncrono.
- Nuevos endpoints o cambios en URLs deben reflejarse en `ConstantesEndpoint`.

## Estándares de Código
- **No bloquear hilos eternamente**: Usar `ConfiguracionGateway` para controlar tiempos de lectura/conexión.
- **Backoff Exponencial**: Recomendado para reintentos de consulta de tickets y evitar baneos de IPs.
- **Tipado seguro**: Usar `ExcepcionTransporte` para errores de red o HTTP (5xx) delegando la decisión de reintento a las capas superiores.

## Pruebas
- Uso de `MockWebServer` (o similar ligero) en test para simular latencia de red y respuestas erróneas (500, 502).
- Validar el correcto parseo de un ZIP simulando la respuesta en bytes de la SUNAT.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
