package com.cna.ublkit.core.enumerado;

/**
 * Enumera los tipos documentales principales soportados por UBLKit.
 *
 * <p>Esta enumeración representa la naturaleza funcional del documento y
 * permite tomar decisiones de validación, serialización y transporte.</p>
 *
 * @author Edwin Luis Barboza Pinedo
 * @since 0.1.0
 */
public enum TipoDocumento {
    FACTURA,
    BOLETA,
    NOTA_CREDITO,
    NOTA_DEBITO,
    GUIA_REMISION_REMITENTE,
    GUIA_REMISION_TRANSPORTISTA,
    RESUMEN_DIARIO,
    COMUNICACION_BAJA
}
