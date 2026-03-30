package com.cna.ublkit.core.valor;

import java.util.Objects;

/**
 * Representa la serie de un documento.
 *
 * <p>Este objeto de valor encapsula la serie documental y permite centralizar
 * futuras validaciones de formato.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public final class NumeroSerie {

    private final String valor;

    /**
     * Crea una nueva serie documental.
     *
     * @param valor valor textual de la serie
     */
    public NumeroSerie(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La serie no puede ser nula ni vacía");
        }
        this.valor = valor;
    }

    /**
     * Retorna el valor textual de la serie.
     *
     * @return valor de la serie
     */
    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumeroSerie that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
