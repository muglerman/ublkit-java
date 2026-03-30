# UBLKit — guía inicial de arquitectura, naming y documentación
>
> Enfoque: librería comunitaria, Java puro en el núcleo, adapters opcionales para Spring Boot y Quarkus.

---

## 1. Objetivo del proyecto

**UBLKit** debe ser una librería moderna para:

- modelar documentos UBL de forma amigable
- validarlos antes del envío
- firmarlos digitalmente
- enviarlos por distintos canales
- interpretar respuestas
- renderizar representaciones humanas como HTML/PDF

La idea central es esta:

- el **núcleo** no depende de frameworks
- Spring Boot y Quarkus son **adaptadores**
- el modelo público debe estar orientado al **dominio**, no al XML crudo
- puedes guiarte de xbuilder, xsender y fractuyo

---

## 2. Principios arquitectónicos

## 2.1. Arquitectura hexagonal

La arquitectura debe separar claramente:

- **dominio**: reglas, modelos, contratos
- **aplicación**: casos de uso y orquestación
- **infraestructura**: XML, firma, HTTP, SOAP, archivos, PDF, frameworks

La regla principal es:

> El dominio no conoce frameworks, ni XML, ni HTTP, ni Spring, ni Quarkus.

---

## 2.2. Regla de dependencia

Las dependencias siempre apuntan hacia adentro.

### Correcto

- `infraestructura -> aplicacion -> dominio`

### Incorrecto

- `dominio -> spring`
- `dominio -> quarkus`
- `dominio -> http`
- `dominio -> soap`
- `dominio -> xml`

---

## 2.3. Java puro primero

El proyecto debe construirse así:

- primero una API limpia en **Java puro**
- luego adaptadores para:
  - Spring Boot
  - Quarkus

Esto significa que módulos como `ublkit-core`, `ublkit-validation`, `ublkit-ubl`, `ublkit-sign` y buena parte de `ublkit-gateway` deben ser utilizables **sin Spring**.

Spring Boot y Quarkus no deben definir la arquitectura.  
Solo deben facilitar la integración.

---

## 3. Módulos y responsabilidad de cada uno

## 3.1. `ublkit-core`

### Responsabilidad

Contener los conceptos base compartidos por todo el proyecto.

### Debe incluir

- tipos base del dominio
- objetos de valor
- enums comunes
- excepciones base
- resultados comunes
- metadatos compartidos

### Debe evitar

- XML
- JAXB
- HttpClient
- Spring
- Quarkus
- SOAP
- PDF

### Paquetes sugeridos

```text
com.cna.ublkit.core
  ├── modelo
  ├── valor
  ├── enumerado
  ├── error
  └── resultado
```

### Clases iniciales sugeridas

- `TipoDocumento`
- `TipoAmbiente`
- `ProtocoloTransporte`
- `NumeroSerie`
- `IdentificadorDocumento`
- `Moneda`
- `Dinero`
- `ResultadoOperacion<T>`
- `ExcepcionUblKit`

---

## 3.2. `ublkit-catalogs`

### Responsabilidad

Gestionar catálogos normativos y tablas de referencia.

### Debe incluir

- catálogos SUNAT
- búsqueda por código
- validación de existencia
- descripciones
- vigencia/versionado

### Paquetes sugeridos

```text
com.cna.ublkit.catalogs
  ├── modelo
  ├── proveedor
  ├── sunat
  └── validacion
```

### Clases iniciales sugeridas

- `EntradaCatalogo`
- `VersionCatalogo`
- `ProveedorCatalogos`
- `CatalogoTiposDocumento`
- `CatalogoTributos`

---

## 3.3. `ublkit-validation`

### Responsabilidad

Validar documentos y datos antes de serializar, firmar o enviar.

### Debe incluir

- validaciones sintácticas
- validaciones semánticas
- validaciones de negocio
- advertencias y errores
- reporte de validación

### Regla importante

La validación no debe depender solo de excepciones.
Debe existir un resultado explícito.

### Paquetes sugeridos

```text
com.cna.ublkit.validation
  ├── api
  ├── modelo
  ├── validador
  └── regla
```

### Clases iniciales sugeridas

- `ResultadoValidacion`
- `IncidenciaValidacion`
- `SeveridadValidacion`
- `Validador<T>`
- `ValidadorFactura`

---

## 3.4. `ublkit-ubl`

### Responsabilidad

