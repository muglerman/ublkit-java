package com.cna.ublkit.spring;

import com.cna.ublkit.gateway.api.PasarelaSunat;
import com.cna.ublkit.gateway.api.PasarelaSunatDefecto;
import com.cna.ublkit.gateway.config.ConfiguracionGateway;
import com.cna.ublkit.gateway.transporte.ClienteSoap;
import com.cna.ublkit.gateway.transporte.HttpClienteNativoSoap;
import com.cna.ublkit.render.html.RenderizadorHtmlFactura;
import com.cna.ublkit.render.pdf.RenderizadorPdfFactura;
import com.cna.ublkit.storage.AlmacenDocumentos;
import com.cna.ublkit.storage.AlmacenLocalStorage;
import com.cna.ublkit.storage.AlmacenS3;
import com.cna.ublkit.ubl.xml.SerializadorXmlFactura;
import com.cna.ublkit.validation.validador.ValidadorFactura;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
    void autoConfiguration_ShouldRegisterLocalStorageBeanByDefault() throws Exception {
        Path tempDir = Files.createTempDirectory("ublkit-storage-test");

        contextRunner
                .withPropertyValues("ublkit.storage.local.base-directory=" + tempDir)
                .run(context -> {
                    assertThat(context).hasSingleBean(AlmacenDocumentos.class);
                    assertThat(context.getBean(AlmacenDocumentos.class)).isInstanceOf(AlmacenLocalStorage.class);
                });
    }

    @Test
    void autoConfiguration_ShouldRegisterS3StorageBeanWhenConfigured() {
        contextRunner
                .withPropertyValues(
                        "ublkit.storage.type=s3",
                        "ublkit.storage.s3.endpoint=http://localhost:9100",
                        "ublkit.storage.s3.access-key=test-access",
                        "ublkit.storage.s3.secret-key=test-secret",
                        "ublkit.storage.s3.bucket=test-bucket",
                        "ublkit.storage.s3.region=us-east-1",
                        "ublkit.storage.s3.create-bucket-on-startup=false"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(AlmacenDocumentos.class);
                    assertThat(context.getBean(AlmacenDocumentos.class)).isInstanceOf(AlmacenS3.class);
                });
    }

    @Test
    void autoConfiguration_ShouldBindGatewayProperties() {
        contextRunner
                .withPropertyValues(
                        "ublkit.gateway.connect-timeout-ms=3000",
                        "ublkit.gateway.read-timeout-ms=10000",
                        "ublkit.gateway.max-intentos=7",
                        "ublkit.gateway.max-connections=120"
                )
                .run(context -> {
                    ConfiguracionGateway config = context.getBean(ConfiguracionGateway.class);
                    assertThat(config.connectTimeout()).hasSeconds(3);
                    assertThat(config.readTimeout()).hasSeconds(10);
                    assertThat(config.maxIntentos()).isEqualTo(7);
                    assertThat(config.maxConnections()).isEqualTo(120);

                    PasarelaSunatDefecto pasarela = (PasarelaSunatDefecto) context.getBean(PasarelaSunat.class);
                    Object maxIntentos = ReflectionTestUtils.getField(pasarela, "maxIntentos");
                    assertThat(maxIntentos).isEqualTo(7);
                });
    }

    @Test
    void autoConfiguration_ShouldKeepCompatibilityWithDurationProperties() {
        contextRunner
                .withPropertyValues(
                        "ublkit.gateway.connect-timeout=3s",
                        "ublkit.gateway.read-timeout=25s",
                        "ublkit.gateway.max-intentos=5"
                )
                .run(context -> {
                    ConfiguracionGateway config = context.getBean(ConfiguracionGateway.class);
                    assertThat(config.connectTimeout()).hasSeconds(3);
                    assertThat(config.readTimeout()).hasSeconds(25);
                    assertThat(config.maxIntentos()).isEqualTo(5);
                    assertThat(config.maxConnections()).isEqualTo(100);
                });
    }

    @Test
    void autoConfiguration_ShouldRespectUserProvidedBean() {
        ClienteSoap custom = new HttpClienteNativoSoap();

        contextRunner
                .withBean(ClienteSoap.class, () -> custom)
                .run(context -> assertThat(context.getBean(ClienteSoap.class)).isSameAs(custom));
    }

    @Test
    void autoConfiguration_ShouldRespectUserProvidedStorageBean() {
        AlmacenDocumentos custom = mock(AlmacenDocumentos.class);

        contextRunner
                .withBean(AlmacenDocumentos.class, () -> custom)
                .run(context -> assertThat(context.getBean(AlmacenDocumentos.class)).isSameAs(custom));
    }
}
