# ublkit-sign

Módulo dedicado a la firma digital de documentos XML siguiendo los estándares de SUNAT.

## Responsabilidad
- Firma de XML (X509 Digital Signature).
- Carga y manejo de material criptográfico (PFX, JKS, etc.).
- Extracción de Hash y Digest obligatorios para el documento electrónico.

## Componentes Clave
- `CargadorCertificado`: Carga certificados desde archivos o flujos.
- `FirmadorXml`: Clase principal para aplicar la firma digital a un XML.
- `DetallesCertificado`: Información del certificado procesado (Vigencia, Emisor, Clave Privada).

## Dependencias
- `ublkit-core`

## Ejemplo de Uso
```java
DetallesCertificado cert = CargadorCertificado.desdeArchivo("certificado.pfx", "password");
FirmadorXml firmador = new FirmadorXml();
Document firmado = firmador.firmar(xmlOriginal, "IdentificadorFirma", cert);
```
