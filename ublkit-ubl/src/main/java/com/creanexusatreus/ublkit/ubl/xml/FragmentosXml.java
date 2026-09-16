package com.creanexusatreus.ublkit.ubl.xml;

import com.creanexusatreus.ublkit.core.modelo.Contacto;
import com.creanexusatreus.ublkit.core.modelo.Direccion;
import com.creanexusatreus.ublkit.ubl.modelo.DocumentoBase;
import com.creanexusatreus.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DatosHidrobiologicos;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DatosTransporteCarga;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.DocumentoRelacionado;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.GuiaRelacionada;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.PuntoTransporte;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.TramoTransporteCarga;
import com.creanexusatreus.ublkit.ubl.modelo.linea.CargoDescuento;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;
import com.creanexusatreus.ublkit.ubl.modelo.total.GrupoTributario;
import com.creanexusatreus.ublkit.ubl.modelo.total.GrupoTributarioFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.List;

import static com.creanexusatreus.ublkit.ubl.xml.XmlUblHelper.*;

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

    /**
     * Formatea el correlativo de un comprobante con la convención SUNAT de 8 dígitos rellenados con ceros
     * (p.ej. 9 -> "00000009"). Centraliza el padding para que TODOS los serializadores que construyen el
     * {@code cbc:ID} con formato serie-correlativo (factura, nota, guía de remisión, retención, percepción)
     * produzcan el mismo formato. No aplica a resumen diario (RC) ni comunicación de baja (RA), cuyo ID usa
     * el correlativo diario sin padding.
     */
    static String correlativoFormateado(Integer numero) {
        return numero != null ? String.format("%08d", numero) : "00000001";
    }

    static void agregarDatosGenerales(Document doc, Element raiz, DocumentoBase documento) {
        raiz.appendChild(cbc(doc, "UBLVersionID", ConstantesUbl.UBL_VERSION));
        raiz.appendChild(cbc(doc, "CustomizationID", ConstantesUbl.CUSTOMIZATION_ID));
        raiz.appendChild(cbc(doc, "ID", documento.getSerie() + "-" + correlativoFormateado(documento.getNumero())));
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
            // DocumentTypeCode pertenece al Catálogo 12 (no al 01): debe llevar sus
            // atributos de lista, igual que la referencia de anticipos (evita 4009).
            additional.appendChild(cbcConAtributos(doc, "DocumentTypeCode", rel.tipoDocumento(),
                    ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_LIST_NAME, "Documento Relacionado",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12"));
            raiz.appendChild(additional);
        }
    }

    // ── Tax Total ────────────────────────────────────────────────

    static void agregarTotalImpuestos(Document doc, Element raiz, TotalImpuestos imp, String moneda) {
        if (imp == null) return;

        Element taxTotal = cac(doc, TAG_TAX_TOTAL);
        taxTotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, imp.total(), moneda));
        for (GrupoTributario grupo : imp.grupos()) {
            taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda, grupo));
        }

        raiz.appendChild(taxTotal);
    }

    private static Element crearSubtotalImpuesto(Document doc, String moneda, GrupoTributario grupo) {
        Element subtotal = cac(doc, TAG_TAX_SUBTOTAL);
        subtotal.appendChild(cbcMonto(doc, TAG_TAXABLE_AMOUNT, grupo.baseImponible(), moneda));
        subtotal.appendChild(cbcMonto(doc, TAG_TAX_AMOUNT, grupo.importe(), moneda));
        Element category = cac(doc, TAG_TAX_CATEGORY);
        category.appendChild(cbcConAtributos(doc, "ID", grupo.categoriaId(),
                ATTR_SCHEME_AGENCY_NAME, VALUE_UN_ECE_AGENCY,
                ATTR_SCHEME_ID, VALUE_UN_ECE_5305,
                ATTR_SCHEME_NAME, VALUE_TAX_CATEGORY_IDENTIFIER));
        category.appendChild(cbcConAtributos(doc, "Percent", escalar(grupo.porcentaje())));
        if (grupo.codigoAfectacion() != null && esAfectacionIgv(grupo.tributoId())) {
            category.appendChild(cbcConAtributos(doc, "TaxExemptionReasonCode", grupo.codigoAfectacion(),
                    ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                    ATTR_LIST_NAME, "Afectacion del IGV",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"));
        }
        Element scheme = cac(doc, TAG_TAX_SCHEME);
        scheme.appendChild(cbcConAtributos(doc, "ID", grupo.tributoId(),
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                ATTR_SCHEME_ID, VALUE_UN_ECE_5153,
                ATTR_SCHEME_NAME, VALUE_CODIGO_TRIBUTOS));
        scheme.appendChild(cbc(doc, "Name", grupo.tributoNombre()));
        scheme.appendChild(cbc(doc, TAG_TAX_TYPE_CODE, grupo.tributoTipoCodigo()));
        category.appendChild(scheme);
        subtotal.appendChild(category);
        return subtotal;
    }

    private static boolean esAfectacionIgv(String tributoId) {
        return "1000".equals(tributoId) || "1016".equals(tributoId)
                || "9995".equals(tributoId) || "9996".equals(tributoId)
                || "9997".equals(tributoId) || "9998".equals(tributoId);
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

        taxTotal.appendChild(crearSubtotalImpuesto(doc, moneda, GrupoTributarioFactory.igv(linea)));

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
        agregarDatosHidrobiologicos(doc, item, linea.getDatosHidrobiologicos());
        lineaXml.appendChild(item);

        // Price
        Element price = cac(doc, "Price");
        price.appendChild(cbcMonto(doc, "PriceAmount", orZero(linea.getPrecio()), moneda));
        lineaXml.appendChild(price);
    }

    // ── Detracción ───────────────────────────────────────────────

    /**
     * Agrega los dos bloques SPOT que SUNAT exige para una detracción.
     * El importe de detracción siempre se expresa en PEN, incluso cuando el
     * comprobante se haya emitido en otra moneda.
     */
    static void agregarDetraccion(Document doc, Element raiz, Detraccion detraccion) {
        if (detraccion == null) {
            return;
        }
        detraccion.validar();

        Element paymentMeans = cac(doc, "PaymentMeans");
        paymentMeans.appendChild(cbc(doc, "ID", "Detraccion"));
        paymentMeans.appendChild(cbcConAtributos(doc, "PaymentMeansCode", detraccion.medioDePago(),
                ATTR_LIST_NAME, "Medio de pago",
                ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo59"));
        Element account = cac(doc, "PayeeFinancialAccount");
        account.appendChild(cbc(doc, "ID", detraccion.cuentaBancaria()));
        paymentMeans.appendChild(account);
        raiz.appendChild(paymentMeans);

        Element paymentTerms = cac(doc, "PaymentTerms");
        paymentTerms.appendChild(cbc(doc, "ID", "Detraccion"));
        paymentTerms.appendChild(cbcConAtributos(doc, "PaymentMeansID", detraccion.tipoBienDetraido(),
                ATTR_SCHEME_NAME, "Codigo de detraccion",
                ATTR_SCHEME_AGENCY_NAME, VALUE_PE_SUNAT,
                "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo54"));
        paymentTerms.appendChild(cbc(doc, "PaymentPercent", detraccion.porcentaje().toPlainString()));
        paymentTerms.appendChild(cbcMonto(doc, "Amount", detraccion.monto(), "PEN"));
        raiz.appendChild(paymentTerms);
    }

    // ── Complementos SPOT por línea ──────────────────────────────

    private static void agregarDatosHidrobiologicos(Document doc, Element item, DatosHidrobiologicos datos) {
        if (datos == null) {
            return;
        }

        agregarPropiedadItem(doc, item, "Matrícula de la embarcación pesquera", "3001",
                datos.matriculaEmbarcacion());
        agregarPropiedadItem(doc, item, "Nombre de la embarcación pesquera", "3002",
                datos.nombreEmbarcacion());
        agregarPropiedadItem(doc, item, "Descripción del tipo de la especie vendida", "3003",
                datos.descripcionEspecie());
        agregarPropiedadItem(doc, item, "Lugar de descarga", "3004", datos.lugarDescarga());

        Element cantidad = nuevaPropiedadItem(doc, "Cantidad de la especie vendida", "3006");
        cantidad.appendChild(cbcCantidad(doc, "ValueQuantity", datos.cantidadEspecieTne(), "TNE"));
        item.appendChild(cantidad);

        Element fecha = nuevaPropiedadItem(doc, "Fecha de descarga", "3005");
        Element periodo = cac(doc, "UsabilityPeriod");
        periodo.appendChild(cbc(doc, "StartDate", datos.fechaDescarga()));
        fecha.appendChild(periodo);
        item.appendChild(fecha);
    }

    private static void agregarPropiedadItem(Document doc, Element item, String nombre, String codigo, String valor) {
        Element propiedad = nuevaPropiedadItem(doc, nombre, codigo);
        propiedad.appendChild(cbc(doc, "Value", valor));
        item.appendChild(propiedad);
    }

    private static Element nuevaPropiedadItem(Document doc, String nombre, String codigo) {
        Element propiedad = cac(doc, "AdditionalItemProperty");
        propiedad.appendChild(cbc(doc, "Name", nombre));
        propiedad.appendChild(cbcConAtributos(doc, "NameCode", codigo,
                ATTR_LIST_NAME, "Propiedad del item",
                ATTR_LIST_AGENCY_NAME, VALUE_PE_SUNAT,
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo55"));
        return propiedad;
    }

    static void agregarDatosTransporteCarga(Document doc, Element lineaXml, LineaDetalle linea) {
        DatosTransporteCarga datos = linea.getDatosTransporteCarga();
        if (datos == null) {
            return;
        }

        Element delivery = cac(doc, "Delivery");
        delivery.appendChild(crearDeliveryLocation(doc, datos.destino()));

        Element despatch = cac(doc, "Despatch");
        despatch.appendChild(cbc(doc, "Instructions", datos.detalleViaje()));
        despatch.appendChild(crearDespatchAddress(doc, datos.origen()));
        delivery.appendChild(despatch);

        delivery.appendChild(crearValorReferencial(doc, "01", datos.valorReferencialServicio()));
        delivery.appendChild(crearValorReferencial(doc, "02", datos.valorReferencialCargaEfectiva()));
        delivery.appendChild(crearValorReferencial(doc, "03", datos.valorReferencialCargaUtilNominal()));

        if (!datos.tramos().isEmpty()) {
            Element shipment = cac(doc, "Shipment");
            for (TramoTransporteCarga tramo : datos.tramos()) {
                shipment.appendChild(crearTramo(doc, tramo));
            }
            delivery.appendChild(shipment);
        }

        lineaXml.appendChild(delivery);
    }

    private static Element crearDespatchAddress(Document doc, PuntoTransporte punto) {
        Element address = cac(doc, "DespatchAddress");
        agregarPuntoTransporte(doc, address, punto);
        return address;
    }

    private static Element crearDeliveryLocation(Document doc, PuntoTransporte punto) {
        Element location = cac(doc, "DeliveryLocation");
        Element address = cac(doc, "Address");
        agregarPuntoTransporte(doc, address, punto);
        location.appendChild(address);
        return location;
    }

    private static void agregarPuntoTransporte(Document doc, Element address, PuntoTransporte punto) {
        address.appendChild(cbcConAtributos(doc, "ID", punto.ubigeo(),
                ATTR_SCHEME_AGENCY_NAME, "PE:INEI",
                ATTR_SCHEME_NAME, "Ubigeos"));
        Element addressLine = cac(doc, "AddressLine");
        addressLine.appendChild(cbc(doc, "Line", punto.direccionDetallada()));
        address.appendChild(addressLine);
    }

    private static Element crearValorReferencial(Document doc, String tipo, BigDecimal monto) {
        Element deliveryTerms = cac(doc, "DeliveryTerms");
        deliveryTerms.appendChild(cbc(doc, "ID", tipo));
        deliveryTerms.appendChild(cbcMonto(doc, "Amount", monto, "PEN"));
        return deliveryTerms;
    }

    private static Element crearTramo(Document doc, TramoTransporteCarga tramo) {
        Element consignment = cac(doc, "Consignment");
        consignment.appendChild(cbc(doc, "ID", tramo.identificador()));
        if (tramo.descripcion() != null) {
            consignment.appendChild(cbc(doc, "CarrierServiceInstructions", tramo.descripcion()));
        }
        if (tramo.origen() != null) {
            consignment.appendChild(crearEventoTransporte(doc, "PlannedPickupTransportEvent", tramo.origen()));
        }
        if (tramo.destino() != null) {
            consignment.appendChild(crearEventoTransporte(doc, "PlannedDeliveryTransportEvent", tramo.destino()));
        }
        if (tramo.valorPreliminarReferencialCargaEfectiva() != null) {
            Element deliveryTerms = cac(doc, "DeliveryTerms");
            deliveryTerms.appendChild(cbcMonto(doc, "Amount",
                    tramo.valorPreliminarReferencialCargaEfectiva(), "PEN"));
            consignment.appendChild(deliveryTerms);
        }
        return consignment;
    }

    private static Element crearEventoTransporte(Document doc, String nombre, PuntoTransporte punto) {
        Element event = cac(doc, nombre);
        Element location = cac(doc, "Location");
        location.appendChild(cbcConAtributos(doc, "ID", punto.ubigeo(),
                ATTR_SCHEME_AGENCY_NAME, "PE:INEI",
                ATTR_SCHEME_NAME, "Ubigeos"));
        event.appendChild(location);
        return event;
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