Modelar documentos y transformarlos a UBL/XML.

### Debe incluir

- modelo documental amigable
- mapeadores a UBL
- serialización XML
- normalización documental

### Debe evitar

- envío
- OAuth
- SOAP
- PDF
- lógica Spring/Quarkus

### Paquetes sugeridos

```text
com.cna.ublkit.ubl
  ├── api
  ├── modelo
  ├── ensamblador
  ├── mapeador
  └── xml
```

### Clases iniciales sugeridas

- `BorradorFactura`
- `EmisorDocumento`
- `ReceptorDocumento`
- `LineaFactura`
- `TotalesDocumento`
- `MapeadorFacturaUbl`
- `SerializadorXmlUbl`

---

## 3.5. `ublkit-sign`

### Responsabilidad

Firmar digitalmente XML y cargar material criptográfico.

### Debe incluir

- carga de certificados
- firma XML
- extracción de hash/digest
- soporte para formatos de certificado

### Paquetes sugeridos

```text
com.cna.ublkit.sign
  ├── api
  ├── certificado
  ├── cargador
  └── xml
```

### Clases iniciales sugeridas

- `OrigenCertificado`
- `DetallesCertificado`
- `CargadorCertificado`
- `FirmadorXml`
- `ResultadoFirma`

---

## 3.6. `ublkit-gateway`

### Responsabilidad

Integrar con sistemas externos como SUNAT u OSE.

### Debe incluir

- envío
- consulta
- verificación de ticket
- interpretación de respuestas
- CDR
- resolución de endpoint
- resolución de autenticación
- routing SOAP/REST

### Regla clave

`ublkit-gateway` no debe construir el documento.
Debe trabajar con un documento ya preparado o un XML ya firmado.

### Paquetes sugeridos

```text
com.cna.ublkit.gateway
  ├── api
  ├── caso
  ├── enrutamiento
  ├── autenticacion
  ├── endpoint
  ├── transporte
  │   ├── soap
  │   └── rest
  └── respuesta
```

### Clases iniciales sugeridas

- `PasarelaSunat`
- `PoliticaEnrutamiento`
- `ResolvedorEndpoints`
- `ClienteTransporteSoap`
- `ClienteTransporteRest`
- `ResultadoEnvio`
- `ResultadoConsulta`
- `LectorCdr`

---

## 3.7. `ublkit-render`

### Responsabilidad

Generar representación humana de los documentos.

### Debe incluir

- HTML
- PDF
- plantillas
- render para ticket térmico
- recursos visuales

### Paquetes sugeridos

```text
com.cna.ublkit.render
  ├── api
  ├── html
  ├── pdf
  ├── plantilla
  └── modelo
```

### Clases iniciales sugeridas

- `RenderizadorDocumento`
- `RenderizadorHtmlFactura`
- `RenderizadorPdfFactura`
- `ResultadoRender`

---

## 3.8. `ublkit-testkit`

### Responsabilidad

Facilitar pruebas unitarias, integrales y de regresión.

### Debe incluir

- fixtures
- builders de test
- golden files
- respuestas simuladas
- utilidades de aserción

### Clases iniciales sugeridas

- `FacturasEjemplo`
- `RespuestasSunatSimuladas`
- `AssertsXml`
- `AssertsCdr`

---

## 3.9. `ublkit-spring-boot-starter`

### Responsabilidad

Autoconfiguración para Spring Boot.

### Debe incluir

- configuración automática
- propiedades
- beans
- integración con el ecosistema Spring

### Importante

No debe contener el core real del sistema.
Solo adaptación.

---

## 3.10. `ublkit-quarkus`

### Responsabilidad

Adaptación a Quarkus.

### Debe incluir

- configuración
- productores CDI
- wiring
- soporte nativo si aplica

### Importante

No debe liderar el diseño del proyecto.

---

## 4. Orden recomendado para construir el proyecto

## Fase 1

- `ublkit-core`
- `ublkit-catalogs`
- `ublkit-validation`

## Fase 2

- `ublkit-ubl`

## Fase 3

- `ublkit-sign`

## Fase 4

- `ublkit-gateway`

## Fase 5

- `ublkit-render`

## Fase 6

- `ublkit-testkit`

## Fase 7

- `ublkit-spring-boot-starter`
- `ublkit-quarkus`

---

## 5. Naming claro en español

La convención debe ser consistente.

