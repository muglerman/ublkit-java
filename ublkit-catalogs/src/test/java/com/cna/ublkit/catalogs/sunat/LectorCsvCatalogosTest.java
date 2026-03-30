package com.cna.ublkit.catalogs.sunat;

import com.cna.ublkit.catalogs.api.ProveedorCatalogos;
import com.cna.ublkit.catalogs.modelo.EntradaCatalogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LectorCsvCatalogosTest {

    private ProveedorCatalogos proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new LectorCsvCatalogos();
    }

    @Test
    void testObtenerCatalogo_Catalogo01() {
        // Act
        List<EntradaCatalogo> catalogo = proveedor.obtenerCatalogo("01");

        // Assert
        assertThat(catalogo).isNotEmpty();
        
        Optional<EntradaCatalogo> factura = catalogo.stream()
                .filter(e -> "01".equals(e.getCodigo()))
                .findFirst();
                
        assertThat(factura).isPresent();
        assertThat(factura.get().getDescripcion()).isEqualTo("Factura");
    }

    @Test
    void testBuscar_CatalogoExistente_EntradaExistente() {
        // Act
        Optional<EntradaCatalogo> boleta = proveedor.buscar("01", "03");

        // Assert
        assertThat(boleta).isPresent();
        assertThat(boleta.get().getDescripcion()).isEqualTo("Boleta de venta");
    }

    @Test
    void testObtenerCatalogo_AtributosAdicionales_Catalogo05() {
        // Act
        Optional<EntradaCatalogo> igv = proveedor.buscar("05", "1000");

        // Assert
        assertThat(igv).isPresent();
        assertThat(igv.get().getAtributoAdicional("codigo_internacional")).hasValue("VAT");
        assertThat(igv.get().getAtributoAdicional("nombre")).hasValue("IGV");
        assertThat(igv.get().getAtributoAdicional("atributo_inexistente")).isEmpty();
    }

    @Test
    void testObtenerCatalogo_NoExistente() {
        // Act
        List<EntradaCatalogo> vacio = proveedor.obtenerCatalogo("00_inexistente");

        // Assert
        assertThat(vacio).isEmpty();
    }

    @Test
    void testBuscar_EntradaInexistente() {
        // Act
        Optional<EntradaCatalogo> vacio = proveedor.buscar("01", "codigo_falso");

        // Assert
        assertThat(vacio).isEmpty();
    }
}
