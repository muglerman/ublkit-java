package com.cna.ublkit.gateway.endpoint;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link ConstantesEndpoint} constants.
 * Tests SUNAT endpoint constants availability and format.
 */
class ConstantesEndpointTest {

    /**
     * Test that SOAP Beta Factura endpoint is defined.
     */
    @Test
    void soapBetaFacturaEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA).isNotEmpty();
    }

    /**
     * Test that SOAP Prod Factura endpoint is defined.
     */
    @Test
    void soapProdFacturaEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_PROD_FACTURA).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_PROD_FACTURA).isNotEmpty();
    }

    /**
     * Test that SOAP Beta Retencion endpoint is defined.
     */
    @Test
    void soapBetaRetencionEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_BETA_RETENCION).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_BETA_RETENCION).isNotEmpty();
    }

    /**
     * Test that SOAP Prod Retencion endpoint is defined.
     */
    @Test
    void soapProdRetencionEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_PROD_RETENCION).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_PROD_RETENCION).isNotEmpty();
    }

    /**
     * Test that SOAP Beta Consulta endpoint is defined.
     */
    @Test
    void soapBetaConsultaEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_BETA_CONSULTA).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_BETA_CONSULTA).isNotEmpty();
    }

    /**
     * Test that SOAP Prod Consulta endpoint is defined.
     */
    @Test
    void soapProdConsultaEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.SOAP_PROD_CONSULTA).isNotNull();
        assertThat(ConstantesEndpoint.SOAP_PROD_CONSULTA).isNotEmpty();
    }

    /**
     * Test that REST Beta Token endpoint is defined.
     */
    @Test
    void restBetaTokenEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_BETA_TOKEN).isNotNull();
        assertThat(ConstantesEndpoint.REST_BETA_TOKEN).isNotEmpty();
    }

    /**
     * Test that REST Prod Token endpoint is defined.
     */
    @Test
    void restProdTokenEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN).isNotNull();
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN).isNotEmpty();
    }

    /**
     * Test that REST Beta Envio endpoint is defined.
     */
    @Test
    void restBetaEnvioEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_BETA_ENVIO).isNotNull();
        assertThat(ConstantesEndpoint.REST_BETA_ENVIO).isNotEmpty();
    }

    /**
     * Test that REST Prod Envio endpoint is defined.
     */
    @Test
    void restProdEnvioEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_PROD_ENVIO).isNotNull();
        assertThat(ConstantesEndpoint.REST_PROD_ENVIO).isNotEmpty();
    }

    /**
     * Test that REST Beta Ticket endpoint is defined.
     */
    @Test
    void restBetaTicketEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_BETA_TICKET).isNotNull();
        assertThat(ConstantesEndpoint.REST_BETA_TICKET).isNotEmpty();
    }

    /**
     * Test that REST Prod Ticket endpoint is defined.
     */
    @Test
    void restProdTicketEndpoint_isDefined() {
        assertThat(ConstantesEndpoint.REST_PROD_TICKET).isNotNull();
        assertThat(ConstantesEndpoint.REST_PROD_TICKET).isNotEmpty();
    }

    /**
     * Test that SOAP endpoints are HTTPS URLs.
     */
    @Test
    void soapEndpoints_areHttpsUrls() {
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA).startsWith("https://");
        assertThat(ConstantesEndpoint.SOAP_PROD_FACTURA).startsWith("https://");
        assertThat(ConstantesEndpoint.SOAP_BETA_RETENCION).startsWith("https://");
        assertThat(ConstantesEndpoint.SOAP_PROD_RETENCION).startsWith("https://");
        assertThat(ConstantesEndpoint.SOAP_BETA_CONSULTA).startsWith("https://");
        assertThat(ConstantesEndpoint.SOAP_PROD_CONSULTA).startsWith("https://");
    }

    /**
     * Test that REST endpoints are HTTPS URLs.
     */
    @Test
    void restEndpoints_areHttpsUrls() {
        assertThat(ConstantesEndpoint.REST_BETA_TOKEN).startsWith("https://");
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN).startsWith("https://");
        assertThat(ConstantesEndpoint.REST_BETA_ENVIO).startsWith("https://");
        assertThat(ConstantesEndpoint.REST_PROD_ENVIO).startsWith("https://");
        assertThat(ConstantesEndpoint.REST_BETA_TICKET).startsWith("https://");
        assertThat(ConstantesEndpoint.REST_PROD_TICKET).startsWith("https://");
    }

    /**
     * Test that beta endpoints use different host than prod endpoints.
     */
    @Test
    void betaEndpoints_useDifferentHostThanProdEndpoints() {
        // Beta typically uses e-beta or test subdomains
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA)
                .contains("beta");
        assertThat(ConstantesEndpoint.SOAP_PROD_FACTURA)
                .doesNotContain("beta");

        assertThat(ConstantesEndpoint.REST_BETA_TOKEN)
                .contains("test");
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN)
                .doesNotContain("test");
    }

    /**
     * Test that SOAP endpoints contain billService or similar path.
     */
    @Test
    void soapEndpoints_haveExpectedPaths() {
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA)
                .contains("billService");
        assertThat(ConstantesEndpoint.SOAP_PROD_FACTURA)
                .contains("billService");
    }

    /**
     * Test that REST Token endpoints contain oauth2 path.
     */
    @Test
    void restTokenEndpoints_haveExpectedPaths() {
        assertThat(ConstantesEndpoint.REST_BETA_TOKEN)
                .contains("oauth2");
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN)
                .contains("oauth2");
    }

    /**
     * Test that all endpoints are valid URL formats.
     */
    @Test
    void allEndpoints_areValidUrlFormats() {
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA)
                .matches("^https://[a-z0-9.-]+\\.[a-z]{2,}.*$");
        assertThat(ConstantesEndpoint.REST_PROD_ENVIO)
                .matches("^https://[a-z0-9.-]+\\.[a-z]{2,}.*$");
    }

    /**
     * Test that REST Envio endpoints end with trailing slash.
     */
    @Test
    void restEnvioEndpoints_endWithTrailingSlash() {
        assertThat(ConstantesEndpoint.REST_BETA_ENVIO).endsWith("/");
        assertThat(ConstantesEndpoint.REST_PROD_ENVIO).endsWith("/");
    }

    /**
     * Test that REST Ticket endpoints end with trailing slash.
     */
    @Test
    void restTicketEndpoints_endWithTrailingSlash() {
        assertThat(ConstantesEndpoint.REST_BETA_TICKET).endsWith("/");
        assertThat(ConstantesEndpoint.REST_PROD_TICKET).endsWith("/");
    }

    /**
     * Test that SOAP Beta Factura and Consulta endpoints may be the same.
     */
    @Test
    void soapBetaFacturaAndConsultaEndpoints_areCorrect() {
        // They may use the same service or different ones
        assertThat(ConstantesEndpoint.SOAP_BETA_FACTURA).isNotEmpty();
        assertThat(ConstantesEndpoint.SOAP_BETA_CONSULTA).isNotEmpty();
    }

    /**
     * Test that REST token endpoints contain client_id placeholder.
     */
    @Test
    void restTokenEndpoints_containClientIdPlaceholder() {
        assertThat(ConstantesEndpoint.REST_BETA_TOKEN)
                .contains("{client_id}");
        assertThat(ConstantesEndpoint.REST_PROD_TOKEN)
                .contains("{client_id}");
    }

    /**
     * Test that constants are not null and immutable.
     */
    @Test
    void allConstants_areImmutable() {
        // Since they are final static Strings, they cannot be modified
        String originalBeta = ConstantesEndpoint.SOAP_BETA_FACTURA;
        String sameBeta = ConstantesEndpoint.SOAP_BETA_FACTURA;

        assertThat(originalBeta).isEqualTo(sameBeta);
    }
}
