package com.cna.ublkit.sign.certificado;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class RepositorioCertificadosTest {

    @Test
    void obtenerOCargar_debeCargarUnaSolaVezEnConcurrencia() throws Exception {
        RepositorioCertificados repositorio = new RepositorioCertificados();
        AtomicInteger invocaciones = new AtomicInteger(0);

        DetallesCertificado esperado = CargadorCertificado.cargar(
            new OrigenCertificado(
                RepositorioCertificadosTest.class.getClassLoader().getResourceAsStream("test-keystore.p12"),
                "changeit"
            )
        );

        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Callable<DetallesCertificado>> tareas = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                tareas.add(() -> repositorio.obtenerOCargar("empresa-20123456789", () -> {
                    invocaciones.incrementAndGet();
                    return esperado;
                }));
            }

            List<Future<DetallesCertificado>> resultados = pool.invokeAll(tareas);

            for (Future<DetallesCertificado> futuro : resultados) {
                assertThat(futuro.get()).isSameAs(esperado);
            }

            assertThat(invocaciones.get()).isEqualTo(1);
            assertThat(repositorio.size()).isEqualTo(1);
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void invalidar_y_limpiar_debenActualizarCache() {
        RepositorioCertificados repositorio = new RepositorioCertificados();

        DetallesCertificado d1 = CargadorCertificado.cargar(
            new OrigenCertificado(
                RepositorioCertificadosTest.class.getClassLoader().getResourceAsStream("test-keystore.p12"),
                "changeit"
            )
        );

        repositorio.obtenerOCargar("k1", () -> d1);
        assertThat(repositorio.size()).isEqualTo(1);

        repositorio.invalidar("k1");
        assertThat(repositorio.size()).isEqualTo(0);

        repositorio.obtenerOCargar("k2", () -> d1);
        assertThat(repositorio.size()).isEqualTo(1);

        repositorio.limpiar();
        assertThat(repositorio.size()).isEqualTo(0);
    }
}
