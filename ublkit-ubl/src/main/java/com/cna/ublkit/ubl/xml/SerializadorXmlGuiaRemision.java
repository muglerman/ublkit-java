package com.cna.ublkit.ubl.xml;

import com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento;
import com.cna.ublkit.ubl.modelo.guia.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.RoundingMode;

import static com.cna.ublkit.ubl.xml.XmlUblHelper.*;

/**
 * Serializa un {@link BorradorGuiaRemision} a XML UBL 2.1 DespatchAdvice.
 * <p>
 * Soporta GRE-Remitente (09) y GRE-Transportista (31).
 * </p>
 *
 * @since 0.1.0
 */
public final class SerializadorXmlGuiaRemision implements SerializadorXml<BorradorGuiaRemision> {

    private static final String NS_DESPATCH = "urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2";

    @Override
    public String serializar(BorradorGuiaRemision guia) {
        if (guia == null) return null;
        com.cna.ublkit.ubl.ensamblador.EnsambladorGuia.ensamblar(guia);

        Document doc = crearDocumento(NS_DESPATCH, "DespatchAdvice");
        Element raiz = doc.getDocumentElement();

        // 1. UBLExtensions
        FragmentosXml.agregarExtensiones(doc, raiz);

        // 2. General data
        raiz.appendChild(cbc(doc, "UBLVersionID", ConstantesUbl.UBL_VERSION));
        raiz.appendChild(cbc(doc, "CustomizationID", guia.getVersion() != null ? guia.getVersion() : ConstantesUbl.CUSTOMIZATION_ID));
        raiz.appendChild(cbc(doc, "ID", guia.getSerie() + "-" + guia.getNumero()));
        raiz.appendChild(cbc(doc, "IssueDate", guia.getFechaEmision()));
        if (guia.getHoraEmision() != null) {
            raiz.appendChild(cbc(doc, "IssueTime", guia.getHoraEmision()));
        }

        // 3. DespatchAdviceTypeCode
        raiz.appendChild(cbcConAtributos(doc, "DespatchAdviceTypeCode",
                guia.getTipoComprobante() != null ? guia.getTipoComprobante() : "09",
                "listAgencyName", "PE:SUNAT",
                "listName", "Tipo de Documento",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"));

        // 4. Notes
        if (guia.getObservaciones() != null) {
            raiz.appendChild(cbcCdata(doc, "Note", guia.getObservaciones()));
        }

        // 5. OrderReference (documento de baja)
        if (guia.getDocumentoBaja() != null) {
            Element order = cac(doc, "OrderReference");
            order.appendChild(cbc(doc, "ID", guia.getDocumentoBaja().serieNumero()));
            order.appendChild(cbcConAtributos(doc, "OrderTypeCode", guia.getDocumentoBaja().tipoDocumento(),
                    "listAgencyName", "PE:SUNAT",
                    "listName", "SUNAT:Identificador de Tipo de Documento",
                    "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01"));
            raiz.appendChild(order);
        }

        // 6. AdditionalDocumentReference (documento relacionado principal)
        if (guia.getDocumentoRelacionado() != null) {
            agregarDocRelGuia(doc, raiz, guia.getDocumentoRelacionado(), "catalogo21");
        }

        // 7. AdditionalDocumentReference (documentos relacionados adicionales)
        if (guia.getDocumentosRelacionados() != null) {
            for (DocumentoRelacionadoGuia rel : guia.getDocumentosRelacionados()) {
                agregarDocRelGuia(doc, raiz, rel, "catalogo21");
            }
        }

        // 8. AdditionalDocumentReference (documentos adicionales - Cat 61)
        if (guia.getDocumentosAdicionales() != null) {
            for (DocumentoAdicionalGuia adicional : guia.getDocumentosAdicionales()) {
                Element ref = cac(doc, "AdditionalDocumentReference");
                ref.appendChild(cbc(doc, "ID", adicional.serieNumero()));
                ref.appendChild(cbcConAtributos(doc, "DocumentTypeCode", adicional.tipoDocumento(),
                        "listAgencyName", "PE:SUNAT",
                        "listName", "Documento relacionado al transporte",
                        "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo61"));
                if (adicional.emisor() != null) {
                    Element issuer = cac(doc, "IssuerParty");
                    Element pid = cac(doc, "PartyIdentification");
                    pid.appendChild(cbcConAtributos(doc, "ID", adicional.emisor(),
                            "schemeID", "6",
                            "schemeName", "Documento de Identidad",
                            "schemeAgencyName", "PE:SUNAT",
                            "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"));
                    issuer.appendChild(pid);
                    ref.appendChild(issuer);
                }
                raiz.appendChild(ref);
            }
        }

        // 9. Signature
        agregarFirmaGuia(doc, raiz, guia);

        // 10. DespatchSupplierParty (remitente)
        agregarRemitente(doc, raiz, guia);

        // 11. DeliveryCustomerParty (destinatario)
        agregarDestinatario(doc, raiz, guia);

        // 12. BuyerCustomerParty (comprador, opcional)
        if (guia.getComprador() != null) {
            agregarParteConIdentidad(doc, raiz, "BuyerCustomerParty",
                    guia.getComprador().tipoDocumentoIdentidad(),
                    guia.getComprador().numeroDocumentoIdentidad(),
                    guia.getComprador().nombre());
        }

        // 13. SellerSupplierParty (tercero, opcional)
        if (guia.getTercero() != null) {
            agregarParteConIdentidad(doc, raiz, "SellerSupplierParty",
                    guia.getTercero().tipoDocumentoIdentidad(),
                    guia.getTercero().numeroDocumentoIdentidad(),
                    guia.getTercero().nombre());
        }

        // 14. Shipment
        agregarShipment(doc, raiz, guia);

        // 15. DespatchLines
        agregarLineas(doc, raiz, guia);

        return documentoAString(doc);
    }

    // ── DocRef Guía ──────────────────────────────────────────────

    private void agregarDocRelGuia(Document doc, Element raiz, DocumentoRelacionadoGuia rel, String catalogo) {
        Element ref = cac(doc, "AdditionalDocumentReference");
        ref.appendChild(cbc(doc, "ID", rel.serieNumero()));
        ref.appendChild(cbcConAtributos(doc, "DocumentTypeCode", rel.tipoDocumento(),
                "listAgencyName", "PE:SUNAT",
                "listName", "SUNAT:Identificador de documento relacionado",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:" + catalogo));
        raiz.appendChild(ref);
    }

    // ── Firma ────────────────────────────────────────────────────

    private void agregarFirmaGuia(Document doc, Element raiz, BorradorGuiaRemision guia) {
        FirmanteDocumento firmante = guia.getFirmante();
        if (firmante == null && guia.getRemitente() != null) {
            firmante = new FirmanteDocumento(guia.getRemitente().ruc(), guia.getRemitente().razonSocial());
        }
        if (firmante == null) return;

        Element sig = cac(doc, "Signature");
        sig.appendChild(cbc(doc, "ID", firmante.ruc()));
        Element sp = cac(doc, "SignatoryParty");
        Element pid = cac(doc, "PartyIdentification");
        pid.appendChild(cbc(doc, "ID", firmante.ruc()));
        sp.appendChild(pid);
        Element pn = cac(doc, "PartyName");
        pn.appendChild(cbcCdata(doc, "Name", firmante.razonSocial()));
        sp.appendChild(pn);
        sig.appendChild(sp);
        Element att = cac(doc, "DigitalSignatureAttachment");
        Element ext = cac(doc, "ExternalReference");
        ext.appendChild(cbc(doc, "URI", "#UBLKIT-SIGN"));
        att.appendChild(ext);
        sig.appendChild(att);
        raiz.appendChild(sig);
    }

    // ── Remitente ────────────────────────────────────────────────

    private void agregarRemitente(Document doc, Element raiz, BorradorGuiaRemision guia) {
        if (guia.getRemitente() == null) return;
        Element supplier = cac(doc, "DespatchSupplierParty");
        Element party = cac(doc, "Party");
        Element pid = cac(doc, "PartyIdentification");
        pid.appendChild(cbcConAtributos(doc, "ID", guia.getRemitente().ruc(),
                "schemeID", "6",
                "schemeName", "Documento de Identidad",
                "schemeAgencyName", "PE:SUNAT",
                "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"));
        party.appendChild(pid);
        Element legal = cac(doc, "PartyLegalEntity");
        legal.appendChild(cbcCdata(doc, "RegistrationName", guia.getRemitente().razonSocial()));
        party.appendChild(legal);
        supplier.appendChild(party);
        raiz.appendChild(supplier);
    }

    // ── Destinatario ─────────────────────────────────────────────

    private void agregarDestinatario(Document doc, Element raiz, BorradorGuiaRemision guia) {
        if (guia.getDestinatario() == null) return;
        agregarParteConIdentidad(doc, raiz, "DeliveryCustomerParty",
                guia.getDestinatario().tipoDocumentoIdentidad(),
                guia.getDestinatario().numeroDocumentoIdentidad(),
                guia.getDestinatario().nombre());
    }

    // ── Parte genérica con identidad ─────────────────────────────

    private void agregarParteConIdentidad(Document doc, Element raiz, String tagName,
                                           String tipoDoc, String numDoc, String nombre) {
        Element container = cac(doc, tagName);
        Element party = cac(doc, "Party");
        Element pid = cac(doc, "PartyIdentification");
        pid.appendChild(cbcConAtributos(doc, "ID", numDoc,
                "schemeID", tipoDoc,
                "schemeName", "Documento de Identidad",
                "schemeAgencyName", "PE:SUNAT",
                "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"));
        party.appendChild(pid);
        Element legal = cac(doc, "PartyLegalEntity");
        legal.appendChild(cbcCdata(doc, "RegistrationName", nombre));
        party.appendChild(legal);
        container.appendChild(party);
        raiz.appendChild(container);
    }

    // ── Shipment ─────────────────────────────────────────────────

    private void agregarShipment(Document doc, Element raiz, BorradorGuiaRemision guia) {
        DatosEnvio envio = guia.getEnvio();
        if (envio == null) return;

        Element shipment = cac(doc, "Shipment");
        shipment.appendChild(cbc(doc, "ID", "SUNAT_Envio"));

        // HandlingCode (motivo traslado)
        shipment.appendChild(cbcConAtributos(doc, "HandlingCode",
                envio.getTipoTraslado() != null ? envio.getTipoTraslado() : "01",
                "listAgencyName", "PE:SUNAT",
                "listName", "Motivo de traslado",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo20"));

        if (envio.getMotivoTraslado() != null) {
            shipment.appendChild(cbc(doc, "HandlingInstructions", envio.getMotivoTraslado()));
        }
        if (envio.getSustentoPeso() != null) {
            shipment.appendChild(cbc(doc, "Information", envio.getSustentoPeso()));
        }

        // Peso bruto
        if (envio.getPesoTotal() != null) {
            Element peso = doc.createElementNS(ConstantesUbl.NS_CBC, "cbc:GrossWeightMeasure");
            peso.setAttribute("unitCode", envio.getPesoTotalUnidadMedida() != null ? envio.getPesoTotalUnidadMedida() : "KGM");
            peso.setTextContent(envio.getPesoTotal().setScale(3, RoundingMode.HALF_UP).toPlainString());
            shipment.appendChild(peso);
        }

        // Peso ítems
        if (envio.getPesoItems() != null) {
            Element pesoNet = doc.createElementNS(ConstantesUbl.NS_CBC, "cbc:NetWeightMeasure");
            pesoNet.setAttribute("unitCode", "KGM");
            pesoNet.setTextContent(envio.getPesoItems().setScale(3, RoundingMode.HALF_UP).toPlainString());
            shipment.appendChild(pesoNet);
        }

        if (envio.getNumeroDeBultos() != null) {
            shipment.appendChild(cbc(doc, "TotalTransportHandlingUnitQuantity", envio.getNumeroDeBultos()));
        }

        // Indicadores especiales
        if (envio.getIndicadores() != null) {
            for (String ind : envio.getIndicadores()) {
                shipment.appendChild(cbc(doc, "SpecialInstructions", ind));
            }
        }

        // ShipmentStage
        agregarShipmentStage(doc, shipment, envio);

        // Delivery (destino + partida)
        agregarDelivery(doc, shipment, envio);

        // Contenedores
        agregarContenedores(doc, shipment, envio);

        // Vehículo
        agregarVehiculo(doc, shipment, envio);

        // Puerto/Aeropuerto
        agregarPuertoAeropuerto(doc, shipment, envio);

        raiz.appendChild(shipment);
    }

    private void agregarShipmentStage(Document doc, Element shipment, DatosEnvio envio) {
        Element stage = cac(doc, "ShipmentStage");
        stage.appendChild(cbcConAtributos(doc, "TransportModeCode",
                envio.getTipoModalidadTraslado() != null ? envio.getTipoModalidadTraslado() : "01",
                "listName", "Modalidad de traslado",
                "listAgencyName", "PE:SUNAT",
                "listURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo18"));

        Element transit = cac(doc, "TransitPeriod");
        transit.appendChild(cbc(doc, "StartDate",
                envio.getFechaTraslado() != null ? envio.getFechaTraslado() : java.time.LocalDate.now()));
        stage.appendChild(transit);

        // Transportista
        if (envio.getTransportista() != null) {
            Element carrier = cac(doc, "CarrierParty");
            Element pid = cac(doc, "PartyIdentification");
            pid.appendChild(cbcConAtributos(doc, "ID", envio.getTransportista().numeroDocumentoIdentidad(),
                    "schemeID", envio.getTransportista().tipoDocumentoIdentidad()));
            carrier.appendChild(pid);
            Element legal = cac(doc, "PartyLegalEntity");
            legal.appendChild(cbcCdata(doc, "RegistrationName", envio.getTransportista().nombre()));
            if (envio.getTransportista().numeroRegistroMTC() != null) {
                legal.appendChild(cbc(doc, "CompanyID", envio.getTransportista().numeroRegistroMTC()));
            }
            carrier.appendChild(legal);
            stage.appendChild(carrier);
        }

        // Choferes
        if (envio.getChoferes() != null) {
            for (Conductor chofer : envio.getChoferes()) {
                Element driver = cac(doc, "DriverPerson");
                driver.appendChild(cbcConAtributos(doc, "ID", chofer.numeroDocumentoIdentidad(),
                        "schemeID", chofer.tipoDocumentoIdentidad(),
                        "schemeName", "Documento de Identidad",
                        "schemeAgencyName", "PE:SUNAT",
                        "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06"));
                if (chofer.nombres() != null) driver.appendChild(cbc(doc, "FirstName", chofer.nombres()));
                if (chofer.apellidos() != null) driver.appendChild(cbc(doc, "FamilyName", chofer.apellidos()));
                if (chofer.tipo() != null) driver.appendChild(cbc(doc, "JobTitle", chofer.tipo()));
                if (chofer.licencia() != null) {
                    Element licRef = cac(doc, "IdentityDocumentReference");
                    licRef.appendChild(cbc(doc, "ID", chofer.licencia()));
                    driver.appendChild(licRef);
                }
                stage.appendChild(driver);
            }
        }

        shipment.appendChild(stage);
    }

    private void agregarDelivery(Document doc, Element shipment, DatosEnvio envio) {
        Element delivery = cac(doc, "Delivery");

        if (envio.getDestino() != null) {
            Element addr = cac(doc, "DeliveryAddress");
            addr.appendChild(cbcConAtributos(doc, "ID", envio.getDestino().ubigeo(),
                    "schemeAgencyName", "PE:INEI", "schemeName", "Ubigeos"));
            if (envio.getDestino().codigoLocal() != null) {
                addr.appendChild(cbcConAtributos(doc, "AddressTypeCode", envio.getDestino().codigoLocal(),
                        "listID", envio.getDestino().ruc() != null ? envio.getDestino().ruc() : ""));
            }
            Element line = cac(doc, "AddressLine");
            line.appendChild(cbc(doc, "Line", envio.getDestino().direccion()));
            addr.appendChild(line);
            delivery.appendChild(addr);
        }

        if (envio.getPartida() != null) {
            Element despatch = cac(doc, "Despatch");
            Element addr = cac(doc, "DespatchAddress");
            addr.appendChild(cbcConAtributos(doc, "ID", envio.getPartida().ubigeo(),
                    "schemeAgencyName", "PE:INEI", "schemeName", "Ubigeos"));
            if (envio.getPartida().codigoLocal() != null) {
                addr.appendChild(cbcConAtributos(doc, "AddressTypeCode", envio.getPartida().codigoLocal(),
                        "listID", envio.getPartida().ruc() != null ? envio.getPartida().ruc() : ""));
            }
            Element line = cac(doc, "AddressLine");
            line.appendChild(cbc(doc, "Line", envio.getPartida().direccion()));
            addr.appendChild(line);
            despatch.appendChild(addr);
            delivery.appendChild(despatch);
        }

        shipment.appendChild(delivery);
    }

    private void agregarContenedores(Document doc, Element shipment, DatosEnvio envio) {
        if (envio.getContenedores() == null) return;
        for (Contenedor cont : envio.getContenedores()) {
            Element thu = cac(doc, "TransportHandlingUnit");
            Element pkg = cac(doc, "Package");
            pkg.appendChild(cbc(doc, "ID", cont.numero()));
            if (cont.precinto() != null) {
                pkg.appendChild(cbc(doc, "TraceID", cont.precinto()));
            }
            thu.appendChild(pkg);
            shipment.appendChild(thu);
        }
    }

    private void agregarVehiculo(Document doc, Element shipment, DatosEnvio envio) {
        if (envio.getVehiculo() == null) return;

        Element thu = cac(doc, "TransportHandlingUnit");
        Element equip = cac(doc, "TransportEquipment");
        equip.appendChild(cbc(doc, "ID", envio.getVehiculo().placa()));

        if (envio.getVehiculo().numeroCirculacion() != null) {
            Element means = cac(doc, "ApplicableTransportMeans");
            means.appendChild(cbc(doc, "RegistrationNationalityID", envio.getVehiculo().numeroCirculacion()));
            equip.appendChild(means);
        }

        // Vehículos secundarios
        if (envio.getVehiculo().secundarios() != null) {
            for (Vehiculo sec : envio.getVehiculo().secundarios()) {
                Element attached = cac(doc, "AttachedTransportEquipment");
                attached.appendChild(cbc(doc, "ID", sec.placa()));
                if (sec.numeroCirculacion() != null) {
                    Element means = cac(doc, "ApplicableTransportMeans");
                    means.appendChild(cbc(doc, "RegistrationNationalityID", sec.numeroCirculacion()));
                    attached.appendChild(means);
                }
                if (sec.numeroAutorizacion() != null) {
                    Element shipRef = cac(doc, "ShipmentDocumentReference");
                    shipRef.appendChild(cbcConAtributos(doc, "ID", sec.numeroAutorizacion(),
                            "schemeID", sec.codigoEmisor() != null ? sec.codigoEmisor() : "",
                            "schemeName", "Entidad Autorizadora",
                            "schemeAgencyName", "PE:SUNAT"));
                    attached.appendChild(shipRef);
                }
                equip.appendChild(attached);
            }
        }

        if (envio.getVehiculo().numeroAutorizacion() != null) {
            Element shipRef = cac(doc, "ShipmentDocumentReference");
            shipRef.appendChild(cbcConAtributos(doc, "ID", envio.getVehiculo().numeroAutorizacion(),
                    "schemeID", envio.getVehiculo().codigoEmisor() != null ? envio.getVehiculo().codigoEmisor() : "",
                    "schemeName", "Entidad Autorizadora",
                    "schemeAgencyName", "PE:SUNAT"));
            equip.appendChild(shipRef);
        }

        thu.appendChild(equip);
        shipment.appendChild(thu);
    }

    private void agregarPuertoAeropuerto(Document doc, Element shipment, DatosEnvio envio) {
        if (envio.getPuerto() != null) {
            Element port = cac(doc, "FirstArrivalPortLocation");
            port.appendChild(cbcConAtributos(doc, "ID", envio.getPuerto().codigo(),
                    "schemeAgencyName", "PE:SUNAT", "schemeName", "Puertos",
                    "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo63"));
            port.appendChild(cbc(doc, "LocationTypeCode", "1"));
            if (envio.getPuerto().descripcion() != null) {
                port.appendChild(cbc(doc, "Name", envio.getPuerto().descripcion()));
            }
            shipment.appendChild(port);
        } else if (envio.getAeropuerto() != null) {
            Element port = cac(doc, "FirstArrivalPortLocation");
            port.appendChild(cbcConAtributos(doc, "ID", envio.getAeropuerto().codigo(),
                    "schemeAgencyName", "PE:SUNAT", "schemeName", "Aeropuertos",
                    "schemeURI", "urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo64"));
            port.appendChild(cbc(doc, "LocationTypeCode", "2"));
            if (envio.getAeropuerto().descripcion() != null) {
                port.appendChild(cbc(doc, "Name", envio.getAeropuerto().descripcion()));
            }
            shipment.appendChild(port);
        }
    }

    // ── DespatchLines ────────────────────────────────────────────

    private void agregarLineas(Document doc, Element raiz, BorradorGuiaRemision guia) {
        if (guia.getDetalles() == null) return;

        int idx = 1;
        for (LineaGuia linea : guia.getDetalles()) {
            Element despLine = cac(doc, "DespatchLine");
            despLine.appendChild(cbc(doc, "ID", idx));

            Element qty = doc.createElementNS(ConstantesUbl.NS_CBC, "cbc:DeliveredQuantity");
            qty.setAttribute("unitCode", linea.unidadMedida() != null ? linea.unidadMedida() : "NIU");
            qty.setTextContent(linea.cantidad() != null ? linea.cantidad().setScale(2, RoundingMode.HALF_UP).toPlainString() : "1.00");
            despLine.appendChild(qty);

            Element orderLine = cac(doc, "OrderLineReference");
            orderLine.appendChild(cbc(doc, "LineID", idx));
            despLine.appendChild(orderLine);

            Element item = cac(doc, "Item");
            if (linea.descripcion() != null) {
                item.appendChild(cbcCdata(doc, "Description", linea.descripcion()));
            }
            if (linea.codigo() != null) {
                Element sellerId = cac(doc, "SellersItemIdentification");
                sellerId.appendChild(cbc(doc, "ID", linea.codigo()));
                item.appendChild(sellerId);
            }
            if (linea.codigoSunat() != null) {
                Element commodity = cac(doc, "CommodityClassification");
                commodity.appendChild(cbcConAtributos(doc, "ItemClassificationCode", linea.codigoSunat(),
                        "listID", "UNSPSC", "listAgencyName", "GS1 US", "listName", "Item Classification"));
                item.appendChild(commodity);
            }
            if (linea.atributos() != null) {
                for (AtributoItem attr : linea.atributos()) {
                    Element prop = cac(doc, "AdditionalItemProperty");
                    prop.appendChild(cbc(doc, "Name", attr.codigo()));
                    prop.appendChild(cbc(doc, "NameCode", attr.codigo()));
                    if (attr.valor() != null) {
                        prop.appendChild(cbc(doc, "Value", attr.valor()));
                    }
                    item.appendChild(prop);
                }
            }
            despLine.appendChild(item);
            raiz.appendChild(despLine);
            idx++;
        }
    }
}
