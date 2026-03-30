package com.cna.ublkit.ubl.modelo;

import com.cna.ublkit.ubl.modelo.actor.EmisorDocumento;
import com.cna.ublkit.ubl.modelo.actor.ReceptorDocumento;
import com.cna.ublkit.ubl.modelo.complemento.*;
import com.cna.ublkit.ubl.modelo.guia.*;
import com.cna.ublkit.ubl.modelo.linea.LineaDetalle;
import com.cna.ublkit.ubl.modelo.total.TotalImporte;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModeloDocumentalTest {

    @Test
    void testBorradorFactura_creacionBasica() {
        var emisor = new EmisorDocumento("20123456789", "Mi Empresa", "Mi Empresa SAC", null, null);
        var receptor = new ReceptorDocumento("6", "20987654321", "Cliente SAC", null, null);

        var linea = new LineaDetalle();
        linea.setDescripcion("Laptop HP");
        linea.setCantidad(BigDecimal.ONE);
        linea.setPrecio(new BigDecimal("2500.00"));

        var factura = new BorradorFactura();
        factura.setSerie("F001");
        factura.setNumero(1);
        factura.setMoneda("PEN");
        factura.setFechaEmision(LocalDate.of(2026, 3, 30));
        factura.setEmisor(emisor);
        factura.setReceptor(receptor);
        factura.setDetalles(List.of(linea));
        factura.setTipoComprobante("01");
        factura.setTipoOperacion("0101");

        assertThat(factura.getSerie()).isEqualTo("F001");
        assertThat(factura.getTipoComprobante()).isEqualTo("01");
        assertThat(factura.getEmisor().ruc()).isEqualTo("20123456789");
        assertThat(factura.getReceptor().nombre()).isEqualTo("Cliente SAC");
        assertThat(factura.getDetalles()).hasSize(1);
        assertThat(factura.getDetalles().get(0).getDescripcion()).isEqualTo("Laptop HP");
    }

    @Test
    void testBorradorNotaCredito_creacionBasica() {
        var nota = new BorradorNotaCredito();
        nota.setSerie("F001");
        nota.setNumero(1);
        nota.setMoneda("PEN");
        nota.setFechaEmision(LocalDate.of(2026, 3, 30));
        nota.setTipoNota("01"); // Catálogo 09: Anulación de la operación
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setComprobanteAfectadoTipo("01");
        nota.setSustentoDescripcion("Error en el monto");
        nota.setTotalImporte(new TotalImporte(
                new BigDecimal("100"), new BigDecimal("84.75"),
                new BigDecimal("100"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertThat(nota.getTipoNota()).isEqualTo("01");
        assertThat(nota.getComprobanteAfectadoSerieNumero()).isEqualTo("F001-1");
        assertThat(nota.getTotalImporte().importe()).isEqualTo(new BigDecimal("100"));
    }

    @Test
    void testBorradorNotaDebito_creacionBasica() {
        var nota = new BorradorNotaDebito();
        nota.setTipoNota("01"); // Catálogo 10: Intereses por mora
        nota.setComprobanteAfectadoSerieNumero("F001-1");
        nota.setSustentoDescripcion("Intereses por mora");

        assertThat(nota.getTipoNota()).isEqualTo("01");
        assertThat(nota).isInstanceOf(DocumentoBase.class);
    }

    @Test
    void testDocumentoBase_esSealedClass() {
        assertThat(DocumentoBase.class.isSealed()).isTrue();
        assertThat(DocumentoBase.class.getPermittedSubclasses())
                .containsExactlyInAnyOrder(
                        BorradorFactura.class,
                        BorradorNotaCredito.class,
                        BorradorNotaDebito.class
                );
    }

    @Test
    void testBorradorFactura_conDetraccion() {
        var factura = new BorradorFactura();
        factura.setTipoOperacion("1001");
        factura.setDetraccion(new Detraccion(
                "001", "0004-1234567", "014",
                new BigDecimal("0.12"), new BigDecimal("120.00")));

        assertThat(factura.getDetraccion().tipoBienDetraido()).isEqualTo("014");
        assertThat(factura.getDetraccion().porcentaje()).isEqualByComparingTo("0.12");
    }

    @Test
    void testBorradorFactura_conFormaDePagoCredito() {
        var cuota1 = new CuotaDePago(new BigDecimal("500"), LocalDate.of(2026, 4, 30));
        var cuota2 = new CuotaDePago(new BigDecimal("500"), LocalDate.of(2026, 5, 31));
        var formaPago = new FormaDePago("Credito", new BigDecimal("1000"), List.of(cuota1, cuota2));

        var factura = new BorradorFactura();
        factura.setFormaDePago(formaPago);

        assertThat(factura.getFormaDePago().tipo()).isEqualTo("Credito");
        assertThat(factura.getFormaDePago().cuotas()).hasSize(2);
    }

    @Test
    void testBorradorGuiaRemision_GRERemitente() {
        var guia = new BorradorGuiaRemision();
        guia.setSerie("T001");
        guia.setNumero(1);
        guia.setTipoComprobante("09");
        guia.setFechaEmision(LocalDate.of(2026, 3, 30));

        var envio = new DatosEnvio();
        envio.setTipoTraslado("01");
        envio.setPesoTotal(new BigDecimal("50.00"));
        envio.setPesoTotalUnidadMedida("KGM");
        envio.setTipoModalidadTraslado("02");
        envio.setFechaTraslado(LocalDate.of(2026, 4, 1));
        envio.setPartida(new PuntoPartida("150101", "Av Lima 123", "0000", null));
        envio.setDestino(new PuntoDestino("040101", "Jr Arequipa 456", null, null));

        guia.setEnvio(envio);
        guia.setDetalles(List.of(new LineaGuia("NIU", BigDecimal.TEN, "Cajas de papel", "P001", null, List.of())));

        assertThat(guia.isGRERemitente()).isTrue();
        assertThat(guia.isGRETransportista()).isFalse();
        assertThat(guia.getEnvio().getPartida().ubigeo()).isEqualTo("150101");
        assertThat(guia.getDetalles()).hasSize(1);
    }

    @Test
    void testBorradorGuiaRemision_GRETransportista() {
        var guia = new BorradorGuiaRemision();
        guia.setTipoComprobante("31");
        guia.setSerie("V001");

        assertThat(guia.isGRETransportista()).isTrue();
        assertThat(guia.isGRERemitente()).isFalse();
    }
}
