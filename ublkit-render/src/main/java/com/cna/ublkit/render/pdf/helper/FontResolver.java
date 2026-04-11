package com.cna.ublkit.render.pdf.helper;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.extend.FSSupplier;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FontResolver {
    public static void configurePdfA(PdfRendererBuilder builder) {
        builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_B);
        try {
            builder.useFont(new ResourceSupplier("fonts/Roboto-Regular.ttf"), "Roboto", 400, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ResourceSupplier("fonts/Roboto-Bold.ttf"), "Roboto", 700, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);

            // PDF/A requires a color profile
            try (InputStream is = FontResolver.class.getClassLoader().getResourceAsStream("color/sRGB.icc")) {
                if (is != null) {
                    byte[] colorProfile = readAllBytes(is);
                    builder.useColorProfile(colorProfile);
                } else {
                    throw new RuntimeException("Color profile not found in classpath: color/sRGB.icc");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error configuring PDF/A fonts and color profile", e);
        }
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private static class ResourceSupplier implements FSSupplier<InputStream> {
        private final String resource;

        public ResourceSupplier(String resource) {
            this.resource = resource;
        }

        @Override
        public InputStream supply() {
            InputStream is = FontResolver.class.getClassLoader().getResourceAsStream(resource);
            if (is == null) {
                throw new RuntimeException("Resource not found in classpath: " + resource);
            }
            return is;
        }
    }
}
