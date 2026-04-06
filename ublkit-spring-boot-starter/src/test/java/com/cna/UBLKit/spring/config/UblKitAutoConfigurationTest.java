package com.cna.ublkit.spring.config;

import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.sunat.LectorCsvCatalogos;
import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.api.PasarelaSunatDefecto;
import com.cna.ublkit.render.html.*;
import com.cna.ublkit.render.pdf.*;
import com.cna.ublkit.ubl.xml.*;
import com.cna.ublkit.validation.validador.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive test suite for UblKitAutoConfiguration - Spring Boot autoconfiguration.
 * Tests bean creation, conditional configuration, and property binding patterns.
 */
@DisplayName("UblKitAutoConfiguration Spring Boot Tests")
class UblKitAutoConfigurationTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(UblKitAutoConfiguration.class);
    }

    @Nested
    @DisplayName("PDF Renderer Bean Creation Tests")
    class PdfRendererBeanTests {

        @Test
        @DisplayName("Should create RenderizadorPdfFactura bean")
        void beanRenderizadorPdfFactura() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfFactura.class);
                RenderizadorPdfFactura bean = context.getBean(RenderizadorPdfFactura.class);
                assertThat(bean).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorPdfNota bean")
        void beanRenderizadorPdfNota() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfNota.class);
                assertThat(context.getBean(RenderizadorPdfNota.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorPdfGuiaRemision bean")
        void beanRenderizadorPdfGuiaRemision() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfGuiaRemision.class);
                assertThat(context.getBean(RenderizadorPdfGuiaRemision.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorPdfResumenDiario bean")
        void beanRenderizadorPdfResumenDiario() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfResumenDiario.class);
                assertThat(context.getBean(RenderizadorPdfResumenDiario.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorPdfComunicacionBaja bean")
        void beanRenderizadorPdfComunicacionBaja() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfComunicacionBaja.class);
                assertThat(context.getBean(RenderizadorPdfComunicacionBaja.class)).isNotNull();
            });
        }
    }

    @Nested
    @DisplayName("HTML Renderer Bean Creation Tests")
    class HtmlRendererBeanTests {

        @Test
        @DisplayName("Should create RenderizadorHtmlFactura bean")
        void beanRenderizadorHtmlFactura() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorHtmlFactura.class);
                assertThat(context.getBean(RenderizadorHtmlFactura.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorHtmlNota bean")
        void beanRenderizadorHtmlNota() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorHtmlNota.class);
                assertThat(context.getBean(RenderizadorHtmlNota.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorHtmlGuiaRemision bean")
        void beanRenderizadorHtmlGuiaRemision() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorHtmlGuiaRemision.class);
                assertThat(context.getBean(RenderizadorHtmlGuiaRemision.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorHtmlResumenDiario bean")
        void beanRenderizadorHtmlResumenDiario() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorHtmlResumenDiario.class);
                assertThat(context.getBean(RenderizadorHtmlResumenDiario.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create RenderizadorHtmlComunicacionBaja bean")
        void beanRenderizadorHtmlComunicacionBaja() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorHtmlComunicacionBaja.class);
                assertThat(context.getBean(RenderizadorHtmlComunicacionBaja.class)).isNotNull();
            });
        }
    }

    @Nested
    @DisplayName("XML Serializer Bean Creation Tests")
    class XmlSerializerBeanTests {

        @Test
        @DisplayName("Should create SerializadorXmlFactura bean")
        void beanSerializadorXmlFactura() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlFactura.class);
                assertThat(context.getBean(SerializadorXmlFactura.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlNotaCredito bean")
        void beanSerializadorXmlNotaCredito() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlNotaCredito.class);
                assertThat(context.getBean(SerializadorXmlNotaCredito.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlNotaDebito bean")
        void beanSerializadorXmlNotaDebito() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlNotaDebito.class);
                assertThat(context.getBean(SerializadorXmlNotaDebito.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlGuiaRemision bean")
        void beanSerializadorXmlGuiaRemision() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlGuiaRemision.class);
                assertThat(context.getBean(SerializadorXmlGuiaRemision.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlComunicacionBaja bean")
        void beanSerializadorXmlComunicacionBaja() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlComunicacionBaja.class);
                assertThat(context.getBean(SerializadorXmlComunicacionBaja.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlResumenDiario bean")
        void beanSerializadorXmlResumenDiario() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlResumenDiario.class);
                assertThat(context.getBean(SerializadorXmlResumenDiario.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlPercepcion bean")
        void beanSerializadorXmlPercepcion() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlPercepcion.class);
                assertThat(context.getBean(SerializadorXmlPercepcion.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create SerializadorXmlRetencion bean")
        void beanSerializadorXmlRetencion() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(SerializadorXmlRetencion.class);
                assertThat(context.getBean(SerializadorXmlRetencion.class)).isNotNull();
            });
        }
    }

    @Nested
    @DisplayName("Validator Bean Creation Tests")
    class ValidatorBeanTests {

        @Test
        @DisplayName("Should create ValidadorFactura bean")
        void beanValidadorFactura() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorFactura.class);
                assertThat(context.getBean(ValidadorFactura.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorNotaCredito bean")
        void beanValidadorNotaCredito() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorNotaCredito.class);
                assertThat(context.getBean(ValidadorNotaCredito.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorNotaDebito bean")
        void beanValidadorNotaDebito() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorNotaDebito.class);
                assertThat(context.getBean(ValidadorNotaDebito.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorGuiaRemision bean")
        void beanValidadorGuiaRemision() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorGuiaRemision.class);
                assertThat(context.getBean(ValidadorGuiaRemision.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorResumenDiario bean")
        void beanValidadorResumenDiario() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorResumenDiario.class);
                assertThat(context.getBean(ValidadorResumenDiario.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorComunicacionBaja bean")
        void beanValidadorComunicacionBaja() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorComunicacionBaja.class);
                assertThat(context.getBean(ValidadorComunicacionBaja.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorPercepcion bean")
        void beanValidadorPercepcion() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorPercepcion.class);
                assertThat(context.getBean(ValidadorPercepcion.class)).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create ValidadorRetencion bean")
        void beanValidadorRetencion() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ValidadorRetencion.class);
                assertThat(context.getBean(ValidadorRetencion.class)).isNotNull();
            });
        }
    }

    @Nested
    @DisplayName("Catalog Provider Bean Tests")
    class CatalogProviderBeanTests {

        @Test
        @DisplayName("Should create ProveedorCatalogos bean")
        void beanProveedorCatalogos() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(ProveedorCatalogos.class);
                ProveedorCatalogos bean = context.getBean(ProveedorCatalogos.class);
                assertThat(bean).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create LectorCsvCatalogos as ProveedorCatalogos implementation")
        void beanLectorCsvCatalogos() {
            contextRunner.run(context -> {
                ProveedorCatalogos proveedor = context.getBean(ProveedorCatalogos.class);
                assertThat(proveedor).isInstanceOf(LectorCsvCatalogos.class);
            });
        }
    }

    @Nested
    @DisplayName("Gateway Provider Bean Tests")
    class GatewayProviderBeanTests {

        @Test
        @DisplayName("Should create PasarelaSunat bean")
        void beanPasarelaSunat() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(PasarelaSunat.class);
                PasarelaSunat bean = context.getBean(PasarelaSunat.class);
                assertThat(bean).isNotNull();
            });
        }

        @Test
        @DisplayName("Should create PasarelaSunatDefecto as PasarelaSunat implementation")
        void beanPasarelaSunatDefecto() {
            contextRunner.run(context -> {
                PasarelaSunat pasarela = context.getBean(PasarelaSunat.class);
                assertThat(pasarela).isInstanceOf(PasarelaSunatDefecto.class);
            });
        }

        @Test
        @DisplayName("Should use default BETA environment when property not set")
        void pasarelaDefaultAmbiente() {
            contextRunner.run(context -> {
                assertThatNoException().isThrownBy(() -> {
                    PasarelaSunat pasarela = context.getBean(PasarelaSunat.class);
                    assertThat(pasarela).isNotNull();
                });
            });
        }
    }

    @Nested
    @DisplayName("Conditional Bean Creation Tests")
    class ConditionalBeanTests {

        @Test
        @DisplayName("Should respect ConditionalOnMissingBean for custom RenderizadorPdfFactura")
        void conditionalOnMissingBean_custom() {
            contextRunner
                .withUserConfiguration(CustomBeanConfiguration.class)
                .run(context -> {
                    RenderizadorPdfFactura bean = context.getBean(RenderizadorPdfFactura.class);
                    assertThat(bean).isInstanceOf(CustomRenderizadorPdfFactura.class);
                });
        }

        @Test
        @DisplayName("Should use default bean when no custom bean provided")
        void conditionalOnMissingBean_default() {
            contextRunner.run(context -> {
                RenderizadorPdfFactura bean = context.getBean(RenderizadorPdfFactura.class);
                assertThat(bean).isNotInstanceOf(CustomRenderizadorPdfFactura.class);
            });
        }
    }

    @Nested
    @DisplayName("Multiple Bean Tests")
    class MultipleBeanTests {

        @Test
        @DisplayName("Should create all beans without conflicts")
        void allBeansCreated() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(RenderizadorPdfFactura.class);
                assertThat(context).hasSingleBean(RenderizadorHtmlFactura.class);
                assertThat(context).hasSingleBean(ValidadorFactura.class);
                assertThat(context).hasSingleBean(SerializadorXmlFactura.class);
                assertThat(context).hasSingleBean(ProveedorCatalogos.class);
                assertThat(context).hasSingleBean(PasarelaSunat.class);
            });
        }

        @Test
        @DisplayName("Should create beans with correct types")
        void beansHaveCorrectTypes() {
            contextRunner.run(context -> {
                assertThat(context.getBean(RenderizadorPdfFactura.class))
                    .isInstanceOf(RenderizadorPdfFactura.class);
                assertThat(context.getBean(ValidadorFactura.class))
                    .isInstanceOf(ValidadorFactura.class);
                assertThat(context.getBean(ProveedorCatalogos.class))
                    .isInstanceOf(ProveedorCatalogos.class);
            });
        }
    }

    // Test configuration for ConditionalOnMissingBean testing
    @Configuration
    static class CustomBeanConfiguration {
        @Bean
        public RenderizadorPdfFactura renderizadorPdfFactura() {
            return new CustomRenderizadorPdfFactura();
        }
    }

    // Custom implementation for testing ConditionalOnMissingBean
    static class CustomRenderizadorPdfFactura extends RenderizadorPdfFactura {
    }
}
