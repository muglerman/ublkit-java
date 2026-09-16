package com.creanexusatreus.ublkit.render.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.support.GreTCarrierTestFixtures;
import com.creanexusatreus.ublkit.render.support.PdfVisualSnapshotSupport;

@DisplayName("RenderizadorPdfGuiaRemision - paginación GRE-T")
class RenderizadorPdfGuiaRemisionMultipageTest {

    @ParameterizedTest(name = "multipágina · estilo {0}")
    @EnumSource(EstiloPlantilla.class)
    void repiteSoloLaCabeceraCompactaYElEncabezadoDeTablaEnPaginasPosteriores(EstiloPlantilla estilo)
            throws IOException {
        var scenario = GreTCarrierTestFixtures.multipageScenario();
        byte[] pdf = render(estilo, scenario);
        List<String> pages = PdfVisualSnapshotSupport.extractTextByPage(pdf);

        assertTrue(pages.size() >= 2, "El estilo " + estilo.carpeta() + " debe generar más de una página");

        String firstPage = pages.get(0);
        String secondPage = pages.get(1);
        String normalizedFirstPage = normalize(firstPage);
        String normalizedSecondPage = normalize(secondPage);

        assertTrue(firstPage.contains("Guía de Remisión Transportista")
                || firstPage.contains("GUÍA DE REMISIÓN TRANSPORTISTA"),
                "La primera página debe conservar la cabecera completa en " + estilo.carpeta());
        assertFalse(normalizedFirstPage.contains("CONTINUACIONDEBIENESTRANSPORTADOS"),
                "La primera página no debe usar la cabecera compacta en " + estilo.carpeta());

        assertTrue(normalizedSecondPage.contains("CONTINUACIONDEBIENESTRANSPORTADOS"),
                "La segunda página debe usar la cabecera compacta en " + estilo.carpeta());
        assertTrue(normalizedSecondPage.contains("RUC20600456789"),
                "La segunda página debe repetir el RUC en " + estilo.carpeta());
        assertTrue(normalizedSecondPage.contains("V001-00009876"),
                "La segunda página debe repetir la serie-correlativo en " + estilo.carpeta());
        assertTrue(normalizedSecondPage.contains("DESCRIPCION"),
                "La segunda página debe repetir el encabezado de columnas en " + estilo.carpeta());

        assertFalse(normalizedSecondPage.contains("MANUFACTURASANDINATEXTILSAC"),
                "La segunda página no debe repetir el bloque del remitente en " + estilo.carpeta());
        assertFalse(normalizedSecondPage.contains("CENTRODEDISTRIBUCIONANDINOSAC"),
                "La segunda página no debe repetir el bloque del destinatario en " + estilo.carpeta());
        assertFalse(normalizedSecondPage.contains("SUBCONTRATACIONSUNAT"),
                "La segunda página no debe repetir el bloque SUNAT en " + estilo.carpeta());
        assertFalse(normalizedSecondPage.contains("PROVEEDORINTERNODERUTASAC"),
                "La segunda página no debe repetir el bloque interno en " + estilo.carpeta());
        assertFalse(normalizedSecondPage.contains("VEHICULOS"),
                "La segunda página no debe repetir el bloque de vehículos en " + estilo.carpeta());
        assertFalse(normalizedSecondPage.contains("CONDUCTORES"),
                "La segunda página no debe repetir el bloque de conductores en " + estilo.carpeta());
    }

    private byte[] render(EstiloPlantilla estilo, GreTCarrierTestFixtures.CarrierScenario scenario) {
        return new RenderizadorPdfGuiaRemision(FormatoImpresion.A4)
                .renderizar(ContextoRender.of(
                        scenario.guia(),
                        GreTCarrierTestFixtures.HASH,
                        GreTCarrierTestFixtures.QR_BASE64,
                        scenario.atributos(),
                        estilo))
                .contenidoPdf();
    }

    private String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .replaceAll("\\s+", "")
                .toUpperCase();
    }
}
