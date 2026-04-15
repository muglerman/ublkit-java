package com.cna.ublkit.sign.certificado;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * Repositorio en memoria para reutilizar certificados ya cargados.
 * <p>
 * Pensado para escenarios de alta concurrencia donde cargar y parsear
 * el keystore en cada operación de firma genera costo innecesario de CPU/IO.
 * </p>
 *
 * <p>
 * Este repositorio es thread-safe y garantiza carga única por clave mediante
 * {@link ConcurrentMap#computeIfAbsent(Object, java.util.function.Function)}.
 * </p>
 *
 * @since 1.0.0
 */
public final class RepositorioCertificados {

    private final ConcurrentMap<String, DetallesCertificado> cache = new ConcurrentHashMap<>();

    /**
     * Obtiene un certificado cacheado o lo carga una sola vez para la clave indicada.
     *
     * @param claveIdentidad identificador lógico del certificado (ej: RUC, tenant o alias).
     * @param cargador función que carga el certificado cuando no existe en cache.
     * @return certificado reutilizable.
     */
    public DetallesCertificado obtenerOCargar(String claveIdentidad, Supplier<DetallesCertificado> cargador) {
        Objects.requireNonNull(claveIdentidad, "claveIdentidad no puede ser null");
        Objects.requireNonNull(cargador, "cargador no puede ser null");

        return cache.computeIfAbsent(claveIdentidad, k -> {
            DetallesCertificado certificado = cargador.get();
            if (certificado == null) {
                throw new IllegalArgumentException("El cargador de certificado no puede retornar null");
            }
            return certificado;
        });
    }

    /**
     * Invalida una entrada puntual del cache.
     */
    public void invalidar(String claveIdentidad) {
        if (claveIdentidad != null) {
            cache.remove(claveIdentidad);
        }
    }

    /**
     * Limpia todo el cache de certificados.
     */
    public void limpiar() {
        cache.clear();
    }

    /**
     * Retorna el tamaño actual del cache.
     */
    public int size() {
        return cache.size();
    }
}
