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

    public FirmadorXml() {
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
     * @return Documento DOM firmado (mismo objeto, modificado in-place).
     * @throws ExcepcionUblKit si ocurre un error al firmar.
     */
    public static Document firmar(Document documento, String idReferencia, DetallesCertificado certificado) {
        try {
            asegurarUBLExtensions(documento);
            asegurarUBLExtension(documento);
            Node nodoContenido = asegurarExtensionContent(documento);

            DOMSignContext contextoFirma = new DOMSignContext(
                    certificado.clavePrivada(),
                    documento.getDocumentElement()
            );
            contextoFirma.setDefaultNamespacePrefix("ds");
            contextoFirma.setParent(nodoContenido);

            XMLSignatureFactory fabrica = XMLSignatureFactory.getInstance("DOM");

            Reference referencia = fabrica.newReference(
                    "",
                    fabrica.newDigestMethod(DigestMethod.SHA1, null),
                    Collections.singletonList(
                            fabrica.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)
                    ),
                    null,
                    null
            );

            SignedInfo infoFirmada = fabrica.newSignedInfo(
                    fabrica.newCanonicalizationMethod(
                            CanonicalizationMethod.INCLUSIVE,
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

            return documento;

        } catch (ExcepcionUblKit e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionUblKit("Error al firmar XML: " + e.getMessage(), e);
        }
    }

    // ── Estructura UBL Extensions ────────────────────────────────

    private static void asegurarUBLExtensions(Document documento) {
        NodeList lista = documento.getDocumentElement().getElementsByTagName("ext:UBLExtensions");
        if (lista.item(0) == null) {
            Element documentElement = documento.getDocumentElement();
            Node nodo = documento.createElement("ext:UBLExtensions");
            documentElement.insertBefore(nodo, documentElement.getFirstChild());
        }
    }

    private static void asegurarUBLExtension(Document documento) {
        NodeList listaExtensions = documento.getDocumentElement().getElementsByTagName("ext:UBLExtensions");
        NodeList listaExtension = documento.getDocumentElement().getElementsByTagName("ext:UBLExtension");
        if (listaExtension.item(0) == null) {
            Node nodo = documento.createElement("ext:UBLExtension");
            listaExtensions.item(0).appendChild(nodo);
        }
    }

    private static Node asegurarExtensionContent(Document documento) {
        NodeList listaExtension = documento.getDocumentElement().getElementsByTagName("ext:UBLExtension");
        NodeList listaContent = documento.getDocumentElement().getElementsByTagName("ext:ExtensionContent");
        Node nodoContenido = listaContent.item(0);
        if (nodoContenido == null) {
            nodoContenido = documento.createElement("ext:ExtensionContent");
            listaExtension.item(0).appendChild(nodoContenido);
        }
        return nodoContenido;
    }
}
