package com.cna.ublkit.quarkus.producer;

import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.cna.ublkit.render.html.RenderizadorHtmlNota;
import com.cna.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.cna.ublkit.render.html.RenderizadorHtmlComunicacionBaja;
import com.cna.ublkit.render.pdf.RenderizadorPdfComunicacionBaja;
import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;
import com.cna.ublkit.render.pdf.RenderizadorPdfGuiaRemision;
import com.cna.ublkit.render.pdf.RenderizadorPdfNota;
import com.cna.ublkit.render.pdf.RenderizadorPdfResumenDiario;
import com.cna.ublkit.ubl.xml.SerializadorXmlComunicacionBaja;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlGuiaRemision;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaCredito;
import com.cna.ublkit.ubl.xml.SerializadorXmlNotaDebito;
import com.cna.ublkit.ubl.xml.SerializadorXmlPercepcion;
import com.cna.ublkit.ubl.xml.SerializadorXmlRetencion;
import com.cna.ublkit.ubl.xml.SerializadorXmlResumenDiario;
import com.cna.ublkit.validation.validador.ValidadorComunicacionBaja;
import com.cna.ublkit.validation.validador.ValidadorFactura;
import com.cna.ublkit.validation.validador.ValidadorGuiaRemision;
import com.cna.ublkit.validation.validador.ValidadorNotaCredito;
import com.cna.ublkit.validation.validador.ValidadorNotaDebito;
import com.cna.ublkit.validation.validador.ValidadorPercepcion;
import com.cna.ublkit.validation.validador.ValidadorRetencion;
import com.cna.ublkit.validation.validador.ValidadorResumenDiario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

/**
 * Productores CDI para integrar los componentes de UBLKit en aplicaciones Quarkus.
 *
 * @since 0.1.0
 */
@ApplicationScoped
public class UblKitProducers {

    @Produces
    @Singleton
    public RenderizadorPdfFactura produceRenderizadorPdfFactura() {
        return new RenderizadorPdfFactura();
    }

    @Produces
    @Singleton
    public RenderizadorHtmlFactura produceRenderizadorHtmlFactura() {
        return new RenderizadorHtmlFactura();
    }

    @Produces
    @Singleton
    public RenderizadorPdfNota produceRenderizadorPdfNota() {
        return new RenderizadorPdfNota();
    }

    @Produces
    @Singleton
    public RenderizadorHtmlNota produceRenderizadorHtmlNota() {
        return new RenderizadorHtmlNota();
    }

    @Produces
    @Singleton
    public RenderizadorPdfGuiaRemision produceRenderizadorPdfGuiaRemision() {
        return new RenderizadorPdfGuiaRemision();
    }

    @Produces
    @Singleton
    public RenderizadorHtmlGuiaRemision produceRenderizadorHtmlGuiaRemision() {
        return new RenderizadorHtmlGuiaRemision();
    }

    @Produces
    @Singleton
    public RenderizadorPdfResumenDiario produceRenderizadorPdfResumenDiario() {
        return new RenderizadorPdfResumenDiario();
    }

    @Produces
    @Singleton
    public RenderizadorHtmlResumenDiario produceRenderizadorHtmlResumenDiario() {
        return new RenderizadorHtmlResumenDiario();
    }

    @Produces
    @Singleton
    public RenderizadorPdfComunicacionBaja produceRenderizadorPdfComunicacionBaja() {
        return new RenderizadorPdfComunicacionBaja();
    }

    @Produces
    @Singleton
    public RenderizadorHtmlComunicacionBaja produceRenderizadorHtmlComunicacionBaja() {
        return new RenderizadorHtmlComunicacionBaja();
    }

    @Produces
    @Singleton
    public SerializadorXmlFactura produceSerializadorXmlFactura() {
        return new SerializadorXmlFactura();
    }

    @Produces
    @Singleton
    public SerializadorXmlNotaCredito produceSerializadorXmlNotaCredito() {
        return new SerializadorXmlNotaCredito();
    }

    @Produces
    @Singleton
    public SerializadorXmlNotaDebito produceSerializadorXmlNotaDebito() {
        return new SerializadorXmlNotaDebito();
    }

    @Produces
    @Singleton
    public SerializadorXmlGuiaRemision produceSerializadorXmlGuiaRemision() {
        return new SerializadorXmlGuiaRemision();
    }

    @Produces
    @Singleton
    public SerializadorXmlComunicacionBaja produceSerializadorXmlComunicacionBaja() {
        return new SerializadorXmlComunicacionBaja();
    }

    @Produces
    @Singleton
    public SerializadorXmlResumenDiario produceSerializadorXmlResumenDiario() {
        return new SerializadorXmlResumenDiario();
    }

    @Produces
    @Singleton
    public SerializadorXmlPercepcion produceSerializadorXmlPercepcion() {
        return new SerializadorXmlPercepcion();
    }

    @Produces
    @Singleton
    public SerializadorXmlRetencion produceSerializadorXmlRetencion() {
        return new SerializadorXmlRetencion();
    }

    @Produces
    @Singleton
    public ValidadorFactura produceValidadorFactura() {
        return new ValidadorFactura();
    }

    @Produces
    @Singleton
    public ValidadorNotaCredito produceValidadorNotaCredito() {
        return new ValidadorNotaCredito();
    }

    @Produces
    @Singleton
    public ValidadorNotaDebito produceValidadorNotaDebito() {
        return new ValidadorNotaDebito();
    }

    @Produces
    @Singleton
    public ValidadorGuiaRemision produceValidadorGuiaRemision() {
        return new ValidadorGuiaRemision();
    }

    @Produces
    @Singleton
    public ValidadorResumenDiario produceValidadorResumenDiario() {
        return new ValidadorResumenDiario();
    }

    @Produces
    @Singleton
    public ValidadorComunicacionBaja produceValidadorComunicacionBaja() {
        return new ValidadorComunicacionBaja();
    }

    @Produces
    @Singleton
    public ValidadorPercepcion produceValidadorPercepcion() {
        return new ValidadorPercepcion();
    }

    @Produces
    @Singleton
    public ValidadorRetencion produceValidadorRetencion() {
        return new ValidadorRetencion();
    }
}
