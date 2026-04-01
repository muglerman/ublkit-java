package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.core.modelo.Contacto;
import com.cna.ublkit.core.modelo.Direccion;
import com.cna.ublkit.ubl.modelo.DocumentoBase;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.cna.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.cna.ublkit.ubl.modelo.linea.CargoDescuento;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.List;

import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Constructores de fragmentos XML UBL 2.1 compartidos entre todos los serializadores.
 *
 * @since 0.1.0
 */
final class FragmentosXml {

    private FragmentosXml() {
    }

    private static final String TAG_PARTY_IDENTIFICATION = "PartyIdentification";
    private static final String TAG_TAX_TOTAL = "TaxTotal";
    private static final String TAG_TAX_SUBTOTAL = "TaxSubtotal";
    private static final String TAG_TAXABLE_AMOUNT = "TaxableAmount";
    private static final String TAG_TAX_AMOUNT = "TaxAmount";
    private static final String TAG_TAX_CATEGORY = "TaxCategory";
    private static final String TAG_TAX_SCHEME = "TaxScheme";
    private static final String TAG_TAX_TYPE_CODE = "TaxTypeCode";
    private static final String ATTR_SCHEME_ID = "schemeID";
    private static final String ATTR_SCHEME_NAME = "schemeName";
    private static final String ATTR_SCHEME_AGENCY_NAME = "schemeAgencyName";
    private static final String ATTR_LIST_AGENCY_NAME = "listAgencyName";
    private static final String ATTR_LIST_NAME = "listName";
    private static final String VALUE_DOC_IDENTIDAD = "Documento de Identidad";
    private static final String VALUE_PE_SUNAT = "PE:SUNAT";
    private static final String VALUE_UN_ECE_5305 = "UN/ECE 5305";
    private static final String VALUE_TAX_CATEGORY_IDENTIFIER = "Tax Category Identifier";
    private static final String VALUE_UN_ECE_5153 = "UN/ECE 5153";
    private static final String VALUE_CODIGO_TRIBUTOS = "Codigo de tributos";
    private static final String VALUE_UN_ECE_AGENCY = "United Nations Economic Commission for Europe";

    // ── UBL Extensions ───────────────────────────────────────────

    static void agregarExtensiones(Document doc, Element raiz) {
        Element extensions = ext(doc, "UBLExtensions");
        Element extension = ext(doc, "UBLExtension");
        Element content = ext(doc, "ExtensionContent");
        extension.appendChild(content);
        extensions.appendChild(extension);
        raiz.appendChild(extensions);
    }

    // ── Datos generales ──────────────────────────────────────────

    static void agregarDatosGenerales(Document doc, Element raiz, DocumentoBase documento) {
        raiz.appendChild(cbc(doc, "UBLVersionID", ConstantesUbl.UBL_VERSION));
        raiz.appendChild(cbc(doc, "CustomizationID", ConstantesUbl.CUSTOMIZATION_ID));
        raiz.appendChild(cbc(doc, "ID", documento.getSerie() + "-" + documento.getNumero()));
        raiz.appendChild(cbc(doc, "IssueDate", documento.getFechaEmision()));
        if (documento.getHoraEmision() != null) {
            raiz.appendChild(cbc(doc, "IssueTime", documento.getHoraEmision()));
        }
    }

    // ── Firma ────────────────────────────────────────────────────

