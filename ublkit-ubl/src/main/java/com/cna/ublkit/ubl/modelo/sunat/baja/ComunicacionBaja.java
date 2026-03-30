package com.cna.ublkit.ubl.modelo.sunat.baja;

import com.cna.ublkit.ubl.modelo.sunat.DocumentoSunat;
import java.util.List;

/**
 * Comunicación de Baja (RA).
 * Permite dar de baja comprobantes de pago electrónicos (facturas, notas).
 *
 * @since 0.1.0
 */
public class ComunicacionBaja extends DocumentoSunat {

    private List<ItemBaja> comprobantes;

    public ComunicacionBaja() {}

    public List<ItemBaja> getComprobantes() { return comprobantes; }
    public void setComprobantes(List<ItemBaja> comprobantes) { this.comprobantes = comprobantes; }
}
