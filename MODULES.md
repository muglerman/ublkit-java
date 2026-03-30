# Módulos del Proyecto UBLKit

UBLKit se divide en unidades compuestas de manera jerárquica para evitar librerías monolíticas y facilitar la extensión.

### 1. `ublkit-core` (15 clases)
- **Responsabilidad:** Define tipos de dato general del paradigma (TiposDocumento, TipoAmbiente, resultado global de operaciones).
- **Contenido:** Enums base (TipoDocumento, TipoAmbiente, ProtocoloTransporte), Value Objects (NumeroSerie, Moneda, Dinero, IdentificadorDocumento), resultado genérico (`ResultadoOperacion<T>`), Excepciones (ExcepcionUblKit, ExcepcionSerializacionXml, ExcepcionEnsamblaje, ExcepcionValidacion, ExcepcionTransporte, ExcepcionAutenticacionSunat, ExcepcionFirmaDigital).
- **No debe contener:** Interacciones SOAP, JAXB, Spring, Quarkus.

### 2. `ublkit-catalogs` (3 clases + 35 CSVs)
- **Responsabilidad:** Gestión de catálogos normativos SUNAT (Catálogo 01-60).
- **Funcionalidad:** Búsqueda por código (ProveedorCatalogos), caché concurrente (LectorCsvCatalogos), validación de existencia.

### 3. `ublkit-validation` (12 clases)
- **Responsabilidad:** Procesar de forma íntegra un documento y devolver un `ResultadoValidacion` con errores y advertencias.
- **Contenido:** Validador<T> (interfaz), ResultadoValidacion, IncidenciaValidacion, SeveridadValidacion. Validadores: Factura, NotaCredito, NotaDebito, GuiaRemision, ResumenDiario, ComunicacionBaja, Percepcion, Retencion.
- **Homologación SUNAT:** puente XSL embebido (`ValidadorSunatXsl`) + catálogo de reglas (`ReglaSunatXsl`) para validar Factura/Boleta, Notas, Guías, Resumen Diario y Comunicación de Baja con recursos empaquetados en `ublkit-validation`.
- **Principio:** No interrumpe flujos por el primer fallo.

### 4. `ublkit-ubl` (69 clases, 47 tests)
- **Responsabilidad:** Mapeo del modelo de dominio amigable al estándar XML UBL 2.1.
- **Modelo (52 clases):** DocumentoBase (sealed) → BorradorFactura, BorradorNotaCredito, BorradorNotaDebito. BorradorGuiaRemision + 17 records. Modelos SUNAT: ComunicacionBaja, ResumenDiario, Percepción, Retención.
- **Ensambladores (4):** EnsambladorFactura, EnsambladorNota, EnsambladorPercepcion, EnsambladorRetencion.
- **Serialización XML (11):** SerializadorXml<T>, SerializadorXmlFactura, SerializadorXmlNotaCredito, SerializadorXmlNotaDebito, SerializadorXmlGuiaRemision, SerializadorXmlComunicacionBaja, SerializadorXmlResumenDiario, SerializadorXmlPercepcion, SerializadorXmlRetencion, ConstantesUbl, XmlUblHelper.

### 5. `ublkit-sign` (7 clases, 8 tests)
- **Responsabilidad:** Firma digital de archivos XML con estándar X509.
- **Contenido:** FirmadorXml (RSA-SHA1 enveloped), CargadorCertificado (PKCS12), ServicioFirma (fachada), XmlHelper (DOM seguro).

### 6. `ublkit-gateway` (18 clases, 8 tests)
- **Responsabilidad:** Envío a SUNAT u OSE mediante protocolos SOAP y REST (API Guías).
- **Contenido:** PasarelaSunat (interfaz + impl), ClienteSoap/ClienteRest (nativos con java.net.http), ProveedorToken (OAuth2), ResolvedorEndpoints, LectorCdr, ZipHelper, HashHelper.
- **Modelo de respuesta:** ResultadoEnvio, ResultadoConsulta, ArchivoCdr, EstadoEnvio.

### 7. `ublkit-render` (15 clases + templates HTML y SUNAT, 3 tests)
- **Responsabilidad:** Representación humana de los documentos.
- **Soporte:** HTML (Pebble templates), PDF (OpenHTMLtoPDF/JasperReports) y Tickets térmicos (58mm/80mm).
- **Renderizadores:** Factura (HTML/PDF/Ticket), Nota (HTML/PDF), GuiaRemision (HTML/PDF), ResumenDiario (HTML/PDF), ComunicacionBaja (HTML/PDF).
- **Plantillas SUNAT:** incluye `.jrxml` de referencia para Factura/Boleta, Notas Crédito/Débito y Guía de Remisión empaquetadas en `ublkit-render`.

### 8. `ublkit-testkit` (9 clases)
- **Responsabilidad:** Acelerar el desarrollo con mocks de pasarela, fixtures de documentos y aserciones personalizadas.
- **Contenido:** FacturasEjemplo, NotasCreditoEjemplo, NotasDebitoEjemplo, GuiasRemisionEjemplo, SimuladorGateway, AssertsXml, AssertsCdr, RespuestasSunatSimuladas, GoldenXml.

---

### Integraciones (Spring Boot & Quarkus)
Proveen autoconfiguración (`Beans`, inyección, propiedades) basadas en los *ports* y servicios exportados de los módulos listados previamente.
- **Spring Boot Starter**: Autoconfigura serializadores XML, firmador y renderizadores.
- **Quarkus**: Productores CDI equivalentes para un arranque rápido en contenedores.
