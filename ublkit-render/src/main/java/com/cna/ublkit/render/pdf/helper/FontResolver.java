package com.cna.ublkit.render.pdf.helper;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.extend.FSSupplier;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.InputStream;

public class FontResolver {
    public static void configurePdfA(PdfRendererBuilder builder) {
        builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_3_B);
        try {
            builder.useFont(new ResourceSupplier("fonts/Roboto-Regular.ttf"), "Roboto", 400, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.useFont(new ResourceSupplier("fonts/Roboto-Bold.ttf"), "Roboto", 700, com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL, true);

            // PDF/A requires a color profile
            ICC_Profile srgbProfile = ICC_Profile.getInstance(ColorSpace.CS_sRGB);
            builder.useColorProfile(srgbProfile.getData());
        } catch (Exception e) {
            throw new RuntimeException("Error configuring PDF/A fonts and color profile", e);
        }
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
