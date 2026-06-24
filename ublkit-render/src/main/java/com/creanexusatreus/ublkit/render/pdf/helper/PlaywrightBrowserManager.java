package com.creanexusatreus.ublkit.render.pdf.helper;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

import com.creanexusatreus.ublkit.render.modelo.FormatoImpresion;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;

public class PlaywrightBrowserManager {

    /** Alto útil de una página A5 apaisada a 96 dpi: 148 mm = 559 px (márgenes en 0). */
    private static final double ALTO_A5_LANDSCAPE_PX = 559.0;
    /** Escala mínima razonable: por debajo el documento sería ilegible (se deja paginar). */
    private static final double ESCALA_MINIMA = 0.45;
    /**
     * Techo de tiempo (ms) para cargar el HTML y generar el PDF. Acota las operaciones de Playwright
     * para que un render problemático <b>falle rápido</b> en vez de colgar el hilo indefinidamente
     * (causa raíz del congelamiento en contenedor).
     */
    private static final double RENDER_TIMEOUT_MS = 20_000;
    /** Techo (ms) para el handshake CDP del launch ({@link BrowserType.LaunchOptions#setTimeout}). */
    private static final double LAUNCH_TIMEOUT_MS = 15_000;
    /**
     * Presupuesto total de pared (ms) para TODO el render: {@code create()} + launch + carga + pdf.
     * Cubre lo que {@code setTimeout} no cubre (extracción del driver, spawn del proceso node, llamadas
     * nativas atascadas por presión de memoria). Debe ser mayor que {@code LAUNCH_TIMEOUT_MS +
     * RENDER_TIMEOUT_MS} (para que los timeouts blandos disparen primero cuando es posible) y menor que
     * el {@code PDF_LOCK_TIMEOUT_SECONDS=90s} de {@code PdfServiceImpl} (para que un cuelgue falle
     * rápido y libere el lock antes de que los demás callers caigan en PDF_BUSY).
     */
    private static final long TOTAL_RENDER_BUDGET_MS = 45_000;

