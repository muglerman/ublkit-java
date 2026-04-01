package com.cna.ublkit.spring.config;

import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.sunat.LectorCsvCatalogos;
import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.api.PasarelaSunatDefecto;
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
import com.cna.ublkit.ubl.xml.*;
import com.cna.ublkit.validation.validador.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguración de Spring Boot para los componentes base de UBLKit.
 *
 * @since 0.1.0
 */
@AutoConfiguration
public class UblKitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfFactura renderizadorPdfFactura() {
        return new RenderizadorPdfFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlFactura renderizadorHtmlFactura() {
        return new RenderizadorHtmlFactura();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfNota renderizadorPdfNota() {
        return new RenderizadorPdfNota();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlNota renderizadorHtmlNota() {
        return new RenderizadorHtmlNota();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfGuiaRemision renderizadorPdfGuiaRemision() {
        return new RenderizadorPdfGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlGuiaRemision renderizadorHtmlGuiaRemision() {
        return new RenderizadorHtmlGuiaRemision();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfResumenDiario renderizadorPdfResumenDiario() {
        return new RenderizadorPdfResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlResumenDiario renderizadorHtmlResumenDiario() {
        return new RenderizadorHtmlResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorPdfComunicacionBaja renderizadorPdfComunicacionBaja() {
        return new RenderizadorPdfComunicacionBaja();
    }

    @Bean
    @ConditionalOnMissingBean
    public RenderizadorHtmlComunicacionBaja renderizadorHtmlComunicacionBaja() {
        return new RenderizadorHtmlComunicacionBaja();
    }

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
    public ValidadorResumenDiario validadorResumenDiario() {
        return new ValidadorResumenDiario();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidadorComunicacionBaja validadorComunicacionBaja() {
        return new ValidadorComunicacionBaja();
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

    @Bean
    @ConditionalOnMissingBean
    public ProveedorCatalogos proveedorCatalogos() {
        return new LectorCsvCatalogos();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasarelaSunat pasarelaSunat() {
        // Configuración por properties del sistema:
        // -Dublkit.sunat.ambiente=PRODUCCION|BETA
        String ambienteProp = System.getProperty("ublkit.sunat.ambiente", "BETA");
        TipoAmbiente.valueOf(ambienteProp.toUpperCase()); // validación temprana
        return new PasarelaSunatDefecto();
    }
}
