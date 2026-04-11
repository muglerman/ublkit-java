package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.BorradorFactura;
import com.cna.ublkit.ubl.modelo.DocumentoBase;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.Detraccion;
import com.cna.ublkit.ubl.modelo.complemento.FormaDePago;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import com.cna.ublkit.ubl.modelo.total.TotalImpuestos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Ensamblador de documentos de venta (Factura y Boleta).
 * <p>
 * Enriquece un {@link BorradorFactura} calculando automáticamente los campos
 * derivados que el usuario no haya proporcionado: impuestos por línea,
 * totales de impuestos y totales monetarios.
 * </p>
 * <p>
 * Si el usuario ya proporcionó valores calculados (como en fractuyo), esos
 * valores se respetan y no se sobrescriben.
 * </p>
 *
 * @since 0.1.0
 */
public final class EnsambladorFactura {

    private static final BigDecimal TASA_IGV_DEFECTO = new BigDecimal("0.18");
    private static final BigDecimal UMBRAL_BOLETA_RECEPTOR = new BigDecimal("700.00");
    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final int ESCALA = 2;
    private static final RoundingMode REDONDEO = RoundingMode.HALF_UP;

    private EnsambladorFactura() {
    }

    /**
     * Ensambla un borrador de factura, calculando campos derivados si faltan.
     *
     * @param factura borrador a ensamblar (se modifica in-place)
     * @return el mismo borrador ensamblado
     */
    public static BorradorFactura ensamblar(BorradorFactura factura) {
        aplicarReglasExportacion(factura);
        aplicarDefectos(factura);
        ensamblarLineas(factura);
        ensamblarTotalImpuestos(factura);
        ensamblarTotalImporte(factura);
        aplicarReglasBoleta(factura);
        aplicarReglasFormaPago(factura);
        aplicarReglasDetraccion(factura);
        ensamblarLeyendasSunat(factura);
        return factura;
    }

    // ── Reglas Forma Pago Crédito ───────────────────────────────────

    private static void aplicarReglasFormaPago(BorradorFactura factura) {
        FormaDePago fdp = factura.getFormaDePago();
        if (fdp != null && "Credito".equals(fdp.tipo())) {
            if (fdp.total() == null && fdp.cuotas() != null) {
                BigDecimal total = BigDecimal.ZERO;
                for (var cuota : fdp.cuotas()) {
                    if (cuota.importe() != null) {
                        total = total.add(cuota.importe());
                    }
                }
                factura.setFormaDePago(new FormaDePago(fdp.tipo(), total, fdp.cuotas()));
            }
        }
    }

    // ── Detracciones ──────────────────────────────────────────────────

    private static void aplicarReglasDetraccion(BorradorFactura factura) {
        Detraccion det = factura.getDetraccion();
        if (det != null && esOperacionDetraccion(factura.getTipoOperacion())) {
            BigDecimal porcentaje = det.porcentaje();
            if (porcentaje == null && det.tipoBienDetraido() != null) {
                porcentaje = CatalogoDetracciones.obtenerPorcentaje(det.tipoBienDetraido());
            }

            BigDecimal monto = det.monto();
            if (monto == null && porcentaje != null) {
                BigDecimal total = montoTotalDocumento(factura);
                monto = total.multiply(porcentaje).setScale(ESCALA, REDONDEO);
            }

            String cuenta = det.cuentaBancaria();
            if (cuenta == null && factura.getEmisor() != null && factura.getEmisor().cuentaBancoNacionDetraccion() != null) {
                cuenta = factura.getEmisor().cuentaBancoNacionDetraccion();
            }

            factura.setDetraccion(new Detraccion(
                    det.medioDePago(),
                    cuenta,
                    det.tipoBienDetraido(),
                    porcentaje,
                    monto
            ));
        }
    }

    // ── Reglas Exportación ───────────────────────────────────────

