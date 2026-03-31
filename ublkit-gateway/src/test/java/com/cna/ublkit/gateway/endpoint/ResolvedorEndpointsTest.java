package com.cna.ublkit.gateway.endpoint;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.core.error.ExcepcionUblKit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResolvedorEndpointsTest {

    @Test
    void testUrlSoapFactura() {
        assertThat(ResolvedorEndpoints.urlSoapFactura(TipoAmbiente.BETA))
                .isEqualTo(ConstantesEndpoint.SOAP_BETA_FACTURA);
        
        assertThat(ResolvedorEndpoints.urlSoapFactura(TipoAmbiente.PRODUCCION))
                .isEqualTo(ConstantesEndpoint.SOAP_PROD_FACTURA);
    }

    @Test
    void testUrlRestToken() {
        assertThat(ResolvedorEndpoints.urlRestToken(TipoAmbiente.PRODUCCION, "mi_client_id"))
                .isEqualTo("https://api-seguridad.sunat.gob.pe/v1/clientessol/mi_client_id/oauth2/token");
    }

    @Test
    void testUrlRestTokenBeta() {
        assertThat(ResolvedorEndpoints.urlRestToken(TipoAmbiente.BETA, "mi_client_id"))
                .isEqualTo("https://gre-test.nubefact.com/v1/clientessol/mi_client_id/oauth2/token");
    }

    @Test
    void testUrlRestTokenFallaSinClientId() {
        assertThatThrownBy(() -> ResolvedorEndpoints.urlRestToken(TipoAmbiente.PRODUCCION, ""))
                .isInstanceOf(ExcepcionUblKit.class)
                .hasMessageContaining("Client ID es requerido");
    }
}
