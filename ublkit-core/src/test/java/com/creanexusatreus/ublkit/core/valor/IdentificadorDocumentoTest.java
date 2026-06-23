package com.cna.ublkit.core.valor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("IdentificadorDocumento - Value Object Tests")
class IdentificadorDocumentoTest {

    @Test
    @DisplayName("crear_conSerieYNumeroValidos_exitoso")
    void crear_conSerieYNumeroValidos_exitoso() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("F001");
        Integer numero = 1;

        // Act
        IdentificadorDocumento identificador = new IdentificadorDocumento(serie, numero);

        // Assert
        assertThat(identificador.serie()).isEqualTo(serie);
        assertThat(identificador.numero()).isEqualTo(numero);
    }

    @Test
    @DisplayName("crear_conSerieNull_lanzaIllegalArgumentException")
    void crear_conSerieNull_lanzaIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> new IdentificadorDocumento(null, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("La serie es obligatoria");
    }

    @Test
    @DisplayName("crear_conNumeroNull_lanzaIllegalArgumentException")
    void crear_conNumeroNull_lanzaIllegalArgumentException() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("F001");

        // Act & Assert
        assertThatThrownBy(() -> new IdentificadorDocumento(serie, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El número debe ser mayor a cero");
    }

    @Test
    @DisplayName("crear_conNumeroCero_lanzaIllegalArgumentException")
    void crear_conNumeroCero_lanzaIllegalArgumentException() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("F001");

        // Act & Assert
        assertThatThrownBy(() -> new IdentificadorDocumento(serie, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El número debe ser mayor a cero");
    }

    @Test
    @DisplayName("crear_conNumeroNegativo_lanzaIllegalArgumentException")
    void crear_conNumeroNegativo_lanzaIllegalArgumentException() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("F001");

        // Act & Assert
        assertThatThrownBy(() -> new IdentificadorDocumento(serie, -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El número debe ser mayor a cero");
    }

    @ParameterizedTest
    @CsvSource({
        "F001, 1",
        "B001, 100",
        "FF001, 999999",
        "SERIE, 1000000"
    })
    @DisplayName("crear_conDiferentesValores_exitoso")
    void crear_conDiferentesValores_exitoso(String serieValor, Integer numero) {
        // Arrange
        NumeroSerie serie = new NumeroSerie(serieValor);

        // Act
        IdentificadorDocumento identificador = new IdentificadorDocumento(serie, numero);

        // Assert
        assertThat(identificador.serie().getValor()).isEqualTo(serieValor);
        assertThat(identificador.numero()).isEqualTo(numero);
    }

    @Test
    @DisplayName("serie_accesor_retornaValorCorrect")
    void serie_accesor_retornaValorCorrect() {
        // Arrange
        NumeroSerie serieEsperada = new NumeroSerie("F001");
        IdentificadorDocumento identificador = new IdentificadorDocumento(serieEsperada, 1);

        // Act
        NumeroSerie serieActual = identificador.serie();

        // Assert
        assertThat(serieActual).isEqualTo(serieEsperada);
    }

    @Test
    @DisplayName("numero_accesor_retornaValorCorrect")
    void numero_accesor_retornaValorCorrect() {
        // Arrange
        Integer numeroEsperado = 123;
        IdentificadorDocumento identificador = new IdentificadorDocumento(new NumeroSerie("F001"), numeroEsperado);

        // Act
        Integer numeroActual = identificador.numero();

        // Assert
        assertThat(numeroActual).isEqualTo(numeroEsperado);
    }

    @Test
    @DisplayName("valor_construyeStringConFormatoSerieHifonNumero")
    void valor_construyeStringConFormatoSerieHifonNumero() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("F001");
        Integer numero = 42;
        IdentificadorDocumento identificador = new IdentificadorDocumento(serie, numero);

        // Act
        String valor = identificador.valor();

        // Assert
        assertThat(valor).isEqualTo("F001-42");
    }

    @Test
    @DisplayName("valor_conNumeroGrande_formatoCorrect")
    void valor_conNumeroGrande_formatoCorrect() {
        // Arrange
        NumeroSerie serie = new NumeroSerie("SERIE");
        Integer numero = 1000000;
        IdentificadorDocumento identificador = new IdentificadorDocumento(serie, numero);

        // Act
        String valor = identificador.valor();

        // Assert
        assertThat(valor).isEqualTo("SERIE-1000000");
    }

    @Test
    @DisplayName("toString_retornaValor")
    void toString_retornaValor() {
        // Arrange
        IdentificadorDocumento identificador = new IdentificadorDocumento(new NumeroSerie("F001"), 42);

        // Act
        String resultado = identificador.toString();

        // Assert
        assertThat(resultado).isEqualTo("F001-42");
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        IdentificadorDocumento identificador1 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        IdentificadorDocumento identificador2 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);

        // Act & Assert
        assertThat(identificador1).isEqualTo(identificador2);
    }

    @Test
    @DisplayName("equals_diferenteNumero_retornaFalse")
    void equals_diferenteNumero_retornaFalse() {
        // Arrange
        IdentificadorDocumento identificador1 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        IdentificadorDocumento identificador2 = new IdentificadorDocumento(new NumeroSerie("F001"), 43);

        // Act & Assert
        assertThat(identificador1).isNotEqualTo(identificador2);
    }

    @Test
    @DisplayName("equals_diferenteSerie_retornaFalse")
    void equals_diferenteSerie_retornaFalse() {
        // Arrange
        IdentificadorDocumento identificador1 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        IdentificadorDocumento identificador2 = new IdentificadorDocumento(new NumeroSerie("F002"), 42);

        // Act & Assert
        assertThat(identificador1).isNotEqualTo(identificador2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        IdentificadorDocumento identificador = new IdentificadorDocumento(new NumeroSerie("F001"), 42);

        // Act & Assert
        assertThat(identificador).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        IdentificadorDocumento identificador = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        Object otro = "F001-42";

        // Act & Assert
        assertThat(identificador).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        IdentificadorDocumento identificador1 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        IdentificadorDocumento identificador2 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);

        // Act & Assert
        assertThat(identificador1.hashCode()).isEqualTo(identificador2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteValor_diferenteHash")
    void hashCode_diferenteValor_diferenteHash() {
        // Arrange
        IdentificadorDocumento identificador1 = new IdentificadorDocumento(new NumeroSerie("F001"), 42);
        IdentificadorDocumento identificador2 = new IdentificadorDocumento(new NumeroSerie("F001"), 43);

        // Act & Assert
        assertThat(identificador1.hashCode()).isNotEqualTo(identificador2.hashCode());
    }
}