    private static void aplicarReglasExportacion(BorradorFactura factura) {
        boolean esExportacion = "0200".equals(factura.getTipoOperacion());
        if (!esExportacion && factura.getDetalles() != null) {
            for (var linea : factura.getDetalles()) {
                if ("40".equals(linea.getIgvTipo())) {
                    esExportacion = true;
                    break;
                }
            }
        }

        if (esExportacion) {
            factura.setTipoOperacion("0200");
            if (factura.getMoneda() == null) {
                factura.setMoneda("USD");
            }
            if (factura.getDetalles() != null) {
                for (var linea : factura.getDetalles()) {
                    if (linea.getIgvTipo() == null || !"40".equals(linea.getIgvTipo())) {
                        linea.setIgvTipo("40");
                    }
                    if (linea.getIgv() != null && linea.getIgv().compareTo(java.math.BigDecimal.ZERO) > 0) {
                        linea.setIgv(java.math.BigDecimal.ZERO);
                    }
                }
            }
        }
    }

    // ── Defectos ─────────────────────────────────────────────────

    private static void aplicarDefectos(BorradorFactura factura) {
        if (factura.getMoneda() == null) {
            factura.setMoneda("PEN");
        }
        if (factura.getTasaIgv() == null) {
            factura.setTasaIgv(TASA_IGV_DEFECTO);
        }
        if (factura.getTipoComprobante() == null && factura.getSerie() != null) {
            String serie = factura.getSerie().toUpperCase();
            if (serie.startsWith("F")) {
                factura.setTipoComprobante("01");
            } else if (serie.startsWith("B")) {
                factura.setTipoComprobante("03");
            }
        }
        if (factura.getTipoOperacion() == null) {
            factura.setTipoOperacion("0101");
        }
    }

    // ── Líneas ───────────────────────────────────────────────────

    private static void ensamblarLineas(DocumentoBase documento) {
        List<LineaDetalle> detalles = documento.getDetalles();
        if (detalles == null) return;

        BigDecimal tasaIgvGlobal = documento.getTasaIgv() != null
                ? documento.getTasaIgv()
                : TASA_IGV_DEFECTO;

        for (LineaDetalle linea : detalles) {
            ensamblarLinea(linea, tasaIgvGlobal, documento.getTasaIcb());
        }
    }

