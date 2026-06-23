package com.cna.ublkit.gateway.transporte;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Fabrica central de HttpClient nativo para aplicar configuración de pool
 * de conexiones de forma consistente.
 */
public final class HttpClientNativoFactory {

    private static final Logger LOG = Logger.getLogger(HttpClientNativoFactory.class.getName());
    private static final String PROP_CONNECTION_POOL_SIZE = "jdk.httpclient.connectionPoolSize";
    private static final AtomicBoolean POOL_MISMATCH_WARNING_EMITTED = new AtomicBoolean(false);

    private HttpClientNativoFactory() {
    }

    public static HttpClient crear(Duration connectTimeout, int maxConnections) {
        aplicarPoolConexiones(maxConnections);
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(connectTimeout)
                .build();
    }

    static synchronized void aplicarPoolConexiones(int maxConnections) {
        int pool = Math.max(1, maxConnections);
        String existente = System.getProperty(PROP_CONNECTION_POOL_SIZE);

        if (existente == null || existente.isBlank()) {
            System.setProperty(PROP_CONNECTION_POOL_SIZE, String.valueOf(pool));
            return;
        }

        if (!String.valueOf(pool).equals(existente)) {
            if (POOL_MISMATCH_WARNING_EMITTED.compareAndSet(false, true)) {
                LOG.warning("HttpClient pool ya configurado con " + existente
                        + ". Se mantiene ese valor y se ignora maxConnections=" + pool);
            }
        }
    }
}