    private static volatile Playwright playwright;
    private static volatile Browser browser;
    /**
     * Guarda <b>solo</b> la lectura/escritura de los campos {@link #playwright}/{@link #browser}/
     * {@link #renderExecutor} (secciones cortas). El launch del navegador (bloqueante) corre
     * <b>fuera</b> de este lock para que {@link #resetBrowser()} pueda ejecutarse siempre desde el hilo
     * watchdog sin bloquearse — a diferencia del antiguo {@code synchronized} sobre el monitor de clase,
     * que un launch colgado retenía indefinidamente.
     */
    private static final ReentrantLock browserLock = new ReentrantLock();
    /**
     * Serializa los renders en un único hilo (el {@link Browser} compartido no es thread-safe). Se
     * <b>descarta y recrea</b> tras un timeout de pared, porque el hilo atascado en una llamada nativa
     * no responde a la interrupción y no puede reutilizarse.
     */
    private static volatile ExecutorService renderExecutor;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(PlaywrightBrowserManager::resetBrowser));
    }

    private PlaywrightBrowserManager() {
    }

    /**
     * Devuelve el navegador compartido, construyéndolo de forma perezosa. El costoso
     * {@code Playwright.create()} + {@code launch()} corre <b>sin lock</b> (patrón build-outside +
     * publish con double-check de carrera), de modo que un launch colgado no retiene ningún monitor y
     * {@link #resetBrowser()} puede recuperar el estado desde el watchdog.
     */
    public static Browser getBrowser() {
        Browser current = browser;
        if (current != null) {
            return current;
        }
        // Construcción bloqueante fuera de cualquier lock.
        Playwright nuevoPlaywright = Playwright.create();
        Browser nuevoBrowser = nuevoPlaywright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(true)
            .setTimeout(LAUNCH_TIMEOUT_MS)
            // Flags imprescindibles en contenedor: sin sandbox (Chromium corre como usuario no-root;
            // --no-sandbox + --disable-setuid-sandbox) y sin /dev/shm (default 64 MB) para que no se
            // cuelgue/caiga por memoria compartida.
            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox", "--disable-dev-shm-usage", "--disable-gpu")));

        browserLock.lock();
        try {
            if (browser != null) {
                // Perdimos la carrera: descartamos el que acabamos de construir.
                closeQuietly(nuevoBrowser, nuevoPlaywright);
                return browser;
            }
            playwright = nuevoPlaywright;
            browser = nuevoBrowser;
            return browser;
        } finally {
            browserLock.unlock();
        }
    }

    /**
     * Renderiza el HTML a PDF acotado por un presupuesto de pared duro: corre {@link #renderInterno}
     * en un hilo dedicado y aborta si excede {@link #TOTAL_RENDER_BUDGET_MS}.
     *
     * <p>Punto de entrada único para todos los renderizadores. Ante timeout o cualquier fallo, descarta
     * el navegador estático para que la próxima llamada relance uno limpio (evita el "tracing object
     * doesn't exist" permanente) y propaga la excepción, de modo que {@code PdfServiceImpl} libere su
     * {@code pdfLock} en seguida en vez de quedar colgado 90 s y envenenar todos los PDF.</p>
     */
    public static byte[] render(String html, FormatoImpresion formato) {
        Future<byte[]> future = executor().submit(() -> renderInterno(html, formato));
        try {
            return future.get(TOTAL_RENDER_BUDGET_MS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);   // interrupción best-effort (una llamada nativa no responde)
            discardExecutor();     // el hilo atascado no es reutilizable: se descarta y recrea
            resetBrowser();        // la próxima llamada relanza un navegador limpio
            throw new PlaywrightException("Render PDF excedió el presupuesto de "
                + TOTAL_RENDER_BUDGET_MS + " ms (posible cuelgue del navegador); navegador reiniciado", e);
        } catch (ExecutionException e) {
            resetBrowser();        // cualquier fallo ⇒ relanzar limpio
            Throwable causa = e.getCause();
            if (causa instanceof RuntimeException re) {
                throw re;
            }
            throw new PlaywrightException("Error renderizando PDF: "
                + (causa != null ? causa.getMessage() : e.getMessage()), causa);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
            throw new PlaywrightException("Render PDF interrumpido", e);
        }
    }

    /** Trabajo de render efectivo: abre una página efímera del navegador compartido y genera el PDF. */
    private static byte[] renderInterno(String html, FormatoImpresion formato) {
        try (Page page = getBrowser().newPage()) {
            return renderizarPdf(page, html, formato);
        } catch (PlaywrightException e) {
            resetBrowser();
            throw e;
        }
    }

    /**
     * Carga el HTML en la página y genera el PDF aplicando la política de tamaño por formato.
     *
     * <p>Para A5 (apaisado, "una A4 partida a la mitad y girada", 210×148 mm) ajusta el documento a
     * <b>una sola página</b>: mide el alto real del contenido y, si excede los 148 mm de la hoja,
     * reduce la escala de impresión (fit-to-page) para que entre completo sin partirse. Los
     * documentos cortos (que ya caben) se imprimen a escala 1. Para A4 se respeta la paginación
     * natural (un comprobante largo puede ocupar varias páginas).</p>
     */
    public static byte[] renderizarPdf(Page page, String html, FormatoImpresion formato) {
        page.setDefaultTimeout(RENDER_TIMEOUT_MS);
        // LOAD (no NETWORKIDLE): todos los recursos (fuentes, logo) van inlineados en base64, así que
        // basta con LOAD y se elimina la espera indefinida de NETWORKIDLE.
        page.setContent(html, new Page.SetContentOptions()
            .setWaitUntil(WaitUntilState.LOAD)
            .setTimeout(RENDER_TIMEOUT_MS));
        Page.PdfOptions options = getPdfOptions(formato);
        if (formato == FormatoImpresion.A5) {
            options.setScale(escalaAjusteA5(page));
        }
        return page.pdf(options);
    }

    /** Cierra y descarta el navegador/Playwright estáticos; la próxima llamada relanza uno limpio. */
    private static void resetBrowser() {
        Browser b;
        Playwright pw;
        browserLock.lock();
        try {
            b = browser;
            pw = playwright;
            browser = null;
            playwright = null;
        } finally {
            browserLock.unlock();
        }
        // Cierra fuera del lock: un close() lento no debe bloquear a otros hilos.
        closeQuietly(b, pw);
    }

    private static void closeQuietly(Browser b, Playwright pw) {
        if (b != null) {
            try { b.close(); } catch (Exception ignored) { /* idempotente */ }
        }
        if (pw != null) {
            try { pw.close(); } catch (Exception ignored) { /* idempotente */ }
        }
    }

    /** Executor perezoso de un solo hilo (daemon) que serializa el acceso al navegador no thread-safe. */
    private static ExecutorService executor() {
        ExecutorService ex = renderExecutor;
        if (ex != null) {
            return ex;
        }
        browserLock.lock();
        try {
            if (renderExecutor == null) {
                renderExecutor = Executors.newSingleThreadExecutor(r -> {
                    Thread t = new Thread(r, "playwright-render");
                    t.setDaemon(true); // nunca bloquea el shutdown de la JVM
                    return t;
                });
            }
            return renderExecutor;
        } finally {
            browserLock.unlock();
        }
    }

    /**
     * Descarta el executor tras un timeout. El hilo atascado en la llamada nativa CDP/proceso ignora la
     * interrupción y no muere: queda huérfano (daemon) hasta que el SO recupera el proceso chromium
     * colgado. Es un leak acotado y recuperable — preferible al envenenamiento permanente del lock.
     */
    private static void discardExecutor() {
        browserLock.lock();
        try {
            if (renderExecutor != null) {
                renderExecutor.shutdownNow();
                renderExecutor = null;
            }
        } finally {
            browserLock.unlock();
        }
    }

    /** Escala de impresión para que el contenido A5 entre en una sola página apaisada. */
    private static double escalaAjusteA5(Page page) {
        Object alto = page.evaluate("() => Math.ceil(document.body.scrollHeight)");
        double altoContenido = ((Number) alto).doubleValue();
        if (altoContenido <= ALTO_A5_LANDSCAPE_PX) {
            return 1.0;
        }
        // 0.98 de margen de seguridad para evitar un desbordamiento por redondeo a una 2ª página.
        double escala = (ALTO_A5_LANDSCAPE_PX / altoContenido) * 0.98;
        return Math.max(ESCALA_MINIMA, Math.min(1.0, escala));
    }

    public static Page.PdfOptions getPdfOptions(FormatoImpresion formato) {
        Page.PdfOptions options = new Page.PdfOptions()
            .setPrintBackground(true)
            .setMargin(new com.microsoft.playwright.options.Margin()
                .setTop("0")
                .setRight("0")
                .setBottom("0")
                .setLeft("0"));

        switch (formato) {
            case A4_LANDSCAPE:
                // A4 apaisado (297 x 210 mm): para documentos anchos como el Manifiesto de Carga.
                options.setFormat("A4");
                options.setLandscape(true);
                break;
            case A5:
                // A5 apaisado: una A4 partida a la mitad y girada (210 x 148 mm).
                options.setFormat("A5");
                options.setLandscape(true);
                break;
            case TICKET_80MM:
                options.setWidth("80mm");
                options.setPreferCSSPageSize(true);
                break;
            case TICKET_58MM:
                options.setWidth("58mm");
                options.setPreferCSSPageSize(true);
                break;
            case A4:
            default:
                options.setFormat("A4");
                break;
        }

        return options;
    }
}
