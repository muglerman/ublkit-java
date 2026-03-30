package com.cna.ublkit.ubl.modelo.sunat.resumen;

import com.cna.ublkit.ubl.modelo.sunat.DocumentoSunat;
import java.util.List;

/**
 * Resumen Diario de Boletas (RC).
 * Permite informar a SUNAT las boletas de venta y notas asociadas emitidas en un día.
 *
 * @since 0.1.0
 */
public class ResumenDiario extends DocumentoSunat {

    private List<ItemResumenDiario> comprobantes;

    public ResumenDiario() {}

    public List<ItemResumenDiario> getComprobantes() { return comprobantes; }
    public void setComprobantes(List<ItemResumenDiario> comprobantes) { this.comprobantes = comprobantes; }
}
