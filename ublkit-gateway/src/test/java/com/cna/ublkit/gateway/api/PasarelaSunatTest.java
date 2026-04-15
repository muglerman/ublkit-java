package com.cna.ublkit.gateway.api;

import com.cna.ublkit.core.enumerado.TipoAmbiente;
import com.cna.ublkit.gateway.autenticacion.CredencialesEmpresa;
import com.cna.ublkit.gateway.respuesta.ArchivoCdr;
import com.cna.ublkit.gateway.respuesta.ResultadoConsulta;
import com.cna.ublkit.gateway.respuesta.ResultadoEnvio;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite for {@link PasarelaSunat} interface.
 * Validates the main gateway contract definition.
 */
class PasarelaSunatTest {

    /**
     * Test that the interface defines enviarComprobante method.
     */
    @Test
    void interfaceDefinesEnviarComprobanteMethod() {
        // Verify method exists via reflection
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarComprobante") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines enviarRetencionPercepcion method.
     */
    @Test
    void interfaceDefinesEnviarRetencionPercepcionMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarRetencionPercepcion") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines enviarResumen method.
     */
    @Test
    void interfaceDefinesEnviarResumenMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarResumen") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines enviarGuiaRemision method.
     */
    @Test
    void interfaceDefinesEnviarGuiaRemisionMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarGuiaRemision") && m.getParameterCount() == 4);
    }

    /**
     * Test that the interface defines consultarTicketSoap method.
     */
    @Test
    void interfaceDefinesConsultarTicketSoapMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicketSoap") && m.getParameterCount() == 3);
    }

    /**
     * Test that the interface defines consultarTicketRest method.
     */
    @Test
    void interfaceDefinesConsultarTicketRestMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("consultarTicketRest") && m.getParameterCount() == 3);
    }

    @Test
    void interfaceDefinesEnviarGuiaRemisionYEsperarMethod() {
        assertThat(PasarelaSunat.class.getDeclaredMethods())
                .anyMatch(m -> m.getName().equals("enviarGuiaRemisionYEsperar") && m.getParameterCount() == 4);
    }

    /**
     * Test that all methods return expected types.
     */
    @Test
    void interfaceMethodsReturnExpectedTypes() {
        var methods = PasarelaSunat.class.getDeclaredMethods();

        // enviarComprobante returns ResultadoEnvio
        assertThat(methods)
                .anyMatch(m -> m.getName().equals("enviarComprobante") &&
                        m.getReturnType().equals(ResultadoEnvio.class));

        // consultarTicketSoap returns ResultadoConsulta
        assertThat(methods)
                .anyMatch(m -> m.getName().equals("consultarTicketSoap") &&
                        m.getReturnType().equals(ResultadoConsulta.class));
    }

    /**
     * Test that interface is functional (can be implemented).
     */
    @Test
    void interfaceCanBeImplemented() {
        // Create a simple test implementation
        PasarelaSunat testImpl = new PasarelaSunat() {
            @Override
            public ResultadoEnvio enviarComprobante(String xmlFirmado, String nombreArchivo,
                    CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                return ResultadoEnvio.asincrono("123");
            }

            @Override
            public ResultadoEnvio enviarRetencionPercepcion(String xmlFirmado, String nombreArchivo,
                    CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                return ResultadoEnvio.asincrono("124");
            }

            @Override
            public ResultadoEnvio enviarResumen(String xmlFirmado, String nombreArchivo,
                    CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                return ResultadoEnvio.asincrono("125");
            }

            @Override
            public ResultadoEnvio enviarGuiaRemision(String xmlFirmado, String nombreArchivo,
                    CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                return ResultadoEnvio.asincrono("126");
            }

            @Override
            public ResultadoConsulta consultarTicketSoap(String ticket, CredencialesEmpresa credenciales,
                    TipoAmbiente ambiente) {
                return ResultadoConsulta.pendiente();
            }

            @Override
            public ResultadoConsulta consultarTicketRest(String ticket, CredencialesEmpresa credenciales,
                    TipoAmbiente ambiente) {
                return ResultadoConsulta.pendiente();
            }

            @Override
            public CompletableFuture<ArchivoCdr> enviarGuiaRemisionYEsperar(String xmlFirmado, String nombreArchivo,
                    CredencialesEmpresa credenciales, TipoAmbiente ambiente) {
                return CompletableFuture.completedFuture(new ArchivoCdr(new byte[] {}, "0", "OK", java.util.List.of()));
            }
        };

        assertThat(testImpl).isNotNull();
        assertThat(testImpl).isInstanceOf(PasarelaSunat.class);
    }
}
