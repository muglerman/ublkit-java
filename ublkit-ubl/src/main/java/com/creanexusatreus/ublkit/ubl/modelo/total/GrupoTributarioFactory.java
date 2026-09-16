package com.creanexusatreus.ublkit.ubl.modelo.total;

import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.xml.CategoriaIgv;
import java.math.BigDecimal;

/** Construye la descripción tributaria común para línea y resumen global. */
public final class GrupoTributarioFactory {
    private GrupoTributarioFactory() { }

    public static GrupoTributario igv(LineaDetalle linea) {
        CategoriaIgv cat = CategoriaIgv.obtener(linea.getIgvTipo());
        BigDecimal porcentaje = cat.categoriaId().equals("S") && linea.getTasaIgv() != null
                ? linea.getTasaIgv().multiply(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        return new GrupoTributario(linea.getIgvTipo(), cat.categoriaId(), cat.tribCode(), cat.tribName(),
                cat.tribTypeCode(), porcentaje, linea.getIgvBaseImponible(), linea.getIgv());
    }
    public static GrupoTributario isc(LineaDetalle linea) {
        return new GrupoTributario(null, "S", "2000", "ISC", "EXC",
                linea.getTasaIsc() == null ? BigDecimal.ZERO : linea.getTasaIsc().multiply(BigDecimal.valueOf(100)),
                linea.getIscBaseImponible(), linea.getIsc());
    }
    public static GrupoTributario icbper(LineaDetalle linea) {
        return new GrupoTributario(null, "S", "7152", "ICBPER", "OTH", BigDecimal.ZERO,
                BigDecimal.ZERO, linea.getIcb());
    }
}
