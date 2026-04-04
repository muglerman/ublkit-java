package com.cna.ublkit.gateway.autenticacion;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.cna.ublkit.core.enumerado.TipoAmbiente;

class ProveedorTokenNativoTest {

    @Test
    void normalizarCredencialesParaAmbiente_debeForzarReglasBetaGre() throws Exception {
        ProveedorTokenNativo proveedor = new ProveedorTokenNativo();
        CredencialesEmpresa original = new CredencialesEmpresa("20606860618", "TN3KP3AC", "123456",
                "5ff266e4-eba0-48ae-b18c-1ae78d2c3a85", "88DuwSmRtokQyKyujkidKQ==");

        CredencialesEmpresa normalizada = invocarNormalizacion(proveedor, original, TipoAmbiente.BETA);

        assertThat(normalizada.ruc()).isEqualTo("20606860618");
        assertThat(normalizada.usuarioSol()).isEqualTo("MODDATOS");
        assertThat(normalizada.claveSol()).isEqualTo("MODDATOS");
        assertThat(normalizada.clientId()).startsWith("test-");
        assertThat(normalizada.clientSecret()).startsWith("test-");
    }

    @Test
    void normalizarCredencialesParaAmbiente_noDebeCambiarProduccion() throws Exception {
        ProveedorTokenNativo proveedor = new ProveedorTokenNativo();
        CredencialesEmpresa original = new CredencialesEmpresa("20606860618", "USUARIO_SOL", "CLAVE_SOL", "client-id",
                "client-secret");

        CredencialesEmpresa normalizada = invocarNormalizacion(proveedor, original, TipoAmbiente.PRODUCCION);

        assertThat(normalizada).isEqualTo(original);
    }

    private CredencialesEmpresa invocarNormalizacion(ProveedorTokenNativo proveedor, CredencialesEmpresa credenciales,
            TipoAmbiente ambiente) throws Exception {
        Method metodo = ProveedorTokenNativo.class.getDeclaredMethod("normalizarCredencialesParaAmbiente",
                CredencialesEmpresa.class, TipoAmbiente.class);
        metodo.setAccessible(true);
        return (CredencialesEmpresa) metodo.invoke(proveedor, credenciales, ambiente);
    }
}
