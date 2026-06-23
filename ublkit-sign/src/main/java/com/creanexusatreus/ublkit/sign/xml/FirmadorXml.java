package com.cna.ublkit.sign.xml;

import com.cna.ublkit.core.error.ExcepcionUblKit;
import com.cna.ublkit.sign.certificado.DetallesCertificado;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Firma documentos XML UBL con certificado digital X.509.
 * <p>
 * Implementa la firma enveloped (dentro del propio documento) utilizando
 * SHA-1 digest y RSA-SHA1 signature, conforme a los requisitos de SUNAT.
 * La firma se inserta dentro de {@code ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent}.
 * </p>
 *
 * @since 0.1.0
 */
public final class FirmadorXml {

    private static final String TAG_UBL_EXTENSIONS = "ext:UBLExtensions";
    private static final String TAG_UBL_EXTENSION = "ext:UBLExtension";
    private static final String TAG_EXTENSION_CONTENT = "ext:ExtensionContent";
    private static final String C14N_INCLUSIVA = CanonicalizationMethod.INCLUSIVE;

    private FirmadorXml() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciable");
    }

    /**
     * Firma un XML representado como String.
     *
     * @param xml           Contenido XML a firmar.
     * @param idReferencia  ID de la referencia de firma (ej. "SignSUNAT").
     * @param certificado   Detalles del certificado con clave privada.
     * @return Documento DOM firmado.
     * @throws ExcepcionUblKit si ocurre un error al firmar.
     */
    public static Document firmar(String xml, String idReferencia, DetallesCertificado certificado) {
        try {
            Document documento = XmlHelper.convertirStringADocumento(xml);
            return firmar(documento, idReferencia, certificado);
        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error al parsear XML para firma: " + e.getMessage(), e);
        }
    }

    /**
     * Firma un documento DOM existente.
     *
     * @param documento     Documento DOM a firmar.
     * @param idReferencia  ID de la referencia de firma.
     * @param certificado   Detalles del certificado con clave privada.
     * @return Nuevo Documento DOM firmado (clonado, se preserva la inmutabilidad del original).
     * @throws ExcepcionUblKit si ocurre un error al firmar.
     */
    public static Document firmar(Document documento, String idReferencia, DetallesCertificado certificado) {
        try {
            Document docFirma = (Document) documento.cloneNode(true);
            XmlHelper.compactarEstructura(docFirma);

            asegurarUBLExtensions(docFirma);
            asegurarUBLExtension(docFirma);
            Node nodoContenido = asegurarExtensionContent(docFirma);

            DOMSignContext contextoFirma = new DOMSignContext(
                    certificado.clavePrivada(),
                    docFirma.getDocumentElement()
            );
            contextoFirma.setDefaultNamespacePrefix("ds");
            contextoFirma.setParent(nodoContenido);

            XMLSignatureFactory fabrica = XMLSignatureFactory.getInstance("DOM");

            Reference referencia = fabrica.newReference(
                    "",
                    fabrica.newDigestMethod(DigestMethod.SHA1, null),
                    java.util.List.of(
                        fabrica.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                        fabrica.newTransform(C14N_INCLUSIVA, (TransformParameterSpec) null)
                    ),
                    null,
                    null
            );

            SignedInfo infoFirmada = fabrica.newSignedInfo(
                    fabrica.newCanonicalizationMethod(
                        C14N_INCLUSIVA,
                            (C14NMethodParameterSpec) null
                    ),
                    fabrica.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                    Collections.singletonList(referencia)
            );

            KeyInfoFactory keyInfoFactory = fabrica.getKeyInfoFactory();
            X509Data datosX509 = keyInfoFactory.newX509Data(
                    Collections.singletonList(certificado.certificado())
            );
            KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(datosX509));

            XMLSignature firma = fabrica.newXMLSignature(infoFirmada, keyInfo, null, idReferencia, null);
            firma.sign(contextoFirma);

            return docFirma;

        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error al firmar XML: " + e.getMessage(), e);
        }
    }

    /**
     * Firma un XML y retorna bytes compactos/minificados.
     * Esta es la salida recomendada para transporte hacia SUNAT.
     */
    public static byte[] firmarComoBytes(String xml, String idReferencia, DetallesCertificado certificado) {
        Document documentoFirmado = firmar(xml, idReferencia, certificado);
        try {
            return XmlHelper.documentoABytesMinificado(documentoFirmado);
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error serializando XML firmado: " + e.getMessage(), e);
        }
    }

    /**
     * Firma un XML y retorna String compacto/minificado.
     * Evita exposición de DOM mutable en el flujo de integración.
     */
    public static String firmarComoString(String xml, String idReferencia, DetallesCertificado certificado) {
        byte[] bytes = firmarComoBytes(xml, idReferencia, certificado);
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    // ── Estructura UBL Extensions ────────────────────────────────

    private static void asegurarUBLExtensions(Document documento) {
        NodeList lista = documento.getDocumentElement().getElementsByTagName(TAG_UBL_EXTENSIONS);
        if (lista.item(0) == null) {
            Element documentElement = documento.getDocumentElement();
            Node nodo = documento.createElement(TAG_UBL_EXTENSIONS);
            documentElement.insertBefore(nodo, documentElement.getFirstChild());
        }
    }

    private static void asegurarUBLExtension(Document documento) {
        NodeList listaExtensions = documento.getDocumentElement().getElementsByTagName(TAG_UBL_EXTENSIONS);
        NodeList listaExtension = documento.getDocumentElement().getElementsByTagName(TAG_UBL_EXTENSION);
        if (listaExtension.item(0) == null) {
            Node nodo = documento.createElement(TAG_UBL_EXTENSION);
            listaExtensions.item(0).appendChild(nodo);
        }
    }

    private static Node asegurarExtensionContent(Document documento) {
        NodeList listaExtension = documento.getDocumentElement().getElementsByTagName(TAG_UBL_EXTENSION);
        NodeList listaContent = documento.getDocumentElement().getElementsByTagName(TAG_EXTENSION_CONTENT);
        Node nodoContenido = listaContent.item(0);
        if (nodoContenido == null) {
            nodoContenido = documento.createElement(TAG_EXTENSION_CONTENT);
            listaExtension.item(0).appendChild(nodoContenido);
        }
        return nodoContenido;
    }
}