    static void agregarFirma(Document doc, Element raiz, DocumentoBase documento) {
        FirmanteDocumento firmante = documento.getFirmante();
        if (firmante == null && documento.getEmisor() != null) {
            firmante = new FirmanteDocumento(documento.getEmisor().ruc(), documento.getEmisor().razonSocial());
        }
        if (firmante == null) return;

        Element signature = cac(doc, "Signature");
        signature.appendChild(cbc(doc, "ID", firmante.ruc()));

        Element signatoryParty = cac(doc, "SignatoryParty");
        Element partyId = cac(doc, TAG_PARTY_IDENTIFICATION);
        partyId.appendChild(cbc(doc, "ID", firmante.ruc()));
        signatoryParty.appendChild(partyId);

        Element partyName = cac(doc, "PartyName");
        partyName.appendChild(cbcCdata(doc, "Name", firmante.razonSocial()));
        signatoryParty.appendChild(partyName);
        signature.appendChild(signatoryParty);

        Element attachment = cac(doc, "DigitalSignatureAttachment");
        Element extRef = cac(doc, "ExternalReference");
        extRef.appendChild(cbc(doc, "URI", "#UBLKIT-SIGN"));
        attachment.appendChild(extRef);
        signature.appendChild(attachment);

        raiz.appendChild(signature);
    }

    // ── Proveedor (Emisor) ───────────────────────────────────────

