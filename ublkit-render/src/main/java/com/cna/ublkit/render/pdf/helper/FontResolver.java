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
    private static final byte[] CACHED_ICC_PROFILE = loadIccProfile();
    private static final boolean VALID_ICC_PROFILE = CACHED_ICC_PROFILE != null && isValidIccProfile(CACHED_ICC_PROFILE);

    private static byte[] loadIccProfile() {
        try (InputStream is = FontResolver.class.getClassLoader().getResourceAsStream("color/sRGB.icc")) {
            if (is != null) {
                return readAllBytes(is);
            } else {
                LOGGER.warning("Color profile not found in classpath: color/sRGB.icc; using non-PDF/A fallback");
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error loading ICC profile; using non-PDF/A fallback", e);
            return null;
        }
    }

    public static void configurePdfA(PdfRendererBuilder builder) {
        try {
            builder.useFont(new ResourceSupplier("fonts/Roboto-Regular.ttf"), "Roboto", 400, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ResourceSupplier("fonts/Roboto-Bold.ttf"), "Roboto", 700, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);

            // PDF/A requires a valid ICC color profile. Use cached profile if available.
            if (VALID_ICC_PROFILE) {
                builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_B);
                builder.useColorProfile(CACHED_ICC_PROFILE);
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
