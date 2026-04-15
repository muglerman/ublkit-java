package com.cna.ublkit.spring;

import com.cna.ublkit.gateway.api.*;
import com.cna.ublkit.gateway.autenticacion.*;
import com.cna.ublkit.gateway.config.ConfiguracionGateway;
import com.cna.ublkit.gateway.transporte.*;
import com.cna.ublkit.render.html.*;
import com.cna.ublkit.render.pdf.*;
import com.cna.ublkit.storage.AlmacenDocumentos;
import com.cna.ublkit.storage.AlmacenLocalStorage;
import com.cna.ublkit.storage.AlmacenS3;
import com.cna.ublkit.ubl.xml.*;
import com.cna.ublkit.validation.validador.*;
import java.net.URI;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@AutoConfiguration
@EnableConfigurationProperties({UblKitProperties.class, UblKitStorageProperties.class})
public class UblKitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConfiguracionGateway configuracionGateway(UblKitProperties properties) {
        UblKitProperties.Gateway gateway = properties.getGateway();
        int maxIntentos = Math.max(1, gateway.getMaxIntentos());
        int maxConnections = Math.max(1, gateway.getMaxConnections());
        return new ConfiguracionGateway(
            gateway.resolveConnectTimeout(),
            gateway.resolveReadTimeout(),
            maxIntentos,
            maxConnections
        );
    }

    // --- Storage ---
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ublkit.storage", name = "type", havingValue = "local", matchIfMissing = true)
    public AlmacenDocumentos almacenLocalStorage(UblKitStorageProperties properties) {
        return new AlmacenLocalStorage(properties.getLocal().getBaseDirectory());
    }

    @Bean
    @ConditionalOnMissingBean(S3Client.class)
    @ConditionalOnProperty(prefix = "ublkit.storage", name = "type", havingValue = "s3")
    public S3Client s3Client(UblKitStorageProperties properties) {
        UblKitStorageProperties.S3 s3 = properties.getS3();
        validateS3Properties(s3);

        var builder = S3Client.builder()
                .region(Region.of(resolveRegion(s3.getRegion())))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3.getAccessKey(), s3.getSecretKey())))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(s3.isPathStyleAccess())
                        .build());

        if (StringUtils.hasText(s3.getEndpoint())) {
            builder.endpointOverride(URI.create(s3.getEndpoint()));
        }

        S3Client client = builder.build();
        ensureBucketExists(client, s3);
        return client;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ublkit.storage", name = "type", havingValue = "s3")
    public AlmacenDocumentos almacenS3Storage(S3Client s3Client, UblKitStorageProperties properties) {
        return new AlmacenS3(s3Client, properties.getS3().getBucket());
    }

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
    public ClienteSoap clienteSoap(ConfiguracionGateway configuracionGateway) {
        return new HttpClienteNativoSoap(
                configuracionGateway.connectTimeout(),
                configuracionGateway.readTimeout(),
                configuracionGateway.maxConnections()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ClienteRest clienteRest(ConfiguracionGateway configuracionGateway) {
        return new HttpClienteNativoRest(
                configuracionGateway.connectTimeout(),
                configuracionGateway.readTimeout(),
                configuracionGateway.maxConnections()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ProveedorToken proveedorToken(ConfiguracionGateway configuracionGateway) {
        return new ProveedorTokenNativo(
                configuracionGateway.connectTimeout(),
                configuracionGateway.readTimeout(),
                configuracionGateway.maxConnections()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public PasarelaSunat pasarelaSunat(ClienteSoap clienteSoap,
                                       ClienteRest clienteRest,
                                       ProveedorToken proveedorToken,
                                       ConfiguracionGateway configuracionGateway) {
        return new PasarelaSunatDefecto(clienteSoap, clienteRest, proveedorToken, configuracionGateway);
    }

    private void validateS3Properties(UblKitStorageProperties.S3 s3) {
        if (!StringUtils.hasText(s3.getAccessKey())) {
            throw new IllegalStateException("ublkit.storage.s3.access-key es obligatorio cuando ublkit.storage.type=s3");
        }
        if (!StringUtils.hasText(s3.getSecretKey())) {
            throw new IllegalStateException("ublkit.storage.s3.secret-key es obligatorio cuando ublkit.storage.type=s3");
        }
        if (!StringUtils.hasText(s3.getBucket())) {
            throw new IllegalStateException("ublkit.storage.s3.bucket es obligatorio cuando ublkit.storage.type=s3");
        }
    }

    private String resolveRegion(String region) {
        return StringUtils.hasText(region) ? region : "us-east-1";
    }

    private void ensureBucketExists(S3Client s3Client, UblKitStorageProperties.S3 s3) {
        if (!s3.isCreateBucketOnStartup()) {
            return;
        }

        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(s3.getBucket()).build());
        } catch (S3Exception e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(s3.getBucket()).build());
        }
    }
}
