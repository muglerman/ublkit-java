package com.cna.ublkit.sign.certificado;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Decorador de {@link ProveedorCertificado} que cachea los certificados en memoria
 * para evitar fugas y latencias altas en entornos multi-tenant, permitiendo destruirlos
 * automáticamente luego de un TTL configurado.
 *
 * @since 0.1.0
 */
public class ProveedorCertificadoCache implements ProveedorCertificado {

    private final ProveedorCertificado delegado;
    private final ConcurrentHashMap<String, EntradaCache> cache = new ConcurrentHashMap<>();
    private final long ttlMillis;
    private final ScheduledExecutorService limpiador;
    private Consumer<DetallesCertificado> onExpirar;

    public ProveedorCertificadoCache(ProveedorCertificado delegado, long ttlMinutos) {
        this.delegado = delegado;
        this.ttlMillis = TimeUnit.MINUTES.toMillis(ttlMinutos);
        this.limpiador = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "proveedor-certificado-cache-limpiador");
            t.setDaemon(true);
            return t;
        });
        this.limpiador.scheduleAtFixedRate(this::limpiarExpirados, 1, 1, TimeUnit.MINUTES);
    }

    public void setOnExpirar(Consumer<DetallesCertificado> onExpirar) {
        this.onExpirar = onExpirar;
    }

    @Override
    public DetallesCertificado obtenerCertificado(String id) {
        long ahora = System.currentTimeMillis();
        EntradaCache entrada = cache.get(id);

        if (entrada != null) {
            if (ahora - entrada.timestamp < ttlMillis) {
                entrada.timestamp = ahora; // Renovamos TTL por acceso
                return entrada.detalles;
            } else {
                cache.remove(id);
                notificarExpiracion(entrada.detalles);
            }
        }

        DetallesCertificado nuevo = delegado.obtenerCertificado(id);
        if (nuevo != null) {
            cache.put(id, new EntradaCache(nuevo, ahora));
        }
        return nuevo;
    }

    /**
     * Invalida explícitamente el certificado de un tenant (ej. si cambió su clave).
     */
    public void invalidar(String id) {
        EntradaCache removido = cache.remove(id);
        if (removido != null) {
            notificarExpiracion(removido.detalles);
        }
    }

    /**
     * Cierra el thread de limpieza.
     */
    public void apagar() {
        limpiador.shutdown();
    }

    private void limpiarExpirados() {
        long ahora = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> {
            boolean expiro = (ahora - entry.getValue().timestamp) >= ttlMillis;
            if (expiro) {
                notificarExpiracion(entry.getValue().detalles);
            }
            return expiro;
        });
    }

    private void notificarExpiracion(DetallesCertificado detalles) {
        if (onExpirar != null && detalles != null) {
            try {
                onExpirar.accept(detalles);
            } catch (Exception e) {
                // Ignorar error del consumidor
            }
        }
    }

    private static class EntradaCache {
        final DetallesCertificado detalles;
        long timestamp;

        EntradaCache(DetallesCertificado detalles, long timestamp) {
            this.detalles = detalles;
            this.timestamp = timestamp;
        }
    }
}
