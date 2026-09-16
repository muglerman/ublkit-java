package com.creanexusatreus.ublkit.render.support;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

public final class PdfVisualSnapshotSupport {

    private static final String UPDATE_PROPERTY = "ublkit.visual.update";
    private static final float RENDER_DPI = 144f;
    private static final int PIXEL_CHANNEL_TOLERANCE = 6;
    private static final double MAX_DIFFERING_RATIO = 0.0012d;
    private static final Path MODULE_ROOT = locateModuleRoot();

    private PdfVisualSnapshotSupport() {
    }

    public static void assertPdfMatchesSnapshot(byte[] pdf, String relativeResourcePath) throws IOException {
        BufferedImage actual = renderPdfPreview(pdf);
        Path baselinePath = MODULE_ROOT.resolve("src/test/resources").resolve(relativeResourcePath);
        Path actualOutputPath = MODULE_ROOT.resolve("target/visual-regression")
                .resolve(relativeResourcePath.replace(".png", "-actual.png"));
        Path diffOutputPath = MODULE_ROOT.resolve("target/visual-regression")
                .resolve(relativeResourcePath.replace(".png", "-diff.png"));
        ensureParent(actualOutputPath);
        ImageIO.write(actual, "png", actualOutputPath.toFile());

        if (Boolean.getBoolean(UPDATE_PROPERTY)) {
            ensureParent(baselinePath);
            ImageIO.write(actual, "png", baselinePath.toFile());
            return;
        }

        if (!Files.exists(baselinePath)) {
            throw new AssertionError("Falta el baseline " + baselinePath
                    + " — regenérelo explícitamente con -D" + UPDATE_PROPERTY + "=true");
        }

        BufferedImage expected = ImageIO.read(baselinePath.toFile());
        ComparisonResult result = compare(expected, actual);
        if (!result.matches()) {
            ensureParent(diffOutputPath);
            ImageIO.write(result.diffImage(), "png", diffOutputPath.toFile());
            throw new AssertionError("Snapshot visual distinto para " + relativeResourcePath
                    + " — píxeles diferentes: " + result.differingPixels()
                    + " / " + result.totalPixels()
                    + ", ratio=" + String.format("%.6f", result.differingRatio())
                    + ", máximo delta RGBA=" + result.maxDelta()
                    + ". Revise: " + diffOutputPath);
        }
    }

    public static BufferedImage renderPdfPreview(byte[] pdf) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            PDFRenderer renderer = new PDFRenderer(document);
            List<BufferedImage> pages = new ArrayList<>();
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                pages.add(renderer.renderImageWithDPI(page, RENDER_DPI));
            }
            return stitchPages(pages);
        }
    }

    public static int pageCount(byte[] pdf) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            return document.getNumberOfPages();
        }
    }

    public static List<String> extractTextByPage(byte[] pdf) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            PDFTextStripper stripper = new PDFTextStripper();
            List<String> pages = new ArrayList<>();
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
                pages.add(stripper.getText(document));
            }
            return pages;
        }
    }

    private static BufferedImage stitchPages(List<BufferedImage> pages) {
        if (pages.isEmpty()) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }

        int gap = 18;
        int width = pages.stream().mapToInt(BufferedImage::getWidth).max().orElse(1);
        int height = pages.stream().mapToInt(BufferedImage::getHeight).sum() + (gap * (pages.size() - 1));
        BufferedImage stitched = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = stitched.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        int y = 0;
        for (int i = 0; i < pages.size(); i++) {
            BufferedImage page = pages.get(i);
            graphics.drawImage(page, 0, y, null);
            y += page.getHeight();
            if (i < pages.size() - 1) {
                graphics.setColor(new Color(0xD1, 0xD5, 0xDB));
                graphics.fillRect(0, y, width, gap);
                y += gap;
            }
        }
        graphics.dispose();
        return stitched;
    }

    private static ComparisonResult compare(BufferedImage expected, BufferedImage actual) {
        if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
            BufferedImage diff = new BufferedImage(
                    Math.max(expected.getWidth(), actual.getWidth()),
                    Math.max(expected.getHeight(), actual.getHeight()),
                    BufferedImage.TYPE_INT_ARGB);
            return new ComparisonResult(false, 0, 0, 1d, Integer.MAX_VALUE, diff);
        }

        int width = expected.getWidth();
        int height = expected.getHeight();
        BufferedImage diff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        long differingPixels = 0;
        int maxDelta = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int left = expected.getRGB(x, y);
                int right = actual.getRGB(x, y);
                int delta = maxChannelDelta(left, right);
                maxDelta = Math.max(maxDelta, delta);
                if (delta > PIXEL_CHANNEL_TOLERANCE) {
                    differingPixels++;
                    diff.setRGB(x, y, new Color(220, 38, 38, 255).getRGB());
                } else {
                    int grey = luminance(right);
                    diff.setRGB(x, y, new Color(grey, grey, grey, 80).getRGB());
                }
            }
        }

        long totalPixels = (long) width * height;
        double differingRatio = totalPixels == 0 ? 0d : (double) differingPixels / totalPixels;
        boolean matches = differingRatio <= MAX_DIFFERING_RATIO;
        return new ComparisonResult(matches, differingPixels, totalPixels, differingRatio, maxDelta, diff);
    }

    private static int maxChannelDelta(int left, int right) {
        return Math.max(
                Math.max(Math.abs(((left >> 24) & 0xFF) - ((right >> 24) & 0xFF)),
                        Math.abs(((left >> 16) & 0xFF) - ((right >> 16) & 0xFF))),
                Math.max(Math.abs(((left >> 8) & 0xFF) - ((right >> 8) & 0xFF)),
                        Math.abs((left & 0xFF) - (right & 0xFF))));
    }

    private static int luminance(int rgba) {
        int red = (rgba >> 16) & 0xFF;
        int green = (rgba >> 8) & 0xFF;
        int blue = rgba & 0xFF;
        return (red * 30 + green * 59 + blue * 11) / 100;
    }

    private static void ensureParent(Path path) throws IOException {
        Files.createDirectories(path.getParent());
    }

    private static Path locateModuleRoot() {
        Path current = Path.of("").toAbsolutePath();
        while (current != null) {
            if (Files.exists(current.resolve("src/main/resources/templates"))
                    && Files.exists(current.resolve("src/test/java"))) {
                return current;
            }
            current = current.getParent();
        }
        return Path.of("").toAbsolutePath();
    }

    private record ComparisonResult(boolean matches, long differingPixels, long totalPixels,
            double differingRatio, int maxDelta, BufferedImage diffImage) {
    }
}
