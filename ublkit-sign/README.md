# ublkit-sign

## Nombre y Descripción del Proyecto
**ublkit-sign** es un módulo que pertenece a la librería comunitaria UBLKit.
Módulo de infraestructura responsable de aplicar la firma digital estándar exigida por SUNAT a los documentos XML UBL (estándar XMLDSig / X.509 RSA-SHA1).

## Stack Tecnológico
- Java 21+
- Java Cryptography Architecture (JCA) nativa de Java (`java.security.*`, `javax.xml.crypto.*`).
- Proveedores criptográficos estándar (PKCS12, JKS).

## Arquitectura del Proyecto
Módulo de Infraestructura. Transforma un XML no firmado en un documento con un nodo `ds:Signature` envuelto (Enveloped Signature), que posteriormente puede ser enviado a la SUNAT. Está altamente aislado para asegurar la gestión correcta de certificados en memoria.

## Empezando
### Requisitos Previos
- Java 21+
- Maven 3.8+

### Instalación
Para utilizar este módulo, agrégalo como dependencia en tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.cna</groupId>
    <artifactId>ublkit-sign</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Estructura del Proyecto
La estructura del módulo es:
- `src/main/java/com/cna/ublkit/sign/`: El `ServicioFirma` como fachada, modelos de origen y detalles (`DetallesCertificado`, `OrigenCertificado`), lógica de carga (`CargadorCertificado`) y el motor de firma subyacente (`FirmadorXml`).

## Características Principales
- Carga transparente de certificados digitales desde almacenes PKCS12 (.p12) o JKS (Java KeyStore).
- Firma nativa de XML bajo el estándar Enveloped Signature especificado por UBL, sin usar BouncyCastle o librerías adicionales que sobrecargan el runtime.
- Extracción del *Digest Value* (Hash) resultante y del XML firmado (`ResultadoFirma`).
- Preparado para multitenencia (el certificado no se ancla a nivel de configuración global, sino que se suministra por solicitud o a través de un `RepositorioCertificados`).

## Flujo de Desarrollo
- El XML generado por `ublkit-ubl` se pasa al `ServicioFirma` junto con el certificado del emisor.
- El objeto `ResultadoFirma` retornado contiene tanto la cadena XML final como el hash necesario para el código QR.

## Estándares de Código
- **Cero archivos temporales**: Todo el proceso de firmado debe ocurrir en memoria (DOM to DOM o DOM a String).
- No cachear estáticamente instancias de `KeyStore` para evitar fugas de memoria en escenarios SaaS multitenant. Se usa caché dinámico si es necesario.

## Pruebas
- Validar que el XML resultante cumpla con el esquema XMLDSig y tenga referencias válidas.
- Asegurar que la modificación de un solo bit en el XML firmado rompa la validación del hash en los tests.

## Contribución
Las contribuciones son bienvenidas. Por favor, lee el archivo `CONTRIBUTING.md` en la raíz del repositorio para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`).
3. Haz tus cambios siguiendo los estándares de código.
4. Envía un Pull Request.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` en la raíz del repositorio para más detalles.
