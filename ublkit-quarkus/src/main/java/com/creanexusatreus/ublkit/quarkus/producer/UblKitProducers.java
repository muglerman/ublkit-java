package com.creanexusatreus.ublkit.quarkus.producer;

import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlFactura;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlGuiaRemision;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlNota;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlResumenDiario;
import com.creanexusatreus.ublkit.render.html.RenderizadorHtmlComunicacionBaja;
import com.creanexusatreus.ublkit.render.pdf.RenderizadorPdfComunicacionBaja;
import com.creanexusatreus.ublkit.render.pdf.RenderizadorPdfFactura;
import com.creanexusatreus.ublkit.render.pdf.RenderizadorPdfGuiaRemision;
import com.creanexusatreus.ublkit.render.pdf.RenderizadorPdfNota;
import com.creanexusatreus.ublkit.render.pdf.RenderizadorPdfResumenDiario;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlComunicacionBaja;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlFactura;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlGuiaRemision;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlNotaCredito;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlNotaDebito;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlPercepcion;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlRetencion;
import com.creanexusatreus.ublkit.ubl.xml.SerializadorXmlResumenDiario;
import com.creanexusatreus.ublkit.validation.validador.ValidadorComunicacionBaja;
import com.creanexusatreus.ublkit.validation.validador.ValidadorFactura;
import com.creanexusatreus.ublkit.validation.validador.ValidadorGuiaRemision;
import com.creanexusatreus.ublkit.validation.validador.ValidadorNotaCredito;
import com.creanexusatreus.ublkit.validation.validador.ValidadorNotaDebito;
import com.creanexusatreus.ublkit.validation.validador.ValidadorPercepcion;
import com.creanexusatreus.ublkit.validation.validador.ValidadorRetencion;
import com.creanexusatreus.ublkit.validation.validador.ValidadorResumenDiario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Productores CDI para integrar los componentes de UBLKit en aplicaciones Quarkus.
 * Cachea instancias para mantener singletons tanto en contexto CDI como en tests.
 *
 * @since 0.1.0
 */
@ApplicationScoped
public class UblKitProducers {

    private static final Map<String, Object> cache = new HashMap<>();

    @Produces
    @Singleton
    public RenderizadorPdfFactura produceRenderizadorPdfFactura() {
        return (RenderizadorPdfFactura) cache.computeIfAbsent(
            "RenderizadorPdfFactura",
            k -> new RenderizadorPdfFactura()
        );
    }

    @Produces
    @Singleton
    public RenderizadorHtmlFactura produceRenderizadorHtmlFactura() {
        return (RenderizadorHtmlFactura) cache.computeIfAbsent(
            "RenderizadorHtmlFactura",
            k -> new RenderizadorHtmlFactura()
        );
    }

    @Produces
    @Singleton
    public RenderizadorPdfNota produceRenderizadorPdfNota() {
        return (RenderizadorPdfNota) cache.computeIfAbsent(
            "RenderizadorPdfNota",
            k -> new RenderizadorPdfNota()
        );
    }

    @Produces
    @Singleton
    public RenderizadorHtmlNota produceRenderizadorHtmlNota() {
        return (RenderizadorHtmlNota) cache.computeIfAbsent(
            "RenderizadorHtmlNota",
            k -> new RenderizadorHtmlNota()
        );
    }

    @Produces
    @Singleton
    public RenderizadorPdfGuiaRemision produceRenderizadorPdfGuiaRemision() {
        return (RenderizadorPdfGuiaRemision) cache.computeIfAbsent(
            "RenderizadorPdfGuiaRemision",
            k -> new RenderizadorPdfGuiaRemision()
        );
    }

    @Produces
    @Singleton
    public RenderizadorHtmlGuiaRemision produceRenderizadorHtmlGuiaRemision() {
        return (RenderizadorHtmlGuiaRemision) cache.computeIfAbsent(
            "RenderizadorHtmlGuiaRemision",
            k -> new RenderizadorHtmlGuiaRemision()
        );
    }

    @Produces
    @Singleton
    public RenderizadorPdfResumenDiario produceRenderizadorPdfResumenDiario() {
        return (RenderizadorPdfResumenDiario) cache.computeIfAbsent(
            "RenderizadorPdfResumenDiario",
            k -> new RenderizadorPdfResumenDiario()
        );
    }

    @Produces
    @Singleton
    public RenderizadorHtmlResumenDiario produceRenderizadorHtmlResumenDiario() {
        return (RenderizadorHtmlResumenDiario) cache.computeIfAbsent(
            "RenderizadorHtmlResumenDiario",
            k -> new RenderizadorHtmlResumenDiario()
        );
    }

