package com.cna.ublkit.render.pdf.helper;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.extend.FSSupplier;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FontResolver {
    private static final Logger LOGGER = Logger.getLogger(FontResolver.class.getName());

    public static void configurePdfA(PdfRendererBuilder builder) {
        try {
            builder.useFont(new ResourceSupplier("fonts/Roboto-Regular.ttf"), "Roboto", 400, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ResourceSupplier("fonts/Roboto-Bold.ttf"), "Roboto", 700, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);

            // PDF/A requires a valid ICC color profile. If it is missing/corrupt,
            // we fall back to standard PDF rendering to avoid hard failures.
            try (InputStream is = FontResolver.class.getClassLoader().getResourceAsStream("color/sRGB.icc")) {
                if (is != null) {
                    byte[] colorProfile = readAllBytes(is);
                    if (isValidIccProfile(colorProfile)) {
                        builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_B);
                        builder.useColorProfile(colorProfile);
                    } else {
                        LOGGER.warning("Invalid ICC profile at color/sRGB.icc; using non-PDF/A fallback");
                    }
                } else {
                    LOGGER.warning("Color profile not found in classpath: color/sRGB.icc; using non-PDF/A fallback");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error configuring PDF/A fonts/color profile; using non-PDF/A fallback", e);
        }
    }

    private static boolean isValidIccProfile(byte[] profile) {
        if (profile == null || profile.length < 40) {
            return false;
        }
        // ICC profiles contain the ASCII signature 'acsp' at bytes 36..39.
        return profile[36] == 'a' && profile[37] == 'c' && profile[38] == 's' && profile[39] == 'p';
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
