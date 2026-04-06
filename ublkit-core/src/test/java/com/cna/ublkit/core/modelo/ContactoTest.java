package com.cna.ublkit.core.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Contacto - Record Tests")
class ContactoTest {

    @Test
    @DisplayName("crear_conTodosLosParametros_exitoso")
    void crear_conTodosLosParametros_exitoso() {
        // Arrange
        String nombre = "Juan Perez";
        String telefono = "+51 987654321";
        String email = "juan@example.com";

        // Act
        Contacto contacto = new Contacto(nombre, telefono, email);

        // Assert
        assertThat(contacto).isNotNull();
        assertThat(contacto.nombre()).isEqualTo(nombre);
        assertThat(contacto.telefono()).isEqualTo(telefono);
        assertThat(contacto.email()).isEqualTo(email);
    }

    @Test
    @DisplayName("crear_conValoresNull_exitoso")
    void crear_conValoresNull_exitoso() {
        // Act
        Contacto contacto = new Contacto(null, null, null);

        // Assert
        assertThat(contacto.nombre()).isNull();
        assertThat(contacto.telefono()).isNull();
        assertThat(contacto.email()).isNull();
    }

    @Test
    @DisplayName("crear_conValoresVacios_exitoso")
    void crear_conValoresVacios_exitoso() {
        // Act
        Contacto contacto = new Contacto("", "", "");

        // Assert
        assertThat(contacto.nombre()).isEmpty();
        assertThat(contacto.telefono()).isEmpty();
        assertThat(contacto.email()).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
        "Juan Perez, +51 987654321, juan@example.com",
        "Maria Garcia, +51 912345678, maria@company.com",
        "Carlos Lopez, 0051987654321, carlos@domain.org",
        "Ana Martinez, 987654321, ana@test.net"
    })
    @DisplayName("crear_conDiferentesContactos_exitoso")
    void crear_conDiferentesContactos_exitoso(String nombre, String telefono, String email) {
        // Act
        Contacto contacto = new Contacto(nombre, telefono, email);

        // Assert
        assertThat(contacto.nombre()).isEqualTo(nombre);
        assertThat(contacto.telefono()).isEqualTo(telefono);
        assertThat(contacto.email()).isEqualTo(email);
    }

    @Test
    @DisplayName("nombre_accesor_retornaValorCorrect")
    void nombre_accesor_retornaValorCorrect() {
        // Arrange
        String nombreEsperado = "Juan Perez";
        Contacto contacto = new Contacto(nombreEsperado, "987654321", "juan@example.com");

        // Act
        String nombreActual = contacto.nombre();

        // Assert
        assertThat(nombreActual).isEqualTo(nombreEsperado);
    }

    @Test
    @DisplayName("telefono_accesor_retornaValorCorrect")
    void telefono_accesor_retornaValorCorrect() {
        // Arrange
        String telefonoEsperado = "+51987654321";
        Contacto contacto = new Contacto("Juan", telefonoEsperado, "juan@example.com");

        // Act
        String telefonoActual = contacto.telefono();

        // Assert
        assertThat(telefonoActual).isEqualTo(telefonoEsperado);
    }

    @Test
    @DisplayName("email_accesor_retornaValorCorrect")
    void email_accesor_retornaValorCorrect() {
        // Arrange
        String emailEsperado = "juan@example.com";
        Contacto contacto = new Contacto("Juan", "987654321", emailEsperado);

        // Act
        String emailActual = contacto.email();

        // Assert
        assertThat(emailActual).isEqualTo(emailEsperado);
    }

    @Test
    @DisplayName("equals_mismoValor_retornaTrue")
    void equals_mismoValor_retornaTrue() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Juan", "987654321", "juan@example.com");

        // Act & Assert
        assertThat(contacto1).isEqualTo(contacto2);
    }

    @Test
    @DisplayName("equals_diferenteNombre_retornaFalse")
    void equals_diferenteNombre_retornaFalse() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Maria", "987654321", "juan@example.com");

        // Act & Assert
        assertThat(contacto1).isNotEqualTo(contacto2);
    }

    @Test
    @DisplayName("equals_diferenteTelefono_retornaFalse")
    void equals_diferenteTelefono_retornaFalse() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Juan", "912345678", "juan@example.com");

        // Act & Assert
        assertThat(contacto1).isNotEqualTo(contacto2);
    }

    @Test
    @DisplayName("equals_diferenteEmail_retornaFalse")
    void equals_diferenteEmail_retornaFalse() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Juan", "987654321", "juan@other.com");

        // Act & Assert
        assertThat(contacto1).isNotEqualTo(contacto2);
    }

    @Test
    @DisplayName("equals_conNull_retornaFalse")
    void equals_conNull_retornaFalse() {
        // Arrange
        Contacto contacto = new Contacto("Juan", "987654321", "juan@example.com");

        // Act & Assert
        assertThat(contacto).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals_conOtraTipo_retornaFalse")
    void equals_conOtraTipo_retornaFalse() {
        // Arrange
        Contacto contacto = new Contacto("Juan", "987654321", "juan@example.com");
        Object otro = "Juan";

        // Act & Assert
        assertThat(contacto).isNotEqualTo(otro);
    }

    @Test
    @DisplayName("hashCode_mismoValor_mismoHash")
    void hashCode_mismoValor_mismoHash() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Juan", "987654321", "juan@example.com");

        // Act & Assert
        assertThat(contacto1.hashCode()).isEqualTo(contacto2.hashCode());
    }

    @Test
    @DisplayName("hashCode_diferenteValor_diferenteHash")
    void hashCode_diferenteValor_diferenteHash() {
        // Arrange
        Contacto contacto1 = new Contacto("Juan", "987654321", "juan@example.com");
        Contacto contacto2 = new Contacto("Maria", "987654321", "juan@example.com");

        // Act & Assert
        assertThat(contacto1.hashCode()).isNotEqualTo(contacto2.hashCode());
    }

    @Test
    @DisplayName("toString_representacionValida")
    void toString_representacionValida() {
        // Arrange
        Contacto contacto = new Contacto("Juan", "987654321", "juan@example.com");

        // Act
        String resultado = contacto.toString();

        // Assert
        assertThat(resultado)
            .contains("Contacto")
            .contains("Juan")
            .contains("987654321")
            .contains("juan@example.com");
    }

    @Test
    @DisplayName("crear_conSoloNombre_exitoso")
    void crear_conSoloNombre_exitoso() {
        // Act
        Contacto contacto = new Contacto("Juan", null, null);

        // Assert
        assertThat(contacto.nombre()).isEqualTo("Juan");
        assertThat(contacto.telefono()).isNull();
        assertThat(contacto.email()).isNull();
    }

    @Test
    @DisplayName("crear_conSoloTelefono_exitoso")
    void crear_conSoloTelefono_exitoso() {
        // Act
        Contacto contacto = new Contacto(null, "987654321", null);

        // Assert
        assertThat(contacto.nombre()).isNull();
        assertThat(contacto.telefono()).isEqualTo("987654321");
        assertThat(contacto.email()).isNull();
    }

    @Test
    @DisplayName("crear_conSoloEmail_exitoso")
    void crear_conSoloEmail_exitoso() {
        // Act
        Contacto contacto = new Contacto(null, null, "juan@example.com");

        // Assert
        assertThat(contacto.nombre()).isNull();
        assertThat(contacto.telefono()).isNull();
        assertThat(contacto.email()).isEqualTo("juan@example.com");
    }
}
