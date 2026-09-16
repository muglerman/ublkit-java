package com.creanexusatreus.ublkit.ubl.ensamblador;

import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaCredito;
import com.creanexusatreus.ublkit.ubl.modelo.BorradorNotaDebito;
import com.creanexusatreus.ublkit.ubl.modelo.DocumentoBase;
import com.creanexusatreus.ublkit.ubl.modelo.complemento.Detraccion;
import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImporte;
import com.creanexusatreus.ublkit.ubl.modelo.total.TotalImpuestos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        aplicarReglasDetraccion(nota);
        ensamblarLeyendasSunat(nota);
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

    private static void aplicarReglasDetraccion(BorradorNotaDebito nota) {
        Detraccion detraccion = nota.getDetraccion();
        if (detraccion == null) {
            return;
        }

        String cuenta = detraccion.cuentaBancaria();
        if ((cuenta == null || cuenta.isBlank()) && nota.getEmisor() != null) {
            cuenta = nota.getEmisor().cuentaBancoNacionDetraccion();
        }

        Detraccion normalizada = new Detraccion(
                detraccion.medioDePago(),
                cuenta,
                detraccion.tipoBienDetraido(),
                detraccion.porcentaje(),
                detraccion.monto());
        normalizada.validar();
        nota.setDetraccion(normalizada);
    }

    private static void ensamblarLeyendasSunat(BorradorNotaDebito nota) {
        if (nota.getDetraccion() == null) {
            return;
        }

        Map<String, String> leyendas = nota.getLeyendas() != null
                ? new LinkedHashMap<>(nota.getLeyendas())
                : new LinkedHashMap<>();
        leyendas.putIfAbsent("2006", "OPERACION SUJETA A DETRACCION");
        nota.setLeyendas(leyendas);
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

        documento.setTotalImpuestos(TotalImpuestos.desdeLineas(detalles));
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