    static void ensamblarLinea(LineaDetalle linea, BigDecimal tasaIgvGlobal, BigDecimal tasaIcbGlobal) {
        // Unidad de medida por defecto
        if (linea.getUnidadMedida() == null) {
            linea.setUnidadMedida("NIU");
        }

        // Tasa IGV: usar la de la línea o la global
        BigDecimal tasaIgv = linea.getTasaIgv() != null ? linea.getTasaIgv() : tasaIgvGlobal;
        linea.setTasaIgv(tasaIgv);

        // Tipo de afectación IGV por defecto (gravado - operación onerosa)
        if (linea.getIgvTipo() == null) {
            linea.setIgvTipo("10");
        }

        // Cálculo de IGV base imponible e IGV si no fueron proporcionados
        if (linea.getIgvBaseImponible() == null && linea.getCantidad() != null && linea.getPrecio() != null) {
            BigDecimal precioUnitario;
            if (linea.isPrecioConImpuestos()) {
                // Precio incluye IGV: base = precio / (1 + tasa)
                precioUnitario = linea.getPrecio()
                        .divide(BigDecimal.ONE.add(tasaIgv), 10, REDONDEO);
            } else {
                precioUnitario = linea.getPrecio();
            }
            linea.setPrecio(precioUnitario.setScale(10, REDONDEO));
            linea.setIgvBaseImponible(
                    linea.getCantidad().multiply(precioUnitario).setScale(ESCALA, REDONDEO)
            );
        }

        if (linea.getIgv() == null && linea.getIgvBaseImponible() != null) {
            String tipo = linea.getIgvTipo();
            if (esGravado(tipo)) {
                linea.setIgv(
                        linea.getIgvBaseImponible().multiply(tasaIgv).setScale(ESCALA, REDONDEO)
                );
            } else {
                linea.setIgv(BigDecimal.ZERO.setScale(ESCALA, REDONDEO));
            }
        }

        // ISC (si aplica y no fue proporcionado)
        if (linea.getIsc() == null && linea.getTasaIsc() != null && linea.getIscBaseImponible() == null) {
            if (linea.getIgvBaseImponible() != null) {
                linea.setIscBaseImponible(linea.getIgvBaseImponible());
            }
        }
        if (linea.getIsc() == null && linea.getTasaIsc() != null && linea.getIscBaseImponible() != null) {
            linea.setIsc(
                    linea.getIscBaseImponible().multiply(linea.getTasaIsc()).setScale(ESCALA, REDONDEO)
            );
        }

        // ICBPER (si aplica y no fue proporcionado)
        if (linea.isIcbAplica() && linea.getIcb() == null && linea.getCantidad() != null) {
            BigDecimal tasaIcb = linea.getTasaIcb() != null ? linea.getTasaIcb() : tasaIcbGlobal;
            if (tasaIcb != null) {
                linea.setTasaIcb(tasaIcb);
                linea.setIcb(
                        linea.getCantidad().multiply(tasaIcb).setScale(ESCALA, REDONDEO)
                );
            }
        }

        // Total impuestos de la línea
        if (linea.getTotalImpuestos() == null) {
            BigDecimal total = BigDecimal.ZERO;
            if (linea.getIgv() != null) total = total.add(linea.getIgv());
            if (linea.getIsc() != null) total = total.add(linea.getIsc());
            if (linea.getIcb() != null) total = total.add(linea.getIcb());
            linea.setTotalImpuestos(total.setScale(ESCALA, REDONDEO));
        }

        // Precio de referencia (para PricingReference en XML)
        if (linea.getPrecioReferencia() == null && linea.getCantidad() != null
                && linea.getCantidad().compareTo(BigDecimal.ZERO) > 0
                && linea.getIgvBaseImponible() != null) {
            if (esGratuito(linea.getIgvTipo())) {
                linea.setPrecioReferencia(BigDecimal.ZERO.setScale(ESCALA, REDONDEO));
                linea.setPrecioReferenciaTipo("02");
            } else {
                BigDecimal base = linea.getIgvBaseImponible();
                BigDecimal igv = linea.getIgv() != null ? linea.getIgv() : BigDecimal.ZERO;
                linea.setPrecioReferencia(
                        base.add(igv).divide(linea.getCantidad(), ESCALA, REDONDEO)
                );
                linea.setPrecioReferenciaTipo("01");
            }
        }
    }

    // ── Totales de impuestos ─────────────────────────────────────

    private static void ensamblarTotalImpuestos(DocumentoBase documento) {
        if (documento.getTotalImpuestos() != null) return;
        List<LineaDetalle> detalles = documento.getDetalles();
        if (detalles == null || detalles.isEmpty()) return;

        BigDecimal totalGeneral = BigDecimal.ZERO;
        BigDecimal gravadoImp = BigDecimal.ZERO;
        BigDecimal gravadoBase = BigDecimal.ZERO;
        BigDecimal exoneradoImp = BigDecimal.ZERO;
        BigDecimal exoneradoBase = BigDecimal.ZERO;
        BigDecimal inafectoImp = BigDecimal.ZERO;
        BigDecimal inafectoBase = BigDecimal.ZERO;
        BigDecimal gratuitoImp = BigDecimal.ZERO;
        BigDecimal gratuitoBase = BigDecimal.ZERO;
        BigDecimal exportacionImp = BigDecimal.ZERO;
        BigDecimal exportacionBase = BigDecimal.ZERO;
        BigDecimal ivapImp = BigDecimal.ZERO;
        BigDecimal ivapBase = BigDecimal.ZERO;
        BigDecimal icbImp = BigDecimal.ZERO;
        BigDecimal iscImp = BigDecimal.ZERO;
        BigDecimal iscBase = BigDecimal.ZERO;

        for (LineaDetalle linea : detalles) {
            String tipo = linea.getIgvTipo() != null ? linea.getIgvTipo() : "10";
            BigDecimal igv = orZero(linea.getIgv());
            BigDecimal base = orZero(linea.getIgvBaseImponible());

            if (esGravado(tipo)) {
                gravadoImp = gravadoImp.add(igv);
                gravadoBase = gravadoBase.add(base);
            } else if (esExonerado(tipo)) {
                exoneradoImp = exoneradoImp.add(igv);
                exoneradoBase = exoneradoBase.add(base);
            } else if (esInafecto(tipo)) {
                inafectoImp = inafectoImp.add(igv);
                inafectoBase = inafectoBase.add(base);
            } else if (esGratuito(tipo)) {
                gratuitoImp = gratuitoImp.add(igv);
                gratuitoBase = gratuitoBase.add(base);
            } else if (esExportacion(tipo)) {
                exportacionImp = exportacionImp.add(igv);
                exportacionBase = exportacionBase.add(base);
            } else if (esIvap(tipo)) {
                ivapImp = ivapImp.add(igv);
                ivapBase = ivapBase.add(base);
            }

            if (linea.getIsc() != null) {
                iscImp = iscImp.add(linea.getIsc());
                iscBase = iscBase.add(orZero(linea.getIscBaseImponible()));
            }
            if (linea.getIcb() != null) {
                icbImp = icbImp.add(linea.getIcb());
            }
        }

        totalGeneral = gravadoImp.add(exoneradoImp).add(inafectoImp)
                .add(iscImp).add(icbImp).add(ivapImp);

        documento.setTotalImpuestos(new TotalImpuestos(
                totalGeneral.setScale(ESCALA, REDONDEO),
                nulo(gravadoImp), nulo(gravadoBase),
                nulo(exoneradoImp), nulo(exoneradoBase),
                nulo(inafectoImp), nulo(inafectoBase),
                nulo(gratuitoImp), nulo(gratuitoBase),
                nulo(exportacionImp), nulo(exportacionBase),
                nulo(ivapImp), nulo(ivapBase),
                nulo(icbImp),
                nulo(iscImp), nulo(iscBase)
        ));
    }

