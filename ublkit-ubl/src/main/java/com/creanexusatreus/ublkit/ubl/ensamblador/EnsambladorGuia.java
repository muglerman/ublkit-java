package com.cna.ublkit.ubl.ensamblador;

import com.cna.ublkit.ubl.modelo.guia.BorradorGuiaRemision;
import com.cna.ublkit.ubl.modelo.guia.DatosEnvio;
import com.cna.ublkit.ubl.modelo.guia.PuntoDestino;
import com.cna.ublkit.ubl.modelo.guia.PuntoPartida;
import java.util.ArrayList;
import java.util.List;

public class EnsambladorGuia {

    public static BorradorGuiaRemision ensamblar(BorradorGuiaRemision guia) {
        aplicarReglasEnvio(guia);
        return guia;
    }

    private static void aplicarReglasEnvio(BorradorGuiaRemision guia) {
        DatosEnvio envio = guia.getEnvio();
        if (envio == null) return;

        String motivo = envio.getMotivoTraslado(); // Motivo can be the code or description, wait: tipoTraslado is the code (Catálogo 20).
        String tipoTraslado = envio.getTipoTraslado();

        if (tipoTraslado != null) {
            // Importación (08)
            if ("08".equals(tipoTraslado)) {
                if (envio.getPartida() != null && envio.getPartida().codigoLocal() == null) {
                    envio.setPartida(new PuntoPartida(
                        envio.getPartida().ubigeo(),
                        envio.getPartida().direccion(),
                        "0000",
                        envio.getPartida().ruc()
                    ));
                }
            }

            // Exportación (09)
            if ("09".equals(tipoTraslado)) {
                if (envio.getDestino() != null && envio.getDestino().codigoLocal() == null) {
                    envio.setDestino(new PuntoDestino(
                        envio.getDestino().ubigeo(),
                        envio.getDestino().direccion(),
                        "0000",
                        envio.getDestino().ruc()
                    ));
                }
            }

            // Traslados entre establecimientos (04)
            if ("04".equals(tipoTraslado)) {
                if (envio.getPartida() != null && envio.getPartida().codigoLocal() == null) {
                    envio.setPartida(new PuntoPartida(
                        envio.getPartida().ubigeo(),
                        envio.getPartida().direccion(),
                        "0000",
                        envio.getPartida().ruc()
                    ));
                }
                if (envio.getDestino() != null && envio.getDestino().codigoLocal() == null) {
                    envio.setDestino(new PuntoDestino(
                        envio.getDestino().ubigeo(),
                        envio.getDestino().direccion(),
                        "0000",
                        envio.getDestino().ruc()
                    ));
                }
            }

            // Transbordo (cambio de transportista en ruta)
            // Se puede marcar un indicador (código 06 de Catálogo 20)
            if ("06".equals(tipoTraslado) || "Transbordo".equalsIgnoreCase(motivo)) {
                List<String> indicadores = envio.getIndicadores();
                if (indicadores == null) {
                    indicadores = new ArrayList<>();
                }
                if (!indicadores.contains("SUNAT_Envio_IndicadorTransbordo")) {
                    indicadores.add("SUNAT_Envio_IndicadorTransbordo");
                }
                envio.setIndicadores(indicadores);
            }
        }
    }
}