## 5.1. Reglas generales de naming

### Clases

- sustantivos claros
- evitar nombres genéricos como `Manager`, `Util`, `Helper`, `Processor`

### Interfaces

- nombres orientados a capacidad o contrato
- no abusar del prefijo `I`

### Métodos

- verbo + intención clara

### Paquetes

- sustantivos en minúscula
- coherentes con la responsabilidad

---

## 5.2. Recomendaciones concretas

### En vez de esto

- `DocumentManager`
- `XmlHelper`
- `SunatProcessor`
- `GeneralService`
- `Utils`

### Preferir esto

- `SerializadorXmlUbl`
- `FirmadorXml`
- `PasarelaSunat`
- `ValidadorFactura`
- `ResolvedorEndpoints`

---

## 5.3. Traducciones recomendadas

### Core

- `DocumentType` -> `TipoDocumento`
- `EnvironmentType` -> `TipoAmbiente`
- `OperationResult` -> `ResultadoOperacion`
- `Money` -> `Dinero`

### Validation

- `ValidationResult` -> `ResultadoValidacion`
- `ValidationIssue` -> `IncidenciaValidacion`
- `Validator` -> `Validador`

### UBL

- `InvoiceDraft` -> `BorradorFactura`
- `InvoiceLine` -> `LineaFactura`
- `XmlSerializer` -> `SerializadorXml`

### Sign

- `SignatureResult` -> `ResultadoFirma`
- `CertificateLoader` -> `CargadorCertificado`

### Gateway

- `GatewayResult` -> `ResultadoEnvio`
- `RoutingPolicy` -> `PoliticaEnrutamiento`
- `EndpointResolver` -> `ResolvedorEndpoints`

### Render

- `Renderer` -> `Renderizador`
- `TemplateResolver` -> `ResolvedorPlantilla`

---

## 6. Estructura hexagonal recomendada dentro de cada módulo

No todos los módulos necesitan exactamente la misma estructura, pero la convención general puede ser esta:

```text
modulo/
  ├── dominio
  │   ├── modelo
  │   ├── puerto
  │   ├── servicio
  │   └── regla
  ├── aplicacion
  │   ├── caso
  │   ├── comando
  │   ├── consulta
  │   └── dto
  └── infraestructura
      ├── adaptador
      ├── configuracion
      ├── persistencia
      ├── xml
      ├── http
      └── soap
```

### Ejemplo para `ublkit-gateway`

```text
ublkit-gateway/
  ├── dominio
  │   ├── modelo
  │   ├── puerto
  │   └── servicio
  ├── aplicacion
  │   ├── caso
  │   └── dto
  └── infraestructura
      ├── rest
      ├── soap
      ├── sunat
      └── parser
```

---

## 7. Buenas prácticas obligatorias

## 7.1. Inmutabilidad por defecto

Usar objetos inmutables donde sea posible.

- `record` cuando tenga sentido
- builders para objetos complejos
- evitar setters públicos en objetos de valor

---

## 7.2. Excepciones tipadas

No lanzar `RuntimeException` genéricas para todo.

### Crear excepciones claras

- `ExcepcionUblKit`
- `ExcepcionValidacionDocumento`
- `ExcepcionFirmaXml`
- `ExcepcionTransporte`
- `ExcepcionAutenticacionSunat`

---

## 7.3. Nada de clases utilitarias gigantes

Evitar `*Utils` con decenas de métodos sin cohesión.

Si algo merece existir, probablemente merece un nombre de servicio real.

---

## 7.4. Nada de “Dios classes”

Evitar clases que:

- construyen
- validan
- firman
- envían
- interpretan respuesta

Todo en una misma unidad.

---

## 7.5. API pública pequeña y elegante

La API visible al usuario debe ser más pequeña que la implementación interna.

---

## 7.6. Tests desde el inicio

Cada módulo debe nacer con:

- tests unitarios
- tests de contrato
- casos borde

---

## 7.7. Backward compatibility cuidada

Como será librería comunitaria, romper API pública debe ser una decisión consciente.

---

## 8. Clases iniciales sugeridas para empezar hoy

## 8.1. `ublkit-core`

### `TipoDocumento.java`

