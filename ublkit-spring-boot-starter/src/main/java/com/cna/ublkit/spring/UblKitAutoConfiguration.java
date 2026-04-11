package com.cna.ublkit.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import com.cna.ublkit.render.pdf.*;
import com.cna.ublkit.render.html.*;
import com.cna.ublkit.ubl.xml.*;
import com.cna.ublkit.validation.validador.*;
import com.cna.ublkit.gateway.api.*;
import com.cna.ublkit.gateway.transporte.*;
import com.cna.ublkit.gateway.autenticacion.*;
import com.cna.ublkit.sign.api.ServicioFirma;

@AutoConfiguration
public class UblKitAutoConfiguration {

    // --- Renderizadores PDF ---
    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfFactura renderizadorPdfFactura() {
        return new RenderizadorPdfFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfGuiaRemision renderizadorPdfGuiaRemision() {
        return new RenderizadorPdfGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfNota renderizadorPdfNota() {
        return new RenderizadorPdfNota();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfComunicacionBaja renderizadorPdfComunicacionBaja() {
        return new RenderizadorPdfComunicacionBaja();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfResumenDiario renderizadorPdfResumenDiario() {
        return new RenderizadorPdfResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorTicketFactura renderizadorTicketFactura() {
        return new RenderizadorTicketFactura();
    }

    // --- Renderizadores HTML ---
    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlFactura renderizadorHtmlFactura() {
        return new RenderizadorHtmlFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlGuiaRemision renderizadorHtmlGuiaRemision() {
        return new RenderizadorHtmlGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlNota renderizadorHtmlNota() {
        return new RenderizadorHtmlNota();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlComunicacionBaja renderizadorHtmlComunicacionBaja() {
        return new RenderizadorHtmlComunicacionBaja();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlResumenDiario renderizadorHtmlResumenDiario() {
        return new RenderizadorHtmlResumenDiario();
    }

    // --- Serializadores XML ---
    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlFactura serializadorXmlFactura() {
        return new SerializadorXmlFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlNotaCredito serializadorXmlNotaCredito() {
        return new SerializadorXmlNotaCredito();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlNotaDebito serializadorXmlNotaDebito() {
        return new SerializadorXmlNotaDebito();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlGuiaRemision serializadorXmlGuiaRemision() {
        return new SerializadorXmlGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlComunicacionBaja serializadorXmlComunicacionBaja() {
        return new SerializadorXmlComunicacionBaja();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlResumenDiario serializadorXmlResumenDiario() {
        return new SerializadorXmlResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlPercepcion serializadorXmlPercepcion() {
        return new SerializadorXmlPercepcion();
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializadorXmlRetencion serializadorXmlRetencion() {
        return new SerializadorXmlRetencion();
    }

    // --- Validadores ---
    @Bean
    @ConditionalOnMissingBean
    public ValidadorFactura validadorFactura() {
        return new ValidadorFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorNotaCredito validadorNotaCredito() {
        return new ValidadorNotaCredito();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorNotaDebito validadorNotaDebito() {
        return new ValidadorNotaDebito();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorGuiaRemision validadorGuiaRemision() {
        return new ValidadorGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorComunicacionBaja validadorComunicacionBaja() {
        return new ValidadorComunicacionBaja();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorResumenDiario validadorResumenDiario() {
        return new ValidadorResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorPercepcion validadorPercepcion() {
        return new ValidadorPercepcion();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorRetencion validadorRetencion() {
        return new ValidadorRetencion();
    }

    // --- Gateway ---
    @Bean
    @ConditionalOnMissingBean
    public ClienteSoap clienteSoap() {
        return new HttpClienteNativoSoap();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClienteRest clienteRest() {
        return new HttpClienteNativoRest();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProveedorToken proveedorToken() {
        return new ProveedorTokenNativo();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasarelaSunat pasarelaSunat(ClienteSoap clienteSoap, ClienteRest clienteRest, ProveedorToken proveedorToken) {
        return new PasarelaSunatDefecto(clienteSoap, clienteRest, proveedorToken);
    }
}
