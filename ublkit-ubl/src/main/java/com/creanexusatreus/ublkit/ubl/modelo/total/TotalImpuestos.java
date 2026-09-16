package com.creanexusatreus.ublkit.ubl.modelo.total;

import com.creanexusatreus.ublkit.ubl.modelo.linea.LineaDetalle;
import com.creanexusatreus.ublkit.ubl.xml.CategoriaIgv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Desglose completo de impuestos del documento.
 * Equivale a {@code cac:TaxTotal} en UBL 2.1.
 *
 * @param total                    Suma total de todos los impuestos.
 * @param gravadoImporte           Monto IGV gravado.
 * @param gravadoBaseImponible     Base imponible gravada.
 * @param exoneradoImporte         Monto exonerado.
 * @param exoneradoBaseImponible   Base imponible exonerada.
 * @param inafectoImporte          Monto inafecto.
 * @param inafectoBaseImponible    Base imponible inafecta.
 * @param gratuitoImporte          Monto gratuito.
 * @param gratuitoBaseImponible    Base imponible gratuita.
 * @param exportacionImporte       Monto de exportación.
 * @param exportacionBaseImponible Base imponible de exportación.
 * @param ivapImporte              Monto IVAP.
 * @param ivapBaseImponible        Base imponible IVAP.
 * @param icbImporte               Monto ICBPER (bolsas plásticas).
 * @param iscImporte               Monto ISC.
 * @param iscBaseImponible         Base imponible ISC.
 *
 * @since 0.1.0
 */
public final class TotalImpuestos {
    private final List<GrupoTributario> grupos;
    private final BigDecimal total;

    public TotalImpuestos(BigDecimal total, List<GrupoTributario> grupos) {
        this.total = total == null ? BigDecimal.ZERO : total;
        this.grupos = Collections.unmodifiableList(new ArrayList<>(grupos == null ? List.of() : grupos));
    }

    public static TotalImpuestos desdeLineas(List<LineaDetalle> lineas) {
        Map<String, GrupoTributario> acumulados = new LinkedHashMap<>();
        if (lineas != null) {
            for (LineaDetalle linea : lineas) {
                agregar(acumulados, GrupoTributarioFactory.igv(linea));
                if (linea.getIsc() != null) agregar(acumulados, GrupoTributarioFactory.isc(linea));
                if (linea.getIcb() != null) agregar(acumulados, GrupoTributarioFactory.icbper(linea));
            }
        }
        List<GrupoTributario> grupos = new ArrayList<>(acumulados.values());
        BigDecimal total = grupos.stream().map(GrupoTributario::importe)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new TotalImpuestos(total, grupos);
    }

    private static void agregar(Map<String, GrupoTributario> grupos, GrupoTributario grupo) {
        if (grupo == null || (grupo.baseImponible().signum() == 0 && grupo.importe().signum() == 0)) return;
        String clave = String.join("|", nullToEmpty(grupo.tributoId()), nullToEmpty(grupo.categoriaId()),
                nullToEmpty(grupo.codigoAfectacion()), grupo.porcentaje().stripTrailingZeros().toPlainString());
        grupos.merge(clave, grupo, GrupoTributario::acumular);
    }

    private static String nullToEmpty(String value) { return value == null ? "" : value; }

    public BigDecimal total() { return total; }
    public List<GrupoTributario> grupos() { return grupos; }

    // Proyecciones de presentación: no son la fuente del XML.
    public BigDecimal gravadoImporte() { return suma("1000"); }
    public BigDecimal gravadoBaseImponible() { return base("1000"); }
    public BigDecimal exoneradoImporte() { return suma("9997"); }
    public BigDecimal exoneradoBaseImponible() { return base("9997"); }
    public BigDecimal inafectoImporte() { return suma("9998"); }
    public BigDecimal inafectoBaseImponible() { return base("9998"); }
    public BigDecimal gratuitoImporte() { return suma("9996"); }
    public BigDecimal gratuitoBaseImponible() { return base("9996"); }
    public BigDecimal exportacionImporte() { return suma("9995"); }
    public BigDecimal exportacionBaseImponible() { return base("9995"); }
    public BigDecimal ivapImporte() { return suma("1016"); }
    public BigDecimal ivapBaseImponible() { return base("1016"); }
    public BigDecimal icbImporte() { return suma("7152"); }
    public BigDecimal iscImporte() { return suma("2000"); }
    public BigDecimal iscBaseImponible() { return base("2000"); }
    private BigDecimal suma(String id) { return valor(id, false); }
    private BigDecimal base(String id) { return valor(id, true); }
    private BigDecimal valor(String id, boolean base) {
        BigDecimal result = grupos.stream().filter(g -> id.equals(g.tributoId()))
                .map(g -> base ? g.baseImponible() : g.importe()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return result.signum() == 0 ? null : result;
    }

    /** Constructor usado únicamente por fixtures antiguos durante su migración. */
    public TotalImpuestos(BigDecimal total, BigDecimal gravadoImporte, BigDecimal gravadoBase,
                          BigDecimal exoneradoImporte, BigDecimal exoneradoBase, BigDecimal inafectoImporte,
                          BigDecimal inafectoBase, BigDecimal gratuitoImporte, BigDecimal gratuitoBase,
                          BigDecimal exportacionImporte, BigDecimal exportacionBase, BigDecimal ivapImporte,
                          BigDecimal ivapBase, BigDecimal icbImporte, BigDecimal iscImporte,
                          BigDecimal iscBase) {
        this(total, legacyGroups(gravadoImporte, gravadoBase, exoneradoImporte, exoneradoBase,
                inafectoImporte, inafectoBase, gratuitoImporte, gratuitoBase, exportacionImporte,
                exportacionBase, ivapImporte, ivapBase, icbImporte, iscImporte, iscBase));
    }

    private static List<GrupoTributario> legacyGroups(BigDecimal gi, BigDecimal gb, BigDecimal ei, BigDecimal eb,
            BigDecimal oi, BigDecimal ob, BigDecimal zi, BigDecimal zb, BigDecimal xi, BigDecimal xb,
            BigDecimal vi, BigDecimal vb, BigDecimal icb, BigDecimal isci, BigDecimal iscb) {
        List<GrupoTributario> r = new ArrayList<>();
        addLegacy(r, "S", "1000", "IGV", "VAT", gi, gb);
        addLegacy(r, "E", "9997", "EXO", "VAT", ei, eb);
        addLegacy(r, "O", "9998", "INA", "FRE", oi, ob);
        addLegacy(r, "Z", "9996", "GRA", "FRE", zi, zb);
        addLegacy(r, "G", "9995", "EXP", "FRE", xi, xb);
        addLegacy(r, "S", "1016", "IVAP", "VAT", vi, vb);
        addLegacy(r, "S", "7152", "ICBPER", "OTH", icb, null);
        addLegacy(r, "S", "2000", "ISC", "EXC", isci, iscb);
        return r;
    }
    private static void addLegacy(List<GrupoTributario> r, String cat, String id, String name, String type,
            BigDecimal amount, BigDecimal base) {
        if (amount != null || base != null) r.add(new GrupoTributario(null, cat, id, name, type, BigDecimal.ZERO, base, amount));
    }
}
