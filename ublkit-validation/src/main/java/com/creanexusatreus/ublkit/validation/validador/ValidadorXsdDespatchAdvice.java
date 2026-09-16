package com.creanexusatreus.ublkit.validation.validador;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/** Validación estructural local contra el XSD UBL 2.1 de DespatchAdvice publicado por SUNAT. */
final class ValidadorXsdDespatchAdvice {

    private static final Logger LOG = Logger.getLogger(ValidadorXsdDespatchAdvice.class.getName());
    private static final String SCHEMA_RESOURCE =
            "sunat/schema/ubl-2.1/maindoc/UBL-DespatchAdvice-2.1.xsd";
    private static final String COMMON_SCHEMA_PREFIX = "sunat/schema/ubl-2.1/common/";
    private static final Set<String> COMMON_SCHEMAS = Set.of(
            "CCTS_CCT_SchemaModule-2.1.xsd",
            "UBL-CommonAggregateComponents-2.1.xsd",
            "UBL-CommonBasicComponents-2.1.xsd",
            "UBL-CommonExtensionComponents-2.1.xsd",
            "UBL-CommonSignatureComponents-2.1.xsd",
            "UBL-CoreComponentParameters-2.1.xsd",
            "UBL-ExtensionContentDataType-2.1.xsd",
            "UBL-QualifiedDataTypes-2.1.xsd",
            "UBL-SignatureAggregateComponents-2.1.xsd",
            "UBL-SignatureBasicComponents-2.1.xsd",
            "UBL-UnqualifiedDataTypes-2.1.xsd",
            "UBL-XAdESv132-2.1.xsd",
            "UBL-XAdESv141-2.1.xsd",
            "UBL-xmldsig-core-schema-2.1.xsd");
    private static final Object SCHEMA_LOCK = new Object();
    private static volatile Schema schema;

    private ValidadorXsdDespatchAdvice() {
    }

    static void validar(String xml) throws SAXException {
        try {
            obtenerSchema().newValidator().validate(new DOMSource(documentoConExtensionDeFirma(xml)));
        } catch (java.io.IOException e) {
            throw new SAXException("No se pudo leer el XML GRE para validación XSD", e);
        } catch (Exception e) {
            if (e instanceof SAXException saxException) {
                throw saxException;
            }
            throw new SAXException("No se pudo preparar el XML GRE para validación XSD", e);
        }
    }

    private static Schema obtenerSchema() throws SAXException {
        Schema cacheado = schema;
        if (cacheado != null) {
            return cacheado;
        }
        synchronized (SCHEMA_LOCK) {
            if (schema == null) {
                schema = cargarSchema(ValidadorXsdDespatchAdvice.class.getClassLoader());
            }
            return schema;
        }
    }

    /**
     * UBL exige contenido no vacío en ext:ExtensionContent. La validación ocurre antes de la firma, por lo que se
     * coloca una extensión ajena mínima en memoria únicamente para validar la estructura del documento sin alterarlo.
     */
    private static Document documentoConExtensionDeFirma(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        factory.setNamespaceAware(true);
        Document document = factory.newDocumentBuilder().parse(new org.xml.sax.InputSource(new StringReader(xml)));
        var extensions = document.getElementsByTagNameNS(
                "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2", "ExtensionContent");
        for (int i = 0; i < extensions.getLength(); i++) {
            Element extensionContent = (Element) extensions.item(i);
            if (!extensionContent.hasChildNodes()) {
                extensionContent.appendChild(document.createElementNS(
                        "urn:ublkit:prevalidation", "pre:Placeholder"));
            }
        }
        return document;
    }

    private static Schema cargarSchema(ClassLoader classLoader) throws SAXException {
        try {
            InputStream input = classLoader.getResourceAsStream(SCHEMA_RESOURCE);
            if (input == null) {
                throw new IllegalStateException("No se encontró el XSD UBL 2.1: " + SCHEMA_RESOURCE);
            }
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            factory.setResourceResolver(new ResolverXsdClasspath(classLoader));
            try (input) {
                StreamSource source = new StreamSource(input);
                source.setSystemId("classpath:/" + SCHEMA_RESOURCE);
                return factory.newSchema(source);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "No se pudo cargar el bundle XSD UBL 2.1 de DespatchAdvice", e);
            throw new SAXException("No se pudo cargar el XSD UBL 2.1 de DespatchAdvice", e);
        }
    }

    static LSResourceResolver resolverParaPruebas(ClassLoader classLoader) {
        return new ResolverXsdClasspath(classLoader);
    }

    private static final class ResolverXsdClasspath implements LSResourceResolver {
        private final ClassLoader classLoader;

        private ResolverXsdClasspath(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        public LSInput resolveResource(String type, String namespaceUri, String publicId, String systemId,
                                       String baseUri) {
            String nombre = nombreDeSchema(systemId);
            if (!COMMON_SCHEMAS.contains(nombre)) {
                throw new LSException(LSException.PARSE_ERR,
                        "Import XSD no permitido fuera del bundle UBL 2.1: " + systemId);
            }
            String recurso = COMMON_SCHEMA_PREFIX + nombre;
            InputStream input = classLoader.getResourceAsStream(recurso);
            if (input == null) {
                throw new LSException(LSException.PARSE_ERR, "No se encontró el XSD UBL 2.1: " + recurso);
            }
            return new EntradaClasspath(publicId, "classpath:/" + recurso, input);
        }

        private String nombreDeSchema(String systemId) {
            if (systemId == null || systemId.isBlank() || systemId.contains("\\") || systemId.contains(":")) {
                return "";
            }
            int slash = systemId.lastIndexOf('/');
            return slash >= 0 ? systemId.substring(slash + 1) : systemId;
        }
    }

    private static final class EntradaClasspath implements LSInput {
        private String publicId;
        private String systemId;
        private InputStream byteStream;

        private EntradaClasspath(String publicId, String systemId, InputStream byteStream) {
            this.publicId = publicId;
            this.systemId = systemId;
            this.byteStream = byteStream;
        }

        @Override public java.io.Reader getCharacterStream() { return null; }
        @Override public void setCharacterStream(java.io.Reader value) { }
        @Override public InputStream getByteStream() { return byteStream; }
        @Override public void setByteStream(InputStream value) { byteStream = value; }
        @Override public String getStringData() { return null; }
        @Override public void setStringData(String value) { }
        @Override public String getSystemId() { return systemId; }
        @Override public void setSystemId(String value) { systemId = value; }
        @Override public String getPublicId() { return publicId; }
        @Override public void setPublicId(String value) { publicId = value; }
        @Override public String getBaseURI() { return null; }
        @Override public void setBaseURI(String value) { }
        @Override public String getEncoding() { return null; }
        @Override public void setEncoding(String value) { }
        @Override public boolean getCertifiedText() { return false; }
        @Override public void setCertifiedText(boolean value) { }
    }
}
