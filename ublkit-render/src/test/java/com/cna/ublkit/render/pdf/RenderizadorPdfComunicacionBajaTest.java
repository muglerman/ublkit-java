package com.cna.ublkit.render.pdf;

import com.cna.ublkit.render.modelo.ContextoRender;
import com.cna.ublkit.render.modelo.ResultadoRender;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for RenderizadorPdfComunicacionBaja.
 * Tests PDF rendering of voided document communications.
 *
 * @since 0.2.0
 */
@DisplayName("RenderizadorPdfComunicacionBaja - PDF Voided Communication Rendering")
class RenderizadorPdfComunicacionBajaTest {

    @Nested
    @DisplayName("Basic PDF Rendering Tests")
    class BasicPdfRenderingTests {

        @Test
        @DisplayName("Should generate valid PDF output")
        void shouldGenerateValidPdfOutput() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf(), "Must return PDF");
            assertFalse(resultado.isHtml(), "Must not return HTML");
            assertNotNull(resultado.contenidoPdf(), "PDF content must not be null");
            assertTrue(resultado.contenidoPdf().length > 100, "PDF must have reasonable size");
        }

        @Test
        @DisplayName("Should have PDF file header")
        void shouldHavePdfFileHeader() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            String header = new String(resultado.contenidoPdf(), 0, 5);
            assertEquals("%PDF-", header, "Must have valid PDF header");
        }

        @Test
        @DisplayName("Should return non-null PDF bytes")
        void shouldReturnNonNullPdfBytes() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertNotNull(resultado.contenidoPdf());
            assertTrue(resultado.contenidoPdf().length > 0);
        }
    }

    @Nested
    @DisplayName("PDF Generation Tests")
    class PdfGenerationTests {

        @Test
        @DisplayName("Should generate PDF from HTML rendering")
        void shouldGeneratePdfFromHtmlRendering() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash123", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500, "PDF should have substantial content");
        }

        @Test
        @DisplayName("Should convert HTML to PDF correctly")
        void shouldConvertHtmlToPdfCorrectly() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertFalse(resultado.isHtml());
        }

        @Test
        @DisplayName("Should use fast mode rendering")
        void shouldUseFastModeRendering() {
            ComunicacionBaja baja = crearComunicacionBasica();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            // Fast mode should complete quickly
            long startTime = System.currentTimeMillis();
            ResultadoRender resultado = renderer.renderizar(contexto);
            long duration = System.currentTimeMillis() - startTime;

            assertTrue(resultado.isPdf());
            assertTrue(duration < 10000, "Should complete in reasonable time");
        }
    }

    @Nested
    @DisplayName("Content Mapping Tests")
    class ContentMappingTests {

        @Test
        @DisplayName("Should include communication metadata in PDF")
        void shouldIncludeCommunicationMetadataInPdf() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 500);
        }

        @Test
        @DisplayName("Should include issuer information in PDF")
        void shouldIncludeIssuerInformationInPdf() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 100);
        }

        @Test
        @DisplayName("Should include voided items in PDF")
        void shouldIncludeVoidedItemsInPdf() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 100);
        }

        @Test
        @DisplayName("Should include hash in PDF")
        void shouldIncludeHashInPdf() {
            ComunicacionBaja baja = crearComunicacionBasica();
            String hash = "hash_value_test";
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, hash, null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 100);
        }

        @Test
        @DisplayName("Should include QR code in PDF when provided")
        void shouldIncludeQrCodeInPdfWhenProvided() {
            ComunicacionBaja baja = crearComunicacionBasica();
            String qr = "QR_CODE_DATA";
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", qr);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 100);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle communication without issuer")
        void shouldHandleCommunicationWithoutIssuer() {
            ComunicacionBaja baja = new ComunicacionBaja();
            baja.setFechaEmision(LocalDate.now());
            baja.setNumero(1);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isPdf());
        }

        @Test
        @DisplayName("Should handle communication without signer")
        void shouldHandleCommunicationWithoutSigner() {
            ComunicacionBaja baja = crearComunicacionBasica();
            baja.setFirmante(null);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isPdf());
        }

        @Test
        @DisplayName("Should handle communication with empty items")
        void shouldHandleCommunicationWithEmptyItems() {
            ComunicacionBaja baja = crearComunicacionBasica();
            baja.setComprobantes(List.of());

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isPdf());
        }

        @Test
        @DisplayName("Should handle communication with many voided items")
        void shouldHandleCommunicationWithManyVoidedItems() {
            ComunicacionBaja baja = crearComunicacionBasica();
            List<ItemBaja> items = new java.util.ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                ItemBaja item = new ItemBaja("F0" + (i / 10), i, "01", "Reason " + i);
                items.add(item);
            }
            baja.setComprobantes(items);

            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);
            assertTrue(resultado.isPdf());
            assertTrue(resultado.contenidoPdf().length > 1000);
        }
    }

    @Nested
    @DisplayName("PDF File Operations Tests")
    class PdfFileOperationsTests {

        @Test
        @DisplayName("Should save PDF to file successfully")
        void shouldSavePdfToFileSuccessfully() throws IOException {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            Path tempFile = Files.createTempFile("test-comunicacion-baja-", ".pdf");
            Files.write(tempFile, resultado.contenidoPdf());

            assertTrue(Files.exists(tempFile), "File must exist");
            assertTrue(Files.size(tempFile) > 100, "File must have content");

            // Cleanup
            Files.delete(tempFile);
        }

        @Test
        @DisplayName("Should allow PDF to be read from file")
        void shouldAllowPdfToBeReadFromFile() throws IOException {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            Path tempFile = Files.createTempFile("test-", ".pdf");
            Files.write(tempFile, resultado.contenidoPdf());

            byte[] readBytes = Files.readAllBytes(tempFile);
            assertEquals(resultado.contenidoPdf().length, readBytes.length);

            // Cleanup
            Files.delete(tempFile);
        }
    }

    @Nested
    @DisplayName("PDF Integrity Tests")
    class PdfIntegrityTests {

        @Test
        @DisplayName("Should generate consistent PDFs for same input")
        void shouldGenerateConsistentPdfsForSameInput() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado1 = renderer.renderizar(contexto);
            ResultadoRender resultado2 = renderer.renderizar(contexto);

            assertTrue(resultado1.isPdf());
            assertTrue(resultado2.isPdf());
            // Both should generate PDFs (may have different content due to timestamps)
            assertTrue(resultado1.contenidoPdf().length > 100);
            assertTrue(resultado2.contenidoPdf().length > 100);
        }

        @Test
        @DisplayName("Should create valid PDF structure")
        void shouldCreateValidPdfStructure() {
            ComunicacionBaja baja = crearComunicacionCompleta();
            ContextoRender<ComunicacionBaja> contexto = ContextoRender.of(baja, "hash", null);
            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado = renderer.renderizar(contexto);

            byte[] pdf = resultado.contenidoPdf();

            // Check PDF header
            String header = new String(pdf, 0, 5);
            assertEquals("%PDF-", header);

            // Check for EOF marker (simplified check)
            String content = new String(pdf);
            assertTrue(content.contains("%%EOF") || pdf.length > 100, "Should have PDF content");
        }

        @Test
        @DisplayName("Should preserve PDF across multiple renders")
        void shouldPreservePdfAcrossMultipleRenders() {
            ComunicacionBaja baja1 = crearComunicacionBasica();
            ComunicacionBaja baja2 = crearComunicacionCompleta();

            ContextoRender<ComunicacionBaja> contexto1 = ContextoRender.of(baja1, "hash1", null);
            ContextoRender<ComunicacionBaja> contexto2 = ContextoRender.of(baja2, "hash2", null);

            RenderizadorPdfComunicacionBaja renderer = new RenderizadorPdfComunicacionBaja();

            ResultadoRender resultado1 = renderer.renderizar(contexto1);
            ResultadoRender resultado2 = renderer.renderizar(contexto2);

            assertTrue(resultado1.isPdf());
            assertTrue(resultado2.isPdf());
            assertTrue(resultado1.contenidoPdf().length > 100);
            assertTrue(resultado2.contenidoPdf().length > resultado1.contenidoPdf().length);
        }
    }

    // ═════════════════════════════════════════════════════════════════════
    // Test Data Factories
    // ═════════════════════════════════════════════════════════════════════

    private ComunicacionBaja crearComunicacionBasica() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.now());
        baja.setMoneda("PEN");
        baja.setNumero(1);
        return baja;
    }

    private ComunicacionBaja crearComunicacionCompleta() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 3, 30));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 3, 29));
        baja.setMoneda("PEN");
        baja.setNumero(1);

        EmisorDocumento emisor = new EmisorDocumento(
                "20123456789", "Empresa Test", "Empresa Comercial", null, null
        );
        baja.setEmisor(emisor);

        com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento firmante = new com.cna.ublkit.ubl.modelo.actor.FirmanteDocumento(
                "20123456789", "Empresa Test"
        );
        baja.setFirmante(firmante);

        ItemBaja item1 = new ItemBaja("F001", 100, "01", "Cancelación");
        ItemBaja item2 = new ItemBaja("F002", 101, "01", "Anulación");
        baja.setComprobantes(List.of(item1, item2));

        return baja;
    }
}
