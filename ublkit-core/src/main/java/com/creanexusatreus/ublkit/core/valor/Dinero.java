package com.cna.ublkit.core.valor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value object de monto monetario tipado.
 *
 * @since 0.1.0
 */
public final class Dinero {
    private final BigDecimal monto;
    private final Moneda moneda;

    public Dinero(BigDecimal monto, Moneda moneda) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto es obligatorio");
        }
        if (moneda == null) {
            throw new IllegalArgumentException("La moneda es obligatoria");
        }
        this.monto = monto.setScale(2, RoundingMode.HALF_UP);
        this.moneda = moneda;
    }

    public BigDecimal monto() {
        return monto;
    }

    public Moneda moneda() {
        return moneda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dinero dinero)) return false;
        return Objects.equals(monto, dinero.monto) && Objects.equals(moneda, dinero.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto, moneda);
    }
}

