package com.cna.ublkit.spring;

import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.cna.ublkit.validation.validador.ValidadorFactura;
import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.api.PasarelaSunatDefecto;
import com.cna.ublkit.gateway.config.ConfiguracionGateway;
import com.cna.ublkit.gateway.transporte.ClienteSoap;
import com.cna.ublkit.gateway.transporte.HttpClienteNativoSoap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UblKitAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(UblKitAutoConfiguration.class));

    @Test
    void autoConfiguration_ShouldRegisterBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(RenderizadorPdfFactura.class);
            assertThat(context).hasSingleBean(RenderizadorHtmlFactura.class);
            assertThat(context).hasSingleBean(SerializadorXmlFactura.class);
            assertThat(context).hasSingleBean(ValidadorFactura.class);
            assertThat(context).hasSingleBean(PasarelaSunat.class);
            assertThat(context).hasSingleBean(ConfiguracionGateway.class);
        });
    }

    @Test
    void autoConfiguration_ShouldBindGatewayProperties() {
        contextRunner
                .withPropertyValues(
                        "ublkit.gateway.connect-timeout=3s",
                        "ublkit.gateway.read-timeout=25s",
                        "ublkit.gateway.max-intentos=7"
                )
                .run(context -> {
                    ConfiguracionGateway config = context.getBean(ConfiguracionGateway.class);
                    assertThat(config.connectTimeout()).hasSeconds(3);
                    assertThat(config.readTimeout()).hasSeconds(25);
                    assertThat(config.maxIntentos()).isEqualTo(7);

                    PasarelaSunatDefecto pasarela = (PasarelaSunatDefecto) context.getBean(PasarelaSunat.class);
                    Object maxIntentos = ReflectionTestUtils.getField(pasarela, "maxIntentos");
                    assertThat(maxIntentos).isEqualTo(7);
                });
    }

    @Test
    void autoConfiguration_ShouldRespectUserProvidedBean() {
        ClienteSoap custom = new HttpClienteNativoSoap();

        contextRunner
                .withBean(ClienteSoap.class, () -> custom)
                .run(context -> assertThat(context.getBean(ClienteSoap.class)).isSameAs(custom));
    }
}
