package com.cna.ublkit.core.valor;

import java.util.Locale;
import java.util.Objects;

/**
 * Value object de código de moneda ISO-4217.
 *
 * @since 0.1.0
 */
public final class Moneda {
    private final String codigo;

    public Moneda(String codigo) {
        if (codigo == null || !codigo.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("La moneda debe tener 3 letras ISO-4217");
        }
        this.codigo = codigo.toUpperCase(Locale.ROOT);
    }

    public String codigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Moneda moneda)) return false;
        return Objects.equals(codigo, moneda.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}

