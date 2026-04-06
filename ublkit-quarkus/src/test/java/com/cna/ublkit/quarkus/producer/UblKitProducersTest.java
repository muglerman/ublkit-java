package com.cna.ublkit.quarkus.producer;

import com.cna.ublkit.render.html.*;
import com.cna.ublkit.render.pdf.*;
import com.cna.ublkit.ubl.xml.*;
import com.cna.ublkit.validation.validador.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive test suite for UblKitProducers - Quarkus CDI bean producers.
 * Tests producer methods, bean creation patterns, and singleton scope handling.
 */
@DisplayName("UblKitProducers Bean Producer Tests")
class UblKitProducersTest {

    private UblKitProducers producers;

    @BeforeEach
    void setUp() {
        producers = new UblKitProducers();
    }

    @Nested
    @DisplayName("PDF Renderer Producers")
    class PdfRendererProducersTests {

        @Test
        @DisplayName("Should produce RenderizadorPdfFactura bean")
        void produceRenderizadorPdfFactura() {
            RenderizadorPdfFactura resultado = producers.produceRenderizadorPdfFactura();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorPdfFactura.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorPdfNota bean")
        void produceRenderizadorPdfNota() {
            RenderizadorPdfNota resultado = producers.produceRenderizadorPdfNota();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorPdfNota.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorPdfGuiaRemision bean")
        void produceRenderizadorPdfGuiaRemision() {
            RenderizadorPdfGuiaRemision resultado = producers.produceRenderizadorPdfGuiaRemision();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorPdfGuiaRemision.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorPdfResumenDiario bean")
        void produceRenderizadorPdfResumenDiario() {
            RenderizadorPdfResumenDiario resultado = producers.produceRenderizadorPdfResumenDiario();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorPdfResumenDiario.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorPdfComunicacionBaja bean")
        void produceRenderizadorPdfComunicacionBaja() {
            RenderizadorPdfComunicacionBaja resultado = producers.produceRenderizadorPdfComunicacionBaja();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorPdfComunicacionBaja.class);
        }
    }

    @Nested
    @DisplayName("HTML Renderer Producers")
    class HtmlRendererProducersTests {

        @Test
        @DisplayName("Should produce RenderizadorHtmlFactura bean")
        void produceRenderizadorHtmlFactura() {
            RenderizadorHtmlFactura resultado = producers.produceRenderizadorHtmlFactura();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorHtmlFactura.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorHtmlNota bean")
        void produceRenderizadorHtmlNota() {
            RenderizadorHtmlNota resultado = producers.produceRenderizadorHtmlNota();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorHtmlNota.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorHtmlGuiaRemision bean")
        void produceRenderizadorHtmlGuiaRemision() {
            RenderizadorHtmlGuiaRemision resultado = producers.produceRenderizadorHtmlGuiaRemision();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorHtmlGuiaRemision.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorHtmlResumenDiario bean")
        void produceRenderizadorHtmlResumenDiario() {
            RenderizadorHtmlResumenDiario resultado = producers.produceRenderizadorHtmlResumenDiario();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorHtmlResumenDiario.class);
        }

        @Test
        @DisplayName("Should produce RenderizadorHtmlComunicacionBaja bean")
        void produceRenderizadorHtmlComunicacionBaja() {
            RenderizadorHtmlComunicacionBaja resultado = producers.produceRenderizadorHtmlComunicacionBaja();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(RenderizadorHtmlComunicacionBaja.class);
        }
    }

    @Nested
    @DisplayName("XML Serializer Producers")
    class XmlSerializerProducersTests {

        @Test
        @DisplayName("Should produce SerializadorXmlFactura bean")
        void produceSerializadorXmlFactura() {
            SerializadorXmlFactura resultado = producers.produceSerializadorXmlFactura();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlFactura.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlNotaCredito bean")
        void produceSerializadorXmlNotaCredito() {
            SerializadorXmlNotaCredito resultado = producers.produceSerializadorXmlNotaCredito();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlNotaCredito.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlNotaDebito bean")
        void produceSerializadorXmlNotaDebito() {
            SerializadorXmlNotaDebito resultado = producers.produceSerializadorXmlNotaDebito();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlNotaDebito.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlGuiaRemision bean")
        void produceSerializadorXmlGuiaRemision() {
            SerializadorXmlGuiaRemision resultado = producers.produceSerializadorXmlGuiaRemision();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlGuiaRemision.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlComunicacionBaja bean")
        void produceSerializadorXmlComunicacionBaja() {
            SerializadorXmlComunicacionBaja resultado = producers.produceSerializadorXmlComunicacionBaja();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlComunicacionBaja.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlResumenDiario bean")
        void produceSerializadorXmlResumenDiario() {
            SerializadorXmlResumenDiario resultado = producers.produceSerializadorXmlResumenDiario();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlResumenDiario.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlPercepcion bean")
        void produceSerializadorXmlPercepcion() {
            SerializadorXmlPercepcion resultado = producers.produceSerializadorXmlPercepcion();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlPercepcion.class);
        }

        @Test
        @DisplayName("Should produce SerializadorXmlRetencion bean")
        void produceSerializadorXmlRetencion() {
            SerializadorXmlRetencion resultado = producers.produceSerializadorXmlRetencion();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(SerializadorXmlRetencion.class);
        }
    }

    @Nested
    @DisplayName("Validator Producers")
    class ValidatorProducersTests {

        @Test
        @DisplayName("Should produce ValidadorFactura bean")
        void produceValidadorFactura() {
            ValidadorFactura resultado = producers.produceValidadorFactura();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorFactura.class);
        }

        @Test
        @DisplayName("Should produce ValidadorNotaCredito bean")
        void produceValidadorNotaCredito() {
            ValidadorNotaCredito resultado = producers.produceValidadorNotaCredito();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorNotaCredito.class);
        }

        @Test
        @DisplayName("Should produce ValidadorNotaDebito bean")
        void produceValidadorNotaDebito() {
            ValidadorNotaDebito resultado = producers.produceValidadorNotaDebito();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorNotaDebito.class);
        }

        @Test
        @DisplayName("Should produce ValidadorGuiaRemision bean")
        void produceValidadorGuiaRemision() {
            ValidadorGuiaRemision resultado = producers.produceValidadorGuiaRemision();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorGuiaRemision.class);
        }

        @Test
        @DisplayName("Should produce ValidadorResumenDiario bean")
        void produceValidadorResumenDiario() {
            ValidadorResumenDiario resultado = producers.produceValidadorResumenDiario();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorResumenDiario.class);
        }

        @Test
        @DisplayName("Should produce ValidadorComunicacionBaja bean")
        void produceValidadorComunicacionBaja() {
            ValidadorComunicacionBaja resultado = producers.produceValidadorComunicacionBaja();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorComunicacionBaja.class);
        }

        @Test
        @DisplayName("Should produce ValidadorPercepcion bean")
        void produceValidadorPercepcion() {
            ValidadorPercepcion resultado = producers.produceValidadorPercepcion();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorPercepcion.class);
        }

        @Test
        @DisplayName("Should produce ValidadorRetencion bean")
        void produceValidadorRetencion() {
            ValidadorRetencion resultado = producers.produceValidadorRetencion();

            assertThat(resultado).isNotNull();
            assertThat(resultado).isInstanceOf(ValidadorRetencion.class);
        }
    }

    @Nested
    @DisplayName("Singleton Scope Tests")
    class SingletonScopeTests {

        @Test
        @DisplayName("Should produce same instance for PDF Factura renderer (singleton)")
        void singletonPdfFactura() {
            RenderizadorPdfFactura bean1 = producers.produceRenderizadorPdfFactura();
            RenderizadorPdfFactura bean2 = producers.produceRenderizadorPdfFactura();

            assertThat(bean1).isSameAs(bean2);
        }

        @Test
        @DisplayName("Should produce same instance for HTML Factura renderer (singleton)")
        void singletonHtmlFactura() {
            RenderizadorHtmlFactura bean1 = producers.produceRenderizadorHtmlFactura();
            RenderizadorHtmlFactura bean2 = producers.produceRenderizadorHtmlFactura();

            assertThat(bean1).isSameAs(bean2);
        }

        @Test
        @DisplayName("Should produce same instance for validators (singleton)")
        void singletonValidator() {
            ValidadorFactura bean1 = producers.produceValidadorFactura();
            ValidadorFactura bean2 = producers.produceValidadorFactura();

            assertThat(bean1).isSameAs(bean2);
        }

        @Test
        @DisplayName("Should produce same instance for XML serializers (singleton)")
        void singletonSerializer() {
            SerializadorXmlFactura bean1 = producers.produceSerializadorXmlFactura();
            SerializadorXmlFactura bean2 = producers.produceSerializadorXmlFactura();

            assertThat(bean1).isSameAs(bean2);
        }
    }

    @Nested
    @DisplayName("Producer Independence Tests")
    class ProducerIndependenceTests {

        @Test
        @DisplayName("Should produce different instances for different types")
        void diferentesInstancias() {
            RenderizadorPdfFactura pdfFactura = producers.produceRenderizadorPdfFactura();
            RenderizadorHtmlFactura htmlFactura = producers.produceRenderizadorHtmlFactura();

            assertThat(pdfFactura).isNotNull();
            assertThat(htmlFactura).isNotNull();
            assertThat(pdfFactura).isNotInstanceOf(RenderizadorHtmlFactura.class);
        }

        @Test
        @DisplayName("Should produce all beans without errors")
        void produccionCompleta() {
            assertThatNoException().isThrownBy(() -> {
                producers.produceRenderizadorPdfFactura();
                producers.produceRenderizadorHtmlFactura();
                producers.produceValidadorFactura();
                producers.produceSerializadorXmlFactura();
            });
        }
    }
}
