package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.sunat.baja.ComunicacionBaja;
import com.cna.ublkit.ubl.modelo.sunat.baja.ItemBaja;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnsambladorComunicacionBajaTest {

    @Test
    void ensamblar_aplicaFechaEmisionPorDefecto() {
        ComunicacionBaja baja = crearBajaSinDefectos();
        baja.setFechaEmision(null);

        EnsambladorComunicacionBaja.ensamblar(baja);

        assertNotNull(baja.getFechaEmision());
        assertEquals(LocalDate.now(), baja.getFechaEmision());
    }

    @Test
    void ensamblar_aplicaFechaEmisionComprobantesPorDefecto() {
        ComunicacionBaja baja = crearBajaSinDefectos();
        baja.setFechaEmisionComprobantes(null);

        EnsambladorComunicacionBaja.ensamblar(baja);

        assertNotNull(baja.getFechaEmisionComprobantes());
        assertEquals(baja.getFechaEmision(), baja.getFechaEmisionComprobantes());
    }

    @Test
    void ensamblar_aplicaNumeroPorDefecto() {
        ComunicacionBaja baja = crearBajaSinDefectos();
        baja.setNumero(null);

        EnsambladorComunicacionBaja.ensamblar(baja);

        assertEquals(1, baja.getNumero());
    }

    @Test
    void ensamblar_respetaValoresExistentes() {
        ComunicacionBaja baja = crearBajaSinDefectos();
        LocalDate fecha = LocalDate.of(2026, 1, 1);
        baja.setFechaEmision(fecha);
        baja.setNumero(42);

        EnsambladorComunicacionBaja.ensamblar(baja);

        assertEquals(fecha, baja.getFechaEmision());
        assertEquals(42, baja.getNumero());
    }

    @Test
    void ensamblar_retornaMismaInstancia() {
        ComunicacionBaja baja = crearBajaSinDefectos();
        ComunicacionBaja resultado = EnsambladorComunicacionBaja.ensamblar(baja);
        assertSame(baja, resultado);
    }

    private ComunicacionBaja crearBajaSinDefectos() {
        ComunicacionBaja baja = new ComunicacionBaja();
        baja.setFechaEmision(LocalDate.of(2026, 4, 15));
        baja.setFechaEmisionComprobantes(LocalDate.of(2026, 4, 14));
        baja.setNumero(1);
        baja.setEmisor(new EmisorDocumento("20123456789", null, "EMPRESA S.A.C.", null, null));
        baja.setComprobantes(List.of(new ItemBaja("F001", 1, "01", "Motivo")));
        return baja;
    }
}
