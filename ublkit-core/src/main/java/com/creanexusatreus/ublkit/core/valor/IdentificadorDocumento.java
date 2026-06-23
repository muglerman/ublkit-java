package com.cna.ublkit.core.valor;

import java.util.Objects;

/**
 * Value object para identidad de documento serie-número.
 *
 * @since 0.1.0
 */
public final class IdentificadorDocumento {
    private final NumeroSerie serie;
    private final Integer numero;

    public IdentificadorDocumento(NumeroSerie serie, Integer numero) {
        if (serie == null) throw new IllegalArgumentException("La serie es obligatoria");
        if (numero == null || numero <= 0) throw new IllegalArgumentException("El número debe ser mayor a cero");
        this.serie = serie;
        this.numero = numero;
    }

    public NumeroSerie serie() {
        return serie;
    }

    public Integer numero() {
        return numero;
    }

    public String valor() {
        return serie.getValor() + "-" + numero;
    }

    @Override
    public String toString() {
        return valor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentificadorDocumento that)) return false;
        return Objects.equals(serie, that.serie) && Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serie, numero);
    }
}

