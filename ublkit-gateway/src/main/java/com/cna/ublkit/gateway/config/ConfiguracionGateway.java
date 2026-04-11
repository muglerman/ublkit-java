package com.cna.ublkit.gateway.config;

import java.time.Duration;

/**
 * Configuración para el Gateway de SUNAT.
 * Permite ajustar los timeouts de conexión y lectura,
 * así como el número máximo de reintentos para errores temporales.
 *
 * @since 0.1.0
 */
public record ConfiguracionGateway(
        Duration connectTimeout,
        Duration readTimeout,
        int maxIntentos
) {
    /**
     * Configuración por defecto recomendada:
     * - Timeout de conexión: 10 segundos
     * - Timeout de lectura: 60 segundos
     * - Máximo de intentos: 3
     */
    public static ConfiguracionGateway porDefecto() {
        return new ConfiguracionGateway(Duration.ofSeconds(10), Duration.ofSeconds(60), 3);
    }
}
