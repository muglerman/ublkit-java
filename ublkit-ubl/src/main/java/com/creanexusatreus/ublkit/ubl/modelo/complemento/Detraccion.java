package com.creanexusatreus.ublkit.ubl.modelo.complemento;

import java.math.BigDecimal;

/**
 * Datos del Sistema de Pago de Obligaciones Tributarias (SPOT).
 * <p>
 * El porcentaje se expresa como lo recibe Quantus y la API: porcentaje humano.
 * Por ejemplo, {@code 12} representa 12% y se serializa como
 * {@code cbc:PaymentPercent=12}; no como 1200.
 * </p>
 *
 * @param medioDePago       Medio de pago (Catálogo 59).
 * @param cuentaBancaria    Número de cuenta bancaria del Banco de la Nación.
 * @param tipoBienDetraido  Código del bien o servicio sujeto a detracción (Catálogo 54).
 * @param porcentaje        Porcentaje humano de detracción (ej. 12 para 12%).
 * @param monto             Monto de la detracción.
 *
 * @since 0.1.0
 */
public record Detraccion(
        String medioDePago,
        String cuentaBancaria,
        String tipoBienDetraido,
        BigDecimal porcentaje,
        BigDecimal monto
) {

    /**
     * Valida los datos que SUNAT exige cuando se informa una detracción.
     * Se invoca al ensamblar y al serializar para que una llamada directa al
     * serializador no genere XML incompleto.
     */
    public void validar() {
        validarTexto(medioDePago, "medio de pago");
        validarTexto(cuentaBancaria, "cuenta bancaria del Banco de la Nación");
        validarTexto(tipoBienDetraido, "código de bien o servicio sujeto a detracción");
        validarDecimalPositivo(porcentaje, 3, 5, "porcentaje de detracción");
        validarDecimalPositivo(monto, 12, 2, "monto de detracción");
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La detracción requiere " + campo);
        }
    }

    private static void validarDecimalPositivo(BigDecimal valor, int maxEnteros, int maxDecimales, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("La detracción requiere " + campo);
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El " + campo + " de la detracción debe ser positivo");
        }

        BigDecimal normalizado = valor.stripTrailingZeros();
        int decimales = Math.max(normalizado.scale(), 0);
        int enteros = normalizado.precision() - decimales;
        if (enteros > maxEnteros || decimales > maxDecimales) {
            throw new IllegalArgumentException(
                    "El " + campo + " de la detracción debe tener hasta " + maxEnteros
                            + " enteros y " + maxDecimales + " decimales");
        }
    }

    // Alias JavaBean para las plantillas existentes. Conservan la API pública
    // del record y corrigen los nombres de propiedad históricos del render.
    public String getMedioPago() {
        return medioDePago;
    }

    public String getCuentaBanco() {
        return cuentaBancaria;
    }

    public String getCodigoBien() {
        return tipoBienDetraido;
    }
}
