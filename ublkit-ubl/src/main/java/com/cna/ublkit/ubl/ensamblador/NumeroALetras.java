package com.cna.ublkit.ubl.ensamblador;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Conversor simple de números a letras para leyenda SUNAT 1000.
 */
final class NumeroALetras {

    private static final String[] UNIDADES = {
            "", "UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE",
            "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE",
            "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"
    };

    private static final String[] DECENAS = {
            "", "", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
            "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private static final String[] CENTENAS = {
            "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
            "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
    };

    private NumeroALetras() {
    }

    static String formatearMonto(BigDecimal monto, String moneda) {
        BigDecimal normalizado = monto == null
                ? BigDecimal.ZERO
                : monto.abs().setScale(2, RoundingMode.HALF_UP);
        long entero = normalizado.longValue();
        int centimos = normalizado
                .subtract(BigDecimal.valueOf(entero))
                .movePointRight(2)
                .intValue();

        return "SON: " + convertir(entero) + " CON "
                + String.format("%02d", centimos)
                + "/100 " + descripcionMoneda(moneda);
    }

    private static String descripcionMoneda(String moneda) {
        if ("USD".equals(moneda)) return "DOLARES AMERICANOS";
        if ("EUR".equals(moneda)) return "EUROS";
        return "SOLES";
    }

    private static String convertir(long numero) {
        if (numero == 0) return "CERO";
        if (numero < 0) return "MENOS " + convertir(-numero);
        if (numero <= 999_999_999L) {
            return convertirMillones((int) numero).trim().replaceAll("\\s+", " ");
        }
        return String.valueOf(numero);
    }

    private static String convertirMillones(int numero) {
        int millones = numero / 1_000_000;
        int resto = numero % 1_000_000;

        StringBuilder sb = new StringBuilder();
        if (millones > 0) {
            if (millones == 1) {
                sb.append("UN MILLON");
            } else {
                sb.append(convertirMiles(millones)).append(" MILLONES");
            }
            if (resto > 0) sb.append(" ");
        }
        if (resto > 0) {
            sb.append(convertirMiles(resto));
        }
        return sb.toString();
    }

    private static String convertirMiles(int numero) {
        int miles = numero / 1000;
        int resto = numero % 1000;
        StringBuilder sb = new StringBuilder();

        if (miles > 0) {
            if (miles == 1) {
                sb.append("MIL");
            } else {
                sb.append(convertirCentenas(miles)).append(" MIL");
            }
            if (resto > 0) sb.append(" ");
        }
        if (resto > 0) {
            sb.append(convertirCentenas(resto));
        }
        return sb.toString();
    }

    private static String convertirCentenas(int numero) {
        if (numero == 100) return "CIEN";
        if (numero < 100) return convertirDecenas(numero);

        int centenas = numero / 100;
        int resto = numero % 100;
        if (resto == 0) return CENTENAS[centenas];
        return CENTENAS[centenas] + " " + convertirDecenas(resto);
    }

    private static String convertirDecenas(int numero) {
        if (numero <= 20) return UNIDADES[numero];
        if (numero < 30) {
            return switch (numero) {
                case 21 -> "VEINTIUNO";
                case 22 -> "VEINTIDOS";
                case 23 -> "VEINTITRES";
                case 24 -> "VEINTICUATRO";
                case 25 -> "VEINTICINCO";
                case 26 -> "VEINTISEIS";
                case 27 -> "VEINTISIETE";
                case 28 -> "VEINTIOCHO";
                case 29 -> "VEINTINUEVE";
                default -> "VEINTE";
            };
        }

        int decena = numero / 10;
        int unidad = numero % 10;
        if (unidad == 0) return DECENAS[decena];
        return DECENAS[decena] + " Y " + UNIDADES[unidad];
    }
}
