package com.creanexusatreus.ublkit.ubl.modelo.total;

import java.math.BigDecimal;
import java.util.Objects;

/** Un grupo homogéneo de tributo para la representación UBL/SUNAT. */
public record GrupoTributario(
        String codigoAfectacion,
        String categoriaId,
        String tributoId,
        String tributoNombre,
        String tributoTipoCodigo,
        BigDecimal porcentaje,
        BigDecimal baseImponible,
        BigDecimal importe
) {
    public GrupoTributario {
        Objects.requireNonNull(tributoId, "tributoId");
        porcentaje = porcentaje == null ? BigDecimal.ZERO : porcentaje;
        baseImponible = baseImponible == null ? BigDecimal.ZERO : baseImponible;
        importe = importe == null ? BigDecimal.ZERO : importe;
    }

    public GrupoTributario acumular(GrupoTributario otro) {
        return new GrupoTributario(codigoAfectacion, categoriaId, tributoId, tributoNombre,
                tributoTipoCodigo, porcentaje, baseImponible.add(otro.baseImponible),
                importe.add(otro.importe));
    }
}