```java
package com.cna.ublkit.core.enumerado;

/**
 * Enumera los tipos documentales principales soportados por UBLKit.
 *
 * <p>Esta enumeración representa la naturaleza funcional del documento y
 * permite tomar decisiones de validación, serialización y transporte.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public enum TipoDocumento {
    FACTURA,
    BOLETA,
    NOTA_CREDITO,
    NOTA_DEBITO,
    GUIA_REMISION_REMITENTE,
    GUIA_REMISION_TRANSPORTISTA,
    RESUMEN_DIARIO,
    COMUNICACION_BAJA
}
```

### `TipoAmbiente.java`

```java
package com.cna.ublkit.core.enumerado;

/**
 * Representa el ambiente de operación hacia servicios externos.
 *
 * <p>Este tipo se utiliza para resolver endpoints, credenciales y políticas
 * de integración según el contexto de ejecución.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public enum TipoAmbiente {
    BETA,
    PRODUCCION
}
```

### `ProtocoloTransporte.java`

```java
package com.cna.ublkit.core.enumerado;

/**
 * Define el protocolo de transporte usado para la comunicación externa.
 *
 * <p>Se utiliza principalmente en módulos de integración para determinar
 * el mecanismo de envío o consulta.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public enum ProtocoloTransporte {
    SOAP,
    REST
}
```

### `NumeroSerie.java`

```java
package com.cna.ublkit.core.valor;

import java.util.Objects;

/**
 * Representa la serie de un documento.
 *
 * <p>Este objeto de valor encapsula la serie documental y permite centralizar
 * futuras validaciones de formato.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public final class NumeroSerie {

    private final String valor;

    /**
     * Crea una nueva serie documental.
     *
     * @param valor valor textual de la serie
     */
    public NumeroSerie(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La serie no puede ser nula ni vacía");
        }
        this.valor = valor;
    }

    /**
     * Retorna el valor textual de la serie.
     *
     * @return valor de la serie
     */
    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumeroSerie that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
```

### `ExcepcionUblKit.java`

```java
package com.cna.ublkit.core.error;

/**
 * Excepción base del proyecto UBLKit.
 *
 * <p>Todas las excepciones funcionales o técnicas específicas del proyecto
 * deberían heredar de esta clase para facilitar manejo uniforme.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public class ExcepcionUblKit extends RuntimeException {

    /**
     * Crea una excepción con mensaje.
     *
     * @param mensaje detalle del error
     */
    public ExcepcionUblKit(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una excepción con mensaje y causa.
     *
     * @param mensaje detalle del error
     * @param causa excepción original
     */
    public ExcepcionUblKit(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
```

---

## 8.2. `ublkit-validation`

### `SeveridadValidacion.java`

```java
package com.cna.ublkit.validation.modelo;

/**
 * Define la severidad de una incidencia encontrada durante la validación.
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public enum SeveridadValidacion {
    ERROR,
    ADVERTENCIA
}
```

### `IncidenciaValidacion.java`

```java
package com.cna.ublkit.validation.modelo;

/**
 * Representa una incidencia detectada durante el proceso de validación.
 *
 * @param codigo código interno de la incidencia
 * @param mensaje descripción legible del problema detectado
 * @param severidad severidad asociada a la incidencia
 *
 * @author Edwin Luis Barboza Pinedo Luis Barboza 
 * @since 0.1.0
 */
public record IncidenciaValidacion(
        String codigo,
        String mensaje,
        SeveridadValidacion severidad
) {
}
```

### `ResultadoValidacion.java`

```java
package com.cna.ublkit.validation.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resultado de una validación funcional o estructural.
 *
 * <p>Permite acumular errores y advertencias sin interrumpir el flujo con
 * excepciones, lo que mejora la experiencia del desarrollador.</p>
 *
 * @author Edwin Luis Barboza Pinedo Luis Barboza 
 * @since 0.1.0
 */
public final class ResultadoValidacion {

    private final List<IncidenciaValidacion> incidencias = new ArrayList<>();

    /**
     * Agrega una incidencia al resultado.
     *
     * @param incidencia incidencia a registrar
     */
    public void agregar(IncidenciaValidacion incidencia) {
        this.incidencias.add(incidencia);
    }

    /**
     * Indica si la validación no contiene errores.
     *
     * @return {@code true} si no existen incidencias de severidad ERROR
     */
    public boolean esValido() {
        return incidencias.stream()
                .noneMatch(i -> i.severidad() == SeveridadValidacion.ERROR);
    }

    /**
     * Retorna las incidencias registradas.
     *
     * @return lista inmodificable de incidencias
     */
    public List<IncidenciaValidacion> getIncidencias() {
        return Collections.unmodifiableList(incidencias);
    }
}
```

