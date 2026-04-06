package com.cna.ublkit.core.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Direccion - Record Tests")
class DireccionTest {

    @Test
    @DisplayName("crear_conTodosLosParametros_exitoso")
    void crear_conTodosLosParametros_exitoso() {
        // Arrange
        String ubigeo = "050101";
        String codigoLocal = "0000";
        String urbanizacion = "Los Pinos";
        String departamento = "Ayacucho";
        String provincia = "Huamanga";
        String distrito = "Ayacucho";
        String direccion = "Jirón las piedras 123";
        String codigoPais = "PE";

        // Act
        Direccion dir = new Direccion(ubigeo, codigoLocal, urbanizacion, departamento, provincia, distrito, direccion, codigoPais);

        // Assert
        assertThat(dir.ubigeo()).isEqualTo(ubigeo);
        assertThat(dir.codigoLocal()).isEqualTo(codigoLocal);
        assertThat(dir.urbanizacion()).isEqualTo(urbanizacion);
        assertThat(dir.departamento()).isEqualTo(departamento);
        assertThat(dir.provincia()).isEqualTo(provincia);
        assertThat(dir.distrito()).isEqualTo(distrito);
        assertThat(dir.direccion()).isEqualTo(direccion);
        assertThat(dir.codigoPais()).isEqualTo(codigoPais);
    }

    @Test
    @DisplayName("crear_conValoresNull_exitoso")
    void crear_conValoresNull_exitoso() {
        // Act
        Direccion dir = new Direccion(null, null, null, null, null, null, null, null);

        // Assert
        assertThat(dir.ubigeo()).isNull();
        assertThat(dir.codigoLocal()).isNull();
        assertThat(dir.urbanizacion()).isNull();
        assertThat(dir.departamento()).isNull();
        assertThat(dir.provincia()).isNull();
        assertThat(dir.distrito()).isNull();
        assertThat(dir.direccion()).isNull();
        assertThat(dir.codigoPais()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
        "050101, 0000, Los Pinos, Ayacucho, Huamanga, Ayacucho, Jirón las piedras 123, PE",
        "080131, 0100, San Isidro, Lima, Lima, San Isidro, Avenida Paseo de la República 3200, PE",
        "040131, 0001, Miraflores, Arequipa, Arequipa, Arequipa, Calle Mercaderes 523, PE"
    })
    @DisplayName("crear_conDiferentesDirecciones_exitoso")
    void crear_conDiferentesDirecciones_exitoso(String ubigeo, String codigoLocal, String urbanizacion,
                                                 String departamento, String provincia, String distrito,
                                                 String direccion, String codigoPais) {
        // Act
        Direccion dir = new Direccion(ubigeo, codigoLocal, urbanizacion, departamento, provincia, distrito, direccion, codigoPais);

        // Assert
        assertThat(dir.ubigeo()).isEqualTo(ubigeo);
        assertThat(dir.codigoLocal()).isEqualTo(codigoLocal);
        assertThat(dir.urbanizacion()).isEqualTo(urbanizacion);
        assertThat(dir.departamento()).isEqualTo(departamento);
        assertThat(dir.provincia()).isEqualTo(provincia);
        assertThat(dir.distrito()).isEqualTo(distrito);
        assertThat(dir.direccion()).isEqualTo(direccion);
        assertThat(dir.codigoPais()).isEqualTo(codigoPais);
    }

    @Test
    @DisplayName("ubigeo_accesor_retornaValorCorrect")
    void ubigeo_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String ubigeo = dir.ubigeo();

        // Assert
        assertThat(ubigeo).isEqualTo("050101");
    }

    @Test
    @DisplayName("codigoLocal_accesor_retornaValorCorrect")
    void codigoLocal_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0100", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String codigoLocal = dir.codigoLocal();

        // Assert
        assertThat(codigoLocal).isEqualTo("0100");
    }

    @Test
    @DisplayName("urbanizacion_accesor_retornaValorCorrect")
    void urbanizacion_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "San Isidro", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String urbanizacion = dir.urbanizacion();

        // Assert
        assertThat(urbanizacion).isEqualTo("San Isidro");
    }

    @Test
    @DisplayName("departamento_accesor_retornaValorCorrect")
    void departamento_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Lima", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String departamento = dir.departamento();

        // Assert
        assertThat(departamento).isEqualTo("Lima");
    }

    @Test
    @DisplayName("provincia_accesor_retornaValorCorrect")
    void provincia_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Wamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String provincia = dir.provincia();

        // Assert
        assertThat(provincia).isEqualTo("Wamanga");
    }

    @Test
    @DisplayName("distrito_accesor_retornaValorCorrect")
    void distrito_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "San Juan Bautista", "Jirón las piedras 123", "PE");

        // Act
        String distrito = dir.distrito();

        // Assert
        assertThat(distrito).isEqualTo("San Juan Bautista");
    }

    @Test
    @DisplayName("direccion_accesor_retornaValorCorrect")
    void direccion_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Calle Principal 456", "PE");

        // Act
        String direccion = dir.direccion();

        // Assert
        assertThat(direccion).isEqualTo("Calle Principal 456");
    }

    @Test
    @DisplayName("codigoPais_accesor_retornaValorCorrect")
    void codigoPais_accesor_retornaValorCorrect() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "AR");

        // Act
        String codigoPais = dir.codigoPais();

        // Assert
        assertThat(codigoPais).isEqualTo("AR");
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        Direccion dir1 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");
        Direccion dir2 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act & Assert
        assertThat(dir1).isEqualTo(dir2);
    }

    @Test
    @DisplayName("equals_diferenteUbigeo_retornaFalse")
    void equals_diferenteUbigeo_retornaFalse() {
        // Arrange
        Direccion dir1 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");
        Direccion dir2 = new Direccion("080131", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act & Assert
        assertThat(dir1).isNotEqualTo(dir2);
    }

    @Test
    @DisplayName("equals_diferenteDireccion_retornaFalse")
    void equals_diferenteDireccion_retornaFalse() {
        // Arrange
        Direccion dir1 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");
        Direccion dir2 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Calle Otra 456", "PE");

        // Act & Assert
        assertThat(dir1).isNotEqualTo(dir2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act & Assert
        assertThat(dir).isNotEqualTo(null);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        Direccion dir1 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");
        Direccion dir2 = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act & Assert
        assertThat(dir1.hashCode()).isEqualTo(dir2.hashCode());
    }

    @Test
    @DisplayName("toString_representacionValida")
    void toString_representacionValida() {
        // Arrange
        Direccion dir = new Direccion("050101", "0000", "Los Pinos", "Ayacucho", "Huamanga", "Ayacucho", "Jirón las piedras 123", "PE");

        // Act
        String resultado = dir.toString();

        // Assert
        assertThat(resultado)
            .contains("Direccion")
            .contains("050101")
            .contains("Ayacucho")
            .contains("PE");
    }
}
