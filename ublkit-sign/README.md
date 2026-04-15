# ublkit-sign

Modulo de firma digital XML para documentos UBL.

## Alcance
- Carga de certificados desde keystore (PKCS12/JKS).
- Firma XML con referencia de firma configurable.
- Entrega de XML firmado y digest para uso posterior (QR, render, auditoria).

## API publica recomendada
- `CargadorCertificado.cargar(OrigenCertificado)`
- `ServicioFirma.firmarXml(xml, certificado)`
- `ServicioFirma.firmarXml(xml, idReferencia, certificado)`

## Objetos clave
- `OrigenCertificado`
- `DetallesCertificado`
- `ResultadoFirma`

## Dependencias
- `ublkit-core`

## Ejemplo rapido

```java
import com.cna.ublkit.sign.api.ResultadoFirma;
import com.cna.ublkit.sign.api.ServicioFirma;
import com.cna.ublkit.sign.certificado.CargadorCertificado;
import com.cna.ublkit.sign.certificado.DetallesCertificado;
import com.cna.ublkit.sign.certificado.OrigenCertificado;

InputStream pfx = Files.newInputStream(Path.of("certificado.pfx"));
DetallesCertificado cert = CargadorCertificado.cargar(new OrigenCertificado(pfx, "password"));

ResultadoFirma resultado = ServicioFirma.firmarXml(xml, "SignSUNAT", cert);
if (resultado.exitoso()) {
	String digest = resultado.digestValue();
	byte[] xmlFirmado = resultado.xmlFirmado();
}
```

## Notas operativas
- Si ocurre un fallo de firma, `ResultadoFirma` retorna `exitoso=false` y `mensajeError`.
- La API expone XML firmado como `byte[]` y `String` para facilitar integracion con gateway/render.
