# ublkit-qr

## Nombre y Descripción del Proyecto
**ublkit-qr** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo enfocado en la generación del código QR oficial requerido por SUNAT para la representación impresa de los comprobantes de pago electrónicos.

## Stack Tecnológico
- Java 21+
- `com.google.zxing:core` (Librería ZXing para generar códigos de barras y QR)
- Codificación en Base64

## Arquitectura del Proyecto
Módulo de infraestructura específico. Extrae los datos necesarios de un documento ensamblado y su firma XML, construyendo la trama estricta exigida por la norma para los QR, y exporta una imagen Base64 o arreglo de bytes que puede ser consumida por motores de renderización o servicios web.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-qr</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura contiene:
- `src/main/java/com/cna/ublkit/qr/`: El servicio principal `GeneradorQrSunat`.

## Características Principales
- Construcción automática de la trama de texto SUNAT (RUC emisor | Tipo documento | Serie | Número | IGV | Total | Fecha | Tipo Doc Adquiriente | Nro Doc Adquiriente | Hash firma).
- Generación de la imagen del código QR en formato PNG.
- Codificación transparente a `Base64` o arreglos de bytes sin dependencias del sistema operativo.

## Flujo de Desarrollo
- Los cambios en este módulo suelen ser muy raros a menos que SUNAT modifique la composición de la trama del QR.
- Utilizado por el módulo de renderización u otras aplicaciones para inyectar la imagen al PDF/HTML.

## Estándares de Código
- Las imágenes se devuelven en memoria (`byte[]` o `Base64`) y **no se escriben en disco**.
- Dependencia aislada (ZxIng no contamina el core ni el ubl base).

## Pruebas
- Validar que las tramas generadas contengan exactamente los pipe (`|`) y datos obligatorios.
- Validar la legibilidad de la imagen generada mediante la decodificación ZXing inversa en tests.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
