package com.cna.ublkit.spring;

import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.cna.ublkit.validation.validador.ValidadorFactura;
import com.cna.ublkit.gateway.api.PasarelaSunat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
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
        });
    }
}