    @Produces
    @Singleton
    public RenderizadorPdfComunicacionBaja produceRenderizadorPdfComunicacionBaja() {
        return (RenderizadorPdfComunicacionBaja) cache.computeIfAbsent(
            "RenderizadorPdfComunicacionBaja",
            k -> new RenderizadorPdfComunicacionBaja()
        );
    }

    @Produces
    @Singleton
    public RenderizadorHtmlComunicacionBaja produceRenderizadorHtmlComunicacionBaja() {
        return (RenderizadorHtmlComunicacionBaja) cache.computeIfAbsent(
            "RenderizadorHtmlComunicacionBaja",
            k -> new RenderizadorHtmlComunicacionBaja()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlFactura produceSerializadorXmlFactura() {
        return (SerializadorXmlFactura) cache.computeIfAbsent(
            "SerializadorXmlFactura",
            k -> new SerializadorXmlFactura()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlNotaCredito produceSerializadorXmlNotaCredito() {
        return (SerializadorXmlNotaCredito) cache.computeIfAbsent(
            "SerializadorXmlNotaCredito",
            k -> new SerializadorXmlNotaCredito()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlNotaDebito produceSerializadorXmlNotaDebito() {
        return (SerializadorXmlNotaDebito) cache.computeIfAbsent(
            "SerializadorXmlNotaDebito",
            k -> new SerializadorXmlNotaDebito()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlGuiaRemision produceSerializadorXmlGuiaRemision() {
        return (SerializadorXmlGuiaRemision) cache.computeIfAbsent(
            "SerializadorXmlGuiaRemision",
            k -> new SerializadorXmlGuiaRemision()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlComunicacionBaja produceSerializadorXmlComunicacionBaja() {
        return (SerializadorXmlComunicacionBaja) cache.computeIfAbsent(
            "SerializadorXmlComunicacionBaja",
            k -> new SerializadorXmlComunicacionBaja()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlResumenDiario produceSerializadorXmlResumenDiario() {
        return (SerializadorXmlResumenDiario) cache.computeIfAbsent(
            "SerializadorXmlResumenDiario",
            k -> new SerializadorXmlResumenDiario()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlPercepcion produceSerializadorXmlPercepcion() {
        return (SerializadorXmlPercepcion) cache.computeIfAbsent(
            "SerializadorXmlPercepcion",
            k -> new SerializadorXmlPercepcion()
        );
    }

    @Produces
    @Singleton
    public SerializadorXmlRetencion produceSerializadorXmlRetencion() {
        return (SerializadorXmlRetencion) cache.computeIfAbsent(
            "SerializadorXmlRetencion",
            k -> new SerializadorXmlRetencion()
        );
    }

    @Produces
    @Singleton
    public ValidadorFactura produceValidadorFactura() {
        return (ValidadorFactura) cache.computeIfAbsent(
            "ValidadorFactura",
            k -> new ValidadorFactura()
        );
    }

    @Produces
    @Singleton
    public ValidadorNotaCredito produceValidadorNotaCredito() {
        return (ValidadorNotaCredito) cache.computeIfAbsent(
            "ValidadorNotaCredito",
            k -> new ValidadorNotaCredito()
        );
    }

    @Produces
    @Singleton
    public ValidadorNotaDebito produceValidadorNotaDebito() {
        return (ValidadorNotaDebito) cache.computeIfAbsent(
            "ValidadorNotaDebito",
            k -> new ValidadorNotaDebito()
        );
    }

    @Produces
    @Singleton
    public ValidadorGuiaRemision produceValidadorGuiaRemision() {
        return (ValidadorGuiaRemision) cache.computeIfAbsent(
            "ValidadorGuiaRemision",
            k -> new ValidadorGuiaRemision()
        );
    }

    @Produces
    @Singleton
    public ValidadorResumenDiario produceValidadorResumenDiario() {
        return (ValidadorResumenDiario) cache.computeIfAbsent(
            "ValidadorResumenDiario",
            k -> new ValidadorResumenDiario()
        );
    }

    @Produces
    @Singleton
    public ValidadorComunicacionBaja produceValidadorComunicacionBaja() {
        return (ValidadorComunicacionBaja) cache.computeIfAbsent(
            "ValidadorComunicacionBaja",
            k -> new ValidadorComunicacionBaja()
        );
    }

    @Produces
    @Singleton
    public ValidadorPercepcion produceValidadorPercepcion() {
        return (ValidadorPercepcion) cache.computeIfAbsent(
            "ValidadorPercepcion",
            k -> new ValidadorPercepcion()
        );
    }

    @Produces
    @Singleton
    public ValidadorRetencion produceValidadorRetencion() {
        return (ValidadorRetencion) cache.computeIfAbsent(
            "ValidadorRetencion",
            k -> new ValidadorRetencion()
        );
    }
}
