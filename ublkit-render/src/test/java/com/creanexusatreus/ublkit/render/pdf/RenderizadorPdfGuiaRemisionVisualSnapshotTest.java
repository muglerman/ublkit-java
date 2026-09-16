package com.creanexusatreus.ublkit.render.pdf;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.creanexusatreus.ublkit.render.modelo.ContextoRender;
import com.creanexusatreus.ublkit.render.modelo.EstiloPlantilla;
import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.creanexusatreus.ublkit.render.support.GreTCarrierTestFixtures;
import com.creanexusatreus.ublkit.render.support.PdfVisualSnapshotSupport;

@DisplayName("RenderizadorPdfGuiaRemision - snapshots visuales Classic Mono")
class RenderizadorPdfGuiaRemisionVisualSnapshotTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("classicMonoScenarios")
    void comparaLosSnapshotsClassicMonoDerivadosDelPdf(String snapshotName,
            GreTCarrierTestFixtures.CarrierScenario scenario) throws IOException {
        byte[] pdf = new RenderizadorPdfGuiaRemision(FormatoImpresion.A4)
                .renderizar(ContextoRender.of(
                        scenario.guia(),
                        GreTCarrierTestFixtures.HASH,
                        GreTCarrierTestFixtures.QR_BASE64,
                        scenario.atributos(),
                        EstiloPlantilla.CLASSIC_MONO))
                .contenidoPdf();

        PdfVisualSnapshotSupport.assertPdfMatchesSnapshot(
                pdf, "visual/gre-t/classic-mono/" + snapshotName + ".png");
    }

    static Stream<Arguments> classicMonoScenarios() {
        return GreTCarrierTestFixtures.classicMonoVisualScenarios().stream()
                .map(scenario -> Arguments.of(scenario.snapshotName(), scenario));
    }
}
