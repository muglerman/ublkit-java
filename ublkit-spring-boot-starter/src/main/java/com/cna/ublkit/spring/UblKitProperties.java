package com.cna.ublkit.spring;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades externas para configurar el comportamiento del starter.
 */
@ConfigurationProperties(prefix = "ublkit")
public class UblKitProperties {

    private final Gateway gateway = new Gateway();

    public Gateway getGateway() {
        return gateway;
    }

    public static class Gateway {
        private Duration connectTimeout = Duration.ofSeconds(10);
        private Duration readTimeout = Duration.ofSeconds(60);
        private Long connectTimeoutMs;
        private Long readTimeoutMs;
        private int maxIntentos = 3;
        private int maxConnections = 100;

        public Duration resolveConnectTimeout() {
            if (connectTimeoutMs != null) {
                return Duration.ofMillis(Math.max(1L, connectTimeoutMs));
            }
            return connectTimeout;
        }

        public Duration resolveReadTimeout() {
            if (readTimeoutMs != null) {
                return Duration.ofMillis(Math.max(1L, readTimeoutMs));
            }
            return readTimeout;
        }

        public Duration getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public Duration getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
        }

        public Long getConnectTimeoutMs() {
            return connectTimeoutMs;
        }

        public void setConnectTimeoutMs(Long connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
        }

        public Long getReadTimeoutMs() {
            return readTimeoutMs;
        }

        public void setReadTimeoutMs(Long readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
        }

        public int getMaxIntentos() {
            return maxIntentos;
        }

        public void setMaxIntentos(int maxIntentos) {
            this.maxIntentos = maxIntentos;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }
    }
}
