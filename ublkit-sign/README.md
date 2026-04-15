# ublkit-sign

Modulo de firma digital XML para documentos UBL.

## Alcance
- Carga de certificados desde keystore (PKCS12/JKS).
- Firma XML con referencia de firma configurable.
- Entrega de XML firmado y digest para uso posterior (QR, render, auditoria).

## API publica recomendada
- `CargadorCertificado.cargar(OrigenCertificado)`
- `CargadorCertificado.cargar(byte[], password)`
- `ServicioFirma.firmarXml(xml, certificado)`
- `ServicioFirma.firmarXml(xml, idReferencia, certificado)`
- `RepositorioCertificados.obtenerOCargar(clave, cargador)`
- `FirmadorXml.firmarComoBytes(xml, idReferencia, certificado)`
- `FirmadorXml.firmarComoString(xml, idReferencia, certificado)`

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
import com.cna.ublkit.sign.certificado.RepositorioCertificados;

InputStream pfx = Files.newInputStream(Path.of("certificado.pfx"));
DetallesCertificado cert = CargadorCertificado.cargar(new OrigenCertificado(pfx, "password"));

ResultadoFirma resultado = ServicioFirma.firmarXml(xml, "SignSUNAT", cert);
if (resultado.exitoso()) {
	String digest = resultado.digestValue();
	byte[] xmlFirmado = resultado.xmlFirmado();
}

// Recomendado en alto throughput: cachear y reutilizar el certificado por identidad
RepositorioCertificados repo = new RepositorioCertificados();
DetallesCertificado certReutilizable = repo.obtenerOCargar("20123456789", () -> cert);
ResultadoFirma resultado2 = ServicioFirma.firmarXml(xml, "SignSUNAT", certReutilizable);
```

## Notas operativas
- Si ocurre un fallo de firma, `ResultadoFirma` retorna `exitoso=false` y `mensajeError`.
- La API expone XML firmado como `byte[]` y `String` para facilitar integracion con gateway/render.

## Canonicalizacion y salida segura
- La firma XMLDSig se genera con canonicalizacion inclusiva (C14N) para compatibilidad SUNAT.
- El flujo recomendado retorna salida compacta/minificada (`byte[]`/`String`) y evita exponer DOM mutable en integracion.
- No aplicar pretty print despues de firmar: cambiar espacios/indentacion invalida el hash de firma.

## Concurrencia y rendimiento
- `ServicioFirma` es stateless y puede invocarse concurrentemente.
- `DetallesCertificado` es inmutable: cargalo una vez y reutilizalo entre hilos.
- Evita abrir/parsing del `.p12` por cada request; carga en startup o usa `RepositorioCertificados`.
- Para rotacion de certificados, invalida una clave puntual con `RepositorioCertificados.invalidar(clave)` o limpia todo con `limpiar()`.

## Errores frecuentes
- Cargar un certificado sin clave privada utilizable.
- Firmar con `idReferencia` incompatible con la expectativa del flujo receptor.

## Checklist de produccion
- Proteger password de keystore en secreto externo.
- Rotar certificados y monitorear vigencia/expiracion.
- Precalentar certificados en el arranque para evitar latencia inicial en primera firma.