### `Validador.java`

```java
package com.cna.ublkit.validation.api;

import com.cna.ublkit.validation.modelo.ResultadoValidacion;

/**
 * Contrato base para validadores del sistema.
 *
 * @param <T> tipo de objeto a validar
 *
 * @author Edwin Luis Barboza Pinedo Luis Barboza Pinedo
 * @since 0.1.0
 */
public interface Validador<T> {

    /**
     * Ejecuta la validación sobre el objeto recibido.
     *
     * @param objetivo objeto a validar
     * @return resultado de validación con errores y advertencias acumuladas
     */
    ResultadoValidacion validar(T objetivo);
}
```

---

## 9. Documentación que el proyecto sí o sí debe tener

Estos archivos no son opcionales en un proyecto comunitario serio.

## 9.1. `README.md`

Debe incluir:

- qué es UBLKit
- objetivos
- módulos
- estado del proyecto
- ejemplo mínimo de uso
- roadmap resumido
- cómo contribuir

---

## 9.2. `ARCHITECTURE.md`

Debe incluir:

- visión de arquitectura
- hexagonal
- reglas de dependencia
- responsabilidades por módulo
- decisiones clave

---

## 9.3. `MODULES.md`

Debe incluir:

- qué hace cada módulo
- qué depende de qué
- qué no debe contener cada módulo

---

## 9.4. `NAMING.md`

Debe incluir:

- convenciones de nombres
- nombres permitidos y no permitidos
- criterios para clases, interfaces, paquetes, métodos y excepciones

---

## 9.5. `CONTRIBUTING.md`

Debe incluir:

- cómo levantar el proyecto
- convenciones de commits
- estilo de código
- cómo abrir PRs
- criterios de revisión

---

## 9.6. `ROADMAP.md`

Debe incluir:

- MVP
- fases
- prioridades
- módulos en desarrollo
- funcionalidades futuras

---

## 9.7. `DECISIONS.md` o carpeta `docs/adr`

Debe incluir decisiones de arquitectura:

- por qué Java puro en el núcleo
- por qué hexagonal
- por qué separar Spring/Quarkus
- por qué no exponer XML crudo como API principal

Esto evita olvidar por qué se tomó una decisión.

---

## 9.8. `CHANGELOG.md`

Necesario si será una librería pública.

---

## 9.9. `LICENSE`

Obligatorio en open source.

---

## 9.10. `CODE_OF_CONDUCT.md`

Muy recomendable para comunidad.

---

## 10. Javadocs: regla obligatoria

Sí o sí debes documentar con Javadoc:

- toda interfaz pública
- toda clase pública
- enums públicos
- records públicos
- métodos públicos relevantes
- excepciones públicas

### No hace falta sobredocumentar

No documentes trivialidades obvias línea por línea.

### Sí documenta

- intención
- contrato
- invariantes
- precondiciones
- postcondiciones
- efectos laterales
- ejemplos

---

## 11. Estándar de comentarios

## Sí

- comentarios para explicar decisiones
- comentarios para explicar reglas de negocio
- comentarios para advertir limitaciones
- Javadocs claros

## No

- comentarios que repitan literalmente el código
- comentarios obvios
- comentarios desactualizados

---

## 12. Roadmap técnico recomendado

## Etapa 1

- `ublkit-core`
- `ublkit-validation`
- `README.md`
- `ARCHITECTURE.md`
- `MODULES.md`
- `NAMING.md`

## Etapa 2

- `ublkit-ubl` con una primera factura mínima
- serialización XML básica
- tests

## Etapa 3

- `ublkit-sign`
- firma XML
- tests con certificados

## Etapa 4

- `ublkit-gateway`
- contratos primero
- fake transport
- luego integración real

## Etapa 5

- `ublkit-render`
- HTML primero
- PDF después

## Etapa 6

- `ublkit-testkit`

## Etapa 7

- `ublkit-spring-boot-starter`
- `ublkit-quarkus`

---

No empieces por:

- Spring
- Quarkus
- SOAP
- PDF
- todos los documentos a la vez

Empieza por:

1. un núcleo limpio
2. validación bien modelada
3. una factura mínima
4. XML bien serializado
5. firma
6. recién después integración externa

---