    static void agregarEmisor(Document doc, Element raiz, EmisorDocumento emisor) {
        if (emisor == null) return;

        Element supplier = cac(doc, "AccountingSupplierParty");
        Element party = cac(doc, "Party");

        // PartyIdentification
        Element partyId = cac(doc, TAG_PARTY_IDENTIFICATION);
        Element id = cbcConAtributos(doc, "ID", emisor.ruc(),
                ATTR_SCHEME_ID, "6",
                ATTR_SCHEME_NAME, VALUE_DOC_IDENTIDAD,
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
        partyId.appendChild(id);
        party.appendChild(partyId);

        // PartyName
        if (emisor.nombreComercial() != null) {
            Element partyName = cac(doc, "PartyName");
            partyName.appendChild(cbc(doc, "Name", emisor.nombreComercial()));
            party.appendChild(partyName);
        }

        // PartyLegalEntity
        Element legalEntity = cac(doc, "PartyLegalEntity");
        legalEntity.appendChild(cbcCdata(doc, "RegistrationName", emisor.razonSocial()));
        if (emisor.direccion() != null) {
            Element regAddress = cac(doc, "RegistrationAddress");
            agregarDireccion(doc, regAddress, emisor.direccion());
            legalEntity.appendChild(regAddress);
        }
        party.appendChild(legalEntity);

        // Contact
        if (emisor.contacto() != null) {
            party.appendChild(crearContacto(doc, emisor.contacto()));
        }

        supplier.appendChild(party);
        raiz.appendChild(supplier);
    }

    // ── Cliente (Receptor) ───────────────────────────────────────

    static void agregarReceptor(Document doc, Element raiz, ReceptorDocumento receptor) {
        if (receptor == null) return;

        Element customer = cac(doc, "AccountingCustomerParty");
        Element party = cac(doc, "Party");

        // PartyIdentification
        Element partyId = cac(doc, TAG_PARTY_IDENTIFICATION);
        Element id = cbcConAtributos(doc, "ID", receptor.numDocIdentidad(),
                ATTR_SCHEME_ID, receptor.tipoDocIdentidad(),
                ATTR_SCHEME_NAME, VALUE_DOC_IDENTIDAD,
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06");
        partyId.appendChild(id);
        party.appendChild(partyId);

        // PartyLegalEntity
        Element legalEntity = cac(doc, "PartyLegalEntity");
        legalEntity.appendChild(cbcCdata(doc, "RegistrationName", receptor.nombre()));
        if (receptor.direccion() != null) {
            Element regAddress = cac(doc, "RegistrationAddress");
            agregarDireccion(doc, regAddress, receptor.direccion());
            legalEntity.appendChild(regAddress);
        }
        party.appendChild(legalEntity);

        // Contact
        if (receptor.contacto() != null) {
            party.appendChild(crearContacto(doc, receptor.contacto()));
        }

        customer.appendChild(party);
        raiz.appendChild(customer);
    }

    // ── Dirección ────────────────────────────────────────────────

    static void agregarDireccion(Document doc, Element padre, Direccion dir) {
        if (dir.ubigeo() != null) {
            padre.appendChild(cbc(doc, "ID", dir.ubigeo()));
        }
        if (dir.codigoLocal() != null) {
            padre.appendChild(cbc(doc, "AddressTypeCode", dir.codigoLocal()));
        }
        if (dir.urbanizacion() != null) {
            padre.appendChild(cbc(doc, "CitySubdivisionName", dir.urbanizacion()));
        }
        if (dir.provincia() != null) {
            padre.appendChild(cbc(doc, "CityName", dir.provincia()));
        }
        if (dir.departamento() != null) {
            padre.appendChild(cbc(doc, "CountrySubentity", dir.departamento()));
        }
        if (dir.distrito() != null) {
            padre.appendChild(cbc(doc, "District", dir.distrito()));
        }
        if (dir.direccion() != null) {
            Element addressLine = cac(doc, "AddressLine");
            addressLine.appendChild(cbcCdata(doc, "Line", dir.direccion()));
            padre.appendChild(addressLine);
        }
        if (dir.codigoPais() != null) {
            Element country = cac(doc, "Country");
            country.appendChild(cbc(doc, "IdentificationCode", dir.codigoPais()));
            padre.appendChild(country);
        }
    }

    // ── Contacto ─────────────────────────────────────────────────

    static Element crearContacto(Document doc, Contacto contacto) {
        Element contact = cac(doc, "Contact");
        if (contacto.telefono() != null) {
            contact.appendChild(cbc(doc, "Telephone", contacto.telefono()));
        }
        if (contacto.email() != null) {
            contact.appendChild(cbc(doc, "ElectronicMail", contacto.email()));
        }
        return contact;
    }

    // ── Guías relacionadas ───────────────────────────────────────

    static void agregarGuias(Document doc, Element raiz, List<GuiaRelacionada> guias) {
        if (guias == null) return;
        for (GuiaRelacionada guia : guias) {
            Element despatch = cac(doc, "DespatchDocumentReference");
            despatch.appendChild(cbc(doc, "ID", guia.serieNumero()));
            despatch.appendChild(cbc(doc, "DocumentTypeCode", guia.tipoDocumento()));
            raiz.appendChild(despatch);
        }
    }

    // ── Documentos relacionados ──────────────────────────────────

    static void agregarDocumentosRelacionados(Document doc, Element raiz, List<DocumentoRelacionado> docs) {
        if (docs == null) return;
        for (DocumentoRelacionado rel : docs) {
            Element additional = cac(doc, "AdditionalDocumentReference");
            additional.appendChild(cbc(doc, "ID", rel.serieNumero()));
            additional.appendChild(cbc(doc, "DocumentTypeCode", rel.tipoDocumento()));
            raiz.appendChild(additional);
        }
    }

    // ── Tax Total ────────────────────────────────────────────────

    static void agregarTotalImpuestos(Document doc, Element raiz, TotalImpuestos imp, String moneda) {
        if (imp == null) return;

        Element taxTotal = cac(doc, TAG_TAX_TOTAL);
        taxTotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, imp.total(), moneda));

        // ISC
        if (imp.iscImporte() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.iscBaseImponible(), imp.iscImporte(),
                    null, null, null,
                    "2000", "ISC", "EXC"));
        }

        // IGV Gravado
        if (imp.gravadoBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.gravadoBaseImponible(), imp.gravadoImporte(),
                    "S", VALUE_UN_ECE_5305, VALUE_TAX_CATEGORY_IDENTIFIER,
                    "1000", "IGV", "VAT"));
        }

        // Inafecto
        if (imp.inafectoBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.inafectoBaseImponible(), imp.inafectoImporte(),
                    "S", VALUE_UN_ECE_5305, VALUE_TAX_CATEGORY_IDENTIFIER,
                    "9998", "INA", "FRE"));
        }

        // Exonerado
        if (imp.exoneradoBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.exoneradoBaseImponible(), imp.exoneradoImporte(),
                    "S", VALUE_UN_ECE_5305, VALUE_TAX_CATEGORY_IDENTIFIER,
                    "9997", "EXO", "VAT"));
        }

        // Gratuito
        if (imp.gratuitoBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.gratuitoBaseImponible(), imp.gratuitoImporte(),
                    "S", VALUE_UN_ECE_5305, VALUE_TAX_CATEGORY_IDENTIFIER,
                    "9996", "GRA", "FRE"));
        }

        // Exportación
        if (imp.exportacionBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.exportacionBaseImponible(), BigDecimal.ZERO,
                    null, null, null,
                    "9995", "EXP", "FRE"));
        }

        // IVAP
        if (imp.ivapBaseImponible() != null) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda,
                    imp.ivapBaseImponible(), imp.ivapImporte(),
                    "S", VALUE_UN_ECE_5305, VALUE_TAX_CATEGORY_IDENTIFIER,
                    "1016", "IVAP", "VAT"));
        }

        // ICBPER
        if (imp.icbImporte() != null) {
            taxTotal.appendChild(crearSubtotalIcbper(doc, moneda, imp.icbImporte()));
        }

        raiz.appendChild(taxTotal);
    }

    private static Element crearSubtotalImpuesto(Document doc, String moneda,
                                                  BigDecimal base, BigDecimal monto,
                                                  String catId, String catSchemeId, String catSchemeName,
                                                  String tribCode, String tribName, String tribTypeCode) {
        Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
        if (base != null) {
            subtotal.appendChild(cbcMonto(doc, TAG_TAXABLE_AMOUNT, base, moneda));
        }
        subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, monto, moneda));

        Element category = cac(doc, TAG_TAX_CATEGORY);
        if (catId != null) {
            Element categoryId = cbcConAtributos(doc, "ID", catId,
                    ATTR_SCHEME_AGENCY_NAME, VALUE_UN_ECE_AGENCY,
                    ATTR_SCHEME_ID, catSchemeId,
                    ATTR_SCHEME_NAME, catSchemeName);
            category.appendChild(categoryId);
        }

        Element scheme = cac(doc, TAG_TAX_SCHEME);
        Element schemeId = cbcConAtributos(doc, "ID", tribCode,
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                ATTR_SCHEME_ID, VALUE_UN_ECE_5153,
                ATTR_SCHEME_NAME, VALUE_CODIGO_TRIBUTOS);
        scheme.appendChild(schemeId);
        scheme.appendChild(cbc(doc, "Name", tribName));
        scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, tribTypeCode));
        category.appendChild(scheme);

        subtotal.appendChild(category);
        return subtotal;
    }

    private static Element crearSubtotalIcbper(Document doc, String moneda, BigDecimal monto) {
        Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
        subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, monto, moneda));

        Element category = cac(doc, TAG_TAX_CATEGORY);
        category.appendChild(cbcConAtributos(doc, "ID", "S",
                ATTR_SCHEME_AGENCY_NAME, VALUE_UN_ECE_AGENCY,
                ATTR_SCHEME_ID, VALUE_UN_ECE_5305,
                ATTR_SCHEME_NAME, VALUE_TAX_CATEGORY_IDENTIFIER));

        Element scheme = cac(doc, TAG_TAX_SCHEME);
        scheme.appendChild(cbcConAtributos(doc, "ID", "7152",
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                ATTR_SCHEME_ID, VALUE_UN_ECE_5153,
                ATTR_SCHEME_NAME, VALUE_CODIGO_TRIBUTOS));
        scheme.appendChild(cbc(doc, "Name", "ICBPER"));
        scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, "OTH"));
        category.appendChild(scheme);

        subtotal.appendChild(category);
        return subtotal;
    }

    // ── Línea de detalle (TaxTotal de la línea) ──────────────────

    static void agregarImpuestosLinea(Document doc, Element lineaXml, LineaDetalle linea, String moneda) {
        Element taxTotal = cac(doc, TAG_TAX_TOTAL);
        taxTotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, orZero(linea.getTotalImpuestos()), moneda));

        // ISC
        if (linea.getIsc() != null) {
            Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
            subtotal.appendChild(cbcMonto(doc, TAG_TAXABLE_AMOUNT, orZero(linea.getIscBaseImponible()), moneda));
            subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, linea.getIsc(), moneda));

            Element category = cac(doc, TAG_TAX_CATEGORY);
            category.appendChild(cbcConAtributos(doc, "Percent",
                    escalar(linea.getTasaIsc() != null ? linea.getTasaIsc().multiply(new BigDecimal("100")) : BigDecimal.ZERO)));
            if (linea.getIscTipo() != null) {
                category.appendChild(cbc(doc, "TierRange", linea.getIscTipo()));
            }
            Element scheme = cac(doc, TAG_TAX_SCHEME);
            scheme.appendChild(cbc(doc, "ID", "2000"));
            scheme.appendChild(cbc(doc, "Name", "ISC"));
            scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, "EXC"));
            category.appendChild(scheme);

            subtotal.appendChild(category);
            taxTotal.appendChild(subtotal);
        }

        // IGV
        {
            Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
            subtotal.appendChild(cbcMonto(doc, TAG_TAXABLE_AMOUNT, orZero(linea.getIgvBaseImponible()), moneda));
            subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, orZero(linea.getIgv()), moneda));

            Element category = cac(doc, TAG_TAX_CATEGORY);
            CategoriaIgv cat = CategoriaIgv.obtener(linea.getIgvTipo());
            category.appendChild(cbcConAtributos(doc, "ID", cat.categoriaId(),
                    ATTR_SCHEME_AGENCY_NAME, VALUE_UN_ECE_AGENCY,
                    ATTR_SCHEME_ID, VALUE_UN_ECE_5305,
                    ATTR_SCHEME_NAME, VALUE_TAX_CATEGORY_IDENTIFIER));
            category.appendChild(cbcConAtributos(doc, "Percent",
                    escalar(linea.getTasaIgv() != null ? linea.getTasaIgv().multiply(new BigDecimal("100")) : BigDecimal.ZERO)));
            category.appendChild(cbcConAtributos(doc, "TaxExemptionReasonCode", linea.getIgvTipo(),
                    ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_LIST_NAME, "Afectacion del IGV",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"));

            Element scheme = cac(doc, TAG_TAX_SCHEME);
            scheme.appendChild(cbcConAtributos(doc, "ID", cat.tribCode(),
                    ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_SCHEME_ID, VALUE_UN_ECE_5153,
                    ATTR_SCHEME_NAME, VALUE_CODIGO_TRIBUTOS));
            scheme.appendChild(cbc(doc, "Name", cat.tribName()));
            scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, cat.tribTypeCode()));
            category.appendChild(scheme);

            subtotal.appendChild(category);
            taxTotal.appendChild(subtotal);
        }

        // ICBPER
        if (linea.getIcb() != null) {
            Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
            subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, linea.getIcb(), moneda));
            subtotal.appendChild(cbcCantidad(doc, "BaseUnitMeasure",
                    linea.getCantidad() != null ? linea.getCantidad() : BigDecimal.ONE,
                    linea.getUnidadMedida() != null ? linea.getUnidadMedida() : "NIU"));

            Element category = cac(doc, TAG_TAX_CATEGORY);
            category.appendChild(cbcMonto(doc, "PerUnitAmount", orZero(linea.getTasaIcb()), moneda));

            Element scheme = cac(doc, TAG_TAX_SCHEME);
            scheme.appendChild(cbcConAtributos(doc, "ID", "7152",
                    ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_SCHEME_ID, VALUE_UN_ECE_5153,
                    ATTR_SCHEME_NAME, VALUE_CODIGO_TRIBUTOS));
            scheme.appendChild(cbc(doc, "Name", "ICBPER"));
            scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, "OTH"));
            category.appendChild(scheme);

            subtotal.appendChild(category);
            taxTotal.appendChild(subtotal);
        }

        lineaXml.appendChild(taxTotal);
    }

    // ── Item y Price ─────────────────────────────────────────────

    static void agregarItemYPrecio(Document doc, Element lineaXml, LineaDetalle linea, String moneda) {
        // Item
        Element item = cac(doc, "Item");
        item.appendChild(cbcCdata(doc, "Description", linea.getDescripcion() != null ? linea.getDescripcion() : ""));

        if (linea.getCodigoProducto() != null) {
            Element sellerId = cac(doc, "SellersItemIdentification");
            sellerId.appendChild(cbc(doc, "ID", linea.getCodigoProducto()));
            item.appendChild(sellerId);
        }
        if (linea.getCodigoProductoGS1() != null) {
            Element stdId = cac(doc, "StandardItemIdentification");
            stdId.appendChild(cbcConAtributos(doc, "ID", linea.getCodigoProductoGS1(), "schemeID", "GTIN"));
            item.appendChild(stdId);
        }
        if (linea.getCodigoProductoSunat() != null) {
            Element commodity = cac(doc, "CommodityClassification");
            commodity.appendChild(cbcConAtributos(doc, "ItemClassificationCode", linea.getCodigoProductoSunat(),
                    "listID", "UNSPSC",
                    "listAgencyName", "GS1 US",
                    "listName", "Item Classification"));
            item.appendChild(commodity);
        }
        lineaXml.appendChild(item);

        // Price
        Element price = cac(doc, "Price");
        price.appendChild(cbcMonto(doc, "PriceAmount", orZero(linea.getPrecio()), moneda));
        lineaXml.appendChild(price);
    }

    // ── PricingReference ─────────────────────────────────────────

    static void agregarPricingReference(Document doc, Element lineaXml, LineaDetalle linea, String moneda) {
        if (linea.getPrecioReferencia() == null) return;

        Element pricingRef = cac(doc, "PricingReference");
        Element altPrice = cac(doc, "AlternativeConditionPrice");
        altPrice.appendChild(cbcMonto(doc, "PriceAmount", linea.getPrecioReferencia(), moneda));
        altPrice.appendChild(cbcConAtributos(doc, "PriceTypeCode",
                linea.getPrecioReferenciaTipo() != null ? linea.getPrecioReferenciaTipo() : "01",
                "listAgencyName", "PE:SUNAT",
                "listName", "Tipo de Precio",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"));
        pricingRef.appendChild(altPrice);
        lineaXml.appendChild(pricingRef);
    }

    // ── Cargos y descuentos ──────────────────────────────────────

    static void agregarCargosDescuentos(Document doc, Element raiz, List<CargoDescuento> cargos, boolean esCargo, String moneda) {
        if (cargos == null) return;
        for (CargoDescuento cargo : cargos) {
            Element ac = cac(doc, "AllowanceCharge");
            ac.appendChild(cbc(doc, "ChargeIndicator", esCargo ? "true" : "false"));
            ac.appendChild(cbc(doc, "AllowanceChargeReasonCode", cargo.tipo()));
            if (cargo.porcentaje() != null) {
                ac.appendChild(cbc(doc, "MultiplierFactorNumeric", escalar(cargo.porcentaje())));
            }
            ac.appendChild(cbcMonto(doc, "Amount", orZero(cargo.monto()), moneda));
            ac.appendChild(cbcMonto(doc, "BaseAmount", orZero(cargo.monto()), moneda));
            raiz.appendChild(ac);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────

    private static BigDecimal orZero(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