    // ── Total importe ────────────────────────────────────────────

    private static void ensamblarTotalImporte(BorradorFactura factura) {
        if (factura.getTotalImporte() != null) return;

        TotalImpuestos imp = factura.getTotalImpuestos();
        if (imp == null) return;

        BigDecimal importeSinImpuestos = BigDecimal.ZERO;
        BigDecimal importeConImpuestos;
        BigDecimal anticipoTotal = BigDecimal.ZERO;
        BigDecimal descuentoTotal = BigDecimal.ZERO;

        // Sumar bases imponibles (sin gratuita)
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.gravadoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.exoneradoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.inafectoBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.exportacionBaseImponible()));
        importeSinImpuestos = importeSinImpuestos.add(orZero(imp.ivapBaseImponible()));

        importeConImpuestos = importeSinImpuestos.add(imp.total());

        // Anticipos
        if (factura.getAnticipos() != null) {
            for (var anticipo : factura.getAnticipos()) {
                if (anticipo.monto() != null) {
                    anticipoTotal = anticipoTotal.add(anticipo.monto());
                }
            }
        }

        // Descuentos globales
        if (factura.getDescuentos() != null) {
            for (var descuento : factura.getDescuentos()) {
                if (descuento.monto() != null) {
                    descuentoTotal = descuentoTotal.add(descuento.monto());
                }
            }
        }

        BigDecimal importe = importeConImpuestos
                .subtract(anticipoTotal)
                .subtract(descuentoTotal);

        factura.setTotalImporte(new TotalImporte(
                importe.setScale(ESCALA, REDONDEO),
                importeSinImpuestos.setScale(ESCALA, REDONDEO),
                importeConImpuestos.setScale(ESCALA, REDONDEO),
                anticipoTotal.compareTo(BigDecimal.ZERO) != 0 ? anticipoTotal.setScale(ESCALA, REDONDEO) : null,
                descuentoTotal.compareTo(BigDecimal.ZERO) != 0 ? descuentoTotal.setScale(ESCALA, REDONDEO) : null
        ));
    }

    // ── Reglas de boleta ────────────────────────────────────────

    private static void aplicarReglasBoleta(BorradorFactura factura) {
        if (!"03".equals(factura.getTipoComprobante())) return;

        if (factura.getReceptor() != null) return;

        BigDecimal monto = montoTotalDocumento(factura);
        if (monto.compareTo(UMBRAL_BOLETA_RECEPTOR) < 0) {
            factura.setReceptor(new ReceptorDocumento("0", "0", "CLIENTE VARIOS", null, null));
        }
    }

    // ── Leyendas SUNAT automáticas ──────────────────────────────

    private static void ensamblarLeyendasSunat(BorradorFactura factura) {
        Map<String, String> leyendas = factura.getLeyendas() != null
                ? new LinkedHashMap<>(factura.getLeyendas())
                : new LinkedHashMap<>();

        TotalImporte total = factura.getTotalImporte();
        if (total != null && total.importe() != null && !leyendas.containsKey("1000")) {
            leyendas.put("1000", NumeroALetras.formatearMonto(total.importe(), factura.getMoneda()));
        }

        if (!leyendas.containsKey("1002") && tieneOperacionGratuita(factura)) {
            leyendas.put("1002", "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE");
        }

        if (!leyendas.containsKey("2006") && esOperacionDetraccion(factura.getTipoOperacion())) {
            leyendas.put("2006", "OPERACION SUJETA A DETRACCION");
        }

        if (!leyendas.containsKey("2007") && tieneOperacionIvap(factura)) {
            leyendas.put("2007", "OPERACION SUJETA AL IVAP");
        }

        factura.setLeyendas(leyendas);
    }

    private static boolean esOperacionDetraccion(String tipoOperacion) {
        return "1001".equals(tipoOperacion)
                || "1002".equals(tipoOperacion)
                || "1003".equals(tipoOperacion)
                || "1004".equals(tipoOperacion);
    }

    private static boolean tieneOperacionGratuita(BorradorFactura factura) {
        if (factura.getDetalles() == null) return false;
        return factura.getDetalles().stream()
                .anyMatch(linea -> esGratuito(linea.getIgvTipo())
                        && orZero(linea.getIgvBaseImponible()).compareTo(BigDecimal.ZERO) > 0);
    }

    private static boolean tieneOperacionIvap(BorradorFactura factura) {
        if (factura.getDetalles() == null) return false;
        return factura.getDetalles().stream()
                .anyMatch(linea -> esIvap(linea.getIgvTipo())
                        && orZero(linea.getIgvBaseImponible()).compareTo(BigDecimal.ZERO) > 0);
    }

    private static BigDecimal montoTotalDocumento(BorradorFactura factura) {
        if (factura.getTotalImporte() != null && factura.getTotalImporte().importe() != null) {
            return factura.getTotalImporte().importe();
        }
        if (factura.getDetalles() == null) return BigDecimal.ZERO;
        BigDecimal acumulado = BigDecimal.ZERO;
        for (LineaDetalle linea : factura.getDetalles()) {
            if (linea.getCantidad() == null || linea.getPrecio() == null) continue;
            acumulado = acumulado.add(linea.getCantidad().multiply(linea.getPrecio()));
        }
        return acumulado.setScale(ESCALA, REDONDEO);
    }

    // ── Helpers de tipo IGV (Catálogo 07 SUNAT) ──────────────────

    static boolean esGravado(String tipo) {
        return tipo != null && tipo.startsWith("1") && !tipo.equals("17");
    }

    static boolean esExonerado(String tipo) {
        return "20".equals(tipo) || "21".equals(tipo);
    }

    static boolean esInafecto(String tipo) {
        return tipo != null && tipo.startsWith("3");
    }

    static boolean esGratuito(String tipo) {
        return tipo != null && (
                "11".equals(tipo) || "12".equals(tipo) || "13".equals(tipo)
                        || "14".equals(tipo) || "15".equals(tipo) || "16".equals(tipo)
                        || "17".equals(tipo)
                        || "21".equals(tipo)
                        || "31".equals(tipo) || "32".equals(tipo) || "33".equals(tipo)
                        || "34".equals(tipo) || "35".equals(tipo) || "36".equals(tipo)
                        || "37".equals(tipo)
        );
    }

    static boolean esExportacion(String tipo) {
        return "40".equals(tipo);
    }

    static boolean esIvap(String tipo) {
        return "17".equals(tipo);
    }

    private static BigDecimal orZero(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    private static BigDecimal nulo(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) != 0 ? valor.setScale(ESCALA, REDONDEO) : null;
    }
}
