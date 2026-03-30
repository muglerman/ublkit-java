package com.cna.ublkit.testkit.assertion;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Comparador simple XML contra golden files.
 *
 * @since 0.1.0
 */
public final class GoldenXml {

    private GoldenXml() {
    }

    public static void assertEqualsGolden(String xmlActual, Path goldenPath) {
        try {
            String esperado = Files.readString(goldenPath);
            Assertions.assertEquals(normalizar(esperado), normalizar(xmlActual),
                    "El XML no coincide con golden file: " + goldenPath);
        } catch (IOException e) {
            Assertions.fail("No se pudo leer golden file " + goldenPath + ": " + e.getMessage());
        }
    }

    private static String normalizar(String xml) {
        return xml.replaceAll(">\\s+<", "><")
                .replaceAll("\\s+", " ")
                .trim();
    }
}

