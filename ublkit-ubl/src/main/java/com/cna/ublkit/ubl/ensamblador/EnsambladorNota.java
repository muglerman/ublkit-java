package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.BorradorNotaCredito;
import com.cna.ublkit.ubl.modelo.BorradorNotaDebito;
import com.cna.ublkit.ubl.modelo.DocumentoBase;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Ensamblador para Notas de Crédito y Notas de Débito.
 * <p>
 * Enriquece el documento calculando impuestos por línea y totales,
 * respetando valores ya proporcionados por el usuario.
 * </p>
 *
 * @since 0.1.0
 */
public final class EnsambladorNota {

    private static final BigDecimal TASA_IGV_DEFECTO = new BigDecimal("0.18");
    private static final int ESCALA = 2;
    private static final RoundingMode REDONDEO = RoundingMode.HALF_UP;

    private EnsambladorNota() {
    }

    public static BorradorNotaCredito ensamblar(BorradorNotaCredito nota) {
        aplicarDefectos(nota);
        ensamblarLineas(nota);
        ensamblarTotalImpuestos(nota);
        ensamblarTotalImporteNota(nota, nota.getTotalImporte(), ti -> nota.setTotalImporte(ti));
        return nota;
    }

    public static BorradorNotaDebito ensamblar(BorradorNotaDebito nota) {
        aplicarDefectos(nota);
        ensamblarLineas(nota);
        ensamblarTotalImpuestos(nota);
        ensamblarTotalImporteNota(nota, nota.getTotalImporte(), ti -> nota.setTotalImporte(ti));
        return nota;
    }

    private static void aplicarDefectos(DocumentoBase nota) {
        if (nota.getMoneda() == null) {
            nota.setMoneda("PEN");
        }
        if (nota.getTasaIgv() == null) {
            nota.setTasaIgv(TASA_IGV_DEFECTO);
        }
    }

private static void ensamblarLineas(DocumentoBase documento) {
    List<LineaDetalle> detalles = documento.getDetalles();
    if (detalles == null) return;

    BigDecimal tasaIgv = documento.getTasaIgv() != null ? documento.getTasaIgv() : TASA_IGV_DEFECTO;
    for (LineaDetalle linea : detalles) {
        EnsambladorFactura.ensamblarLinea(linea, tasaIgv, documento.getTasaIcb());
    }

    EnsambladorFactura.ajustarRedondeoLineas(detalles);
}

    private static void ensamblarTotalImpuestos(DocumentoBase documento) {
        if (documento.getTotalImpuestos() != null) return;
        List<LineaDetalle> detalles = documento.getDetalles();
        if (detalles == null || detalles.isEmpty()) return;

        BigDecimal totalGeneral = BigDecimal.ZERO;
        BigDecimal gravadoImp = BigDecimal.ZERO, gravadoBase = BigDecimal.ZERO;
        BigDecimal exoneradoImp = BigDecimal.ZERO, exoneradoBase = BigDecimal.ZERO;
        BigDecimal inafectoImp = BigDecimal.ZERO, inafectoBase = BigDecimal.ZERO;
        BigDecimal gratuitoImp = BigDecimal.ZERO, gratuitoBase = BigDecimal.ZERO;
        BigDecimal exportacionImp = BigDecimal.ZERO, exportacionBase = BigDecimal.ZERO;
        BigDecimal ivapImp = BigDecimal.ZERO, ivapBase = BigDecimal.ZERO;
        BigDecimal icbImp = BigDecimal.ZERO;
        BigDecimal iscImp = BigDecimal.ZERO, iscBase = BigDecimal.ZERO;

        for (LineaDetalle linea : detalles) {
            String tipo = linea.getIgvTipo() != null ? linea.getIgvTipo() : "10";
            BigDecimal igv = orZero(linea.getIgv());
            BigDecimal base = orZero(linea.getIgvBaseImponible());

            if (EnsambladorFactura.esGravado(tipo)) { gravadoImp = gravadoImp.add(igv); gravadoBase = gravadoBase.add(base); }
            else if (EnsambladorFactura.esExonerado(tipo)) { exoneradoImp = exoneradoImp.add(igv); exoneradoBase = exoneradoBase.add(base); }
            else if (EnsambladorFactura.esInafecto(tipo)) { inafectoImp = inafectoImp.add(igv); inafectoBase = inafectoBase.add(base); }
            else if (EnsambladorFactura.esGratuito(tipo)) { gratuitoImp = gratuitoImp.add(igv); gratuitoBase = gratuitoBase.add(base); }
            else if (EnsambladorFactura.esExportacion(tipo)) { exportacionImp = exportacionImp.add(igv); exportacionBase = exportacionBase.add(base); }
            else if (EnsambladorFactura.esIvap(tipo)) { ivapImp = ivapImp.add(igv); ivapBase = ivapBase.add(base); }

            if (linea.getIsc() != null) { iscImp = iscImp.add(linea.getIsc()); iscBase = iscBase.add(orZero(linea.getIscBaseImponible())); }
            if (linea.getIcb() != null) { icbImp = icbImp.add(linea.getIcb()); }
        }

        totalGeneral = gravadoImp.add(exoneradoImp).add(inafectoImp).add(iscImp).add(icbImp).add(ivapImp);

        documento.setTotalImpuestos(new TotalImpuestos(
                totalGeneral.setScale(ESCALA, REDONDEO),
                nulo(gravadoImp), nulo(gravadoBase),
                nulo(exoneradoImp), nulo(exoneradoBase),
                nulo(inafectoImp), nulo(inafectoBase),
                nulo(gratuitoImp), nulo(gratuitoBase),
                nulo(exportacionImp), nulo(exportacionBase),
                nulo(ivapImp), nulo(ivapBase),
                nulo(icbImp), nulo(iscImp), nulo(iscBase)
        ));
    }

    @FunctionalInterface
    private interface SetterTotalImporte {
        void set(TotalImporte ti);
    }

    private static void ensamblarTotalImporteNota(DocumentoBase nota, TotalImporte existente, SetterTotalImporte setter) {
        if (existente != null) return;

        TotalImpuestos imp = nota.getTotalImpuestos();
        if (imp == null) return;

        BigDecimal importeSinImpuestos = BigDecimal.ZERO;
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.gravadoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.exoneradoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.inafectoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.exportacionBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.ivapBaseImponible()));

        BigDecimal importeConImpuestos = importeSinImpuestos.add(imp.total());

        setter.set(new TotalImporte(
                importeConImpuestos.setScale(ESCALA, REDONDEO),
                importeSinImpuestos.setScale(ESCALA, REDONDEO),
                importeConImpuestos.setScale(ESCALA, REDONDEO),
                null, null
        ));
    }

    private static BigDecimal orZero(BigDecimal v) { return v != null ? v : BigDecimal.ZERO; }
    private static BigDecimal nulo(BigDecimal v) { return v.compareTo(BigDecimal.ZERO) != 0 ? v.setScale(ESCALA, REDONDEO) : null; }
}
